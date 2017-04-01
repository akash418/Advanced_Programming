/**
 * @author Nishant Gahlawat-2015151,Akash Kumar Gautam-2015011
 */

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
/** The Frame containing all the panels*/

public class MainFrame extends JFrame{
	private static final long serialVersionUID = 1L;
	private JPanel MainPanel,QueryOptionPanel,QueryPanel; /**< Mainpanel distribution */
	private JLabel Banner;/**< banner label */
	ResultPanel RP = new ResultPanel();/**< Panel to display results*/
	Database DB;/**< database */
	Query1Panel Q1;/**< query1panel */
	Query2Panel Q2;/**<query2panel */
	Query3Panel Q3;/**< query3panel */
	/**Constructor*/
	MainFrame(Database SharedDB){
		DB=SharedDB;
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(1000,700);
		setUpMainPanel();
		Q1=new Query1Panel(DB,RP);
		Q2=new Query2Panel(DB,RP);
		Q3=new Query3Panel(DB,RP);
		this.add(MainPanel);
		this.setVisible(true);
	}
	/**setup the frame and the panels*/
	private void setUpMainPanel(){
		MainPanel = new JPanel();
		MainPanel.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		
		Banner = new JLabel("<html><b>DBLP Query Engine</b><html>");
		Banner.setHorizontalAlignment(JLabel.CENTER);
		Banner.setFont(new Font("Serif",Font.PLAIN,50));
		Banner.setPreferredSize(new Dimension(1000,100));
		Banner.setMinimumSize(new Dimension(1000,100));
		Banner.setBorder(BorderFactory.createLineBorder(Color.black));
		
		gbc.insets=new Insets(0,0,0,0);
		gbc.gridx=0;gbc.gridy=0;
		gbc.gridheight=1;gbc.gridwidth=2;
		gbc.weightx=1.0;gbc.weighty=1.0;
		gbc.anchor=GridBagConstraints.NORTH;
		gbc.fill=GridBagConstraints.HORIZONTAL;
		MainPanel.add(Banner,gbc);
		
		setUpQueryOptionPanel();
		gbc.insets=new Insets(0,0,0,0);
		gbc.gridx=0;gbc.gridy=1;
		gbc.gridheight=1;gbc.gridwidth=1;
		gbc.weightx=0.0;gbc.weighty=1.0;
		gbc.anchor=GridBagConstraints.NORTH;
		gbc.fill=GridBagConstraints.HORIZONTAL;
		MainPanel.add(QueryOptionPanel,gbc);
		
		setUpQueryPanel();
		gbc.insets=new Insets(0,0,0,0);
		gbc.gridx=0;gbc.gridy=2;
		gbc.gridheight=1;gbc.gridwidth=1;
		gbc.weightx=0.0;gbc.weighty=1.0;
		gbc.anchor=GridBagConstraints.NORTH;
		gbc.fill=GridBagConstraints.HORIZONTAL;
		MainPanel.add(QueryPanel,gbc);
		
		gbc.insets=new Insets(0,0,0,0);
		gbc.gridx=1;gbc.gridy=1;
		gbc.gridheight=2;gbc.gridwidth=1;
		gbc.weightx=1.0;gbc.weighty=1.0;
		gbc.anchor=GridBagConstraints.CENTER;
		gbc.fill=GridBagConstraints.BOTH;
		MainPanel.add(RP,gbc);
	}
	/**Set up panel to contain qury1,query2,query3 panels option drop down*/
	private void setUpQueryOptionPanel(){
		QueryOptionPanel = new JPanel();
		QueryOptionPanel.setPreferredSize(new Dimension(300,50));
		QueryOptionPanel.setMinimumSize(new Dimension(300,50));
		QueryOptionPanel.setBorder(BorderFactory.createLineBorder(Color.black));
		
		String[] queries_type=new String[]{"Queries","Query1","Query2","Query3"};
		JComboBox<String>queries=new JComboBox<String>(queries_type);
		QueryOptionPanel.add(queries);

		queries.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent event){
				JComboBox<String> combo=(JComboBox<String>) event.getSource();
				String item=(String)combo.getSelectedItem();
				if(item.equals("Queries")){
					MainFrame.this.setToQueryDefault();
				}
				else if(item.equals("Query1")){
					MainFrame.this.setToQuery1();
				}
				else if(item.equals("Query2")){
					MainFrame.this.setToQuery2();
				}
				else if(item.equals("Query3")){
					MainFrame.this.setToQuery3();
				}
			}
		});
	}
	/**Panel to contain the required panel*/
	private void setUpQueryPanel(){
		QueryPanel = new JPanel();
		QueryPanel.setPreferredSize(new Dimension(300,400));
		QueryPanel.setMinimumSize(new Dimension(300,400));
		QueryPanel.setBorder(BorderFactory.createLineBorder(Color.black));
	}
	/**Remove all optioned query - blank*/
	private void setToQueryDefault(){
		QueryPanel.removeAll();
		QueryPanel.revalidate();
		QueryPanel.repaint();
	}
	/**Set space for query 1*/
	private void setToQuery1(){
		Q1.Q1Reset();
		QueryPanel.removeAll();
		QueryPanel.add(Q1);
		QueryPanel.revalidate();
		QueryPanel.repaint();
	}
	/**set space for query 2*/
	private void setToQuery2(){
		QueryPanel.removeAll();
		QueryPanel.add(Q2);
		QueryPanel.revalidate();
		QueryPanel.repaint();
	}
	/**set space for query 3*/
	private void setToQuery3(){
		Q3.Q3Reset();
		QueryPanel.removeAll();
		QueryPanel.add(Q3);
		QueryPanel.revalidate();
		QueryPanel.repaint();
	}
}
