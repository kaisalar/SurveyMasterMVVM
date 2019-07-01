package com.kaisalar.android_client.ui.main.surveys

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.kaisalar.android_client.R
import com.kaisalar.android_client.data.db.entity.SurveyEntity
import com.kaisalar.android_client.util.DateUtils

class SurveysAdapter(
    val context: Context,
    private val surveys: List<SurveyEntity>,
    private val shareOnClickListener: (SurveyEntity) -> Unit,
    private val deleteOnClickListener: (SurveyEntity) -> Unit,
    private val surveyOnClickListener: (SurveyEntity) -> Unit
) : RecyclerView.Adapter<SurveysAdapter.Holder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(context).inflate(R.layout.survey_list_item, parent, false)
        return Holder(view)
    }

    override fun getItemCount(): Int {
        return surveys.count()
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bindSurvey(surveys[position], position)
    }

    inner class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val sideBar = itemView.findViewById<View>(R.id.sideBarView)
        private val title = itemView.findViewById<TextView>(R.id.surveyTitleTextView)
        private val date = itemView.findViewById<TextView>(R.id.surveyDateTextView)
        private val shareButton = itemView.findViewById<Button>(R.id.shareButton)
        private val deleteButton = itemView.findViewById<Button>(R.id.deleteButton)

        fun bindSurvey(survey: SurveyEntity, position: Int) {
            // set the color
            when (position % 3) {
                0 -> sideBar.background.setTint(context.resources.getColor(R.color.color4))
                1 -> sideBar.background.setTint(context.resources.getColor(R.color.color3))
                2 -> sideBar.background.setTint(context.resources.getColor(R.color.color2))
            }

            // set the title
            title.text = survey.title

            // set the date
            date.text = DateUtils.convertTimeMillis(survey.date)

            // set actions listeners
            shareButton.setOnClickListener { shareOnClickListener(survey) }

            deleteButton.setOnClickListener { deleteOnClickListener(survey) }

            itemView.setOnClickListener { surveyOnClickListener(survey) }
        }
    }
}