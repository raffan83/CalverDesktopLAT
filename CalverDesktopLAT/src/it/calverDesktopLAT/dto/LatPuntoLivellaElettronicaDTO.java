package it.calverDesktopLAT.dto;

import java.math.BigDecimal;

public class LatPuntoLivellaElettronicaDTO {
	
	private int id;
	private int id_misura;
	private int punto; 
	private String tipo_prova;
	private int numero_prova; 
	private BigDecimal indicazione_iniziale;
	private BigDecimal indicazione_iniziale_corr;
	private BigDecimal valore_nominale; 
	private BigDecimal valore_andata_taratura; 
	private BigDecimal valore_andata_campione; 
	private BigDecimal valore_ritorno_taratura; 
	private BigDecimal valore_ritorno_campione; 
	private BigDecimal andata_scostamento_campione;
	private BigDecimal andata_correzione_campione;
	private BigDecimal ritorno_scostamento_campione;
	private BigDecimal ritorno_correzione_campione;
	private BigDecimal inclinazione_cmp_campione;
	private BigDecimal scarto_tipo;
	private BigDecimal scostamentoA; 
	private BigDecimal scostamentoB;
	private BigDecimal scostamentoMed;
	private BigDecimal scostamentoOff;
	private BigDecimal inc_ris;
	private BigDecimal inc_rip;
	private BigDecimal inc_cmp;
	private BigDecimal inc_stab;
	private BigDecimal inc_est;

	
	
	
	public BigDecimal getAndata_correzione_campione() {
		return andata_correzione_campione;
	}
	public void setAndata_correzione_campione(BigDecimal andata_correzione_campione) {
		this.andata_correzione_campione = andata_correzione_campione;
	}
	public BigDecimal getRitorno_correzione_campione() {
		return ritorno_correzione_campione;
	}
	public void setRitorno_correzione_campione(BigDecimal ritorno_correzione_campione) {
		this.ritorno_correzione_campione = ritorno_correzione_campione;
	}
	public BigDecimal getScostamentoA() {
		return scostamentoA;
	}
	public void setScostamentoA(BigDecimal scostamentoA) {
		this.scostamentoA = scostamentoA;
	}
	public BigDecimal getScostamentoB() {
		return scostamentoB;
	}
	public void setScostamentoB(BigDecimal scostamentoB) {
		this.scostamentoB = scostamentoB;
	}
	public BigDecimal getScostamentoMed() {
		return scostamentoMed;
	}
	public void setScostamentoMed(BigDecimal scostamentoMed) {
		this.scostamentoMed = scostamentoMed;
	}
	public BigDecimal getScostamentoOff() {
		return scostamentoOff;
	}
	public void setScostamentoOff(BigDecimal scostamentoOff) {
		this.scostamentoOff = scostamentoOff;
	}
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
	public int getPunto() {
		return punto;
	}
	public void setPunto(int punto) {
		this.punto = punto;
	}
	public String getTipo_prova() {
		return tipo_prova;
	}
	public void setTipo_prova(String tipo_prova) {
		this.tipo_prova = tipo_prova;
	}
	public int getNumero_prova() {
		return numero_prova;
	}
	public void setNumero_prova(int numero_prova) {
		this.numero_prova = numero_prova;
	}
	public BigDecimal getIndicazione_iniziale() {
		return indicazione_iniziale;
	}
	public void setIndicazione_iniziale(BigDecimal indicazione_iniziale) {
		this.indicazione_iniziale = indicazione_iniziale;
	}
	public BigDecimal getIndicazione_iniziale_corr() {
		return indicazione_iniziale_corr;
	}
	public void setIndicazione_iniziale_corr(BigDecimal indicazione_iniziale_corr) {
		this.indicazione_iniziale_corr = indicazione_iniziale_corr;
	}
	public BigDecimal getValore_nominale() {
		return valore_nominale;
	}
	public void setValore_nominale(BigDecimal valore_nominale) {
		this.valore_nominale = valore_nominale;
	}
	public BigDecimal getValore_andata_taratura() {
		return valore_andata_taratura;
	}
	public void setValore_andata_taratura(BigDecimal valore_andata_taratura) {
		this.valore_andata_taratura = valore_andata_taratura;
	}
	public BigDecimal getValore_andata_campione() {
		return valore_andata_campione;
	}
	public void setValore_andata_campione(BigDecimal valore_andata_campione) {
		this.valore_andata_campione = valore_andata_campione;
	}
	public BigDecimal getValore_ritorno_taratura() {
		return valore_ritorno_taratura;
	}
	public void setValore_ritorno_taratura(BigDecimal valore_ritorno_taratura) {
		this.valore_ritorno_taratura = valore_ritorno_taratura;
	}
	public BigDecimal getValore_ritorno_campione() {
		return valore_ritorno_campione;
	}
	public void setValore_ritorno_campione(BigDecimal valore_ritorno_campione) {
		this.valore_ritorno_campione = valore_ritorno_campione;
	}
	public BigDecimal getAndata_scostamento_campione() {
		return andata_scostamento_campione;
	}
	public void setAndata_scostamento_campione(BigDecimal andata_scostamento_campione) {
		this.andata_scostamento_campione = andata_scostamento_campione;
	}
	public BigDecimal getRitorno_scostamento_campione() {
		return ritorno_scostamento_campione;
	}
	public void setRitorno_scostamento_campione(BigDecimal ritorno_scostamento_campione) {
		this.ritorno_scostamento_campione = ritorno_scostamento_campione;
	}
	public BigDecimal getInclinazione_cmp_campione() {
		return inclinazione_cmp_campione;
	}
	public void setInclinazione_cmp_campione(BigDecimal inclinazione_cmp_campione) {
		this.inclinazione_cmp_campione = inclinazione_cmp_campione;
	}
	public BigDecimal getScarto_tipo() {
		return scarto_tipo;
	}
	public void setScarto_tipo(BigDecimal scarto_tipo) {
		this.scarto_tipo = scarto_tipo;
	}
	public BigDecimal getInc_ris() {
		return inc_ris;
	}
	public void setInc_ris(BigDecimal inc_ris) {
		this.inc_ris = inc_ris;
	}
	public BigDecimal getInc_rip() {
		return inc_rip;
	}
	public void setInc_rip(BigDecimal inc_rip) {
		this.inc_rip = inc_rip;
	}
	public BigDecimal getInc_cmp() {
		return inc_cmp;
	}
	public void setInc_cmp(BigDecimal inc_cmp) {
		this.inc_cmp = inc_cmp;
	}
	public BigDecimal getInc_stab() {
		return inc_stab;
	}
	public void setInc_stab(BigDecimal inc_stab) {
		this.inc_stab = inc_stab;
	}
	public BigDecimal getInc_est() {
		return inc_est;
	}
	public void setInc_est(BigDecimal inc_est) {
		this.inc_est = inc_est;
	}

	
}
