package it.calverDesktopLAT.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import it.calverDesktopLAT.bo.GestioneDB;
import it.calverDesktopLAT.utl.Costanti;

public class PannelloDB extends JPanel {
	
	JComboBox<String> listaTabelle;
	JButton btnExplore;
    DefaultTableModel model;
    JTable jTable;
    JTextField jtfFilter = new JTextField();
    TableRowSorter<TableModel> rowSorter;
    JScrollPane scrollPane=null ;
    JPanel p;
    
	public PannelloDB()
	{
		p=this;
		setLayout(null);
		setBackground(Color.white);
		costruisciPannello();
	
	}

	private void costruisciPannello() {
		
		try
		{
		JLabel selezionaTabelle = new JLabel("Seleziona Tabelle");
		selezionaTabelle.setBounds(10,10,150,30);
		selezionaTabelle.setFont(new Font(Costanti.FONT, Font.BOLD,14));
		
		listaTabelle= new JComboBox<>(GestioneDB.getDatiTabelle());
		
		listaTabelle.setBounds(170, 10,300,25);
		
		btnExplore = new JButton("Explore");
		btnExplore.setBounds(480, 10,120, 25);
		
		this.add(selezionaTabelle);
		this.add(listaTabelle);
		this.add(btnExplore);
	
		
		
		btnExplore.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
			
				    int l=getWidth();
		            int h=getHeight();
		            if(scrollPane!=null)
		            {
		            	p.remove(scrollPane);
		            }
		            
		            try {
				
					String[] listaColonne=GestioneDB.getListaColonne(listaTabelle.getSelectedItem().toString());
				
					Object[][] data =GestioneDB.getPlayLoad(listaColonne.length,listaTabelle.getSelectedItem().toString());
					
					    model = new DefaultTableModel(data, listaColonne);
					  
					    jTable = new JTable(model);
					

					    rowSorter = new TableRowSorter<>(jTable.getModel());


			            jTable.setRowSorter(rowSorter);
	
			        	scrollPane = new JScrollPane();
						scrollPane.setBounds(10, 40,l-10 , h-100);
						scrollPane.getViewport().add(jTable);
						
						
						add(scrollPane);
						
			            
			       
			        
					JLabel ricerca = new JLabel("Ricerca");
					ricerca.setBounds(10,h-30, 100,25);
					jtfFilter.setBounds(120, h-30,200,25);
		            add(ricerca);
		            add(jtfFilter, BorderLayout.CENTER);
		            
		            
		            scrollPane.repaint();
		            jTable.repaint();   
		           
		            p.validate();
			        p.repaint();    
			       
			        updateUI();
	    
				} catch (Exception e) {
					PannelloConsole.printException(e);
					e.printStackTrace();
				}
			}
			
		});
		
	       jtfFilter.getDocument().addDocumentListener(new DocumentListener(){

	            @Override
	            public void insertUpdate(DocumentEvent e) {
	                String text = jtfFilter.getText();

	                if (text.trim().length() == 0) {
	                    rowSorter.setRowFilter(null);
	                } else {
	                    rowSorter.setRowFilter(RowFilter.regexFilter("(?i)" + text));
	                }
	            }

	            @Override
	            public void removeUpdate(DocumentEvent e) {
	                String text = jtfFilter.getText();

	                if (text.trim().length() == 0) {
	                    rowSorter.setRowFilter(null);
	                } else {
	                    rowSorter.setRowFilter(RowFilter.regexFilter("(?i)" + text));
	                }
	            }

	            @Override
	            public void changedUpdate(DocumentEvent e) {
	                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	            }

	            
	        });
	       
	       
	
		}catch (Exception e) {
			PannelloConsole.printException(e);
			e.printStackTrace();
		}
	}

	   public class MyCellRenderer extends javax.swing.table.DefaultTableCellRenderer {

	        public java.awt.Component getTableCellRendererComponent(javax.swing.JTable table, java.lang.Object value, boolean isSelected, boolean hasFocus, int row, int column) {
	            final java.awt.Component cellComponent = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

	            Object val = table.getValueAt(row, 9);
	            String sval = val.toString();
	            System.out.println(sval);
	         
	            boolean flag=true;
	            
	            try 
	            {
	            	if(sval.equals("0"))
	            	{
	            		flag=false;
	            	}
	            	else
	            	{
	            		int i =Integer.parseInt(sval.substring(0,2));
	            		if(i>3 && i<10)
	            		{
	            			flag=true;
	            		}
	            		else
	            		{
	            			flag=false;
	            		}
	            	}
				} catch (Exception e) 
				{
					// TODO: handle exception
				}
	           
	           if(flag)
	           { 
	            if (row % 2 == 0) 
	            {
	                cellComponent.setForeground(Color.black);
	                cellComponent.setBackground(Color.white);

	            }
	            else
	            {
	            	cellComponent.setForeground(Color.black);
	                cellComponent.setBackground(new Color(224,224,224));
	            }
	           }
	           else
	           {
	        	   cellComponent.setForeground(Color.black);
	                cellComponent.setBackground(new Color(255,51,51));
	           }
	            return cellComponent;

	        }

	    }
}

