package com.kaisalar.android_client.ui.authentication

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
import androidx.navigation.ui.navigateUp
import com.kaisalar.android_client.R
import com.kaisalar.android_client.util.StringValidationUtils
import com.kaisalar.android_client.viewmodel.AuthViewModel
import kotlinx.android.synthetic.main.first_sign_up_fragment.submitButton
import kotlinx.android.synthetic.main.second_sign_up_fragment.*

class SecondSignUpFragment : Fragment() {

    companion object {
        fun newInstance() = SecondSignUpFragment()
    }

    private lateinit var viewModel: AuthViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.second_sign_up_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        activity?.run {
            viewModel = ViewModelProviders.of(this).get(AuthViewModel::class.java)
        }

        viewModel.getCreationUser().observe(this, Observer {
            emailEditText.setText(it.email)
        })

        emailEditText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {}

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (isValidEmail(s.toString())) emailTextLayout.error = null
                viewModel.setUserEmail(s.toString())
            }
        })

        submitButton.setOnClickListener {
            if (validateInput()) {
                val nextAction =
                    SecondSignUpFragmentDirections.nextAction()
                findNavController().navigate(nextAction)
            }
        }

        prevButton.setOnClickListener {
            findNavController().navigateUp(null)
        }
    }

    private fun validateInput(): Boolean {
        val email = emailEditText.text.toString()

        if (StringValidationUtils.isBlankOrEmpty(email)) {
            emailTextLayout.error = "Please enter your email address."
            return false
        }

        if (!StringValidationUtils.isEmail(email)) {
            emailTextLayout.error = "Please enter a valid email address."
            return false
        }

        return true
    }

    private fun isValidEmail(email: String): Boolean {
        return StringValidationUtils.isEmail(email) && !StringValidationUtils.isBlankOrEmpty(email)
    }
}


