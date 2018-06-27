package pl.kostrowski.doka.jzlecenia.dao;


import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.kostrowski.doka.jzlecenia.model.InputFile;

import java.util.List;

@Repository
public interface InputFileDao extends CrudRepository<InputFile, Long> {

    List<InputFile> findAll();
    void deleteInputFileById(Long inputFileId);


}
