package it.calverDesktopLAT.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.math.BigDecimal;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.border.LineBorder;

import it.calverDesktopLAT.bo.GestioneConversioneBO;
import it.calverDesktopLAT.dto.CampioneCorrettoDTO;
import it.calverDesktopLAT.dto.ParametroTaraturaDTO;
import net.miginfocom.swing.MigLayout;
import java.awt.Font;
import javax.swing.ImageIcon;

public class FrameScala extends JFrame {

	public JFrame g;
	JTextField valore_inserito;
	BigDecimal[] minMaxScala=null;
	String parametroTaratura;
	String codiceCampione;
	JTextField val_camp_scl_corr;
	JTextField val_incer_scl_corr;
	JRadioButton rdbtnValoreCorretto;
	JRadioButton rdbtnValoreInserito;
	
	public FrameScala(String _codiceCampione,String _parametroTaratura, final int index, final ParametroTaraturaDTO parametro, final JComboBox listaComboParametri) throws Exception {
		
		g=this;

		this.setSize(750,245);

		getContentPane().setLayout(null);
		
		setTitle("Valore Scala"); 
		minMaxScala=GestioneConversioneBO.getMinMaxScala(_codiceCampione,_parametroTaratura);
		parametroTaratura=_parametroTaratura;
		codiceCampione=_codiceCampione;
		

		ButtonGroup group1 = new ButtonGroup();
		
		JPanel panel_conversione_scala = new JPanel();
		
		panel_conversione_scala.setPreferredSize(new Dimension(700, 200));
		
		panel_conversione_scala.setBorder(new LineBorder(Color.RED, 2, true));
		panel_conversione_scala.setBackground(Color.WHITE);
		
		
		
		panel_conversione_scala.setLayout(new MigLayout("", "[:70px:100px][grow][][25px][][100px,grow][25px][pref!]", "[24.00][][][][][]"));
		
		JLabel lblMinimoScala = new JLabel("Minimo Scala");
		lblMinimoScala.setFont(new Font("Arial", Font.BOLD, 14));
		panel_conversione_scala.add(lblMinimoScala, "cell 0 1,alignx trailing");

		JTextField textField_min_scl = new JTextField(minMaxScala[0].toPlainString());
		textField_min_scl.setFont(new Font("Arial", Font.PLAIN, 14));
		textField_min_scl.setEditable(false);
		panel_conversione_scala.add(textField_min_scl, "cell 1 1,growx");
		textField_min_scl.setColumns(10);

		JLabel lblValoreCorretto = new JLabel("Valore Corretto");
		lblValoreCorretto.setFont(new Font("Arial", Font.BOLD, 14));
		panel_conversione_scala.add(lblValoreCorretto, "cell 5 1,aligny bottom");
		rdbtnValoreInserito = new JRadioButton("Valore Lettura");
		rdbtnValoreInserito.setFont(new Font("Arial", Font.PLAIN, 12));
		rdbtnValoreInserito.setBackground(Color.WHITE);
		panel_conversione_scala.add(rdbtnValoreInserito, "cell 7 1,alignx left");

		//	val_camp_scl_corr = new JTextField();
		val_camp_scl_corr = new JTextField();
		val_camp_scl_corr.setFont(new Font("Arial", Font.PLAIN, 14));
		val_camp_scl_corr.setEditable(false);
		panel_conversione_scala.add(val_camp_scl_corr, "cell 5 2,growx");
		val_camp_scl_corr.setColumns(10);

		rdbtnValoreCorretto = new JRadioButton("Valore Corretto");
		rdbtnValoreCorretto.setFont(new Font("Arial", Font.PLAIN, 12));
		rdbtnValoreCorretto.setSelected(true);
		rdbtnValoreCorretto.setBackground(Color.WHITE);
		panel_conversione_scala.add(rdbtnValoreCorretto, "cell 7 2,alignx left");

		group1.add(rdbtnValoreCorretto);
		group1.add(rdbtnValoreInserito);
		JLabel lblLettura = new JLabel("Lettura:");
		lblLettura.setFont(new Font("Arial", Font.BOLD, 14));
		panel_conversione_scala.add(lblLettura, "cell 0 3,alignx trailing");

		valore_inserito = new JTextField();
		valore_inserito.setFont(new Font("Arial", Font.PLAIN, 14));
		panel_conversione_scala.add(valore_inserito, "cell 1 3,growx");
		valore_inserito.setColumns(10);

			aggiungiTastierino(valore_inserito,g);



		JButton btnCheck = new JButton("Check");
		btnCheck.setIcon(new ImageIcon(FrameScala.class.getResource("/image/check.png")));
		btnCheck.setFont(new Font("Arial", Font.BOLD, 14));


		panel_conversione_scala.add(btnCheck, "cell 3 3,height :35:35");

		JLabel lblIncertezzaCorretta = new JLabel("Incertezza Corretta");
		lblIncertezzaCorretta.setFont(new Font("Arial", Font.BOLD, 14));
		panel_conversione_scala.add(lblIncertezzaCorretta, "cell 5 3,aligny bottom");

		val_incer_scl_corr = new JTextField();
		val_incer_scl_corr.setFont(new Font("Arial", Font.PLAIN, 14));
		val_incer_scl_corr.setEditable(false);
		panel_conversione_scala.add(val_incer_scl_corr, "cell 5 4,growx");
		val_incer_scl_corr.setColumns(10);

		JLabel lblMassimoScala = new JLabel("Massimo Scala");
		lblMassimoScala.setFont(new Font("Arial", Font.BOLD, 14));
		panel_conversione_scala.add(lblMassimoScala, "cell 0 5,alignx trailing");

		JTextField textField_max_scl = new JTextField(minMaxScala[1].toPlainString());
		textField_max_scl.setFont(new Font("Arial", Font.PLAIN, 14));
		textField_max_scl.setEditable(false);
		panel_conversione_scala.add(textField_max_scl, "cell 1 5,growx");
		textField_max_scl.setColumns(10);

		JButton btnContinua_2 = new JButton("Continua");
		btnContinua_2.setIcon(new ImageIcon(FrameScala.class.getResource("/image/continue.png")));
		btnContinua_2.setFont(new Font("Arial", Font.BOLD, 14));
		panel_conversione_scala.add(btnContinua_2, "cell 7 5");

		btnContinua_2.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent arg0) {

			try
			{	
				if(rdbtnValoreCorretto.isSelected())
				{
					FrameComposizioneCampioni.listaMisura[index].setText(val_camp_scl_corr.getText());
					FrameComposizioneCampioni.listaIncertezza[index].setText(val_incer_scl_corr.getText());
					FrameComposizioneCampioni.listaMisureConfermata[index].setText(val_camp_scl_corr.getText());
					FrameComposizioneCampioni.listaIncertezzaConfermata[index].setText(val_incer_scl_corr.getText());
				}else
				{

					FrameComposizioneCampioni.listaMisura[index].setText(valore_inserito.getText());
					FrameComposizioneCampioni.listaIncertezza[index].setText(val_incer_scl_corr.getText());
					FrameComposizioneCampioni.listaMisureConfermata[index].setText(valore_inserito.getText());
					FrameComposizioneCampioni.listaIncertezzaConfermata[index].setText(val_incer_scl_corr.getText());
				}
				
				String[] uMConversione;
				String[] fattMolt;

			
					uMConversione = GestioneConversioneBO.getListaUMConvertibili(parametro.getUm_fond(),parametro.getTipoGrandezza());
					fattMolt= GestioneConversioneBO.getListaFattoriMoltiplicativi();

					FrameComposizioneCampioni.comboUMConver[index].removeAllItems();
					FrameComposizioneCampioni.comboFattMolt[index].removeAll();

					for(String str : uMConversione) {
						FrameComposizioneCampioni.comboUMConver[index].addItem(str);
					}

					for(String str : fattMolt) {
						FrameComposizioneCampioni.comboFattMolt[index].addItem(str);
					}
					
			//		JLabel labUm= new JLabel(parametro.getUm());
					FrameComposizioneCampioni.listaLabel[index].setText(parametro.getUm());
					//	int h= listaComboParametri.getLocation().y;
					//	labUm.setBounds(310, h, 30, 25);
				//		FrameComposizioneCampioni.panelOperazioni.add(labUm);
					
						FrameComposizioneCampioni.update();
					
				g.dispose();
			}catch (Exception e) {
				e.printStackTrace();
			}
			
			}
		});

		btnCheck.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) 
			{

				try{
					BigDecimal valore_ins=new BigDecimal(valore_inserito.getText());

					if((valore_ins.compareTo(minMaxScala[0])==1 || valore_ins.compareTo(minMaxScala[0])==0) && 
							(valore_ins.compareTo(minMaxScala[1])==-1 ||valore_ins.compareTo(minMaxScala[1])==0))
					{
						CampioneCorrettoDTO valoreCorretto = GestioneConversioneBO.getValoreCampioneCorretto(parametroTaratura,valore_ins,false,null,null,0,codiceCampione);
						val_camp_scl_corr.setText(valoreCorretto.getValoreCampioneCorretto().toPlainString());
						val_incer_scl_corr.setText(valoreCorretto.getIncertezzaCorretta().toPlainString());
					}
					else
					{
						val_camp_scl_corr.setText("");
						val_incer_scl_corr.setText("");
						JOptionPane.showMessageDialog(null, "Valore lettura Fuori scala","Out of Range",JOptionPane.INFORMATION_MESSAGE,new ImageIcon(PannelloTOP.class.getResource("/image/error.png")));
					}

				}catch(NumberFormatException nfe)
				{
					val_camp_scl_corr.setText("");
					val_incer_scl_corr.setText("");
					JOptionPane.showMessageDialog(null, "Attenzione numero inserito formalmente scorretto! \n Formato corretto: xxxx.xx","Errore Input",JOptionPane.INFORMATION_MESSAGE,new ImageIcon(PannelloTOP.class.getResource("/image/error.png")));
				}
				catch(Exception e)
				{
					e.printStackTrace();
				}

			}
		});
		
		getContentPane().setLayout(new MigLayout("", "[grow]"));
		getContentPane().add(panel_conversione_scala,"cell 0 0,grow");
	}

	private void aggiungiTastierino(final JTextField txtFld, final JFrame frame) {


		txtFld.addMouseListener(new MouseAdapter(){
			@Override
			public void mouseClicked(MouseEvent e){

				SwingUtilities.invokeLater(new Runnable(){
					public void run() 
					{
						try
						{
							Tastiera f=new Tastiera(txtFld,frame);

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
		});
	}

}
