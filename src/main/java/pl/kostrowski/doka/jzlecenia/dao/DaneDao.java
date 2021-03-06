package pl.kostrowski.doka.jzlecenia.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.kostrowski.doka.jzlecenia.model.InputFile;
import pl.kostrowski.doka.jzlecenia.model.LineFromFile;

import java.util.List;

@Repository
public interface DaneDao extends CrudRepository<LineFromFile, Long> {

    List<LineFromFile> findAllByNrZlecenia(String noZlecenia);
    void deleteByInputFileId(Long inputFileId);

}
