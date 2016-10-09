package pl.parser.nbp.api;

import java.util.Calendar;

public interface PozycjaFilter{
	public boolean isOK(Pozycja p);
	public boolean isdata_publikacjiOK(Calendar c);
}
