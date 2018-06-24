package pl.kostrowski.doka.jzlecenia.dto;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;


@Data
public class LineFromFileDto {

    private String fileName;

    private String nrZlecenia;

    private String nrArtykulu;

    private String nazwaArtykulu;

    private Double iloscZamowiona;

    private Double iloscPozostalaDoWydania;

    private LocalDate dataDostawy;

    private String jednostkaSalesUnit;

    private String nrProjektu;

    private String nrBudowy;

    private String nrKlienta;

    private String nrWierszaWZleceniu;

    private String typBiznesu;

    private Integer statusWiersza;

    private Double masaJednostkowa;

    private Double cenaSprzedazyZaJednostke;

    private String jednostkaSalesPriceUnit;

    private Double tonaz;

    private Double pozostalyTonazDoWydania;

    private LocalDateTime dataModyfikacji;

    private LocalDateTime dataUtworzenia;

    private String ktoStworzyl;

}
