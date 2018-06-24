package pl.kostrowski.doka.jzlecenia.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.kostrowski.doka.jzlecenia.dto.OrderDto;
import pl.kostrowski.doka.jzlecenia.model.LineFromFile;
import pl.kostrowski.doka.jzlecenia.service.OrdersRetriever;

import java.util.List;


@Controller
public class SingleController {

    private final OrdersRetriever ordersRetriever;

    @Autowired
    public SingleController(OrdersRetriever ordersRetriever) {
        this.ordersRetriever = ordersRetriever;
    }

    @RequestMapping(value = "/details/order")
    public String showSingleList(@RequestParam(value = "orderNo", required=false) String orderNo,
                                 Model model) {

        List<LineFromFile> allByNoOfOrder = ordersRetriever.displayOrderWithNumber(orderNo);
        model.addAttribute("allByNoOfOrder", allByNoOfOrder);

        return "single/order";
    }


}
