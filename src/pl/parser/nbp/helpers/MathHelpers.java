package pl.parser.nbp.helpers;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Locale;

public class MathHelpers {

	//======================================================================================================
	private static DecimalFormat df;
	static {
		df = new DecimalFormat("#,###,##0.00");
		DecimalFormatSymbols otherSymbols = new DecimalFormatSymbols(Locale.ENGLISH);
		otherSymbols.setDecimalSeparator(',');
		otherSymbols.setGroupingSeparator('\'');
		df.setDecimalFormatSymbols(otherSymbols);
	}
	
	public static double string2Double(String str) throws ParseException{
		return df.parse(str).doubleValue();
	}
	
	//======================================================================================================
	private static DecimalFormat doulble_prec4_comma;
	static {
		DecimalFormatSymbols formatSymbols = new DecimalFormatSymbols(Locale.GERMAN);
		doulble_prec4_comma = new DecimalFormat("#.####",formatSymbols);
	}
	
	public static String double2String(double d){
	  return doulble_prec4_comma.format(d);
	}
	
	
	//======================================================================================================	
    public static double getMean(ArrayList<Double> data) {
        double sum = 0.0;
        for(double a : data)
            sum += a;
        return sum/data.size();
    }

	//======================================================================================================
    public static double getVariance(ArrayList<Double> data) {
        double mean = getMean(data);
        double temp = 0;
        for(double a :data)
            temp += (a-mean)*(a-mean);
        return temp/data.size();
    }

	//======================================================================================================
    public static double getStdDev(ArrayList<Double> data) {
        return Math.sqrt(getVariance(data));
    }
}
