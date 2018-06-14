package pl.kostrowski.doka.jzlecenia.dao;


import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.kostrowski.doka.jzlecenia.model.LineFromFile;

@Repository
public interface DaneDao extends CrudRepository<LineFromFile, Long> {

}
