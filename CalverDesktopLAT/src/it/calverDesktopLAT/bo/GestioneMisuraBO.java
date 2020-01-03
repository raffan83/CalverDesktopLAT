package it.calverDesktopLAT.bo;


import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.SQLException;
import java.util.ArrayList;

import org.sqlite.SQLite;

import it.calverDesktopLAT.dao.SQLiteDAO;
import it.calverDesktopLAT.dto.DatiEsterniDTO;
import it.calverDesktopLAT.dto.LatMassaAMB;
import it.calverDesktopLAT.dto.LatMassaAMB_DATA;
import it.calverDesktopLAT.dto.LatMassaAMB_SONDE;
import it.calverDesktopLAT.dto.LatMisuraDTO;
import it.calverDesktopLAT.dto.LatPuntoLivellaElettronicaDTO;
import it.calverDesktopLAT.dto.MisuraDTO;
import it.calverDesktopLAT.dto.ParametroTaraturaDTO;
import it.calverDesktopLAT.dto.ProvaMisuraDTO;
import it.calverDesktopLAT.dto.PuntoLivellaBollaDTO;
import it.calverDesktopLAT.dto.RegLinDTO;
import it.calverDesktopLAT.dto.TabellaMisureDTO;
import it.calverDesktopLAT.utl.Costanti;
import it.calverDesktopLAT.utl.Utility;

// Referenced classes of package it.calverDesktop.bo:
//            SessionBO

public class GestioneMisuraBO
{

    public GestioneMisuraBO()
    {
    }

    public static int isPresent(String id)
        throws Exception
    {
        int result = SQLiteDAO.isPresent(id);
        return result;
    }

    public static int insertMisura(String id)
        throws Exception
    {
        return SQLiteDAO.insertMisura(id);
    }

    public static ProvaMisuraDTO getProvaMisura(String id)
        throws Exception
    {
        ProvaMisuraDTO provaMisura = SQLiteDAO.getInfoMisura(id);
        int numIdTab_prova = SQLiteDAO.getNumeroTabellePerProva(provaMisura.getIdMisura());
        ArrayList listaTabelle = SQLiteDAO.getListaTabelle(provaMisura, numIdTab_prova);
        provaMisura.setListaTabelle(listaTabelle);
        return provaMisura;
    }

    public static int insertTabellePerMisura(String tipoProva, int idMisura, Integer punti, Integer ripetizioni, String labelPunti, String calibrazione)
        throws Exception
    {
        int seq_tab = SQLiteDAO.getMaxTablella(idMisura);
        seq_tab++;
        if(tipoProva.equals("L"))
        {
            SQLiteDAO.inserisciMisuraLineare(idMisura, seq_tab, punti, labelPunti, calibrazione);
        } else
        {
            SQLiteDAO.inserisciMisuraRipetibile(idMisura, seq_tab, punti, ripetizioni, labelPunti, calibrazione);
        }
        return seq_tab;
    }
    
    public static int insertTabellePerMisuraRDP(int idMisura, String campioniString, String descrizioneProva,
		BigDecimal valore_rilevato, String esito, ByteArrayOutputStream file_att)throws Exception {
	
    //	 int seq_tab = SQLiteDAO.getMaxTablellaGeneral();
   //      seq_tab++;
         
    	return SQLiteDAO.inserisciMisuraRDP(idMisura, campioniString, descrizioneProva, valore_rilevato, esito,file_att);
	
    //	 return seq_tab;
    }
    
	public static void ModificaTabellePerMisuraRDP(int idRecord, String descrizioneCampione, String descrizioneProva,
			BigDecimal valoreRilevato, String esito, ByteArrayOutputStream file_att) throws Exception {
		
		 SQLiteDAO.updateMisuraRDP(idRecord, descrizioneCampione, descrizioneProva, valoreRilevato, esito,file_att);
	}
    
    public static void updateRecordMisura(MisuraDTO misura)
        throws SQLException
    {
        SQLiteDAO.updateRecordMisura(misura);
    }

    public static Integer getStatoMisura(String id)
        throws Exception
    {
        Integer toRet = SQLiteDAO.getStatoMisura(id);
        return toRet;
    }

    public static BigDecimal calcolaIncertezzaSVTLineare(BigDecimal valoreMisura, BigDecimal risoluzione_misura, BigDecimal valoreCampione, BigDecimal incertezza)
    {
        BigDecimal rad3 = new BigDecimal("1.73205080757");
        BigDecimal two_rad3 = new BigDecimal("3.46410161514");
        double primoFattore = Math.pow(incertezza.divide(new BigDecimal(2)).doubleValue(), 2D);
        double secondoFattore = Math.pow(valoreMisura.subtract(valoreCampione).divide(rad3, 10, RoundingMode.HALF_UP).doubleValue(), 2D);
        double terzoFattore = Math.pow(risoluzione_misura.divide(two_rad3, 10, RoundingMode.HALF_UP).doubleValue(), 2D);
        double valoreReturn = 2D * Math.sqrt(primoFattore + secondoFattore + terzoFattore);
        BigDecimal _incertezza = new BigDecimal(valoreReturn);
        _incertezza = _incertezza.setScale(Utility.getScale(_incertezza), RoundingMode.HALF_UP);
        return _incertezza;
    }

    public static BigDecimal calcolaIncertezzaRDTLineare(BigDecimal valoreMisura, BigDecimal risoluzione_misura, BigDecimal valoreCampione, BigDecimal incertezza)
    {
        BigDecimal two_rad3 = new BigDecimal("3.46410161514");
        double primoFattore = Math.pow(incertezza.divide(new BigDecimal(2)).doubleValue(), 2D);
        double terzoFattore = Math.pow(risoluzione_misura.divide(two_rad3, 10, RoundingMode.HALF_UP).doubleValue(), 2D);
        double valoreReturn = 2D * Math.sqrt(primoFattore + terzoFattore);
        BigDecimal _incertezza = new BigDecimal(valoreReturn);
        _incertezza = _incertezza.setScale(Utility.getScale(_incertezza), RoundingMode.HALF_UP);
        return _incertezza;
    }

    public static BigDecimal getValoreMedioCampione(int id, BigDecimal valoreCampione, ProvaMisuraDTO misura)
        throws Exception
    {
        int id_ripet = getNumeroRipetizioni(misura, id);
        ArrayList valoriMedi = SQLiteDAO.getValoriMediCampione(misura.getIdMisura(), id_ripet, id);
        if(valoriMedi.size() == 0)
        {
            return valoreCampione;
        }
        int denominatore = 0;
        BigDecimal numeratore = BigDecimal.valueOf(0L);
        for(int i = 0; i < valoriMedi.size(); i++)
        {
            numeratore = numeratore.add((BigDecimal)valoriMedi.get(i));
            denominatore++;
        }

        numeratore = numeratore.add(valoreCampione);
        denominatore++;
        return numeratore.divide(BigDecimal.valueOf(denominatore), 10, RoundingMode.HALF_UP);
    }
    
	public static BigDecimal getValoreScostamentoMax(int id, BigDecimal valoreCampione, BigDecimal valoreStrumento,ProvaMisuraDTO misura) throws Exception {
		
		BigDecimal valoreMaxCampione=valoreStrumento.subtract(valoreCampione).abs();
        
		int id_ripet = getNumeroRipetizioni(misura, id);
        
        ArrayList<BigDecimal> valoriMediCampione = SQLiteDAO.getValoriMediCampione(misura.getIdMisura(), id_ripet, id);
        
        ArrayList<BigDecimal> valoriMediStrumento = SQLiteDAO.getValoriMediStumento(misura.getIdMisura(), id_ripet, id);
       
        if(valoriMediCampione.size() == 0)
        {
            return valoreMaxCampione;
        }
        
        for(int i = 0; i < valoriMediCampione.size(); i++)
        {
           BigDecimal scos=valoriMediStrumento.get(i).subtract(valoriMediCampione.get(i)).abs();
           
           if(scos.compareTo(valoreMaxCampione)==1) 
           {
        	   valoreMaxCampione=scos;
           }
        }

        
        return valoreMaxCampione;
	}
    
 

    public static void setValoreMedioCampione(int id, BigDecimal valoreMedioCampione)
        throws Exception
    {
        ProvaMisuraDTO misura = getProvaMisura(SessionBO.idStrumento);
        int id_ripet = getNumeroRipetizioni(misura, id);
        SQLiteDAO.setValoriMediCampione(misura.getIdMisura(), id_ripet, valoreMedioCampione, id);
    }

    public static BigDecimal getValoreMedioStrumento(int id, BigDecimal valoreMisura, ProvaMisuraDTO misura)
        throws Exception
    {
        int id_ripet = getNumeroRipetizioni(misura, id);
        ArrayList valoriMedi = SQLiteDAO.getValoriMediStumento(misura.getIdMisura(), id_ripet, id);
        if(valoriMedi.size() == 0)
        {
            return valoreMisura;
        }
        int denominatore = 0;
        BigDecimal numeratore = BigDecimal.valueOf(0L);
        for(int i = 0; i < valoriMedi.size(); i++)
        {
            numeratore = numeratore.add((BigDecimal)valoriMedi.get(i));
            denominatore++;
        }

        numeratore = numeratore.add(valoreMisura);
        denominatore++;
        return numeratore.divide(BigDecimal.valueOf(denominatore), 10, RoundingMode.HALF_UP);
    }


    public static void setValoreMedioStrumento(int id, BigDecimal valoreMedioStrumento)
        throws Exception
    {
        ProvaMisuraDTO misura = getProvaMisura(SessionBO.idStrumento);
        int id_ripet = getNumeroRipetizioni(misura, id);
        SQLiteDAO.setValoriMediStrumento(misura.getIdMisura(), id_ripet, valoreMedioStrumento, id);
    }

    public static MisuraDTO getMisura(int id)
        throws Exception
    {
        MisuraDTO misura = SQLiteDAO.getMisura(id);
        return misura;
    }

    public static void terminaMisura(String idStrumento, BigDecimal temperatura, BigDecimal umidita, int sr, int firma)
        throws Exception
    {
        SQLiteDAO.terminaMisura(idStrumento, temperatura, umidita, sr, firma);
    }

    public static ArrayList getListaPunti(int id, ProvaMisuraDTO misura)
        throws Exception
    {
        int id_ripet = getNumeroRipetizioni(misura, id);
        return SQLiteDAO.getListaPunti(id, misura.getIdMisura(), id_ripet);
    }

    public static BigDecimal calcolaIncertezzaSVTRipetibilita(ArrayList<MisuraDTO> listaMisure, BigDecimal incertezzaCampione, BigDecimal valoreMedioCampione, BigDecimal valoreMedioStrumento, int tipo, BigDecimal risoluzione_misura, BigDecimal scostamentoMax)
    {
        BigDecimal rad3 = new BigDecimal("1.73205080757");
        BigDecimal two_rad3 = new BigDecimal("3.46410161514");
        
        double primoFattore = Math.pow(incertezzaCampione.divide(new BigDecimal(2)).doubleValue(), 2D);
        
        double secondoFattore = Math.pow(scostamentoMax.divide(rad3, 10, RoundingMode.HALF_UP).doubleValue(), 2D);
        
        double terzoFattore = Math.pow(risoluzione_misura.divide(two_rad3, 10, RoundingMode.HALF_UP).doubleValue(), 2D);
       
        BigDecimal deviazioneStandard = getDeviazioneStandard(listaMisure, valoreMedioCampione, valoreMedioStrumento, tipo);
      
      
        double sommaFattori = primoFattore + secondoFattore +terzoFattore + deviazioneStandard.doubleValue();
       
        double doubleReturn = 2D * Math.sqrt(sommaFattori);
        BigDecimal incertezza = new BigDecimal(doubleReturn);
        return incertezza;
    }

    
    
 
   

	public static BigDecimal calcolaIncertezzaRDTRipetibilita(ArrayList listaMisure, BigDecimal incertezzaCampione, BigDecimal valoreMedioCampione, BigDecimal valoreMedioStrumento, int tipo, BigDecimal risoluzione_misura)
    {
        BigDecimal rad3 = new BigDecimal("1.73205080757");
        BigDecimal two_rad3 = new BigDecimal("3.46410161514");
        double primoFattore = Math.pow(incertezzaCampione.divide(new BigDecimal(2)).doubleValue(), 2D);
        
       
        
        double terzoFattore = Math.pow(risoluzione_misura.divide(two_rad3, 10, RoundingMode.HALF_UP).doubleValue(), 2D);
        BigDecimal deviazioneStandard = getDeviazioneStandard(listaMisure, valoreMedioCampione, valoreMedioStrumento, tipo);
       
        double sommaFattori = primoFattore + terzoFattore + deviazioneStandard.doubleValue();
        
        double doubleReturn = 2D * Math.sqrt(sommaFattori);
        BigDecimal incertezza = new BigDecimal(doubleReturn);
        return incertezza;
    }

    private static BigDecimal getDeviazioneStandard(ArrayList listaMisure, BigDecimal valoreMedioCampione, BigDecimal valoreMedioStrumento, int tipo)
    {
        BigDecimal valoreSommatoria = BigDecimal.ZERO;
        BigDecimal deviazioneStandard = null;
        int index = -1;
        if(tipo == 1)
        {
            for(int i = 0; i < listaMisure.size(); i++)
            {
                if(((MisuraDTO)listaMisure.get(i)).getValoreCampione() != null && !((MisuraDTO)listaMisure.get(i)).getValoreCampione().equals(""))
                {
                    double val = Math.pow(((MisuraDTO)listaMisure.get(i)).getValoreCampione().subtract(valoreMedioCampione).doubleValue(), 2D);
                    valoreSommatoria = valoreSommatoria.add(new BigDecimal(val));
                    index++;
                }
            }

        } else
        {
            for(int i = 0; i < listaMisure.size(); i++)
            {
                if(((MisuraDTO)listaMisure.get(i)).getValoreStrumento() != null && !((MisuraDTO)listaMisure.get(i)).getValoreStrumento().equals(""))
                {
                    double val = Math.pow(((MisuraDTO)listaMisure.get(i)).getValoreStrumento().subtract(valoreMedioStrumento).doubleValue(), 2D);
                    valoreSommatoria = valoreSommatoria.add(new BigDecimal(val));
                    index++;
                }
            }

        }
        if(valoreSommatoria.compareTo(BigDecimal.ZERO) == 0)
        {
            return BigDecimal.ZERO;
        } else
        {
            double rad = valoreSommatoria.divide(new BigDecimal(index), 10, RoundingMode.HALF_UP).doubleValue();
            deviazioneStandard = new BigDecimal(rad);
            return deviazioneStandard;
        }
    }

    public static void updateValoriRipetibilita(ArrayList listaMisure)
    {
        SQLiteDAO.updateValoriRipetibilita(listaMisure);
    }

    public static void deleteRecordMisura(int id)
    {
        SQLiteDAO.deleteRecordMisura(id);
    }

    public static void assegnaASFound(ArrayList listaTabelle)
    {
        for(int i = 0; i < listaTabelle.size(); i++)
        {
            try
            {
                SQLiteDAO.assegnaASFound(((TabellaMisureDTO)listaTabelle.get(i)).getListaMisure());
            }
            catch(SQLException e)
            {
                e.printStackTrace();
            }
        }

    }

    public static void removeTabellaMisura(int idMisura, int id_tabella)
        throws Exception
    {
        SQLiteDAO.removeTabellaMisura(idMisura, id_tabella);
    }

    private static int getNumeroRipetizioni(ProvaMisuraDTO misura, int id)
    {
        int nRipet = 0;
        ArrayList listaTabelle = misura.getListaTabelle();
        for(int i = 0; i < listaTabelle.size(); i++)
        {
            TabellaMisureDTO tabella = (TabellaMisureDTO)listaTabelle.get(i);
            ArrayList listaMisure = tabella.getListaMisure();
            for(int j = 0; j < listaMisure.size(); j++)
            {
                if(((MisuraDTO)listaMisure.get(j)).getId() == id)
                {
                    return ((MisuraDTO)listaMisure.get(j)).getId_ripetizione();
                }
            }

        }

        return nRipet;
    }

    public static void cambiaStatoMisura(String idStrumento, int stato)
        throws Exception
    {
        SQLiteDAO.cambiaStatoMisura(idStrumento, stato);
    }

    public static void updateListaMisure(ArrayList listaMisure, int id, BigDecimal valoreMisura, BigDecimal valoreCampione)
    {
        for(int i = 0; i < listaMisure.size(); i++)
        {
            if(((MisuraDTO)listaMisure.get(i)).getId() == id)
            {
                ((MisuraDTO)listaMisure.get(i)).setValoreStrumento(valoreMisura);
                ((MisuraDTO)listaMisure.get(i)).setValoreCampione(valoreCampione);
            }
        }

    }

	public static ArrayList<DatiEsterniDTO> getDatiEsterni(String filename) throws Exception {
		
		return SQLiteDAO.getDatiEsterni(filename);
	}
    
	public static void importaMisura(String filename, ProvaMisuraDTO misura, String id) throws NumberFormatException, Exception {
		
		
		for(int i=0 ; i<misura.getListaTabelle().size();i++) 
		{
			removeTabellaMisura(misura.getIdMisura(),misura.getListaTabelle().get(i).getId_tabella());
		}
		
		ArrayList<MisuraDTO> listaMisureEsterne =SQLiteDAO.getListaRecordTabellaEsterna(filename,Integer.parseInt(id));
		
		for (MisuraDTO misuraDTO : listaMisureEsterne) 
		{
			SQLiteDAO.inserisciRigaTabellaDuplicata(misuraDTO,misuraDTO.getIdTabella(),misura.getIdMisura(),misuraDTO.getTipoProva());
		}
	}

	public static int isPresentLAT(String id) throws Exception {
		int result = SQLiteDAO.isPresentLAT(id);
        return result;
	}

	public static int insertMisuraLAT(String id, int indexTableLAT) throws Exception {
		
		return SQLiteDAO.insertMisuraLAT(id,indexTableLAT);
	}

	public static void insertPuntiLivellaBolla(int idMisura) throws Exception {
		
		PuntoLivellaBollaDTO punto = null;
		for(int i=0;i<=11;i++) 
		{
			punto= new PuntoLivellaBollaDTO();
		
			punto.setId_misura(idMisura);
			punto.setRif_tacca(i);
			punto.setSemisc("DX");
			SQLiteDAO.insertListaPuntiLivellaBolla(punto);
		}
	
		for(int i=0;i<=11;i++) 
		{
			punto= new PuntoLivellaBollaDTO();
			
			punto.setId_misura(idMisura);
			punto.setRif_tacca(i);
			punto.setSemisc("SX");
			SQLiteDAO.insertListaPuntiLivellaBolla(punto);
		}
		
		
		
	}
	
	public static void insertPuntiLivellaElettronica(int idMisura) throws Exception {
		
		LatPuntoLivellaElettronicaDTO punto = null;
		
		/*Inserimento parte lineare*/
		for(int i=0;i<=20;i++) 
		{
			punto= new LatPuntoLivellaElettronicaDTO();
		
			punto.setId_misura(idMisura);
			punto.setPunto(i+1);
			punto.setTipo_prova("L");
			punto.setIndicazione_iniziale(BigDecimal.ZERO);
			punto.setIndicazione_iniziale_corr(BigDecimal.ZERO);
			punto.setInclinazione_cmp_campione(BigDecimal.ZERO);
			SQLiteDAO.insertListaPuntiLivellaElettronica(punto);
		}
	
		/*
		 *  Inserimento parte ripetibile
		 */
		
		for(int j=1;j<=5;j++) 
		{
			for(int i=0;i<=20;i++) 
			{
				punto= new LatPuntoLivellaElettronicaDTO();
				
				punto.setId_misura(idMisura);
				punto.setPunto(i+1);
				punto.setNumero_prova(j);
				punto.setTipo_prova("R");
				punto.setIndicazione_iniziale(BigDecimal.ZERO);
				punto.setIndicazione_iniziale_corr(BigDecimal.ZERO);
				punto.setInclinazione_cmp_campione(BigDecimal.ZERO);
				SQLiteDAO.insertListaPuntiLivellaElettronica(punto);
			}	
		}
		
		/*Inserimento Incertezze*/
		for(int i=0;i<=20;i++) 
		{
			punto= new LatPuntoLivellaElettronicaDTO();
		
			punto.setId_misura(idMisura);
			punto.setPunto(i+1);
			punto.setTipo_prova("I");
			punto.setIndicazione_iniziale(BigDecimal.ZERO);
			punto.setIndicazione_iniziale_corr(BigDecimal.ZERO);
			punto.setInclinazione_cmp_campione(BigDecimal.ZERO);
			SQLiteDAO.insertListaPuntiLivellaElettronica(punto);
		}
		
	}

	public static ArrayList<PuntoLivellaBollaDTO> getListaPuntiLivellaBolla(int idMisura, String semisc) throws Exception {
		
		return SQLiteDAO.getListaPuntiLivellaBolla(idMisura,semisc);
	}
	
	public static ArrayList<LatPuntoLivellaElettronicaDTO> getListaPuntiLivellaElettronicaLineari(int idMisura) throws Exception {
		
		return SQLiteDAO.getListaPuntiLivellaElettronicaLineari(idMisura);
	}
	
	public static ArrayList<ArrayList<LatPuntoLivellaElettronicaDTO>> getListaPuntiLivellaElettroniaRipetibili(int idMisura) throws Exception {
	
		return SQLiteDAO.getListaPuntiLivellaElettronicaRipetibili(idMisura);
	}

	public static BigDecimal getArcosec(String value) {
		
		Double val =new Double(value);
		Double arc=Math.asin(val/1000);
		arc=Math.toDegrees(arc);
		Double toRet=arc*3600;
		return new BigDecimal(toRet)/*.setScale(Costanti.RISOLUZIONE_LIVELLA_BOLLA, RoundingMode.HALF_UP)*/;
	}

	public static BigDecimal getArcosecInv(String value) {
		Double val=new Double(value);
		
		val=val/3600;
		val=Math.toRadians(val);
		val=1000*Math.sin(val);
		return new BigDecimal(val)/*.setScale(Costanti.RISOLUZIONE_LIVELLA_BOLLA+2, RoundingMode.HALF_UP)*/;
	}

	public static void updateRecordPuntoLivellaBolla(PuntoLivellaBollaDTO punto) throws Exception {
			
		SQLiteDAO.updateRecordPuntoLivellaBolla(punto);
	}

	public static BigDecimal getAverageLivella(ArrayList<PuntoLivellaBollaDTO> listaPuntiDX,ArrayList<PuntoLivellaBollaDTO> listaPuntiSX, int type) {
		
		BigDecimal media=BigDecimal.ZERO;
		int index=0;
		
		if(type==2) 
		{
			for (PuntoLivellaBollaDTO puntoDX : listaPuntiDX) 
			{
				if(puntoDX.getDiv_dex()!=null && puntoDX.getDiv_dex().compareTo(BigDecimal.ZERO)!=0) 
				{
					media=media.add(puntoDX.getDiv_dex().abs());
					index++;
				}
			}
			for (PuntoLivellaBollaDTO puntoSX : listaPuntiSX ) 
			{
				if(puntoSX.getDiv_dex()!=null && puntoSX.getDiv_dex().compareTo(BigDecimal.ZERO)!=0) 
				{
					media=media.add(puntoSX.getDiv_dex().abs());
					index++;
				}
			}
			
			
		}
		
		if(type==1) 
		{
			for (PuntoLivellaBollaDTO puntoSX : listaPuntiSX) 
			{
				if(puntoSX.getDiv_dex()!=null && puntoSX.getDiv_dex().compareTo(BigDecimal.ZERO)!=0) 
				{
					media=media.add(puntoSX.getDiv_dex().abs() );
					index++;
				}
			}
			
			
		}
		
		if(type==0) 
		{
		for (PuntoLivellaBollaDTO puntoDX : listaPuntiDX) 
		{
			if(puntoDX.getDiv_dex()!=null && puntoDX.getDiv_dex().compareTo(BigDecimal.ZERO)!=0) 
			{
				media=media.add(puntoDX.getDiv_dex().abs());
				index++;
			}
		}
		}
		if(media.compareTo(BigDecimal.ZERO)!=0 && index>0)
		{
			return media.divide(new BigDecimal(index), RoundingMode.HALF_UP);
		}
		else 
		{
			return BigDecimal.ZERO;
		}
	}

	public static BigDecimal getDevStdLivella(ArrayList<PuntoLivellaBollaDTO> listaPuntiDX, ArrayList<PuntoLivellaBollaDTO> listaPuntiSX, int type) {
		
		BigDecimal mediaGlobale =GestioneMisuraBO.getAverageLivella(listaPuntiDX, listaPuntiSX,type);
		
		BigDecimal media=BigDecimal.ZERO;
		
		int index=0;
		
		if(type==2) 
		{
			for (PuntoLivellaBollaDTO puntoDX : listaPuntiDX) 
			{
				if(puntoDX.getDiv_dex()!=null && puntoDX.getDiv_dex().compareTo(BigDecimal.ZERO)!=0) 
				{
					 double val = Math.pow(puntoDX.getDiv_dex().abs().subtract(mediaGlobale).doubleValue(), 2D);
	                 media = media.add(new BigDecimal(val));
	                 index++;
	                    
					
				}
			}
			for (PuntoLivellaBollaDTO puntoSX : listaPuntiSX ) 
			{
				if(puntoSX.getDiv_dex()!=null && puntoSX.getDiv_dex().compareTo(BigDecimal.ZERO)!=0) 
				{
					double val = Math.pow(puntoSX.getDiv_dex().abs().subtract(mediaGlobale).doubleValue(), 2D);
	                media = media.add(new BigDecimal(val));
	                index++;
				}
			}
			
			
		}
		
		if(type==1) 
		{
			for (PuntoLivellaBollaDTO puntoSX : listaPuntiSX) 
			{
				if(puntoSX.getDiv_dex()!=null && puntoSX.getDiv_dex().compareTo(BigDecimal.ZERO)!=0) 
				{
					double val = Math.pow(puntoSX.getDiv_dex().abs().subtract(mediaGlobale).doubleValue(), 2D);
	                 media = media.add(new BigDecimal(val));
	                 index++;
				}
			}
			
			
		}
		
		if(type==0) 
		{
		for (PuntoLivellaBollaDTO puntoDX : listaPuntiDX) 
		{
			if(puntoDX.getDiv_dex()!=null && puntoDX.getDiv_dex().compareTo(BigDecimal.ZERO)!=0) 
			{
				double val = Math.pow(puntoDX.getDiv_dex().abs().subtract(mediaGlobale).doubleValue(), 2D);
                media = media.add(new BigDecimal(val));
                index++;
			}
		}
		}
		
		if(media.compareTo(BigDecimal.ZERO)!=0  && index>1)
		{
			
			BigDecimal d=new BigDecimal(1).setScale(10, RoundingMode.HALF_UP).divide(new BigDecimal(index-1).setScale(10, RoundingMode.HALF_UP),RoundingMode.HALF_DOWN);
			
			BigDecimal  b = media.multiply(d);
		
			Double d1=b.doubleValue();
			
			d1=Math.sqrt(d1);
			return  new BigDecimal(d1);
		}
		else 
		{
			return BigDecimal.ZERO;
		}
		
	}

	
	public static BigDecimal getDevStd(ArrayList<BigDecimal> lista, BigDecimal mediaGlobale,int scala) {

		BigDecimal media=BigDecimal.ZERO;

		int index=0;
		
		if(mediaGlobale==null) 
		{
			int indexMediaGlolbale=0;
			
			BigDecimal avg=new BigDecimal(0);
			
			for (BigDecimal number : lista) 
			{
				avg=avg.add(number);
				indexMediaGlolbale++;
			}
			
			mediaGlobale=avg.divide(new BigDecimal(indexMediaGlolbale),RoundingMode.HALF_UP);
		}
		
	
		mediaGlobale.setScale(scala,RoundingMode.HALF_UP);
		
			for (BigDecimal number : lista) 
			{
				if(number!=null && number.compareTo(BigDecimal.ZERO)!=0) 
				{
					 double val = Math.pow(number.abs().subtract(mediaGlobale).doubleValue(), 2D);
	                 media = media.add(new BigDecimal(val));
	                 index++;
	                    
					
				}
			}
		
		if(media.compareTo(BigDecimal.ZERO)!=0  && index>1)
		{
			
			BigDecimal d=new BigDecimal(1).setScale(10, RoundingMode.HALF_UP).divide(new BigDecimal(index-1).setScale(10, RoundingMode.HALF_UP),RoundingMode.HALF_DOWN);
			
			BigDecimal  b = media.multiply(d);
		
			Double d1=b.doubleValue();
			
			d1=Math.sqrt(d1);
			
			return  new BigDecimal(d1).setScale(scala,RoundingMode.HALF_UP);
		}
		else 
		{
			return BigDecimal.ZERO.setScale(scala,RoundingMode.HALF_UP);
		}
		
	}
	
	public static BigDecimal getScMaxLivella(ArrayList<PuntoLivellaBollaDTO> listaPuntiDX,ArrayList<PuntoLivellaBollaDTO> listaPuntiSX) {
		
		BigDecimal mediaGlobale =GestioneMisuraBO.getAverageLivella(listaPuntiDX, listaPuntiSX,2);
		
		BigDecimal max=BigDecimal.ZERO;
		
		
			for (PuntoLivellaBollaDTO puntoDX : listaPuntiDX) 
			{
				if(puntoDX.getDiv_dex()!=null && puntoDX.getDiv_dex().compareTo(BigDecimal.ZERO)!=0) 
				{
					BigDecimal tmp=puntoDX.getDiv_dex().abs().subtract(mediaGlobale).abs();
	               
					if(tmp.compareTo(max)>=1) 
					{
						max=tmp;
					}
	                
	                    
					
				}
			}
			for (PuntoLivellaBollaDTO puntoSX : listaPuntiSX ) 
			{
				if(puntoSX.getDiv_dex()!=null && puntoSX.getDiv_dex().compareTo(BigDecimal.ZERO)!=0) 
				{
					BigDecimal tmp=puntoSX.getDiv_dex().abs().subtract(mediaGlobale).abs();
		               
					if(tmp.compareTo(max)>=1) 
					{
						max=tmp;
					}
				}
			}
			
		
		
		return max;
	}

	public static BigDecimal getIncertezzaLivellaBolla_EM(BigDecimal er, String _sensib) {
		
		BigDecimal em = null;
		
		
		BigDecimal sensib=new BigDecimal(_sensib);
			
		
		er=er.divide(new BigDecimal(2).setScale(10, RoundingMode.HALF_DOWN));
		
		er=er.multiply(er);
		
		sensib=new BigDecimal(Math.pow(sensib.doubleValue(), 2));
		
		BigDecimal cost=new BigDecimal(0.039204);
		
		
		sensib=sensib.multiply(cost);
		
		er=er.add(sensib);
		
		em=new BigDecimal(2).multiply(new BigDecimal(Math.pow(er.doubleValue(), 0.5)));
		
		return em;
	}

	public static BigDecimal getIncertezzaLivellaBolla_UM(String _em, String _scMax) {
		
		BigDecimal um = null;
		
		BigDecimal em=new BigDecimal(_em);
		
		BigDecimal scMax=new BigDecimal(_scMax);
		
		scMax= scMax.multiply(scMax);
		
		em=em.setScale(10,RoundingMode.HALF_DOWN).divide(new BigDecimal(2).setScale(10, RoundingMode.HALF_DOWN),RoundingMode.HALF_DOWN);
		
		em=em.multiply(em);
		
		um=new BigDecimal(2).multiply(new BigDecimal(Math.pow(scMax.add(em).doubleValue(), 0.5)));
		
		return um;
	}

	public static void eliminaRigaLivellaABolla(int indexPoint) throws Exception {
	
		PuntoLivellaBollaDTO punto = new PuntoLivellaBollaDTO();
		punto.setId(indexPoint);
		SQLiteDAO.updateRecordPuntoLivellaBolla(punto);
		
	}

	public static void updateRecordMisuraLAT(LatMisuraDTO lat) throws Exception {
		
		SQLiteDAO.updateRecordPuntoLivellaBolla(lat);
		
	}

	public static LatMisuraDTO getMisuraLAT(int idMisura) throws Exception {
	
		return SQLiteDAO.getMisuraLAT(idMisura);
	}

	public static BigDecimal getErroreCumulativo(BigDecimal mediaTratto, String campioneUtilizzato) {
			
			BigDecimal errore_cumulativo=null;
			
			try
			{	
			ArrayList<ParametroTaraturaDTO> listaParametri= GestioneCampioneBO.getParametriTaraturaSelezionato(campioneUtilizzato,campioneUtilizzato);
			
			
			
			ParametroTaraturaDTO limiteInferiore= null;
			ParametroTaraturaDTO limiteSuperiore=null;
		
				for (int i=0;i<listaParametri.size()-1;i++)
				{
					BigDecimal param1= listaParametri.get(i).getValoreTaratura();
					BigDecimal param2= listaParametri.get(i+1).getValoreTaratura();
							
					if((mediaTratto.compareTo(param1)==1 ||mediaTratto.compareTo(param1)==0 )&& (mediaTratto.compareTo(param2)==-1 || mediaTratto.compareTo(param2)==0 ))
					{
						
						listaParametri.get(i).setValore_nominale((listaParametri.get(i).getValore_nominale().setScale(Costanti.SCALA,RoundingMode.HALF_UP)));
						listaParametri.get(i).setValoreTaratura((listaParametri.get(i).getValoreTaratura().setScale(Costanti.SCALA,RoundingMode.HALF_UP)));
						
						if(listaParametri.get(i).getIncertezzaAssoluta()!=null)
						{
							listaParametri.get(i).setIncertezzaAssoluta((listaParametri.get(i).getIncertezzaAssoluta().setScale(Costanti.SCALA,RoundingMode.HALF_UP)));
							
						}
						if(listaParametri.get(i).getIncertezzaRelativa()!=null)
						{
							listaParametri.get(i).setIncertezzaRelativa((listaParametri.get(i).getIncertezzaRelativa().setScale(Costanti.SCALA,RoundingMode.HALF_UP)));
						}
						
						limiteInferiore=listaParametri.get(i);
						
						
						listaParametri.get(i+1).setValore_nominale((listaParametri.get(i+1).getValore_nominale().setScale(Costanti.SCALA,RoundingMode.HALF_UP)));
						
						listaParametri.get(i+1).setValoreTaratura((listaParametri.get(i+1).getValoreTaratura().setScale(Costanti.SCALA,RoundingMode.HALF_UP)));
						
						if(listaParametri.get(i+1).getIncertezzaAssoluta()!=null)
						{
							listaParametri.get(i+1).setIncertezzaAssoluta((listaParametri.get(i+1).getIncertezzaAssoluta().setScale(Costanti.SCALA,RoundingMode.HALF_UP)));
							
						}
						if(listaParametri.get(i+1).getIncertezzaRelativa()!=null)
						{
							listaParametri.get(i+1).setIncertezzaRelativa((listaParametri.get(i+1).getIncertezzaRelativa().setScale(Costanti.SCALA,RoundingMode.HALF_UP)));
						}
						
						limiteSuperiore=listaParametri.get(i+1);
						break;
					}
				}
				
			if(limiteInferiore!=null && limiteSuperiore!=null)
			{
				
				BigDecimal correzioneSup=limiteSuperiore.getValoreTaratura().subtract(limiteSuperiore.getValore_nominale()); //0.2
				
				BigDecimal correzioneInf=limiteInferiore.getValoreTaratura().subtract(limiteInferiore.getValore_nominale()); //1.3
				
				
				BigDecimal a1=mediaTratto.subtract(limiteInferiore.getValore_nominale());
				
				
				BigDecimal a2=limiteSuperiore.getValore_nominale().subtract(limiteInferiore.getValore_nominale());
				
				BigDecimal pt=a1.divide(a2,RoundingMode.HALF_UP);
				
				BigDecimal st=correzioneSup.subtract(correzioneInf);
				
				errore_cumulativo=pt.multiply(st).add(correzioneInf);
			}
			
			
			}catch(Exception ex)
			{
				ex.printStackTrace();
			}
			return errore_cumulativo;
		
	}

	public static void updateRecordPuntoLivellaElettronica(LatPuntoLivellaElettronicaDTO punto) throws Exception {
		
		SQLiteDAO.updateRecordPuntoLivellaElettronica(punto);
	}

	public static ArrayList<RegLinDTO> getListaRegressioneLineare(ArrayList<ParametroTaraturaDTO> listaParametri) {
	
		ArrayList<RegLinDTO> listaRegLin = new ArrayList<RegLinDTO>();
		
		RegLinDTO regression=null;
		
		for (int i = 0; i < listaParametri.size()-1;i++) 
		{
			regression= new RegLinDTO();
			BigDecimal x = listaParametri.get(i).getValoreTaratura().setScale(Costanti.RISOLUZIONE_LIVELLA_ELETTRONICA, RoundingMode.HALF_UP);
			BigDecimal x1 = listaParametri.get(i+1).getValoreTaratura().setScale(Costanti.RISOLUZIONE_LIVELLA_ELETTRONICA, RoundingMode.HALF_UP);
			
			BigDecimal y=listaParametri.get(i).getValoreTaratura().subtract(listaParametri.get(i).getValore_nominale()).setScale(Costanti.RISOLUZIONE_LIVELLA_ELETTRONICA, RoundingMode.HALF_UP);
			BigDecimal y1=listaParametri.get(i+1).getValoreTaratura().subtract(listaParametri.get(i+1).getValore_nominale()).setScale(Costanti.RISOLUZIONE_LIVELLA_ELETTRONICA, RoundingMode.HALF_UP);
			
			BigDecimal medX =(x.add(x1)).divide(new BigDecimal(2),RoundingMode.HALF_UP).setScale(Costanti.RISOLUZIONE_LIVELLA_ELETTRONICA+15, RoundingMode.HALF_UP);
			BigDecimal medY =(y.add(y1)).divide(new BigDecimal(2),RoundingMode.HALF_UP).setScale(Costanti.RISOLUZIONE_LIVELLA_ELETTRONICA+15, RoundingMode.HALF_UP);
			
			BigDecimal firstSum=(x.subtract(medX)).multiply(y.subtract(medY));
			BigDecimal secondSum=(x1.subtract(medX)).multiply(y1.subtract(medY));
			
			BigDecimal numerator=firstSum.add(secondSum);
			
			BigDecimal d1=(x.subtract(medX)).multiply((x.subtract(medX)));
			BigDecimal d2=(x1.subtract(medX)).multiply((x1.subtract(medX)));
			
			BigDecimal m = numerator.divide(d1.add(d2),RoundingMode.HALF_UP);
			
			BigDecimal q=medY.subtract(m.multiply(medX));
			
			regression.setValore_misurato(x);
			regression.setValore_riferimento(listaParametri.get(i).getValore_nominale());
			regression.setScostamento(y);
			regression.setM(m.setScale(Costanti.RISOLUZIONE_LIVELLA_ELETTRONICA+14,RoundingMode.HALF_UP));
			regression.setQ(q.setScale(Costanti.RISOLUZIONE_LIVELLA_ELETTRONICA+13,RoundingMode.HALF_UP));
			
			System.out.println(regression.toString());
			
			listaRegLin.add(regression);
		}
		
		RegLinDTO lastReg = new RegLinDTO();
		int lastIndex=listaParametri.size()-1;
		
		BigDecimal x = listaParametri.get(lastIndex).getValoreTaratura().setScale(Costanti.RISOLUZIONE_LIVELLA_ELETTRONICA, RoundingMode.HALF_UP);
		BigDecimal y=listaParametri.get(lastIndex).getValoreTaratura().subtract(listaParametri.get(lastIndex).getValore_nominale()).setScale(Costanti.RISOLUZIONE_LIVELLA_ELETTRONICA, RoundingMode.HALF_UP);
		
		lastReg.setValore_misurato(x);
		lastReg.setValore_riferimento(listaParametri.get(lastIndex).getValore_nominale());
		lastReg.setScostamento(y);
		lastReg.setM(BigDecimal.ZERO);
		lastReg.setQ(BigDecimal.ZERO);
		System.out.println(lastReg.toString());
		listaRegLin.add(lastReg);
		return listaRegLin;
	}

	public static ArrayList<LatPuntoLivellaElettronicaDTO> getListaPuntiLivellaElettronicaIncertezze(int idMisura) throws Exception {
		// TODO Auto-generated method stub
		return SQLiteDAO.getListaPuntiLivellaElettronicaIncertezze(idMisura);
	}

	public static void insertCondizioniAmbientali(ArrayList<LatMassaAMB> listaValoriAmbientali, int idMisura) throws Exception {
		
		SQLiteDAO.insertCondizioniAmbientali(listaValoriAmbientali,idMisura);
		
	}

	public static void removeCondizioniAmbientali(int idMisura) throws Exception {
		
		SQLiteDAO.removeCondizioniAmbientali(idMisura);
		
		SQLiteDAO.removeCondizioniAmbientaliDati(idMisura);
		
	}

	public static ArrayList<LatMassaAMB> getListaCondizioniAmbientali(int idMisura) throws Exception {
		
		return SQLiteDAO.getListaCondizioniAmbientali(idMisura);
			
	}

	public static ArrayList<LatMassaAMB_SONDE> getListaCorrezioniSondeLAT(int id_tipo) throws Exception {
		
		return SQLiteDAO.getListaCorrezioniSondeLAT(id_tipo);

	}

	public static void insertCondizioniAmbientaliDati(LatMassaAMB_DATA datiCalcolati) throws Exception {
		
		SQLiteDAO.insertCondizioniAmbientaliDati(datiCalcolati);
		
	}

	public static LatMassaAMB_DATA getCondizioniAmbientaliDati(int idMisura) throws Exception {
		
		return SQLiteDAO.getCondizioniAmbientaliDati(idMisura);
		
	}

	



}

