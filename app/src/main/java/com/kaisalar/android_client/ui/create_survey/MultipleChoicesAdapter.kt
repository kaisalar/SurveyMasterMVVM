package com.kaisalar.android_client.ui.create_survey

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.appcompat.widget.AppCompatImageButton
import androidx.recyclerview.widget.RecyclerView
import com.kaisalar.android_client.R

class MultipleChoicesAdapter(
    val context: Context,
    private val choiceType: String,
    private val choices: List<String>,
    private val choiceOnTextChangeListener: (String, Int) -> Unit,
    private val deleteOnClickListener: (Int) -> Unit
) : RecyclerView.Adapter<MultipleChoicesAdapter.Holder>() {

    companion object {
        const val CHOICE_RADIO_GROUP = "CHOICE_RADIO_GROUP"
        const val CHOICE_CHECK_BOX = "CHOICE_CHECK_BOX"
        const val CHOICE_DROP_DOWN = "CHOICE_DROP_DOWN"
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val itemView = when (choiceType) {
            CHOICE_RADIO_GROUP -> LayoutInflater
                .from(context)
                .inflate(R.layout.radio_group_choice_list_item, parent, false)
            CHOICE_CHECK_BOX -> LayoutInflater
                .from(context)
                .inflate(R.layout.check_box_choice_list_item, parent, false)
            CHOICE_DROP_DOWN -> LayoutInflater
                .from(context)
                .inflate(R.layout.drop_down_choice_list_item, parent, false)
            else -> null
        }

        return Holder(itemView!!)
    }

    override fun getItemCount(): Int {
        return choices.count()
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bindChoice(choices[position], position)
    }

    inner class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val choiceEditText = itemView.findViewById<EditText>(R.id.choiceEditText)
        private val deleteButton = itemView.findViewById<AppCompatImageButton>(R.id.deleteChoiceButton)

        fun bindChoice(choice: String, position: Int) {
            choiceEditText.setText(choice)

            choiceEditText.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(s: Editable?) {}
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    choiceOnTextChangeListener(s.toString(), position)
                }
            })

            deleteButton.setOnClickListener {
                deleteOnClickListener(position)
            }
        }
    }
}