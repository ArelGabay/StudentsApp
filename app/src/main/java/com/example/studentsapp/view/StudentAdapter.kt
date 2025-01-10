package com.example.studentsapp.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.studentsapp.R
import com.example.studentsapp.model.Student

class StudentAdapter(

    private var students: List<Student>, // List of students
    private val onItemClick: (Student) -> Unit, // Click listener for each student
    private val onCheckClick: (Student) -> Unit // Click listener for the checkbox
) : RecyclerView.Adapter<StudentAdapter.StudentViewHolder>() {

    inner class StudentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val nameTextView: TextView = itemView.findViewById(R.id.item_student_name) // Student name
        private val idTextView: TextView = itemView.findViewById(R.id.item_student_id) // Student ID
        private val checkBox: CheckBox = itemView.findViewById(R.id.item_student_check_box) // Checkbox

        // Bind the student data to the view
        fun bind(student: Student) {
            nameTextView.text = student.name
            idTextView.text = student.id
            checkBox.isChecked = student.checked

            // Set the click listeners
            itemView.setOnClickListener { onItemClick(student) }

            // Set the checkbox listener
            checkBox.setOnCheckedChangeListener { _, isChecked ->
                student.checked = isChecked
                onCheckClick(student) // Call the listener
            }
        }
    }

    // Create the view holder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudentViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_student, parent, false)
        return StudentViewHolder(view)
    }

    override fun onBindViewHolder(holder: StudentViewHolder, position: Int) {
        holder.bind(students[position])
    }

    override fun getItemCount(): Int = students.size

    // Update the student list and notify the RecyclerView
    fun updateData(newStudents: List<Student>) {
        students = newStudents
        notifyDataSetChanged()
    }

}