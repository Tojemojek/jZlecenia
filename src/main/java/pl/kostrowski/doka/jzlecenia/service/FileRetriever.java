package pl.kostrowski.doka.jzlecenia.service;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.net.URISyntaxException;


@Service
public class FileRetriever {

    private final Environment environment;
    private final Logger LOG = LoggerFactory.getLogger(FileRetriever.class);

    @Autowired
    public FileRetriever(Environment environment) {
        this.environment = environment;
    }

    public File passFileOn(MultipartFile multipartFile) {

        String fileSubfolder = environment.getProperty("save.file.path");
        String directoryPath = new File(".").getAbsolutePath();
        directoryPath = directoryPath.substring(0,directoryPath.lastIndexOf("."));
        String filePath = directoryPath + fileSubfolder + File.separator;

        LOG.info("Ścieżka zapisu = " + filePath);

        String fullFilePathWithName = filePath + multipartFile.getOriginalFilename();

        LOG.info("Ścieżka zapisu z nazwą pliku = " + fullFilePathWithName);

        try {
            if (!multipartFile.isEmpty()) {
                File localFile = new File(fullFilePathWithName);
                multipartFile.transferTo(localFile);
                LOG.info("Zapisano plik + " + localFile.toString());
                return localFile;
            }
        } catch (Exception e) {
            LOG.warn("Coś poszło nie tak z zapisem pliku" + multipartFile.getOriginalFilename());
        }

        return null;
    }
}
