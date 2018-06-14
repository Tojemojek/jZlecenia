package pl.kostrowski.doka.jzlecenia.mappings;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Data
public class MapColumns {

    private Map<String,String> mappings = new HashMap<>();
    private Map<String,String> invertedMappings = new HashMap<>();

    {
        mappings.put("salesId","SalesId");
        mappings.put("itemId","ItemId");
        mappings.put("salesStatus","SalesStatus");
        mappings.put("orderedQty","QtyOrdered");
        mappings.put("salesPrice","SalesPrice");
        mappings.put("weight","CCSI_Weight");

        invertedMappings = mappings.entrySet()
                .stream()
                .collect(Collectors.toMap(Map.Entry::getValue, Map.Entry::getKey));
    }
}
