package pl.kostrowski.doka.jzlecenia.dao;


import org.apache.commons.lang3.time.DateUtils;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pl.kostrowski.doka.jzlecenia.dto.OrderDto;

import javax.persistence.Convert;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.beans.PersistenceDelegate;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.Duration;
import java.time.LocalDate;
import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Repository
public class CustomQueries {


    @PersistenceContext
    private EntityManager em;

    public List<OrderDto> getListOfAllOrders() {

        StringBuilder sb = new StringBuilder();
        sb.append("SELECT DISTINCT ");
        sb.append("SUM(ilosc_zamowiona * masa_jednostkowa), count(*),  nr_zlecenia ");
        sb.append("FROM dane ");
        sb.append("WHERE ");
        sb.append("ilosc_zamowiona IS NOT null ");
        sb.append("AND masa_jednostkowa IS NOT null ");
        sb.append("GROUP by nr_zlecenia, file_name ");
        sb.append("ORDER by nr_zlecenia");

        String myQuery = sb.toString();

        Query query = em.createNativeQuery(myQuery);
        List<Object[]> tempResults = query.getResultList();

        List<OrderDto> orderDtos = new LinkedList<>();

        for (Object[] tempResult : tempResults) {
            OrderDto orderDto = new OrderDto();

            orderDto.setTonaz(((BigDecimal) tempResult[0]).doubleValue());
            orderDto.setLiczbaLinii(((BigInteger) tempResult[1]).intValue());
            orderDto.setNrZlecenia((String) tempResult[2]);

            orderDtos.add(orderDto);
        }
        return orderDtos;
    }

    public List<OrderDto> getListOfModifiedOrders() {

        StringBuilder sb = new StringBuilder();

        sb.append("select * from ( ");
        sb.append("select ");
        sb.append("a.nr_zlecenia, ");
        sb.append("count(DISTINCT a.suma_tonazu ) as licznikTonazu, ");
        sb.append("count(DISTINCT a.liczba_wierszy) as licznikLinii ");
        sb.append("from zlecenia a ");
        sb.append("group by a.nr_zlecenia ");
        sb.append(") b ");
        sb.append("where b.licznikTonazu > 1 or b.licznikLinii > 1 ");

        String myQuery = sb.toString();

        Query query = em.createNativeQuery(myQuery);
        List<Object[]> tempResults = query.getResultList();

        List<OrderDto> orderDtos = new LinkedList<>();

        for (Object[] tempResult : tempResults) {
            OrderDto orderDto = new OrderDto();

            orderDto.setNrZlecenia((String) tempResult[0]);

            String rozneTonaze = (((BigInteger) tempResult[1]).intValue() > 1) ? "Tak, tyle wersji: "
                    +  ((BigInteger) tempResult[1]).intValue():"Nie";
            String rozneLiczbyLinii = (((BigInteger) tempResult[2]).intValue() > 1) ? "Tak, tyle wersji:  "
                    +((BigInteger) tempResult[2]).intValue() :"Nie";

            orderDto.setCzyRozneIlosciTonazu(rozneTonaze);
            orderDto.setCzyRozneIlosciLinii(rozneLiczbyLinii);

            orderDtos.add(orderDto);
        }
        return orderDtos;
    }

    public List<OrderDto> getShortDeliveryTime(@Param("dateDiff") Integer dateDiff){

        StringBuilder sb = new StringBuilder();
        sb.append("select distinct ");
        sb.append("nr_zlecenia, data_dostawy, convert(data_utworzenia, date),  (data_dostawy - data_utworzenia) ");
        sb.append("from dane ");
        sb.append("where (data_dostawy - data_utworzenia) < ");
        sb.append(dateDiff);
        sb.append(" group by nr_zlecenia, data_dostawy, data_utworzenia ");
        sb.append("order by (data_dostawy - data_utworzenia)  asc ");

        String myQuery = sb.toString();

        Query query = em.createNativeQuery(myQuery);
        List<Object[]> tempResults = query.getResultList();

        return convertShortLongTermDeliveries(tempResults);
    }


    public List<OrderDto> getLongDeliveryTime(@Param("dateDiff") Integer dateDiff){

        StringBuilder sb = new StringBuilder();
        sb.append("select distinct ");
        sb.append("nr_zlecenia, data_dostawy, convert(data_utworzenia, date),  (data_dostawy - data_utworzenia) ");
        sb.append("from dane ");
        sb.append("where (data_dostawy - data_utworzenia) > ");
        sb.append(dateDiff);
        sb.append(" group by nr_zlecenia, data_dostawy, data_utworzenia ");
        sb.append("order by (data_dostawy - data_utworzenia)  desc ");

        String myQuery = sb.toString();

        Query query = em.createNativeQuery(myQuery);
        List<Object[]> tempResults = query.getResultList();

        return convertShortLongTermDeliveries(tempResults);
    }

    private List<OrderDto> convertShortLongTermDeliveries(List<Object[]> tempResults) {

        List<OrderDto> orderDtos = new LinkedList<>();

        for (Object[] tempResult : tempResults) {
            OrderDto orderDto = new OrderDto();

            orderDto.setNrZlecenia((String) tempResult[0]);

            LocalDate dataDostawyTmp = ((java.sql.Date) tempResult[1]).toLocalDate();
            LocalDate dataUtworzeniaTmp = ((java.sql.Date) tempResult[2]).toLocalDate();

            Integer czasNaReakcje = ((Long) ChronoUnit.DAYS.between(dataUtworzeniaTmp, dataDostawyTmp)).intValue();

            orderDto.setDataUtworzenia(dataUtworzeniaTmp);
            orderDto.setDataDostawy(dataDostawyTmp);
            orderDto.setCzasNaReakcje(czasNaReakcje);

            orderDtos.add(orderDto);
        }

        return orderDtos;
    }

}
