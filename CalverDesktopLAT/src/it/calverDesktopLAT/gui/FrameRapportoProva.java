package it.calverDesktopLAT.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
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
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;

import it.calverDesktopLAT.bo.GestioneCampioneBO;
import it.calverDesktopLAT.bo.GestioneMisuraBO;
import it.calverDesktopLAT.bo.SessionBO;
import it.calverDesktopLAT.dto.MisuraDTO;
import it.calverDesktopLAT.dto.ParametroTaraturaDTO;
import it.calverDesktopLAT.dto.ProvaMisuraDTO;
import it.calverDesktopLAT.dto.TabellaMisureDTO;
import it.calverDesktopLAT.utl.Costanti;
import it.calverDesktopLAT.utl.Utility;
import net.miginfocom.swing.MigLayout;
import java.awt.Font;
import javax.swing.ImageIcon;


public class FrameRapportoProva  extends JFrame implements ActionListener{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private JFrame frm;

	static JPanel mainPanel;
	static JPanel panel_Prova_RDP;
	static JPanel panel_misuraMaster;
	static JPanel panel_rip_misura_precedente;
	static JPanel panelCampione;
	static JScrollPane mainScroll;
	private JLabel lblCampioneUtilizzato;
	private JLabel lblValoreMisura;

	private JTextField textField_desc_libera_campione;
	private JTextField textField_rif_cmp_precedente;
	private JComboBox<?> comboCampioni;

	private JButton btnAggiungiCampione;


	private JTable table;

	public  static String title;
	private static String[] listaCampioniCompleta=null;
	public  static  String campioneInterpolazione;

	static ProvaMisuraDTO misura;

	ParametroTaraturaDTO parametro=null;
	private JTextArea textArea_descrizione_misura;
	private JTextField textField_valore_rilevato;
	private JComboBox comboEsito;
	private JButton btnAggiungi;
	private JLabel lblValoreRilevato;
	private JTextField textField_rif_valore_precedente;
	private JScrollPane scrollPane;
	private JTextArea textArea_rif_descrizione_prova_precedente;
	private JLabel lblEsito;
	private JTextField textField_rif_esito_precedente;
	private JLabel lblListaProveEffetuate;
	private JScrollPane scrollPane_1;
	private JTable table_prove;
	private JLabel lblValoreRilevato_1;
	private JLabel lblEsito_1;
	private JScrollPane scrollPane_2;
	private JScrollPane scrollPane_3;
	private JList lista_campioni_Aggiunti;
	private static DefaultListModel<String> dlm;
	private JButton btnAggiungiCampione_desc;
	private JTextField textField_path_file;
	private JButton btnCarica;
	private ByteArrayOutputStream file_att = null;
	private JButton btnApriAllegato;
	private JButton btnModifica;
	private JPopupMenu popupMenu,popupMenuListaCampioni;
	private JMenuItem jmit,jmitListaCampioni;
	MisuraDTO punto_misura;
	private JButton btnElimina;
	private JButton btnVisualizzaAllegato;

	public FrameRapportoProva(ProvaMisuraDTO _misura) 
	{



		try
		{	
			/*Inizzializzazione Variabili Costruttore*/
			frm=this;
			misura=_misura;
			file_att= new ByteArrayOutputStream();

			/*Inizzializzazione Variabile JFrame frm*/
			getContentPane().setBackground(Color.WHITE);
			setTitle("Foglio raccolta dati");
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			setSize(820, 780);

			/*Inizzializzazione Pannello Principale e  JScrollPane*/

			mainPanel = new JPanel();
			mainPanel.setPreferredSize(new Dimension(750, 730));
			//	mainPanel.setBackground(Color.CYAN);
			mainPanel.setLayout(new MigLayout("", "[grow]", "[158.00][:140:140][grow][grow][grow]"));

			mainScroll= new JScrollPane();
			mainScroll.setPreferredSize(new Dimension(660, 770));
			mainScroll.setViewportView(mainPanel);


			panel_rip_misura_precedente = getPanelMisurePrecedenti();
			panelCampione = getPannellCampione();
			panel_Prova_RDP = getPannelloProvaRDP();
			panel_misuraMaster= getPanelMisura(misura);

			mainPanel.add(panel_rip_misura_precedente, "cell 0 0,grow");
			mainPanel.add(panelCampione,"cell 0 1,grow");
			mainPanel.add(panel_Prova_RDP, "cell 0 2,grow");
			mainPanel.add(panel_misuraMaster, "cell 0 3,grow");

			getContentPane().add(mainScroll);
		}


		catch (Exception e1) {

			e1.printStackTrace();
		}




	}

	private JPanel getPanelMisurePrecedenti() {

		JPanel panel_rip_misura_precedente_RDP= new JPanel();
		panel_rip_misura_precedente_RDP.setBorder(new TitledBorder(new LineBorder(Costanti.COLOR_RED, 2, true), "Riferimenti Precedente Misura", TitledBorder.LEADING, TitledBorder.TOP, null, Color.RED));
		panel_rip_misura_precedente_RDP.setBackground(Color.WHITE);
		panel_rip_misura_precedente_RDP.setLayout(new MigLayout("", "[][grow]", "[][grow][][]"));

		lblCampioneUtilizzato = new JLabel("Campione Utilizzato");
		lblCampioneUtilizzato.setFont(new Font("Arial", Font.BOLD, 14));
		panel_rip_misura_precedente_RDP.add(lblCampioneUtilizzato, "cell 0 0,alignx trailing");

		textField_rif_cmp_precedente = new JTextField();
		textField_rif_cmp_precedente.setFont(new Font("Arial", Font.PLAIN, 14));
		textField_rif_cmp_precedente.setEnabled(false);
		panel_rip_misura_precedente_RDP.add(textField_rif_cmp_precedente, "flowx,cell 1 0,growx,width :350:");
		textField_rif_cmp_precedente.setColumns(10);

		lblValoreMisura = new JLabel("Descrizione Prova");
		lblValoreMisura.setFont(new Font("Arial", Font.BOLD, 14));
		panel_rip_misura_precedente_RDP.add(lblValoreMisura, "cell 0 1,alignx right,height 100:100:200");

		lblValoreRilevato = new JLabel("Valore Rilevato");
		lblValoreRilevato.setFont(new Font("Arial", Font.BOLD, 14));
		panel_rip_misura_precedente_RDP.add(lblValoreRilevato, "cell 0 2,alignx trailing");

		textField_rif_valore_precedente = new JTextField();
		textField_rif_valore_precedente.setFont(new Font("Arial", Font.PLAIN, 14));
		textField_rif_valore_precedente.setEnabled(false);
		textField_rif_valore_precedente.setEditable(false);
		textField_rif_valore_precedente.setBackground(Color.WHITE);
		panel_rip_misura_precedente_RDP.add(textField_rif_valore_precedente, "cell 1 2,alignx left");
		textField_rif_valore_precedente.setColumns(10);

		scrollPane = new JScrollPane();
		panel_rip_misura_precedente_RDP.add(scrollPane, "cell 1 1,grow");

		textArea_rif_descrizione_prova_precedente = new JTextArea();
		textArea_rif_descrizione_prova_precedente.setFont(new Font("Arial", Font.PLAIN, 14));
		textArea_rif_descrizione_prova_precedente.setEnabled(false);
		textArea_rif_descrizione_prova_precedente.setEditable(false);
		textArea_rif_descrizione_prova_precedente.setLineWrap(true);
		textArea_rif_descrizione_prova_precedente.setBackground(SystemColor.text);
		scrollPane.setViewportView(textArea_rif_descrizione_prova_precedente);

		lblEsito = new JLabel("Esito");
		lblEsito.setFont(new Font("Arial", Font.BOLD, 14));
		panel_rip_misura_precedente_RDP.add(lblEsito, "cell 0 3,alignx trailing");

		textField_rif_esito_precedente = new JTextField();
		textField_rif_esito_precedente.setFont(new Font("Arial", Font.PLAIN, 14));
		textField_rif_esito_precedente.setEnabled(false);
		textField_rif_esito_precedente.setEditable(false);
		textField_rif_esito_precedente.setColumns(10);
		textField_rif_esito_precedente.setBackground(Color.WHITE);
		panel_rip_misura_precedente_RDP.add(textField_rif_esito_precedente, "cell 1 3");

		btnApriAllegato = new JButton("Apri Allegato");
		btnApriAllegato.setIcon(new ImageIcon(FrameRapportoProva.class.getResource("/image/attach.png")));
		btnApriAllegato.setFont(new Font("Arial", Font.BOLD, 14));

		btnApriAllegato.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) 
			{
				JFileChooser jfc = new JFileChooser();
				jfc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				jfc.showSaveDialog(frm);

				File f= jfc.getSelectedFile();
				if(f!=null)
				{
					try {
						String filename=f.getPath()+"\\allegato.pdf";
						FileUtils.writeByteArrayToFile(new File(filename), punto_misura.getFile_att_prec());
						JOptionPane.showMessageDialog(null, "File salvato con successo in \""+filename+"\"","Conferma salvataggio",JOptionPane.INFORMATION_MESSAGE,new ImageIcon(PannelloTOP.class.getResource("/image/confirm.png")));
					} catch (IOException e) {

						PannelloConsole.printException(e);
					}
				}

			}
		});
		panel_rip_misura_precedente_RDP.add(btnApriAllegato, "cell 1 0,alignx right,growy");

		return panel_rip_misura_precedente_RDP;
	}


	private JPanel getPannellCampione() throws Exception {

		final JPanel panelCampione = new JPanel();
		panelCampione.setBorder(new TitledBorder(new LineBorder(Costanti.COLOR_RED, 2, true), "Lista Campioni", TitledBorder.LEADING, TitledBorder.TOP, null, Color.RED));
		panelCampione.setBackground(Color.WHITE);
		//	panel.setBounds(10, 11, 464, 640);

		panelCampione.setLayout(new MigLayout("", "[150:300:600]15[][][150:300:600,grow][150:300:600,grow][]", "[][grow][grow][][][]"));

		btnAggiungiCampione = new JButton("+");
		btnAggiungiCampione.setFont(new Font("Arial", Font.BOLD, 14));
		panelCampione.add(btnAggiungiCampione, "cell 1 2,alignx center");

		btnAggiungiCampione.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				if(comboCampioni.getSelectedIndex()>0 ) 
				{
					String campione= comboCampioni.getSelectedItem().toString();

					dlm.addElement(campione);
				}
			}


		});

		btnElimina = new JButton("Elimina");
		btnElimina.setIcon(new ImageIcon(FrameRapportoProva.class.getResource("/image/delete.png")));
		btnElimina.setFont(new Font("Arial", Font.BOLD, 14));
		btnElimina.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				if(dlm.size()>0 && lista_campioni_Aggiunti.getSelectedIndex()>-1)
				{
					dlm.removeElementAt(lista_campioni_Aggiunti.getSelectedIndex());
				}
			}
		});
		panelCampione.add(btnElimina, "cell 5 2");

		btnAggiungiCampione_desc = new JButton("+");
		btnAggiungiCampione_desc.setFont(new Font("Arial", Font.BOLD, 14));
		panelCampione.add(btnAggiungiCampione_desc, "cell 1 5");

		btnAggiungiCampione_desc.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				if(textField_desc_libera_campione.getText().length()>0) 
				{	
					dlm.addElement(textField_desc_libera_campione.getText());
				}
			}


		});

		scrollPane_3 = new JScrollPane();
		panelCampione.add(scrollPane_3, "cell 3 2 2 4,grow");

		dlm= new DefaultListModel<String>();
		lista_campioni_Aggiunti = new JList(dlm);
		lista_campioni_Aggiunti.setFont(new Font("Arial", Font.PLAIN, 14));
		scrollPane_3.setViewportView(lista_campioni_Aggiunti);


		JLabel lab_tipoMisura = new JLabel("Descrizione Campione Libera");
		lab_tipoMisura.setFont(new Font("Arial", Font.BOLD, 14));
		panelCampione.add(lab_tipoMisura, "cell 0 4");

		textField_desc_libera_campione = new JTextField();
		textField_desc_libera_campione.setFont(new Font("Arial", Font.PLAIN, 14));
		panelCampione.add(textField_desc_libera_campione, "cell 0 5,growx");
		textField_desc_libera_campione.setColumns(10);

		JLabel lblCodiceCampione = new JLabel("Codice Campione");
		lblCodiceCampione.setFont(new Font("Arial", Font.BOLD, 14));
		panelCampione.add(lblCodiceCampione, "cell 0 1");

		listaCampioniCompleta=GestioneCampioneBO.getListaCampioniCompleta();

		comboCampioni = new JComboBox(listaCampioniCompleta);
		comboCampioni.setFont(new Font("Arial", Font.PLAIN, 14));
		panelCampione.add(comboCampioni, "cell 0 2,growx");


		return panelCampione;
	}



	private JPanel getPannelloProvaRDP() {
		JPanel panel_provaRDP = new JPanel();
		panel_provaRDP.setBorder(new TitledBorder(new LineBorder(Costanti.COLOR_RED, 2, true), "Gestione Prova", TitledBorder.LEADING, TitledBorder.TOP, null, Color.RED));
		panel_provaRDP.setBackground(Color.WHITE);
		panel_provaRDP.setLayout(new MigLayout("", "[80%,grow][][][][pref!,grow]", "[20.00,grow][][][][56px]"));

		scrollPane_2 = new JScrollPane();
		panel_provaRDP.add(scrollPane_2, "cell 0 0 1 4,grow");

		textArea_descrizione_misura = new JTextArea();
		textArea_descrizione_misura.setFont(new Font("Arial", Font.PLAIN, 14));
		textArea_descrizione_misura.setLineWrap(true);
		scrollPane_2.setViewportView(textArea_descrizione_misura);
		textArea_descrizione_misura.setBackground(Color.WHITE);

		lblValoreRilevato_1 = new JLabel("Valore");
		lblValoreRilevato_1.setFont(new Font("Arial", Font.BOLD, 14));
		panel_provaRDP.add(lblValoreRilevato_1, "cell 2 0,alignx right");

		textField_valore_rilevato = new JTextField();
		textField_valore_rilevato.setFont(new Font("Arial", Font.PLAIN, 14));
		panel_provaRDP.add(textField_valore_rilevato, "cell 4 0,growx");
		textField_valore_rilevato.setColumns(10);

		aggiungiTastierino(textField_valore_rilevato, frm);
		
		String[] data= {"POSITIVO","NEGATIVO","N/A"};

		lblEsito_1 = new JLabel("Esito");
		lblEsito_1.setFont(new Font("Arial", Font.BOLD, 14));
		panel_provaRDP.add(lblEsito_1, "cell 2 2,alignx right");

		textField_path_file = new JTextField();
		textField_path_file.setFont(new Font("Arial", Font.PLAIN, 14));
		textField_path_file.setEditable(false);
		panel_provaRDP.add(textField_path_file, "flowx,cell 0 4,growx");
		textField_path_file.setColumns(10);

		btnAggiungi = new JButton("Aggiungi");
		btnAggiungi.setIcon(new ImageIcon(FrameRapportoProva.class.getResource("/image/add.png")));
		btnAggiungi.setFont(new Font("Arial", Font.BOLD, 14));

		btnAggiungi.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent arg0) {

				try {	
					if(textArea_descrizione_misura.getText().length()==0)
					{
						JOptionPane.showMessageDialog(null, "Attenzione descrizione prova assente","Errore Input",JOptionPane.INFORMATION_MESSAGE,new ImageIcon(PannelloTOP.class.getResource("/image/error.png")));
						return;
					}
					if(dlm.size()==0)
					{
						JOptionPane.showMessageDialog(null, "Attenzione lista campioni vuota","Errore Input",JOptionPane.INFORMATION_MESSAGE,new ImageIcon(PannelloTOP.class.getResource("/image/error.png")));
						return;
					}
					String valRil="";
					String descrizioneCampione=getCampioniString(dlm);
					int indiceRiga= table_prove.getModel().getRowCount();

					BigDecimal valoreRilevato = null;

					if(textField_valore_rilevato.getText().length()>0) 
					{
						valoreRilevato= new BigDecimal(textField_valore_rilevato.getText());
						valRil=Utility.BigDecimalStp(new BigDecimal(textField_valore_rilevato.getText())).toString();
					}



					int id= GestioneMisuraBO.insertTabellePerMisuraRDP(
							SessionBO.idMisura,descrizioneCampione,
							textArea_descrizione_misura.getText(),
							valoreRilevato,
							comboEsito.getSelectedItem().toString(),
							file_att);

					DefaultTableModel model =(DefaultTableModel) table_prove.getModel();
					model.addRow(new Object[0]);
					model.setValueAt(id, indiceRiga, 0);
					model.setValueAt(textArea_descrizione_misura.getText(), indiceRiga, 1);
					model.setValueAt(descrizioneCampione, indiceRiga, 2);
					model.setValueAt(valRil, indiceRiga, 3);
					model.setValueAt(comboEsito.getSelectedItem().toString(),indiceRiga, 4);
					table_prove.setModel(model);

					btnVisualizzaAllegato.setEnabled(false);

					DefaultTreeModel modelTree = (DefaultTreeModel)PannelloMisuraMaster.tree.getModel();
					DefaultMutableTreeNode root = (DefaultMutableTreeNode)modelTree.getRoot();
					root.removeAllChildren();

					ProvaMisuraDTO provaReload =  GestioneMisuraBO.getProvaMisura(""+SessionBO.idStrumento);
					PannelloMisuraMaster.disegnaAlbero(provaReload);


				}catch(NumberFormatException nfe)
				{
					textField_valore_rilevato.setText("");
					JOptionPane.showMessageDialog(null, "Attenzione numero inserito formalmente scorretto! \n Formato corretto: xxxx.xx","Errore Input",JOptionPane.INFORMATION_MESSAGE,new ImageIcon(PannelloTOP.class.getResource("/image/error.png")));
				}
				catch (Exception e) {
					e.printStackTrace();
				}  
			}


		});
		panel_provaRDP.add(btnAggiungi, "flowx,cell 4 4,growx,height :28:28");

		btnCarica = new JButton("Carica");
		btnCarica.setIcon(new ImageIcon(FrameRapportoProva.class.getResource("/image/load.png")));
		btnCarica.setFont(new Font("Arial", Font.BOLD, 14));
		btnCarica.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				JFileChooser jfc = new JFileChooser();

				javax.swing.filechooser.FileFilter docFilter = new it.calverDesktopLAT.utl.FileTypeFilter(".pdf", "Documento PDF");
				jfc.addChoosableFileFilter(docFilter);
				jfc.showOpenDialog(GeneralGUI.g);
				File f= jfc.getSelectedFile();
				if(f!=null)
				{
					String ext1 = FilenameUtils.getExtension(f.getPath()); 
					if(ext1.equalsIgnoreCase("pdf"))
					{
						try {

							FileInputStream fis = new FileInputStream(f);
							byte[] buffer = new byte[1024];
							file_att = new ByteArrayOutputStream();
							for (int len; (len = fis.read(buffer)) != -1;) {
								file_att.write(buffer, 0, len);
							}
					
							textField_path_file.setText(f.getPath());
						} catch (FileNotFoundException e) {
							System.err.println(e.getMessage());
						} catch (IOException e2) {
							System.err.println(e2.getMessage());
						}
					}
					else
					{
						JOptionPane.showMessageDialog(null,"Il sistema può caricare solo file in formato PDF","Exstension Error",JOptionPane.INFORMATION_MESSAGE,new ImageIcon(PannelloTOP.class.getResource("/image/error.png")));
					}

				}
			}
		});
		panel_provaRDP.add(btnCarica, "cell 0 4,height :28:28");

		btnModifica = new JButton("Modifica");
		btnModifica.setIcon(new ImageIcon(FrameRapportoProva.class.getResource("/image/update.png")));
		btnModifica.setFont(new Font("Arial", Font.BOLD, 14));
		btnModifica.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {

				try 
				{
					if(textArea_descrizione_misura.getText().length()==0)
					{
						JOptionPane.showMessageDialog(null, "Attenzione descrizione prova assente","Errore Input",JOptionPane.INFORMATION_MESSAGE,new ImageIcon(PannelloTOP.class.getResource("/image/error.png")));
						return;
					}
					if(dlm.size()==0)
					{
						JOptionPane.showMessageDialog(null, "Attenzione lista campioni vuota","Errore Input",JOptionPane.INFORMATION_MESSAGE,new ImageIcon(PannelloTOP.class.getResource("/image/error.png")));
						return;
					}

					BigDecimal valoreRilevato = null;

					if(textField_valore_rilevato.getText().length()>0) 
					{
						valoreRilevato= new BigDecimal(textField_valore_rilevato.getText());

					}

					int indiceRiga = table_prove.getSelectedRow();
					if(indiceRiga!=-1)
					{
						String descrizioneCampione=getCampioniString(dlm);
						int idRecord=Integer.parseInt(table_prove.getValueAt(indiceRiga, 0).toString());

						GestioneMisuraBO.ModificaTabellePerMisuraRDP(
								idRecord,descrizioneCampione,
								textArea_descrizione_misura.getText(),
								valoreRilevato,
								comboEsito.getSelectedItem().toString(),
								file_att);



						DefaultTableModel model =(DefaultTableModel) table_prove.getModel();

						model.setValueAt(idRecord, indiceRiga, 0);
						model.setValueAt(textArea_descrizione_misura.getText(), indiceRiga, 1);
						model.setValueAt(descrizioneCampione, indiceRiga, 2);
						model.setValueAt(valoreRilevato, indiceRiga, 3);
						model.setValueAt(comboEsito.getSelectedItem().toString(),indiceRiga, 4);
						table_prove.setModel(model);



						DefaultTreeModel modelTree = (DefaultTreeModel)PannelloMisuraMaster.tree.getModel();
						DefaultMutableTreeNode root = (DefaultMutableTreeNode)modelTree.getRoot();
						root.removeAllChildren();

						ProvaMisuraDTO provaReload =  GestioneMisuraBO.getProvaMisura(""+SessionBO.idStrumento);
						PannelloMisuraMaster.disegnaAlbero(provaReload);
						
						JOptionPane.showMessageDialog(null, "Modifica effettuata correttamente","Modifica",JOptionPane.INFORMATION_MESSAGE,new ImageIcon(PannelloTOP.class.getResource("/image/confirm.png")));
					}
				}
				catch(NumberFormatException nfe)
				{
					textField_valore_rilevato.setText("");
					JOptionPane.showMessageDialog(null, "Attenzione numero inserito formalmente scorretto! \n Formato corretto: xxxx.xx","Errore Input",JOptionPane.INFORMATION_MESSAGE,new ImageIcon(PannelloTOP.class.getResource("/image/error.png")));
				}
				catch (Exception e) {
					e.printStackTrace();
				}  
			}
		});
		panel_provaRDP.add(btnModifica, "cell 4 4,height :28:28");
		comboEsito = new JComboBox(data);
		comboEsito.setFont(new Font("Arial", Font.PLAIN, 14));
		panel_provaRDP.add(comboEsito, "cell 4 2,growx");

		return panel_provaRDP;
	}

	private JPanel getPanelMisura(ProvaMisuraDTO provaMisuramisura) {

		JPanel panel_tabellaRDP = new JPanel();
		panel_tabellaRDP.setBorder(new TitledBorder(new LineBorder(Costanti.COLOR_RED, 2, true), "Misura Strumento in Verifica", TitledBorder.LEADING, TitledBorder.TOP, null, Color.RED));
		panel_tabellaRDP.setBackground(Color.WHITE);

		panel_tabellaRDP.setLayout(new MigLayout("", "[grow]", "[grow][grow][grow]"));

		btnApriAllegato.setEnabled(false);

		lblListaProveEffetuate = new JLabel("Lista Prove effetuate");
		lblListaProveEffetuate.setFont(new Font("Arial", Font.BOLD, 14));
		panel_tabellaRDP.add(lblListaProveEffetuate, "flowx,cell 0 0");

		scrollPane_1 = new JScrollPane();
		panel_tabellaRDP.add(scrollPane_1, "cell 0 1,grow");

		table_prove = new JTable();
		table_prove.setFont(new Font("Arial", Font.PLAIN, 14));

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
				default:
					return String.class;
				}
			}
		};

		model.addColumn("ID");
		model.addColumn("Descrizione");
		model.addColumn("Campioni Utilizzati");
		model.addColumn("Valore Prova");
		model.addColumn("Esito");

		ArrayList<TabellaMisureDTO> tabella= provaMisuramisura.getListaTabelle();

		int indiceRiga=0;
		for (int i = 0; i < tabella.size(); i++) {
			TabellaMisureDTO tab = tabella.get(i);
			ArrayList<MisuraDTO> listaMisure= tab.getListaMisure();

			for (int j = 0; j < listaMisure.size(); j++) {
				MisuraDTO misura=listaMisure.get(j);
				model.addRow(new Object[0]);
				model.setValueAt(misura.getId(), indiceRiga, 0);
				model.setValueAt(misura.getTipoVerifica(), indiceRiga, 1);
				model.setValueAt(misura.getDescrizioneCampione(), indiceRiga, 2);
				model.setValueAt(Utility.BigDecimalStp(misura.getValoreStrumento()), indiceRiga, 3);
				model.setValueAt(misura.getEsito(),indiceRiga, 4);

				indiceRiga++;
			}
		}
		table_prove.getSelectionModel().addListSelectionListener(new SharedListSelectionHandler());

		table_prove.setModel(model);

		// int index = table_prove.getColumnModel().getColumnIndex("ID");

		table_prove.getColumnModel().getColumn(0).setPreferredWidth(10);
		table_prove.getColumnModel().getColumn(1).setPreferredWidth(300);
		table_prove.getColumnModel().getColumn(2).setPreferredWidth(10);
		table_prove.getColumnModel().getColumn(3).setPreferredWidth(50);
		table_prove.getColumnModel().getColumn(4).setPreferredWidth(10);
		//	 column.setPreferredWidth(10);
		// table_prove.removeColumn(column);

		scrollPane_1.setViewportView(table_prove);

		popupMenu= new JPopupMenu();
		jmit= new JMenuItem("Elimina Prova");
		jmit.addActionListener(this);
		popupMenu.add(jmit);
		table_prove.setComponentPopupMenu(popupMenu);

		btnVisualizzaAllegato = new JButton("Visualizza Allegato");
		btnVisualizzaAllegato.setIcon(new ImageIcon(FrameRapportoProva.class.getResource("/image/attach.png")));
		btnVisualizzaAllegato.setFont(new Font("Arial", Font.BOLD, 14));
		btnVisualizzaAllegato.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {

					int indiceRiga = table_prove.getSelectedRow();
					if(indiceRiga!=-1)
					{

						int idRecord=Integer.parseInt(table_prove.getValueAt(indiceRiga, 0).toString());

						MisuraDTO pt =GestioneMisuraBO.getMisura(idRecord);

						if(pt.getFile_att()==null) 
						{
							JOptionPane.showMessageDialog(null, "Non ci sono allegati da Visualizzare","Attenzione",JOptionPane.INFORMATION_MESSAGE,new ImageIcon(PannelloTOP.class.getResource("/image/attention.png")));
							return;
						}
						JFileChooser jfc = new JFileChooser();
						jfc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
						jfc.showSaveDialog(frm);

						File f= jfc.getSelectedFile();
						if(f!=null)
						{

							String filename=f.getPath()+"\\allegato.pdf";
							FileUtils.writeByteArrayToFile(new File(filename), pt.getFile_att());
							JOptionPane.showMessageDialog(null, "File salvato con successo in \""+filename+"\"","Salvataggio confermato",JOptionPane.INFORMATION_MESSAGE,new ImageIcon(PannelloTOP.class.getResource("/image/confirm.png")));

						}

					}
				} catch (Exception ec) {

					PannelloConsole.printException(ec);
				}
			}
		});
		panel_tabellaRDP.add(btnVisualizzaAllegato, "cell 0 2");



		return panel_tabellaRDP;
	}

	private String getCampioniString(DefaultListModel<String> dlm) {

		String toReturn="";
		for (int i=0;i<dlm.size();i++) {

			toReturn=toReturn+dlm.getElementAt(i).toString()+"|";

		}
		return toReturn.substring(0,toReturn.length()-1);
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
	public class SharedListSelectionHandler implements ListSelectionListener {
		public void valueChanged(ListSelectionEvent e) {
			try {
				ListSelectionModel lsm = (ListSelectionModel)e.getSource();
				if (!lsm.isSelectionEmpty()) 
				{ 
					// Find out which indexes are selected.
					int minIndex = lsm.getMinSelectionIndex();
					int maxIndex = lsm.getMaxSelectionIndex();
					for (int i = minIndex; i <= maxIndex; i++) {
						if (lsm.isSelectedIndex(i)) {

							int idPunto=Integer.parseInt(table_prove.getModel().getValueAt(i, 0).toString());

							punto_misura=GestioneMisuraBO.getMisura(idPunto);

							textField_path_file.setText("");
							file_att=null;
							/*Parte Precedente*/

							if(punto_misura.getMisuraCampionePrecedente()!=null && punto_misura.getMisuraCampionePrecedente().length()>0) 
							{
								textField_rif_cmp_precedente.setText(punto_misura.getMisuraCampionePrecedente());
							}else 
							{
								textField_rif_cmp_precedente.setText("ND");
							}

							if(punto_misura.getProvaPrecedente()!=null && punto_misura.getProvaPrecedente().length()>0) 
							{
								textArea_rif_descrizione_prova_precedente.setText(punto_misura.getProvaPrecedente());
							}else 
							{
								textArea_rif_descrizione_prova_precedente.setText("ND");
							}

							if(punto_misura.getMisuraPrecedente()!=null && punto_misura.getMisuraPrecedente().length()>0) 
							{
								textField_rif_valore_precedente.setText(punto_misura.getMisuraPrecedente());
							}else 
							{
								textField_rif_valore_precedente.setText("ND");
							}
							if(punto_misura.getEsitoPrecedente()!=null && punto_misura.getEsitoPrecedente().length()>0) 
							{
								textField_rif_esito_precedente.setText(punto_misura.getEsitoPrecedente());
							}else 
							{
								textField_rif_esito_precedente.setText("ND");
							}

							if(punto_misura.getFile_att_prec()!=null) 
							{
								btnApriAllegato.setEnabled(true);
							}
							else
							{
								btnApriAllegato.setEnabled(false);
							}

							if(punto_misura.getFile_att()!=null) 
							{
								btnVisualizzaAllegato.setEnabled(true);
							}
							else
							{
								btnVisualizzaAllegato.setEnabled(false);
							}


							/*Parte Corrente*/
							if(table_prove.getModel().getValueAt(i, 1)!=null)
							{
								textArea_descrizione_misura.setText(table_prove.getModel().getValueAt(i, 1).toString());
							}else 
							{
								textArea_descrizione_misura.setText("");
							}

							if(table_prove.getModel().getValueAt(i, 2)!=null)
							{
								compilaListaCampioni(table_prove.getModel().getValueAt(i, 2).toString());
							}

							if(table_prove.getModel().getValueAt(i, 3)!=null)
							{
								textField_valore_rilevato.setText(table_prove.getModel().getValueAt(i, 3).toString());
							}else 
							{
								textField_valore_rilevato.setText("");
							}

							if(table_prove.getModel().getValueAt(i, 4)!=null)
							{
								if(table_prove.getModel().getValueAt(i, 4).toString().equals("IDONEO"))
								{
									comboEsito.setSelectedIndex(0);
								}
								else
								{

									comboEsito.setSelectedIndex(1);
								}

							}

						}
					}
				}
			}catch (Exception ez) {
				ez.printStackTrace();
			}
		}

		private void compilaListaCampioni(String _listaCMP) {

			dlm.removeAllElements();

			String[] listCMP=_listaCMP.split("\\|");

			for (int i = 0; i < listCMP.length; i++) {
				String string = listCMP[i];
				dlm.addElement(string);
			}

		}



	}
	@Override
	public void actionPerformed(ActionEvent event) {

		JMenuItem menu = (JMenuItem) event.getSource();

		if (menu == jmit) {
			eliminaRecordRDP();
		}
	}



	private void eliminaRecordRDP() {

		try
		{
			int selectedRow = table_prove.getSelectedRow();
			if(selectedRow!=-1)
			{
				String idRecord=""+table_prove.getValueAt(selectedRow, 0);

				GestioneMisuraBO.deleteRecordMisura(Integer.parseInt(idRecord));

				DefaultTableModel model =(DefaultTableModel) table_prove.getModel();
				model.removeRow(selectedRow);


				DefaultTreeModel modelTree = (DefaultTreeModel)PannelloMisuraMaster.tree.getModel();
				DefaultMutableTreeNode root = (DefaultMutableTreeNode)modelTree.getRoot();
				root.removeAllChildren();

				ProvaMisuraDTO provaReload =  GestioneMisuraBO.getProvaMisura(""+SessionBO.idStrumento);
				PannelloMisuraMaster.disegnaAlbero(provaReload);

			}
			else
			{
				JOptionPane.showMessageDialog(null, "Selezionare correttamente la riga della prova da eliminare","Attenzione",JOptionPane.INFORMATION_MESSAGE,new ImageIcon(PannelloTOP.class.getResource("/image/attention.png")));
			}
		}catch (Exception e) 
		{
			PannelloConsole.printException(e);
		}	
	}
}
