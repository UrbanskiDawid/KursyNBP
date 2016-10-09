package pl.parser.nbp.api;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;

import pl.parser.nbp.helpers.MyLogger;
import pl.parser.nbp.helpers.NetworkHelpers;


public class Dir{
	
	int year = 1988;
	String url="";
	
	public Dir(int year){
		this.year= year;
 
	    String url = "http://www.nbp.pl/kursy/xml/dir";
		int currentYear = Calendar.getInstance().get(Calendar.YEAR);
		if(year!=currentYear){
			url += year;
		}		
		url += ".txt";
		
		this.url = url;
	}

	/*
    'xnnnzrrmmdd.xml' 

	x – litera określająca typ tabeli:
		a - tabela kursów średnich walut obcych;
		b - tabela kursów średnich walut niewymienialnych;
		c - tabela kursów kupna i sprzedaży; *
		h - tabela kursów jednostek rozliczeniowych.
		
    nnn – trzyznakowy (liczbowy) numer tabeli w roku; 

	rrmmdd – data publikacji/obowiązywania tabeli w formacie (bez odstępów): dwie ostatnie cyfry numeru roku, dwie cyfry numeru miesiąca oraz dwie cyfry numeru dnia.
	 */
	public void getData(
			DirFilter filter,
			ArrayList<DirElement> ret)
	{
		ArrayList<String> out;
		try {
			out = NetworkHelpers.readUrl(this.url);
		} catch (IOException e) {
			MyLogger.LOGGER.fine(e.toString());
			return;
		}	
			
		for(String s: out) {
			DirElement e = new DirElement(s);
			if(filter.isOK(e)){
				MyLogger.LOGGER.info("NBP odp: "+s);
				ret.add(e);
			}else{
				MyLogger.LOGGER.info("NBP odp: "+s+" NO!");				
			}
		}
	}
}