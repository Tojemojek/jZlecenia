package pl.kostrowski.doka.jzlecenia.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.kostrowski.doka.jzlecenia.service.OrderDisplayer;


@Controller
public class SingleController {

    private final OrderDisplayer orderDisplayer;

    @Autowired
    public SingleController(OrderDisplayer orderDisplayer) {
        this.orderDisplayer = orderDisplayer;
    }

    @RequestMapping(value = "/details/order")
    public String showSingleList(@RequestParam(value = "orderNo", required=false) String orderNo,
                                 Model model) {

        model.addAttribute("diffOrdersGeneral", orderDisplayer.displayDifferencesInOrders(orderNo));

        model.addAttribute("diffOrdersDetails", orderDisplayer.diplayOrderWithDiffInfo(orderNo));
        return "single/order";
    }


}
