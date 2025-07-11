package org.example.laplateforme.dao;

import org.example.laplateforme.model.Student;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StudentDAO {

    // Ajouter un étudiant
    public void addStudent(Student student) {
        String sql = "INSERT INTO Student (firstName, lastName, age, grade) VALUES (?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, student.getFirstName());
            stmt.setString(2, student.getLastName());
            stmt.setInt(3, student.getAge());
            stmt.setInt(4, Integer.parseInt(student.getGrade()));

            stmt.executeUpdate();
            System.out.println("✅ Étudiant ajouté.");
        } catch (SQLException e) {
            System.err.println("❌ Erreur ajout étudiant : " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Modifier un étudiant
    public void updateStudent(Student student) {
        String sql = "UPDATE Student SET firstName = ?, lastName = ?, age = ?, grade = ? WHERE id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, student.getFirstName());
            stmt.setString(2, student.getLastName());
            stmt.setInt(3, student.getAge());
            stmt.setInt(4, Integer.parseInt(student.getGrade()));
            stmt.setInt(5, student.getId());

            int rows = stmt.executeUpdate();
            if (rows > 0) {
                System.out.println("✅ Étudiant mis à jour.");
            } else {
                System.out.println("⚠️ Aucun étudiant trouvé avec cet ID.");
            }

        } catch (SQLException e) {
            System.err.println("❌ Erreur mise à jour : " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Supprimer un étudiant
    public void deleteStudent(int id) {
        String sql = "DELETE FROM Student WHERE id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);

            int rows = stmt.executeUpdate();
            if (rows > 0) {
                System.out.println("✅ Étudiant supprimé.");
            } else {
                System.out.println("⚠️ Étudiant introuvable.");
            }

        } catch (SQLException e) {
            System.err.println("❌ Erreur suppression : " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Récupérer un étudiant par ID
    public Student getStudentById(int id) {
        String sql = "SELECT * FROM Student WHERE id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Student(
                        rs.getInt("id"),
                        rs.getString("firstName"),
                        rs.getString("lastName"),
                        rs.getInt("age"),
                        String.valueOf(rs.getInt("grade"))
                );
            }

        } catch (SQLException e) {
            System.err.println("❌ Erreur récupération ID : " + e.getMessage());
            e.printStackTrace();
        }

        return null;
    }

    // Récupérer tous les étudiants
    public List<Student> getAllStudents() {
        List<Student> students = new ArrayList<>();
        String sql = "SELECT * FROM Student ORDER BY id";

        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                students.add(new Student(
                        rs.getInt("id"),
                        rs.getString("firstName"),
                        rs.getString("lastName"),
                        rs.getInt("age"),
                        String.valueOf(rs.getInt("grade"))
                ));
            }

        } catch (SQLException e) {
            System.err.println("❌ Erreur récupération liste : " + e.getMessage());
            e.printStackTrace();
        }

        return students;
    }
}
