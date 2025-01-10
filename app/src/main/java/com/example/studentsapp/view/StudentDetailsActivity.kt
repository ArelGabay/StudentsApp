package com.example.studentsapp.view

import android.os.Bundle
import android.widget.CheckBox
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.studentsapp.R
import com.example.studentsapp.repository.StudentRepository

// Activity to display details of a selected student
class StudentDetailsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_student_details)

        val studentId = intent.getStringExtra("studentId")

        // Get the student ID passed from MainActivity
        val student = StudentRepository.getAllStudents().find { it.id == studentId }

        // TODO: Use the student ID to fetch and display the student's details
        // For now, you can log or display the studentId to ensure it's working
        if (student != null) {
            findViewById<TextView>(R.id.activity_student_details_id_text_view).text = "Student ID: ${student.id}"
            findViewById<TextView>(R.id.activity_student_details_name_text_view).text = "Name: ${student.name}"
            findViewById<TextView>(R.id.activity_student_details_phone_text_view).text = "Phone: ${student.phone}"
            findViewById<TextView>(R.id.activity_student_details_address_text_view).text = "Address: ${student.address}"

            val checkBox = findViewById<CheckBox>(R.id.activity_student_details_check_box_button)
            checkBox.isChecked = student.checked
            checkBox.text = if (student.checked) "Checked" else "Unchecked"
        }
    }
}