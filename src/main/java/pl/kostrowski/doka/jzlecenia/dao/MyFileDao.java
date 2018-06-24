package pl.kostrowski.doka.jzlecenia.dao;


import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.kostrowski.doka.jzlecenia.model.MyFile;

import java.util.List;

@Repository
public interface MyFileDao extends CrudRepository<MyFile, Long> {

    List<MyFile> findAll();
    void deleteByFileName(String filename);

}
