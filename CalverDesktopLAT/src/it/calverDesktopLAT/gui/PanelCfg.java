package it.calverDesktopLAT.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSlider;
import javax.swing.JTextField;

import net.miginfocom.swing.MigLayout;
import java.awt.Font;
import javax.swing.ImageIcon;

public class PanelCfg extends JPanel {
	private JFrame controllingFrame;
	private JTextField textField_delay;
	
	public PanelCfg(JFrame frameCfg) {
	
		controllingFrame=frameCfg;
		setSize(400, 180);
		setLayout(new MigLayout("", "[grow][][grow]", "[][][][][][][]"));
		
		JLabel lblDelay = new JLabel("Delay");
		lblDelay.setFont(new Font("Arial", Font.BOLD, 12));
		add(lblDelay, "cell 0 1,alignx trailing");
		
		textField_delay = new JTextField();
		textField_delay.setFont(new Font("Arial", Font.PLAIN, 12));
		add(textField_delay, "cell 1 1");
		textField_delay.setColumns(10);
		
		JLabel lblCalcoloValoreSonda = new JLabel("Calcolo valore Sonda");
		lblCalcoloValoreSonda.setFont(new Font("Arial", Font.BOLD, 12));
		add(lblCalcoloValoreSonda, "cell 0 2,alignx right");
		
		final JRadioButton rd_dir = new JRadioButton("Diretto");
		rd_dir.setFont(new Font("Arial", Font.BOLD, 12));
		add(rd_dir, "flowx,cell 1 2");
		
		final JRadioButton rd_inst = new JRadioButton("Istantaneo");
		rd_inst.setFont(new Font("Arial", Font.BOLD, 12));
		add(rd_inst, "cell 1 3");
		
		final JRadioButton rd_riemp = new JRadioButton("Riempimento");
		rd_riemp.setFont(new Font("Arial", Font.BOLD, 12));
		add(rd_riemp, "cell 1 4");
		
		JLabel lblBufferMedia = new JLabel("Buffer Media");
		lblBufferMedia.setFont(new Font("Arial", Font.BOLD, 12));
		add(lblBufferMedia, "cell 0 5,alignx trailing");
		
		JLabel lblMs = new JLabel("ms");
		lblMs.setFont(new Font("Arial", Font.BOLD, 12));
		add(lblMs, "cell 1 1");
		
		textField_delay.setText(""+GuiWorker1.delay);
		
		final JSlider slider = new JSlider(JSlider.HORIZONTAL,1,10,2);
		slider.setFont(new Font("Arial", Font.BOLD, 12));
		add(slider, "cell 1 5");
		slider.setMajorTickSpacing(1);
		slider.setMinorTickSpacing(1);
		slider.setPaintTicks(true);
		slider.setPaintLabels(true);
		slider.setValue(GuiWorker1.bufferMedia);
		
		ButtonGroup group = new ButtonGroup();
		group.add(rd_dir);
		group.add(rd_inst);
		group.add(rd_riemp);
		
		if(GuiWorker1.media==0){rd_dir.setSelected(true);}
		if(GuiWorker1.media==1){rd_inst.setSelected(true);}
		if(GuiWorker1.media==2){rd_riemp.setSelected(true);}
		
		JButton btnSet = new JButton("Configura");
		btnSet.setIcon(new ImageIcon(PanelCfg.class.getResource("/image/calcola.png")));
		btnSet.setFont(new Font("Arial", Font.BOLD, 12));
		btnSet.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) 
			{
				try 
				{
					GuiWorker1.delay=Integer.parseInt(textField_delay.getText());
					GuiWorker1.bufferMedia=slider.getValue();
					if(rd_dir.isSelected())
					{
						GuiWorker1.media=0;
					}
					if(rd_inst.isSelected())
					{
						GuiWorker1.media=1;
					}
					if(rd_riemp.isSelected())
					{
						GuiWorker1.media=2;
					}
				} 
				catch (NumberFormatException nfe) {
					JOptionPane.showMessageDialog(null, "I campi delay e buffer accettano solo numeri","Errore",JOptionPane.INFORMATION_MESSAGE,new ImageIcon(PannelloTOP.class.getResource("/image/error.png")));
				}
				
			}
		});
		add(btnSet, "cell 2 5,alignx center");
		
	}

}
