Select * from
(select nr_zlecenia, count(*) as liczba
from dane
where file_name = '2018-06-15-zlecenia.xlsx'
group by nr_zlecenia) a
join (
select nr_zlecenia, count(*) as liczba
from dane
where file_name = '2018-06-13-zlecenia.xlsx'
group by nr_zlecenia ) b
on a.nr_zlecenia = b.nr_zlecenia
where a.liczba != b.liczba




Select * from
(
select nr_zlecenia, count(*) as liczba, sum(ilosc_zamowiona * masa_jednostkowa) as suma
from dane
where
file_name = '2018-06-15-zlecenia.xlsx'
group by nr_zlecenia
) a
join (
select nr_zlecenia, count(*) as liczba, sum(ilosc_zamowiona * masa_jednostkowa) as suma
from dane
where file_name = '2018-06-13-zlecenia.xlsx'
group by nr_zlecenia
) b
on a.nr_zlecenia = b.nr_zlecenia
where (a.liczba != b.liczba or a.suma != b.suma)



Select * from
(
select nr_zlecenia, file_name, count(*) as liczba_wierszy, sum(ilosc_zamowiona * masa_jednostkowa) as tonaz_calkowity
from dane
group by nr_zlecenia, file_name
order by nr_zlecenia
) a
join (
select nr_zlecenia, file_name, count(*) as liczba_wierszy, sum(ilosc_zamowiona * masa_jednostkowa) as tonaz_calkowity
from dane
group by nr_zlecenia, file_name
order by nr_zlecenia
) b
on a.nr_zlecenia = b.nr_zlecenia
where (a.liczba_wierszy != b.liczba_wierszy or a.tonaz_calkowity !=b.tonaz_calkowity) and b.file_name>a.file_name




CREATE VIEW zlecenia AS
SELECT nr_zlecenia, file_name, count(*) as liczba_wierszy, sum(ilosc_zamowiona * masa_jednostkowa) as suma_tonazu
FROM dane
group by nr_zlecenia, file_name



select * from
(
select
a.nr_zlecenia, count(DISTINCT a.suma_tonazu ) as licznikTonazu, count(DISTINCT a.liczba_wierszy) as licznikLinii
from zlecenia a
group by a.nr_zlecenia
) b
where b.licznikTonazu > 1 or b.licznikLinii > 1