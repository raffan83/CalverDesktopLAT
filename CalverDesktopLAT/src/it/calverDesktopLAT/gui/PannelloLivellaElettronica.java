package it.calverDesktopLAT.gui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.ObjectStreamException;
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
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;

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

import it.calverDesktopLAT.bo.GestioneCampioneBO;
import it.calverDesktopLAT.bo.GestioneMisuraBO;
import it.calverDesktopLAT.bo.SessionBO;
import it.calverDesktopLAT.bo.Statistic;
import it.calverDesktopLAT.dto.LatMisuraDTO;
import it.calverDesktopLAT.dto.LatPuntoLivellaElettronicaDTO;
import it.calverDesktopLAT.dto.MisuraDTO;
import it.calverDesktopLAT.dto.ParametroTaraturaDTO;
import it.calverDesktopLAT.dto.ProvaMisuraDTO;
import it.calverDesktopLAT.dto.RegLinDTO;
import it.calverDesktopLAT.dto.TabellaMisureDTO;
import it.calverDesktopLAT.utl.Costanti;
import net.miginfocom.swing.MigLayout;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

public class PannelloLivellaElettronica extends JPanel  {

	ArrayList<LatPuntoLivellaElettronicaDTO> listaPuntiLineari,listaIncertezze;
	ArrayList<ArrayList<LatPuntoLivellaElettronicaDTO>> listaPuntiRipetibili;
	
	private JTextField textField_indicazione_iniziale;
	private JTextField textField_indicazione_corretta;
	private JTextField textField_inclinazione_cmp;
	private JTextField incertezza_em;
	private JTextArea textArea;
	private JTextField campo_misura;
	private JTextField sensibilita;
	private JComboBox comboBox_cmpRif;
	BigDecimal var_offset= null;
	ArrayList<ParametroTaraturaDTO> listaParametri;
	ArrayList<RegLinDTO> regressioneLineare;
	ModelIncertezze model_incertezze;
	ModelProvaLineare modelLin;
	LatMisuraDTO lat;
	
	public PannelloLivellaElettronica(int index) {

		SessionBO.prevPage="PMM";
	
		try 
		{
		setLayout(new MigLayout("", "[grow][grow][grow][grow][grow]", "[][grow]"));

		JScrollPane mainScroll = new JScrollPane();
		add(mainScroll, "cell 0 1 5 1,grow");

		final JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		mainScroll.setViewportView(tabbedPane);

	
			
			listaPuntiLineari = GestioneMisuraBO.getListaPuntiLivellaElettronicaLineari(SessionBO.idMisura);
			listaPuntiRipetibili = GestioneMisuraBO.getListaPuntiLivellaElettroniaRipetibili(SessionBO.idMisura);
			listaIncertezze =GestioneMisuraBO.getListaPuntiLivellaElettronicaIncertezze(SessionBO.idMisura);  
			

			
				JLabel initCmp= new JLabel("Indicazione Iniziale Campione");
				initCmp.setFont(new Font("Arial", Font.BOLD, 14));
				add(initCmp, "flowx,cell 0 0");
				
				textField_indicazione_iniziale = new JTextField();
				textField_indicazione_iniziale.setBackground(Color.YELLOW);
				textField_indicazione_iniziale.setFont(new Font("Arial", Font.BOLD, 14));
				textField_indicazione_iniziale.setText(listaPuntiLineari.get(0).getIndicazione_iniziale().toPlainString());
				textField_indicazione_iniziale.setEditable(true);
				add(textField_indicazione_iniziale, "cell 0 0");
				textField_indicazione_iniziale.setColumns(10);

				JLabel lblSMediaTotale = new JLabel("Indicazione Iniziale Corretta");
				lblSMediaTotale.setFont(new Font("Arial", Font.BOLD, 14));
		        add(lblSMediaTotale, "flowx,cell 2 0");
		        
		        textField_indicazione_corretta = new JTextField();
		        textField_indicazione_corretta.setBackground(Color.YELLOW);
		        textField_indicazione_corretta.setFont(new Font("Arial", Font.BOLD, 14));
		        textField_indicazione_corretta.setText(listaPuntiLineari.get(0).getIndicazione_iniziale_corr().toPlainString());
		        textField_indicazione_corretta.setEditable(true);
				add(textField_indicazione_corretta, "cell 2 0");
				textField_indicazione_corretta.setColumns(10);
				
		
				JLabel lbl_inclinazione_cmp = new JLabel("Inc. tipo comp. del camp.");
				lbl_inclinazione_cmp.setFont(new Font("Arial", Font.BOLD, 14));
				add(lbl_inclinazione_cmp, "flowx,cell 4 0");
				
				textField_inclinazione_cmp = new JTextField();
				textField_inclinazione_cmp.setBackground(Color.YELLOW);
				textField_inclinazione_cmp.setEditable(true);
				textField_inclinazione_cmp.setFont(new Font("Arial", Font.BOLD, 14));
				textField_inclinazione_cmp.setText(listaPuntiLineari.get(0).getInclinazione_cmp_campione().toPlainString());
				add(textField_inclinazione_cmp, "cell 4 0");
				textField_inclinazione_cmp.setColumns(10);
				
				
				PannelloIncertezze pannelloIncertezze = new PannelloIncertezze();
				tabbedPane.addTab("Riferimenti & Incertezza", pannelloIncertezze.get());
				PannelloProvaLineare provaLineare = new PannelloProvaLineare();
				tabbedPane.addTab("Prova Lineare",provaLineare.get());
				PannelloProvaRipetibile provaRipetibile = new PannelloProvaRipetibile();
				tabbedPane.addTab("Prova Ripetibilità",provaRipetibile.get());
				
				PannelloGrafico provaGrafico = new PannelloGrafico();
				tabbedPane.addTab("Grafico Scostamenti",provaGrafico.get());
				
				tabbedPane.setSelectedIndex(index);
				
				
				  tabbedPane.addChangeListener(new ChangeListener() {
						public void stateChanged(ChangeEvent e) {
					    
					     if(tabbedPane.getSelectedIndex()==3) 
					     {
					    	 PannelloGrafico provaGrafico = new PannelloGrafico();
					    	 
					    	 tabbedPane.setComponentAt(3, provaGrafico.get());
					     }
					     }
				    });
			
				textField_indicazione_iniziale.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						try {
							BigDecimal indicazione_campione=new BigDecimal(textField_indicazione_iniziale.getText()).setScale(Costanti.RISOLUZIONE_LIVELLA_ELETTRONICA+2);

							if(regressioneLineare!=null ) 
							{
								RegLinDTO regres=null;
								for (int i = 0; i <regressioneLineare.size()-1; i++) {
							
								if(indicazione_campione.compareTo(regressioneLineare.get(i).getValore_misurato())>=0 && indicazione_campione.compareTo(regressioneLineare.get(i+1).getValore_misurato())<=0 )
								{
									regres=regressioneLineare.get(i);
									break;
								}
								
								}
								if(regres!=null) 
								{
									BigDecimal indicazione_corretta=(indicazione_campione.multiply(regres.getM()).add(regres.getQ())).multiply(new BigDecimal(-1)).setScale(Costanti.RISOLUZIONE_LIVELLA_ELETTRONICA+2,RoundingMode.HALF_UP);
									
									textField_indicazione_corretta.setText(indicazione_corretta.add(indicazione_campione).toPlainString());
									
									var_offset=indicazione_corretta;
								}
							}
							
						} catch (Exception e2) {
							e2.printStackTrace();
							textField_indicazione_corretta.setText("0");
						}
						
					}
				});

		}catch 
		(Exception e) {
			e.printStackTrace();
		}
	}


	
	public class PannelloIncertezze implements TableModelListener,ActionListener {

		JPanel semInc= new JPanel();
		JTable tabellaIncertezze;

		public PannelloIncertezze() throws Exception 
		{
		semInc.setBackground(Color.LIGHT_GRAY);
		

		
		semInc.setLayout(new MigLayout("", "[][][20px][][grow][:100:]", "[][][][][][grow][][][]"));

	
			
				JLabel lblVerifichePreliminari = new JLabel("Verifiche preliminari");
				lblVerifichePreliminari.setFont(new Font("Arial", Font.BOLD | Font.ITALIC, 14));
				semInc.add(lblVerifichePreliminari, "cell 0 0");

				JLabel lblCampioneDiRiferimento = new JLabel("Campione di Riferimento");
				semInc.add(lblCampioneDiRiferimento, "cell 0 1,alignx trailing,aligny top");


				comboBox_cmpRif = new JComboBox(GestioneCampioneBO.getListaCampioniCompleta());
				semInc.add(comboBox_cmpRif, "cell 1 1 2 1,growx,aligny top");

				JLabel lblCampoMisura = new JLabel("Campo Misura");
				semInc.add(lblCampoMisura, "cell 0 2,alignx trailing,aligny top");

				campo_misura = new JTextField();
				campo_misura.setColumns(10);
				semInc.add(campo_misura, "flowx,cell 1 2,growx,aligny top");



				JLabel lblSensibilit = new JLabel("Sensibilit\u00E0:");
				semInc.add(lblSensibilit, "cell 0 3,alignx trailing,aligny top");

				sensibilita = new JTextField();
				sensibilita.setColumns(10);
				semInc.add(sensibilita, "flowx,cell 1 3,growx,aligny top");

				JLabel lblIncertezzaEstesaUem = new JLabel("Incertezza Estesa U(Em)");
				lblIncertezzaEstesaUem.setFont(new Font("Arial", Font.PLAIN, 12));
				semInc.add(lblIncertezzaEstesaUem, "cell 0 4,alignx trailing,aligny top");

				incertezza_em = new JTextField();
				incertezza_em.setEditable(false);
				incertezza_em.setColumns(10);
				semInc.add(incertezza_em, "flowx,cell 1 4,growx,aligny top");

			
				JButton btnSalva = new JButton("Salva");
				
				btnSalva.setIcon(new ImageIcon(PannelloLivellaElettronica.class.getResource("/image/save.png")));
				btnSalva.setFont(new Font("Arial", Font.BOLD, 12));
				semInc.add(btnSalva, "cell 0 7 3 1,alignx center,aligny center");

				JLabel lblNote = new JLabel("Note:");
				lblNote.setFont(new Font("Arial", Font.PLAIN, 12));
				semInc.add(lblNote, "cell 3 7,alignx right");

				textArea = new JTextArea();
				JScrollPane scrollPaneNote = new JScrollPane(textArea);
				semInc.add(scrollPaneNote, "cell 4 7,grow");

				JLabel label_1 = new JLabel("''");
				label_1.setVerticalAlignment(SwingConstants.TOP);
				semInc.add(label_1, "cell 2 2");

				JLabel label_2 = new JLabel("''");
				label_2.setVerticalAlignment(SwingConstants.TOP);
				semInc.add(label_2, "cell 2 3");

				JLabel label_3 = new JLabel("''");
				label_3.setVerticalAlignment(SwingConstants.TOP);
				semInc.add(label_3, "cell 2 4");

				tabellaIncertezze = new JTable();
			//	tableProvaLineare.setDefaultRenderer(Object.class, new MyCellRenderer());
				model_incertezze = new ModelIncertezze();

				LatPuntoLivellaElettronicaDTO punto =null;
				for (int i = 0; i <listaIncertezze.size(); i++) {

					punto= listaIncertezze.get(i);
					model_incertezze.addRow(new Object[0]);
					model_incertezze.setValueAt(punto.getPunto(), i, 0);
					model_incertezze.setValueAt(punto.getValore_nominale(), i, 1);
					model_incertezze.setValueAt(punto.getInc_ris(), i, 2);
					model_incertezze.setValueAt(punto.getInc_rip(), i, 3);
					model_incertezze.setValueAt(punto.getInc_cmp(), i, 4);
					model_incertezze.setValueAt(punto.getInc_stab(), i, 5);
					model_incertezze.setValueAt(punto.getInc_est(), i, 6);
					model_incertezze.setValueAt(punto.getId(), i,7);
					
				}

				tabellaIncertezze.setModel(model_incertezze);
				tabellaIncertezze.setFont(new Font("Arial", Font.BOLD, 12));
				tabellaIncertezze.getTableHeader().setFont(new Font("Arial", Font.BOLD, 10));
				tabellaIncertezze.setRowHeight(25);

				
				model_incertezze.addTableModelListener(this);
				
				TableColumn column = tabellaIncertezze.getColumnModel().getColumn(tabellaIncertezze.getColumnModel().getColumnIndex("index"));
				tabellaIncertezze.removeColumn(column);

				JScrollPane scrollTab = new JScrollPane(tabellaIncertezze);
				semInc.add(scrollTab, "cell 4 0 1 6,height :350:400,grow");

				
	
				LatMisuraDTO misura =GestioneMisuraBO.getMisuraLAT(SessionBO.idMisura);

				/*Riempo pannello se il campo riferimenti_incertezza !=null*/
				if(misura.getRif_campione()!=null) 
				{
					comboBox_cmpRif.setSelectedItem(misura.getRif_campione());
					campo_misura.setText(misura.getCampo_misura().toPlainString());
					sensibilita.setText(misura.getSensibilita().toPlainString());
					
					listaParametri=GestioneCampioneBO.getParametriTaratura(comboBox_cmpRif.getSelectedItem().toString());
					
					regressioneLineare= GestioneMisuraBO.getListaRegressioneLineare(listaParametri);
					 
					if(misura.getIncertezza_estesa()!=null)
					{
						incertezza_em.setText(misura.getIncertezza_estesa().toPlainString());
					}
					
					
					textArea.setText(misura.getNote());
				}
				
				
				comboBox_cmpRif.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						try 
						{
							ArrayList<ParametroTaraturaDTO> listaParametri=GestioneCampioneBO.getParametriTaratura(comboBox_cmpRif.getSelectedItem().toString());
							
							regressioneLineare= GestioneMisuraBO.getListaRegressioneLineare(listaParametri);
							
						}catch (Exception ex1) 
						{
							
							ex1.printStackTrace();
						}
						
					}
				});
				
				
				btnSalva.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						
						try 
						{
						int scelta=	JOptionPane.showConfirmDialog(null,"Vuoi Salvare i dati ?","Salva",JOptionPane.YES_NO_OPTION,JOptionPane.INFORMATION_MESSAGE,new ImageIcon(PannelloTOP.class.getResource("/image/question.png")));
						
						if(scelta==0) 
						
						{
							boolean check=true;
							StringBuffer sb = new StringBuffer();
							
							lat = new LatMisuraDTO();
							lat.setId(SessionBO.idMisura);
							if(comboBox_cmpRif.getSelectedIndex()<0 )
							{
								sb.append("* Selezionare Campioni riferimento/lavoro \n");
								check=false;
							}
							if(sensibilita.getText().length()<=0)
							{
								sb.append("* Indicare Risoluzione  \n");
								check=false;
							}
							if(check) 
							{
								lat.setId(SessionBO.idMisura);
								lat.setRif_campione(comboBox_cmpRif.getSelectedItem().toString());
								
								lat.setCampo_misura(new BigDecimal(campo_misura.getText()).setScale(Costanti.RISOLUZIONE_LIVELLA_BOLLA+2,RoundingMode.HALF_UP));
								
								lat.setSensibilita(new BigDecimal(sensibilita.getText()).setScale(Costanti.RISOLUZIONE_LIVELLA_BOLLA+2,RoundingMode.HALF_UP));
								
								lat.setNote(textArea.getText());
								
								GestioneMisuraBO.updateRecordMisuraLAT(lat);
								
								JOptionPane.showMessageDialog(null,"Salvataggio Completato","Salva",JOptionPane.INFORMATION_MESSAGE,new ImageIcon(PannelloTOP.class.getResource("/image/confirm.png")));
							}
							
						}
						
						}catch (Exception e1) 
						{
						e1.printStackTrace();
							}
						}
				});
				
		}	
			
			
			
			public Component get() {
			return semInc;
		}



			@Override
			public void actionPerformed(ActionEvent e) {
					
			}



			@Override
			public void tableChanged(TableModelEvent e) {
				int row = e.getFirstRow();
				try 
				{
				TableModel model = (TableModel)e.getSource();
				
				int indexPoint=Integer.parseInt(model.getValueAt(row,7).toString());

				LatPuntoLivellaElettronicaDTO punto = new LatPuntoLivellaElettronicaDTO();
				punto.setId(indexPoint);
				punto.setValore_nominale(checkField(model.getValueAt(row, 1),Costanti.RISOLUZIONE_LIVELLA_ELETTRONICA+1));
				punto.setInc_ris(checkField(model.getValueAt(row, 2),Costanti.RISOLUZIONE_LIVELLA_ELETTRONICA+1));
				punto.setInc_rip(checkField(model.getValueAt(row, 3),Costanti.RISOLUZIONE_LIVELLA_ELETTRONICA+1));
				punto.setInc_cmp(checkField(model.getValueAt(row, 4),Costanti.RISOLUZIONE_LIVELLA_ELETTRONICA+1));
				punto.setInc_stab(checkField(model.getValueAt(row, 5),Costanti.RISOLUZIONE_LIVELLA_ELETTRONICA+1));
				punto.setInc_est(checkField(model.getValueAt(row, 6),Costanti.RISOLUZIONE_LIVELLA_ELETTRONICA+1));
				
				GestioneMisuraBO.updateRecordPuntoLivellaElettronica(punto);
			
				
				
				
				{}}catch (Exception ex) {
					ex.printStackTrace();
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
	}

	public class MyCellRenderer extends javax.swing.table.DefaultTableCellRenderer {



		public java.awt.Component getTableCellRendererComponent(javax.swing.JTable table, java.lang.Object value, boolean isSelected, boolean hasFocus, int row, int column) {

			final java.awt.Component cellComponent = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);


			if (column  == 1 ||column  == 2 ||column  == 3 ||column  == 4 ||column  ==5 ) 
			{
				cellComponent.setBackground(new Color(255,255,102));
				cellComponent.setForeground(Color.BLACK);

			}
			else if(column  == 6 ||column==7)
			{
				cellComponent.setBackground(new Color(255,0,0));
				cellComponent.setForeground(Color.BLACK);
			}
			else if(column  == 8 ||column==9)
			{
				cellComponent.setBackground(new Color(0,102,255));
				cellComponent.setForeground(Color.BLACK);
			}
			else 
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

	public class MyCellRendererRipetibilita extends javax.swing.table.DefaultTableCellRenderer {



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
	
	public class PannelloGrafico {

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
	
	class ModelIncertezze extends DefaultTableModel {


		public ModelIncertezze() {
			addColumn("Punto");
			addColumn("Valore nominale");
			addColumn("Inc. Risoluzione");
			addColumn("Inc. Ripetibilità");
			addColumn("Inc. Campione");
			addColumn("Inc. Stab");
			addColumn("Inc. Estesa");
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
			default:
				return String.class;
			}
		}

		@Override
		   public boolean isCellEditable(int row, int column) {
    
                return false;
           
        }


	}
}


