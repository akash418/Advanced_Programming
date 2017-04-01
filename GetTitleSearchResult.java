/**
 * @author Nishant Gahlawat-2015151,Akash Kumar Gautam-2015011
 */

import java.io.*;
import java.util.*;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

/**Class to parse through XML in search of right title*/
 
public class GetTitleSearchResult {
	/** Resulting Database*/
	private ResultDatabase RDB;
	/** Constructor and parser*/
	GetTitleSearchResult(String tag){
		try{
			 
			 File inputFile = new File("dblp.xml");
			 
			 SAXParserFactory factory = SAXParserFactory.newInstance();
			 SAXParser saxParser = factory.newSAXParser();
			 RDB = new TitleSearchParser(tag);
			 
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
	//! return the result
	public ArrayList<Publication> getResult(){
		return RDB.getResult();
	}
}
