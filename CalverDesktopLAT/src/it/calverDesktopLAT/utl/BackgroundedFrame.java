package it.calverDesktopLAT.utl;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.JPanel;


public class BackgroundedFrame extends JPanel{
	  /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	BufferedImage img;
	  int width;
	  int height;

	  public BackgroundedFrame(File f){
	    super(true); //crea un JPanel con doubleBuffered true
	    try{
	      setImage(ImageIO.read(f));
	    } catch(Exception e) {}
	  }
	  public void setImage(BufferedImage img){
	    this.img = img;
	    width = img.getWidth();
	    height = img.getHeight();
	  }

	  // sovrascrivi il metodo paintComponent passandogli l'immagine partendo dalle coordinate 0,0 senza usare un ImageObserver (null)
	  public void paintComponent(Graphics g){
	    super.paintComponent(g);
	    g.drawImage(img, 0, 0, null);
	  }
	}
