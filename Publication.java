/**
 * @author Nishant Gahlawat-2015151,Akash Kumar Gautam-2015011
 */

import java.util.*;
import java.util.regex.*;

 /**Class for the publications in the database, notably the resulted ones*/
 
public class Publication{
	private String Title = "---";/**< Title of publication*/
	private ArrayList<String> Authors = new ArrayList<String>();/**< Authors of publication*/
	private Integer year;/**< year of publication*/
	private String pages = "---";/**< pages in the publication*/
	private String volume = "---";/**< volume of the published journal/book */
	private String journal = "---";/**< journal/bbok realesed in*/
	private String url = "---";/**< url for the publication*/
	private int ExRel = 0,ExSpRel=0;/**< Relevance according to the tag*/
	/**
	 * Setters
	 */
	public void setTitle(String _title){
		Title=_title;
	}
	public void addAuthor(String _author){
		Authors.add(_author);
	}
	public void setYear (int _year){
		year=new Integer(_year);
	}
	public void setPages(String _pages){
		pages=_pages;
	}
	public void setVolume(String _volume){
		volume=_volume;
	}
	public void setJournal(String _journal){
		journal=_journal;
	}
	public void setURL(String _url){
		url=_url;
	}
	/**
	 * Get the authors of the publication
	 */
	public ArrayList<String> getAuthors()
	{
		return Authors;
	}
	/**
	 * Getters
	 */
	public String getTitle(){
		return this.Title;
	}
	public Integer getYear(){
		return this.year;
	}
	public String[] getStringArray(long i){
		String[] str ={""+i+"",Authors.toString(),Title,pages,""+year+"",volume,journal,url};
		return str;
	}
	
	 /**For comparision and sorting*/
	 
	public int compareYear(Publication arg0) {
		if(this.year==null)
			return Integer.MAX_VALUE;
		else if(arg0.year==null)
			return Integer.MIN_VALUE;
		else
			return (this.year-arg0.year);
	}
	
	/**setting the relevance according to tag(Auhtors)*/
	 
	public void setRelevanceByAuthor(String tag){
		String terms[] = tag.split(" ");
		int exact=0;int exactSplit=0;
		Iterator<String> AutIter = Authors.iterator();
		while(AutIter.hasNext()){
			String curName = AutIter.next();
			Matcher m = Pattern.compile("\\b"+tag.toLowerCase()+"\\b").matcher(curName.toLowerCase());
			if(m.find()){
				exact++;
			}
			else{
				int i;
				for(i=0;i<terms.length;i++){
					m = Pattern.compile("\\b"+terms[i].toLowerCase()+"\\b").matcher(curName.toLowerCase());
					while(m.find())
						exactSplit++;
				}
			}
		}
		ExRel=exact;ExSpRel=exactSplit;
	}
	
	 /**setting the relevance according to tag(Title)*/
	 
	public void setRelevanceByTitle(String tag){
		String terms[] = tag.split(" ");
		int exact=0;int exactSplit=0;
		if(Title.toLowerCase().contains(tag.toLowerCase())){
				exact++;
		}
		else{
			int i;
			for(i=0;i<terms.length;i++){
				Matcher m = Pattern.compile("\\b"+terms[i].toLowerCase()+"\\b").matcher(Title.toLowerCase());
				while(m.find())
					exactSplit++;
			}
		}
		ExRel=exact;ExSpRel=exactSplit;
	}
	
	/**For comparision and sorting*/
	 
	public int compareRel(Publication arg0){
		if(this.ExRel>arg0.ExRel)
			return 1;
		else if(this.ExRel<arg0.ExRel)
			return -1;
		else{
			if(this.ExSpRel>arg0.ExSpRel)
				return 1;
			else if(this.ExSpRel<arg0.ExSpRel)
				return -1;
			else
				return 0;
		}
	}
}
