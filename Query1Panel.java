/**
 * @author Nishant Gahlawat-2015151,Akash Kumar Gautam-2015011
 */

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
/**Panel class for query 1*/
public class Query1Panel extends JPanel{
	private static final long serialVersionUID = 1L;
	private String[] search_type;/**< search by author or title*/
	JComboBox<String> searchby;/**< ComboBox for choice of author or title*/
	JLabel name_title,year_since,custom_range;/**< Label for indication of what is to be entered*/
	JRadioButton yearRadio,relRadio;/**< Radio buttons to set select sorting method*/
	JTextField sr_name_title,sr_year_since,sr_custom_from,sr_custom_till;/**< TextFields for input*/
	ButtonGroup bg;/**< ButtonGroup for the readio buttons*/
	GridBagConstraints gbc;/**< for the layout*/
	JButton SearchButton,ResetButton;/**< Buttons to search and reset*/
	Database DB;/**< Database for the search*/
	ResultPanel RP;/**< Result Panel to give the result to*/
	/**Constructor*/
	Query1Panel(Database SharedDB,ResultPanel SharedRP){
		super();
		DB=SharedDB;
		RP = SharedRP;
		this.setPreferredSize(new Dimension(300,390));
		this.setMinimumSize(new Dimension(300,390));
		this.setBorder(BorderFactory.createLineBorder(Color.black));
		this.setLayout(new GridBagLayout());
		gbc = new GridBagConstraints();
		gbc.weightx=1.0;gbc.weighty=0.2;
		gbc.anchor=GridBagConstraints.CENTER;
		addSearchBy();
		addNameTitle();
		addYearChoice();
		addRadioButtons();
		addButtons();
	}
	/**add Combo Box*/
	private void addSearchBy(){
		search_type=new String[]{"Search By","Author","Title"};
		searchby=new JComboBox<String>(search_type);
		gbc.gridx=0;gbc.gridy=0;
		gbc.gridheight=1;gbc.gridwidth=3;
		gbc.fill=GridBagConstraints.NONE;
		this.add(searchby,gbc);
	}
	/**Add label and textfield for search tag*/
	private void addNameTitle(){
		name_title = new JLabel("Name/Title-Tags: ");
		gbc.gridx=0;gbc.gridy=1;
		gbc.gridheight=1;gbc.gridwidth=1;
		gbc.fill=GridBagConstraints.HORIZONTAL;
		this.add(name_title,gbc);
		sr_name_title = new JTextField();
		gbc.gridx=1;gbc.gridy=1;
		gbc.gridheight=1;gbc.gridwidth=2;
		gbc.fill=GridBagConstraints.HORIZONTAL;
		this.add(sr_name_title,gbc);
	}
	/**add the year choices*/
	private void addYearChoice(){
		year_since = new JLabel("Since Year: ");
		gbc.gridx=0;gbc.gridy=2;
		gbc.gridheight=1;gbc.gridwidth=1;
		gbc.fill=GridBagConstraints.HORIZONTAL;
		this.add(year_since,gbc);
		
		custom_range = new JLabel("Custom Range: ");
		gbc.gridx=0;gbc.gridy=3;
		gbc.gridheight=1;gbc.gridwidth=1;
		gbc.fill=GridBagConstraints.HORIZONTAL;
		this.add(custom_range,gbc);
		
		sr_year_since = new JTextField();
		gbc.gridx=1;gbc.gridy=2;
		gbc.gridheight=1;gbc.gridwidth=2;
		gbc.fill=GridBagConstraints.HORIZONTAL;
		this.add(sr_year_since,gbc);
		
		sr_custom_from = new JTextField();
		sr_custom_from.setPreferredSize(new Dimension(50,20));
		sr_custom_from.setMinimumSize(new Dimension(50,20));
		gbc.gridx=1;gbc.gridy=3;
		gbc.gridheight=1;gbc.gridwidth=1;
		gbc.fill=GridBagConstraints.NONE;
		this.add(sr_custom_from,gbc);
		
		sr_custom_till = new JFormattedTextField();
		sr_custom_till.setPreferredSize(new Dimension(50,20));
		sr_custom_till.setMinimumSize(new Dimension(50,20));
		gbc.gridx=2;gbc.gridy=3;
		gbc.gridheight=1;gbc.gridwidth=1;
		gbc.fill=GridBagConstraints.NONE;
		this.add(sr_custom_till,gbc);
	}
	/**Add the radio buttons*/
	private void addRadioButtons() {
		yearRadio = new JRadioButton("Sort by Year.");
		relRadio = new JRadioButton("Sort by Relevance.");
		bg = new ButtonGroup();
		bg.add(yearRadio);bg.add(relRadio);
		bg.setSelected(yearRadio.getModel(),true);
		gbc.insets=new Insets(0,0,0,0);
		gbc.gridx=0;gbc.gridy=4;
		gbc.gridheight=1;gbc.gridwidth=3;
		gbc.fill=GridBagConstraints.HORIZONTAL;
		this.add(yearRadio,gbc);
		gbc.insets=new Insets(0,0,0,0);
		gbc.gridx=0;gbc.gridy=5;
		gbc.gridheight=1;gbc.gridwidth=3;
		gbc.fill=GridBagConstraints.HORIZONTAL;
		this.add(relRadio,gbc);
	}
	/**Add the search and reset buttons*/
	private void addButtons(){
		SearchButton = new JButton("Search");
		SearchButton.setPreferredSize(new Dimension(100,20));
		SearchButton.setMinimumSize(new Dimension(100,20));
		SearchButton.addActionListener(new SearchButtonActionListener());
		gbc.insets=new Insets(0,0,0,0);
		gbc.gridx=0;gbc.gridy=6;
		gbc.gridheight=1;gbc.gridwidth=1;
		gbc.fill=GridBagConstraints.NONE;
		this.add(SearchButton,gbc);
		ResetButton = new JButton("Reset");
		ResetButton.setPreferredSize(new Dimension(100,20));
		ResetButton.setMinimumSize(new Dimension(100,20));
		ResetButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				Q1Reset();
			}
		});
		gbc.insets=new Insets(0,0,0,0);
		gbc.gridx=2;gbc.gridy=6;
		gbc.gridheight=1;gbc.gridwidth=1;
		gbc.fill=GridBagConstraints.NONE;
		this.add(ResetButton,gbc);
	}
	/**Reset the fields of the panel*/
	public void Q1Reset(){
		RP.Reset();
		sr_name_title.setText("");
		sr_year_since.setText("");
		sr_custom_from.setText("");
		sr_custom_till.setText("");
		bg.setSelected(yearRadio.getModel(),true);
	}
	/**Action for the search button*/
	private class SearchButtonActionListener implements ActionListener{
		public void actionPerformed(ActionEvent arg0) {
			if(searchby.getSelectedItem().equals("Search By") || sr_name_title.getText().equals("")){
				JOptionPane.showMessageDialog(null, "Enter Search Method and/or Search Tag.");
			}
			else{
				int yearFrom=Integer.MIN_VALUE,yearTill=Integer.MAX_VALUE;
				try{
					if(sr_year_since.getText().equals("")){
						if(!sr_custom_from.getText().equals("") || !sr_custom_till.getText().equals("")){
							yearFrom = Integer.parseInt(sr_custom_from.getText());
							yearTill = Integer.parseInt(sr_custom_till.getText());
						}
					}
					else{
						yearFrom = Integer.parseInt(sr_year_since.getText());
					}
					if(!yearRadio.isSelected() && !relRadio.isSelected())
						JOptionPane.showMessageDialog(null, "Select a sorting method");
					else{
						ArrayList<Publication> Result;
						if(searchby.getSelectedItem().equals("Author"))
							Result = new GetAuthorSearchResult(sr_name_title.getText(),DB).getResult();
						else
							Result = new GetTitleSearchResult(sr_name_title.getText()).getResult();
						Result = RP.SortByYear(Result,yearFrom,yearTill);
						if(relRadio.isSelected()){
							Iterator<Publication> iter = Result.iterator();
							if(searchby.getSelectedItem().equals("Author"))
								while(iter.hasNext())
									iter.next().setRelevanceByAuthor(sr_name_title.getText());
							else
								while(iter.hasNext())
									iter.next().setRelevanceByTitle(sr_name_title.getText());
							Collections.sort(Result,new PublicationRelevanceComparator());
							Collections.reverse(Result);
						}
						else{
							Collections.sort(Result,new PublicationYearComparator());
							Collections.reverse(Result);
						}
						RP.addResultPublication(Result);
					}
				}
				catch(Exception e){
					e.printStackTrace();
					JOptionPane.showMessageDialog(null, "Invalid Input for year.");
				}
			}
		}
	}
}
