package com.kaisalar.android_client.ui.survey_detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.kaisalar.android_client.R
import com.kaisalar.android_client.data.model.ResponseForGetting
import kotlinx.android.synthetic.main.responses_fragment.*

class ResponsesFragment : Fragment() {

    companion object {
        fun newInstance() = ResponsesFragment()
    }

    private lateinit var viewModel: SurveyDetailsViewModel
    private lateinit var globalAdapter: ResponsesAdapter
    private val responses = mutableListOf<ResponseForGetting>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.responses_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        activity?.run {
            viewModel = ViewModelProviders.of(this).get(SurveyDetailsViewModel::class.java)
        }

        val adapter = ResponsesAdapter(
            context = context!!,
            responses = responses,
            responseOnClickListener = {
                val action = ResponsesFragmentDirections.answersAction(it._id)
                findNavController().navigate(action)
            }
        )
        globalAdapter = adapter

        val layoutManager = LinearLayoutManager(context)

        responsesRecyclerView.apply {
            this.adapter = adapter
            this.layoutManager = layoutManager
        }

        initUpdate()
        resRefreshLayout.setColorSchemeColors(
            resources.getColor(R.color.color2),
            resources.getColor(R.color.color3),
            resources.getColor(R.color.color4)
        )

        resRefreshLayout.setOnRefreshListener {
            refreshUpdate()
        }
    }

    private fun initUpdate() {
        resRefreshLayout?.isRefreshing = true
        refreshUpdate()
    }

    private fun refreshUpdate() {
        viewModel.getSurveyResponses(
            onSuccess = {
                resRefreshLayout?.isRefreshing = false
                responses.clear()
                responses.addAll(it)
                globalAdapter.notifyDataSetChanged()
            },
            onFailure = {
                resRefreshLayout?.isRefreshing = false
                Snackbar.make(
                    responsesRecyclerView,
                    "Can't fetch data from server :(",
                    Snackbar.LENGTH_SHORT
                ).show()
            }
        )
    }

}
