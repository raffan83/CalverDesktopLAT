package it.calverDesktopLAT.gui;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class JPersonalPanel extends JPanel {

	private BufferedImage img;
	private int widthImg;
	private int heightImg;
	static JFrame myFrame=null;
	
	public JPersonalPanel(JFrame myF)
	{
		
		try {
			myFrame=myF;
			URL imageURL = GeneralGUI.class.getResource("/image/creaStr.png");
			img=ImageIO.read(imageURL);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void setImage(BufferedImage img){
	    this.img = img;
	    widthImg = img.getWidth();
	    heightImg = img.getHeight();
	  }

	  public void paintComponent(Graphics g){
	    super.paintComponent(g);
	   // System.out.println(myFrame.getHeight()+myFrame.getWidth());
	    this.setBounds(0, 0, myFrame.getWidth(), myFrame.getHeight());
	    g.drawImage(img, 0, 0, myFrame.getWidth(), myFrame.getHeight(),this);
	
	    
	  }
}
