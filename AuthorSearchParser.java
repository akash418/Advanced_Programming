/**
 * @author Nishant Gahlawat-2015151,Akash Kumar Gautam-2015011
 */

import java.util.ArrayList;
import java.util.Iterator;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
/** Parser to search for the right author*/
public class AuthorSearchParser extends ResultDatabase{
	Publication P;/**< Current publication being read*/
	ArrayList<String> RelNames;/**< The names being searched for*/
	String conc="";/**< String to concatenated to*/
	/**boolean values for current tag*/
	private boolean bTitle = false, bAuthors = false,bPages = false,bYear = false;
	private boolean bVolume = false,bJournal = false,bURL = false;
	/**
	 * Constructor
	 */
	AuthorSearchParser(ArrayList<String> tag){
		RelNames=tag;
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
	 * see if the current publciation has the right title
	 */
	private boolean RightTitle(Publication curP){
		Iterator<String> names = curP.getAuthors().iterator();
		while(names.hasNext()){
			if(RelNames.contains(names.next()))
				return true;
		}
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
