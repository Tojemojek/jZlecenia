package pl.kostrowski.doka.jzlecenia.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import pl.kostrowski.doka.jzlecenia.model.LineFromFile;
import pl.kostrowski.doka.jzlecenia.service.FileDeleter;
import pl.kostrowski.doka.jzlecenia.service.OrdersRetriever;

import java.util.List;


@Controller
public class DeleteController {

    private final FileDeleter fileDeleter;

    @Autowired
    public DeleteController(FileDeleter fileDeleter) {
        this.fileDeleter = fileDeleter;
    }


    @RequestMapping(value = "/delete/file", method = RequestMethod.POST)
    public String showSingleList(@RequestParam(value = "fileName") String fileName) {

        fileDeleter.deleteDateFromDbWhereFileNameIs(fileName);

        return "redirect:/details/files";
    }


}
