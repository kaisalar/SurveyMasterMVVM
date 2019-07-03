package com.kaisalar.android_client.ui.survey_detail

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.kaisalar.android_client.R
import com.kaisalar.android_client.data.model.UserForSurvey

class UsersAdapter(
    val context: Context,
    val users: List<UserForSurvey>,
    val deleteOnClickListener: (UserForSurvey) -> Unit
) : RecyclerView.Adapter<UsersAdapter.Holder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val itemView = LayoutInflater.from(context).inflate(R.layout.user_list_item, parent)
        return Holder(itemView)
    }

    override fun getItemCount(): Int {
        return users.count()
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bindUser(users[position])
    }

    inner class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val userEmail = itemView.findViewById<TextView>(R.id.user_survey_email)
        private val role = itemView.findViewById<TextView>(R.id.user_survey_role)
        private val deleteRoleButton = itemView.findViewById<Button>(R.id.user_survey_delete_role_button)

        fun bindUser(user : UserForSurvey) {
            userEmail.text = user.userEmail
            role.text = user.role
            deleteRoleButton.setOnClickListener { deleteOnClickListener(user) }
        }
    }
}