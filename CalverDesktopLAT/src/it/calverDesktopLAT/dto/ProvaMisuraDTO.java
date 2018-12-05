package it.calverDesktopLAT.dto;

import java.util.ArrayList;
import java.util.Date;

public class ProvaMisuraDTO {
	public int idStrumento;
	public Date dataMisura;
	public int idMisura;
	
	public ArrayList<TabellaMisureDTO> listaTabelle=new ArrayList<>();
	
	public ProvaMisuraDTO(int _idMisura) {
		
		idMisura=_idMisura;
	}
	public ProvaMisuraDTO() {
		// TODO Auto-generated constructor stub
	}
	public int getIdMisura() {
		return idMisura;
	}
	public void setIdMisura(int idMisura) {
		this.idMisura = idMisura;
	}
	
	
	public int getIdStrumento() {
		return idStrumento;
	}
	public void setIdStrumento(int idStrumento) {
		this.idStrumento = idStrumento;
	}
	public Date getDataMisura() {
		return dataMisura;
	}
	public void setDataMisura(Date dataMisura) {
		this.dataMisura = dataMisura;
	}
	public ArrayList<TabellaMisureDTO> getListaTabelle() {
		return listaTabelle;
	}
	public void setListaTabelle(ArrayList<TabellaMisureDTO> listaTabelle) {
		this.listaTabelle = listaTabelle;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "MISURA " +idMisura;
	}
	

}
