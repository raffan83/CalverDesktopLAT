package it.calverDesktopLAT.gui;


import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.SystemColor;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.net.URL;
import java.util.ArrayList;
import java.util.Vector;
import java.util.prefs.Preferences;

import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.plaf.basic.BasicComboBoxRenderer;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

import org.apache.commons.io.FilenameUtils;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.block.BlockBorder;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.StandardBarPainter;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import it.calverDesktopLAT.bo.GestioneMisuraBO;
import it.calverDesktopLAT.bo.GestioneStampaBO;
import it.calverDesktopLAT.bo.GestioneStrumentoBO;
import it.calverDesktopLAT.bo.SessionBO;
import it.calverDesktopLAT.dao.SQLiteDAO;
import it.calverDesktopLAT.dto.ClassificazioneDTO;
import it.calverDesktopLAT.dto.LuogoVerificaDTO;
import it.calverDesktopLAT.dto.MisuraDTO;
import it.calverDesktopLAT.dto.ProvaMisuraDTO;
import it.calverDesktopLAT.dto.StrumentoDTO;
import it.calverDesktopLAT.dto.TabellaMisureDTO;
import it.calverDesktopLAT.dto.TipoRapportoDTO;
import it.calverDesktopLAT.dto.TipoStrumentoDTO;
import it.calverDesktopLAT.utl.Costanti;
import it.calverDesktopLAT.utl.Utility;
import net.miginfocom.swing.MigLayout;


public class PannelloMisuraMaster extends JPanel 
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField denominazioneField;
	private JTextField cod_int_field;
	private JTextField construct_field;
	private JTextField model_field;
	private JTextField matricola_field;
	private JTextField freq_field;
	private JTextField res_field;
	private JTextField campo_misura_field;
	private JTextField id_str_field;
	private JTextField proc_field;
	private JTextArea textAreaNote;
	private JButton btnSalva;
	static JTree tree =null;
	JPopupMenu popupMenu,popupMenuMisura;
	JMenuItem jmit,jmitMisura;
	int current;


	JLabel lblTipoStrumento;
	private JTextField id_field;
	private JTextField address_field;
	private JTextField temp_field;
	private JTextField umiditaField;
	private JTextField reparto_field;
	private JTextField utilizzatore_field;

	JPanel panel_header;
	JPanel panel_dati_str;
	JPanel panel_dati_misura;
	JPanel panel_struttura;
	JPanel panel_avvio;
	JPanel panel_stampa;
	JSplitPane splitPane;


	StrumentoDTO strumento;
	private JTextField nCertificatoField;
	private ProvaMisuraDTO misura;
	JComboBox<TipoRapportoDTO> comboBox_tipoRapporto;
	JComboBox<String> comboBox;
	JComboBox<String> comboRicevuto;
	JComboBox<ClassificazioneDTO> comboBox_classificazione;
	JComboBox<TipoStrumentoDTO> comboBox_tipo_strumento;
	JComboBox<LuogoVerificaDTO> comboBox_luogoVerifica;

	Vector<TipoRapportoDTO> vectorTipoRapporto = null;
	Vector<ClassificazioneDTO> vectorClassificazione = null;
	Vector<TipoStrumentoDTO> vectorTipoStrumento = null;
	Vector<LuogoVerificaDTO> vectorLuogoVerifica = null;

	JCheckBox chckbxFs;
	private BufferedImage img;
	private int widthImg;
	private int heightImg;
	static JFrame myFrame=null;

	public PannelloMisuraMaster(String id) throws Exception
	{
		SessionBO.prevPage="PSS";
		current=-1;
		strumento=GestioneStrumentoBO.getStrumento(SessionBO.idStrumento);

		myFrame=SessionBO.generarFrame;
		URL imageURL = GeneralGUI.class.getResource("/image/creaStr.png");
		setImage(ImageIO.read(imageURL));

		this.setLayout(new MigLayout("","[grow]","[grow]"));
		
		
		setBorder(new LineBorder(new Color(255, 255, 255), 3, true));

		JPanel masterPanel = new JPanel();



		masterPanel.setLayout(new MigLayout("", "[70%][20%][10%]", "[12%][73%][15%]"));

		panel_header = costruisciPanelHeader();	
		panel_dati_str = costruisciPanelDatiStr(id,misura);
		panel_struttura =creaPanelStruttura(misura);
		panel_avvio=costruisciPanelAvvio();
		panel_stampa=costruisciPanelStampa();


		masterPanel.add(panel_header,"cell 0 0,grow");
		masterPanel.add(panel_dati_str,"cell 0 1,grow");
		masterPanel.add(panel_struttura,"cell 0 2,grow");
		masterPanel.add(panel_avvio,"cell 1 0 1 3 ,grow");
		masterPanel.add(panel_stampa, "cell 0 2,width : 150:300");


		double height=(SessionBO.heightFrame*73)/100;
		double width=(SessionBO.widthFrame*70)/100;

		masterPanel.setPreferredSize(new Dimension((int)width-50,(int) height/2));

		JScrollPane scroll= new JScrollPane(masterPanel);

		this.add(scroll, "cell 0 0,grow");

	}	
	private JPanel costruisciPanelStampa() {

		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(new LineBorder(Costanti.COLOR_RED, 2, true), "Stampa & Campioni", TitledBorder.LEADING, TitledBorder.TOP, null, Costanti.COLOR_RED));
		panel.setBackground(Color.WHITE);
		add(panel, "cell 0 2,width : 150:300");
		panel.setLayout(new MigLayout("", "[pref!][grow]", "[][grow]"));

		JLabel lblCertificato = new JLabel("Stampa");
		lblCertificato.setFont(new Font("Arial", Font.BOLD, 16));
		panel.add(lblCertificato, "cell 0 0");

		JButton btnStampa = new JButton("Etichetta");
		btnStampa.setIcon(new ImageIcon(PannelloTOP.class.getResource("/image/print.png")));
		btnStampa.setFont(new Font("Arial", Font.BOLD, 16));
		panel.add(btnStampa, "flowx,cell 1 0");
		//	add(splitPane,"cell 2 0 3 5,grow");

		chckbxFs = new JCheckBox("FS");
		chckbxFs.setFont(new Font("Arial", Font.BOLD, 16));
		panel.add(chckbxFs, "cell 1 0");
		chckbxFs.setBackground(Color.WHITE);


		JButton btnVisualizzaCampioni = new JButton("Visualizza Campioni");
		btnVisualizzaCampioni.setIcon(new ImageIcon(PannelloTOP.class.getResource("/image/search_24.png")));
		btnVisualizzaCampioni.setFont(new Font("Arial", Font.BOLD, 16));
		panel.add(btnVisualizzaCampioni, "cell 0 1 2 1,alignx center");
		btnVisualizzaCampioni.setBounds(274, 7, 123, 23);



		btnVisualizzaCampioni.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) 

			{
				SwingUtilities.invokeLater(new Runnable(){
					public void run() 
					{
						try
						{
							JFrame f=new FrameCampioni();
							URL iconURL = getClass().getResource("/image/logo.png");


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
		btnStampa.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				try
				{

					if(SQLiteDAO.getStatoMisura(""+strumento.get__id())==1)
					{

						boolean esito =valutaEsitoMisura();

						int i=GestioneStampaBO.stampaEtichetta(strumento,nCertificatoField,esito,SQLiteDAO.getInfoMisura(""+strumento.get__id()).dataMisura,chckbxFs.isSelected());

						if(i==-1)
						{
							JOptionPane.showMessageDialog(null,"Errore su recupero numero scheda \nContattare l'assiastenza","Errore",JOptionPane.ERROR_MESSAGE,new ImageIcon(PannelloTOP.class.getResource("/image/error.png")));
						}


					}
					else
					{
						JOptionPane.showMessageDialog(null,"Per stampare l'etichetta la misura deve essere terminata ","Attenzione",JOptionPane.WARNING_MESSAGE,new ImageIcon(PannelloTOP.class.getResource("/image/attention.png")));
					}

				}
				catch (Exception e) 
				{
					e.printStackTrace();
				}
			}


		});


		return panel;
	}


	public void setImage(BufferedImage img){
		this.img = img;
		widthImg = img.getWidth();
		heightImg = img.getHeight();
	}

	public void paintComponent(Graphics g){
		super.paintComponent(g);
		// System.out.println(myFrame.getHeight()+myFrame.getWidth());
		this.setBounds(0, 0, myFrame.getWidth()-32, myFrame.getHeight()-272);
		g.drawImage(img, 5, 5, myFrame.getWidth()-32, myFrame.getHeight()-272,this);


	}
	private JScrollPane costruisciPanelChart(ProvaMisuraDTO misura, int typeChart)  {
		JScrollPane panel_chart = new JScrollPane();

		try 
		{
			panel_chart.setBorder(new LineBorder(Costanti.COLOR_RED, 2, true));
			panel_chart.setBackground(Color.WHITE);

			if(typeChart==1)
			{
				DefaultCategoryDataset dataset =getDataSetChart1();// new DefaultCategoryDataset()

				JFreeChart chart = ChartFactory.createBarChart3D(
						"Performance: Incertezza - Accetabilità",null, 
						null, dataset  ,PlotOrientation.VERTICAL,
						true,                     // include legend
						true,                     // tooltips?
						false    );


				chart.addSubtitle(new TextTitle("Strumento: "+strumento.getDenominazione()+ " Codice: "+strumento.getCodice_interno()+" Matricola :"+strumento.getMatricola()));
				chart.setBackgroundPaint(Color.WHITE);

				CategoryPlot plot = (CategoryPlot) chart.getPlot();


				plot.setBackgroundPaint(SystemColor.inactiveCaption);//change background color

				//set  bar chart color

				((BarRenderer)plot.getRenderer()).setBarPainter(new StandardBarPainter());

				BarRenderer r = (BarRenderer)chart.getCategoryPlot().getRenderer();
				r.setSeriesPaint(0, Color.BLUE);
				r.setSeriesPaint(1, Costanti.COLOR_RED);

				final CategoryAxis domainAxis = plot.getDomainAxis();
				domainAxis.setCategoryLabelPositions(CategoryLabelPositions.DOWN_45);
				Font f=domainAxis.getLabelFont();
				final Font titleFont = new Font(f.getFamily(), f.getStyle(),10);
				domainAxis.setTickLabelFont(titleFont);
				NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
				rangeAxis.setStandardTickUnits(NumberAxis.createStandardTickUnits());

				BarRenderer renderer = (BarRenderer) plot.getRenderer();
				renderer.setDrawBarOutline(true);
				chart.getLegend().setFrame(BlockBorder.NONE);
				ChartPanel chartPanel = new ChartPanel(chart, false);

				chartPanel.setMouseWheelEnabled(true);
				chartPanel.setPreferredSize(new Dimension(700, 300));

				panel_chart.setPreferredSize(new Dimension(700,300));
				panel_chart.setViewportView(chartPanel);

			}
			else if(typeChart==2)
			{


				final XYSeriesCollection dataset= getDataSetChart2();
				JFreeChart xylineChart = ChartFactory.createXYLineChart(
						"Deriva Strumento", 
						"POM",
						"Value", 
						dataset,
						PlotOrientation.VERTICAL, 
						true, true, false);

				xylineChart.addSubtitle(new TextTitle("Strumento: "+strumento.getDenominazione()+ " Codice: "+strumento.getCodice_interno()+" Matricola :"+strumento.getMatricola()));
				xylineChart.setBackgroundPaint(Color.WHITE);

				XYPlot plot = xylineChart.getXYPlot();
				XYLineAndShapeRenderer rendu = new XYLineAndShapeRenderer( );

				if(SessionBO.tipoRapporto.equals(Costanti.SVT)) 
				{				
					rendu.setSeriesPaint(0, Costanti.COLOR_RED);
					rendu.setSeriesPaint(1, Costanti.COLOR_RED);
					rendu.setSeriesPaint(2, new Color(0,0,255));
					rendu.setSeriesPaint(3, new Color(0,0,255));
					rendu.setSeriesPaint(4, new Color(255,255,0));

					rendu.setBaseStroke(new BasicStroke(5));
					rendu.setSeriesStroke( 0, new BasicStroke( 3 ) );
					rendu.setSeriesStroke( 1, new BasicStroke( 3 ) );
					rendu.setSeriesStroke( 2, new BasicStroke( 3 ) );
					rendu.setSeriesStroke( 3, new BasicStroke( 3 ) );
					rendu.setSeriesStroke( 4, new BasicStroke(3,BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10.0f, new float[] {10.0f}, 0.0f));
				}

				else 
				{

					rendu.setSeriesPaint(0, new Color(0,0,255));
					rendu.setSeriesPaint(1, new Color(0,0,255));
					rendu.setSeriesPaint(2, new Color(255,255,0));

					rendu.setBaseStroke(new BasicStroke(5));
					rendu.setSeriesStroke( 0, new BasicStroke( 3 ) );
					rendu.setSeriesStroke( 1, new BasicStroke( 3 ) );
					rendu.setSeriesStroke( 2, new BasicStroke(3,BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 10.0f, new float[] {10.0f}, 0.0f));
				}
				plot.setRenderer(rendu);
				xylineChart.getLegend().setFrame(BlockBorder.NONE);
				ChartPanel chartPanel = new ChartPanel(xylineChart, false);

				chartPanel.setMouseWheelEnabled(true);
				chartPanel.setPreferredSize(new Dimension(700, 300));

				panel_chart.setPreferredSize(new Dimension(700,300));
				panel_chart.setViewportView(chartPanel);


			}	

		}catch (Exception e) {
			e.printStackTrace();
			PannelloConsole.printArea("Errore generazione grafico");
		}
		return panel_chart;
	}


	private DefaultCategoryDataset getDataSetChart1() throws Exception {

		DefaultCategoryDataset dataset = new DefaultCategoryDataset();

		ProvaMisuraDTO misura =GestioneMisuraBO.getProvaMisura(SessionBO.idStrumento);

		for (TabellaMisureDTO tabella : misura.getListaTabelle()) {

			ArrayList<MisuraDTO> listaPunti=tabella.getListaMisure();

			if(tabella.getTipoProva().startsWith("L"))
			{
				for (MisuraDTO puntoMisura : listaPunti) 
				{
					if(puntoMisura.getIncertezza()!=null) 
					{
						dataset.addValue(puntoMisura.getIncertezza().round(new MathContext(2, RoundingMode.HALF_UP)), "Incertezza", puntoMisura.getTipoVerifica());

						if(SessionBO.tipoRapporto.equals(Costanti.SVT))
						{
							dataset.addValue(puntoMisura.getAccettabilita(), "Accettabilità", puntoMisura.getTipoVerifica());
						}
					}	
				}
			}
			else 
			{
				for (MisuraDTO puntoMisura : listaPunti) 
				{
					if(puntoMisura.getIncertezza()!=null) 
					{
						dataset.addValue(puntoMisura.getIncertezza().round(new MathContext(2, RoundingMode.HALF_UP)), "Incertezza", puntoMisura.getTipoVerifica());

						if(SessionBO.tipoRapporto.equals(Costanti.SVT))
						{
							dataset.addValue(puntoMisura.getAccettabilita(), "Accettabilità", puntoMisura.getTipoVerifica());
						}
					}
				}

			}
		}


		return dataset;
	}

	private XYSeriesCollection getDataSetChart2() throws Exception {

		XYSeriesCollection series = new XYSeriesCollection();

		final XYSeries accPos = new XYSeries( "Accettabilità +" );
		final XYSeries accNeg = new XYSeries( "Accettabilità -" );

		final XYSeries misura_inc_pos = new XYSeries( "Punto + U" );
		final XYSeries misura_inc_neg = new XYSeries( "Punto - U" );

		final XYSeries misuraPnt = new XYSeries( "Punto" );

		ProvaMisuraDTO misura =GestioneMisuraBO.getProvaMisura(SessionBO.idStrumento);

		for (TabellaMisureDTO tabella : misura.getListaTabelle()) {

			ArrayList<MisuraDTO> listaPunti=tabella.getListaMisure();

			for (int i=0 ;i<listaPunti.size();i++)
			{
				MisuraDTO puntoMisura=listaPunti.get(i);

				if(puntoMisura.getIncertezza()!=null) 
				{
					BigDecimal accettabilita = puntoMisura.getAccettabilita();
					BigDecimal incertezza = puntoMisura.getIncertezza();
					BigDecimal punto = puntoMisura.getValoreStrumento();


					if(accettabilita.compareTo(BigDecimal.ZERO)>0) 
					{
						accPos.add(i+1,accettabilita.doubleValue()+punto.doubleValue());
						accNeg.add(i+1,(accettabilita.doubleValue()*-1)+punto.doubleValue());
					}
					else 
					{
						accPos.add(i+1,(accettabilita.doubleValue()*-1)+punto.doubleValue());
						accNeg.add(i+1,accettabilita.doubleValue()+punto.doubleValue());
					}

					misura_inc_pos.add(i+1,incertezza.doubleValue()+punto.doubleValue());
					misura_inc_neg.add(i+1,(incertezza.doubleValue()*-1)+punto.doubleValue());

					misuraPnt.add(i+1,punto.doubleValue());


				}

			}



		}
		if(SessionBO.tipoRapporto.equals(Costanti.SVT))
		{
			series.addSeries(accPos);
			series.addSeries(accNeg);
		}
		series.addSeries(misura_inc_pos);
		series.addSeries(misura_inc_neg);
		series.addSeries(misuraPnt); 
		return series;
	}



	private JPanel costruisciPanelHeader() {
		JPanel panel_header = new JPanel();
		panel_header.setLayout(new MigLayout("", "[]15[pref!,grow][10][][grow]", "[top][grow][]"));
		panel_header.setBorder(new TitledBorder(new LineBorder(Costanti.COLOR_RED, 2, true), "Dati Clienti e Certificato", TitledBorder.LEADING, TitledBorder.TOP, null, Costanti.COLOR_RED));
		panel_header.setBackground(Color.WHITE);
		panel_header.setBounds(10, 10, 540, 65);

		JLabel lblID = new JLabel("ID");
		lblID.setFont(new Font("Arial", Font.BOLD, 16));
		lblID.setHorizontalAlignment(SwingConstants.RIGHT);
		panel_header.add(lblID, "cell 0 0,alignx right");

		id_field = new JTextField();
		id_field.setFont(new Font("Arial", Font.PLAIN, 16));
		id_field.setEditable(false);
		id_field.setText("0");
		id_field.setColumns(10);
		panel_header.add(id_field,"cell 1 0,width 50:70:,aligny top");

		JLabel lblIndirizzo = new JLabel("Indirizzo");
		lblIndirizzo.setFont(new Font("Arial", Font.BOLD, 16));
		panel_header.add(lblIndirizzo, "cell 3 0");

		address_field = new JTextField();
		address_field.setFont(new Font("Arial", Font.PLAIN, 16));
		address_field.setEditable(false);
		address_field.setText((String) null);
		address_field.setColumns(10);
		panel_header.add(address_field,"cell 4 0,growx,width 200:300:,aligny top");

		JLabel lblNCertificato = new JLabel("# Certificato");
		lblNCertificato.setFont(new Font("Arial", Font.BOLD, 16));
		panel_header.add(lblNCertificato, "cell 0 1,alignx right,aligny top");

		nCertificatoField = new JTextField();
		nCertificatoField.setForeground(new Color(0, 128, 0));
		nCertificatoField.setFont(new Font("Arial", Font.BOLD, 16));
		panel_header.add(nCertificatoField, "cell 1 1,aligny top");
		nCertificatoField.setEditable(false);
		nCertificatoField.setColumns(10);
		nCertificatoField.setText(strumento.getnCertificato());

		JButton btnGrafico = new JButton("Grafico Incertezza");
		btnGrafico.setFont(new Font("Arial", Font.BOLD, 14));
		btnGrafico.setIcon(new ImageIcon(PannelloTOP.class.getResource("/image/graf1.png")));
		btnGrafico.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				SwingUtilities.invokeLater(new Runnable(){
					public void run() 
					{
						try
						{
							JFrame f=new JFrame();
							f.setSize(800,400);
							f.setTitle("Grafico");
							Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
							int x = (dim.width-800) / 2;
							int y = (dim.height-400) / 2;
							f.setLocation(x, y);

							f.getContentPane().add(costruisciPanelChart(misura,1));

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
		panel_header.add(btnGrafico, "flowx,cell 4 1,aligny top");

		JButton btnGrafico_1 = new JButton("Deriva Strumento");
		btnGrafico_1.setIcon(new ImageIcon(PannelloTOP.class.getResource("/image/graf2.png")));
		btnGrafico_1.setFont(new Font("Arial", Font.BOLD, 14));
		btnGrafico_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				SwingUtilities.invokeLater(new Runnable(){
					public void run() 
					{
						try
						{
							JFrame f=new JFrame();
							f.setSize(800,400);
							f.setTitle("Grafico 1");
							Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
							int x = (dim.width-800) / 2;
							int y = (dim.height-400) / 2;
							f.setLocation(x, y);

							f.getContentPane().add(costruisciPanelChart(misura,2));

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
		panel_header.add(btnGrafico_1, "cell 4 1,aligny top");


		vectorTipoRapporto=SQLiteDAO.getVectorTipoRapporto();

		id_str_field = new JTextField();
		id_str_field.setEditable(false);
		id_str_field.setColumns(10);

		vectorLuogoVerifica=SQLiteDAO.getVectorLuogoVerifica();
		//	panel_header.add(id_str_field,"cell 4 1,width 50:100:");

		return panel_header;

	}

	private JPanel costruisciPanelDatiStr(String id, ProvaMisuraDTO misura) {
		JPanel panel_dati_str= new JPanel();
		panel_dati_str.setBorder(new TitledBorder(new LineBorder(Costanti.COLOR_RED, 2, true), "Dati Strumento", TitledBorder.LEADING, TitledBorder.TOP, null, Costanti.COLOR_RED));
		panel_dati_str.setBackground(Color.white);
		panel_dati_str.setBounds(10, 85, 540, 250);

		panel_dati_str.setLayout(new MigLayout("", "[]15[grow][10][][]", "[][][][][][][][][][][]"));

		JLabel lblNewLabel = new JLabel("Denominazione");
		lblNewLabel.setFont(new Font("Arial", Font.BOLD, 14));
		//	lblNewLabel.setBounds(5, 10, 78, 14);
		panel_dati_str.add(lblNewLabel, "cell 0 0,aligny baseline");

		vectorClassificazione=SQLiteDAO.getVectorClassificazione();

		vectorTipoStrumento=SQLiteDAO.getVectorTipoStrumento();

		denominazioneField = new JTextField();
		denominazioneField.setFont(new Font("Arial", Font.PLAIN, 16));
		denominazioneField.setEditable(false);
		//	denominazioneField.setBounds(85, 9, 180, 17);
		panel_dati_str.add(denominazioneField,"cell 1 0,growx,width 100:180:");
		denominazioneField.setColumns(10);

		JLabel lblFreqTar = new JLabel("Frequenza");
		lblFreqTar.setFont(new Font("Arial", Font.BOLD, 14));
		//	lblFreqTar.setBounds(275, 110, 57, 14);
		panel_dati_str.add(lblFreqTar, "cell 3 0");

		freq_field = new JTextField();
		freq_field.setFont(new Font("Arial", Font.PLAIN, 16));
		freq_field.setEditable(false);
		freq_field.setColumns(10);
		//	freq_field.setBounds(349, 109, 180, 17);
		panel_dati_str.add(freq_field,"cell 4 0,width :50:");
		freq_field.setText(""+strumento.getFreq_taratura());

		JLabel lblCodiceInterno = new JLabel("Codice interno");
		lblCodiceInterno.setFont(new Font("Arial", Font.BOLD, 14));
		//	lblCodiceInterno.setBounds(5, 35, 71, 14);
		panel_dati_str.add(lblCodiceInterno, "cell 0 1");

		cod_int_field = new JTextField();
		cod_int_field.setDocument(new JTextFieldLimit(22));
		cod_int_field.setFont(new Font("Arial", Font.PLAIN, 16));
		cod_int_field.setEditable(false);
		cod_int_field.setColumns(10);
		//	cod_int_field.setBounds(85, 34, 180, 17);
		panel_dati_str.add(cod_int_field,"cell 1 1,width 100:180:250,grow");

		int indiceTipoStrumento=Utility.getIndicaTipoStrumento(vectorTipoStrumento,strumento.getId_tipoStrumento());
		int indiceTipoRapporto=Utility.getIndicaTipoRapporto(vectorTipoRapporto,strumento.getTipoRapporto());
		int indiceClass=Utility.getIndicaClassificazione(vectorClassificazione,strumento.getClassificazione());
		int indiceLuogoVerifica=Utility.getIndicaLuogoVerifica(vectorLuogoVerifica,strumento.getLuogoVerifica());

		JLabel cam_mis_field = new JLabel("Campo Misura");
		cam_mis_field.setFont(new Font("Arial", Font.BOLD, 14));
		//	cam_mis_field.setBounds(274, 35, 67, 14);
		panel_dati_str.add(cam_mis_field, "cell 3 1");

		campo_misura_field = new JTextField();
		campo_misura_field.setFont(new Font("Arial", Font.PLAIN, 16));
		campo_misura_field.setEditable(false);
		campo_misura_field.setColumns(10);
		//	campo_misura_Field.setBounds(349, 34, 180, 17);
		panel_dati_str.add(campo_misura_field,"cell 4 1,width 100:180:");
		campo_misura_field.setText(strumento.getCampo_misura());

		JLabel lblMatricola = new JLabel("Matricola");
		lblMatricola.setFont(new Font("Arial", Font.BOLD, 14));
		lblMatricola.setHorizontalAlignment(SwingConstants.RIGHT);
		//	lblMatricola.setBounds(275, 10, 43, 14);
		panel_dati_str.add(lblMatricola, "cell 0 2");

		matricola_field = new JTextField();
		matricola_field.setDocument(new JTextFieldLimit(22));
		matricola_field.setFont(new Font("Arial", Font.PLAIN, 16));
		matricola_field.setEditable(false);
		matricola_field.setColumns(10);
		//	matricola_field.setBounds(349, 9, 180, 17);
		panel_dati_str.add(matricola_field,"cell 1 2,width 100:180:250,grow");
		matricola_field.setText(strumento.getMatricola());

		JLabel lblRisoluz = new JLabel("Risoluz.");
		lblRisoluz.setFont(new Font("Arial", Font.BOLD, 14));
		//	lblRisoluz.setBounds(275, 60, 43, 14);
		panel_dati_str.add(lblRisoluz, "cell 3 2");

		res_field = new JTextField();
		res_field.setFont(new Font("Arial", Font.PLAIN, 16));
		res_field.setEditable(false);
		res_field.setColumns(10);
		//	res_field.setBounds(349, 59, 180, 17);
		panel_dati_str.add(res_field,"cell 4 2,width 100:180:200");
		res_field.setText(strumento.getRisoluzione());

		JLabel lblCostruttore = new JLabel("Costruttore");
		lblCostruttore.setFont(new Font("Arial", Font.BOLD, 14));
		//	lblCostruttore.setBounds(5, 60, 71, 14);
		panel_dati_str.add(lblCostruttore, "cell 0 3");

		construct_field = new JTextField();
		construct_field.setFont(new Font("Arial", Font.PLAIN, 16));
		construct_field.setEditable(false);
		construct_field.setColumns(10);
		//	construct_field.setBounds(85, 59, 180, 17);
		panel_dati_str.add(construct_field,"cell 1 3,width 100:180:,grow");
		construct_field.setText(strumento.getCostruttore());

		JLabel lblCalss = new JLabel("Classificazione");
		lblCalss.setFont(new Font("Arial", Font.BOLD, 14));
		//	lblCalss.setBounds(275, 85, 69, 14);
		panel_dati_str.add(lblCalss, "cell 3 3");

		comboBox_classificazione = new JComboBox(vectorClassificazione);
		comboBox_classificazione.setFont(new Font("Arial", Font.PLAIN, 16));
		comboBox_classificazione.setRenderer( new ItemRendererClass());
		comboBox_classificazione.setEnabled(false);
		panel_dati_str.add(comboBox_classificazione, "flowx,cell 4 3,width :100:");
		comboBox_classificazione.setSelectedIndex(indiceClass);

		JLabel lblModello = new JLabel("Modello");
		lblModello.setFont(new Font("Arial", Font.BOLD, 14));
		//	lblModello.setBounds(5, 85, 36, 14);
		panel_dati_str.add(lblModello, "cell 0 4");

		model_field = new JTextField();
		model_field.setFont(new Font("Arial", Font.PLAIN, 16));
		model_field.setEditable(false);
		//	model_field.setBounds(85, 84, 180, 17);
		panel_dati_str.add(model_field,"cell 1 4,width 100:180:,grow");
		model_field.setColumns(10);
		model_field.setText(strumento.getModello());

		JLabel lblTipoRapporto = new JLabel("Tipo Rapporto");
		lblTipoRapporto.setFont(new Font("Arial", Font.BOLD, 14));
		panel_dati_str.add(lblTipoRapporto, "cell 3 4");

		comboBox_tipoRapporto = new JComboBox(vectorTipoRapporto);
		comboBox_tipoRapporto.setFont(new Font("Arial", Font.PLAIN, 16));
		panel_dati_str.add(comboBox_tipoRapporto, "flowx,cell 4 4,width :100:");
		comboBox_tipoRapporto.setRenderer( new ItemRendererTipoRapporto());
		comboBox_tipoRapporto.setEnabled(false);
		comboBox_tipoRapporto.setSelectedIndex(indiceTipoRapporto);


		JLabel lblReparto = new JLabel("Reparto");
		lblReparto.setFont(new Font("Arial", Font.BOLD, 14));
		lblReparto.setBounds(5, 110, 46, 14);
		panel_dati_str.add(lblReparto, "cell 0 5");



		reparto_field = new JTextField();
		reparto_field.setFont(new Font("Arial", Font.PLAIN, 16));
		reparto_field.setEditable(false);
		reparto_field.setText((String) null);
		reparto_field.setColumns(10);
		//	reparto_field.setBounds(85, 107, 180, 17);
		panel_dati_str.add(reparto_field,"cell 1 5,width 100:180:,grow");
		reparto_field.setText(strumento.getReparto());

		try 
		{
			denominazioneField.setText(strumento.getDenominazione());
			cod_int_field.setText(strumento.getCodice_interno());
			address_field.setText(strumento.getIndirizzo());
			id_field.setText(""+strumento.get__id());
			id_str_field.setText(strumento.getId_tipoStrumento());




			JLabel lblIdts = new JLabel("Luogo Verifica");
			lblIdts.setFont(new Font("Arial", Font.BOLD, 14));
			panel_dati_str.add(lblIdts, "cell 3 5");

			comboBox_luogoVerifica = new JComboBox(vectorLuogoVerifica);
			comboBox_luogoVerifica.setFont(new Font("Arial", Font.PLAIN, 16));
			panel_dati_str.add(comboBox_luogoVerifica, "cell 4 5,width :100:");
			comboBox_luogoVerifica.setRenderer( new ItemRendererLuogoVerificao());
			comboBox_luogoVerifica.setEnabled(false);
			comboBox_luogoVerifica.setSelectedIndex(indiceLuogoVerifica);

			JLabel lblUtilizzatore = new JLabel("Utilizzatore");
			lblUtilizzatore.setFont(new Font("Arial", Font.BOLD, 14));
			//	lblUtilizzatore.setBounds(5, 135, 53, 14);
			panel_dati_str.add(lblUtilizzatore, "cell 0 6");



			utilizzatore_field = new JTextField();
			utilizzatore_field.setFont(new Font("Arial", Font.PLAIN, 16));
			utilizzatore_field.setEditable(false);
			utilizzatore_field.setText((String) null);
			utilizzatore_field.setColumns(10);
			//	utilizzatore_field.setBounds(85, 132, 180, 17);
			panel_dati_str.add(utilizzatore_field,"cell 1 6,width 100:180:,grow");
			utilizzatore_field.setText(strumento.getUtilizzatore());







			lblTipoStrumento = new JLabel("Tipo Strumento");
			lblTipoStrumento.setFont(new Font("Arial", Font.BOLD, 14));
			panel_dati_str.add(lblTipoStrumento, "cell 3 6");
			lblTipoStrumento.setVisible(false);

			comboBox_tipo_strumento = new JComboBox<TipoStrumentoDTO>(vectorTipoStrumento);
			comboBox_tipo_strumento.setFont(new Font("Arial", Font.PLAIN, 16));
			comboBox_tipo_strumento.setRenderer( new ItemRendererTipoStrumento());
			panel_dati_str.add(comboBox_tipo_strumento, "cell 4 6,width 100:180:200");

			comboBox_tipo_strumento.setSelectedIndex(indiceTipoStrumento);
			comboBox_tipo_strumento.setVisible(false);

			JLabel lblProcedura = new JLabel("Procedura");
			lblProcedura.setFont(new Font("Arial", Font.BOLD, 14));
			panel_dati_str.add(lblProcedura, "cell 0 7");
			lblProcedura.setBounds(10, 11, 54, 14);




			proc_field = new JTextField();
			proc_field.setFont(new Font("Arial", Font.PLAIN, 16));
			panel_dati_str.add(proc_field, "cell 1 7,grow");
			proc_field.setEditable(false);
			proc_field.setText((String) null);
			proc_field.setColumns(10);
			proc_field.setBounds(85, 12, 75, 17);
			proc_field.setText(strumento.getProcedura());

			JLabel lblNote = new JLabel("Note:");
			lblNote.setFont(new Font("Arial", Font.BOLD, 14));
			//		lblNote.setBounds(5, 160, 36, 14);
			panel_dati_str.add(lblNote, "cell 0 8");

			JScrollPane scrollPane = new JScrollPane();
			//		scrollPane.setBounds(85, 160, 444, 79);
			panel_dati_str.add(scrollPane,"cell 1 8 4 1,growx,height :100:100");

			textAreaNote = new JTextArea();
			textAreaNote.setFont(new Font("Arial", Font.PLAIN, 16));
			textAreaNote.setLineWrap(true);
			textAreaNote.setEditable(false);
			scrollPane.setViewportView(textAreaNote);
			textAreaNote.setText(strumento.getNote());







			JButton btnModifica = new JButton("Modifica");
			btnModifica.setIcon(new ImageIcon(PannelloTOP.class.getResource("/image/update.png")));
			btnModifica.setFont(new Font("Arial", Font.BOLD, 16));
			btnModifica.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {

					comboBox_tipoRapporto.setEnabled(true);
					denominazioneField.setEditable(true);
					cod_int_field.setEditable(true);
					construct_field.setEditable(true);
					model_field.setEditable(true);
					reparto_field.setEditable(true);
					utilizzatore_field.setEditable(true);
					matricola_field.setEditable(true);
					campo_misura_field.setEditable(true);
					res_field.setEditable(true);
					comboBox_classificazione.setEnabled(true);
					freq_field.setEditable(true);

					proc_field.setEditable(true);

					textAreaNote.setEditable(true);

					lblTipoStrumento.setVisible(true);
					comboBox_tipo_strumento.setVisible(true);
					comboBox_luogoVerifica.setEnabled(true);
					btnSalva.setVisible(true);

				}
			});
			btnSalva = new JButton("Salva");
			btnSalva.setIcon(new ImageIcon(PannelloTOP.class.getResource("/image/save.png")));
			btnSalva.setFont(new Font("Arial", Font.BOLD, 16));


			btnSalva.setVisible(false);
			panel_dati_str.add(btnSalva, "flowx,cell 0 9 5 1,alignx center");

			btnSalva.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent arg0) 
				{

					int scelta=	JOptionPane.showConfirmDialog(null,"Vuoi modificare i dati dello strumento ?","Salvataggio",JOptionPane.YES_NO_OPTION,0,new ImageIcon(PannelloTOP.class.getResource("/image/question.png")));	

					if(scelta==0)
					{		


						try
						{



							int freq =Integer.parseInt(freq_field.getText());
							comboBox_tipoRapporto.setEnabled(false);
							denominazioneField.setEditable(false);
							cod_int_field.setEditable(false);
							construct_field.setEditable(false);
							model_field.setEditable(false);
							reparto_field.setEditable(false);
							utilizzatore_field.setEditable(false);
							matricola_field.setEditable(false);
							campo_misura_field.setEditable(false);
							res_field.setEditable(false);
							comboBox_classificazione.setEnabled(false);
							freq_field.setEditable(false);

							textAreaNote.setEditable(false);
							btnSalva.setVisible(false);
							lblTipoStrumento.setVisible(false);
							comboBox_tipo_strumento.setVisible(false);
							comboBox_luogoVerifica.setEnabled(false);
							proc_field.setEditable(false);

							TipoRapportoDTO tipoRpp=(TipoRapportoDTO)comboBox_tipoRapporto.getSelectedItem();
							ClassificazioneDTO clas=(ClassificazioneDTO)comboBox_classificazione.getSelectedItem();
							LuogoVerificaDTO luogo =(LuogoVerificaDTO)comboBox_luogoVerifica.getSelectedItem();

							strumento.setIdTipoRappoto(tipoRpp.getId());
							SessionBO.tipoRapporto=tipoRpp.getDescrizione();
							strumento.setTipoRapporto(tipoRpp.getDescrizione());
							strumento.setDenominazione(denominazioneField.getText());
							strumento.setCodice_interno(cod_int_field.getText());
							strumento.setCostruttore(construct_field.getText());
							strumento.setModello(model_field.getText());
							strumento.setReparto(reparto_field.getText());
							strumento.setUtilizzatore(utilizzatore_field.getText());
							strumento.setMatricola(matricola_field.getText());
							strumento.setCampo_misura(campo_misura_field.getText());
							strumento.setRisoluzione(res_field.getText());
							strumento.setIdClassificazione(clas.getId());
							strumento.setLuogoVerifica(luogo.getId());
							strumento.setFreq_taratura(freq);
							strumento.setProcedura(proc_field.getText());
							strumento.setNote(textAreaNote.getText());
							TipoStrumentoDTO item = (TipoStrumentoDTO)comboBox_tipo_strumento.getSelectedItem();
							strumento.setId_tipoStrumento(""+item.getId());

							id_str_field.setText(""+item.getId());

							int toReturn=GestioneStrumentoBO.updateStrumento(strumento);

							if(toReturn==1)
							{
								JOptionPane.showMessageDialog(null,"Salvataggio effettuato","Salvataggio",JOptionPane.INFORMATION_MESSAGE,new ImageIcon(PannelloTOP.class.getResource("/image/confirm.png")));	
							}

						}
						catch (NumberFormatException nfe) 
						{
							JOptionPane.showMessageDialog(null,"La frequenza può essere solo numerica","Errore",JOptionPane.ERROR_MESSAGE,new ImageIcon(PannelloTOP.class.getResource("/image/error.png")));
						}
						catch (Exception e) 
						{
							PannelloConsole.printException(e);
							e.printStackTrace();
						}

					}	

				}
			});
			panel_dati_str.add(btnModifica, "flowx,cell 0 9 5 1,alignx center");

			SessionBO.tipoRapporto=strumento.getTipoRapporto();	

		} catch (Exception e) 
		{
			PannelloConsole.printException(e);
			e.printStackTrace();
		}

		return panel_dati_str;
	}

	private JPanel creaPanelStruttura( ProvaMisuraDTO misura) {

		final JPanel panel_m_build = new JPanel();
		panel_m_build.setBorder(new TitledBorder(new LineBorder(Costanti.COLOR_RED, 2, true), "Termina Misura", TitledBorder.LEADING, TitledBorder.TOP, null, Costanti.COLOR_RED));
		panel_m_build.setBackground(Color.white);
		panel_m_build.setLayout(new MigLayout("", "[]15[]15[][][][grow]", "[][]"));

		JLabel lblTemperatura = new JLabel("Temperatura");
		lblTemperatura.setFont(new Font("Arial", Font.BOLD, 14));
		panel_m_build.add(lblTemperatura, "cell 0 0");
		lblTemperatura.setBounds(10, 36, 62, 14);



		temp_field = new JTextField("0.0");
		temp_field.setFont(new Font("Arial", Font.PLAIN, 16));
		panel_m_build.add(temp_field, "cell 1 0,wmax 60");
		temp_field.setColumns(10);
		temp_field.setBounds(85, 37, 75, 17);

		JLabel lblRicevuto = new JLabel("Ricevuto");
		lblRicevuto.setFont(new Font("Arial", Font.BOLD, 14));
		panel_m_build.add(lblRicevuto, "cell 3 0");

		comboRicevuto = new JComboBox(Costanti.TIPO_SATORICEZIONE);
		comboRicevuto.setFont(new Font("Arial", Font.PLAIN, 16));
		panel_m_build.add(comboRicevuto, "cell 4 0,growx");


		JLabel lblUmidit = new JLabel("Umidit\u00E0");
		lblUmidit.setFont(new Font("Arial", Font.BOLD, 14));
		panel_m_build.add(lblUmidit, "cell 0 1");
		lblUmidit.setBounds(277, 39, 35, 14);

		umiditaField = new JTextField("0.0");
		umiditaField.setFont(new Font("Arial", Font.PLAIN, 16));
		panel_m_build.add(umiditaField, "cell 1 1,wmax 60");
		umiditaField.setBounds(322, 36, 75, 17);
		umiditaField.setColumns(10);

		JLabel lblFirme = new JLabel("Firme");
		lblFirme.setFont(new Font("Arial", Font.BOLD, 14));
		panel_m_build.add(lblFirme, "cell 3 1");

		final JComboBox comboBox_firme = new JComboBox(Costanti.LISTA_FIRME);
		comboBox_firme.setFont(new Font("Arial", Font.PLAIN, 16));
		panel_m_build.add(comboBox_firme, "cell 4 1,growx");

		final JButton btnChiudiMisura = new JButton("Termina Misura");
		btnChiudiMisura.setIcon(new ImageIcon(PannelloTOP.class.getResource("/image/insert.png")));
		btnChiudiMisura.setFont(new Font("Arial", Font.BOLD, 16));
		panel_m_build.add(btnChiudiMisura, "cell 5 0 1 2,gapx 30,grow");

		btnChiudiMisura.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) 

			{

				int scelta=	JOptionPane.showConfirmDialog(null,"Vuoi terminare la misura ?","Salvataggio",JOptionPane.YES_NO_OPTION,0,new ImageIcon(PannelloTOP.class.getResource("/image/question.png")));

				if(scelta==0)
				{
					String temperatura=temp_field.getText();
					String umidita=umiditaField.getText();

					int sr=0;
					int firma=comboBox_firme.getSelectedIndex();
					if(comboRicevuto.getSelectedIndex()==0)
					{
						sr=8901;
					}
					else if(comboRicevuto.getSelectedIndex()==1)
					{
						sr=8900;
					}
					else if(comboRicevuto.getSelectedIndex()==2)
					{
						sr=8902;
					}


					try {

						if(SQLiteDAO.getStatoMisura(SessionBO.idStrumento)==1)
						{
							btnChiudiMisura.setEnabled(false);
						}

						ProvaMisuraDTO lista =GestioneMisuraBO.getProvaMisura(SessionBO.idStrumento);
						boolean check=false;

						if(SessionBO.tipoRapporto.equals("RDP") && lista.getListaTabelle().size()>0)
						{
							check=true;
						}
						else
						{
							check=Utility.contollaPunti(lista.getListaTabelle());
						}


						if(check==true)
						{
							BigDecimal temp=new BigDecimal(temperatura);	
							BigDecimal umd=new BigDecimal(umidita);	
							GestioneMisuraBO.terminaMisura(SessionBO.idStrumento,temp,umd,sr,firma);
							JOptionPane.showMessageDialog(null,"Salvataggio effettuato","Salvataggio",JOptionPane.INFORMATION_MESSAGE,new ImageIcon(PannelloTOP.class.getResource("/image/confirm.png")));



						}else
						{
							JOptionPane.showMessageDialog(null,"Per terminare la misura, tutti i punti devono essere valorizzati","Salvataggio",JOptionPane.ERROR_MESSAGE,new ImageIcon(PannelloTOP.class.getResource("/image/attention.png")));
						}
					} catch (NumberFormatException e) {
						JOptionPane.showMessageDialog(null,"I campi temperatura e umidità accettano solo valori numerici","Salvataggio",JOptionPane.ERROR_MESSAGE,new ImageIcon(PannelloTOP.class.getResource("/image/error.png")));
					}
					catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		});






		return panel_m_build;

	}

	private JPanel costruisciPanelAvvio() {
		JPanel panel_avvio = new JPanel();
		panel_avvio.setLayout(new MigLayout("", "[grow]", "[grow]"));
		panel_avvio.setBorder(new TitledBorder(new LineBorder(Costanti.COLOR_RED, 2, true), "Avvia", TitledBorder.LEADING, TitledBorder.TOP, null, Costanti.COLOR_RED));
		panel_avvio.setBackground(Color.white);

		JButton avvia = new JButton("Avvia Misura");
		avvia.setFont(new Font("Arial", Font.BOLD, 16));
		avvia.setIcon(new ImageIcon(PannelloTOP.class.getResource("/image/avvia.png")));
		panel_avvio.add(avvia,"cell 0 0,width 80:200:250,alignx center,growy,height 40:60:100");




		avvia.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {


				try {

					if(SessionBO.indexTableLAT==1) 
					{
						JPanel panelDB =new PannelloLivellaBolla();
						SystemGUI.callPanel(panelDB, "PMT");
					}

				}
				catch (Exception e) 
				{
					e.printStackTrace();
				}


			}
		});

		return panel_avvio;
	}


	public static void disegnaAlbero(ProvaMisuraDTO misura) {


		DefaultTreeModel model = (DefaultTreeModel)tree.getModel();

		DefaultMutableTreeNode root = (DefaultMutableTreeNode)model.getRoot();

		for (int tab = 0; tab < misura.getListaTabelle().size(); tab++) {

			TabellaMisureDTO tabella =misura.getListaTabelle().get(tab);


			DefaultMutableTreeNode  top = new DefaultMutableTreeNode(tabella);

			for (int i = 0; i <tabella.getListaMisure().size(); i++) {

				MisuraDTO misuraTab=tabella.getListaMisure().get(i);

				DefaultMutableTreeNode tmp = new DefaultMutableTreeNode(misuraTab.getTipoVerifica());

				top.add(tmp);
			}

			root.add(top);
		}




		model.reload(root);

	}


	public void valueChanged(TreeSelectionEvent e) 
	{

	}

	private boolean valutaEsitoMisura() throws Exception {


		boolean esito=true;

		try
		{	
			ProvaMisuraDTO misura =GestioneMisuraBO.getProvaMisura(SessionBO.idStrumento);
			ArrayList<TabellaMisureDTO> listaTabelle= misura.getListaTabelle();

			for (int i = 0; i < listaTabelle.size(); i++) {

				TabellaMisureDTO tabella=listaTabelle.get(i);

				for (int j = 0; j < tabella.getListaMisure().size(); j++) {

					MisuraDTO m=tabella.getListaMisure().get(j);

					if(!m.getEsito().equals("IDONEO"))
					{
						return false;
					}
				}
			}
		}catch (Exception e) {
			throw e;
		}
		return esito;
	}

	class ItemRendererTipoRapporto extends BasicComboBoxRenderer
	{
		public Component getListCellRendererComponent(
				JList list, Object value, int index,
				boolean isSelected, boolean cellHasFocus)
		{
			super.getListCellRendererComponent(list, value, index,
					isSelected, cellHasFocus);

			if (value != null)
			{
				TipoRapportoDTO item = (TipoRapportoDTO)value;
				setText( item.getDescrizione() );
			}
			return this;
		}
	}
	class ItemRendererLuogoVerificao extends BasicComboBoxRenderer
	{
		public Component getListCellRendererComponent(
				JList list, Object value, int index,
				boolean isSelected, boolean cellHasFocus)
		{
			super.getListCellRendererComponent(list, value, index,
					isSelected, cellHasFocus);

			if (value != null)
			{
				LuogoVerificaDTO item = (LuogoVerificaDTO)value;
				setText( item.getDescrizione() );
			}
			return this;
		}
	}
	class ItemRendererClass extends BasicComboBoxRenderer
	{
		public Component getListCellRendererComponent(
				JList list, Object value, int index,
				boolean isSelected, boolean cellHasFocus)
		{
			super.getListCellRendererComponent(list, value, index,
					isSelected, cellHasFocus);

			if (value != null)
			{
				ClassificazioneDTO item = (ClassificazioneDTO)value;
				setText( item.getDescrizione() );
			}
			return this;
		}
	}
	class ItemRendererTipoStrumento extends BasicComboBoxRenderer
	{
		public Component getListCellRendererComponent(
				JList list, Object value, int index,
				boolean isSelected, boolean cellHasFocus)
		{
			super.getListCellRendererComponent(list, value, index,
					isSelected, cellHasFocus);

			if (value != null)
			{
				TipoStrumentoDTO item = (TipoStrumentoDTO)value;
				setText( item.getDescrizione() );
			}
			return this;
		}
	}
}
