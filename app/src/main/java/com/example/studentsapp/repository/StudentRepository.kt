package com.example.studentsapp.repository

import com.example.studentsapp.model.Student

object StudentRepository {
    // List of students
    private val students = mutableListOf<Student>()

    // Get all students
    fun getAllStudents(): List<Student> = students

    // Add a student
    fun addStudent(student: Student) {
        students.add(student)
    }

    // Update student information
    fun updateStudent(student: Student) {
        val index = students.indexOfFirst { it.id == student.id } // Find the index of the student
        if (index != -1) { // If the student exists
            students[index] = student // Update the student
        }
    }

    // Delete a student
    fun deleteStudent(student: Student) {
        students.remove(student)
    }
}