package pl.kostrowski.doka.jzlecenia.converters;

import org.springframework.stereotype.Service;
import pl.kostrowski.doka.jzlecenia.dto.LineFromFileDto;
import pl.kostrowski.doka.jzlecenia.model.LineFromFile;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;

@Service
public class LineFromFileConverter {

    public List<LineFromFile> convert(List<LineFromFileDto> input) {

        List<LineFromFile> result = new LinkedList<>();

        if (input != null) {
            for (LineFromFileDto lineFromFileDto : input) {
                LineFromFile lineFromFile = new LineFromFile();

                lineFromFile.setSalesId(lineFromFileDto.getSalesId());
                lineFromFile.setSalesStatus(lineFromFileDto.getSalesStatus());
                lineFromFile.setItemId(lineFromFileDto.getItemId());

                BigDecimal orderedQuantity = tryToParseBigDecimal(lineFromFileDto.getOrderedQty(), 4);
                lineFromFile.setOrderedQty(orderedQuantity);

                BigDecimal salesPrice = tryToParseBigDecimal(lineFromFileDto.getSalesPrice(), 2);
                lineFromFile.setSalesPrice(salesPrice);

                BigDecimal weight = tryToParseBigDecimal(lineFromFileDto.getWeight(), 4);
                lineFromFile.setWeight(weight);

                result.add(lineFromFile);
            }
        }
        return result;
    }

    private BigDecimal tryToParseBigDecimal (String input, int scale){

        BigDecimal result;
        input = input.replace(".","")
                .replace(",",".")
                .replaceAll("\\s+","")
                .replaceAll("\u0160", "")
                .replaceAll("\u00A0", "");

        try {
            result = new BigDecimal(input).setScale(scale);
        } catch (NumberFormatException e){
            result = null;
        }
        return result;
    }
}
