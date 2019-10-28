package it.calverDesktopLAT.dto;

import java.math.BigDecimal;

public class LatMassaAMB_SONDE {

	private int id;
	private int id_misura;
	private int id_tipo;
	private int numero;
	private BigDecimal temperatura;
	private BigDecimal errore;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getId_misura() {
		return id_misura;
	}
	public void setId_misura(int id_misura) {
		this.id_misura = id_misura;
	}
	public int getId_tipo() {
		return id_tipo;
	}
	public void setId_tipo(int id_tipo) {
		this.id_tipo = id_tipo;
	}
	public int getNumero() {
		return numero;
	}
	public void setNumero(int numero) {
		this.numero = numero;
	}
	public BigDecimal getTemperatura() {
		return temperatura;
	}
	public void setTemperatura(BigDecimal temperatura) {
		this.temperatura = temperatura;
	}
	public BigDecimal getErrore() {
		return errore;
	}
	public void setErrore(BigDecimal errore) {
		this.errore = errore;
	}
	
	
}
