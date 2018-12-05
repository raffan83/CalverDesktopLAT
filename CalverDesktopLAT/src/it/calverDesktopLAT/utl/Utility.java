package it.calverDesktopLAT.utl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Vector;

import it.calverDesktopLAT.dto.ClassificazioneDTO;
import it.calverDesktopLAT.dto.LuogoVerificaDTO;
import it.calverDesktopLAT.dto.MisuraDTO;
import it.calverDesktopLAT.dto.TabellaMisureDTO;
import it.calverDesktopLAT.dto.TipoRapportoDTO;
import it.calverDesktopLAT.dto.TipoStrumentoDTO;

public class Utility {

	public static String[] convertString(ArrayList<String> tables) {
		
		if(tables!=null && tables.size()>0)
		{
			String[] toRet = new String[tables.size()];
			
			for (int i = 0; i < toRet.length; i++) {
				toRet[i]=tables.get(i).toString();
			}
			return toRet;
		}
		
		return null;
	}

	public static Integer getNumber(String string) {
		
		Integer toRet=null;
		
		if(string!=null)
		{
			try 
			{
				toRet=Integer.parseInt(string);
			} 
			catch (NumberFormatException e) 
			{
				return null;
			}
		}
		
		return toRet;
	}

	public static boolean isNumber(String nPunti) {
		boolean flag=true;
		
		try 
		{
		 Integer.parseInt(nPunti);		
		} 
		catch (NumberFormatException e) 
		{
			return false;
		}
		
		
		return flag;
	}

	public static String toString(Object valueAt) {
	
		if(valueAt!=null)
		{
			return valueAt.toString();
		}
		return "";
	}

	public static String[] getListaTemperature(String um_fond) {
		
		String[] lista = new String[3];
		
		if(um_fond.equals("°C"))
		{
			lista[0]="Seleziona Parametro....";
			lista[1]="K @ Kelvin";
			lista[2]="°F @ Grado Fahrenehit";
		}
		if(um_fond.equals("°F"))
		{
			lista[0]="Seleziona Parametro....";
			lista[1]="K @ Kelvin";
			lista[2]="°C @ Grado Celsius";
		}
		if(um_fond.equals("K"))
		{
			lista[0]="Seleziona Parametro....";
			lista[1]="°F @ Grado Fahrenehit";
			lista[2]="°C @ Grado Celsius";
		}
		
		return lista;	
	}

	public static String getLabelUMConvertita(String umCombo, String fmCombo) {
	
		String labelReturn="";
		
		String fm=fmCombo.substring(fmCombo.indexOf("(")+1,fmCombo.indexOf(")"));
		if(fm.length()==2)
		{
			fm="";
		}
		labelReturn=fm+umCombo.split("@")[0];
		
		return labelReturn;
	}

	public static BigDecimal getRisoluzione(String _nCifre) {
		
		int nCifre=Integer.parseInt(_nCifre);
		
		if(nCifre>0)
		{
			
		
		String dec=""; 
		
		
		for (int i = 1; i < nCifre; i++) {
			dec=dec+"0";
		}
		
		return new BigDecimal("0."+dec+"1");
		
		}
		else
		{
			return BigDecimal.ONE;
		}
	}

	public static boolean contollaPunti(ArrayList<TabellaMisureDTO> listaTabelle) {
	boolean toRet=true;
	
		if(listaTabelle.size()==0) {return false;}
	
		for (TabellaMisureDTO tabella : listaTabelle) {
			
			ArrayList<MisuraDTO> listaMisura = tabella.getListaMisure();
			
			for (MisuraDTO misura : listaMisura) {
				if(misura.getValoreStrumento()==null)
				{
					return false;
				}
			}
			
		}
		return toRet;
	}

	public static Object BigDecimalStp(BigDecimal value) {
		
		if(value!=null)
		{
			return value.toPlainString();
		}
		return null;
	}

	

	public static int getScale(BigDecimal incertezza) {
		
		if(incertezza.intValue() > 0)
		{
			return 2;
		}
		else
		{
			int scale = incertezza.scale();
			int precision = incertezza.precision();
			
			return Math.abs(scale-precision)+2;
		}	

	}
	public static String[] getListaTemperature() {
		
	String[] lista = new String[4];

			
			lista[0]="Seleziona Parametro....";
			lista[1]="°F @ Grado Fahrenehit";
			lista[2]="°C @ Grado Celsius";
			lista[3]="K @ Kelvin";
		
		
		return lista;	
		
		
	}

	public static int getIndicaTipoRapporto(Vector<TipoRapportoDTO> vectorTipoRapporto, String tipoRapporto) {
		
		int indice=0;
		
		for(int i=0; i<vectorTipoRapporto.size();i++)
		{
			if(vectorTipoRapporto.get(i).getDescrizione().equals(tipoRapporto))
			{
				return i;
			}
		}
		
		return indice;
	}

	public static int getIndicaClassificazione(Vector<ClassificazioneDTO> vectorClassificazione,String classificazione) {
		int indice=0;
		
		for(int i=0; i<vectorClassificazione.size();i++)
		{
			if(vectorClassificazione.get(i).getDescrizione().equals(classificazione))
			{
				return i;
			}
		}
		
		return indice;
	}

	public static int getIndicaTipoStrumento(Vector<TipoStrumentoDTO> vectorTipoStrumento,String id_tipoStrumento) {
		int indice=0;
		
		for(int i=0; i<vectorTipoStrumento.size();i++)
		{
			if(vectorTipoStrumento.get(i).getId()==Integer.parseInt(id_tipoStrumento))
			{
				return i;
			}
		}
		
		return indice;
	}


	public static int getIndicaLuogoVerifica(Vector<LuogoVerificaDTO> vectorLuogoVerifica, int idLuogoVerifica) {
	int indice=0;
		
		for(int i=0; i<vectorLuogoVerifica.size();i++)
		{
			if(vectorLuogoVerifica.get(i).getId()==idLuogoVerifica)
			{
				return i;
			}
		}
		
		return indice;
	}
}
