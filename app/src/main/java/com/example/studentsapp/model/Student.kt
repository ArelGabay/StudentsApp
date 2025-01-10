package com.example.studentsapp.model

// Data class for student
data class Student(
    var id: String, // Unique identifier
    var name: String, // Student name
    var phone: String, // Phone number
    var address: String, // Address
    var checked: Boolean = false // Checked status
)