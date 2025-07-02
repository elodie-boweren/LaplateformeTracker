package org.example.laplateforme;

import org.example.laplateforme.dao.Database;
import org.example.laplateforme.dao.UserDAO;

public class Main {
    public static void main(String[] args) {
        // Create object Database
        Database database = new Database();

        // Connect to database
        if (!database.connectDb()) {
            return;
        }
    // Create or check tables Student and User
        database.createStudentTable();
        database.createUserTable();


        // Close connection to database
        database.closeConnection();
        }
    }
