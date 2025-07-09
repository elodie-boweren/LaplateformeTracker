package org.example.laplateforme.controller;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextField;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.event.ActionEvent;

import org.example.laplateforme.dao.StudentDAO;
import org.example.laplateforme.model.Student;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public class DashboardController {

    @FXML
    private TableView<Student> studentTable;

    @FXML
    private TableColumn<Student, String> nameColumn;

    @FXML
    private TableColumn<Student, String> surnameColumn;

    @FXML
    private TableColumn<Student, Integer> ageColumn;

    @FXML
    private TableColumn<Student, String> gradeColumn;

    @FXML
    private TextField filterFirstName;

    @FXML
    private TextField filterLastName;

    @FXML
    private TextField filterAge;

    @FXML
    private TextField filterGrade;

    @FXML
    private Button addStudentButton;

    @FXML
    private Button editStudentButton;

    private StudentDAO studentDAO;
    private ObservableList<Student> studentList;
    private FilteredList<Student> filteredStudents;

    @FXML
    public void initialize() {
        studentDAO = new StudentDAO();
        studentList = FXCollections.observableArrayList();
        filteredStudents = new FilteredList<>(studentList);

        // Configuration des colonnes
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        surnameColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        ageColumn.setCellValueFactory(new PropertyValueFactory<>("age"));
        gradeColumn.setCellValueFactory(new PropertyValueFactory<>("grade"));

        // Liaison de la liste filtrée à la table
        studentTable.setItems(filteredStudents);

        // Configuration des filtres
        setupFilters();

        // Charger les données
        refreshTable();
    }

    private void setupFilters() {
        // Écouter les changements dans les champs de filtre
        filterFirstName.textProperty().addListener((observable, oldValue, newValue) -> applyFilters());
        filterLastName.textProperty().addListener((observable, oldValue, newValue) -> applyFilters());
        filterAge.textProperty().addListener((observable, oldValue, newValue) -> applyFilters());
        filterGrade.textProperty().addListener((observable, oldValue, newValue) -> applyFilters());
    }

    private void applyFilters() {
        filteredStudents.setPredicate(student -> {
            if (student == null) return false;

            // Filtre par prénom
            String firstNameFilter = filterFirstName.getText();
            if (firstNameFilter != null && !firstNameFilter.isEmpty()) {
                if (!student.getFirstName().toLowerCase().contains(firstNameFilter.toLowerCase())) {
                    return false;
                }
            }

            // Filtre par nom
            String lastNameFilter = filterLastName.getText();
            if (lastNameFilter != null && !lastNameFilter.isEmpty()) {
                if (!student.getLastName().toLowerCase().contains(lastNameFilter.toLowerCase())) {
                    return false;
                }
            }

            // Filtre par âge
            String ageFilter = filterAge.getText();
            if (ageFilter != null && !ageFilter.isEmpty()) {
                try {
                    int age = Integer.parseInt(ageFilter);
                    if (student.getAge() != age) {
                        return false;
                    }
                } catch (NumberFormatException e) {
                    // Ignorer si ce n'est pas un nombre valide
                }
            }

            // Filtre par grade
            String gradeFilter = filterGrade.getText();
            if (gradeFilter != null && !gradeFilter.isEmpty()) {
                if (!student.getGrade().toLowerCase().contains(gradeFilter.toLowerCase())) {
                    return false;
                }
            }

            return true;
        });
    }

    @FXML
    private void clearFilters(ActionEvent event) {
        filterFirstName.clear();
        filterLastName.clear();
        filterAge.clear();
        filterGrade.clear();
    }

    @FXML
    private void refreshTable() {
        List<Student> students = studentDAO.getAllStudents();
        studentList.clear();
        studentList.addAll(students);
    }

    @FXML
    private void onAddStudent() {
        showStudentDialog(null);
    }

    @FXML
    private void onEditStudent() {
        Student selectedStudent = studentTable.getSelectionModel().getSelectedItem();
        if (selectedStudent != null) {
            showStudentDialog(selectedStudent);
        } else {
            showAlert("Aucune sélection", "Veuillez sélectionner un étudiant à modifier.");
        }
    }

    @FXML
    private void onDeleteStudent() {
        Student selectedStudent = studentTable.getSelectionModel().getSelectedItem();
        if (selectedStudent != null) {
            Optional<ButtonType> result = showConfirmationDialog(
                    "Confirmer la suppression",
                    "Êtes-vous sûr de vouloir supprimer cet étudiant ?",
                    selectedStudent.getFirstName() + " " + selectedStudent.getLastName()
            );

            if (result.isPresent() && result.get() == ButtonType.OK) {
                studentDAO.deleteStudent(selectedStudent.getId());
                refreshTable();
            }
        } else {
            showAlert("Aucune sélection", "Veuillez sélectionner un étudiant à supprimer.");
        }
    }

    private void showStudentDialog(Student student) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/laplateforme/view/student-form.fxml"));
            Parent root = loader.load();

            StudentFormController controller = loader.getController();
            controller.setStudent(student);
            controller.setDashboardController(this);

            Stage stage = new Stage();
            stage.setTitle(student == null ? "Ajouter un étudiant" : "Modifier un étudiant");
            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();

        } catch (IOException e) {
            System.err.println("Erreur lors de l'ouverture du formulaire: " + e.getMessage());
            showAlert("Erreur", "Impossible d'ouvrir le formulaire d'étudiant.");
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private Optional<ButtonType> showConfirmationDialog(String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        return alert.showAndWait();
    }

    // Méthode appelée par le formulaire d'étudiant pour rafraîchir la table
    public void onStudentSaved() {
        refreshTable();
    }
}