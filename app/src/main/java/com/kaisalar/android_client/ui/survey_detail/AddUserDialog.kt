package com.kaisalar.android_client.ui.survey_detail

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputLayout
import com.kaisalar.android_client.R
import com.kaisalar.android_client.data.model.ROLE_ADMIN
import com.kaisalar.android_client.data.model.ROLE_COLLABORATOR
import com.kaisalar.android_client.data.model.UserForSurveyForCreation
import com.kaisalar.android_client.util.StringValidationUtils
import com.kaisalar.android_client.viewmodel.SurveyDetailsViewModel

class AddUserDialog : BottomSheetDialogFragment() {
    private lateinit var viewModel: SurveyDetailsViewModel

    private lateinit var emailEditText: EditText
    private lateinit var emailTextLayout: TextInputLayout

    private lateinit var rolesRadioGroup: RadioGroup
    private lateinit var roleAdminRadioButton: RadioButton
    private lateinit var roleCollaboratorRadioButton: RadioButton

    private lateinit var positiveButton: MaterialButton
    private lateinit var negativeButton: MaterialButton

    private val user = UserForSurveyForCreation("", "")

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.dialog_add_user, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        activity?.run {
            viewModel = ViewModelProviders.of(this).get(SurveyDetailsViewModel::class.java)
        }

        emailEditText = view.findViewById(R.id.userEmailEditText)
        emailTextLayout = view.findViewById(R.id.userEmailTextLayout)
        rolesRadioGroup = view.findViewById(R.id.rolesRadioGroup)
        roleAdminRadioButton = view.findViewById(R.id.roleAdminRadioButton)
        roleCollaboratorRadioButton = view.findViewById(R.id.roleCollaboratorRadioButton)
        positiveButton = view.findViewById(R.id.positiveButton)
        negativeButton = view.findViewById(R.id.negativeButton)

        emailEditText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {}

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val email = s.toString()
                if (!StringValidationUtils.isBlankOrEmpty(email) && StringValidationUtils.isEmail(email))
                    emailTextLayout.error = null
                user.email = email
            }
        })

        rolesRadioGroup.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.roleAdminRadioButton -> user.role = ROLE_ADMIN
                R.id.roleCollaboratorRadioButton -> user.role = ROLE_COLLABORATOR
            }
        }

        positiveButton.setOnClickListener {
            var isValid = true
            // Validation on Email
            if (StringValidationUtils.isBlankOrEmpty(user.email)) {
                emailTextLayout.error = "Please enter an email address"
                isValid = false
            }
            if (!StringValidationUtils.isEmail(user.email)) {
                emailTextLayout.error = "Please enter a valid email address"
                isValid = false
            }
            // Validate on role
            if (user.role.isEmpty()) {
                Toast.makeText(context, "Please select a role", Toast.LENGTH_SHORT).show()
                isValid = false
            }
            if(isValid) {
                val action = AddUserDialogDirections.loadingAction()
                findNavController().navigate(action)

                viewModel.addUserForSurvey(
                    user = user,
                    onSuccess = {
                        findNavController().navigateUp()
                        Toast.makeText(context, "User Added Successfully", Toast.LENGTH_SHORT).show()
                        this.dismiss()
                    },
                    onFailure = {
                        findNavController().navigateUp()
                        Toast.makeText(context, "Cannot Add This User", Toast.LENGTH_SHORT).show()
                    }
                )
            }
        }

        negativeButton.setOnClickListener {
            this.dismiss()
        }
    }
}