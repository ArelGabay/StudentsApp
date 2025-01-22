package com.example.studentsapp.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.CheckBox
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.studentsapp.R
import com.example.studentsapp.repository.StudentRepository
import com.example.studentsapp.viewmodel.StudentViewModel

// Activity to display details of a selected student
class StudentDetailsActivity : AppCompatActivity() {

    private val viewModel: StudentViewModel = StudentViewModel()
    private var studentId: String? = null

    // Activity Result Launcher for EditStudentActivity
    private val editStudentLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == RESULT_OK) {
            val isDeleted = result.data?.getBooleanExtra("deleted", false) ?: false

            if (isDeleted) {
                // If the student was deleted, close this activity
                finish()
            } else {
                // If not deleted, check for an updated student ID
                studentId = result.data?.getStringExtra("newStudentId") ?: studentId
                loadStudentDetails() // Refresh details with the new ID
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_student_details)

        studentId = intent.getStringExtra("studentId")

        loadStudentDetails()

        val editButton = findViewById<Button>(R.id.activity_student_details_edit_button)
        editButton.setOnClickListener {
            val intent = Intent(this, EditStudentActivity::class.java)
            intent.putExtra("studentId", studentId)
            editStudentLauncher.launch(intent)
        }

        val backButton = findViewById<Button>(R.id.activity_student_details_back_button)
        backButton.setOnClickListener {
            finish()
        }

    }

    override fun onResume() {
        super.onResume()
        loadStudentDetails() // Reload student details
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == EDIT_STUDENT_REQUEST_CODE && resultCode == RESULT_OK) {
            // Update studentId with the new ID
            studentId = data?.getStringExtra("newStudentId") ?: studentId
        }
    }

    private fun loadStudentDetails() {
        Log.d("StudentDetailsActivity", "Loading details for ID: $studentId")
        val student = viewModel.getAllStudents().find { it.id == studentId }
        if (student != null) {
            Log.d("StudentDetailsActivity", "Found student: ${student.id}")
            findViewById<TextView>(R.id.activity_student_details_id_text_view).text = "Student ID: ${student.id}"
            findViewById<TextView>(R.id.activity_student_details_name_text_view).text = "Name: ${student.name}"
            findViewById<TextView>(R.id.activity_student_details_phone_text_view).text = "Phone: ${student.phone}"
            findViewById<TextView>(R.id.activity_student_details_address_text_view).text = "Address: ${student.address}"

            val checkBox = findViewById<CheckBox>(R.id.activity_student_details_check_box_button)
            checkBox.isChecked = student.checked
            checkBox.text = if (student.checked) "Checked" else "Unchecked"
        }
    }

    companion object {
        private const val EDIT_STUDENT_REQUEST_CODE = 1
    }
}
