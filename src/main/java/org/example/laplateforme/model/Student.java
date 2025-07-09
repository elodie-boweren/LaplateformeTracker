package org.example.laplateforme.model;

import javafx.beans.property.*;

public class Student {
    private int id;
    private StringProperty firstName;
    private StringProperty lastName;
    private IntegerProperty age;
    private StringProperty grade;

    // Constructeur par défaut
    public Student() {
        this.firstName = new SimpleStringProperty();
        this.lastName = new SimpleStringProperty();
        this.age = new SimpleIntegerProperty();
        this.grade = new SimpleStringProperty();
    }

    // Constructeur avec paramètres
    public Student(int id, String firstName, String lastName, int age, String grade) {
        this.id = id;
        this.firstName = new SimpleStringProperty(firstName);
        this.lastName = new SimpleStringProperty(lastName);
        this.age = new SimpleIntegerProperty(age);
        this.grade = new SimpleStringProperty(grade);
    }

    // Getters et setters pour l'ID
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    // Getters et setters pour firstName
    public String getFirstName() {
        return firstName.get();
    }

    public void setFirstName(String firstName) {
        this.firstName.set(firstName);
    }

    public StringProperty firstNameProperty() {
        return firstName;
    }

    // Getters et setters pour lastName
    public String getLastName() {
        return lastName.get();
    }

    public void setLastName(String lastName) {
        this.lastName.set(lastName);
    }

    public StringProperty lastNameProperty() {
        return lastName;
    }

    // Getters et setters pour age
    public int getAge() {
        return age.get();
    }

    public void setAge(int age) {
        this.age.set(age);
    }

    public IntegerProperty ageProperty() {
        return age;
    }

    // Getters et setters pour grade
    public String getGrade() {
        return grade.get();
    }

    public void setGrade(String grade) {
        this.grade.set(grade);
    }

    public StringProperty gradeProperty() {
        return grade;
    }

    @Override
    public String toString() {
        return "Student {" +
                "id=" + id +
                ", firstName='" + getFirstName() + '\'' +
                ", lastName='" + getLastName() + '\'' +
                ", age=" + getAge() +
                ", grade='" + getGrade() + '\'' +
                '}';
    }
}