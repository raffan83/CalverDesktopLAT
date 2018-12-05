package it.calverDesktopLAT.dto;

public class ClassificazioneDTO {
	
	private int id;
	private String descrizione;
	public ClassificazioneDTO(int i, String string) {
		
		id=i;
		descrizione=string;
	}
	public ClassificazioneDTO(){};
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
