package it.calverDesktopLAT.bo;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.print.PageFormat;
import java.awt.print.Paper;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.EnumMap;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.swing.JTextField;

import org.apache.commons.codec.binary.Base64;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import it.calverDesktopLAT.dao.SQLiteDAO;
import it.calverDesktopLAT.dto.StrumentoDTO;
import it.calverDesktopLAT.utl.Costanti;

public class GestioneStampaBO {

	
	
	public static int stampaEtichetta(StrumentoDTO strumento, JTextField nCertificatoField, boolean esito,Date dataMisura, boolean fuoriServizio)
	{
		
		String printerType=GestioneRegistro.getStringValue(Costanti.COD_PRINT);
	try{	
		String codice= GestioneRegistro.getStringValue(Costanti.COD_OPT)+GestioneRegistro.getStringValue(Costanti.COD_CNT);
		
		if(codice==null)
		{
			return -1;
		}
		
		PrinterJob pj = PrinterJob.getPrinterJob();
		
		if (pj.printDialog()) {
		PageFormat pf = pj.defaultPage();
		Paper paper = pf.getPaper(); 
		//double width = fromCMToPPI(11);
		//double height = fromCMToPPI(2);
		double width = 0;
		double height = 0; 
		
		
		if(printerType.equals("1")) {
		width = fromCMToPPI(5);
		height = fromCMToPPI(5); 
		}else {
			width = fromCMToPPI(11);
			height = fromCMToPPI(2); 
		}
		
		paper.setSize(width, height);
		

		if(printerType.equals("1")) {
		paper.setImageableArea(
		12, 
		12, 
		fromCMToPPI(5), 
		fromCMToPPI(5)); 
		}else {
			paper.setImageableArea(
					0, 
					0, 
					fromCMToPPI(11), 
					fromCMToPPI(11)); 
		}
	//	System.out.println("Before- " + dump(paper)); 


		pf.setOrientation(PageFormat.PORTRAIT);
		//pf.setOrientation(PageFormat.LANDSCAPE);
		
		pf.setPaper(paper); 
	//	System.out.println("After- " + dump(paper));
	//	System.out.println("After- " + dump(pf)); 
		dump(pf); 
		PageFormat validatePage = pj.validatePage(pf);
	//	System.out.println("Valid- " + dump(validatePage)); 
		
		pj.setPrintable(new MyPrintable(strumento,codice,nCertificatoField,esito,dataMisura,fuoriServizio), pf);
		
		pj.print();
		}} catch 
		
		(Exception ex) {
			ex.printStackTrace();
		} 

		return 0;
		
	}

protected static double fromCMToPPI(double cm) { 
	return toPPI(cm * 0.393700787); 
	}


	protected static double toPPI(double inch) { 
	return inch * 72d; 
	}


	protected static String dump(Paper paper) { 
	StringBuilder sb = new StringBuilder(64);
	sb.append(paper.getWidth()).append("x").append(paper.getHeight())
	.append("/").append(paper.getImageableX()).append("x").
	append(paper.getImageableY()).append(" - ").append(paper
	.getImageableWidth()).append("x").append(paper.getImageableHeight()); 
	return sb.toString(); 
	}


	protected static String dump(PageFormat pf) { 
	Paper paper = pf.getPaper(); 
	return dump(paper); 
	}
	
	public static class MyPrintable implements Printable {

		StrumentoDTO strumento;
		Date dataMisura;
		String codice;
		JTextField nCertificatoField;
		boolean esito;
		boolean fuoriServizio;
		
		public MyPrintable(StrumentoDTO _strumento, String _codice, JTextField _nCertificatoField, boolean _esito, Date _dataMisura,boolean _fuoriServizio)
		{
			strumento=_strumento;
			codice=_codice;
			nCertificatoField=_nCertificatoField;
			esito=_esito;
			dataMisura=_dataMisura;
			fuoriServizio=_fuoriServizio;
			
		}
		
		@Override
		public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) throws PrinterException { 
		
		int result = NO_SUCH_PAGE; 	
		
		String printerType=GestioneRegistro.getStringValue(Costanti.COD_PRINT);
		try
		{	
		
	
		if (pageIndex < 1) { 
		Graphics2D g2d = (Graphics2D) graphics; 

		g2d.translate((int) pageFormat.getImageableX(), (int) pageFormat.getImageableY()); 
		FontMetrics fm = g2d.getFontMetrics();
		//int corAscent=fm.getAscent()+5;
		
		int corAscent=0;
		int incrementRow=0;
		int fontSize=0;
		
		if(printerType.equals("1")) {
		corAscent=113;
		incrementRow=16;
		fontSize=15;
		AffineTransform at = new AffineTransform();
		at.rotate(-Math.PI/2);

		g2d.setTransform(at);
		}else {
			corAscent=fm.getAscent()+9;
			incrementRow=6;
			fontSize=5;
		}
		
		
		g2d.setFont(new Font("Arial", Font.ITALIC, fontSize)); 
		
		Image img1Header = Toolkit.getDefaultToolkit().getImage(GestioneRegistro.getStringValue(Costanti.COD_IMG_PATH)+"/logo.png");
		
		if(printerType.equals("1")) {
	    g2d.drawImage(img1Header, -200,0,180, 90, null);
	    g2d.drawString("Codice Interno",-200,corAscent);
		}else {
		
			g2d.drawImage(img1Header, 0,0,46, 18, null);
			g2d.drawString("Codice Interno",0,corAscent);
		}
		
		g2d.setFont(new Font("Arial", Font.BOLD, fontSize));
		corAscent=corAscent+incrementRow;

		
		if(printerType.equals("1")) {
			g2d.drawString(strumento.getCodice_interno(),-200,corAscent);
			}else {
			
				g2d.drawString(strumento.getCodice_interno(),0,corAscent);
			}
		
	    corAscent=corAscent+incrementRow;
	//	}
	//	else
	//	{
	    g2d.setFont(new Font("Arial", Font.ITALIC, fontSize));
		
			if(printerType.equals("1")) {
				g2d.drawString("Matricola",-200,corAscent);
				}else {
				
					g2d.drawString("Matricola",-0,corAscent);
				}
			
			
	//		if(strumento.getMatricola()!=null)
	//		{
			g2d.setFont(new Font("Arial", Font.BOLD, fontSize));
			
				corAscent=corAscent+incrementRow;
				//g2d.drawString(strumento.getMatricola(),0,corAscent);
								
				if(printerType.equals("1")) {
					g2d.drawString(strumento.getMatricola(),-200,corAscent);
					}else {
					
						g2d.drawString(strumento.getMatricola(),0,corAscent);
					}			
		//	}
	//	}
	    g2d.setFont(new Font("Arial", Font.ITALIC, fontSize));
	    corAscent=corAscent+incrementRow;
	    
		if(printerType.equals("1")) {
			g2d.drawString("Data verifica",-200,corAscent);
			}else {
			
				g2d.drawString("Data verifica",0,corAscent);
			}	
	 
	   
	    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
	   // g2d.setFont(new Font("Arial", Font.BOLD, 5));
	    g2d.setFont(new Font("Arial", Font.BOLD, fontSize));
	    corAscent=corAscent+incrementRow;    
	    
		if(printerType.equals("1")) {
			g2d.drawString(sdf.format(dataMisura),-200,corAscent);    
			}else {
			
				g2d.drawString(sdf.format(dataMisura),0,corAscent);    
			}
	    //g2d.drawString(sdf.format(dataMisura),0,corAscent);
	    
	  
	   if(fuoriServizio==false) 
	   {
		   g2d.setFont(new Font("Arial", Font.ITALIC, fontSize));
		    corAscent=corAscent+incrementRow;
		    
		if(printerType.equals("1")) {
			 g2d.drawString("Pross. verifica",-200,corAscent);   
			}else {
			
				 g2d.drawString("Pross. verifica",0,corAscent);   
			}
	    
	    if(esito)
	    {
	    	Calendar c = Calendar.getInstance(); 
			c.setTime(dataMisura); 
	    	c.add(Calendar.MONTH,strumento.getFreq_taratura());
		    c.getTime();
	    g2d.setFont(new Font("Arial", Font.BOLD, fontSize));
	    corAscent=corAscent+incrementRow;
	    //g2d.drawString(sdf.format(new java.sql.Date(c.getTime().getTime())),0,corAscent);
	    
	    
		if(printerType.equals("1")) {
			g2d.drawString(sdf.format(new java.sql.Date(c.getTime().getTime())),-200,corAscent);
			}else {
			
				g2d.drawString(sdf.format(new java.sql.Date(c.getTime().getTime())),0,corAscent);
			}
	    
	    
	    }else
	    {
	    	g2d.setFont(new Font("Arial", Font.BOLD, fontSize));
	  	    corAscent=corAscent+incrementRow;
	  	    
			if(printerType.equals("1")) {
				g2d.drawString("- - ",-200,corAscent);
				}else {
				
					g2d.drawString("- - ",0,corAscent);
				}
	  	    
	  	    
	  	    
	    }
	}else 
	{
		 g2d.setFont(new Font("Arial", Font.ITALIC, fontSize));
		 corAscent=corAscent+incrementRow;
		    
		if(printerType.equals("1")) {
			 g2d.drawString("Stato Strumento",-200,corAscent);
			  corAscent=corAscent+incrementRow;
			  g2d.setFont(new Font("Arial", Font.BOLD, fontSize));
			  g2d.drawString("FUORI SERVIZIO",-200,corAscent);
			}else {
			
				 g2d.drawString("Stato Strumento",0,corAscent);
				 corAscent=corAscent+incrementRow;
				 g2d.setFont(new Font("Arial", Font.BOLD, fontSize));
				  g2d.drawString("FUORI SERVIZIO",0,corAscent);
			}
	}
	    g2d.setFont(new Font("Arial", Font.ITALIC, fontSize));
	    corAscent=corAscent+incrementRow;
	    
	    if(printerType.equals("1")) {
	    	 g2d.drawString("N° scheda",-200,corAscent);
			}else {
			
				 g2d.drawString("N° scheda",0,corAscent);
			}
	    
	  
	    g2d.setFont(new Font("Arial", Font.BOLD, fontSize));
	    corAscent=corAscent+incrementRow;
	    
	    if(nCertificatoField.getText().length()>0)
	    {
	    	 if(printerType.equals("1")) {
	    		 g2d.drawString(nCertificatoField.getText(),-200,corAscent);
				}else {
				
					g2d.drawString(nCertificatoField.getText(),0,corAscent);
				}
	    	
	    }
	    else
	    {
	    	String code=strumento.getTipoRapporto()+""+codice;
	    	g2d.drawString(code,-200,corAscent);
	    	
	    	 if(printerType.equals("1")) {
	    		 g2d.drawString(code,-200,corAscent);
				}else {
				
					g2d.drawString(code,0,corAscent);
				}
	    	
	    	nCertificatoField.setText(code);
	    	incrementa();
	    	salvaCertificato(strumento.get__id(),code);
	    	
	    }
	    
	   if(strumento.getCreato().equals("N"))
	   {
	    createQR(strumento);
	    Image img1 = Toolkit.getDefaultToolkit().getImage(GestioneRegistro.getStringValue(Costanti.COD_IMG_PATH)+"\\qr.png");
	    //int w11=img1.getWidth(null);
	    //int h11=img1.getHeight(null);
	    //g2d.drawImage(img1, -5,corAscent,w11, h11, null);
	    int w11=0;
	    int h11=0;
	    
	    
	    
   	 if(printerType.equals("1")) {
   		w11=175;
	    h11=175;
	    g2d.drawImage(img1, -200,corAscent,w11, h11, null);
		}
   	 else {
		w11=img1.getWidth(null);
		h11=img1.getHeight(null);
		g2d.drawImage(img1, -5,corAscent,w11, h11, null);
		}
	
	    
	    
	   }
		result = PAGE_EXISTS;
		pageIndex=1;
		}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return result; 
		}

		}
	
	
	public static void createQR(StrumentoDTO strumento) throws Exception
	{
		byte[] bytesEncoded = Base64.encodeBase64((""+strumento.get__id()).getBytes());
		System.out.println("encoded value is " + new String(bytesEncoded));

 		String myCodeText = "http://"+Costanti.DEPLOY_HOST+"/dettaglioStrumentoFull.do?id_str="+new String(bytesEncoded);
		
		String filePath = GestioneRegistro.getStringValue(Costanti.COD_IMG_PATH)+"\\qr.png";
		int size = 60;
		String fileType = "png";
		File myFile = new File(filePath);
		try {
			
			Map<EncodeHintType, Object> hintMap = new EnumMap<EncodeHintType, Object>(EncodeHintType.class);
			hintMap.put(EncodeHintType.CHARACTER_SET, "UTF-8");
			
			// Now with zxing version 3.2.1 you could change border size (white border size to just 1)
			hintMap.put(EncodeHintType.MARGIN, 0); /* default = 4 */
			hintMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);
 
			QRCodeWriter qrCodeWriter = new QRCodeWriter();
			BitMatrix byteMatrix = qrCodeWriter.encode(myCodeText, BarcodeFormat.QR_CODE, size,
					size, hintMap);
			int CrunchifyWidth = byteMatrix.getWidth();
			BufferedImage image = new BufferedImage(CrunchifyWidth, CrunchifyWidth,
					BufferedImage.TYPE_INT_RGB);
			image.createGraphics();
 
			Graphics2D graphics = (Graphics2D) image.getGraphics();
			graphics.setColor(Color.WHITE);
			graphics.fillRect(0, 0, CrunchifyWidth, CrunchifyWidth);
			graphics.setColor(Color.BLACK);
 
			for (int i = 0; i < CrunchifyWidth; i++) {
				for (int j = 0; j < CrunchifyWidth; j++) {
					if (byteMatrix.get(i, j)) {
						graphics.fillRect(i, j, 1, 1);
					}
				}
			}
			ImageIO.write(image, fileType, myFile);
		} catch (WriterException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void incrementa() throws SQLException {
		
	 int cnt=Integer.parseInt(GestioneRegistro.getStringValue(Costanti.COD_CNT));
	 cnt=cnt+1;
	 GestioneRegistro.setStringValue(Costanti.COD_CNT,""+ cnt);
	 
	}
	

	private static void salvaCertificato(int id, String code) throws SQLException {
		
		SQLiteDAO.salvaCertificato(id,code);
		
	}
}
