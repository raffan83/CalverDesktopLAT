package it.calverDesktopLAT.dto;

import java.util.ArrayList;

public class TabellaMisureDTO {

	
	String tipoProva;
	int id_tabella;
	ArrayList<MisuraDTO> listaMisure = new ArrayList<>();
	
	public String getTipoProva() {
		return tipoProva;
	}
	public void setTipoProva(String tipoProva) {
		this.tipoProva = tipoProva;
	}
	public int getId_tabella() {
		return id_tabella;
	}
	public void setId_tabella(int id_tabella) {
		this.id_tabella = id_tabella;
	}
	public ArrayList<MisuraDTO> getListaMisure() {
		return listaMisure;
	}
	public void setListaMisure(ArrayList<MisuraDTO> listaMisure) {
		this.listaMisure = listaMisure;
	}
	
	@Override
	public String toString() {
		
		String[] parent=this.getTipoProva().split("_");
		
		String cal="";
		
		if(this.getListaMisure().get(0).getCalibrazione()!=null &&
				!this.getListaMisure().get(0).getCalibrazione().equals(""))
		{
			cal="("+this.getListaMisure().get(0).getCalibrazione()+")";
		}
		
		if(parent[0].equals("L"))
		{
			return "Linearità "+parent[1]+" P."+cal;
		}
		
		if (parent[0].equals("R"))
		{
			return "Ripetibilità "+parent[2]+" P. x "+parent[1]+" R."+cal;
		}
		if(parent[0].equals("RDP")) 
		{
			return "Prova RDP";
		}
		return "NO CONTENT";
	}
}
