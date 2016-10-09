package pl.parser.nbp;

import pl.parser.nbp.api.KodWaluty;
import pl.parser.nbp.helpers.MyLogger;


public class Zapytanie{
	KodWaluty kod;
	PrzedzialDat przedzial;

	public Zapytanie(KodWaluty Kod, PrzedzialDat Przedzial){
		kod= Kod;
		przedzial = Przedzial;
		MyLogger.LOGGER.info("Nowe zapytanie: kod:"+kod+" przedzial:"+przedzial);
	}
}