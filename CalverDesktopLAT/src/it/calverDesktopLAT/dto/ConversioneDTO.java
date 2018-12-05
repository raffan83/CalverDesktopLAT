package it.calverDesktopLAT.dto;

import java.math.BigDecimal;

public class ConversioneDTO {

	private boolean validita;
	private BigDecimal fattoreConversione;
	private double potenza;
	private BigDecimal valoreConvertito;
	private BigDecimal incertezzaConvertita;
	private BigDecimal valoreMinScalaCanvertito;
	private BigDecimal valoreMaxScalaCanvertito;
	private double grado;
	
	public double getGrado() {
		return grado;
	}
	public void setGrado(double grado) {
		this.grado = grado;
	}
	public boolean isValidita() {
		return validita;
	}
	public void setValidita(boolean validita) {
		this.validita = validita;
	}
	public BigDecimal getFattoreConversione() {
		return fattoreConversione;
	}
	public void setFattoreConversione(BigDecimal fattoreConversione) {
		this.fattoreConversione = fattoreConversione;
	}
	public double getPotenza() {
		return potenza;
	}
	public void setPotenza(double potenza) {
		this.potenza = potenza;
	}
	public BigDecimal getValoreConvertito() {
		return valoreConvertito;
	}
	public void setValoreConvertito(BigDecimal valoreConvertito) {
		this.valoreConvertito = valoreConvertito;
	}
	public BigDecimal getIncertezzaConvertita() {
		return incertezzaConvertita;
	}
	public void setIncertezzaConvertita(BigDecimal incertezzaConvertita) {
		this.incertezzaConvertita = incertezzaConvertita;
	}
	public BigDecimal getValoreMinScalaCanvertito() {
		return valoreMinScalaCanvertito;
	}
	public void setValoreMinScalaCanvertito(BigDecimal valoreMinScalaCanvertito) {
		this.valoreMinScalaCanvertito = valoreMinScalaCanvertito;
	}
	public BigDecimal getValoreMaxScalaCanvertito() {
		return valoreMaxScalaCanvertito;
	}
	public void setValoreMaxScalaCanvertito(BigDecimal valoreMaxScalaCanvertito) {
		this.valoreMaxScalaCanvertito = valoreMaxScalaCanvertito;
	}
	
	
}
