package org.example.laplateforme.utils;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.example.laplateforme.model.Student;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class ExcelExporter {

    public static void exportStudentsToExcel(List<Student> students, File file) {
        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Étudiants");

            // En-tête
            Row header = sheet.createRow(0);
            header.createCell(0).setCellValue("ID");
            header.createCell(1).setCellValue("Prénom");
            header.createCell(2).setCellValue("Nom");
            header.createCell(3).setCellValue("Âge");
            header.createCell(4).setCellValue("Note");

            int rowNum = 1;
            for (Student s : students) {
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(s.getId());
                row.createCell(1).setCellValue(s.getFirstName());
                row.createCell(2).setCellValue(s.getLastName());
                row.createCell(3).setCellValue(s.getAge());
                row.createCell(4).setCellValue(s.getGrade());
            }

            for (int i = 0; i < 5; i++) {
                sheet.autoSizeColumn(i);
            }

            try (FileOutputStream out = new FileOutputStream(file)) {
                workbook.write(out);
            }

            System.out.println("✅ Export terminé : " + file.getAbsolutePath());

        } catch (IOException e) {
            System.err.println("❌ Erreur export Excel");
            e.printStackTrace();
        }
    }
}
