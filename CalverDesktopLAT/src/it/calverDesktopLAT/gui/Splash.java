package it.calverDesktopLAT.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Toolkit;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingWorker;

public class Splash extends SwingWorker<Integer, Integer>
{
	private JFrame g ;
	
	JPanel pan ;
	JPanel panel = new JPanel();
	static JLabel lab;
	
    public Splash(JComponent frm)
    {
    	g= new JFrame();
		
    	g.setUndecorated(true);
    	
    	g.setBackground(new Color(1.0f,1.0f,1.0f,0.5f));
    	Point d=frm.getLocation(); 
      	
        int x = ( frm.getWidth()  - 300) / 2;
 		int y = ( frm.getHeight()  - 400) / 2;
 		
 		x=x+(int)d.getX();
 		y=y+(int)d.getY();
 		
 		g.setLocation(x, y);
    }

   
    
   /* public static void main(String[] args) throws InterruptedException {
    	
    	JFrame f = new JFrame();
    	f.setSize(300,200);
    	
    	f.getContentPane().setLayout(new BorderLayout());
    	JButton but = new JButton("Avvia");
    	f.getContentPane().add(but);
    	f.pack();
    	f.setVisible(true);
    	
    	
    	
    	but.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				
				Splash d =	 new Splash();
				
				d.execute();
				
				
				Thread thread = new Thread(d.new ValidateThread());
				thread.start();
				

				//	d.close();
			}
    		
    	});

	}*/

	public void close() {
		try {
			Thread.sleep(50);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		g.dispose();	
	}
	


	@Override
	protected Integer doInBackground() throws Exception {
		
		try 
		{
		URL iconURL = this.getClass().getResource("/image/logo.png");


		ImageIcon img = new ImageIcon(iconURL);
		g.setIconImage(img.getImage());
		
		g.getContentPane().setLayout(new BorderLayout());
    	
    //	g.setTitle("Attendere...");
    	g.setPreferredSize(new Dimension(200, 200));
    	
    	String imgLocation = "/image/Splash.gif";
		URL imageURL = GeneralGUI.class.getResource(imgLocation);
		
    	lab = new JLabel(new ImageIcon(imageURL));
    	
        g.getContentPane().add(lab);
        Dimension screenSize =
                Toolkit.getDefaultToolkit().getScreenSize();
              Dimension labelSize = g.getPreferredSize();
              g.setLocation(screenSize.width/2 - (labelSize.width/2),
                          screenSize.height/2 - (labelSize.height/2));
              
        g.pack();
		g.setVisible(true);
		
		}catch (Exception e) {
		e.printStackTrace();
		}
		return 0;
	}



}