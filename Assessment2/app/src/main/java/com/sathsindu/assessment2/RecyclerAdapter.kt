package com.sathsindu.assessment2

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class RecyclerAdapter: RecyclerView.Adapter<RecyclerAdapter.ViewHolder>() {

    private var subject =
        arrayOf("AWS", "Python Programming", "Big Data")
    private var lecturer = arrayOf("Mike Andrew", "John Bravo", "Max Bush")
    private var date = arrayOf("Mon,Wed,Thu", "Mon/Tue/Wed", "Mon/Wed/Thu")
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerAdapter.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.cardlayout, parent, false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return subject.size
    }

    override fun onBindViewHolder(holder: RecyclerAdapter.ViewHolder, position: Int) {
        holder.subjectName.text = subject[position]
        holder.lecturer.text = lecturer[position]
        holder.schedule.text = date[position]
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var subjectName: TextView
        var lecturer: TextView
        var schedule: TextView

        init {
            subjectName = itemView.findViewById(R.id.subject)
            lecturer = itemView.findViewById(R.id.lecturer)
            schedule = itemView.findViewById(R.id.schedule)
        }


    }

}