package it.calverDesktopLAT.gui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.math.BigDecimal;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.block.BlockBorder;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import it.calverDesktopLAT.bo.GestioneMisuraBO;
import it.calverDesktopLAT.bo.GestioneStrumentoBO;
import it.calverDesktopLAT.bo.SessionBO;
import it.calverDesktopLAT.dto.MisuraDTO;
import it.calverDesktopLAT.dto.ProvaMisuraDTO;
import it.calverDesktopLAT.dto.StrumentoDTO;
import it.calverDesktopLAT.dto.TabellaMisureDTO;
import it.calverDesktopLAT.utl.Costanti;
import it.calverDesktopLAT.utl.Utility;
import net.miginfocom.swing.MigLayout;
import javax.swing.ScrollPaneConstants;

public class PannelloMisuraTabelle extends JPanel implements TableModelListener{
	
	JScrollPane panel;
	
	private JTable table;
	ArrayList<JTable> listaJTable=null;
	JPanel pannelloTabelle;
	ProvaMisuraDTO lista=null;
	HashMap<Integer, JTable> listaTabelleOrdinate = new HashMap<>();
	
	private BufferedImage img;
	private int widthImg;
	private int heightImg;
	static JFrame myFrame=null;
	
	public PannelloMisuraTabelle()
	{
		SessionBO.prevPage="PMM";
		
	
		setBorder(new LineBorder(new Color(255, 255, 255), 3, true));
		
		
		
		setLayout(new MigLayout());

		panel = new JScrollPane();
		panel.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		panel.setPreferredSize(new Dimension(1600,  GeneralGUI.center.getHeight()+10));
		this.add(panel);
		
		try {
			costruisciPannello();
		} catch (Exception e) {
			PannelloConsole.printException(e);
			e.printStackTrace();
		}
		
	}

	private void costruisciPannello() throws Exception {
		
		
		
		lista =GestioneMisuraBO.getProvaMisura(SessionBO.idStrumento);
		
		pannelloTabelle = new JPanel();
		
		myFrame=SessionBO.generarFrame;
		URL imageURL = GeneralGUI.class.getResource("/image/creaStr.png");
		setImage(ImageIO.read(imageURL));
		
		pannelloTabelle.setLayout(null);
		int height=lista.getListaTabelle().size()*265;
		pannelloTabelle.setPreferredSize(new Dimension(1580, height));
		JLabel lblNewLabel = new JLabel("Lista Tabelle Misurazione");
		lblNewLabel.setFont(new Font("Arial", Font.BOLD, 16));
		lblNewLabel.setBounds(10, 10, 250, 20);
		
		pannelloTabelle.add(lblNewLabel);
		
		listaJTable= new ArrayList<>();
		
		int yAxis=	35;
		
		for (int i = 0; i < lista.getListaTabelle().size(); i++) {
		
		
		final TabellaMisureDTO tabella = lista.getListaTabelle().get(i);	
		 
		if(tabella.getTipoProva().startsWith("L"))
		 {
			if(SessionBO.tipoRapporto.equals("SVT"))
			{
			  table=getTabellaLinearitaSVT(tabella);
			}
			if(SessionBO.tipoRapporto.equals("RDT"))
			{
			  table=getTabellaLinearitaRDT(tabella);
			}
			
			  table.getModel().addTableModelListener(this);
			  
		 }
		if(tabella.getTipoProva().startsWith("R"))
		 {
			if(SessionBO.tipoRapporto.equals("SVT"))
			{
			  table=getTabellaRipetibilitaSVT(tabella);
			}
			if(SessionBO.tipoRapporto.equals("RDT"))
			{
			  table=getTabellaRipetibilitaRDT(tabella);
			}
			  table.getModel().addTableModelListener(this);
		 }
		
		
		
		int add=i*260;
		int tableWidth=(int)Toolkit.getDefaultToolkit().getScreenSize().getWidth()-100;
		
		
		JPanel pan1= new JPanel();
		String title=  getTitle(tabella);
		pan1.setBorder (BorderFactory.createTitledBorder (new LineBorder(Costanti.COLOR_RED,2),
                 title,
                 TitledBorder.LEFT,
                 TitledBorder.TOP,new Font("Arial", Font.BOLD,14),Costanti.COLOR_RED));
		pan1.setBounds(10,yAxis+add,tableWidth,240);
	//	pan1.setCBorder(new LineBorder(Color.CYAN,2));
		pan1.setLayout(null);
		
		JScrollPane scroll = new JScrollPane();
		scroll.setSize(600,150);
		scroll.setBorder(new LineBorder(Color.gray,2));
		scroll.setBounds(10,20, tableWidth-200, 210);
		scroll.setViewportView(table);
		
		pan1.add(scroll);
		
		JButton button =new JButton("Misura "+(tabella.getId_tabella()));
		button.setIcon(new ImageIcon(PannelloTOP.class.getResource("/image/misura.png")));
		button.setBounds(tableWidth-175, 80, 150, 40);
		button.setFont(new Font("Arial",Font.BOLD,18));
		pan1.add(button);
		
		JButton buttonGrafico =new JButton("Grafico");
		buttonGrafico.setIcon(new ImageIcon(PannelloTOP.class.getResource("/image/graf2.png")));
		buttonGrafico.setBounds(tableWidth-175, 140, 150, 40);
		buttonGrafico.setFont(new Font("Arial",Font.BOLD,18));
		pan1.add(buttonGrafico);
		
		buttonGrafico.addActionListener(new ActionListener() {
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
		        		
		        		f.getContentPane().add(costruisciPanelChart(tabella));
		        		
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
		
		
		table.setFont(new Font("Arial", Font.PLAIN, 16));
		table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
		table.setRowHeight(30);
		
		listaJTable.add(table);
		listaTabelleOrdinate.put(tabella.getId_tabella(), table);
		button.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {				
				
				costruisciFrameMisura(e);
			}
		});
		
		
	//	JScrollPane pantemp =costruisciPanelChart(tabella);
	//	pantemp.setPreferredSize(new Dimension(800,160));
	//	pantemp.setBounds(750, yAxis+add, 730, 190);
		
		
		pannelloTabelle.add(pan1);
	//	pannelloTabelle.add(pantemp);
		
		}
		panel.setViewportView(pannelloTabelle);
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
		  
	private JScrollPane costruisciPanelChart(TabellaMisureDTO tabella)  {
		JScrollPane panel_chart = new JScrollPane();

		try 
		{
			panel_chart.setBorder(new LineBorder(Color.RED, 2, true));
			panel_chart.setBackground(Color.WHITE);

			final XYSeriesCollection dataset= getDataSetChart2(tabella);
				JFreeChart xylineChart = ChartFactory.createXYLineChart(
						"Deriva Strumento", 
						"POM",
						"Value", 
						dataset,
						PlotOrientation.VERTICAL, 
						true, true, false);

				StrumentoDTO strumento =GestioneStrumentoBO.getStrumento(SessionBO.idStrumento);
				xylineChart.addSubtitle(new TextTitle("Strumento: "+strumento.getDenominazione()+ " Codice: "+strumento.getCodice_interno()+" Matricola :"+strumento.getMatricola()));
				xylineChart.setBackgroundPaint(Color.WHITE);

				XYPlot plot = xylineChart.getXYPlot();
			    XYLineAndShapeRenderer rendu = new XYLineAndShapeRenderer( );
				
				if(SessionBO.tipoRapporto.equals(Costanti.SVT)) 
				{				
				rendu.setSeriesPaint(0, new Color(255,0,0));
				rendu.setSeriesPaint(1, new Color(255,0,0));
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

				panel_chart.setViewportView(chartPanel);	

		}catch (Exception e) {
			e.printStackTrace();
			PannelloConsole.printArea("Errore generazione grafico");
		}
		return panel_chart;
	}
	
	private XYSeriesCollection getDataSetChart2(TabellaMisureDTO tabella) throws Exception {

		XYSeriesCollection series = new XYSeriesCollection();

		final XYSeries accPos = new XYSeries( "Accettabilità +" );
		final XYSeries accNeg = new XYSeries( "Accettabilità -" );

		final XYSeries misura_inc_pos = new XYSeries( "Punto + U" );
		final XYSeries misura_inc_neg = new XYSeries( "Punto - U" );

		final XYSeries misuraPnt = new XYSeries( "Punto" );

		
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
	
	protected void costruisciFrameMisura(ActionEvent e) {
		
		String command = ((JButton) e.getSource()).getActionCommand();
		
		int pv=Integer.parseInt(command.substring(6,command.length()).trim());
		
	//	final JTable table= listaJTable.get(pv-1);//.getModel().setValueAt("ok corral",0, 2);
		
		final JTable table=listaTabelleOrdinate.get(pv);
		
		TabellaMisureDTO tabella =getTabella(lista,pv);
		
		final String title=getTitle(tabella);
		
		String tipoProva=tabella.getTipoProva();
		
		final boolean tp;
		
		if(tipoProva.startsWith("R"))
		{
			tp=true;
		}else
		{
			tp=false;
		}
		
		SwingUtilities.invokeLater(new Runnable(){
            public void run() 
            {
            	try
            	{
            	JFrame f=new FrameMisura(table, title,tp);
            	
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

	private TabellaMisureDTO getTabella(ProvaMisuraDTO lista, int pv) {
		
		TabellaMisureDTO tabella = null;
		
		for (TabellaMisureDTO table : lista.getListaTabelle()) {
			
			if(table.getId_tabella()==pv)
			{
				return table;
			}
		}
		
		return tabella;
	}

	private String getTitle(TabellaMisureDTO tabella) {
		String title="";
		 
		String[] arrProva=tabella.getTipoProva().split("_");
		
		if(arrProva[0].equals("L"))
		{
			title="Linearità "+arrProva[1]+" p.";
		}else
		{
			title="Ripetibilità "+arrProva[1]+"x"+arrProva[2]+" p.";
		}
		return title;
	}

	
	private JTable getTabellaLinearitaSVT(TabellaMisureDTO tabella) {
		
		JTable table = new JTable();
		table.setDefaultRenderer(Object.class, new MyCellRenderer(tabella.getTipoProva()));
		DefaultTableModel model = new DefaultTableModel() {

			@Override
			public Class<?> getColumnClass(int column) {
				switch (column) {
				case 0:
					return Integer.class;
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
				default:
					return String.class;
				}
			}
			 @Override
			public boolean isCellEditable(int row, int column) {
				if(column>4)
				{
				return false;
				}else
				{
					return true;
				}
			}
			 
		};

		model.addColumn("ID");
		model.addColumn("Tipo Verifica");
		model.addColumn("UM");
		model.addColumn("Valore Campione");
		model.addColumn("Valore Strumento");
		model.addColumn("Scostamento");
		model.addColumn("Accettabilità");
		model.addColumn("Incertezza");
		model.addColumn("Esito");

		for (int i = 0; i < tabella.getListaMisure().size(); i++) {
			MisuraDTO misura=tabella.getListaMisure().get(i);
			model.addRow(new Object[0]);
			model.setValueAt(misura.getId(), i, 0);
			model.setValueAt(misura.getTipoVerifica(), i, 1);
			
			if(misura.getApplicabile().equals("S"))
			{
				model.setValueAt(misura.getUm(), i, 2);
				model.setValueAt(Utility.BigDecimalStp(misura.getValoreCampione()), i, 3);
				model.setValueAt(Utility.BigDecimalStp(misura.getValoreStrumento()), i, 4);
				model.setValueAt(Utility.BigDecimalStp(misura.getScostamento()), i, 5);
				model.setValueAt(Utility.BigDecimalStp(misura.getAccettabilita()), i, 6);
				model.setValueAt(Utility.BigDecimalStp(misura.getIncertezza()), i, 7);
				model.setValueAt(misura.getEsito(), i, 8);
			}
			else
			{
				model.setValueAt("N/A", i, 2);
				model.setValueAt("N/A", i, 3);
				model.setValueAt("N/A", i, 4);
				model.setValueAt("N/A", i, 5);
				model.setValueAt("N/A", i, 6);
				model.setValueAt("N/A", i, 7);
				model.setValueAt("N/A", i, 8);
			}
		}
		table.setModel(model);
		
		int index = table.getColumnModel().getColumnIndex("ID");
		
		  TableColumn column = table.getColumnModel().getColumn(index);
		  table.removeColumn(column);
		  
		return table;
	}
	private JTable getTabellaRipetibilitaSVT(TabellaMisureDTO tabella) {
		
		JTable table = new JTable();
		table.setDefaultRenderer(Object.class, new MyCellRenderer(tabella.getTipoProva()));
		DefaultTableModel model = new DefaultTableModel() {

			@Override
			public Class<?> getColumnClass(int column) {
				switch (column) {
				case 0:
					return Integer.class;
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
				default:
					return String.class;
				}
			}
		};




		model.addColumn("ID");
		model.addColumn("Tipo Verifica");
		model.addColumn("UM");
		model.addColumn("Valore Campione");
		model.addColumn("Valore Medio Campione");
		model.addColumn("Valore Strumento");
		model.addColumn("Valore Medio Strumento");
		model.addColumn("Scostamento");
		model.addColumn("Accettabilità");
		model.addColumn("Incertezza");
		model.addColumn("Esito");

		for (int i = 0; i < tabella.getListaMisure().size(); i++) {
			MisuraDTO misura=tabella.getListaMisure().get(i);
			model.addRow(new Object[0]);
			model.setValueAt(misura.getId(), i, 0);
			model.setValueAt(misura.getTipoVerifica(), i, 1);
			
			if(misura.getApplicabile().equals("S"))
			{
				model.setValueAt(misura.getUm(), i, 2);
				model.setValueAt(Utility.BigDecimalStp(misura.getValoreCampione()), i, 3);
				model.setValueAt(Utility.BigDecimalStp(misura.getValoreMedioCampione()), i, 4);
				model.setValueAt(Utility.BigDecimalStp(misura.getValoreStrumento()), i, 5);
				model.setValueAt(Utility.BigDecimalStp(misura.getValoreMedioStrumento()), i, 6);
				model.setValueAt(Utility.BigDecimalStp(misura.getScostamento()), i, 7);
				model.setValueAt(Utility.BigDecimalStp(misura.getAccettabilita()), i, 8);
				model.setValueAt(Utility.BigDecimalStp(misura.getIncertezza()), i, 9);
				model.setValueAt(misura.getEsito(), i, 10);
			}
			else
			{
				model.setValueAt("N/A", i, 2);
				model.setValueAt("N/A", i, 3);
				model.setValueAt("N/A", i, 4);
				model.setValueAt("N/A", i, 5);
				model.setValueAt("N/A", i, 6);
				model.setValueAt("N/A", i, 7);
				model.setValueAt("N/A", i, 8);
				model.setValueAt("N/A", i, 9);
				model.setValueAt("N/A", i, 10);
			}
		}
		table.setModel(model);
		
		int index = table.getColumnModel().getColumnIndex("ID");
		
		  TableColumn column = table.getColumnModel().getColumn(index);
		  table.removeColumn(column);
		  
		return table;
	}
	
	private JTable getTabellaLinearitaRDT(TabellaMisureDTO tabella) {
		
		JTable table = new JTable();
		table.setDefaultRenderer(Object.class, new MyCellRenderer(tabella.getTipoProva()));
		DefaultTableModel model = new DefaultTableModel() {

			@Override
			public Class<?> getColumnClass(int column) {
				switch (column) {
				case 0:
					return Integer.class;
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
			//	case 7:
			//		return String.class;
				default:
					return String.class;
				}
			}
		};




		model.addColumn("ID");
		model.addColumn("Tipo Verifica");
		model.addColumn("UM");
		model.addColumn("Valore Campione");
		model.addColumn("Valore Strumento");
		model.addColumn("Correzione");
		model.addColumn("Incertezza");
	//	model.addColumn("Esito");

		for (int i = 0; i < tabella.getListaMisure().size(); i++) {
			MisuraDTO misura=tabella.getListaMisure().get(i);
			model.addRow(new Object[0]);
			model.setValueAt(misura.getId(), i, 0);
			model.setValueAt(misura.getTipoVerifica(), i, 1);
			
			if(misura.getApplicabile().equals("S"))
			{
			model.setValueAt(misura.getUm(), i, 2);
			model.setValueAt(Utility.BigDecimalStp(misura.getValoreCampione()), i, 3);
			model.setValueAt(Utility.BigDecimalStp(misura.getValoreStrumento()), i, 4);
			model.setValueAt(Utility.BigDecimalStp(misura.getScostamento()), i, 5);
			model.setValueAt(Utility.BigDecimalStp(misura.getIncertezza()), i, 6);
		//	model.setValueAt(misura.getEsito(), i, 7);
			}
			else
			{
				model.setValueAt("N/A", i, 2);
				model.setValueAt("N/A", i, 3);
				model.setValueAt("N/A", i, 4);
				model.setValueAt("N/A", i, 5);
				model.setValueAt("N/A", i, 6);
			//	model.setValueAt("N/A", i, 7);
			}
		}
		table.setModel(model);
		
		int index = table.getColumnModel().getColumnIndex("ID");
		TableColumn column = table.getColumnModel().getColumn(index);
		 table.removeColumn(column);
		  
		return table;
	}


	
	private JTable getTabellaRipetibilitaRDT(TabellaMisureDTO tabella) {
		JTable table = new JTable();
		table.setDefaultRenderer(Object.class, new MyCellRenderer(tabella.getTipoProva()));
		DefaultTableModel model = new DefaultTableModel() {

			@Override
			public Class<?> getColumnClass(int column) {
				switch (column) {
				case 0:
					return Integer.class;
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
			//	case 9:
			//		return String.class;
				default:
					return String.class;
				}
			}
		};




		model.addColumn("ID");
		model.addColumn("Tipo Verifica");
		model.addColumn("UM");
		model.addColumn("Valore Campione");
		model.addColumn("Valore Medio Campione");
		model.addColumn("Valore Strumento");
		model.addColumn("Valore Medio Strumento");
		model.addColumn("Correzione");
		model.addColumn("Incertezza");
//		model.addColumn("Esito");

		for (int i = 0; i < tabella.getListaMisure().size(); i++) {
			MisuraDTO misura=tabella.getListaMisure().get(i);
			model.addRow(new Object[0]);
			model.setValueAt(misura.getId(), i, 0);
			model.setValueAt(misura.getTipoVerifica(), i, 1);
			if(misura.getApplicabile().equals("S"))
			{
				model.setValueAt(misura.getUm(), i, 2);
				model.setValueAt(Utility.BigDecimalStp(misura.getValoreCampione()), i, 3);
				model.setValueAt(Utility.BigDecimalStp(misura.getValoreMedioCampione()), i, 4);
				model.setValueAt(Utility.BigDecimalStp(misura.getValoreStrumento()), i, 5);
				model.setValueAt(Utility.BigDecimalStp(misura.getValoreMedioStrumento()), i, 6);
				model.setValueAt(Utility.BigDecimalStp(misura.getScostamento()), i, 7);
				model.setValueAt(Utility.BigDecimalStp(misura.getIncertezza()), i, 8);
	//			model.setValueAt(misura.getEsito(), i, 9);
			}
			else
			{
				model.setValueAt("N/A", i, 2);
				model.setValueAt("N/A", i, 3);
				model.setValueAt("N/A", i, 4);
				model.setValueAt("N/A", i, 5);
				model.setValueAt("N/A", i, 6);
				model.setValueAt("N/A", i, 7);
				model.setValueAt("N/A", i, 8);
		//		model.setValueAt("N/A", i, 9);
			}
		}
		table.setModel(model);
		
		int index = table.getColumnModel().getColumnIndex("ID");
		
		  TableColumn column = table.getColumnModel().getColumn(index);
		  table.removeColumn(column);  
		return table;
	}
	
	public class MyCellRenderer extends javax.swing.table.DefaultTableCellRenderer {
		
		String tipo="";
		public MyCellRenderer(String _tipo)
		{
			tipo=_tipo;
		}

        public java.awt.Component getTableCellRendererComponent(javax.swing.JTable table, java.lang.Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        
        	final java.awt.Component cellComponent = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

      
             String[] arrProva=tipo.split("_");
             
        if(arrProva[0].equals("L"))
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
        	ArrayList<Boolean> checkList= getListaControlloRipetibilita(Integer.parseInt(arrProva[1]),Integer.parseInt(arrProva[2]));
 
        	if(checkList.get(row)==false)
        	{
        		 cellComponent.setForeground(Color.black);
                 cellComponent.setBackground(Color.white);	
        	}else
        	{
        		cellComponent.setForeground(Color.black);
                cellComponent.setBackground(new Color(224,224,224));
        	}
        }
            
        return cellComponent;

        }

		private ArrayList<Boolean> getListaControlloRipetibilita(int punti, int ripetizioni) {
			
			boolean flag=false;
			ArrayList<Boolean> lista=new ArrayList<Boolean>();

			for (int i = 1; i <= ripetizioni; i++) {
				
				if(i % 2 == 0)
				{
					flag=false;
				}
				else
				{
					flag=true;
				}
				for (int j = 1; j <= punti; j++) 
				{
					lista.add(flag);
				}
			
			}
			return lista;
		}

    }

	@Override
	public void tableChanged(TableModelEvent e) {
		 int row = e.getFirstRow();
	        TableModel model = (TableModel)e.getSource();
	        MisuraDTO misura=null;
			try {
				
				misura = GestioneMisuraBO.getMisura(Integer.parseInt(model.getValueAt(row,0).toString()));

	        
	       if(model.getColumnCount()==9 && SessionBO.tipoRapporto.equals("SVT"))
	       {
	    	misura.setId(Integer.parseInt(model.getValueAt(row,0).toString()));   
	        misura.setTipoVerifica(Utility.toString(model.getValueAt(row, 1)));
	        misura.setUm(Utility.toString(model.getValueAt(row, 2)));
	        if(model.getValueAt(row, 3)!=null)
	        {
	        	misura.setValoreCampione(new BigDecimal(model.getValueAt(row, 3).toString()));
	        }
	        if(model.getValueAt(row, 4)!=null)
	        {
	        	misura.setValoreStrumento(new BigDecimal(model.getValueAt(row, 4).toString()));
	        }
	        if(model.getValueAt(row, 5)!=null)
	        {
	        	misura.setScostamento(new BigDecimal(model.getValueAt(row, 5).toString()));
	        }
	        if(model.getValueAt(row, 6)!=null)
	        {
	        	misura.setAccettabilita(new BigDecimal(model.getValueAt(row, 6).toString()));
	        }
	        if(model.getValueAt(row, 7)!=null)
	        {
	        	misura.setIncertezza(new BigDecimal(model.getValueAt(row, 7).toString()));
	        }
	        
	        misura.setEsito(Utility.toString(model.getValueAt(row, 8)));
	       }
	       
	       if(model.getColumnCount()==7 && SessionBO.tipoRapporto.equals("RDT"))
	       { 
	    	misura.setId(Integer.parseInt(model.getValueAt(row,0).toString()));
	        misura.setTipoVerifica(Utility.toString(model.getValueAt(row, 1)));
	        misura.setUm(Utility.toString(model.getValueAt(row, 2)));
	        if(model.getValueAt(row, 3)!=null)
	        {
	        	misura.setValoreCampione(new BigDecimal(model.getValueAt(row, 3).toString()));
	        }
	        if(model.getValueAt(row, 4)!=null)
	        {
	        	misura.setValoreStrumento(new BigDecimal(model.getValueAt(row, 4).toString()));
	        }
	        if(model.getValueAt(row, 5)!=null)
	        {
	        	misura.setScostamento(new BigDecimal(model.getValueAt(row, 5).toString()));
	        }
	        if(model.getValueAt(row, 6)!=null)
	        {
	        	misura.setIncertezza(new BigDecimal(model.getValueAt(row, 6).toString()));
	        }
	       }
	       
	       if(model.getColumnCount()==11 && SessionBO.tipoRapporto.equals("SVT"))
	       {
	    	misura.setId(Integer.parseInt(Utility.toString(model.getValueAt(row,0))));
	        misura.setTipoVerifica(Utility.toString(model.getValueAt(row, 1)));
	        misura.setUm(Utility.toString(model.getValueAt(row, 2)));
	        
	        if(model.getValueAt(row, 3)!=null)
	        {
	        	misura.setValoreCampione(new BigDecimal(model.getValueAt(row, 3).toString()));
	        }
	        if(model.getValueAt(row, 4)!=null)
	        {
	        	misura.setValoreMedioCampione(new BigDecimal(model.getValueAt(row, 4).toString()));
	        }
	        
	        if(model.getValueAt(row, 5)!=null)
	        {
	        	misura.setValoreStrumento(new BigDecimal(model.getValueAt(row, 5).toString()));
	        }
	        if(model.getValueAt(row, 6)!=null)
	        {	
	        	misura.setValoreMedioStrumento(new BigDecimal(model.getValueAt(row, 6).toString()));
	        }
	        if(model.getValueAt(row, 7)!=null)
	        {	
	        	misura.setScostamento(new BigDecimal(model.getValueAt(row, 7).toString()));
	        }
	        if(model.getValueAt(row, 8)!=null)
	        {
	        	misura.setAccettabilita(new BigDecimal(model.getValueAt(row, 8).toString()));
	        }
	        if(model.getValueAt(row, 9)!=null)
	        {
	        	misura.setIncertezza(new BigDecimal(model.getValueAt(row, 9).toString()));
	        }
	        misura.setEsito(Utility.toString(model.getValueAt(row, 10)));
	       }
	       
	       if(model.getColumnCount()==9 && SessionBO.tipoRapporto.equals("RDT"))
	       { 
	       	misura.setId(Integer.parseInt(model.getValueAt(row,0).toString()));
	        misura.setTipoVerifica(Utility.toString(model.getValueAt(row, 1)));
	        misura.setUm(Utility.toString(model.getValueAt(row, 2)));
	        if(model.getValueAt(row, 3)!=null)
	        {
	        	misura.setValoreCampione(new BigDecimal(model.getValueAt(row, 3).toString()));
	        }
	        if(model.getValueAt(row, 4)!=null)
	        {
	        	misura.setValoreMedioCampione(new BigDecimal(model.getValueAt(row, 4).toString()));
	        }
	        if(model.getValueAt(row, 5)!=null)
	        {
	        	misura.setValoreStrumento(new BigDecimal(model.getValueAt(row, 5).toString()));
	        }
	        if(model.getValueAt(row, 6)!=null)
	        {
	        	misura.setValoreMedioStrumento(new BigDecimal(model.getValueAt(row, 6).toString()));
	        }
	        if(model.getValueAt(row, 7)!=null)
	        {
	        	misura.setScostamento(new BigDecimal(model.getValueAt(row, 7).toString().toCharArray()));
	        }
	        if(model.getValueAt(row, 8)!=null)
	        {
	        	misura.setIncertezza(new BigDecimal(model.getValueAt(row, 8).toString()));
	        }
	        
	       }
	       
	       
	       
		GestioneMisuraBO.updateRecordMisura(misura);
		} catch (Exception e1) {
			
			PannelloConsole.printException(e1);
			e1.printStackTrace();
		}
	    //    PannelloConsole.printArea("ID: "+model.getValueAt(row, 0));
	     //   PannelloConsole.printArea(data.toString());
		
	}
}
