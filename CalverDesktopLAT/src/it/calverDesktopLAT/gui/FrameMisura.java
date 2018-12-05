package it.calverDesktopLAT.gui;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import gnu.io.NoSuchPortException;
import it.calverDesktopLAT.bo.GestioneCampioneBO;
import it.calverDesktopLAT.bo.GestioneConversioneBO;
import it.calverDesktopLAT.bo.GestioneDASM;
import it.calverDesktopLAT.bo.GestioneMisuraBO;
import it.calverDesktopLAT.bo.GestioneSonda;
import it.calverDesktopLAT.bo.GestioneStrumentoBO;
import it.calverDesktopLAT.bo.SessionBO;
import it.calverDesktopLAT.bo.TwoWaySerialComm;
import it.calverDesktopLAT.dao.SQLiteDAO;
import it.calverDesktopLAT.dto.CampioneCorrettoDTO;
import it.calverDesktopLAT.dto.ConversioneDTO;
import it.calverDesktopLAT.dto.DatiDASM_DTO;
import it.calverDesktopLAT.dto.MisuraDTO;
import it.calverDesktopLAT.dto.ParametroTaraturaDTO;
import it.calverDesktopLAT.dto.ProvaMisuraDTO;
import it.calverDesktopLAT.dto.SondaDTO;
import it.calverDesktopLAT.utl.Costanti;
import it.calverDesktopLAT.utl.Utility;
import net.miginfocom.swing.MigLayout;
import java.awt.Font;


public class FrameMisura  extends JFrame{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private JFrame frm;

	static JPanel mainPanel;
	static JPanel panel_conversioniMaster;
	static JPanel panel_misuraMaster;
	static JPanel panel_rip_misura_precedente;
	static JPanel panelCampione;
	static JScrollPane mainScroll;


	private JLabel lblFattoreMoltiplicativo;
	private JLabel lblValoreIncertezzaConvertito;
	private JLabel lblValoreCampioneConvertito;
	private JLabel lblUmConversione;
	private JLabel lblMinimoScala;
	private JLabel lblMassimoScala;
	private JLabel lblLettura;
	private JLabel lblValoreCorretto;
	private JLabel lblIncertezzaCorretta;
	private JLabel lblConversioneDirettaCompleta;
	private JLabel lblFattoreMoltiplicativo_1;
	private JLabel lblValoreCampione_1;
	private JLabel lblMinimoScalaconvertito;
	private JLabel lblMinimoScalaconvertito_1;
	private JLabel lblValoreCorretto_1;
	private JLabel lblIncertezza_2;
	private JLabel lblMisura;
	private JLabel lblRisoluzione;
	private JLabel lblfondoScala;
	private JLabel lblInterpolazione;
	private JLabel lblAccettabilita;
	private JLabel lblCifreSignificative;
	private JLabel lblCifreSignificative_1;
	private JLabel lblU;
	private JLabel lblEsito;
	private JLabel label_perc;
	private JLabel lblDevStd;
	private JLabel lblDivisione;
	private JLabel lblSelTolleranza;
	private JLabel lblDgt;
	private JLabel lblComposizioneCampione;
	private JLabel lblMisuraCalcolata;
	private JLabel lblIncertezzaCalcolata;
	private JLabel lblUm;
	private JLabel lblRisoluzione_1;
	private JLabel lblCampioniUtilizzati;
	private JLabel lblCampioneUtilizzato;
	private JLabel lblValoreMisura;

	private JTextField textField_tipo_misura;
	private JTextField textField_scadenza;
	private JTextField textField_risoluzione;
	private JTextField textField_um_camp;
	private JTextField textField_val_nom;
	private JTextField textField_val_cert;
	private JTextField textField_incertezza;
	private JTextField val_Campione_Dir;
	private JTextField val_Incer_Dir;
	private JTextField UM_Incer_dir;
	private JTextField UM_Camp_dir;
	private JTextField val_camp_conv_dir;
	private JTextField val_incer_conv_dir;
	static  JTextField valore_inserito;
	private JTextField textField_min_scl;
	private JTextField textField_max_scl;
	private JTextField val_camp_scl_corr;
	private JTextField val_incer_scl_corr;
	private JTextField textField_min_sca_comp;
	static  JTextField textField_lettura_sca_comp;
	private JTextField textField_max_scl_comp;
	private JTextField textField_val_corr_scl;
	private JTextField textField_val_icer_scl_comp;
	static  JTextField textField_misura;
	private JTextField textField_risoluzione_misura;
	private JTextField textField_fondoScala;
	private JTextField cifreSignificative_dirette;
	private JTextField textField_accettabilita;
	private JTextField textField_esito;
	private JTextField cifreSignificative_scala;
	private JTextField calcolo_u;
	private JTextField textField_percentuale;
	private JTextField textField_divisione;
	private JTextField textField_dgt;
	private static JTextField textField_txtComposta;
	private static JTextField textField_misuraCalcolata;
	private static  JTextField textField_incertezzaCalcolata;
	private static JTextField unitaMisuraComposta;
	private static JTextField risoluzioneComposta;
	private static JTextField textField_campioni_util;
	private JTextField textField_rif_cmp_precedente;
	private JTextField textField_rif_misura_prec;


	private JComboBox<?> puntoCorrente;
	private JComboBox<?> comboCampioni;
	private JComboBox<String> comboParTar;
	private JComboBox<String> comboUMConver;
	private JComboBox<String> comboFattMolt;
	private JComboBox<Object> comboBox_um_scala_comp;
	private JComboBox<Object> comboBoxFM;
	private JComboBox<?> comboBox_interpolazione;
	private JComboBox<?> comboBox_tolleranza;

	private JButton btnContinua;
	private JButton btnContinua_2;
	private JButton btnCheck,btnContinua_1;
	private JButton btnContinua_3;
	private JButton btnCheckScalaComp;
	private JButton btnControllaEsito;
	private JButton btn_confermaDati;
	private JButton btnComponiCampioni;
	private JButton btnDasm;
	private JButton btnFunzioniSpeciali;

	private static BigDecimal[] minMaxScala=null;
	private static BigDecimal incertezza;
	private static BigDecimal incertezzaCampione;
	private static BigDecimal valoreCampione;
	private static BigDecimal valoreMisura;
	private static BigDecimal risoluzione_misura;
	private static BigDecimal accettabilita;
	private static BigDecimal fondoScala;
	private static BigDecimal percentuale;
	private static BigDecimal valoreMedioCampione=null;
	private static BigDecimal valoreMaxCampione=null;
	private static BigDecimal valoreMaxStrumento=null;
	private static BigDecimal valoreScostamentoMax=null;
	private static BigDecimal valoreMedioStrumento=null;
	private static BigDecimal incertezzaRipetibilita=null;

	private JRadioButton rdbtnValoreInserito;
	private JRadioButton rdbtnValoreCorretto;
	private JRadioButton rdbtnValoreCorretto_1;
	private JRadioButton rdbtnValoreInserito_1;
	private JRadioButton rdbtnCampione;
	private JRadioButton rdbtnStrumento;

	static int taratura=0;
	static int interpolazione=0;
	static int id;
	int width,height;

	private boolean interpolato;
	private boolean tipoProva;
	private boolean firstTime;

	private JTable table;

	public  static String title;
	private static String[] listaCampioniPerStrumento=null;
	public  static  String campioneInterpolazione;

	private Splash spl=null;

	static MisuraDTO misura;

	ParametroTaraturaDTO parametro=null;

	private ConversioneDTO valRapConversione=null;

	private static ArrayList<SondaDTO> listaSonde;

	private JCheckBox chckbxNonApplicabile;

	TwoWaySerialComm com=null;



	public FrameMisura(JTable _table,String _title,boolean _tipoProva) 
	{
	


		try
		{	
			/*Inizzializzazione Variabili Costruttore*/
			misura=new MisuraDTO();
			table=_table;
			title=_title;
			tipoProva=_tipoProva;
			frm=this;
			com = new TwoWaySerialComm();
			listaSonde = new ArrayList<SondaDTO>();
			firstTime=false;

			Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
			
			height=(int)dim.getHeight()-100;
			width=(int)(dim.getWidth()/10)*8;
			
			/*Inizzializzazione Variabile JFrame frm*/
			getContentPane().setBackground(Color.WHITE);
			setTitle("Foglio raccolta dati");
			
			setResizable(false);
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			setSize(width,height+20);

			/*Inizzializzazione Pannello Principale e  JScrollPane*/
			
			
			mainPanel = new JPersonalPanel(this);
			mainPanel.setPreferredSize(new Dimension(width-50, height-30));
		//	mainPanel.setBackground(Color.CYAN);
			mainPanel.setLayout(new MigLayout("", "[grow]", "[pref!][141.00][grow][grow][grow]"));
			
			mainScroll= new JScrollPane();
			mainScroll.setPreferredSize(new Dimension(660, 770));
			mainScroll.setViewportView(mainPanel);
				

				panel_rip_misura_precedente = getPanelMisurePrecedenti();
				panelCampione = getPannellCampione();
				panel_conversioniMaster = getPannelloConversioneMaster();
				panel_misuraMaster= getPanelMisura();
		
				mainPanel.add(panel_rip_misura_precedente, "cell 0 0,growx");
				mainPanel.add(panelCampione,"cell 0 1,grow");
				mainPanel.add(panel_conversioniMaster, "cell 0 2,grow");
				mainPanel.add(panel_misuraMaster, "cell 0 3,grow");
				
				getContentPane().add(mainScroll);
			
			puntoCorrente.setSelectedIndex(0);
		}


		catch (Exception e1) {

			e1.printStackTrace();
		}


		

	}

	private JPanel getPanelMisurePrecedenti() {

		JPanel panel_rip_misura_precedente= new JPanel();
		panel_rip_misura_precedente.setBorder(new TitledBorder(new LineBorder(Costanti.COLOR_RED, 2, true), "Riferimenti Precedente Misura", TitledBorder.LEADING, TitledBorder.TOP, null, Costanti.COLOR_RED));
		panel_rip_misura_precedente.setBackground(Color.WHITE);
		panel_rip_misura_precedente.setLayout(new MigLayout("", "[][pref!,grow][30px][][grow]", "[]"));

		lblCampioneUtilizzato = new JLabel("Campione Utilizzato");
		lblCampioneUtilizzato.setFont(new Font("Arial", Font.BOLD, 14));
		panel_rip_misura_precedente.add(lblCampioneUtilizzato, "cell 0 0,alignx trailing");

		textField_rif_cmp_precedente = new JTextField();
		textField_rif_cmp_precedente.setFont(new Font("Arial", Font.PLAIN, 14));
		textField_rif_cmp_precedente.setEnabled(false);
		panel_rip_misura_precedente.add(textField_rif_cmp_precedente, "flowx,cell 1 0,width :350:");
		textField_rif_cmp_precedente.setColumns(10);
		
				lblValoreMisura = new JLabel("Valore Misura");
				lblValoreMisura.setFont(new Font("Arial", Font.BOLD, 14));
				panel_rip_misura_precedente.add(lblValoreMisura, "cell 3 0,alignx right");
		
				textField_rif_misura_prec = new JTextField();
				textField_rif_misura_prec.setFont(new Font("Arial", Font.PLAIN, 14));
				textField_rif_misura_prec.setEnabled(false);
				panel_rip_misura_precedente.add(textField_rif_misura_prec, "cell 4 0,growx,width :200:250");
				textField_rif_misura_prec.setColumns(10);

		return panel_rip_misura_precedente;
	}


	private JPanel getPannellCampione() throws Exception {

		final JPanel panelCampione = new JPanel();
		panelCampione.setBorder(new TitledBorder(new LineBorder(new Color(215, 23, 29), 2, true), "Lista Punti Misura", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(215, 23, 29)));
		panelCampione.setBackground(Color.WHITE);
		//	panel.setBounds(10, 11, 464, 640);

		panelCampione.setLayout(new MigLayout("", "[150:300:600]15[40px]15[pref!][::30px][pref!][::30px][pref!][grow]", "[][][][][][][]"));


		JLabel lblPuntoCorrente = new JLabel("Punto Corrente");
		lblPuntoCorrente.setFont(new Font("Arial", Font.BOLD, 14));
		panelCampione.add(lblPuntoCorrente, "flowx,cell 0 0,growx");
				
						JLabel lab_tipoMisura = new JLabel("Tipo Grandezza");
						lab_tipoMisura.setFont(new Font("Arial", Font.BOLD, 14));
						panelCampione.add(lab_tipoMisura, "cell 2 0");
						
								JLabel lbl_scadenza = new JLabel("Scadenza");
								lbl_scadenza.setFont(new Font("Arial", Font.BOLD, 14));
								panelCampione.add(lbl_scadenza, "cell 4 0");
						
								JLabel lblUMCamp = new JLabel("UM Camp. / UM Norm.");
								lblUMCamp.setFont(new Font("Arial", Font.BOLD, 14));
								panelCampione.add(lblUMCamp, "cell 6 0");
						
								btnDasm = new JButton("DASM");
								btnDasm.setIcon(new ImageIcon(PannelloTOP.class.getResource("/image/dasm.png")));
								btnDasm.setFont(new Font("Arial", Font.BOLD, 14));
								panelCampione.add(btnDasm, "cell 7 0 1 2,alignx center,hmin 40,aligny center");
								
										btnDasm.addActionListener(new ActionListener() {
											public void actionPerformed(ActionEvent arg0) {
								
												spl =	 new Splash(panelCampione);
								
												spl.execute();
								
												Thread thread = new Thread(new ThreadDASM());
												thread.start();
								
											}
										});
				
						textField_tipo_misura = new JTextField();
						textField_tipo_misura.setFont(new Font("Arial", Font.PLAIN, 14));
						textField_tipo_misura.setEditable(false);
						panelCampione.add(textField_tipo_misura, "cell 2 1,wmin 200");
						textField_tipo_misura.setColumns(10);
						
								textField_scadenza = new JTextField();
								textField_scadenza.setFont(new Font("Arial", Font.PLAIN, 14));
								textField_scadenza.setEditable(false);
								textField_scadenza.setColumns(10);
								panelCampione.add(textField_scadenza, "cell 4 1");
						
								textField_um_camp = new JTextField();
								textField_um_camp.setFont(new Font("Arial", Font.PLAIN, 14));
								textField_um_camp.setEditable(false);
								textField_um_camp.setColumns(10);
								panelCampione.add(textField_um_camp, "cell 6 1");
				
						JLabel lblCodiceCampione = new JLabel("Codice Campione");
						lblCodiceCampione.setFont(new Font("Arial", Font.BOLD, 14));
						panelCampione.add(lblCodiceCampione, "cell 0 2");
						
								JLabel lblVal_nom = new JLabel("Valore Nominale");
								lblVal_nom.setFont(new Font("Arial", Font.BOLD, 14));
								panelCampione.add(lblVal_nom, "cell 2 2");
				
						JLabel lbl_risoluzione = new JLabel("Risoluzione");
						lbl_risoluzione.setFont(new Font("Arial", Font.BOLD, 14));
						panelCampione.add(lbl_risoluzione, "cell 4 2");
		
				listaCampioniPerStrumento=GestioneCampioneBO.getListaCampioniPerStrumento(SessionBO.idStrumento);
						
				comboCampioni = new JComboBox(listaCampioniPerStrumento);
				comboCampioni.setFont(new Font("Arial", Font.PLAIN, 14));
				panelCampione.add(comboCampioni, "cell 0 3,growx");
				
						comboCampioni.addActionListener(new ActionListener() {
				
							@Override
							public void actionPerformed(ActionEvent arg0) {
								try {
				
									if(comboCampioni.getSelectedIndex()!=0)
									{
										String codiceCampione=comboCampioni.getSelectedItem().toString();
				
										ArrayList<String> listaTipiGrandezza = GestioneStrumentoBO.getListaTipoGrandezza(SessionBO.idStrumento);
				
										interpolato=SQLiteDAO.isInterpolabile(codiceCampione);
				
										String[] listaParametriTaratura=GestioneCampioneBO.getParametriTaratura(codiceCampione,interpolato,listaTipiGrandezza);
				
										textField_tipo_misura.setText("");
										textField_scadenza.setText("");
										textField_um_camp.setText("");
										textField_val_nom.setText("");
										textField_val_cert.setText("");
										textField_incertezza.setText("");
										textField_risoluzione.setText("");
										textField_dgt.setText("");
										btnContinua.setVisible(false);
				
										comboParTar.removeAllItems();
				
										for(String str : listaParametriTaratura) {
				
				
											comboParTar.addItem(str);
										}
									}
								} catch (Exception e) {
									e.printStackTrace();
								}
				
							}
						});
		
				textField_val_nom = new JTextField();
				textField_val_nom.setFont(new Font("Arial", Font.PLAIN, 14));
				textField_val_nom.setEditable(false);
				textField_val_nom.setColumns(10);
				panelCampione.add(textField_val_nom, "flowy,cell 2 3");
		
				textField_risoluzione = new JTextField();
				textField_risoluzione.setFont(new Font("Arial", Font.PLAIN, 14));
				textField_risoluzione.setEditable(false);
				textField_risoluzione.setColumns(10);
				panelCampione.add(textField_risoluzione, "cell 4 3");
				
						btnComponiCampioni = new JButton("Componi Campioni");
						btnComponiCampioni.setIcon(new ImageIcon(this.getClass().getResource("/image/composition.png")));
						             
						btnComponiCampioni.setFont(new Font("Arial", Font.BOLD, 14));
						
								panelCampione.add(btnComponiCampioni, "cell 7 2 1 3,alignx center,hmin 40");
								
								
										btnComponiCampioni.addActionListener(new ActionListener() {
											public void actionPerformed(ActionEvent arg0) {
								
												SwingUtilities.invokeLater(new Runnable(){
													public void run() 
													{
														try
														{
															JFrame f=new FrameComposizioneCampioni(listaCampioniPerStrumento);
								
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
										});
		
				JLabel lbl_val_cert = new JLabel("Valore Certificato");
				lbl_val_cert.setFont(new Font("Arial", Font.BOLD, 14));
				panelCampione.add(lbl_val_cert, "cell 2 4");
		
				JLabel lblIncertezza = new JLabel("Incertezza");
				lblIncertezza.setFont(new Font("Arial", Font.BOLD, 14));
				panelCampione.add(lblIncertezza, "cell 4 4");
		
				JLabel lblParametriTaratura = new JLabel("Parametri Taratura");
				lblParametriTaratura.setFont(new Font("Arial", Font.BOLD, 14));
				panelCampione.add(lblParametriTaratura, "cell 0 5");
		
				textField_val_cert = new JTextField();
				textField_val_cert.setFont(new Font("Arial", Font.PLAIN, 14));
				textField_val_cert.setEditable(false);
				textField_val_cert.setColumns(10);
				panelCampione.add(textField_val_cert, "cell 2 5");
				
						textField_incertezza = new JTextField();
						textField_incertezza.setFont(new Font("Arial", Font.PLAIN, 14));
						textField_incertezza.setEditable(false);
						textField_incertezza.setColumns(10);
						panelCampione.add(textField_incertezza, "cell 4 5");
		
		
				comboParTar = new JComboBox();
				comboParTar.setFont(new Font("Arial", Font.PLAIN, 14));
				panelCampione.add(comboParTar, "cell 0 6,growx");
				
						comboParTar.addActionListener(new ActionListener() {
				
							@Override
							public void actionPerformed(ActionEvent arg0) {
				
								try {
				
									if(comboParTar.getSelectedItem()!=null){
				
										ArrayList<ParametroTaraturaDTO> listaParametri=GestioneCampioneBO.getParametriTaraturaSelezionato(comboParTar.getSelectedItem().toString(),comboCampioni.getSelectedItem().toString());
				
				
										if(listaParametri.size()>1)
										{
											parametro=listaParametri.get(0);
											taratura=1;
											SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
											textField_tipo_misura.setText(parametro.getTipoGrandezza());
											textField_scadenza.setText(sdf.format(parametro.getDataScadenza()));
											textField_um_camp.setText(parametro.getUm()+ " / "+parametro.getUm_fond());
											textField_val_nom.setText("SCALA VALORI");
											textField_val_cert.setText("SCALA VALORI");
											textField_incertezza.setText("SCALA VALORI");
											textField_risoluzione.setText(""+parametro.getRisoluzione());
											btnContinua.setVisible(true);
				
											//								SessionBO.cifreSignificative=;
				
										}
				
										if(listaParametri.size()==1)
										{
											parametro=listaParametri.get(0);
											taratura=0;
											SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
											textField_tipo_misura.setText(parametro.getTipoGrandezza());
											textField_scadenza.setText(sdf.format(parametro.getDataScadenza()));
											textField_um_camp.setText(parametro.getUm()+ " / "+parametro.getUm_fond());
											textField_val_nom.setText(parametro.getValore_nominale().stripTrailingZeros().toPlainString());
											textField_val_cert.setText(parametro.getValoreTaratura().stripTrailingZeros().toPlainString());
											incertezza=GestioneCampioneBO.getIncertezza(parametro.getIncertezzaAssoluta(),parametro.getIncertezzaRelativa(),parametro.getValore_nominale());
											textField_incertezza.setText(incertezza.stripTrailingZeros().toPlainString());
											textField_risoluzione.setText(""+parametro.getRisoluzione());
											btnContinua.setVisible(true);
										}
				
									}
								} catch (Exception e) {
									e.printStackTrace();
								}
							}
						});

		
		
				puntoCorrente = new JComboBox(getListaPunti(table));
				puntoCorrente.setFont(new Font("Arial", Font.PLAIN, 14));
				panelCampione.add(puntoCorrente, "cell 0 0,growx,width 100:150:200");
				
						btnContinua = new JButton("Continua");
						btnContinua.setIcon(new ImageIcon(FrameMisura.class.getResource("/image/continue.png")));
						btnContinua.setFont(new Font("Arial", Font.BOLD, 14));
						
								btnContinua.setVisible(false);
								panelCampione.add(btnContinua, "cell 7 5 1 2,alignx center,hmin 40");

		btnContinua.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				int scelta=	JOptionPane.showConfirmDialog(null,"Vuoi convertire il campione ?","Conversione",JOptionPane.YES_NO_OPTION,JOptionPane.INFORMATION_MESSAGE,new ImageIcon(PannelloTOP.class.getResource("/image/question.png")));

				panel_misuraMaster.setVisible(false);

				if(scelta==1 && interpolato==false)
				{
					conversioneDirettaNoConversione();
				}
				if(scelta==0 && interpolato==false)
				{
					conversioneDirettaSiConversione();
				}
				if(scelta==1 && interpolato ==true)
				{
					conversioneScalaNoConversione();
				}
				if(scelta==0 && interpolato ==true)
				{
					conversioneScalaSiConversione();
				}

			}


		});

		return panelCampione;
	}

	private Component getPannelloComposizioneCampioni() 
	{
		JPanel panel_composizione = new JPanel();
		panel_composizione.setBorder(new LineBorder(Color.RED, 2, true));
		panel_composizione.setBackground(Color.WHITE);

		panel_composizione.setLayout(new MigLayout("", "[][grow][100px]", "[grow][grow][grow][grow][grow][grow]"));

		lblComposizioneCampione= new JLabel();
		lblComposizioneCampione.setFont(new Font("Arial", Font.BOLD, 14));
		lblComposizioneCampione.setText("Composizione Campione :");
		panel_composizione.add(lblComposizioneCampione, "cell 0 0,alignx trailing");

		textField_txtComposta = new JTextField();
		textField_txtComposta.setFont(new Font("Arial", Font.PLAIN, 14));
		textField_txtComposta.setEditable(false);
		panel_composizione.add(textField_txtComposta, "cell 1 0,growx");
		textField_txtComposta.setColumns(10);

		lblCampioniUtilizzati = new JLabel("Campioni Utilizzati :");
		lblCampioniUtilizzati.setFont(new Font("Arial", Font.BOLD, 14));
		panel_composizione.add(lblCampioniUtilizzati, "cell 0 1,alignx trailing");

		textField_campioni_util = new JTextField();
		textField_campioni_util.setFont(new Font("Arial", Font.PLAIN, 14));
		textField_campioni_util.setEditable(false);
		panel_composizione.add(textField_campioni_util, "cell 1 1,width 100:200:");
		textField_campioni_util.setColumns(10);

		lblMisuraCalcolata = new JLabel("Misura Calcolata :");
		lblMisuraCalcolata.setFont(new Font("Arial", Font.BOLD, 14));
		panel_composizione.add(lblMisuraCalcolata, "cell 0 2,alignx trailing");

		textField_misuraCalcolata = new JTextField();
		textField_misuraCalcolata.setFont(new Font("Arial", Font.PLAIN, 14));
		textField_misuraCalcolata.setEditable(false);
		panel_composizione.add(textField_misuraCalcolata, "flowx,cell 1 2,width 100:200:");
		textField_misuraCalcolata.setColumns(10);
				
						lblIncertezzaCalcolata = new JLabel("Incertezza Calcolata :");
						lblIncertezzaCalcolata.setFont(new Font("Arial", Font.BOLD, 14));
						panel_composizione.add(lblIncertezzaCalcolata, "cell 0 3,alignx trailing");
				
						textField_incertezzaCalcolata = new JTextField();
						textField_incertezzaCalcolata.setFont(new Font("Arial", Font.PLAIN, 14));
						textField_incertezzaCalcolata.setEditable(false);
						panel_composizione.add(textField_incertezzaCalcolata, "cell 1 3,width 100:200:");
						textField_incertezzaCalcolata.setColumns(10);
		
				lblUm = new JLabel("UM :");
				lblUm.setFont(new Font("Arial", Font.BOLD, 14));
				panel_composizione.add(lblUm, "cell 0 4,alignx trailing");
				
						unitaMisuraComposta = new JTextField();
						unitaMisuraComposta.setFont(new Font("Arial", Font.PLAIN, 14));
						unitaMisuraComposta.setEditable(false);
						panel_composizione.add(unitaMisuraComposta, "cell 1 4,width 100:200:");
						unitaMisuraComposta.setColumns(10);
		
				lblRisoluzione_1 = new JLabel("Risoluzione :");
				lblRisoluzione_1.setFont(new Font("Arial", Font.BOLD, 14));
				panel_composizione.add(lblRisoluzione_1, "cell 0 5,alignx trailing");
				
						risoluzioneComposta = new JTextField();
						risoluzioneComposta.setFont(new Font("Arial", Font.PLAIN, 14));
						risoluzioneComposta.setEditable(false);
						panel_composizione.add(risoluzioneComposta, "cell 1 5,width 100:200:");
						risoluzioneComposta.setColumns(10);

		return panel_composizione;
	}

	private JPanel getPannelloConversioneMaster() {
		JPanel panel_conversioniMaster = new JPanel();
		panel_conversioniMaster.setBackground(Color.PINK);
		panel_conversioniMaster.setLayout(new CardLayout(0, 0));
		panel_conversioniMaster.add(new JPanel(),"blank");
		panel_conversioniMaster.add(getPannelloConversioneDiretta(), "card_conversione_diretta");
		panel_conversioniMaster.add(getPannelloConversioneScala(), "card_conversione_scala");
		panel_conversioniMaster.add(getPannelloConversioneScalaCompleta(), "card_conversione_scala_completa");
		panel_conversioniMaster.add(getPannelloComposizioneCampioni(), "card_composizione");

		CardLayout cl = (CardLayout)(panel_conversioniMaster.getLayout());
		cl.show(panel_conversioniMaster ,"blank");

		return panel_conversioniMaster;
	}


	public  Component getPannelloConversioneDiretta() {
		JPanel panel_conversione_diretta = new JPanel();

		panel_conversione_diretta.setBorder(new LineBorder(Color.RED, 2, true));
		panel_conversione_diretta.setBackground(Color.WHITE);
		panel_conversione_diretta.setLayout(new MigLayout("", "[:150px:150px,grow][][:200px:200px][][:250px:250px,grow][][:200px:200px,grow]", "[][][][26.00][30.00][]"));

		JLabel lblValoreCampione = new JLabel("Valore Campione");
		lblValoreCampione.setFont(new Font("Arial", Font.BOLD, 14));
		panel_conversione_diretta.add(lblValoreCampione, "cell 0 0");

		JLabel lblUmCampione = new JLabel("UM Campione");
		lblUmCampione.setFont(new Font("Arial", Font.BOLD, 14));
		panel_conversione_diretta.add(lblUmCampione, "cell 2 0");

		lblUmConversione = new JLabel("UM Conversione");
		lblUmConversione.setFont(new Font("Arial", Font.BOLD, 14));
		panel_conversione_diretta.add(lblUmConversione, "cell 4 0");

		lblValoreCampioneConvertito = new JLabel("Valore Campione Convertito");
		lblValoreCampioneConvertito.setFont(new Font("Arial", Font.BOLD, 14));
		panel_conversione_diretta.add(lblValoreCampioneConvertito, "cell 6 0");

		val_Campione_Dir = new JTextField();
		val_Campione_Dir.setFont(new Font("Arial", Font.PLAIN, 14));
		val_Campione_Dir.setEditable(false);
		panel_conversione_diretta.add(val_Campione_Dir, "cell 0 1,growx");
		val_Campione_Dir.setColumns(10);

		UM_Camp_dir = new JTextField();
		UM_Camp_dir.setFont(new Font("Arial", Font.PLAIN, 14));
		UM_Camp_dir.setEditable(false);
		panel_conversione_diretta.add(UM_Camp_dir, "cell 2 1,growx");
		UM_Camp_dir.setColumns(10);

		comboUMConver = new JComboBox();
		comboUMConver.setFont(new Font("Arial", Font.PLAIN, 14));
		panel_conversione_diretta.add(comboUMConver, "cell 4 1,growx");

		val_camp_conv_dir = new JTextField();
		val_camp_conv_dir.setFont(new Font("Arial", Font.PLAIN, 14));
		val_camp_conv_dir.setEditable(false);
		panel_conversione_diretta.add(val_camp_conv_dir, "cell 6 1,growx,aligny baseline");
		val_camp_conv_dir.setColumns(10);

		JLabel lblIncertezza_1 = new JLabel("Incertezza");
		lblIncertezza_1.setFont(new Font("Arial", Font.BOLD, 14));
		panel_conversione_diretta.add(lblIncertezza_1, "cell 0 2");

		JLabel lblUmIncertazza = new JLabel("UM Incertezza");
		lblUmIncertazza.setFont(new Font("Arial", Font.BOLD, 14));
		panel_conversione_diretta.add(lblUmIncertazza, "cell 2 2");

		lblFattoreMoltiplicativo = new JLabel("Fattore Moltiplicativo");
		lblFattoreMoltiplicativo.setFont(new Font("Arial", Font.BOLD, 14));
		panel_conversione_diretta.add(lblFattoreMoltiplicativo, "cell 4 2");

		lblValoreIncertezzaConvertito = new JLabel("Valore Incertezza Convertito");
		lblValoreIncertezzaConvertito.setFont(new Font("Arial", Font.BOLD, 14));
		panel_conversione_diretta.add(lblValoreIncertezzaConvertito, "cell 6 2");

		val_Incer_Dir = new JTextField();
		val_Incer_Dir.setFont(new Font("Arial", Font.PLAIN, 14));
		val_Incer_Dir.setEditable(false);
		panel_conversione_diretta.add(val_Incer_Dir, "cell 0 3,growx");
		val_Incer_Dir.setColumns(10);

		UM_Incer_dir = new JTextField();
		UM_Incer_dir.setFont(new Font("Arial", Font.PLAIN, 14));
		UM_Incer_dir.setEditable(false);
		panel_conversione_diretta.add(UM_Incer_dir, "cell 2 3,growx");
		UM_Incer_dir.setColumns(10);

		comboFattMolt = new JComboBox();
		comboFattMolt.setFont(new Font("Arial", Font.PLAIN, 14));
		panel_conversione_diretta.add(comboFattMolt, "cell 4 3,growx");

		val_incer_conv_dir = new JTextField();
		val_incer_conv_dir.setFont(new Font("Arial", Font.PLAIN, 14));
		val_incer_conv_dir.setEditable(false);
		panel_conversione_diretta.add(val_incer_conv_dir, "cell 6 3,growx");
		val_incer_conv_dir.setColumns(10);

		lblCifreSignificative = new JLabel("Cifre decimali");
		lblCifreSignificative.setFont(new Font("Arial", Font.BOLD, 14));
		panel_conversione_diretta.add(lblCifreSignificative, "flowx,cell 0 4,aligny bottom");

		cifreSignificative_dirette = new JTextField();
		panel_conversione_diretta.add(cifreSignificative_dirette, "cell 0 4,aligny bottom");
		cifreSignificative_dirette.setColumns(10);
		
				btnContinua_1 = new JButton("Continua");
				btnContinua_1.setIcon(new ImageIcon(FrameMisura.class.getResource("/image/continue.png")));
				btnContinua_1.setFont(new Font("Arial", Font.BOLD, 14));
				btnContinua_1.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {

						if(cifreSignificative_dirette.isVisible()==true)
						{

							if(val_camp_conv_dir.getText().length()>0)
							{
								if(cifreSignificative_dirette.getText().length()>0)
								{


									if(val_camp_conv_dir.getText().length()>0)
									{
										valoreCampione=new BigDecimal(val_camp_conv_dir.getText());
										incertezzaCampione=new BigDecimal(val_incer_conv_dir.getText());

										misura.setIncertezza(incertezzaCampione);
										misura.setValoreCampione(valoreCampione);
										misura.setUm(Utility.getLabelUMConvertita(comboUMConver.getSelectedItem().toString(),comboFattMolt.getSelectedItem().toString()));
										misura.setRisoluzione_campione(Utility.getRisoluzione(cifreSignificative_dirette.getText()));
										misura.setUm_calc(comboUMConver.getSelectedItem().toString());
										misura.setFm(comboFattMolt.getSelectedItem().toString());
										misura.setSelConversione(2);
										misura.setLetturaCampione(valoreCampione);
									}
									panel_misuraMaster.setVisible(true);


								}
								else
								{
									JOptionPane.showMessageDialog(null, "Inserire Cifre Significative","Errore Input",JOptionPane.INFORMATION_MESSAGE,new ImageIcon(PannelloTOP.class.getResource("/image/error.png")));
								}
							}else
							{
								JOptionPane.showMessageDialog(null, "Non hai applicato conversioni","Errore Input",JOptionPane.INFORMATION_MESSAGE,new ImageIcon(PannelloTOP.class.getResource("/image/error.png")));
							}
						}else
						{
							panel_misuraMaster.setVisible(true);
						}
					}
				});
				panel_conversione_diretta.add(btnContinua_1, "cell 6 5,alignx center,aligny bottom");

		comboUMConver.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {

				if(comboUMConver.getSelectedIndex()>0)
				{		
					String umCon=comboUMConver.getSelectedItem().toString();

					valRapConversione=GestioneConversioneBO.getValoreConvertito(parametro,umCon,textField_tipo_misura.getText(),null,null);

					val_camp_conv_dir.setText(valRapConversione.getValoreConvertito().stripTrailingZeros().toPlainString());

					val_incer_conv_dir.setText(valRapConversione.getIncertezzaConvertita().stripTrailingZeros().toPlainString());
					if(valRapConversione.isValidita()==false)
					{
						comboFattMolt.setEditable(false);
					}
					comboFattMolt.setSelectedIndex(11);
				}

			}
		});

		comboFattMolt.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {

				if(comboFattMolt.getSelectedIndex()>0)
				{
					String classe=comboFattMolt.getSelectedItem().toString();

					if(valRapConversione.getPotenza()==1)
					{
						valRapConversione.setPotenza(10);
					}

					double grado=GestioneConversioneBO.getPotenzaPerClasse(classe,valRapConversione.getPotenza());

					BigDecimal newValoreConvertito=valRapConversione.getValoreConvertito().divide(new BigDecimal(grado),RoundingMode.HALF_UP);

					BigDecimal newIncertezzaConvertito=valRapConversione.getIncertezzaConvertita().divide(new BigDecimal(grado),RoundingMode.HALF_UP);

					val_camp_conv_dir.setText(newValoreConvertito.setScale(10,RoundingMode.HALF_UP).stripTrailingZeros().toPlainString());

					val_incer_conv_dir.setText(newIncertezzaConvertito.setScale(10,RoundingMode.HALF_UP).stripTrailingZeros().toPlainString());

				}

			}
		});

		return panel_conversione_diretta;
	}

	private Component getPannelloConversioneScala() {

		JPanel panel_conversione_scala = new JPanel();
		panel_conversione_scala.setBorder(new LineBorder(Color.RED, 2, true));
		panel_conversione_scala.setBackground(Color.WHITE);
		panel_conversione_scala.setLayout(new MigLayout("", "[:70px:100px][:200px:250px][][25px][][:200px:250][25px][grow]", "[24.00][][][][][][26.00][57.00]"));
		panel_conversione_scala.setVisible(false);
		lblMinimoScala = new JLabel("Minimo Scala");
		lblMinimoScala.setFont(new Font("Arial", Font.BOLD, 14));
		panel_conversione_scala.add(lblMinimoScala, "cell 0 1,alignx trailing");

		textField_min_scl = new JTextField();
		textField_min_scl.setFont(new Font("Arial", Font.PLAIN, 14));
		textField_min_scl.setEditable(false);
		panel_conversione_scala.add(textField_min_scl, "cell 1 1,growx");
		textField_min_scl.setColumns(10);

		lblValoreCorretto = new JLabel("Valore Corretto");
		lblValoreCorretto.setFont(new Font("Arial", Font.BOLD, 14));
		panel_conversione_scala.add(lblValoreCorretto, "cell 5 1,aligny bottom");

		ButtonGroup group1 = new ButtonGroup();
		rdbtnValoreInserito = new JRadioButton("Valore Lettura");
		rdbtnValoreInserito.setFont(new Font("Arial", Font.BOLD, 12));
		rdbtnValoreInserito.setBackground(Color.WHITE);
		panel_conversione_scala.add(rdbtnValoreInserito, "cell 7 1,alignx left");

		//	val_camp_scl_corr = new JTextField();
		val_camp_scl_corr = new JTextField();
		val_camp_scl_corr.setFont(new Font("Arial", Font.PLAIN, 14));
		val_camp_scl_corr.setEditable(false);
		panel_conversione_scala.add(val_camp_scl_corr, "cell 5 2,growx");
		val_camp_scl_corr.setColumns(10);

		rdbtnValoreCorretto = new JRadioButton("Valore Corretto");
		rdbtnValoreCorretto.setFont(new Font("Arial", Font.BOLD, 12));
		rdbtnValoreCorretto.setSelected(true);
		rdbtnValoreCorretto.setBackground(Color.WHITE);
		panel_conversione_scala.add(rdbtnValoreCorretto, "cell 7 2,alignx left");

		group1.add(rdbtnValoreCorretto);
		group1.add(rdbtnValoreInserito);
		lblLettura = new JLabel("Lettura:");
		lblLettura.setFont(new Font("Arial", Font.BOLD, 14));
		panel_conversione_scala.add(lblLettura, "cell 0 3,alignx trailing");

		valore_inserito = new JTextField();
		valore_inserito.setFont(new Font("Arial", Font.PLAIN, 14));
		panel_conversione_scala.add(valore_inserito, "cell 1 3,growx");
		valore_inserito.setColumns(10);

		aggiungiTastierino(valore_inserito,frm);



		btnCheck = new JButton("Check");
		btnCheck.setIcon(new ImageIcon(FrameMisura.class.getResource("/image/check.png")));
		btnCheck.setFont(new Font("Arial", Font.BOLD, 14));


		panel_conversione_scala.add(btnCheck, "cell 3 3");

		lblIncertezzaCorretta = new JLabel("Incertezza Corretta");
		lblIncertezzaCorretta.setFont(new Font("Arial", Font.BOLD, 14));
		panel_conversione_scala.add(lblIncertezzaCorretta, "cell 5 3,aligny bottom");

		val_incer_scl_corr = new JTextField();
		val_incer_scl_corr.setFont(new Font("Arial", Font.PLAIN, 14));
		val_incer_scl_corr.setEditable(false);
		panel_conversione_scala.add(val_incer_scl_corr, "cell 5 4,growx");
		val_incer_scl_corr.setColumns(10);

		lblMassimoScala = new JLabel("Massimo Scala");
		lblMassimoScala.setFont(new Font("Arial", Font.BOLD, 14));
		panel_conversione_scala.add(lblMassimoScala, "cell 0 5,alignx trailing");

		textField_max_scl = new JTextField();
		textField_max_scl.setFont(new Font("Arial", Font.PLAIN, 14));
		textField_max_scl.setEditable(false);
		panel_conversione_scala.add(textField_max_scl, "cell 1 5,growx");
		textField_max_scl.setColumns(10);

		btnContinua_2 = new JButton("Continua");
		btnContinua_2.setIcon(new ImageIcon(FrameMisura.class.getResource("/image/continue.png")));
		btnContinua_2.setFont(new Font("Arial", Font.BOLD, 14));
		panel_conversione_scala.add(btnContinua_2, "cell 7 5");

		misura.setUm_calc("");
		misura.setFm("");

		btnCheck.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) 
			{

				try{
					BigDecimal valore_ins=new BigDecimal(valore_inserito.getText());

					if((valore_ins.compareTo(minMaxScala[0])==1 || valore_ins.compareTo(minMaxScala[0])==0) && 
							(valore_ins.compareTo(minMaxScala[1])==-1 ||valore_ins.compareTo(minMaxScala[1])==0))
					{
						CampioneCorrettoDTO valoreCorretto = GestioneConversioneBO.getValoreCampioneCorretto(comboParTar.getSelectedItem().toString(),valore_ins,false,null,null,0,comboCampioni.getSelectedItem().toString());
						val_camp_scl_corr.setText(valoreCorretto.getValoreCampioneCorretto().stripTrailingZeros().toPlainString());
						val_incer_scl_corr.setText(valoreCorretto.getIncertezzaCorretta().stripTrailingZeros().toPlainString());

						misura.setLetturaCampione(valore_ins);
					}
					else
					{
						val_camp_scl_corr.setText("");
						val_incer_scl_corr.setText("");
						JOptionPane.showMessageDialog(null, "Valore lettura Fuori scala","Out of Range",JOptionPane.INFORMATION_MESSAGE,new ImageIcon(PannelloTOP.class.getResource("/image/error.png")));
					}

				}catch(NumberFormatException nfe)
				{
					val_camp_scl_corr.setText("");
					val_incer_scl_corr.setText("");
					JOptionPane.showMessageDialog(null, "Attenzione numero inserito formalmente scorretto! \n Formato corretto: xxxx.xx","Errore Input",JOptionPane.INFORMATION_MESSAGE,new ImageIcon(PannelloTOP.class.getResource("/image/error.png")));
				}
				catch(Exception e)
				{
					e.printStackTrace();
				}

			}
		});


		btnContinua_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				if(val_camp_scl_corr.getText().length()>0)
				{
					if(rdbtnValoreCorretto.isSelected())
					{
						valoreCampione=new BigDecimal(val_camp_scl_corr.getText());
						incertezzaCampione=new BigDecimal(val_incer_scl_corr.getText());
					}else
					{
						valoreCampione=new BigDecimal(valore_inserito.getText());
						incertezzaCampione=new BigDecimal(val_incer_scl_corr.getText());
					}

					misura.setIncertezza(incertezzaCampione);
					misura.setValoreCampione(valoreCampione);
					misura.setUm(parametro.getUm());
					misura.setRisoluzione_campione(parametro.getRisoluzione());
					misura.setSelConversione(3);

					panel_misuraMaster.setVisible(true);
				}
				else
				{
					JOptionPane.showMessageDialog(null, "Nessuna lettura inserita","Errore Input",JOptionPane.INFORMATION_MESSAGE,new ImageIcon(PannelloTOP.class.getResource("/image/error.png")));
				}
			}
		});

		return panel_conversione_scala;
	}

	private Component getPannelloConversioneScalaCompleta() {
		JPanel panel_conversione_scala_completa = new JPanel();
		panel_conversione_scala_completa.setBorder(new LineBorder(Color.RED, 2, true));
		panel_conversione_scala_completa.setBackground(Color.WHITE);
		panel_conversione_scala_completa.setLayout(new MigLayout("", "[:200:250][25px][:300px:300px][][][25px][:200:250][][grow]", "[14px][][][][][]"));

		lblConversioneDirettaCompleta = new JLabel("UM Conversione");
		lblConversioneDirettaCompleta.setFont(new Font("Arial", Font.BOLD, 14));
		panel_conversione_scala_completa.add(lblConversioneDirettaCompleta, "flowx,cell 0 0,alignx left,aligny top");

		lblMinimoScalaconvertito_1 = new JLabel("Min Scala (c)");
		lblMinimoScalaconvertito_1.setFont(new Font("Arial", Font.BOLD, 14));
		panel_conversione_scala_completa.add(lblMinimoScalaconvertito_1, "flowx,cell 2 0,alignx left");

		lblValoreCorretto_1 = new JLabel("Valore Corretto");
		lblValoreCorretto_1.setFont(new Font("Arial", Font.BOLD, 14));
		panel_conversione_scala_completa.add(lblValoreCorretto_1, "cell 6 0,alignx center");

		comboBox_um_scala_comp = new JComboBox<>();
		comboBox_um_scala_comp.setFont(new Font("Arial", Font.PLAIN, 14));
		panel_conversione_scala_completa.add(comboBox_um_scala_comp, "flowx,cell 0 1,growx");

		textField_val_corr_scl = new JTextField();
		textField_val_corr_scl.setFont(new Font("Arial", Font.PLAIN, 14));
		textField_val_corr_scl.setEditable(false);
		panel_conversione_scala_completa.add(textField_val_corr_scl, "cell 6 1,growx");
		textField_val_corr_scl.setColumns(10);
		ButtonGroup group2 =new ButtonGroup();

		rdbtnValoreCorretto_1 = new JRadioButton("Valore Corretto");
		rdbtnValoreCorretto_1.setFont(new Font("Tahoma", Font.BOLD, 12));
		rdbtnValoreCorretto_1.setSelected(true);
		rdbtnValoreCorretto_1.setBackground(Color.WHITE);
		panel_conversione_scala_completa.add(rdbtnValoreCorretto_1, "cell 8 1");

		lblFattoreMoltiplicativo_1 = new JLabel("Fattore Moltiplicativo");
		lblFattoreMoltiplicativo_1.setFont(new Font("Arial", Font.BOLD, 14));
		panel_conversione_scala_completa.add(lblFattoreMoltiplicativo_1, "flowx,cell 0 2");

		lblValoreCampione_1 = new JLabel("Lettura");
		lblValoreCampione_1.setFont(new Font("Arial", Font.BOLD, 14));
		panel_conversione_scala_completa.add(lblValoreCampione_1, "flowx,cell 2 2");
		
				btnCheckScalaComp = new JButton("Check");
				btnCheckScalaComp.setIcon(new ImageIcon(FrameMisura.class.getResource("/image/check.png")));
				btnCheckScalaComp.setFont(new Font("Arial", Font.BOLD, 14));
				panel_conversione_scala_completa.add(btnCheckScalaComp, "cell 4 2");
				
				
						btnCheckScalaComp.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent arg0) 
							{
				
								try{
				
									BigDecimal valore_ins=new BigDecimal(textField_lettura_sca_comp.getText());
									BigDecimal minScalaConv = new BigDecimal(textField_min_sca_comp.getText());
									BigDecimal maxnScalaConv = new BigDecimal(textField_max_scl_comp.getText());
									if((valore_ins.compareTo(minScalaConv)==1 || valore_ins.compareTo(minScalaConv)==0) && 
											(valore_ins.compareTo(maxnScalaConv)==-1 ||valore_ins.compareTo(maxnScalaConv)==0))
									{
				
										String classe="";
										if(comboBoxFM.getSelectedIndex()==0)
										{
											classe=Costanti.CLASSE_PER_POTENZA_DEFAULT;
											comboBoxFM.setSelectedIndex(7);
										}
										else{
											classe=comboBoxFM.getSelectedItem().toString();
										}
										double potenza=GestioneConversioneBO.getPotenzaPerClasse(classe,valRapConversione.getPotenza());
				
				
										String umCon=comboBox_um_scala_comp.getSelectedItem().toString();
				
										misura.setUm_calc(umCon);
										misura.setFm(classe);
										CampioneCorrettoDTO valoreCorretto=null;
										
										if(umCon.equals("sec @ Periodo"))
										{
											valoreCorretto = GestioneConversioneBO.getValoreCampioneCorrettoPeriodo(comboParTar.getSelectedItem().toString(),valore_ins,true,umCon,textField_tipo_misura.getText(),potenza,comboCampioni.getSelectedItem().toString());
										}
										else 
										{
										 valoreCorretto = GestioneConversioneBO.getValoreCampioneCorretto(comboParTar.getSelectedItem().toString(),valore_ins,true,umCon,textField_tipo_misura.getText(),potenza,comboCampioni.getSelectedItem().toString());
										}
										
										textField_val_corr_scl.setText(valoreCorretto.getValoreCampioneCorretto().setScale(Costanti.SCALA,RoundingMode.HALF_UP).stripTrailingZeros().toPlainString());
										textField_val_icer_scl_comp.setText(valoreCorretto.getIncertezzaCorretta().setScale(Costanti.SCALA,RoundingMode.HALF_UP).stripTrailingZeros().toPlainString());
									}
									else
									{
										textField_val_corr_scl.setText("");
										textField_val_icer_scl_comp.setText("");
										JOptionPane.showMessageDialog(null, "Valore Lettura Fuori scala","Out of Range",JOptionPane.INFORMATION_MESSAGE,new ImageIcon(PannelloTOP.class.getResource("/image/error.png")));
									}
				
								}catch(NumberFormatException nfe)
								{
									textField_val_corr_scl.setText("");
									textField_val_icer_scl_comp.setText("");
									JOptionPane.showMessageDialog(null, "Attenzione numero inserito formalmente scorretto! \n Formato corretto: xxxx.xx","Errore Input",JOptionPane.INFORMATION_MESSAGE,new ImageIcon(PannelloTOP.class.getResource("/image/error.png")));
								}
								catch(Exception e)
								{
									e.printStackTrace();
								}
				
							}
						});

		lblIncertezza_2 = new JLabel("Incertezza Corretta");
		lblIncertezza_2.setFont(new Font("Arial", Font.BOLD, 14));
		panel_conversione_scala_completa.add(lblIncertezza_2, "cell 6 2,alignx center");



		rdbtnValoreInserito_1 = new JRadioButton("Valore Lettura");
		rdbtnValoreInserito_1.setFont(new Font("Tahoma", Font.BOLD, 12));
		rdbtnValoreInserito_1.setBackground(Color.WHITE);
		panel_conversione_scala_completa.add(rdbtnValoreInserito_1, "cell 8 2");

		group2.add(rdbtnValoreCorretto_1);
		group2.add(rdbtnValoreInserito_1);
		comboBoxFM = new JComboBox<>();
		comboBoxFM.setFont(new Font("Arial", Font.PLAIN, 14));
		panel_conversione_scala_completa.add(comboBoxFM, "cell 0 3,growx");

		textField_val_icer_scl_comp = new JTextField();
		textField_val_icer_scl_comp.setFont(new Font("Arial", Font.PLAIN, 14));
		textField_val_icer_scl_comp.setEditable(false);
		panel_conversione_scala_completa.add(textField_val_icer_scl_comp, "cell 6 3,growx");
		textField_val_icer_scl_comp.setColumns(10);

		lblMinimoScalaconvertito = new JLabel("Max Scala (c)");
		lblMinimoScalaconvertito.setFont(new Font("Arial", Font.BOLD, 14));
		panel_conversione_scala_completa.add(lblMinimoScalaconvertito, "flowx,cell 2 4");

		//textField_lettura_sca_comp = createFilteredField();
		textField_lettura_sca_comp = new JTextField();
		textField_lettura_sca_comp.setFont(new Font("Arial", Font.PLAIN, 14));
		panel_conversione_scala_completa.add(textField_lettura_sca_comp, "cell 2 2,growx");
		textField_lettura_sca_comp.setColumns(10);
		aggiungiTastierino(textField_lettura_sca_comp, frm);


		textField_max_scl_comp = new JTextField();
		textField_max_scl_comp.setFont(new Font("Arial", Font.PLAIN, 14));
		textField_max_scl_comp.setEditable(false);
		panel_conversione_scala_completa.add(textField_max_scl_comp, "cell 2 4,growx");
		textField_max_scl_comp.setColumns(10);

		textField_min_sca_comp = new JTextField();
		textField_min_sca_comp.setFont(new Font("Arial", Font.PLAIN, 14));
		textField_min_sca_comp.setEditable(false);
		panel_conversione_scala_completa.add(textField_min_sca_comp, "cell 2 0,growx");
		textField_min_sca_comp.setColumns(10);

		lblCifreSignificative_1 = new JLabel("Cifre decimali");
		lblCifreSignificative_1.setFont(new Font("Arial", Font.BOLD, 14));
		panel_conversione_scala_completa.add(lblCifreSignificative_1, "flowx,cell 0 5");

		cifreSignificative_scala = new JTextField();
		cifreSignificative_scala.setFont(new Font("Arial", Font.PLAIN, 14));
		panel_conversione_scala_completa.add(cifreSignificative_scala, "cell 0 5");
		cifreSignificative_scala.setColumns(10);

		aggiungiTastierino(cifreSignificative_scala, frm);

		btnContinua_3 = new JButton("Continua");
		btnContinua_3.setIcon(new ImageIcon(FrameMisura.class.getResource("/image/continue.png")));
		btnContinua_3.setFont(new Font("Arial", Font.BOLD, 14));
		panel_conversione_scala_completa.add(btnContinua_3, "cell 8 5,alignx center");

		btnContinua_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				if(textField_val_corr_scl.getText().length()>0){	
					if(cifreSignificative_scala.getText().length()>0)
					{
						if(rdbtnValoreCorretto_1.isSelected())
						{
							valoreCampione=new BigDecimal(textField_val_corr_scl.getText());
							incertezzaCampione=new BigDecimal(textField_val_icer_scl_comp.getText());

						}else
						{
							valoreCampione=new BigDecimal(textField_lettura_sca_comp.getText());
							incertezzaCampione=new BigDecimal(textField_val_icer_scl_comp.getText());
						}


						misura.setIncertezza(incertezzaCampione);
						misura.setValoreCampione(valoreCampione);
						misura.setUm(Utility.getLabelUMConvertita(comboBox_um_scala_comp.getSelectedItem().toString(),comboBoxFM.getSelectedItem().toString()));
						misura.setRisoluzione_campione(Utility.getRisoluzione(cifreSignificative_scala.getText()));
						misura.setSelConversione(4);
						misura.setLetturaCampione(new BigDecimal(textField_lettura_sca_comp.getText()));

						panel_misuraMaster.setVisible(true);
					}
					else
					{
						JOptionPane.showMessageDialog(null, "Inserire Cifre Significative","Errore Input",JOptionPane.INFORMATION_MESSAGE,new ImageIcon(PannelloTOP.class.getResource("/image/error.png")));
					}
				}else
				{
					JOptionPane.showMessageDialog(null, "Nessuna lettura inserita","Errore Input",JOptionPane.INFORMATION_MESSAGE,new ImageIcon(PannelloTOP.class.getResource("/image/error.png")));
				}
			}
		});

		comboBox_um_scala_comp.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {

				if(comboBox_um_scala_comp.getSelectedIndex()>0)
				{	
					try{
						String umCon=comboBox_um_scala_comp.getSelectedItem().toString();

						minMaxScala=GestioneConversioneBO.getMinMaxScala(comboCampioni.getSelectedItem().toString(),comboParTar.getSelectedItem().toString());

						if(parametro.getTipoGrandezza().equalsIgnoreCase("Temperatura"))
						{
							valRapConversione=GestioneConversioneBO.getValoreConvertitoTemperatura(parametro,umCon,textField_tipo_misura.getText(),minMaxScala[0],minMaxScala[1]);
						}
						else
						{
							valRapConversione=GestioneConversioneBO.getValoreConvertito(parametro,umCon,textField_tipo_misura.getText(),minMaxScala[0],minMaxScala[1]);
						}

						
						textField_min_sca_comp.setText(valRapConversione.getValoreMinScalaCanvertito().stripTrailingZeros().toPlainString());
						textField_max_scl_comp.setText(valRapConversione.getValoreMaxScalaCanvertito().stripTrailingZeros().toPlainString());

						minMaxScala[0]=valRapConversione.getValoreMinScalaCanvertito();
						minMaxScala[1]=valRapConversione.getValoreMaxScalaCanvertito();

						if(textField_lettura_sca_comp.getText().length()>0) 
						{
							
							try 
							{
								BigDecimal  valoreCampione=new BigDecimal(textField_lettura_sca_comp.getText());
								
								parametro.setValoreTaratura(valoreCampione);
								
								valRapConversione=GestioneConversioneBO.getValoreConvertito(parametro,umCon,textField_tipo_misura.getText(),null,null);

								textField_lettura_sca_comp.setText(valRapConversione.getValoreConvertito().stripTrailingZeros().toPlainString());
							} 
							catch (Exception e) 
							{
								
								JOptionPane.showMessageDialog(null, "Impossibile convertire valore campione , numero inserito scorretto","Errore",JOptionPane.INFORMATION_MESSAGE,new ImageIcon(PannelloTOP.class.getResource("/image/error.png")));
							}
							
							
						}else 
						{
							if(firstTime==true) 
							{
								JOptionPane.showMessageDialog(null, "Il valore in lettura non è presente, verranno convertiti solo i limiti di scala","Attenzione",JOptionPane.INFORMATION_MESSAGE,new ImageIcon(PannelloTOP.class.getResource("/image/attention.png")));
							}
							else 
							{
								firstTime=true;
							}
						}
						
						comboBoxFM.setSelectedIndex(7);

					}catch(Exception ex)
					{
						ex.printStackTrace();
					}

				}
			}
		});

		comboBoxFM.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {

				if(comboBoxFM.getSelectedIndex()!=0)
				{
					String classe=comboBoxFM.getSelectedItem().toString();

					double grado=GestioneConversioneBO.getPotenzaPerClasse(classe,valRapConversione.getPotenza());


					BigDecimal newValoreConvertitoMinScala=minMaxScala[0].divide(new BigDecimal(grado),RoundingMode.HALF_UP);
					BigDecimal newValoreConvertitoMaxScala=minMaxScala[1].divide(new BigDecimal(grado),RoundingMode.HALF_UP);
					
					if(valRapConversione.getValoreConvertito()!=null) 
					{
						BigDecimal newValoreLettura=valRapConversione.getValoreConvertito().divide(new BigDecimal(grado),RoundingMode.HALF_UP);
						textField_lettura_sca_comp.setText(newValoreLettura.setScale(10,RoundingMode.HALF_UP).stripTrailingZeros().toPlainString());
					}
					

					textField_min_sca_comp.setText(newValoreConvertitoMinScala.setScale(10,RoundingMode.HALF_UP).stripTrailingZeros().toPlainString());
					textField_max_scl_comp.setText(newValoreConvertitoMaxScala.setScale(10,RoundingMode.HALF_UP).stripTrailingZeros().toPlainString());
					
				}
			}
		});

		return panel_conversione_scala_completa;
	}

	private void conversioneDirettaNoConversione() 
	{
		CardLayout cl = (CardLayout)(this.panel_conversioniMaster.getLayout());
		cl.show(this.panel_conversioniMaster ,"card_conversione_diretta");

		val_camp_conv_dir.setVisible(false);
		val_incer_conv_dir.setVisible(false);
		comboUMConver.setVisible(false);
		comboFattMolt.setVisible(false);
		lblFattoreMoltiplicativo.setVisible(false);
		lblValoreIncertezzaConvertito.setVisible(false);
		lblValoreCampioneConvertito.setVisible(false);
		lblUmConversione.setVisible(false);
		lblCifreSignificative.setVisible(false);
		cifreSignificative_dirette.setVisible(false);

		val_Campione_Dir.setText(parametro.getValoreTaratura().stripTrailingZeros().toPlainString());
		val_Incer_Dir.setText(incertezza.toString());
		UM_Camp_dir.setText(parametro.getUm());
		UM_Incer_dir.setText(parametro.getUm());


		valoreCampione=parametro.getValoreTaratura();
		incertezzaCampione=incertezza;

		misura.setIncertezza(incertezza);
		misura.setValoreCampione(parametro.getValoreTaratura());
		misura.setUm(parametro.getUm());
		misura.setRisoluzione_campione(parametro.getRisoluzione());
		misura.setUm_calc("");
		misura.setFm("");
		misura.setSelConversione(1);


		panel_conversioniMaster.setVisible(true);



	}

	private void conversioneDirettaSiConversione() {

		lblCifreSignificative.setVisible(true);
		cifreSignificative_dirette.setVisible(true);

		CardLayout cl = (CardLayout)(FrameMisura.panel_conversioniMaster.getLayout());
		cl.show(FrameMisura.panel_conversioniMaster,"card_conversione_diretta");

		val_camp_conv_dir.setVisible(true);
		val_incer_conv_dir.setVisible(true);
		comboUMConver.setVisible(true);
		comboFattMolt.setVisible(true);
		lblFattoreMoltiplicativo.setVisible(true);
		lblValoreIncertezzaConvertito.setVisible(true);
		lblValoreCampioneConvertito.setVisible(true);
		lblUmConversione.setVisible(true);

		val_Campione_Dir.setText(parametro.getValoreTaratura().stripTrailingZeros().toPlainString());
		val_Incer_Dir.setText(incertezza.toString());
		UM_Camp_dir.setText(parametro.getUm());
		UM_Incer_dir.setText(parametro.getUm());


		String[] uMConversione;
		String[] fattMolt;

		try {
			uMConversione = GestioneConversioneBO.getListaUMConvertibili(parametro.getUm_fond(),textField_tipo_misura.getText());
			fattMolt= GestioneConversioneBO.getListaFattoriMoltiplicativi();

			comboUMConver.removeAllItems();
			comboFattMolt.removeAll();

			for(String str : uMConversione) {
				comboUMConver.addItem(str);
			}

			for(String str : fattMolt) {
				comboFattMolt.addItem(str);
			}
			panel_conversioniMaster.setVisible(true);
		} catch (Exception e) {

			e.printStackTrace();
		}	


	}

	private void conversioneScalaNoConversione() {

		CardLayout cl = (CardLayout)(this.panel_conversioniMaster.getLayout());
		cl.show(this.panel_conversioniMaster,"card_conversione_scala");

		try {
			minMaxScala=GestioneConversioneBO.getMinMaxScala(comboCampioni.getSelectedItem().toString(),comboParTar.getSelectedItem().toString());
			textField_min_scl.setText(minMaxScala[0].stripTrailingZeros().toPlainString());
			textField_max_scl.setText(minMaxScala[1].stripTrailingZeros().toPlainString());
			panel_conversioniMaster.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void conversioneScalaSiConversione() {
		CardLayout cl = (CardLayout)(this.panel_conversioniMaster.getLayout());
		cl.show(this.panel_conversioniMaster,"card_conversione_scala_completa");

		String[] uMConversione;
		String[] fattMolt;

		try {
			uMConversione = GestioneConversioneBO.getListaUMConvertibili(parametro.getUm_fond(),textField_tipo_misura.getText());
			fattMolt= GestioneConversioneBO.getListaFattoriMoltiplicativi();

			comboBox_um_scala_comp.removeAllItems();
			comboFattMolt.removeAllItems();

			for(String str : uMConversione) {
				comboBox_um_scala_comp.addItem(str);
			}

			for(String str : fattMolt) {
				comboBoxFM.addItem(str);
			}

			minMaxScala=GestioneConversioneBO.getMinMaxScala(comboCampioni.getSelectedItem().toString(),comboParTar.getSelectedItem().toString());
			textField_min_sca_comp.setText(minMaxScala[0].stripTrailingZeros().toPlainString());
			textField_max_scl_comp.setText(minMaxScala[1].stripTrailingZeros().toPlainString());
			panel_conversioniMaster.setVisible(true);
		} catch (Exception e) {

			e.printStackTrace();
		}	


	}

	private void conversioneComposizioneCampione() {

		CardLayout cl = (CardLayout)(panel_conversioniMaster.getLayout());
		cl.show(panel_conversioniMaster ,"card_composizione");
		panel_conversioniMaster.setVisible(true);

		textField_txtComposta.setText(misura.getDescrizioneParametro());
		textField_campioni_util.setText(misura.getDescrizioneCampione());

		textField_incertezzaCalcolata.setText("Ricalcolare");
		textField_misuraCalcolata.setText("Ricalcolare");



	}

	private JPanel getPanelMisura() {

		JPanel panel_misura = new JPanel();
		panel_misura.setToolTipText("Funzioni Speciali");
		panel_misura.setBorder(new TitledBorder(new LineBorder(Costanti.COLOR_RED, 2, true), "Misura Strumento in Verifica", TitledBorder.LEADING, TitledBorder.TOP, null, Costanti.COLOR_RED));
		panel_misura.setBackground(Color.WHITE);

		panel_misura.setLayout(new MigLayout("", "[][grow][][][pref!,grow][:200:200]", "[][][][][][][][]"));
		panel_misura.setVisible(false);

		lblMisura = new JLabel("Misura");
		lblMisura.setFont(new Font("Arial", Font.BOLD, 14));
		panel_misura.add(lblMisura, "cell 0 0,alignx right");
		textField_misura = new JTextField();
		textField_misura.setFont(new Font("Arial", Font.PLAIN, 14));
		panel_misura.add(textField_misura, "cell 1 0,width 100:150:");
		textField_misura.setColumns(10);
		aggiungiTastierino(textField_misura, frm);

		lblSelTolleranza = new JLabel("Sel. Tolleranza");
		lblSelTolleranza.setFont(new Font("Arial", Font.BOLD, 14));
		panel_misura.add(lblSelTolleranza, "cell 3 0,alignx trailing");
		comboBox_tolleranza = new JComboBox(Costanti.TOLLERANZA);
		comboBox_tolleranza.setFont(new Font("Arial", Font.PLAIN, 14));
		comboBox_tolleranza.setSelectedIndex(0);
		panel_misura.add(comboBox_tolleranza, "cell 4 0");

		lblDivisione = new JLabel("Divisione");
		lblDivisione.setFont(new Font("Arial", Font.BOLD, 14));
		panel_misura.add(lblDivisione, "cell 0 1,alignx right");

		textField_divisione = new JTextField();
		textField_divisione.setFont(new Font("Arial", Font.PLAIN, 14));
		panel_misura.add(textField_divisione, "cell 1 1,width 50:100:");
		textField_divisione.setColumns(10);
		aggiungiTastierino(textField_divisione, frm);

		lblDevStd = new JLabel("Calcolo DEV STD su:");

		lblDevStd.setFont(new Font("Arial",Font.BOLD,14));

		rdbtnCampione = new JRadioButton("Campione");
		rdbtnCampione.setFont(new Font("Arial",Font.BOLD,14));
		rdbtnCampione.setSelected(true);
		rdbtnCampione.setBackground(Color.WHITE);

		lblDgt = new JLabel("Dgt");
		lblDgt.setFont(new Font("Arial", Font.BOLD, 14));
		panel_misura.add(lblDgt, "cell 3 1,alignx trailing");

		textField_dgt = new JTextField();
		textField_dgt.setFont(new Font("Arial", Font.PLAIN, 14));
		panel_misura.add(textField_dgt, "flowx,cell 4 1");
		textField_dgt.setColumns(10);

		aggiungiTastierino(textField_dgt,frm);

		rdbtnStrumento = new JRadioButton("Strumento");
		rdbtnStrumento.setBackground(Color.WHITE);
		rdbtnStrumento.setFont(new Font("Arial",Font.BOLD,14));
		ButtonGroup group= new ButtonGroup();

		group.add(rdbtnCampione);
		group.add(rdbtnStrumento);

		lblInterpolazione = new JLabel("Interpolazione");
		lblInterpolazione.setFont(new Font("Arial", Font.BOLD, 14));
		panel_misura.add(lblInterpolazione, "cell 0 2,alignx right");
		comboBox_interpolazione = new JComboBox(Costanti.INTERPOLAZIONE);
		comboBox_interpolazione.setFont(new Font("Arial", Font.PLAIN, 14));
		panel_misura.add(comboBox_interpolazione, "flowx,cell 1 2");
		lblfondoScala = new JLabel("FondoScala");
		lblfondoScala.setFont(new Font("Arial", Font.BOLD, 14));
		panel_misura.add(lblfondoScala, "cell 3 2,alignx trailing");

		textField_fondoScala = new JTextField();
		textField_fondoScala.setFont(new Font("Arial", Font.PLAIN, 14));
		textField_fondoScala.setText("0");
		panel_misura.add(textField_fondoScala, "flowx,cell 4 2");
		textField_fondoScala.setColumns(10);
		aggiungiTastierino(textField_fondoScala, frm);



		lblRisoluzione = new JLabel("Risoluzione");
		lblRisoluzione.setFont(new Font("Arial", Font.BOLD, 14));
		panel_misura.add(lblRisoluzione, "cell 0 3,alignx right");

		textField_risoluzione_misura = new JTextField();
		textField_risoluzione_misura.setFont(new Font("Arial", Font.PLAIN, 14));
		textField_risoluzione_misura.setEditable(false);
		panel_misura.add(textField_risoluzione_misura, "cell 1 3,width 50:100:");
		textField_risoluzione_misura.setColumns(10);


		lblAccettabilita = new JLabel("Accettabilit\u00E0");
		lblAccettabilita.setFont(new Font("Arial", Font.BOLD, 14));
		panel_misura.add(lblAccettabilita, "cell 3 3,alignx trailing");

		textField_accettabilita = new JTextField();
		textField_accettabilita.setFont(new Font("Arial", Font.PLAIN, 14));
		textField_accettabilita.setEditable(false);
		panel_misura.add(textField_accettabilita, "cell 4 3");
		textField_accettabilita.setColumns(10);
		
				lblU = new JLabel("U:");
				lblU.setFont(new Font("Arial", Font.BOLD, 14));
				panel_misura.add(lblU, "cell 0 5,alignx right");

		lblEsito = new JLabel("Esito:");
		lblEsito.setFont(new Font("Arial", Font.BOLD, 14));
		panel_misura.add(lblEsito, "cell 3 5,alignx trailing");

		textField_esito = new JTextField();
		textField_esito.setFont(new Font("Arial", Font.PLAIN, 14));
		textField_esito.setEnabled(false);
		textField_esito.setEditable(false);
		textField_esito.setBackground(Color.YELLOW);
		panel_misura.add(textField_esito, "cell 4 5");
		textField_esito.setColumns(10);

		chckbxNonApplicabile = new JCheckBox("Non applicabile");
		chckbxNonApplicabile.setFont(new Font("Arial", Font.BOLD, 12));
		chckbxNonApplicabile.setBackground(Color.WHITE);
		chckbxNonApplicabile.setSelected(false);
		panel_misura.add(chckbxNonApplicabile, "cell 0 6");


		btn_confermaDati = new JButton("Conferma Dati");
		btn_confermaDati.setIcon(new ImageIcon(FrameMisura.class.getResource("/image/save.png")));
		btn_confermaDati.setFont(new Font("Arial", Font.BOLD, 14));
		btn_confermaDati.setEnabled(false);
		panel_misura.add(btn_confermaDati, "flowx,cell 0 7 6 1,alignx center");

		calcolo_u = new JTextField();
		calcolo_u.setFont(new Font("Arial", Font.BOLD, 16));
		calcolo_u.setEnabled(false);
		calcolo_u.setEditable(false);
		panel_misura.add(calcolo_u, "cell 1 5,width :150:,growy");
		calcolo_u.setColumns(10);

		btnFunzioniSpeciali = new JButton("Funzioni Speciali");
		btnFunzioniSpeciali.setFont(new Font("Arial", Font.BOLD, 14));
		btnFunzioniSpeciali.setToolTipText("Funzioni Speciali");

		label_perc = new JLabel("%");
		label_perc.setFont(new Font("Arial", Font.BOLD, 14));
		panel_misura.add(label_perc, "cell 4 2,alignx trailing");
		textField_percentuale = new JTextField();
		panel_misura.add(textField_percentuale, "cell 4 2,growx,width 10:50:100");
		textField_percentuale.setColumns(10);
		aggiungiTastierino(textField_percentuale, frm);

		if(tipoProva)
		{
			panel_misura.add(rdbtnStrumento, "cell 5 3,alignx left,gapleft 25");
			panel_misura.add(rdbtnCampione, "cell 5 2,alignx left,gapleft 25");
			panel_misura.add(lblDevStd, "cell 5 1,alignx left,gapleft 25");
		}

		if(SessionBO.tipoRapporto.equals("SVT"))
		{
			lblSelTolleranza.setVisible(true);
			comboBox_tolleranza.setVisible(true);

			lblDgt.setVisible(true);
			textField_dgt.setVisible(true);

			label_perc.setVisible(true);
			textField_percentuale.setVisible(true);

			lblfondoScala.setVisible(true);
			textField_fondoScala.setVisible(true);

			lblAccettabilita.setVisible(true);
			textField_accettabilita.setVisible(true);

			lblEsito.setVisible(true);
			textField_esito.setVisible(true);

		}
		if(SessionBO.tipoRapporto.equals("RDT"))
		{
			lblSelTolleranza.setVisible(false);
			comboBox_tolleranza.setVisible(false);

			lblDgt.setVisible(false);
			textField_dgt.setVisible(false);

			label_perc.setVisible(false);
			textField_percentuale.setVisible(false);

			lblfondoScala.setVisible(false);
			textField_fondoScala.setVisible(false);

			lblAccettabilita.setVisible(false);
			textField_accettabilita.setVisible(false);

			lblEsito.setVisible(false);
			textField_esito.setVisible(false);
		}

		puntoCorrente.addActionListener(new ActionListener() {


			public void actionPerformed(ActionEvent arg0) {

				int rigaSel=puntoCorrente.getSelectedIndex();
				int id=(int)table.getModel().getValueAt(rigaSel,0);
				firstTime=false;
				btn_confermaDati.setEnabled(false);
				boolean daPrecedente=false;

				try {
					misura= GestioneMisuraBO.getMisura(id);

					if(misura.getMisuraCampionePrecedente()!=null && misura.getMisuraCampionePrecedente().length()>0)
					{
						textField_rif_cmp_precedente.setText(misura.getMisuraCampionePrecedente());
						textField_rif_cmp_precedente.setToolTipText(misura.getMisuraCampionePrecedente());
						textField_rif_misura_prec.setText(new BigDecimal(misura.getMisuraPrecedente()).stripTrailingZeros().toPlainString());
						daPrecedente=true;
						if(misura.getPercentuale()!=null)
						{
							textField_percentuale.setText(misura.getPercentuale().stripTrailingZeros().toPlainString());
						}
						else
						{
							textField_percentuale.setText("");
						}
						if(misura.getFondoScala()!=null)
						{
							textField_fondoScala.setText(misura.getFondoScala().stripTrailingZeros().toPlainString());
						}
						else
						{
							textField_fondoScala.setText("");
						}
						
						if(misura.getDgt()!=null)
						{
							textField_dgt.setText(misura.getDgt().stripTrailingZeros().toPlainString());
						}
							comboBox_tolleranza.setSelectedIndex(misura.getSelTolleranza());
					}
					else
					{
						textField_rif_cmp_precedente.setText("ND");
						textField_rif_misura_prec.setText("ND");
					}

					if(misura.getMisura()!=null )
					{
						panel_conversioniMaster.setVisible(true);	

						String descrizioneCampione=misura.getDescrizioneCampione();

						if(descrizioneCampione.indexOf("|")>0)
						{
							descrizioneCampione=descrizioneCampione.substring(0,descrizioneCampione.indexOf("|"));
						}
						comboCampioni.setSelectedItem(descrizioneCampione);
						comboParTar.setSelectedItem(misura.getDescrizioneParametro());


						if(misura.getSelConversione()==1)
						{
							conversioneDirettaNoConversione();
							btnContinua_1.doClick();
						}
						if(misura.getSelConversione()==2)
						{

							conversioneDirettaSiConversione();
							comboUMConver.setSelectedItem(misura.getUm_calc());
							comboFattMolt.setSelectedItem(misura.getFm());

							cifreSignificative_dirette.setText(""+misura.getRisoluzione_misura().precision());
							btnContinua_1.doClick();
						}
						if(misura.getSelConversione()==3)
						{
							conversioneScalaNoConversione();
							valore_inserito.setText(misura.getLetturaCampione().stripTrailingZeros().toPlainString());
							cifreSignificative_scala.setText(""+misura.getRisoluzione_campione().stripTrailingZeros().scale());
							btnCheck.doClick();
							btnContinua_2.doClick();
						}
						if(misura.getSelConversione()==4)
						{
							conversioneScalaSiConversione();
							comboBox_um_scala_comp.setSelectedItem(misura.getUm_calc());
							comboBoxFM.setSelectedItem(misura.getFm());
							textField_lettura_sca_comp.setText(misura.getLetturaCampione().stripTrailingZeros().toPlainString());
							cifreSignificative_scala.setText(""+misura.getRisoluzione_campione().stripTrailingZeros().scale());
							btnCheckScalaComp.doClick();
							btnContinua_3.doClick();

						}

						if(misura.getSelConversione()==5)
						{
							conversioneComposizioneCampione();
						}

						panel_misuraMaster.setVisible(true);
						comboBox_tolleranza.setSelectedIndex(misura.getSelTolleranza());

						textField_misura.setText(misura.getMisura().toPlainString());
						textField_risoluzione_misura.setText(misura.getRisoluzione_misura().stripTrailingZeros().toPlainString());

						if(misura.getFondoScala()!=null)
						{
							textField_fondoScala.setText(misura.getFondoScala().stripTrailingZeros().toPlainString());
						}
						else
						{
							textField_fondoScala.setText("");
						}

						if(misura.getPercentuale()!=null)
						{
							textField_percentuale.setText(misura.getPercentuale().stripTrailingZeros().toPlainString());
						}
						else
						{
							textField_percentuale.setText("");
						}

						if(misura.getDgt()!=null)
						{
							textField_dgt.setText(misura.getDgt().stripTrailingZeros().toPlainString());
						}
						else
						{
							textField_dgt.setText("");
						}

						comboBox_interpolazione.setSelectedItem(misura.getInterpolazione());

						if(misura.getAccettabilita()!=null)
						{
							textField_accettabilita.setText(misura.getAccettabilita().stripTrailingZeros().toPlainString());
						}else
						{
							textField_accettabilita.setText("");
						}

						if(misura.getApplicabile().equals("N"))
						{
							chckbxNonApplicabile.setSelected(true);
						}
						else
						{
							chckbxNonApplicabile.setSelected(false);
						}


					}
					else
					{
						panel_misuraMaster.setVisible(false);
						panel_conversioniMaster.setVisible(false);
						textField_misura.setText("");
						textField_risoluzione_misura.setText("");
						comboBox_interpolazione.setSelectedItem("");
						textField_accettabilita.setText("");
						if(daPrecedente==false) 
						{
							textField_dgt.setText("");
							comboBox_tolleranza.setSelectedIndex(0);
							textField_percentuale.setText("");
							textField_fondoScala.setText("");
						}
						textField_lettura_sca_comp.setText("");
						textField_val_corr_scl.setText("");
						textField_val_icer_scl_comp.setText("");
						
						
						textField_divisione.setText("");
						chckbxNonApplicabile.setSelected(false);

						if(comboBox_um_scala_comp.getItemCount()>0)comboBox_um_scala_comp.setSelectedIndex(0);
						if(comboBoxFM.getItemCount()>0)comboBoxFM.setSelectedIndex(0);
						if(comboFattMolt.getItemCount()>0)comboFattMolt.setSelectedIndex(0);
						if(comboUMConver.getItemCount()>0)comboUMConver.setSelectedIndex(0);
					}

					textField_esito.setEnabled(false);
					textField_esito.setEditable(false);
					textField_esito.setText("");
					textField_esito.setBackground(Color.YELLOW);
					calcolo_u.setText("");
				}
				catch (Exception e) 
				{
					e.printStackTrace();
				}


			}


		});

		textField_dgt.getDocument().addDocumentListener(new DocumentListener() { 


			public void changedUpdate(DocumentEvent arg0) {}

			public void insertUpdate(DocumentEvent arg0) {

				if (comboBox_tolleranza.getSelectedIndex()==0)
				{

					if(textField_dgt.getText().length()>0)
					{
						try 
						{
							Double.parseDouble(textField_dgt.getText());
							textField_accettabilita.setText(textField_dgt.getText());
						} 
						catch (Exception e) 
						{
							JOptionPane.showMessageDialog(null,"Il campo accetta solo numeri", "Input",JOptionPane.INFORMATION_MESSAGE,new ImageIcon(PannelloTOP.class.getResource("/image/attention.png")));
						}	
					}
				}

			}

			@Override
			public void removeUpdate(DocumentEvent arg0) {

				if(textField_dgt.getText().length()==0)
				{
					textField_accettabilita.setText("");
				}

			}
		});

		comboBox_tolleranza.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int index=comboBox_tolleranza.getSelectedIndex();
				if(index==0)
				{
					abilita(textField_misura);
					abilita(textField_dgt);
					disabilita(textField_fondoScala);
					disabilita(textField_percentuale);

				}
				if(index==1)
				{
					abilita(textField_misura);
					disabilita(textField_fondoScala);
					disabilita(textField_dgt);
					abilita(textField_percentuale);

				}
				if(index==2)
				{
					abilita(textField_misura);
					abilita(textField_fondoScala);
					abilita(textField_percentuale);
					disabilita(textField_dgt);

				}
				if(index==3)
				{
					abilita(textField_misura);
					disabilita(textField_fondoScala);
					abilita(textField_percentuale);
					abilita(textField_dgt);
				}
			}
		});

		textField_divisione.getDocument().addDocumentListener(new DocumentListener() {

			@Override
			public void removeUpdate(DocumentEvent arg0) {

				if(textField_divisione.getText().length()==0)
				{
					textField_risoluzione_misura.setText("");
				}

			}

			@Override
			public void insertUpdate(DocumentEvent arg0) {

				String div = textField_divisione.getText();
				if(!div.equals(""))
				{
					try {
						BigDecimal d =new BigDecimal(div);
						d=d.setScale(Costanti.SCALA,RoundingMode.HALF_UP).divide(new BigDecimal(comboBox_interpolazione.getSelectedItem().toString()),RoundingMode.HALF_UP).setScale(Costanti.SCALA, RoundingMode.HALF_UP);
						textField_risoluzione_misura.setText(""+d.stripTrailingZeros());

					} catch (Exception e2) {
						JOptionPane.showMessageDialog(null, "Attenzione numero inserito formalmente scorretto! \n Formato corretto: xxxx.xx","Errore Input",JOptionPane.INFORMATION_MESSAGE,new ImageIcon(PannelloTOP.class.getResource("/image/error.png")));
					}


				}

			}

			@Override
			public void changedUpdate(DocumentEvent arg0) {
				// TODO Auto-generated method stub

			}
		});

		comboBox_interpolazione.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {

				String div = textField_divisione.getText();

				if(div.length()>0)
				{

					try {
						
						BigDecimal d =new BigDecimal(div);
						d=d.setScale(Costanti.SCALA,RoundingMode.HALF_UP).divide(new BigDecimal(comboBox_interpolazione.getSelectedItem().toString()),RoundingMode.HALF_UP).setScale(Costanti.SCALA, RoundingMode.HALF_UP);
						textField_risoluzione_misura.setText(""+d.stripTrailingZeros());


					} catch (Exception e2) {
						JOptionPane.showMessageDialog(null, "Attenzione numero inserito formalmente scorretto! \n Formato corretto: xxxx.xx","Errore Input",JOptionPane.INFORMATION_MESSAGE,new ImageIcon(PannelloTOP.class.getResource("/image/error.png")));
					}
				}

			}
		});		

		btnFunzioniSpeciali.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) 
			{

				SwingUtilities.invokeLater(new Runnable(){
					public void run() 
					{
						try
						{
							JFrame f=new FrameInterpolazione();

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
		});


		/* Interpolazione */
		btnFunzioniSpeciali.setIcon(new ImageIcon(FrameMisura.class.getResource("/image/Interpolation.png")));
		panel_misura.add(btnFunzioniSpeciali, "cell 1 0,gapx 15");
		
				btnControllaEsito = new JButton("Calcola Incertezza");
				btnControllaEsito.setIcon(new ImageIcon(FrameMisura.class.getResource("/image/incertezza.png")));
				btnControllaEsito.setFont(new Font("Arial", Font.BOLD, 14));
				
						btnControllaEsito.setSelectedIcon(null);
						
								panel_misura.add(btnControllaEsito, "cell 1 5");
								
								
										btnControllaEsito.addActionListener(new ActionListener() {
											public void actionPerformed(ActionEvent arg0) 
											{
								
												try
												{	
													/*CALCOLO INCERTEZZA IN FUNZIONE DI 2 PARAMETRI 
													 * @param tipoProva true=ripetibilità / false=linearita
													 * @param tipoRapporto SVT oppure RDT
													 * */
													BigDecimal u=null;
													
													if(misura.getSelConversione()==5 && textField_misuraCalcolata!=null && textField_misuraCalcolata.getText().equals("Ricalcolare")) 
													{
														JOptionPane.showMessageDialog(null,"Non è possibile ricalcolare l'incertezza fino a quando non si ricalcola la composizione dei campioni","Attenzione",JOptionPane.INFORMATION_MESSAGE,new ImageIcon(PannelloTOP.class.getResource("/image/attention.png")));
														return;
													}
								
													if(SessionBO.tipoRapporto.equals( "SVT"))	{
								
														if(textField_misura.getText().length()>0 && textField_risoluzione_misura.getText().length()>0 && textField_accettabilita.getText().length()>0)
														{
															textField_esito.setText("");
															textField_esito.setBackground(Color.yellow);
															valoreMisura=new BigDecimal(textField_misura.getText());
															risoluzione_misura=new BigDecimal(textField_risoluzione_misura.getText());
								
															interpolazione =Integer.parseInt(comboBox_interpolazione.getSelectedItem().toString());
								
								
								
															if(tipoProva)
															{
								
																try {
								
								
																	ProvaMisuraDTO misura =GestioneMisuraBO.getProvaMisura(SessionBO.idStrumento);
								
																	int rigaSel=puntoCorrente.getSelectedIndex();
																	id=(int)table.getModel().getValueAt(rigaSel,0);
								
								
																	valoreMedioCampione= GestioneMisuraBO.getValoreMedioCampione(id,valoreCampione,misura);
																	valoreMedioStrumento= GestioneMisuraBO.getValoreMedioStrumento(id,valoreMisura,misura);
																	
																	
																	valoreScostamentoMax = GestioneMisuraBO.getValoreScostamentoMax(id,valoreCampione,valoreMisura, misura);
															
																	
																	ArrayList<MisuraDTO> listaMisure=GestioneMisuraBO.getListaPunti(id, misura);
																	
																	for (int i=0;i<listaMisure.size();i++) 
																	{
																	
																		if(listaMisure.get(i).getId()==id)
																		{
																			listaMisure.get(i).setValoreStrumento(valoreMisura);
																			listaMisure.get(i).setValoreCampione(valoreCampione);
																		}
																	}
																	
								
																	int tipoDev=1;
																	if(rdbtnStrumento.isSelected())
																	{
																		tipoDev=2;
																	}
								
																	incertezzaRipetibilita=GestioneMisuraBO.calcolaIncertezzaSVTRipetibilita(listaMisure,incertezzaCampione,valoreMedioCampione,valoreMedioStrumento ,tipoDev,risoluzione_misura,valoreScostamentoMax);
																	incertezzaRipetibilita = incertezzaRipetibilita.round(new MathContext(2, RoundingMode.HALF_UP));
								
																	u=incertezzaRipetibilita;
																	
																} catch (Exception e) {
								
																	e.printStackTrace();
																}	
								
								
															}
															else
															{
								
																u =GestioneMisuraBO.calcolaIncertezzaSVTLineare(valoreMisura,risoluzione_misura,valoreCampione,incertezzaCampione);
																u = u.round(new MathContext(2, RoundingMode.HALF_UP));
																misura.setIncertezza(u);
															}
															
															
															calcolo_u.setText(u.stripTrailingZeros().toPlainString());
								
															accettabilita=BigDecimal.ZERO;
															fondoScala=BigDecimal.ZERO;
															percentuale=BigDecimal.ZERO;
								
															if(textField_accettabilita.getText().length()>0)
															{
																accettabilita=new BigDecimal(textField_accettabilita.getText());
															}
								
															if(textField_fondoScala.getText().length()>0)
															{
																fondoScala=new BigDecimal(textField_fondoScala.getText());
															}
								
															if(textField_percentuale.getText().length()>0)
															{
																percentuale=new BigDecimal(textField_percentuale.getText());
															}									
								
								
															if(u.compareTo(accettabilita.abs())< 1 || u.compareTo(accettabilita.abs())==0  )
															{
																textField_esito.setText(Costanti.IDONEO);
																textField_esito.setBackground(Color.green);
															}
															else
															{
																textField_esito.setText(Costanti.NON_IDONEO);
																textField_esito.setBackground(Color.red);
															}
															btn_confermaDati.setEnabled(true);
								
														}
														else
														{
															JOptionPane.showMessageDialog(null, "Inserire Misura - Risoluzione - Accetabilità","",JOptionPane.INFORMATION_MESSAGE,new ImageIcon(PannelloTOP.class.getResource("/image/attention.png")));
														}
								
													}
								
													if(SessionBO.tipoRapporto.equals( "RDT"))	{
								
								
														textField_esito.setText("");
														//	textField_esito.setBackground(Color.yellow);
														valoreMisura=new BigDecimal(textField_misura.getText());
														risoluzione_misura=new BigDecimal(textField_risoluzione_misura.getText());
														interpolazione =Integer.parseInt(comboBox_interpolazione.getSelectedItem().toString());
								
								
								
														if(tipoProva)
														{
								
															try {
								
																/*Controllo se la misura è originata da una composizione*/
																
																ProvaMisuraDTO misura =GestioneMisuraBO.getProvaMisura(SessionBO.idStrumento);
								
																int rigaSel=puntoCorrente.getSelectedIndex();
																id=(int)table.getModel().getValueAt(rigaSel,0);
								
																valoreMedioCampione= GestioneMisuraBO.getValoreMedioCampione(id,valoreCampione,misura);
																valoreMedioStrumento= GestioneMisuraBO.getValoreMedioStrumento(id,valoreMisura,misura);
								
																ArrayList<MisuraDTO> listaMisure=GestioneMisuraBO.getListaPunti(id, misura);
								
																MisuraDTO misuraAdd  = new MisuraDTO();
																misuraAdd.setValoreStrumento(valoreMisura);
																misuraAdd.setValoreCampione(valoreCampione);
								
																listaMisure.add(misuraAdd);
																int tipoDev=1;
																if(rdbtnStrumento.isSelected())
																{
																	tipoDev=2;
																}
								
																incertezzaRipetibilita=GestioneMisuraBO.calcolaIncertezzaRDTRipetibilita(listaMisure,incertezzaCampione,valoreMedioCampione,valoreMedioStrumento ,tipoDev,risoluzione_misura);
																incertezzaRipetibilita = incertezzaRipetibilita.round(new MathContext(2, RoundingMode.HALF_UP));
																u=incertezzaRipetibilita;
															} catch (Exception e) {
								
																e.printStackTrace();
															}	
								
								
														}
														else
														{
								
															u =GestioneMisuraBO.calcolaIncertezzaRDTLineare(valoreMisura,risoluzione_misura,valoreCampione,incertezzaCampione);
															u = u.round(new MathContext(2, RoundingMode.HALF_UP));
															misura.setIncertezza(u);
														}
														calcolo_u.setText(u.stripTrailingZeros().toPlainString());
								
														accettabilita=BigDecimal.ZERO;
														fondoScala=BigDecimal.ZERO;
														percentuale=BigDecimal.ZERO;
								
														btn_confermaDati.setEnabled(true);
								
													}
								
												}catch(NumberFormatException ex)
												{
													JOptionPane.showMessageDialog(null, "Attenzione numero inserito formalmente scorretto! \n Formato corretto: xxxx.xx","Errore Input",JOptionPane.INFORMATION_MESSAGE,new ImageIcon(PannelloTOP.class.getResource("/image/error.png")));
												}
											}
										});

		textField_percentuale.getDocument().addDocumentListener(new DocumentListener() { 

			public void changedUpdate(DocumentEvent e) {

			}
			@Override
			public void insertUpdate(DocumentEvent e) {

				if (comboBox_tolleranza.getSelectedIndex()==1)
				{

					if(textField_percentuale.getText().length()>0)
					{

						try 
						{
							Double.parseDouble(textField_percentuale.getText());
							Double.parseDouble(textField_misura.getText());

							percentuale=new BigDecimal(textField_percentuale.getText());
							valoreMisura= new BigDecimal(textField_misura.getText());

						} catch (NumberFormatException nfe) 
						{
							JOptionPane.showMessageDialog(null, "Percentuale errata","Errore input",JOptionPane.INFORMATION_MESSAGE,new ImageIcon(PannelloTOP.class.getResource("/image/error.png")));	
						}
						BigDecimal percMis=valoreMisura.multiply(percentuale).divide(BigDecimal.valueOf(100)).setScale(Costanti.SCALA,RoundingMode.HALF_UP);
						textField_accettabilita.setText(percMis.stripTrailingZeros().toPlainString());
					}
				}
				if(comboBox_tolleranza.getSelectedIndex()==2)
				{
					try 
					{
						Double.parseDouble(textField_percentuale.getText());
						Double.parseDouble(textField_fondoScala.getText());

						percentuale=new BigDecimal(textField_percentuale.getText());
						fondoScala=new BigDecimal(textField_fondoScala.getText());
						BigDecimal percFS=percentuale.multiply(fondoScala).divide(BigDecimal.valueOf(100)).setScale(Costanti.SCALA,RoundingMode.HALF_UP);
						textField_accettabilita.setText(percFS.stripTrailingZeros().toPlainString());


					} catch (NumberFormatException nfe) {

						JOptionPane.showMessageDialog(null, "Percentuale o Fondo Scala errati","Errore input",JOptionPane.INFORMATION_MESSAGE,new ImageIcon(PannelloTOP.class.getResource("/image/error.png")));

					}


				}
				if(comboBox_tolleranza.getSelectedIndex()==3)
				{
					try 
					{
						Double.parseDouble(textField_percentuale.getText());
						Double.parseDouble(textField_dgt.getText());
						percentuale=new BigDecimal(textField_percentuale.getText());
						valoreMisura=new BigDecimal(textField_misura.getText());

					} catch (NumberFormatException nfe) {

						//	JOptionPane.showMessageDialog(null, "Errore input dati","",JOptionPane.ERROR_MESSAGE);
						return;	
					}

					BigDecimal percMis=valoreMisura.multiply(percentuale).divide(BigDecimal.valueOf(100)).setScale(Costanti.SCALA,RoundingMode.HALF_UP); 
					BigDecimal sum =percMis.add(new BigDecimal(textField_dgt.getText()).setScale(Costanti.SCALA,RoundingMode.HALF_UP));

					textField_accettabilita.setText(sum.stripTrailingZeros().setScale(Costanti.SCALA,RoundingMode.HALF_UP).toPlainString());
				}

			}
			@Override
			public void removeUpdate(DocumentEvent e) {
				// TODO Auto-generated method stub

			}
		});

		/*Salvataggio dati su DB*/
		btn_confermaDati.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				try{

					int scelta=	JOptionPane.showConfirmDialog(null,"Vuoi Salvare i dati ?","Salva",JOptionPane.YES_NO_OPTION,JOptionPane.INFORMATION_MESSAGE,new ImageIcon(PannelloTOP.class.getResource("/image/question.png")));

					if(scelta==0)
					{


						int rigaSel=puntoCorrente.getSelectedIndex();
						id=(int)table.getModel().getValueAt(rigaSel,0);
						int risol=new BigDecimal(textField_risoluzione_misura.getText()).stripTrailingZeros().scale();

						int risolCampione=misura.getRisoluzione_campione().stripTrailingZeros().scale();

						misura.setId(id);
						misura.setTipoVerifica(puntoCorrente.getSelectedItem().toString());
						misura.setValoreStrumento(valoreMisura.setScale(risol,RoundingMode.HALF_UP));
						misura.setMisura(valoreMisura.setScale(risol,RoundingMode.HALF_UP));

						if(textField_dgt.getText().length()>0) 
						{
							misura.setDgt(new BigDecimal(textField_dgt.getText()));
						}
						else
						{
							misura.setDgt(BigDecimal.ZERO);
						}

						if(SessionBO.tipoRapporto.equals( "SVT"))
						{
							misura.setScostamento((valoreMisura.setScale(risol,RoundingMode.HALF_UP).subtract(valoreCampione.setScale(risolCampione,RoundingMode.HALF_UP)).setScale(risol+1,RoundingMode.HALF_UP)));
						}

						if(SessionBO.tipoRapporto.equals( "RDT"))
						{
							misura.setScostamento((valoreMisura.setScale(risol,RoundingMode.HALF_UP).subtract(valoreCampione.setScale(risolCampione,RoundingMode.HALF_UP)).multiply(new BigDecimal(-1)).setScale(risol,RoundingMode.HALF_UP)));
						}

						misura.setAccettabilita(accettabilita.setScale(risol,RoundingMode.HALF_UP));
						misura.setEsito(textField_esito.getText());


						if(misura.getSelConversione()!=5)
						{
							if(campioneInterpolazione!=null && !campioneInterpolazione.equals("") && !campioneInterpolazione.equals("null"))
							{
								misura.setDescrizioneCampione(comboCampioni.getSelectedItem().toString()+campioneInterpolazione);
							}else
							{
								if(comboCampioni.getSelectedIndex()>0) 
								{
									misura.setDescrizioneCampione(comboCampioni.getSelectedItem().toString());
								}else 
								{
									misura.setDescrizioneCampione("");
								}
							}
							misura.setDescrizioneParametro(comboParTar.getSelectedItem().toString());
							campioneInterpolazione="";
						}
						else
						{
							if(campioneInterpolazione!=null && !campioneInterpolazione.equals("") && !campioneInterpolazione.equals("null"))
							{
								misura.setDescrizioneCampione(textField_campioni_util.getText()+campioneInterpolazione);
							}else 
							{
								misura.setDescrizioneCampione(textField_campioni_util.getText());
							}
							
							misura.setDescrizioneParametro(textField_txtComposta.getText());
							campioneInterpolazione="";
						}


						misura.setRisoluzione_misura(risoluzione_misura);
						misura.setFondoScala(fondoScala);
						misura.setInterpolazione(interpolazione);
						misura.setSelTolleranza(comboBox_tolleranza.getSelectedIndex());
						misura.setPercentuale(percentuale);

						if(chckbxNonApplicabile.isSelected())
						{
							misura.setApplicabile("N");
						}else
						{
							misura.setApplicabile("S");
						}

						misura.setValoreCampione(misura.getValoreCampione().setScale(risolCampione,RoundingMode.HALF_UP));


						if(tipoProva)
						{
							ProvaMisuraDTO provaMisura =GestioneMisuraBO.getProvaMisura(SessionBO.idStrumento);

							valoreMedioCampione= GestioneMisuraBO.getValoreMedioCampione(id,valoreCampione,provaMisura);	

							valoreMedioCampione=valoreMedioCampione.setScale(risolCampione,RoundingMode.HALF_UP);

							GestioneMisuraBO.setValoreMedioCampione(id,valoreMedioCampione);

							valoreMedioStrumento= GestioneMisuraBO.getValoreMedioStrumento(id,valoreMisura,provaMisura);	

							valoreMedioStrumento=valoreMedioStrumento.setScale(risol,RoundingMode.HALF_UP);

							GestioneMisuraBO.setValoreMedioStrumento(id,valoreMedioStrumento);

							SQLiteDAO.updateRecordMisura(misura);

							ArrayList<MisuraDTO> listaMisure=GestioneMisuraBO.getListaPunti(id, provaMisura);

						//	BigDecimal incertezzaMax=getIncertezzaMassima(listaMisure,incertezzaRipetibilita);
							
							for (int i = 0; i < listaMisure.size(); i++) 
							{
								
								
								
								
								listaMisure.get(i).setIncertezza(incertezzaRipetibilita);
								//listaMisure.get(i).setIncertezza(incertezzaMax);
								listaMisure.get(i).setScostamento(valoreMedioCampione.subtract(valoreMedioStrumento));

								if(listaMisure.get(i).getAccettabilita()!=null)
								{	  
									BigDecimal accettabilita=listaMisure.get(i).getAccettabilita();

									if(incertezzaRipetibilita.compareTo(accettabilita)< 1 || incertezzaRipetibilita.compareTo(accettabilita)==0  )
									{
										listaMisure.get(i).setEsito(Costanti.IDONEO);

									}
									else
									{
										listaMisure.get(i).setEsito(Costanti.NON_IDONEO);

									}
								}
								else
								{
									listaMisure.remove(i);
								}

							}

							GestioneMisuraBO.updateValoriRipetibilita(listaMisure);
						}
						else
						{
							SQLiteDAO.updateRecordMisura(misura);
						}


						if(comboParTar.getSelectedItem()!=null){

							GestioneCampioneBO.saveCampione(id,SessionBO.idMisura,comboCampioni.getSelectedItem().toString(),comboParTar.getSelectedItem().toString());

						}else
						{
							GestioneCampioneBO.saveCampione(id,SessionBO.idMisura,"CMP","CMP");
						}
						JPanel panelDB =new PannelloMisuraTabelle();

						SystemGUI.callPanel(panelDB, "PMT");

					}	
				} catch (Exception e) {
					e.printStackTrace();
				}



			}

			private BigDecimal getIncertezzaMassima(ArrayList<MisuraDTO> listaMisure,BigDecimal incertezzaRipetibilita) {
			
				BigDecimal incertezzaMax= incertezzaRipetibilita;
				
				for (int j = 0; j < listaMisure.size(); j++) {
					
					BigDecimal incertezzaCorrente=listaMisure.get(j).getIncertezza();
					
					if(incertezzaCorrente!=null) 
					{
						if(incertezzaMax.compareTo(incertezzaCorrente)==-1)
						{
							incertezzaMax=incertezzaCorrente;
						}
					}
					
				}
				return incertezzaMax;
			}
		});

		return panel_misura;
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

	private Punto[] getListaPunti(JTable tab) {

		int size=tab.getModel().getRowCount();

		Punto[] data = new Punto[size];

		for(int i=0;i<size;i++)
		{
			data[i]=new Punto(table.getModel().getValueAt(i, 1).toString());
		}
		return data;
	}

	private void disabilita(JTextField field) {

		field.setEditable(false);
		field.setEnabled(false);
		field.setBackground(Color.gray);

	}
	private void abilita(JTextField field) {

		field.setEditable(true);
		field.setEnabled(true);
		field.setBackground(Color.white);

	}
	public class Punto{

		private String nomePunto;

		public Punto(String name){
			this.nomePunto = name;
		}
		@Override
		public String toString(){
			return nomePunto;
		}

	}

	public static void setPannelloMisura(String txtComposizione, String cmp, BigDecimal _misura,BigDecimal incertezza, String um, BigDecimal risoluzioneLab) {

		panel_misuraMaster.setVisible(false);
		CardLayout cl = (CardLayout)(panel_conversioniMaster.getLayout());
		cl.show(panel_conversioniMaster ,"card_composizione");
		panel_conversioniMaster.setVisible(true);

		valoreCampione=_misura;
		incertezzaCampione=incertezza;
		textField_txtComposta.setText(txtComposizione);
		textField_campioni_util.setText(cmp);
		textField_misuraCalcolata.setText(_misura.setScale(Costanti.SCALA,RoundingMode.HALF_UP).stripTrailingZeros().toPlainString());
		textField_incertezzaCalcolata.setText(incertezza.setScale(Costanti.SCALA,RoundingMode.HALF_UP).stripTrailingZeros().toPlainString());
		unitaMisuraComposta.setText(um);
		risoluzioneComposta.setText(risoluzioneLab.toPlainString());

		misura.setSelConversione(5);
		misura.setIncertezza(incertezzaCampione);
		misura.setValoreCampione(valoreCampione);
		misura.setRisoluzione_campione(risoluzioneLab);
		misura.setUm(um);

		panel_misuraMaster.setVisible(true);
	}

	class ThreadDASM implements Runnable {

		@Override
		public void run() 
		{
			try
			{

				if(comboParTar.getSelectedItem()!=null)
				{
					String canale=comboParTar.getSelectedItem().toString();

					if(canale!=null && canale.indexOf("[")==0 && canale.indexOf("]")>0)
					{
						DatiDASM_DTO dati =GestioneDASM.getDatiPorta();


						if(com.isConnected()==false)
						{
							com.connect(dati.getPorta(), dati.getFramerate());
							listaSonde=GestioneSonda.getListaSonde(com);
						}

						String pv=canale.substring(canale.indexOf("[")+1,canale.indexOf("]"));

						SondaDTO sonda=DASM_GUI.getSonda(listaSonde,pv);


						GuiWorker1 d =	 new GuiWorker1(sonda,com, frm);
						d.execute();
						spl.close();
					}
					else
					{
						spl.close();
						JOptionPane.showMessageDialog(null, "Il parametro selezionato non è applicabile al sistema DASM","Parametro non valido",JOptionPane.INFORMATION_MESSAGE,new ImageIcon(PannelloTOP.class.getResource("/image/error.png")));
					}

				}else
				{

					spl.close();
				}
			}
			catch(NoSuchPortException e)
			{
				spl.close();
				JOptionPane.showMessageDialog(null, "Dispositivo DasmTar non connesso","Parametro non valido",JOptionPane.INFORMATION_MESSAGE,new ImageIcon(PannelloTOP.class.getResource("/image/error.png")));
			}
			catch(Exception ex)
			{
				spl.close();
				ex.printStackTrace();
			}
		}
	}
}
