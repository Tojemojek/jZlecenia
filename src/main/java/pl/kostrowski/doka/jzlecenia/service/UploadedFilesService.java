package pl.kostrowski.doka.jzlecenia.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.kostrowski.doka.jzlecenia.dao.InputFileDao;
import pl.kostrowski.doka.jzlecenia.model.InputFile;

import java.util.List;

@Service
public class UploadedFilesService {

    private final InputFileDao inputFileDao;

    @Autowired
    public UploadedFilesService(InputFileDao inputFileDao) {
        this.inputFileDao = inputFileDao;
    }

    public List<InputFile> diplayAllFiles() {

        return inputFileDao.findAll();

    }
}
