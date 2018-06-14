package pl.kostrowski.doka.jzlecenia.model;


import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;

@Data
@Entity
@Table(name = "dane")
public class LineFromFile {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "sales_id")
    private String salesId;

    @Column(name = "item_id")
    private String itemId;

    @Column(name = "sales_status")
    private String salesStatus;

    @Column(name = "ordered_qty")
    private BigDecimal orderedQty;

    @Column(name = "sales_price")
    private BigDecimal salesPrice;

    @Column(name = "weight")
    private BigDecimal weight;


}
