package pl.kostrowski.doka.jzlecenia.model;


import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "dane")
public class LineFromFile {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL)
    private InputFile inputFile;

    @Column(name = "nr_zlecenia")
    private String nrZlecenia;

    @Column(name = "nr_artykulu")
    private String nrArtykulu;

    @Column(name = "nazwa_artykulu")
    private String nazwaArtykulu;

    @Column(name = "ilosc_zamowiona")
    private BigDecimal iloscZamowiona;

    @Column(name = "ilosc_pozostala_do_wydania")
    private BigDecimal iloscPozostalaDoWydania;

    @Column(name = "data_dostawy")
    private LocalDate dataDostawy;

    @Column(name = "jednostka_sales_unit")
    private String jednostkaSalesUnit;

    @Column(name = "nr_projektu")
    private String nrProjektu;

    @Column(name = "nr_budowy")
    private String nrBudowy;

    @Column(name = "nr_klienta")
    private String nrKlienta;

    @Column(name = "nr_wiersza_w_zleceniu")
    private String nrWierszaWZleceniu;

    @Column(name = "typ_biznesu")
    private String typBiznesu;

    @Column(name = "status_wiersza")
    private Integer statusWiersza;

    @Column(name = "masa_jednostkowa")
    private BigDecimal masaJednostkowa;

    @Column(name = "cena_sprzedazy_za_jednostke")
    private BigDecimal cenaSprzedazyZaJednostke;

    @Column(name = "jednostka_sales_price_unit")
    private String jednostkaSalesPriceUnit;

    @Column(name = "tonaz")
    private BigDecimal tonaz;

    @Column(name = "pozostaly_tonaz_do_wydania")
    private BigDecimal pozostalyTonazDoWydania;

    @Column(name = "data_modyfikacji")
    private LocalDateTime dataModyfikacji;

    @Column(name = "data_utworzenia")
    private LocalDateTime dataUtworzenia;

    @Column(name = "kto_stworzyl")
    private String ktoStworzyl;

}
