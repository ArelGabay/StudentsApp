package com.example.studentsapp.view

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.studentsapp.R
import com.example.studentsapp.model.Student
import com.example.studentsapp.viewmodel.StudentViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: StudentViewModel // ViewModel for managing data
    private lateinit var adapter: StudentAdapter // Adapter for the RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main_activity_recycler_view)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Initialize the ViewModel
        viewModel = StudentViewModel()

        val recyclerView = findViewById<RecyclerView>(R.id.main_activity_recycler_view)
        adapter = StudentAdapter(
            students = viewModel.getAllStudents(),
            onItemClick = { student -> openStudentDetails(student) },
            onCheckClick = { student -> viewModel.updateStudent(student) }
        )

        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        addMockData()
    }

    // Navigate to the Student Details screen
    private fun openStudentDetails(student: Student) {
        val intent = Intent(this, StudentDetailsActivity::class.java)
        intent.putExtra("studentId", student.id) // Pass the student ID
        startActivity(intent)
    }

    private fun addMockData() {
        viewModel.addStudent(
            Student(
                id = "1",
                name = "John Doe",
                phone = "123-456-7890",
                address = "123 Main Street, Springfield"
            )
        )
        viewModel.addStudent(
            Student(
                id = "2",
                name = "Jane Smith",
                phone = "987-654-3210",
                address = "456 Elm Street, Springfield"
            )
        )
        adapter.updateData(viewModel.getAllStudents()) // Refresh RecyclerView with new data
    }

}