package com.example.studentsapp.viewmodel

import com.example.studentsapp.model.Student
import com.example.studentsapp.repository.StudentRepository

// ViewModel to manage the student data for the UI
class StudentViewModel{

    // Get the list of all students directly from the repository
    fun getAllStudents(): List<Student> = StudentRepository.getAllStudents()

    // Add a new student to the repository
    fun addStudent(student: Student) {
        StudentRepository.addStudent(student)
    }

    // Update an existing student in the repository
    fun updateStudent(student: Student) {
        StudentRepository.updateStudent(student)
    }

    // Delete a student from the repository by ID
    fun deleteStudent(student: Student) {
        StudentRepository.deleteStudent(student)
    }

}