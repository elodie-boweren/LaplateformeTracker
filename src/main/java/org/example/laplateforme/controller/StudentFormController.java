package org.example.laplateforme.controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.example.laplateforme.dao.StudentDAO;
import org.example.laplateforme.model.Student;

public class StudentFormController {

    @FXML private TextField firstNameField;
    @FXML private TextField lastNameField;
    @FXML private TextField ageField;
    @FXML private TextField gradeField;
    @FXML private Label errorLabel;

    private DashboardController dashboardController;
    private Student currentStudent;
    private final StudentDAO studentDAO = new StudentDAO();

    @FXML
    private void initialize() {
        // Initialisation si nécessaire
    }

    public void setDashboardController(DashboardController controller) {
        this.dashboardController = controller;
    }

    public void setStudentData(Student student) {
        this.currentStudent = student;
        if (student != null) {
            firstNameField.setText(student.getFirstName());
            lastNameField.setText(student.getLastName());
            ageField.setText(String.valueOf(student.getAge()));
            gradeField.setText(String.valueOf(student.getGrade()));
        }
    }

    @FXML
    private void onSave() {
        if (!validateInputs()) {
            return;
        }

        try {
            String firstName = firstNameField.getText().trim();
            String lastName = lastNameField.getText().trim();
            int age = Integer.parseInt(ageField.getText().trim());
            int grade = Integer.parseInt(gradeField.getText().trim());

            Student student = new Student(firstName, lastName, age, grade);

            if (currentStudent != null) {
                student.setId(currentStudent.getId());
                if (studentDAO.updateStudent(student)) {
                    dashboardController.refreshStudentList();
                    closeWindow();
                } else {
                    showError("Failed to update student");
                }
            } else {
                if (studentDAO.addStudent(student)) {
                    dashboardController.refreshStudentList();
                    closeWindow();
                } else {
                    showError("Failed to add student");
                }
            }
        } catch (NumberFormatException e) {
            showError("Please enter valid numbers for age and grade");
        } catch (Exception e) {
            showError("An unexpected error occurred");
            e.printStackTrace();
        }
    }

    @FXML
    private void onCancel() {
        closeWindow();
    }

    private boolean validateInputs() {
        hideError();

        if (firstNameField.getText().trim().isEmpty() ||
                lastNameField.getText().trim().isEmpty() ||
                ageField.getText().trim().isEmpty() ||
                gradeField.getText().trim().isEmpty()) {

            showError("All fields are required");
            return false;
        }

        try {
            Integer.parseInt(ageField.getText().trim());
            Integer.parseInt(gradeField.getText().trim());
        } catch (NumberFormatException e) {
            showError("Age and grade must be valid numbers");
            return false;
        }

        return true;
    }

    private void showError(String message) {
        errorLabel.setText(message);
        errorLabel.setVisible(true);
    }

    private void hideError() {
        errorLabel.setVisible(false);
    }

    private void closeWindow() {
        firstNameField.getScene().getWindow().hide();
    }
}
