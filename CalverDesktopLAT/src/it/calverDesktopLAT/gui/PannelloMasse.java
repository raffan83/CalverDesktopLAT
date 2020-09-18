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

import com.sun.xml.internal.bind.v2.runtime.reflect.ListTransducedAccessorImpl;

import it.calverDesktopLAT.bo.GestioneCampioneBO;
import it.calverDesktopLAT.bo.GestioneMisuraBO;
import it.calverDesktopLAT.bo.GestioneRegistro;
import it.calverDesktopLAT.bo.GestioneStrumentoBO;
import it.calverDesktopLAT.bo.SessionBO;
import it.calverDesktopLAT.dao.SQLiteDAO;
import it.calverDesktopLAT.dto.LatMassaAMB;
import it.calverDesktopLAT.dto.LatMassaAMB_DATA;
import it.calverDesktopLAT.dto.LatMassaAMB_SONDE;
import it.calverDesktopLAT.dto.LatMassaClasseDTO;
import it.calverDesktopLAT.dto.LatMassaEffMag;
import it.calverDesktopLAT.dto.LatMassaScartiTipo;
import it.calverDesktopLAT.dto.LatMisuraDTO;
import it.calverDesktopLAT.dto.LatPuntoLivellaElettronicaDTO;
import it.calverDesktopLAT.dto.ParametroTaraturaDTO;
import it.calverDesktopLAT.dto.RegLinDTO;
import it.calverDesktopLAT.utl.Costanti;
import it.calverDesktopLAT.utl.Utility;
import jdk.nashorn.internal.ir.CatchNode;
import net.miginfocom.swing.MigLayout;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JCheckBox;
import javax.swing.border.LineBorder;

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
	ModelElaborazioneDati model_elaborazioneDati;
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
	private JTextField textField_cmp_l1_r1;
	private JTextField textField_l2;
	private JTextField textField_mis_l3_r1;
	private JTextField textField_val_taratura_param;
	private JTextField textField_cmp_l4_r1;
	private ArrayList<LatMassaClasseDTO> listaClassi =null;
	private ArrayList<LatMassaScartiTipo> listaScarti=null;
	private JTextField textField_val_u_param;
	private JTextField textField_mis_l2_r1;
	private JTextField textField_cmp_l1_r2;
	private JTextField textField_mis_l2_r2;
	private JTextField textField_cmp_l1_r3;
	private JTextField textField_mis_l2_r3;
	private JTextField textField_mis_l3_r2;
	private JTextField textField_cmp_l4_r2;
	private JTextField textField_mis_l3_r3;
	private JTextField textField_cmp_l4_r3;
	BigDecimal valoreNominale=BigDecimal.ZERO;
	LatMassaAMB_DATA datiCondizioniAmbinetali;
	
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
			tabbedPane.addTab("Effetto Magnetico", pannelloEffettoMagnetico);

			JPanel pannelloElaborazioneDati= costruisciPannelloElaborazioneDati();
			tabbedPane.addTab("Elaborazione Dati", pannelloElaborazioneDati);
			

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



	private JPanel costruisciPannelloElaborazioneDati() {
		JPanel mainPanelElaborazioneDati= new JPanel();

		JTable tabellaElaborazioneDati;

		mainPanelElaborazioneDati.setBackground(Color.LIGHT_GRAY);

		
		try 
		{
			
			mainPanelElaborazioneDati.setLayout(new MigLayout("", "[grow]", "[30%,grow][grow]"));
			model_elaborazioneDati = new ModelElaborazioneDati();


			JPanel pannelloInserimentoValori= new JPanel();
			pannelloInserimentoValori.setBorder(new LineBorder(new Color(255, 0, 0), 2, true));
			pannelloInserimentoValori.setBackground(Color.WHITE);
			pannelloInserimentoValori.setLayout(new MigLayout("", "[][::10px][:125px:125px][:125px:125px,grow][:125px:125px,grow][:125px:125px,grow][grow]", "[grow][][grow][10px:10px][][grow][][grow][][grow][][20px:20px][grow]"));
			mainPanelElaborazioneDati.add(pannelloInserimentoValori, "cell 0 0,growy");

			JLabel lblComparatore = new JLabel("Comparatore");
			lblComparatore.setFont(new Font("Arial", Font.BOLD, 14));
			pannelloInserimentoValori.add(lblComparatore, "flowx,cell 0 0,alignx trailing");

			listaScarti=GestioneCampioneBO.getListaScartiTipo();
			
			String[] descrizioni = new String[listaScarti.size()+1];
			
			descrizioni[0]="Seleziona un comparatore...";
			for (int i = 0; i <listaScarti.size(); i++) {
				descrizioni[i+1] =listaScarti.get(i).getDescrizione();
			}
			
			final JComboBox comboBox_comparatore_ed = new JComboBox(descrizioni);
			comboBox_comparatore_ed.setFont(new Font("Arial", Font.PLAIN, 14));
			pannelloInserimentoValori.add(comboBox_comparatore_ed, "cell 2 0,growx");

			JLabel lblCampione = new JLabel("Campione");
			lblCampione.setFont(new Font("Arial", Font.BOLD, 14));
			pannelloInserimentoValori.add(lblCampione, "cell 0 2,alignx trailing");

			final JComboBox comboBox_campione_ed = new JComboBox(GestioneCampioneBO.getListaCampioniCompleta());
			comboBox_campione_ed.setFont(new Font("Arial", Font.PLAIN, 14));
			pannelloInserimentoValori.add(comboBox_campione_ed, "cell 2 2,growx");
			
						JLabel lblValoreNominale = new JLabel("Parametro");
						lblValoreNominale.setFont(new Font("Arial", Font.BOLD, 14));
						pannelloInserimentoValori.add(lblValoreNominale, "cell 3 2,alignx trailing");
						
									final JComboBox comboBox_valore_nominale_ed = new JComboBox();
									comboBox_valore_nominale_ed.addActionListener(new ActionListener() {
										public void actionPerformed(ActionEvent e) {
										}
									});
									comboBox_valore_nominale_ed.setFont(new Font("Arial", Font.PLAIN, 14));
									pannelloInserimentoValori.add(comboBox_valore_nominale_ed, "cell 4 2,growx,width :150:");
									
									comboBox_valore_nominale_ed.addActionListener(new ActionListener() {
										public void actionPerformed(ActionEvent e) {
											
											try 
											{
											if(comboBox_valore_nominale_ed.getSelectedIndex()!=0)
											{
												String codiceParametro=comboBox_valore_nominale_ed.getSelectedItem().toString();
												
												String codiceCampione=comboBox_campione_ed.getSelectedItem().toString();

												ArrayList<ParametroTaraturaDTO> listaParametriTaratura=GestioneCampioneBO.getParametriTaratura(codiceCampione);

												

												for(ParametroTaraturaDTO param : listaParametriTaratura) {


													if(param.getDescrizioneParametro().equals(codiceParametro)) 
													{
														textField_val_taratura_param.setText(param.getValoreTaratura().toPlainString());
														
														BigDecimal value=param.getIncertezzaAssoluta().setScale(20).divide(new BigDecimal(2).setScale(20),RoundingMode.HALF_UP);
														textField_val_u_param.setText(value.stripTrailingZeros().toPlainString());
													}
													
													
												}
											}
										 }
											catch (Exception ex) {
											ex.printStackTrace();
										}
											
										}
									});
			
						JLabel lblLetturaL_1 = new JLabel("Campione L1");
						lblLetturaL_1.setFont(new Font("Arial", Font.BOLD, 14));
						pannelloInserimentoValori.add(lblLetturaL_1, "cell 2 4,alignx center");
			
			JLabel lblUmc = new JLabel("Misurando L2");
			lblUmc.setFont(new Font("Arial", Font.BOLD, 14));
			pannelloInserimentoValori.add(lblUmc, "cell 3 4,alignx center");
			
			JLabel lblValoreTaratura = new JLabel("Campione L4");
			lblValoreTaratura.setFont(new Font("Arial", Font.BOLD, 14));
			pannelloInserimentoValori.add(lblValoreTaratura, "cell 5 4,alignx center,aligny center");

			textField_cmp_l1_r1 = new JTextField();
			textField_cmp_l1_r1.setFont(new Font("Arial", Font.PLAIN, 14));
			pannelloInserimentoValori.add(textField_cmp_l1_r1, "cell 2 5,width : 150:");
			textField_cmp_l1_r1.setColumns(10);
						
						textField_mis_l2_r1 = new JTextField();
						textField_mis_l2_r1.setFont(new Font("Arial", Font.PLAIN, 14));
						textField_mis_l2_r1.setColumns(10);
						pannelloInserimentoValori.add(textField_mis_l2_r1, "cell 3 5,growx");
			
						textField_mis_l3_r1 = new JTextField();
						textField_mis_l3_r1.setFont(new Font("Arial", Font.PLAIN, 14));
						textField_mis_l3_r1.setColumns(10);
						pannelloInserimentoValori.add(textField_mis_l3_r1, "cell 4 5,width :150:");
			
			textField_cmp_l4_r1 = new JTextField();
			textField_cmp_l4_r1.setFont(new Font("Arial", Font.PLAIN, 14));
			pannelloInserimentoValori.add(textField_cmp_l4_r1, "cell 5 5,growx");
			textField_cmp_l4_r1.setColumns(10);
			
			textField_cmp_l1_r2 = new JTextField();
			textField_cmp_l1_r2.setFont(new Font("Arial", Font.PLAIN, 14));
			textField_cmp_l1_r2.setColumns(10);
			pannelloInserimentoValori.add(textField_cmp_l1_r2, "cell 2 6,growx,width : 150:");
			
			textField_mis_l2_r2 = new JTextField();
			textField_mis_l2_r2.setFont(new Font("Arial", Font.PLAIN, 14));
			textField_mis_l2_r2.setColumns(10);
			pannelloInserimentoValori.add(textField_mis_l2_r2, "cell 3 6,growx");
			
			textField_mis_l3_r2 = new JTextField();
			textField_mis_l3_r2.setFont(new Font("Arial", Font.PLAIN, 14));
			textField_mis_l3_r2.setColumns(10);
			pannelloInserimentoValori.add(textField_mis_l3_r2, "cell 4 6,growx");
			
			textField_cmp_l4_r2 = new JTextField();
			textField_cmp_l4_r2.setFont(new Font("Arial", Font.PLAIN, 14));
			textField_cmp_l4_r2.setColumns(10);
			pannelloInserimentoValori.add(textField_cmp_l4_r2, "cell 5 6,growx");
			
			textField_cmp_l1_r3 = new JTextField();
			textField_cmp_l1_r3.setFont(new Font("Arial", Font.PLAIN, 14));
			textField_cmp_l1_r3.setColumns(10);
			pannelloInserimentoValori.add(textField_cmp_l1_r3, "cell 2 7,growx,width : 150:");
			
			textField_mis_l2_r3 = new JTextField();
			textField_mis_l2_r3.setFont(new Font("Arial", Font.PLAIN, 14));
			textField_mis_l2_r3.setColumns(10);
			pannelloInserimentoValori.add(textField_mis_l2_r3, "cell 3 7,growx");
			
			textField_mis_l3_r3 = new JTextField();
			textField_mis_l3_r3.setFont(new Font("Arial", Font.PLAIN, 14));
			textField_mis_l3_r3.setColumns(10);
			pannelloInserimentoValori.add(textField_mis_l3_r3, "cell 4 7,growx");
			
			textField_cmp_l4_r3 = new JTextField();
			textField_cmp_l4_r3.setFont(new Font("Arial", Font.PLAIN, 14));
			textField_cmp_l4_r3.setColumns(10);
			pannelloInserimentoValori.add(textField_cmp_l4_r3, "cell 5 7,growx");

			JLabel lblClasseCampione = new JLabel("Caso");
			lblClasseCampione.setHorizontalAlignment(SwingConstants.TRAILING);
			lblClasseCampione.setFont(new Font("Arial", Font.BOLD, 14));
			pannelloInserimentoValori.add(lblClasseCampione, "cell 0 9,alignx trailing");

			String[] classi =getValNominaleClassi(listaClassi);
			final JComboBox comboBox_caso = new JComboBox(classi);
			comboBox_caso.setModel(new DefaultComboBoxModel(new String[] {"", "1", "2", "3"}));
			comboBox_caso.setFont(new Font("Arial", Font.PLAIN, 14));
			pannelloInserimentoValori.add(comboBox_caso, "cell 2 9");

			JButton btnCalcolaInserisci = new JButton("Calcola & Inserisci");
			btnCalcolaInserisci.setIcon(new ImageIcon(PannelloMasse.class.getResource("/image/calcola.png")));
			btnCalcolaInserisci.setFont(new Font("Arial", Font.BOLD, 14));
			pannelloInserimentoValori.add(btnCalcolaInserisci, "cell 0 12 6 1,alignx center");
			
						JLabel lblLetturaL = new JLabel("Misurando L3");
						lblLetturaL.setFont(new Font("Arial", Font.BOLD, 14));
						pannelloInserimentoValori.add(lblLetturaL, "cell 4 4,alignx center");

			JPanel pannelloTabElaborazioneDati= new JPanel();
			pannelloTabElaborazioneDati.setBorder(new LineBorder(new Color(255, 0, 0), 2, true));
			pannelloTabElaborazioneDati.setBackground(Color.WHITE);
			pannelloTabElaborazioneDati.setLayout(new MigLayout("", "[grow]", "[grow]"));


			mainPanelElaborazioneDati.add(pannelloTabElaborazioneDati, "cell 0 1,grow");


			tabellaElaborazioneDati = new JTable();
			tabellaElaborazioneDati.setDefaultRenderer(Object.class, new MyCellRenderer());


			tabellaElaborazioneDati.setModel(model_elaborazioneDati);
			tabellaElaborazioneDati.setFont(new Font("Arial", Font.BOLD, 12));
			tabellaElaborazioneDati.getTableHeader().setFont(new Font("Arial", Font.BOLD, 10));
			tabellaElaborazioneDati.setRowHeight(25);


			//	model_condizionniAmb.addTableModelListener(this);

			TableColumn column = tabellaElaborazioneDati.getColumnModel().getColumn(tabellaElaborazioneDati.getColumnModel().getColumnIndex("index"));
			tabellaElaborazioneDati.removeColumn(column);

			JScrollPane scrollTab = new JScrollPane(tabellaElaborazioneDati);
			pannelloTabElaborazioneDati.add(scrollTab, "cell 0 0,grow");

			comboBox_campione_ed.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
				
					try 
					{
					if(comboBox_campione_ed.getSelectedIndex()!=0)
					{
						String codiceCampione=comboBox_campione_ed.getSelectedItem().toString();

						String[] listaParametriTaratura=GestioneCampioneBO.getParametriTaraturaTotali(codiceCampione);

						comboBox_valore_nominale_ed.removeAllItems();

						for(String str : listaParametriTaratura) {


							comboBox_valore_nominale_ed.addItem(str);
						}
					}
				 }
					catch (Exception ex) {
					ex.printStackTrace();
				}
				}
			});
			
	/*Dati Test*/
			
			textField_cmp_l1_r1.setText("0.001002");
			textField_mis_l2_r1.setText("0.001004");
			textField_mis_l3_r1.setText("0.001003");
			textField_cmp_l4_r1.setText("0.001002");
		
			textField_cmp_l1_r2.setText("0.001003");
			textField_mis_l2_r2.setText("0.001006");
			textField_mis_l3_r2.setText("0.001005");
			textField_cmp_l4_r2.setText("0.001004");
			
			textField_cmp_l1_r3.setText("0.001004");
			textField_mis_l2_r3.setText("0.001006");
			textField_mis_l3_r3.setText("0.001007");
			textField_cmp_l4_r3.setText("0.001005");
			
			comboBox_campione_ed.setSelectedIndex(1);
			comboBox_comparatore_ed.setSelectedIndex(1);
			comboBox_valore_nominale_ed.setSelectedIndex(1);
			
			btnCalcolaInserisci.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) 
				{
					
					
					boolean calcola=true;
					
					try {
					if(comboBox_comparatore_ed.getSelectedIndex()==0) 
					{
						calcola=false;
					}
					if(comboBox_campione_ed.getSelectedIndex()==0) 
					{
						calcola=false;
					}
					if(comboBox_valore_nominale_ed.getSelectedIndex()==0) 
					{
						calcola=false;
					}
					
					/*Campione L1*/ 
					if(textField_cmp_l1_r1.getText().length()==0 || !Utility.isNumber(textField_cmp_l1_r1.getText())) 
					{
						calcola=false;
					}
					if(textField_cmp_l1_r2.getText().length()==0 || !Utility.isNumber(textField_cmp_l1_r2.getText())) 
					{
						calcola=false;
					}
					if(textField_cmp_l1_r3.getText().length()==0 || !Utility.isNumber(textField_cmp_l1_r3.getText())) 
					{
						calcola=false;
					}
					/*Misurando L2*/
					if(textField_mis_l2_r1.getText().length()==0 || !Utility.isNumber(textField_mis_l2_r1.getText())) 
					{
						calcola=false;
					}
					if(textField_mis_l2_r2.getText().length()==0 || !Utility.isNumber(textField_mis_l2_r2.getText())) 
					{
						calcola=false;
					}
					if(textField_mis_l2_r3.getText().length()==0 || !Utility.isNumber(textField_mis_l2_r3.getText())) 
					{
						calcola=false;
					}
					/*Misurando L3*/
					if(textField_mis_l3_r1.getText().length()==0 || !Utility.isNumber(textField_mis_l3_r1.getText())) 
					{
						calcola=false;
					}
					if(textField_mis_l3_r2.getText().length()==0 || !Utility.isNumber(textField_mis_l3_r2.getText())) 
					{
						calcola=false;
					}
					if(textField_mis_l3_r3.getText().length()==0 || !Utility.isNumber(textField_mis_l3_r3.getText())) 
					{
						calcola=false;
					}
					/*Campione L4*/
					if(textField_cmp_l4_r1.getText().length()==0 || !Utility.isNumber(textField_cmp_l4_r1.getText())) 
					{
						calcola=false;
					}
					if(textField_cmp_l4_r2.getText().length()==0 || !Utility.isNumber(textField_cmp_l4_r2.getText())) 
					{
						calcola=false;
					}
					if(textField_cmp_l4_r3.getText().length()==0 || !Utility.isNumber(textField_cmp_l4_r3.getText())) 
					{
						calcola=false;
					}
					
					int row=model_elaborazioneDati.getRowCount();
					
					
					
					if(calcola) 
					{
						
						int ripetizione = GestioneMisuraBO.getRipetizioneMasse(SessionBO.idMisura);
						
						datiCondizioniAmbinetali=GestioneMisuraBO.getCondizioniAmbientaliDati(SessionBO.idMisura);
						
						BigDecimal cmp_l1_r1=new BigDecimal(textField_cmp_l1_r1.getText());
						BigDecimal mis_l2_r1=new BigDecimal(textField_mis_l2_r1.getText());
						BigDecimal mis_l3_r1=new BigDecimal(textField_mis_l3_r1.getText());
						BigDecimal cmp_l4_r1=new BigDecimal(textField_cmp_l4_r1.getText());
						
						BigDecimal cmp_l1_r2=new BigDecimal(textField_cmp_l1_r2.getText());
						BigDecimal mis_l2_r2=new BigDecimal(textField_mis_l2_r2.getText());
						BigDecimal mis_l3_r2=new BigDecimal(textField_mis_l3_r2.getText());
						BigDecimal cmp_l4_r2=new BigDecimal(textField_cmp_l4_r2.getText());
						
						BigDecimal cmp_l1_r3=new BigDecimal(textField_cmp_l1_r3.getText());
						BigDecimal mis_l2_r3=new BigDecimal(textField_mis_l2_r3.getText());
						BigDecimal mis_l3_r3=new BigDecimal(textField_mis_l3_r3.getText());
						BigDecimal cmp_l4_r3=new BigDecimal(textField_cmp_l4_r3.getText());
						
						BigDecimal i_esima_diff_1=null;
						BigDecimal i_esima_diff_2=null;
						BigDecimal i_esima_diff_3=null;
						
						ArrayList<BigDecimal> lista = new ArrayList<>();
						
						for (int i=0;i<3;i++)
							{
							model_elaborazioneDati.addRow(new Object[0]);
							
							model_elaborazioneDati.setValueAt(comboBox_comparatore_ed.getSelectedItem().toString(), row+i, 0);
							model_elaborazioneDati.setValueAt(comboBox_campione_ed.getSelectedItem().toString(), row+i, 1);
							model_elaborazioneDati.setValueAt(comboBox_valore_nominale_ed.getSelectedItem().toString(), row+i, 2);
							
					    	
					    	
					    	
							if(i==0) 
							{
								model_elaborazioneDati.setValueAt(cmp_l1_r1.toPlainString(), row+i, 3);
								model_elaborazioneDati.setValueAt(mis_l2_r1.toPlainString(), row+i, 4);
								model_elaborazioneDati.setValueAt(mis_l3_r1.toPlainString(), row+i, 5);
								model_elaborazioneDati.setValueAt(cmp_l4_r1.toPlainString(), row+i, 6);
								
								BigDecimal sum=(cmp_l1_r1.multiply(new BigDecimal(-1))).add(mis_l2_r1).add(mis_l3_r1).subtract(cmp_l4_r1);
								i_esima_diff_1=new BigDecimal(0.5).multiply(sum);
								lista.add(i_esima_diff_1);
								model_elaborazioneDati.setValueAt(i_esima_diff_1, row+i, 7);
							}
							else if(i==1) 
							{
								model_elaborazioneDati.setValueAt(cmp_l1_r2.toPlainString(), row+i, 3);
								model_elaborazioneDati.setValueAt(mis_l2_r2.toPlainString(), row+i, 4);
								model_elaborazioneDati.setValueAt(mis_l3_r2.toPlainString(), row+i, 5);
								model_elaborazioneDati.setValueAt(cmp_l4_r2.toPlainString(), row+i, 6);
								
								BigDecimal sum=(cmp_l1_r2.multiply(new BigDecimal(-1))).add(mis_l2_r2).add(mis_l3_r2).subtract(cmp_l4_r2);
								i_esima_diff_2=new BigDecimal(0.5).multiply(sum);
								lista.add(i_esima_diff_2);
								model_elaborazioneDati.setValueAt(i_esima_diff_2, row+i, 7);
								
							}
							else if(i==2) 
							{
								model_elaborazioneDati.setValueAt(cmp_l1_r3.toPlainString(), row+i, 3);
								model_elaborazioneDati.setValueAt(mis_l2_r3.toPlainString(), row+i, 4);
								model_elaborazioneDati.setValueAt(mis_l3_r3.toPlainString(), row+i, 5);
								model_elaborazioneDati.setValueAt(cmp_l4_r3.toPlainString(), row+i, 6);
								
								BigDecimal sum=(cmp_l1_r3.multiply(new BigDecimal(-1))).add(mis_l2_r3).add(mis_l3_r3).subtract(cmp_l4_r3);
								i_esima_diff_3=new BigDecimal(0.5).multiply(sum);
								lista.add(i_esima_diff_3);
								model_elaborazioneDati.setValueAt(i_esima_diff_3, row+i, 7);
								
								BigDecimal media_i_esima_diff=(i_esima_diff_1.add(i_esima_diff_2).add(i_esima_diff_3)).divide(new BigDecimal(3),RoundingMode.HALF_UP).setScale(8,RoundingMode.HALF_UP);
								
								model_elaborazioneDati.setValueAt(media_i_esima_diff, (row+i)-2, 8);
								model_elaborazioneDati.setValueAt(media_i_esima_diff, (row+i)-1, 8);
								model_elaborazioneDati.setValueAt(media_i_esima_diff, row+i, 8);
								
								LatMassaScartiTipo scarto=listaScarti.get(comboBox_comparatore_ed.getSelectedIndex()-1);
								
								if(row<=2) 
								{
									model_elaborazioneDati.setValueAt(scarto.getScarto().toPlainString(), (row+i)-2, 9);
									model_elaborazioneDati.setValueAt(scarto.getScarto().toPlainString(), (row+i)-1, 9);
									model_elaborazioneDati.setValueAt(scarto.getScarto().toPlainString(), row+i, 9);
									
									model_elaborazioneDati.setValueAt(scarto.getGradi_liberta(), (row+i)-2, 10);
									model_elaborazioneDati.setValueAt(scarto.getGradi_liberta(), (row+i)-1, 10);
									model_elaborazioneDati.setValueAt(scarto.getGradi_liberta(), row+i, 10);
									
									
								}
								else 
								{
									if(model_elaborazioneDati.getValueAt((row+i)-2, 0).toString().equals(model_elaborazioneDati.getValueAt((row+i), 0).toString()))
									{
										model_elaborazioneDati.setValueAt(model_elaborazioneDati.getValueAt(row+i-3, 11), (row+i)-2, 9);
										model_elaborazioneDati.setValueAt(model_elaborazioneDati.getValueAt(row+i-3, 11), (row+i)-1, 9);
										model_elaborazioneDati.setValueAt(model_elaborazioneDati.getValueAt(row+i-3, 11), row+i, 9);
										
										model_elaborazioneDati.setValueAt(Integer.parseInt(model_elaborazioneDati.getValueAt(row+i-3, 10).toString())+2, (row+i)-2, 10);
										model_elaborazioneDati.setValueAt(Integer.parseInt(model_elaborazioneDati.getValueAt(row+i-3, 10).toString())+2, (row+i)-1, 10);
										model_elaborazioneDati.setValueAt(Integer.parseInt(model_elaborazioneDati.getValueAt(row+i-3, 10).toString())+2, row+i, 10);
									}else 
									{
										model_elaborazioneDati.setValueAt(scarto.getScarto(), (row+i)-2, 9);
										model_elaborazioneDati.setValueAt(scarto.getScarto(), (row+i)-1, 9);
										model_elaborazioneDati.setValueAt(scarto.getScarto(), row+i, 9);
										
										model_elaborazioneDati.setValueAt(scarto.getGradi_liberta(), (row+i)-2, 10);
										model_elaborazioneDati.setValueAt(scarto.getGradi_liberta(), (row+i)-1, 10);
										model_elaborazioneDati.setValueAt(scarto.getGradi_liberta(), row+i, 10);
									}
								}
								
								BigDecimal devStd=GestioneMisuraBO.getDevStd(lista, null, 15);
								
								BigDecimal sc1= new BigDecimal(model_elaborazioneDati.getValueAt(row+i, 9).toString());
								BigDecimal vc1= new BigDecimal(model_elaborazioneDati.getValueAt(row+i, 10).toString());
								
								BigDecimal sc2_part1=vc1.multiply(sc1.multiply(sc1));
								BigDecimal sc2_part2=new BigDecimal(2).multiply(devStd.multiply(devStd));
								
								BigDecimal sc2=new BigDecimal(Math.sqrt(((sc2_part1.add(sc2_part2)).divide(vc1.add(new BigDecimal(2)),RoundingMode.HALF_UP)).doubleValue()));
								
								model_elaborazioneDati.setValueAt(sc2.toPlainString(), (row+i)-2,11);
								model_elaborazioneDati.setValueAt(sc2.toPlainString(), (row+i)-1, 11);
								model_elaborazioneDati.setValueAt(sc2.toPlainString(), row+i, 11);
								
								
								
								model_elaborazioneDati.setValueAt(devStd.toPlainString(), (row+i)-2,12);
								model_elaborazioneDati.setValueAt(devStd.toPlainString(), (row+i)-1, 12);
								model_elaborazioneDati.setValueAt(devStd.toPlainString(), row+i, 12);
								
								String esito="";
								
								if(devStd.compareTo(sc1.multiply(new BigDecimal(2)))<1) 
								{
									esito="OK";
								}else 
								{
									esito="NON OK";
								}
								
								model_elaborazioneDati.setValueAt(esito, (row+i)-2,13);
								model_elaborazioneDati.setValueAt(esito, (row+i)-1, 13);
								model_elaborazioneDati.setValueAt(esito, row+i, 13);
								
								BigDecimal ud=sc2.divide(new BigDecimal(1.732050807568877),RoundingMode.HALF_UP);
								
								model_elaborazioneDati.setValueAt(ud, (row+i)-2,14);
								model_elaborazioneDati.setValueAt(ud, (row+i)-1, 14);
								model_elaborazioneDati.setValueAt(ud, row+i, 14);
								
								
								BigDecimal uf=scarto.getIncertezzaScarto().divide(new BigDecimal(3.464101615137755),RoundingMode.HALF_UP).multiply(new BigDecimal(1.414213562373095));
								
								model_elaborazioneDati.setValueAt(uf, (row+i)-2,15);
								model_elaborazioneDati.setValueAt(uf, (row+i)-1, 15);
								model_elaborazioneDati.setValueAt(uf, row+i, 15);
								
								model_elaborazioneDati.setValueAt(comboBox_caso.getSelectedIndex(), (row+i)-2,16);
								model_elaborazioneDati.setValueAt(comboBox_caso.getSelectedIndex(), (row+i)-1, 16);
								model_elaborazioneDati.setValueAt(comboBox_caso.getSelectedIndex(), row+i, 16);
								
								
								BigDecimal correzioneGalleggiamento=new BigDecimal(textField_pa_no_cipm.getText()).subtract(new BigDecimal(1.2));
								int idndiceDensMag=row/3;
								
								double px=1/Double.parseDouble(model_condizionniEffMag.getValueAt(idndiceDensMag, 18).toString());
								
								double pc=1/Double.parseDouble(model_condizionniEffMag.getValueAt(idndiceDensMag, 13).toString());
								
								correzioneGalleggiamento=correzioneGalleggiamento.multiply(new BigDecimal(px-pc));
								
								model_elaborazioneDati.setValueAt(correzioneGalleggiamento.stripTrailingZeros().toPlainString(), (row+i)-2,17);
								model_elaborazioneDati.setValueAt(correzioneGalleggiamento.stripTrailingZeros().toPlainString(), (row+i)-1, 17);
								model_elaborazioneDati.setValueAt(correzioneGalleggiamento.stripTrailingZeros().toPlainString(), row+i, 17);
								
								
								if(comboBox_caso.getSelectedIndex()==1) 
								{
									double m0=Double.parseDouble(model_condizionniEffMag.getValueAt(idndiceDensMag, 2).toString());
									
									double pa=0;
									
									if(datiCondizioniAmbinetali.getDensita_aria_cimp()!=null) 
									{
										pa=datiCondizioniAmbinetali.getDensita_aria_cimp().doubleValue();
									}else 
									{ 
										pa=Double.parseDouble(textField_pa_no_cipm.getText());
									}	
									
									double u=Double.parseDouble(textField_U_pa.getText());
									
									double pMin=1/Double.parseDouble(model_condizionniEffMag.getValueAt(idndiceDensMag, 12).toString());
									
									double pMax=1/Double.parseDouble(model_condizionniEffMag.getValueAt(idndiceDensMag, 14).toString());
									
									double u_gal=(m0/Math.sqrt(6))*(pMin-pMax)*Math.sqrt(Math.pow(pa-1.2,2)+Math.pow(u, 2)*pa);
									
									model_elaborazioneDati.setValueAt(new BigDecimal(u_gal).stripTrailingZeros().toPlainString(), (row+i)-2,18);
									model_elaborazioneDati.setValueAt(new BigDecimal(u_gal).stripTrailingZeros().toPlainString(), (row+i)-1, 18);
									model_elaborazioneDati.setValueAt(new BigDecimal(u_gal).stripTrailingZeros().toPlainString(), row+i, 18);
									
									
									double mx=media_i_esima_diff.doubleValue()+correzioneGalleggiamento.doubleValue()+Double.parseDouble(model_condizionniEffMag.getValueAt(idndiceDensMag, 8).toString());
								
									model_elaborazioneDati.setValueAt(new BigDecimal(mx).stripTrailingZeros().toPlainString(), (row+i)-2,19);
									model_elaborazioneDati.setValueAt(new BigDecimal(mx).stripTrailingZeros().toPlainString(), (row+i)-1, 19);
									model_elaborazioneDati.setValueAt(new BigDecimal(mx).stripTrailingZeros().toPlainString(), row+i, 19);
									
									
									double uMx=2*Math.sqrt(Math.pow(u_gal, 2)+Math.pow(ud.doubleValue(), 2)+Math.pow(Double.parseDouble(model_condizionniEffMag.getValueAt(idndiceDensMag, 9).toString()), 2)+Math.pow(uf.doubleValue(), 2));
									
									model_elaborazioneDati.setValueAt(new BigDecimal(uMx).stripTrailingZeros().toPlainString(), (row+i)-2,20);
									model_elaborazioneDati.setValueAt(new BigDecimal(uMx).stripTrailingZeros().toPlainString(), (row+i)-1, 20);
									model_elaborazioneDati.setValueAt(new BigDecimal(uMx).stripTrailingZeros().toPlainString(), row+i, 20);
								
								}
								
								if(comboBox_caso.getSelectedIndex()==2) 
								{
									double m0=Double.parseDouble(model_condizionniEffMag.getValueAt(idndiceDensMag, 2).toString());
									
									double pa=0;
									
									if(datiCondizioniAmbinetali.getDensita_aria_cimp()!=null) 
									{
										pa=datiCondizioniAmbinetali.getDensita_aria_cimp().doubleValue();
									}else 
									{ 
										pa=Double.parseDouble(textField_pa_no_cipm.getText());
									}	
									
									double u=Double.parseDouble(textField_U_pa.getText());
									
									double pMinC=1/Double.parseDouble(model_condizionniEffMag.getValueAt(idndiceDensMag, 12).toString());
									
									double pMaxC=1/Double.parseDouble(model_condizionniEffMag.getValueAt(idndiceDensMag, 14).toString());
									
									double pMinX=1/Double.parseDouble(model_condizionniEffMag.getValueAt(idndiceDensMag, 17).toString());
									
									double pMaxX=1/Double.parseDouble(model_condizionniEffMag.getValueAt(idndiceDensMag, 19).toString());
									
									double u_gal=(m0/Math.sqrt(12))*Math.sqrt(Math.pow((pMinX-pMaxX),2)+Math.pow((pMinC-pMaxC),2))*Math.sqrt(Math.pow(pa-1.2,2)+Math.pow(u, 2)*pa);
									
									model_elaborazioneDati.setValueAt(new BigDecimal(u_gal).stripTrailingZeros().toPlainString(), (row+i)-2,18);
									model_elaborazioneDati.setValueAt(new BigDecimal(u_gal).stripTrailingZeros().toPlainString(), (row+i)-1, 18);
									model_elaborazioneDati.setValueAt(new BigDecimal(u_gal).stripTrailingZeros().toPlainString(), row+i, 18);
									
									
									double mx=media_i_esima_diff.doubleValue()+correzioneGalleggiamento.doubleValue()+Double.parseDouble(model_condizionniEffMag.getValueAt(idndiceDensMag, 8).toString());
								
									model_elaborazioneDati.setValueAt(new BigDecimal(mx).stripTrailingZeros().toPlainString(), (row+i)-2,19);
									model_elaborazioneDati.setValueAt(new BigDecimal(mx).stripTrailingZeros().toPlainString(), (row+i)-1, 19);
									model_elaborazioneDati.setValueAt(new BigDecimal(mx).stripTrailingZeros().toPlainString(), row+i, 19);
									
									
									double uMx=2*Math.sqrt(Math.pow(u_gal, 2)+Math.pow(ud.doubleValue(), 2)+Math.pow(Double.parseDouble(model_condizionniEffMag.getValueAt(idndiceDensMag, 9).toString()), 2)+Math.pow(uf.doubleValue(), 2));
									
									model_elaborazioneDati.setValueAt(new BigDecimal(uMx).stripTrailingZeros().toPlainString(), (row+i)-2,20);
									model_elaborazioneDati.setValueAt(new BigDecimal(uMx).stripTrailingZeros().toPlainString(), (row+i)-1, 20);
									model_elaborazioneDati.setValueAt(new BigDecimal(uMx).stripTrailingZeros().toPlainString(), row+i, 20);
								
								}
								
								if(comboBox_caso.getSelectedIndex()==3) 
								{
									double m0=Double.parseDouble(model_condizionniEffMag.getValueAt(idndiceDensMag, 2).toString());
									
									double pa_norm=0;
									
									if(datiCondizioniAmbinetali.getDensita_aria_cimp()!=null) 
									{
										pa_norm=datiCondizioniAmbinetali.getDensita_aria_cimp().doubleValue()-1.2;
									}else 
									{ 
										pa_norm=Double.parseDouble(textField_pa_no_cipm.getText())-1.2;
									}	
									
									double u=Double.parseDouble(textField_U_pa.getText());
									
									double pC=Double.parseDouble(model_condizionniEffMag.getValueAt(idndiceDensMag, 13).toString());
									
									double pX=Double.parseDouble(model_condizionniEffMag.getValueAt(idndiceDensMag, 18).toString());
									
									double u_gal_1_parte=(Math.pow(u, 2)*pX/Math.pow(pX, 4))+(Math.pow(u, 2)*pC/Math.pow(pC, 4));
									
									double u_gal_2_parte=Math.pow(pa_norm, 2)+Math.pow(u, 2)*pa_norm;
									
									double u_gal_3_parte=Math.pow(u, 2)*pa_norm*Math.pow((1/pX)-(1/pC), 2);
									
									double u_gal=m0*Math.sqrt(u_gal_1_parte*u_gal_2_parte+u_gal_3_parte);
									
									model_elaborazioneDati.setValueAt(new BigDecimal(u_gal).stripTrailingZeros().toPlainString(), (row+i)-2,18);
									model_elaborazioneDati.setValueAt(new BigDecimal(u_gal).stripTrailingZeros().toPlainString(), (row+i)-1, 18);
									model_elaborazioneDati.setValueAt(new BigDecimal(u_gal).stripTrailingZeros().toPlainString(), row+i, 18);
									
									
									double mx=media_i_esima_diff.doubleValue()+correzioneGalleggiamento.doubleValue()+Double.parseDouble(model_condizionniEffMag.getValueAt(idndiceDensMag, 8).toString());
								
									model_elaborazioneDati.setValueAt(new BigDecimal(mx).stripTrailingZeros().toPlainString(), (row+i)-2,19);
									model_elaborazioneDati.setValueAt(new BigDecimal(mx).stripTrailingZeros().toPlainString(), (row+i)-1, 19);
									model_elaborazioneDati.setValueAt(new BigDecimal(mx).stripTrailingZeros().toPlainString(), row+i, 19);
									
									
									double uMx=2*Math.sqrt(Math.pow(u_gal, 2)+Math.pow(ud.doubleValue(), 2)+Math.pow(Double.parseDouble(model_condizionniEffMag.getValueAt(idndiceDensMag, 9).toString()), 2)+Math.pow(uf.doubleValue(), 2));
									
									model_elaborazioneDati.setValueAt(new BigDecimal(uMx).stripTrailingZeros().toPlainString(), (row+i)-2,20);
									model_elaborazioneDati.setValueAt(new BigDecimal(uMx).stripTrailingZeros().toPlainString(), (row+i)-1, 20);
									model_elaborazioneDati.setValueAt(new BigDecimal(uMx).stripTrailingZeros().toPlainString(), row+i, 20);
								
								}
								
							}
							

						
							
							}
						
						
					}
					else 
					{
						JOptionPane.showMessageDialog(null,"Compilare tutti i campi","Errore Compilazione",JOptionPane.INFORMATION_MESSAGE,new ImageIcon(PannelloTOP.class.getResource("/image/error.png")));
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

		return mainPanelElaborazioneDati;
	}



	private JPanel costruisciPannelloEffettoMagnetico() {
		JPanel mainPanelEffettoMagnetico= new JPanel();

		JTable tabellaEffettoMagnetico;

		mainPanelEffettoMagnetico.setBackground(Color.LIGHT_GRAY);

		
		
		try 
		{
			listaClassi=GestioneMisuraBO.getListaClassi();
			mainPanelEffettoMagnetico.setLayout(new MigLayout("", "[grow]", "[30%,grow][grow]"));
			model_condizionniEffMag = new ModelEffettoMagnetico();


			JPanel pannelloInserimentoValori= new JPanel();
			pannelloInserimentoValori.setBorder(new LineBorder(Color.RED, 2, true));
			pannelloInserimentoValori.setBackground(Color.WHITE);
			pannelloInserimentoValori.setLayout(new MigLayout("", "[][::10px][][::10px][][::10px][][::10px][grow]", "[grow][][grow][][][grow][][grow][][grow][][grow][20px:20px][grow]"));
			mainPanelEffettoMagnetico.add(pannelloInserimentoValori, "cell 0 0,growy");

			JLabel lblComparatore = new JLabel("Comparatore");
			lblComparatore.setFont(new Font("Arial", Font.BOLD, 14));
			pannelloInserimentoValori.add(lblComparatore, "flowx,cell 0 0,alignx trailing");

			listaScarti=GestioneCampioneBO.getListaScartiTipo();
			
			String[] descrizioni = new String[listaScarti.size()+1];
			
			descrizioni[0]="Seleziona un comparatore...";
			for (int i = 0; i <listaScarti.size(); i++) {
				descrizioni[i+1] =listaScarti.get(i).getDescrizione();
			}
			
			final JComboBox comboBox_comparatore = new JComboBox(descrizioni);
			comboBox_comparatore.setFont(new Font("Arial", Font.PLAIN, 14));
			pannelloInserimentoValori.add(comboBox_comparatore, "cell 2 0,growx");
			
			final JCheckBox chckbxSegnoDistintivo = new JCheckBox("Segno Distintivo");
			chckbxSegnoDistintivo.setBackground(Color.WHITE);
			chckbxSegnoDistintivo.setFont(new Font("Arial", Font.BOLD, 14));
			pannelloInserimentoValori.add(chckbxSegnoDistintivo, "cell 4 0,alignx right");

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
			
			JLabel lblClasseOimlR = new JLabel("Classe OIML R111-1");
			lblClasseOimlR.setFont(new Font("Arial", Font.BOLD, 14));
			pannelloInserimentoValori.add(lblClasseOimlR, "cell 0 3,alignx right");
			
			final JComboBox comboBox_classe_oiml = new JComboBox();
			comboBox_classe_oiml.setFont(new Font("Arial", Font.PLAIN, 14));
			comboBox_classe_oiml.setModel(new DefaultComboBoxModel(new String[] {"E1", "E2", "F1", "F2", "M1", "M1/2", "M2", "M2/2", "M3", "M3/2"}));
			pannelloInserimentoValori.add(comboBox_classe_oiml, "cell 2 3");
			
			JLabel lblValoreTaratura = new JLabel("Mc");
			lblValoreTaratura.setFont(new Font("Arial", Font.BOLD, 14));
			pannelloInserimentoValori.add(lblValoreTaratura, "cell 4 3,alignx trailing");
			
			textField_val_taratura_param = new JTextField();
			textField_val_taratura_param.setFont(new Font("Arial", Font.PLAIN, 14));
			textField_val_taratura_param.setEditable(false);
			pannelloInserimentoValori.add(textField_val_taratura_param, "cell 6 3");
			textField_val_taratura_param.setColumns(10);

			JLabel lblLetturaL_1 = new JLabel("Lettura L1");
			lblLetturaL_1.setFont(new Font("Arial", Font.BOLD, 14));
			pannelloInserimentoValori.add(lblLetturaL_1, "cell 0 5,alignx trailing");

			textField_l1 = new JTextField();
			textField_l1.setFont(new Font("Arial", Font.PLAIN, 14));
			pannelloInserimentoValori.add(textField_l1, "cell 2 5,width : 150:");
			textField_l1.setColumns(10);
			
			JLabel lblUmc = new JLabel("U(Mc)");
			lblUmc.setFont(new Font("Arial", Font.BOLD, 14));
			pannelloInserimentoValori.add(lblUmc, "cell 4 5,alignx right");
			
			textField_val_u_param = new JTextField();
			textField_val_u_param.setFont(new Font("Arial", Font.PLAIN, 14));
			textField_val_u_param.setEditable(false);
			textField_val_u_param.setColumns(10);
			pannelloInserimentoValori.add(textField_val_u_param, "cell 6 5");

			JLabel lblLetturaL = new JLabel("Lettura L2");
			lblLetturaL.setFont(new Font("Arial", Font.BOLD, 14));
			pannelloInserimentoValori.add(lblLetturaL, "cell 0 7,alignx trailing");

			textField_l2 = new JTextField();
			textField_l2.setFont(new Font("Arial", Font.PLAIN, 14));
			textField_l2.setColumns(10);
			pannelloInserimentoValori.add(textField_l2, "cell 2 7,width :150:");

			JLabel lblClasseCampione = new JLabel("Classe Campione");
			lblClasseCampione.setHorizontalAlignment(SwingConstants.TRAILING);
			lblClasseCampione.setFont(new Font("Arial", Font.BOLD, 14));
			pannelloInserimentoValori.add(lblClasseCampione, "cell 0 9,alignx trailing");

			String[] classi =getValNominaleClassi(listaClassi);
			final JComboBox comboBox_classe_campione = new JComboBox(classi);
			comboBox_classe_campione.setFont(new Font("Arial", Font.PLAIN, 14));
			pannelloInserimentoValori.add(comboBox_classe_campione, "cell 2 9,growx");

			JLabel lblUClasseCampione = new JLabel("(U) Classe Campione");
			lblUClasseCampione.setHorizontalAlignment(SwingConstants.TRAILING);
			lblUClasseCampione.setFont(new Font("Arial", Font.BOLD, 14));
			pannelloInserimentoValori.add(lblUClasseCampione, "cell 4 9,alignx trailing");

			final JComboBox comboBox_u_classe_campione = new JComboBox();
			comboBox_u_classe_campione.setFont(new Font("Arial", Font.PLAIN, 14));
			comboBox_u_classe_campione.setModel(new DefaultComboBoxModel(new String[] {"Seleziona ...", "140", "170", "600"}));
			pannelloInserimentoValori.add(comboBox_u_classe_campione, "cell 6 9,growx");

			JLabel lblClasseTaratura = new JLabel("Classe Taratura");
			lblClasseTaratura.setHorizontalAlignment(SwingConstants.TRAILING);
			lblClasseTaratura.setFont(new Font("Arial", Font.BOLD, 14));
			pannelloInserimentoValori.add(lblClasseTaratura, "cell 0 11,alignx trailing");

			final JComboBox comboBox_classe_taratura = new JComboBox(classi);
			comboBox_classe_taratura.setFont(new Font("Arial", Font.PLAIN, 14));
			pannelloInserimentoValori.add(comboBox_classe_taratura, "cell 2 11,growx");

			JLabel lblUClasseTaratura = new JLabel("(U) Classe Taratura");
			lblUClasseTaratura.setHorizontalAlignment(SwingConstants.TRAILING);
			lblUClasseTaratura.setFont(new Font("Arial", Font.BOLD, 14));
			pannelloInserimentoValori.add(lblUClasseTaratura, "cell 4 11,alignx trailing");

			final JComboBox comboBox_u_classe_taratura = new JComboBox();
			comboBox_u_classe_taratura.setFont(new Font("Arial", Font.PLAIN, 14));
			comboBox_u_classe_taratura.setModel(new DefaultComboBoxModel(new String[] {"Seleziona ...", "140", "170", "600"}));
			pannelloInserimentoValori.add(comboBox_u_classe_taratura, "cell 6 11,growx");

			JButton btnCalcolaInserisci = new JButton("Calcola & Inserisci");
			btnCalcolaInserisci.setIcon(new ImageIcon(PannelloMasse.class.getResource("/image/calcola.png")));
			btnCalcolaInserisci.setFont(new Font("Arial", Font.BOLD, 14));
			pannelloInserimentoValori.add(btnCalcolaInserisci, "cell 0 13 8 1,alignx center");

			JPanel pannelloTabEffettoMag= new JPanel();
			pannelloTabEffettoMag.setBorder(new LineBorder(Color.RED, 2, true));
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


			ArrayList<LatMassaEffMag> listaEffettoMagnetico = GestioneMisuraBO.getListaEffettoMagnetico(SessionBO.idMisura);
			
			
			for (int i = 0; i < listaEffettoMagnetico.size(); i++) {
				
				int row=i;
				
				LatMassaEffMag effetto=listaEffettoMagnetico.get(i);
				
				model_condizionniEffMag.addRow(new Object[0]);
				model_condizionniEffMag.setValueAt(effetto.getComparatore(), row, 0);
				model_condizionniEffMag.setValueAt(effetto.getCampione(), row, 1);
				model_condizionniEffMag.setValueAt(effetto.getValore_nominale_campione(), row, 2);
				model_condizionniEffMag.setValueAt(effetto.getClasseOiml(), row, 3);
				model_condizionniEffMag.setValueAt(effetto.getSegno_distintivo(), row, 4);
				model_condizionniEffMag.setValueAt(effetto.getEff_mag_L1().stripTrailingZeros().toPlainString(), row, 5);
				model_condizionniEffMag.setValueAt(effetto.getEff_mag_L2().stripTrailingZeros().toPlainString(), row, 6);
				model_condizionniEffMag.setValueAt(effetto.getEff_mag_esito(), row, 7);
				model_condizionniEffMag.setValueAt(effetto.getMc().stripTrailingZeros().toPlainString(), row, 8);
				model_condizionniEffMag.setValueAt(effetto.getuMc().stripTrailingZeros().toPlainString(), row, 9);
				
				model_condizionniEffMag.setValueAt(effetto.getClasse_campione(), row, 10);
				model_condizionniEffMag.setValueAt(effetto.getClasse_campione_u().stripTrailingZeros().toPlainString(), row, 11);
				model_condizionniEffMag.setValueAt(effetto.getClasse_campione_min().stripTrailingZeros().toPlainString(), row, 12);
				model_condizionniEffMag.setValueAt(effetto.getClasse_campione_pc().stripTrailingZeros().toPlainString(), row, 13);
				model_condizionniEffMag.setValueAt(effetto.getClasse_campione_max().stripTrailingZeros().toPlainString(), row, 14);
				
				model_condizionniEffMag.setValueAt(effetto.getClasse_taratura(), row, 15);
				model_condizionniEffMag.setValueAt(effetto.getClasse_taratura_u().stripTrailingZeros().toPlainString(), row, 16);
				model_condizionniEffMag.setValueAt(effetto.getClasse_taratura_min().stripTrailingZeros().toPlainString(), row, 17);
				model_condizionniEffMag.setValueAt(effetto.getClasse_taratura_pc().stripTrailingZeros().toPlainString(), row, 18);
				model_condizionniEffMag.setValueAt(effetto.getClasse_taratura_max().stripTrailingZeros().toPlainString(), row, 19);
			}
			
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
					if(comboBox_valore_nominale.getSelectedIndex()>0)
					{
						String codiceParametro=comboBox_valore_nominale.getSelectedItem().toString();
						
						String codiceCampione=comboBox_campione.getSelectedItem().toString();

						ArrayList<ParametroTaraturaDTO> listaParametriTaratura=GestioneCampioneBO.getParametriTaratura(codiceCampione);

						

						for(ParametroTaraturaDTO param : listaParametriTaratura) {


							if(param.getDescrizioneParametro().equals(codiceParametro)) 
							{
								textField_val_taratura_param.setText(param.getValoreTaratura().toPlainString());
								
								valoreNominale=param.getValore_nominale();
								BigDecimal value=param.getIncertezzaAssoluta().setScale(20).divide(new BigDecimal(2).setScale(20),RoundingMode.HALF_UP);
								textField_val_u_param.setText(value.stripTrailingZeros().toPlainString());
							}
							
							
						}
					}
				 }
					catch (Exception ex) {
					ex.printStackTrace();
				}
					
				}
			});

			btnCalcolaInserisci.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					
					boolean calcola=true;
					
					if(comboBox_comparatore.getSelectedIndex()==0) 
					{
						calcola=false;
					}
					if(comboBox_campione.getSelectedIndex()==0) 
					{
						calcola=false;
					}
					if(comboBox_valore_nominale.getSelectedIndex()==0) 
					{
						calcola=false;
					}
					if(comboBox_classe_oiml.getSelectedIndex()==0) 
					{
						calcola=false;
					}
					if(textField_val_taratura_param.getText().length()==0) 
					{
						calcola=false;
					}
					if(textField_val_u_param.getText().length()==0) 
					{
						calcola=false;
					}
					if(textField_l1.getText().length()==0) 
					{
						calcola=false;
					}
					if(textField_l2.getText().length()==0) 
					{
						calcola=false;
					}
					if(comboBox_classe_campione.getSelectedIndex()==0) 
					{
						calcola=false;
					}
					if(comboBox_u_classe_campione.getSelectedIndex()==0) 
					{
						calcola=false;
					}
					if(comboBox_classe_taratura.getSelectedIndex()==0) 
					{
						calcola=false;
					}
					if(comboBox_u_classe_taratura.getSelectedIndex()==0) 
					{
						calcola=false;
					}
					
					int row=model_condizionniEffMag.getRowCount();
					if(calcola) 
					{
						model_condizionniEffMag.addRow(new Object[0]);
						model_condizionniEffMag.setValueAt(comboBox_comparatore.getSelectedItem().toString(), row, 0);
						model_condizionniEffMag.setValueAt(comboBox_campione.getSelectedItem().toString(), row, 1);
						model_condizionniEffMag.setValueAt(valoreNominale.stripTrailingZeros().toPlainString(), row, 2);
						model_condizionniEffMag.setValueAt(comboBox_classe_oiml.getSelectedItem().toString(), row, 3);
						
						if(chckbxSegnoDistintivo.isSelected()) 
						{
							model_condizionniEffMag.setValueAt("*", row, 4);
						}
						model_condizionniEffMag.setValueAt(textField_l1.getText(), row, 5);
						model_condizionniEffMag.setValueAt(textField_l2.getText().toString(), row, 6);
						
						String esito =getEsitoEffettoMagnetico(textField_l1.getText(),textField_l2.getText(),listaScarti.get(comboBox_comparatore.getSelectedIndex()-1));
						model_condizionniEffMag.setValueAt(esito, row, 7);
						model_condizionniEffMag.setValueAt(textField_val_taratura_param.getText().toString(), row, 8);
						model_condizionniEffMag.setValueAt(textField_val_u_param.getText().toString(), row, 9);
						
						model_condizionniEffMag.setValueAt(comboBox_classe_campione.getSelectedItem().toString(), row, 10);
						model_condizionniEffMag.setValueAt(comboBox_u_classe_campione.getSelectedItem().toString(), row, 11);
						
						model_condizionniEffMag.setValueAt(listaClassi.get(comboBox_classe_campione.getSelectedIndex()-1).getDens_min(), row, 12);
						double media_ccp=(listaClassi.get(comboBox_classe_campione.getSelectedIndex()-1).getDens_min()+listaClassi.get(comboBox_classe_campione.getSelectedIndex()-1).getDens_max())/2;
						model_condizionniEffMag.setValueAt(media_ccp, row, 13);
						model_condizionniEffMag.setValueAt(listaClassi.get(comboBox_classe_campione.getSelectedIndex()-1).getDens_max(), row, 14);
						
						model_condizionniEffMag.setValueAt(comboBox_classe_taratura.getSelectedItem().toString(), row, 15);
						model_condizionniEffMag.setValueAt(comboBox_u_classe_taratura.getSelectedItem().toString(), row, 16);
						
						model_condizionniEffMag.setValueAt(listaClassi.get(comboBox_classe_taratura.getSelectedIndex()-1).getDens_min(), row, 17);
						double media_cct=(listaClassi.get(comboBox_classe_taratura.getSelectedIndex()-1).getDens_min()+listaClassi.get(comboBox_classe_taratura.getSelectedIndex()-1).getDens_max())/2;
						model_condizionniEffMag.setValueAt(media_cct, row, 18);
						model_condizionniEffMag.setValueAt(listaClassi.get(comboBox_classe_taratura.getSelectedIndex()-1).getDens_max(), row, 19);
						
						
						LatMassaEffMag effMag= new LatMassaEffMag();
						effMag.setId_misura(SessionBO.idMisura);
						effMag.setComparatore(comboBox_comparatore.getSelectedItem().toString());
						effMag.setCampione(comboBox_campione.getSelectedItem().toString());
						effMag.setValore_nominale_campione(comboBox_valore_nominale.getSelectedItem().toString());
						effMag.setClasseOiml(comboBox_classe_oiml.getSelectedItem().toString());
						if(chckbxSegnoDistintivo.isSelected()) 
						{
							effMag.setSegno_distintivo("*");
						}
						effMag.setEff_mag_L1(new BigDecimal(textField_l1.getText()));
						effMag.setEff_mag_L2(new BigDecimal(textField_l2.getText()));
						effMag.setEff_mag_esito(esito);
						
						effMag.setMc(new BigDecimal(textField_val_taratura_param.getText()));
						effMag.setuMc(new BigDecimal(textField_val_u_param.getText()));
						
						effMag.setClasse_campione(comboBox_classe_campione.getSelectedItem().toString());
						effMag.setClasse_campione_u(new BigDecimal(comboBox_u_classe_campione.getSelectedItem().toString()));  
						effMag.setClasse_campione_min(new BigDecimal(listaClassi.get(comboBox_classe_campione.getSelectedIndex()-1).getDens_min())) ; 
						effMag.setClasse_campione_pc(new BigDecimal(media_ccp)) ; 
						effMag.setClasse_campione_max(new BigDecimal(listaClassi.get(comboBox_classe_campione.getSelectedIndex()-1).getDens_max())) ; 
						
						effMag.setClasse_taratura(comboBox_classe_taratura.getSelectedItem().toString());
						effMag.setClasse_taratura_u(new BigDecimal(comboBox_u_classe_taratura.getSelectedItem().toString()));  
						effMag.setClasse_taratura_min(new BigDecimal(listaClassi.get(comboBox_classe_taratura.getSelectedIndex()-1).getDens_min())) ; 
						effMag.setClasse_taratura_pc(new BigDecimal(media_cct)) ; 
						effMag.setClasse_taratura_max(new BigDecimal(listaClassi.get(comboBox_classe_taratura.getSelectedIndex()-1).getDens_max())) ;
						
						try {
							GestioneMisuraBO.insertEffettoMagnetico(effMag);
						} catch (Exception e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						
					}else 
					{
						JOptionPane.showMessageDialog(null,"Compilare tutti i campi","Errore Compilazione",JOptionPane.INFORMATION_MESSAGE,new ImageIcon(PannelloTOP.class.getResource("/image/error.png")));
					}
				}

				private String getEsitoEffettoMagnetico(String l1, String l2,LatMassaScartiTipo latMassaScartiTipo) {
					
					if(Utility.isNumber(l1) && Utility.isNumber(l2)) 
					{
						double lettuta1=Double.parseDouble(l1);
						double lettuta2=Double.parseDouble(l2);
						
						double valore=Math.abs(lettuta1)-lettuta2;
						
						double ub = Math.sqrt((Math.pow(latMassaScartiTipo.getScarto().doubleValue(),2)+(Math.pow(latMassaScartiTipo.getIncertezzaScarto().doubleValue(),2)/6)));
					
						if(valore<2*ub) 
						{
							return "NON RILEVATO, NO DISTANZIALE";
						}
						else 
						{
							return "RILEVATO, SI DISTANZIALE";
						}
					}
					else 
					{
						return "ERRORE";
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



	private String[] getValNominaleClassi(ArrayList<LatMassaClasseDTO> listClass) {
		
		String[] data =new String[listClass.size()+1];
		
		data[0]="Seleziona...";
		
		for (int i = 0; i < listClass.size(); i++)
		{
			
			data[i+1]=listaClassi.get(i).getVal_nominale();
		}
		return data;
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

			datiCondizioniAmbinetali=GestioneMisuraBO.getCondizioniAmbientaliDati(SessionBO.idMisura);

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
		addColumn("C. OIML");
		addColumn("SEGNO DIST.");
		addColumn("EFF MAG L1");
		addColumn("EFF MAG L2");
		addColumn("EFF MAG ESITO");
		addColumn("Mc");
		addColumn("U (Mc)");
		addColumn("C.C.");
		addColumn("C.C. U");
		addColumn("C.C. \u03C1min");
		addColumn("C.C. \u03C1c");
		addColumn("C.C. \u03C1max");
		addColumn("C.T.");
		addColumn("C.T. U");
		addColumn("C.T. \u03C1min");
		addColumn("C.T. \u03C1x");
		addColumn("C.T. \u03C1max");
		

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
		case 20:
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
class ModelElaborazioneDati extends DefaultTableModel {


	public ModelElaborazioneDati() {
		addColumn("COMPARATORE");
		addColumn("CAMPIONE");
		addColumn("VAL. NOM.");
		addColumn("CAMPIONE L1");
		addColumn("MISURANDO L2");
		addColumn("MISURANDOL3");
		addColumn("CAMPIONE L4");
		addColumn("I-ESIMA DIFF");
		addColumn("I-ESIMA DIFF MEDIA");
		addColumn("SC1");
		addColumn("VC1");
		addColumn("SC2");
		addColumn("Sd");
		addColumn("ESITO");
		addColumn("U(d)");
		addColumn("U(uf)");
		addColumn("CASO");
		addColumn("δmB");
		addColumn("U(δmB)");
		addColumn("mX");
		addColumn("U(mX)");
		

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
		case 20:
			return String.class;
		case 21:
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

