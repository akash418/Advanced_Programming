/**
 * @author Nishant Gahlawat-2015151,Akash Kumar Gautam-2015011
 */

import java.util.Comparator;

 /**Comparator class to sort publicaitons in ascending year*/

public class PublicationYearComparator implements Comparator<Publication>{
	/**
	 * (non-Javadoc) Comparing the year
	 * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
	 */
	public int compare(Publication arg0, Publication arg1) {
		if(arg0.compareYear(arg1)<0)
			return -1;
		else if(arg0.compareYear(arg1)>0)
			return 1;
		else
			return 0;
	}
	
}
