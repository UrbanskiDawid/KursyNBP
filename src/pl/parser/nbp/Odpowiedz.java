package pl.parser.nbp;

import pl.parser.nbp.helpers.MathHelpers;

public class Odpowiedz {
	
	public Zapytanie zapytanie;
	
	double sredniKursKupna;
	double odchylenieStandardowe;

	public Odpowiedz(
	  Zapytanie pZap,
	  double pSredniKursKupna,
	  double pOdchylenieStandardowe)
	{
		zapytanie = pZap;
		sredniKursKupna = pSredniKursKupna;
		odchylenieStandardowe = pOdchylenieStandardowe;	    
	}
	
	@Override
	public String toString() {
		return 
		MathHelpers.double2String(sredniKursKupna)
		+"\n"
		+MathHelpers.double2String(odchylenieStandardowe);
	}
}
