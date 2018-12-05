package it.calverDesktopLAT.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

import it.calverDesktopLAT.bo.GestioneMisuraBO;
import it.calverDesktopLAT.bo.SessionBO;
import it.calverDesktopLAT.dto.MisuraDTO;
import it.calverDesktopLAT.dto.ProvaMisuraDTO;
import it.calverDesktopLAT.dto.TabellaMisureDTO;
import it.calverDesktopLAT.utl.Costanti;

public class FrameCampioni extends JFrame {
	
JScrollPane panel;
	
	private JTable table;
	ArrayList<JTable> listaJTable=null;
	JPanel pannelloTabelle;
	ProvaMisuraDTO lista=null;
	public FrameCampioni()
	{
		
		SessionBO.prevPage="PMM";
		panel=new JScrollPane();
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		int x = (dim.width - 530) / 2;
		int y = (dim.height - 600) / 2;
		setLocation(x, y);
		setResizable(false);
		panel.setBackground(Color.white);
		panel.setLayout(null);
		setSize(530, 600);
		panel = new JScrollPane();
		panel.setBorder(new LineBorder(Costanti.COLOR_RED, 2, true));
		setTitle("Campioni Utilizzati");
		
		
		
		try {
			costruisciPannello();
			this.getContentPane().add(panel);
		} catch (Exception e) {
			PannelloConsole.printException(e);
			e.printStackTrace();
		}
		
	}

	private void costruisciPannello() throws Exception {
		
		lista =GestioneMisuraBO.getProvaMisura(SessionBO.idStrumento);
		pannelloTabelle = new JPanel();
		pannelloTabelle.setLayout(null);
		int height=lista.getListaTabelle().size()*215;
		pannelloTabelle.setPreferredSize(new Dimension(520, height));
		JLabel lblNewLabel = new JLabel("Lista Campioni Utilizzati");
		lblNewLabel.setFont(new Font("Arial", Font.BOLD, 16));
		lblNewLabel.setBounds(10, 10, 245, 20);
		
		pannelloTabelle.add(lblNewLabel);
		
		listaJTable= new ArrayList<>();
		
		int yAxis=	35;
		
		for (int y = 0; y < lista.getListaTabelle().size(); y++) {
		
		
		TabellaMisureDTO tabella = lista.getListaTabelle().get(y);	
		 
		table = new JTable();
		
		table.setFont(new Font("Arial", Font.PLAIN, 14));
		table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
		table.setRowHeight(20);
		
		table.setDefaultRenderer(Object.class, new MyCellRenderer(tabella.getTipoProva()));
		DefaultTableModel model = new DefaultTableModel() {

			@Override
			public Class<?> getColumnClass(int column) {
				switch (column) {
				case 0:
					return String.class;
				case 1:
					return String.class;
				case 2:
					return String.class;
				default:
					return String.class;
				}
			}
			 @Override
			public boolean isCellEditable(int row, int column) {
				return false;		
			}
			 
		};




		model.addColumn("Tipo Verifica");
		model.addColumn("Descrizione Campione");
		model.addColumn("Descrizione Parametro");

		for (int i = 0; i < tabella.getListaMisure().size(); i++) {
			MisuraDTO misura=tabella.getListaMisure().get(i);
			model.addRow(new Object[0]);
			model.setValueAt(misura.getTipoVerifica(), i, 0);
			model.setValueAt(misura.getDescrizioneCampione(), i, 1);
			model.setValueAt(misura.getDescrizioneParametro(), i, 2);
			
		}
		table.setModel(model);

		int add=y*210;
		
		JPanel pan1= new JPanel();
		String title=  getTitle(tabella);
		pan1.setBorder (BorderFactory.createTitledBorder (new LineBorder(Costanti.COLOR_RED,2),
                 title,
                 TitledBorder.LEFT,
                 TitledBorder.TOP,new Font("Arial", Font.BOLD,14),Costanti.COLOR_RED));
		pan1.setBounds(10,yAxis+add,480,190);
	//	pan1.setCBorder(new LineBorder(Color.CYAN,2));
		pan1.setLayout(null);
		
		JScrollPane scroll = new JScrollPane();
		scroll.setSize(300,150);
		scroll.setBorder(new LineBorder(Color.gray,2));
		scroll.setBounds(10,20, 450, 160);
		scroll.setViewportView(table);
		
		pan1.add(scroll);
		
		
		pannelloTabelle.add(pan1);
		
		}
		panel.setViewportView(pannelloTabelle);
	}

	

	private String getTitle(TabellaMisureDTO tabella) {
		String title="";
		 
		String[] arrProva=tabella.getTipoProva().split("_");
		
		if(arrProva[0].equals("L"))
		{
			title="Linearità "+arrProva[1]+" p.";
		}else
		{
			title="Ripetibilità "+arrProva[1]+"x"+arrProva[2]+" p.";
		}
		return title;
	}

	public class MyCellRenderer extends javax.swing.table.DefaultTableCellRenderer {
		
		String tipo="";
		public MyCellRenderer(String _tipo)
		{
			tipo=_tipo;
		}

        public java.awt.Component getTableCellRendererComponent(javax.swing.JTable table, java.lang.Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        
        	final java.awt.Component cellComponent = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

      
             String[] arrProva=tipo.split("_");
             
        if(arrProva[0].equals("L"))
        {	
            if (row % 2 == 0) 
            {
                cellComponent.setForeground(Color.black);
                cellComponent.setBackground(Color.white);

            }
            else
            {
            	cellComponent.setForeground(Color.black);
                cellComponent.setBackground(new Color(224,224,224));
                
            }
        }
        else
        {
        	ArrayList<Boolean> checkList= getListaControlloRipetibilita(Integer.parseInt(arrProva[1]),Integer.parseInt(arrProva[2]));
 
        	if(checkList.get(row)==false)
        	{
        		 cellComponent.setForeground(Color.black);
                 cellComponent.setBackground(Color.white);	
        	}else
        	{
        		cellComponent.setForeground(Color.black);
                cellComponent.setBackground(new Color(224,224,224));
        	}
        }
            
        return cellComponent;

        }

		private ArrayList<Boolean> getListaControlloRipetibilita(int punti, int ripetizioni) {
			
			boolean flag=false;
			ArrayList<Boolean> lista=new ArrayList<Boolean>();

			for (int i = 1; i <= ripetizioni; i++) {
				
				if(i % 2 == 0)
				{
					flag=false;
				}
				else
				{
					flag=true;
				}
				for (int j = 1; j <= punti; j++) 
				{
					lista.add(flag);
				}
			
			}
			return lista;
		}

    }

	
	
	

}
