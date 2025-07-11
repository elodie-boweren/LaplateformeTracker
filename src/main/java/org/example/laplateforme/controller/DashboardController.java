package org.example.laplateforme.controller;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.event.ActionEvent;
import javafx.scene.control.TextField;
import org.example.laplateforme.dao.StudentDAO;
import org.example.laplateforme.model.Student;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public class DashboardController {

    @FXML private TableView<Student> studentTable;
    @FXML private TableColumn<Student, String> nameColumn;
    @FXML private TableColumn<Student, String> surnameColumn;
    @FXML private TableColumn<Student, Integer> ageColumn;
    @FXML private TableColumn<Student, String> gradeColumn;
    @FXML private TextField filterFirstName;
    @FXML private TextField filterLastName;
    @FXML private TextField filterAge;
    @FXML private TextField filterGrade;
    @FXML private Button logoutButton;

    private final StudentDAO studentDAO = new StudentDAO();
    private ObservableList<Student> studentList = FXCollections.observableArrayList();

    @FXML
    private void handleLogout(ActionEvent event) {
        try {
            // Charger la vue de connexion
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/laplateforme/view/login.fxml"));
            Parent root = loader.load();

            // Obtenir la scène actuelle et la remplacer
            Stage stage = (Stage) logoutButton.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void initialize() {
        setupTableColumns();
        loadStudentData();
    }

    @FXML
    private void clearFilters(ActionEvent event) {
        filterFirstName.clear();
        filterLastName.clear();
        filterAge.clear();
        filterGrade.clear();

    private void setupTableColumns() {
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        surnameColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        ageColumn.setCellValueFactory(new PropertyValueFactory<>("age"));
        gradeColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(String.valueOf(cellData.getValue().getGrade())));
    }

    private void loadStudentData() {
        studentList.clear();
        List<Student> students = studentDAO.getAllStudents();
        studentList.addAll(students);
        studentTable.setItems(studentList);
    }

    @FXML
    private void onAddStudent() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/student-form.fxml"));
            Parent root = loader.load();

            StudentFormController controller = loader.getController();
            controller.setDashboardController(this);

            Stage stage = new Stage();
            stage.setTitle("Add New Student");
            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Error", "Could not open student form");
        }
    }

    @FXML
    private void onEditStudent() {
        Student selectedStudent = studentTable.getSelectionModel().getSelectedItem();
        if (selectedStudent == null) {
            showAlert("No Selection", "Please select a student to edit");
            return;
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/student-form.fxml"));
            Parent root = loader.load();

            StudentFormController controller = loader.getController();
            controller.setDashboardController(this);
            controller.setStudentData(selectedStudent);

            Stage stage = new Stage();
            stage.setTitle("Edit Student");
            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Error", "Could not open student form");
        }
    }

    @FXML
    private void onDeleteStudent() {
        Student selectedStudent = studentTable.getSelectionModel().getSelectedItem();
        if (selectedStudent == null) {
            showAlert("No Selection", "Please select a student to delete");
            return;
        }

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete Student");
        alert.setHeaderText("Delete Student: " + selectedStudent.getFirstName() + " " + selectedStudent.getLastName());
        alert.setContentText("Are you sure you want to delete this student?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            if (studentDAO.deleteStudent(selectedStudent.getId())) {
                loadStudentData();
            } else {
                showAlert("Error", "Failed to delete student");
            }
        }
    }

    @FXML
    private void refreshTable() {
        loadStudentData();
    }

    @FXML
    private void onLogout() {
        try {
            // Fermer la fenêtre actuelle
            Stage stage = (Stage) logoutButton.getScene().getWindow();
            stage.close();

            // Ouvrir la fenêtre de connexion
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/auth.fxml"));
            Parent root = loader.load();
            Stage loginStage = new Stage();
            loginStage.setTitle("Login");
            loginStage.setScene(new Scene(root));
            loginStage.show();
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Error", "Could not open login window");
        }
    }

    public void refreshStudentList() {
        loadStudentData();
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
