package pl.kostrowski.doka.jzlecenia.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.kostrowski.doka.jzlecenia.dao.MyFileDao;
import pl.kostrowski.doka.jzlecenia.model.MyFile;

import java.util.List;

@Service
public class UploadedFilesService {

    private final MyFileDao myFileDao;

    @Autowired
    public UploadedFilesService(MyFileDao myFileDao) {
        this.myFileDao = myFileDao;
    }

    public List<MyFile> diplayAllFiles() {

        return myFileDao.findAll();

    }
}
