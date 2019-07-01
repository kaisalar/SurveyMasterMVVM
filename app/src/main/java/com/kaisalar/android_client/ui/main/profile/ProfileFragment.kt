package com.kaisalar.android_client.ui.main.profile

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.kaisalar.android_client.R
import com.kaisalar.android_client.data.webservice.AuthService
import com.kaisalar.android_client.ui.authentication.AuthActivity
import kotlinx.android.synthetic.main.profile_fragment.*

class ProfileFragment : Fragment() {

    companion object {
        fun newInstance() = ProfileFragment()
    }

    private lateinit var viewModel: ProfileViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.profile_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(ProfileViewModel::class.java)

        val user = AuthService.getInstance(context!!).getUserPublicInfo()

        userNameTextView.text = "${user.firstName} ${user.lastName}"
        userEmailTextView.text = user.email

        signOutButton.setOnClickListener {
            viewModel.signOut()
            startActivity(Intent(context, AuthActivity::class.java))
            activity?.apply {
                finish()
            }
        }
    }
}
