package it.calverDesktopLAT.dto;

import java.math.BigDecimal;

public class LatMassaScartiTipo {

	private int id;
	private String descrizione;
	private BigDecimal scarto;
	private BigDecimal incertezzaScarto;
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
	public BigDecimal getScarto() {
		return scarto;
	}
	public void setScarto(BigDecimal scarto) {
		this.scarto = scarto;
	}
	public BigDecimal getIncertezzaScarto() {
		return incertezzaScarto;
	}
	public void setIncertezzaScarto(BigDecimal incertezzaScarto) {
		this.incertezzaScarto = incertezzaScarto;
	}
	
	
	
}
