package it.calverDesktopLAT.dto;

public class LuogoVerificaDTO {
	private int id;
	private String descrizione="";
	
	public LuogoVerificaDTO(int id, String descrizione) {
		super();
		this.id=id;
		this.descrizione = descrizione;
	}
	public LuogoVerificaDTO(){}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getDescrizione() {
		return descrizione;
	}
	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

}
