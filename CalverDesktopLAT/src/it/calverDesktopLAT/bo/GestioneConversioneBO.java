package it.calverDesktopLAT.bo;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;

import it.calverDesktopLAT.dao.SQLiteDAO;
import it.calverDesktopLAT.dto.CampioneCorrettoDTO;
import it.calverDesktopLAT.dto.ConversioneDTO;
import it.calverDesktopLAT.dto.ParametroTaraturaDTO;
import it.calverDesktopLAT.utl.Costanti;
import it.calverDesktopLAT.utl.Utility;

public class GestioneConversioneBO {

	public static String[] getListaUMConvertibili(String um_fond, String tipoGrandezza) throws Exception {
		
		if(tipoGrandezza.equals("Temperatura"))
		{
		String []	listaTemperature= Utility.getListaTemperature(um_fond);
		
		return listaTemperature;
		}
		
		
		ArrayList<String> tmp=SQLiteDAO.getListaUMConvertibili(um_fond,tipoGrandezza);
		
		String[] stockArr = new String[tmp.size()];
		
		stockArr = tmp.toArray(stockArr);
		
			return stockArr;
	
	}


	public static String[] getListaFattoriMoltiplicativi() throws Exception {
	
		ArrayList<String> tmp=SQLiteDAO.getListaFattoriMoltiplicativi();
		
		String[] stockArr = new String[tmp.size()];
		
		stockArr = tmp.toArray(stockArr);
		
			return stockArr;
	}

	public static double getPotenza(String str) throws Exception {
	
		return SQLiteDAO.getPotenza(str);
	}
	
	private static double getPotenzaPerClasse(String cls) throws Exception {
		
		return SQLiteDAO.getPotenzaPerClasse(cls);
	}

	public static ConversioneDTO getFattoreConversione(String um_da,String umCon,String tipoGrandezza) throws Exception {
		// TODO Auto-generated method stub
		ConversioneDTO conversione= SQLiteDAO.getFattoreConversione(um_da, umCon,tipoGrandezza);
		
		return conversione;
	}

	public static ConversioneDTO getValoreConvertito(ParametroTaraturaDTO parametro, String umCon,String tipoGrandezza,BigDecimal minScala,BigDecimal maxScala) {
	
		ConversioneDTO fattoreConversione=null;
	
		try {

			double potenza=1;

			fattoreConversione=getFattoreConversione(parametro.getUm_fond(),umCon.substring(0,umCon.indexOf("@")).trim(),tipoGrandezza);
	
			if(minScala==null){
			
			BigDecimal valConvertito=null;
			BigDecimal incertezzaConvertita=null;
			
			if(!parametro.getUm().equals(parametro.getUm_fond()))
			{
			int index=parametro.getUm().length()-parametro.getUm_fond().length();	
			String str=parametro.getUm().substring(0,index);
			potenza=getPotenza(str);
			}
			
			double grado=0;
			
			if(fattoreConversione.getPotenza()==10 && potenza==1)
			{
				grado=1;
			}else
			{
				grado=Math.pow(fattoreConversione.getPotenza(),potenza);	
			}
			
		
			 BigDecimal incertezza=GestioneCampioneBO.getIncertezza(parametro.getIncertezzaAssoluta(),parametro.getIncertezzaRelativa(),parametro.getValore_nominale());
			 
			 valConvertito=	parametro.getValoreTaratura().multiply(fattoreConversione.getFattoreConversione()).multiply(new BigDecimal(grado));
			
			 incertezzaConvertita=incertezza.multiply(fattoreConversione.getFattoreConversione()).multiply(new BigDecimal(grado));
	
			fattoreConversione.setValoreConvertito(valConvertito.setScale(10,RoundingMode.HALF_UP));
			fattoreConversione.setIncertezzaConvertita(incertezzaConvertita.setScale(10,RoundingMode.HALF_UP));
			}
			else
			{

				if(!parametro.getUm().equals(parametro.getUm_fond()))
				{
				int index=parametro.getUm().length()-parametro.getUm_fond().length();	
				String str=parametro.getUm().substring(0,index);
				potenza=getPotenza(str);
				}
				
				double grado=0;
				
				if(fattoreConversione.getPotenza()==10 && potenza==1)
				{
					grado=1;
				}else
				{
					grado=Math.pow(fattoreConversione.getPotenza(),potenza);	
				}
				
				 minScala=	minScala.multiply(fattoreConversione.getFattoreConversione()).multiply(new BigDecimal(grado));
				 maxScala=	maxScala.multiply(fattoreConversione.getFattoreConversione()).multiply(new BigDecimal(grado));

			if(umCon.equals("sec @ Periodo"))
			{ 	 
				minScala=conversionePeriodo(minScala);
				maxScala=conversionePeriodo(maxScala);
			}	
			
			 fattoreConversione.setValoreMinScalaCanvertito(minScala.setScale(10,RoundingMode.HALF_UP));
			 fattoreConversione.setValoreMaxScalaCanvertito(maxScala.setScale(10,RoundingMode.HALF_UP));
			}
		
		
		} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		return fattoreConversione;
	}

	public static ConversioneDTO getValoreConvertitoTemperatura(ParametroTaraturaDTO parametro, String umCon, String tipoMisura,BigDecimal minScala, BigDecimal maxScala) {
		ConversioneDTO fattoreConversione=new ConversioneDTO();
		
		try {

		if(umCon.startsWith("K") && parametro.getUm_fond().equals("°C"))
		{
			minScala=minScala.add(new BigDecimal("273.15"));
			maxScala=maxScala.add(new BigDecimal("273.15"));
			 fattoreConversione.setValoreMinScalaCanvertito(minScala.setScale(10,RoundingMode.HALF_UP));
			 fattoreConversione.setValoreMaxScalaCanvertito(maxScala.setScale(10,RoundingMode.HALF_UP));
		}
		if(umCon.startsWith("C") && parametro.getUm_fond().equals("°K"))
		{
			minScala=minScala.subtract(new BigDecimal("273.15"));
			maxScala=maxScala.subtract(new BigDecimal("273.15"));
			 fattoreConversione.setValoreMinScalaCanvertito(minScala.setScale(10,RoundingMode.HALF_UP));
			 fattoreConversione.setValoreMaxScalaCanvertito(maxScala.setScale(10,RoundingMode.HALF_UP));
		}
		
		if(umCon.startsWith("°F") && parametro.getUm_fond().equals("°C"))
		{
			minScala=(minScala.multiply(new BigDecimal("1.8"))).add(new BigDecimal("32"));
			maxScala=(maxScala.multiply(new BigDecimal("1.8"))).add(new BigDecimal("32"));
			 fattoreConversione.setValoreMinScalaCanvertito(minScala.setScale(10,RoundingMode.HALF_UP));
			 fattoreConversione.setValoreMaxScalaCanvertito(maxScala.setScale(10,RoundingMode.HALF_UP));
		}
		if(umCon.startsWith("°C") && parametro.getUm_fond().equals("°F"))
		{
			minScala=(minScala.divide(new BigDecimal("1.8"),RoundingMode.HALF_UP)).subtract(new BigDecimal("32"));
			maxScala=(maxScala.divide(new BigDecimal("1.8"),RoundingMode.HALF_UP)).subtract(new BigDecimal("32"));
			 fattoreConversione.setValoreMinScalaCanvertito(minScala.setScale(10,RoundingMode.HALF_UP));
			 fattoreConversione.setValoreMaxScalaCanvertito(maxScala.setScale(10,RoundingMode.HALF_UP));
		}
		
		
		
		} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		fattoreConversione.setPotenza(10);
		return fattoreConversione;
		
	}
	
	
	public static double getPotenzaPerClasse(String classe, double potenza) {
		
		double toRet=0;
		try {
			String cls=classe.substring(0,classe.indexOf("(")).trim();
			
			double pot=getPotenzaPerClasse(cls);
			
			 toRet=Math.pow(potenza,pot);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
 
		return toRet;
	}

	public static BigDecimal[] getMinMaxScala(String codice,String parametro) throws Exception {
	 return	SQLiteDAO.getMinMaxScala(codice,parametro);
		
	}

	public static CampioneCorrettoDTO getValoreCampioneCorretto(String parTar,BigDecimal valore_inserito,boolean conversione, String umCon, String tipoGrandezza,double fm, String campione) {
		
		CampioneCorrettoDTO campioneCorretto=null;
		
		try
		{	
		ArrayList<ParametroTaraturaDTO> listaParametri= GestioneCampioneBO.getParametriTaraturaSelezionato(parTar,campione);
		
		
		
		ParametroTaraturaDTO limiteInferiore= null;
		ParametroTaraturaDTO limiteSuperiore=null;
		ConversioneDTO fattoreConversione=null;
		
		if(conversione)
		{
			ParametroTaraturaDTO parametro =listaParametri.get(0);
			double potenza=1;

			fattoreConversione=getFattoreConversione(parametro.getUm_fond(),umCon.substring(0,umCon.indexOf("@")).trim(),tipoGrandezza);
	
			if(!parametro.getUm().equals(parametro.getUm_fond()))
			{
			int index=parametro.getUm().length()-parametro.getUm_fond().length();	
			String str=parametro.getUm().substring(0,index);
			potenza=getPotenza(str);
			
			}
			
			double grado=0;
			
			if(fattoreConversione.getPotenza()==10 && potenza==1)
			{
				grado=1;
			}else
			{
				grado=Math.pow(fattoreConversione.getPotenza(),potenza);	
			}
			
			for (int i=0;i<listaParametri.size()-1;i++)
			{
				BigDecimal param1= (listaParametri.get(i).getValoreTaratura().multiply(fattoreConversione.getFattoreConversione()).multiply(new BigDecimal(grado))).divide(new BigDecimal(fm),RoundingMode.HALF_UP);
				BigDecimal param2= (listaParametri.get(i+1).getValoreTaratura().multiply(fattoreConversione.getFattoreConversione()).multiply(new BigDecimal(grado))).divide(new BigDecimal(fm),RoundingMode.HALF_UP);
						
				if((valore_inserito.compareTo(param1)==1 ||valore_inserito.compareTo(param1)==0 )&& (valore_inserito.compareTo(param2)==-1 || valore_inserito.compareTo(param2)==0 ))
				{
					
					listaParametri.get(i).setValore_nominale((listaParametri.get(i).getValore_nominale().setScale(Costanti.SCALA,RoundingMode.HALF_UP).multiply(fattoreConversione.getFattoreConversione()).multiply(new BigDecimal(grado))).divide(new BigDecimal(fm),RoundingMode.HALF_UP));
					listaParametri.get(i).setValoreTaratura((listaParametri.get(i).getValoreTaratura().setScale(Costanti.SCALA,RoundingMode.HALF_UP).multiply(fattoreConversione.getFattoreConversione()).multiply(new BigDecimal(grado))).divide(new BigDecimal(fm),RoundingMode.HALF_UP));
					
					if(listaParametri.get(i).getIncertezzaAssoluta()!=null)
					{
						listaParametri.get(i).setIncertezzaAssoluta((listaParametri.get(i).getIncertezzaAssoluta().setScale(Costanti.SCALA,RoundingMode.HALF_UP).multiply(fattoreConversione.getFattoreConversione()).multiply(new BigDecimal(grado))).divide(new BigDecimal(fm),RoundingMode.HALF_UP));
						
					}
					if(listaParametri.get(i).getIncertezzaRelativa()!=null)
					{
						listaParametri.get(i).setIncertezzaRelativa((listaParametri.get(i).getIncertezzaRelativa().setScale(Costanti.SCALA,RoundingMode.HALF_UP).multiply(fattoreConversione.getFattoreConversione()).multiply(new BigDecimal(grado))).divide(new BigDecimal(fm),RoundingMode.HALF_UP));
					}
					
					limiteInferiore=listaParametri.get(i);
					
					
					listaParametri.get(i+1).setValore_nominale((listaParametri.get(i+1).getValore_nominale().setScale(Costanti.SCALA,RoundingMode.HALF_UP).multiply(fattoreConversione.getFattoreConversione()).multiply(new BigDecimal(grado))).divide(new BigDecimal(fm),RoundingMode.HALF_UP));
					
					listaParametri.get(i+1).setValoreTaratura((listaParametri.get(i+1).getValoreTaratura().setScale(Costanti.SCALA,RoundingMode.HALF_UP).multiply(fattoreConversione.getFattoreConversione()).multiply(new BigDecimal(grado))).divide(new BigDecimal(fm),RoundingMode.HALF_UP));
					
					if(listaParametri.get(i+1).getIncertezzaAssoluta()!=null)
					{
						listaParametri.get(i+1).setIncertezzaAssoluta((listaParametri.get(i+1).getIncertezzaAssoluta().setScale(Costanti.SCALA,RoundingMode.HALF_UP).multiply(fattoreConversione.getFattoreConversione()).multiply(new BigDecimal(grado))).divide(new BigDecimal(fm),RoundingMode.HALF_UP));
						
					}
					if(listaParametri.get(i+1).getIncertezzaRelativa()!=null)
					{
						listaParametri.get(i+1).setIncertezzaRelativa((listaParametri.get(i+1).getIncertezzaRelativa().setScale(Costanti.SCALA,RoundingMode.HALF_UP).multiply(fattoreConversione.getFattoreConversione()).multiply(new BigDecimal(grado))).divide(new BigDecimal(fm),RoundingMode.HALF_UP));
					}
					
					limiteSuperiore=listaParametri.get(i+1);
					campioneCorretto = new CampioneCorrettoDTO();
					break;
				}
			}
			
			
			
		}
		
		else
		{
			for (int i=0;i<listaParametri.size()-1;i++)
			{
				if((valore_inserito.compareTo(listaParametri.get(i).getValoreTaratura())==1 ||valore_inserito.compareTo(listaParametri.get(i).getValoreTaratura())==0 )&&
						(valore_inserito.compareTo(listaParametri.get(i+1).getValoreTaratura())==-1 || valore_inserito.compareTo(listaParametri.get(i+1).getValoreTaratura())==0 ))
				{
					limiteInferiore=listaParametri.get(i);
					limiteSuperiore=listaParametri.get(i+1);
					campioneCorretto = new CampioneCorrettoDTO();
					break;
				}
			}
		}
		
		if(limiteInferiore!=null && limiteSuperiore!=null)
		{
			
			/*
			 var 1= valoreInserito - valoreTaratura.limiteInferiore
			 
			 var 2= correzioneSup - correzioneInf
			 
			 var numeratore =var1 * var 2 
			 
			 var denominatore = (valoreTaratura.limitesuperiore - valoreTaratura.limiteInferiore) 
			 
			 correzione= numeratore / denominatore
			 
			 valoreCorretto =valoreInserito + correzione + correzioneInf
			 
			  */
			
			BigDecimal correzioneInferiore= limiteInferiore.getValore_nominale().subtract(limiteInferiore.getValoreTaratura());
			
			BigDecimal correzioneSuperiore= limiteSuperiore.getValore_nominale().subtract(limiteSuperiore.getValoreTaratura());
			
			BigDecimal var1 = valore_inserito.subtract(limiteInferiore.getValoreTaratura());
			
			BigDecimal var2 = correzioneSuperiore.subtract(correzioneInferiore);
			
			BigDecimal numeratore = var1.multiply(var2);
			
			BigDecimal denominatore = (limiteSuperiore.getValoreTaratura().subtract(limiteInferiore.getValoreTaratura())).add(correzioneInferiore);

			BigDecimal correzione =(numeratore.divide(denominatore,10, RoundingMode.HALF_EVEN)).add(correzioneInferiore);
			
			campioneCorretto.setValoreCampioneCorretto(valore_inserito.add(correzione));
			
			
			BigDecimal incertezzaInferiore=GestioneCampioneBO.getIncertezza(limiteInferiore.getIncertezzaAssoluta(), limiteInferiore.getIncertezzaRelativa(), limiteInferiore.getValore_nominale());
		
			BigDecimal incertezzaSuperiore=GestioneCampioneBO.getIncertezza(limiteSuperiore.getIncertezzaAssoluta(), limiteSuperiore.getIncertezzaRelativa(), limiteSuperiore.getValore_nominale());
		
			if(incertezzaInferiore.compareTo(incertezzaSuperiore)==1)
			{
				campioneCorretto.setIncertezzaCorretta(incertezzaInferiore);
			}
			
			if(incertezzaInferiore.compareTo(incertezzaSuperiore)==-1 || incertezzaInferiore.compareTo(incertezzaSuperiore)==0 )
			{
				campioneCorretto.setIncertezzaCorretta(incertezzaSuperiore);
			}
		
		}
		
		
		}catch(Exception ex)
		{
			ex.printStackTrace();
		}
		return campioneCorretto;
	}

	
	public static CampioneCorrettoDTO getValoreCampioneCorrettoPeriodo(String parTar,BigDecimal valore_inserito,boolean conversione, String umCon, String tipoGrandezza,double fm, String campione) {
		CampioneCorrettoDTO campioneCorretto=null;
		
		try
		{	
		ArrayList<ParametroTaraturaDTO> listaParametri= GestioneCampioneBO.getParametriTaraturaSelezionato(parTar,campione);
		
		
		ParametroTaraturaDTO limiteInferiore= null;
		ParametroTaraturaDTO limiteSuperiore=null;
		ConversioneDTO fattoreConversione=null;
		
		if(conversione)
		{
			ParametroTaraturaDTO parametro =listaParametri.get(0);
			double potenza=1;

			fattoreConversione=getFattoreConversione(parametro.getUm_fond(),umCon.substring(0,umCon.indexOf("@")).trim(),tipoGrandezza);
	
			if(!parametro.getUm().equals(parametro.getUm_fond()))
			{
			int index=parametro.getUm().length()-parametro.getUm_fond().length();	
			String str=parametro.getUm().substring(0,index);
			potenza=getPotenza(str);
			
			}
			
			double grado=0;
			
			if(fattoreConversione.getPotenza()==10 && potenza==1)
			{
				grado=1;
			}else
			{
				grado=Math.pow(fattoreConversione.getPotenza(),potenza);	
			}
			
			for (int i=0;i<listaParametri.size()-1;i++)
			{
				BigDecimal param1= listaParametri.get(i).getValoreTaratura().multiply(fattoreConversione.getFattoreConversione()).multiply(new BigDecimal(grado));
				BigDecimal param2= listaParametri.get(i+1).getValoreTaratura().multiply(fattoreConversione.getFattoreConversione()).multiply(new BigDecimal(grado));

				param1=conversionePeriodo(param1);
				param2=conversionePeriodo(param2);
				
				param1=(param1.divide(new BigDecimal(fm),RoundingMode.HALF_UP));
				param2=(param2.divide(new BigDecimal(fm),RoundingMode.HALF_UP));
				
				
				
				
				if((valore_inserito.compareTo(param1)==1 ||valore_inserito.compareTo(param1)==0 )&& (valore_inserito.compareTo(param2)==-1 || valore_inserito.compareTo(param2)==0 ))
				{	
					BigDecimal cento = new BigDecimal("100");
					cento.setScale(10);
					/*Calcolo % incertezza*/
					
					BigDecimal percentualeIncertezzaInferiore= (listaParametri.get(i).getValore_nominale().setScale(Costanti.SCALA,RoundingMode.HALF_UP).multiply(listaParametri.get(i).getIncertezzaAssoluta())).divide(cento,RoundingMode.HALF_UP);
					BigDecimal percentualeIncertezzaSuperiore= (listaParametri.get(i+1).getValore_nominale().setScale(Costanti.SCALA,RoundingMode.HALF_UP).multiply(listaParametri.get(i).getIncertezzaAssoluta())).divide(cento,RoundingMode.HALF_UP);		
					
					listaParametri.get(i).setValore_nominale(conversionePeriodo((listaParametri.get(i).getValore_nominale().setScale(Costanti.SCALA,RoundingMode.HALF_UP).multiply(fattoreConversione.getFattoreConversione()).multiply(new BigDecimal(grado))).divide(new BigDecimal(fm),RoundingMode.HALF_UP)));
					listaParametri.get(i).setValoreTaratura(conversionePeriodo((listaParametri.get(i).getValoreTaratura().setScale(Costanti.SCALA,RoundingMode.HALF_UP).multiply(fattoreConversione.getFattoreConversione()).multiply(new BigDecimal(grado))).divide(new BigDecimal(fm),RoundingMode.HALF_UP)));
					
					if(listaParametri.get(i).getIncertezzaAssoluta()!=null)
					{
						listaParametri.get(i).setIncertezzaAssoluta(valore_inserito.setScale(Costanti.SCALA,RoundingMode.HALF_UP).multiply(percentualeIncertezzaInferiore));
						
					}
					if(listaParametri.get(i).getIncertezzaRelativa()!=null)
					{
						listaParametri.get(i).setIncertezzaRelativa(valore_inserito.setScale(Costanti.SCALA,RoundingMode.HALF_UP).multiply(percentualeIncertezzaInferiore));
					}
					
					limiteInferiore=listaParametri.get(i);
					
					listaParametri.get(i+1).setValore_nominale(conversionePeriodo((listaParametri.get(i+1).getValore_nominale().setScale(Costanti.SCALA,RoundingMode.HALF_UP).multiply(fattoreConversione.getFattoreConversione()).multiply(new BigDecimal(grado))).divide(new BigDecimal(fm),RoundingMode.HALF_UP)));
					
					listaParametri.get(i+1).setValoreTaratura(conversionePeriodo((listaParametri.get(i+1).getValoreTaratura().setScale(Costanti.SCALA,RoundingMode.HALF_UP).multiply(fattoreConversione.getFattoreConversione()).multiply(new BigDecimal(grado))).divide(new BigDecimal(fm),RoundingMode.HALF_UP)));
					
					if(listaParametri.get(i+1).getIncertezzaAssoluta()!=null)
					{
						listaParametri.get(i+1).setIncertezzaAssoluta(valore_inserito.setScale(Costanti.SCALA,RoundingMode.HALF_UP).multiply(percentualeIncertezzaSuperiore));
						
					}
					if(listaParametri.get(i+1).getIncertezzaRelativa()!=null)
					{
						listaParametri.get(i+1).setIncertezzaAssoluta(valore_inserito.setScale(Costanti.SCALA,RoundingMode.HALF_UP).multiply(percentualeIncertezzaSuperiore));
					}
					
					limiteSuperiore=listaParametri.get(i+1);
					campioneCorretto = new CampioneCorrettoDTO();
					
				}
			}
			
			
			
		}
		
		else
		{
			for (int i=0;i<listaParametri.size()-1;i++)
			{
				if((valore_inserito.compareTo(listaParametri.get(i).getValoreTaratura())==1 ||valore_inserito.compareTo(listaParametri.get(i).getValoreTaratura())==0 )&&
						(valore_inserito.compareTo(listaParametri.get(i+1).getValoreTaratura())==-1 || valore_inserito.compareTo(listaParametri.get(i+1).getValoreTaratura())==0 ))
				{
					limiteInferiore=listaParametri.get(i);
					limiteSuperiore=listaParametri.get(i+1);
					campioneCorretto = new CampioneCorrettoDTO();
					break;
				}
			}
		}
		
		if(limiteInferiore!=null && limiteSuperiore!=null)
		{
			
			/*
			 var 1= valoreInserito - valoreTaratura.limiteInferiore
			 
			 var 2= correzioneSup - correzioneInf
			 
			 var numeratore =var1 * var 2 
			 
			 var denominatore = (valoreTaratura.limitesuperiore - valoreTaratura.limiteInferiore) 
			 
			 correzione= numeratore / denominatore
			 
			 valoreCorretto =valoreInserito + correzione + correzioneInf
			 
			  */
			
			BigDecimal correzioneInferiore= limiteInferiore.getValore_nominale().subtract(limiteInferiore.getValoreTaratura());
			
			BigDecimal correzioneSuperiore= limiteSuperiore.getValore_nominale().subtract(limiteSuperiore.getValoreTaratura());
			
			BigDecimal var1 = valore_inserito.subtract(limiteInferiore.getValoreTaratura());
			
			BigDecimal var2 = correzioneSuperiore.subtract(correzioneInferiore);
			
			BigDecimal numeratore = var1.multiply(var2);
			
			BigDecimal denominatore = (limiteSuperiore.getValoreTaratura().subtract(limiteInferiore.getValoreTaratura())).add(correzioneInferiore);

			BigDecimal correzione =(numeratore.divide(denominatore,10, RoundingMode.HALF_EVEN)).add(correzioneInferiore);
			
			campioneCorretto.setValoreCampioneCorretto(valore_inserito.add(correzione));
			
			
			BigDecimal incertezzaInferiore=GestioneCampioneBO.getIncertezza(limiteInferiore.getIncertezzaAssoluta(), limiteInferiore.getIncertezzaRelativa(), limiteInferiore.getValore_nominale());
		
			BigDecimal incertezzaSuperiore=GestioneCampioneBO.getIncertezza(limiteSuperiore.getIncertezzaAssoluta(), limiteSuperiore.getIncertezzaRelativa(), limiteSuperiore.getValore_nominale());
		
			if(incertezzaInferiore.compareTo(incertezzaSuperiore)==1)
			{
				campioneCorretto.setIncertezzaCorretta(incertezzaInferiore);
			}
			
			if(incertezzaInferiore.compareTo(incertezzaSuperiore)==-1 || incertezzaInferiore.compareTo(incertezzaSuperiore)==0 )
			{
				campioneCorretto.setIncertezzaCorretta(incertezzaSuperiore);
			}
		
		}
		
		
		}catch(Exception ex)
		{
			ex.printStackTrace();
		}
		return campioneCorretto;
	}

	private static BigDecimal conversionePeriodo(BigDecimal param) {
		
		if( param.compareTo(BigDecimal.ZERO)>0) 
			{
			 param=BigDecimal.ONE.setScale(10).divide(param,RoundingMode.HALF_UP);
			}
			else
			{
				param=BigDecimal.ZERO;
			}
		return param;
	}


	public static String[] getListaGrandezze() throws Exception {
		
	ArrayList<String> tmp=SQLiteDAO.getListaGrandezze();
		
		String[] stockArr = new String[tmp.size()];
		
		stockArr = tmp.toArray(stockArr);
		
			return stockArr;
	}


		public static Object[] getListaUM(String param) throws Exception {
		
		if(param.equals("Temperatura"))
		{
		String []	listaTemperature= Utility.getListaTemperature();
		
		return listaTemperature;
		}
		
		ArrayList<String> tipoGrandezze = SQLiteDAO.getListaUM(param);
		
		return tipoGrandezze.toArray();
	}


		


	



	

}
