package pl.kostrowski.doka.jzlecenia.dto;


import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class OrderDto {

    private String fileName;

    private String nrZlecenia;

    private Double tonaz;

    private Integer liczbaLinii;

    private String czyRozneIlosciLinii;

    private String czyRozneIlosciTonazu;

    private LocalDateTime dataModyfikacji;

    private LocalDateTime dataCzasUtworzenia;

    private LocalDate dataDostawy;

    private LocalDate dataUtworzenia;

    private String ktoStworzyl;

    private Integer czasNaReakcje;

    private LocalDate dataZPliku;


}
