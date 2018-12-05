package it.calverDesktopLAT.gui;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import it.calverDesktopLAT.bo.GestioneCampioneBO;
import it.calverDesktopLAT.bo.GestioneConversioneBO;
import it.calverDesktopLAT.bo.GestioneFormuleBO;
import it.calverDesktopLAT.bo.GestioneStrumentoBO;
import it.calverDesktopLAT.bo.SessionBO;
import it.calverDesktopLAT.dao.SQLiteDAO;
import it.calverDesktopLAT.dto.ConversioneDTO;
import it.calverDesktopLAT.dto.ParametroTaraturaDTO;
import it.calverDesktopLAT.utl.Costanti;
import it.calverDesktopLAT.utl.Utility;
import net.miginfocom.swing.MigLayout;
import java.awt.Font;


public class FrameComposizioneCampioni extends JFrame {

	private static JFrame g;
	private JTextField nFattori;
	public static JTextField txtIncertezza;
	public static JTextField txtMisura;
	public static JPanel panelOperazioni=null;
	public JComboBox[] listaComboCampioni;
	public JComboBox[] listaComboParametri;
	public static JTextField[] listaMisura;
	public static JLabel[] listaLabel;

	public static JTextField[] listaIncertezza;
	public String[] listaCampioniPerStrumento;
	public static JComboBox[] comboUMConver;
	public static JComboBox[] comboFattMolt;
	public static JTextField[] listaMisureConfermata;
	public static JTextField[] listaIncertezzaConfermata;
	public static BigDecimal[] valConvertito;
	public static BigDecimal[] incertezzaConvertita;
	JComboBox comboBoxFormule;
	ParametroTaraturaDTO parametro;
	ConversioneDTO valRapConversione;
	private JTextField risoluzione;
	private JComboBox comboBox_grandezze;
	private JComboBox comboBox_um;
	JComboBox comboBox_fatt_molt;
	private JTextField textField_par_da;
	private JTextField textField_par_a;
	int index;
	private JTextField textField;
	private JScrollPane scrollPane;
	protected JComboBox comboBox_lista_parametri_fnc;
	private boolean testata;
	JButton btnCreaStruttura;

	public FrameComposizioneCampioni(String[] _listaCampioniPerStrumento) throws Exception 
	{
		testata=true;
	//	setResizable(false);
		g=this;

		listaComboCampioni=new JComboBox[0];
		listaComboParametri=new JComboBox[0];
		listaMisura=new JTextField[0];
		listaLabel=new JLabel[0];
		listaIncertezza=new JTextField[0];
		listaCampioniPerStrumento=new String[0];
		comboUMConver=new JComboBox[0];
		comboFattMolt=new JComboBox[0];
		listaMisureConfermata=new JTextField[0];
		listaIncertezzaConfermata=new JTextField[0];
		valConvertito=new BigDecimal[0];
		incertezzaConvertita=new BigDecimal[0];
		index=30;

		panelOperazioni= new JPanel();
		panelOperazioni.setLayout(new MigLayout("", "[grow][grow][grow][grow][grow][grow][grow][grow]", "[]"));

		listaCampioniPerStrumento=_listaCampioniPerStrumento;

		comboBox_lista_parametri_fnc = new JComboBox(SQLiteDAO.getListaCampioniCompletaNoInterpolabili().toArray());
		comboBox_lista_parametri_fnc.setFont(new Font("Arial", Font.PLAIN, 14));

		final ComboboxToolTipRenderer rendererCampioni = new ComboboxToolTipRenderer();

		final ComboboxToolTipRenderer rendererParametri = new ComboboxToolTipRenderer();

		//	UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

		setSize(1200,650);

		getContentPane().setLayout(new MigLayout("", "[grow]"));

		JPanel panel = new JPanel();
		panel.setBackground(Color.WHITE);
		panel.setBounds(0, 0, 1185, 442);
		getContentPane().add(panel,"cell 0 0,grow");
		panel.setLayout(new MigLayout("", "[80px][:150px:150px,grow][:150px:150px][grow][]", "[][][19.00][][300px:350px:800px][][:30px:30px][:100px:100px][:100px:100px][:100px:100px][:100px:100px]"));

		JLabel lblSelezionaFunzione = new JLabel("Seleziona Funzione");
		lblSelezionaFunzione.setFont(new Font("Arial", Font.BOLD, 14));
		panel.add(lblSelezionaFunzione, "flowx,cell 0 0,alignx right");

		String[] listaFormule=GestioneFormuleBO.getListaFormule();

		comboBoxFormule = new JComboBox(listaFormule);
		comboBoxFormule.setFont(new Font("Arial", Font.PLAIN, 14));

		scrollPane = new JScrollPane();
		panel.add(scrollPane, "cell 0 4 5 1,grow");

		panel.add(comboBoxFormule, "flowx,cell 1 0,width 100:150:");

		JLabel lblSelezionaOperatori = new JLabel("N\u00B0 fattori");
		lblSelezionaOperatori.setFont(new Font("Arial", Font.BOLD, 14));
		panel.add(lblSelezionaOperatori, "flowx,cell 2 0,width : 50:,alignx left");



		btnCreaStruttura = new JButton("Aggiungi");
		btnCreaStruttura.setIcon(new ImageIcon(FrameComposizioneCampioni.class.getResource("/image/add.png")));
		btnCreaStruttura.setFont(new Font("Arial", Font.BOLD, 14));
		btnCreaStruttura.addActionListener(new ActionListener() {




			public void actionPerformed(ActionEvent arg0) {
				try {	
					boolean sequence=false;
					int size=0;
					int startParamIndex=0;

					if(comboBox_lista_parametri_fnc.getSelectedIndex()>0) 
					{
						String[] lista=null;
						try {
							int da=Integer.parseInt(textField_par_da.getText());
							int a= Integer.parseInt(textField_par_a.getText());
							sequence=true;
							size=(a-da)+1;
							startParamIndex=Integer.parseInt(textField_par_da.getText());

							if(da<1) 
							{
								JOptionPane.showMessageDialog(null, "In numero minimo della sequenza è 1","Input non valido",JOptionPane.INFORMATION_MESSAGE,new ImageIcon(PannelloTOP.class.getResource("/image/attention.png")));

								return;
							}

							if(da>a) 
							{
								JOptionPane.showMessageDialog(null, "Sequenza numerica non corretta","Input non valido",JOptionPane.INFORMATION_MESSAGE,new ImageIcon(PannelloTOP.class.getResource("/image/error.png")));

								return;
							}

							lista =GestioneCampioneBO.getParametriTaraturaTotali(comboBox_lista_parametri_fnc.getSelectedItem().toString());

							String s=lista[a];
							s=lista[da];

						}catch (ArrayIndexOutOfBoundsException ae) {
							JOptionPane.showMessageDialog(null, "Indice non corretto, questo campione ha: "+(lista.length -1)+" parametri" ,"Input non valido",JOptionPane.INFORMATION_MESSAGE,new ImageIcon(PannelloTOP.class.getResource("/image/error.png")));
							return;
						}	

						catch (Exception e) 
						{
							e.printStackTrace();
						}
					}


					int originalSize=0;

					try {
						if(sequence==false) 
						{
							size=Integer.parseInt(nFattori.getText());
						}
						originalSize=listaComboCampioni.length;

					} catch (Exception e) {
						JOptionPane.showMessageDialog(null, "Il campo n° Fattori accetta solo valori numerici","Input non valido",JOptionPane.INFORMATION_MESSAGE,new ImageIcon(PannelloTOP.class.getResource("/image/error.png")));
					}


					listaComboCampioni= (JComboBox[]) aggiungiComboJComboBox(listaComboCampioni,size);
					listaComboParametri= (JComboBox[]) aggiungiComboJComboBox(listaComboParametri,size);
					listaLabel=  (JLabel[]) aggiungiComboJLabel(listaLabel,size);
					listaMisura=  (JTextField[]) aggiungiComboJTextField(listaMisura,size);
					listaIncertezza = (JTextField[]) aggiungiComboJTextField(listaIncertezza,size);
					comboUMConver= (JComboBox[]) aggiungiComboJComboBox(comboUMConver,size);
					comboFattMolt= (JComboBox[]) aggiungiComboJComboBox(comboFattMolt,size);
					listaMisureConfermata= (JTextField[]) aggiungiComboJTextField(listaMisureConfermata,size);
					listaIncertezzaConfermata = (JTextField[]) aggiungiComboJTextField(listaIncertezzaConfermata,size);
					valConvertito= (BigDecimal[]) aggiungiComboBigDecimal(valConvertito,size);
					incertezzaConvertita= (BigDecimal[]) aggiungiComboBigDecimal(incertezzaConvertita,size);


					JLabel labCodiceCampione= new JLabel("Codice Campione");
					JLabel labParametriTaratura = new JLabel("Parametro Taratura");
					JLabel labUM= new JLabel("UM");
					JLabel labMisura = new JLabel("Valore Campione");
					JLabel labIncertezza = new JLabel("Incertezza Campione");
					JLabel labUMConversione= new JLabel("UM Conversione");
					JLabel labfattMolt = new JLabel("Fattore molt.");
					JLabel labMisuraConf = new JLabel("Valore Campione Conf");
					JLabel labIncertezzaConf = new JLabel("Incertezza Campione Conf");

//					labCodiceCampione.setBounds(5, 5, 150, 25);
//					labParametriTaratura.setBounds(160, 5, 150, 25);
//					labUM.setBounds(310, 5, 25, 25);
//					labMisura.setBounds(340, 5, 150, 25);
//					labIncertezza.setBounds(450, 5, 150, 25);
//					labUMConversione.setBounds(600, 5, 150, 25);
//					labfattMolt.setBounds(750, 5, 150, 25);
//					labMisuraConf.setBounds(900, 5, 150, 25);
//					labIncertezzaConf.setBounds(1050, 5, 150, 25);

					if(testata==true)
					{
					panelOperazioni.add(labCodiceCampione,"cell 0 0");
					panelOperazioni.add(labParametriTaratura,"cell 1 0");
					panelOperazioni.add(labUM,"cell 2 0");
					panelOperazioni.add(labMisura,"cell 3 0");
					panelOperazioni.add(labIncertezza,"cell 4 0");
					panelOperazioni.add(labUMConversione,"cell 5 0");
					panelOperazioni.add(labfattMolt,"cell 6 0");
					panelOperazioni.add(labMisuraConf,"cell 7 0");
					panelOperazioni.add(labIncertezzaConf,"cell 8 0");
					
					testata=false;
					}
					for (int i = originalSize; i < listaComboCampioni.length; i++) {

						final int indice=i;

						if(sequence==true) 
						{							

							listaComboCampioni[i]= new JComboBox<>(SQLiteDAO.getListaCampioniCompletaNoInterpolabili().toArray());

							listaComboCampioni[i].setBounds(5, index, 140, 25);

							listaComboCampioni[i].setRenderer(rendererCampioni);

							rendererCampioni.setTooltips(SQLiteDAO.getListaCampioniCompletaNoInterpolabili());



							listaComboParametri[i]= new JComboBox<>();

							listaComboParametri[i].setRenderer(rendererParametri);

							ArrayList<String> listaTipiGrandezza = GestioneStrumentoBO.getListaTipoGrandezza(SessionBO.idStrumento);


							String codiceCampione=comboBox_lista_parametri_fnc.getSelectedItem().toString();

							String[] listaParametriTaratura=GestioneCampioneBO.getParametriTaraturaTotali(codiceCampione);

							List<String> toolTipParametri = new ArrayList<String>();

							for(String str : listaParametriTaratura) {
								listaComboParametri[i].addItem(str);
								toolTipParametri.add(str);
							}

							rendererParametri.setTooltips(toolTipParametri);

							listaComboCampioni[i].setSelectedIndex(comboBox_lista_parametri_fnc.getSelectedIndex());
							listaComboParametri[i].setSelectedIndex(startParamIndex);
							startParamIndex++;				

							listaComboParametri[i].setBounds(160, index, 140, 25);
						}

						else
						{
							/*Controllare con terenzio*/
							//		listaComboCampioni[i]= new JComboBox<>(listaCampioniPerStrumento);

							listaComboCampioni[i]= new JComboBox<>(SQLiteDAO.getListaCampioniCompleta().toArray());
							listaComboCampioni[i].setRenderer(rendererCampioni);

							rendererCampioni.setTooltips(SQLiteDAO.getListaCampioniCompleta());


				//			listaComboCampioni[i].setBounds(5, index, 140, 25);

							listaComboParametri[i]= new JComboBox<>();
							listaComboParametri[i].setRenderer(rendererParametri);
							listaComboParametri[i].addItem("Seleziona Parametro ...");
					//		listaComboParametri[i].setBounds(160, index, 140, 25);
						}


						
						listaLabel[i]=new JLabel("");
						
						listaMisura[i] = new JTextField();
						listaMisura[i].setEditable(false);
				//		listaMisura[i].setBounds(340, index, 100, 25);

						listaIncertezza[i] = new JTextField();
						listaIncertezza[i].setEditable(false);
				//		listaIncertezza[i].setBounds(450, index, 100, 25);

						comboUMConver[i]= new JComboBox<>();
						comboUMConver[i].addItem("Seleziona UM Conversione");
				//		comboUMConver[i].setBounds(600, index, 140, 25);

						comboFattMolt[i]= new JComboBox<>();
				//		comboFattMolt[i].setBounds(750, index, 140, 25);

						listaMisureConfermata[i] = new JTextField();
						listaMisureConfermata[i].setEditable(false);
					//	listaMisureConfermata[i].setBounds(900, index, 100, 25);

						listaIncertezzaConfermata[i] = new JTextField();
						listaIncertezzaConfermata[i].setEditable(false);
				//		listaIncertezzaConfermata[i].setBounds(1050, index, 100, 25);

						panelOperazioni.add(listaComboCampioni[i],"cell 0 "+i+1+",grow");
						panelOperazioni.add(listaComboParametri[i],"cell 1 "+i+1+",grow");
						panelOperazioni.add(listaLabel[i],"cell 2 "+i+1+",grow");
						panelOperazioni.add(listaMisura[i],"cell 3 "+i+1+",grow");
						panelOperazioni.add(listaIncertezza[i],"cell 4 "+i+1+",grow");
						panelOperazioni.add(comboUMConver[i],"cell 5 "+i+1+",grow");
						panelOperazioni.add(comboFattMolt[i],"cell 6 "+i+1+",grow");
						panelOperazioni.add(listaMisureConfermata[i],"cell 7 "+i+1+",grow");
						panelOperazioni.add(listaIncertezzaConfermata[i],"cell 8 "+i+1+",grow");

						doChange(i);
						index+=30;

						listaComboCampioni[i].addActionListener(new ActionListener() {

							@Override
							public void actionPerformed(ActionEvent arg0) {
								try {
									String codiceCampione=listaComboCampioni[indice].getSelectedItem().toString();

									//	ArrayList<String> listaTipiGrandezza = GestioneStrumentoBO.getListaTipoGrandezza(SessionBO.idStrumento);

									//	boolean interpolato=SQLiteDAO.isInterpolabile(codiceCampione);

									//	String[] listaParametriTaratura=GestioneCampioneBO.getParametriTaratura(codiceCampione,interpolato,listaTipiGrandezza);

									String[] listaParametriTaratura=GestioneCampioneBO.getParametriTaraturaTotali(codiceCampione);

									listaComboParametri[indice].removeAllItems();
									List<String> toolTipParametri = new ArrayList<String>();
									for(String str : listaParametriTaratura) {
										listaComboParametri[indice].addItem(str);
										toolTipParametri.add(str);
									}

									rendererParametri.setTooltips(toolTipParametri);
								} catch (Exception e) {
									e.printStackTrace();
								}


							}
						});

						listaComboParametri[i].addActionListener(new ActionListener() {

							public void actionPerformed(ActionEvent arg0) {


								doChange(indice);

							}


						});

						comboUMConver[indice].addActionListener(new ActionListener() {


							public void actionPerformed(ActionEvent e) {

								if(comboUMConver[indice].getSelectedIndex()>0)
								{	
									try {	
										String umCon=comboUMConver[indice].getSelectedItem().toString();

										BigDecimal[]minMaxScala=null;	


										minMaxScala=GestioneConversioneBO.getMinMaxScala(listaComboCampioni[indice].getSelectedItem().toString(),listaComboParametri[indice].getSelectedItem().toString());

										valRapConversione=GestioneConversioneBO.getValoreConvertito(parametro,umCon,parametro.getTipoGrandezza(),minMaxScala[0],minMaxScala[1]);

										double potenza=0;

										if(!parametro.getUm().equals(parametro.getUm_fond()))
										{
											int index=parametro.getUm().length()-parametro.getUm_fond().length();	
											String str=parametro.getUm().substring(0,index);
											potenza=SQLiteDAO.getPotenza(str);
										}

										double grado=0;

										if(valRapConversione.getPotenza()==10 && potenza==1)
										{
											grado=1;
										}else
										{
											grado=Math.pow(valRapConversione.getPotenza(),potenza);	
										}



										valConvertito[indice]=	new BigDecimal(listaMisura[indice].getText()).multiply(valRapConversione.getFattoreConversione()).multiply(new BigDecimal(grado));

										incertezzaConvertita[indice]=new BigDecimal(listaIncertezza[indice].getText()).multiply(valRapConversione.getFattoreConversione()).multiply(new BigDecimal(grado));

										listaMisureConfermata[indice].setText(valConvertito[indice].setScale(10,RoundingMode.HALF_UP).toPlainString());

										listaIncertezzaConfermata[indice].setText(incertezzaConvertita[indice].setScale(10,RoundingMode.HALF_UP).toPlainString());


										if(valRapConversione.isValidita()==false)
										{
											comboFattMolt[indice].setEditable(false);
										}
										comboFattMolt[indice].setSelectedIndex(7);



									}  catch (Exception e1) {
										// TODO Auto-generated catch block
										e1.printStackTrace();
									}
								}
							}
						});

						comboFattMolt[indice].addActionListener(new ActionListener() {


							public void actionPerformed(ActionEvent e) {

								if(comboFattMolt[indice].getSelectedIndex()>0)
								{
									String classe=comboFattMolt[indice].getSelectedItem().toString();

									if(valRapConversione.getPotenza()==1)
									{
										valRapConversione.setPotenza(10);
									}

									double gradoFM=GestioneConversioneBO.getPotenzaPerClasse(classe,valRapConversione.getPotenza());

									listaMisureConfermata[indice].setText(valConvertito[indice].divide(new BigDecimal(gradoFM),RoundingMode.HALF_UP).setScale(10,RoundingMode.HALF_UP).toPlainString());

									listaIncertezzaConfermata[indice].setText(incertezzaConvertita[indice].divide(new BigDecimal(gradoFM),RoundingMode.HALF_UP).setScale(10,RoundingMode.HALF_UP).toPlainString());

								}

							}
						});


					}


					panelOperazioni.setPreferredSize(new Dimension(1160,index));
					scrollPane.setViewportView(panelOperazioni);

					g.update(g.getGraphics());

				} catch (Exception e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
			}



			public void doChange(int indice) 
			{
				try {

					final int ind=indice;

					if(listaComboParametri[indice].getSelectedItem()!=null){

						ArrayList<ParametroTaraturaDTO> listaParametri=GestioneCampioneBO.getParametriTaraturaSelezionato(listaComboParametri[indice].getSelectedItem().toString(),listaComboCampioni[indice].getSelectedItem().toString());


						if(listaParametri.size()>1)
						{


							parametro=listaParametri.get(0);
							SwingUtilities.invokeLater(new Runnable(){
								public void run() 
								{
									try
									{

										JFrame f= new FrameScala(listaComboCampioni[ind].getSelectedItem().toString(),listaComboParametri[ind].getSelectedItem().toString(),ind,parametro,listaComboParametri[ind]);
										URL iconURL = this.getClass().getResource("/image/logo.png");


										ImageIcon img = new ImageIcon(iconURL);
										f.setIconImage(img.getImage());

										f.setDefaultCloseOperation(1);
										f.setVisible(true);

									}
									catch(Exception ex)
									{
										//	GeneralGUI.printException(ex);
										ex.printStackTrace();
									}
								}

							});

						}

						if(listaParametri.size()==1)
						{
							parametro=listaParametri.get(0);

							
							listaLabel[indice].setText(parametro.getUm());
							listaMisura[indice].setText(parametro.getValoreTaratura().setScale(10,RoundingMode.HALF_UP).toPlainString());
							BigDecimal incertezza=GestioneCampioneBO.getIncertezza(parametro.getIncertezzaAssoluta(),parametro.getIncertezzaRelativa(),parametro.getValore_nominale());
							listaIncertezza[indice].setText(incertezza.setScale(10,RoundingMode.HALF_UP).toPlainString());

							//JLabel labUm= new JLabel(parametro.getUm());

							//panelOperazioni.add(new JLabel(""),"cell 1 "+indice+1);
							//panelOperazioni.add(labUm,"cell 1 "+indice+1);

							listaMisureConfermata[indice].setText(parametro.getValoreTaratura().setScale(10,RoundingMode.HALF_UP).toPlainString());
							listaIncertezzaConfermata[indice].setText(incertezza.setScale(10,RoundingMode.HALF_UP).toPlainString());

							String[] uMConversione;
							String[] fattMolt;


							uMConversione = GestioneConversioneBO.getListaUMConvertibili(parametro.getUm_fond(),parametro.getTipoGrandezza());
							fattMolt= GestioneConversioneBO.getListaFattoriMoltiplicativi();

							comboUMConver[indice].removeAllItems();
							comboFattMolt[indice].removeAll();

							for(String str : uMConversione) {
								comboUMConver[indice].addItem(str);
							}

							for(String str : fattMolt) {
								comboFattMolt[indice].addItem(str);
							}

						}

					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}





		});
		panel.add(btnCreaStruttura, "flowx,cell 3 0,width :200:200");

		JLabel lblSequenza = new JLabel("Sequenza");
		lblSequenza.setFont(new Font("Arial", Font.BOLD, 14));
		panel.add(lblSequenza, "cell 0 1,alignx right");



		comboBox_lista_parametri_fnc.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {


				if(comboBox_lista_parametri_fnc.getSelectedIndex()==0) 
				{
					textField_par_a.setEnabled(false);
					textField_par_da.setEnabled(false);
					nFattori.setEnabled(true);
				}
				else 
				{
					textField_par_a.setEnabled(true);
					textField_par_da.setEnabled(true);

					textField_par_a.setEditable(true);
					textField_par_da.setEditable(true);


					nFattori.setEnabled(false);
				}


			}
		});
		panel.add(comboBox_lista_parametri_fnc, "flowx,cell 1 1,width 100:150:");

		JLabel lblDaParametro = new JLabel("Parametri");
		lblDaParametro.setFont(new Font("Arial", Font.BOLD, 14));
		panel.add(lblDaParametro, "flowx,cell 2 1,width :50:,alignx left");

		JButton btnAggiungiSequenza = new JButton("Aggiungi Sequenza");
		btnAggiungiSequenza.setIcon(new ImageIcon(FrameComposizioneCampioni.class.getResource("/image/seq.png")));
		btnAggiungiSequenza.setFont(new Font("Arial", Font.BOLD, 14));
		btnAggiungiSequenza.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				if(comboBox_lista_parametri_fnc.getSelectedIndex()>0) 
				{
					btnCreaStruttura.doClick();
				}else 
				{
					JOptionPane.showMessageDialog(null, "Selezionare campione per sequenza","Input non valido",JOptionPane.INFORMATION_MESSAGE,new ImageIcon(PannelloTOP.class.getResource("/image/error.png")));
				}
			}
		});
		panel.add(btnAggiungiSequenza, "cell 3 1,width 200:200:");

		JLabel lblCostante = new JLabel("Costante");
		lblCostante.setFont(new Font("Arial", Font.BOLD, 14));
		panel.add(lblCostante, "cell 0 2,alignx right");


		JButton btnAggiungiCostante = new JButton("Aggiungi Costante");
		btnAggiungiCostante.setIcon(new ImageIcon(FrameComposizioneCampioni.class.getResource("/image/const.png")));
		btnAggiungiCostante.setFont(new Font("Arial", Font.BOLD, 14));
		btnAggiungiCostante.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				listaComboCampioni= (JComboBox[]) aggiungiComboJComboBox(listaComboCampioni,1);
				listaComboParametri= (JComboBox[]) aggiungiComboJComboBox(listaComboParametri,1);
				listaLabel=  (JLabel[]) aggiungiComboJLabel(listaLabel,1);
				listaMisura=  (JTextField[]) aggiungiComboJTextField(listaMisura,1);
				listaIncertezza = (JTextField[]) aggiungiComboJTextField(listaIncertezza,1);
				comboUMConver= (JComboBox[]) aggiungiComboJComboBox(comboUMConver,1);
				comboFattMolt= (JComboBox[]) aggiungiComboJComboBox(comboFattMolt,1);
				listaMisureConfermata= (JTextField[]) aggiungiComboJTextField(listaMisureConfermata,1);
				listaIncertezzaConfermata = (JTextField[]) aggiungiComboJTextField(listaIncertezzaConfermata,1);
				valConvertito= (BigDecimal[]) aggiungiComboBigDecimal(valConvertito,1);
				incertezzaConvertita= (BigDecimal[]) aggiungiComboBigDecimal(incertezzaConvertita,1);


				JLabel labCodiceCampione= new JLabel("Codice Campione");
				JLabel labParametriTaratura = new JLabel("Parametro Taratura");
				JLabel labUM= new JLabel("UM");
				JLabel labMisura = new JLabel("Valore Campione");
				JLabel labIncertezza = new JLabel("Incerteazza Campione");
				JLabel labUMConversione= new JLabel("UM Conversione");
				JLabel labfattMolt = new JLabel("Fattore molt.");
				JLabel labMisuraConf = new JLabel("Valore Campione Conf");
				JLabel labIncertezzaConf = new JLabel("Incerteazza Campione Conf");

				if(testata==true)
				{
				panelOperazioni.add(labCodiceCampione,"cell 0 0");
				panelOperazioni.add(labParametriTaratura,"cell 1 0");
				panelOperazioni.add(labUM,"cell 2 0");
				panelOperazioni.add(labMisura,"cell 3 0");
				panelOperazioni.add(labIncertezza,"cell 4 0");
				panelOperazioni.add(labUMConversione,"cell 5 0");
				panelOperazioni.add(labfattMolt,"cell 6 0");
				panelOperazioni.add(labMisuraConf,"cell 7 0");
				panelOperazioni.add(labIncertezzaConf,"cell 8 0");
				
				testata=false;
				}

				int sizeCost=listaComboCampioni.length-1;
				listaComboCampioni[sizeCost]= new JComboBox<>();

			//	listaComboCampioni[sizeCost].setBounds(5, index, 140, 25);

				listaComboParametri[sizeCost]= new JComboBox<>();
		//		listaComboParametri[sizeCost].setBounds(160, index, 140, 25);

				listaLabel[sizeCost] = new JLabel("");
				
				listaMisura[sizeCost] = new JTextField("0.0000000000");
				listaMisura[sizeCost].setEditable(false);
		//		listaMisura[sizeCost].setBounds(340, index, 100, 25);

				listaIncertezza[sizeCost] = new JTextField("0.0000000000");
				listaIncertezza[sizeCost].setEditable(false);
		//		listaIncertezza[sizeCost].setBounds(450, index, 100, 25);

				comboUMConver[sizeCost]= new JComboBox<>();
		//		comboUMConver[sizeCost].setBounds(600, index, 140, 25);

				comboFattMolt[sizeCost]= new JComboBox<>();
		//		comboFattMolt[sizeCost].setBounds(750, index, 140, 25);

				try {
					listaMisureConfermata[sizeCost] = new JTextField(new BigDecimal(textField.getText()).setScale(Costanti.SCALA,RoundingMode.HALF_UP).toPlainString());
				} catch (NumberFormatException e) 
				{
					listaMisureConfermata[sizeCost] = new JTextField();
				}


				//	listaMisureConfermata[sizeCost] = new JTextField();
				listaMisureConfermata[sizeCost].setEditable(true);
			//	listaMisureConfermata[sizeCost].setBounds(900, index, 100, 25);

				listaIncertezzaConfermata[sizeCost] = new JTextField("0.0000000000");
				listaIncertezzaConfermata[sizeCost].setEditable(true);
			//	listaIncertezzaConfermata[sizeCost].setBounds(1050, index, 100, 25);

//				panelOperazioni.add(listaComboCampioni[sizeCost]);
//				panelOperazioni.add(listaComboParametri[sizeCost]);
//				panelOperazioni.add(listaMisura[sizeCost]);
//				panelOperazioni.add(listaIncertezza[sizeCost]);
//				panelOperazioni.add(comboUMConver[sizeCost]);
//				panelOperazioni.add(comboFattMolt[sizeCost]);
//				panelOperazioni.add(listaMisureConfermata[sizeCost]);
//				panelOperazioni.add(listaIncertezzaConfermata[sizeCost]);

				panelOperazioni.add(listaComboCampioni[sizeCost],"cell 0 "+sizeCost+1+",grow");
				panelOperazioni.add(listaComboParametri[sizeCost],"cell 1 "+sizeCost+1+",grow");
				panelOperazioni.add(listaLabel[sizeCost],"cell 2 "+sizeCost+1+",grow");
				panelOperazioni.add(listaMisura[sizeCost],"cell 3 "+sizeCost+1+",grow");
				panelOperazioni.add(listaIncertezza[sizeCost],"cell 4 "+sizeCost+1+",grow");
				panelOperazioni.add(comboUMConver[sizeCost],"cell 5 "+sizeCost+1+",grow");
				panelOperazioni.add(comboFattMolt[sizeCost],"cell 6 "+sizeCost+1+",grow");
				panelOperazioni.add(listaMisureConfermata[sizeCost],"cell 7 "+sizeCost+1+",grow");
				panelOperazioni.add(listaIncertezzaConfermata[sizeCost],"cell 8 "+sizeCost+1+",grow");

				index+=30;

				panelOperazioni.setPreferredSize(new Dimension(1160,index));
				scrollPane.setViewportView(panelOperazioni);

				g.update(g.getGraphics());
			}
		});

		textField = new JTextField();
		textField.setFont(new Font("Arial", Font.PLAIN, 14));
		panel.add(textField, "cell 1 2,growx");
		textField.setColumns(10);
		panel.add(btnAggiungiCostante, "cell 3 2,width :200:200");

		txtIncertezza = new JTextField();
		txtIncertezza.setFont(new Font("Arial", Font.BOLD, 14));
		txtIncertezza.setText("Incertezza");
		txtIncertezza.setEditable(false);
		panel.add(txtIncertezza, "cell 1 5,width 100:150:200,growy");
		txtIncertezza.setColumns(10);

		nFattori = new JTextField();
		nFattori.setFont(new Font("Arial", Font.PLAIN, 14));
		panel.add(nFattori, "cell 2 0,width :25:,gapx 8");
		nFattori.setColumns(10);

		textField_par_da = new JTextField();
		textField_par_da.setFont(new Font("Arial", Font.PLAIN, 14));
		textField_par_da.setEditable(false);
		panel.add(textField_par_da, "cell 2 1,width :25:");
		textField_par_da.setColumns(10);

		JLabel label = new JLabel(" - ");
		panel.add(label, "cell 2 1");

		textField_par_a = new JTextField();
		textField_par_a.setFont(new Font("Arial", Font.PLAIN, 14));
		textField_par_a.setEditable(false);
		textField_par_a.setColumns(10);
		panel.add(textField_par_a, "cell 2 1,width :25:");
				
				
				
				
				
						JButton btnCalcola = new JButton("Calcola");
						btnCalcola.setIcon(new ImageIcon(FrameComposizioneCampioni.class.getResource("/image/calcola.png")));
						btnCalcola.setFont(new Font("Arial", Font.BOLD, 14));
						panel.add(btnCalcola, "cell 2 5");
						
						
						
								btnCalcola.addActionListener(new ActionListener() {
						
									public void actionPerformed(ActionEvent arg0) {
						
						
										if( comboBoxFormule.getSelectedIndex()==1)
										{
											GestioneFormuleBO.sommaSemplice(listaMisureConfermata,listaIncertezzaConfermata);
										}
										if( comboBoxFormule.getSelectedIndex()==2)
										{
											GestioneFormuleBO.sommaPropagata(listaMisureConfermata,listaIncertezzaConfermata);
										}
										if( comboBoxFormule.getSelectedIndex()==3)
										{
											GestioneFormuleBO.sottrazioneSemplice(listaMisureConfermata,listaIncertezzaConfermata);
										}
										if( comboBoxFormule.getSelectedIndex()==4)
										{
											GestioneFormuleBO.sottrazionePropagata(listaMisureConfermata,listaIncertezzaConfermata);
										}
						
										if( comboBoxFormule.getSelectedIndex()==5)
										{
											GestioneFormuleBO.moltiplicazioneSemplice(listaMisureConfermata,listaIncertezzaConfermata);
										}
						
										if( comboBoxFormule.getSelectedIndex()==6)
										{
											if(listaMisura.length>1 && listaMisura.length<=3)
											{
						
												GestioneFormuleBO.moltiplicazionePropagata(listaMisureConfermata,listaIncertezzaConfermata);
											}
											else
											{
												JOptionPane.showMessageDialog(null, "Quest'operazione può essere eseguita con 2 oppure massimo 3 operandi","N° Operandi errato",JOptionPane.INFORMATION_MESSAGE,new ImageIcon(PannelloTOP.class.getResource("/image/error.png")));
											}
						
						
										}
						
										if( comboBoxFormule.getSelectedIndex()==7)	
										{
											if(listaMisura.length==2)
											{
						
												GestioneFormuleBO.divisioneSemplice(listaMisureConfermata,listaIncertezzaConfermata);
											}
											else
											{
												JOptionPane.showMessageDialog(null, "Quest'operazione può essere eseguita solo con 2 operandi","N° Operandi errato",JOptionPane.INFORMATION_MESSAGE,new ImageIcon(PannelloTOP.class.getResource("/image/error.png")));
											}
										}
										if( comboBoxFormule.getSelectedIndex()==8)	
										{
											if(listaMisura.length==2)
											{
						
												GestioneFormuleBO.divisionePropagata(listaMisureConfermata,listaIncertezzaConfermata);
											}
											else
											{
												JOptionPane.showMessageDialog(null, "Quest'operazione può essere eseguita solo con 2 operandi","N° Operandi errato",JOptionPane.INFORMATION_MESSAGE,new ImageIcon(PannelloTOP.class.getResource("/image/error.png")));
											}
										}
									}
								});
		
				JLabel lblUm = new JLabel("Grandezza");
				lblUm.setFont(new Font("Arial", Font.BOLD, 14));
				panel.add(lblUm, "flowx,cell 0 7");
																
																		comboBox_grandezze = new JComboBox(GestioneConversioneBO.getListaGrandezze());
																		comboBox_grandezze.setFont(new Font("Arial", Font.PLAIN, 14));
																		comboBox_grandezze.addActionListener(new ActionListener() {
																			public void actionPerformed(ActionEvent arg0) {

																				if(comboBox_grandezze.getSelectedIndex()!=0)
																				{

																					Object[] listaUM=null;

																					try {
																						listaUM = GestioneConversioneBO.getListaUM(comboBox_grandezze.getSelectedItem().toString());



																						comboBox_um.removeAllItems();
																						comboBox_fatt_molt.removeAllItems();

																						for (int i = 0; i < listaUM.length; i++) {
																							comboBox_um.addItem(listaUM[i].toString());
																						}

																						String[] listaFattori = GestioneConversioneBO.getListaFattoriMoltiplicativi();

																						for (int i = 0; i < listaFattori.length; i++) {
																							comboBox_fatt_molt.addItem(listaFattori[i].toString());
																						}
																						comboBox_fatt_molt.setSelectedIndex(7);
																					} catch (Exception e) {
																						// TODO Auto-generated catch block
																						e.printStackTrace();
																					}
																				}
																			}
																		});
																		
																		
																				panel.add(comboBox_grandezze, "cell 1 7 2 1,width 150:200:250");
																
																		JLabel lblUm_1 = new JLabel("UM");
																		lblUm_1.setFont(new Font("Arial", Font.BOLD, 14));
																		panel.add(lblUm_1, "cell 0 8");
																				
																						comboBox_um = new JComboBox();
																						comboBox_um.setFont(new Font("Arial", Font.PLAIN, 14));
																						panel.add(comboBox_um, "cell 1 8 2 1,width 150:200:250");
																		
																				JLabel lblFattoreMoltiplicativo = new JLabel("Fattore Moltiplicativo");
																				lblFattoreMoltiplicativo.setFont(new Font("Arial", Font.BOLD, 14));
																				panel.add(lblFattoreMoltiplicativo, "cell 0 9");
																				
																						comboBox_fatt_molt = new JComboBox();
																						comboBox_fatt_molt.setFont(new Font("Arial", Font.PLAIN, 14));
																						panel.add(comboBox_fatt_molt, "cell 1 9,width 100:150:150");
																						
																								JLabel lblRisoluzione = new JLabel("Cifre Significative");
																								lblRisoluzione.setFont(new Font("Arial", Font.BOLD, 14));
																								panel.add(lblRisoluzione, "cell 0 10");
																								
																										risoluzione = new JTextField();
																										risoluzione.setFont(new Font("Arial", Font.PLAIN, 14));
																										panel.add(risoluzione, "cell 1 10,width 75:75:100");
																										risoluzione.setColumns(10);
																										
																												aggiungiTastierino(risoluzione, g);
																												
																														JButton btnConferma = new JButton("Conferma");
																														btnConferma.setIcon(new ImageIcon(FrameComposizioneCampioni.class.getResource("/image/continue.png")));
																														btnConferma.setFont(new Font("Arial", Font.BOLD, 14));
																														panel.add(btnConferma, "cell 4 5 1 6");
																														
																														
																																txtMisura = new JTextField();
																																txtMisura.setFont(new Font("Arial", Font.BOLD, 14));
																																txtMisura.setText("Misura");
																																txtMisura.setEditable(false);
																																panel.add(txtMisura, "cell 0 5,width 100:150:200,grow");
																																txtMisura.setColumns(10);
																														
																														
																																btnConferma.addActionListener(new ActionListener() {
																														
																																	@Override
																																	public void actionPerformed(ActionEvent arg0) {
																														
																														
																																		if(comboBox_um.getSelectedItem()!=null && risoluzione.getText().length()>0) 
																																		{
																														
																																			if(!txtMisura.getText().equals("Misura"))
																																			{
																																				BigDecimal misura=new BigDecimal(txtMisura.getText());
																																				BigDecimal incertezza=new BigDecimal(txtIncertezza.getText());
																														
																																				String txt= getTxtComposizione(listaComboCampioni,listaComboParametri);
																														
																																				String cmp =getListaCampioni(listaComboCampioni);
																														
																																				String um ="";
																														
																																				BigDecimal risoluzioneLab=new BigDecimal("0.1");
																														
																																				try 
																																				{
																																					String um_combo=comboBox_um.getSelectedItem().toString();
																																					um_combo=um_combo.substring(0,um_combo.indexOf("@")).trim();
																														
																																					String fattMolt=comboBox_fatt_molt.getSelectedItem().toString();
																																					fattMolt=fattMolt.substring(fattMolt.indexOf("(")+1,fattMolt.indexOf(")"));
																														
																																					um=fattMolt.concat(um_combo);
																														
																																					Integer.parseInt(risoluzione.getText());
																														
																																					risoluzioneLab=Utility.getRisoluzione(risoluzione.getText());
																														
																																					FrameMisura.setPannelloMisura(txt,cmp,misura,incertezza,um,risoluzioneLab);
																																					g.dispose();
																																				}
																																				catch (NumberFormatException nfe) 
																																				{
																																					JOptionPane.showMessageDialog(null, "Errore input Cifre Significative","Errore",JOptionPane.INFORMATION_MESSAGE,new ImageIcon(PannelloTOP.class.getResource("/image/error.png")));
																																				}
																																				catch (Exception e) {
																																					e.printStackTrace();
																																				}
																																			}
																																		}
																																		else 
																																		{
																																			JOptionPane.showMessageDialog(null, "Selezionare UM e risoluzione prima di confermare","Conferma",JOptionPane.INFORMATION_MESSAGE,new ImageIcon(PannelloTOP.class.getResource("/image/attention.png")));
																																		}
																														
																																	}
																														
																																	private String getListaCampioni(JComboBox[] listaComboCampioni) {
																																		String toReturn="";
																																		HashMap<String,String> lista = new HashMap<>();
																														
																																		for (int i = 0; i < listaComboCampioni.length; i++) {
																														
																																			if(listaComboCampioni[i].getSelectedItem()!=null)
																																			{
																																				String campione= listaComboCampioni[i].getSelectedItem().toString();
																														
																																				if(!campione.equals("null")) 
																																				{
																																					lista.put(campione, campione);
																																				}
																																				}
																																		}
																														
																																		Iterator it = lista.entrySet().iterator();
																																		while (it.hasNext()) {
																																			Map.Entry pair = (Map.Entry)it.next();
																																			toReturn=toReturn+pair.getValue()+"|";
																																			it.remove(); 
																																		}
																														
																																		return toReturn.substring(0,toReturn.length()-1);
																																	}
																														
																																	private String getTxtComposizione(JComboBox[] listaComboCampioni,JComboBox[] listaComboParametri) {
																																		String toReturn="";
																																		String symbol = comboBoxFormule.getSelectedItem().toString().substring(1,2);
																														
																																		for (int i = 0; i < listaComboCampioni.length; i++) {
																														
																																			if(listaComboCampioni[i].getSelectedItem()!=null) {
																																				if(i==0)
																																				{
																																					toReturn=toReturn + listaComboCampioni[i].getSelectedItem().toString()+"("+listaComboParametri[i].getSelectedItem().toString()+")";
																																				}
																																				else
																																				{
																																					toReturn=toReturn +" "+ symbol+" " + listaComboCampioni[i].getSelectedItem().toString()+"("+listaComboParametri[i].getSelectedItem().toString()+")";
																																				}
																																			}
																																		}
																																		return toReturn;
																																	}
																																});
	}

	public static void update() {

		g.update(g.getGraphics());

	}

	private Object[] aggiungiComboJComboBox(Object[] lista, int size) {

		int maxSize=lista.length;

		Object[] newList = Arrays.copyOf(lista, maxSize+size);

		for (int i = 0; i < size; i++) 
		{

			newList[maxSize+i]=new JComboBox();
		}
		return newList;
	}
	private Object[] aggiungiComboJLabel(Object[] lista, int size) {

		int maxSize=lista.length;

		Object[] newList = Arrays.copyOf(lista, maxSize+size);

		for (int i = 0; i < size; i++) 
		{

			newList[maxSize+i]=new JLabel();
		}
		return newList;
	}
	private Object[] aggiungiComboJTextField(Object[] lista, int size) {

		int maxSize=lista.length;

		Object[] newList = Arrays.copyOf(lista, maxSize+size);

		for (int i = 0; i < size; i++) 
		{

			newList[maxSize+i]=new JTextField();
		}
		return newList;
	}
	private Object[] aggiungiComboBigDecimal(Object[] lista, int size) {

		int maxSize=lista.length;

		Object[] newList = Arrays.copyOf(lista, maxSize+size);

		for (int i = 0; i < size; i++) 
		{

			newList[maxSize+i]=new BigDecimal(0);
		}
		return newList;
	}
	private void aggiungiTastierino(final JTextField txtFld, final JFrame frame) {


		txtFld.addMouseListener(new MouseAdapter(){
			@Override
			public void mouseClicked(MouseEvent e){

				SwingUtilities.invokeLater(new Runnable(){
					public void run() 
					{
						try
						{
							Tastiera f=new Tastiera(txtFld,frame);

							f.setDefaultCloseOperation(1);
							f.setVisible(true);

						}
						catch(Exception ex)
						{
							//	GeneralGUI.printException(ex);
							ex.printStackTrace();
						}
					}

				});

			}
		});
	}
}
