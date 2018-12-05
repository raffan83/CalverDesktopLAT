package it.calverDesktopLAT.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.Serializable;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.KeyStroke;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import org.apache.commons.io.FilenameUtils;

import it.calverDesktopLAT.bo.GestioneCampioneBO;
import it.calverDesktopLAT.bo.GestioneDB;
import it.calverDesktopLAT.bo.GestioneRegistro;
import it.calverDesktopLAT.bo.SessionBO;
import it.calverDesktopLAT.utl.Costanti;
import net.miginfocom.swing.MigLayout;

public  class GeneralGUI extends JFrame implements Serializable{
	
	/**
	 * 
	 */
	
	private static final long serialVersionUID = 2738670749999075738L;
	public static JFrame g;
	JPanel pannello;
	JMenuBar menuBar;
	JMenu menuFile;
	JMenu menuDB;
	JMenu menuImpostazioni;
	JButton btn;
	
	static JPanel top;
	static JPanel center;
	static JPanel sideBar;
	static JPanel console;
	JMenu menu,menu1,menu2,menuMis;
	JMenuItem adb, it1,it2,itMis,itMisCh,impost,importazione;
	private JButton btnCls;
	
	
	public GeneralGUI() throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException {
	setTitle("DasmTar v"+Costanti.VERSION+" ®");
	
	g=this;
	
	SessionBO.generarFrame=g;
	
	try
	{
	Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();

	SessionBO.widthFrame=(int)dim.getWidth();
	SessionBO.heightFrame=(int)dim.getHeight();
	
	setSize(SessionBO.widthFrame,SessionBO.heightFrame);
	this.setExtendedState(JFrame.MAXIMIZED_BOTH);
	
	getContentPane().setLayout(new MigLayout("", "[grow]", "[12%][73%][15%]"));
	
	UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
	
		 costruisciFrame();
	

	}
	catch (Exception e) {
		e.printStackTrace();
	}
	
	}

	private void costruisciFrame()  {

		
		top =new PannelloTOP(Color.white,"top",g);

		center =new PannelloCore(Color.white,"",g);
		
		console= new PannelloConsole(Color.RED,"core");

		
		JScrollPane scrollTop= new JScrollPane(top);
		this.getContentPane().add(scrollTop,"cell 0 0,grow");
		
		JScrollPane scrollCenter= new JScrollPane(center);
		this.getContentPane().add(scrollCenter,"cell 0 1,grow");
	     
		JScrollPane scrollConsole = new JScrollPane(console);
		this.getContentPane().add(scrollConsole,"cell 0 2,grow");
	    
		
		
		
		
	   
	    
	  
	    
	    JMenuBar menuBar = new JMenuBar();
	    it1= new JMenuItem("Apri file");
	  	it2= new JMenuItem("Chiudi");
	  	setJMenuBar(menuBar);
	  	
	  	menu = new JMenu("File");
	  	menuBar.add(menu);
	  	
		menuMis = new JMenu("Misure");
	  	menuBar.add(menuMis);
	  	
	  	itMis= new JMenuItem("Avvia Misurazione");
	  	itMis.setAccelerator(KeyStroke.getKeyStroke('M', Toolkit.getDefaultToolkit ().getMenuShortcutKeyMask()));
	  	
	  	itMisCh= new JMenuItem("Chiudi Misurazione");
	  	itMis.setAccelerator(KeyStroke.getKeyStroke('M', Toolkit.getDefaultToolkit ().getMenuShortcutKeyMask()));
	  	
	  	menuMis.add(itMis);
	  	menuMis.add(itMisCh);
	  	
	  	menu1 = new JMenu("DataBase");
	  	menuBar.add(menu1);
	  	
	  	adb = new JMenuItem("Apri Database");
	  	adb.setAccelerator(KeyStroke.getKeyStroke('N', Toolkit.getDefaultToolkit ().getMenuShortcutKeyMask()));
	 // 	adb.setEnabled(false);
	  	
	  	menu1.add(adb);
	  	menu.add(it1);
	  	menu.add(it2);
	  	
	  	menu2 = new JMenu("Impostazioni");
	  	
	  	
	  	menuBar.add(menu2);
	    impost= new JMenuItem("Opzioni");
	    importazione = new JMenuItem("Importa campioni");
	    menu2.add(impost);
	    menu2.add(importazione);
	    
	  //  Costanti.PATH_DB="C:\\Users\\raffaele.fantini\\Desktop\\CM413225052017122620.db";
	    
	    /*
	     * Gestione Metodi
	     */
	    adb.addActionListener(new ActionListener() {
	    		
	    		public void actionPerformed(ActionEvent e) {
	    			
	    			if(!Costanti.PATH_DB.equals(""))
	    			{
	    				JPanel panelDB =new PannelloDB();
	    				
	    				SystemGUI.callPanel(panelDB, "PDB");
	    				
	    				
	    			}else
	    			{
	    				JOptionPane.showMessageDialog(null,"File .db non caricato","File Not Found",JOptionPane.INFORMATION_MESSAGE,new ImageIcon(PannelloTOP.class.getResource("/image/error.png")));
	    			}
	    		}
	    	});

	    it2.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				
				g.dispatchEvent(new WindowEvent(g, WindowEvent.WINDOW_CLOSING));
				
			}
		});
	    
	    it1.addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent arg0) {
	    			
	    				String last_path="";
	    				
	    				if(GestioneRegistro.esixt(Costanti.COD_LAST_PATH))
	    				{
	    					last_path=GestioneRegistro.getStringValue(Costanti.COD_LAST_PATH);
	    				}
	    		
	    				JFileChooser jfc = new JFileChooser(last_path);
	    				
	    				javax.swing.filechooser.FileFilter docFilter = new it.calverDesktopLAT.utl.FileTypeFilter(".db", "Calver database");
	    				jfc.addChoosableFileFilter(docFilter);
	    				jfc.showOpenDialog(g);
	    				File f= jfc.getSelectedFile();
	    				if(f!=null)
	    				{
	    				String path=f.getPath();
	    				String ext1 = FilenameUtils.getExtension(path); 
	    				if(ext1.equals("db"))
	    				{
	    					GestioneRegistro.setStringValue(Costanti.COD_LAST_PATH, f.getParent());
	    					Costanti.PATH_DB=path;
	    					PannelloConsole.printArea("Caricato file: "+path);
	    				}
	    				else
	    				{
	    					JOptionPane.showMessageDialog(null,"Il sistema accetta solo file .db","File Not Found",JOptionPane.INFORMATION_MESSAGE,new ImageIcon(PannelloTOP.class.getResource("/image/error.png")));
	    				}
	    				
	    				}
	    				g.update(g.getGraphics());

	    		
	    	}
	    });
	    
	    itMis.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {

				    	try {
				    		
				    		if(Costanti.PATH_DB.length()!=0)
				    		{
				    			
				    		boolean execute=GestioneDB.checkFile(Costanti.PATH_DB);
				    		
					    		if(execute)
					    		{
					    			JPanel panelDB =new PannelloStrumentoMaster();
			    				
					    			SystemGUI.callPanel(panelDB, "PSS");
					    		}else
					    		{
					    			JOptionPane.showMessageDialog(null,"File già chiuso - non utilizzabile","File chiuso",JOptionPane.INFORMATION_MESSAGE,new ImageIcon(PannelloTOP.class.getResource("/image/error.png")));
					    		}
				    		}else
				    		{
				    			JOptionPane.showMessageDialog(null,"Nessun file selezionato","Nessun file ... ",JOptionPane.INFORMATION_MESSAGE,new ImageIcon(PannelloTOP.class.getResource("/image/error.png")));
				    		}
		    		        
				        } catch (Exception e) 
				        {
				        	e.printStackTrace();
				        }
				    
				
			}
		});
	    
	    itMisCh.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
			
			try
			{	
				int scelta = JOptionPane.showConfirmDialog(null, "Con questa operazione il programma verra chiuso e le misure non saranno più modificabili. \n Confermi la chiusura?", "Chiusura Misure",JOptionPane.YES_NO_OPTION,JOptionPane.INFORMATION_MESSAGE,new ImageIcon(PannelloTOP.class.getResource("/image/attention.png")));
				
				if(scelta==0)
				{
					
					boolean control=GestioneDB.controlloMisuraCertificato();
					
					if(control)
					{
						GestioneDB.chiudiMisura(Costanti.PATH_DB);
						g.dispatchEvent(new WindowEvent(g, WindowEvent.WINDOW_CLOSING));
					}
					else
					{
						JOptionPane.showMessageDialog(null,"Sono presenti misure terminate senza il corrispondente certificato","Stampa Certificati",JOptionPane.INFORMATION_MESSAGE,new ImageIcon(PannelloTOP.class.getResource("/image/attention.png")));
					}
				}
			
		  }catch (Exception e) 
		  {
			e.printStackTrace();
		  }
		}
		});
	    
	    impost.addActionListener(new ActionListener() {
    		
    		public void actionPerformed(ActionEvent e) {
    			
    				JPanel panelDB =new PannelloImpostazioni();
    				
    				SystemGUI.callPanel(panelDB, "PIM");
    				
    				
    			
    		}
    	});
	    
	    importazione.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				
				JFileChooser jfc = new JFileChooser();
				
				javax.swing.filechooser.FileFilter docFilter = new it.calverDesktopLAT.utl.FileTypeFilter(".csv", "CSV - Campioni");
				jfc.addChoosableFileFilter(docFilter);
				jfc.showOpenDialog(g);
				File f= jfc.getSelectedFile();
				if(f!=null)
				{
				String path=f.getPath();
				
				try {
					if(!Costanti.PATH_DB.equals(""))
					{
						boolean res=GestioneCampioneBO.importaCampioni(path);
						
						if(res)
						{
							JOptionPane.showMessageDialog(null, "Importazione completata", "Importazione",JOptionPane.INFORMATION_MESSAGE,new ImageIcon(PannelloTOP.class.getResource("/image/confirm.png")));
						}
					}
					else
					{
						JOptionPane.showMessageDialog(null, "Devi prima selezionare un file .db","Errore",JOptionPane.INFORMATION_MESSAGE,new ImageIcon(PannelloTOP.class.getResource("/image/error.png")));
					}
				} catch (Exception e) 
				{
					PannelloConsole.printException(e);
				}
				}
				g.update(g.getGraphics());	
			}
		});
	    
	    
	    
	}
}
