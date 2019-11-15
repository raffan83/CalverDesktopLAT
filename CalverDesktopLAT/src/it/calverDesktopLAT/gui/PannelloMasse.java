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

import it.calverDesktopLAT.bo.GestioneCampioneBO;
import it.calverDesktopLAT.bo.GestioneMisuraBO;
import it.calverDesktopLAT.bo.GestioneRegistro;
import it.calverDesktopLAT.bo.GestioneStrumentoBO;
import it.calverDesktopLAT.bo.SessionBO;
import it.calverDesktopLAT.dao.SQLiteDAO;
import it.calverDesktopLAT.dto.LatMassaAMB;
import it.calverDesktopLAT.dto.LatMassaAMB_DATA;
import it.calverDesktopLAT.dto.LatMassaAMB_SONDE;
import it.calverDesktopLAT.dto.LatMisuraDTO;
import it.calverDesktopLAT.dto.LatPuntoLivellaElettronicaDTO;
import it.calverDesktopLAT.dto.ParametroTaraturaDTO;
import it.calverDesktopLAT.dto.RegLinDTO;
import it.calverDesktopLAT.utl.Costanti;
import jdk.nashorn.internal.ir.CatchNode;
import net.miginfocom.swing.MigLayout;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.DefaultComboBoxModel;

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
	ModelEffettoMagnetico model_condizionniEffMag;
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
	private JTextField textField_pa_no_cipm;
	private JTextField textField_delta_ur;
	private JTextField textField_incertezza_ur;
	private JTextField textField_delta_press;
	private JTextField textField_incertezza_press;
	private JTextField textField_l1;
	private JTextField textField_l2;
	private JTextField textField_val_taratura_param;

	public PannelloMasse(int index) {

		SessionBO.prevPage="PMM";

		try 
		{
			this.setLayout(new MigLayout("", "[grow]", "[grow]"));

			JScrollPane mainScroll = new JScrollPane();


			final JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);


			JPanel pannelloMonitoraggioAmb = costruisciPannelloAmbientale();
			tabbedPane.addTab("Monitoraggio Ambientale", pannelloMonitoraggioAmb);

			JPanel pannelloEffettoMagnetico= costruisciPannelloEffettoMagnetico();
			tabbedPane.addTab("EffettoMagnetico", pannelloEffettoMagnetico);


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



	private JPanel costruisciPannelloEffettoMagnetico() {
		JPanel mainPanelEffettoMagnetico= new JPanel();

		JTable tabellaEffettoMagnetico;

		mainPanelEffettoMagnetico.setBackground(Color.LIGHT_GRAY);

		try 
		{
			mainPanelEffettoMagnetico.setLayout(new MigLayout("", "[grow]", "[30%,grow][grow]"));
			model_condizionniEffMag = new ModelEffettoMagnetico();


			JPanel pannelloInserimentoValori= new JPanel();
			pannelloInserimentoValori.setBackground(Color.WHITE);
			pannelloInserimentoValori.setLayout(new MigLayout("", "[][::10px][][::10px][][::10px][][::10px][][::10px][][grow]", "[grow][][grow][][grow][][grow][][grow][][grow][][grow]"));
			mainPanelEffettoMagnetico.add(pannelloInserimentoValori, "cell 0 0,grow");

			JLabel lblComparatore = new JLabel("Comparatore");
			lblComparatore.setFont(new Font("Arial", Font.BOLD, 14));
			pannelloInserimentoValori.add(lblComparatore, "flowx,cell 0 0,alignx trailing");

			JComboBox comboBox_comparatore = new JComboBox(GestioneCampioneBO.getListaCampioniCompleta());
			comboBox_comparatore.setFont(new Font("Arial", Font.PLAIN, 14));
			pannelloInserimentoValori.add(comboBox_comparatore, "cell 2 0,growx");

			JLabel lblCampione = new JLabel("Campione");
			lblCampione.setFont(new Font("Arial", Font.BOLD, 14));
			pannelloInserimentoValori.add(lblCampione, "cell 0 2,alignx trailing");

			final JComboBox comboBox_campione = new JComboBox(GestioneCampioneBO.getListaCampioniCompleta());
			comboBox_campione.setFont(new Font("Arial", Font.PLAIN, 14));
			pannelloInserimentoValori.add(comboBox_campione, "cell 2 2,growx");

			JLabel lblValoreNominale = new JLabel("Parametro");
			lblValoreNominale.setFont(new Font("Arial", Font.BOLD, 14));
			pannelloInserimentoValori.add(lblValoreNominale, "cell 4 2,alignx trailing");

			final JComboBox comboBox_valore_nominale = new JComboBox();
			comboBox_valore_nominale.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
				}
			});
			comboBox_valore_nominale.setFont(new Font("Arial", Font.PLAIN, 14));
			pannelloInserimentoValori.add(comboBox_valore_nominale, "cell 6 2,width :150:");
			
			JLabel lblValoreTaratura = new JLabel("Valore Taratura");
			lblValoreTaratura.setFont(new Font("Arial", Font.BOLD, 14));
			pannelloInserimentoValori.add(lblValoreTaratura, "cell 8 2,alignx trailing");
			
			textField_val_taratura_param = new JTextField();
			textField_val_taratura_param.setFont(new Font("Arial", Font.PLAIN, 14));
			textField_val_taratura_param.setEditable(false);
			pannelloInserimentoValori.add(textField_val_taratura_param, "cell 10 2,growx");
			textField_val_taratura_param.setColumns(10);

			JLabel lblLetturaL_1 = new JLabel("Lettura L1");
			lblLetturaL_1.setFont(new Font("Arial", Font.BOLD, 14));
			pannelloInserimentoValori.add(lblLetturaL_1, "cell 0 4,alignx trailing");

			textField_l1 = new JTextField();
			textField_l1.setFont(new Font("Arial", Font.PLAIN, 14));
			pannelloInserimentoValori.add(textField_l1, "cell 2 4,width : 150:");
			textField_l1.setColumns(10);

			JLabel lblLetturaL = new JLabel("Lettura L2");
			lblLetturaL.setFont(new Font("Arial", Font.BOLD, 14));
			pannelloInserimentoValori.add(lblLetturaL, "cell 0 6,alignx trailing");

			textField_l2 = new JTextField();
			textField_l2.setFont(new Font("Arial", Font.PLAIN, 14));
			textField_l2.setColumns(10);
			pannelloInserimentoValori.add(textField_l2, "cell 2 6,width :150:");

			JLabel lblClasseCampione = new JLabel("Classe Campione");
			lblClasseCampione.setHorizontalAlignment(SwingConstants.TRAILING);
			lblClasseCampione.setFont(new Font("Arial", Font.BOLD, 14));
			pannelloInserimentoValori.add(lblClasseCampione, "cell 0 8,alignx trailing");

			JComboBox comboBox_classe_campione = new JComboBox();
			comboBox_classe_campione.setFont(new Font("Arial", Font.PLAIN, 14));
			pannelloInserimentoValori.add(comboBox_classe_campione, "cell 2 8,growx");

			JLabel lblUClasseCampione = new JLabel("(U) Classe Campione");
			lblUClasseCampione.setHorizontalAlignment(SwingConstants.TRAILING);
			lblUClasseCampione.setFont(new Font("Arial", Font.BOLD, 14));
			pannelloInserimentoValori.add(lblUClasseCampione, "cell 4 8,alignx trailing");

			JComboBox comboBox_u_classe_campione = new JComboBox();
			comboBox_u_classe_campione.setFont(new Font("Arial", Font.PLAIN, 14));
			comboBox_u_classe_campione.setModel(new DefaultComboBoxModel(new String[] {"Seleziona ...", "70", "85", "300"}));
			pannelloInserimentoValori.add(comboBox_u_classe_campione, "cell 6 8,growx");

			JLabel lblClasseTaratura = new JLabel("Classe Taratura");
			lblClasseTaratura.setHorizontalAlignment(SwingConstants.TRAILING);
			lblClasseTaratura.setFont(new Font("Arial", Font.BOLD, 14));
			pannelloInserimentoValori.add(lblClasseTaratura, "cell 0 10,alignx trailing");

			JComboBox comboBox_classe_taratura = new JComboBox();
			comboBox_classe_taratura.setFont(new Font("Arial", Font.PLAIN, 14));
			pannelloInserimentoValori.add(comboBox_classe_taratura, "cell 2 10,growx");

			JLabel lblUClasseTaratura = new JLabel("(U) Classe Taratura");
			lblUClasseTaratura.setHorizontalAlignment(SwingConstants.TRAILING);
			lblUClasseTaratura.setFont(new Font("Arial", Font.BOLD, 14));
			pannelloInserimentoValori.add(lblUClasseTaratura, "cell 4 10,alignx trailing");

			JComboBox comboBox_u_classe_taratura = new JComboBox();
			comboBox_u_classe_taratura.setFont(new Font("Arial", Font.PLAIN, 14));
			comboBox_u_classe_taratura.setModel(new DefaultComboBoxModel(new String[] {"Seleziona ...", "70", "85", "300"}));
			pannelloInserimentoValori.add(comboBox_u_classe_taratura, "cell 6 10,growx");

			JButton btnCalcolaInserisci = new JButton("Calcola & Inserisci");
			btnCalcolaInserisci.setIcon(new ImageIcon(PannelloMasse.class.getResource("/image/calcola.png")));
			btnCalcolaInserisci.setFont(new Font("Arial", Font.BOLD, 14));
			pannelloInserimentoValori.add(btnCalcolaInserisci, "cell 0 12 7 1,alignx center");

			JPanel pannelloTabEffettoMag= new JPanel();
			pannelloTabEffettoMag.setBackground(Color.WHITE);
			pannelloTabEffettoMag.setLayout(new MigLayout("", "[grow]", "[grow]"));


			mainPanelEffettoMagnetico.add(pannelloTabEffettoMag, "cell 0 1,grow");


			tabellaEffettoMagnetico = new JTable();
			tabellaEffettoMagnetico.setDefaultRenderer(Object.class, new MyCellRenderer());


			tabellaEffettoMagnetico.setModel(model_condizionniEffMag);
			tabellaEffettoMagnetico.setFont(new Font("Arial", Font.BOLD, 12));
			tabellaEffettoMagnetico.getTableHeader().setFont(new Font("Arial", Font.BOLD, 10));
			tabellaEffettoMagnetico.setRowHeight(25);


			//	model_condizionniAmb.addTableModelListener(this);

			TableColumn column = tabellaEffettoMagnetico.getColumnModel().getColumn(tabellaEffettoMagnetico.getColumnModel().getColumnIndex("index"));
			tabellaEffettoMagnetico.removeColumn(column);

			JScrollPane scrollTab = new JScrollPane(tabellaEffettoMagnetico);
			pannelloTabEffettoMag.add(scrollTab, "cell 0 0,grow");


			comboBox_campione.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
				
					try 
					{
					if(comboBox_campione.getSelectedIndex()!=0)
					{
						String codiceCampione=comboBox_campione.getSelectedItem().toString();

						String[] listaParametriTaratura=GestioneCampioneBO.getParametriTaraturaTotali(codiceCampione);

						comboBox_valore_nominale.removeAllItems();

						for(String str : listaParametriTaratura) {


							comboBox_valore_nominale.addItem(str);
						}
					}
				 }
					catch (Exception ex) {
					ex.printStackTrace();
				}
				}
			});
			
			comboBox_valore_nominale.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					
					try 
					{
					if(comboBox_valore_nominale.getSelectedIndex()!=0)
					{
						String codiceParametro=comboBox_valore_nominale.getSelectedItem().toString();
						
						String codiceCampione=comboBox_campione.getSelectedItem().toString();

						ArrayList<ParametroTaraturaDTO> listaParametriTaratura=GestioneCampioneBO.getParametriTaratura(codiceCampione);

						

						for(ParametroTaraturaDTO param : listaParametriTaratura) {


							if(param.getDescrizioneParametro().equals(codiceParametro)) 
							{
								textField_val_taratura_param.setText(param.getValoreTaratura().toPlainString());
							}
						}
					}
				 }
					catch (Exception ex) {
					ex.printStackTrace();
				}
					
				}
			});

		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}

		return mainPanelEffettoMagnetico;
	}



	public JPanel costruisciPannelloAmbientale(){

		JPanel mainPanelMonitoraggioAmb= new JPanel();
		JTable tabellaCondizioniAmb;


		mainPanelMonitoraggioAmb.setBackground(Color.LIGHT_GRAY);

		try {

			mainPanelMonitoraggioAmb.setLayout(new MigLayout("", "[grow][grow]", "[60%][40%]"));

			JPanel pannelloTabCondizioni= new JPanel();
			pannelloTabCondizioni.setBackground(Color.WHITE);
			pannelloTabCondizioni.setLayout(new MigLayout("", "[grow][pref!,grow]", "[:50:][grow]"));

			//	JPanel pannelloTabSonde= new JPanel();

			JPanel pannelloValori= new JPanel();
			pannelloValori.setBackground(Color.WHITE);

			LatMassaAMB_DATA datiCondizioniAmbinetali=GestioneMisuraBO.getCondizioniAmbientaliDati(SessionBO.idMisura);

			mainPanelMonitoraggioAmb.add(pannelloTabCondizioni, "cell 0 0 1 2,grow");
			mainPanelMonitoraggioAmb.add(pannelloValori, "cell 1 0 1 2,grow");
			pannelloValori.setLayout(new MigLayout("", "[][grow][][][grow][][][grow][]", "[][][][][][25px:n][][15px:n][][15px:n][][25px:n][][25px:n][][15px:n][][15px:n][][]"));

			JLabel lblInformazioniCondizioniAmbientali = new JLabel("Informazioni Condizioni Ambientali");
			lblInformazioniCondizioniAmbientali.setFont(new Font("Arial", Font.BOLD | Font.ITALIC, 18));
			pannelloValori.add(lblInformazioniCondizioniAmbientali, "cell 0 0 9 1,alignx center");

			JLabel lblNewLabel = new JLabel("Temp Media");
			lblNewLabel.setFont(new Font("Arial", Font.BOLD, 14));
			pannelloValori.add(lblNewLabel, "cell 1 2,alignx center");

			JLabel lblUrMedia = new JLabel("UR% Media");
			lblUrMedia.setFont(new Font("Arial", Font.BOLD, 14));
			pannelloValori.add(lblUrMedia, "cell 4 2,alignx center");

			JLabel lblPressMedia = new JLabel("Press Media");
			lblPressMedia.setFont(new Font("Arial", Font.BOLD, 14));
			pannelloValori.add(lblPressMedia, "cell 7 2,alignx center");

			JLabel lblC = new JLabel("C\u00B0");
			lblC.setFont(new Font("Arial", Font.BOLD, 14));
			pannelloValori.add(lblC, "cell 0 3,alignx trailing");

			textField_temperatura_media = new JTextField();
			textField_temperatura_media.setEditable(false);
			textField_temperatura_media.setFont(new Font("Arial", Font.BOLD, 12));
			pannelloValori.add(textField_temperatura_media, "cell 1 3");
			textField_temperatura_media.setColumns(10);

			if(datiCondizioniAmbinetali.getMedia_temperatura()!=null) 
			{
				textField_temperatura_media.setText(datiCondizioniAmbinetali.getMedia_temperatura().setScale(5,RoundingMode.HALF_UP).toPlainString());
			}


			JLabel lbldddddd = new JLabel("%");
			lbldddddd.setFont(new Font("Arial", Font.BOLD, 14));
			pannelloValori.add(lbldddddd, "cell 3 3,alignx trailing");

			textField_ur_media = new JTextField();
			textField_ur_media.setFont(new Font("Arial", Font.BOLD, 12));
			textField_ur_media.setEditable(false);
			textField_ur_media.setColumns(10);
			pannelloValori.add(textField_ur_media, "cell 4 3");

			if(datiCondizioniAmbinetali.getMedia_umidita()!=null) 
			{
				textField_ur_media.setText(datiCondizioniAmbinetali.getMedia_umidita().setScale(1,RoundingMode.HALF_UP).toPlainString());
			}

			JLabel lblMbar = new JLabel("mbar");
			lblMbar.setFont(new Font("Arial", Font.BOLD, 14));
			pannelloValori.add(lblMbar, "cell 6 3,alignx trailing");

			textField_pressione_media = new JTextField();
			textField_pressione_media.setFont(new Font("Arial", Font.BOLD, 12));
			textField_pressione_media.setEditable(false);
			textField_pressione_media.setColumns(10);
			pannelloValori.add(textField_pressione_media, "cell 7 3");

			JLabel label = new JLabel("\u00B1");
			label.setFont(new Font("Arial", Font.BOLD, 14));
			pannelloValori.add(label, "cell 0 4,alignx trailing");

			if(datiCondizioniAmbinetali.getMedia_pressione()!=null) 
			{
				textField_pressione_media.setText(datiCondizioniAmbinetali.getMedia_pressione().setScale(1,RoundingMode.HALF_UP).toPlainString());
			}

			textField_temperatura_media_variazione = new JTextField();
			textField_temperatura_media_variazione.setFont(new Font("Arial", Font.BOLD, 12));
			textField_temperatura_media_variazione.setEditable(false);
			textField_temperatura_media_variazione.setColumns(10);
			pannelloValori.add(textField_temperatura_media_variazione, "cell 1 4");

			if(datiCondizioniAmbinetali.getMedia_temperatura_margine()!=null) 
			{
				textField_temperatura_media_variazione.setText(datiCondizioniAmbinetali.getMedia_temperatura_margine().setScale(1,RoundingMode.HALF_UP).toPlainString());
			}

			JLabel label_2 = new JLabel("\u00B1");
			label_2.setHorizontalAlignment(SwingConstants.RIGHT);
			label_2.setFont(new Font("Arial", Font.BOLD, 14));
			pannelloValori.add(label_2, "cell 3 4,alignx trailing");

			textField_ur_media_variazione = new JTextField();
			textField_ur_media_variazione.setFont(new Font("Arial", Font.BOLD, 12));
			textField_ur_media_variazione.setEditable(false);
			textField_ur_media_variazione.setColumns(10);
			pannelloValori.add(textField_ur_media_variazione, "cell 4 4");

			if(datiCondizioniAmbinetali.getMedia_umidita_margine()!=null) 
			{
				textField_ur_media_variazione.setText(datiCondizioniAmbinetali.getMedia_umidita_margine().setScale(1,RoundingMode.HALF_UP).toPlainString());
			}

			JLabel label_3 = new JLabel("\u00B1");
			label_3.setFont(new Font("Arial", Font.BOLD, 14));
			pannelloValori.add(label_3, "cell 6 4,alignx trailing");

			textField_pressione_media_variazione = new JTextField();
			textField_pressione_media_variazione.setFont(new Font("Arial", Font.BOLD, 12));
			textField_pressione_media_variazione.setEditable(false);
			textField_pressione_media_variazione.setColumns(10);
			pannelloValori.add(textField_pressione_media_variazione, "cell 7 4");

			if(datiCondizioniAmbinetali.getMedia_pressione_margine()!=null) 
			{
				textField_pressione_media_variazione.setText(datiCondizioniAmbinetali.getMedia_pressione_margine().setScale(1,RoundingMode.HALF_UP).toPlainString());
			}

			JLabel lbla = new JLabel("\u03C1a");
			lbla.setFont(new Font("Arial", Font.BOLD | Font.ITALIC, 14));
			pannelloValori.add(lbla, "cell 0 6");

			JLabel lblUa = new JLabel("u(\u03C1a)");
			lblUa.setFont(new Font("Arial", Font.BOLD | Font.ITALIC, 14));
			pannelloValori.add(lblUa, "cell 0 8");

			textField_pa_cipm = new JTextField();
			textField_pa_cipm.setFont(new Font("Arial", Font.BOLD, 12));
			textField_pa_cipm.setEditable(false);
			textField_pa_cipm.setColumns(10);
			pannelloValori.add(textField_pa_cipm, "cell 1 6 7 1");

			if(datiCondizioniAmbinetali.getDensita_aria_cimp()!=null) 
			{
				textField_pa_cipm.setText(datiCondizioniAmbinetali.getDensita_aria_cimp().setScale(9,RoundingMode.HALF_UP).toPlainString());
			}


			textField_U_pa = new JTextField();
			textField_U_pa.setFont(new Font("Arial", Font.BOLD, 12));
			textField_U_pa.setEditable(false);
			textField_U_pa.setColumns(10);
			pannelloValori.add(textField_U_pa, "cell 1 8");

			if(datiCondizioniAmbinetali.getIncertezza_densita_aria_cimp()!=null) 
			{
				textField_U_pa.setText(datiCondizioniAmbinetali.getIncertezza_densita_aria_cimp().setScale(4,RoundingMode.HALF_UP).toPlainString());
			}

			JLabel lblUform = new JLabel("U form");
			lblUform.setFont(new Font("Arial", Font.BOLD | Font.ITALIC, 14));
			pannelloValori.add(lblUform, "cell 0 10,alignx trailing");

			textField_U_form = new JTextField();
			textField_U_form.setFont(new Font("Arial", Font.BOLD, 12));
			textField_U_form.setEditable(false);
			textField_U_form.setColumns(10);
			pannelloValori.add(textField_U_form, "cell 1 10");

			if(datiCondizioniAmbinetali.getIncertezza_form_densita_aria_cimp()!=null) 
			{
				textField_U_form.setText(datiCondizioniAmbinetali.getIncertezza_form_densita_aria_cimp().setScale(5,RoundingMode.HALF_UP).toPlainString());
			}

			JLabel lblFormulaInUso = new JLabel("Formula in uso se le condizioni non discostano pi\u00F9 del 10%");
			lblFormulaInUso.setFont(new Font("Arial", Font.BOLD, 12));
			pannelloValori.add(lblFormulaInUso, "cell 0 12 8 1");

			JLabel lbltc = new JLabel("\u0394t /\u00B0C");
			lbltc.setFont(new Font("Arial", Font.BOLD, 14));
			pannelloValori.add(lbltc, "cell 0 14,alignx trailing");

			textField_delta_temp = new JTextField();
			textField_delta_temp.setFont(new Font("Arial", Font.BOLD, 12));
			textField_delta_temp.setEditable(false);
			textField_delta_temp.setColumns(10);
			pannelloValori.add(textField_delta_temp, "cell 1 14");

			if(datiCondizioniAmbinetali.getDelta_temperatura()!=null) 
			{
				textField_delta_temp.setText(datiCondizioniAmbinetali.getDelta_temperatura().setScale(5,RoundingMode.HALF_UP).toPlainString());
			}

			JLabel lblhr = new JLabel("\u0394hr /%");
			lblhr.setFont(new Font("Arial", Font.BOLD, 14));
			pannelloValori.add(lblhr, "cell 3 14,alignx trailing");

			textField_delta_ur = new JTextField();
			textField_delta_ur.setFont(new Font("Arial", Font.BOLD, 12));
			textField_delta_ur.setEditable(false);
			textField_delta_ur.setColumns(10);
			pannelloValori.add(textField_delta_ur, "cell 4 14");

			if(datiCondizioniAmbinetali.getDelta_umidita()!=null) 
			{
				textField_delta_ur.setText(datiCondizioniAmbinetali.getDelta_umidita().setScale(5,RoundingMode.HALF_UP).toPlainString());
			}	

			JLabel lblppa = new JLabel("\u0394p /Pa");
			lblppa.setFont(new Font("Arial", Font.BOLD, 14));
			pannelloValori.add(lblppa, "cell 6 14,alignx trailing");

			textField_delta_press = new JTextField();
			textField_delta_press.setFont(new Font("Arial", Font.BOLD, 12));
			textField_delta_press.setEditable(false);
			textField_delta_press.setColumns(10);
			pannelloValori.add(textField_delta_press, "cell 7 14");

			if(datiCondizioniAmbinetali.getDelta_pressione()!=null) 
			{
				textField_delta_press.setText(datiCondizioniAmbinetali.getDelta_pressione().setScale(5,RoundingMode.HALF_UP).toPlainString());
			}

			JLabel lblUtc = new JLabel("u(t) /\u00B0C");
			lblUtc.setFont(new Font("Arial", Font.BOLD, 14));
			pannelloValori.add(lblUtc, "cell 0 16,alignx trailing");

			textField_incertezza_temp = new JTextField();
			textField_incertezza_temp.setFont(new Font("Arial", Font.BOLD, 12));
			textField_incertezza_temp.setEditable(false);
			textField_incertezza_temp.setColumns(10);
			pannelloValori.add(textField_incertezza_temp, "cell 1 16");

			if(datiCondizioniAmbinetali.getIncertezzaTemperatura()!=null) 
			{
				textField_incertezza_temp.setText(datiCondizioniAmbinetali.getIncertezzaTemperatura().setScale(3,RoundingMode.HALF_UP).toPlainString());
			}

			JLabel lblUhr = new JLabel("u(hr) /%");
			lblUhr.setFont(new Font("Arial", Font.BOLD, 14));
			pannelloValori.add(lblUhr, "cell 3 16,alignx trailing");

			textField_incertezza_ur = new JTextField();
			textField_incertezza_ur.setFont(new Font("Arial", Font.BOLD, 12));
			textField_incertezza_ur.setEditable(false);
			textField_incertezza_ur.setColumns(10);
			pannelloValori.add(textField_incertezza_ur, "cell 4 16");

			if(datiCondizioniAmbinetali.getIncertezzaUminidta()!=null) 
			{
				textField_incertezza_ur.setText(datiCondizioniAmbinetali.getIncertezzaUminidta().setScale(1,RoundingMode.HALF_UP).toPlainString());
			}

			JLabel lblUppa = new JLabel("u(p) /Pa");
			lblUppa.setFont(new Font("Arial", Font.BOLD, 14));
			pannelloValori.add(lblUppa, "cell 6 16,alignx trailing");

			textField_incertezza_press = new JTextField();
			textField_incertezza_press.setFont(new Font("Arial", Font.BOLD, 12));
			textField_incertezza_press.setEditable(false);
			textField_incertezza_press.setColumns(10);
			pannelloValori.add(textField_incertezza_press, "cell 7 16");

			if(datiCondizioniAmbinetali.getIncertezzaPressione()!=null) 
			{
				textField_incertezza_press.setText(datiCondizioniAmbinetali.getIncertezzaPressione().setScale(1,RoundingMode.HALF_UP).toPlainString());
			}

			JLabel lbla_1 = new JLabel("\u03C1a");
			lbla_1.setFont(new Font("Arial", Font.BOLD | Font.ITALIC, 14));
			pannelloValori.add(lbla_1, "cell 0 18,alignx trailing");

			textField_pa_no_cipm = new JTextField();
			textField_pa_no_cipm.setFont(new Font("Arial", Font.BOLD, 12));
			textField_pa_no_cipm.setEditable(false);
			textField_pa_no_cipm.setColumns(10);
			pannelloValori.add(textField_pa_no_cipm, "cell 1 18");
			//mainPanelMonitoraggioAmb.add(pannelloTabSonde, "cell 1 1,grow");

			if(datiCondizioniAmbinetali.getDensita_aria()!=null) 
			{
				textField_pa_no_cipm.setText(datiCondizioniAmbinetali.getDensita_aria().setScale(7,RoundingMode.HALF_UP).toPlainString());
			}

			final JTextField textField_fileCaricamento = new JTextField();
			textField_fileCaricamento.setFont(new Font("Arial", Font.BOLD, 14));
			textField_fileCaricamento.setEditable(false);
			pannelloTabCondizioni.add(textField_fileCaricamento, "cell 0 0, width :350:");
			textField_fileCaricamento.setColumns(70);


			JButton btnCarica = new JButton("Carica");

			btnCarica.setIcon(new ImageIcon(PannelloLivellaElettronica.class.getResource("/image/load.png")));
			btnCarica.setFont(new Font("Arial", Font.BOLD, 14));

			pannelloTabCondizioni.add(btnCarica, "cell 0 0 ,alignx left");

			JButton btnCalcola = new JButton("Calcola");

			btnCalcola.setIcon(new ImageIcon(PannelloLivellaElettronica.class.getResource("/image/calcola.png")));
			btnCalcola.setFont(new Font("Arial", Font.BOLD, 14));

			pannelloTabCondizioni.add(btnCalcola, "cell 0 0");

			tabellaCondizioniAmb = new JTable();
			tabellaCondizioniAmb.setDefaultRenderer(Object.class, new MyCellRenderer());
			model_condizionniAmb = new ModelCondizioniAmb();

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

			btnCalcola.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					try {
						if(listaTempi!=null && listaTempi.size()>0) 
						{
							GestioneMisuraBO.removeCondizioniAmbientali(SessionBO.idMisura);

							ArrayList<LatMassaAMB> listaValoriAmbientali = new ArrayList<>();

							LatMassaAMB_DATA datiCalcolati= new LatMassaAMB_DATA();

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


							LatMassaAMB latAmb=null;

							for (int i = 0; i <listaTempi.size(); i++) {

								String[] data=listaTempi.get(i).split(";");

								latAmb= new LatMassaAMB();

								model_condizionniAmb.addRow(new Object[0]);
								model_condizionniAmb.setValueAt(data[0].replaceAll(",", "."), i, 0);
								model_condizionniAmb.setValueAt(data[1].replaceAll(",", "."), i, 1);
								model_condizionniAmb.setValueAt(data[2].replaceAll(",", "."), i, 2);
								model_condizionniAmb.setValueAt(data[3].replaceAll(",", "."), i, 3);

								latAmb.setData_ora(data[0].replaceAll(",", "."));
								latAmb.setCh1_temperatura(new BigDecimal(data[1].replaceAll(",", ".")));
								latAmb.setCh2_temperatura(new BigDecimal(data[2].replaceAll(",", ".")));
								latAmb.setCh3_temperatura(new BigDecimal(data[3].replaceAll(",", ".")));


								BigDecimal correzione = getCorrezioneSonda(data[1],listaCorrezzioniSonde_tmp_1);
								latAmb.setCh1_temperatura_corr(correzione);
								listaValori_temp.add(correzione);
								mediaTemperatura=mediaTemperatura.add(correzione);
								model_condizionniAmb.setValueAt(correzione.toPlainString(), i, 4);

								correzione = getCorrezioneSonda(data[2],listaCorrezzioniSonde_tmp_2);
								latAmb.setCh2_temperatura_corr(correzione);
								listaValori_temp.add(correzione);
								mediaTemperatura=mediaTemperatura.add(correzione);
								model_condizionniAmb.setValueAt(correzione.toPlainString(), i, 5);

								correzione = getCorrezioneSonda(data[3],listaCorrezzioniSonde_tmp_3);
								latAmb.setCh3_temperatura_corr(correzione);
								listaValori_temp.add(correzione);
								mediaTemperatura=mediaTemperatura.add(correzione);
								model_condizionniAmb.setValueAt(correzione.toPlainString(), i, 6);

								model_condizionniAmb.setValueAt(data[4].replaceAll(",", "."), i, 7);
								latAmb.setUmidita(new BigDecimal(data[4].replaceAll(",", ".")));

								correzione = getCorrezioneSonda(data[4],listaCorrezzioniSonde_umidita);
								latAmb.setUmidita_corr(correzione);
								listaValori_uhr.add(correzione);
								mediaUHR=mediaUHR.add(correzione);
								model_condizionniAmb.setValueAt(correzione.toPlainString(), i,8);
								model_condizionniAmb.setValueAt(data[5].replaceAll(",", "."), i, 9);
								latAmb.setPressione(new BigDecimal(data[5].replaceAll(",", ".")));

								correzione = getCorrezioneSonda(data[5],listaCorrezzioniSonde_pressione);
								latAmb.setPressione_corretta(correzione);
								listaValori_press.add(correzione);
								mediaPressione=mediaPressione.add(correzione);
								model_condizionniAmb.setValueAt(correzione.toPlainString(), i,10);

								listaValoriAmbientali.add(latAmb);


							}
							GestioneMisuraBO.insertCondizioniAmbientali(listaValoriAmbientali,SessionBO.idMisura);

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


							/*Pa no CIPM*/
							BigDecimal derivataPressione=getDerivataPressione(medTemp);

							BigDecimal derivataTemperatura= getDerivataTemperatura(medTemp,medUHR,medPress);

							BigDecimal derivataUHR=getDerivataUmidita(medTemp);

							BigDecimal pa_no_cipm = new BigDecimal(1.1835).add(derivataTemperatura.multiply(deltaT).add(derivataUHR.multiply(deltaUr).add(derivataPressione.multiply(deltaP))));

							textField_pa_no_cipm.setText(pa_no_cipm.setScale(7,RoundingMode.HALF_UP).toPlainString());


							datiCalcolati.setId_misura(SessionBO.idMisura);
							datiCalcolati.setMedia_temperatura(medTemp);
							datiCalcolati.setMedia_umidita(medUHR);
							datiCalcolati.setMedia_pressione(medPress);

							datiCalcolati.setMedia_temperatura_margine(new BigDecimal(variazioneTemperatura));
							datiCalcolati.setMedia_umidita_margine(new BigDecimal(variazioneUHR));
							datiCalcolati.setMedia_pressione_margine(new BigDecimal(variazionePress));

							datiCalcolati.setDelta_temperatura(deltaT);
							datiCalcolati.setDelta_umidita(deltaUr);
							datiCalcolati.setDelta_pressione(deltaP);

							datiCalcolati.setDerivata_temperatura_cimp(derivataTemperatura);
							datiCalcolati.setDerivata_umidita_cimp(derivataUHR);
							datiCalcolati.setDerivata_pressione_cimp(derivataPressione);

							datiCalcolati.setIncertezzaTemperatura(new BigDecimal(textField_incertezza_temp.getText()));
							datiCalcolati.setIncertezzaUminidta(new BigDecimal(textField_incertezza_ur.getText()));
							datiCalcolati.setIncertezzaPressione(new BigDecimal(textField_incertezza_press.getText()));

							datiCalcolati.setDensita_aria_cimp(densita_aria);
							datiCalcolati.setIncertezza_densita_aria_cimp(u);
							datiCalcolati.setIncertezza_form_densita_aria_cimp(uForm);

							datiCalcolati.setDensita_aria(pa_no_cipm);

							GestioneMisuraBO.insertCondizioniAmbientaliDati(datiCalcolati);
						}



					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}

			});


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
class ModelEffettoMagnetico extends DefaultTableModel {


	public ModelEffettoMagnetico() {
		addColumn("COMPARATORE");
		addColumn("CAMPIONE");
		addColumn("VAL. NOM.");
		addColumn("SEGNO DIST.");
		addColumn("EFF MAG L1");
		addColumn("EFF MAG L2");
		addColumn("EFF MAG ESITO");
		addColumn("Mc");
		addColumn("U (Mc)");
		addColumn("C.C.");
		addColumn("C.C. \u03C1min");
		addColumn("C.C. \u03C1c");
		addColumn("C.C. \u03C1max");
		addColumn("C.C. U");
		addColumn("C.T.");
		addColumn("C.T. \u03C1min");
		addColumn("C.T. \u03C1c");
		addColumn("C.T. \u03C1max");
		addColumn("C.T. U");

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
		case 19:
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


