package pl.kostrowski.doka.jzlecenia.service;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import pl.kostrowski.doka.jzlecenia.configuration.MyPaths;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.nio.file.FileSystems;
import java.nio.file.Path;

public class ReadFromExcel {

    private final String truePath = MyPaths.PATH_TO_SAVE_FILES.getPath();
    private final Path rootLocation = FileSystems.getDefault().getPath(truePath);

    public void read() throws Exception {

        FileInputStream file = new FileInputStream(new File(truePath+"/2018-06-13.xlsx"));
        Workbook workbook = new XSSFWorkbook(file);
        Sheet sheet = workbook.getSheetAt(0);
        DataFormatter dataFormatter = new DataFormatter();


        for (int i = 0; i < 100; i++){
            Row row = sheet.getRow(i);
                for (int j = 0; j <100; j++){
                    Cell cell = row.getCell(j);
                    System.out.print(dataFormatter.formatCellValue(cell));
                }
            System.out.println();
        }



    }








}
