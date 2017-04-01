/**
 * @author Nishant Gahlawat-2015151,Akash Kumar Gautam-2015011
 */

import java.util.Comparator;

/**Class to sort the publications according to relevance*/
 
public class PublicationRelevanceComparator implements Comparator<Publication> {
	/**
	 * (non-Javadoc) Comparing the relevancy
	 * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
	 */
	public int compare(Publication arg0, Publication arg1) {
		return arg0.compareRel(arg1);
	}

}
