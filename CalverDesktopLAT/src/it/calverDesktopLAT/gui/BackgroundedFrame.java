package it.calverDesktopLAT.gui;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;


public class BackgroundedFrame extends JPanel{
	  /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	BufferedImage img;
	  int width;
	  int height;
	  static JFrame myFrame=null;
	  
	  public BackgroundedFrame(URL imageURL, JFrame g){
	    super(true); //crea un JPanel con doubleBuffered true
	    try{
	    	 myFrame=g; 	
	      setImage(ImageIO.read(imageURL));
	    } catch(Exception e) {}
	  }
	  public void setImage(BufferedImage img){
	    this.img = img;
	    width = img.getWidth();
	    height = img.getHeight();
	  }

	  public void paintComponent(Graphics g){
	    super.paintComponent(g);
	   // System.out.println(myFrame.getHeight()+myFrame.getWidth());
	    this.setBounds(0, 0, myFrame.getWidth(), myFrame.getHeight()-40);
	    g.drawImage(img, 0, 0, myFrame.getWidth(), myFrame.getHeight()-40,this);
	
	  }
	}
