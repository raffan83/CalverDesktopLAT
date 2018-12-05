package it.calverDesktopLAT.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.math.RoundingMode;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import it.calverDesktopLAT.bo.GestioneCampioneBO;
import it.calverDesktopLAT.bo.SessionBO;

import java.awt.Font;
import net.miginfocom.swing.MigLayout;
import javax.swing.ImageIcon;

public class FrameInterpolazione extends JFrame {
	
	JFrame g=null;
	JPanel panel=null;
	
	JComboBox<String> listaCampioni;
	
	JTextField sign_out_start=null;
	JTextField sign_out_fs=null;
	
	JTextField sign_str_start=null;
	JTextField sign_str_fs=null;
	
	JTextField val_out=null;
	JTextField result=null;
	
	JButton calcola= new JButton("Calcola");
	JButton cattura= new JButton("Cattura");
	
	public FrameInterpolazione()
	{
		g=this;

		this.setSize(400,300);

		getContentPane().setLayout(null);
		
		
	
		setTitle("Interpolazione"); 
		
		try {
			costruisciFrame();
			
		} catch (Exception e) {
			
			e.printStackTrace();
		}
	}

	private void costruisciFrame() throws Exception {
	
		JPanel panel = new JPanel();
		panel.setLocation(0, 0);
		
		panel.setSize(new Dimension(400, 261));
		panel.setBackground(Color.white);
		panel.setLayout(new MigLayout("", "[pref!][25][grow][pref!][grow][:25:25][5px]", "[25px][25px][26px][27px][25px][30px,grow]"));
		
		JLabel lab_0= new JLabel("0");
		lab_0.setFont(new Font("Arial", Font.BOLD, 14));
		panel.add(lab_0, "cell 2 1,alignx center");
		
		JLabel lab_FS= new JLabel("FS");
		lab_FS.setFont(new Font("Arial", Font.BOLD, 14));
		panel.add(lab_FS, "cell 4 1,alignx center");
		
		JLabel selCampione = new JLabel("Seleziona Campione");
		selCampione.setFont(new Font("Arial", Font.BOLD, 14));
		panel.add(selCampione, "cell 0 0");
		
		listaCampioni= new JComboBox<String>(GestioneCampioneBO.getListaCampioniCompleta());
		
		panel.add(listaCampioni, "cell 2 0 3 1");
		
		
		JLabel lab_sign_out= new JLabel("Segnale Out");
		lab_sign_out.setFont(new Font("Arial", Font.BOLD, 14));
		panel.add(lab_sign_out, "cell 0 2");
		
		
		JLabel lab_sign_str= new JLabel("Range Strumento");
		lab_sign_str.setFont(new Font("Arial", Font.BOLD, 14));
		panel.add(lab_sign_str, "cell 0 3");
		
		sign_out_start= new JTextField();
		sign_out_start.setFont(new Font("Arial", Font.PLAIN, 14));
		panel.add(sign_out_start, "cell 2 2,growx");
		
		sign_out_fs= new JTextField();
		sign_out_fs.setFont(new Font("Arial", Font.PLAIN, 14));
		panel.add(sign_out_fs, "cell 4 2,growx");
		
		sign_str_start= new JTextField();
		sign_str_start.setFont(new Font("Arial", Font.PLAIN, 14));
		panel.add(sign_str_start, "cell 2 3,growx");
		
		sign_str_fs= new JTextField();
		sign_str_fs.setFont(new Font("Arial", Font.PLAIN, 14));
		panel.add(sign_str_fs, "cell 4 3,growx");
		
		JLabel labValOut=new JLabel("Valore uscita");
		labValOut.setFont(new Font("Arial", Font.BOLD, 14));
		panel.add(labValOut, "cell 0 4");
		
		val_out= new JTextField();
		val_out.setFont(new Font("Arial", Font.PLAIN, 14));
		panel.add(val_out, "cell 2 4,growx");
		
		JLabel labResult = new JLabel("Risultato");
		labResult.setFont(new Font("Arial", Font.BOLD, 14));
		panel.add(labResult, "cell 3 4,aligny baseline");
		
		result = new JTextField();
		result.setFont(new Font("Arial", Font.PLAIN, 14));
		result.setEditable(false);
		panel.add(result, "cell 4 4,growx");
		calcola.setIcon(new ImageIcon(FrameInterpolazione.class.getResource("/image/calcola.png")));
		calcola.setFont(new Font("Arial", Font.BOLD, 14));
		panel.add(calcola, "cell 0 5 6 1,alignx center,aligny baseline");
		cattura.setIcon(new ImageIcon(FrameInterpolazione.class.getResource("/image/catch.png")));
		cattura.setFont(new Font("Arial", Font.BOLD, 14));
		panel.add(cattura, "cell 0 5");
		
		sign_out_start.setText(SessionBO.sign_out_start.toPlainString());
		sign_out_fs.setText(SessionBO.sign_out_fs.toPlainString());
		
		sign_str_start.setText(SessionBO.sign_str_start.toPlainString());
		sign_str_fs.setText(SessionBO.sign_str_fs.toPlainString());
		
		calcola.addActionListener(new ActionListener() {
			
			
			public void actionPerformed(ActionEvent arg0) 
			{
			
				BigDecimal signal_out_zero;
				BigDecimal signal_out_fs;
				
				BigDecimal signal_str_zero;
				BigDecimal signal_str_fs;
				
				BigDecimal value;
				
				
				try
				{
					signal_out_zero= new BigDecimal(sign_out_start.getText());
					signal_out_fs= new BigDecimal(sign_out_fs.getText());
					
					signal_str_zero=new BigDecimal(sign_str_start.getText());
					signal_str_fs= new BigDecimal(sign_str_fs.getText());
					
					value= new BigDecimal(val_out.getText());
					
					SessionBO.sign_out_start=new BigDecimal(sign_out_start.getText());
					SessionBO.sign_out_fs=new BigDecimal(sign_out_fs.getText());
					
					SessionBO.sign_str_start=new BigDecimal(sign_str_start.getText());
					SessionBO.sign_str_fs=new BigDecimal( sign_str_fs.getText());
					
					
					BigDecimal fct1=signal_str_fs.subtract(signal_str_zero);
					
					BigDecimal fct2=value.subtract(signal_out_zero).divide(signal_out_fs.subtract(signal_out_zero).setScale(4,RoundingMode.HALF_DOWN));
					
					BigDecimal total=fct1.multiply(fct2).add(signal_str_zero);
					
					result.setText(total.setScale(2, RoundingMode.HALF_DOWN).toPlainString());
					
				}catch (NumberFormatException e) 
				{
					JOptionPane.showMessageDialog(null, "La funzione accetta solo numeri","Errore",JOptionPane.INFORMATION_MESSAGE,new ImageIcon(PannelloTOP.class.getResource("/image/error.png")));
				}
				catch (java.lang.ArithmeticException e) 
				{
					JOptionPane.showMessageDialog(null, "Scala di riferimento errata","Errore",JOptionPane.INFORMATION_MESSAGE,new ImageIcon(PannelloTOP.class.getResource("/image/error.png")));
				}
				
			}
		});
		
		cattura.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {

					FrameMisura.textField_misura.setText(result.getText());
					
					if(listaCampioni.getSelectedIndex()>0)
					{
						FrameMisura.campioneInterpolazione="|"+listaCampioni.getSelectedItem().toString();
					}
					
				 g.dispose();	
				
			}
		});
		
		g.getContentPane().add(panel);
	
	}
	

}
