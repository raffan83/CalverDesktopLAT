package it.calverDesktopLAT.gui;


import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.LineBorder;

import gnu.io.NoSuchPortException;
import gnu.io.PortInUseException;
import it.calverDesktopLAT.bo.GestioneDASM;
import it.calverDesktopLAT.bo.GestioneRegistro;
import it.calverDesktopLAT.bo.TwoWaySerialComm;
import it.calverDesktopLAT.dto.DatiDASM_DTO;
import it.calverDesktopLAT.utl.Costanti;
import net.miginfocom.swing.MigLayout;
import javax.swing.ImageIcon;

public class PannelloImpostazioni extends JPanel {

	JTextField fieldFrameRate=null;
	JTextField fieldNomePorta=null;
	Splash d=null;
	JPanel p =null;
	JTabbedPane tab=null;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	public PannelloImpostazioni()
	{
		//p=this;
		setLayout(null);
		setBackground(Color.white);
		costruisciPannello();
		p=this;
		
	}

	private void costruisciPannello() {
		
		DatiDASM_DTO dati =GestioneDASM.getDatiPorta();
		
		if(dati==null)
		{
			JOptionPane.showMessageDialog(null, "Controllare la presenza della libreria infoCalver.db in ProgramData","Libreria non trovata",JOptionPane.INFORMATION_MESSAGE,new ImageIcon(PannelloTOP.class.getResource("/image/error.png")));
			
			return;
		}
		JTabbedPane tabbedPane = new JTabbedPane();
		tabbedPane.setFont(new Font("Arial", Font.BOLD, 14));
		tabbedPane.setBackground(Color.WHITE);
		tabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);

		tabbedPane.setVisible(true);
		tabbedPane.setBounds(10, 11, 316, 400);


		//this.add(datiDasm);
		//this.add(nomePorta);
		//this.add(fieldNomePorta);
//		this.add(frameRate);
//		this.add(fieldFrameRate);
//		this.add(test);
//		this.add(salva);
		
		
		this.add(tabbedPane);
		
		JPanel panelDASM = new JPanel();
		panelDASM.setBorder(new LineBorder(Color.RED, 2, true));
		panelDASM.setBackground(Color.WHITE);
		JLabel datiDasm = new JLabel("Dati DASM");
		datiDasm.setBounds(10,10,150,30);
		datiDasm.setFont(new Font("Arial", Font.BOLD, 14));
		panelDASM.setLayout(new MigLayout("", "[72px][63px,grow]", "[23px][30px:30px:30px][][30px:30px:30px][][25px][][30px][30px:30px:30px][25px]"));
		
		
		
		panelDASM.add(datiDasm, "cell 0 0,alignx left,aligny center");
		
				
				
				//panelDASM.setVisible(true);
				tabbedPane.addTab("Dati DASM", panelDASM);
				 
				 JLabel nomePorta = new JLabel("Nome Porta: ");
				 nomePorta.setFont(new Font("Arial", Font.BOLD, 12));
				 nomePorta.setBounds(10,50,70,25);
				 panelDASM.add(nomePorta, "cell 0 2,alignx left,aligny center");
				 
				 fieldNomePorta = new JTextField();
				 fieldNomePorta.setFont(new Font("Arial", Font.PLAIN, 14));
				 fieldNomePorta.setBounds(75, 50, 100, 25);
				 
				 fieldNomePorta.setText(dati.getPorta());
				 panelDASM.add(fieldNomePorta, "cell 1 2,width :100:");
				 
				 final JLabel frameRate = new JLabel("Frame Rate: ");
				 frameRate.setFont(new Font("Arial", Font.BOLD, 12));
				 frameRate.setBounds(10,90,70,25);
				 panelDASM.add(frameRate, "cell 0 4,alignx left,aligny center");
				 
				 fieldFrameRate = new JTextField();
				 fieldFrameRate.setText("0");
				 fieldFrameRate.setFont(new Font("Arial", Font.PLAIN, 14));
				 fieldFrameRate.setBounds(75, 90, 75, 25);
				 panelDASM.add(fieldFrameRate, "cell 1 4,width :100:");
				 
				 JButton test = new JButton("Test connessione");
				 test.setIcon(new ImageIcon(PannelloImpostazioni.class.getResource("/image/connection.png")));
				 test.setFont(new Font("Arial", Font.BOLD, 14));
				 test.setBounds(10,130,160,25);
				 
				 test.addActionListener(new ActionListener() {
				 	
				 	@Override
				 	public void actionPerformed(ActionEvent e) {
				 		
				 		
				 	
				 			d =	 new Splash(p);
				 			d.execute();
				 			Thread thread = new Thread(new ValidateThread());
				 			thread.start();
				 		
				 		}
				 	
				 });
				 panelDASM.add(test, "cell 0 6 2 1,alignx left");
				 
				  
				  //panelPrint.add(buttonGroup, "cell 1 2");
				  
				  JButton salva = new JButton("Salva");
				  salva.setIcon(new ImageIcon(PannelloImpostazioni.class.getResource("/image/save.png")));
				  salva.setFont(new Font("Arial", Font.BOLD, 14));
				  salva.setBounds(10,160,125,25);
				  
				  
				  salva.addActionListener(new ActionListener() {
				  	
				  	@Override
				  	public void actionPerformed(ActionEvent e) {
				  		
				  		try 
				  		{
				  			int frameR= Integer.parseInt(fieldFrameRate.getText());
				  			GestioneDASM.saveDatiDasm(fieldNomePorta.getText(),frameR);
				  			
				  			JOptionPane.showMessageDialog(null, "Salvataggio effettuato","Salvataggio",JOptionPane.INFORMATION_MESSAGE,new ImageIcon(PannelloTOP.class.getResource("/image/confirm.png")));
				  		}
				  		catch (NumberFormatException e2) 
				  		{
				  			JOptionPane.showMessageDialog(null, "Frame Rate deve essere di tipo numerico","Errore",JOptionPane.INFORMATION_MESSAGE,new ImageIcon(PannelloTOP.class.getResource("/image/error.png")));
				  			e2.printStackTrace();
				  		}
				  		
				  	}
				  });
				  panelDASM.add(salva, "cell 0 8 2 1,alignx left");
				 JPanel panelPrint = new JPanel();
				 panelPrint.setBackground(UIManager.getColor("Tree.background"));
				 panelPrint.setPreferredSize(new Dimension(150,150));
				 panelPrint.setBorder(new LineBorder(UIManager.getColor("ToolBar.dockingForeground"), 2, true));
				 tabbedPane.addTab("Stampanti", panelPrint);
				 tabbedPane.setEnabledAt(1, true);
				 panelPrint.setLayout(new MigLayout("", "[][]", "[][30px:30:30px][][30px:30px:30px][][25px][27px][27px][30px:30px:30px][21.00][25px][25px]"));
				 
				 JLabel lblSelezionaLaStampante = new JLabel("Seleziona la stampante");
				 lblSelezionaLaStampante.setBackground(Color.WHITE);
				 lblSelezionaLaStampante.setFont(new Font("Tahoma", Font.BOLD, 14));
				 panelPrint.add(lblSelezionaLaStampante, "cell 0 0");
				 
				 JLabel lblZebraZq = new JLabel("Zebra ZQ510");
				 lblZebraZq.setFont(new Font("Tahoma", Font.BOLD, 12));
				 panelPrint.add(lblZebraZq, "cell 0 2,aligny center");
				 
				 final JRadioButton rdbtnZebraradiobtn = new JRadioButton("");
				 rdbtnZebraradiobtn.setBackground(Color.WHITE);
				 buttonGroup.add(rdbtnZebraradiobtn);
				 
				 panelPrint.add(rdbtnZebraradiobtn, "cell 1 2");
				
				 String printTP=GestioneRegistro.getStringValue(Costanti.COD_PRINT);
				 
				
				 rdbtnZebraradiobtn.setSelected(true);
				  
				  JLabel lblDymo = new JLabel("Dymo Label Writer 450");
				  lblDymo.setFont(new Font("Tahoma", Font.BOLD, 12));
				  panelPrint.add(lblDymo, "cell 0 4,aligny center");
				  
				  final JRadioButton rdbtnDymoradiobtn = new JRadioButton("");
				  rdbtnDymoradiobtn.setBackground(Color.WHITE);
				  buttonGroup.add(rdbtnDymoradiobtn);
				  panelPrint.add(rdbtnDymoradiobtn, "cell 1 4");
				  
				  
				  if(printTP.equals("1")) 
					 {
						 rdbtnZebraradiobtn.setSelected(true);
						 rdbtnDymoradiobtn.setSelected(false);
					 }else 
					 {
						 rdbtnZebraradiobtn.setSelected(false);
						 rdbtnDymoradiobtn.setSelected(true);
					 } 
				  
				  JButton salvaPrint = new JButton("Salva");
				  salvaPrint.setIcon(new ImageIcon(PannelloImpostazioni.class.getResource("/image/save.png")));
				  salvaPrint.setFont(new Font("Arial", Font.BOLD, 14));
				  salvaPrint.addActionListener(new ActionListener() {
				  	public void actionPerformed(ActionEvent e) {
				  		String printType=null;
				  	
				  		
				  		if(rdbtnZebraradiobtn.isSelected()) {
				  			printType="1";
				  		}
				  		else if(rdbtnDymoradiobtn.isSelected()) {
				  			printType="2";
				  		}
				  		
				  		GestioneRegistro.setStringValue(Costanti.COD_PRINT, printType);
				  		JOptionPane.showMessageDialog(null, "Salvataggio effettuato","Salvataggio",JOptionPane.INFORMATION_MESSAGE,new ImageIcon(PannelloTOP.class.getResource("/image/confirm.png")));
				  	}
				  });
				  panelPrint.add(salvaPrint, "cell 0 8,alignx left");
		
		
		
	}
	
	

class ValidateThread implements Runnable {
	public void run() {
		
		try
		{	
				TwoWaySerialComm com = new TwoWaySerialComm();
				int fr=Integer.parseInt(fieldFrameRate.getText().trim());

				
				com.connect(fieldNomePorta.getText().trim(),fr);
			
				com.disconnect();
				
				d.close();
				JOptionPane.showMessageDialog(null, "Dispositivo connesso","Success",JOptionPane.INFORMATION_MESSAGE,new ImageIcon(PannelloTOP.class.getResource("/image/confirm.png")));
			}
		catch(PortInUseException pex) 
		{
			d.close();
			JOptionPane.showMessageDialog(null, "Porta in uso - Provare a spegnere e riaccendere il dispositivo ","Errore",JOptionPane.INFORMATION_MESSAGE,new ImageIcon(PannelloTOP.class.getResource("/image/error.png")));
		}
			catch (NoSuchPortException nse) 
			{
				d.close();
				JOptionPane.showMessageDialog(null, "Nessun dispositivo collegato sulla porta seriale "+fieldNomePorta.getText().trim(),"Errore",JOptionPane.INFORMATION_MESSAGE,new ImageIcon(PannelloTOP.class.getResource("/image/error.png")));
			}
			catch(NumberFormatException nfe)
			{
				d.close();
				JOptionPane.showMessageDialog(null, "Frame Rate deve essere di tipo numerico","Errore",JOptionPane.INFORMATION_MESSAGE,new ImageIcon(PannelloTOP.class.getResource("/image/error.png")));
				nfe.printStackTrace();
			}
			catch (UnsatisfiedLinkError e2) 
			{
				d.close();
				JOptionPane.showMessageDialog(null, "Libreria rxtxSerial.dll mancante o versione errata (32 / 64 bit )","Errore",JOptionPane.INFORMATION_MESSAGE,new ImageIcon(PannelloTOP.class.getResource("/image/error.png")));
				e2.printStackTrace();
			}
			catch (Exception er) 
			{
				d.close();
				JOptionPane.showMessageDialog(null,er.getMessage(),"Errore",JOptionPane.INFORMATION_MESSAGE,new ImageIcon(PannelloTOP.class.getResource("/image/error.png")));
				er.printStackTrace();
			}
			
		}
		
	}
	}

