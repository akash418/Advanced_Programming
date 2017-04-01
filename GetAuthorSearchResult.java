/**
 * @author Nishant Gahlawat-2015151,Akash Kumar Gautam-2015011
 */

import java.io.File;
import java.util.ArrayList;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
/** Class to parse through the XMl ins earch of right author*/
public class GetAuthorSearchResult {
	/** Resulting database*/
	private ResultDatabase RDB;
	/** Constructor and parser*/
	GetAuthorSearchResult(String tag,Database DB){
		try{
			ArrayList<String> RelevantNames = DB.getRelevantNames(tag);
			
			File inputFile = new File("dblp.xml");
			 
			SAXParserFactory factory = SAXParserFactory.newInstance();
			SAXParser saxParser = factory.newSAXParser();
			RDB = new AuthorSearchParser(RelevantNames);
			 
			long t=System.currentTimeMillis();
			System.out.println("Start: "+t);
			 
			saxParser.parse(inputFile,RDB);
			
			long t2=System.currentTimeMillis();
			System.out.println("End: "+t2);
			System.out.println("Time Taken: "+(t2-t)+"ms");
		 }
		 catch(Exception e){
			 e.printStackTrace();
		 }
	}
	/** REturn the result*/
	public ArrayList<Publication> getResult(){
		return RDB.getResult();
	}
}
