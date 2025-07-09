package org.example.laplateforme.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.example.laplateforme.dao.StudentDAO;
import org.example.laplateforme.model.Student;

public class StudentFormController {

    @FXML
    private TextField firstNameField;

    @FXML
    private TextField lastNameField;

    @FXML
    private TextField ageField;

    @FXML
    private TextField gradeField;

    @FXML
    private Label errorLabel;

    @FXML
    private Button saveButton;

    @FXML
    private Button cancelButton;

    private Student currentStudent;
    private StudentDAO studentDAO;
    private DashboardController dashboardController;

    @FXML
    public void initialize() {
        studentDAO = new StudentDAO();
    }

    public void setStudent(Student student) {
        this.currentStudent = student;
        if (student != null) {
            // Mode édition
            firstNameField.setText(student.getFirstName());
            lastNameField.setText(student.getLastName());
            ageField.setText(String.valueOf(student.getAge()));
            gradeField.setText(student.getGrade());
        }
        // Sinon c'est le mode création, les champs restent vides
    }

    public void setDashboardController(DashboardController dashboardController) {
        this.dashboardController = dashboardController;
    }

    @FXML
    private void onSave() {
        if (validateInputs()) {
            String firstName = firstNameField.getText().trim();
            String lastName = lastNameField.getText().trim();
            int age = Integer.parseInt(ageField.getText().trim());
            String grade = gradeField.getText().trim();

            if (currentStudent == null) {
                // Mode création
                Student newStudent = new Student(0, firstName, lastName, age, grade);
                studentDAO.addStudent(newStudent);
                System.out.println("Étudiant ajouté avec succès");
            } else {
                // Mode édition
                currentStudent.setFirstName(firstName);
                currentStudent.setLastName(lastName);
                currentStudent.setAge(age);
                currentStudent.setGrade(grade);
                studentDAO.updateStudent(currentStudent);
                System.out.println("Étudiant modifié avec succès");
            }

            // Notifier le dashboard pour rafraîchir la table
            if (dashboardController != null) {
                dashboardController.onStudentSaved();
            }

            closeWindow();
        }
    }

    @FXML
    private void onCancel() {
        closeWindow();
    }

    private boolean validateInputs() {
        // Réinitialiser le message d'erreur
        errorLabel.setVisible(false);

        // Vérifier que tous les champs sont remplis
        if (firstNameField.getText().trim().isEmpty() ||
                lastNameField.getText().trim().isEmpty() ||
                ageField.getText().trim().isEmpty() ||
                gradeField.getText().trim().isEmpty()) {

            showError("Tous les champs sont obligatoires.");
            return false;
        }

        // Vérifier que l'âge est un nombre valide
        try {
            int age = Integer.parseInt(ageField.getText().trim());
            if (age < 0 || age > 150) {
                showError("L'âge doit être compris entre 0 et 150.");
                return false;
            }
        } catch (NumberFormatException e) {
            showError("L'âge doit être un nombre valide.");
            return false;
        }

        return true;
    }

    private void showError(String message) {
        errorLabel.setText(message);
        errorLabel.setVisible(true);
    }

    private void closeWindow() {
        Stage stage = (Stage) saveButton.getScene().getWindow();
        stage.close();
    }
}