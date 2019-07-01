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
import kotlinx.android.synthetic.main.first_sign_up_fragment.*

class FirstSignUpFragment : Fragment() {

    companion object {
        fun newInstance() = FirstSignUpFragment()
    }

    private lateinit var viewModel: AuthViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.first_sign_up_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        activity?.run {
            viewModel = ViewModelProviders.of(this).get(AuthViewModel::class.java)
        }

        viewModel.getCreationUser().observe(this, Observer {
            firstNameEditText.setText(it.firstName)
            lastNameEditText.setText(it.lastName)
        })

        firstNameEditText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {}

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (isValidName(s.toString())) firstNameTextLayout.error = null
                viewModel.setUserFirstName(s.toString())
            }
        })

        lastNameEditText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {}

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (isValidName(s.toString())) lastNameTextLayout.error = null
                viewModel.setUserLastName(s.toString())
            }
        })

        submitButton.setOnClickListener {
            if (validateInput()) {
                val nextAction =
                    FirstSignUpFragmentDirections.nextAction()
                findNavController().navigate(nextAction)
            }
        }

        cancelButton.setOnClickListener {
            findNavController().navigateUp(null)
        }
    }

    private fun validateInput(): Boolean {
        var isValid = true

        // validate the first name
        val firstName = firstNameEditText.text.toString()
        if (!isValidName(firstName)) {
            isValid = false
            firstNameTextLayout.error = "Please enter your first name."
        }

        // validate the last name
        val lastName = lastNameEditText.text.toString()
        if (!isValidName(lastName)) {
            isValid = false
            lastNameTextLayout.error = "Please enter your last name."
        }

        return isValid
    }

    private fun isValidName(name: String): Boolean {
        return !StringValidationUtils.isBlankOrEmpty(name)
    }
}
