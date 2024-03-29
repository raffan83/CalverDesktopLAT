package it.calverDesktopLAT.dao;

import static org.junit.Assert.assertNotNull;

import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Vector;

import com.sun.org.apache.xpath.internal.WhitespaceStrippingElementMatcher;

import it.calverDesktopLAT.dto.CampioneDTO;
import it.calverDesktopLAT.dto.ClassificazioneDTO;
import it.calverDesktopLAT.dto.ConversioneDTO;
import it.calverDesktopLAT.dto.DatiEsterniDTO;
import it.calverDesktopLAT.dto.LatMassaAMB;
import it.calverDesktopLAT.dto.LatMassaAMB_DATA;
import it.calverDesktopLAT.dto.LatMassaAMB_SONDE;
import it.calverDesktopLAT.dto.LatMassaClasseDTO;
import it.calverDesktopLAT.dto.LatMassaEffMag;
import it.calverDesktopLAT.dto.LatMassaScartiTipo;
import it.calverDesktopLAT.dto.LatMisuraDTO;
import it.calverDesktopLAT.dto.LatPuntoLivellaElettronicaDTO;
import it.calverDesktopLAT.dto.LuogoVerificaDTO;
import it.calverDesktopLAT.dto.MisuraDTO;
import it.calverDesktopLAT.dto.ParametroTaraturaDTO;
import it.calverDesktopLAT.dto.ProvaMisuraDTO;
import it.calverDesktopLAT.dto.PuntoLivellaBollaDTO;
import it.calverDesktopLAT.dto.StrumentoDTO;
import it.calverDesktopLAT.dto.TabellaMisureDTO;
import it.calverDesktopLAT.dto.TipoRapportoDTO;
import it.calverDesktopLAT.dto.TipoStrumentoDTO;
import it.calverDesktopLAT.gui.PannelloConsole;
import it.calverDesktopLAT.utl.Costanti;
import it.calverDesktopLAT.utl.Utility;

public class SQLiteDAO {
	
	private static final String insertCMP = "INSERT INTO tblCampioni VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
	
	
	static DatabaseMetaData metadata = null;

	private static  Connection getConnection() throws ClassNotFoundException, SQLException
	{
		Class.forName("org.sqlite.JDBC");
		
		Connection con=DriverManager.getConnection("jdbc:sqlite:"+Costanti.PATH_DB);
		
		return con;
	}
	private static  Connection getConnectionExternal(String filename) throws ClassNotFoundException, SQLException
	{
		Class.forName("org.sqlite.JDBC");
		
		Connection con=DriverManager.getConnection("jdbc:sqlite:"+filename);
		
		return con;
	}


	
	public static String[] listaTabelleDB() throws SQLException, ClassNotFoundException {
		 
		String table[] = { "TABLE" };
		 
		         ResultSet rs = null;
		
		         ArrayList<String> tables = null;
		
		         DatabaseMetaData metadata =getConnection().getMetaData();
		
		         rs = metadata.getTables(null, null, null, table);
		 
		         tables = new ArrayList<String>();
		 
		         while (rs.next()) {
		
		             tables.add(rs.getString("TABLE_NAME"));
		
		         }
		
		         return Utility.convertString(tables);

	}



	public static String[] getListaColonne(String tabella) throws SQLException, ClassNotFoundException {				
			
		ArrayList<String> listaColonne= new ArrayList<String>();
		
		ResultSet rs = getConnection().getMetaData().getColumns(null, null, tabella, null);
			
			while (rs.next()) {
				listaColonne.add(rs.getString("COLUMN_NAME"));
			}

		return Utility.convertString(listaColonne);
	}



	public static Object[][] getPlayLoad(int length,String tableName) throws Exception {
		
		Connection con=null;
		PreparedStatement pst=null;
		ResultSet rs =null;
		Object[][]data=null;
		try
		{
			con=getConnection();
			
			pst=con.prepareStatement("SELECT * FROM "+tableName);
			
			rs=pst.executeQuery();
			
			int numberRow=getNumberRow(tableName);
		
			data=new Object[numberRow][length];
			int ind=0;
			while(rs.next())
			{
							
				for (int i = 0; i < length; i++) {
					data[ind][i] =rs.getString(i+1);
				}
				ind++;
			}
			
		}catch (Exception e) {
			throw e;
		}finally
		{
			pst.close();
			con.close();
		}
		return data;
	}



	private static int getNumberRow(String nomeTaballa) throws SQLException, ClassNotFoundException {
		int i=0;
		
		Connection con=getConnection();
		
		PreparedStatement pst=con.prepareStatement("SELECT * FROM "+nomeTaballa);
		ResultSet rs=pst.executeQuery();
		
		while(rs.next())
		{
			i++;
		}
		return i;
	}



	public static ArrayList<StrumentoDTO> getListaStrumenti(int filter) throws Exception {

		
		Connection con=null;
		PreparedStatement pst=null;
		ArrayList<StrumentoDTO> listaStrumenti = new ArrayList<>();
	try{
		con=getConnection();
		
		String sql="";
		if(filter==0)
		{
			sql="SELECT a.* FROM tblStrumenti a";
		}
		if(filter==1)
		{
			sql="select a.* FROM tblStrumenti a join tblMisure b on a.id=b.id_str where b.statoMisura=0";
		}
		if(filter==2)
		{
			sql="select a.* FROM tblStrumenti a join tblMisure b on a.id=b.id_str where b.statoMisura=1";
		}
		if(filter==3)
		{
			sql="select a.* FROM tblStrumenti a left join tblMisure b on a.id=b.id_str where b.id_str is null";
		}
		
		if(filter==4)
		{
			sql="select a.* FROM tblStrumenti a join tblMisure b on a.id=b.id_str where b.statoMisura=2";
		}
		
		pst=con.prepareStatement(sql);
		ResultSet rs=pst.executeQuery();
		
		StrumentoDTO strumento =null;
		while(rs.next())
		{
			strumento= new StrumentoDTO();
			strumento.set__id(rs.getInt("id"));//
			strumento.setIndirizzo(rs.getString("indirizzo"));
			strumento.setDenominazione(rs.getString("denominazione"));//
			strumento.setCodice_interno(rs.getString("codice_interno"));//
			strumento.setCostruttore(rs.getString("costruttore"));
			strumento.setModello(rs.getString("modello"));
			strumento.setClassificazione(rs.getString("classificazione"));
			strumento.setMatricola(rs.getString("matricola"));
			strumento.setRisoluzione(rs.getString("risoluzione"));
			strumento.setCampo_misura(rs.getString("campo_misura"));
			strumento.setFreq_taratura(rs.getInt("freq_verifica_mesi"));
			strumento.setTipoRapporto(rs.getString("tipoRapporto"));
			strumento.setStatoStrumento(rs.getString("StatoStrumento"));
			strumento.setReparto(rs.getString("reparto"));
			strumento.setUtilizzatore(rs.getString("utilizzatore"));
			strumento.setProcedura(rs.getString("procedura"));
			strumento.setId_tipoStrumento(rs.getString("id_tipo_strumento"));
			strumento.setNote(rs.getString("note"));
			strumento.setDataUltimaVerifica(rs.getString("dataUltimaVerifica"));
			strumento.setDataProssimaVerifica(rs.getString("dataProssimaVerifica"));
			strumento.setnCertificato(rs.getString("nCertificato"));
			strumento.setLuogoVerifica(rs.getInt("luogo_verifica"));
			
			listaStrumenti.add(strumento);
		}
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			throw ex;
		}
		finally
		{
			pst.close();
			con.close();
		}
			return listaStrumenti;
		}
	



	public static StrumentoDTO getStrumento(String id) throws Exception {
	Connection con =null;
	PreparedStatement pst= null;
	
	StrumentoDTO strumento =null;	
	try{
		con=getConnection();
		pst=con.prepareStatement("SELECT a.*, b.descrizione as descTR, c.descrizione as descClass FROM tblStrumenti a " +
								 "left join tbl_tipoRapporto b on  b.id =a.tipoRapporto  " +
								 "left join tbl_classificazione c on  c.id =a.classificazione " +
								 "WHERE a.id=?");
		pst.setInt(1,Integer.parseInt(id));
		ResultSet rs=pst.executeQuery();
		
		
		
		
		while(rs.next())
		{
			strumento= new StrumentoDTO();
			strumento.set__id(rs.getInt("id"));//
			strumento.setIndirizzo(rs.getString("indirizzo"));
			strumento.setDenominazione(rs.getString("denominazione"));//
			strumento.setCodice_interno(rs.getString("codice_interno"));//
			strumento.setCostruttore(rs.getString("costruttore"));
			strumento.setModello(rs.getString("modello"));
			strumento.setClassificazione(rs.getString("descClass"));
			strumento.setMatricola(rs.getString("matricola"));
			strumento.setRisoluzione(rs.getString("risoluzione"));
			strumento.setCampo_misura(rs.getString("campo_misura"));
			strumento.setFreq_taratura(rs.getInt("freq_verifica_mesi"));
			strumento.setTipoRapporto(rs.getString("descTR"));
			strumento.setStatoStrumento(rs.getString("StatoStrumento"));
			strumento.setReparto(rs.getString("reparto"));
			strumento.setUtilizzatore(rs.getString("utilizzatore"));
			strumento.setProcedura(rs.getString("procedura"));
			strumento.setId_tipoStrumento(rs.getString("id_tipo_strumento"));
			strumento.setNote(rs.getString("note"));
			strumento.setDataUltimaVerifica(rs.getString("dataUltimaVerifica"));
			strumento.setDataProssimaVerifica(rs.getString("dataProssimaVerifica"));
			strumento.setnCertificato(rs.getString("nCertificato"));
			strumento.setCreato(rs.getString("creato"));
			strumento.setLuogoVerifica(rs.getInt("luogo_verifica"));
			
		}
	}catch(Exception ex)
	{
		throw ex;
	}
	finally
	{
		pst.close();
		con.close();
	}
		return strumento;
	}

	public static StrumentoDTO getStrumentoDB(String id) throws Exception {
	Connection con =null;
	PreparedStatement pst= null;
	
	StrumentoDTO strumento =null;	
	try{
		con=getConnection();
		pst=con.prepareStatement("SELECT a.* FROM tblStrumenti a WHERE a.id=?");
		pst.setInt(1,Integer.parseInt(id));
		ResultSet rs=pst.executeQuery();
		
		
		
		
		while(rs.next())
		{
			strumento= new StrumentoDTO();
			strumento.set__id(rs.getInt("id"));//
			strumento.setIndirizzo(rs.getString("indirizzo"));
			strumento.setDenominazione(rs.getString("denominazione"));//
			strumento.setCodice_interno(rs.getString("codice_interno"));//
			strumento.setCostruttore(rs.getString("costruttore"));
			strumento.setModello(rs.getString("modello"));
			strumento.setClassificazione(rs.getString("classificazione"));
			strumento.setMatricola(rs.getString("matricola"));
			strumento.setRisoluzione(rs.getString("risoluzione"));
			strumento.setCampo_misura(rs.getString("campo_misura"));
			strumento.setFreq_taratura(rs.getInt("freq_verifica_mesi"));
			strumento.setTipoRapporto(rs.getString("tipoRapporto"));
			strumento.setStatoStrumento(rs.getString("StatoStrumento"));
			strumento.setReparto(rs.getString("reparto"));
			strumento.setUtilizzatore(rs.getString("utilizzatore"));
			strumento.setProcedura(rs.getString("procedura"));
			strumento.setId_tipoStrumento(rs.getString("id_tipo_strumento"));
			strumento.setNote(rs.getString("note"));
			strumento.setDataUltimaVerifica(rs.getString("dataUltimaVerifica"));
			strumento.setDataProssimaVerifica(rs.getString("dataProssimaVerifica"));
			strumento.setnCertificato(rs.getString("nCertificato"));
			strumento.setCreato(rs.getString("creato"));
			strumento.setLuogoVerifica(rs.getInt("luogo_verifica"));
			
			
		}
	}catch(Exception ex)
	{
		throw ex;
	}
	finally
	{
		pst.close();
		con.close();
	}
		return strumento;
	}


	public static int isPresent(String id) throws Exception {
		Connection con =null;
		PreparedStatement pst= null;
		
		try{
			con=getConnection();
			pst=con.prepareStatement("SELECT * FROM tblMisure WHERE id_str=?");
			pst.setInt(1,Integer.parseInt(id));
			ResultSet rs=pst.executeQuery();
			while(rs.next())
			{
			 return rs.getInt(1);
			}
			return 0;
		}catch(Exception ex)
		{
			throw ex;
		}
		finally
		{
			pst.close();
			con.close();
		}
			
	}



	public static int insertMisura(String id) throws Exception {
		
		Connection con =null;
		PreparedStatement pst= null;

		try{
			con=getConnection();
			pst=con.prepareStatement("INSERT INTO tblMisure(id_str,dataMisura,statoMisura) VALUES(?,?,?)",pst.RETURN_GENERATED_KEYS);
			
			pst.setInt(1,Integer.parseInt(id));
			SimpleDateFormat sdf =new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			Date d = new Date();
			pst.setString(2,sdf.format(d));
			pst.setString(3,"0");
			pst.execute();
		
		    ResultSet generatedKeys = pst.getGeneratedKeys(); 
		    	
		            if (generatedKeys.next()) {
		               return (int) generatedKeys.getLong(1);
		            }
		            else {
		                throw new SQLException("Error insert Misura, no ID obtained.");
		            }
		        
			  
		}catch(Exception ex)
		{
			ex.printStackTrace();
			throw ex;
		}
		finally
		{
			pst.close();
			con.close();
		}
		
	}



	public static int getMaxTablella(int idMisura) throws Exception 
	{
		Connection con=null;
		PreparedStatement pst=null;
		ResultSet rs=null;
		int toRet=0;
		
		try 
		{
			con=getConnection();
			pst=con.prepareStatement("SELECT MAX(id_tabella) FROM tblTabelleMisura WHERE id_misura=?");
			pst.setInt(1,idMisura);
			
			rs=pst.executeQuery();
			
			while(rs.next())
			{
				toRet=rs.getInt(1);
			}
			
		}
		catch (Exception e) 
		{
		 e.printStackTrace();	
		 throw e;
		}
		finally
		{
			pst.close();
			con.close();
		}

		return toRet;
	}

	public static int getMaxTablellaGeneral() throws Exception {
		Connection con=null;
		PreparedStatement pst=null;
		ResultSet rs=null;
		int toRet=0;
		
		try 
		{
			con=getConnection();
			pst=con.prepareStatement("SELECT MAX(id) FROM tblTabelleMisura");
			
			rs=pst.executeQuery();
			
			while(rs.next())
			{
				toRet=rs.getInt(1);
			}
			
		}
		catch (Exception e) 
		{
		 e.printStackTrace();	
		 throw e;
		}
		finally
		{
			pst.close();
			con.close();
		}

		return toRet;
	}
	


	public static void inserisciMisuraLineare(int idMisura, int seq_tab,Integer punti, String labelPunti, String calibrazione) throws Exception {
		
		Connection con=null;
		PreparedStatement pst=null;
		try 
		{
			con=getConnection();
			pst=con.prepareStatement("INSERT INTO tblTabelleMisura(id_misura,id_tabella,tipoProva,id_ripetizione,ordine,tipoVerifica,label,calibrazione,applicabile) VALUES(?,?,?,?,?,?,?,?,?)");
			String calLabel="";
			if(calibrazione!=null && calibrazione.length()>0){calLabel="("+calibrazione+")";}
			
			
			pst.setInt(1, idMisura);
			pst.setInt(2,seq_tab);
			pst.setString(3, "L"+"_"+punti);
			
			for (int i = 1; i <= punti; i++) {
			
				pst.setInt(4, 0);
			pst.setInt(5, i);
			pst.setString(6,labelPunti+" "+i+calLabel);
			pst.setString(7,labelPunti);
			pst.setString(8, calibrazione);
			pst.setString(9, "S");
			pst.execute();
			}
		}
		catch (Exception e) 
		{
		 e.printStackTrace();	
		 throw e;
		}
		finally
		{
			pst.close();
			con.close();
		}
	}

public static int inserisciMisuraRDP(int idMisura, String campioniString, String descrizione,
		BigDecimal valore_rilevato, String esito, ByteArrayOutputStream file_att) throws Exception {
		
		Connection con=null;
		PreparedStatement pst=null;
		int id=0;
		try 
		{
			con=getConnection();
			pst=con.prepareStatement("INSERT INTO tblTabelleMisura(id_misura,id_tabella,tipoProva,id_ripetizione,ordine,tipoVerifica,label,valoreStrumento,esito,desc_campione,applicabile,file_att) VALUES(?,?,?,?,?,?,?,?,?,?,?,?)");
			int ordine=getOrdine(idMisura);
			ordine++;
			
			
			pst.setInt(1, idMisura);
			pst.setInt(2,1);
			pst.setString(3, "RDP");
			pst.setInt(4, 0);
			pst.setInt(5,ordine );
			pst.setString(6,descrizione);
			pst.setString(7,descrizione);
			pst.setBigDecimal(8, valore_rilevato);
			pst.setString(9, esito);
			pst.setString(10,campioniString);
			pst.setString(11, "S");
			if(file_att!=null)
			{
				pst.setBytes(12, file_att.toByteArray());
			}
			else
			{
				pst.setBytes(12, null);
			}
			pst.execute();
			ResultSet rs =pst.getGeneratedKeys();
			rs.next();
			id=(int)rs.getLong(1);
			
		}
		catch (Exception e) 
		{
		 e.printStackTrace();	
		 throw e;
		}
		finally
		{
			pst.close();
			con.close();
		}
		return id;
	}

public static void updateMisuraRDP(int idRecord, String descrizioneCampione, String descrizioneProva,BigDecimal valoreRilevato, String esito, ByteArrayOutputStream file_att) throws Exception {
	
	Connection con=null;
	PreparedStatement pst=null;
	try 
	{
		con=getConnection();
		pst=con.prepareStatement("UPDATE tblTabelleMisura SET tipoVerifica=?,label=?,valoreStrumento=?,esito=?,desc_campione=?,file_att=? WHERE id=?");
		
	
		pst.setString(1,descrizioneProva);
		pst.setString(2,descrizioneProva);
		pst.setBigDecimal(3, valoreRilevato);
		pst.setString(4, esito);
		pst.setString(5,descrizioneCampione);
		if(file_att!=null) 
		{
			pst.setBytes(6, file_att.toByteArray());
		}
		else 
		{
			pst.setBytes(6, null);
		}
		pst.setInt(7, idRecord);
		pst.execute();
		
	}
	catch (Exception e) 
	{
	 e.printStackTrace();	
	 throw e;
	}
	finally
	{
		pst.close();
		con.close();
	}
}

	private static int getOrdine(int idMisura) throws Exception 
	{
		Connection con=null;
		PreparedStatement pst=null;
		ResultSet rs=null;
		int toRet=0;
		
		try 
		{
			con=getConnection();
			pst=con.prepareStatement("SELECT MAX(ordine) FROM tblTabelleMisura WHERE id_misura=?");
			pst.setInt(1,idMisura);
			
			rs=pst.executeQuery();
			
			while(rs.next())
			{
				toRet=rs.getInt(1);
			}
			
		}
		catch (Exception e) 
		{
		 e.printStackTrace();	
		 throw e;
		}
		finally
		{
			pst.close();
			con.close();
		}

		return toRet;
	}
	public static void inserisciMisuraRipetibile(int idMisura, int seq_tab,Integer punti, Integer ripetizioni, String labelPunti, String calibrazione) throws Exception {
		
		Connection con=null;
		PreparedStatement pst=null;
		try 
		{
			con=getConnection();
			pst=con.prepareStatement("INSERT INTO tblTabelleMisura(id_misura,id_tabella,tipoProva,id_ripetizione,ordine,tipoVerifica,label,calibrazione,applicabile) VALUES(?,?,?,?,?,?,?,?,?)");
			
			String calLabel="";
			if(calibrazione!=null && calibrazione.length()>0){calLabel="("+calibrazione+")";}
			
			pst.setInt(1, idMisura);
			pst.setInt(2,seq_tab);
			pst.setString(3, "R"+"_"+punti+"_"+ripetizioni);
			
			
			int ordine=1;
			
			for (int i = 1; i <= ripetizioni; i++) {
			
				for (int j = 0; j < punti; j++) {
					
					pst.setInt(4,i);
					pst.setInt(5, ordine);
					pst.setString(6,"["+i+" - rp] "+labelPunti+" "+(j+1)+calLabel);
					pst.setString(7,labelPunti);
					pst.setString(8, calibrazione);
					pst.setString(9, "S");
					pst.execute();
					ordine++;
				}
			
			}
		}
		catch (Exception e) 
		{
		 e.printStackTrace();	
		 throw e;
		}
		finally
		{
			pst.close();
			con.close();
		}
		
	}



	public static ProvaMisuraDTO getInfoMisura(String id_str) throws Exception {
		Connection con=null;
		PreparedStatement pst=null;
		ResultSet rs=null;
		
		ProvaMisuraDTO prova=null;
		
		try 
		{
			con=getConnection();
			pst=con.prepareStatement("SELECT * FROM tblMisure WHERE id_str=?");
			pst.setString(1, id_str);
		
			rs=pst.executeQuery();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			while(rs.next())
			{
				prova=new ProvaMisuraDTO();
				 String dataMisura = rs.getString("dataMisura");
	                if(dataMisura != null)
	                {
	                    prova.setDataMisura(sdf.parse(dataMisura));
	                } else
	                {
	                    prova.setDataMisura(new Date());
	                }
	                
					prova.setTemperatura(rs.getBigDecimal("temperatura"));
					prova.setUmidita(rs.getBigDecimal("umidita"));
	                
				prova.setIdMisura(rs.getInt("id"));
			}
			
			
		}
		catch (Exception e) 
		{
		 e.printStackTrace();	
		 throw e;
		}
		finally
		{
			pst.close();
			con.close();
		}
		return prova;
	}



	public static int getNumeroTabellePerProva(int id) throws Exception {
		Connection con=null;
		PreparedStatement pst=null;
		ResultSet rs=null;
		
		int numTab=0;
		
		try 
		{
			con=getConnection();
			pst=con.prepareStatement("SELECT max(id_tabella) FROM tblTabelleMisura WHERE id_misura=?");
			pst.setInt(1, id);
		
			rs=pst.executeQuery();
			
			while(rs.next())
			{
				numTab=rs.getInt(1);
			}
			
			
		}
		catch (Exception e) 
		{
		 e.printStackTrace();	
		 throw e;
		}
		finally
		{
			pst.close();
			con.close();
		}
		return numTab;
	}



	public static ArrayList<TabellaMisureDTO> getListaTabelle(ProvaMisuraDTO provaMisura, int numIdTab_prova) throws Exception {
		Connection con=null;
		
		PreparedStatement pst=null;
		ResultSet rs=null;
		
		ArrayList<TabellaMisureDTO>  listaTabelle= new ArrayList<>();
		TabellaMisureDTO tabella = null;;
		MisuraDTO misura=null;
		try 
		{
		for (int i = 1; i <= numIdTab_prova; i++) {
			
	
			con=getConnection();
			pst=con.prepareStatement("SELECT * FROM tblTabelleMisura WHERE id_misura=? AND id_tabella=? ORDER BY ordine");
			pst.setInt(1, provaMisura.getIdMisura());
			pst.setInt(2,i);
			
			rs=pst.executeQuery();
			
			
			
			boolean flag=true;
			while(rs.next())
			{
				if(flag)
				{
					tabella=new TabellaMisureDTO();
					tabella.setTipoProva(rs.getString("tipoProva"));
					tabella.setId_tabella(i);
					flag=false;
				}
				
				misura= new MisuraDTO();
				
				misura.setId(rs.getInt("id"));
				misura.setId_ripetizione(rs.getInt("id_ripetizione"));
				misura.setTipoVerifica(rs.getString("tipoVerifica"));
				misura.setOrdine(rs.getInt("ordine"));
				misura.setLabel(rs.getString("label"));
				misura.setUm(rs.getString("um"));
				misura.setValoreCampione(rs.getBigDecimal("valoreCampione"));
				misura.setValoreMedioCampione(rs.getBigDecimal("valoreMedioCampione"));
				misura.setValoreStrumento(rs.getBigDecimal("valoreStrumento"));
				misura.setValoreMedioStrumento(rs.getBigDecimal("valoreMedioStrumento"));
				misura.setScostamento(rs.getBigDecimal("scostamento"));
				misura.setAccettabilita(rs.getBigDecimal("accettabilita"));
				misura.setIncertezza(rs.getBigDecimal("incertezza"));
				misura.setEsito(rs.getString("esito"));
				misura.setDescrizioneCampione(rs.getString("desc_campione"));
				misura.setDescrizioneParametro(rs.getString("desc_parametro"));
				misura.setCalibrazione(rs.getString("calibrazione"));
				misura.setApplicabile(rs.getString("applicabile"));
				misura.setMisuraPrecedente(rs.getString("val_misura_prec"));
				misura.setMisuraCampionePrecedente(rs.getString("val_campione_prec"));
				tabella.getListaMisure().add(misura);
			}
			if(tabella!=null && flag==false)
			{
				listaTabelle.add(tabella);
			}
			
			}		
		}
		catch (Exception e) 
		{
		 e.printStackTrace();	
		 throw e;
		}
		finally
		{
			
			if(numIdTab_prova>0)
			{
			pst.close();
			con.close();
			}
		}
		return listaTabelle;
	}



	public static void updateRecordMisura(MisuraDTO misura) throws SQLException 
	{
	    Connection con=null;
		PreparedStatement pst=null;
		
		try
		{
			con=getConnection();
			pst=con.prepareStatement("UPDATE tblTabelleMisura SET " +
									  "tipoVerifica=?," +
									  "um=?," +
									  "valoreCampione=?," +
									  "valoreStrumento=?," +
									  "scostamento=?," +
									  "accettabilita=?," +
									  "incertezza=?," +
									  "esito=? ," +
									  "desc_campione =? ," +
									  "desc_parametro = ? ," +
									  "misura = ? ," +
									  "um_calc = ?," +
									  "risoluzione_misura = ?," +
									  "risoluzione_campione = ?," +
									  "fondo_scala = ? ," +
									  "interpolazione = ? ," +
									  "fm=?," +
									  "selConversione = ? ," +
									  "letturaCampione = ? ," +
									  "selTolleranza=? ," +
									  "perc_util=? ," +
									  "applicabile=? ,dgt=? " +
									  "WHERE id= ?");
			pst.setString(1, misura.getTipoVerifica());
			pst.setString(2, misura.getUm());
			pst.setBigDecimal(3, misura.getValoreCampione());
			pst.setBigDecimal(4, misura.getValoreStrumento());
			pst.setBigDecimal(5, misura.getScostamento());
			pst.setBigDecimal(6, misura.getAccettabilita());
			pst.setBigDecimal(7, misura.getIncertezza());
			
			pst.setString(8, misura.getEsito());
			pst.setString(9,misura.getDescrizioneCampione());
			pst.setString(10,misura.getDescrizioneParametro());
			pst.setBigDecimal(11, misura.getMisura());
			pst.setString(12, misura.getUm_calc());
			pst.setBigDecimal(13, misura.getRisoluzione_misura());
			pst.setBigDecimal(14, misura.getRisoluzione_campione());
			pst.setBigDecimal(15, misura.getFondoScala());
			pst.setInt(16, misura.getInterpolazione());
			pst.setString(17, misura.getFm());
			pst.setInt(18, misura.getSelConversione());
			pst.setBigDecimal(19, misura.getLetturaCampione());
			pst.setInt(20, misura.getSelTolleranza());
			pst.setBigDecimal(21, misura.getPercentuale());
			pst.setString(22, misura.getApplicabile());
			pst.setBigDecimal(23, misura.getDgt());
			pst.setInt(24, misura.getId());
			
			
			
			int i = pst.executeUpdate();
			
		}
		catch (Exception e) 
		{
			PannelloConsole.printException(e);
			e.printStackTrace();
		}
		finally
		{
			pst.close();
			con.close();
		}
		
		
	}



	public static Integer getStatoMisura(String id) throws Exception {
		
		Connection con=null;
		PreparedStatement pst=null;
		ResultSet rs =null;
		Integer toRet=null;
		try
		{
			con=getConnection();
			
			pst=con.prepareStatement("SELECT * FROM tblMisure WHERE id_str=?");
			pst.setString(1, id);
			
			rs=pst.executeQuery();
			
			while(rs.next())
			{
							
				toRet=rs.getInt("statoMisura");
			}
			
		}catch (Exception e) {
			throw e;
		}finally
		{
			pst.close();
			con.close();
		}
		return toRet;
	}



	public static ArrayList<String> getListaCampioniPerStrumento(String idStrumento) throws Exception {

		Connection con=null;
		PreparedStatement pst=null;
		ResultSet rs =null;
		ArrayList<String> toRet=new  ArrayList<String>();
		toRet.add("Seleziona Campione....");
	
		try
		{
			con=getConnection();
			
			pst=con.prepareStatement("select distinct(codice) from tblCampioni " +
					"left join tbl_ts_tg on tbl_ts_tg.id_tipo_grandezza=tblCampioni.id_tipo_grandezza " +
					"left join tblStrumenti on tbl_ts_tg.id_tipo_strumento=tblStrumenti.id_tipo_strumento " +
					"where tblStrumenti.id=?  ORDER BY codice ASC");
			
			pst.setString(1, idStrumento);
			
			rs=pst.executeQuery();
			
			while(rs.next())
			{
				toRet.add(rs.getString(1));			
				
			}
			
		}catch (Exception e) {
			throw e;
		}finally
		{
			pst.close();
			con.close();
		}
		return toRet;
	}

	public static ArrayList<String> getListaCampioniCompleta() throws Exception {

		Connection con=null;
		PreparedStatement pst=null;
		ResultSet rs =null;
		ArrayList<String> toRet=new  ArrayList<String>();
		toRet.add("Seleziona Campione....");
	
		try
		{
			con=getConnection();
			
			pst=con.prepareStatement("select distinct(codice) from tblCampioni where codice like '%CDT%' ORDER BY codice ASC");
			

			
			rs=pst.executeQuery();
			
			while(rs.next())
			{
				toRet.add(rs.getString(1));			
				
			}
			
		}catch (Exception e) {
			throw e;
		}finally
		{
			pst.close();
			con.close();
		}
		return toRet;
	}
	
	public static ArrayList<String> getListaCampioniCompletaNoInterpolabili() throws Exception {

		Connection con=null;
		PreparedStatement pst=null;
		ResultSet rs =null;
		ArrayList<String> toRet=new  ArrayList<String>();
		toRet.add("Seleziona Campione....");
	
		try
		{
			con=getConnection();
			
			pst=con.prepareStatement("select distinct(codice) from tblCampioni WHERE interpolazione_permessa='0'");
			

			
			rs=pst.executeQuery();
			
			while(rs.next())
			{
				toRet.add(rs.getString(1));			
				
			}
			
		}catch (Exception e) {
			throw e;
		}finally
		{
			pst.close();
			con.close();
		}
		return toRet;
	}

	public static ArrayList<String> getListaParametriTaratura(String codiceCampione, ArrayList<String> listaTipiGrandezza) throws Exception {
		Connection con=null;
		PreparedStatement pst=null;
		ResultSet rs =null;
		ArrayList<String> toRet=new  ArrayList<String>();
		toRet.add("Seleziona Parametro....");
		try
		{
			con=getConnection();
			
			String tipoGrandezza=preparaTipiGrandezza(listaTipiGrandezza);
			
			pst=con.prepareStatement("select parametri_taratura FROM tblCampioni WHERE codice=? AND ("+tipoGrandezza);
					
			pst.setString(1, codiceCampione);
			
			rs=pst.executeQuery();
			
			while(rs.next())
			{
				toRet.add(rs.getString(1));			
				
			}
			
		}catch (Exception e) {
			throw e;
		}finally
		{
			pst.close();
			con.close();
		}
		return toRet;
	}



	private static String preparaTipiGrandezza(ArrayList<String> listaTipiGrandezza) {
		String tipoGrandezza="";
		
		for (int i = 0; i < listaTipiGrandezza.size(); i++) {
			
			tipoGrandezza=tipoGrandezza+" OR id_tipo_grandezza ="+listaTipiGrandezza.get(i);
		}
		
		return tipoGrandezza.substring(4,tipoGrandezza.length())+")";
	}


	public static boolean isInterpolabile(String codiceCampione) throws Exception {
		Connection con=null;
		PreparedStatement pst=null;
		ResultSet rs =null;
		boolean toRet=false;
		try
		{
			con=getConnection();
			
			pst=con.prepareStatement("select interpolazione_permessa FROM  tblCampioni WHERE codice=?");
			
			pst.setString(1, codiceCampione);
			
			rs=pst.executeQuery();		
			rs.next();
			
				if(rs.getInt(1)==1)
				{
					toRet=true;
				}
				
		
		
		}catch (Exception e) {
			throw e;
		}finally
		{
			pst.close();
			con.close();
		}
		return toRet;
		
		
	}



	public static ArrayList<String> getListaParametriTaraturaDistinct(String codiceCampione, ArrayList<String> listaTipiGrandezza) throws Exception {
			Connection con=null;
			PreparedStatement pst=null;
			ResultSet rs =null;
			ArrayList<String> toRet=new  ArrayList<String>();
			toRet.add("Seleziona Parametro....");
			try
			{
				con=getConnection();
				
				String tipoGrandezza=preparaTipiGrandezza(listaTipiGrandezza);
				
				pst=con.prepareStatement("select DISTINCT(parametri_taratura) FROM tblCampioni WHERE codice=? AND ("+tipoGrandezza);
						
				pst.setString(1, codiceCampione);
				
				rs=pst.executeQuery();
				
				while(rs.next())
				{
					toRet.add(rs.getString(1));			
					
				}
				
			}catch (Exception e) {
				throw e;
			}finally
			{
				pst.close();
				con.close();
			}
			return toRet;
		
	}



	public static ArrayList<ParametroTaraturaDTO> getListaParametriTaraturaSelezionato(String parametro, String campione) throws Exception {
		Connection con=null;
		PreparedStatement pst=null;
		ResultSet rs =null;
		ArrayList<ParametroTaraturaDTO> toRet=new  ArrayList<ParametroTaraturaDTO>();
		ParametroTaraturaDTO parametroTar=null;
		try
		{
			con=getConnection();
			
			pst=con.prepareStatement("select * FROM tblCampioni WHERE parametri_taratura=? AND codice=? ORDER BY valore_taratura");
					
			pst.setString(1, parametro);
			pst.setString(2, campione);
			
			rs=pst.executeQuery();
			
			while(rs.next())
			{
				parametroTar= new ParametroTaraturaDTO();
				parametroTar.setDescrizioneParametro(rs.getString("parametri_taratura"));
				parametroTar.setDataScadenza(new SimpleDateFormat("yyyy-MM-dd").parse(rs.getString("data_scadenza")));
				parametroTar.setUm(rs.getString("um"));
				parametroTar.setUm_fond(rs.getString("um_fond"));
				parametroTar.setIncertezzaAssoluta(rs.getBigDecimal("incertezza_assoluta"));
				parametroTar.setIncertezzaRelativa(rs.getBigDecimal("incertezza_relativa"));
				parametroTar.setValore_nominale(rs.getBigDecimal("valore_nominale"));
				parametroTar.setValoreTaratura(rs.getBigDecimal("valore_taratura"));
				parametroTar.setInterpolazione(rs.getInt("interpolazione_permessa"));
				parametroTar.setRisoluzione(rs.getBigDecimal("divisione_unita_misura"));
				parametroTar.setTipoGrandezza(rs.getString("tipoGrandezza"));
				toRet.add(parametroTar);			
				
			}
			
		}catch (Exception e) {
			throw e;
		}finally
		{
			pst.close();
			con.close();
		}
		return toRet;
	}

	public static ArrayList<String> getListaParametriTaraturaTotali(String campione) throws Exception {
		Connection con=null;
		PreparedStatement pst=null;
		ResultSet rs =null;
		ArrayList<String> toRet=new  ArrayList<String>();
		toRet.add("Seleziona Parametro....");
		try
		{
			con=getConnection();
			
			
			pst=con.prepareStatement("select DISTINCT(parametri_taratura) FROM tblCampioni WHERE codice=?");
					
			pst.setString(1, campione);
			
			rs=pst.executeQuery();
			
			while(rs.next())
			{
				toRet.add(rs.getString(1));			
				
			}
			
		}catch (Exception e) {
			throw e;
		}finally
		{
			pst.close();
			con.close();
		}
		return toRet;
	}


	public static ArrayList<String> getListaUMConvertibili(String um_fond, String tipoGrandezza) throws Exception {
		Connection con=null;
		PreparedStatement pst=null;
		ResultSet rs =null;
		ArrayList<String> toRet=new  ArrayList<String>();
		toRet.add("Seleziona Parametro....");
		try
		{
			con=getConnection();
			
			pst=con.prepareStatement("select um_a,um FROM tbl_conversione WHERE um_da=? AND tipo_misura=?");
					
			pst.setString(1, um_fond);
			pst.setString(2, tipoGrandezza);
			
			rs=pst.executeQuery();
			
			while(rs.next())
			{
				toRet.add(rs.getString(1)+" @ "+rs.getString(2));			
				
			}
			
		}catch (Exception e) {
			throw e;
		}finally
		{
			pst.close();
			con.close();
		}
		return toRet;
	
	}



	public static ArrayList<String> getListaFattoriMoltiplicativi() throws Exception {
		Connection con=null;
		PreparedStatement pst=null;
		ResultSet rs =null;
		ArrayList<String> toRet=new  ArrayList<String>();
		toRet.add("Seleziona Parametro....");
		try
		{
			con=getConnection();
			
			pst=con.prepareStatement("select * FROM tbl_fattori_moltiplicativi ");
					
		
			rs=pst.executeQuery();
			
			while(rs.next())
			{
				toRet.add(rs.getString(1)+" ("+rs.getString(2)+") | "+rs.getBigDecimal("fm").toEngineeringString());			
				
			}
			
		}catch (Exception e) {
			throw e;
		}finally
		{
			pst.close();
			con.close();
		}
		return toRet;
	}



	public static double getPotenza(String str) throws Exception {
		Connection con=null;
		PreparedStatement pst=null;
		ResultSet rs =null;
		double potenza=0;
		try
		{
			con=getConnection();
			
			pst=con.prepareStatement("select potenza FROM tbl_fattori_moltiplicativi WHERE sigla=? ");
			pst.setString(1, str);		
		
			rs=pst.executeQuery();
			
			while(rs.next())
			{
				potenza= rs.getDouble(1);
				
			}
			
		}catch (Exception e) {
			throw e;
		}finally
		{
			pst.close();
			con.close();
		}
		return potenza;
	}

	public static double getPotenzaPerClasse(String cls) throws Exception {
		Connection con=null;
		PreparedStatement pst=null;
		ResultSet rs =null;
		double potenza=0;
		try
		{
			con=getConnection();
			
			pst=con.prepareStatement("select potenza FROM tbl_fattori_moltiplicativi WHERE descrizione=? ");
			pst.setString(1, cls);		
		
			rs=pst.executeQuery();
			
			while(rs.next())
			{
				potenza= rs.getDouble(1);
				
			}
			
		}catch (Exception e) {
			throw e;
		}finally
		{
			pst.close();
			con.close();
		}
		return potenza;
	}



	public static ConversioneDTO getFattoreConversione(String um_da,String umCon,String tipoGrandezza) throws Exception {
		Connection con=null;
		PreparedStatement pst=null;
		ResultSet rs =null;
		ConversioneDTO fattConv=null;
		try
		{
			con=getConnection();
			
			pst=con.prepareStatement("select * FROM tbl_conversione WHERE um_da=? AND um_a=?  AND tipo_misura=? ");
			pst.setString(1, um_da);
			pst.setString(2, umCon);
			pst.setString(3, tipoGrandezza);
		
			rs=pst.executeQuery();
			
			while(rs.next())
			{
				fattConv= new ConversioneDTO();
				if(rs.getString("validita").equals("Tutti"))
				{
					fattConv.setValidita(true);
				}
				else
				{
					fattConv.setValidita(false);
				}
				fattConv.setFattoreConversione(rs.getBigDecimal("fattoreConversione"));
				fattConv.setPotenza(rs.getDouble("potenza"));
			
				
			}
			
		}catch (Exception e) {
			e.printStackTrace();
			throw e;
		}finally
		{
			pst.close();
			con.close();
		}
		return fattConv;
	}



	public static BigDecimal[] getMinMaxScala(String codice,String parametro) throws Exception {
		Connection con=null;
		PreparedStatement pst=null;
		ResultSet rs =null;
		BigDecimal[] minMax =new BigDecimal[2];
		try
		{
			con=getConnection();
			
			pst=con.prepareStatement("SELECT MIN(valore_taratura),MAX(valore_taratura)  FROM tblCampioni WHERE codice=? AND parametri_Taratura=?");
			pst.setString(1, codice);
			pst.setString(2, parametro);
		
		
			rs=pst.executeQuery();
			
			while(rs.next())
			{
				minMax[0]=rs.getBigDecimal(1);
				minMax[1]=rs.getBigDecimal(2);
			
				
			}
			
		}catch (Exception e) {
			e.printStackTrace();
			throw e;
		}finally
		{
			pst.close();
			con.close();
		}
		return minMax;
	}



	public static ArrayList<BigDecimal> getValoriMediCampione(int idMisura,int id_ripetizione, int id) throws Exception {
		Connection con=null;
		PreparedStatement pst=null;
		ResultSet rs =null;
		ArrayList<BigDecimal> listaValoriMedi =new ArrayList<BigDecimal>();
		try
		{
			con=getConnection();
			
			pst=con.prepareStatement("SELECT valoreCampione  FROM tblTabelleMisura WHERE id_ripetizione=?  AND id_misura=? AND id<>? AND id_Tabella = (select id_tabella from tblTabelleMisura where id=?)");
			
			pst.setInt(1,id_ripetizione);
			pst.setInt(2, idMisura);
			pst.setInt(3, id);
			pst.setInt(4, id);
		
		
			rs=pst.executeQuery();
			
			while(rs.next())
			{
				Object obj =rs.getObject(1);
				if(obj!=null && obj.toString().length()>0)
				{
					listaValoriMedi.add(rs.getBigDecimal(1));
				}
				}
			
		}catch (Exception e) {
			e.printStackTrace();
			throw e;
		}finally
		{
			pst.close();
			con.close();
		}
		return listaValoriMedi;
	}



	public static void setValoriMediCampione(int idMisura, int id_ripetizioni,BigDecimal valoreMedioCampione, int id) throws Exception {
		
		Connection con=null;
		PreparedStatement pst=null;
		try
		{
			con=getConnection();
			
			pst=con.prepareStatement("update tblTabelleMisura  SET valoreMedioCampione=? WHERE id_ripetizione = ? AND id_misura=? AND id_Tabella = (select id_tabella from tblTabelleMisura where id=?)");
			
			pst.setBigDecimal(1, valoreMedioCampione);
			pst.setInt(2, id_ripetizioni);
			pst.setInt(3, idMisura);
			pst.setInt(4, id);
		
		
			pst.execute();
			
		
			
		}catch (Exception e) {
			e.printStackTrace();
			throw e;
		}finally
		{
			pst.close();
			con.close();
		}

	}



	public static ArrayList<BigDecimal> getValoriMediStumento(int idMisura,int id_ripetizione, int id) throws Exception {
		Connection con=null;
		PreparedStatement pst=null;
		ResultSet rs =null;
		ArrayList<BigDecimal> listaValoriMedi =new ArrayList<BigDecimal>();
		try
		{
			con=getConnection();
			
			pst=con.prepareStatement("SELECT valoreStrumento  FROM tblTabelleMisura WHERE id_ripetizione=?  AND id_misura=? AND id<>? AND id_Tabella = (select id_tabella from tblTabelleMisura where id=?)");
			pst.setInt(1, id_ripetizione);
			pst.setInt(2, idMisura);
			pst.setInt(3, id);
			pst.setInt(4, id);
		
		
			rs=pst.executeQuery();
			
			while(rs.next())
			{
				Object obj =rs.getObject(1);
				if(obj!=null && obj.toString().length()>0)
				{
					listaValoriMedi.add(rs.getBigDecimal(1));
				}
				}
			
		}catch (Exception e) {
			e.printStackTrace();
			throw e;
		}finally
		{
			pst.close();
			con.close();
		}
		return listaValoriMedi;
	}



	public static void setValoriMediStrumento(int idMisura, int id_ripetizione,BigDecimal valoreMedioStrumento, int id) throws Exception {
	
		Connection con=null;
		PreparedStatement pst=null;
		try
		{
			con=getConnection();
			
			pst=con.prepareStatement("update tblTabelleMisura  SET valoreMedioStrumento=? WHERE id_ripetizione=? AND id_misura=? AND id_Tabella = (select id_tabella from tblTabelleMisura where id=?) ");
			
			pst.setBigDecimal(1, valoreMedioStrumento);
			pst.setInt(2, id_ripetizione);
			pst.setInt(3, idMisura);
			pst.setInt(4, id);
		
		
			pst.execute();
			
		
			
		}catch (Exception e) {
			e.printStackTrace();
			throw e;
		}finally
		{
			pst.close();
			con.close();
		}
		
	}

	
	public static MisuraDTO getMisura(int id) throws Exception
	{
		MisuraDTO misura=null;
		Connection con=null;
		PreparedStatement pst=null;
		ResultSet rs =null;
		try
		{
			con=getConnection();
			
			pst=con.prepareStatement("Select * from  tblTabelleMisura WHERE id =?");
			pst.setInt(1, id);
		
		
			rs=pst.executeQuery();
			
		while (rs.next()) {
		
			misura = new MisuraDTO();
			misura.setId(id);
			misura.setId_ripetizione(rs.getInt("id_ripetizione"));
			misura.setOrdine(rs.getInt("ordine"));
			misura.setTipoVerifica(rs.getString("tipoVerifica"));
			misura.setUm(rs.getString("um"));
			misura.setValoreCampione(rs.getBigDecimal("valoreCampione"));
			misura.setValoreMedioCampione(rs.getBigDecimal("valoreMedioCampione"));
			misura.setValoreStrumento(rs.getBigDecimal("valoreStrumento"));
			misura.setValoreMedioStrumento(rs.getBigDecimal("valoreMedioStrumento"));
			misura.setScostamento(rs.getBigDecimal("scostamento"));
			misura.setAccettabilita(rs.getBigDecimal("Accettabilita"));
			misura.setEsito(rs.getString("esito"));
			misura.setDescrizioneCampione(rs.getString("desc_campione"));
			misura.setDescrizioneParametro(rs.getString("desc_parametro"));
			misura.setMisura(rs.getBigDecimal("misura"));
			misura.setUm_calc(rs.getString("um_calc"));
			misura.setRisoluzione_campione(rs.getBigDecimal("risoluzione_campione"));
			misura.setRisoluzione_misura(rs.getBigDecimal("risoluzione_misura"));

			misura.setInterpolazione(rs.getInt("interpolazione"));
			misura.setFm(rs.getString("fm"));
			misura.setSelConversione(rs.getInt("selConversione"));
			misura.setLetturaCampione(rs.getBigDecimal("letturaCampione"));
			misura.setSelTolleranza(rs.getInt("selTolleranza"));
			
			String fs=rs.getString("fondo_scala");
			
			if(fs!=null && fs.length()>0)
			{
				misura.setFondoScala(new BigDecimal(fs));
			}else
			{
				misura.setFondoScala(null);
			}
			
			String perc=rs.getString("perc_util");
			
			if(perc!=null && perc.length()>0)
			{
				misura.setPercentuale(new BigDecimal(perc));
			}else
			{
				misura.setPercentuale(null);
			}
			
			misura.setMisuraPrecedente(rs.getString("val_misura_prec"));
			misura.setMisuraCampionePrecedente(rs.getString("val_campione_prec"));
			misura.setApplicabile(rs.getString("applicabile"));
			misura.setDgt(rs.getBigDecimal("dgt"));
			
			misura.setProvaPrecedente(rs.getString("val_descrizione_prec"));
			misura.setEsitoPrecedente(rs.getString("val_esito_prec"));
			misura.setFile_att(rs.getBytes("file_att"));
			misura.setFile_att_prec(rs.getBytes("file_att_prec"));
			
		}
			
		}catch (Exception e) {
			e.printStackTrace();
			throw e;
		}finally
		{
			pst.close();
			con.close();
		}
		
		return misura;
	}

	public static ArrayList<MisuraDTO> getListaRecordTabellaEsterna(String filename,int idMisura) throws Exception
	{
		ArrayList<MisuraDTO> listaMisure = new ArrayList<>();
		
		MisuraDTO misura=null;
		Connection con=null;
		PreparedStatement pst=null;
		ResultSet rs =null;
		try
		{
			con=getConnectionExternal(filename);
			
			pst=con.prepareStatement("Select * from  tblTabelleMisura WHERE id_misura =?");
			pst.setInt(1, idMisura);
		
		
			rs=pst.executeQuery();
			
		while (rs.next()) {
		
			misura = new MisuraDTO();
			misura.setId(idMisura);
			misura.setIdTabella(rs.getInt("id_tabella"));
			misura.setId_ripetizione(rs.getInt("id_ripetizione"));
			misura.setOrdine(rs.getInt("ordine"));
			misura.setTipoProva(rs.getString("tipoProva"));
			misura.setTipoVerifica(rs.getString("tipoVerifica"));
			misura.setLabel(rs.getString("label"));
			misura.setUm(rs.getString("um"));
			misura.setValoreCampione(rs.getBigDecimal("valoreCampione"));
			misura.setValoreMedioCampione(rs.getBigDecimal("valoreMedioCampione"));
			misura.setValoreStrumento(rs.getBigDecimal("valoreStrumento"));
			misura.setValoreMedioStrumento(rs.getBigDecimal("valoreMedioStrumento"));
			misura.setScostamento(rs.getBigDecimal("scostamento"));
			misura.setAccettabilita(rs.getBigDecimal("Accettabilita"));
			misura.setEsito(rs.getString("esito"));
			misura.setDescrizioneCampione(rs.getString("desc_campione"));
			misura.setDescrizioneParametro(rs.getString("desc_parametro"));
			misura.setMisura(rs.getBigDecimal("misura"));
			misura.setUm_calc(rs.getString("um_calc"));
			misura.setRisoluzione_campione(rs.getBigDecimal("risoluzione_campione"));
			misura.setRisoluzione_misura(rs.getBigDecimal("risoluzione_misura"));

			misura.setInterpolazione(rs.getInt("interpolazione"));
			misura.setFm(rs.getString("fm"));
			misura.setSelConversione(rs.getInt("selConversione"));
			misura.setLetturaCampione(rs.getBigDecimal("letturaCampione"));
			misura.setSelTolleranza(rs.getInt("selTolleranza"));
			
			String fs=rs.getString("fondo_scala");
			
			if(fs!=null && fs.length()>0)
			{
				misura.setFondoScala(new BigDecimal(fs));
			}else
			{
				misura.setFondoScala(null);
			}
			
			String perc=rs.getString("perc_util");
			
			if(perc!=null && perc.length()>0)
			{
				misura.setPercentuale(new BigDecimal(perc));
			}else
			{
				misura.setPercentuale(null);
			}
			
			misura.setMisuraPrecedente(rs.getString("val_misura_prec"));
			misura.setMisuraCampionePrecedente(rs.getString("val_campione_prec"));
			misura.setApplicabile(rs.getString("applicabile"));
			misura.setDgt(rs.getBigDecimal("dgt"));
			
			
			listaMisure.add(misura);
		}
			
		}catch (Exception e) {
			e.printStackTrace();
			throw e;
		}finally
		{
			pst.close();
			con.close();
		}
		
		return listaMisure;
	}

	public static boolean isPresentCampione(int idTabella, int idMisura) throws Exception {
		Connection con=null;
		PreparedStatement pst=null;
		ResultSet rs;
		boolean isPresent=false;
		try
		{
			con=getConnection();
			
			pst=con.prepareStatement("select * from tblCampioniUtilizzati WHERE id_tabellaMisura=? AND id_misura=?");
			
			pst.setInt(1,idTabella);
			pst.setInt(2, idMisura);

			rs=pst.executeQuery();
			
		while(rs.next())
		{
			isPresent=true;
		}
		
			
		}catch (Exception e) {
			e.printStackTrace();
			throw e;
		}finally
		{
			pst.close();
			con.close();
		}
		
		return isPresent;
	}



	public static void insertCampioneUtilizzato(int idTabella, int idMisura,String campione, String parametro) throws Exception {
		
		Connection con=null;
		PreparedStatement pst=null;
	
		try
		{
			con=getConnection();
			
			pst=con.prepareStatement("INSERT INTO tblCampioniUtilizzati(id_tabellaMisura,id_misura,desc_parametro,desc_campione) VALUES(?,?,?,?)");
			
			pst.setInt(1,idTabella);
			pst.setInt(2, idMisura);
			pst.setString(3, campione);
			pst.setString(4, parametro);

			pst.execute();		
			
		}catch (Exception e) {
			e.printStackTrace();
			throw e;
		}finally
		{
			pst.close();
			con.close();
		}
		
		
	}



	public static void updateCampioneUtilizzato(int idTabella, int idMisura,String campione, String parametro) throws Exception {
		Connection con=null;
		PreparedStatement pst=null;
	
		try
		{
			con=getConnection();
			
			pst=con.prepareStatement("UPDATE  tblCampioniUtilizzati SET desc_campione=? , desc_parametro=? WHERE id_tabellaMisura=? AND id_misura=?");
			
			pst.setString(1, campione);
			pst.setString(2, parametro);
			pst.setInt(3,idTabella);
			pst.setInt(4, idMisura);
			

			pst.execute();		
			
		}catch (Exception e) {
			e.printStackTrace();
			throw e;
		}finally
		{
			pst.close();
			con.close();
		}
		
	}



	public static void terminaMisura(String idStrumento, BigDecimal temperatura,BigDecimal umidita, int sr, int firma) throws Exception {
		
		Connection con=null;
		PreparedStatement pst=null;
	
		try
		{
			con=getConnection();
			
			pst=con.prepareStatement("UPDATE  tblMisure SET dataMisura=? ,temperatura=? , umidita=? , statoRicezione=? ,statoMisura=1, tipoFirma=? WHERE id_str=?");
			SimpleDateFormat sdf =new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			Date d = new Date();
			pst.setString(1,sdf.format(d));
			
			pst.setBigDecimal(2, temperatura);
			pst.setBigDecimal(3, umidita);
			pst.setInt(4,sr);
			pst.setInt(5, firma);
			pst.setInt(6, Integer.parseInt(idStrumento));
			
		

			pst.execute();		
			
		}catch (Exception e) {
			e.printStackTrace();
			throw e;
		}finally
		{
			pst.close();
			con.close();
		}

	}
	
	public static ArrayList<MisuraDTO> getListaPunti(int id, int idMisura,int id_ripetizione) throws Exception
	{
		
		ArrayList<MisuraDTO> listaMisura=new ArrayList<>();
		Connection con=null;
		PreparedStatement pst=null;
		ResultSet rs =null;
		try
		{
			con=getConnection();
			
			pst=con.prepareStatement("select * from tblTabelleMisura where id_Tabella = (select id_tabella from tblTabelleMisura where id=?) AND id_misura=? and id_ripetizione = ?");
			pst.setInt(1, id);
			pst.setInt(2, idMisura);
			pst.setInt(3, id_ripetizione);
		
		
			rs=pst.executeQuery();
		MisuraDTO misura=null;
		
		while (rs.next()) {
		
			misura = new MisuraDTO();
			misura.setId(rs.getInt("id"));
			misura.setId_ripetizione(rs.getInt("id_ripetizione"));
			misura.setOrdine(rs.getInt("ordine"));
			misura.setTipoVerifica(rs.getString("tipoVerifica"));
			misura.setUm(rs.getString("um"));
			misura.setValoreCampione(rs.getBigDecimal("valoreCampione"));
			misura.setValoreMedioCampione(rs.getBigDecimal("valoreMedioCampione"));
			misura.setValoreStrumento(rs.getBigDecimal("valoreStrumento"));
			misura.setValoreMedioStrumento(rs.getBigDecimal("valoreMedioStrumento"));
			misura.setScostamento(rs.getBigDecimal("scostamento"));
			misura.setAccettabilita(rs.getBigDecimal("Accettabilita"));
			misura.setEsito(rs.getString("esito"));
			misura.setDescrizioneCampione(rs.getString("desc_campione"));
			misura.setDescrizioneParametro(rs.getString("desc_parametro"));
			misura.setMisura(rs.getBigDecimal("misura"));
			misura.setUm_calc(rs.getString("um_calc"));
			misura.setRisoluzione_campione(rs.getBigDecimal("risoluzione_campione"));
			misura.setRisoluzione_misura(rs.getBigDecimal("risoluzione_misura"));
			misura.setFondoScala(rs.getBigDecimal("fondo_scala"));
			misura.setInterpolazione(rs.getInt("interpolazione"));
			misura.setIncertezza(rs.getBigDecimal("incertezza"));
			misura.setFm(rs.getString("fm"));
			misura.setSelConversione(rs.getInt("selConversione"));
			misura.setLetturaCampione(rs.getBigDecimal("letturaCampione"));
			misura.setSelTolleranza(rs.getInt("selTolleranza"));
			misura.setPercentuale(rs.getBigDecimal("perc_util"));
			misura.setMisuraPrecedente(rs.getString("val_misura_prec"));
			misura.setMisuraCampionePrecedente(rs.getString("val_campione_prec"));
			
			listaMisura.add(misura);
		}
			
		}catch (Exception e) {
			e.printStackTrace();
			throw e;
		}finally
		{
			pst.close();
			con.close();
		}
		
		return listaMisura;
		
	}



	public static void updateValoriRipetibilita(ArrayList<MisuraDTO> listaMisure) {
		
		Connection con=null;
		PreparedStatement pst=null;
		try
		{
			con=getConnection();
			
			pst=con.prepareStatement("update tblTabelleMisura  SET scostamento=? , incertezza=? , esito=? WHERE id=?");
			
			for (int i = 0; i < listaMisure.size(); i++) {
			MisuraDTO misura=listaMisure.get(i);
				if(misura.getValoreStrumento()!=null && !misura.getValoreStrumento().equals(""))
				{
					pst.setBigDecimal(1, misura.getScostamento());
					pst.setBigDecimal(2, misura.getIncertezza());
					pst.setString(3, misura.getEsito());
					pst.setInt(4, misura.getId());
					pst.execute();
				}
			}
			
			
		}catch (Exception e) {
			e.printStackTrace();
			}
		
	}



	public static Vector<ClassificazioneDTO> getVectorClassificazione() {
		Connection con=null;
		PreparedStatement pst=null;
		ResultSet rs = null;
		Vector<ClassificazioneDTO> model = new Vector<ClassificazioneDTO>();
		try
		{
			con=getConnection();
			
			pst=con.prepareStatement("select * FROM tbl_classificazione");
			rs=pst.executeQuery();
			
			ClassificazioneDTO classificazione=null;
			while(rs.next())
			{
				classificazione= new ClassificazioneDTO();
				classificazione.setId(rs.getInt("id"));
				classificazione.setDescrizione(rs.getString("descrizione"));
				
				model.addElement(classificazione);
			}
			
			
		}catch (Exception e) 
		{
			e.printStackTrace();
		}
		return model;
	} 
	
	public static Vector<LuogoVerificaDTO> getVectorLuogoVerifica() {
		Connection con=null;
		PreparedStatement pst=null;
		ResultSet rs = null;
		Vector<LuogoVerificaDTO> model = new Vector<LuogoVerificaDTO>();
		try
		{
			con=getConnection();
			
			pst=con.prepareStatement("select * FROM tbl_luogoVerifica");
			rs=pst.executeQuery();
			
			LuogoVerificaDTO luogo=null;
			while(rs.next())
			{
				luogo= new LuogoVerificaDTO();
				luogo.setId(rs.getInt("id"));
				luogo.setDescrizione(rs.getString("descrizione"));
				
				model.addElement(luogo);
			}
			
			
		}catch (Exception e) 
		{
			e.printStackTrace();
		}
		return model;
	} 
	
	public static Vector<TipoRapportoDTO> getVectorTipoRapporto() {
		Connection con=null;
		PreparedStatement pst=null;
		ResultSet rs = null;
		Vector<TipoRapportoDTO> model = new Vector<TipoRapportoDTO>();
		try
		{
			con=getConnection();
			
			pst=con.prepareStatement("select * FROM tbl_tipoRapporto ");
			rs=pst.executeQuery();
			
			TipoRapportoDTO tipoRapporto=null;
			while(rs.next())
			{
				tipoRapporto= new TipoRapportoDTO();
				tipoRapporto.setId(rs.getInt("id"));
				tipoRapporto.setDescrizione(rs.getString("descrizione"));
				
				model.addElement(tipoRapporto);
			}
			
			
		}catch (Exception e) 
		{
			e.printStackTrace();
		}
		return model;
	} 
	
	public static Vector<TipoStrumentoDTO> getVectorTipoStrumento() {
		Connection con=null;
		PreparedStatement pst=null;
		ResultSet rs = null;
		Vector<TipoStrumentoDTO> model = new Vector<TipoStrumentoDTO>();
		try
		{
			con=getConnection();
			
			pst=con.prepareStatement("select * FROM tbl_tipoStrumento ORDER BY descrizione ASC");
			rs=pst.executeQuery();
			
			TipoStrumentoDTO tipoStrumento=null;
			
			TipoStrumentoDTO ts = new TipoStrumentoDTO();
			ts.setDescrizione("Seleziona tipo strumento");
			ts.setId(0);
			model.addElement(ts);
			while(rs.next())
			{
				tipoStrumento= new TipoStrumentoDTO();
				tipoStrumento.setId(rs.getInt("id"));
				tipoStrumento.setDescrizione(rs.getString("descrizione"));
				
				model.addElement(tipoStrumento);
			}
			
			
		}catch (Exception e) 
		{
			e.printStackTrace();
		}
		return model;
	}



	public static int insertStrumento(StrumentoDTO strumento, String nomeSede) throws Exception {
		
		Connection con=null;
		PreparedStatement pst=null;
		try
		{
			con=getConnection();
			
			pst=con.prepareStatement("INSERT INTO tblStrumenti(indirizzo,denominazione,codice_interno,costruttore,modello,classificazione,matricola," +
															   "risoluzione,campo_misura,freq_verifica_mesi,tipoRapporto,statoStrumento," +
																"reparto,utilizzatore,procedura,id_tipo_strumento,note,creato,importato,luogo_verifica) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)",
																Statement.RETURN_GENERATED_KEYS);
			
			
			pst.setString(1, nomeSede);
			pst.setString(2, strumento.getDenominazione());
			pst.setString(3, strumento.getCodice_interno());
			pst.setString(4, strumento.getCostruttore());
			pst.setString(5, strumento.getModello());
			pst.setString(6, strumento.getClassificazione());
			pst.setString(7, strumento.getMatricola());
			pst.setString(8, strumento.getRisoluzione());
			pst.setString(9, strumento.getCampo_misura());
			pst.setInt(10, strumento.getFreq_taratura());
			pst.setString(11, strumento.getTipoRapporto());
			pst.setString(12, strumento.getStatoStrumento());
			pst.setString(13, strumento.getReparto());
			pst.setString(14, strumento.getUtilizzatore());
			pst.setString(15, strumento.getProcedura());
			pst.setString(16, strumento.getId_tipoStrumento());
			pst.setString(17, strumento.getNote());
			pst.setString(18, "S");
			pst.setString(19, "N");
			pst.setString(20, ""+strumento.getLuogoVerifica());
		
			pst.executeUpdate();
			
			ResultSet rs = pst.getGeneratedKeys();
			rs.next();
		    return rs.getInt(1);
				
		
			
			
		}catch (Exception e) 
		{
			e.printStackTrace();
			throw e;
		}
	}



	public static String getNomeSede() {
		Connection con=null;
		PreparedStatement pst=null;
		ResultSet rs = null;
		String nomeSede="";
		try
		{
			con=getConnection();
			
			pst=con.prepareStatement("select sede FROM tbl_general WHERE id=1");
			rs=pst.executeQuery();
			
			
			while(rs.next())
			{
				nomeSede= rs.getString(1);
				
			}
			
			
		}catch (Exception e) 
		{
			e.printStackTrace();
		}
		return nomeSede;
	}



	public static boolean checkExecute(String pATH_DB) {
		boolean toReturn=true;

		Connection con=null;
		PreparedStatement pst=null;
		ResultSet rs = null;
	
		
		try
		{
			con=getConnection();
			
			pst=con.prepareStatement("select * FROM tbl_general WHERE upload='S'");
			rs=pst.executeQuery();
			
			
			while(rs.next())
			{
			toReturn=false;
				
			}
			
		}catch (Exception e) 
		 {
			e.printStackTrace();
		}	
		
		return toReturn;
		}



	public static void chiudiMisura(String pATH_DB) {
		Connection con=null;
		PreparedStatement pst=null;
	
		
		try
		{
		  con=getConnection();
			
		  pst=con.prepareStatement("UPDATE tbl_general SET upload='S'");
			
		   pst.execute();	
		

		
		}catch (Exception e) 
		 {
			e.printStackTrace();
		
		 }	
		
		
		}



	public static void deleteRecordMisura(int id) {
		
		Connection con=null;
		PreparedStatement pst=null;
	
		
		try
		{
		  con=getConnection();
			
		  pst=con.prepareStatement("DELETE FROM tblTabelleMisura WHERE id=?");
		  pst.setInt(1, id);	
		   pst.execute();	
		
			
		}catch (Exception e) 
		 {
			e.printStackTrace();
		}	
		
		}



	public static void assegnaASFound(ArrayList<MisuraDTO> listaMisure) throws SQLException {
		
		Connection con=null;
		PreparedStatement pst=null;
	
		
		try
		{
		  con=getConnection();
			
		  pst=con.prepareStatement("Update tblTabelleMisura SET tipoVerifica=?,calibrazione=? WHERE id=?");
		  
		 for (int i = 0; i < listaMisure.size(); i++) 
		 {
			pst.setString(1,listaMisure.get(i).getTipoVerifica()+" (ASF)");
			pst.setString(2,"ASF");
			pst.setInt(3,listaMisure.get(i).getId() );
			
			pst.execute();
		 }
		
			
		}
		catch (Exception e) 
		 {
			e.printStackTrace();
		}
		finally
		{
			pst.close();
			con.close();
		}	
		
		}

	





	public static void salvaCertificato(int id, String code) throws SQLException {
		
		Connection con=null;
		PreparedStatement pst=null;
		
		try
		{
		  con=getConnection();
			
		  pst=con.prepareStatement("Update tblStrumenti set nCertificato=? WHERE id=?");
		  pst.setString(1, code);
		  pst.setInt(2, id);
		  
		  pst.execute();
		 
		}
		catch (Exception e) 
		 {
			e.printStackTrace();
			
		}finally
		{
			pst.close();
			con.close();
		}	
		
	}


	public static ArrayList<String> getListaGrandezze() throws Exception {
		
		Connection con =null;
		PreparedStatement pst=null;
		ResultSet rs = null;
		ArrayList<String> listaRitorno= new ArrayList<String>();
		listaRitorno.add("Selezione grandezza");
		try {
			con =getConnection();
			pst=con.prepareStatement("SELECT COUNT(tipo_misura) AS count,tipo_misura FROM tbl_conversione GROUP BY tipo_misura");
			
			rs=pst.executeQuery();
			
			while(rs.next())
			{
				int numero =rs.getInt("count");
				if(numero>0)
				{
					listaRitorno.add(rs.getString("tipo_misura"));
				}
			}
			
		} catch (Exception e) 
		{
			e.printStackTrace();
			throw e;
			
		}
		return listaRitorno;
	}


	public static ArrayList<String> getListaUM(String param) throws Exception {
		Connection con =null;
		PreparedStatement pst=null;
		ResultSet rs = null;
		ArrayList<String> listaRitorno= new ArrayList<String>();
		listaRitorno.add("Selezione Unit� Misura");
		try {
			con =getConnection();
			pst=con.prepareStatement("select distinct (um_a),um from tbl_conversione where tipo_misura=?");
			pst.setString(1, param);
			rs=pst.executeQuery();
			
			while(rs.next())
			{
				listaRitorno.add(rs.getString(1)+" @ "+rs.getString(2));			
				
			}
			
		} catch (Exception e) 
		{
			e.printStackTrace();
			throw e;
			
		}
		
		return listaRitorno;
	}


	public static boolean controllaMisuraCertificato() throws Exception {
		Connection con =null;
		PreparedStatement pst=null;
		ResultSet rs = null;

		try {
			con =getConnection();
			pst=con.prepareStatement("SELECT * FROM tblMisure WHERE statoMisura=1");
		
			rs=pst.executeQuery();
			
			while(rs.next())
			{
				
				return true;
			}
			
		} catch (Exception e) 
		{
			e.printStackTrace();
			throw e;
			
		}finally 
		{
			pst.close();
			con.close();
		}
		
		return false;
	}


	public static int updateStrumento(StrumentoDTO strumento) throws Exception {
		
		Connection con =null;
		PreparedStatement pst=null;
		int toReturn=0;
		
		try {
			con =getConnection();
			pst=con.prepareStatement("UPDATE tblStrumenti set denominazione=?,codice_interno=?,costruttore=?,modello=?," +
															  "classificazione=?,matricola=?,risoluzione=?,campo_misura=?," +
															  "freq_verifica_mesi=?,tipoRapporto=?,reparto=?,utilizzatore=?," +
															  "procedura=?,id_tipo_strumento=?,note=?,strumentoModificato='S',luogo_verifica=? WHERE id=?");
		
			pst.setString(1,strumento.getDenominazione());
			pst.setString(2,strumento.getCodice_interno());
			pst.setString(3,strumento.getCostruttore());
			pst.setString(4,strumento.getModello());
			pst.setInt(5,strumento.getIdClassificazione());
			pst.setString(6,strumento.getMatricola());
			pst.setString(7,strumento.getRisoluzione());
			pst.setString(8,strumento.getCampo_misura());
			pst.setInt(9,strumento.getFreq_taratura());
			pst.setInt(10, strumento.getIdTipoRappoto());
			pst.setString(11,strumento.getReparto());
			pst.setString(12,strumento.getUtilizzatore());
			pst.setString(13,strumento.getProcedura());
			pst.setString(14, strumento.getId_tipoStrumento());
			pst.setString(15, strumento.getNote());
			pst.setInt(16, strumento.getLuogoVerifica());
			pst.setInt(17, strumento.get__id());
			
			
			toReturn=pst.executeUpdate();
			
		} catch (Exception e) 
		{
			e.printStackTrace();
			throw e;
			
		}
		
		return toReturn;
	}


	public static void removeTabellaMisura(int idMisura, int id_tabella) throws Exception {
		
		Connection con =null;
		PreparedStatement pst=null;
		
		try 
		{
			con=getConnection();
			pst=con.prepareStatement("DELETE FROM tblTabelleMisura WHERE id_misura=? AND id_tabella=?");
			pst.setInt(1, idMisura);
			pst.setInt(2, id_tabella);
			
			
			pst.execute();
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			throw e;
		}
		
	}


	public static HashMap<String, String> getListaIDCampioni() throws Exception {
		HashMap<String, String> listaCampioni = new HashMap<>();
		Connection con =null;
		PreparedStatement pst=null;
		ResultSet rs = null;
		try 
		{
			con=getConnection();
			pst=con.prepareStatement("SELECT DISTINCT(ID_CAMP) FROM tblCampioni");
			
			rs= pst.executeQuery();
			
			while(rs.next())
			{
				listaCampioni.put(rs.getString(1),"");
			}
			
		}catch(Exception e)
		{
			throw e;
		}

		return listaCampioni;
	}


	public static boolean insertCampioni(ArrayList<CampioneDTO> listaCampioni) throws Exception {
	
		Connection con=null;
		PreparedStatement pst = null;
		boolean result=false;
		try 
		{
			con=getConnection();

			for (int i=0; i<listaCampioni.size();i++) {
				CampioneDTO campione =listaCampioni.get(i);
				pst=con.prepareStatement(insertCMP);
				pst.setString(1, campione.getId());
				pst.setString(2, campione.getCodice());
				pst.setString(3, campione.getMatricola());
				pst.setString(4, campione.getModello());
				pst.setString(5, campione.getNum_certificato());
				pst.setString(6, campione.getData());
				pst.setString(7, campione.getData_scadenza());
				pst.setString(8, campione.getFreq_mesi());
				pst.setString(9, campione.getParam_taratura());
				pst.setString(10, campione.getUm());
				pst.setString(11, campione.getUm_fond());
				pst.setString(12, campione.getValore_taratura());
				pst.setString(13, campione.getValore_nominale());
				pst.setString(14, campione.getDiv_um());
				pst.setString(15, campione.getIncertezzaAssoluta());
				pst.setString(16, campione.getIncertezzaRelativa());
				pst.setString(17, campione.getId_tipo_grand());
				pst.setString(18, campione.getInterpolazione());
				pst.setString(19, campione.getTipo_grandezza());
				pst.setString(20, campione.getAbilitato());
				
				result=pst.execute();
				
			}
			
		} catch (Exception e) 
		{
			throw e;
		}
		return result;
	}


	public static void cambiaStatoMisura(String idStrumento, int stato) throws Exception 
	{
		Connection con=null;
		PreparedStatement pst=null;
	
		try
		{
			con=getConnection();
			
			pst=con.prepareStatement("UPDATE  tblMisure SET statoMisura=? WHERE id_str=?");
			
			pst.setInt(1, stato);
			pst.setString(2, idStrumento);
		
			
			

			pst.execute();		
			
		}catch (Exception e) {
			e.printStackTrace();
			throw e;
		}finally
		{
			pst.close();
			con.close();
		}
		
	}


	public static ArrayList<String> getListaTipoGrandezzeByStrumento(String idStrumento) throws Exception {
		Connection con=null;
		PreparedStatement pst=null;
		ResultSet rs=null;
		ArrayList<String> listaTipoGrandezza= new ArrayList<>();
		
		try
		{
			con=getConnection();
			
			pst=con.prepareStatement("select id_tipo_grandezza FROM  tbl_ts_tg AS a " +
									 "left join tblStrumenti AS b on a.id_tipo_strumento=b.id_tipo_strumento " +
									 "where b.id=?");
			
			pst.setString(1, idStrumento);
			rs = pst.executeQuery();		
			
			while(rs.next())
			{
				listaTipoGrandezza.add(rs.getString(1));
			}
			
			
			
		}catch (Exception e) {
			e.printStackTrace();
			throw e;
		}finally
		{
			pst.close();
			con.close();
		}
		return listaTipoGrandezza;
	}


	public static int getMaxIDStrumento() throws Exception {
		Connection con=null;
		PreparedStatement pst=null;
		ResultSet rs=null;
		int toRet=0;
		
		try 
		{
			con=getConnection();
			pst=con.prepareStatement("SELECT MAX(id) FROM tblStrumenti");
			
			rs=pst.executeQuery();
			
			while(rs.next())
			{
				toRet=rs.getInt(1);
			}
			
		}
		catch (Exception e) 
		{
		 e.printStackTrace();	
		 throw e;
		}
		finally
		{
			pst.close();
			con.close();
		}

		return toRet;
	}



	public static void inserisciRigaTabellaDuplicata(MisuraDTO misura,int idTabella , int idMisura,String tipoProva) throws Exception {
		
		Connection con=null;
		PreparedStatement pst=null;
		try 
		{
			con=getConnection();
			pst=con.prepareStatement("INSERT INTO tblTabelleMisura(id_misura,id_tabella,id_ripetizione,ordine,tipoProva,label,tipoVerifica,applicabile,val_misura_prec,val_campione_prec) VALUES(?,?,?,?,?,?,?,?,?,?)");
		
		
			
			
			pst.setInt(1, idMisura);
			pst.setInt(2,idTabella);
			pst.setInt(3, misura.getId_ripetizione());
			pst.setInt(4, misura.getOrdine());
			pst.setString(5, tipoProva);
			pst.setString(6,misura.getLabel());
			pst.setString(7,misura.getTipoVerifica());
			pst.setString(8, misura.getApplicabile());
			if(misura.getValoreStrumento()!=null)
			{
				pst.setString(9,misura.getValoreStrumento().toPlainString());
			}else 
			{
				pst.setString(9,"");
			}
			pst.setString(10, misura.getDescrizioneCampione());
			
			pst.execute();
			
		}
		catch (Exception e) 
		{
		 e.printStackTrace();	
		 throw e;
		}
		finally
		{
			pst.close();
			con.close();
		}
		
	}
	public static ArrayList<DatiEsterniDTO> getDatiEsterni(String filename) throws Exception {
		ArrayList<DatiEsterniDTO> listaMisure = new ArrayList<>();
		
		DatiEsterniDTO dati=null;
		Connection con=null;
		PreparedStatement pst=null;
		ResultSet rs =null;
		try
		{
			con=getConnectionExternal(filename);
			
			pst=con.prepareStatement("select a.id , a.id_str ,b.codice_interno ,b.matricola,b.denominazione,b.modello from tblMisure a " + 
					"left join tblStrumenti b on a.id_str=b.id where statoMisura=1");
			
			rs=pst.executeQuery();
			
		while (rs.next()) {
		
			dati = new DatiEsterniDTO();
			dati.setIdMisura(rs.getInt(1));
			dati.setIdStrumento(rs.getInt(2));
			dati.setCodiceInterno(rs.getString(3));
			dati.setMatricola(rs.getString(4));
			dati.setDenominazione(rs.getString(5));
			dati.setModello(rs.getString(6));
			
			listaMisure.add(dati);
			
		}
			
		}catch (Exception e) {
			e.printStackTrace();
			throw e;
		}finally
		{
			pst.close();
			con.close();
		}
		
		return listaMisure;
	}
	public static void riapriMisura() throws Exception {
		
		Connection con=null;
		PreparedStatement pst=null;
		
		try 
		{
			con=getConnection();
			pst=con.prepareStatement("UPDATE tbl_general set upload='N'");
			pst.execute();
		}
		catch (Exception e) 
		{
		 e.printStackTrace();	
		 throw e;
		}
		finally
		{
			pst.close();
			con.close();
		}	
		
	}
	public static int getIndiceMasterLAT(String id_tipoStrumento) throws Exception {
		Connection con=null;
		PreparedStatement pst=null;
		ResultSet rs =null;
		int indexMasterTable=0;
		
		try 
		{
			con=getConnection();
			pst=con.prepareStatement("Select *FROM  lat_master");
			rs=pst.executeQuery();
			
			while(rs.next())
			{
				String[] listType=rs.getString("rif_tipoStrumento").split(";");
				
				for (String rif : listType) {
					
					if(rif.equals(id_tipoStrumento)) 
					{
						indexMasterTable=rs.getInt("id");
					}
					
				}
			}
		}
		catch (Exception e) 
		{
		 e.printStackTrace();	
		 throw e;
		}
		finally
		{
			pst.close();
			con.close();
		}	
		return indexMasterTable;
	}
	public static int isPresentLAT(String id) throws Exception{
		Connection con =null;
		PreparedStatement pst= null;
		
		try{
			con=getConnection();
			pst=con.prepareStatement("SELECT * FROM lat_misura WHERE id_strumento=?");
			pst.setInt(1,Integer.parseInt(id));
			ResultSet rs=pst.executeQuery();
			while(rs.next())
			{
			 return rs.getInt(1);
			}
			return 0;
		}catch(Exception ex)
		{
			throw ex;
		}
		finally
		{
			pst.close();
			con.close();
		}
	}
	public static int insertMisuraLAT(String id,int indexTableLAT)throws Exception {
		
		Connection con =null;
		PreparedStatement pst= null;

		try{
			con=getConnection();
			pst=con.prepareStatement("INSERT INTO lat_Misura(id_strumento,dataMisura,id_misura_lat) VALUES(?,?,?)",pst.RETURN_GENERATED_KEYS);
			
			pst.setInt(1,Integer.parseInt(id));
			SimpleDateFormat sdf =new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			Date d = new Date();
			pst.setString(2,sdf.format(d));
			pst.setInt(3,indexTableLAT);
			pst.execute();
		
		    ResultSet generatedKeys = pst.getGeneratedKeys(); 
		    	
		            if (generatedKeys.next()) {
		               return (int) generatedKeys.getLong(1);
		            }
		            else {
		                throw new SQLException("Error insert Misura, no ID obtained.");
		            }
		        
			  
		}catch(Exception ex)
		{
			ex.printStackTrace();
			throw ex;
		}
		finally
		{
			pst.close();
			con.close();
		}
		
	}
	
	public static void insertListaPuntiLivellaElettronica(LatPuntoLivellaElettronicaDTO punto) throws Exception {
		Connection con =null;
		PreparedStatement pst= null;

		try{
			con=getConnection();
			pst=con.prepareStatement("INSERT INTO lat_punto_livella_elettronica(id_misura,punto,tipo_prova,numero_prova,indicazione_iniziale,indicazione_iniziale_corr,valore_nominale," + 
					"valore_andata_taratura,valore_andata_campione,valore_ritorno_taratura,valore_ritorno_campione,andata_scostamento_campione,ritorno_scostamento_campione," + 
					"inclinazione_cmp_campione,scarto_tipo) "
					+ "VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			
			
			
			pst.setInt(1, punto.getId_misura());
			pst.setInt(2, punto.getPunto());
			pst.setString(3, punto.getTipo_prova());
			pst.setInt(4, punto.getNumero_prova());
			pst.setBigDecimal(5, punto.getIndicazione_iniziale());
			pst.setBigDecimal(6, punto.getIndicazione_iniziale_corr());
			pst.setBigDecimal(7, punto.getValore_nominale());
			pst.setBigDecimal(8, punto.getValore_andata_taratura());
			pst.setBigDecimal(9, punto.getValore_andata_campione());
			pst.setBigDecimal(10, punto.getValore_ritorno_taratura());
			pst.setBigDecimal(11, punto.getValore_ritorno_campione());
			pst.setBigDecimal(12, punto.getAndata_scostamento_campione());
			pst.setBigDecimal(13, punto.getRitorno_scostamento_campione());
			pst.setBigDecimal(14, punto.getInclinazione_cmp_campione());
			pst.setBigDecimal(15, punto.getScarto_tipo());
			
			pst.execute();
		
		    
			  
		}catch(Exception ex)
		{
			ex.printStackTrace();
			throw ex;
		}
		finally
		{
			pst.close();
			con.close();
		}
	}
	
	public static void insertListaPuntiLivellaBolla(PuntoLivellaBollaDTO punto) throws Exception {
		Connection con =null;
		PreparedStatement pst= null;

		try{
			con=getConnection();
			pst=con.prepareStatement("INSERT INTO lat_punto_livella(id_misura,rif_tacca,semisc,valore_nominale_tratto," + 
					"valore_nominale_tratto_sec,p1_andata,p1_ritorno,p1_media,p1_diff," + 
					"p2_andata,p2_ritorno,p2_media,p2_diff,media,errore_cum,media_corr_sec,media_corr_mm,div_dex,valore_nominale_tacca,corr_boll_mm,corr_boll_sec) "
					+ "VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			
			
			
			pst.setInt(1, punto.getId_misura());
			pst.setInt(2, punto.getRif_tacca());
			pst.setString(3, punto.getSemisc());
			pst.setBigDecimal(4, punto.getValore_nominale_tratto());
			pst.setBigDecimal(5, punto.getValore_nominale_tratto_sec());
			pst.setBigDecimal(6, punto.getP1_andata());
			pst.setBigDecimal(7, punto.getP1_ritorno());
			pst.setBigDecimal(8, punto.getP1_media());
			pst.setBigDecimal(9, punto.getP1_diff());
			pst.setBigDecimal(10, punto.getP2_andata());
			pst.setBigDecimal(11, punto.getP2_ritorno());
			pst.setBigDecimal(12, punto.getP2_media());
			pst.setBigDecimal(13, punto.getP2_diff());
			pst.setBigDecimal(14, punto.getMedia());
			pst.setBigDecimal(15, punto.getErrore_cum());
			pst.setBigDecimal(16, punto.getMedia_corr_sec());
			pst.setBigDecimal(17, punto.getMedia_corr_mm());
			pst.setBigDecimal(18, punto.getDiv_dex());
			pst.setString(19, punto.getValore_nominale_tacca());
			pst.setBigDecimal(20, punto.getCorr_boll_mm());
			pst.setBigDecimal(21, punto.getCorr_boll_sec());
			
			pst.execute();
		
		    
			  
		}catch(Exception ex)
		{
			ex.printStackTrace();
			throw ex;
		}
		finally
		{
			pst.close();
			con.close();
		}
	}
	
	public static ArrayList<PuntoLivellaBollaDTO> getListaPuntiLivellaBolla(int idMisura, String semisc) throws Exception {
		Connection con=null;
		PreparedStatement pst=null;
		ResultSet rs=null;
		ArrayList<PuntoLivellaBollaDTO> listaPunti= new ArrayList<PuntoLivellaBollaDTO>();
		
		try 
		{
			con=getConnection();
			
			pst=con.prepareStatement("SELECT * FROM lat_punto_livella where id_misura=? AND semisc=? order by rif_tacca ASC");
			
			pst.setInt(1, idMisura);
			pst.setString(2, semisc);
			rs=pst.executeQuery();
			
			PuntoLivellaBollaDTO punto= null;
			while(rs.next())
			{
				punto= new PuntoLivellaBollaDTO();
				punto.setId(rs.getInt("id"));
				punto.setRif_tacca(rs.getInt("rif_tacca"));
				punto.setSemisc(semisc);
				punto.setValore_nominale_tratto(rs.getBigDecimal("valore_nominale_tratto"));
				punto.setValore_nominale_tratto_sec(rs.getBigDecimal("valore_nominale_tratto_sec"));
				punto.setP1_andata(rs.getBigDecimal("p1_andata"));
				punto.setP1_ritorno(rs.getBigDecimal("p1_ritorno"));
				punto.setP1_media(rs.getBigDecimal("p1_media"));
				punto.setP1_diff(rs.getBigDecimal("p1_diff"));
				punto.setP2_andata(rs.getBigDecimal("p2_andata"));
				punto.setP2_ritorno(rs.getBigDecimal("p2_ritorno"));
				punto.setP2_media(rs.getBigDecimal("p2_media"));
				punto.setP2_diff(rs.getBigDecimal("p2_diff"));
				punto.setMedia(rs.getBigDecimal("media"));
				punto.setErrore_cum(rs.getBigDecimal("errore_cum"));
				punto.setMedia_corr_sec(rs.getBigDecimal("media_corr_sec"));
				punto.setMedia_corr_mm(rs.getBigDecimal("media_corr_mm"));
				punto.setDiv_dex(rs.getBigDecimal("div_dex"));
				punto.setValore_nominale_tacca(rs.getString("valore_nominale_tacca"));
				punto.setCorr_boll_mm(rs.getBigDecimal("corr_boll_mm"));
				punto.setCorr_boll_sec(rs.getBigDecimal("corr_boll_sec"));
				
				
				
				listaPunti.add(punto);
			}
			
		}
		catch (Exception e) 
		{
		 e.printStackTrace();	
		 throw e;
		}
		finally
		{
			pst.close();
			con.close();
		}

		return listaPunti;
	}
	
	public static ArrayList<LatPuntoLivellaElettronicaDTO> getListaPuntiLivellaElettronicaLineari(int idMisura) throws Exception {
		
		Connection con=null;
		PreparedStatement pst=null;
		ResultSet rs=null;
		ArrayList<LatPuntoLivellaElettronicaDTO> listaPunti= new ArrayList<LatPuntoLivellaElettronicaDTO>();
		
		
		try 
		{
			con=getConnection();
			
			pst=con.prepareStatement("SELECT * FROM lat_punto_livella_elettronica where id_misura=? AND tipo_prova=\'L\' order by punto ASC");
			
			pst.setInt(1, idMisura);
			rs=pst.executeQuery();
			
			LatPuntoLivellaElettronicaDTO punto= null;
			while(rs.next())
			{
				punto= new LatPuntoLivellaElettronicaDTO();
				punto.setId(rs.getInt("id"));
				punto.setPunto(rs.getInt("punto"));
				punto.setTipo_prova(rs.getString("tipo_prova"));
				punto.setNumero_prova(rs.getInt("numero_prova"));
				punto.setIndicazione_iniziale(rs.getBigDecimal("indicazione_iniziale"));
				punto.setIndicazione_iniziale_corr(rs.getBigDecimal("indicazione_iniziale_corr"));
				punto.setValore_nominale(rs.getBigDecimal("valore_nominale"));
				punto.setValore_andata_taratura(rs.getBigDecimal("valore_andata_taratura"));
				punto.setValore_andata_campione(rs.getBigDecimal("valore_andata_campione"));
				punto.setValore_ritorno_taratura(rs.getBigDecimal("valore_ritorno_taratura"));
				punto.setValore_ritorno_campione(rs.getBigDecimal("valore_ritorno_campione"));
				punto.setAndata_scostamento_campione(rs.getBigDecimal("andata_scostamento_campione"));
				punto.setAndata_correzione_campione(rs.getBigDecimal("andata_correzione_campione"));
				punto.setRitorno_scostamento_campione(rs.getBigDecimal("ritorno_scostamento_campione"));
				punto.setRitorno_correzione_campione(rs.getBigDecimal("ritorno_correzione_campione"));
				punto.setInclinazione_cmp_campione(rs.getBigDecimal("inclinazione_cmp_campione"));
				punto.setScostamentoA(rs.getBigDecimal("scostamentoA"));
				punto.setScostamentoB(rs.getBigDecimal("scostamentoB"));
				punto.setScostamentoMed(rs.getBigDecimal("scostamentoMed"));
				punto.setScostamentoOff(rs.getBigDecimal("scostamentoOff"));
				punto.setScarto_tipo(rs.getBigDecimal("scarto_tipo"));
				
				
				listaPunti.add(punto);
			}
			
		}
		catch (Exception e) 
		{
		 e.printStackTrace();	
		 throw e;
		}
		finally
		{
			pst.close();
			con.close();
		}

		return listaPunti;
	}
	
public static ArrayList<LatPuntoLivellaElettronicaDTO> getListaPuntiLivellaElettronicaIncertezze(int idMisura) throws Exception {
		
		Connection con=null;
		PreparedStatement pst=null;
		ResultSet rs=null;
		ArrayList<LatPuntoLivellaElettronicaDTO> listaPunti= new ArrayList<LatPuntoLivellaElettronicaDTO>();
		
		
		try 
		{
			con=getConnection();
			
			pst=con.prepareStatement("SELECT * FROM lat_punto_livella_elettronica where id_misura=? AND tipo_prova=\'I\' order by punto ASC");
			
			pst.setInt(1, idMisura);
			rs=pst.executeQuery();
			
			LatPuntoLivellaElettronicaDTO punto= null;
			while(rs.next())
			{
				punto= new LatPuntoLivellaElettronicaDTO();
				punto.setId(rs.getInt("id"));
				punto.setPunto(rs.getInt("punto"));
				punto.setTipo_prova(rs.getString("tipo_prova"));
				punto.setValore_nominale(rs.getBigDecimal("valore_nominale"));
				punto.setInc_ris(rs.getBigDecimal("inc_ris"));
				punto.setInc_rip(rs.getBigDecimal("inc_rip"));
				punto.setInc_cmp(rs.getBigDecimal("inc_cmp"));
				punto.setInc_stab(rs.getBigDecimal("inc_stab"));
				punto.setInc_est(rs.getBigDecimal("inc_est"));
				
			
				
				listaPunti.add(punto);
			}
			
		}
		catch (Exception e) 
		{
		 e.printStackTrace();	
		 throw e;
		}
		finally
		{
			pst.close();
			con.close();
		}

		return listaPunti;
	}

	public static ArrayList<ArrayList<LatPuntoLivellaElettronicaDTO>> getListaPuntiLivellaElettronicaRipetibili(int idMisura) throws Exception {
		Connection con=null;
		PreparedStatement pst=null;
		ResultSet rs=null;
		ArrayList<ArrayList<LatPuntoLivellaElettronicaDTO>> listaPunti= new ArrayList<ArrayList<LatPuntoLivellaElettronicaDTO>>();
		
		
		try 
		{
			con=getConnection();
			
			
			for (int i = 1; i <= 5; i++) {
				
			
			pst=con.prepareStatement("SELECT * FROM lat_punto_livella_elettronica where id_misura=? AND numero_prova=? order by punto ASC");
			
			pst.setInt(1, idMisura);
			pst.setInt(2, i);
			rs=pst.executeQuery();
			
			
			ArrayList<LatPuntoLivellaElettronicaDTO> lista_singolo_punto = new ArrayList<LatPuntoLivellaElettronicaDTO>();
			
			LatPuntoLivellaElettronicaDTO punto= null;
			while(rs.next())
			{
				punto= new LatPuntoLivellaElettronicaDTO();
				punto.setId(rs.getInt("id"));
				punto.setPunto(rs.getInt("punto"));
				punto.setTipo_prova(rs.getString("tipo_prova"));
				punto.setNumero_prova(rs.getInt("numero_prova"));
				punto.setIndicazione_iniziale(rs.getBigDecimal("indicazione_iniziale"));
				punto.setIndicazione_iniziale_corr(rs.getBigDecimal("indicazione_iniziale_corr"));
				punto.setValore_nominale(rs.getBigDecimal("valore_nominale"));
				punto.setValore_andata_taratura(rs.getBigDecimal("valore_andata_taratura"));
				punto.setValore_andata_campione(rs.getBigDecimal("valore_andata_campione"));
				punto.setValore_ritorno_taratura(rs.getBigDecimal("valore_ritorno_taratura"));
				punto.setValore_ritorno_campione(rs.getBigDecimal("valore_ritorno_campione"));
				punto.setAndata_scostamento_campione(rs.getBigDecimal("andata_scostamento_campione"));
				punto.setAndata_correzione_campione(rs.getBigDecimal("andata_correzione_campione"));
				punto.setRitorno_scostamento_campione(rs.getBigDecimal("ritorno_scostamento_campione"));
				punto.setRitorno_correzione_campione(rs.getBigDecimal("ritorno_correzione_campione"));
				punto.setInclinazione_cmp_campione(rs.getBigDecimal("inclinazione_cmp_campione"));
				punto.setScostamentoA(rs.getBigDecimal("scostamentoA"));
				punto.setScostamentoB(rs.getBigDecimal("scostamentoB"));
				punto.setScostamentoMed(rs.getBigDecimal("scostamentoMed"));
				punto.setScostamentoOff(rs.getBigDecimal("scostamentoOff"));
				punto.setScarto_tipo(rs.getBigDecimal("scarto_tipo"));
				
				
				
				lista_singolo_punto.add(punto);
			}
			listaPunti.add(lista_singolo_punto);
		  }	
		}
		catch (Exception e) 
		{
		 e.printStackTrace();	
		 throw e;
		}
		finally
		{
			pst.close();
			con.close();
		}

		return listaPunti;
	}
	
	public static void updateRecordPuntoLivellaBolla(PuntoLivellaBollaDTO punto) throws Exception {
	
		Connection con =null;
		PreparedStatement pst= null;

		try{
			con=getConnection();
			pst=con.prepareStatement("UPDATE lat_punto_livella SET valore_nominale_tratto=?," + 
					"valore_nominale_tratto_sec=?,p1_andata=?,p1_ritorno=?,p1_media=?,p1_diff=?," + 
					"p2_andata=?,p2_ritorno=?,p2_media=?,p2_diff=?,media=?,errore_cum=?,media_corr_sec=?,media_corr_mm=?,div_dex=?,corr_boll_mm=?,corr_boll_sec=? WHERE id=? ");
			
	
			pst.setBigDecimal(1, punto.getValore_nominale_tratto());
			pst.setBigDecimal(2, punto.getValore_nominale_tratto_sec());
			pst.setBigDecimal(3, punto.getP1_andata());
			pst.setBigDecimal(4, punto.getP1_ritorno());
			pst.setBigDecimal(5, punto.getP1_media());
			pst.setBigDecimal(6, punto.getP1_diff());
			pst.setBigDecimal(7, punto.getP2_andata());
			pst.setBigDecimal(8, punto.getP2_ritorno());
			pst.setBigDecimal(9, punto.getP2_media());
			pst.setBigDecimal(10, punto.getP2_diff());
			pst.setBigDecimal(11, punto.getMedia());
			pst.setBigDecimal(12, punto.getErrore_cum());
			pst.setBigDecimal(13, punto.getMedia_corr_sec());
			pst.setBigDecimal(14, punto.getMedia_corr_mm());
			pst.setBigDecimal(15, punto.getDiv_dex());
			pst.setBigDecimal(16, punto.getCorr_boll_mm());
			pst.setBigDecimal(17, punto.getCorr_boll_sec());
			pst.setInt(18, punto.getId());
			
			pst.execute();
		
		    
			  
		}catch(Exception ex)
		{
			ex.printStackTrace();
			throw ex;
		}
		finally
		{
			pst.close();
			con.close();
		}
		
	}
	public static void updateRecordPuntoLivellaBolla(LatMisuraDTO lat) throws Exception {
					   
		Connection con =null;
		PreparedStatement pst= null;

		try{
			con=getConnection();			
			
			pst=con.prepareStatement("UPDATE lat_misura SET incertezzaRif=?," + 
					"incertezzaRif_sec=?,incertezzaEstesa=?,incertezzaEstesa_sec=?,incertezzaMedia=?,campo_misura=?,unita_formato=?,campo_misura_sec=?," + 
					"sensibilita=?,stato=?,ammaccature=?,bolla_trasversale=?,regolazione=?,centraggio=?,note=?,id_rif_campione=?,id_rif_campione_lavoro=? WHERE id=? ");
	
			pst.setBigDecimal(1, lat.getIncertezza_rif());
			pst.setBigDecimal(2, lat.getIncertezza_sec());
			pst.setBigDecimal(3, lat.getIncertezza_estesa());
			pst.setBigDecimal(4,lat.getIncertezza_estesa_sec());
			pst.setBigDecimal(5,lat.getIncertezza_media());
			pst.setBigDecimal(6, lat.getCampo_misura());
			pst.setBigDecimal(7, lat.getUnita_formato());
			pst.setBigDecimal(8, lat.getCampo_misura_sec());
			pst.setBigDecimal(9,lat.getSensibilita());
			pst.setString(10, lat.getStato());
			pst.setString(11, lat.getAmmaccature());
			pst.setString(12, lat.getBolla_trasversale());
			pst.setString(13, lat.getRegolazione());
			pst.setString(14, lat.getCentraggio());
			pst.setString(15,lat.getNote() );
			pst.setString(16,lat.getRif_campione());
			pst.setString(17,lat.getRif_campione_lavoro());
			pst.setInt(18, lat.getId());
			
			pst.execute();
		
		    
			  
		}catch(Exception ex)
		{
			ex.printStackTrace();
			throw ex;
		}
		finally
		{
			pst.close();
			con.close();
		}
		
	}
	public static LatMisuraDTO getMisuraLAT(int idMisura) throws Exception {
		Connection con =null;
		PreparedStatement pst= null;
		ResultSet rs=null;
		LatMisuraDTO misura= null;
		try{
			con=getConnection();
			
			pst=con.prepareStatement("SELECT * FROM lat_misura where id=?");
		
			pst.setInt(1, idMisura);
			
			rs=pst.executeQuery();
			
			while(rs.next()) 
			{
				misura= new LatMisuraDTO();
				
				misura.setIncertezza_rif(rs.getBigDecimal("incertezzaRif"));
				misura.setIncertezza_sec(rs.getBigDecimal("incertezzaRif_sec"));
				misura.setIncertezza_estesa(rs.getBigDecimal("incertezzaEstesa"));
				misura.setIncertezza_estesa_sec(rs.getBigDecimal("incertezzaEstesa_sec"));
				misura.setIncertezza_media(rs.getBigDecimal("incertezzaMedia"));
				misura.setCampo_misura(rs.getBigDecimal("campo_misura"));
				misura.setUnita_formato(rs.getBigDecimal("unita_formato"));
				misura.setCampo_misura_sec(rs.getBigDecimal("campo_misura_sec"));
				misura.setSensibilita(rs.getBigDecimal("sensibilita"));
				misura.setStato(rs.getString("stato"));
				misura.setAmmaccature(rs.getString("ammaccature"));
				misura.setBolla_trasversale(rs.getString("bolla_trasversale"));
				misura.setRegolazione(rs.getString("regolazione"));
				misura.setCentraggio(rs.getString("centraggio"));
				misura.setNote(rs.getString("note"));
				misura.setRif_campione(rs.getString("id_rif_campione"));
				misura.setRif_campione_lavoro(rs.getString("id_rif_campione_lavoro"));
			
			}
			
		
		    
			  
		}catch(Exception ex)
		{
			ex.printStackTrace();
			throw ex;
			
		}
		finally
		{
			pst.close();
			con.close();
		}
		return misura;
		
	}
	public static void updateRecordPuntoLivellaElettronica(LatPuntoLivellaElettronicaDTO punto) throws Exception {
		
		Connection con =null;
		PreparedStatement pst= null;

		try{
			con=getConnection();			
			
			pst=con.prepareStatement("UPDATE lat_punto_livella_elettronica SET indicazione_iniziale=?," + 
					"indicazione_iniziale_corr=?,valore_nominale=?,valore_andata_taratura=?,valore_andata_campione=?,valore_ritorno_taratura=?,valore_ritorno_campione=?,"+ 
					"andata_scostamento_campione=?,andata_correzione_campione=?,ritorno_scostamento_campione=?,ritorno_correzione_campione=?,inclinazione_cmp_campione=?,"+
					"scostamentoA=?,scostamentoB=?,scostamentoMed=?,scostamentoOff=?,scarto_tipo=?,inc_ris=?,inc_rip=?,inc_cmp=?,inc_stab=?,inc_est=? WHERE id=? ");
	
			pst.setBigDecimal(1, punto.getIndicazione_iniziale());
			pst.setBigDecimal(2, punto.getIndicazione_iniziale_corr());
			pst.setBigDecimal(3, punto.getValore_nominale());
			pst.setBigDecimal(4,punto.getValore_andata_taratura());
			pst.setBigDecimal(5,punto.getValore_andata_campione());
			pst.setBigDecimal(6, punto.getValore_ritorno_taratura());
			pst.setBigDecimal(7, punto.getValore_ritorno_campione());
			pst.setBigDecimal(8,punto.getAndata_scostamento_campione());
			pst.setBigDecimal(9,punto.getAndata_correzione_campione());
			pst.setBigDecimal(10, punto.getRitorno_scostamento_campione());
			pst.setBigDecimal(11,punto.getRitorno_correzione_campione());
			pst.setBigDecimal(12, punto.getInclinazione_cmp_campione());
			pst.setBigDecimal(13,punto.getScostamentoA());
			pst.setBigDecimal(14,punto.getScostamentoB());
			pst.setBigDecimal(15,punto.getScostamentoMed());
			pst.setBigDecimal(16,punto.getScostamentoOff());
			pst.setBigDecimal(17, punto.getScarto_tipo());
			pst.setBigDecimal(18, punto.getInc_ris());
			pst.setBigDecimal(19, punto.getInc_rip());
			pst.setBigDecimal(20, punto.getInc_cmp());
			pst.setBigDecimal(21, punto.getInc_stab());
			pst.setBigDecimal(22, punto.getInc_est());
			pst.setInt(23, punto.getId());
			
			pst.execute();
		
		    
			  
		}catch(Exception ex)
		{
			ex.printStackTrace();
			throw ex;
		}
		finally
		{
			pst.close();
			con.close();
		}
		
	}
	
	public static ArrayList<ParametroTaraturaDTO> getListaParametriTaraturaSelezionato(String codCampione) throws Exception {
		Connection con=null;
		PreparedStatement pst=null;
		ResultSet rs =null;
		ArrayList<ParametroTaraturaDTO> toRet=new  ArrayList<ParametroTaraturaDTO>();
		ParametroTaraturaDTO parametroTar=null;
		try
		{
			con=getConnection();
			
			pst=con.prepareStatement("select * FROM tblCampioni WHERE codice=? ORDER BY valore_taratura");
					
			pst.setString(1, codCampione);	
			
			rs=pst.executeQuery();
			
			while(rs.next())
			{
				parametroTar= new ParametroTaraturaDTO();
				parametroTar.setDescrizioneParametro(rs.getString("parametri_taratura"));
				parametroTar.setDataScadenza(new SimpleDateFormat("yyyy-MM-dd").parse(rs.getString("data_scadenza")));
				parametroTar.setUm(rs.getString("um"));
				parametroTar.setUm_fond(rs.getString("um_fond"));
				parametroTar.setIncertezzaAssoluta(rs.getBigDecimal("incertezza_assoluta"));
				parametroTar.setIncertezzaRelativa(rs.getBigDecimal("incertezza_relativa"));
				parametroTar.setValore_nominale(rs.getBigDecimal("valore_nominale"));
				parametroTar.setValoreTaratura(rs.getBigDecimal("valore_taratura"));
				parametroTar.setScostamentoPrecedente(rs.getBigDecimal("valore_scostamento_precedente"));
				parametroTar.setInterpolazione(rs.getInt("interpolazione_permessa"));
				parametroTar.setRisoluzione(rs.getBigDecimal("divisione_unita_misura"));
				parametroTar.setTipoGrandezza(rs.getString("tipoGrandezza"));
				toRet.add(parametroTar);			
				
			}
			
		}catch (Exception e) {
			throw e;
		}finally
		{
			pst.close();
			con.close();
		}
		return toRet;
	}
	public static void insertCondizioniAmbientali(ArrayList<LatMassaAMB> listaValoriAmbientali, int idMisura) throws Exception {
		
		Connection con =null;
		PreparedStatement pst= null;

		try{
			con=getConnection();
			con.setAutoCommit(false);
			
			pst=con.prepareStatement("INSERT INTO lat_massa_amb(id_misura,data_ora,ch1_temperatura,ch2_temperatura," + 
					"ch3_temperatura,umidita,pressione,CH1_TEMPERATURA_CORR,CH2_TEMPERATURA_CORR,CH3_TEMPERATURA_CORR,UMIDITA_CORR,PRESSIONE_CORRETTA) VALUES(?,?,?,?,?,?,?,?,?,?,?,?)");
			
			LatMassaAMB data=null;
			for (int i=0;i<listaValoriAmbientali.size();i++) {
				
			data=listaValoriAmbientali.get(i);
			
			pst.setInt(1, idMisura);
			pst.setString(2,data.getData_ora());
			pst.setBigDecimal(3,data.getCh1_temperatura());
			pst.setBigDecimal(4,data.getCh2_temperatura());
			pst.setBigDecimal(5,data.getCh3_temperatura());
			pst.setBigDecimal(6,data.getUmidita());
			pst.setBigDecimal(7,data.getPressione());
			pst.setBigDecimal(8,data.getCh1_temperatura_corr());
			pst.setBigDecimal(9,data.getCh2_temperatura_corr());
			pst.setBigDecimal(10,data.getCh3_temperatura_corr());
			pst.setBigDecimal(11,data.getUmidita_corr());
			pst.setBigDecimal(12,data.getPressione_corretta());
		
			pst.execute();
			
			}
			con.commit();
			  
		}catch(Exception ex)
		{
			ex.printStackTrace();
			throw ex;
		}
		finally
		{
			pst.close();
			con.close();
		}
		
	}
	
	public static void insertCondizioniAmbientaliDati(LatMassaAMB_DATA datiCalcolati) throws Exception {
	
		Connection con =null;
		PreparedStatement pst= null;

		try{
			con=getConnection();
			con.setAutoCommit(false);
			
			pst=con.prepareStatement("INSERT INTO lat_massa_amb_data(id_misura,temperatura,umidita,pressione,INCERTEZZATEMPERATURA,INCERTEZZAUMINIDTA,INCERTEZZAPRESSIONE,MEDIA_TEMPERATURA,MEDIA_UMIDITA,MEDIA_PRESSIONE,"
																   +"MEDIA_TEMPERATURA_MARGINE,MEDIA_UMIDITA_MARGINE,MEDIA_PRESSIONE_MARGINE,DENSITA_ARIA_CIMP,DERIVATA_TEMPERATURA_CIMP,DERIVATA_PRESSIONE_CIMP,DERIVATA_UMIDITA_CIMP,"
																   +"INCERTEZZA_DENSITA_ARIA_CIMP,INCERTEZZA_FORM_DENSITA_ARIA_CIMP,DENSITA_ARIA_P0,DENSITA_ARIA,DELTA_TEMPERATURA,DELTA_UMIDITA,DELTA_PRESSIONE,INCERETZZA_SONDA_CAMPIONE,INCERTEZZA_SONDA_UMIDITA,INCERTEZZA_SONDA_PRESSIONE) "
																   + "VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			
			
			pst.setInt(1, datiCalcolati.getId_misura());
			pst.setBigDecimal(2,null);
			pst.setBigDecimal(3,null);
			pst.setBigDecimal(4,null);
			pst.setBigDecimal(5,datiCalcolati.getIncertezzaTemperatura());
			pst.setBigDecimal(6,datiCalcolati.getIncertezzaUminidta());
			pst.setBigDecimal(7,datiCalcolati.getIncertezzaPressione());
			pst.setBigDecimal(8,datiCalcolati.getMedia_temperatura());
			pst.setBigDecimal(9,datiCalcolati.getMedia_umidita());
			pst.setBigDecimal(10,datiCalcolati.getMedia_pressione());
			pst.setBigDecimal(11,datiCalcolati.getMedia_temperatura_margine());
			pst.setBigDecimal(12,datiCalcolati.getMedia_umidita_margine());
			pst.setBigDecimal(13,datiCalcolati.getMedia_pressione_margine());
			pst.setBigDecimal(14,datiCalcolati.getDensita_aria_cimp());
			pst.setBigDecimal(15,datiCalcolati.getDerivata_temperatura_cimp());
			pst.setBigDecimal(16,datiCalcolati.getDerivata_pressione_cimp());
			pst.setBigDecimal(17,datiCalcolati.getDerivata_umidita_cimp());
			pst.setBigDecimal(18,datiCalcolati.getIncertezza_densita_aria_cimp());
			pst.setBigDecimal(19,datiCalcolati.getIncertezza_form_densita_aria_cimp());
			pst.setBigDecimal(20,null);
			pst.setBigDecimal(21,datiCalcolati.getDensita_aria());
			pst.setBigDecimal(22,datiCalcolati.getDelta_temperatura());
			pst.setBigDecimal(23,datiCalcolati.getDelta_umidita());
			pst.setBigDecimal(24,datiCalcolati.getDelta_pressione());
			pst.setBigDecimal(25,null);
			pst.setBigDecimal(26,null);
			pst.setBigDecimal(27,null);
		
			pst.execute();
			con.commit();
			  
		}catch(Exception ex)
		{
			ex.printStackTrace();
			throw ex;
		}
		finally
		{
			pst.close();
			con.close();
		}
		
	}
	
	public static LatMassaAMB_DATA getCondizioniAmbientaliDati(int idMisura) throws Exception {
		
		Connection con =null;
		PreparedStatement pst= null;
		ResultSet rs;
		LatMassaAMB_DATA datiCalcolati;
		try{
			con=getConnection();
			
			pst=con.prepareStatement("SELECT * FROM lat_massa_amb_data WHERE ID_MISURA=?");
			
			pst.setInt(1, idMisura);
			
			rs= pst.executeQuery();
			
			datiCalcolati= new LatMassaAMB_DATA();
			
			
		while(rs.next()) 
		{	
			datiCalcolati.setId_misura(idMisura);
			datiCalcolati.setIncertezzaTemperatura(rs.getBigDecimal("INCERTEZZATEMPERATURA"));
			datiCalcolati.setIncertezzaUminidta(rs.getBigDecimal("INCERTEZZAUMINIDTA"));
			datiCalcolati.setIncertezzaPressione(rs.getBigDecimal("INCERTEZZAPRESSIONE"));
			datiCalcolati.setMedia_temperatura(rs.getBigDecimal("MEDIA_TEMPERATURA"));
			datiCalcolati.setMedia_umidita(rs.getBigDecimal("MEDIA_UMIDITA"));
			datiCalcolati.setMedia_pressione(rs.getBigDecimal("MEDIA_PRESSIONE"));
			datiCalcolati.setMedia_temperatura_margine(rs.getBigDecimal("MEDIA_TEMPERATURA_MARGINE"));
			datiCalcolati.setMedia_umidita_margine(rs.getBigDecimal("MEDIA_UMIDITA_MARGINE"));
			datiCalcolati.setMedia_pressione_margine(rs.getBigDecimal("MEDIA_PRESSIONE_MARGINE"));
			datiCalcolati.setDensita_aria_cimp(rs.getBigDecimal("DENSITA_ARIA_CIMP"));
		    datiCalcolati.setDerivata_temperatura_cimp(rs.getBigDecimal("DERIVATA_TEMPERATURA_CIMP"));
			datiCalcolati.setDerivata_pressione_cimp(rs.getBigDecimal("DERIVATA_PRESSIONE_CIMP"));
			datiCalcolati.setDerivata_umidita_cimp(rs.getBigDecimal("DERIVATA_UMIDITA_CIMP"));
			datiCalcolati.setIncertezza_densita_aria_cimp(rs.getBigDecimal("INCERTEZZA_DENSITA_ARIA_CIMP"));
		    datiCalcolati.setIncertezza_form_densita_aria_cimp(rs.getBigDecimal("INCERTEZZA_FORM_DENSITA_ARIA_CIMP"));
			datiCalcolati.setDensita_aria(rs.getBigDecimal("DENSITA_ARIA"));
			datiCalcolati.setDelta_temperatura(rs.getBigDecimal("DELTA_TEMPERATURA"));
			datiCalcolati.setDelta_umidita(rs.getBigDecimal("DELTA_UMIDITA"));
			datiCalcolati.setDelta_pressione(rs.getBigDecimal("DELTA_PRESSIONE"));

		}
			  
		}catch(Exception ex)
		{
			ex.printStackTrace();
			throw ex;
		}
		finally
		{
			pst.close();
			con.close();
		}
		
		return datiCalcolati;
		
	}
	
	
	public static void removeCondizioniAmbientali(int idMisura) throws Exception {
		
		Connection con =null;
		PreparedStatement pst= null;

		try{
			con=getConnection();
			pst=con.prepareStatement("DELETE FROM lat_massa_amb WHERE id_misura=?");
			pst.setInt(1, idMisura);
		
			pst.execute();
		
		    
			  
		}catch(Exception ex)
		{
			ex.printStackTrace();
			throw ex;
		}
		finally
		{
			pst.close();
			con.close();
		}
		
		
	}
	public static ArrayList<LatMassaAMB> getListaCondizioniAmbientali(int idMisura) throws Exception {
		Connection con=null;
		PreparedStatement pst=null;
		ResultSet rs =null;
		ArrayList<LatMassaAMB> toRet=new  ArrayList<LatMassaAMB>();
		LatMassaAMB conAmb=null;
		try
		{
			con=getConnection();
			
			pst=con.prepareStatement("select * FROM lat_massa_amb WHERE id_Misura=? ORDER BY id");
					
			pst.setInt(1, idMisura);	
			
			rs=pst.executeQuery();
			
			while(rs.next())
			{
				conAmb= new LatMassaAMB();
				
				conAmb.setId(rs.getInt("id"));
				conAmb.setId_misura(idMisura);
				conAmb.setData_ora(rs.getString("data_ora"));
				conAmb.setCh1_temperatura(rs.getBigDecimal("ch1_temperatura"));
				conAmb.setCh2_temperatura(rs.getBigDecimal("ch2_temperatura"));
				conAmb.setCh3_temperatura(rs.getBigDecimal("ch3_temperatura"));
				conAmb.setCh1_temperatura_corr(rs.getBigDecimal("ch1_temperatura_corr"));
				conAmb.setCh2_temperatura_corr(rs.getBigDecimal("ch2_temperatura_corr"));
				conAmb.setCh3_temperatura_corr(rs.getBigDecimal("ch3_temperatura_corr"));
				conAmb.setUmidita(rs.getBigDecimal("umidita"));
				conAmb.setUmidita_corr(rs.getBigDecimal("umidita_corr"));
				conAmb.setPressione(rs.getBigDecimal("pressione"));
				conAmb.setPressione_corretta(rs.getBigDecimal("pressione_corretta"));
				
				toRet.add(conAmb);			
				
			}
			
		}catch (Exception e) {
			throw e;
		}finally
		{
			pst.close();
			con.close();
		}
		return toRet;
	}
	public static ArrayList<LatMassaAMB_SONDE> getListaCorrezioniSondeLAT(int id_tipo) throws Exception {
		Connection con=null;
		PreparedStatement pst=null;
		ResultSet rs =null;
		ArrayList<LatMassaAMB_SONDE> toRet=new  ArrayList<LatMassaAMB_SONDE>();
		LatMassaAMB_SONDE corrSonda=null;
		try
		{
			con=getConnection();
			
			pst=con.prepareStatement("select * FROM lat_massa_amb_sonde WHERE ID_TIPO=? ORDER BY NUMERO");
			pst.setInt(1, id_tipo);		
			
			rs=pst.executeQuery();
			
			while(rs.next())
			{
				corrSonda= new LatMassaAMB_SONDE();
				
				corrSonda.setId_tipo(rs.getInt("ID_TIPO"));
				corrSonda.setNumero(rs.getInt("NUMERO"));
				corrSonda.setIndicazione(rs.getBigDecimal("INDICAZIONE"));
				corrSonda.setErrore(rs.getBigDecimal("ERRORE"));
				corrSonda.setReg_lin_m(rs.getBigDecimal("REG_LIN_M"));
				corrSonda.setReg_lin_q(rs.getBigDecimal("REG_LIN_Q"));
				
				toRet.add(corrSonda);			
				
			}
			
		}catch (Exception e) {
			throw e;
		}finally
		{
			pst.close();
			con.close();
		}
		return toRet;
	}
	public static void removeCondizioniAmbientaliDati(int idMisura) throws Exception {
	
		Connection con =null;
		PreparedStatement pst= null;

		try{
			con=getConnection();
			pst=con.prepareStatement("DELETE FROM lat_massa_amb_data WHERE id_misura=?");
			pst.setInt(1, idMisura);
		
			pst.execute();
		
		    
			  
		}catch(Exception ex)
		{
			ex.printStackTrace();
			throw ex;
		}
		finally
		{
			pst.close();
			con.close();
		}
	}
	public static void updateIndicazioniTestata(LatPuntoLivellaElettronicaDTO punto, int idMisura) throws Exception {
	
		Connection con =null;
		PreparedStatement pst= null;

		try{
			con=getConnection();			
			
			pst=con.prepareStatement("UPDATE lat_punto_livella_elettronica SET indicazione_iniziale=?," + 
					"indicazione_iniziale_corr=?,inclinazione_cmp_campione=? WHERE id_misura=? ");
	
			pst.setBigDecimal(1, punto.getIndicazione_iniziale());
			pst.setBigDecimal(2, punto.getIndicazione_iniziale_corr());
			pst.setBigDecimal(3, punto.getInclinazione_cmp_campione());
			pst.setInt(4,idMisura);
			
			pst.execute();
		
		    
			  
		}catch(Exception ex)
		{
			ex.printStackTrace();
			throw ex;
		}
		finally
		{
			pst.close();
			con.close();
		}
		
	}
	public static int getIdTabellaIncertezza(int idMisura, String idPunto) throws Exception {
		Connection con =null;
		PreparedStatement pst= null;
		ResultSet rs=null;

		try{
			con=getConnection();			
			
			pst=con.prepareStatement("SELECT id FROM lat_punto_livella_elettronica WHERE id_misura=? AND punto=? AND tipo_prova=\'I\'");
			
			pst.setInt(1, idMisura);
			pst.setInt(2, Integer.parseInt(idPunto));
			
			rs=pst.executeQuery();
		
		    while(rs.next()) 
		    {
		    	return rs.getInt("id");
		    }
			  
		}catch(Exception ex)
		{
			ex.printStackTrace();
			throw ex;
		}
		finally
		{
			pst.close();
			con.close();
		}
		return 0;
	}
	public static ArrayList<LatMassaClasseDTO> getListaClassi() throws Exception {
		
		Connection con =null;
		PreparedStatement pst= null;
		ResultSet rs=null;
		ArrayList<LatMassaClasseDTO> listaClassi = new ArrayList<LatMassaClasseDTO>();
		try{
			con=getConnection();			
			
			pst=con.prepareStatement("SELECT * FROM lat_massa_classe");
			rs=pst.executeQuery();
		
			LatMassaClasseDTO classe;
		    while(rs.next()) 
		    {
		    	classe = new LatMassaClasseDTO();
		    	classe.setId(rs.getInt("id"));
		    	classe.setVal_nominale(rs.getString("val_nominale"));
		    	classe.setMg(rs.getInt("mg"));
		    	classe.setDens_min(rs.getInt("dens_min"));
		    	classe.setDens_max(rs.getInt("dens_max"));
		    	
		    	listaClassi.add(classe);
		    }
			  
		}catch(Exception ex)
		{
			ex.printStackTrace();
			throw ex;
		}
		finally
		{
			pst.close();
			con.close();
		}
		return listaClassi;
	}
	public static ArrayList<LatMassaScartiTipo> getListaScartiTipo() throws Exception {
		Connection con =null;
		PreparedStatement pst= null;
		ResultSet rs=null;
		ArrayList<LatMassaScartiTipo> listaScarti = new ArrayList<LatMassaScartiTipo>();
		try{
			con=getConnection();			
			
			pst=con.prepareStatement("SELECT * FROM lat_massa_scarti_tipo");
			rs=pst.executeQuery();
		
			LatMassaScartiTipo scarto;
		    while(rs.next()) 
		    {
		    	scarto = new LatMassaScartiTipo();
		    	scarto.setId(rs.getInt("id"));
		    	scarto.setDescrizione(rs.getString("descrizione"));
		    	scarto.setScarto(rs.getBigDecimal("scarto"));
		    	scarto.setIncertezzaScarto(rs.getBigDecimal("uf"));
		    	scarto.setGradi_liberta(rs.getInt("gradi_lib"));
		    	
		    	
		    	listaScarti.add(scarto);
		    }
			  
		}catch(Exception ex)
		{
			ex.printStackTrace();
			throw ex;
		}
		finally
		{
			pst.close();
			con.close();
		}
		return listaScarti;
	}
	public static void insertEffettoMagnetico(LatMassaEffMag effMag) throws Exception {
	
		Connection con =null;
		PreparedStatement pst= null;

		try{
			con=getConnection();
			con.setAutoCommit(false);
			
			pst=con.prepareStatement("INSERT INTO lat_massa_eff_mag(id_misura,comparatore,campione,valore_nominale_campione,classe_OIML,segno_distintivo,eff_mag_L1,eff_mag_L2,eff_mag_esito,mc,uMc,"
																   +"classe_campione,classe_campione_u,classe_campione_min,classe_campione_pc,classe_campione_max,"
																   +"classe_taratura,classe_taratura_u,classe_taratura_min,classe_taratura_pc,classe_taratura_max) "
																   + "VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			
			
			pst.setInt(1, effMag.getId_misura());
			pst.setString(2,effMag.getComparatore());
			pst.setString(3,effMag.getCampione());
			pst.setString(4,effMag.getValore_nominale_campione());
			pst.setString(5,effMag.getClasseOiml());
			pst.setString(6,effMag.getSegno_distintivo());
			pst.setBigDecimal(7,effMag.getEff_mag_L1());
			pst.setBigDecimal(8,effMag.getEff_mag_L2());
			pst.setString(9,effMag.getEff_mag_esito());
			pst.setBigDecimal(10,effMag.getMc());
			pst.setBigDecimal(11,effMag.getuMc());
			pst.setString(12,effMag.getClasse_campione());
			pst.setBigDecimal(13,effMag.getClasse_campione_u());
			pst.setBigDecimal(14,effMag.getClasse_campione_min());
			pst.setBigDecimal(15,effMag.getClasse_campione_pc());
			pst.setBigDecimal(16,effMag.getClasse_campione_max());
			pst.setString(17,effMag.getClasse_taratura());
			pst.setBigDecimal(18,effMag.getClasse_taratura_u());
			pst.setBigDecimal(19,effMag.getClasse_taratura_min());
			pst.setBigDecimal(20,effMag.getClasse_taratura_pc());
			pst.setBigDecimal(21,effMag.getClasse_taratura_max());
			pst.execute();
			con.commit();
			  
		}catch(Exception ex)
		{
			ex.printStackTrace();
			throw ex;
		}
		finally
		{
			pst.close();
			con.close();
		}
		
	}
	public static ArrayList<LatMassaEffMag> getListaEffettoMagnetico(int idMisura) throws Exception {
		Connection con=null;
		PreparedStatement pst=null;
		ResultSet rs =null;
		ArrayList<LatMassaEffMag> toRet=new  ArrayList<LatMassaEffMag>();
		
		LatMassaEffMag effetto=null;
		try
		{
			con=getConnection();
			
			pst=con.prepareStatement("select * FROM lat_massa_eff_Mag WHERE id_misura=? ORDER BY ID ASC ");
			pst.setInt(1, idMisura);		
			
			rs=pst.executeQuery();
			
			while(rs.next())
			{
				effetto= new LatMassaEffMag();
				
				effetto.setId_misura(idMisura);
				effetto.setComparatore(rs.getString("comparatore"));
				effetto.setCampione(rs.getString("campione"));
				effetto.setValore_nominale_campione(rs.getString("valore_nominale_campione"));
				effetto.setClasseOiml(rs.getString("classe_OIML"));
				effetto.setSegno_distintivo(rs.getString("segno_distintivo"));
				effetto.setEff_mag_L1(rs.getBigDecimal("eff_mag_L1"));
				effetto.setEff_mag_L2(rs.getBigDecimal("eff_mag_L2"));
				effetto.setEff_mag_esito(rs.getString("eff_mag_esito"));
				effetto.setMc(rs.getBigDecimal("mc"));
				effetto.setuMc(rs.getBigDecimal("uMc"));
				effetto.setClasse_campione(rs.getString("classe_campione"));
				effetto.setClasse_campione_u(rs.getBigDecimal("classe_campione_u"));
				effetto.setClasse_campione_min(rs.getBigDecimal("classe_campione_min"));
				effetto.setClasse_campione_pc(rs.getBigDecimal("classe_campione_pc"));
				effetto.setClasse_campione_max(rs.getBigDecimal("classe_campione_max"));
				effetto.setClasse_taratura(rs.getString("classe_taratura"));
				effetto.setClasse_taratura_u(rs.getBigDecimal("classe_taratura_u"));
				effetto.setClasse_taratura_min(rs.getBigDecimal("classe_taratura_min"));
				effetto.setClasse_taratura_pc(rs.getBigDecimal("classe_taratura_pc"));
				effetto.setClasse_taratura_max(rs.getBigDecimal("classe_taratura_max"));
				
				toRet.add(effetto);			
				
			}
			
		}catch (Exception e) {
			throw e;
		}finally
		{
			pst.close();
			con.close();
		}
		return toRet;
	}
	public static int getRipetizioneMasse(int idMisura) throws Exception {
		Connection con =null;
		PreparedStatement pst= null;
		ResultSet rs=null;
		int ripetizione=0;
		try{
			con=getConnection();			
			
			pst=con.prepareStatement("SELECT Max(ripetizione) FROM lat_massa_data WHERE id_misura=?");
			pst.setInt(1, idMisura);
			rs=pst.executeQuery();
		
		    while(rs.next()) 
		    {
		    	ripetizione=rs.getInt(1);
		    }
			  
		}catch(Exception ex)
		{
			ex.printStackTrace();
			throw ex;
		}
		finally
		{
			pst.close();
			con.close();
		}
		return ripetizione;
	}

}
