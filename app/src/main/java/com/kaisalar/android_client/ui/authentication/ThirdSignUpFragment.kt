package com.kaisalar.android_client.ui.authentication

import android.content.Intent
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
import com.google.android.material.snackbar.Snackbar
import com.kaisalar.android_client.R
import com.kaisalar.android_client.ui.main.MainActivity
import com.kaisalar.android_client.viewmodel.AuthViewModel
import kotlinx.android.synthetic.main.third_sing_up_fragment.*

class ThirdSignUpFragment : Fragment() {

    companion object {
        fun newInstance() = ThirdSignUpFragment()
    }

    private lateinit var viewModel: AuthViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.third_sing_up_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        activity?.run {
            viewModel = ViewModelProviders.of(this).get(AuthViewModel::class.java)
        }

        viewModel.getCreationUser().observe(this, Observer {
            passwordEditText.setText(it.password)
        })

        passwordEditText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {}

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (isValidPassword(s.toString())) passwordTextLayout.error = null
                viewModel.setUserPassword(s.toString())
            }
        })

        passwordConfirmEditText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {}

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s.toString().contentEquals(passwordEditText.text.toString()))
                    passwordConfirmTextLayout.error = null
            }
        })

        finishButton.setOnClickListener {
            if (validateInput()) createAccount(it)
        }

        prevButton.setOnClickListener {
            findNavController().navigateUp(null)
        }
    }

    private fun createAccount(view: View) {
        val loadingAction =
            ThirdSignUpFragmentDirections.loadingAction()
        findNavController().navigate(loadingAction)

        viewModel.createAccount(
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
        val password = passwordEditText.text.toString()
        if (!isValidPassword(password)) {
            passwordTextLayout.error = "Minimum length of password should be 8."
            return false
        }

        val passwordConfirm = passwordConfirmEditText.text.toString()
        if (!passwordConfirm.contentEquals(password)) {
            passwordConfirmTextLayout.error = "Passwords do not match."
            return false
        }

        return true
    }

    private fun isValidPassword(password: String): Boolean {
        return password.length >= 8
    }

    override fun onStop() {
        super.onStop()
        viewModel.cancelCreateAccount()
    }
}
