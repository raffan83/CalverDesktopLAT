package it.calverDesktopLAT.gui;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;

import javax.swing.ButtonGroup;
import javax.swing.DefaultCellEditor;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.TableModelEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import it.calverDesktopLAT.bo.GestioneMisuraBO;
import it.calverDesktopLAT.bo.SessionBO;
import it.calverDesktopLAT.dto.DatiEsterniDTO;
import it.calverDesktopLAT.dto.ProvaMisuraDTO;
import net.miginfocom.swing.MigLayout;

public class FrameImportazioneMisure extends JFrame{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static String filename;
	public static ProvaMisuraDTO misura;
	private JTable table;
	JPanel panel;
	private JScrollPane scroll;
	private JTextField textField_search;
	private TableRowSorter<TableModel> rowSorter = null;
	JFrame me;
	
	public FrameImportazioneMisure(String _filename,ProvaMisuraDTO _misura)
	{
		me=this;
		setResizable(false);
		filename=_filename;
		misura=_misura;
		
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		int x = (dim.width - 700) / 2;
		int y = (dim.height - 600) / 2;
		setLocation(x, y);
		setResizable(false);
		setSize(new Dimension(810, 390));
		getContentPane().setLayout(null);

		panel = new JPanel();
		panel.setBounds(0, 0, 800, 355);
		getContentPane().add(panel);
		panel.setLayout(new MigLayout("", "[grow]", "[35px][grow][35px]"));
		
		costruisciPannello();
		
		
	}
	
	
	
	private void costruisciPannello() {
		
		JLabel lblSelezionaLaMisura = new JLabel("Seleziona la misura da importare");
		lblSelezionaLaMisura.setFont(new Font("Arial", Font.BOLD, 16));
		panel.add(lblSelezionaLaMisura, "cell 0 0,alignx center");

		DefaultTableModel model = new DefaultTableModel() {

			@Override
			public Class<?> getColumnClass(int column) {
				switch (column) {
				case 0:
					return boolean.class;
				case 1:
					return Integer.class;
				case 2:
					return Integer.class;
				case 3:
					return String.class;
				case 4:
					return String.class;
				default:
					return String.class;
				}
			}
			 @Override
			public boolean isCellEditable(int row, int column) {
				if(column>0)
				{
				return false;
				}else
				{
					return true;
				}
			}
			 
			 
		};
		
		model.addColumn("");
		model.addColumn("ID Misura");
		model.addColumn("ID Strumento");
		model.addColumn("Matricola");
		model.addColumn("Codice Interno");
		model.addColumn("Denominazione");
		model.addColumn("Modello");

		ButtonGroup group = new ButtonGroup();
		
		try {
			ArrayList<DatiEsterniDTO> datiExt=GestioneMisuraBO.getDatiEsterni(filename);
			
			for (int i=0;i<datiExt.size();i++) 
			{
				DatiEsterniDTO dati=datiExt.get(i);
				model.addRow(new Object[0]);
				model.setValueAt(new JRadioButton(), i, 0);
				model.setValueAt(dati.getIdMisura(), i, 1);
				model.setValueAt(dati.getIdStrumento(), i, 2);
				model.setValueAt(dati.getCodiceInterno(), i, 3);
				model.setValueAt(dati.getMatricola(), i, 4);
				model.setValueAt(dati.getDenominazione(), i, 5);
				model.setValueAt(dati.getModello(), i, 6);
				
				group.add((JRadioButton) model.getValueAt(i, 0));
			}
			
		} catch (Exception e1) {
			e1.printStackTrace();
			PannelloConsole.printArea(e1.getMessage());
		} 
		
		for (int i = 0; i <100; i++) 
		{
		

		}
		  table = new JTable(model) {
		      public void tableChanged(TableModelEvent e) {
		        super.tableChanged(e);
		        repaint();
		      }
		    };
		//table.setBorder(new LineBorder(Color.RED, 2, true));
		
		 table.getColumnModel().getColumn(0).setCellRenderer( new RadioButtonRenderer());
		 table.getColumnModel().getColumn(0).setCellEditor( new RadioButtonEditor(new JCheckBox()));

		 table.getColumnModel().getColumn(0).setPreferredWidth(10);
		 table.getColumnModel().getColumn(1).setPreferredWidth(25);
		 table.getColumnModel().getColumn(2).setPreferredWidth(25);
		 
		 rowSorter= new TableRowSorter<TableModel>(table.getModel());
		 table.setRowSorter(rowSorter);
		 
			table.setFont(new Font("Arial", Font.PLAIN, 14));
			table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
			table.setRowHeight(25);
       
		scroll = new JScrollPane(table);
		scroll.setPreferredSize(new Dimension(400,200));
		panel.add(scroll, "cell 0 1,grow");
		
		JLabel lblCerca = new JLabel("Cerca:");
		panel.add(lblCerca, "flowx,cell 0 2");
		lblCerca.setFont(new Font("Arial",Font.BOLD,14));
		
		textField_search = new JTextField();
		panel.add(textField_search, "cell 0 2,wmin 200,height :35:35");
		textField_search.setColumns(10);
		textField_search.setFont(new Font("Arial",Font.PLAIN,14));
		 textField_search.getDocument().addDocumentListener(new DocumentListener(){

				@Override
				public void insertUpdate(DocumentEvent e) {
					String text = textField_search.getText();

					if (text.trim().length() == 0) {
						rowSorter.setRowFilter(null);
					} else {
						rowSorter.setRowFilter(RowFilter.regexFilter("(?i)" + text));
					}
				}

				@Override
				public void removeUpdate(DocumentEvent e) {
					String text = textField_search.getText();

					if (text.trim().length() == 0) {
						rowSorter.setRowFilter(null);
					} else {
						rowSorter.setRowFilter(RowFilter.regexFilter("(?i)" + text));
					}
				}

				@Override
				public void changedUpdate(DocumentEvent e) {
					throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
				}

			});
		 
		JButton btnImporta = new JButton("Importa");
		
		btnImporta.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				String id=null;
				for (int i = 0; i < table.getRowCount(); i++) {
					JRadioButton chked = (JRadioButton)table.getValueAt(i, 0);
					String dataCol1 = table.getValueAt(i, 1).toString();
					if (chked.isSelected()) {
						id=dataCol1;
						break;
					}
				}
				
				if(id!=null)
				{
					try 
					{
						GestioneMisuraBO.importaMisura(filename,misura,id);
						
						
						ProvaMisuraDTO lis =GestioneMisuraBO.getProvaMisura(SessionBO.idStrumento);

						PannelloMisuraMaster	panelDB =new PannelloMisuraMaster(SessionBO.idStrumento);
					
					SystemGUI.callPanel(panelDB, "PMM");
					
					me.dispose();
					
					}catch(Exception ex)
					{
						ex.printStackTrace();
						PannelloConsole.printArea(ex.getMessage());
					}
				}
			}
		});
		btnImporta.setFont(new Font("Arial", Font.BOLD, 14));
		btnImporta.setIcon(new ImageIcon(PannelloTOP.class.getResource("/image/import.png")));
		panel.add(btnImporta, "cell 0 2,gapx 20");
		
	}



	class RadioButtonRenderer implements TableCellRenderer {
		  public Component getTableCellRendererComponent(JTable table, Object value,
		      boolean isSelected, boolean hasFocus, int row, int column) {
		    if (value == null)
		      return null;
		    return (Component) value;
		  }
		}

		class RadioButtonEditor extends DefaultCellEditor implements ItemListener {
		  private JRadioButton button;

		  public RadioButtonEditor(JCheckBox checkBox) {
		    super(checkBox);
		  }

		  public Component getTableCellEditorComponent(JTable table, Object value,
		      boolean isSelected, int row, int column) {
		    if (value == null)
		      return null;
		    button = (JRadioButton) value;
		    button.addItemListener(this);
		    
		    
		    if(isSelected)
			{
		    	button.setBackground(table.getSelectionBackground());
		    	button.setForeground(table.getSelectionForeground());
			}
		    return (Component) value;
		  }

		  public Object getCellEditorValue() {
		    button.removeItemListener(this);
		    return button;
		  }

		  public void itemStateChanged(ItemEvent e) {
		    super.fireEditingStopped();
		  }
		}
}
