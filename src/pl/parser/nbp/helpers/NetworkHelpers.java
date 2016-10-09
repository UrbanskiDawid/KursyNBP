package pl.parser.nbp.helpers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

//TODO: rewrite (or just use JSOUP)
public class NetworkHelpers {

	public static ArrayList<String> readUrl(String url) throws IOException 
	{
		URLConnection yc = new URL(url).openConnection();
		BufferedReader in = new BufferedReader(new InputStreamReader( yc.getInputStream(), "UTF-8"));
		String inputLine;

		ArrayList<String> ret = new ArrayList<String>();
		while ((inputLine = in.readLine()) != null)    ret.add(inputLine);
		in.close();

		return ret;
	}
	

	public static String getSubElementTextOrNull(Element parent,String TagName)
	{
	  NodeList conditionList = parent.getElementsByTagName(TagName);
	  if(conditionList.getLength()!=1) return null;
	  
      Element child = (Element) conditionList.item(0);
      return child.getTextContent();
    }
}
