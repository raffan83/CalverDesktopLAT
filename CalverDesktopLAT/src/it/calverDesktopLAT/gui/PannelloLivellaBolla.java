package it.calverDesktopLAT.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;

import it.calverDesktopLAT.bo.GestioneMisuraBO;
import it.calverDesktopLAT.bo.SessionBO;
import it.calverDesktopLAT.dto.PuntoLivellaBollaDTO;
import it.calverDesktopLAT.utl.Costanti;
import it.calverDesktopLAT.utl.Utility;
import net.miginfocom.swing.MigLayout;

public class PannelloLivellaBolla extends JPanel implements TableModelListener,ActionListener  {
	private JTable tableDX;
	private String originalValue="";
	JLabel lblInserimentoNonValido;
	public PannelloLivellaBolla() {
		
		SessionBO.prevPage="PMM";
		
		
		setLayout(new MigLayout("", "[grow]", "[grow]"));
		
		JScrollPane mainScroll = new JScrollPane();
		add(mainScroll, "cell 0 0,grow");
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
	//	tabbedPane.setBackground(Color.ORANGE);
		mainScroll.setViewportView(tabbedPane);
	
		try 
		{
		
	//	JScrollPane scrollDX= new JScrollPane(costruisciPanelSemiscalaPositivaDX());
		tabbedPane.addTab("Semiscala Positiva DX",costruisciPanelSemiscalaPositivaDX());
	//	tabbedPane.addTab("Semiscala Positiva SX", costruisciPanelSemiscalaPositivaSX());
	//	tabbedPane.addTab("Riferimenti & Incertezza", costruisciPanelRiferimentiIncertezza());
		}catch 
		(Exception e) {
			e.printStackTrace();
		}
	}

	private JPanel costruisciPanelSemiscalaPositivaDX() throws Exception {
		
		JPanel semDex= new JPanel();
		
		
		ArrayList<PuntoLivellaBollaDTO> listaPunti = GestioneMisuraBO.getListaPuntiLivellaBolla(SessionBO.idMisura, "DX");
		//semDex.setBackground(Color.CYAN);
		semDex.setLayout(new MigLayout("", "[grow]", "[30px][grow][]"));
		
			tableDX = new JTable();
			tableDX.setDefaultRenderer(Object.class, new MyCellRenderer());
			DefaultTableModel model = new DefaultTableModel() {

				@Override
				public Class<?> getColumnClass(int column) {
					switch (column) {
					case 0:
						return String.class;
					case 1:
						return String.class;
					case 2:
						return String.class;
					case 3:
						return String.class;
					case 4:
						return String.class;
					case 5:
						return String.class;
					case 6:
						return String.class;
					case 7:
						return String.class;
					case 8:
						return String.class;
					case 9:
						return String.class;
					case 10:
						return String.class;
					case 11:
						return String.class;
					case 12:
						return String.class;
					case 13:
						return String.class;
					case 14:
						return String.class;
					case 15:
						return String.class;
					case 16:
						return String.class;
					default:
						return String.class;
					}
				}
				 @Override
				public boolean isCellEditable(int row, int column) {
					if(column==0 || column==2|| column==5|| column==6|| column==9|| column==10|| column==11
							|| column==13|| column==14|| column==15)
					{
					return false;
					}else
					{
						return true;
					}
				}
				 
			};

			model.addColumn("Tratto");
			model.addColumn("mm/m");
			model.addColumn("sec");
			model.addColumn("P1 Andata");
			model.addColumn("P1 Ritorno");
			model.addColumn("P1 Media");
			model.addColumn("P1 Diff");
			model.addColumn("P2 Andata");
			model.addColumn("P2 Ritorno");
			model.addColumn("P2 Media");
			model.addColumn("P2 Diff");
			model.addColumn("Media Tratto 0");
			model.addColumn("Errore Cumm");
			model.addColumn("AVG sec");
			model.addColumn("AVG mm/m");
			model.addColumn("Div Dex mm/m");
			model.addColumn("index");

			PuntoLivellaBollaDTO punto =null;
			for (int i = 0; i <listaPunti.size(); i++) {
				
				punto= listaPunti.get(i);
				model.addRow(new Object[0]);
				model.setValueAt(punto.getRif_tacca(), i, 0);
				model.setValueAt(punto.getValore_nominale_tratto(), i, 1);
				model.setValueAt(punto.getValore_nominale_tratto_sec(), i, 2);
				model.setValueAt(punto.getP1_andata(), i, 3);
				model.setValueAt(punto.getP1_ritorno(), i, 4);
				model.setValueAt(punto.getP1_media(), i, 5);
				model.setValueAt(punto.getP1_diff(), i, 6);
				model.setValueAt(punto.getP2_andata(), i, 7);
				model.setValueAt(punto.getP2_ritorno(), i, 8);
				model.setValueAt(punto.getP2_media(), i, 9);
				model.setValueAt(punto.getP2_diff(), i, 10);
				model.setValueAt(punto.getMedia(), i, 11);
				model.setValueAt(punto.getErrore_cum(), i, 12);
				model.setValueAt(punto.getMedia_corr_sec(), i, 13);
				model.setValueAt(punto.getMedia_corr_mm(), i, 14);
				model.setValueAt(punto.getDiv_dex(), i, 15);
				model.setValueAt(punto.getId(), i, 16);
							
			}
			
	    model.addTableModelListener(this);
	    tableDX.getSelectionModel().addListSelectionListener(new RowListener());
	    tableDX.getColumnModel().getSelectionModel().addListSelectionListener(new ColumnListener());
	    
		tableDX.setModel(model);
		tableDX.setFont(new Font("Arial", Font.PLAIN, 12));
		tableDX.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
		tableDX.setRowHeight(25);
		

		
		  TableColumn column = tableDX.getColumnModel().getColumn(tableDX.getColumnModel().getColumnIndex("index"));
		  tableDX.removeColumn(column);
		
	//	tableDX.setPreferredScrollableViewportSize(new Dimension(900, 780));
		JScrollPane scrollTab = new JScrollPane(tableDX);
		semDex.add(scrollTab, "cell 0 1,grow");
		
		 lblInserimentoNonValido = new JLabel("* Inserimento non valido");
		lblInserimentoNonValido.setForeground(Color.RED);
		lblInserimentoNonValido.setFont(new Font("Arial", Font.BOLD, 12));
		lblInserimentoNonValido.setVisible(false);
		semDex.add(lblInserimentoNonValido, "cell 0 2");
		
		/*tableDX.addKeyListener(new KeyAdapter() {
		    public void keyPressed(KeyEvent e) {
		   int id = e.getID();
		    if(id==KeyEvent.KEY_PRESSED && e.getKeyCode()!=38 && e.getKeyCode()!=39 && e.getKeyCode()!=40 && e.getKeyCode()!=37 ) 
		    {	
		      int selectedColumn = tableDX.getSelectedColumn();
		      int selectrow = tableDX.getSelectedRow();
		      int keyCode = e.getKeyCode();
	          String c=    KeyEvent.getKeyText(keyCode);
	          System.out.println(c);
		      tableDX.getModel().setValueAt(c, selectrow,selectedColumn);
		    }
		      }
	});*/
		
		
		return semDex;
	}
	
private JPanel costruisciPanelSemiscalaPositivaSX() {
		
		JPanel semSX= new JPanel();
		semSX.setBackground(Color.BLACK);
		semSX.setLayout(new MigLayout("", "[grow]", "[grow]"));
		return semSX;
	}
private JPanel costruisciPanelRiferimentiIncertezza() {
	
	JPanel semInc= new JPanel();
	semInc.setBackground(Color.GREEN);
	semInc.setLayout(new MigLayout("", "[grow]", "[grow]"));
	return semInc;
}

public class MyCellRenderer extends javax.swing.table.DefaultTableCellRenderer {
	
	

    public java.awt.Component getTableCellRendererComponent(javax.swing.JTable table, java.lang.Object value, boolean isSelected, boolean hasFocus, int row, int column) {
    
    	final java.awt.Component cellComponent = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

 
        if (column  == 1 ||column  == 3 ||column  == 4 ||column  == 7 ||column  ==8  ||column  == 12 ) 
        {
            cellComponent.setBackground(new Color(224,224,224));
            cellComponent.setForeground(Color.BLACK);

        }else 
        {
        	cellComponent.setBackground(Color.white);
        	cellComponent.setForeground(Color.BLACK);
        }
        
    return cellComponent;

    }

}

@Override
public void tableChanged(TableModelEvent e) {

	 int row = e.getFirstRow();
	 int column=e.getColumn();
	 
	 
     TableModel model = (TableModel)e.getSource();
     int indexPoint=Integer.parseInt(model.getValueAt(row,16).toString());
    
     String value = model.getValueAt(row,column).toString();
    		 
     if(controllaNumero(model,value,row,column))
     {
    	 if(column==1) 
    	 {
    		 model.setValueAt(GestioneMisuraBO.getArcosec(value), row, 2);
    	 }
    	 if(column==3 ||column==4)
    	 {
    		 Object col3= model.getValueAt(row,3);
    		 Object col4= model.getValueAt(row,4);
    		 
    		 if(col3!=null && col4!=null) 
    		 {
    			 /*Media P1*/
    			 BigDecimal bd1=new BigDecimal(col3.toString()).setScale(Costanti.RISOLUZIONE_LIVELLA_BOLLA,RoundingMode.HALF_UP);
    			 BigDecimal bd2=new BigDecimal(col4.toString()).setScale(Costanti.RISOLUZIONE_LIVELLA_BOLLA,RoundingMode.HALF_UP);
    			 bd1=bd1.add(bd2).divide(new BigDecimal("2"),RoundingMode.HALF_UP);
    			 model.setValueAt(bd1.setScale(Costanti.RISOLUZIONE_LIVELLA_BOLLA,RoundingMode.HALF_UP).toPlainString(), row, 5);
    		 
    			/*Tratto 0*/
    			 
    			BigDecimal pivot= new BigDecimal( model.getValueAt(0,5).toString()).setScale(Costanti.RISOLUZIONE_LIVELLA_BOLLA,RoundingMode.HALF_UP);
    			
    			for (int i = 0; i < 11; i++) 
    			{
					Object obj =model.getValueAt(i,5);
					
					if(obj!=null) 
					{
						BigDecimal bd_tratto= new BigDecimal(obj.toString()).setScale(Costanti.RISOLUZIONE_LIVELLA_BOLLA);
						model.setValueAt(bd_tratto.subtract(pivot).setScale(Costanti.RISOLUZIONE_LIVELLA_BOLLA), i, 6);
					}
				}
    		 }
    		 
    		 /*Medie*/
    		 Object m1=model.getValueAt(row, 6);
    		 Object m2=model.getValueAt(row, 10);	 
    		 impostaMedie(m1,m2,model,row);
    		 
    		 if(model.getValueAt(row,12)!=null) 
    		 {
    			 column=12;
    		}
    		 
    	 }
    	 
    	 if(column==7 ||column==8)
    	 {
    		 Object col7= model.getValueAt(row,7);
    		 Object col8= model.getValueAt(row,8);
    		 
    		 if(col7!=null && col8!=null) 
    		 {
    			 /*Media P2*/
    			 BigDecimal bd1=new BigDecimal(col7.toString()).setScale(Costanti.RISOLUZIONE_LIVELLA_BOLLA,RoundingMode.HALF_UP);
    			 BigDecimal bd2=new BigDecimal(col8.toString()).setScale(Costanti.RISOLUZIONE_LIVELLA_BOLLA,RoundingMode.HALF_UP);
    			 bd1=bd1.add(bd2).divide(new BigDecimal("2"),RoundingMode.HALF_UP);
    			 model.setValueAt(bd1.setScale(Costanti.RISOLUZIONE_LIVELLA_BOLLA,RoundingMode.HALF_UP).toPlainString(), row, 9);
    		 
    			/*Tratto 0*/
    			 
    			BigDecimal pivot= new BigDecimal( model.getValueAt(0,9).toString()).setScale(Costanti.RISOLUZIONE_LIVELLA_BOLLA,RoundingMode.HALF_UP);
    			
    			for (int i = 0; i < 11; i++) 
    			{
					Object obj =model.getValueAt(i,9);
					
					if(obj!=null) 
					{
						BigDecimal bd_tratto= new BigDecimal(obj.toString()).setScale(Costanti.RISOLUZIONE_LIVELLA_BOLLA);
						model.setValueAt(bd_tratto.subtract(pivot).setScale(Costanti.RISOLUZIONE_LIVELLA_BOLLA), i, 10);
					}
				}
    		 }
    		 
    		 /*Medie*/
    		 Object m1=model.getValueAt(row, 6);
    		 Object m2=model.getValueAt(row, 10);	 
    		 impostaMedie(m1,m2,model,row);
    		 
    		 if(model.getValueAt(row,12)!=null) 
    		 {
    			 column=12;
    		}
    	 }
    	 
    	 if(column==12)
    	 {
    		 Object col12= model.getValueAt(row,12);
    		 
    		 if(col12!=null) 
    		 {
    			 BigDecimal erroreCum= new BigDecimal(col12.toString()).setScale(Costanti.RISOLUZIONE_LIVELLA_BOLLA, RoundingMode.HALF_UP);
    			 
    			 Object obj =model.getValueAt(row, 11);
    			 if(obj!=null) 
    			 {
    				 BigDecimal mediaTratto= new BigDecimal(obj.toString()).setScale(Costanti.RISOLUZIONE_LIVELLA_BOLLA, RoundingMode.HALF_UP);
    				 
    				 model.setValueAt(mediaTratto.subtract(erroreCum).setScale(Costanti.RISOLUZIONE_LIVELLA_BOLLA,RoundingMode.HALF_UP), row,13);
    				 
    				 BigDecimal avgArcsecInv=GestioneMisuraBO.getArcosecInv(mediaTratto.subtract(erroreCum).setScale(Costanti.RISOLUZIONE_LIVELLA_BOLLA,RoundingMode.HALF_UP).toPlainString());
    				 model.setValueAt(avgArcsecInv.toPlainString(), row,14);
    			 
    				 if(row==0) 
    				 {
    					 model.setValueAt("0.00",0,15);
    				 }else 
    				 {
    					 Object obj1 =model.getValueAt(row-1, 14);
    					 
    					 if(obj1!=null)
    					 {
    						 model.setValueAt(avgArcsecInv.subtract(new BigDecimal(obj1.toString())), row, 15);
    					 }
    				 }
    				 
    				 
    			 }
    		 }
    	 }
    	 
    	 
    	 PuntoLivellaBollaDTO punto = new PuntoLivellaBollaDTO();
    	 punto= new PuntoLivellaBollaDTO();
			punto.setId(indexPoint);
			punto.setValore_nominale_tratto(checkField(model.getValueAt(row, 1),Costanti.RISOLUZIONE_LIVELLA_BOLLA));
			punto.setValore_nominale_tratto_sec(checkField(model.getValueAt(row, 2),Costanti.RISOLUZIONE_LIVELLA_BOLLA));
			punto.setP1_andata(checkField(model.getValueAt(row, 3),Costanti.RISOLUZIONE_LIVELLA_BOLLA));
			punto.setP1_ritorno(checkField(model.getValueAt(row, 4),Costanti.RISOLUZIONE_LIVELLA_BOLLA));
			punto.setP1_media(checkField(model.getValueAt(row, 5),Costanti.RISOLUZIONE_LIVELLA_BOLLA));
			punto.setP1_diff(checkField(model.getValueAt(row, 6),Costanti.RISOLUZIONE_LIVELLA_BOLLA));
			punto.setP2_andata(checkField(model.getValueAt(row, 7),Costanti.RISOLUZIONE_LIVELLA_BOLLA));
			punto.setP2_ritorno(checkField(model.getValueAt(row, 8),Costanti.RISOLUZIONE_LIVELLA_BOLLA));
			punto.setP2_media(checkField(model.getValueAt(row, 9),Costanti.RISOLUZIONE_LIVELLA_BOLLA));
			punto.setP2_diff(checkField(model.getValueAt(row, 10),Costanti.RISOLUZIONE_LIVELLA_BOLLA));
			punto.setMedia(checkField(model.getValueAt(row, 11),Costanti.RISOLUZIONE_LIVELLA_BOLLA));
			punto.setErrore_cum(checkField(model.getValueAt(row, 12),Costanti.RISOLUZIONE_LIVELLA_BOLLA));
			punto.setMedia_corr_sec(checkField(model.getValueAt(row, 13),Costanti.RISOLUZIONE_LIVELLA_BOLLA));
			punto.setMedia_corr_mm(checkField(model.getValueAt(row, 14),Costanti.RISOLUZIONE_LIVELLA_BOLLA+2));
			punto.setDiv_dex(checkField(model.getValueAt(row, 15),Costanti.RISOLUZIONE_LIVELLA_BOLLA+2));
			
		try 
		{
			GestioneMisuraBO.updateRecordPuntoLivellaBolla(punto);
		} catch (Exception e2) {
			
			e2.printStackTrace();
		}	
			
     }
     	
}

private BigDecimal checkField(Object valueAt, int risoluzioneLivellaBolla) {
	
		if(valueAt!=null) 
		{
			BigDecimal bd= new BigDecimal(valueAt.toString());
			bd.setScale(risoluzioneLivellaBolla,RoundingMode.HALF_UP);
			return bd ;
		}
		else 
		{
			return null;
		}
}

private void impostaMedie(Object m1, Object m2 ,TableModel model,int row) {
	
	if(m1!=null &&m2!=null) 
	{
		BigDecimal bd_m1=new BigDecimal(m1.toString()).setScale(Costanti.RISOLUZIONE_LIVELLA_BOLLA,RoundingMode.HALF_UP);
		BigDecimal bd_m2=new BigDecimal(m2.toString()).setScale(Costanti.RISOLUZIONE_LIVELLA_BOLLA,RoundingMode.HALF_UP);
		
		BigDecimal mediaTratto = bd_m1.add(bd_m2).divide(new BigDecimal(2),RoundingMode.HALF_UP);
		model.setValueAt(mediaTratto.toPlainString(),row,11);
		
		
	}
	
}

private boolean controllaNumero(TableModel model, String val,int row, int column) {
	
	try
	{
		if(val!=null) 
		{
			BigDecimal toRet= new BigDecimal(val);
			return true;
		}
		else 
		{
			return true;
		}
	}
	catch(NumberFormatException ex) 
	{
		if(originalValue.equals("")) 
		{
			originalValue="0";
		}
		model.setValueAt(originalValue, row, column);
		lblInserimentoNonValido.setVisible(true);
		return false;
	}
	
	
	
	
}

@Override
public void actionPerformed(ActionEvent e) {
	
}

private void outputSelection() {
	lblInserimentoNonValido.setVisible(false);
	Object value=tableDX.getModel().getValueAt(tableDX.getSelectedRow(),tableDX.getSelectedColumn());
	if(value!=null) 
	{
		originalValue=tableDX.getModel().getValueAt(tableDX.getSelectedRow(),tableDX.getSelectedColumn()).toString();
	}
	else 
	{
		originalValue="";
	}
	

}

private class RowListener implements ListSelectionListener {
    public void valueChanged(ListSelectionEvent event) {
        if (event.getValueIsAdjusting()) {
            return;
        }
        outputSelection();
    }
}

private class ColumnListener implements ListSelectionListener {
    public void valueChanged(ListSelectionEvent event) {
        if (event.getValueIsAdjusting()) {
            return;
        }
        outputSelection();
    }
}



}
