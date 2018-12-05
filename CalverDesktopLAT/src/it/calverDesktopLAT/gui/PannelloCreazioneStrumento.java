package it.calverDesktopLAT.gui;

import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.plaf.basic.BasicComboBoxRenderer;

import it.calverDesktopLAT.bo.GestioneStrumentoBO;
import it.calverDesktopLAT.bo.SessionBO;
import it.calverDesktopLAT.dao.SQLiteDAO;
import it.calverDesktopLAT.dto.ClassificazioneDTO;
import it.calverDesktopLAT.dto.LuogoVerificaDTO;
import it.calverDesktopLAT.dto.StrumentoDTO;
import it.calverDesktopLAT.dto.TipoRapportoDTO;
import it.calverDesktopLAT.dto.TipoStrumentoDTO;
import it.calverDesktopLAT.utl.Costanti;
import net.miginfocom.swing.MigLayout;

public class PannelloCreazioneStrumento extends JPanel{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField textField_denominazione;
	private JTextField textField_codiceInterno;
	private JTextField textField_costruttore;
	private JTextField textField_modello;
	private JTextField textField_matricola;
	private JTextField textField_risoluzione;
	private JTextField textField_campoMisura;
	private JTextField textField_freqMesi;
	private JTextField txtInServizio;
	private JTextField textField_utilizzatore;
	private JTextField textField_procedura;
	private BufferedImage img;
	private int widthImg;
	private int heightImg;
	 static JFrame myFrame=null;
	
	 @SuppressWarnings("unchecked")
	public PannelloCreazioneStrumento() {
		
		
		myFrame=SessionBO.generarFrame;
		URL imageURL = GeneralGUI.class.getResource("/image/creaStr.png");
		try {
			setImage(ImageIO.read(imageURL));
		} catch (IOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		
		SessionBO.prevPage="PSS";
		
	//	setBackground(Costanti.backgroundGrey);
		setLayout(new MigLayout("", "[86.00][450][][50px][][450]", "[][40][40][40][40][40][40][40][40][][50][]"));
		
		JLabel lblCreazioneStrumentoIn = new JLabel("Creazione Strumento");
		lblCreazioneStrumentoIn.setFont(new Font("Arial", Font.ITALIC, 22));
		add(lblCreazioneStrumentoIn, "cell 0 0 6 1,alignx center,aligny center");
		
		JLabel lblDenominazione = new JLabel("Denominazione");
		lblDenominazione.setFont(new Font("Arial", Font.BOLD, 18));
		add(lblDenominazione, "cell 0 1,alignx right");
		
		textField_denominazione = new JTextField();
		textField_denominazione.setFont(new Font("Arial", Font.PLAIN, 18));
		add(textField_denominazione, "cell 1 1,growx");
		textField_denominazione.setColumns(10);
		
		JLabel lblCampoMisura = new JLabel("Campo Misura");
		lblCampoMisura.setFont(new Font("Arial", Font.BOLD, 18));
		add(lblCampoMisura, "flowx,cell 4 1,alignx right");
		
		textField_campoMisura = new JTextField();
		textField_campoMisura.setFont(new Font("Arial", Font.PLAIN, 18));
		textField_campoMisura.setColumns(10);
		add(textField_campoMisura, "cell 5 1,width :200:");
		
		JLabel lblCodiceInterno = new JLabel("Codice Interno");
		lblCodiceInterno.setFont(new Font("Arial", Font.BOLD, 18));
		add(lblCodiceInterno, "cell 0 2,alignx right");
		
		textField_codiceInterno = new JTextField();
		textField_codiceInterno.setDocument(new JTextFieldLimit(22));
		textField_codiceInterno.setFont(new Font("Arial", Font.PLAIN, 18));
		textField_codiceInterno.setColumns(10);
		add(textField_codiceInterno, "cell 1 2,width :250:");
		
		JLabel lblmaxCar = new JLabel("(max. 22 caratteri)");
		lblmaxCar.setFont(new Font("Arial", Font.PLAIN, 14));
		add(lblmaxCar, "cell 1 2");
		
		JLabel lblFreqVerifica = new JLabel("Freq Verifica");
		lblFreqVerifica.setFont(new Font("Arial", Font.BOLD, 18));
		add(lblFreqVerifica, "cell 4 2,alignx right");
		
		textField_freqMesi = new JTextField();
		textField_freqMesi.setFont(new Font("Arial", Font.PLAIN, 18));
		textField_freqMesi.setColumns(10);
		add(textField_freqMesi, "flowx,cell 5 2,width :80:");
		
		JLabel lblMatricola = new JLabel("Matricola");
		lblMatricola.setFont(new Font("Arial", Font.BOLD, 18));
		add(lblMatricola, "cell 0 3,alignx right");
		
		textField_matricola = new JTextField();
		textField_matricola.setDocument(new JTextFieldLimit(22));
		textField_matricola.setFont(new Font("Arial", Font.PLAIN, 18));
		textField_matricola.setColumns(10);
		add(textField_matricola, "flowx,cell 1 3,width :250:");
		
		JLabel lblReparto = new JLabel("Reparto");
		lblReparto.setFont(new Font("Arial", Font.BOLD, 18));
		add(lblReparto, "cell 4 3,alignx right");
		
		final JTextField textField_reparto = new JTextField();
		textField_reparto.setFont(new Font("Arial", Font.PLAIN, 18));
		textField_reparto.setColumns(10);
		add(textField_reparto, "cell 5 3,growx");
		
		JLabel lblCostruttore = new JLabel("Costruttore");
		lblCostruttore.setFont(new Font("Arial", Font.BOLD, 18));
		add(lblCostruttore, "cell 0 4,alignx right");
		
		textField_costruttore = new JTextField();
		textField_costruttore.setFont(new Font("Arial", Font.PLAIN, 18));
		textField_costruttore.setColumns(10);
		add(textField_costruttore, "cell 1 4,growx");
		
		JLabel lblUtilizzatore = new JLabel("Utilizzatore");
		lblUtilizzatore.setFont(new Font("Arial", Font.BOLD, 18));
		add(lblUtilizzatore, "cell 4 4,alignx right");
		
		textField_utilizzatore = new JTextField();
		textField_utilizzatore.setFont(new Font("Arial", Font.PLAIN, 18));
		textField_utilizzatore.setColumns(10);
		add(textField_utilizzatore, "cell 5 4,growx");
		
		JLabel lblModello = new JLabel("Modello");
		lblModello.setFont(new Font("Arial", Font.BOLD, 18));
		add(lblModello, "cell 0 5,alignx right");
		
		textField_modello = new JTextField();
		textField_modello.setFont(new Font("Arial", Font.PLAIN, 18));
		textField_modello.setColumns(10);
		add(textField_modello, "cell 1 5,growx");
		
		JLabel lblProcedura = new JLabel("Procedura");
		lblProcedura.setFont(new Font("Arial", Font.BOLD, 18));
		add(lblProcedura, "cell 4 5,alignx right");
		
		textField_procedura = new JTextField();
		textField_procedura.setFont(new Font("Arial", Font.PLAIN, 18));
		textField_procedura.setColumns(10);
		add(textField_procedura, "cell 5 5,growx");
		
		JLabel lblRisoluzione = new JLabel("Risoluzione");
		lblRisoluzione.setFont(new Font("Arial", Font.BOLD, 18));
		add(lblRisoluzione, "cell 0 6,alignx right");
		
		textField_risoluzione = new JTextField();
		textField_risoluzione.setFont(new Font("Arial", Font.PLAIN, 18));
		textField_risoluzione.setColumns(10);
		add(textField_risoluzione, "cell 1 6,width :200:");
		
		JLabel lblTipoRapporto = new JLabel("Tipo Rapporto");
		lblTipoRapporto.setFont(new Font("Arial", Font.BOLD, 18));
		add(lblTipoRapporto, "cell 4 6,alignx right");
		
		final JComboBox<TipoRapportoDTO> comboBox_tipoRapp = new JComboBox<TipoRapportoDTO>(SQLiteDAO.getVectorTipoRapporto());
		comboBox_tipoRapp.setFont(new Font("Arial", Font.PLAIN, 18));
		comboBox_tipoRapp.setSelectedIndex(1);
		comboBox_tipoRapp.setRenderer( new ItemRendererTipoRapporto());
		add(comboBox_tipoRapp, "cell 5 6,width :100:");
		
		comboBox_tipoRapp.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				TipoRapportoDTO trp=(TipoRapportoDTO)comboBox_tipoRapp.getSelectedItem();
				if(!trp.getDescrizione().equals(Costanti.SVT))
				{
					textField_freqMesi.setText("0");
					textField_freqMesi.setEnabled(false);
				}
				else
				{
					textField_freqMesi.setText("");
					textField_freqMesi.setEnabled(true);
				}
				
			}
		});
		
		JLabel lblClassificazione = new JLabel("Classificazione");
		lblClassificazione.setFont(new Font("Arial", Font.BOLD, 18));
		add(lblClassificazione, "cell 0 7,alignx right");
		
		final JComboBox<ClassificazioneDTO> comboBox_class = new JComboBox<ClassificazioneDTO>(SQLiteDAO.getVectorClassificazione());
		comboBox_class.setFont(new Font("Arial", Font.PLAIN, 18));
		comboBox_class.setRenderer( new ItemRendererClass());
		add(comboBox_class, "cell 1 7,width :100:");
		
		JLabel lblStatoStrumento = new JLabel("Stato Strumento");
		lblStatoStrumento.setFont(new Font("Arial", Font.BOLD, 18));
		add(lblStatoStrumento, "cell 4 7,alignx right");
		
		txtInServizio = new JTextField();
		txtInServizio.setFont(new Font("Arial", Font.PLAIN, 18));
		txtInServizio.setEnabled(false);
		txtInServizio.setText("In servizio");
		txtInServizio.setColumns(10);
		add(txtInServizio, "cell 5 7,width :100:");
		
		JLabel lblTipoStrumento = new JLabel("Tipo Strumento");
		lblTipoStrumento.setFont(new Font("Arial", Font.BOLD, 18));
		add(lblTipoStrumento, "cell 0 8,alignx right");
		
		final JComboBox<TipoStrumentoDTO> comboBox_tipoStrumento = new JComboBox<TipoStrumentoDTO>(SQLiteDAO.getVectorTipoStrumento());
		comboBox_tipoStrumento.setFont(new Font("Arial", Font.PLAIN, 18));
		comboBox_tipoStrumento.setRenderer( new ItemRendererTipoStrumento());
		add(comboBox_tipoStrumento, "cell 1 8,width :350:");
		
		JLabel lblLuogoVerifica = new JLabel("Luogo Verifica");
		lblLuogoVerifica.setFont(new Font("Arial", Font.BOLD, 18));
		add(lblLuogoVerifica, "cell 4 8,alignx right");
		
		final JComboBox<LuogoVerificaDTO> comboBox_luogoVerifica = new JComboBox<LuogoVerificaDTO>(SQLiteDAO.getVectorLuogoVerifica());
		comboBox_luogoVerifica.setFont(new Font("Arial", Font.PLAIN, 18));
		comboBox_luogoVerifica.setRenderer( new ItemRendererLuogoVerifica());
		add(comboBox_luogoVerifica, "cell 5 8,width :100:");
		
		JLabel lblNote = new JLabel("Note");
		lblNote.setFont(new Font("Arial", Font.BOLD, 18));
		add(lblNote, "cell 0 9,alignx right,aligny top");
		
		JScrollPane scrollPane = new JScrollPane();
		add(scrollPane, "cell 1 9 5 1,grow");
		
		final JTextArea textArea_note = new JTextArea();
		textArea_note.setFont(new Font("Arial", Font.PLAIN, 18));
		textArea_note.setLineWrap(true);
		textArea_note.setRows(5);
		scrollPane.setViewportView(textArea_note);
		textArea_note.setColumns(5);
		
		JButton btnInserisci = new JButton("Inserisci");
		btnInserisci.setFont(new Font("Arial", Font.BOLD, 18));
		btnInserisci.setIcon(new ImageIcon(PannelloTOP.class.getResource("/image/insert.png")));
		add(btnInserisci, "cell 0 10 6 1,alignx center,height :50:");
		
		JLabel lblmesi = new JLabel("(Mesi)");
		add(lblmesi, "cell 5 2");
		lblmesi.setFont(new Font("Arial",Font.PLAIN,14));
		
		JLabel lblmaxCaratteri = new JLabel("(max. 22 caratteri)");
		lblmaxCaratteri.setFont(new Font("Arial", Font.PLAIN, 14));
		add(lblmaxCaratteri, "cell 1 3");
		
		btnInserisci.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				StrumentoDTO strumento = new StrumentoDTO();
				
				if(textField_codiceInterno.getText().length()==0 && textField_matricola.getText().length()==0)
				{
					JOptionPane.showMessageDialog(null,"Indicare Codice Interno o Matricola","Errore",JOptionPane.ERROR_MESSAGE,new ImageIcon(PannelloTOP.class.getResource("/image/error.png")));
			    	return;
				}
				
				strumento.setDenominazione(textField_denominazione.getText());
				strumento.setCodice_interno(textField_codiceInterno.getText());
				strumento.setCostruttore(textField_costruttore.getText());
				strumento.setModello(textField_modello.getText());
			    ClassificazioneDTO item = (ClassificazioneDTO)comboBox_class.getSelectedItem();
			    strumento.setClassificazione(""+item.getId());
			    strumento.setMatricola(textField_matricola.getText());
			    strumento.setRisoluzione(textField_risoluzione.getText());
			    strumento.setCampo_misura(textField_campoMisura.getText());  
			    try
			    {
			    	Integer.parseInt(textField_freqMesi.getText());
			    	strumento.setFreq_taratura(Integer.parseInt(textField_freqMesi.getText()));
			    }catch (NumberFormatException ex) {
					JOptionPane.showMessageDialog(null,"Il campo Frequenza Mesi accetta solo numeri","Errore",JOptionPane.ERROR_MESSAGE,new ImageIcon(PannelloTOP.class.getResource("/image/error.png")));
					return;
			    }
			    TipoRapportoDTO item1 = (TipoRapportoDTO)comboBox_tipoRapp.getSelectedItem();
			    
			    
			    strumento.setTipoRapporto(""+item1.getId());
			    
			    strumento.setStatoStrumento("In servizio");
			    strumento.setReparto(textField_reparto.getText());
			    strumento.setUtilizzatore(textField_utilizzatore.getText());
			    strumento.setProcedura(textField_procedura.getText());
			    
			    TipoStrumentoDTO tipo=(TipoStrumentoDTO)comboBox_tipoStrumento.getSelectedItem();

			    if(tipo.getId()!=0)
			    {
			    	strumento.setId_tipoStrumento(""+tipo.getId());
			    }
			    else 
			    {
			    	JOptionPane.showMessageDialog(null,"Selezionare tipo strumento","Errore",JOptionPane.ERROR_MESSAGE,new ImageIcon(PannelloTOP.class.getResource("/image/error.png")));
			    	return;
			    }
			    LuogoVerificaDTO luogo = (LuogoVerificaDTO)comboBox_luogoVerifica.getSelectedItem();
			   
			    strumento.setLuogoVerifica(luogo.getId());
			    
			    strumento.setNote(textArea_note.getText());
			    
			    int id=0;
			    try {
				id =	GestioneStrumentoBO.insertStrumento(strumento);
				} catch (Exception e1)
				{
					
					JOptionPane.showMessageDialog(null,"Errore inserimento strumento "+e1.getMessage()  ,"Errore",JOptionPane.ERROR_MESSAGE,new ImageIcon(PannelloTOP.class.getResource("/image/error.png")));
					e1.printStackTrace();
					return;
				}
			    if(id>0)
			    {
			    	JOptionPane.showMessageDialog(null,"Inserimento completato correttamente - ID Strumento: "+id,"Strumento",JOptionPane.INFORMATION_MESSAGE,new ImageIcon(PannelloTOP.class.getResource("/image/confirm.png")));
			    }
			    JPanel panelDB =new PannelloStrumentoMaster();
			    
			    GeneralGUI.center.removeAll();
			    GeneralGUI.center.add(panelDB);
			    GeneralGUI.center.repaint();
				
			}
		});
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
		  

	 class ItemRendererClass extends BasicComboBoxRenderer
	    {
	        /**
		 * 
		 */
		private static final long serialVersionUID = 1L;

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
	 
	 class ItemRendererLuogoVerifica extends BasicComboBoxRenderer
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
}
