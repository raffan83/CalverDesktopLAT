package it.calverDesktopLAT.gui;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import org.apache.commons.io.FileUtils;

import it.calverDesktopLAT.bo.GestioneRegistro;
import it.calverDesktopLAT.utl.Costanti;
import net.miginfocom.swing.MigLayout;

public class FirstAccess extends JFrame{
	
	private JTextField tf_cod_operatore;
	private JTextField tf_contatore;
	private JTextField tf_dasm_port;
	private JTextField tf_frame_rate;
	
	public FirstAccess() {
		setTitle("Primo inserimento");
		setResizable(false);
		getContentPane().setLayout(null);
		JPanel panel = new JPersonalPanel(this);
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		setSize(400, 350);
		
		
		
		panel.setBounds(0, 0, 400, 315);
		getContentPane().add(panel);
		
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		int x = (dim.width - panel.getWidth()) / 2;
		int y = (dim.height - panel.getHeight()) / 2;
		setLocation(x, y);
		
		panel.setLayout(new MigLayout("", "[][grow][grow]", "[30px][][35px][35][35][35][][][grow]"));
		
		
		JLabel lblNelSistemaNon = new JLabel("Nel sistema non sono presenti informazioni essenziali");
		lblNelSistemaNon.setForeground(Costanti.COLOR_RED);
		lblNelSistemaNon.setFont(new Font("Arial", Font.BOLD, 14));
		panel.add(lblNelSistemaNon, "flowy,cell 0 0 3 1,alignx left");
		
		JLabel lblInserireIDati = new JLabel("Inserire i dati:");
		lblInserireIDati.setFont(new Font("Arial", Font.BOLD, 15));
		panel.add(lblInserireIDati, "cell 0 1");
		
		JLabel lblCodiceOperatore = new JLabel("Codice Operatore:");
		lblCodiceOperatore.setFont(new Font("Arial", Font.BOLD, 14));
		panel.add(lblCodiceOperatore, "cell 0 2,alignx trailing");
		
		tf_cod_operatore = new JTextField(3);
		tf_cod_operatore.setDocument(new JTextFieldLimit(3));
		tf_cod_operatore.setFont(new Font("Arial", Font.PLAIN, 14));
		panel.add(tf_cod_operatore, "cell 1 2,wmax 40");
		tf_cod_operatore.setColumns(10);
		
		JLabel lblContatore = new JLabel("Contatore:");
		lblContatore.setFont(new Font("Arial", Font.BOLD, 14));
		panel.add(lblContatore, "cell 0 3,alignx trailing");
		
		tf_contatore = new JTextField();
		tf_contatore.setFont(new Font("Arial", Font.PLAIN, 14));
		panel.add(tf_contatore, "cell 1 3,wmax 40");
		tf_contatore.setColumns(10);
		tf_contatore.setText("1");
		JLabel lblDasmPort = new JLabel("DASM Port:");
		lblDasmPort.setFont(new Font("Arial", Font.BOLD, 14));
		panel.add(lblDasmPort, "cell 0 4,alignx trailing");
		
		tf_dasm_port = new JTextField();
		tf_dasm_port.setFont(new Font("Arial", Font.PLAIN, 14));
		panel.add(tf_dasm_port, "cell 1 4,wmax 100");
		tf_dasm_port.setColumns(10);
		
		JLabel lblDasmFrameRate = new JLabel("DASM Frame Rate");
		lblDasmFrameRate.setFont(new Font("Arial", Font.BOLD, 14));
		panel.add(lblDasmFrameRate, "cell 0 5,alignx trailing");
		
		JLabel lblPerLaCorretta = new JLabel("per la corretta esucuzione del software.");
		lblPerLaCorretta.setForeground(Costanti.COLOR_RED);
		lblPerLaCorretta.setFont(new Font("Arial", Font.BOLD, 14));
		panel.add(lblPerLaCorretta, "cell 0 0 3 1");
		
		tf_frame_rate = new JTextField();
		tf_frame_rate.setFont(new Font("Arial", Font.PLAIN, 14));
		panel.add(tf_frame_rate, "cell 1 5,wmax 100");
		tf_frame_rate.setColumns(10);
		tf_frame_rate.setText("0");
		
		JButton btnInserisci = new JButton("Inserisci");
		btnInserisci.setIcon(new ImageIcon(PannelloTOP.class.getResource("/image/insert.png")));
		btnInserisci.setFont(new Font("Arial", Font.BOLD, 14));
		btnInserisci.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) 
			{
				
				try 
				{
					int contatore=Integer.parseInt(tf_contatore.getText());
					int fr=Integer.parseInt(tf_frame_rate.getText());
					
					if(tf_cod_operatore.getText().length()>0 && tf_cod_operatore.getText().length()<4) 
					{
						GestioneRegistro.createKey();
						
						GestioneRegistro.setStringValue(Costanti.COD_OPT, tf_cod_operatore.getText());
						GestioneRegistro.setStringValue(Costanti.COD_CNT, ""+contatore);
						GestioneRegistro.setStringValue(Costanti.COD_DASM_PORT, tf_dasm_port.getText());
						GestioneRegistro.setStringValue(Costanti.COD_DASM_FR, ""+fr);
						GestioneRegistro.setStringValue(Costanti.COD_PRINT, "1");
						
						
						System.out.println("write reg ok");
						
						if(inserisciImmagine()) 
						{
							JOptionPane.showMessageDialog(null, "Parametri inseriti correttamente \n Riavviare l'applicazione", "Conferma", JOptionPane.INFORMATION_MESSAGE,new ImageIcon(PannelloTOP.class.getResource("/image/confirm.png")));
						}
						else 
						{
							JOptionPane.showMessageDialog(null, "Parametri inseriti correttamente ma immagine di etichetta \n non configurata (contattare l'assistenza)\n Riavviare l'applicazione", "Conferma", JOptionPane.WARNING_MESSAGE,new ImageIcon(PannelloTOP.class.getResource("/image/attention.png")));
						}
						
						
					}
					else 
					{
						JOptionPane.showMessageDialog(null, "Il codice operatore non può essere superiore a tre caratteri","Errore",JOptionPane.ERROR_MESSAGE,new ImageIcon(PannelloTOP.class.getResource("/image/error.png")));
					}
					
				
					dispose();
					
					
				}
				catch(NumberFormatException ex)
				{
					JOptionPane.showMessageDialog(null, "I campi Contatore e Frame Rate accettano solo valori numerici","Errore",JOptionPane.ERROR_MESSAGE,new ImageIcon(PannelloTOP.class.getResource("/image/error.png")));
				}
				
			}

			private boolean inserisciImmagine() {
				
			try 
			{	
				String userprofile = System.getenv("USERPROFILE");
				
				File f = new File(userprofile+"//Calver");
				
				if(f.exists()==false) 
				{
					f.mkdir();
				}
				
				URL iconURL = this.getClass().getResource("/image/logo_print.png");
				
				File destination =new File(f.getPath()+"//"+"logo.png");
				
				FileUtils.copyURLToFile(iconURL,destination );
				
				GestioneRegistro.setStringValue(Costanti.COD_IMG_PATH, ""+f.getPath());
				
			}catch(Exception ex) 
			{
				return false;
			}	
				return true;
			}
		});
		panel.add(btnInserisci, "cell 0 7 3 1,alignx center");
	}
}
