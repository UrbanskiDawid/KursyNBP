package pl.parser.nbp.api;

import java.text.ParseException;
import org.w3c.dom.Element;
import pl.parser.nbp.helpers.MathHelpers;
import pl.parser.nbp.helpers.NetworkHelpers;


public class Pozycja {
	//String nazwa_waluty   = "";//dolar amerykański
	//double przelicznik    = 1;//1
	public String kod_waluty     = "";//USD
	public double kurs_kupna     = 1.0;//2,8210
	public double kurs_sprzedazy = 2.0; //2,8780
    
	public Pozycja(Element pozycja) throws ParseException {
		/*
		<pozycja>
		<nazwa_waluty>dolar amerykański</nazwa_waluty>
		<przelicznik>1</przelicznik>
		<kod_waluty>USD</kod_waluty>
		<kurs_kupna>2,8210</kurs_kupna>
		<kurs_sprzedazy>2,8780</kurs_sprzedazy>
		</pozycja>
		*/

		//this.nazwa_waluty   = getSubElementTextOrNull(pozycja,"nazwa_waluty");
		//this.przelicznik    = Math.string2Double(getSubElementTextOrNull(pozycja,"przelicznik"));
		this.kod_waluty     = NetworkHelpers.getSubElementTextOrNull(pozycja,"kod_waluty");
		this.kurs_kupna     = MathHelpers.string2Double(NetworkHelpers.getSubElementTextOrNull(pozycja,"kurs_kupna"));
		this.kurs_sprzedazy = MathHelpers.string2Double(NetworkHelpers.getSubElementTextOrNull(pozycja,"kurs_sprzedazy"));
	}
	
	@Override
	public String toString() {
		return Pozycja.class.getName()+": ["+this.kod_waluty+" S:"+this.kurs_sprzedazy+" K:"+this.kurs_kupna+"]";
	}

};

