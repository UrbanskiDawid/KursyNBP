package pl.parser.nbp;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import pl.parser.nbp.api.KodWaluty;
import pl.parser.nbp.api.Dir;
import pl.parser.nbp.api.DirElement;
import pl.parser.nbp.api.DirFilter;
import pl.parser.nbp.api.Pozycja;
import pl.parser.nbp.api.PozycjaFilter;
import pl.parser.nbp.helpers.MathHelpers;
import pl.parser.nbp.helpers.MyLogger;

public class MainClass {

	static DateFormat userInputDateformat = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);

	public static void main(String[] args) {
                
		if(args.length!=3) {
			System.err.println("Error: wrong args. Expected <CODE> <FROM> <TO>. Example: EUR 2013-01-28 2013-01-31");
			MyLogger.LOGGER.fine("Error: wrong main args");
			return;
		}	

		KodWaluty kodWaluty;
		try {
			kodWaluty =  KodWaluty.valueOf(args[0]);	
		} catch (Exception e) {
			e.printStackTrace();
            return;
		}
		
		PrzedzialDat przedzial;
		try {
			Calendar from = Calendar.getInstance();
			from.setTime(userInputDateformat.parse(args[1]));
			
			Calendar to = Calendar.getInstance();
			to.setTime(userInputDateformat.parse(args[2]));

			przedzial = new PrzedzialDat(from, to);
		} catch (ParseException e) {
			e.printStackTrace();
			return;
		}

		Zapytanie zapytanie = new Zapytanie(kodWaluty, przedzial);
		
		Odpowiedz o = ZapytajNBP(zapytanie);
		System.out.println(o);
	}

	public static Odpowiedz ZapytajNBP(Zapytanie zapytanie)	{
		
		MyLogger.LOGGER.info("Pytam NBP o kupno sprzedaż w przedziale: "+zapytanie.przedzial);

		//get dirs
		ArrayList<DirElement> dirs = new ArrayList<DirElement>();
		{
			KupSprzedajFilter dirFilter = new KupSprzedajFilter(zapytanie.przedzial);
			for(int year =zapytanie.przedzial.from.get(Calendar.YEAR);
					year<=zapytanie.przedzial.to.get(Calendar.YEAR);
					year++)
			{
				Dir dir = new Dir(year);
				MyLogger.LOGGER.info("Pytam NBP o rok:"+year);
				dir.getData(dirFilter,dirs);
			}			
		}
		MyLogger.LOGGER.info("DONE: found "+dirs.size() + "dirs.");
		
		//get pozycje
		ArrayList<Pozycja> pozycje = new ArrayList<Pozycja>();
		{
			PozycjaByCodeFilter pozycjaFilter = new PozycjaByCodeFilter(zapytanie);

			for(DirElement e : dirs) {
				e.getData(pozycjaFilter, pozycje);
			}
		}
		MyLogger.LOGGER.info("DONE: found "+pozycje.size() + "pozycje.");
		
		if(pozycje.size()==0) return new Odpowiedz(zapytanie, 0,0);
		
		return Odpowiedz(zapytanie,pozycje);

	}
	
	private static Odpowiedz Odpowiedz(Zapytanie zapytanie,ArrayList<Pozycja> pozycje) {
		
		ArrayList<Double> kursy_kupna = new ArrayList<Double>();
		ArrayList<Double> kursy_sprzedazy = new ArrayList<Double>();

		for(Pozycja p : pozycje){
			MyLogger.LOGGER.info("DONE: dane: "+p.toString());
			kursy_kupna.add(p.kurs_kupna);
			kursy_sprzedazy.add(p.kurs_sprzedazy);
		}
		
		double sredniaKupna=MathHelpers.getMean(kursy_kupna);
		MyLogger.LOGGER.info("DONE: srednia cena kupna: "+kursy_kupna.toString()+" = "+sredniaKupna);
				
		double odchylenieStandardoweSprzedazy = MathHelpers.getStdDev(kursy_sprzedazy);
		MyLogger.LOGGER.info("DONE: odchylenie Standardowe Sprzedazy: "+kursy_sprzedazy.toString()+" = "+odchylenieStandardoweSprzedazy);

		return new Odpowiedz(zapytanie, sredniaKupna, odchylenieStandardoweSprzedazy);
	}
};

class PozycjaByCodeFilter implements PozycjaFilter{
	
	String kodWalutyStr = "";
	PrzedzialDat przedzial;
	
	public PozycjaByCodeFilter(Zapytanie zapytanie){
		this.kodWalutyStr = zapytanie.kod.name();
		this.przedzial = zapytanie.przedzial;
	}

    public boolean isdata_publikacjiOK(Calendar c){
    	return przedzial.isInRange(c); 
    }
    
	public boolean isOK(Pozycja p) {
		return p.kod_waluty.equals(kodWalutyStr);
	}	
}

class KupSprzedajFilter implements DirFilter{
	
	PrzedzialDat przedzial;
	
	public KupSprzedajFilter(PrzedzialDat przedzial){
      this.przedzial = przedzial;
	}
	
	public boolean isOK(DirElement str) {
		
		if(!str.isBuySell()) return false;
		
		Calendar c;
		try {
			c = str.getCallendar();
		} catch (ParseException e) {
			return false;
		}

		return (przedzial.isInRange(c));
	}
}
