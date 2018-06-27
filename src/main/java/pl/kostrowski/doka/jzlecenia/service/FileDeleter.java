package pl.kostrowski.doka.jzlecenia.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.kostrowski.doka.jzlecenia.dao.DaneDao;
import pl.kostrowski.doka.jzlecenia.dao.InputFileDao;

import javax.transaction.Transactional;

@Service
public class FileDeleter {

    private final DaneDao daneDao;
    private final InputFileDao inputFileDao;

    @Autowired
    public FileDeleter(DaneDao daneDao, InputFileDao inputFileDao) {
        this.daneDao = daneDao;
        this.inputFileDao = inputFileDao;
    }

    @Transactional
    public void deleteDateFromDbWhereFileNameIs(Long inputFileId) {
        daneDao.deleteByInputFileId(inputFileId);
        inputFileDao.deleteInputFileById(inputFileId);
    }
}
