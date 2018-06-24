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
public class DiffController {

    private final OrdersRetriever ordersRetriever;

    @Autowired
    public DiffController(OrdersRetriever ordersRetriever) {
        this.ordersRetriever = ordersRetriever;
    }

    @RequestMapping(value = "/details/diff")
    public String showDiff(@RequestParam(value = "orderNo", required=false) String orderNo,
                           Model model) {

        List<OrderDto> showDiff = ordersRetriever.showDiffOrders(orderNo);
        model.addAttribute("showDiff", showDiff);

        return "diff/orderdiff";
    }

    @RequestMapping(value = "/details/diff/short")
    public String showShortDeliveries(@RequestParam(value = "dateDiff", required=false) Integer dateDiff,
                           Model model) {

        List<OrderDto> showDiff = ordersRetriever.showShortDeliveryOders(dateDiff);
        model.addAttribute("showDiff", showDiff);

        return "diff/orderShortDeliverydiff";
    }

    @RequestMapping(value = "/details/diff/long")
    public String showLongDeliveries(@RequestParam(value = "dateDiff", required=false) Integer dateDiff,
                                      Model model) {

        List<OrderDto> showDiff = ordersRetriever.showLongDeliveryOders(dateDiff);
        model.addAttribute("showDiff", showDiff);

        return "diff/orderLongDeliverydiff";
    }

}
