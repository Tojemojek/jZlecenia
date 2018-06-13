package pl.kostrowski.doka.jzlecenia.service;

import org.springframework.web.multipart.MultipartFile;

public interface FileRetriever {

    void store(MultipartFile file);

}
