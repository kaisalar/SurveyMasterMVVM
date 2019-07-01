package com.kaisalar.android_client.ui.main.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.kaisalar.android_client.R
import com.kaisalar.android_client.data.webservice.AuthService
import com.kaisalar.android_client.ui.main.MainViewModel
import kotlinx.android.synthetic.main.home_fragment.*

class HomeFragment : Fragment() {

    companion object {
        fun newInstance() = HomeFragment()
    }

    private lateinit var viewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.home_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        activity?.run {
            viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        }

        val fn = AuthService.getInstance(context!!).getUserPublicInfo().firstName
        val ln = AuthService.getInstance(context!!).getUserPublicInfo().lastName
        hiTextView.text = "$fn $ln"
        // TODO: Use the ViewModel
    }

}
