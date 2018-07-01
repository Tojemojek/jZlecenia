package pl.kostrowski.doka.jzlecenia.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.kostrowski.doka.jzlecenia.dto.OrderDto;
import pl.kostrowski.doka.jzlecenia.model.LineFromFile;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

@Service
public class OrderDisplayer {

    OrdersRetriever ordersRetriever;

    @Autowired
    public OrderDisplayer(OrdersRetriever ordersRetriever) {
        this.ordersRetriever = ordersRetriever;
    }

    public List<List<LineFromFile>> diplayOrderWithDiffInfo(String orderId) {

        List<LineFromFile> lineFromFiles = ordersRetriever.displayOrderWithNumber(orderId);

        Map<LocalDate, List<LineFromFile>> sortedByDateFromFile =
                lineFromFiles.stream().collect(Collectors.groupingBy(lines -> lines.getInputFile().getFileDate()));


        Map<LocalDate, List<LineFromFile>> sortedByDatesSummirized = new TreeMap<>();

        for (Map.Entry<LocalDate, List<LineFromFile>> entry : sortedByDateFromFile.entrySet()) {
            List<LineFromFile> lineFromFilesSummirized = summarizedOrder(entry.getValue());
            sortedByDatesSummirized.put(entry.getKey(), lineFromFilesSummirized);
        }

        List<List<LineFromFile>> collected = sortedByDatesSummirized.values().stream().collect(Collectors.toList());

        return collected;
    }

    public List<OrderDto> displayDifferencesInOrders(String orderId) {

        List<OrderDto> orderDtos = ordersRetriever.showSingleDiffOrder(orderId);
        return  orderDtos;

    }

    private List<LineFromFile> summarizedOrder(List<LineFromFile> singleDateList) {


        Map<String,LineFromFile> summarized = new TreeMap<>();

        for (LineFromFile lineFromFile : singleDateList) {

            if (summarized.containsKey(lineFromFile.getNrArtykulu())){

                LineFromFile summirizedLine = summarized.get(lineFromFile.getNrArtykulu());

                BigDecimal tonaz = lineFromFile.getTonaz().add(summirizedLine.getTonaz());
                BigDecimal iloscZamowiona = lineFromFile.getIloscZamowiona().add(summirizedLine.getIloscZamowiona());
                BigDecimal iloscDoWydania = lineFromFile.getIloscPozostalaDoWydania().add(summirizedLine.getIloscPozostalaDoWydania());

                summirizedLine.setIloscPozostalaDoWydania(iloscDoWydania);
                summirizedLine.setIloscZamowiona(iloscZamowiona);
                summirizedLine.setTonaz(tonaz);

            } else {
                summarized.put(lineFromFile.getNrArtykulu(), lineFromFile);
            }
        }
        List<LineFromFile> collect = summarized.values().stream().collect(Collectors.toList());

        return collect;
    }


}
