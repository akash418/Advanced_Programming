/**
 * @author Nishant Gahlawat-2015151,Akash Kumar Gautam-2015011
 */

import java.util.ArrayList;
import java.util.Iterator;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

 /**Class for the initial parsing of the XML Document, building up the entity resolution*/
 
public class DBLP_Parser extends Database{
	private Person newAuthor;/**< A new author generated from <www> tag*/
	private boolean bAuthors = false;/**< Condition for if a new author is available*/
	private ArrayList<String> pubAuthors;/**< List of authors for current publication*/
	String aut = "";/**< Current author String
	/**
	 * Start a tag element
	 * (non-Javadoc)
	 * @see org.xml.sax.helpers.DefaultHandler#startElement(java.lang.String, java.lang.String, java.lang.String, org.xml.sax.Attributes)
	 */
	public void startElement(String uri,String localName, String qName,Attributes attributes) throws SAXException{
		if(qName.equalsIgnoreCase("article") || qName.equalsIgnoreCase("proceedings") || qName.equalsIgnoreCase("inproceedings") || qName.equalsIgnoreCase("incollection")
				|| qName.equalsIgnoreCase("book") || qName.equalsIgnoreCase("phdthesis") || qName.equalsIgnoreCase("mastersthesis")){
			pubAuthors = new ArrayList<String>();
		}
		else if(qName.equalsIgnoreCase("www")){
			newAuthor = new Person();
		}
		else if(qName.equalsIgnoreCase("author")){
			bAuthors = true;
		}
	}
	/**
	 * End a tag element
	 * (non-Javadoc)
	 * @see org.xml.sax.helpers.DefaultHandler#endElement(java.lang.String, java.lang.String, java.lang.String)
	 */
	public void endElement(String uri, String localName, String qName) throws SAXException {
		if(qName.equalsIgnoreCase("article") || qName.equalsIgnoreCase("proceedings") || qName.equalsIgnoreCase("inproceedings") || qName.equalsIgnoreCase("incollection")
				|| qName.equalsIgnoreCase("book") || qName.equalsIgnoreCase("phdthesis") || qName.equalsIgnoreCase("mastersthesis")){
			if(pubAuthors.size()!=0){
				Iterator<String> authors = pubAuthors.iterator();
				while(authors.hasNext()){
					String temp = authors.next();
					if(autToNo.containsKey(temp)){
						autToNo.put(temp, autToNo.get(temp)+1);
					}
					else{
						autToNo.put(temp, 1);
					}
				}
			}
			pubAuthors=null;
		}
		else if(qName.equalsIgnoreCase("www")){
			if(newAuthor.size()!=0){
				this.Persons.add(newAuthor);
			}
			newAuthor=null;
		}
		else if(qName.equalsIgnoreCase("author")){
			if(newAuthor!=null){
				newAuthor.add(aut);
			}
			else if(pubAuthors!=null){
				pubAuthors.add(aut);
			}
			aut="";
			bAuthors = false;
		}
	}
	/**
	 * read characters in tag
	 * (non-Javadoc)
	 * @see org.xml.sax.helpers.DefaultHandler#characters(char[], int, int)
	 */
	public void characters(char ch[], int start,int length) throws SAXException {
		if(bAuthors){
			aut = aut + new String(ch,start,length);
		}
	}
}
