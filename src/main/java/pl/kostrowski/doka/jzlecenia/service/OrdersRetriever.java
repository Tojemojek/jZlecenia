package pl.kostrowski.doka.jzlecenia.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.kostrowski.doka.jzlecenia.dao.CustomQueries;
import pl.kostrowski.doka.jzlecenia.dao.DaneDao;
import pl.kostrowski.doka.jzlecenia.dto.OrderDto;
import pl.kostrowski.doka.jzlecenia.model.LineFromFile;

import java.util.LinkedList;
import java.util.List;


@Service
public class OrdersRetriever {


    private final CustomQueries customQueries;
    private final DaneDao daneDao;

    @Autowired
    public OrdersRetriever(CustomQueries customQueries, DaneDao daneDao) {
        this.customQueries = customQueries;
        this.daneDao = daneDao;
    }

    public List<OrderDto> displayAllOrders() {

        return customQueries.getListOfAllOrders();

    }

    public List<LineFromFile> displayOrderWithNumber(String orderNumber) {
        if (orderNumber != null) {
            return daneDao.findAllByNrZlecenia(orderNumber);
        }
        return new LinkedList<>();
    }

    public List<OrderDto> showDiffOrders() {

        List<OrderDto> listOfModifiedOrders = customQueries.getListOfModifiedOrders();

        return listOfModifiedOrders;

    }

    public List<OrderDto> showSingleDiffOrder(String orderId) {

        List<OrderDto> listOfModifiedOrders = customQueries.getListOfModifiedOrder(orderId);

        return listOfModifiedOrders;

    }


    public List<OrderDto> showShortDeliveryOders(Integer dateDiff) {

        if (dateDiff != null) {
            List<OrderDto> listShortDeliveries = customQueries.getShortDeliveryTime(dateDiff);
            return listShortDeliveries;
        }
        return null;

    }

    public List<OrderDto> showLongDeliveryOders(Integer dateDiff) {

        if (dateDiff != null) {
            List<OrderDto> listLongDeliveries = customQueries.getLongDeliveryTime(dateDiff);

            return listLongDeliveries;
        }
        return null;

    }
}
