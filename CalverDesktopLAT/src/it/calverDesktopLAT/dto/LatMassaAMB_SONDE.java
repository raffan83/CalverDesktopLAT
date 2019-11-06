package it.calverDesktopLAT.dto;

import java.math.BigDecimal;

public class LatMassaAMB_SONDE {

	private int id;
	private int id_tipo;
	private int numero;
	private BigDecimal indicazione;
	private BigDecimal errore;
	private BigDecimal reg_lin_m;
	private BigDecimal reg_lin_q;
	
	

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public BigDecimal getReg_lin_m() {
		return reg_lin_m;
	}
	public void setReg_lin_m(BigDecimal reg_lin_m) {
		this.reg_lin_m = reg_lin_m;
	}
	public BigDecimal getReg_lin_q() {
		return reg_lin_q;
	}
	public void setReg_lin_q(BigDecimal reg_lin_q) {
		this.reg_lin_q = reg_lin_q;
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
	
	public BigDecimal getIndicazione() {
		return indicazione;
	}
	public void setIndicazione(BigDecimal indicazione) {
		this.indicazione = indicazione;
	}
	public BigDecimal getErrore() {
		return errore;
	}
	public void setErrore(BigDecimal errore) {
		this.errore = errore;
	}
	
	
}
