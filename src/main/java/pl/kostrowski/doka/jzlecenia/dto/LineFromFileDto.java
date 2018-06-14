package pl.kostrowski.doka.jzlecenia.dto;


import lombok.Data;


@Data
public class LineFromFileDto {

    private String salesId;

    private String itemId;

    private String salesStatus;

    private String orderedQty;

    private String salesPrice;

    private String weight;

}
