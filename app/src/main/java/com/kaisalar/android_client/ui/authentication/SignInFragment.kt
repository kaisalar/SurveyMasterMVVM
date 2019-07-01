package com.kaisalar.android_client.ui.authentication

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.kaisalar.android_client.R
import com.kaisalar.android_client.ui.main.MainActivity
import com.kaisalar.android_client.util.StringValidationUtils
import com.kaisalar.android_client.viewmodel.AuthViewModel
import kotlinx.android.synthetic.main.sign_in_fragment.*

class SignInFragment : Fragment() {

    companion object {
        fun newInstance() = SignInFragment()
    }

    private lateinit var viewModel: AuthViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.sign_in_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(AuthViewModel::class.java)

        emailEditText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {}

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (isValidEmail(s.toString())) emailTextLayout.error = null
            }
        })

        passwordEditText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {}

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (isValidPassword(s.toString())) passwordTextLayout.error = null
            }
        })

        signInButton.setOnClickListener {
            if (validateInput()) signIn(it)
        }
    }

    private fun signIn(view: View) {
        val email = emailEditText.text.toString()
        val password = passwordEditText.text.toString()

        if (StringValidationUtils.isBlankOrEmpty(email) ||
            StringValidationUtils.isBlankOrEmpty(password) ||
            !StringValidationUtils.isEmail(email)) return

        val loadingAction =
            SignInFragmentDirections.loadingAction()
        findNavController().navigate(loadingAction)

        viewModel.signIn(
            email = emailEditText.text.toString(),
            password = passwordEditText.text.toString(),
            onSuccess = {
                startActivity(Intent(context, MainActivity::class.java))
                activity?.apply {
                    finish()
                }
            },
            onFailure = {
                findNavController().navigateUp()
                Snackbar.make(view, "Something went wrong!", Snackbar.LENGTH_LONG).show()
            }
        )
    }

    private fun validateInput(): Boolean {
        val email = emailEditText.text.toString()
        if (!isValidEmail(email)) {
            emailTextLayout.error = "Please enter a valid email."
            return false
        }

        val password = passwordEditText.text.toString()
        if (!isValidPassword(password)) {
            passwordTextLayout.error = "Please enter a valid password."
            return false
        }

        return true
    }

    private fun isValidEmail(email: String): Boolean {
        return !StringValidationUtils.isBlankOrEmpty(email) && StringValidationUtils.isEmail(email)
    }

    private fun isValidPassword(password: String): Boolean {
        return password.length >= 8
    }

    override fun onStop() {
        super.onStop()
        viewModel.cancelSignIn()
    }
}
