package pl.parser.nbp;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class PrzedzialDat{
	
	Calendar from;
	Calendar to;
	
	static SimpleDateFormat printFormat = new SimpleDateFormat("yyyy-MM-dd");
	
	public PrzedzialDat(Calendar from, Calendar to) {
		Date now = new Date();

		if( from.after(now)
				||
		    to.after(now)
		        ||
		    from.after(to) ) { 
				throw new IllegalArgumentException();
			}

		this.from = from;
		this.to   = to;
	}
	
	@Override
	public String toString() {
      return "from:"+printFormat.format(from.getTime())+" to:"+printFormat.format(to.getTime());
	}

	//kursy z daty początkowej i końcowej również mają być brane pod uwagę
	public boolean isInRange(Calendar c){
		if ( c.before(from) && !isSameDay(c,from)) return false;
		if ( c.after (to  ) && !isSameDay(c,to  )) return false;
		return true;
	}
	
	public static boolean isSameDay(Calendar a,Calendar b) {
		return
		( a.get(Calendar.YEAR) == b.get(Calendar.YEAR)
		  &&
		  a.get(Calendar.DAY_OF_MONTH) == b.get(Calendar.DAY_OF_MONTH)
		  &&
		  a.get(Calendar.MONTH) == b.get(Calendar.MONTH) );

	}
}
