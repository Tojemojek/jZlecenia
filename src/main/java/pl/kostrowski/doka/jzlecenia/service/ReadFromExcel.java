package pl.kostrowski.doka.jzlecenia.service;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.kostrowski.doka.jzlecenia.converters.LineFromFileConverter;
import pl.kostrowski.doka.jzlecenia.dao.DaneDao;
import pl.kostrowski.doka.jzlecenia.dao.InputFileDao;
import pl.kostrowski.doka.jzlecenia.dto.LineFromFileDto;
import pl.kostrowski.doka.jzlecenia.mappings.MyMappings;
import pl.kostrowski.doka.jzlecenia.model.LineFromFile;
import pl.kostrowski.doka.jzlecenia.model.InputFile;

import javax.transaction.Transactional;
import java.io.File;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;


@Service
public class ReadFromExcel {

    private final Logger LOG = LoggerFactory.getLogger(ReadFromExcel.class);
    private final DaneDao daneDao;
    private final InputFileDao inputFileDao;
    private final DataFormatter dataFormatter = new DataFormatter();
    private final LineFromFileConverter lineFromFileConverter;


    @Autowired
    public ReadFromExcel(DaneDao daneDao, InputFileDao inputFileDao, LineFromFileConverter lineFromFileConverter) {
        this.daneDao = daneDao;
        this.lineFromFileConverter = lineFromFileConverter;
        this.inputFileDao = inputFileDao;
    }

    @Transactional
    public void read(File file) throws Exception {

        String fileName = file.getName();

        InputFile inputFile = new InputFile();
        inputFile.setFileName(fileName);
        inputFile.setInputTimeStamp(LocalDateTime.now());

        try {
            inputFile.setFileDate(LocalDate.parse(StringUtils.substring(fileName, 0, 10)));
        } catch (Exception e){
            LOG.warn("Nie udało się rozpoznać daty pliku " + fileName);
            inputFile.setFileDate(null);
        }


        MyMappings mapColumns = new MyMappings();
        Map<String, String> invertedMappings = mapColumns.getInvertedColumnMappings();

        Workbook workbook = new XSSFWorkbook(file);
        Sheet sheet = workbook.getSheetAt(0);

        Row titleRow = sheet.getRow(0);

        Map<String, Integer> columnNumbers = findColumnNumbers(invertedMappings, titleRow);

        List<LineFromFileDto> dataFromFile = getDataFromFile(columnNumbers, sheet);

        List<LineFromFile> convert = lineFromFileConverter.convert(dataFromFile, inputFile);

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

        for (int i = 1; i <= lastRowNum; i++) {

            LineFromFileDto lineFromFileDto = new LineFromFileDto();
            Row currentRow = sheet.getRow(i);

            if (currentRow != null) {
                //
                String nrZleceniaTmp = parseStringFromCell(columnNumbers.get("nrZlecenia"), currentRow);
                lineFromFileDto.setNrZlecenia(nrZleceniaTmp);

                String nrArtykuluTmp = parseStringFromCell(columnNumbers.get("nrArtykulu"), currentRow);
                lineFromFileDto.setNrArtykulu(nrArtykuluTmp);

                String nazwaArtykuluTmp = parseStringFromCell(columnNumbers.get("nazwaArtykulu"), currentRow);
                lineFromFileDto.setNazwaArtykulu(nazwaArtykuluTmp);

                Double iloscZamowionaTmp = parseDoubleFromCell(columnNumbers.get("iloscZamowiona"), currentRow);
                lineFromFileDto.setIloscZamowiona(iloscZamowionaTmp);

                Double iloscPozostalaDoWydaniaTmp = parseDoubleFromCell(columnNumbers.get("iloscPozostalaDoWydania"), currentRow);
                lineFromFileDto.setIloscPozostalaDoWydania(iloscPozostalaDoWydaniaTmp);

                LocalDate dataDostawyTmp = parseLocalDateFromCell(columnNumbers.get("dataDostawy"), currentRow);
                lineFromFileDto.setDataDostawy(dataDostawyTmp);

                String jednostkaSalesUnitTmp = parseStringFromCell(columnNumbers.get("jednostkaSalesUnit"), currentRow);
                lineFromFileDto.setJednostkaSalesUnit(jednostkaSalesUnitTmp);

                String nrProjektuTmp = parseStringFromCell(columnNumbers.get("nrProjektu"), currentRow);
                lineFromFileDto.setNrProjektu(nrProjektuTmp);

                String nrBudowyTmp = parseStringFromCell(columnNumbers.get("nrBudowy"), currentRow);
                lineFromFileDto.setNrBudowy(nrBudowyTmp);

                String nrKlientaTmp = parseStringFromCell(columnNumbers.get("nrKlienta"), currentRow);
                lineFromFileDto.setNrKlienta(nrKlientaTmp);

                String nrWierszaWZleceniuTmp = parseStringFromCell(columnNumbers.get("nrWierszaWZleceniu"), currentRow);
                lineFromFileDto.setNrWierszaWZleceniu(nrWierszaWZleceniuTmp);

                String typBiznesuTmp = parseStringFromCell(columnNumbers.get("typBiznesu"), currentRow);
                lineFromFileDto.setTypBiznesu(typBiznesuTmp);

                Integer statusWierszaTmp = parseIntegerFromCell(columnNumbers.get("statusWiersza"), currentRow);
                lineFromFileDto.setStatusWiersza(statusWierszaTmp);

                Double masaJednostkowaTmp = parseDoubleFromCell(columnNumbers.get("masaJednostkowa"), currentRow);
                lineFromFileDto.setMasaJednostkowa(masaJednostkowaTmp);

                Double cenaSprzedazyZaJednostkeTmp = parseDoubleFromCell(columnNumbers.get("cenaSprzedazyZaJednostke"), currentRow);
                lineFromFileDto.setCenaSprzedazyZaJednostke(cenaSprzedazyZaJednostkeTmp);

                String jednostkaSalesPriceUnitTmp = parseStringFromCell(columnNumbers.get("jednostkaSalesPriceUnit"), currentRow);
                lineFromFileDto.setJednostkaSalesPriceUnit(jednostkaSalesPriceUnitTmp);

                Double tonazTmp = parseDoubleFromCell(columnNumbers.get("tonaz"), currentRow);
                lineFromFileDto.setTonaz(tonazTmp);

                Double pozostalyTonazDoWydaniaTmp = parseDoubleFromCell(columnNumbers.get("pozostalyTonazDoWydania"), currentRow);
                lineFromFileDto.setPozostalyTonazDoWydania(pozostalyTonazDoWydaniaTmp);

                LocalDateTime dataModyfikacjiTmp = parseLocalDateTimeFromCell(columnNumbers.get("dataModyfikacji"), currentRow);
                lineFromFileDto.setDataModyfikacji(dataModyfikacjiTmp);

                LocalDateTime dataUtworzeniaTmp = parseLocalDateTimeFromCell(columnNumbers.get("dataUtworzenia"), currentRow);
                lineFromFileDto.setDataUtworzenia(dataUtworzeniaTmp);

                String ktoStworzylTmp = parseStringFromCell(columnNumbers.get("ktoStworzyl"), currentRow);
                lineFromFileDto.setKtoStworzyl(ktoStworzylTmp);

                results.add(lineFromFileDto);
            }
        }

        return results;
    }


    private String parseStringFromCell(Integer columnNumber, Row currentRow) {
        try {
            return dataFormatter.formatCellValue(currentRow.getCell(columnNumber));
        } catch (NullPointerException e) {
            LOG.trace("komórka pusta" + " parseStringFromCell row " + currentRow.getRowNum() + " kol " + columnNumber);
            return null;
        } catch (Exception e) {
            LOG.debug(e.toString() + " parseStringFromCell row " + currentRow.getRowNum() + " kol " + columnNumber);
            return null;
        }
    }

    private Double parseDoubleFromCell(Integer columnNumber, Row currentRow) {

        try {
            return currentRow.getCell(columnNumber).getNumericCellValue();
        } catch (NullPointerException e) {
            LOG.trace("komórka pusta" + " parseDoubleFromCell row " + currentRow.getRowNum() + " kol " + columnNumber);
            return null;
        } catch (Exception e) {
            LOG.info(e.toString() + " parseDoubleFromCell row " + currentRow.getRowNum() + " kol " + columnNumber);
            return null;
        }
    }

    private Integer parseIntegerFromCell(Integer columnNumber, Row currentRow) {
        try {
            CellType cellTypeEnum = currentRow.getCell(columnNumber).getCellTypeEnum();
            if (cellTypeEnum.equals(CellType.STRING)) {
                return Integer.parseInt(currentRow.getCell(columnNumber).getStringCellValue());
            } else if (cellTypeEnum.equals(CellType.NUMERIC)) {
                return ((Double) currentRow.getCell(columnNumber).getNumericCellValue()).intValue();
            }
            return null;
        } catch (NullPointerException e) {
            LOG.trace("komórka pusta" + " parseIntegerFromCell row " + currentRow.getRowNum() + " kol " + columnNumber);
            return null;
        } catch (Exception e) {
            LOG.info(e.toString() + " parseIntegerFromCell row " + currentRow.getRowNum() + " kol " + columnNumber);
            return null;
        }
    }

    private LocalDate parseLocalDateFromCell(Integer columnNumber, Row currentRow) {
        try {
            Date dateCellValue = currentRow.getCell(columnNumber).getDateCellValue();
            return dateCellValue.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        } catch (NullPointerException e) {
            LOG.trace("komórka pusta" + " parseLocalDateFromCell row " + currentRow.getRowNum() + " kol " + columnNumber);
            return null;
        } catch (Exception e) {
            LOG.info(e.toString() + " parseLocalDateFromCell row " + currentRow.getRowNum() + " kol " + columnNumber);
            return null;
        }
    }

    private LocalDateTime parseLocalDateTimeFromCell(Integer columnNumber, Row currentRow) {
        try {
            Date dateCellValue = currentRow.getCell(columnNumber).getDateCellValue();
            return dateCellValue.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        } catch (NullPointerException e) {
            LOG.trace("komórka pusta" + " parseLocalDateTimeFromCell row " + currentRow.getRowNum() + " kol " + columnNumber);
            return null;
        } catch (Exception e) {
            LOG.info(e.toString() + " parseLocalDateFromCell row " + currentRow.getRowNum() + " kol " + columnNumber);
            return null;
        }
    }

}
