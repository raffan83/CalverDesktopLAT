package it.calverDesktopLAT.gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import org.apache.commons.io.FilenameUtils;

import it.calverDesktopLAT.bo.GestioneMisuraBO;
import it.calverDesktopLAT.bo.GestioneRegistro;
import it.calverDesktopLAT.bo.SessionBO;
import it.calverDesktopLAT.dto.LatMassaAMB;
import it.calverDesktopLAT.dto.LatMassaAMB_SONDE;
import it.calverDesktopLAT.dto.LatMisuraDTO;
import it.calverDesktopLAT.dto.LatPuntoLivellaElettronicaDTO;
import it.calverDesktopLAT.dto.ParametroTaraturaDTO;
import it.calverDesktopLAT.dto.RegLinDTO;
import it.calverDesktopLAT.utl.Costanti;
import net.miginfocom.swing.MigLayout;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class PannelloMasse extends JPanel  {

	ArrayList<LatPuntoLivellaElettronicaDTO> listaPuntiLineari,listaIncertezze;
	ArrayList<ArrayList<LatPuntoLivellaElettronicaDTO>> listaPuntiRipetibili;
	private JTextField incertezza_em;
	private JTextArea textArea;
	private JTextField campo_misura;
	private JTextField sensibilita;
	private JComboBox comboBox_cmpRif;
	BigDecimal var_offset= null;
	ArrayList<ParametroTaraturaDTO> listaParametri;
	ArrayList<RegLinDTO> regressioneLineare;
	ModelCondizioniAmb model_condizionniAmb;
	ArrayList<String> listaTempi;
	LatMisuraDTO lat;
	private JTextField textField_temperatura_media;
	private JTextField textField_temperatura_media_variazione;
	private JTextField textField_ur_media;
	private JTextField textField_ur_media_variazione;
	private JTextField textField_pressione_media;
	private JTextField textField_pressione_media_variazione;
	private JTextField textField_pa_cipm;
	private JTextField textField_U_pa;
	private JTextField textField_U_form;
	private JTextField textField_delta_temp;
	private JTextField textField_incertezza_temp;
	private JTextField textField_p0;
	private JTextField textField_pa_no_cipm;
	private JTextField textField_delta_ur;
	private JTextField textField_incertezza_ur;
	private JTextField textField_delta_press;
	private JTextField textField_incertezza_press;

	public PannelloMasse(int index) {

		SessionBO.prevPage="PMM";

		try 
		{
			this.setLayout(new MigLayout("", "[grow]", "[grow]"));

			JScrollPane mainScroll = new JScrollPane();
			

			final JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
			

			JPanel pannelloMonitoraggioAmb = costruisciPannelloAmbientale();
			tabbedPane.addTab("Monitoraggio Ambientale", pannelloMonitoraggioAmb);
		

			tabbedPane.setSelectedIndex(index);
			
			double height=(SessionBO.heightFrame*73)/100;
			double width=(SessionBO.widthFrame*70)/100;
			
			tabbedPane.setPreferredSize(new Dimension((int)width-50,(int) height/2));
			mainScroll.setPreferredSize(new Dimension((int)width-50,(int) height/2));

			mainScroll.setViewportView(tabbedPane);
			
			add(mainScroll, "cell 0 0,grow");


		}catch 
		(Exception e) {
			e.printStackTrace();
		}
	}



	public JPanel costruisciPannelloAmbientale(){

		JPanel mainPanelMonitoraggioAmb= new JPanel();
		JTable tabellaCondizioniAmb;

		
			mainPanelMonitoraggioAmb.setBackground(Color.LIGHT_GRAY);

			try {

				mainPanelMonitoraggioAmb.setLayout(new MigLayout("", "[grow][grow]", "[60%][40%]"));

				JPanel pannelloTabCondizioni= new JPanel();
				pannelloTabCondizioni.setLayout(new MigLayout("", "[grow][pref!,grow]", "[:50:][grow]"));

				JPanel pannelloTabSonde= new JPanel();
				

				JPanel pannelloValori= new JPanel();


				mainPanelMonitoraggioAmb.add(pannelloTabCondizioni, "cell 0 0 1 2,grow");
				mainPanelMonitoraggioAmb.add(pannelloValori, "cell 1 0 ,grow");
				pannelloValori.setLayout(new MigLayout("", "[][grow][][][grow][][][grow][]", "[][][][][][][][][][][][][][][]"));
				
				JLabel lblNewLabel = new JLabel("Temp Media");
				lblNewLabel.setFont(new Font("Arial", Font.BOLD, 14));
				pannelloValori.add(lblNewLabel, "cell 0 1 2 1,alignx center");
				
				JLabel lblUrMedia = new JLabel("UR% Media");
				lblUrMedia.setFont(new Font("Arial", Font.BOLD, 14));
				pannelloValori.add(lblUrMedia, "cell 3 1 2 1,alignx center");
				
				JLabel lblPressMedia = new JLabel("Press Media");
				lblPressMedia.setFont(new Font("Arial", Font.BOLD, 14));
				pannelloValori.add(lblPressMedia, "cell 6 1 2 1,alignx center");
				
				JLabel lblC = new JLabel("C\u00B0");
				lblC.setFont(new Font("Arial", Font.BOLD, 12));
				pannelloValori.add(lblC, "cell 0 2,alignx trailing");
				
				textField_temperatura_media = new JTextField();
				textField_temperatura_media.setEditable(false);
				textField_temperatura_media.setFont(new Font("Arial", Font.BOLD, 12));
				pannelloValori.add(textField_temperatura_media, "cell 1 2");
				textField_temperatura_media.setColumns(10);
				
				JLabel lbldddddd = new JLabel("%");
				pannelloValori.add(lbldddddd, "cell 3 2,alignx trailing");
				
				textField_ur_media = new JTextField();
				textField_ur_media.setFont(new Font("Arial", Font.BOLD, 12));
				textField_ur_media.setEditable(false);
				textField_ur_media.setColumns(10);
				pannelloValori.add(textField_ur_media, "cell 4 2");
				
				JLabel lblMbar = new JLabel("mbar");
				lblMbar.setFont(new Font("Arial", Font.BOLD, 12));
				pannelloValori.add(lblMbar, "cell 6 2,alignx trailing");
				
				textField_pressione_media = new JTextField();
				textField_pressione_media.setFont(new Font("Arial", Font.BOLD, 12));
				textField_pressione_media.setEditable(false);
				textField_pressione_media.setColumns(10);
				pannelloValori.add(textField_pressione_media, "cell 7 2");
				
				JLabel label = new JLabel("\u00B1");
				label.setFont(new Font("Arial", Font.BOLD, 12));
				pannelloValori.add(label, "cell 0 3,alignx trailing");
				
				textField_temperatura_media_variazione = new JTextField();
				textField_temperatura_media_variazione.setFont(new Font("Arial", Font.BOLD, 12));
				textField_temperatura_media_variazione.setEditable(false);
				textField_temperatura_media_variazione.setColumns(10);
				pannelloValori.add(textField_temperatura_media_variazione, "cell 1 3");
				
				JLabel label_2 = new JLabel("\u00B1");
				label_2.setHorizontalAlignment(SwingConstants.RIGHT);
				label_2.setFont(new Font("Arial", Font.BOLD, 12));
				pannelloValori.add(label_2, "cell 3 3,alignx trailing");
				
				textField_ur_media_variazione = new JTextField();
				textField_ur_media_variazione.setFont(new Font("Arial", Font.BOLD, 12));
				textField_ur_media_variazione.setEditable(false);
				textField_ur_media_variazione.setColumns(10);
				pannelloValori.add(textField_ur_media_variazione, "cell 4 3");
				
				JLabel label_3 = new JLabel("\u00B1");
				label_3.setFont(new Font("Arial", Font.BOLD, 12));
				pannelloValori.add(label_3, "cell 6 3,alignx trailing");
				
				textField_pressione_media_variazione = new JTextField();
				textField_pressione_media_variazione.setFont(new Font("Arial", Font.BOLD, 12));
				textField_pressione_media_variazione.setEditable(false);
				textField_pressione_media_variazione.setColumns(10);
				pannelloValori.add(textField_pressione_media_variazione, "cell 7 3");
				
				JLabel lbla = new JLabel("\u03C1a");
				lbla.setFont(new Font("Arial", Font.BOLD | Font.ITALIC, 14));
				pannelloValori.add(lbla, "cell 0 5");
				
				JLabel lblUa = new JLabel("u(\u03C1a)");
				lblUa.setFont(new Font("Arial", Font.BOLD | Font.ITALIC, 14));
				pannelloValori.add(lblUa, "cell 0 6");
				
				textField_pa_cipm = new JTextField();
				textField_pa_cipm.setFont(new Font("Arial", Font.BOLD, 12));
				textField_pa_cipm.setEditable(false);
				textField_pa_cipm.setColumns(10);
				pannelloValori.add(textField_pa_cipm, "cell 1 5 7 1");
				
				textField_U_pa = new JTextField();
				textField_U_pa.setFont(new Font("Arial", Font.BOLD, 12));
				textField_U_pa.setEditable(false);
				textField_U_pa.setColumns(10);
				pannelloValori.add(textField_U_pa, "cell 1 6");
				
				JLabel lblUform = new JLabel("U form");
				lblUform.setFont(new Font("Arial", Font.BOLD | Font.ITALIC, 14));
				pannelloValori.add(lblUform, "cell 0 7,alignx trailing");
				
				textField_U_form = new JTextField();
				textField_U_form.setFont(new Font("Arial", Font.BOLD, 12));
				textField_U_form.setEditable(false);
				textField_U_form.setColumns(10);
				pannelloValori.add(textField_U_form, "cell 1 7");
				
				JLabel lblFormulaInUso = new JLabel("Formula in uso se le condizioni non discostano pi\u00F9 del 10%");
				lblFormulaInUso.setFont(new Font("Arial", Font.BOLD, 12));
				pannelloValori.add(lblFormulaInUso, "cell 0 9 8 1");
				
				JLabel lbltc = new JLabel("\u0394t /\u00B0C");
				lbltc.setFont(new Font("Arial", Font.BOLD, 12));
				pannelloValori.add(lbltc, "cell 0 11,alignx trailing");
				
				textField_delta_temp = new JTextField();
				textField_delta_temp.setFont(new Font("Arial", Font.BOLD, 12));
				textField_delta_temp.setEditable(false);
				textField_delta_temp.setColumns(10);
				pannelloValori.add(textField_delta_temp, "cell 1 11");
				
				JLabel lblhr = new JLabel("\u0394hr /%");
				lblhr.setFont(new Font("Arial", Font.BOLD, 12));
				pannelloValori.add(lblhr, "cell 3 11,alignx trailing");
				
				textField_delta_ur = new JTextField();
				textField_delta_ur.setFont(new Font("Arial", Font.BOLD, 12));
				textField_delta_ur.setEditable(false);
				textField_delta_ur.setColumns(10);
				pannelloValori.add(textField_delta_ur, "cell 4 11");
				
				JLabel lblppa = new JLabel("\u0394p /Pa");
				lblppa.setFont(new Font("Arial", Font.BOLD, 12));
				pannelloValori.add(lblppa, "cell 6 11,alignx trailing");
				
				textField_delta_press = new JTextField();
				textField_delta_press.setFont(new Font("Arial", Font.BOLD, 12));
				textField_delta_press.setEditable(false);
				textField_delta_press.setColumns(10);
				pannelloValori.add(textField_delta_press, "cell 7 11");
				
				JLabel lblUtc = new JLabel("u(t) /\u00B0C");
				lblUtc.setFont(new Font("Arial", Font.BOLD, 12));
				pannelloValori.add(lblUtc, "cell 0 12,alignx trailing");
				
				textField_incertezza_temp = new JTextField();
				textField_incertezza_temp.setFont(new Font("Arial", Font.BOLD, 12));
				textField_incertezza_temp.setEditable(false);
				textField_incertezza_temp.setColumns(10);
				pannelloValori.add(textField_incertezza_temp, "cell 1 12");
				
				JLabel lblUhr = new JLabel("u(hr) /%");
				lblUhr.setFont(new Font("Arial", Font.BOLD, 12));
				pannelloValori.add(lblUhr, "cell 3 12,alignx trailing");
				
				textField_incertezza_ur = new JTextField();
				textField_incertezza_ur.setFont(new Font("Arial", Font.BOLD, 12));
				textField_incertezza_ur.setEditable(false);
				textField_incertezza_ur.setColumns(10);
				pannelloValori.add(textField_incertezza_ur, "cell 4 12");
				
				JLabel lblUppa = new JLabel("u(p) /Pa");
				lblUppa.setFont(new Font("Arial", Font.BOLD, 12));
				pannelloValori.add(lblUppa, "cell 6 12,alignx trailing");
				
				textField_incertezza_press = new JTextField();
				textField_incertezza_press.setFont(new Font("Arial", Font.BOLD, 12));
				textField_incertezza_press.setEditable(false);
				textField_incertezza_press.setColumns(10);
				pannelloValori.add(textField_incertezza_press, "cell 7 12");
				
				JLabel label_5 = new JLabel("\u03C10");
				label_5.setFont(new Font("Arial", Font.BOLD | Font.ITALIC, 14));
				pannelloValori.add(label_5, "cell 0 13,alignx trailing");
				
				textField_p0 = new JTextField();
				textField_p0.setFont(new Font("Arial", Font.BOLD, 12));
				textField_p0.setEditable(false);
				textField_p0.setColumns(10);
				pannelloValori.add(textField_p0, "cell 1 13");
				
				JLabel lbla_1 = new JLabel("\u03C1a");
				lbla_1.setFont(new Font("Arial", Font.BOLD | Font.ITALIC, 14));
				pannelloValori.add(lbla_1, "cell 0 14,alignx trailing");
				
				textField_pa_no_cipm = new JTextField();
				textField_pa_no_cipm.setFont(new Font("Arial", Font.BOLD, 12));
				textField_pa_no_cipm.setEditable(false);
				textField_pa_no_cipm.setColumns(10);
				pannelloValori.add(textField_pa_no_cipm, "cell 1 14");
				mainPanelMonitoraggioAmb.add(pannelloTabSonde, "cell 1 1,grow");


				final JTextField textField_fileCaricamento = new JTextField();
				textField_fileCaricamento.setFont(new Font("Arial", Font.BOLD, 14));
				textField_fileCaricamento.setEditable(false);
				pannelloTabCondizioni.add(textField_fileCaricamento, "cell 0 0, width :350:");
				textField_fileCaricamento.setColumns(70);


				JButton btnCarica = new JButton("Carica");

				btnCarica.setIcon(new ImageIcon(PannelloLivellaElettronica.class.getResource("/image/load.png")));
				btnCarica.setFont(new Font("Arial", Font.BOLD, 14));

				btnCarica.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {

						 String last_path="";
		    				
		    				if(GestioneRegistro.esixt(Costanti.COD_LAST_PATH))
		    				{
		    					last_path=GestioneRegistro.getStringValue(Costanti.COD_LAST_PATH);
		    				}
		    				
						JFileChooser jfc = new JFileChooser(last_path);

						javax.swing.filechooser.FileFilter docFilter = new it.calverDesktopLAT.utl.FileTypeFilter(".pdf", "Documento PDF");
						jfc.addChoosableFileFilter(docFilter);
						jfc.showOpenDialog(GeneralGUI.g);
						
						File f= jfc.getSelectedFile();
						if(f!=null)
						{
							textField_fileCaricamento.setText(f.getPath());
							String ext1 = FilenameUtils.getExtension(f.getPath()); 
							if(ext1.equalsIgnoreCase("csv"))
							{

								try {

									listaTempi= new ArrayList<String>();

									BufferedReader br = Files.newBufferedReader(Paths.get(f.getPath()));
									String line;
									while ((line = br.readLine()) != null) 
									{
										listaTempi.add(line);
									}



								} catch (IOException e2) {
									System.err.println(e2.getMessage());
								}
							}
							else
							{
								JOptionPane.showMessageDialog(null,"Il sistema può caricare solo file in formato CSV","Exstension Error",JOptionPane.INFORMATION_MESSAGE,new ImageIcon(PannelloTOP.class.getResource("/image/error.png")));
							}

						}
					}

				});

				pannelloTabCondizioni.add(btnCarica, "cell 0 0 ,alignx left");



				JButton btnCalcola = new JButton("Calcola");

				btnCalcola.setIcon(new ImageIcon(PannelloLivellaElettronica.class.getResource("/image/calcola.png")));
				btnCalcola.setFont(new Font("Arial", Font.BOLD, 14));

				pannelloTabCondizioni.add(btnCalcola, "cell 0 0");

				tabellaCondizioniAmb = new JTable();
				tabellaCondizioniAmb.setDefaultRenderer(Object.class, new MyCellRenderer());
				model_condizionniAmb = new ModelCondizioniAmb();

				btnCalcola.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						try {
							if(listaTempi!=null && listaTempi.size()>0) 
							{
								GestioneMisuraBO.removeCondizioniAmbientali(SessionBO.idMisura);

								int rowCount = model_condizionniAmb.getRowCount();
								//Remove rows one by one from the end of the table
								for (int i = rowCount - 1; i >= 0; i--) {
									model_condizionniAmb.removeRow(i);
								}


								ArrayList<LatMassaAMB_SONDE> listaCorrezzioniSonde_tmp_1=GestioneMisuraBO.getListaCorrezioniSondeLAT(1);
								ArrayList<LatMassaAMB_SONDE> listaCorrezzioniSonde_tmp_2=GestioneMisuraBO.getListaCorrezioniSondeLAT(2);
								ArrayList<LatMassaAMB_SONDE> listaCorrezzioniSonde_tmp_3=GestioneMisuraBO.getListaCorrezioniSondeLAT(3);
								ArrayList<LatMassaAMB_SONDE> listaCorrezzioniSonde_umidita=GestioneMisuraBO.getListaCorrezioniSondeLAT(5);
								ArrayList<LatMassaAMB_SONDE> listaCorrezzioniSonde_pressione=GestioneMisuraBO.getListaCorrezioniSondeLAT(4);
								
								
								ArrayList<BigDecimal> listaValori_temp=new ArrayList<>();
								ArrayList<BigDecimal> listaValori_uhr=new ArrayList<>();
								ArrayList<BigDecimal> listaValori_press=new ArrayList<>();
							    BigDecimal mediaTemperatura=BigDecimal.ZERO;
							    BigDecimal mediaUHR=BigDecimal.ZERO;
							    BigDecimal mediaPressione=BigDecimal.ZERO;
							    
								for (int i = 0; i <listaTempi.size(); i++) {

									String[] data=listaTempi.get(i).split(";");

									model_condizionniAmb.addRow(new Object[0]);
									model_condizionniAmb.setValueAt(data[0].replaceAll(",", "."), i, 0);
									model_condizionniAmb.setValueAt(data[1].replaceAll(",", "."), i, 1);
									model_condizionniAmb.setValueAt(data[2].replaceAll(",", "."), i, 2);
									model_condizionniAmb.setValueAt(data[3].replaceAll(",", "."), i, 3);
									
									BigDecimal correzione = getCorrezioneSonda(data[1],listaCorrezzioniSonde_tmp_1);
									listaValori_temp.add(correzione);
									mediaTemperatura=mediaTemperatura.add(correzione);
									model_condizionniAmb.setValueAt(correzione.toPlainString(), i, 4);
						
									correzione = getCorrezioneSonda(data[2],listaCorrezzioniSonde_tmp_2);
									listaValori_temp.add(correzione);
									mediaTemperatura=mediaTemperatura.add(correzione);
									model_condizionniAmb.setValueAt(correzione.toPlainString(), i, 5);
									
									correzione = getCorrezioneSonda(data[3],listaCorrezzioniSonde_tmp_3);
									listaValori_temp.add(correzione);
									mediaTemperatura=mediaTemperatura.add(correzione);
									model_condizionniAmb.setValueAt(correzione.toPlainString(), i, 6);
									
									model_condizionniAmb.setValueAt(data[4].replaceAll(",", "."), i, 7);
									
									correzione = getCorrezioneSonda(data[4],listaCorrezzioniSonde_umidita);
									listaValori_uhr.add(correzione);
									mediaUHR=mediaUHR.add(correzione);
								    model_condizionniAmb.setValueAt(correzione.toPlainString(), i,8);
								    model_condizionniAmb.setValueAt(data[5].replaceAll(",", "."), i, 9);
								    
									correzione = getCorrezioneSonda(data[5],listaCorrezzioniSonde_pressione);
									listaValori_press.add(correzione);
									mediaPressione=mediaPressione.add(correzione);
								    model_condizionniAmb.setValueAt(correzione.toPlainString(), i,10);
								    model_condizionniAmb.setValueAt(i+1, i, 11);

								    
									
								}
						//		GestioneMisuraBO.insertCondizioniAmbientali(listaTempi,SessionBO.idMisura);
							
							    BigDecimal size=new BigDecimal(model_condizionniAmb.getRowCount());

							    /*Valori associati ai Valori medi di Temperatura / Umidità / Pressione*/
							    
							    BigDecimal medTemp =(mediaTemperatura.setScale(5).divide(size.multiply(new BigDecimal(3)),RoundingMode.HALF_UP));
							    textField_temperatura_media.setText(medTemp.toPlainString());
							    
							    BigDecimal medUHR =(mediaUHR.setScale(5).divide(size,RoundingMode.HALF_UP));
							    textField_ur_media.setText(medUHR.setScale(1,RoundingMode.HALF_UP).toPlainString());
							    
							    BigDecimal medPress=(mediaPressione.setScale(5).divide(size,RoundingMode.HALF_UP));
							    textField_pressione_media.setText(medPress.setScale(1,RoundingMode.HALF_UP).toPlainString());
							    
							    BigDecimal deltaT=medTemp.subtract(new BigDecimal(20.00000));
							    
							    BigDecimal deltaUr=medUHR.subtract(new BigDecimal(50.00000));
							    
							    BigDecimal deltaP=(medPress.subtract(new BigDecimal(1000))).multiply(new BigDecimal(100));
		
							    textField_delta_temp.setText(deltaT.setScale(5,RoundingMode.HALF_UP).toPlainString());
							    
							    textField_delta_ur.setText(deltaUr.setScale(5,RoundingMode.HALF_UP).toPlainString());
							    
							    textField_delta_press.setText(deltaP.setScale(5,RoundingMode.HALF_UP).toPlainString());
							   
							    /* Valori associati a +/- Media Temperatura/Umidità/Pressione*/
							    
							    double variazioneTemperatura= getVariazione(listaValori_temp,1);
							    
							    double variazioneUHR= getVariazione(listaValori_uhr,2);
	
							    double variazionePress= getVariazione(listaValori_press,3);
							    
							    textField_temperatura_media_variazione.setText(""+new BigDecimal(variazioneTemperatura).setScale(1,RoundingMode.HALF_UP));
							    
							    textField_ur_media_variazione.setText(""+new BigDecimal(variazioneUHR).setScale(1,RoundingMode.HALF_UP));
							    
							    textField_pressione_media_variazione.setText(""+new BigDecimal(variazionePress).setScale(1,RoundingMode.HALF_UP));
							    
							   
							    /*pa [kg/m3]*/
							    BigDecimal densita_aria=getDensita(medTemp,medUHR,medPress);
							    
							  
							    BigDecimal uForm = densita_aria.multiply(new BigDecimal(2)).multiply(new BigDecimal(0.0001));
							    
							    BigDecimal u=getIncertezzaDensita(medTemp,medUHR,medPress,uForm);
							    
							    textField_pa_cipm.setText(densita_aria.setScale(9,RoundingMode.HALF_UP).toPlainString());
							    
							    textField_U_form.setText(uForm.setScale(5,RoundingMode.HALF_UP).toPlainString());
							    
							    textField_U_pa.setText(u.setScale(4,RoundingMode.HALF_UP).toPlainString());
							    
							    
							    
							}
							
							
							
						} catch (Exception e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}

				});

				ArrayList<LatMassaAMB> listaAmb=GestioneMisuraBO.getListaCondizioniAmbientali(SessionBO.idMisura);
				LatMassaAMB punto=null;
								for (int i = 0; i <listaAmb.size(); i++) {
				
									punto= listaAmb.get(i);
									model_condizionniAmb.addRow(new Object[0]);
									model_condizionniAmb.setValueAt(punto.getData_ora() ,i, 0);
									model_condizionniAmb.setValueAt(punto.getCh1_temperatura(), i, 1);
									model_condizionniAmb.setValueAt(punto.getCh2_temperatura(), i, 2);
									model_condizionniAmb.setValueAt(punto.getCh3_temperatura(), i, 3);
									model_condizionniAmb.setValueAt(punto.getCh1_temperatura_corr(), i, 4);
									model_condizionniAmb.setValueAt(punto.getCh2_temperatura_corr(), i, 5);
									model_condizionniAmb.setValueAt(punto.getCh3_temperatura_corr(), i, 6);
									model_condizionniAmb.setValueAt(punto.getUmidita(), i,7);
									model_condizionniAmb.setValueAt(punto.getUmidita_corr(), i,8);
									model_condizionniAmb.setValueAt(punto.getPressione(), i,9);
									model_condizionniAmb.setValueAt(punto.getPressione_corretta(), i,10);
									model_condizionniAmb.setValueAt(punto.getId(), i,11);
									
						
									
								}

				tabellaCondizioniAmb.setModel(model_condizionniAmb);
				tabellaCondizioniAmb.setFont(new Font("Arial", Font.BOLD, 12));
				tabellaCondizioniAmb.getTableHeader().setFont(new Font("Arial", Font.BOLD, 10));
				tabellaCondizioniAmb.setRowHeight(25);


			//	model_condizionniAmb.addTableModelListener(this);

				TableColumn column = tabellaCondizioniAmb.getColumnModel().getColumn(tabellaCondizioniAmb.getColumnModel().getColumnIndex("index"));
			    tabellaCondizioniAmb.removeColumn(column);

				JScrollPane scrollTab = new JScrollPane(tabellaCondizioniAmb);
				pannelloTabCondizioni.add(scrollTab, "cell 0 1,grow");

				return mainPanelMonitoraggioAmb;

						
			}catch (Exception e1) 
			{
				e1.printStackTrace();
			}
			
			return mainPanelMonitoraggioAmb;

		}	

	private double getVariazione(ArrayList<BigDecimal> listaValori, int type ) {
		
		
		   BigDecimal devStd=GestioneMisuraBO.getDevStd(listaValori, null, 4);
			
		    double part_1 = Math.pow(devStd.doubleValue(), 2);
		    
		    double part_2=0;
		    
		    double part_3=0;
		    
		    if(type==1) 
		    {
			     part_2 = Math.pow((3*Costanti.RISOLUZIONE_RSG30_TEMP_RIS)/Math.sqrt(12),2);
			    
			     part_3 = Math.pow((3*(Costanti.RISOLUZIONE_RSG30_TEMP_RIS_U/2)),2);
		    
		    }
		    if(type==2) 
		    {
			     part_2 = Math.pow(Costanti.RISOLUZIONE_RSG30_UR_RIS/Math.sqrt(12),2);
			    
			     part_3 = Math.pow(Costanti.RISOLUZIONE_RSG30_UR_RIS_U/2,2);
		    
		    }
		    
		    if(type==3) 
		    {
			     part_2 = Math.pow(Costanti.RISOLUZIONE_RSG30_PRESS_RIS/Math.sqrt(12),2);
			    
			     part_3 = Math.pow(Costanti.RISOLUZIONE_RSG30_PRESS_RIS_U/2,2);
		    
		    }
		    
		    double variazione_temp=2*Math.sqrt(part_1+part_2+part_3);
		    
		    return variazione_temp;
	}

	private BigDecimal getCorrezioneSonda(String data,ArrayList<LatMassaAMB_SONDE> listaCorrezzioniSonde) {
		
		BigDecimal correzione=BigDecimal.ZERO;
		try 
		{
		BigDecimal pivot =new BigDecimal(data.replaceAll(",", "."));
		
		for (int i = 0; i < listaCorrezzioniSonde.size()-1; i++) 
		{
			
			LatMassaAMB_SONDE sondaINF= listaCorrezzioniSonde.get(i);
			LatMassaAMB_SONDE sondaSUP= listaCorrezzioniSonde.get(i+1);
			
			
			BigDecimal limiteInferiore=sondaINF.getIndicazione();
			BigDecimal limiteSuperiore=sondaSUP.getIndicazione();
			
			if(pivot.doubleValue()>limiteInferiore.doubleValue() && pivot.doubleValue()<=limiteSuperiore.doubleValue()) 
			{
				correzione= pivot.multiply(BigDecimal.ONE.subtract(sondaINF.getReg_lin_m())).subtract(sondaINF.getReg_lin_q());
			
				correzione=correzione.setScale(2, RoundingMode.HALF_UP);
				
			}
		}
			
		}catch (Exception e) 
		{
			e.printStackTrace();
			return BigDecimal.ZERO;
		}
		
		return correzione;
	}


	private BigDecimal getDensita(BigDecimal medTemp, BigDecimal medUHR, BigDecimal medPress) {
		
		BigDecimal pa=BigDecimal.ZERO;
		
		BigDecimal part_1=new BigDecimal(0.34848).multiply(medPress);
		
		double exp=Math.exp(new BigDecimal(0.0612).multiply(medTemp).doubleValue());
		
		BigDecimal part_2=new BigDecimal(0.009024).multiply(medUHR.multiply(new BigDecimal(exp)));
		
		pa=(part_1.subtract(part_2)).divide(new BigDecimal(273.15).add(medTemp),RoundingMode.HALF_UP);
		
		pa.setScale(9,RoundingMode.HALF_UP);
		
		return pa;
	}
	
	private BigDecimal getIncertezzaDensita(BigDecimal medTemp, BigDecimal medUHR,BigDecimal medPress, BigDecimal uForm) 
	{
		BigDecimal incertezza = null;
		
		
		BigDecimal derivataPressione=getDerivataPressione(medTemp);
		
		BigDecimal derivataTemperatura= getDerivataTemperatura(medTemp,medUHR,medPress);
		
		BigDecimal derivataUHR=getDerivataUmidita(medTemp);
		
		BigDecimal uT=new BigDecimal(Costanti.RISOLUZIONE_RSG30_TEMP_RIS_U/2);

		BigDecimal uP=new BigDecimal((Costanti.RISOLUZIONE_RSG30_PRESS_RIS_U*100)/2);
		
		BigDecimal uR=new BigDecimal(Costanti.RISOLUZIONE_RSG30_UR_RIS_U/2);
		
		textField_incertezza_temp.setText(uT.setScale(3,RoundingMode.HALF_UP).toPlainString());
		
		textField_incertezza_press.setText(uP.setScale(1,RoundingMode.HALF_UP).toPlainString());
		
		textField_incertezza_ur.setText(uR.setScale(1,RoundingMode.HALF_UP).toPlainString());
		
		
		BigDecimal u_part_1=new BigDecimal(Math.pow(derivataPressione.multiply(uP).doubleValue(),2));
		
		BigDecimal u_part_2=new BigDecimal(Math.pow(derivataTemperatura.multiply(uT).doubleValue(),2));
		
		BigDecimal u_part_3=new BigDecimal(Math.pow(derivataUHR.multiply(uR).doubleValue(),2));
		
		BigDecimal u_part_4=new BigDecimal(Math.pow(uForm.doubleValue(),2));
		
		incertezza=new BigDecimal (Math.sqrt(u_part_1.doubleValue()+u_part_2.doubleValue()+u_part_3.doubleValue()+u_part_4.doubleValue()));
		
		return incertezza;
	}
	

		private BigDecimal getDerivataUmidita(BigDecimal medTemp) {
		
			BigDecimal derivataUHR=new BigDecimal(-0.009024).multiply(new BigDecimal(Math.exp(0.0612*medTemp.doubleValue())));
			
			derivataUHR=derivataUHR.divide(new BigDecimal(273.15).add(medTemp),RoundingMode.HALF_UP);
			
		return derivataUHR;
	}



		private BigDecimal getDerivataTemperatura(BigDecimal medTemp, BigDecimal medUHR, BigDecimal medPress) {
		
			BigDecimal derivataTemperatura_part_1=new BigDecimal(-0.009024).multiply(medUHR).multiply(new BigDecimal(Math.exp(0.0612*medTemp.doubleValue())).multiply(new BigDecimal(0.0612)));
			
			derivataTemperatura_part_1=derivataTemperatura_part_1.divide(new BigDecimal(273.15).add(medTemp),RoundingMode.HALF_UP);
			
			BigDecimal derivataTemperartura_part_2=(new BigDecimal(0.34848).multiply(medPress)).subtract(new BigDecimal(0.009024).multiply(medUHR).multiply(new BigDecimal(Math.exp(0.0612*medTemp.doubleValue()))));
			
			derivataTemperartura_part_2=derivataTemperartura_part_2.divide(new BigDecimal(Math.pow(new BigDecimal(273.15).add(medTemp).doubleValue(), 2)),RoundingMode.HALF_UP);
			
			return derivataTemperatura_part_1.subtract(derivataTemperartura_part_2);
			
	}



		private BigDecimal getDerivataPressione(BigDecimal medTemp) {
			
		BigDecimal derivataPressione = new BigDecimal(0.34848).divide(new BigDecimal(273.15).add(medTemp),RoundingMode.HALF_UP);
			
		derivataPressione=derivataPressione.divide(new BigDecimal(100),RoundingMode.HALF_UP);	
			
		return derivataPressione;
	}



		private BigDecimal checkField(Object valueAt, int risoluzioneLivellaBolla) {

			if(valueAt!=null && !valueAt.toString().equals("")) 
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
	}



	

	/*	public class PannelloGrafico {

		JPanel chartPanel=null;

		public PannelloGrafico()
		{


			try 
			{
			//	panel_chart.setBorder(new LineBorder(Costanti.COLOR_RED, 2, true));
			//	panel_chart.setBackground(Color.WHITE);

					final XYSeriesCollection dataset= getDataSetChart(modelLin);
					JFreeChart xylineChart = ChartFactory.createXYLineChart(
							"Deriva Strumento", 
							"POM",
							"Value", 
							dataset,
							PlotOrientation.VERTICAL, 
							true, true, false);

			//		xylineChart.addSubtitle(new TextTitle("Strumento: "+strumento.getDenominazione()+ " Codice: "+strumento.getCodice_interno()+" Matricola :"+strumento.getMatricola()));
					xylineChart.setBackgroundPaint(Color.WHITE);

					XYPlot plot = xylineChart.getXYPlot();
					XYLineAndShapeRenderer rendu = new XYLineAndShapeRenderer( );


						rendu.setSeriesPaint(0, Costanti.COLOR_BLUE);
						rendu.setSeriesPaint(1, Color.MAGENTA);
						rendu.setSeriesPaint(2, Color.YELLOW);
						rendu.setBaseStroke(new BasicStroke(5));

					plot.setRenderer(rendu);
					xylineChart.getLegend().setFrame(BlockBorder.NONE);
					 chartPanel = new ChartPanel(xylineChart, false);

				//	chartPanel.setMouseWheelEnabled(true);
					chartPanel.setPreferredSize(new Dimension(700, 300));

			//		panel_chart.setPreferredSize(new Dimension(700,300));
			//		panel_chart.setViewportView(chartPanel);




			}catch (Exception e) {
				e.printStackTrace();
				PannelloConsole.printArea("Errore generazione grafico");
			}
		}


		public Component get() {
			return chartPanel;
		}
	}

	private XYSeriesCollection getDataSetChart(ModelProvaLineare model) throws Exception {

		XYSeriesCollection series = new XYSeriesCollection();

		final XYSeries lettura_andata = new XYSeries( "Lettura Andata" );
		final XYSeries lettura_ritorno = new XYSeries( "Lettura Ritorno" );
		final XYSeries media = new XYSeries( "Media" );



		for (int i=0;i<model.getRowCount();i++) 
		{
			Object andata = model.getValueAt(i, 2);
			Object sc_andata = model.getValueAt(i, 10);

			if(andata!=null && andata.toString().length()>0 && sc_andata!=null && sc_andata.toString().length()>0)
			{
				lettura_andata.add(new Double(andata.toString()),new Double(sc_andata.toString()));

			}

			Object ritorno = model.getValueAt(i, 4);
			Object sc_ritorno = model.getValueAt(i, 11);

			if(ritorno!=null && ritorno.toString().length()>0 && sc_ritorno!=null && sc_ritorno.toString().length()>0)
			{
				lettura_ritorno.add(new Double(ritorno.toString()),new Double(sc_ritorno.toString()));

			}

			if(andata!=null && andata.toString().length()>0 && sc_andata!=null && sc_andata.toString().length()>0 &&
					ritorno!=null && ritorno.toString().length()>0 && sc_ritorno!=null && sc_ritorno.toString().length()>0) 
			{
				Double media_g= (new Double(andata.toString())+new Double(ritorno.toString()))/2;
				Double media_g_sc= (new Double(sc_andata.toString())+new Double(sc_ritorno.toString()))/2;

				media.add(media_g,media_g_sc);
			}

		}
		series.addSeries(lettura_andata);
		series.addSeries(lettura_ritorno);
		series.addSeries(media);

		return series;
	}


	private class PannelloProvaLineare extends JPanel implements TableModelListener
	{
		private JTable tableProvaLineare;
		private String originalValue="";
		JLabel lblInserimentoNonValido;
		JPanel semDex;

		private JMenuItem jmit;

		PannelloProvaLineare()
		{
			semDex= new JPanel();


			semDex.setLayout(new MigLayout("", "[grow]", "[grow]"));

			tableProvaLineare = new JTable();
			tableProvaLineare.setDefaultRenderer(Object.class, new MyCellRenderer());
			modelLin = new ModelProvaLineare();

			LatPuntoLivellaElettronicaDTO punto =null;
			for (int i = 0; i <listaPuntiLineari.size(); i++) {

				punto= listaPuntiLineari.get(i);
				modelLin.addRow(new Object[0]);
				modelLin.setValueAt(punto.getPunto(), i, 0);
				modelLin.setValueAt(punto.getValore_nominale(), i, 1);
				modelLin.setValueAt(punto.getValore_andata_taratura(), i, 2);
				modelLin.setValueAt(punto.getValore_andata_campione(), i, 3);
				modelLin.setValueAt(punto.getValore_ritorno_taratura(), i, 4);
				modelLin.setValueAt(punto.getValore_ritorno_campione(), i, 5);
				modelLin.setValueAt(punto.getAndata_scostamento_campione(), i, 6);
				modelLin.setValueAt(punto.getAndata_correzione_campione(), i, 7);
				modelLin.setValueAt(punto.getRitorno_scostamento_campione(), i, 8);
				modelLin.setValueAt(punto.getRitorno_correzione_campione(), i, 9);
				modelLin.setValueAt(punto.getScostamentoA(), i, 10);
				modelLin.setValueAt(punto.getScostamentoB(), i, 11);
				modelLin.setValueAt(punto.getScostamentoMed(), i, 12);
				modelLin.setValueAt(punto.getScostamentoOff(), i, 13);
				modelLin.setValueAt(punto.getId(), i, 14);

			}

			if(modelLin.getValueAt( 10, 12)!=null && modelLin.getValueAt( 10, 12).toString().length()>0) 
			{
				var_offset=new BigDecimal(modelLin.getValueAt( 10, 12).toString());
			}else 
			{
				var_offset=new BigDecimal(textField_indicazione_corretta.getText());
			}

			modelLin.addTableModelListener(this);



			tableProvaLineare.setModel(modelLin);
			tableProvaLineare.setFont(new Font("Arial", Font.BOLD, 12));
			tableProvaLineare.getTableHeader().setFont(new Font("Arial", Font.BOLD, 10));
			tableProvaLineare.setRowHeight(25);

			TableColumn column = tableProvaLineare.getColumnModel().getColumn(tableProvaLineare.getColumnModel().getColumnIndex("index"));
			tableProvaLineare.removeColumn(column);

			JScrollPane scrollTab = new JScrollPane(tableProvaLineare);
			semDex.add(scrollTab, "cell 0 0 ,grow,height :450:500");





		}

		public JPanel get() {
			return semDex;
		}

		@Override
		public void tableChanged(TableModelEvent e) {

			int row = e.getFirstRow();
			int column=e.getColumn();

			try 
			{
			TableModel model = (TableModel)e.getSource();
			int indexPoint=Integer.parseInt(model.getValueAt(row,14).toString());

			String value = model.getValueAt(row,column).toString();



			if(controllaNumero(model,value,row,column))
			{

				if(column==3) 
				{
					scostamento_andata(row);

					if(model.getValueAt(row,5)!=null && model.getValueAt(row,5).toString().length()>0) 
					{
						column=5;
					}
				}
				if(column==5) 
				{
					scostamento_ritorno(row);
					scostamento_medio(row);
				}

				LatPuntoLivellaElettronicaDTO punto = new LatPuntoLivellaElettronicaDTO();
				punto.setId(indexPoint);
				punto.setIndicazione_iniziale(checkField(textField_indicazione_iniziale.getText(),Costanti.RISOLUZIONE_LIVELLA_ELETTRONICA));
				punto.setIndicazione_iniziale_corr(checkField(textField_indicazione_corretta.getText(),Costanti.RISOLUZIONE_LIVELLA_ELETTRONICA));
				punto.setInclinazione_cmp_campione(checkField(textField_inclinazione_cmp.getText(),Costanti.RISOLUZIONE_LIVELLA_ELETTRONICA));
				punto.setValore_nominale(checkField(model.getValueAt(row, 1),Costanti.RISOLUZIONE_LIVELLA_ELETTRONICA));
				punto.setValore_andata_taratura(checkField(model.getValueAt(row, 2),Costanti.RISOLUZIONE_LIVELLA_ELETTRONICA));
				punto.setValore_andata_campione(checkField(model.getValueAt(row, 3),Costanti.RISOLUZIONE_LIVELLA_ELETTRONICA));
				punto.setValore_ritorno_taratura(checkField(model.getValueAt(row, 4),Costanti.RISOLUZIONE_LIVELLA_ELETTRONICA));
				punto.setValore_ritorno_campione(checkField(model.getValueAt(row, 5),Costanti.RISOLUZIONE_LIVELLA_ELETTRONICA));
				punto.setAndata_scostamento_campione(checkField(model.getValueAt(row, 6),Costanti.RISOLUZIONE_LIVELLA_ELETTRONICA+1));
				punto.setAndata_correzione_campione(checkField(model.getValueAt(row, 7),Costanti.RISOLUZIONE_LIVELLA_ELETTRONICA+1));
				punto.setRitorno_scostamento_campione(checkField(model.getValueAt(row, 8),Costanti.RISOLUZIONE_LIVELLA_ELETTRONICA+1));
				punto.setRitorno_correzione_campione(checkField(model.getValueAt(row, 9),Costanti.RISOLUZIONE_LIVELLA_ELETTRONICA+1));
				punto.setScostamentoA(checkField(model.getValueAt(row, 10),Costanti.RISOLUZIONE_LIVELLA_ELETTRONICA+1));
				punto.setScostamentoB(checkField(model.getValueAt(row, 11),Costanti.RISOLUZIONE_LIVELLA_ELETTRONICA+1));
				punto.setScostamentoMed(checkField(model.getValueAt(row, 12),Costanti.RISOLUZIONE_LIVELLA_ELETTRONICA+1));
				punto.setScostamentoOff(checkField(model.getValueAt(row, 13),Costanti.RISOLUZIONE_LIVELLA_ELETTRONICA-1));

				GestioneMisuraBO.updateRecordPuntoLivellaElettronica(punto);

			}
			} catch (Exception e2) 
			{

			e2.printStackTrace();
			}	



		}

		private void scostamento_medio(int row) {

			BigDecimal scostamento_A=new BigDecimal(modelLin.getValueAt(row, 2).toString()).subtract(new BigDecimal(modelLin.getValueAt(row, 7).toString()));
			BigDecimal scostamento_B=new BigDecimal(modelLin.getValueAt(row, 4).toString()).subtract(new BigDecimal(modelLin.getValueAt(row, 9).toString()));

			modelLin.setValueAt(scostamento_A.toPlainString(), row, 10);
			modelLin.setValueAt(scostamento_B.toPlainString(), row, 11);

			BigDecimal media =(scostamento_A.add(scostamento_B)).divide(new BigDecimal(2),RoundingMode.HALF_UP);
			modelLin.setValueAt(media.toPlainString(), row, 12);


			if(modelLin.getValueAt(10, 12)!=null && modelLin.getValueAt(10, 12).toString().length()>0) 
			{
				var_offset=new BigDecimal(modelLin.getValueAt(10, 12).toString());
			}
			for (int i = 0; i < modelLin.getRowCount(); i++) 
			{
				if(modelLin.getValueAt(i, 12)!=null && modelLin.getValueAt(i, 12).toString().length()>0)
				{
					BigDecimal off_set =(new BigDecimal(modelLin.getValueAt(i, 12).toString()).subtract(var_offset));
					modelLin.setValueAt(off_set.toPlainString(), i, 13);
				}
			}


		}

		private void scostamento_ritorno(int row) throws Exception {



			if(regressioneLineare!=null)
			{

				BigDecimal ritorno_cmp=new BigDecimal(modelLin.getValueAt(row,5).toString()).setScale(Costanti.RISOLUZIONE_LIVELLA_ELETTRONICA);

				RegLinDTO regres=null;
				for (int i = 0; i <regressioneLineare.size()-1; i++) {

					if(ritorno_cmp.compareTo(regressioneLineare.get(i).getValore_misurato())>=0 && ritorno_cmp.compareTo(regressioneLineare.get(i+1).getValore_misurato())<=0 )
					{
						regres=regressioneLineare.get(i);
						break;
					}
				} 

				if(regres!=null) 
				{
					BigDecimal sc_cmp=ritorno_cmp.multiply(regres.getM()).add(regres.getQ());
					BigDecimal camp_corr= ritorno_cmp.add(sc_cmp.multiply(new BigDecimal(-1))).subtract(new BigDecimal(textField_indicazione_corretta.getText()));


					modelLin.setValueAt(sc_cmp.setScale(Costanti.RISOLUZIONE_LIVELLA_ELETTRONICA+1,RoundingMode.HALF_UP).toPlainString(), row, 8);
					modelLin.setValueAt(camp_corr.setScale(Costanti.RISOLUZIONE_LIVELLA_ELETTRONICA+1,RoundingMode.HALF_UP).toPlainString(), row, 9);

				}


			}else 
			{
				JOptionPane.showMessageDialog(null,"Il campione selezionato non ha i parametri necessari ad eseguire la misura", "Attenzione",JOptionPane.ERROR_MESSAGE);
			}

		}

		private void scostamento_andata(int row) throws Exception {

		try 
		{				
			if(regressioneLineare!=null)
			{


				BigDecimal andata_cmp=new BigDecimal(modelLin.getValueAt(row,3).toString()).setScale(Costanti.RISOLUZIONE_LIVELLA_ELETTRONICA+15);

				RegLinDTO regres=null;
				for (int i = 0; i <regressioneLineare.size()-1; i++) {
					if(andata_cmp.compareTo(regressioneLineare.get(i).getValore_misurato())>=0 && andata_cmp.compareTo(regressioneLineare.get(i+1).getValore_misurato())<=0 )
					{
						regres=regressioneLineare.get(i);
						break;
					}
				} 

				if(regres!=null) 
				{
					BigDecimal sc_cmp=andata_cmp.multiply(regres.getM()).add(regres.getQ());
					BigDecimal camp_corr= andata_cmp.add(sc_cmp.multiply(new BigDecimal(-1))).subtract(new BigDecimal(textField_indicazione_corretta.getText()));

					modelLin.setValueAt(sc_cmp.setScale(Costanti.RISOLUZIONE_LIVELLA_ELETTRONICA+1,RoundingMode.HALF_UP).toPlainString(), row, 6);
					modelLin.setValueAt(camp_corr.setScale(Costanti.RISOLUZIONE_LIVELLA_ELETTRONICA+1,RoundingMode.HALF_UP).toPlainString(), row, 7);

					BigDecimal scostamento_A=new BigDecimal(modelLin.getValueAt(row, 2).toString()).subtract(camp_corr);
					modelLin.setValueAt(scostamento_A.setScale(Costanti.RISOLUZIONE_LIVELLA_ELETTRONICA+1,RoundingMode.HALF_UP).toPlainString(), row, 10);
				}

			}else 
			{
				JOptionPane.showMessageDialog(null,"Il campione selezionato non ha i parametri necessari ad eseguire la misura", "Attenzione",JOptionPane.ERROR_MESSAGE);
			}

		}catch (Exception e) {
			throw e;
		}
		}

		private BigDecimal checkField(Object valueAt, int risoluzioneLivellaBolla) {

			if(valueAt!=null && !valueAt.toString().equals("")) 
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
			//	lblInserimentoNonValido.setVisible(true);
				return false;
			}




		}

	}
	class ModelProvaLineare extends DefaultTableModel {


		public ModelProvaLineare() {
			addColumn("Punto");
			addColumn("Valore Nominale");
			addColumn("[A] Taratura");
			addColumn("[A] Campione");
			addColumn("[R] Taratura");
			addColumn("[R] Campione");
			addColumn("[A]Sc. Campione");
			addColumn("[A]Camp Corretto");
			addColumn("[R]Sc. Campione");
			addColumn("[R]Camp Corretto");
			addColumn("Scost [A]");
			addColumn("Scost [R]");
			addColumn("Scost Medio");
			addColumn("Scost Offset");
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
			default:
				return String.class;
			}
		}

		@Override
		   public boolean isCellEditable(int row, int column) {
	            if (column <6) {
	            	System.out.println(column);
	                return true;
	            }
	            else {
	                return false;
	            }
	        }
	}

	class ModelProvaRipetibile extends DefaultTableModel {


		public ModelProvaRipetibile() {
			addColumn("Punto");
			addColumn("Valore Nominale");
			addColumn("[A - 1] Taratura");
			addColumn("[A - 1] Campione");
			addColumn("[R - 1] Taratura");
			addColumn("[R - 1] Campione");
			addColumn("[A - 2] Taratura");
			addColumn("[A - 2] Campione");
			addColumn("[R - 2] Taratura");
			addColumn("[R - 2] Campione");
			addColumn("[A - 3] Taratura");
			addColumn("[A - 3] Campione");
			addColumn("[R - 3] Taratura");
			addColumn("[R - 3] Campione");
			addColumn("[A - 4] Taratura");
			addColumn("[A - 4] Campione");
			addColumn("[R - 4] Taratura");
			addColumn("[R - 4] Campione");
			addColumn("[A - 5] Taratura");
			addColumn("[A - 5] Campione");
			addColumn("[R - 5] Taratura");
			addColumn("[R - 5] Campione");
			addColumn("Scarto Tipo");

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
			case 20:
				return String.class;
			case 21:
				return String.class;
			case 22:
				return String.class;
			case 23:
				return String.class;
			default:
				return String.class;
			}
		}

		@Override
		   public boolean isCellEditable(int row, int column) {
	        //    if (column <6) {
	         //   	System.out.println(column);
	                return true;
	         //   }
	          //  else {
	           //     return false;
	            //}
	        }
	}

	private class PannelloProvaRipetibile extends JPanel implements TableModelListener,ActionListener
	{
		private JTable tableProvaRipetibile;
		JLabel lblInserimentoNonValido;
		private String originalValue="";
		JPanel semDex;
		private ModelProvaRipetibile model;
		private JMenuItem jmit;

		PannelloProvaRipetibile()
		{
			semDex= new JPanel();


			semDex.setLayout(new MigLayout("", "[grow]", "[grow]"));

			tableProvaRipetibile = new JTable();
			tableProvaRipetibile.setDefaultRenderer(Object.class, new MyCellRendererRipetibilita());
			model = new ModelProvaRipetibile();


			ArrayList<LatPuntoLivellaElettronicaDTO> listaPunto=null;
			String[] indexL =new String[21]; 
			LatPuntoLivellaElettronicaDTO punto =null;
			for (int i = 0; i <listaPuntiRipetibili.size(); i++) {

				listaPunto=listaPuntiRipetibili.get(i);

				int index_column=i*4;
				for (int j = 0; j < listaPunto.size(); j++) 
				{

				punto= listaPunto.get(j);
				if(i==0) 
				{
					model.addRow(new Object[0]);
					model.setValueAt(punto.getPunto(), j, 0);
					model.setValueAt(punto.getValore_nominale(), j, 1);
					model.setValueAt(punto.getScarto_tipo(), j, 22);
				}
				model.setValueAt(punto.getValore_andata_taratura(), j, 2+index_column);
				model.setValueAt(punto.getValore_andata_campione(), j, 3+index_column);
				model.setValueAt(punto.getValore_ritorno_taratura(), j, 4+index_column);
				model.setValueAt(punto.getValore_ritorno_campione(), j, 5+index_column);

				//model.setValueAt(punto.getId(), i, 22);

				if(indexL[j]!=null) 
				{
				indexL[j]=indexL[j]+";"+punto.getId();
				}else 
				{
					indexL[j]=""+punto.getId();
				}


				}
			}

			for (int i = 0; i < indexL.length; i++) {
				model.setValueAt(indexL[i], i, 23);
			}


			model.addTableModelListener(this);

			tableProvaRipetibile.setModel(model);
			tableProvaRipetibile.setFont(new Font("Arial", Font.BOLD, 12));
			tableProvaRipetibile.getTableHeader().setFont(new Font("Arial", Font.BOLD, 10));
			tableProvaRipetibile.setRowHeight(25);

			TableColumn column = tableProvaRipetibile.getColumnModel().getColumn(tableProvaRipetibile.getColumnModel().getColumnIndex("index"));
			tableProvaRipetibile.removeColumn(column);

			JScrollPane scrollTab = new JScrollPane(tableProvaRipetibile);
			semDex.add(scrollTab, "cell 0 0 ,grow,height :450:500");

		}

		public JPanel get() {
			return semDex;
		}

		@Override
		public void tableChanged(TableModelEvent e) {

			int row = e.getFirstRow();
			int column=e.getColumn();

			try 
			{
			TableModel model = (TableModel)e.getSource();

			String strIDs=model.getValueAt(row,23).toString();
			int indexStrIDs=getIDsFromColumn(column);


			int indexPoint=Integer.parseInt(strIDs.split(";")[indexStrIDs]);

			String value = model.getValueAt(row,column).toString();

			if(controllaNumero(value,row,column))
			{
				if(column<22)
				{
					if(controllaRiga(model,row))
					{
						double[] listaScostamenti=getScostamenti(model,row);
						if(listaScostamenti!=null) 
						{

							new Statistic(listaScostamenti);
							BigDecimal inc_rip=new BigDecimal(Statistic.getStdDev());
							model.setValueAt(inc_rip.setScale(Costanti.RISOLUZIONE_LIVELLA_ELETTRONICA, RoundingMode.HALF_UP).toPlainString(), row, 22);
							BigDecimal inc_cmp=new BigDecimal(textField_inclinazione_cmp.getText());
							BigDecimal inc_ris=getRisInc();


							if(inc_ris!=null) 
							{
								BigDecimal inc_stab=getIncStab();
								model_incertezze.setValueAt(new BigDecimal(model.getValueAt(row, 1).toString()), row, 1);
								model_incertezze.setValueAt(inc_ris.setScale(Costanti.RISOLUZIONE_LIVELLA_ELETTRONICA+3, RoundingMode.HALF_UP).toPlainString(), row, 2);
								model_incertezze.setValueAt(inc_rip.setScale(Costanti.RISOLUZIONE_LIVELLA_ELETTRONICA+2, RoundingMode.HALF_UP).toPlainString(), row, 3);
								model_incertezze.setValueAt(inc_cmp.setScale(Costanti.RISOLUZIONE_LIVELLA_ELETTRONICA+1, RoundingMode.HALF_UP).toPlainString(), row, 4);
								model_incertezze.setValueAt(inc_stab.setScale(Costanti.RISOLUZIONE_LIVELLA_ELETTRONICA+1, RoundingMode.HALF_UP).toPlainString(), row, 5);

								BigDecimal sumContrib=inc_ris.pow(2).add(inc_rip.pow(2)).add(inc_cmp.pow(2)).add(inc_stab.pow(2));

								BigDecimal em= new BigDecimal(2).multiply(new BigDecimal(Math.sqrt(sumContrib.doubleValue())));

								model_incertezze.setValueAt(em.setScale(Costanti.RISOLUZIONE_LIVELLA_ELETTRONICA+1, RoundingMode.HALF_UP).toPlainString(), row, 6);

								setEmMax(model_incertezze);
							}
						}
					}
					else 
					{
						System.out.println("mancata presenza");
					}

				}
				int index_column=indexStrIDs*4;
				LatPuntoLivellaElettronicaDTO punto = new LatPuntoLivellaElettronicaDTO();
				punto.setId(indexPoint);
				punto.setValore_nominale(checkField(model.getValueAt(row, 1),Costanti.RISOLUZIONE_LIVELLA_ELETTRONICA));
				punto.setValore_andata_taratura(checkField(model.getValueAt(row, index_column+2),Costanti.RISOLUZIONE_LIVELLA_ELETTRONICA));
				punto.setValore_andata_campione(checkField(model.getValueAt(row, index_column+3),Costanti.RISOLUZIONE_LIVELLA_ELETTRONICA));
				punto.setValore_ritorno_taratura(checkField(model.getValueAt(row, index_column+4),Costanti.RISOLUZIONE_LIVELLA_ELETTRONICA));
				punto.setValore_ritorno_campione(checkField(model.getValueAt(row, index_column+5),Costanti.RISOLUZIONE_LIVELLA_ELETTRONICA));
				punto.setScarto_tipo(checkField(model.getValueAt(row, 22),Costanti.RISOLUZIONE_LIVELLA_ELETTRONICA));

				GestioneMisuraBO.updateRecordPuntoLivellaElettronica(punto);

		}
			} catch (Exception e2) 
			{

			e2.printStackTrace();
			}	



		}


		private void setEmMax(ModelIncertezze model_incertezze) throws Exception {

			BigDecimal ex=BigDecimal.ZERO;

			for (int i=0;i<model_incertezze.getRowCount();i++) {

				if(model_incertezze.getValueAt(i, 6)!=null)
				{
					BigDecimal em_model=new BigDecimal(model_incertezze.getValueAt(i, 6).toString());

					if(em_model.compareTo(ex)>0) 
					{
						ex=em_model;
						incertezza_em.setText(ex.setScale(Costanti.RISOLUZIONE_LIVELLA_ELETTRONICA-1,RoundingMode.HALF_UP).toPlainString());
					}
				}		
			}

			lat = new LatMisuraDTO();
			lat.setId(SessionBO.idMisura);
			lat.setRif_campione(comboBox_cmpRif.getSelectedItem().toString());

			lat.setCampo_misura(new BigDecimal(campo_misura.getText()).setScale(Costanti.RISOLUZIONE_LIVELLA_BOLLA+2,RoundingMode.HALF_UP));

			lat.setSensibilita(new BigDecimal(sensibilita.getText()).setScale(Costanti.RISOLUZIONE_LIVELLA_BOLLA+2,RoundingMode.HALF_UP));

			lat.setIncertezza_estesa(ex.setScale(Costanti.RISOLUZIONE_LIVELLA_ELETTRONICA-1,RoundingMode.HALF_UP));
			lat.setNote(textArea.getText());

			GestioneMisuraBO.updateRecordMisuraLAT(lat);

		}

		private BigDecimal getIncStab() {

			BigDecimal inc_stab= BigDecimal.ZERO;

			if(listaParametri!=null) 
			{

				for (ParametroTaraturaDTO listaParam : listaParametri) {

					BigDecimal sc1 = listaParam.getValoreTaratura().subtract(listaParam.getValore_nominale()).setScale(10, RoundingMode.HALF_UP);

					BigDecimal val=(sc1.subtract(listaParam.getScostamentoPrecedente()).abs()).divide(new BigDecimal("3.46410161513775"),RoundingMode.HALF_UP);

					if(val.compareTo(inc_stab)>0)
					{
						inc_stab=val;
					}
				}
			}



			return inc_stab;
		}

		private BigDecimal getRisInc() {
			BigDecimal ris=null;
			try 
			{
				ris =new BigDecimal(sensibilita.getText()).setScale(10);

				ris=ris.divide(new BigDecimal("2").multiply(new BigDecimal("1.73205080756888")),RoundingMode.HALF_UP);

			}catch (NumberFormatException e) {

				JOptionPane.showMessageDialog(null,"Il campo risoluzione non è settato correttamente", "Attenzione",JOptionPane.ERROR_MESSAGE);
			}

			return ris;
		}

		private double[] getScostamenti(TableModel model2, int row) throws Exception {

			double[] listaScostamenti = new double[10];

			int indexArray=0;

			for (int j = 2; j <=21; j=j+2) {

				BigDecimal valTaratura=new BigDecimal(model.getValueAt(row, j).toString());
				BigDecimal valCampione=new BigDecimal(model.getValueAt(row, j+1).toString());


			if(regressioneLineare!=null )
			{


				RegLinDTO regres=null;
				for (int i = 0; i <regressioneLineare.size()-1; i++) {
					if(valCampione.compareTo(regressioneLineare.get(i).getValore_misurato())>=0 && valCampione.compareTo(regressioneLineare.get(i+1).getValore_misurato())<=0 )
					{
						regres=regressioneLineare.get(i);
						break;
					}
				} 

				if(regres!=null) 
				{
					BigDecimal sc_cmp=valCampione.multiply(regres.getM()).add(regres.getQ());
					BigDecimal camp_corr= valCampione.add(sc_cmp.multiply(new BigDecimal(-1))).subtract(new BigDecimal(textField_indicazione_iniziale.getText())).setScale(Costanti.RISOLUZIONE_LIVELLA_ELETTRONICA+1, RoundingMode.HALF_UP);
					listaScostamenti[indexArray]=valTaratura.subtract(camp_corr).doubleValue();
					indexArray++;

				}


			}else 
			{
				JOptionPane.showMessageDialog(null,"Il campione selezionato non ha i parametri necessari ad eseguire la misura", "Attenzione",JOptionPane.ERROR_MESSAGE);
				return null;
			}

			}
			return listaScostamenti;
		}

		private boolean controllaRiga(TableModel modelC, int row) {

			for (int i = 0; i < modelC.getColumnCount()-2; i++) {

				Object obj =modelC.getValueAt(row, i);
				if(obj==null || controllaNumero(obj.toString(), row, i)==false)
				{
					return false;
				}
			}
			return true;
		}

		private int getIDsFromColumn(int column) {

			int id=0;
			 if(column  == 6 ||column==7 || column  == 8 ||column==9 )
			{
				id=1;
			}
			else if(column  == 10 ||column==11 || column  == 12 ||column==13 )
			{
				id=2;
			}
			else if(column  == 14 ||column==15 || column  == 16 ||column==17 )
			{
				id=3;
			}
			else if(column  == 18 ||column==19 || column  == 20 ||column==21 )
			{
				id=4;
			}


			return id;
		}		

		private void scostamento_andata(int row) throws Exception {

		try 
		{	

			if(listaParametri==null || listaParametri.size()==0)
			{
				listaParametri=GestioneCampioneBO.getParametriTaratura(comboBox_cmpRif.getSelectedItem().toString());
			}

			if(listaParametri!=null && listaParametri.size()==21)
			{
				 regressioneLineare= GestioneMisuraBO.getListaRegressioneLineare(listaParametri);

				BigDecimal andata_cmp=new BigDecimal(model.getValueAt(row,3).toString()).setScale(Costanti.RISOLUZIONE_LIVELLA_ELETTRONICA+15);

				RegLinDTO regres=null;
				for (int i = 0; i <regressioneLineare.size()-1; i++) {
					if(andata_cmp.compareTo(regressioneLineare.get(i).getValore_misurato())>=0 && andata_cmp.compareTo(regressioneLineare.get(i+1).getValore_misurato())<=0 )
					{
						regres=regressioneLineare.get(i);
						break;
					}
				} 

				if(regres!=null) 
				{
					BigDecimal sc_cmp=andata_cmp.multiply(regres.getM()).add(regres.getQ());
					BigDecimal camp_corr= andata_cmp.add(sc_cmp.multiply(new BigDecimal(-1))).subtract(new BigDecimal(textField_indicazione_corretta.getText()));

					model.setValueAt(sc_cmp.setScale(Costanti.RISOLUZIONE_LIVELLA_ELETTRONICA+1,RoundingMode.HALF_UP).toPlainString(), row, 6);
					model.setValueAt(camp_corr.setScale(Costanti.RISOLUZIONE_LIVELLA_ELETTRONICA+1,RoundingMode.HALF_UP).toPlainString(), row, 7);

				}

			}else 
			{
				JOptionPane.showMessageDialog(null,"Il campione selezionato non ha i parametri necessari ad eseguire la misura", "Attenzione",JOptionPane.ERROR_MESSAGE);
			}

		}catch (Exception e) {
			throw e;
		}
		}

		private BigDecimal checkField(Object valueAt, int risoluzioneLivellaBolla) {

			if(valueAt!=null && !valueAt.toString().equals("")) 
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

		private boolean controllaNumero(String val,int row, int column) {

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
			//	lblInserimentoNonValido.setVisible(true);
				return false;
			}




		}


		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub

		}

	}
	 */
	
	 class MyCellRenderer extends javax.swing.table.DefaultTableCellRenderer {



		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public java.awt.Component getTableCellRendererComponent(javax.swing.JTable table, java.lang.Object value, boolean isSelected, boolean hasFocus, int row, int column) {

			final java.awt.Component cellComponent = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);


			if (row%2==0) 
			{
				cellComponent.setBackground(new Color(255,255,255));
				cellComponent.setForeground(Color.BLACK);

			}
			else
			{
				cellComponent.setBackground(Color.LIGHT_GRAY);
				cellComponent.setForeground(Color.BLACK);
			}

			return cellComponent;


		}
	}
	
	  class MyCellRendererRipetibilita extends javax.swing.table.DefaultTableCellRenderer {



			public java.awt.Component getTableCellRendererComponent(javax.swing.JTable table, java.lang.Object value, boolean isSelected, boolean hasFocus, int row, int column) {

				final java.awt.Component cellComponent = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);


				if(column  == 2 ||column==3 || column  == 4 ||column==5 )
				{
					cellComponent.setBackground(new Color(255,255,153));
					cellComponent.setForeground(Color.BLACK);
				}
				else if(column  == 6 ||column==7 || column  == 8 ||column==9 )
				{
					cellComponent.setBackground(new Color(204,255,153));
					cellComponent.setForeground(Color.BLACK);
				}
				else if(column  == 10 ||column==11 || column  == 12 ||column==13 )
				{
					cellComponent.setBackground(new Color(153,255,255));
					cellComponent.setForeground(Color.BLACK);
				}
				else if(column  == 14 ||column==15 || column  == 16 ||column==17 )
				{
					cellComponent.setBackground(new Color(255,153,153));
					cellComponent.setForeground(Color.BLACK);
				}
				else if(column  == 18 ||column==19 || column  == 20 ||column==21 )
				{
					cellComponent.setBackground(new Color(224,224,224));
					cellComponent.setForeground(Color.BLACK);
				}
				else 
				{
					cellComponent.setBackground(Color.white);
					cellComponent.setForeground(Color.BLACK);
				}

				return cellComponent;


			}
		}
	 
	class ModelCondizioniAmb extends DefaultTableModel {


		public ModelCondizioniAmb() {
			addColumn("DATA / ORA");
			addColumn("CH1 TEMP");
			addColumn("CH2 TEMP");
			addColumn("CH3 TEMP");
			addColumn("CH1 TEMP CORR");
			addColumn("CH2 TEMP CORR");
			addColumn("CH3 TEMP CORR");
			addColumn("UHR");
			addColumn("UHR CORR");
			addColumn("PRESS");
			addColumn("PRESS CORR");

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
			default:
				return String.class;
			}
		}

		@Override
		public boolean isCellEditable(int row, int column) {

			return false;

		}


	}



