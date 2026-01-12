package com.example.studentmanagementapp


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class StudentAdapter(private val students: List<Student>) :
    RecyclerView.Adapter<StudentAdapter.StudentViewHolder>() {

    class StudentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvName: TextView = itemView.findViewById(R.id.tvName)
        val tvCourse: TextView = itemView.findViewById(R.id.tvCourse)
        val tvRegNumber: TextView = itemView.findViewById(R.id.tvRegNumber)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudentViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_student, parent, false)
        return StudentViewHolder(view)
    }

    override fun onBindViewHolder(holder: StudentViewHolder, position: Int) {
        val student = students[position]
        holder.tvName.text = "Name: ${student.name}"
        holder.tvCourse.text = "Course: ${student.course}"
        holder.tvRegNumber.text = "Reg No: ${student.regNumber}"
    }

    override fun getItemCount(): Int = students.size
}
