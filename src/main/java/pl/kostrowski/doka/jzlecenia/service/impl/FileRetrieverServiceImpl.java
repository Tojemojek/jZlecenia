package pl.kostrowski.doka.jzlecenia.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import pl.kostrowski.doka.jzlecenia.configuration.MyPaths;
import pl.kostrowski.doka.jzlecenia.exceptions.StorageException;
import pl.kostrowski.doka.jzlecenia.service.FileRetriever;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;


@Service
public class FileRetrieverServiceImpl implements FileRetriever {

    private final String truePath = MyPaths.PATH_TO_SAVE_FILES.getPath();
    private final Path rootLocation = FileSystems.getDefault().getPath(truePath);

    @Override
    public void store(MultipartFile file) {
        String filename = StringUtils.cleanPath(file.getOriginalFilename());
        try {
            if (file.isEmpty()) {
                throw new StorageException("Failed to store empty file " + filename);
            }
            if (filename.contains("..")) {
                // This is a security check
                throw new StorageException(
                        "Cannot store file with relative path outside current directory "
                                + filename);
            }
            try (InputStream inputStream = file.getInputStream()) {
                Files.copy(inputStream, this.rootLocation.resolve(filename),
                        StandardCopyOption.REPLACE_EXISTING);
            }
        } catch (IOException e) {
            throw new StorageException("Failed to store file " + filename, e);
        }
    }
}