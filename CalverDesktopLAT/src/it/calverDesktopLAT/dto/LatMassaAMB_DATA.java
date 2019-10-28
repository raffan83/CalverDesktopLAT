package it.calverDesktopLAT.dto;

import java.math.BigDecimal;

public class LatMassaAMB_DATA {
	
	private int id;
	private int id_misura;
	
	private BigDecimal temperatura;
	private BigDecimal umidita;
	private BigDecimal pressione;
	
	private BigDecimal incertezzaTemperatura;
	private BigDecimal incertezzaUminidta;
	private BigDecimal incertezzaPressione;
	
	private BigDecimal media_temperatura;
	private BigDecimal media_umidita;
	private BigDecimal media_pressione;
	
	private BigDecimal media_temperatura_margine;
	private BigDecimal media_umidita_margine;
	private BigDecimal media_pressione_margine;
	
	private BigDecimal densita_aria_cimp;
	private BigDecimal derivata_temperatura_cimp;
	private BigDecimal derivata_pressione_cimp;
	private BigDecimal derivata_umidita_cimp;
	private BigDecimal incertezza_densita_aria_cimp;
	private BigDecimal incertezza_form_densita_aria_cimp;
	
	private BigDecimal densita_aria_p0;
	private BigDecimal densita_aria;
	private BigDecimal delta_temperatura;
	private BigDecimal delta_umidita;
	private BigDecimal delta_pressione;
	
	private BigDecimal inceretzza_sonda_campione;
	private BigDecimal incertezza_sonda_umidita;
	private BigDecimal incertezza_sonda_pressione;
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
	public BigDecimal getTemperatura() {
		return temperatura;
	}
	public void setTemperatura(BigDecimal temperatura) {
		this.temperatura = temperatura;
	}
	public BigDecimal getUmidita() {
		return umidita;
	}
	public void setUmidita(BigDecimal umidita) {
		this.umidita = umidita;
	}
	public BigDecimal getPressione() {
		return pressione;
	}
	public void setPressione(BigDecimal pressione) {
		this.pressione = pressione;
	}
	public BigDecimal getIncertezzaTemperatura() {
		return incertezzaTemperatura;
	}
	public void setIncertezzaTemperatura(BigDecimal incertezzaTemperatura) {
		this.incertezzaTemperatura = incertezzaTemperatura;
	}
	public BigDecimal getIncertezzaUminidta() {
		return incertezzaUminidta;
	}
	public void setIncertezzaUminidta(BigDecimal incertezzaUminidta) {
		this.incertezzaUminidta = incertezzaUminidta;
	}
	public BigDecimal getIncertezzaPressione() {
		return incertezzaPressione;
	}
	public void setIncertezzaPressione(BigDecimal incertezzaPressione) {
		this.incertezzaPressione = incertezzaPressione;
	}
	public BigDecimal getMedia_temperatura() {
		return media_temperatura;
	}
	public void setMedia_temperatura(BigDecimal media_temperatura) {
		this.media_temperatura = media_temperatura;
	}
	public BigDecimal getMedia_umidita() {
		return media_umidita;
	}
	public void setMedia_umidita(BigDecimal media_umidita) {
		this.media_umidita = media_umidita;
	}
	public BigDecimal getMedia_pressione() {
		return media_pressione;
	}
	public void setMedia_pressione(BigDecimal media_pressione) {
		this.media_pressione = media_pressione;
	}
	public BigDecimal getMedia_temperatura_margine() {
		return media_temperatura_margine;
	}
	public void setMedia_temperatura_margine(BigDecimal media_temperatura_margine) {
		this.media_temperatura_margine = media_temperatura_margine;
	}
	public BigDecimal getMedia_umidita_margine() {
		return media_umidita_margine;
	}
	public void setMedia_umidita_margine(BigDecimal media_umidita_margine) {
		this.media_umidita_margine = media_umidita_margine;
	}
	public BigDecimal getMedia_pressione_margine() {
		return media_pressione_margine;
	}
	public void setMedia_pressione_margine(BigDecimal media_pressione_margine) {
		this.media_pressione_margine = media_pressione_margine;
	}
	public BigDecimal getDensita_aria_cimp() {
		return densita_aria_cimp;
	}
	public void setDensita_aria_cimp(BigDecimal densita_aria_cimp) {
		this.densita_aria_cimp = densita_aria_cimp;
	}
	public BigDecimal getDerivata_temperatura_cimp() {
		return derivata_temperatura_cimp;
	}
	public void setDerivata_temperatura_cimp(BigDecimal devivata_temperatura_cimp) {
		this.derivata_temperatura_cimp = devivata_temperatura_cimp;
	}
	public BigDecimal getDerivata_pressione_cimp() {
		return derivata_pressione_cimp;
	}
	public void setDerivata_pressione_cimp(BigDecimal derivata_pressione_cimp) {
		this.derivata_pressione_cimp = derivata_pressione_cimp;
	}
	public BigDecimal getDerivata_umidita_cimp() {
		return derivata_umidita_cimp;
	}
	public void setDerivata_umidita_cimp(BigDecimal derivata_umidita_cimp) {
		this.derivata_umidita_cimp = derivata_umidita_cimp;
	}
	public BigDecimal getIncertezza_densita_aria_cimp() {
		return incertezza_densita_aria_cimp;
	}
	public void setIncertezza_densita_aria_cimp(BigDecimal incertezza_densita_aria_cimp) {
		this.incertezza_densita_aria_cimp = incertezza_densita_aria_cimp;
	}
	public BigDecimal getIncertezza_form_densita_aria_cimp() {
		return incertezza_form_densita_aria_cimp;
	}
	public void setIncertezza_form_densita_aria_cimp(BigDecimal incertezza_form_densita_aria_cimp) {
		this.incertezza_form_densita_aria_cimp = incertezza_form_densita_aria_cimp;
	}
	public BigDecimal getDensita_aria_p0() {
		return densita_aria_p0;
	}
	public void setDensita_aria_p0(BigDecimal densita_aria_p0) {
		this.densita_aria_p0 = densita_aria_p0;
	}
	public BigDecimal getDensita_aria() {
		return densita_aria;
	}
	public void setDensita_aria(BigDecimal densita_aria) {
		this.densita_aria = densita_aria;
	}
	public BigDecimal getDelta_temperatura() {
		return delta_temperatura;
	}
	public void setDelta_temperatura(BigDecimal delta_temperatura) {
		this.delta_temperatura = delta_temperatura;
	}
	public BigDecimal getDelta_umidita() {
		return delta_umidita;
	}
	public void setDelta_umidita(BigDecimal delta_umidita) {
		this.delta_umidita = delta_umidita;
	}
	public BigDecimal getDelta_pressione() {
		return delta_pressione;
	}
	public void setDelta_pressione(BigDecimal delta_pressione) {
		this.delta_pressione = delta_pressione;
	}
	public BigDecimal getInceretzza_sonda_campione() {
		return inceretzza_sonda_campione;
	}
	public void setInceretzza_sonda_campione(BigDecimal inceretzza_sonda_campione) {
		this.inceretzza_sonda_campione = inceretzza_sonda_campione;
	}
	public BigDecimal getIncertezza_sonda_umidita() {
		return incertezza_sonda_umidita;
	}
	public void setIncertezza_sonda_umidita(BigDecimal incertezza_sonda_umidita) {
		this.incertezza_sonda_umidita = incertezza_sonda_umidita;
	}
	public BigDecimal getIncertezza_sonda_pressione() {
		return incertezza_sonda_pressione;
	}
	public void setIncertezza_sonda_pressione(BigDecimal incertezza_sonda_pressione) {
		this.incertezza_sonda_pressione = incertezza_sonda_pressione;
	}
	

}
