package it.calverDesktopLAT.gui;

import java.awt.Color;
import java.awt.Component;
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
import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.JButton;

public class PannelloLivellaBolla extends JPanel  {

	ArrayList<PuntoLivellaBollaDTO> listaPuntiSX,listaPuntiDX;
	private JTextField textField_media_totale;
	private JTextField textField_dev_std_totale;
	private JTextField textField_scmax;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;
	private JTextField textField_4;
	private JTextField incertezza_um;
	private JTextField incertezza_er;
	private JTextField incertezza_em;
	private JTextField incertezza_er_sec;
	private JTextField incertezza_em_sec;
	private JTextField campo_misura;
	private JTextField sensibilita;
	private JTextField campo_misura_sec;
	public PannelloLivellaBolla() {

		SessionBO.prevPage="PMM";


		setLayout(new MigLayout("", "[grow][grow][grow][grow][grow]", "[][grow]"));
		{
			JLabel lblDevStdTotale = new JLabel("Dev. Std Totale");
			add(lblDevStdTotale, "flowx,cell 2 0");
		}
		{
			JLabel lblScmax = new JLabel("SCmax");
			add(lblScmax, "flowx,cell 4 0");
		}

		JScrollPane mainScroll = new JScrollPane();
		add(mainScroll, "cell 0 1 5 1,grow");

		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		//	tabbedPane.setBackground(Color.ORANGE);
		mainScroll.setViewportView(tabbedPane);

		try 
		{
			listaPuntiDX = GestioneMisuraBO.getListaPuntiLivellaBolla(SessionBO.idMisura, "DX");
			listaPuntiSX = GestioneMisuraBO.getListaPuntiLivellaBolla(SessionBO.idMisura, "SX");

			PannelloDX dx = new PannelloDX();
			tabbedPane.addTab("Semiscala Positiva DX",dx.get());
			PannelloSX sx = new PannelloSX();
			tabbedPane.addTab("Semiscala Positiva SX",sx.get());
			
		    tabbedPane.addTab("Riferimenti & Incertezza", costruisciPanelRiferimentiIncertezza());
		    
		    JLabel lblSMediaTotale = new JLabel("S. Media Totale");
		    add(lblSMediaTotale, "flowx,cell 0 0");
		    {
		    	textField_media_totale = new JTextField();
		    	textField_media_totale.setFont(new Font("Arial", Font.BOLD, 12));
		    	textField_media_totale.setText(GestioneMisuraBO.getAverageLivella(listaPuntiDX, listaPuntiSX,2).toPlainString());
		    	textField_media_totale.setEditable(false);
		    	add(textField_media_totale, "cell 0 0");
		    	textField_media_totale.setColumns(10);
		    }
		    {
		    	textField_dev_std_totale = new JTextField();
		    	textField_dev_std_totale.setEditable(false);
		    	textField_dev_std_totale.setFont(new Font("Arial", Font.BOLD, 12));
		    	textField_dev_std_totale.setText(GestioneMisuraBO.getDevStdLivella(listaPuntiDX, listaPuntiSX,2).toPlainString());
		    	add(textField_dev_std_totale, "cell 2 0");
		    	textField_dev_std_totale.setColumns(10);
		    }
		    {
		    	textField_scmax = new JTextField();
		    	textField_scmax.setEditable(false);
		    	textField_scmax.setFont(new Font("Arial", Font.BOLD, 12));
		    	textField_scmax.setText(GestioneMisuraBO.getScMaxLivella(listaPuntiDX, listaPuntiSX).toPlainString());
		    	add(textField_scmax, "cell 4 0");
		    	textField_scmax.setColumns(10);
		    }
		}catch 
		(Exception e) {
			e.printStackTrace();
		}
	}

	
	
	private JPanel costruisciPanelRiferimentiIncertezza() {

		JPanel semInc= new JPanel();
		semInc.setBackground(Color.LIGHT_GRAY);
		semInc.setLayout(new MigLayout("", "[pref!,grow][pref!,grow][grow][grow]", "[pref!,grow][pref!,grow][pref!,grow][pref!,grow][pref!,grow][][30:pref:pref][pref!,grow][][][][][][][][][][][grow][]"));
		{
			JLabel lblVerifichePreliminari = new JLabel("Verifiche preliminari");
			lblVerifichePreliminari.setFont(new Font("Arial", Font.BOLD | Font.ITALIC, 14));
			semInc.add(lblVerifichePreliminari, "cell 0 0");
		}
		{
			JLabel lblStatoDiConservazione = new JLabel("Stato di conservazione e pulizia:");
			lblStatoDiConservazione.setFont(new Font("Arial", Font.PLAIN, 12));
			semInc.add(lblStatoDiConservazione, "cell 0 1,alignx trailing");
		}
		{
			textField = new JTextField();
			semInc.add(textField, "cell 1 1,width :150:");
			textField.setColumns(10);
		}
		{
			JLabel lblPresenzeDiFitte = new JLabel("Presenze di fitte e ammaccature");
			lblPresenzeDiFitte.setFont(new Font("Arial", Font.PLAIN, 12));
			semInc.add(lblPresenzeDiFitte, "cell 0 2,alignx trailing");
		}
		{
			textField_1 = new JTextField();
			textField_1.setColumns(10);
			semInc.add(textField_1, "cell 1 2,growx");
		}
		{
			JLabel lblPresenzaDiBolla = new JLabel("Presenza di Bolla trasversale");
			lblPresenzaDiBolla.setFont(new Font("Arial", Font.PLAIN, 12));
			semInc.add(lblPresenzaDiBolla, "cell 0 3,alignx trailing");
		}
		{
			textField_2 = new JTextField();
			textField_2.setColumns(10);
			semInc.add(textField_2, "cell 1 3,growx");
		}
		{
			JLabel lblRegolazioneESigilli = new JLabel("Regolazione e Sigilli");
			lblRegolazioneESigilli.setFont(new Font("Arial", Font.PLAIN, 12));
			semInc.add(lblRegolazioneESigilli, "cell 0 4,alignx trailing");
		}
		{
			textField_3 = new JTextField();
			textField_3.setColumns(10);
			semInc.add(textField_3, "cell 1 4,growx");
		}
		{
			JLabel lblCentraggioRispettoAllasse = new JLabel("Centraggio rispetto all'asse di gravita");
			semInc.add(lblCentraggioRispettoAllasse, "cell 0 5,alignx trailing");
		}
		{
			textField_4 = new JTextField();
			textField_4.setColumns(10);
			semInc.add(textField_4, "cell 1 5,growx");
		}
		{
			JLabel lblCampoMisura = new JLabel("Campo Misura");
			semInc.add(lblCampoMisura, "cell 0 7,alignx trailing");
		}
		{
			campo_misura = new JTextField();
			campo_misura.setColumns(10);
			semInc.add(campo_misura, "flowx,cell 1 7,growx");
		}
		{
			campo_misura_sec = new JTextField();
			campo_misura_sec.setEditable(false);
			campo_misura_sec.setColumns(10);
			semInc.add(campo_misura_sec, "flowx,cell 2 7,alignx center");
		}
		{
			JLabel lblSensibilit = new JLabel("Sensibilit\u00E0:");
			semInc.add(lblSensibilit, "cell 0 9,alignx trailing");
		}
		{
			sensibilita = new JTextField();
			sensibilita.setColumns(10);
			semInc.add(sensibilita, "flowx,cell 1 9,growx");
		}
		{
			JLabel lblIncertezzaAssociataAl = new JLabel("Incertezza associata al riferimento U(Er)");
			lblIncertezzaAssociataAl.setFont(new Font("Arial", Font.PLAIN, 12));
			semInc.add(lblIncertezzaAssociataAl, "cell 0 11,alignx trailing");
		}
		{
			incertezza_er = new JTextField();
			incertezza_er.setEditable(false);
			incertezza_er.setColumns(10);
			semInc.add(incertezza_er, "flowx,cell 1 11,growx");
		}
		{
			incertezza_er_sec = new JTextField();
			incertezza_er_sec.setEditable(false);
			incertezza_er_sec.setColumns(10);
			semInc.add(incertezza_er_sec, "flowx,cell 2 11,alignx center");
		}
		{
			JLabel lblIncertezzaEstesaUem = new JLabel("Incertezza Estesa U(Em)");
			lblIncertezzaEstesaUem.setFont(new Font("Arial", Font.PLAIN, 12));
			semInc.add(lblIncertezzaEstesaUem, "cell 0 13,alignx trailing");
		}
		{
			incertezza_em = new JTextField();
			incertezza_em.setEditable(false);
			incertezza_em.setColumns(10);
			semInc.add(incertezza_em, "flowx,cell 1 13,growx");
		}
		{
			incertezza_em_sec = new JTextField();
			incertezza_em_sec.setEditable(false);
			incertezza_em_sec.setColumns(10);
			semInc.add(incertezza_em_sec, "flowx,cell 2 13,alignx center");
		}
		{
			JLabel lblIncertezzaDaAssociare = new JLabel("Incertezza da associare al valore medio di");
			lblIncertezzaDaAssociare.setFont(new Font("Arial", Font.PLAIN, 12));
			semInc.add(lblIncertezzaDaAssociare, "cell 0 15,alignx trailing");
		}
		{
			incertezza_um = new JTextField();
			incertezza_um.setEditable(false);
			incertezza_um.setColumns(10);
			semInc.add(incertezza_um, "flowx,cell 1 15,growx");
		}
		{
			JLabel lblUnaDivisioneDella = new JLabel("una divisione della scala graduata Um");
			lblUnaDivisioneDella.setFont(new Font("Arial", Font.PLAIN, 12));
			semInc.add(lblUnaDivisioneDella, "cell 0 16");
		}
		{
			JButton btnNewButton = new JButton("Calcola");
			btnNewButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					
					if(campo_misura.getText().length()>0 && sensibilita.getText().length()>0) 
					{
						campo_misura_sec.setText(GestioneMisuraBO.getArcosec(campo_misura.getText()).setScale(Costanti.RISOLUZIONE_LIVELLA_BOLLA,RoundingMode.HALF_UP).toPlainString());
						
						BigDecimal er= (new BigDecimal(campo_misura_sec.getText()).multiply(new BigDecimal("0.002")).add(new BigDecimal("1.5")));
						
						incertezza_er_sec.setText(er.setScale(Costanti.RISOLUZIONE_LIVELLA_BOLLA,RoundingMode.HALF_UP).toPlainString());						
						
						incertezza_er.setText(GestioneMisuraBO.getArcosecInv(incertezza_er_sec.getText()).setScale(Costanti.RISOLUZIONE_LIVELLA_BOLLA+2,RoundingMode.HALF_UP).toPlainString());
						
						BigDecimal em=GestioneMisuraBO.getIncertezzaLivellaBolla_EM(incertezza_er.getText(),sensibilita.getText());
						
						incertezza_em.setText(em.setScale(Costanti.RISOLUZIONE_LIVELLA_BOLLA+2,RoundingMode.HALF_DOWN).toPlainString());
						
						incertezza_em_sec.setText(GestioneMisuraBO.getArcosec(incertezza_em.getText()).setScale(Costanti.RISOLUZIONE_LIVELLA_BOLLA,RoundingMode.HALF_DOWN).toPlainString());
						
						BigDecimal um=GestioneMisuraBO.getIncertezzaLivellaBolla_UM(incertezza_em.getText(), textField_scmax.getText());
						
						incertezza_um.setText(um.setScale(Costanti.RISOLUZIONE_LIVELLA_BOLLA+2,RoundingMode.HALF_DOWN).toPlainString());
						
					}else 
					{
						JOptionPane.showMessageDialog(null,"Compilare \"Campo Misura\" e \"SensibilitÓ\"","Attenzione",JOptionPane.WARNING_MESSAGE,new ImageIcon(PannelloTOP.class.getResource("/image/attention.png")));
					}
					
				}
			});
			btnNewButton.setFont(new Font("Arial", Font.BOLD, 12));
			btnNewButton.setIcon(new ImageIcon(PannelloLivellaBolla.class.getResource("/image/calcola.png")));
			semInc.add(btnNewButton, "cell 1 17,alignx left");
		}
		{
			JLabel lblNote = new JLabel("Note:");
			lblNote.setFont(new Font("Arial", Font.PLAIN, 12));
			semInc.add(lblNote, "cell 0 18,alignx right");
		}
		{
			JTextArea textArea = new JTextArea();
			JScrollPane scrollPaneNote = new JScrollPane(textArea);
			semInc.add(scrollPaneNote, "cell 1 18 3 1,grow");
			
		}
		{
			JLabel label = new JLabel("''");
			semInc.add(label, "cell 2 11");
		}
		{
			JLabel label = new JLabel("''");
			semInc.add(label, "cell 2 13");
		}
		{
			JLabel label = new JLabel("''");
			semInc.add(label, "cell 2 7");
		}
		{
			JLabel lblMmm_2 = new JLabel("mm/m");
			semInc.add(lblMmm_2, "cell 1 7");
		}
		{
			JLabel label = new JLabel("mm/m");
			semInc.add(label, "cell 1 9");
		}
		{
			JLabel label = new JLabel("mm/m");
			semInc.add(label, "cell 1 11");
		}
		{
			JLabel label = new JLabel("mm/m");
			semInc.add(label, "cell 1 13");
		}
		{
			JLabel label = new JLabel("mm/m");
			semInc.add(label, "cell 1 15");
		}
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

	private class PannelloDX extends JPanel implements TableModelListener
	{
		private JTable tableDX,tableTratto;
		private String originalValue="";
		JLabel lblInserimentoNonValido;
		private JTextField s_media_field;
		private JTextField dev_st_field;
		JPanel semDex;
		private ModelTratto modelTratto;
		private ModelSemisc model;

		PannelloDX()
		{
			semDex= new JPanel();

			BigDecimal s_mediaTotale = GestioneMisuraBO.getAverageLivella(listaPuntiDX,listaPuntiSX,2);

			semDex.setLayout(new MigLayout("", "[grow][][][][]", "[30px][:360px:410px][][][]"));

			tableDX = new JTable();
			tableDX.setDefaultRenderer(Object.class, new MyCellRenderer());
			model = new ModelSemisc();

			PuntoLivellaBollaDTO punto =null;
			for (int i = 0; i <listaPuntiDX.size(); i++) {

				punto= listaPuntiDX.get(i);
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
			tableDX.setFont(new Font("Arial", Font.BOLD, 10));
			tableDX.getTableHeader().setFont(new Font("Arial", Font.BOLD, 10));
			tableDX.setRowHeight(25);

			TableColumn column = tableDX.getColumnModel().getColumn(tableDX.getColumnModel().getColumnIndex("index"));
			tableDX.removeColumn(column);

			JScrollPane scrollTab = new JScrollPane(tableDX);
			semDex.add(scrollTab, "cell 0 1 3 1,growx,height :350:400");

			/*Tabella Tratto*/

			tableTratto = new JTable();
			modelTratto= new ModelTratto();
			tableTratto.setModel(modelTratto);
			for (int i = 0; i <listaPuntiDX.size(); i++) {
				punto= listaPuntiDX.get(i);
				modelTratto.addRow(new Object[0]);		
				modelTratto.setValueAt(punto.getRif_tacca(), i, 0);

				if( punto.getDiv_dex()!=null && punto.getDiv_dex().abs().compareTo(BigDecimal.ZERO)==1) 
				{
					modelTratto.setValueAt(punto.getDiv_dex().toPlainString(), i, 1);
					modelTratto.setValueAt(punto.getDiv_dex().subtract(s_mediaTotale),i,2);
				}else 
				{
					modelTratto.setValueAt("", i, 2);
				}


			}

			tableTratto.setFont(new Font("Arial", Font.BOLD, 10));
			tableTratto.getTableHeader().setFont(new Font("Arial", Font.BOLD, 10));
			tableTratto.setRowHeight(25);


			lblInserimentoNonValido = new JLabel("* Inserimento non valido");
			lblInserimentoNonValido.setForeground(Color.RED);
			lblInserimentoNonValido.setFont(new Font("Arial", Font.BOLD, 12));
			lblInserimentoNonValido.setVisible(false);
			semDex.add(lblInserimentoNonValido, "cell 0 2");


			JLabel lblSMedia = new JLabel("s. media");
			lblSMedia.setFont(new Font("Arial", Font.BOLD, 12));
			semDex.add(lblSMedia, "flowx,cell 0 3,alignx trailing");

			JScrollPane scrollTabTratto = new JScrollPane(tableTratto);
			semDex.add(scrollTabTratto, "cell 4 1,growx,width :150:200,height :350:400");

			s_media_field = new JTextField();
			s_media_field.setFont(new Font("Arial", Font.BOLD, 12));
			s_media_field.setEditable(false);
			s_media_field.setText(GestioneMisuraBO.getAverageLivella(listaPuntiDX, listaPuntiSX,0).toPlainString());
			semDex.add(s_media_field, "cell 1 3");
			s_media_field.setColumns(10);

			JLabel lblMmm = new JLabel("mm/m");
			lblMmm.setFont(new Font("Arial", Font.BOLD, 12));
			semDex.add(lblMmm, "cell 2 3");

			JLabel lblDevSt = new JLabel("dev. st");
			lblDevSt.setFont(new Font("Arial", Font.BOLD, 12));
			semDex.add(lblDevSt, "cell 0 4,alignx trailing");

			dev_st_field = new JTextField();
			dev_st_field.setEditable(false);
			dev_st_field.setFont(new Font("Arial", Font.BOLD, 12));
			dev_st_field.setText(GestioneMisuraBO.getDevStdLivella(listaPuntiDX, listaPuntiSX,0).toPlainString());
			semDex.add(dev_st_field, "cell 1 4");
			dev_st_field.setColumns(10);

			JLabel lblMmm_1 = new JLabel("mm/m");
			lblMmm_1.setFont(new Font("Arial", Font.BOLD, 12));
			semDex.add(lblMmm_1, "cell 2 4");


			


			
		}

		public JPanel get() {
			return semDex;
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

									/*Gestione Model Tratto e Medie*/

									modelTratto.setValueAt(avgArcsecInv.subtract(new BigDecimal(obj1.toString())), row, 1);
								
									listaPuntiDX.get(row).setDiv_dex( avgArcsecInv.subtract(new BigDecimal(obj1.toString())));
									
									s_media_field.setText(GestioneMisuraBO.getAverageLivella(listaPuntiDX, listaPuntiSX,0).toPlainString());
									dev_st_field.setText(GestioneMisuraBO.getDevStdLivella(listaPuntiDX, listaPuntiSX,0).toPlainString());
									
									BigDecimal s_mediaTotale = GestioneMisuraBO.getAverageLivella(listaPuntiDX,listaPuntiSX,2);

									for (int i = 0; i <listaPuntiDX.size(); i++) {
										PuntoLivellaBollaDTO punto= listaPuntiDX.get(i);
										if( punto.getDiv_dex()!=null && punto.getDiv_dex().abs().compareTo(BigDecimal.ZERO)==1) 
										{
											modelTratto.setValueAt(punto.getDiv_dex().toPlainString(), i, 1);
											modelTratto.setValueAt(punto.getDiv_dex().subtract(s_mediaTotale),i,2);
										}else 
										{
											modelTratto.setValueAt("", i, 2);
										}


									}
								}
					
								/*aggiorna s media e devstd*/
								
								textField_media_totale.setText(GestioneMisuraBO.getAverageLivella(listaPuntiDX, listaPuntiSX,2).toPlainString());
								textField_dev_std_totale.setText(GestioneMisuraBO.getDevStdLivella(listaPuntiDX, listaPuntiSX,2).toPlainString());
								textField_scmax.setText(GestioneMisuraBO.getScMaxLivella(listaPuntiDX, listaPuntiSX).toPlainString());
								
						
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

	}
	
	private class PannelloSX extends JPanel implements TableModelListener
	{
		private JTable tableSX,tableTrattoSX;
		private String originalValue="";
		JLabel lblInserimentoNonValido;
		private JTextField s_media_field;
		private JTextField dev_st_field;
		private ModelTratto modelTratto;
		private ModelSemisc model;
		JPanel semSX;

		PannelloSX()
		{
			semSX= new JPanel();

			BigDecimal s_mediaTotale = GestioneMisuraBO.getAverageLivella(listaPuntiDX,listaPuntiSX,2);

			semSX.setLayout(new MigLayout("", "[grow][][][][]", "[30px][:360px:410px][][][]"));

			tableSX = new JTable();
			tableSX.setDefaultRenderer(Object.class, new MyCellRenderer());
			model = new ModelSemisc();

			PuntoLivellaBollaDTO punto =null;
			for (int i = 0; i <listaPuntiSX.size(); i++) {

				punto= listaPuntiSX.get(i);
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

			tableSX.getSelectionModel().addListSelectionListener(new RowListener());
			tableSX.getColumnModel().getSelectionModel().addListSelectionListener(new ColumnListener());

			tableSX.setModel(model);
			tableSX.setFont(new Font("Arial", Font.BOLD, 10));
			tableSX.getTableHeader().setFont(new Font("Arial", Font.BOLD, 10));
			tableSX.setRowHeight(25);

			TableColumn column = tableSX.getColumnModel().getColumn(tableSX.getColumnModel().getColumnIndex("index"));
			tableSX.removeColumn(column);

			JScrollPane scrollTab = new JScrollPane(tableSX);
			semSX.add(scrollTab, "cell 0 1 3 1,growx,height :350:400");

			/*Tabella Tratto*/

			tableTrattoSX = new JTable();
			modelTratto= new ModelTratto();
			tableTrattoSX.setModel(modelTratto);
			for (int i = 0; i <listaPuntiSX.size(); i++) {
				punto= listaPuntiSX.get(i);
				modelTratto.addRow(new Object[0]);		
				modelTratto.setValueAt(punto.getRif_tacca(), i, 0);

				if( punto.getDiv_dex()!=null && punto.getDiv_dex().abs().compareTo(BigDecimal.ZERO)==1) 
				{
					modelTratto.setValueAt(punto.getDiv_dex().toPlainString(), i, 1);
					modelTratto.setValueAt(punto.getDiv_dex().subtract(s_mediaTotale),i,2);
				}else 
				{
					modelTratto.setValueAt("", i, 2);
				}


			}

			tableTrattoSX.setFont(new Font("Arial", Font.BOLD, 10));
			tableTrattoSX.getTableHeader().setFont(new Font("Arial", Font.BOLD, 10));
			tableTrattoSX.setRowHeight(25);


			lblInserimentoNonValido = new JLabel("* Inserimento non valido");
			lblInserimentoNonValido.setForeground(Color.RED);
			lblInserimentoNonValido.setFont(new Font("Arial", Font.BOLD, 12));
			lblInserimentoNonValido.setVisible(false);
			semSX.add(lblInserimentoNonValido, "cell 0 2");


			JLabel lblSMedia = new JLabel("s. media");
			lblSMedia.setFont(new Font("Arial", Font.BOLD, 12));
			semSX.add(lblSMedia, "flowx,cell 0 3,alignx trailing");

			JScrollPane scrollTabTratto = new JScrollPane(tableTrattoSX);
			semSX.add(scrollTabTratto, "cell 4 1,growx,width :150:200,height :350:400");

			s_media_field = new JTextField();
			s_media_field.setFont(new Font("Arial", Font.BOLD, 12));
			s_media_field.setEditable(false);
			s_media_field.setText(GestioneMisuraBO.getAverageLivella(listaPuntiDX, listaPuntiSX,1).toPlainString());
			semSX.add(s_media_field, "cell 1 3");
			s_media_field.setColumns(10);

			JLabel lblMmm = new JLabel("mm/m");
			lblMmm.setFont(new Font("Arial", Font.BOLD, 12));
			semSX.add(lblMmm, "cell 2 3");

			JLabel lblDevSt = new JLabel("dev. st");
			lblDevSt.setFont(new Font("Arial", Font.BOLD, 12));
			semSX.add(lblDevSt, "cell 0 4,alignx trailing");

			dev_st_field = new JTextField();
			dev_st_field.setEditable(false);
			dev_st_field.setFont(new Font("Arial", Font.BOLD, 12));
			dev_st_field.setText(GestioneMisuraBO.getDevStdLivella(listaPuntiDX, listaPuntiSX,1).toPlainString());
			semSX.add(dev_st_field, "cell 1 4");
			dev_st_field.setColumns(10);

			JLabel lblMmm_1 = new JLabel("mm/m");
			lblMmm_1.setFont(new Font("Arial", Font.BOLD, 12));
			semSX.add(lblMmm_1, "cell 2 4");




			
		}

		public JPanel get() {
			return semSX;
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

									/*Gestione Model Tratto e Medie*/

									modelTratto.setValueAt(avgArcsecInv.subtract(new BigDecimal(obj1.toString())), row, 1);
									listaPuntiSX.get(row).setDiv_dex( avgArcsecInv.subtract(new BigDecimal(obj1.toString())));
									s_media_field.setText(GestioneMisuraBO.getAverageLivella(listaPuntiDX, listaPuntiSX,1).toPlainString());
									dev_st_field.setText(GestioneMisuraBO.getDevStdLivella(listaPuntiDX, listaPuntiSX,1).toPlainString());

									BigDecimal s_mediaTotale = GestioneMisuraBO.getAverageLivella(listaPuntiDX,listaPuntiSX,2);
									

									for (int i = 0; i <listaPuntiSX.size(); i++) {
										PuntoLivellaBollaDTO punto= listaPuntiSX.get(i);
										if( punto.getDiv_dex()!=null && punto.getDiv_dex().abs().compareTo(BigDecimal.ZERO)==1) 
										{
											modelTratto.setValueAt(punto.getDiv_dex().toPlainString(), i, 1);
											modelTratto.setValueAt(punto.getDiv_dex().abs().subtract(s_mediaTotale),i,2);
										}else 
										{
											modelTratto.setValueAt("", i, 2);
										}


									}
								}
								
								/*aggiorna s media e devstd*/
								
								textField_media_totale.setText(GestioneMisuraBO.getAverageLivella(listaPuntiDX, listaPuntiSX,2).toPlainString());
								textField_dev_std_totale.setText(GestioneMisuraBO.getDevStdLivella(listaPuntiDX, listaPuntiSX,2).toPlainString());
								textField_scmax.setText(GestioneMisuraBO.getScMaxLivella(listaPuntiDX, listaPuntiSX).toPlainString());
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

		private void outputSelection() {
			lblInserimentoNonValido.setVisible(false);
			Object value=tableSX.getModel().getValueAt(tableSX.getSelectedRow(),tableSX.getSelectedColumn());
			if(value!=null) 
			{
				originalValue=tableSX.getModel().getValueAt(tableSX.getSelectedRow(),tableSX.getSelectedColumn()).toString();
			}
			else 
			{
				originalValue="";
			}


		}

	}

	class ModelSemisc extends DefaultTableModel {


		public ModelSemisc() {
			addColumn("Tratto");
			addColumn("mm/m");
			addColumn("sec");
			addColumn("P1 Andata");
			addColumn("P1 Ritorno");
			addColumn("P1 Media");
			addColumn("P1 Diff");
			addColumn("P2 Andata");
			addColumn("P2 Ritorno");
			addColumn("P2 Media");
			addColumn("P2 Diff");
			addColumn("Media Tratto 0");
			addColumn("Errore Cumm");
			addColumn("AVG sec");
			addColumn("AVG mm/m");
			addColumn("Div Dex mm/m");
			addColumn("index");
		}
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


	}
	class ModelTratto extends DefaultTableModel {


		public ModelTratto() {
			addColumn("Tratto");
			addColumn("Valore 1 div. Liv mm/m");
			addColumn("Scostamento media mm/m");

		}
		@Override
		public Class<?> getColumnClass(int column) {
			switch (column) {
			case 0:
				return String.class;
			case 1:
				return String.class;
			case 2:
				return String.class;
			default:
				return String.class;
			}
		}

		@Override
		public boolean isCellEditable(int row, int column) {
			if(column<3)
			{
				return true;
			}else
			{
				return true;
			}
		}


	}
}


