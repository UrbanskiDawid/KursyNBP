# KursyNBP

Simple JAVA program that queries exchange rates from NPB.

Queried data: **kurs_kupna** and **kurs_sprzedaży**.

## User input:
To start program please provide:
- currency code
    > one of (USD, EUR, CHF or GBP)
- date from (YYYY-MM-DD) first day
- date end (YYYY-MM-DD)  last day
   > dates are inlusive
   > date is compared to **'data_publikacji'** from XML

## Output:
There result of query shows on standard output in two lines:
- average buy price for given curency in given time frame
- average deviation for all selling prices in given time frame


## Example:
```
java pl.parser.nbp.MainClass EUR 2013-01-28 2013-01-31
```
> **EUR** request currency code (Euro)

> **2013-01-28** › start date

> **2012-01-31** › end data

```sh
4,1505
0,0125
```
> **4,1505** › average buy price

> **0,0125** ›  average sale price deviation

## Info:
- all source files are in **pl.parser.nbp** package
- **'main'** method is in **'MainClass'** class
- HOW-TO to download data from  NBP: http://www.nbp.pl/home.aspx?f=/kursy/instrukcja_pobierania_kursow_walut.html
- Examle XML data: http://www.nbp.pl/kursy/xml/c073z070413.xml

