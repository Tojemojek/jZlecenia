package pl.kostrowski.doka.jzlecenia.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import pl.kostrowski.doka.jzlecenia.configuration.MyPaths;


import java.io.File;


@Service
public class FileRetriever {


    public File passFileOn(MultipartFile multipartFile) {
        try {
            if (!multipartFile.isEmpty()) {
                File localFile = new File(MyPaths.PATH_TO_SAVE_FILES.getPath() + multipartFile.getOriginalFilename());
                multipartFile.transferTo(localFile);
                return localFile;
            }
        } catch (Exception e) {

        }
        return null;

    }
}
