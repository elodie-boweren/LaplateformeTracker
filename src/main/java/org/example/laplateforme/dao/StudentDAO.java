package org.example.laplateforme.dao;

import org.example.laplateforme.model.Student;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StudentDAO {
    private final Database database;

    public StudentDAO() {
        this.database = Database.getInstance();
        if (!this.database.isConnected()) {
            this.database.connectDb();
        }
    }

    public boolean addStudent(Student student) {
        String sql = "INSERT INTO Student (firstName, lastName, age, grade) VALUES (?, ?, ?, ?)";

        try (Connection conn = database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, student.getFirstName());
            stmt.setString(2, student.getLastName());
            stmt.setInt(3, student.getAge());
            stmt.setInt(4, student.getGrade());

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.err.println("Error adding student: " + e.getMessage());
            return false;
        }
    }

    public boolean updateStudent(Student student) {
        String sql = "UPDATE Student SET firstName = ?, lastName = ?, age = ?, grade = ? WHERE id = ?";

        try (Connection conn = database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, student.getFirstName());
            stmt.setString(2, student.getLastName());
            stmt.setInt(3, student.getAge());
            stmt.setInt(4, student.getGrade());
            stmt.setInt(5, student.getId());

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.err.println("Error updating student: " + e.getMessage());
            return false;
        }
    }

    public boolean deleteStudent(int id) {
        String sql = "DELETE FROM Student WHERE id = ?";

        try (Connection conn = database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.err.println("Error deleting student: " + e.getMessage());
            return false;
        }
    }

    public Student getStudentById(int id) {
        String sql = "SELECT * FROM Student WHERE id = ?";
        Student student = null;

        try (Connection conn = database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    student = new Student(
                            rs.getInt("id"),
                            rs.getString("firstName"),
                            rs.getString("lastName"),
                            rs.getInt("age"),
                            rs.getInt("grade")
                    );
                }
            }
        } catch (SQLException e) {
            System.err.println("Error getting student by ID: " + e.getMessage());
        }
        return student;
    }

    public List<Student> getAllStudents() {
        List<Student> students = new ArrayList<>();
        String sql = "SELECT * FROM Student";

        try (Connection conn = database.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Student student = new Student(
                        rs.getInt("id"),
                        rs.getString("firstName"),
                        rs.getString("lastName"),
                        rs.getInt("age"),
                        rs.getInt("grade")
                );
                students.add(student);
            }
        } catch (SQLException e) {
            System.err.println("Error getting all students: " + e.getMessage());
        }
        return students;
    }

    public List<Student> searchStudents(String firstName, String lastName, Integer age, Integer grade) {
        List<Student> students = new ArrayList<>();
        StringBuilder sql = new StringBuilder("SELECT * FROM Student WHERE 1=1");

        if (firstName != null && !firstName.isEmpty()) {
            sql.append(" AND firstName ILIKE ?");
        }
        if (lastName != null && !lastName.isEmpty()) {
            sql.append(" AND lastName ILIKE ?");
        }
        if (age != null) {
            sql.append(" AND age = ?");
        }
        if (grade != null) {
            sql.append(" AND grade = ?");
        }

        try (Connection conn = database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql.toString())) {

            int paramIndex = 1;
            if (firstName != null && !firstName.isEmpty()) {
                stmt.setString(paramIndex++, "%" + firstName + "%");
            }
            if (lastName != null && !lastName.isEmpty()) {
                stmt.setString(paramIndex++, "%" + lastName + "%");
            }
            if (age != null) {
                stmt.setInt(paramIndex++, age);
            }
            if (grade != null) {
                stmt.setInt(paramIndex, grade);
            }

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Student student = new Student(
                            rs.getInt("id"),
                            rs.getString("firstName"),
                            rs.getString("lastName"),
                            rs.getInt("age"),
                            rs.getInt("grade")
                    );
                    students.add(student);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error searching students: " + e.getMessage());
        }
        return students;
    }
}
