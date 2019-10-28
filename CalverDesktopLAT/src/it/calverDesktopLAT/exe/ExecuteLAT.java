package it.calverDesktopLAT.exe;

import static com.sun.jna.platform.win32.WinReg.HKEY_CURRENT_USER;

import java.awt.image.BufferedImage;
import java.net.URL;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import com.sun.jna.platform.win32.Advapi32Util;

import it.calverDesktopLAT.bo.GestioneRegistro;
import it.calverDesktopLAT.bo.SessionBO;
import it.calverDesktopLAT.gui.FirstAccess;
import it.calverDesktopLAT.gui.GeneralGUI;
import it.calverDesktopLAT.gui.InitSplash;
import it.calverDesktopLAT.utl.Costanti;

public class ExecuteLAT {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try 
		{

	        
	        SwingUtilities.invokeLater(new Runnable(){
	            public void run() 
	            {
	            	try
	            	{
	            		final InitSplash fr= new InitSplash();
	            		 
	            		fr.setVisible(true);
  	           	        
  	           	        InitSplash.setMessage("Inizzializzazione applicazione...", 0);
  	           	        Thread.sleep(50);
  	           	        
  	           	        InitSplash.setMessage("Caricamento chiavi di registro...", 1);
	            		
	            		 
	            		 if(!GestioneRegistro.isConfig()) 
	            		 { 
	            				SwingUtilities.invokeLater(new Runnable(){
	            		            public void run() 
	            		            {
	            		            	try
	            		            	{
	            		            	JFrame f=new FirstAccess();
	            		            	
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
	            		 else 
	            		{
	            			 
	                Map <String, Object>values = Advapi32Util.registryGetValues(HKEY_CURRENT_USER,Costanti.REGISTER_KEY);
		            			
		            SessionBO.globalKey=values;		 
	        
		            
		          if( ! GestioneRegistro.esixt(Costanti.COD_PRINT)) 
		          {
		        	  GestioneRegistro.setStringValue(Costanti.COD_PRINT, "1");
		          }
		          
		          
	            	GeneralGUI g1 = new GeneralGUI();
	            	
	            //	g1.setResizable(false);
	            	
	            	URL iconURL = this.getClass().getResource("/image/logo.png");
	                
	            	BufferedImage imgB = ImageIO.read(iconURL);
	                 
	            	ImageIcon img = new ImageIcon(imgB);
	            	
	            	g1.setIconImage(img.getImage());
	      	        
	            	g1.setDefaultCloseOperation(3);
	      	        g1.setVisible(true);
	      	        
	      	      InitSplash.setMessage("Avvio Applicazione...", 100);
	      	      Thread.sleep(500);
	      	      fr.dispose();
	            		
	            		}
	      	        }
	            	catch(Exception ex)
	      	        {
					//	GeneralGUI.printException(ex);
	      	        	ex.printStackTrace();
	      	        }
	            }

	        });
	        
		
		} catch (Exception e) {
			e.printStackTrace();
		}

	}


	}


