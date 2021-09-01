package it.calverDesktopLAT.bo;

import java.sql.SQLException;

import it.calverDesktopLAT.dao.SQLiteDAO;

public class GestioneDB {

	public static String[] getDatiTabelle() throws SQLException, ClassNotFoundException{
		
		return SQLiteDAO.listaTabelleDB();
	}

	public static String[] getListaColonne(String tabella) throws ClassNotFoundException, SQLException {
		
		return SQLiteDAO.getListaColonne(tabella);
	}

	public static Object[][] getPlayLoad(int length, String tableName) throws Exception {
		// TODO Auto-generated method stub
		return SQLiteDAO.getPlayLoad(length,tableName);
	}

	public static boolean checkFile(String pATH_DB) {
		
		return SQLiteDAO.checkExecute(pATH_DB);
	}

	
	public static void chiudiMisura(String pATH_DB) throws Exception {
		
			SQLiteDAO.chiudiMisura(pATH_DB);
	}

	public static boolean controlloMisuraCertificato() throws Exception {
		
		return SQLiteDAO.controllaMisuraCertificato();
		
	}

}
