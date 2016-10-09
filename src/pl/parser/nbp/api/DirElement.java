package pl.parser.nbp.api;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import pl.parser.nbp.helpers.MyLogger;
import pl.parser.nbp.helpers.NetworkHelpers;


public class DirElement {
	
	String str;	
	static final DateFormat data_time_Format = new SimpleDateFormat("yyMMdd", Locale.ENGLISH);
	
	public DirElement(String str){
		this.str = str;
		//TODO: validate?
	}
	
	//c - tabela kursów kupna i sprzedaży;
	public boolean isBuySell() {
		if(str.charAt(0)=='c') return true;
		return false;
	}
	
	public Calendar getCallendar() throws ParseException{
		Calendar ret = Calendar.getInstance();
		String sub = this.str.substring(5,11);
		ret.setTime(data_time_Format.parse(sub));
		return ret;
	}
	
	@Override
	public String toString() {
		return DirElement.class.getName()+":"
			    +" '"+this.str+"'"
				+" type: '"+this.str.charAt(0)+"'"
				+" date: '"+this.str.substring(5,11)+"'";
	}
	
	public String getUrl() {		
		return "http://www.nbp.pl/kursy/xml/"+this.str+".xml";
	}
	
	public void getData(
		PozycjaFilter filter,
		ArrayList<Pozycja> ret) {
		
		MyLogger.LOGGER.info("get data for "+this.toString() );
		
		InputStream inputXml = null;
		try
		{
		   String url = this.getUrl();
		   inputXml  = new URL(url).openConnection().getInputStream();
		   Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(inputXml);
		   
		   NodeList tabela_kursow = doc.getElementsByTagName("tabela_kursow");
		   if(tabela_kursow.getLength()!=1){
			   throw new Exception("url: '"+url+"' is missing 'tabela_kursow'");
		   }

		   Calendar data_publikacji = Calendar.getInstance();
		   {
			   DateFormat data_publikacji_format = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
			   Element tabela = (Element) tabela_kursow.item(0);
			   String dateStr = NetworkHelpers.getSubElementTextOrNull(tabela,"data_publikacji");
			   data_publikacji.setTime( data_publikacji_format.parse(dateStr) );
			   MyLogger.LOGGER.info("data_notowania dla:"+str+" = "+dateStr );
		   }
		   
		   if(filter.isdata_publikacjiOK(data_publikacji)){
			   NodeList nodeList = doc.getElementsByTagName("pozycja");
			   for (int i = 0; i < nodeList.getLength(); i++) {
			        Node node = nodeList.item(i);
	
			        if (node.getNodeType() == Node.ELEMENT_NODE) {
			        	try{
				        	Pozycja p =  new Pozycja((Element)node);
				        	if(filter.isOK(p)){
				        		MyLogger.LOGGER.info("NBPapiDirElement "+str+"-> "+p);
								ret.add(p);
							}else{
								MyLogger.LOGGER.info("NBPapiDirElement "+str+"-> "+p+"NO!");				
							}
				        	
			        	}catch(Exception e){
			        		e.printStackTrace();
			        	}
			        }
			    }
		   }else{
			   MyLogger.LOGGER.info("SKIP: wrong 'data_publikacji' ");
		   }
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
		finally
		{
		   try
		   {
		      if (inputXml != null)
		      inputXml.close();
		   }
		   catch (IOException ex)
		   {
			   ex.printStackTrace();
		   }
		}
	}
}