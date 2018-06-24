package pl.kostrowski.doka.jzlecenia.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pl.kostrowski.doka.jzlecenia.service.FileRetriever;
import pl.kostrowski.doka.jzlecenia.service.GetFromUserAndParse;

@Controller
public class FileUploadController {

    private GetFromUserAndParse getFromUserAndParse;


    @Autowired
    public FileUploadController(GetFromUserAndParse getFromUserAndParse) {
        this.getFromUserAndParse = getFromUserAndParse;
    }

    @PostMapping("/upload/file")
    public String handleFileUpload(@RequestParam("file") MultipartFile file,
                                   RedirectAttributes redirectAttributes) {

        try {
            getFromUserAndParse.process(file);
        } catch (Exception e) {
            e.printStackTrace();
        }

        redirectAttributes.addFlashAttribute("message",
                "Udało się wgrać i obrobić " + file.getOriginalFilename() + "!");

        return "redirect:/details/files";
    }
}