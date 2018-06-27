package pl.kostrowski.doka.jzlecenia.model;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "pliki")
public class InputFile {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "file_name")
    private String fileName;

    @Column(name = "input_timestamp")
    private LocalDateTime inputTimeStamp;

    @Column(name = "file_date")
    private LocalDate fileDate;

}
