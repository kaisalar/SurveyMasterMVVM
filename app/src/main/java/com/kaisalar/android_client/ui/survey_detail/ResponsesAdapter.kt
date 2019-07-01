package com.kaisalar.android_client.ui.survey_detail

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.kaisalar.android_client.R
import com.kaisalar.android_client.data.model.ResponseForGetting
import com.kaisalar.android_client.util.DateUtils

class ResponsesAdapter(
    val context: Context,
    private val responses: List<ResponseForGetting>,
    private val responseOnClickListener: (ResponseForGetting) -> Unit
) : RecyclerView.Adapter<ResponsesAdapter.Holder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val itemView = LayoutInflater.from(context).inflate(R.layout.response_list_item, parent, false)
        return Holder(itemView)
    }

    override fun getItemCount(): Int {
        return responses.count()
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bindResponse(responses[position], position)
    }

    inner class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val responseNumber = itemView.findViewById<TextView>(R.id.responseTextView)
        private val responseDate = itemView.findViewById<TextView>(R.id.responseDateTextView)

        fun bindResponse(response: ResponseForGetting, position: Int) {
            itemView.setOnClickListener {
                responseOnClickListener(response)
            }
            responseNumber.text = "Response #${position + 1}"
            responseDate.text = DateUtils.convertTimeMillis(response.date)
        }
    }
}