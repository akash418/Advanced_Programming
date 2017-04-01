/**
 * @author Nishant Gahlawat-2015151,Akash Kumar Gautam-2015011
 */

import java.awt.*;
import java.awt.event.*;
import java.util.*;

import javax.swing.*;

/** class responsible for creating query2 panel*/

public class Query2Panel extends JPanel{
	private static final long serialVersionUID = 1L;	
	GridBagConstraints gbc;/**< For the layout of the panel*/
	JButton SearchButton,ResetButton;/**< Buttons for Search and Reset*/
	JLabel sr_noOfPub;/**< Labels for indicating what the field is for*/
	JTextField noOfPub;/**< TextField*/;
	ResultPanel RP;/**< The ResultPanel which gets the result*/
	Database DB;/**< The Database which result is got from*/
	/**Constructor*/
	Query2Panel(Database SharedDB,ResultPanel SharedRP){
		super();
		RP = SharedRP;
		DB=SharedDB;
		this.setPreferredSize(new Dimension(300,390));
		this.setMinimumSize(new Dimension(300,390));
		this.setBorder(BorderFactory.createLineBorder(Color.black));
		this.setLayout(new GridBagLayout());
		gbc = new GridBagConstraints();
		addFillPublication();
		addButtons();
	}
	/**Add the fields and labels for panel*/
	private void addFillPublication(){
		sr_noOfPub = new JLabel("Enter No of Publications: ");
		gbc.gridx=0;gbc.gridy=0;
		gbc.gridheight=1;gbc.gridwidth=1;
		gbc.weightx=1.0;gbc.weighty=0.2;
		gbc.anchor=GridBagConstraints.CENTER;
		gbc.fill=GridBagConstraints.HORIZONTAL;
		this.add(sr_noOfPub,gbc);
		
		noOfPub = new JTextField("");
		gbc.gridx=1;gbc.gridy=0;
		gbc.gridheight=1;gbc.gridwidth=2;
		gbc.weightx=1.0;gbc.weighty=0.2;
		gbc.anchor=GridBagConstraints.CENTER;
		gbc.fill=GridBagConstraints.HORIZONTAL;
		this.add(noOfPub,gbc);
	}
	/**Add the search and reset buttons to the panel*/
	private void addButtons(){
		SearchButton = new JButton("Search");
		SearchButton.setPreferredSize(new Dimension(100,20));
		SearchButton.setMinimumSize(new Dimension(100,20));
		SearchButton.addActionListener(new SearchButtonActionListener());
		ResetButton = new JButton("Reset");
		ResetButton.setPreferredSize(new Dimension(100,20));
		ResetButton.setMinimumSize(new Dimension(100,20));
		ResetButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				Q2Reset();
			}
		});
		gbc.insets=new Insets(0,0,0,0);
		gbc.gridx=0;gbc.gridy=1;
		gbc.gridheight=1;gbc.gridwidth=1;
		gbc.weightx=1.0;gbc.weighty=0.2;
		gbc.anchor=GridBagConstraints.NORTH;
		gbc.fill=GridBagConstraints.NONE;
		this.add(SearchButton,gbc);
		
		gbc.insets=new Insets(0,0,0,0);
		gbc.gridx=1;gbc.gridy=1;
		gbc.gridheight=1;gbc.gridwidth=1;
		gbc.weightx=1.0;gbc.weighty=0.2;
		gbc.anchor=GridBagConstraints.NORTH;
		gbc.fill=GridBagConstraints.NONE;
		this.add(ResetButton,gbc);
	}
	/**Reset the panel*/
	public void Q2Reset(){
		RP.Reset();
		noOfPub.setText("");
	}
	/**Action for the search button*/
	private class SearchButtonActionListener implements ActionListener{
		public void actionPerformed(ActionEvent arg0) {
			if(noOfPub.getText().equals("")){
				JOptionPane.showMessageDialog(null, "Enter No. of Publications.");
			}
			else{
				try{
					ArrayList<Person> Result = DB.SearchMoreK(Integer.parseInt(noOfPub.getText()));
					if(Result!=null)
						RP.addResultPerson(Result);
				}
				catch(Exception e){
					JOptionPane.showMessageDialog(null, "Invalid Input.");
				}
			}
		}
	}
}
