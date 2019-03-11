package it.calverDesktopLAT.bo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;

import it.calverDesktopLAT.dao.SQLiteDAO;
import it.calverDesktopLAT.dto.CampioneDTO;
import it.calverDesktopLAT.dto.ParametroTaraturaDTO;

public class GestioneCampioneBO {

	public static String[] getListaCampioniPerStrumento(String idStrumento) throws Exception {
		
		
		ArrayList<String> tmp=SQLiteDAO.getListaCampioniPerStrumento(idStrumento);
		
		String[] stockArr = new String[tmp.size()];
		
		stockArr = tmp.toArray(stockArr);
		
		return stockArr;
	
	}

	
	public static String[] getListaCampioniCompleta() throws Exception {
		
		
		ArrayList<String> tmp=SQLiteDAO.getListaCampioniCompleta();
		
		String[] stockArr = new String[tmp.size()];
		
		stockArr = tmp.toArray(stockArr);
		
		return stockArr;
	
	}
	
	public static String[] getParametriTaratura(String codiceCampione,boolean interpolato, ArrayList<String> listaTipiGrandezza) throws Exception {

		
		if(interpolato==false)
		{
		ArrayList<String> tmp=SQLiteDAO.getListaParametriTaratura(codiceCampione,listaTipiGrandezza);
		
		String[] stockArr = new String[tmp.size()];
		
		stockArr = tmp.toArray(stockArr);
		
			return stockArr;
		}else
		{
			ArrayList<String> tmp=SQLiteDAO.getListaParametriTaraturaDistinct(codiceCampione,listaTipiGrandezza);
			
			String[] stockArr = new String[tmp.size()];
			
			stockArr = tmp.toArray(stockArr);
			
			return stockArr;
		}
		
	}
	
public static String[] getParametriTaraturaTotali(String codiceCampione) throws Exception {

		
		
		ArrayList<String> tmp=SQLiteDAO.getListaParametriTaraturaTotali(codiceCampione);
		
		String[] stockArr = new String[tmp.size()];
		
		stockArr = tmp.toArray(stockArr);
		
			return stockArr;

	}

	public static ArrayList<ParametroTaraturaDTO> getParametriTaraturaSelezionato(String parametro, String campione) throws Exception {
		
	
		ArrayList<ParametroTaraturaDTO> listaParametri = SQLiteDAO.getListaParametriTaraturaSelezionato(parametro,campione);
		
		return listaParametri;
	}
	

	public static BigDecimal getIncertezza(BigDecimal incertezzaAssoluta,BigDecimal incertezzaRelativa, BigDecimal valoreNominale) {
		
		if(incertezzaAssoluta!=null && !incertezzaAssoluta.equals("null"))
		{
			try 
			{
				return incertezzaAssoluta;
			} catch (Exception e) 
			{
				return new BigDecimal(0);
			} 
			
		}
		else
		{
			if(incertezzaRelativa!=null && !incertezzaRelativa.equals("null"))
			{
				
				try
				{
					/*controllare moltiplicazione*/
				return incertezzaRelativa.multiply(valoreNominale);
				}
				catch(Exception ex)
				{
					return new BigDecimal(0);
				}
			}
			else
			{
				return new BigDecimal(0);
			}
		}	
		
	}

	public static void saveCampione(int idTabella, int idMisura, String campione,String parametro) {
		
		try {
			if(!SQLiteDAO.isPresentCampione(idTabella,idMisura))
			{
				SQLiteDAO.insertCampioneUtilizzato(idTabella,idMisura,campione,parametro);
			}else
			{
				SQLiteDAO.updateCampioneUtilizzato(idTabella,idMisura,campione,parametro);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}


	public static boolean importaCampioni(String path) throws Exception {
		
		boolean result=false;
		
		try 
		{
			
			
			HashMap<String, String> listaIDCampioni=SQLiteDAO.getListaIDCampioni();
			File f = new File(path);
			FileReader is = new FileReader(f);
			BufferedReader br = new BufferedReader(is);
			
			ArrayList<CampioneDTO> listaCampioni = new ArrayList<>();
			
			String sCurrentLine;
			br.readLine();
			
			while ((sCurrentLine = br.readLine()) != null) 
			{
				String[] singoloCampione=sCurrentLine.split(";");
				
				if(!listaIDCampioni.containsKey(normalizza(singoloCampione[0])))
				{
					
					CampioneDTO cmp = new CampioneDTO();
					cmp.setId(normalizza(singoloCampione[0]));
					cmp.setCodice(normalizza(singoloCampione[1]));
					cmp.setMatricola(normalizza(singoloCampione[2]));
					cmp.setModello(normalizza(singoloCampione[3]));
					cmp.setNum_certificato(normalizza(singoloCampione[4]));
					cmp.setData(normalizza(singoloCampione[5]));
					cmp.setData_scadenza(normalizza(singoloCampione[6]));
					cmp.setFreq_mesi(normalizza(singoloCampione[7]));
					cmp.setParam_taratura(normalizza(singoloCampione[8]));
					cmp.setUm(normalizza(singoloCampione[9]));
					cmp.setUm_fond(normalizza(singoloCampione[10]));
					cmp.setValore_taratura(normalizza(singoloCampione[11]));
					cmp.setValore_nominale(normalizza(singoloCampione[12]));
					cmp.setDiv_um(normalizza(singoloCampione[13]));
					cmp.setIncertezzaAssoluta(normalizza(singoloCampione[14]));
					cmp.setIncertezzaRelativa(normalizza(singoloCampione[15]));
					cmp.setId_tipo_grand(normalizza(singoloCampione[16]));
					cmp.setInterpolazione(normalizza(singoloCampione[17]));
					cmp.setTipo_grandezza(normalizza(singoloCampione[18]));
					cmp.setAbilitato(normalizza(singoloCampione[19]));
					listaCampioni.add(cmp);
				}
			}
			
			 result=SQLiteDAO.insertCampioni(listaCampioni);
			
		} 
		catch(Exception e) 
		{
			e.printStackTrace();
			throw e;
		}
		return result;
	}





	private static String normalizza(String value) 
	{
		if(value != null && value.length()>1)
		{
			return value.substring(1,value.length()-1);
		}
		else
		{
			return "";
		}
		
	}


	public static ArrayList<ParametroTaraturaDTO> getParametriTaratura(String codCampione) throws Exception {

		ArrayList<ParametroTaraturaDTO> listaParametri = SQLiteDAO.getListaParametriTaraturaSelezionato(codCampione);
		
		return listaParametri;
	}
}
