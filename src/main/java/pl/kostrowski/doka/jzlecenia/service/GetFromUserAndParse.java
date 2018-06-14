package pl.kostrowski.doka.jzlecenia.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

@Service
public class GetFromUserAndParse {

    private final FileRetriever storageService;
    private final ReadFromExcel readFromExcel;


    @Autowired
    public GetFromUserAndParse(FileRetriever storageService, ReadFromExcel readFromExcel) {
        this.storageService = storageService;
        this.readFromExcel = readFromExcel;
    }

    public void process(MultipartFile multipartFile) throws Exception {

        File file = storageService.passFileOn(multipartFile);
        readFromExcel.read(file);

    }
}
