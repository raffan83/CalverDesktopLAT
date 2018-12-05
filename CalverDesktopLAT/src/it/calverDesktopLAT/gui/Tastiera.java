package it.calverDesktopLAT.gui;


import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import net.miginfocom.swing.MigLayout;

public class Tastiera extends JFrame implements WindowFocusListener,KeyListener{
	
	private JTextField textField;
	String s="";
	JFrame frame= null;
	final JButton button_1;
	final JButton button_2;
	final JButton button_3;
	final JButton button_4;
	final JButton button_5;
	final JButton button_6;
	final JButton button_7;
	final JButton button_8;
	final JButton button_9;
	final JButton button_0;
	final JButton button_point;
	final JButton button_C;
	final JButton button_conferma;
	
	public Tastiera(final JTextField field,JFrame frm)
	{
		addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        
		   if(field.isEnabled()==false && frame!=null)
			{
			 frame.dispose();
			}
		   
		addWindowFocusListener(this);
		setTitle("Bloc Num");
		frame=this;
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		int x = (dim.width - frm.getWidth()) / 2;
		int y = (dim.height - frm.getHeight()) / 2;
		
		s=field.getText();
		
		setLocation(x,y);
		
		getContentPane().setLayout(new MigLayout("", "[grow]", "[grow]"));
		setSize(new Dimension(250,350));
		
		JPanel panel = new JPanel();
		panel.setPreferredSize(new Dimension(250,350));
		panel.setBackground(UIManager.getColor("Button.light"));
		
		getContentPane().add(panel, "cell 0 0,grow , width 100:250:");
		panel.setLayout(new MigLayout("", "[grow][grow][grow]", "[grow][grow][grow][grow][grow][grow][grow]"));
		
		textField = new JTextField();
		textField.setFont(new Font("Tahoma", Font.PLAIN, 18));
		textField.setBackground(UIManager.getColor("Button.disabledShadow"));
		textField.setEditable(false);
		panel.add(textField, "cell 0 0 3 1,width 40:250:,height 20:50:200,grow");
		textField.setColumns(10);
		textField.setText(s);
		
		button_7 = new JButton("7");
		button_7.setFont(new Font("Tahoma", Font.PLAIN, 18));
		panel.add(button_7, "cell 0 2,width 40:70:500,height 40:70:200,grow");
		
		button_7.addActionListener(new ActionListener() { 
            public void actionPerformed(ActionEvent event){
            	textField.setEditable(true);
            	s=s+button_7.getText();
            	textField.setText(s);
            	textField.setEditable(false);
                
            }
        }); 
		
		button_8 = new JButton("8");
		button_8.setFont(new Font("Tahoma", Font.PLAIN, 18));
		panel.add(button_8, "cell 1 2,width 40:70:500,height 40:70:200,grow");
		
		button_9 = new JButton("9");
		button_9.setFont(new Font("Tahoma", Font.PLAIN, 18));
		panel.add(button_9, "cell 2 2,width 40:70:500,height 40:70:200,grow");
		
			button_9.addActionListener(new ActionListener() { 
            public void actionPerformed(ActionEvent event){
            	textField.setEditable(true);
            	s=s+button_9.getText();
            	textField.setText(s);
            	textField.setEditable(false);
                
            }
        }); 
		
		button_4 = new JButton("4");
		button_4.setFont(new Font("Tahoma", Font.PLAIN, 18));
		panel.add(button_4, "cell 0 3,width 40:70:500,height 40:70:200,grow");
		
		button_5 = new JButton("5");
		button_5.setFont(new Font("Tahoma", Font.PLAIN, 18));
		panel.add(button_5, "cell 1 3,width 40:70:500,height 40:70:200,grow");
		
		button_6 = new JButton("6");
		button_6.setFont(new Font("Tahoma", Font.PLAIN, 18));
		panel.add(button_6, "cell 2 3,width 40:70:500,height 40:70:200,grow");
		
		button_1 = new JButton("1");
		button_1.setFont(new Font("Tahoma", Font.PLAIN, 18));
		panel.add(button_1, "cell 0 4,width 40:70:500,height 40:70:200,grow");
		
		button_2 = new JButton("2");
		button_2.setFont(new Font("Tahoma", Font.PLAIN, 18));
		panel.add(button_2, "cell 1 4,width 40:70:500,height 40:70:200,grow");
		
		button_3 = new JButton("3");
		button_3.setFont(new Font("Tahoma", Font.PLAIN, 18));
		panel.add(button_3, "cell 2 4,width 40:70:500,height 40:70:200,grow");
		
		button_point = new JButton(".");
		button_point.setFont(new Font("Tahoma", Font.PLAIN, 18));
		panel.add(button_point, "cell 0 5,width 40:70:500,height 40:70:200,grow");
		
		button_0 = new JButton("0");
		button_0.setFont(new Font("Tahoma", Font.PLAIN, 18));
		panel.add(button_0, "cell 1 5,width 40:70:500,height 40:70:200,grow");
		
		button_C = new JButton("C");
		button_C.setFont(new Font("Tahoma", Font.PLAIN, 18));
		panel.add(button_C, "cell 2 5,width 40:70:500,height 40:70:200,grow");
		
		button_conferma = new JButton("CONFERMA");
		button_conferma.setFont(new Font("Tahoma", Font.PLAIN, 18));
		panel.add(button_conferma, "cell 0 6 3 1,alignx right,height 40:70:200,grow");
		
		button_8.addActionListener(new ActionListener() { 
            public void actionPerformed(ActionEvent event){
            	textField.setEditable(true);
            	s=s+button_8.getText();
            	textField.setText(s);
            	textField.setEditable(false);
                
            }
        }); 
		
		button_6.addActionListener(new ActionListener() { 
            public void actionPerformed(ActionEvent event){
            	textField.setEditable(true);
            	s=s+button_6.getText();
            	textField.setText(s);
            	textField.setEditable(false);
                
            }
        }); 
		
		button_5.addActionListener(new ActionListener() { 
            public void actionPerformed(ActionEvent event){
            	textField.setEditable(true);
            	s=s+button_5.getText();
            	textField.setText(s);
            	textField.setEditable(false);
                
            }
        }); 
		
		button_4.addActionListener(new ActionListener() { 
            public void actionPerformed(ActionEvent event){
            	textField.setEditable(true);
            	s=s+button_4.getText();
            	textField.setText(s);
            	textField.setEditable(false);
                
            }
        }); 
		
		button_3.addActionListener(new ActionListener() { 
            public void actionPerformed(ActionEvent event){
            	textField.setEditable(true);
            	s=s+button_3.getText();
            	textField.setText(s);
            	textField.setEditable(false);
                
            }
        });
		
		button_2.addActionListener(new ActionListener() { 
            public void actionPerformed(ActionEvent event){
            	textField.setEditable(true);
            	s=s+button_2.getText();
            	textField.setText(s);
            	textField.setEditable(false);
                
            }
        });
		
		button_1.addActionListener(new ActionListener() { 
            public void actionPerformed(ActionEvent event){
            	textField.setEditable(true);
            	s=s+button_1.getText();
            	textField.setText(s);
            	textField.setEditable(false);
                
            }
        });
		
		button_0.addActionListener(new ActionListener() { 
            public void actionPerformed(ActionEvent event){
            	textField.setEditable(true);
            	s=s+button_0.getText();
            	textField.setText(s);
            	textField.setEditable(false);
                
            }
        });
		
		
        button_point.addActionListener(new ActionListener() { 
            public void actionPerformed(ActionEvent event){
            	if(s.indexOf(".")<0)
            	{
            		textField.setEditable(true);
            	s=s+button_point.getText();
            	textField.setText(s);
            	textField.setEditable(false);
            	}
            }
        });
        
	
        button_C.addActionListener(new ActionListener() { 
            public void actionPerformed(ActionEvent event){
            //	textField.setEditable(true);
            	if(s.length()>0)
            	{
            		s=s.substring(0,s.length()-1);
            		textField.setText(s);
            	//	textField.setEditable(false);
            	}
            
            }
        });
        
		
       button_conferma.addActionListener(new ActionListener() { 
            public void actionPerformed(ActionEvent event){
            
            	if (frame.isDisplayable()) { 
                    frame.dispose();
                }
            }
        });
        
        textField.getDocument().addDocumentListener(new DocumentListener() {
        	  public void changedUpdate(DocumentEvent e) 
        	  {
        		  warn();
        	  }

			@Override
			public void insertUpdate(DocumentEvent arg0) {
				warn();
			}

			@Override
			public void removeUpdate(DocumentEvent arg0) {
				warn();
				
			}
			 public void warn() 
			 {
				 field.setText(textField.getText());
			}
        });

       
	}

	@Override
	public void windowGainedFocus(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowLostFocus(WindowEvent e) {
		if (frame.isDisplayable()) { 
           frame.dispose();
        }
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		System.out.println(e.getKeyCode());
		
		 if (e.getKeyCode() == 109 || e.getKeyCode() == 45) 
		  {
         
			if(textField.getText().length()==0)
			{
				s="-";
			}
			else
			{
				if(!textField.getText().substring(0,1).equals("-"))
				{
					s="-"+textField.getText();
				}
				
			}
			textField.setText(s);
	      }
		 
		 if (e.getKeyCode() == KeyEvent.VK_1 || e.getKeyCode() == KeyEvent.VK_NUMPAD1) 
		  {
			  button_1.doClick();
	      }
		  if (e.getKeyCode() == KeyEvent.VK_2 || e.getKeyCode() == KeyEvent.VK_NUMPAD2) 
		  {
			  button_2.doClick();
	      }
		  if (e.getKeyCode() == KeyEvent.VK_3 || e.getKeyCode() == KeyEvent.VK_NUMPAD3) 
		  {
			  button_3.doClick();
	      }
		  if (e.getKeyCode() == KeyEvent.VK_4 || e.getKeyCode() == KeyEvent.VK_NUMPAD4) 
		  {
			  button_4.doClick();
	      }
		  if (e.getKeyCode() == KeyEvent.VK_5 || e.getKeyCode() == KeyEvent.VK_NUMPAD5) 
		  {
			  button_5.doClick();
	      }
		  if (e.getKeyCode() == KeyEvent.VK_6 || e.getKeyCode() == KeyEvent.VK_NUMPAD6) 
		  {
			  button_6.doClick();
	      }
		  if (e.getKeyCode() == KeyEvent.VK_7 || e.getKeyCode() == KeyEvent.VK_NUMPAD7) 
		  {
			  button_7.doClick();
	      }
		  if (e.getKeyCode() == KeyEvent.VK_8 || e.getKeyCode() == KeyEvent.VK_NUMPAD8) 
		  {
			  button_8.doClick();
	      }
		  if (e.getKeyCode() == KeyEvent.VK_9 || e.getKeyCode() == KeyEvent.VK_NUMPAD9) 
		  {
			  button_9.doClick();
	      }
		  if (e.getKeyCode() == KeyEvent.VK_0 || e.getKeyCode() == KeyEvent.VK_NUMPAD0) 
		  {
			  button_0.doClick();
	      }
		  
		  if (e.getKeyCode() == KeyEvent.VK_ENTER || e.getKeyCode() == 27) 
		  {
			  button_conferma.doClick();
	      }
		  
		  if (e.getKeyCode() == KeyEvent.VK_BACK_SPACE || e.getKeyCode() == 127) 
		  {
			  button_C.doClick();
	      }
		  
		  if (e.getKeyCode() == 110 || e.getKeyCode() == 44 || e.getKeyCode() == 46) 
		  {
			  button_point.doClick();
	      }
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		
		
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		
		
	}

	public static void main(String[] args) {
		JFrame f = new JFrame();
		JTextField tf= new JTextField();
		f.setSize(new Dimension(200,300));
		tf.setBounds(10, 10, 100, 25);
		
		f.getContentPane().add(tf);
		
		Tastiera t = new Tastiera(tf, f);
		t.setDefaultCloseOperation(3);
	       t.setVisible(true);
	}

}
