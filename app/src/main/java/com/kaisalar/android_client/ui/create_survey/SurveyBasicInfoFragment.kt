package com.kaisalar.android_client.ui.create_survey

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.github.mikephil.charting.utils.ColorTemplate.rgb
import com.kaisalar.android_client.R
import com.kaisalar.android_client.util.StringValidationUtils
import com.kaisalar.android_client.viewmodel.CreateSurveyViewModel
import dev.sasikanth.colorsheet.ColorSheet
import kotlinx.android.synthetic.main.survey_basic_info_fragment.*

class SurveyBasicInfoFragment : Fragment() {

    companion object {
        fun newInstance() = SurveyBasicInfoFragment()
    }

    private val colors = mutableListOf(
        rgb("#eb3b5a"),
        rgb("#4b6584"),
        rgb("#2d98da"),
        rgb("#f79f1f"),
        rgb("#5758bb"),
        rgb("#1b1464"),
        rgb("#6f1e51"),
        rgb("#26de81")
    )
    private lateinit var viewModel: CreateSurveyViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.survey_basic_info_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        activity?.run {
            viewModel = ViewModelProviders.of(this).get(CreateSurveyViewModel::class.java)
        }

        viewModel.getCreatedSurvey().observe(this, Observer {
            titleEditText.setText(it.title)
            descriptionEditText.setText(it.description)
        })

        titleEditText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {}
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (!StringValidationUtils.isBlankOrEmpty(s.toString())) titleTextLayout.error = null
                viewModel.postTitle(s.toString())
            }
        })

        descriptionEditText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {}
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (!StringValidationUtils.isBlankOrEmpty(s.toString())) descriptionTextLayout.error = null
                viewModel.postDescription(s.toString())
            }
        })


        nextButton.setOnClickListener {
            val title = titleEditText.text.toString()
            if (StringValidationUtils.isBlankOrEmpty(title)) {
                titleTextLayout.error = "Please enter the survey's title."
            } else {
                val action = SurveyBasicInfoFragmentDirections.nextAction()
                findNavController().navigate(action)
            }
        }

        cancelButton.setOnClickListener {
            activity?.run {
                onBackPressed()
            }
        }

        pickColotButton.setOnClickListener {

            ColorSheet().colorPicker(
                colors = colors.toIntArray(),
                noColorOption = true,
                listener = { color ->

                })
                .show(activity?.supportFragmentManager!!)
        }
    }
}
