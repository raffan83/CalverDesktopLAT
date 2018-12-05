package it.calverDesktopLAT.bo;


import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.SQLException;
import java.util.ArrayList;

import it.calverDesktopLAT.dao.SQLiteDAO;
import it.calverDesktopLAT.dto.DatiEsterniDTO;
import it.calverDesktopLAT.dto.MisuraDTO;
import it.calverDesktopLAT.dto.ProvaMisuraDTO;
import it.calverDesktopLAT.dto.PuntoLivellaBollaDTO;
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

	public static ArrayList<PuntoLivellaBollaDTO> getListaPuntiLivellaBolla(int idMisura, String semisc) throws Exception {
		
		return SQLiteDAO.getListaPuntiLivellaBolla(idMisura,semisc);
	}

	public static BigDecimal getArcosec(String value) {
		
		Double val =new Double(value);
		Double arc=Math.asin(val/1000);
		arc=Math.toDegrees(arc);
		Double toRet=arc*3600;
		return new BigDecimal(toRet).setScale(Costanti.RISOLUZIONE_LIVELLA_BOLLA, RoundingMode.HALF_UP);
	}

	public static BigDecimal getArcosecInv(String value) {
		Double val=new Double(value);
		
		val=val/3600;
		val=Math.toRadians(val);
		val=1000*Math.sin(val);
		return new BigDecimal(val).setScale(Costanti.RISOLUZIONE_LIVELLA_BOLLA+2, RoundingMode.HALF_UP);
	}

	public static void updateRecordPuntoLivellaBolla(PuntoLivellaBollaDTO punto) throws Exception {
			
		SQLiteDAO.updateRecordPuntoLivellaBolla(punto);
	}

}

