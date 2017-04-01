/**
 * @author Nishant Gahlawat-2015151,Akash Kumar Gautam-2015011
 */

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
/**Class to parse while searching for the right title*/
public class TitleSearchParser extends ResultDatabase{
	/** The current publication*/
	Publication P;
	/** The title tag to be searched for*/
	String titleTag;
	/** String to concatenate*/
	String conc="";
	/** boolean values for the current tag*/
	private boolean bTitle = false, bAuthors = false,bPages = false,bYear = false;
	/** boolean values for the current tag*/
	private boolean bVolume = false,bJournal = false,bURL = false;
	/**Constructor*/
	TitleSearchParser(String tag){
		titleTag=tag;
		ResultDB = new ArrayList<Publication>();
	}
	/**
	 * (non-Javadoc)
	 * @see org.xml.sax.helpers.DefaultHandler#startElement(java.lang.String, java.lang.String, java.lang.String, org.xml.sax.Attributes)
	 */
	public void startElement(String uri,String localName, String qName,Attributes attributes) throws SAXException{
		if(qName.equalsIgnoreCase("article") || qName.equalsIgnoreCase("proceedings") || qName.equalsIgnoreCase("inproceedings") || qName.equalsIgnoreCase("incollection")
				|| qName.equalsIgnoreCase("book") || qName.equalsIgnoreCase("phdthesis") || qName.equalsIgnoreCase("mastersthesis")){
			P = new Publication();
		}
		else if(qName.equalsIgnoreCase("title")){
			bTitle = true;
		}
		else if(qName.equalsIgnoreCase("author")){
			bAuthors = true;
		}
		else if(qName.equalsIgnoreCase("pages")){
			bPages = true;
		}
		else if(qName.equalsIgnoreCase("year")){
			bYear = true;
		}
		else if(qName.equalsIgnoreCase("volume")){
			bVolume = true;
		}
		else if(qName.equalsIgnoreCase("journal") || qName.equalsIgnoreCase("booktitle")){
			bJournal = true;
		}
		else if(qName.equalsIgnoreCase("url")){
			bURL = true;
		}
	}
	/**
	 * (non-Javadoc)
	 * @see org.xml.sax.helpers.DefaultHandler#endElement(java.lang.String, java.lang.String, java.lang.String)
	 */
	public void endElement(String uri, String localName, String qName) throws SAXException {
		if(qName.equalsIgnoreCase("article") || qName.equalsIgnoreCase("proceedings") || qName.equalsIgnoreCase("inproceedings") || qName.equalsIgnoreCase("incollection")
				|| qName.equalsIgnoreCase("book") || qName.equalsIgnoreCase("phdthesis") || qName.equalsIgnoreCase("mastersthesis")){
			if(RightTitle(P)){
				ResultDB.add(P);
			}
			P=null;
		}
		else if(qName.equalsIgnoreCase("author")){
			if(P!=null){
				P.addAuthor(conc);
			}
			conc="";
			bAuthors = false;
		}
		else if(qName.equalsIgnoreCase("title")){
			if(P!=null){
				P.setTitle(conc);
			}
			conc="";
			bTitle = false;
		}
	}
	/**
	 * See if the current title is fits the search tag
	 */
	private boolean RightTitle(Publication curP){
		String terms[] = titleTag.split(" ");
		String curTitle = curP.getTitle();
		int i;
		int matches=0;
		for(i=0;i<terms.length;i++){
			Matcher m = Pattern.compile(terms[i].toLowerCase()).matcher(curTitle.toLowerCase());
			if(m.find())
				matches++;
		}
		if(matches==terms.length)
			return true;
		return false;
	}
	/**
	 * (non-Javadoc)
	 * @see org.xml.sax.helpers.DefaultHandler#characters(char[], int, int)
	 */
	public void characters(char ch[], int start,int length) throws SAXException {
		if(bTitle){ 
			conc=conc+new String(ch,start,length);
		}
		else if(bAuthors){
			conc=conc+new String(ch,start,length);
		}
		else if(bPages){
			if(P!=null){
				P.setPages(new String(ch,start,length));
			}
			bPages = false;
		}
		else if(bYear){
			if(P!=null){
				P.setYear(Integer.parseInt(new String(ch,start,length)));
			}
			bYear = false;
		}
		else if(bVolume){
			if(P!=null){
				P.setVolume(new String(ch,start,length));
			}
			bVolume = false;
		}
		else if(bJournal){
			if(P!=null){
				P.setJournal(new String(ch,start,length));
			}
			bJournal = false;
		}
		else if(bURL){
			if(P!=null){
				P.setURL(new String(ch,start,length));
			}
			bURL = false;
		}
	}
}
