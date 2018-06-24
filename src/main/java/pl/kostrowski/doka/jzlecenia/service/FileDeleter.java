package pl.kostrowski.doka.jzlecenia.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.kostrowski.doka.jzlecenia.dao.DaneDao;
import pl.kostrowski.doka.jzlecenia.dao.MyFileDao;

import javax.transaction.Transactional;

@Service
public class FileDeleter {

    private final DaneDao daneDao;
    private final MyFileDao myFileDao;

    @Autowired
    public FileDeleter(DaneDao daneDao, MyFileDao myFileDao) {
        this.daneDao = daneDao;
        this.myFileDao = myFileDao;
    }

    @Transactional
    public void deleteDateFromDbWhereFileNameIs(String fileName) {
        daneDao.deleteByFileName(fileName);
        myFileDao.deleteByFileName(fileName);
    }
}
