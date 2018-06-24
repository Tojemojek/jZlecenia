package pl.kostrowski.doka.jzlecenia.mappings;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Data
public class MyMappings {

    private Map<String, String> columnMappings = new HashMap<>();
    private Map<String, String> invertedColumnMappings;

    private Map<Integer, String> statusCodesMapping = new HashMap<>();
    private Map<String, Integer> invertedStatusCodesMapping;


    {
        columnMappings.put("nrZlecenia","SalesId");
        columnMappings.put("nrArtykulu","ItemId");
        columnMappings.put("nazwaArtykulu","Name");
        columnMappings.put("iloscZamowiona","QtyOrdered");
        columnMappings.put("iloscPozostalaDoWydania","RemainSalesPhysical");
        columnMappings.put("dataDostawy","ConfirmedDlv");
        columnMappings.put("jednostkaSalesUnit","SalesUnit");
        columnMappings.put("nrProjektu","Dimension4");
        columnMappings.put("nrBudowy","Dimension5");
        columnMappings.put("nrKlienta","CustAccount");
        columnMappings.put("nrWierszaWZleceniu","CCSI_SalesLineNum");
        columnMappings.put("typBiznesu","CCSI_SalesType");
        columnMappings.put("statusWiersza","CCSI_SalesLineStatus");
        columnMappings.put("masaJednostkowa","CCSI_Weight");
        columnMappings.put("cenaSprzedazyZaJednostke","CCSI_SalesListPrice");
        columnMappings.put("jednostkaSalesPriceUnit","CCSI_SalesPriceUnit");
        columnMappings.put("tonaz","CCSI_Tonnage");
        columnMappings.put("pozostalyTonazDoWydania","CCSI_TonnageRemain");
        columnMappings.put("dataModyfikacji","modifiedDateTime");
        columnMappings.put("dataUtworzenia","createdDateTime");
        columnMappings.put("ktoStworzyl","createdBy");

        invertedColumnMappings = columnMappings.entrySet()
                .stream()
                .collect(Collectors.toMap(Map.Entry::getValue, Map.Entry::getKey));

        statusCodesMapping.put(100, "Created");
        statusCodesMapping.put(130, "Released (Intercompany)");
        statusCodesMapping.put(170, "Approved (Clearing)");
        statusCodesMapping.put(190, "Confirmed – Order (Intercompany)");
        statusCodesMapping.put(192, "Confirmed – Order (Intercompany) after…");
        statusCodesMapping.put(195, "Released to EPOS");
        statusCodesMapping.put(200, "Confirmed");
        statusCodesMapping.put(210, "Confirmed after change");
        statusCodesMapping.put(240, "Order pulling started");
        statusCodesMapping.put(245, "Partial pulled");
        statusCodesMapping.put(250, "Order pulling complete");
        statusCodesMapping.put(270, "Partial delivered");
        statusCodesMapping.put(300, "Delivered");
        statusCodesMapping.put(390, "Partial invoiced");
        statusCodesMapping.put(400, "Invoiced");
        statusCodesMapping.put(500, "Voided");
        statusCodesMapping.put(900, "Material in Transit");

        invertedStatusCodesMapping = statusCodesMapping.entrySet()
                .stream()
                .collect(Collectors.toMap(Map.Entry::getValue, Map.Entry::getKey));

    }
}
