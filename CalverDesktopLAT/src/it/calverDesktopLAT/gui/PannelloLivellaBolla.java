package it.calverDesktopLAT.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;

import it.calverDesktopLAT.bo.GestioneCampioneBO;
import it.calverDesktopLAT.bo.GestioneMisuraBO;
import it.calverDesktopLAT.bo.SessionBO;
import it.calverDesktopLAT.dto.LatMisuraDTO;
import it.calverDesktopLAT.dto.PuntoLivellaBollaDTO;
import it.calverDesktopLAT.utl.Costanti;
import net.miginfocom.swing.MigLayout;
import javax.swing.DefaultComboBoxModel;

public class PannelloLivellaBolla extends JPanel  {

	ArrayList<PuntoLivellaBollaDTO> listaPuntiSX,listaPuntiDX;
	private JTextField textField_media_totale;
	private JTextField textField_dev_std_totale;
	private JTextField textField_scmax;
	private JTextField incertezza_um;
	private JTextField incertezza_er;
	private JTextField incertezza_em;
	private JTextField incertezza_er_sec;
	private JTextField incertezza_em_sec;
	private JTextField campo_misura;
	private JTextField sensibilita;
	private JTextField campo_misura_sec;
	private JComboBox comboBox_cmpRif;
	private JComboBox comboBox_cmpLav;
	private JComboBox comboBox_ammaccature;
	private JComboBox comboBox_bolla_trasversale;
	private JComboBox comboBox_regolazione;
	private JComboBox comboBox_centraggio;
	private JComboBox comboBox_stato;
	private JTextArea textArea;
	
	public PannelloLivellaBolla(int index) {

		SessionBO.prevPage="PMM";


		setLayout(new MigLayout("", "[grow][grow][grow][grow][grow]", "[][grow]"));
		{
			JLabel lblDevStdTotale = new JLabel("Dev. Std Totale");
			lblDevStdTotale.setFont(new Font("Arial", Font.BOLD, 14));
			add(lblDevStdTotale, "flowx,cell 2 0");
		}
		{
			JLabel lblScmax = new JLabel("SCmax");
			lblScmax.setFont(new Font("Arial", Font.BOLD, 14));
			add(lblScmax, "flowx,cell 4 0");
		}

		JScrollPane mainScroll = new JScrollPane();
		add(mainScroll, "cell 0 1 5 1,grow");

		
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		mainScroll.setViewportView(tabbedPane);

		try 
		{
			listaPuntiDX = GestioneMisuraBO.getListaPuntiLivellaBolla(SessionBO.idMisura, "DX");
			listaPuntiSX = GestioneMisuraBO.getListaPuntiLivellaBolla(SessionBO.idMisura, "SX");

			tabbedPane.addTab("Riferimenti & Incertezza", costruisciPanelRiferimentiIncertezza());
			
			PannelloDX dx = new PannelloDX();
			tabbedPane.addTab("Semiscala DX",dx.get());
			PannelloSX sx = new PannelloSX();
			tabbedPane.addTab("Semiscala SX",sx.get());
			
		    
		    tabbedPane.setSelectedIndex(index);
		    
		    JLabel lblSMediaTotale = new JLabel("S. Media Totale");
		    lblSMediaTotale.setFont(new Font("Arial", Font.BOLD, 14));
		    
		    add(lblSMediaTotale, "flowx,cell 0 0");
		    {
		    	textField_media_totale = new JTextField();
		    	textField_media_totale.setBackground(Color.YELLOW);
		    	textField_media_totale.setFont(new Font("Arial", Font.BOLD, 14));
		    	textField_media_totale.setText(GestioneMisuraBO.getAverageLivella(listaPuntiDX, listaPuntiSX,2).toPlainString());
		    	textField_media_totale.setEditable(false);
		    	add(textField_media_totale, "cell 0 0");
		    	textField_media_totale.setColumns(10);
		    	
		    }
		    {
		    	textField_dev_std_totale = new JTextField();
		    	textField_dev_std_totale.setBackground(Color.YELLOW);
		    	textField_dev_std_totale.setEditable(false);
		    	textField_dev_std_totale.setFont(new Font("Arial", Font.BOLD, 14));
		    	textField_dev_std_totale.setText(GestioneMisuraBO.getDevStdLivella(listaPuntiDX, listaPuntiSX,2).setScale(Costanti.RISOLUZIONE_LIVELLA_BOLLA+3,RoundingMode.HALF_UP).toPlainString());
		    	add(textField_dev_std_totale, "cell 2 0");
		    	textField_dev_std_totale.setColumns(10);
		    }
		    {
		    	textField_scmax = new JTextField();
		    	textField_scmax.setBackground(Color.YELLOW);
		    	textField_scmax.setEditable(false);
		    	textField_scmax.setFont(new Font("Arial", Font.BOLD, 14));
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
		semInc.setLayout(new MigLayout("", "[pref!,grow][pref!,grow][][grow]", "[pref!,grow][][][pref!,grow][pref!,grow][pref!,grow][pref!,grow][][30:pref:pref][pref!,grow][][][][][][][][][][grow][pref!,grow][]"));

		try{
			{
				JLabel lblVerifichePreliminari = new JLabel("Verifiche preliminari");
				lblVerifichePreliminari.setFont(new Font("Arial", Font.BOLD | Font.ITALIC, 14));
				semInc.add(lblVerifichePreliminari, "cell 0 0");

				JLabel lblCampioneDiRiferimento = new JLabel("Campione di Riferimento:");
				semInc.add(lblCampioneDiRiferimento, "cell 0 1,alignx trailing");


				comboBox_cmpRif = new JComboBox(GestioneCampioneBO.getListaCampioniCompleta());
				comboBox_cmpRif.setEnabled(false);
				semInc.add(comboBox_cmpRif, "cell 1 1 2 1");

				JLabel lblCampioneDiLavoro = new JLabel("Campione di lavoro:");
				semInc.add(lblCampioneDiLavoro, "cell 0 2,alignx trailing");


				comboBox_cmpLav = new JComboBox(GestioneCampioneBO.getListaCampioniCompleta());
				comboBox_cmpLav.setEnabled(false);
				semInc.add(comboBox_cmpLav, "cell 1 2 2 1");


				JLabel lblStatoDiConservazione = new JLabel("Stato di conservazione e pulizia:");
				lblStatoDiConservazione.setFont(new Font("Arial", Font.PLAIN, 12));
				semInc.add(lblStatoDiConservazione, "cell 0 3,alignx trailing");
				
				
				comboBox_stato = new JComboBox();
				comboBox_stato.setModel(new DefaultComboBoxModel(new String[] {"Buono", "Sufficiente"}));
				comboBox_stato.setEditable(true);
				semInc.add(comboBox_stato, "cell 1 3 2 1");

				JLabel lblPresenzeDiFitte = new JLabel("Presenze di fitte e ammaccature:");
				lblPresenzeDiFitte.setFont(new Font("Arial", Font.PLAIN, 12));
				semInc.add(lblPresenzeDiFitte, "cell 0 4,alignx trailing");
				
				comboBox_ammaccature = new JComboBox();
				comboBox_ammaccature.setModel(new DefaultComboBoxModel(new String[] {"Non presenti", "Tali da non pregiudicare la stabilità e affidabilità della livella"}));
				comboBox_ammaccature.setEditable(true);
				semInc.add(comboBox_ammaccature, "cell 1 4 2 1");

				JLabel lblPresenzaDiBolla = new JLabel("Presenza di Bolla trasversale:");
				lblPresenzaDiBolla.setFont(new Font("Arial", Font.PLAIN, 12));
				semInc.add(lblPresenzaDiBolla, "cell 0 5,alignx trailing");
				
				comboBox_bolla_trasversale = new JComboBox();
				comboBox_bolla_trasversale.setModel(new DefaultComboBoxModel(new String[] {"Sì", "No"}));
				comboBox_bolla_trasversale.setEditable(true);
				semInc.add(comboBox_bolla_trasversale, "cell 1 5 2 1");

				JLabel lblRegolazioneESigilli = new JLabel("Regolazione e Sigilli:");
				lblRegolazioneESigilli.setFont(new Font("Arial", Font.PLAIN, 12));
				semInc.add(lblRegolazioneESigilli, "cell 0 6,alignx trailing");
				
				comboBox_regolazione = new JComboBox();
				comboBox_regolazione.setModel(new DefaultComboBoxModel(new String[] {"In ordine", "Non applicabile"}));
				comboBox_regolazione.setEditable(true);
				semInc.add(comboBox_regolazione, "cell 1 6,growx");

				JLabel lblCentraggioRispettoAllasse = new JLabel("Centraggio rispetto all'asse di gravita:");
				semInc.add(lblCentraggioRispettoAllasse, "cell 0 7,alignx trailing");
				
				comboBox_centraggio = new JComboBox();
				comboBox_centraggio.setModel(new DefaultComboBoxModel(new String[] {"Eseguito", "Non necessario bolla centrata"}));
				comboBox_centraggio.setEditable(true);
				semInc.add(comboBox_centraggio, "cell 1 7 2 1");

				JLabel lblCampoMisura = new JLabel("Campo Misura: ±");
				semInc.add(lblCampoMisura, "cell 0 9,alignx trailing");

				campo_misura = new JTextField();
				campo_misura.setColumns(10);
				semInc.add(campo_misura, "flowx,cell 1 9,growx");
				
				JLabel lblNewLabel = new JLabel(" \u00B1");
				semInc.add(lblNewLabel, "flowx,cell 2 9,alignx left");

				JLabel lblSensibilit = new JLabel("Sensibilit\u00E0:");
				semInc.add(lblSensibilit, "cell 0 11,alignx trailing");

				sensibilita = new JTextField();
				sensibilita.setColumns(10);
				semInc.add(sensibilita, "flowx,cell 1 11,growx");

				JLabel lblIncertezzaAssociataAl = new JLabel("Incertezza associata al riferimento U(Er):");
				lblIncertezzaAssociataAl.setFont(new Font("Arial", Font.PLAIN, 12));
				semInc.add(lblIncertezzaAssociataAl, "cell 0 13,alignx trailing");

				incertezza_er = new JTextField();
				incertezza_er.setEditable(false);
				incertezza_er.setColumns(10);
				semInc.add(incertezza_er, "flowx,cell 1 13,growx");
				
								incertezza_er_sec = new JTextField();
								incertezza_er_sec.setEditable(false);
								incertezza_er_sec.setColumns(10);
								semInc.add(incertezza_er_sec, "flowx,cell 2 13,alignx left");

				JLabel lblIncertezzaEstesaUem = new JLabel("Incertezza Estesa U(Em):");
				lblIncertezzaEstesaUem.setFont(new Font("Arial", Font.PLAIN, 12));
				semInc.add(lblIncertezzaEstesaUem, "cell 0 15,alignx trailing");

				incertezza_em = new JTextField();
				incertezza_em.setEditable(false);
				incertezza_em.setColumns(10);
				semInc.add(incertezza_em, "flowx,cell 1 15,growx");
				
								incertezza_em_sec = new JTextField();
								incertezza_em_sec.setEditable(false);
								incertezza_em_sec.setColumns(10);
								semInc.add(incertezza_em_sec, "flowx,cell 2 15,alignx left");

				JLabel lblIncertezzaDaAssociare = new JLabel("Incertezza da associare al valore medio di");
				lblIncertezzaDaAssociare.setFont(new Font("Arial", Font.PLAIN, 12));
				semInc.add(lblIncertezzaDaAssociare, "cell 0 17,alignx trailing");

				incertezza_um = new JTextField();
				incertezza_um.setEditable(false);
				incertezza_um.setColumns(10);
				semInc.add(incertezza_um, "flowx,cell 1 17,growx");
				
								JLabel lblNote = new JLabel("Note:");
								lblNote.setFont(new Font("Arial", Font.PLAIN, 12));
								semInc.add(lblNote, "cell 0 19,alignx right");

				textArea = new JTextArea();
				
				
				campo_misura_sec = new JTextField();
				campo_misura_sec.setEditable(false);
				campo_misura_sec.setColumns(10);
				semInc.add(campo_misura_sec, "cell 2 9,alignx left");
				
				JScrollPane scrollPaneNote = new JScrollPane(textArea);
				semInc.add(scrollPaneNote, "cell 1 19 3 1,grow");

				JLabel lblMmm_2 = new JLabel("mm/m");
				semInc.add(lblMmm_2, "cell 1 9");

				
				JLabel label_4 = new JLabel("mm/m");
				semInc.add(label_4, "cell 1 11");

				JLabel label_5 = new JLabel("mm/m");
				semInc.add(label_5, "cell 1 13");

				JLabel label_6 = new JLabel("mm/m");
				semInc.add(label_6, "cell 1 15");

				JLabel label_7 = new JLabel("mm/m");
				semInc.add(label_7, "cell 1 17");

				JLabel lblUnaDivisioneDella = new JLabel("una divisione della scala graduata Um:");
				lblUnaDivisioneDella.setFont(new Font("Arial", Font.PLAIN, 12));
				semInc.add(lblUnaDivisioneDella, "cell 0 18");

				LatMisuraDTO misura =GestioneMisuraBO.getMisuraLAT(SessionBO.idMisura);

				/*Riempo pannello se il campo riferimenti_incertezza !=null*/
				if(misura.getIncertezza_rif()!=null) 
				{
					comboBox_cmpRif.setSelectedItem(misura.getRif_campione());
					comboBox_cmpLav.setSelectedItem(misura.getRif_campione_lavoro());
					
					comboBox_stato.setSelectedItem(misura.getStato());
					comboBox_ammaccature.setSelectedItem(misura.getAmmaccature());
					comboBox_bolla_trasversale.setSelectedItem(misura.getBolla_trasversale());
					comboBox_regolazione.setSelectedItem(misura.getRegolazione());
					comboBox_centraggio.setSelectedItem(misura.getCentraggio());
					
					campo_misura.setText(misura.getCampo_misura().toPlainString());
					campo_misura_sec.setText(misura.getCampo_misura_sec().toPlainString());
					sensibilita.setText(misura.getSensibilita().toPlainString());
					
					incertezza_er.setText(misura.getIncertezza_rif().toString());
					incertezza_er_sec.setText(misura.getIncertezza_sec().toString());
					incertezza_em.setText(misura.getIncertezza_estesa().toString());
					incertezza_em_sec.setText(misura.getIncertezza_estesa_sec().toString());
					incertezza_um.setText(misura.getIncertezza_media().toString());
					
					textArea.setText(misura.getNote());
				}
				else 
				{
					comboBox_cmpRif.setSelectedItem("CDT054");
					comboBox_cmpLav.setSelectedItem("CDT001/2");
				}
				
				JButton btnNewButton = new JButton("Calcola");
				btnNewButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {

						if(campo_misura.getText().length()>0 && sensibilita.getText().length()>0) 
						{
							campo_misura_sec.setText(GestioneMisuraBO.getArcosec(campo_misura.getText()).setScale(Costanti.RISOLUZIONE_LIVELLA_BOLLA,RoundingMode.HALF_UP).toPlainString());

							incertezza_er_sec.setText(Costanti.INCERTEZZA_CDT001);
							
							incertezza_er.setText(GestioneMisuraBO.getArcosecInv(incertezza_er_sec.getText()).setScale(Costanti.RISOLUZIONE_LIVELLA_BOLLA+2,RoundingMode.HALF_UP).toPlainString());

							BigDecimal em=GestioneMisuraBO.getIncertezzaLivellaBolla_EM(GestioneMisuraBO.getArcosecInv(incertezza_er_sec.getText()),sensibilita.getText());

							incertezza_em.setText(em.setScale(Costanti.RISOLUZIONE_LIVELLA_BOLLA+2,RoundingMode.HALF_DOWN).toPlainString());

							incertezza_em_sec.setText(GestioneMisuraBO.getArcosec(em.toPlainString()).setScale(Costanti.RISOLUZIONE_LIVELLA_BOLLA,RoundingMode.HALF_DOWN).toPlainString());

							BigDecimal um=GestioneMisuraBO.getIncertezzaLivellaBolla_UM(em.toPlainString(), textField_scmax.getText());

							incertezza_um.setText(um.setScale(Costanti.RISOLUZIONE_LIVELLA_BOLLA+2,RoundingMode.HALF_DOWN).toPlainString());

						}else 
						{
							JOptionPane.showMessageDialog(null,"Compilare \"Campo Misura\" e \"Sensibilità\"","Attenzione",JOptionPane.WARNING_MESSAGE,new ImageIcon(PannelloTOP.class.getResource("/image/attention.png")));
						}

					}
				});
				
				btnNewButton.setFont(new Font("Arial", Font.BOLD, 12));
				btnNewButton.setIcon(new ImageIcon(PannelloLivellaBolla.class.getResource("/image/calcola.png")));
				semInc.add(btnNewButton, "cell 1 20,alignx left");
			}
											
										
			
							
							
											JLabel label_3 = new JLabel("''");
											semInc.add(label_3, "cell 2 9");
											
															JLabel label_1 = new JLabel("''");
															semInc.add(label_1, "cell 2 13");
															
																			JLabel label_2 = new JLabel("''");
																			semInc.add(label_2, "cell 2 15");
																			
																							JButton btnSalva = new JButton("Salva");
																							
																							btnSalva.setIcon(new ImageIcon(PannelloLivellaBolla.class.getResource("/image/save.png")));
																							btnSalva.setFont(new Font("Arial", Font.BOLD, 12));
																							semInc.add(btnSalva, "cell 2 20,growy");
																							
																							btnSalva.addActionListener(new ActionListener() {
																								public void actionPerformed(ActionEvent e) {
																									
																									try 
																									{
																									int scelta=	JOptionPane.showConfirmDialog(null,"Vuoi Salvare i dati ?","Salva",JOptionPane.YES_NO_OPTION,JOptionPane.INFORMATION_MESSAGE,new ImageIcon(PannelloTOP.class.getResource("/image/question.png")));
																									
																									if(scelta==0) {
																										
																									
																									boolean check=true;
																									StringBuffer sb = new StringBuffer();
																									
																									LatMisuraDTO lat = new LatMisuraDTO();
																									lat.setId(SessionBO.idMisura);
																									if(comboBox_cmpRif.getSelectedIndex()<1 || comboBox_cmpLav.getSelectedIndex()<1)
																									{
																										sb.append("* Selezionare Campioni riferimento/lavoro \n");
																										check=false;
																									}
																									
																								/*	if(stato.getText().length()<=0)
																									{
																										sb.append("* Indicare lo stato dello strumento \n");
																										check=false;
																									}*/
																									if(incertezza_er.getText().length()<=0)
																									{
																										sb.append("* Calcolare l'incertezza");
																										check=false;
																									}
																									
																									if(check) 
																									{
																										lat.setId(SessionBO.idMisura);
																										lat.setRif_campione(comboBox_cmpRif.getSelectedItem().toString());
																										lat.setRif_campione_lavoro(comboBox_cmpLav.getSelectedItem().toString());
																										
																										lat.setStato(comboBox_stato.getSelectedItem().toString());
																										lat.setAmmaccature(comboBox_ammaccature.getSelectedItem().toString());
																										lat.setBolla_trasversale(comboBox_bolla_trasversale.getSelectedItem().toString());
																										lat.setRegolazione(comboBox_regolazione.getSelectedItem().toString());
																										lat.setCentraggio(comboBox_centraggio.getSelectedItem().toString());
																										
																										lat.setCampo_misura(new BigDecimal(campo_misura.getText()).setScale(Costanti.RISOLUZIONE_LIVELLA_BOLLA+2,RoundingMode.HALF_UP));
																										lat.setCampo_misura_sec(new BigDecimal(campo_misura_sec.getText()).setScale(Costanti.RISOLUZIONE_LIVELLA_BOLLA,RoundingMode.HALF_UP));
																									
																										lat.setSensibilita(new BigDecimal(sensibilita.getText()).setScale(Costanti.RISOLUZIONE_LIVELLA_BOLLA+2,RoundingMode.HALF_UP));
																										
																										lat.setIncertezza_rif(new BigDecimal(incertezza_er.getText()).setScale(Costanti.RISOLUZIONE_LIVELLA_BOLLA+2,RoundingMode.HALF_UP));
																										lat.setIncertezza_sec(new BigDecimal(incertezza_er_sec.getText()).setScale(Costanti.RISOLUZIONE_LIVELLA_BOLLA,RoundingMode.HALF_UP));
																									
																										lat.setIncertezza_estesa(new BigDecimal(incertezza_em.getText()).setScale(Costanti.RISOLUZIONE_LIVELLA_BOLLA+2,RoundingMode.HALF_UP));
																										lat.setIncertezza_estesa_sec(new BigDecimal(incertezza_em_sec.getText()).setScale(Costanti.RISOLUZIONE_LIVELLA_BOLLA,RoundingMode.HALF_UP));
																									
																										lat.setIncertezza_media(new BigDecimal(incertezza_um.getText()).setScale(Costanti.RISOLUZIONE_LIVELLA_BOLLA+2,RoundingMode.HALF_UP));
																									
																										lat.setNote(textArea.getText());
																										
																										GestioneMisuraBO.updateRecordMisuraLAT(lat);
																										
																										JOptionPane.showMessageDialog(null,"Salvataggio Completato","Salva",JOptionPane.INFORMATION_MESSAGE,new ImageIcon(PannelloTOP.class.getResource("/image/confirm.png")));
																									}
																									else 
																									{
																										JOptionPane.showMessageDialog(null,sb,"Attenzione",JOptionPane.WARNING_MESSAGE,new ImageIcon(PannelloTOP.class.getResource("/image/attention.png")));
																									}
																								  }
																								}catch (Exception e1) {
																									e1.printStackTrace();
																								}}
																							});

		}catch (Exception e) {
			e.printStackTrace();
		}
		return semInc;
	}

	public class MyCellRenderer extends javax.swing.table.DefaultTableCellRenderer {



		public java.awt.Component getTableCellRendererComponent(javax.swing.JTable table, java.lang.Object value, boolean isSelected, boolean hasFocus, int row, int column) {

			final java.awt.Component cellComponent = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);


			if (column  == 1 ||column  == 3 ||column  == 4 ||column  == 7 ||column  ==8  ) 
			{
				cellComponent.setBackground(new Color(224,224,224));
				cellComponent.setForeground(Color.BLACK);

			}else 
			{
				cellComponent.setBackground(Color.white);
				cellComponent.setForeground(Color.BLACK);
			}

			
			if(column==16 || column==17) 
			{
				cellComponent.setBackground(Color.yellow);
				cellComponent.setForeground(Color.BLACK);
			}
			
			
			return cellComponent;

			
		}

	}

	private class PannelloDX extends JPanel implements TableModelListener,ActionListener
	{
		private RXTable tableDX,tableTratto;
		private String originalValue="";
		JLabel lblInserimentoNonValido;
		private JTextField s_media_field;
		private JTextField dev_st_field;
		JPanel semDex;
		private ModelTratto modelTratto;
		private ModelSemisc model;
		private JMenuItem jmit;

		PannelloDX()
		{
			semDex= new JPanel();

			BigDecimal s_mediaTotale = GestioneMisuraBO.getAverageLivella(listaPuntiDX,listaPuntiSX,2);

			semDex.setLayout(new MigLayout("", "[grow][][][][]", "[30px][:360px:410px][][][]"));

			tableDX = new RXTable();
			tableDX.setSelectAllForEdit(true);
			tableDX.setDefaultRenderer(Object.class, new MyCellRenderer());
			model = new ModelSemisc();

			PuntoLivellaBollaDTO punto =null;
			for (int i = 0; i <listaPuntiDX.size(); i++) {

				punto= listaPuntiDX.get(i);
				model.addRow(new Object[0]);
				model.setValueAt(punto.getRif_tacca(), i, 0);
				if( valutaConsistenzaPunto(punto)) 
				{
				model.setValueAt(punto.getValore_nominale_tratto().setScale(Costanti.RISOLUZIONE_LIVELLA_BOLLA,RoundingMode.HALF_UP), i, 1);
				model.setValueAt(punto.getValore_nominale_tratto_sec().setScale(Costanti.RISOLUZIONE_LIVELLA_BOLLA,RoundingMode.HALF_UP), i, 2);
				model.setValueAt(punto.getP1_andata().setScale(Costanti.RISOLUZIONE_LIVELLA_BOLLA,RoundingMode.HALF_UP), i, 3);
				model.setValueAt(punto.getP1_ritorno().setScale(Costanti.RISOLUZIONE_LIVELLA_BOLLA,RoundingMode.HALF_UP), i, 4);
				model.setValueAt(punto.getP1_media().setScale(Costanti.RISOLUZIONE_LIVELLA_BOLLA,RoundingMode.HALF_UP), i, 5);
				model.setValueAt(punto.getP1_diff().setScale(Costanti.RISOLUZIONE_LIVELLA_BOLLA,RoundingMode.HALF_UP), i, 6);
				model.setValueAt(punto.getP2_andata().setScale(Costanti.RISOLUZIONE_LIVELLA_BOLLA,RoundingMode.HALF_UP), i, 7);
				model.setValueAt(punto.getP2_ritorno().setScale(Costanti.RISOLUZIONE_LIVELLA_BOLLA,RoundingMode.HALF_UP), i, 8);
				model.setValueAt(punto.getP2_media().setScale(Costanti.RISOLUZIONE_LIVELLA_BOLLA,RoundingMode.HALF_UP), i, 9);
				model.setValueAt(punto.getP2_diff().setScale(Costanti.RISOLUZIONE_LIVELLA_BOLLA,RoundingMode.HALF_UP), i, 10);
				model.setValueAt(punto.getMedia().setScale(Costanti.RISOLUZIONE_LIVELLA_BOLLA,RoundingMode.HALF_UP), i, 11);
				model.setValueAt(punto.getErrore_cum().setScale(Costanti.RISOLUZIONE_LIVELLA_BOLLA+2,RoundingMode.HALF_UP), i, 12);
				model.setValueAt(punto.getMedia_corr_sec().setScale(Costanti.RISOLUZIONE_LIVELLA_BOLLA+2,RoundingMode.HALF_UP), i, 13);
				model.setValueAt(punto.getMedia_corr_mm().setScale(Costanti.RISOLUZIONE_LIVELLA_BOLLA+3,RoundingMode.HALF_UP), i, 14);
				model.setValueAt(punto.getDiv_dex().setScale(Costanti.RISOLUZIONE_LIVELLA_BOLLA+3,RoundingMode.HALF_UP), i, 15);
				if(punto.getCorr_boll_mm()!=null) 
				{
					model.setValueAt(punto.getCorr_boll_mm().setScale(Costanti.RISOLUZIONE_LIVELLA_BOLLA+3,RoundingMode.HALF_UP),i,16);
					model.setValueAt(punto.getCorr_boll_sec().setScale(Costanti.RISOLUZIONE_LIVELLA_BOLLA+3,RoundingMode.HALF_UP),i,17);
				}else 
				{
					model.setValueAt(BigDecimal.ZERO.setScale(Costanti.RISOLUZIONE_LIVELLA_BOLLA+3,RoundingMode.HALF_UP),i,16);
					model.setValueAt(BigDecimal.ZERO.setScale(Costanti.RISOLUZIONE_LIVELLA_BOLLA+3,RoundingMode.HALF_UP),i,17);
				}
				}
				model.setValueAt(punto.getId(), i, 18);
				
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

			JButton button = new JButton("Ricalcola");
			semDex.add(button, "cell 0 0");
			
			JPopupMenu popupMenu= new JPopupMenu();
			jmit= new JMenuItem("Elimina Riga");
			jmit.addActionListener(this);
			popupMenu.add(jmit);
			tableDX.setComponentPopupMenu(popupMenu);
			
			
			button.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
				
					for(int i=0;i<model.getRowCount();i++) 
					{
						if(model.getValueAt(i, 8)!=null) 
						{
							model.setValueAt(model.getValueAt(i, 8), i, 8);
						}
					}
					
				}
			});
			
			
			/*Tabella Tratto*/

			tableTratto = new RXTable();
			tableTratto.setSelectAllForEdit(true);
			modelTratto= new ModelTratto();
			tableTratto.setModel(modelTratto);
			for (int i = 0; i <listaPuntiDX.size(); i++) {
				punto= listaPuntiDX.get(i);
				modelTratto.addRow(new Object[0]);		
				modelTratto.setValueAt(punto.getRif_tacca(), i, 0);

				if( punto.getDiv_dex()!=null && punto.getDiv_dex().abs().compareTo(BigDecimal.ZERO)==1) 
				{
					modelTratto.setValueAt(punto.getDiv_dex().abs().setScale(Costanti.RISOLUZIONE_LIVELLA_BOLLA+3,RoundingMode.HALF_UP).toPlainString(), i, 1);
					modelTratto.setValueAt(punto.getDiv_dex().abs().setScale(Costanti.RISOLUZIONE_LIVELLA_BOLLA+3,RoundingMode.HALF_UP).subtract(s_mediaTotale.setScale(Costanti.RISOLUZIONE_LIVELLA_BOLLA+3,RoundingMode.HALF_UP)),i,2);
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
			s_media_field.setText(GestioneMisuraBO.getAverageLivella(listaPuntiDX, listaPuntiSX,0).setScale(Costanti.RISOLUZIONE_LIVELLA_BOLLA+3,RoundingMode.HALF_UP).toPlainString());
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
			dev_st_field.setText(GestioneMisuraBO.getDevStdLivella(listaPuntiDX, listaPuntiSX,0).setScale(Costanti.RISOLUZIONE_LIVELLA_BOLLA+3,RoundingMode.HALF_UP).toPlainString());
			semDex.add(dev_st_field, "cell 1 4");
			dev_st_field.setColumns(10);

			JLabel lblMmm_1 = new JLabel("mm/m");
			lblMmm_1.setFont(new Font("Arial", Font.BOLD, 12));
			semDex.add(lblMmm_1, "cell 2 4");


			


			
		}
		


public void actionPerformed(ActionEvent event) {
			
			JMenuItem menu = (JMenuItem) event.getSource();
			if (menu == jmit) {
	            eliminaRiga();
	        }
		}
		
		
		private void eliminaRiga() {
			try
			{
				int selectedRow = tableDX.getSelectedRow();
				if(selectedRow!=-1)
				{
					int indexPoint=Integer.parseInt(model.getValueAt(selectedRow,18).toString());
					GestioneMisuraBO.eliminaRigaLivellaABolla(indexPoint);
					
					JPanel panelDB =new PannelloLivellaBolla(0);
					SystemGUI.callPanel(panelDB, "PMT");
					
				}
				else
				{
					JOptionPane.showMessageDialog(null, "Selezionare correttamente la riga da eliminare","Attenzione",JOptionPane.WARNING_MESSAGE,new ImageIcon(PannelloTOP.class.getResource("/image/attention.png")));
				}
			}catch (Exception e) 
			{
				e.printStackTrace();
			}	
			
		}

		public JPanel get() {
			return semDex;
		}

		@Override
		public void tableChanged(TableModelEvent e) {

			int row = e.getFirstRow();
			int column=e.getColumn();


			TableModel model = (TableModel)e.getSource();
			int indexPoint=Integer.parseInt(model.getValueAt(row,18).toString());

			String value = model.getValueAt(row,column).toString();

			if(controllaNumero(model,value,row,column))
			{
				if(column==1) 
				{
					model.setValueAt(GestioneMisuraBO.getArcosec(value).setScale(Costanti.RISOLUZIONE_LIVELLA_BOLLA,RoundingMode.HALF_UP), row, 2);
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
						
						if(model.getValueAt(0,5)==null ) 
						{
						//	JOptionPane.showMessageDialog(null,"Indicare il punto a 0, completare l'intera riga","Attenzione",JOptionPane.WARNING_MESSAGE,new ImageIcon(PannelloTOP.class.getResource("/image/attention.png")));
							return;
						}

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

						
						if( model.getValueAt(0,9)!=null) {
						
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
						}

					/*Medie*/
					Object m1=model.getValueAt(row, 6);
					Object m2=model.getValueAt(row, 10);	 
					BigDecimal mediaTratto=impostaMedie(m1,m2,model,row);
					
					/*
					 *  Calcolo errore Cumulativo
					 *  
					 */
					if(mediaTratto!=null) 
					{
						BigDecimal err_cum=GestioneMisuraBO.getErroreCumulativo(mediaTratto,comboBox_cmpLav.getSelectedItem().toString());
					
					if(err_cum!=null) 
					{
						model.setValueAt(err_cum.setScale(Costanti.RISOLUZIONE_LIVELLA_BOLLA+2,RoundingMode.HALF_UP).toPlainString(), row, 12);
					}
					}
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
						BigDecimal erroreCum= new BigDecimal(col12.toString()).setScale(Costanti.RISOLUZIONE_LIVELLA_BOLLA+2,RoundingMode.HALF_UP);

						Object obj =model.getValueAt(row, 11);
						if(obj!=null) 
						{
							BigDecimal due=new BigDecimal(2).setScale(Costanti.RISOLUZIONE_LIVELLA_BOLLA+1,RoundingMode.HALF_UP);
							
							BigDecimal m1=new BigDecimal(model.getValueAt(row, 6).toString()).setScale(Costanti.RISOLUZIONE_LIVELLA_BOLLA+1,RoundingMode.HALF_UP);
							BigDecimal m2=new BigDecimal(model.getValueAt(row, 10).toString()).setScale(Costanti.RISOLUZIONE_LIVELLA_BOLLA+1,RoundingMode.HALF_UP);
							BigDecimal mediaTratto=m1.add(m2).divide(due,RoundingMode.HALF_UP);
							

							model.setValueAt(mediaTratto.subtract(erroreCum).setScale(Costanti.RISOLUZIONE_LIVELLA_BOLLA+2,RoundingMode.HALF_UP), row,13);

							BigDecimal avgArcsecInv=GestioneMisuraBO.getArcosecInv(mediaTratto.subtract(erroreCum).toPlainString());
							model.setValueAt(avgArcsecInv.setScale(Costanti.RISOLUZIONE_LIVELLA_BOLLA+3,RoundingMode.HALF_UP).toPlainString(), row,14);

							if(row==0) 
							{
								model.setValueAt("0.00",0,15);
							}else 
							{
								Object obj1 =model.getValueAt(row-1, 14);

								if(obj1!=null)
								{
								
									
									model.setValueAt(avgArcsecInv.subtract(new BigDecimal(obj1.toString())).setScale(Costanti.RISOLUZIONE_LIVELLA_BOLLA+3,RoundingMode.HALF_UP), row, 15);
									
									model.setValueAt((avgArcsecInv.subtract(new BigDecimal(model.getValueAt(row, 1).toString())).setScale(Costanti.RISOLUZIONE_LIVELLA_BOLLA+3,RoundingMode.HALF_UP)), row, 16);
									
									
									BigDecimal indicazione_bolla=GestioneMisuraBO.getArcosec(model.getValueAt(row, 1).toString());								
									
									model.setValueAt((mediaTratto.subtract(erroreCum)).subtract(indicazione_bolla).setScale(Costanti.RISOLUZIONE_LIVELLA_BOLLA+3,RoundingMode.HALF_UP), row, 17);
									

									/*Gestione Model Tratto e Medie*/

									BigDecimal val_1_div=avgArcsecInv.subtract(new BigDecimal(obj1.toString()));
									
									val_1_div=val_1_div.setScale(Costanti.RISOLUZIONE_LIVELLA_BOLLA+3,RoundingMode.HALF_UP);
									
									modelTratto.setValueAt(val_1_div, row, 1);
								
									listaPuntiDX.get(row).setDiv_dex( avgArcsecInv.subtract(new BigDecimal(obj1.toString())));
									
									s_media_field.setText(GestioneMisuraBO.getAverageLivella(listaPuntiDX, listaPuntiSX,0).setScale(Costanti.RISOLUZIONE_LIVELLA_BOLLA+3,RoundingMode.HALF_UP).toPlainString());
									dev_st_field.setText(GestioneMisuraBO.getDevStdLivella(listaPuntiDX, listaPuntiSX,0).setScale(Costanti.RISOLUZIONE_LIVELLA_BOLLA+3,RoundingMode.HALF_UP).toPlainString());
									
									BigDecimal s_mediaTotale = GestioneMisuraBO.getAverageLivella(listaPuntiDX,listaPuntiSX,2);

									for (int i = 0; i <listaPuntiDX.size(); i++) {
										PuntoLivellaBollaDTO punto= listaPuntiDX.get(i);
										if( punto.getDiv_dex()!=null && punto.getDiv_dex().abs().compareTo(BigDecimal.ZERO)==1) 
										{
											modelTratto.setValueAt((punto.getDiv_dex().abs()).setScale(Costanti.RISOLUZIONE_LIVELLA_BOLLA+3,RoundingMode.HALF_UP).toPlainString(), i, 1);
											modelTratto.setValueAt((punto.getDiv_dex().abs().setScale(Costanti.RISOLUZIONE_LIVELLA_BOLLA+3,RoundingMode.HALF_UP)).subtract(s_mediaTotale).setScale(Costanti.RISOLUZIONE_LIVELLA_BOLLA+3,RoundingMode.HALF_UP),i,2);
										}else 
										{
											modelTratto.setValueAt("", i, 2);
										}


									}
									
									
								}
					
								/*aggiorna s media e devstd*/
								
								textField_media_totale.setText(GestioneMisuraBO.getAverageLivella(listaPuntiDX, listaPuntiSX,2).setScale(Costanti.RISOLUZIONE_LIVELLA_BOLLA+3,RoundingMode.HALF_UP).toPlainString());
								textField_dev_std_totale.setText(GestioneMisuraBO.getDevStdLivella(listaPuntiDX, listaPuntiSX,2).setScale(Costanti.RISOLUZIONE_LIVELLA_BOLLA+3,RoundingMode.HALF_UP).toPlainString());
								textField_scmax.setText(GestioneMisuraBO.getScMaxLivella(listaPuntiDX, listaPuntiSX).setScale(Costanti.RISOLUZIONE_LIVELLA_BOLLA+3,RoundingMode.HALF_UP).toPlainString());
								
						
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
				punto.setErrore_cum(checkField(model.getValueAt(row, 12),Costanti.RISOLUZIONE_LIVELLA_BOLLA+2));
				punto.setMedia_corr_sec(checkField(model.getValueAt(row, 13),Costanti.RISOLUZIONE_LIVELLA_BOLLA+3));
				punto.setMedia_corr_mm(checkField(model.getValueAt(row, 14),Costanti.RISOLUZIONE_LIVELLA_BOLLA+3));
				punto.setDiv_dex(checkField(model.getValueAt(row, 15),Costanti.RISOLUZIONE_LIVELLA_BOLLA+3));
				punto.setCorr_boll_mm(checkField(model.getValueAt(row, 16),Costanti.RISOLUZIONE_LIVELLA_BOLLA+3));
				punto.setCorr_boll_sec(checkField(model.getValueAt(row, 17),Costanti.RISOLUZIONE_LIVELLA_BOLLA+3));

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

		private BigDecimal impostaMedie(Object m1, Object m2 ,TableModel model,int row) {

			BigDecimal mediaTratto=null;
			
			if(m1!=null &&m2!=null) 
			{
				BigDecimal bd_m1=new BigDecimal(m1.toString()).setScale(20);
				BigDecimal bd_m2=new BigDecimal(m2.toString()).setScale(20);

			    mediaTratto = bd_m1.add(bd_m2).divide(new BigDecimal(2),RoundingMode.HALF_UP);
				model.setValueAt(mediaTratto.setScale(Costanti.RISOLUZIONE_LIVELLA_BOLLA,RoundingMode.HALF_UP).toPlainString(),row,11);

				
			}
			return mediaTratto;
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
	
	private class PannelloSX extends JPanel implements TableModelListener,ActionListener
	{
		private RXTable tableSX,tableTrattoSX;
		private String originalValue="";
		JLabel lblInserimentoNonValido;
		private JTextField s_media_field;
		private JTextField dev_st_field;
		private ModelTratto modelTratto;
		private ModelSemisc model;
		private JPanel semSX;
		private JMenuItem jmit;

		PannelloSX()
		{
			semSX= new JPanel();

			BigDecimal s_mediaTotale = GestioneMisuraBO.getAverageLivella(listaPuntiDX,listaPuntiSX,2);

			semSX.setLayout(new MigLayout("", "[grow][][][][]", "[30px][:360px:410px][][][]"));

			tableSX = new RXTable();
			tableSX.setSelectAllForEdit(true);
			tableSX.setDefaultRenderer(Object.class, new MyCellRenderer());
			model = new ModelSemisc();

			PuntoLivellaBollaDTO punto =null;
			for (int i = 0; i <listaPuntiSX.size(); i++) {
				
				punto= listaPuntiSX.get(i);
				model.addRow(new Object[0]);
				
				model.setValueAt(punto.getRif_tacca(), i, 0);
				if(valutaConsistenzaPunto(punto)) 
				{
					model.setValueAt(punto.getValore_nominale_tratto().setScale(Costanti.RISOLUZIONE_LIVELLA_BOLLA,RoundingMode.HALF_UP), i, 1);
				
					model.setValueAt(punto.getValore_nominale_tratto_sec().setScale(Costanti.RISOLUZIONE_LIVELLA_BOLLA,RoundingMode.HALF_UP), i, 2);
				
				model.setValueAt(punto.getP1_andata().setScale(Costanti.RISOLUZIONE_LIVELLA_BOLLA,RoundingMode.HALF_UP), i, 3);
				model.setValueAt(punto.getP1_ritorno().setScale(Costanti.RISOLUZIONE_LIVELLA_BOLLA,RoundingMode.HALF_UP), i, 4);
				model.setValueAt(punto.getP1_media().setScale(Costanti.RISOLUZIONE_LIVELLA_BOLLA,RoundingMode.HALF_UP), i, 5);
				model.setValueAt(punto.getP1_diff().setScale(Costanti.RISOLUZIONE_LIVELLA_BOLLA,RoundingMode.HALF_UP), i, 6);
				model.setValueAt(punto.getP2_andata().setScale(Costanti.RISOLUZIONE_LIVELLA_BOLLA,RoundingMode.HALF_UP), i, 7);
				model.setValueAt(punto.getP2_ritorno().setScale(Costanti.RISOLUZIONE_LIVELLA_BOLLA,RoundingMode.HALF_UP), i, 8);
				model.setValueAt(punto.getP2_media().setScale(Costanti.RISOLUZIONE_LIVELLA_BOLLA,RoundingMode.HALF_UP), i, 9);
				model.setValueAt(punto.getP2_diff().setScale(Costanti.RISOLUZIONE_LIVELLA_BOLLA,RoundingMode.HALF_UP), i, 10);
				model.setValueAt(punto.getMedia().setScale(Costanti.RISOLUZIONE_LIVELLA_BOLLA,RoundingMode.HALF_UP), i, 11);
				model.setValueAt(punto.getErrore_cum().setScale(Costanti.RISOLUZIONE_LIVELLA_BOLLA+2,RoundingMode.HALF_UP), i, 12);
				model.setValueAt(punto.getMedia_corr_sec().setScale(Costanti.RISOLUZIONE_LIVELLA_BOLLA+2,RoundingMode.HALF_UP), i, 13);
				model.setValueAt(punto.getMedia_corr_mm().setScale(Costanti.RISOLUZIONE_LIVELLA_BOLLA+3,RoundingMode.HALF_UP), i, 14);
				model.setValueAt(punto.getDiv_dex().setScale(Costanti.RISOLUZIONE_LIVELLA_BOLLA+3,RoundingMode.HALF_UP), i, 15);
				if(punto.getCorr_boll_mm()!=null) 
				{
					model.setValueAt(punto.getCorr_boll_mm().setScale(Costanti.RISOLUZIONE_LIVELLA_BOLLA+3,RoundingMode.HALF_UP),i,16);
					model.setValueAt(punto.getCorr_boll_sec().setScale(Costanti.RISOLUZIONE_LIVELLA_BOLLA+3,RoundingMode.HALF_UP),i,17);
				}else 
				{
					model.setValueAt(BigDecimal.ZERO.setScale(Costanti.RISOLUZIONE_LIVELLA_BOLLA+3,RoundingMode.HALF_UP),i,16);
					model.setValueAt(BigDecimal.ZERO.setScale(Costanti.RISOLUZIONE_LIVELLA_BOLLA+3,RoundingMode.HALF_UP),i,17);
				}
				}
					model.setValueAt(punto.getId(), i, 18);
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
			
			
			JButton button = new JButton("Ricalcola");
			semSX.add(button, "cell 0 0");
			
			JLabel didascalia= new JLabel("Note: indicare i valori con il segno negativo (-)");
			didascalia.setFont(new Font("Arial", Font.BOLD, 12));
			semSX.add(didascalia,"cell 0 2 3 1 ,growx");

			JPopupMenu popupMenu= new JPopupMenu();
			jmit= new JMenuItem("Elimina Riga");
			jmit.addActionListener(this);
			popupMenu.add(jmit);
			tableSX.setComponentPopupMenu(popupMenu);
			
			button.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
				
					for(int i=0;i<model.getRowCount();i++) 
					{
						if(model.getValueAt(i, 8)!=null) 
						{
							model.setValueAt(model.getValueAt(i, 8), i, 8);
						}
					}
					
				}
			});
			
			/*Tabella Tratto*/

			tableTrattoSX = new RXTable();
			tableTrattoSX.setSelectAllForEdit(true);
			modelTratto= new ModelTratto();
			tableTrattoSX.setModel(modelTratto);
			for (int i = 0; i <listaPuntiSX.size(); i++) {
				punto= listaPuntiSX.get(i);
				modelTratto.addRow(new Object[0]);		
				modelTratto.setValueAt(punto.getRif_tacca(), i, 0);

				if( punto.getDiv_dex()!=null && punto.getDiv_dex().abs().compareTo(BigDecimal.ZERO)==1) 
				{
					modelTratto.setValueAt(punto.getDiv_dex().abs().setScale(Costanti.RISOLUZIONE_LIVELLA_BOLLA+3,RoundingMode.HALF_UP).toPlainString(), i, 1);
					modelTratto.setValueAt(punto.getDiv_dex().abs().setScale(Costanti.RISOLUZIONE_LIVELLA_BOLLA+3,RoundingMode.HALF_UP).subtract(s_mediaTotale.setScale(Costanti.RISOLUZIONE_LIVELLA_BOLLA+3,RoundingMode.HALF_UP)),i,2);
				}else 
				{
				//	modelTratto.setValueAt("   -   ", i, 1);
				//	modelTratto.setValueAt("   -   ", i, 2);
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
			s_media_field.setText(GestioneMisuraBO.getAverageLivella(listaPuntiDX, listaPuntiSX,1).setScale(Costanti.RISOLUZIONE_LIVELLA_BOLLA+3,RoundingMode.HALF_UP).toPlainString());
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
			dev_st_field.setText(GestioneMisuraBO.getDevStdLivella(listaPuntiDX, listaPuntiSX,1).setScale(Costanti.RISOLUZIONE_LIVELLA_BOLLA+3,RoundingMode.HALF_UP).toPlainString());
			semSX.add(dev_st_field, "cell 1 4");
			dev_st_field.setColumns(10);

			JLabel lblMmm_1 = new JLabel("mm/m");
			lblMmm_1.setFont(new Font("Arial", Font.BOLD, 12));
			semSX.add(lblMmm_1, "cell 2 4");



		
			
		}

		
		
		public void actionPerformed(ActionEvent event) {
			
			JMenuItem menu = (JMenuItem) event.getSource();
			if (menu == jmit) {
	            eliminaRiga();
	        }
		}
		
		
		private void eliminaRiga() {
			try
			{
				int selectedRow = tableSX.getSelectedRow();
				if(selectedRow!=-1)
				{
					int indexPoint=Integer.parseInt(model.getValueAt(selectedRow,18).toString());
					GestioneMisuraBO.eliminaRigaLivellaABolla(indexPoint);
					
					JPanel panelDB =new PannelloLivellaBolla(1);
					SystemGUI.callPanel(panelDB, "PMT");
					
				}
				else
				{
					JOptionPane.showMessageDialog(null, "Selezionare correttamente la riga da eliminare","Attenzione",JOptionPane.WARNING_MESSAGE,new ImageIcon(PannelloTOP.class.getResource("/image/attention.png")));
				}
			}catch (Exception e) 
			{
				e.printStackTrace();
			}	
			
		}


		public JPanel get() {
			return semSX;
		}

		@Override
		public void tableChanged(TableModelEvent e) {

			int row = e.getFirstRow();
			int column=e.getColumn();


			TableModel model = (TableModel)e.getSource();
			int indexPoint=Integer.parseInt(model.getValueAt(row,18).toString());

			String value = model.getValueAt(row,column).toString();

			if(controllaNumero(model,value,row,column))
			{
				if(column==1) 
				{
					model.setValueAt(GestioneMisuraBO.getArcosec(value).setScale(Costanti.RISOLUZIONE_LIVELLA_BOLLA,RoundingMode.HALF_UP), row, 2);
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

						if(model.getValueAt(0,5)==null ) 
						{
						//	JOptionPane.showMessageDialog(null,"Indicare il punto a 0, completare l'intera riga","Attenzione",JOptionPane.WARNING_MESSAGE,new ImageIcon(PannelloTOP.class.getResource("/image/attention.png")));
							return;
						}
						BigDecimal pivot= new BigDecimal( model.getValueAt(0,5).toString()).setScale(Costanti.RISOLUZIONE_LIVELLA_BOLLA,RoundingMode.HALF_UP);

						for (int i = 0; i <= 11; i++) 
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

						if( model.getValueAt(0,9)!=null) {
							
						
						BigDecimal pivot= new BigDecimal( model.getValueAt(0,9).toString()).setScale(Costanti.RISOLUZIONE_LIVELLA_BOLLA,RoundingMode.HALF_UP);

						for (int i = 0; i <= 11; i++) 
						{
							Object obj =model.getValueAt(i,9);

							if(obj!=null) 
							{
								BigDecimal bd_tratto= new BigDecimal(obj.toString()).setScale(Costanti.RISOLUZIONE_LIVELLA_BOLLA);
								model.setValueAt(bd_tratto.subtract(pivot).setScale(Costanti.RISOLUZIONE_LIVELLA_BOLLA), i, 10);
							}
						}
						}
					}

					/*Medie*/
					Object m1=model.getValueAt(row, 6);
					Object m2=model.getValueAt(row, 10);	 
					impostaMedie(m1,m2,model,row);
					
					BigDecimal mediaTratto=impostaMedie(m1,m2,model,row);
					
					/*
					 *  Calcolo errore Cumulativo
					 */
					if(mediaTratto!=null) 
					{
					BigDecimal err_cum=GestioneMisuraBO.getErroreCumulativo(mediaTratto,comboBox_cmpLav.getSelectedItem().toString());
					
						if(err_cum!=null) 
						{
							model.setValueAt(err_cum.setScale(Costanti.RISOLUZIONE_LIVELLA_BOLLA+2,RoundingMode.HALF_UP).toPlainString(), row, 12);
						}
					}
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
						BigDecimal erroreCum= new BigDecimal(col12.toString()).setScale(Costanti.RISOLUZIONE_LIVELLA_BOLLA+2,RoundingMode.HALF_UP);

						Object obj =model.getValueAt(row, 11);
						if(obj!=null) 
						{
							
							
							BigDecimal due=new BigDecimal(2).setScale(20);
							
							BigDecimal m1=new BigDecimal(model.getValueAt(row, 6).toString()).setScale(20);
							BigDecimal m2=new BigDecimal(model.getValueAt(row, 10).toString()).setScale(20);
							BigDecimal mediaTratto=m1.add(m2).divide(due,RoundingMode.HALF_UP);
							

							model.setValueAt(mediaTratto.subtract(erroreCum).setScale(Costanti.RISOLUZIONE_LIVELLA_BOLLA+2,RoundingMode.HALF_UP), row,13);

							BigDecimal avgArcsecInv=GestioneMisuraBO.getArcosecInv(mediaTratto.subtract(erroreCum).toPlainString());
							model.setValueAt(avgArcsecInv.setScale(Costanti.RISOLUZIONE_LIVELLA_BOLLA+3,RoundingMode.HALF_UP).toPlainString(), row,14);

							if(row==0) 
							{
								model.setValueAt("0.00",0,15);
							}else 
							{
								Object obj1 =model.getValueAt(row-1, 14);

								if(obj1!=null)
								{
									
									model.setValueAt(avgArcsecInv.subtract(new BigDecimal(obj1.toString())).setScale(Costanti.RISOLUZIONE_LIVELLA_BOLLA+3,RoundingMode.HALF_UP), row, 15);
									
									model.setValueAt((avgArcsecInv.subtract(new BigDecimal(model.getValueAt(row, 1).toString())).setScale(Costanti.RISOLUZIONE_LIVELLA_BOLLA+3,RoundingMode.HALF_UP)), row, 16);
									
									
									BigDecimal indicazione_bolla=GestioneMisuraBO.getArcosec(model.getValueAt(row, 1).toString());								
									
									model.setValueAt((mediaTratto.subtract(erroreCum)).subtract(indicazione_bolla).setScale(Costanti.RISOLUZIONE_LIVELLA_BOLLA+3,RoundingMode.HALF_UP), row, 17);
									
									
									/*Gestione Model Tratto e Medie*/

									BigDecimal val_1_div=avgArcsecInv.subtract(new BigDecimal(obj1.toString()));
									
									val_1_div=val_1_div.setScale(Costanti.RISOLUZIONE_LIVELLA_BOLLA+3,RoundingMode.HALF_UP);
									
									modelTratto.setValueAt(val_1_div, row, 1);
									
							
									listaPuntiSX.get(row).setDiv_dex( avgArcsecInv.subtract(new BigDecimal(obj1.toString())));
									s_media_field.setText(GestioneMisuraBO.getAverageLivella(listaPuntiDX, listaPuntiSX,1).setScale(Costanti.RISOLUZIONE_LIVELLA_BOLLA+3,RoundingMode.HALF_UP).toPlainString());
									dev_st_field.setText(GestioneMisuraBO.getDevStdLivella(listaPuntiDX, listaPuntiSX,1).setScale(Costanti.RISOLUZIONE_LIVELLA_BOLLA+3,RoundingMode.HALF_UP).toPlainString());

									BigDecimal s_mediaTotale = GestioneMisuraBO.getAverageLivella(listaPuntiDX,listaPuntiSX,2);
									

									for (int i = 0; i <listaPuntiSX.size(); i++) {
										PuntoLivellaBollaDTO punto= listaPuntiSX.get(i);
										if( punto.getDiv_dex()!=null && punto.getDiv_dex().abs().compareTo(BigDecimal.ZERO)==1) 
										{
											modelTratto.setValueAt((punto.getDiv_dex().abs()).setScale(Costanti.RISOLUZIONE_LIVELLA_BOLLA+3,RoundingMode.HALF_UP).toPlainString(), i, 1);
											modelTratto.setValueAt((punto.getDiv_dex().abs().subtract(s_mediaTotale)).setScale(Costanti.RISOLUZIONE_LIVELLA_BOLLA+3,RoundingMode.HALF_UP),i,2);
										}else 
										{
											modelTratto.setValueAt("", i, 2);
										}


									}
								}
								
								/*aggiorna s media e devstd*/
								
								textField_media_totale.setText(GestioneMisuraBO.getAverageLivella(listaPuntiDX, listaPuntiSX,2).setScale(Costanti.RISOLUZIONE_LIVELLA_BOLLA+3,RoundingMode.HALF_UP).toPlainString());
								textField_dev_std_totale.setText(GestioneMisuraBO.getDevStdLivella(listaPuntiDX, listaPuntiSX,2).setScale(Costanti.RISOLUZIONE_LIVELLA_BOLLA+3,RoundingMode.HALF_UP).toPlainString());
								textField_scmax.setText(GestioneMisuraBO.getScMaxLivella(listaPuntiDX, listaPuntiSX).setScale(Costanti.RISOLUZIONE_LIVELLA_BOLLA+3,RoundingMode.HALF_UP).toPlainString());
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
				punto.setErrore_cum(checkField(model.getValueAt(row, 12),Costanti.RISOLUZIONE_LIVELLA_BOLLA+1));
				punto.setMedia_corr_sec(checkField(model.getValueAt(row, 13),Costanti.RISOLUZIONE_LIVELLA_BOLLA+2));
				punto.setMedia_corr_mm(checkField(model.getValueAt(row, 14),Costanti.RISOLUZIONE_LIVELLA_BOLLA+2));
				punto.setDiv_dex(checkField(model.getValueAt(row, 15),Costanti.RISOLUZIONE_LIVELLA_BOLLA+3));
				punto.setCorr_boll_mm(checkField(model.getValueAt(row, 16),Costanti.RISOLUZIONE_LIVELLA_BOLLA+3));
				punto.setCorr_boll_sec(checkField(model.getValueAt(row, 17),Costanti.RISOLUZIONE_LIVELLA_BOLLA+3));

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

		private BigDecimal impostaMedie(Object m1, Object m2 ,TableModel model,int row) {

			BigDecimal mediaTratto=null;
			
			if(m1!=null &&m2!=null) 
			{
				BigDecimal bd_m1=new BigDecimal(m1.toString()).setScale(Costanti.RISOLUZIONE_LIVELLA_BOLLA,RoundingMode.HALF_UP);
				BigDecimal bd_m2=new BigDecimal(m2.toString()).setScale(Costanti.RISOLUZIONE_LIVELLA_BOLLA,RoundingMode.HALF_UP);

				 mediaTratto = bd_m1.add(bd_m2).divide(new BigDecimal(2),RoundingMode.HALF_UP);
				model.setValueAt(mediaTratto.toPlainString(),row,11);
			}
			return mediaTratto;

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

	private boolean valutaConsistenzaPunto(PuntoLivellaBollaDTO punto) {
		
		if(punto.getValore_nominale_tratto()==null)return false;
		if(punto.getValore_nominale_tratto_sec()==null)return false;
		if(punto.getP1_andata()==null)return false;
		if(punto.getP1_ritorno()==null)return false;
		if(punto.getP1_media()==null)return false;
		if(punto.getP1_diff()==null)return false;
		if(punto.getP2_andata()==null)return false;
		if(punto.getP2_ritorno()==null)return false;
		if(punto.getP2_media()==null)return false;
		if(punto.getP2_diff()==null)return false;
		if(punto.getMedia()==null)return false;
		if(punto.getErrore_cum()==null)return false;
		if(punto.getMedia_corr_sec()==null)return false;
		if(punto.getMedia_corr_mm()==null)return false;
		if(punto.getDiv_dex()==null)return false;
				
		return true;
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
			addColumn("corr bolla mm/m");
			addColumn("corr bolla sec");
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
			case 17:
				return String.class;
			case 18:
				return String.class;
			default:
				return String.class;
			}
		}

		@Override
		public boolean isCellEditable(int row, int column) {
			if(column==0 || column==2|| column==5|| column==6|| column==9|| column==10|| column==11 || column==12
					|| column==13|| column==14|| column==15 ||  column==16 || column==17 )
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


