package it.calverDesktopLAT.gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.AbstractCellEditor;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.RowFilter;
import javax.swing.border.LineBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import it.calverDesktopLAT.bo.GestioneMisuraBO;
import it.calverDesktopLAT.bo.GestioneStrumentoBO;
import it.calverDesktopLAT.bo.SessionBO;
import it.calverDesktopLAT.dto.ProvaMisuraDTO;
import it.calverDesktopLAT.dto.StrumentoDTO;
import it.calverDesktopLAT.utl.Costanti;
import net.miginfocom.swing.MigLayout;

public class PannelloStrumentoMaster extends JPanel implements ActionListener {

	JScrollPane scrollPane=null;
	JTextField dataAl;
	JTextField dataDal;
	JPopupMenu popupMenu;
	JMenuItem jmit;
	int x;
	JPanel me;
	Splash d=null;
	private static ArrayList<StrumentoDTO> listaStrumenti=new ArrayList<StrumentoDTO>();

	public class RadioButtonCellEditorRenderer extends AbstractCellEditor implements TableCellRenderer, TableCellEditor, ActionListener {

		private JRadioButton radioButton;



		public RadioButtonCellEditorRenderer() {
			this.radioButton = new JRadioButton();
			radioButton.addActionListener(this);

			 radioButton.setHorizontalAlignment(JRadioButton.CENTER);
		}


		@Override
		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
			radioButton.setSelected(Boolean.TRUE.equals(value));
			Integer stato=null;
			try {
				stato=GestioneMisuraBO.getStatoMisura(table.getValueAt(row, 1).toString());  
				if(stato!=null && stato==0)
				{

					radioButton.setBackground(new Color(250,210,51));
				}
				if(stato!=null && stato==1)
				{

					radioButton.setBackground(Color.green);
				}
				if(stato!=null && stato==2)
				{

					radioButton.setBackground(new Color(215,23,29));
				}
				if(stato==null)
				{

					radioButton.setBackground(Color.white);

				}
				if(isSelected)
				{
					 radioButton.setBackground(table.getSelectionBackground());
					 radioButton.setForeground(table.getSelectionForeground());
				}

				radioButton.setAlignmentX(CENTER_ALIGNMENT);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			return radioButton;
		}

		@Override
		public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
			radioButton.setSelected(Boolean.TRUE.equals(value));
			return radioButton;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			stopCellEditing();
		}

		@Override
		public Object getCellEditorValue() {
			return radioButton.isSelected();
		}

		public void setRadioButton() {
			//	this.radioButton = radioButton;
			this.radioButton.setBackground(Color.orange);
		}

	}

	private class MyObjectManager {
		private PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);
		private List<MyObject> objects = new ArrayList<MyObject>();

		public void addObject(MyObject object) {
			objects.add(object);
			object.setManager(this);
			propertyChangeSupport.firePropertyChange("objects", null, object);
		}

		public List<MyObject> getObjects() {
			return objects;
		}

		public void setAsSelected(MyObject myObject) {
			for (MyObject o : objects) {
				o.setSelected(myObject == o);
			}
		}
	}

	private class MyObject {
		private PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);

		private MyObjectManager manager;

		private boolean selected;

		private String id ;
		private String codiceInterno;
		private String matricola;
		private String denominazione;
		private String costruttore;
		private String modello;
		private String dataUltimaVerifica;
		private String dataProssimaVerifica;


		public MyObject(String _id, String _codiceInterno,String _matricola,String _denominazione, String _costruttore, String _modello,String _dataUltimaVerifica, String _dataProssimaVerifica) {
			this.id = _id;
			this.codiceInterno = _codiceInterno;
			this.matricola=_matricola;
			this.denominazione = _denominazione;
			this.costruttore = _costruttore;
			this.modello = _modello;
			this.dataUltimaVerifica=_dataUltimaVerifica;
			this.dataProssimaVerifica=_dataProssimaVerifica;
			
		}

		public PropertyChangeSupport getPropertyChangeSupport() {
			return propertyChangeSupport;
		}

		public String getValue() {
			return id;
		}

		public void setValue(String value) {
			this.id = value;
			propertyChangeSupport.firePropertyChange("value", null, value);
		}

		public MyObjectManager getManager() {
			return manager;
		}

		public void setManager(MyObjectManager manager) {
			this.manager = manager;
			propertyChangeSupport.firePropertyChange("manager", null, manager);
		}

		public boolean isSelected() {
			return selected;
		}

		public void setSelected(boolean selected) {
			if (this.selected != selected) {
				this.selected = selected;
				if (selected) {
					manager.setAsSelected(this);
				}
				propertyChangeSupport.firePropertyChange("selected", !selected, selected);
			}
		}

		public Object getDenominazione() {
			return denominazione;
		}

		public Object getCodiceInterno() {
			return codiceInterno;
		}

		public Object getMatricola() {
			return matricola;
		}
		public Object getCostruttore() {
			// TODO Auto-generated method stub
			return costruttore;
		}

		public Object getModello() {
			// TODO Auto-generated method stub
			return modello;
		}

		public Object getDataUltimaVerifica() {
			// TODO Auto-generated method stub
			return dataUltimaVerifica;
		}

		public Object getDataProssimaVerifica() {
			// TODO Auto-generated method stub
			return dataProssimaVerifica;
		}

	}


	private JTextField jtfFilter = new JTextField(30);
	JTable table =null;
	private TableRowSorter<TableModel> rowSorter = null;



	public PannelloStrumentoMaster()
	{
		me=this;
		setLayout(new MigLayout("", "[grow]", "[grow][][grow][grow][grow]"));
		setBackground(Color.white);
		costruisciPannello();

	}
	public class MyTableModel extends AbstractTableModel implements PropertyChangeListener {

		private final MyObjectManager manager;

		public MyTableModel(MyObjectManager manager) {
			super();
			this.manager = manager;
			manager.propertyChangeSupport.addPropertyChangeListener(this);
			for (MyObject object : manager.getObjects()) {
				object.getPropertyChangeSupport().addPropertyChangeListener(this);
			}
		}

		@Override
		public void propertyChange(PropertyChangeEvent evt) {
			if (evt.getSource() == manager) {
				// OK, not the cleanest thing, just to get the gist of it.
				if (evt.getPropertyName().equals("objects")) {
					((MyObject) evt.getNewValue()).getPropertyChangeSupport().addPropertyChangeListener(this);
				}
				fireTableDataChanged();
			} else if (evt.getSource() instanceof MyObject) {
				int index = manager.getObjects().indexOf(evt.getSource());
				fireTableRowsUpdated(index, index);
			}
		}

		@Override
		public int getColumnCount() {
			return 9;
		}

		@Override
		public int getRowCount() {
			return manager.getObjects().size();
		}

		public MyObject getValueAt(int row) {
			return manager.getObjects().get(row);
		}

		@Override
		public Object getValueAt(int rowIndex, int columnIndex) {
			switch (columnIndex) {
			case 0:
				return getValueAt(rowIndex).isSelected();
			case 1:
				return getValueAt(rowIndex).getValue();
			case 2:
				return getValueAt(rowIndex).getCodiceInterno();
			case 3:
				return getValueAt(rowIndex).getMatricola();
			case 4:
				return getValueAt(rowIndex).getDenominazione();  
			case 5:
				return getValueAt(rowIndex).getCostruttore();
			case 6:
				return getValueAt(rowIndex).getModello();
			case 7:
				return getValueAt(rowIndex).getDataUltimaVerifica();
			case 8:
				return getValueAt(rowIndex).getDataProssimaVerifica();   

			}
			return null;
		}

		@Override
		public void setValueAt(Object value, int rowIndex, int columnIndex) {
			if (columnIndex == 0) {
				getValueAt(rowIndex).setSelected(Boolean.TRUE.equals(value));
			}
			if (columnIndex == 1) {
				getValueAt(rowIndex).setSelected(Boolean.TRUE.equals(value));
			}
		}

		@Override
		public boolean isCellEditable(int rowIndex, int columnIndex) {
			return columnIndex == 0;
		}

		@Override
		public Class<?> getColumnClass(int column) {
			switch (column) {
			case 0:
				return Boolean.class;
			case 1:
				return String.class;
			case 2:
				return String.class;
			case 3:
				return String.class;
			case 4:
				return String.class;
			case 5:
				return String.class;
			case 6:
				return String.class;
			case 7:
				return String.class;
			case 8:
				return String.class;
			}
			return Object.class;
		}

		@Override
		public String getColumnName(int column) {
			switch (column) {
			case 0:
				return "Seleziona";
			case 1:
				return "ID Strumento";
			case 2:
				return "Codice Interno";
			case 3:
				return "Matricola";
			case 4:
				return "Denominazione";
			case 5:
				return "Costruttore";
			case 6:
				return "Modello";
			case 7:
				return "Ultima Verifica";
			case 8:
				return "Prossima Verifica";    

			}
			return null;
		}

	}

	private void costruisciPannello() {

		SessionBO.prevPage="";
		
		
		
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		x = (dim.width - getWidth()) / 2;
		int y = (dim.height - getHeight()) / 2;
		setLocation(x, y);
		
		setBackground(Costanti.backgroundGrey);
		setBorder(new LineBorder(new Color(255, 255, 255), 3, true));
		
		
		setLayout(new MigLayout("","[grow]", "[grow][grow][grow][grow]"));

		JLabel lisStr= new JLabel("Lista Strumenti");
		Font font = new Font("Arial", Font.BOLD,20);
		lisStr.setFont(font);
		lisStr.setForeground(Color.black);
		add(lisStr,"cell 0 0,align center");
		try
		{
			listaStrumenti= GestioneStrumentoBO.getListaStrumenti(0);
			SessionBO.numeroStrumenti=listaStrumenti.size();
			MyObjectManager manager = new MyObjectManager();
			for (int i = 0; i < listaStrumenti.size(); i++) {
				StrumentoDTO strumento=listaStrumenti.get(i);
				MyObject object = new MyObject(""+strumento.get__id(),strumento.getCodice_interno(),strumento.getMatricola(),strumento.getDenominazione(),
						strumento.getCostruttore(),strumento.getModello(),strumento.getDataUltimaVerifica(),strumento.getDataProssimaVerifica());
				manager.addObject(object);
			}
			

			table = new JTable(new MyTableModel(manager));
			
			
		}catch(Exception e)
		{
			e.printStackTrace();
			PannelloConsole.printException(e);
		}

		table.setFont(new Font("Arial", Font.PLAIN, 18));
		table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 16));
		// ScrollPane for Table
		scrollPane = new JScrollPane();
		scrollPane.setViewportView(table);

		TableColumn column = table.getColumnModel().getColumn(0);
		column.setCellEditor(new RadioButtonCellEditorRenderer());
		column.setCellRenderer(new RadioButtonCellEditorRenderer());
		rowSorter= new TableRowSorter<TableModel>(table.getModel());

		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setRowSorter(rowSorter);
		table.setDefaultRenderer(Object.class, new MyCellRenderer());

		int[] percColum= {5,7,8,10,21,14,14,9,10};
		
		int tableWidth=x*2;
		
		for(int i=0;i<9;i++) 
		{
			int p =Math.round((percColum[i]*tableWidth)/100);
			table.getColumnModel().getColumn(i).setPreferredWidth(p);
			
		}
		table.setRowHeight(40);
		
		class ValidateThread implements Runnable {
			public void run() {

				String id="";
				for (int i = 0; i < table.getRowCount(); i++) {
					Boolean chked = Boolean.valueOf(table.getValueAt(i, 0).toString());
					String dataCol1 = table.getValueAt(i, 1).toString();
					if (chked) {
						id=dataCol1;
						break;
					}
				}
				try {		
					if(!id.equals("") )	
					{			

						
						int indexTableLAT=GestioneStrumentoBO.isIdoneoForLAT(id);
						
						if(indexTableLAT>0) 
						{
									
							
							SessionBO.indexTableLAT=indexTableLAT;
									JPanel panelDB =null;
									int idMisura=GestioneMisuraBO.isPresentLAT(id);
			
			
									if(idMisura==0)
									{
										SessionBO.idStrumento=id;	
										idMisura=GestioneMisuraBO.insertMisuraLAT(id,indexTableLAT);
										
										if(indexTableLAT==1) 
										{
											GestioneMisuraBO.insertPuntiLivellaBolla(idMisura);
										}
										
										SessionBO.idMisura=idMisura;
										panelDB =new PannelloMisuraMaster(id);
									}
									else
									{
			
										SessionBO.idStrumento=id;
										SessionBO.idMisura=idMisura;
										panelDB =new PannelloMisuraMaster(id);
									}
									PannelloConsole.printArea("Apertura Scheda Strumento [ID]: "+id);
									SystemGUI.callPanel(panelDB, "PMM");
						
						}else
						{
							JOptionPane.showMessageDialog(null,"Questo strumento non è LAT","Attenzione",JOptionPane.WARNING_MESSAGE,new ImageIcon(PannelloTOP.class.getResource("/image/attention.png")));
						}

					}else
					{
						JOptionPane.showMessageDialog(null,"Selezionare una scheda","Attenzione",JOptionPane.WARNING_MESSAGE,new ImageIcon(PannelloTOP.class.getResource("/image/attention.png")));	
					}
				} catch (Exception e1) 
				{
					PannelloConsole.printException(e1);
					e1.printStackTrace();
				}
				d.close();
			}
		}

		JLabel lblFiltra = new JLabel("Ricerca");
		lblFiltra.setForeground(Color.black);
		lblFiltra.setFont(new Font("Arial",Font.BOLD, 14));
		add(lblFiltra,"cell 0 1, height :30:");

		String[] data={"Tutti","Lavorati","Chiusi","Aperti","Precedenti"};
		final JComboBox<String> combo= new JComboBox<String>(data);
		combo.setFont(new Font("Arial",Font.BOLD, 14));
		combo.setForeground(Color.black);
		add(combo,"cell 0 1, width 150::, height :30:");


		JLabel lblFiltraPerData = new JLabel("Dal");
		lblFiltraPerData.setForeground(Color.black);
		lblFiltraPerData.setFont(new Font("Arial",Font.BOLD, 14));
		add(lblFiltraPerData,"cell 0 1, gapleft 40, height :30:");

		dataDal= new JTextField();
		dataDal.setColumns(8);
		add(dataDal,"cell 0 1, gapleft 10, height :30:,width 85:85:");
		dataDal.setFont(new Font("Arial",Font.BOLD, 14));
		
		JLabel al = new JLabel("  al");
		al.setForeground(Color.black);
		al.setFont(new Font("Arial",Font.BOLD, 14));
		add(al,"cell 0 1, height :30:");

		 dataAl= new JTextField();
		
		dataAl.setColumns(8);
		dataAl.setFont(new Font("Arial",Font.BOLD, 14));
		add(dataAl,"cell 0 1, gapleft 10, height :30:,width 85:85:");


		JButton cercaData = new JButton("Cerca prossima verifica");
		add(cercaData,"cell 0 1, gapleft 20");

		cercaData.setIcon(new ImageIcon(PannelloTOP.class.getResource("/image/search.png")));
		cercaData.setFont(new Font("Arial",Font.BOLD, 14));

		add(scrollPane,"cell 0 2,growx,growy");

		JButton btnCreaStrumento = new JButton("Crea Strumento");

		JButton btnGetRowSelected = new JButton("Carica Scheda");
		btnGetRowSelected.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				d =	 new Splash(me);
	 			d.execute();
	 			
				Runnable runnable = new ValidateThread();
				Thread thread = new Thread(runnable);
				thread.start();


			}

		});
	//	btnGetRowSelected.setBounds(220, 425, 150, 50);
		btnGetRowSelected.setFont(new Font("Arial",Font.BOLD, 14));
		btnGetRowSelected.setIcon(new ImageIcon(PannelloTOP.class.getResource("/image/load.png")));
		btnCreaStrumento.setFont(new Font("Arial",Font.BOLD, 14));
		btnCreaStrumento.setIcon(new ImageIcon(PannelloTOP.class.getResource("/image/make.png")));
		add(btnCreaStrumento, "cell 0 1, gapleft 25, height :30:");

		JLabel labsearch= new JLabel("Cerca ");
		labsearch.setForeground(Color.black);
		labsearch.setFont(new Font("Arial",Font.BOLD, 14));
		
		add(labsearch,"cell 0 4,height :30:");



		jtfFilter.getDocument().addDocumentListener(new DocumentListener(){

			@Override
			public void insertUpdate(DocumentEvent e) {
				String text = jtfFilter.getText();

				if (text.trim().length() == 0) {
					rowSorter.setRowFilter(null);
				} else {
					rowSorter.setRowFilter(RowFilter.regexFilter("(?i)" + text));
				}
			}

			@Override
			public void removeUpdate(DocumentEvent e) {
				String text = jtfFilter.getText();

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
		jtfFilter.setFont(new Font("Arial",Font.BOLD,14));
		
		add(jtfFilter,"cell 0 4,height :30:");

		add(btnGetRowSelected, "cell 0 4,height :35:,gapleft 25");
		btnCreaStrumento.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {

				JPanel panelDB =new PannelloCreazioneStrumento();

				SystemGUI.callPanel(panelDB, "PCS");

			}
		});

		cercaData.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				
				String _dataDal =dataDal.getText();
				String _dataAl=dataAl.getText();
				
				try {
					ArrayList<StrumentoDTO> listaStrumentiNuova=new ArrayList<StrumentoDTO>();
					 
					SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
				        
					 Date parsedDataDal = format.parse(_dataDal);
					 Date parsedDataAl = format.parse(_dataAl);
				     
						for (int i = 0; i < table.getRowCount(); i++) 
						{
							String dataCompTab = table.getValueAt(i, 8).toString();
							
							if((dataCompTab!=null && dataCompTab.length()>0))
							{
								Date dataComp=format.parse(dataCompTab);
								
								 if (dataComp.compareTo(parsedDataDal) > 0 && dataComp.compareTo(parsedDataAl) < 0) 
								 {
							           listaStrumentiNuova.add(listaStrumenti.get(i));
							     }
							}
						}
						repaintTable(listaStrumentiNuova);
						
				} catch (Exception e) 
				{
					JOptionPane.showMessageDialog(null,"Le date non possono essere vuote e devono essere formattata come gg/mm/aaaa","Attenzione",JOptionPane.WARNING_MESSAGE,new ImageIcon(PannelloTOP.class.getResource("/image/attention.png")));
				}
			}
		});
		
		combo.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				try
				{	
					switch (combo.getSelectedIndex()) {
					case 0:
						listaStrumenti= GestioneStrumentoBO.getListaStrumenti(0);
						repaintTable(listaStrumenti);	
						break;
					case 1:
						listaStrumenti= GestioneStrumentoBO.getListaStrumenti(1);
						repaintTable(listaStrumenti);	
						break;
					case 2:
						listaStrumenti= GestioneStrumentoBO.getListaStrumenti(2);
						repaintTable(listaStrumenti);	
						break;
					case 3:
						listaStrumenti= GestioneStrumentoBO.getListaStrumenti(3);
						repaintTable(listaStrumenti);	
						break;
					case 4:
						listaStrumenti= GestioneStrumentoBO.getListaStrumenti(4);
						repaintTable(listaStrumenti);	
						break;

					default:
						break;
					}
				}catch(Exception ex)
				{
					ex.printStackTrace();
				}
			}

			
			
			
		});
		
		popupMenu= new JPopupMenu();
		jmit= new JMenuItem("Duplica Strumento");
		jmit.addActionListener(this);
		popupMenu.add(jmit);
		table.setComponentPopupMenu(popupMenu);
		
		
	}

	private void repaintTable(ArrayList<StrumentoDTO> listaStrumenti) {

		MyObjectManager manager = new MyObjectManager();
		for (int i = 0; i < listaStrumenti.size(); i++) {
			StrumentoDTO strumento=listaStrumenti.get(i);
			MyObject object = new MyObject(""+strumento.get__id(),strumento.getCodice_interno(),strumento.getMatricola(),strumento.getDenominazione(),
					strumento.getCostruttore(),strumento.getModello(),strumento.getDataUltimaVerifica(),strumento.getDataProssimaVerifica());
			manager.addObject(object);
		}

		table = new JTable(new MyTableModel(manager));
		table.setRowHeight(40);
		table.setFont(new Font("Arial", Font.PLAIN, 18));
		table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 16));
		
		int[] percColum= {5,7,8,10,22,14,14,9,9};
		
		int tableWidth=x*2;
		
		for(int i=0;i<9;i++) 
		{
			int p =Math.round((percColum[i]*tableWidth)/100);
			table.getColumnModel().getColumn(i).setPreferredWidth(p);
			
		}
		

		TableColumn column = table.getColumnModel().getColumn(0);
		column.setCellEditor(new RadioButtonCellEditorRenderer());
		column.setCellRenderer(new RadioButtonCellEditorRenderer());
		rowSorter= new TableRowSorter<TableModel>(table.getModel());

		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setRowSorter(rowSorter);
		table.setDefaultRenderer(Object.class, new MyCellRenderer());
		table.setComponentPopupMenu(popupMenu);
		
		scrollPane.getViewport().add(table);

		table.repaint();

		validate();
		repaint();

	}
	public class MyCellRenderer extends javax.swing.table.DefaultTableCellRenderer {


		public java.awt.Component getTableCellRendererComponent(javax.swing.JTable table, java.lang.Object value, boolean isSelected, boolean hasFocus, int row, int column) {

			final java.awt.Component cellComponent = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

			try
			{


				if(column==1)
				{


					Integer stato=GestioneMisuraBO.getStatoMisura(table.getValueAt(row, 1).toString());


					if(stato!=null && stato==0)
					{
						cellComponent.setForeground(Color.black);
						cellComponent.setBackground(new Color(250,210,51));
					
					}
					if(stato!=null && stato==1)
					{
						cellComponent.setForeground(Color.black);
						cellComponent.setBackground(Color.green);
					}
					if(stato!=null && stato==2)
					{
						cellComponent.setForeground(Color.black);
						cellComponent.setBackground(new Color(215,23,29));
					}
					if(stato==null)
					{
						cellComponent.setForeground(Color.black);
						cellComponent.setBackground(Color.white);

					}
					  if (isSelected)
				        {
				            setBackground(table.getSelectionBackground());
				            setForeground(table.getSelectionForeground());
				        }
				      
				}
			}catch (Exception e) {
				PannelloConsole.printException(e);
			}
			return cellComponent;

		}

	}

	@Override
	public void actionPerformed(ActionEvent event) {
		
		JMenuItem menu = (JMenuItem) event.getSource();
		if (menu == jmit) {
            duplicaStrumento();
        }
	}

	private void duplicaStrumento() {
		
		try
		{
			int selectedRow = table.getSelectedRow();
			if(selectedRow!=-1)
			{
				String idStrumento=""+table.getValueAt(selectedRow, 1);
				int idStrDuplicato=GestioneStrumentoBO.duplicaStrumento(idStrumento);
				JOptionPane.showMessageDialog(null, "Strumento duplicato con id: "+idStrDuplicato,"Attenzione",JOptionPane.WARNING_MESSAGE,new ImageIcon(PannelloTOP.class.getResource("/image/attention.png")));
				
				JPanel panelDB =new PannelloStrumentoMaster();
    			SystemGUI.callPanel(panelDB, "PSS");
			}
			else
			{
				JOptionPane.showMessageDialog(null, "Selezionare correttamente la riga dello strumento da duplicare","Attenzione",JOptionPane.WARNING_MESSAGE,new ImageIcon(PannelloTOP.class.getResource("/image/attention.png")));
			}
		}catch (Exception e) 
		{
		PannelloConsole.printException(e);
		}	
	}
}
