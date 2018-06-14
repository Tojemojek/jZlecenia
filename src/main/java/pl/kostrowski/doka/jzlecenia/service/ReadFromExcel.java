package pl.kostrowski.doka.jzlecenia.service;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import pl.kostrowski.doka.jzlecenia.configuration.MyPaths;
import pl.kostrowski.doka.jzlecenia.converters.LineFromFileConverter;
import pl.kostrowski.doka.jzlecenia.dao.DaneDao;
import pl.kostrowski.doka.jzlecenia.dto.LineFromFileDto;
import pl.kostrowski.doka.jzlecenia.mappings.MapColumns;
import pl.kostrowski.doka.jzlecenia.model.LineFromFile;

import java.io.File;
import java.io.FileInputStream;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;


@Service
public class ReadFromExcel {

    DaneDao daneDao;

    @Autowired
    public ReadFromExcel(DaneDao daneDao) {
        this.daneDao = daneDao;
    }

    public void read(File file) throws Exception {

        MapColumns mapColumns = new MapColumns();
        Map<String, String> invertedMappings = mapColumns.getInvertedMappings();

        Workbook workbook = new XSSFWorkbook(file);
        Sheet sheet = workbook.getSheetAt(0);

        Row titleRow = sheet.getRow(0);

        Map<String, Integer> columnNumbers = findColumnNumbers(invertedMappings, titleRow);

        List<LineFromFileDto> dataFromFile = getDataFromFile(columnNumbers, sheet);

        LineFromFileConverter lineFromFileConverter = new LineFromFileConverter();

        List<LineFromFile> convert = lineFromFileConverter.convert(dataFromFile);

        daneDao.saveAll(convert);
    }

    private Map<String, Integer> findColumnNumbers(Map<String, String> invertedMappings, Row titleRow) {

        Map<String, Integer> columnNumbers = new HashMap<>();
        DataFormatter dataFormatter = new DataFormatter();

        for (Cell cell : titleRow) {
            String cellValue = dataFormatter.formatCellValue(cell);
            if (invertedMappings.containsKey(cellValue)) {
                columnNumbers.put(invertedMappings.get(cellValue), cell.getColumnIndex());
            }
        }
        return columnNumbers;
    }

    private List<LineFromFileDto> getDataFromFile(Map<String, Integer> columnNumbers, Sheet sheet) {

        int lastRowNum = sheet.getLastRowNum();
        List<LineFromFileDto> results = new LinkedList<>();
        DataFormatter dataFormatter = new DataFormatter();

        for (int i = 1; i <= lastRowNum; i++) {

            LineFromFileDto lineFromFileDto = new LineFromFileDto();
            Row currentRow = sheet.getRow(i);

            String salesId1 = dataFormatter.formatCellValue(currentRow.getCell(columnNumbers.get("salesId")));
            lineFromFileDto.setSalesId(salesId1);

            String itemId1 = dataFormatter.formatCellValue(currentRow.getCell(columnNumbers.get("itemId")));
            lineFromFileDto.setItemId(itemId1);

            String salesPrice1 = dataFormatter.formatCellValue(currentRow.getCell(columnNumbers.get("salesPrice")));
            lineFromFileDto.setSalesPrice(salesPrice1);

            String weight1 = dataFormatter.formatCellValue(currentRow.getCell(columnNumbers.get("weight")));
            lineFromFileDto.setWeight(weight1);

            String salesStatus1 = dataFormatter.formatCellValue(currentRow.getCell(columnNumbers.get("salesStatus")));
            lineFromFileDto.setSalesStatus(salesStatus1);

            String orderedQty1 = dataFormatter.formatCellValue(currentRow.getCell(columnNumbers.get("orderedQty")));
            lineFromFileDto.setOrderedQty(orderedQty1);

            results.add(lineFromFileDto);
        }

        return results;
    }


}
