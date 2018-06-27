package pl.kostrowski.doka.jzlecenia.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.kostrowski.doka.jzlecenia.dto.OrderDto;
import pl.kostrowski.doka.jzlecenia.model.InputFile;
import pl.kostrowski.doka.jzlecenia.service.OrdersRetriever;
import pl.kostrowski.doka.jzlecenia.service.UploadedFilesService;

import java.util.List;


@Controller
public class AllController {

    UploadedFilesService uploadedFilesService;
    OrdersRetriever ordersRetriever;

    @Autowired
    public AllController(UploadedFilesService uploadedFilesService, OrdersRetriever ordersRetriever) {
        this.uploadedFilesService = uploadedFilesService;
        this.ordersRetriever = ordersRetriever;
    }

    @RequestMapping(value = "/")
    public String displayMenu() {
        return "index";
    }

    @RequestMapping(value = "/upload/file")
    public String uploadFile() {
        return "uploadFile";
    }

    @RequestMapping(value = "/details/files")
    public String displayFiles(Model model) {

        List<InputFile> uplodedFiles = uploadedFilesService.diplayAllFiles();
        model.addAttribute("uploadedFiles", uplodedFiles);

        return "many/files";
    }

    @RequestMapping(value = "/details/orders")
    public String displayOrders(Model model) {
        List<OrderDto> allOrders = ordersRetriever.displayAllOrders();
        model.addAttribute("allOrders", allOrders);
        return "many/orders";
    }

}
