package pl.parser.nbp.helpers;

import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import pl.parser.nbp.MainClass;

public class MyLogger {
	
	public static final Logger LOGGER = Logger.getLogger( "MainClass", null );
    private static FileHandler fh;  

    static {
    	try {
    		
    	System.setProperty("java.util.logging.SimpleFormatter.format",
    	            "%1$tF %1$tT %4$s %2$s %5$s%6$s%n");
    	
        fh = new FileHandler(MainClass.class.getName()+".log");
        fh.setFormatter(new SimpleFormatter());
        LOGGER.addHandler(fh);
        LOGGER.setUseParentHandlers(false);        

	    } catch (Exception e) {  
	        e.printStackTrace();
	    }
    }
}


