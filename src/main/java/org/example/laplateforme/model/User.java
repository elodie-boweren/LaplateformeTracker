package org.example.laplateforme.model;

public class User {
    private int id;
    private String username;
    private String email;
    private String password;
    private String role;

    // Constructor to create user before insertion in table User (no ID)
    public User(String username, String email, String password, String role) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    // Constructor
    public User(int id, String username, String email, String password, String role) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public int getId() {
        return id;
    }

    public String getUsername() {return username;}

    public String getEmail() {return email;}

    public String getPassword() {return password;}
}
