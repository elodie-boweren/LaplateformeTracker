package org.example.laplateforme.service;

import org.example.laplateforme.dao.Database;

import java.sql.Connection;
import java.util.Timer;
import java.util.TimerTask;

public class BackupService {
    private final Database database;
    private Timer timer;

    public BackupService() {
        this.database = Database.getInstance();
    }

    public void startAutomaticBackup(int hours) {
        timer = new Timer();
        timer.schedule(new BackupTask(), 0, hours * 60 * 60 * 1000);
    }

    public void stopAutomaticBackup() {
        if (timer != null) {
            timer.cancel();
        }
    }

    private class BackupTask extends TimerTask {
        @Override
        public void run() {
            performBackup();
        }
    }

    public void performBackup() {
        String backupFile = "backup_" + System.currentTimeMillis() + ".sql";

        try (Connection conn = database.getConnection()) {
            // Utiliser pg_dump pour une sauvegarde complète
            ProcessBuilder processBuilder = new ProcessBuilder(
                    "pg_dump",
                    "-h", "dpg-d1ierler433s73agd6tg-a.frankfurt-postgres.render.com",
                    "-U", "plateformetracker_user",
                    "-d", "plateformetracker",
                    "-f", backupFile
            );

            processBuilder.environment().put("PGPASSWORD", "5zhAoSpXbD9mTQT3MrggX3pd4YCGiT7Z");

            Process process = processBuilder.start();
            int exitCode = process.waitFor();

            if (exitCode == 0) {
                System.out.println("✅ Sauvegarde automatique réussie: " + backupFile);
            } else {
                System.err.println("❌ Échec de la sauvegarde automatique");
            }
        } catch (Exception e) {
            System.err.println("❌ Erreur lors de la sauvegarde automatique: " + e.getMessage());
        }
    }
}
