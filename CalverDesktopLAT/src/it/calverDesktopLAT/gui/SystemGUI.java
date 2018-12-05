package it.calverDesktopLAT.gui;

import java.awt.CardLayout;

import javax.swing.JPanel;

public class SystemGUI {

	
	public static void callPanel(JPanel panelDB, String cardKay)
	{
		CardLayout cl = (CardLayout)(GeneralGUI.center.getLayout());

		GeneralGUI.center.add(panelDB,cardKay);

		cl.show(GeneralGUI.center ,cardKay);
		
		
	}
}
