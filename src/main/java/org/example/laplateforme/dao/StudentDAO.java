package org.example.laplateforme.dao;
import org.example.laplateforme.model.Student;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StudentDAO {
    private Database database;

    public StudentDAO() {
        this.database = database;
    }

    //Ajouter un étudiant
    public void addStudent(Student student) {
        String sql = "INSERT INTO Student (firstName, lastName, age, grade) VALUES (?, ?, ?, ?)";

        try (Connection conn = database.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, student.getFirstName());
            stmt.setString(2, student.getLastName());
            stmt.setInt(3, student.getAge());
            stmt.setString(4, student.getGrade());

            stmt.executeUpdate();
            System.out.println("Student added successfully");
        } catch (SQLException e) {
            System.err.println("Error");
            e.printStackTrace();
        }
    }

    // Mettre à jour un étudiant
    public void updateStudent(Student student) {
        String sql = "UPDATE Student SET firstName = ?, lastName = ?, age = ?, grade = ? WHERE id = ?";

        try (Connection conn = database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, student.getFirstName());
            stmt.setString(2, student.getLastName());
            stmt.setInt(3, student.getAge());
            stmt.setString(4, student.getGrade());
            stmt.setInt(5, student.getId());

            int rowsUpdated = stmt.executeUpdate();  // ✅ cette variable existe
            if (rowsUpdated > 0) {
                System.out.println("✅ Étudiant mis à jour.");
            } else {
                System.out.println("⚠️ Aucun étudiant trouvé avec cet ID.");
            }

        } catch (SQLException e) {
            System.err.println("❌ Erreur lors de la mise à jour.");
            e.printStackTrace();
        }
    }


    // Rechercher un étudiant par ID
    public Student getStudentById(int id) {
        String sql = "SELECT * FROM Student WHERE id = ?";
        try (Connection conn = database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Student(
                        rs.getInt("id"),
                        rs.getString("firstName"),
                        rs.getString("lastName"),
                        rs.getInt("age"),
                        rs.getString("grade")
                );
            }

        } catch (SQLException e) {
            System.err.println("❌ Erreur lors de la récupération.");
            e.printStackTrace();
        }

        return null;
    }

    // Afficher tous les étudiants
    public List<Student> getAllStudents() {
        List<Student> students = new ArrayList<>();
        String sql = "SELECT * FROM Student ORDER BY id";

        try (Connection conn = database.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Student s = new Student(
                        rs.getInt("id"),
                        rs.getString("firstName"),
                        rs.getString("lastName"),
                        rs.getInt("age"),
                        rs.getString("grade")
                );
                students.add(s);
            }

        } catch (SQLException e) {
            System.err.println("Erreur lors de l'affichage.");
            e.printStackTrace();
        }

        return students;
    }

    // Supprimer un étudiant
    public void deleteStudent(int id) {
        String sql = "DELETE FROM Student WHERE id = ?";

        try (Connection conn = database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);

            int rowsDeleted = stmt.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("✅ Étudiant supprimé.");
            } else {
                System.out.println("⚠️ Aucun étudiant trouvé avec cet ID.");
            }

        } catch (SQLException e) {
            System.err.println("❌ Erreur lors de la suppression.");
            e.printStackTrace();
        }
    }

}
