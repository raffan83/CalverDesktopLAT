package it.calverDesktopLAT.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URL;
import java.text.NumberFormat;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.BoundedRangeModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;
import javax.swing.border.Border;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.dial.DialBackground;
import org.jfree.chart.plot.dial.DialCap;
import org.jfree.chart.plot.dial.DialPlot;
import org.jfree.chart.plot.dial.DialTextAnnotation;
import org.jfree.chart.plot.dial.DialValueIndicator;
import org.jfree.chart.plot.dial.StandardDialFrame;
import org.jfree.chart.plot.dial.StandardDialScale;
import org.jfree.data.general.DefaultValueDataset;
import org.jfree.ui.GradientPaintTransformType;
import org.jfree.ui.StandardGradientPaintTransformer;

import it.calverDesktopLAT.bo.GestioneSonda;
import it.calverDesktopLAT.bo.TwoWaySerialComm;
import it.calverDesktopLAT.dto.SondaDTO;
import net.miginfocom.swing.MigLayout;
import javax.swing.border.TitledBorder;
import javax.swing.border.LineBorder;


class GuiWorker1 extends SwingWorker<Integer, Integer>{


	private JFrame g = new JFrame();
	public static JSlider slider1;
	DefaultValueDataset dataset1;
	GuiWorker1 worker=null;
	SondaDTO sonda=null;
	TwoWaySerialComm com;
	JButton transfer;
	JLabel tf;
	private JButton btnZero;
	private double zero;

	int precision;
	static int delay;
	static int media;
	static int bufferMedia;

	ArrayList<BigDecimal> listaValori;
	private JButton btnSetting;
	private JFrame framePosizione;
	
	public GuiWorker1(SondaDTO s, TwoWaySerialComm _com, JFrame frm) {

		
		if(s==null)
		{
			JOptionPane.showMessageDialog(null, "Il dispositivo è connesso ad una porta seriale, ma probabilmente è una porta di trasmissione", "Attenzione",JOptionPane.INFORMATION_MESSAGE,new ImageIcon(PannelloTOP.class.getResource("/image/attention.png")) );
		}
		
		/*Metodo calcolo valore*/  
		media=1;
		bufferMedia=10;
		listaValori= new ArrayList<>();
		framePosizione=frm;
		worker=this;  
		sonda=s;
		com=_com;

		delay=800;

		zero=0;
		precision=0;

		tf= new JLabel();
		g.getContentPane().setLayout(new BorderLayout());

		URL iconURL = this.getClass().getResource("/image/logo.png");


		ImageIcon img = new ImageIcon(iconURL);
		g.setIconImage(img.getImage());
		
		Point d=frm.getLocation(); 
      	
        int x = ( frm.getWidth()  - 300) / 2;
 		int y = ( frm.getHeight()  - 400) / 2;
 		
 		x=x+(int)d.getX();
 		y=y+(int)d.getY();
 		
 		g.setLocation(x, y);
		 
		dataset1 = new DefaultValueDataset(0D);

		DialPlot dialplot = new DialPlot();

		dialplot.setView(0.00D, 0.00D, 1.00D, 1.00D);
		dialplot.setDataset(0, dataset1);


		StandardDialFrame standarddialframe = new StandardDialFrame();
		standarddialframe.setBackgroundPaint(Color.lightGray);
		standarddialframe.setForegroundPaint(Color.darkGray);
		dialplot.setDialFrame(standarddialframe);

		GradientPaint gradientpaint = new GradientPaint(new Point(), new Color(255, 255, 255), new Point(), new Color(0, 170, 220));
		DialBackground dialbackground = new DialBackground(gradientpaint);

		dialbackground.setGradientPaintTransformer(new StandardGradientPaintTransformer(GradientPaintTransformType.VERTICAL));
		dialplot.setBackground(dialbackground);

		g.setTitle(s.getUm().replace("Â","") +" "+s.getLabel());
		DialTextAnnotation dialtextannotation = new DialTextAnnotation(s.getUm() +" "+s.getLabel());
		dialtextannotation.setFont(new Font("Dialog", 1, 12));
		dialtextannotation.setRadius(0.69999999999999996D);
	//	dialplot.addLayer(dialtextannotation);

		DialValueIndicator dialvalueindicator = new DialValueIndicator(0);
		dialvalueindicator.setFont(new Font("Dialog", 0, 14));
		dialvalueindicator.setOutlinePaint(Color.GREEN);
		dialvalueindicator.setRadius(0.59999999999999998D);
		dialvalueindicator.setAngle(-90D);
		NumberFormat df=NumberFormat.getNumberInstance();

		precision=s.getPrecision().intValue();
		dialvalueindicator.setNumberFormat(df);

		double maxScale=s.getMaxScale().doubleValue();

		BigDecimal perc =(new BigDecimal(maxScale).subtract(s.getMinScale()).divide(new BigDecimal(20)).setScale(2,RoundingMode.HALF_UP));


		StandardDialScale standarddialscale = new StandardDialScale(s.getMinScale().doubleValue(),maxScale, -120D, -300D,perc.doubleValue(), 0);
		standarddialscale.setTickRadius(0.88D);
		standarddialscale.setTickLabelOffset(0.14999999999999999D);
		standarddialscale.setTickLabelFont(new Font("Dialog", 0, 14));
		dialplot.addScale(0, standarddialscale);


		/*/
		 * Quadrante rosso
		 */
		StandardDialScale standarddialscale1 = new StandardDialScale(s.getMinScale().doubleValue(), maxScale, -120D, -300D, perc.doubleValue(), 4);
		standarddialscale1.setTickRadius(0.5D);
		standarddialscale1.setTickLabelOffset(0.14999999999999999D);
		standarddialscale1.setTickLabelFont(new Font("Dialog", 0, 10));
		standarddialscale1.setMajorTickPaint(Color.red);
		standarddialscale1.setMinorTickPaint(Color.red);
		dialplot.addScale(1, standarddialscale1);

		dialplot.mapDatasetToScale(1, 1);
		org.jfree.chart.plot.dial.DialPointer.Pointer pointer = new org.jfree.chart.plot.dial.DialPointer.Pointer(0);
		dialplot.addPointer(pointer);

		DialCap dialcap = new DialCap();
		dialcap.setRadius(0.10000000000000001D);
		dialplot.setCap(dialcap);



		JFreeChart jfreechart = new JFreeChart(dialplot);
		jfreechart.setTitle(s.getId_sonda());
		ChartPanel chartpanel = new ChartPanel(jfreechart);
		chartpanel.setBorder(new TitledBorder(new LineBorder(new Color(255, 0, 0), 2, true), "DASM", TitledBorder.LEADING, TitledBorder.TOP, null, Color.RED));
		chartpanel.setPreferredSize(new Dimension(400, 400));
		JPanel jpanel = new JPanel();
		jpanel.setLayout(new MigLayout("", "[150px][100px][150px]", "[23px][23px]"));
		slider1 = new JSlider(s.getMinScale().intValue(), s.getMaxScale().intValue());
		slider1.setMajorTickSpacing(1);
		slider1.setPaintTicks(true);
		slider1.setPaintLabels(true);
		slider1.addChangeListener(new BoundedChangeListener());

		tf.setFont(new Font("Arial", Font.BOLD, 30));
		tf.setHorizontalAlignment(SwingConstants.CENTER);
		tf.setVerticalAlignment(SwingConstants.CENTER);

		Border border = BorderFactory.createLineBorder(Color.red, 2);
		tf.setBorder(border);
		jpanel.add(tf, "cell 1 0,grow");



		g.addWindowListener(new WindowAdapter()
		{
			@Override
			public void windowClosing(WindowEvent e)
			{
				try {
					Thread.sleep(50);
					worker.cancel(true);
				//	com.disconnect();
					g.dispose();
				} catch (InterruptedException ex) {
					// TODO Auto-generated catch block
					//	e.printStackTrace();
				}

			}
		});

		g.getContentPane().add(chartpanel);      
		g.getContentPane().add(jpanel,"South");
		transfer = new JButton("Cattura");
		transfer.setFont(new Font("Arial", Font.BOLD, 14));
		transfer.setIcon(new ImageIcon(GuiWorker1.class.getResource("/image/catch.png")));
		jpanel.add(transfer, "flowx,cell 0 0,alignx center,growy");

		btnZero = new JButton("ZERO");
		btnZero.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				while(true)
				{
					Double d = GestioneSonda.getValue(com,sonda);

					if(d!=null)
					{
						zero=d;
						break;
					}

				}
			}
		});

		  if(s.isZero())
			  {
			  	
			  	jpanel.add(btnZero, "cell 2 0,alignx center,growy");

			  }    

			btnSetting = new JButton("Impostazioni");
			btnSetting.setFont(new Font("Arial", Font.BOLD, 14));
			btnSetting.setIcon(new ImageIcon(GuiWorker1.class.getResource("/image/calcola.png")));
			btnSetting.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) 
				{
				     SwingUtilities.invokeLater(new Runnable() {
				            public void run() 
				            
				            {
				               
				            	 JFrame frame = new JFrame("Password");
				                 frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);				         				  
					         	
				               
					    		
				                 //Create and set up the content pane.
				                 final Password newContentPane = new Password(frame);
				                 newContentPane.setOpaque(true); //content panes must be opaque
				                 frame.setContentPane(newContentPane);
				          
				                 Point d=g.getLocation(); 
						         	
					                int x = ( g.getWidth()  - 300) / 2;
						    		int y = ( g.getHeight()  - 100) / 2;
						    		
						    		x=x+(int)d.getX();
						    		y=y+(int)d.getY();
						    		
						    		frame.setLocation(x, y);
				
				                 
				                 //Make sure the focus goes to the right component
				                 //whenever the frame is initially given the focus.
				                 frame.addWindowListener(new WindowAdapter() {
				                     public void windowActivated(WindowEvent e) {
				                         newContentPane.resetFocus();
				                     }
				                 });
				          
				                 //Display the window.
				                 frame.pack();
				                 frame.setVisible(true);
				            }
				        });
				    }
			});

			jpanel.add(btnSetting, "cell 1 1,alignx center,growy");

			transfer.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent arg0) {

					Double value=GestioneSonda.getValue(com,sonda);

					FrameMisura.textField_lettura_sca_comp.setText(tf.getText());
					FrameMisura.valore_inserito.setText(tf.getText());
					
					System.out.println(value);
				}
			});

			g.pack();
			g.setVisible(true);
	}

	@Override
	protected  Integer doInBackground() throws Exception {
		System.out.println( "GuiWorker.doInBackground" );

		try{

			while(true)
			{	
				Double value=GestioneSonda.getValue(com,sonda);

				if(value!=null)	
				{

					if(media==0)
					{
						BigDecimal valueFormat=new BigDecimal(value-zero).setScale(precision,RoundingMode.HALF_UP);
						dataset1.setValue(valueFormat);
						tf.setText(""+valueFormat.toPlainString());
					}
					if(media==1 || media==2)
					{

						BigDecimal valoreMediano=getMedia(value-zero);
						dataset1.setValue(valoreMediano);
						tf.setText(""+valoreMediano.toPlainString());

					}
				}

				Thread.sleep(delay);
			}
		}catch (InterruptedException ie) {
			// TODO: handle exception
		}catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}


	private BigDecimal getMedia(double d) 
	{
		listaValori.add(new BigDecimal(d));

		if(listaValori.size()<bufferMedia)
		{
			return new BigDecimal(d).setScale(precision,RoundingMode.HALF_UP);
		}else
		{

			BigDecimal toReturn =BigDecimal.ZERO;

			for (int i = 0; i < bufferMedia; i++) 
			{
				toReturn=toReturn.add(listaValori.get(i));
			}

			swapArry(media);

			return toReturn.divide(new BigDecimal(bufferMedia),precision,RoundingMode.HALF_UP);
		}
	}
	private void swapArry(int method) {

		ArrayList<BigDecimal> newArray= new ArrayList<>();
		if(method==2)
		{	
			listaValori=newArray;

		}else
		{
			for (int i = 0; i < bufferMedia-1; i++) 
			{
				newArray.add(listaValori.get(i+1));
			}
			listaValori=newArray;
		}
	}


	@Override
	protected void done() {
		System.out.println("done");
		com.disconnect();
		
	}


	class BoundedChangeListener implements ChangeListener {
		public void stateChanged(ChangeEvent changeEvent) {
			Object source = changeEvent.getSource();
			if (source instanceof BoundedRangeModel) {
				BoundedRangeModel aModel = (BoundedRangeModel) source;
				if (!aModel.getValueIsAdjusting()) {
					System.out.println("Changed: " + aModel.getValue());
				}
			} else if (source instanceof JSlider) {
				JSlider theJSlider = (JSlider) source;
				if (!theJSlider.getValueIsAdjusting()) {
					System.out.println("Slider changed: " + theJSlider.getValue());
				}
			} else {
				System.out.println("Something changed: " + source);
			}
		}
	}



}
