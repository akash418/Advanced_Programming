/**
 * @author Nishant Gahlawat-2015151,Akash Kumar Gautam-2015011
 */

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.InputSource;

 /** Class to start off the program*/
 
public class Main {
	
	public static void main(String[] args){
		try{
			 BufferedReader bf = new BufferedReader(new InputStreamReader(new FileInputStream(new File("dblp.xml")),"ISO-8859-1"));
			 
			 SAXParserFactory factory = SAXParserFactory.newInstance();
			 SAXParser saxParser = factory.newSAXParser();
			 Database DB = new DBLP_Parser();
			 
			 InputSource is = new InputSource(bf);
			 
			 long t=System.currentTimeMillis();
			 System.out.println("Start: "+t);
			 
			 saxParser.parse(is,DB);
			 
			 long t2=System.currentTimeMillis();
			 System.out.println("End: "+t2);
			 System.out.println("Time Taken: "+(t2-t)+"ms");
			 
			 new MainFrame(DB);
		 }
		 catch(Exception e){
			 e.printStackTrace();
		 }
	 }

}
