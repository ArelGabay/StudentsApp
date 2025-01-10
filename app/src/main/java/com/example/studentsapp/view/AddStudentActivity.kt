package com.example.studentsapp.view

import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.studentsapp.R
import com.example.studentsapp.model.Student
import com.example.studentsapp.repository.StudentRepository

class AddStudentActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_add_student)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Find input fields and buttons
        val idInput = findViewById<EditText>(R.id.activity_add_student_id_edit_text)
        val nameInput = findViewById<EditText>(R.id.activity_add_student_name_edit_text)
        val phoneInput = findViewById<EditText>(R.id.activity_add_student_phone_edit_text)
        val addressInput = findViewById<EditText>(R.id.activity_add_student_address_edit_text)
        val checkBoxInput = findViewById<CheckBox>(R.id.activity_add_student_check_box_button)
        val addStudentButton = findViewById<Button>(R.id.activity_add_student_save_button)
        val cancelButton = findViewById<Button>(R.id.activity_add_student_cancel_button)

        checkBoxInput.setOnCheckedChangeListener { _, isChecked ->
            checkBoxInput.text = if (isChecked) "Checked" else "Unchecked"
        }

        addStudentButton.setOnClickListener {
            // Get input values
            val id = idInput.text.toString()
            val name = nameInput.text.toString()
            val phone = phoneInput.text.toString()
            val address = addressInput.text.toString()
            val isChecked = checkBoxInput.isChecked // true if checked, false otherwise

            // Create and add new student
            val newStudent = Student(id, name, phone, address, isChecked)

            // Add student to database
            StudentRepository.addStudent(newStudent)

            // Finish activity
            finish()
        }

        cancelButton.setOnClickListener {
            // Finish activity
            finish()
        }

    }
}