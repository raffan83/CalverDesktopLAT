package it.calverDesktopLAT.bo;

import java.util.ArrayList;
import java.util.HashMap;

import it.calverDesktopLAT.dao.SQLiteDAO;
import it.calverDesktopLAT.dto.MisuraDTO;
import it.calverDesktopLAT.dto.ProvaMisuraDTO;
import it.calverDesktopLAT.dto.StrumentoDTO;
import it.calverDesktopLAT.dto.TabellaMisureDTO;

public class GestioneStrumentoBO {

	public static ArrayList<StrumentoDTO> getListaStrumenti(int filter) throws Exception {
	
		return SQLiteDAO.getListaStrumenti(filter);
	}

	public static StrumentoDTO getStrumento(String id) throws Exception {
		
		return SQLiteDAO.getStrumento(id);
	}

	public static int insertStrumento(StrumentoDTO strumento) throws Exception {
		
		String nomeSede=SQLiteDAO.getNomeSede();
		
		return SQLiteDAO.insertStrumento(strumento,nomeSede);
		
	}

	public static int updateStrumento(StrumentoDTO strumento) throws Exception {
	
		return SQLiteDAO.updateStrumento(strumento);
		
	}

	public static ArrayList<String> getListaTipoGrandezza(String idStrumento) throws Exception {
		
		return SQLiteDAO.getListaTipoGrandezzeByStrumento(idStrumento);
	}

	public static int duplicaStrumento(String idStrumento) throws Exception {
		
		StrumentoDTO str=SQLiteDAO.getStrumentoDB(idStrumento);
		
		int max =SQLiteDAO.getMaxIDStrumento();
		
		String nomeSede=SQLiteDAO.getNomeSede();
		
		str.set__id(max+1);
		str.setMatricola("DUP");
		str.setCodice_interno("DUP");
		
		SQLiteDAO.insertStrumento(str,nomeSede);
		
		ProvaMisuraDTO provaMisura=null;
		
		try 
		{
			provaMisura =  GestioneMisuraBO.getProvaMisura(idStrumento);	
		} catch (Exception e) {
			// TODO: handle exception
		} 
		
		
		if(provaMisura!=null) 
		{
			duplicaMisura(str.get__id(),provaMisura);
		}
		
		return max+1;
	}

	private static void duplicaMisura(int idStr, ProvaMisuraDTO provaMisura) throws Exception {
		
		int idMisura=GestioneMisuraBO.insertMisura(""+idStr);
		
		for (TabellaMisureDTO tabella : provaMisura.getListaTabelle()) 
		{
			int seq_tab = SQLiteDAO.getMaxTablella(idMisura);
			seq_tab++;
			
			for (MisuraDTO misura : tabella.getListaMisure()) 
			{
				SQLiteDAO.inserisciRigaTabellaDuplicata(misura,seq_tab,idMisura,tabella.getTipoProva());
			}
			
		}
		
	}

	public static int isIdoneoForLAT(String id) throws Exception {
		
		int indexMasterLAT=0;
		
		StrumentoDTO strumento = getStrumento(id);
		
		
		indexMasterLAT=SQLiteDAO.getIndiceMasterLAT(strumento.getId_tipoStrumento());
		
		return indexMasterLAT;
	}

}
