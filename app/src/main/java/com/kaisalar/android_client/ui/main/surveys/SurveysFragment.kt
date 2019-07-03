package com.kaisalar.android_client.ui.main.surveys

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import com.kaisalar.android_client.R
import com.kaisalar.android_client.data.db.entity.SurveyEntity
import com.kaisalar.android_client.data.webservice.HttpRequestQueue
import com.kaisalar.android_client.data.webservice.SurveysService
import com.kaisalar.android_client.data.webservice.TAG_GET_ALL_SURVEYS
import com.kaisalar.android_client.ui.create_survey.CreateSurveyActivity
import com.kaisalar.android_client.ui.survey_detail.EXTRA_SURVEY_ID
import com.kaisalar.android_client.ui.survey_detail.SurveyDetailsActivity
import com.kaisalar.android_client.viewmodel.SurveysViewModel
import kotlinx.android.synthetic.main.surveys_fragment.*



class SurveysFragment : Fragment() {

    companion object {
        fun newInstance() = SurveysFragment()
    }

    private lateinit var viewModel: SurveysViewModel

    private val surveysList = mutableListOf<SurveyEntity>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.surveys_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        activity?.run {
            viewModel = ViewModelProviders.of(this).get(SurveysViewModel::class.java)
        }

        initUI()

        if (!viewModel.isInitUpdated) initUpdate()
    }

    private fun initUI() {
        // setup the refresh layout
        surveysRefreshLayout.setColorSchemeColors(
            resources.getColor(R.color.color2),
            resources.getColor(R.color.color3),
            resources.getColor(R.color.color4)
        )

        surveysRefreshLayout.setOnRefreshListener {
            swipeUpdate()
        }

        // setup the recycler view
        val adapter = SurveysAdapter(
            context = context!!,
            surveys = surveysList,
            shareOnClickListener = {
                shareUrl(it)
            },
            deleteOnClickListener = {
                deleteSurvey(it)
            },
            surveyOnClickListener = {
                surveyOnClick(it)
            }
        )

        val layoutManager = LinearLayoutManager(context)

        surveysRecyclerView.apply {
            this.adapter = adapter
            this.layoutManager = layoutManager
        }

        viewModel.surveys.observe(this, Observer {
            surveysList.clear()
            surveysList.addAll(it)
            adapter.notifyDataSetChanged()
        })

        // setup the create button
        surveysRecyclerView.onFlingListener = object : RecyclerView.OnFlingListener() {
            override fun onFling(velocityX: Int, velocityY: Int): Boolean {
                if (velocityY < 0)
                    createNewSurveyButton.extend()
                else if (velocityY > 0)
                    createNewSurveyButton.shrink()//Code to hide the UI, I have  a custom one that slides down the nav  bar and the fab
                return false
            }
        }

        createNewSurveyButton.setOnClickListener {
            startActivity(Intent(context, CreateSurveyActivity::class.java))
        }

        updateCard.visibility = View.GONE
    }

    private fun initUpdate() {
        surveysRefreshLayout?.isRefreshing = true

        viewModel.updateSurveys(
            onSuccess = {
                viewModel.isInitUpdated = true
                surveysRefreshLayout?.isRefreshing = false
            },
            onFailure = {
                surveysRefreshLayout?.isRefreshing = false
                Snackbar.make(createNewSurveyButton, "Can't update data", Snackbar.LENGTH_SHORT).show()
            }
        )
    }

    private fun swipeUpdate() {
        viewModel.updateSurveys(
            onSuccess = {
                surveysRefreshLayout?.isRefreshing = false
            },
            onFailure = {
                surveysRefreshLayout?.isRefreshing = false
                Snackbar.make(createNewSurveyButton, "Something went wrong!", Snackbar.LENGTH_SHORT).show()
            }
        )
    }

    private fun clearItAll() {
        HttpRequestQueue.getInstance(context!!).cancelFromRequestQueue(TAG_GET_ALL_SURVEYS)
        surveysRefreshLayout?.isRefreshing = false
        updateCard?.visibility = View.GONE
    }

    private fun shareUrl(survey: SurveyEntity) {
        val shareIntent = Intent(Intent.ACTION_SEND)
        shareIntent.type = "text/plain"
        shareIntent.putExtra(Intent.EXTRA_SUBJECT, survey.title)
        shareIntent.putExtra(Intent.EXTRA_TEXT, "http://www.surveymaster.com/filling/${survey._id}")

        startActivity(Intent.createChooser(shareIntent, "Share survey's link"))
    }

    private fun surveyOnClick(survey: SurveyEntity) {
        val intent = Intent(context, SurveyDetailsActivity::class.java)
        intent.putExtra(EXTRA_SURVEY_ID, survey._id)
        startActivity(intent)
    }

    private fun deleteSurvey(survey: SurveyEntity) {
        MaterialAlertDialogBuilder(context)
            .setTitle("Delete Survey")
            .setMessage("Are you sure to delete ${survey.title} permanently?")
            .setPositiveButton("Yes, Delete") { dialog, _ ->
                SurveysService.getInstance(context!!)
                    .deleteSurvey(
                        surveyId = survey._id,
                        onSuccess = {
                            viewModel.deleteSurvey(survey._id)
                            Snackbar.make(
                                createNewSurveyButton,
                                "Survey deleted successfully :)",
                                Snackbar.LENGTH_SHORT)
                                .show()
                            dialog.dismiss()
                        },
                        onFailure = {
                            Snackbar.make(
                                createNewSurveyButton,
                                "Cannot delete the survey right now :(",
                                Snackbar.LENGTH_SHORT)
                                .show()
                            dialog.dismiss()
                        }
                    )
                }
            .setNegativeButton("No", null)
            .show()
    }

    override fun onStop() {
        super.onStop()
        clearItAll()
    }
}
