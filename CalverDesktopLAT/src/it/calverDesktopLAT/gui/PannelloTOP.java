package it.calverDesktopLAT.gui;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.net.URL;

import javax.swing.ButtonModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JToolBar;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.apache.commons.io.FilenameUtils;

import gnu.io.NoSuchPortException;
import gnu.io.PortInUseException;
import it.calverDesktopLAT.bo.GestioneDB;
import it.calverDesktopLAT.bo.GestioneRegistro;
import it.calverDesktopLAT.bo.SessionBO;
import it.calverDesktopLAT.bo.TwoWaySerialComm;
import it.calverDesktopLAT.dao.SQLiteDAO;
import it.calverDesktopLAT.gui.PannelloImpostazioni.ValidateThread;
import it.calverDesktopLAT.utl.Costanti;
import net.miginfocom.swing.MigLayout;
import sun.rmi.runtime.NewThreadAction;

public class PannelloTOP extends JPanel {

	/**
	 * Create the panel.
	 */
	private Splash d=null;
	private JPanel me;
	public PannelloTOP(Color color,String title, JFrame g) {
			
		me=this;
			setBackground(Costanti.backgroundGrey);
			//setBorder(new LineBorder(new Color(255, 255, 255), 3, true));
			setLayout(new MigLayout());
			String imgLocation = "/image/walpaper_top.jpg";
			URL imageURL = GeneralGUI.class.getResource(imgLocation);
			
			JPanel panel = new BackgroundedFrameTop(imageURL, g);
			panel.setLayout(new MigLayout());
			add(panel,"wrap");
			panel.setBackground(color);
			
			
			
		//Create the toolbar.
		JToolBar toolBar = new JToolBar("Still draggable");
		toolBar.setBackground(Costanti.backgroundGreyLight);
		toolBar.setFloatable(false);
		addButtons(toolBar);	
		//this.add(toolBar,"wrap");
		
		panel.add(toolBar,"wrap,gapleft 140, gaptop 10");
		
	
	}

	protected void addButtons(JToolBar toolBar) {
		JButton button = null;

		
		button = makeNavigationButton("open.png", "APRI",
				"Apri",
				"open");

		toolBar.add(button);
		
		button = makeNavigationButton("impostazioni.png", "AVVIA_MISURA",
				"Avvia Misura",
				"begin measure");
button.addActionListener(new ActionListener() {
	
	@Override
	public void actionPerformed(ActionEvent e) {
		 if(Costanti.PATH_DB.length()!=0)
 		{
 			
 		boolean execute=GestioneDB.checkFile(Costanti.PATH_DB);
 		
	    		if(execute)
	    		{
	    			d= new Splash(me);
	    			d.execute();
	    			
	    			Thread thread = new Thread(new ValidateThread());
		 			thread.start();
		 				
	    		}else
	    		{
	    			JOptionPane.showMessageDialog(null,"File già chiuso - non utilizzabile","File chiuso",JOptionPane.ERROR_MESSAGE,new ImageIcon(PannelloTOP.class.getResource("/image/error.png")));
	    		}
 		}else
 		{
 			JOptionPane.showMessageDialog(null,"Nessun file selezionato","Nessun file ... ",JOptionPane.ERROR_MESSAGE,new ImageIcon(PannelloTOP.class.getResource("/image/error.png")));
 		}
		
	}
});
		toolBar.add(button);
		
		//first button
		button = makeNavigationButton("back.png", "INDIETRO",
				"Precedente",
				"Previous");
	
		
		toolBar.add(button);

		//second button
		button = makeNavigationButton("home.png", "HOME",
				"Pagina Iniziale",
				"Home");
	
		toolBar.add(button);
		
	

		button.addKeyListener(new KeyListener() {

            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
            
             if(!Costanti.PATH_DB.equals("")) {	
                if ((e.getKeyCode() == KeyEvent.VK_C) && ((e.getModifiers() & KeyEvent.CTRL_MASK) != 0)) {

                	
                	try {
						SQLiteDAO.riapriMisura();
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
                }
             }
            }

            @Override
            public void keyReleased(KeyEvent e) {
            }
        });
	}

	protected JButton makeNavigationButton(String imageName,
			String actionCommand,
			String toolTipText,
			String altText) {
		//Look for the image.
		String imgLocation = "/image/"+ imageName;
		URL imageURL = PannelloTOP.class.getResource(imgLocation);

		//Create and initialize the button.
		final JButton button = new JButton();
		button.setActionCommand(actionCommand);
		button.setToolTipText(toolTipText);
		
		
		button.getModel().addChangeListener(new ChangeListener() {

		    @Override
		    public void stateChanged(ChangeEvent e) {
		        ButtonModel model = (ButtonModel) e.getSource();

		        if (model.isRollover()) {
		        	button.setBackground(new Color(230, 230, 230).brighter());
		        } else if (model.isPressed()) {
		        	button.setBackground(new Color(230, 230, 230));
		        } else {
		        	button.setBackground(new Color(255, 255, 255));
		        }
		    }
		});
		
		button.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				 String cmd = e.getActionCommand();
				 
				 if(cmd.equals("INDIETRO"))
				 {
					 if(!SessionBO.prevPage.equals(""))
					 {
							CardLayout cl = (CardLayout)(GeneralGUI.center.getLayout());
							String fw=SessionBO.prevPage;
							
							if(SessionBO.prevPage.equals("PMM"))
							{
								SessionBO.prevPage="PSS";
							}
							
							cl.show(GeneralGUI.center ,fw);
					 }
				 }
				 
				 if(cmd.equals("APRI"))
				 {
			
					 String last_path="";
	    				
	    				if(GestioneRegistro.esixt(Costanti.COD_LAST_PATH))
	    				{
	    					last_path=GestioneRegistro.getStringValue(Costanti.COD_LAST_PATH);
	    				}
	    		
	    				JFileChooser jfc = new JFileChooser(last_path);
	    				
	    				javax.swing.filechooser.FileFilter docFilter = new it.calverDesktopLAT.utl.FileTypeFilter(".db", "Calver database");
	    				jfc.addChoosableFileFilter(docFilter);
	    				jfc.showOpenDialog(GeneralGUI.g);
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
	    					JOptionPane.showMessageDialog(null,"Il sistema accetta solo file .db","File Not Found",JOptionPane.ERROR_MESSAGE,new ImageIcon(PannelloTOP.class.getResource("/image/error.png")));
	    				}
	    				
	    				}
	    				GeneralGUI.g.update(GeneralGUI.g.getGraphics());
				 }
				 
//				 if(cmd.equals("AVVIA_MISURA"))
//				 {
//					 if(Costanti.PATH_DB.length()!=0)
//			    		{
//			    			
//			    		boolean execute=GestioneDB.checkFile(Costanti.PATH_DB);
//			    		
//				    		if(execute)
//				    		{
//				    			d= new Splash(me);
//				    			d.execute();
//				    			
//				    			Thread thread = new Thread(new ValidateThread());
//					 			thread.start();
//					 				
//				    		}else
//				    		{
//				    			JOptionPane.showMessageDialog(null,"File già chiuso - non utilizzabile","File chiuso",JOptionPane.ERROR_MESSAGE,new ImageIcon(PannelloTOP.class.getResource("/image/error.png")));
//				    		}
//			    		}else
//			    		{
//			    			JOptionPane.showMessageDialog(null,"Nessun file selezionato","Nessun file ... ",JOptionPane.ERROR_MESSAGE,new ImageIcon(PannelloTOP.class.getResource("/image/error.png")));
//			    		}
//			    
//				 }
				 
				 if(cmd.equals("HOME"))
				 {
					 CardLayout cl = (CardLayout)(GeneralGUI.center.getLayout());
					 cl.show(GeneralGUI.center ,"PSS");
				 }
				
				
			}
		});
		
		

		if (imageURL != null) {                      //image found
			button.setIcon(new ImageIcon(imageURL, altText));
		} else {                                     //no image found
			button.setText(altText);
			System.err.println("Resource not found: "
					+ imgLocation);
		}
		button.setBackground(new Color(255, 255, 255));
		button.setOpaque(true);
		button.setBorderPainted(false);
		return button;
	}
	
	class ValidateThread implements Runnable {
		public void run() {
			
			try
			{
				JPanel panelDB =new PannelloStrumentoMaster();
    			SystemGUI.callPanel(panelDB, "PSS");
    			
    			d.close();
    			PannelloConsole.printArea("Caricati "+SessionBO.numeroStrumenti+" Strumenti");
			}
			catch (Exception er) 
				{
					d.close();
					er.printStackTrace();
				}
				
			}
			
		}
}
