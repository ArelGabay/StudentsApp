package com.example.studentsapp.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.studentsapp.R
import com.example.studentsapp.viewmodel.StudentViewModel

class EditStudentActivity : AppCompatActivity() {

    val viewModel: StudentViewModel = StudentViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_edit_student)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val idInput = findViewById<EditText>(R.id.activity_edit_student_id_edit_text)
        val nameInput = findViewById<EditText>(R.id.activity_edit_student_name_edit_text)
        val phoneInput = findViewById<EditText>(R.id.activity_edit_student_phone_edit_text)
        val addressInput = findViewById<EditText>(R.id.activity_edit_student_address_edit_text)
        val checkBoxInput = findViewById<CheckBox>(R.id.activity_edit_student_check_box_button)
        val saveButton = findViewById<Button>(R.id.activity_edit_student_edit_button)
        val cancelButton = findViewById<Button>(R.id.activity_edit_student_cancel_button)
        val deleteButton = findViewById<Button>(R.id.activity_edit_student_delete_button)

        val studentId = intent.getStringExtra("studentId")

        val student = viewModel.getAllStudents().find { it.id == studentId }
        if (student != null) {
            idInput.setText(student.id)
            nameInput.setText(student.name)
            phoneInput.setText(student.phone)
            addressInput.setText(student.address)
            checkBoxInput.isChecked = student.checked
            checkBoxInput.text = if (student.checked) "Checked" else "Unchecked"
        }

        checkBoxInput.setOnCheckedChangeListener { _, isChecked ->
            checkBoxInput.text = if (isChecked) "Checked" else "Unchecked"
        }

        // Handle Save button click
        saveButton.setOnClickListener {
            student?.let {
                val newId = idInput.text.toString().trim() // Get the updated ID
                val oldId = it.id // Keep track of the old ID

                // Update student fields
                it.name = nameInput.text.toString().trim()
                it.phone = phoneInput.text.toString().trim()
                it.address = addressInput.text.toString().trim()
                it.checked = checkBoxInput.isChecked


                if (oldId != newId) {
                    // If the ID has changed, update the repository
                    viewModel.deleteStudent(it) // Remove the old student record
                    it.id = newId
                    viewModel.addStudent(it) // Add the student with the new ID
                } else {
                    // Update student with the same ID
                    viewModel.updateStudent(it)
                }

                viewModel.sortStudentsByName()


                val resultIntent = Intent()
                resultIntent.putExtra("newStudentId", newId)
                setResult(RESULT_OK, resultIntent)
            }
            finish() // Close the activity
        }

        // Handle Cancel button click
        cancelButton.setOnClickListener {
            finish() // Close the activity without saving
        }

        // Handle Delete button click
        deleteButton.setOnClickListener {
            if (student != null) {
                viewModel.deleteStudent(student) // Remove the student from the repository

                // Set the result to notify the caller that the student was deleted
                val resultIntent = Intent()
                resultIntent.putExtra("deleted", true) // Add a flag for deletion
                setResult(RESULT_OK, resultIntent)
            }
            finish() // Close the Edit Screen
        }

    }
}
