package it.calverDesktopLAT.bo;

import java.awt.Color;
import java.math.BigDecimal;
import java.math.RoundingMode;

import javax.swing.JTextField;

import it.calverDesktopLAT.gui.FrameComposizioneCampioni;

public class GestioneFormuleBO {
	
	public static String[] getListaFormule() {
		String[] lista = new String[9];
		
		lista[0]="Seleziona Formula";
		lista[1]="(+) Somma Semplice";
		lista[2]="(+) Somma con propagazione";
		lista[3]="(-) Sottrazione semplice";
		lista[4]="(-) Sottrazione con propagazione";
		lista[5]="(*) Moltiplicazione semplice";
		lista[6]="(*) Moltiplicazione con propagazione";
		lista[7]="(÷) Divisione semplice";
		lista[8]="(÷) Divisione con propagazione";
		
		return lista;
	}
	

	public static void sommaPropagata(JTextField[] listaMisureConfermata,JTextField[] listaIncertezzaConfermata) {
		BigDecimal totalSumMisura=BigDecimal.ZERO;
		BigDecimal totalSumIncertezza=BigDecimal.ZERO;
		
		for (int i = 0; i < listaMisureConfermata.length; i++) {
			
			if(listaMisureConfermata[i]!=null && listaMisureConfermata[i].getText().length()>0)
			{
				totalSumMisura=totalSumMisura.add(new BigDecimal(listaMisureConfermata[i].getText()));
				BigDecimal incertezza=new BigDecimal(listaIncertezzaConfermata[i].getText());
				totalSumIncertezza=totalSumIncertezza.add(incertezza.pow(2));
				
			}
			
		}
		
		FrameComposizioneCampioni.txtMisura.setBackground(Color.green);
		FrameComposizioneCampioni.txtIncertezza.setBackground(Color.green);
		
		FrameComposizioneCampioni.txtMisura.setText(totalSumMisura.setScale(10,RoundingMode.HALF_UP).toPlainString());
		BigDecimal incertezzaVisualizzata=new BigDecimal(Math.sqrt(totalSumIncertezza.doubleValue()));
		FrameComposizioneCampioni.txtIncertezza.setText(incertezzaVisualizzata.setScale(10,RoundingMode.HALF_UP).toPlainString());
	}

	
	public static void sommaSemplice(JTextField[] listaMisureConfermata,JTextField[] listaIncertezzaConfermata) {
		BigDecimal totalSumMisura=BigDecimal.ZERO;
		BigDecimal totalSumIncertezza=BigDecimal.ZERO;
		
		for (int i = 0; i < listaMisureConfermata.length; i++) {
			
			if(listaMisureConfermata[i]!=null && listaMisureConfermata[i].getText().length()>0)
			{
				totalSumMisura=totalSumMisura.add(new BigDecimal(listaMisureConfermata[i].getText()));
				totalSumIncertezza=totalSumIncertezza.add(new BigDecimal(listaIncertezzaConfermata[i].getText()));
				
			}
			
		}
		
		FrameComposizioneCampioni.txtMisura.setBackground(Color.green);
		FrameComposizioneCampioni.txtIncertezza.setBackground(Color.green);
		
		FrameComposizioneCampioni.txtMisura.setText(totalSumMisura.setScale(10,RoundingMode.HALF_UP).toPlainString());
		FrameComposizioneCampioni.txtIncertezza.setText(totalSumIncertezza.setScale(10,RoundingMode.HALF_UP).toPlainString());
	}


	public static void sottrazioneSemplice(JTextField[] listaMisureConfermata,JTextField[] listaIncertezzaConfermata) {
		BigDecimal totalSumMisura=BigDecimal.ZERO;
		BigDecimal totalSumIncertezza=BigDecimal.ZERO;
		
		for (int i = 0; i < listaMisureConfermata.length; i++) {
			
			if(listaMisureConfermata[i]!=null && listaMisureConfermata[i].getText().length()>0)
			{
				if(i==0)
				{
					totalSumMisura=new BigDecimal(listaMisureConfermata[i].getText());
				}else
				{
					totalSumMisura=totalSumMisura.subtract(new BigDecimal(listaMisureConfermata[i].getText()));
				}
				
				totalSumIncertezza=totalSumIncertezza.add(new BigDecimal(listaIncertezzaConfermata[i].getText()));
				
			}
			
		}
		
		FrameComposizioneCampioni.txtMisura.setBackground(Color.green);
		FrameComposizioneCampioni.txtIncertezza.setBackground(Color.green);
		
		FrameComposizioneCampioni.txtMisura.setText(totalSumMisura.setScale(10,RoundingMode.HALF_UP).toPlainString());
		FrameComposizioneCampioni.txtIncertezza.setText(totalSumIncertezza.setScale(10,RoundingMode.HALF_UP).toPlainString());
		
	}


	public static void sottrazionePropagata(JTextField[] listaMisureConfermata,JTextField[] listaIncertezzaConfermata) {
	
		BigDecimal totalSumMisura=BigDecimal.ZERO;
		BigDecimal totalSumIncertezza=BigDecimal.ZERO;
		
		for (int i = 0; i < listaMisureConfermata.length; i++) {
			
			if(listaMisureConfermata[i]!=null && listaMisureConfermata[i].getText().length()>0)
			{

				if(i==0)
				{
					totalSumMisura=new BigDecimal(listaMisureConfermata[i].getText());
				}else
				{
					totalSumMisura=totalSumMisura.subtract(new BigDecimal(listaMisureConfermata[i].getText()));
				}
				
				
				BigDecimal incertezza=new BigDecimal(listaIncertezzaConfermata[i].getText());
				totalSumIncertezza=totalSumIncertezza.add(incertezza.pow(2));
				
			}
			
		}
		
		FrameComposizioneCampioni.txtMisura.setBackground(Color.green);
		FrameComposizioneCampioni.txtIncertezza.setBackground(Color.green);
		
		FrameComposizioneCampioni.txtMisura.setText(totalSumMisura.setScale(10,RoundingMode.HALF_UP).toPlainString());
		BigDecimal incertezzaVisualizzata=new BigDecimal(Math.sqrt(totalSumIncertezza.doubleValue()));
		FrameComposizioneCampioni.txtIncertezza.setText(incertezzaVisualizzata.setScale(10,RoundingMode.HALF_UP).toPlainString());
		
	}


	public static void moltiplicazioneSemplice(JTextField[] listaMisureConfermata,JTextField[] listaIncertezzaConfermata) {
		BigDecimal totalSumMisura=BigDecimal.ONE;
		BigDecimal totalSumIncertezza=BigDecimal.ONE;
		
		for (int i = 0; i < listaMisureConfermata.length; i++) {
			
			if(listaMisureConfermata[i]!=null && listaMisureConfermata[i].getText().length()>0)
			{
			
				totalSumMisura=totalSumMisura.multiply(new BigDecimal(listaMisureConfermata[i].getText()));
				totalSumIncertezza=totalSumIncertezza.multiply(new BigDecimal(listaIncertezzaConfermata[i].getText()));
				
			}
			
		}
		
		FrameComposizioneCampioni.txtMisura.setBackground(Color.green);
		FrameComposizioneCampioni.txtIncertezza.setBackground(Color.green);
		
		FrameComposizioneCampioni.txtMisura.setText(totalSumMisura.setScale(10,RoundingMode.HALF_UP).toPlainString());
		FrameComposizioneCampioni.txtIncertezza.setText(totalSumIncertezza.setScale(10,RoundingMode.HALF_UP).toPlainString());
		
		
	}


	public static void moltiplicazionePropagata(JTextField[] listaMisureConfermata,JTextField[] listaIncertezzaConfermata) {
		
		BigDecimal totalSumMisura=BigDecimal.ONE;
		BigDecimal totalSumIncertezza=BigDecimal.ONE;
		
		if(listaIncertezzaConfermata.length==2)
		{
			BigDecimal b =new BigDecimal(listaMisureConfermata[1].getText()).pow(2);
			BigDecimal uA =new BigDecimal(listaIncertezzaConfermata[0].getText()).pow(2);
			
			BigDecimal a =new BigDecimal(listaMisureConfermata[0].getText()).pow(2);
			BigDecimal uB =new BigDecimal(listaIncertezzaConfermata[1].getText()).pow(2);
			
			
			totalSumMisura=new BigDecimal(listaMisureConfermata[0].getText()).multiply(new BigDecimal(listaMisureConfermata[1].getText()));
			totalSumIncertezza=new BigDecimal(Math.sqrt((b.multiply(uA)).add(a.multiply(uB)).doubleValue()));
		}
		else
		{
			BigDecimal a =new BigDecimal(listaMisureConfermata[0].getText());
			BigDecimal b =new BigDecimal(listaMisureConfermata[1].getText());
			BigDecimal c =new BigDecimal(listaMisureConfermata[2].getText());
			
			BigDecimal uA =new BigDecimal(listaIncertezzaConfermata[0].getText()).pow(2);
			BigDecimal uB =new BigDecimal(listaIncertezzaConfermata[1].getText()).pow(2);
			BigDecimal uC =new BigDecimal(listaIncertezzaConfermata[2].getText()).pow(2);
			
			BigDecimal primoFattore=(b.multiply(c).pow(2)).multiply(uA);
			BigDecimal secondoFattore=(a.multiply(c).pow(2)).multiply(uB);
			BigDecimal terzoFattore=(a.multiply(b).pow(2)).multiply(uC);
			
			totalSumMisura=a.multiply(b).multiply(c);
			totalSumIncertezza=new BigDecimal(Math.sqrt(primoFattore.add(secondoFattore).add(terzoFattore).doubleValue()));
		}
		
		FrameComposizioneCampioni.txtMisura.setBackground(Color.green);
		FrameComposizioneCampioni.txtIncertezza.setBackground(Color.green);
		
		FrameComposizioneCampioni.txtMisura.setText(totalSumMisura.setScale(10,RoundingMode.HALF_UP).toPlainString());
		FrameComposizioneCampioni.txtIncertezza.setText(totalSumIncertezza.setScale(10,RoundingMode.HALF_UP).toPlainString());
		
	}


	public static void divisioneSemplice(JTextField[] listaMisureConfermata,JTextField[] listaIncertezzaConfermata) {
		
		BigDecimal totalSumMisura=BigDecimal.ZERO;
		BigDecimal totalSumIncertezza=BigDecimal.ZERO;
		
	
		totalSumMisura=new BigDecimal(listaMisureConfermata[0].getText()).divide(new BigDecimal(listaMisureConfermata[1].getText()),RoundingMode.HALF_UP);
		totalSumIncertezza=new BigDecimal(listaIncertezzaConfermata[0].getText()).divide(new BigDecimal(listaMisureConfermata[1].getText()),RoundingMode.HALF_UP);

		FrameComposizioneCampioni.txtMisura.setBackground(Color.green);
		FrameComposizioneCampioni.txtIncertezza.setBackground(Color.green);
		
		FrameComposizioneCampioni.txtMisura.setText(totalSumMisura.setScale(10,RoundingMode.HALF_UP).toPlainString());
		FrameComposizioneCampioni.txtIncertezza.setText(totalSumIncertezza.setScale(10,RoundingMode.HALF_UP).toPlainString());
		
	}


	public static void divisionePropagata(JTextField[] listaMisureConfermata,JTextField[] listaIncertezzaConfermata) {
		
		BigDecimal totalSumMisura=BigDecimal.ZERO;
		BigDecimal totalSumIncertezza=BigDecimal.ZERO;
		
		BigDecimal a=new BigDecimal(listaMisureConfermata[0].getText());
		BigDecimal b=new BigDecimal(listaMisureConfermata[1].getText());
		BigDecimal uA=new BigDecimal(listaIncertezzaConfermata[0].getText());
		BigDecimal uB=new BigDecimal(listaIncertezzaConfermata[1].getText());
	
		BigDecimal factor1=BigDecimal.ONE.divide(b,10,RoundingMode.HALF_UP);
		
		BigDecimal factor2=a.pow(2).divide(b.pow(2),RoundingMode.HALF_UP).multiply(uB.pow(2));
		
		totalSumMisura=a.divide(b,RoundingMode.HALF_UP);
		totalSumIncertezza=factor1.multiply(new BigDecimal(Math.sqrt(uA.pow(2).add(factor2).doubleValue())));
		

		FrameComposizioneCampioni.txtMisura.setBackground(Color.green);
		FrameComposizioneCampioni.txtIncertezza.setBackground(Color.green);
		
		FrameComposizioneCampioni.txtMisura.setText(totalSumMisura.setScale(10,RoundingMode.HALF_UP).toPlainString());
		FrameComposizioneCampioni.txtIncertezza.setText(totalSumIncertezza.setScale(10,RoundingMode.HALF_UP).toPlainString());
		
		
	}
}
