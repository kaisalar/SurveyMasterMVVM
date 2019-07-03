package com.kaisalar.android_client.ui.survey_detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat.getColor
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import com.kaisalar.android_client.R
import com.kaisalar.android_client.data.model.UserForSurvey
import com.kaisalar.android_client.viewmodel.SurveyDetailsViewModel
import kotlinx.android.synthetic.main.fragment_users.*


class UsersFragment : Fragment() {

    companion object {
        fun newInstance() = UsersFragment()
    }

    private lateinit var viewModel: SurveyDetailsViewModel
    private lateinit var globalAdapter: UsersAdapter
    private val users = mutableListOf<UserForSurvey>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_users, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        activity?.run {
            viewModel = ViewModelProviders.of(this).get(SurveyDetailsViewModel::class.java)
        }

        addUserToSurveyFloatingActionButton.setOnClickListener {
            addUserForSurvey()
        }
        val adapter = UsersAdapter(
            context = context!!,
            users = users,
            deleteOnClickListener = {
                deleteUser(it)
            }
        )

        globalAdapter = adapter

        val layoutManager = LinearLayoutManager(context)

        usersRecyclerView.apply {
           this.adapter = adapter
           this.layoutManager = layoutManager
        }

        usersRefreshLayout.setColorSchemeColors(
            getColor(context!!, R.color.color2),
            getColor(context!!, R.color.color3),
            getColor(context!!, R.color.color4)
        )
        usersRefreshLayout.isRefreshing = true
        updateUsers()

        viewModel.users.observe(this, Observer {
            this.users.clear()
            this.users.addAll(it)
            globalAdapter.notifyDataSetChanged()
        })
    }

    private fun updateUsers() {
        viewModel.getSurveyUsers(
            onSuccess = {
                usersRefreshLayout?.isRefreshing = false
                users.clear()
                users.addAll(it)
                globalAdapter.notifyDataSetChanged()
            },
            onFailure = {
                usersRefreshLayout?.isRefreshing = false
                Snackbar.make(usersRecyclerView,"Error Connection :(", Snackbar.LENGTH_SHORT).setAnimationMode(Snackbar.ANIMATION_MODE_SLIDE)
                    .show()
            }
        )
    }

    private fun deleteUser(user: UserForSurvey) {
        MaterialAlertDialogBuilder(context)
            .setTitle("Delete User")
            .setMessage("Are you sure to Delete ${user.userEmail} ?")
            .setPositiveButton("Sure, Delete!") { dialog, _ ->
                dialog.dismiss()
                viewModel.deleteUserFromSurvey(
                    userId = user.userId,
                    onSuccess = {
                        Toast.makeText(context, "$${user.userEmail} deleted!", Toast.LENGTH_SHORT).show()
                    },
                    onFailure = {
                        Toast.makeText(context, "Can't delete user, try again later!", Toast.LENGTH_SHORT).show()
                    }
                )
            }
            .setNegativeButton("No", null)
            .show()
    }

    private fun addUserForSurvey() {
        val action = UsersFragmentDirections.actionAddUser()
        findNavController().navigate(action)
    }

    override fun onStop() {
        super.onStop()
        usersRefreshLayout?.isRefreshing = false
        viewModel.cancelGetSurveyUsersRequest()
    }

}
