package it.calverDesktopLAT.bo;

import java.math.BigDecimal;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class SessionBO 
{
	public static String idStrumento="";
	public static int idMisura;
	public static String tipoRapporto;
	
	public static String actualPage="";
	public static String prevPage="";
	public static String nextPage="";
	
	public static int cifreSignificative=0;	

	
	public static BigDecimal sign_out_start=BigDecimal.ZERO;
	public static BigDecimal sign_out_fs=BigDecimal.ZERO;
	
	public static BigDecimal sign_str_start=BigDecimal.ZERO;
	public static BigDecimal sign_str_fs=BigDecimal.ZERO;
	public static Map<String, Object> globalKey;
	public static JFrame generarFrame;
	public static int numeroStrumenti;
	public static JPanel pannelloCore;
	public static int indexTableLAT;
	public static int widthFrame;
	public static int heightFrame;
	
	
}
