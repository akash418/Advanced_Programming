

/**
 * @author Nishant Gahlawat-2015151,Akash Kumar Gautam-2015011
 */
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import javax.swing.*;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;


 /**Panel for the prediction query*/
 
public class Query3Panel extends JPanel  {
	private static final long serialVersionUID = 1L;
	GridBagConstraints gbc;/**< gbc to set the layout*/
	JTextField author_name;	/**< TextField for the author to be searched for*/
	JTextField year_of_prediction;/**< TextField for the year to be predicted for*/
	JButton SearchButton;/**< Button to initiate search*/
	JButton ResetButton;/**< Button to reset the fields*/
	ResultPanel RP;/**< The ResultPanel that result goes to*/
	Database DB;/**< The Database to get the exact names from*/
	/**Constructor for Query3Panel*/
	Query3Panel(Database SharedDB,ResultPanel SharedRP){
		RP=SharedRP;
		DB=SharedDB;
		this.setPreferredSize(new Dimension(300,390));
		this.setMinimumSize(new Dimension(300,390));
		this.setBorder(BorderFactory.createLineBorder(Color.black));
		this.setLayout(new GridBagLayout());
		gbc = new GridBagConstraints();
		gbc.weightx=1.0;gbc.weighty=0.2;
		gbc.anchor=GridBagConstraints.CENTER;
		addButtons();
	}
	/**Add the Search and Reset Buttons*/
	private void addButtons(){
		JLabel Enter_author = new JLabel("Enter Full Author Name: ");
		gbc.gridx=0;gbc.gridy=0;
		gbc.gridheight=1;gbc.gridwidth=1;
		gbc.weightx=1.0;gbc.weighty=0.2;
		gbc.anchor=GridBagConstraints.CENTER;
		gbc.fill=GridBagConstraints.HORIZONTAL;
		this.add(Enter_author,gbc);
		
		author_name = new JTextField("");
		gbc.gridx=1;gbc.gridy=0;
		gbc.gridheight=1;gbc.gridwidth=2;
		gbc.weightx=1.0;gbc.weighty=0.2;
		gbc.anchor=GridBagConstraints.CENTER;
		gbc.fill=GridBagConstraints.HORIZONTAL;
		this.add(author_name,gbc);	
		
		JLabel Enter_years=new JLabel("Enter year");
		gbc.gridx=0;gbc.gridy=1;
		gbc.gridheight=1;gbc.gridwidth=1;
		gbc.weightx=1.0;gbc.weighty=0.2;
		gbc.anchor=GridBagConstraints.NORTH;
		gbc.fill=GridBagConstraints.HORIZONTAL;
		this.add(Enter_years, gbc);
		
		year_of_prediction=new JTextField("");
		gbc.gridx=1;gbc.gridy=1;
		gbc.weightx=1;gbc.weighty=2;
		gbc.gridheight=1;gbc.gridwidth=2;
		gbc.anchor=GridBagConstraints.NORTH;
		gbc.fill=GridBagConstraints.HORIZONTAL;
		this.add(year_of_prediction, gbc);
		
		SearchButton = new JButton("Search");
		SearchButton.setPreferredSize(new Dimension(100,20));
		SearchButton.setMinimumSize(new Dimension(100,20));
		SearchButton.addActionListener(new SearchButtonActionListener());
		ResetButton = new JButton("Reset");
		ResetButton.setPreferredSize(new Dimension(100,20));
		ResetButton.setMinimumSize(new Dimension(100,20));
		ResetButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				Q3Reset();
			}
		});
		gbc.insets=new Insets(0,0,0,0);
		gbc.gridx=0;gbc.gridy=2;
		gbc.gridheight=1;gbc.gridwidth=1;
		gbc.weightx=1.0;gbc.weighty=0.2;
		gbc.anchor=GridBagConstraints.NORTH;
		gbc.fill=GridBagConstraints.NONE;
		this.add(SearchButton,gbc);
		
		gbc.insets=new Insets(0,0,0,0);
		gbc.gridx=1;gbc.gridy=2;
		gbc.gridheight=1;gbc.gridwidth=1;
		gbc.weightx=1.0;gbc.weighty=0.2;
		gbc.anchor=GridBagConstraints.NORTH;
		gbc.fill=GridBagConstraints.NONE;
		this.add(ResetButton,gbc);
	}
	/**Reset the fields*/
	public void Q3Reset(){
		author_name.setText("");
		year_of_prediction.setText("");
		RP.Reset();
		
	}
	/**The action for the Search Button*/
	private class SearchButtonActionListener implements ActionListener{
		public void actionPerformed(ActionEvent arg0) {
			if(author_name.getText().equals("") || year_of_prediction.getText().equals("")){
				JOptionPane.showMessageDialog(null, "Enter No. of Publications.");
			}
			else{
				ArrayList<String> relNames = DB.getExactNames(author_name.getText());
				if(relNames==null){
					JOptionPane.showMessageDialog(null, "No such author.");
				}
				else{
					ArrayList<Publication> pubs;
					ResultDatabase RDB;
					try{
						File inputFile = new File("dblp.xml");
						 
						SAXParserFactory factory = SAXParserFactory.newInstance();
						SAXParser saxParser = factory.newSAXParser();
						RDB = new AuthorSearchParser(relNames);
						 
						long t=System.currentTimeMillis();
						System.out.println("Start: "+t);
						 
						saxParser.parse(inputFile,RDB);
						
						long t2=System.currentTimeMillis();
						System.out.println("End: "+t2);
						System.out.println("Time Taken: "+(t2-t)+"ms");
						pubs=RDB.getResult();
						Predict(pubs,Integer.parseInt(year_of_prediction.getText()));
					 }
					 catch(Exception e){
						 e.printStackTrace();
					 }
				}
			}
		}
	}
	/**Setting up the field for the prediction*/
	private void Predict(ArrayList<Publication> pubs,int yearToPredict){
		Iterator<Publication> pubIter = pubs.iterator();
		float actual=0;
		HashMap<Integer,Integer> YearToNo = new HashMap<Integer,Integer>();
		while(pubIter.hasNext()){
			Publication temp = pubIter.next();
			if(temp.getYear()<yearToPredict){
				if(!YearToNo.containsKey(temp.getYear())){
					YearToNo.put(temp.getYear(),1);
				}
				else{
					YearToNo.put(temp.getYear(),YearToNo.get(temp.getYear())+1);
				}
			}
			else if(temp.getYear()==yearToPredict)
				actual++;
		}
		float pred=predictNext(YearToNo,yearToPredict);
		JOptionPane.showMessageDialog(null, "Predicted: "+pred+"\nActual: "+actual+"\nDeviation: "+((pred-actual)/actual)*100+"%");
	}
	/**Predicting the next y for x according to data xToy*/ 
	private float predictNext(HashMap<Integer,Integer> xToy, int x){
		Iterator<Integer> Xs = xToy.keySet().iterator();
		float xMean=0,yMean=0;
		while(Xs.hasNext()){
			Integer xi = Xs.next();
			xMean = xMean+xi;yMean = yMean+xToy.get(xi);
		}
		xMean = xMean/xToy.size();
		yMean = yMean/xToy.size();
		float xySig=0,x2Sig=0;
		Xs = xToy.keySet().iterator();
		while(Xs.hasNext()){
			Integer xi = Xs.next();
			xySig = xySig + (xToy.get(xi)-yMean)*(xi-xMean);
			x2Sig = x2Sig + (xi-xMean)*(xi-xMean);
		}
		float b = xySig/x2Sig;
		float a = yMean-b*xMean;
		return (a+b*x);
	}
}
