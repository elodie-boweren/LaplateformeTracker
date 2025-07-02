package org.example.laplateforme.controller;

import org.example.laplateforme.dao.StudentDAO;
import org.example.laplateforme.model.Student;

import java.util.List;

public class StudentController {

    private final StudentDAO studentDAO = new StudentDAO();

    public void addStudent(Student student) {
        studentDAO.addStudent(student);
    }

    public void updateStudent(Student student) {
        studentDAO.updateStudent(student);
    }

    public void deleteStudent(int id) {
        studentDAO.deleteStudent(id);
    }

    public Student getStudentById(int id) {
        return studentDAO.getStudentById(id);
    }

    public List<Student> getAllStudents() {
        return studentDAO.getAllStudents();
    }
}
