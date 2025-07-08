package org.example.laplateforme.controller;

import org.example.laplateforme.dao.StudentDAO;
import org.example.laplateforme.model.Student;
import javafx.collections.FXCollections;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;

import java.util.List;

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

    @FXML private Button addStudentButton;
    @FXML private Button editStudentButton;

    @FXML
    public void initialize() {
        // Indiquer au TableColumn quel attribut afficher en utilisant les noms des getters
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        surnameColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        ageColumn.setCellValueFactory(new PropertyValueFactory<>("age"));
        gradeColumn.setCellValueFactory(new PropertyValueFactory<>("grade"));

        // Charger les étudiants depuis la base
        StudentDAO dao = new StudentDAO();
        List<Student> students = StudentDAO.getAllStudents();

        studentTable.setItems(FXCollections.observableArrayList(students));
    }

    @FXML
    public void onAddStudent() {
        System.out.println("Add student clicked");
    }

    @FXML
    public void onEditStudent() {
        Student selected = studentTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            System.out.println("Editing student: " + selected);
        }
    }
}