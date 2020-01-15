package it.calverDesktopLAT.dto;

import java.math.BigDecimal;
import java.util.Date;

public class LatMisuraDTO {
	
	private int id;
	private Date data_misura;
	private BigDecimal incertezza_rif;
	private BigDecimal incertezza_sec;
	private BigDecimal incertezza_estesa;
	private BigDecimal incertezza_estesa_sec;
	private BigDecimal incertezza_media;
	private BigDecimal campo_misura;
	private BigDecimal unita_formato;
	private BigDecimal campo_misura_sec;
	private BigDecimal sensibilita;
	private String stato;
	private String ammaccature;
	private String bolla_trasversale;
	private String regolazione;
	private String centraggio;
	private String nCertificato;
	private BigDecimal temperatura;
	private BigDecimal umidita;
	private String note;
	private String rif_campione;
	private String rif_campione_lavoro;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Date getData_misura() {
		return data_misura;
	}
	public void setData_misura(Date data_misura) {
		this.data_misura = data_misura;
	}
	public BigDecimal getIncertezza_rif() {
		return incertezza_rif;
	}
	public void setIncertezza_rif(BigDecimal incertezza_rif) {
		this.incertezza_rif = incertezza_rif;
	}
	public BigDecimal getIncertezza_sec() {
		return incertezza_sec;
	}
	public void setIncertezza_sec(BigDecimal incertezza_sec) {
		this.incertezza_sec = incertezza_sec;
	}
	public BigDecimal getIncertezza_estesa() {
		return incertezza_estesa;
	}
	public void setIncertezza_estesa(BigDecimal incertezza_estesa) {
		this.incertezza_estesa = incertezza_estesa;
	}
	public BigDecimal getIncertezza_estesa_sec() {
		return incertezza_estesa_sec;
	}
	public void setIncertezza_estesa_sec(BigDecimal incertezza_estesa_sec) {
		this.incertezza_estesa_sec = incertezza_estesa_sec;
	}
	public BigDecimal getIncertezza_media() {
		return incertezza_media;
	}
	public void setIncertezza_media(BigDecimal incertezza_media) {
		this.incertezza_media = incertezza_media;
	}
	public BigDecimal getCampo_misura() {
		return campo_misura;
	}
	public void setCampo_misura(BigDecimal campo_misura) {
		this.campo_misura = campo_misura;
	}
	
	
	public BigDecimal getUnita_formato() {
		return unita_formato;
	}
	public void setUnita_formato(BigDecimal unita_formato) {
		this.unita_formato = unita_formato;
	}
	public BigDecimal getCampo_misura_sec() {
		return campo_misura_sec;
	}
	public void setCampo_misura_sec(BigDecimal campo_misura_sec) {
		this.campo_misura_sec = campo_misura_sec;
	}
	public BigDecimal getSensibilita() {
		return sensibilita;
	}
	public void setSensibilita(BigDecimal sensibilita) {
		this.sensibilita = sensibilita;
	}
	public String getStato() {
		return stato;
	}
	public void setStato(String stato) {
		this.stato = stato;
	}
	public String getAmmaccature() {
		return ammaccature;
	}
	public void setAmmaccature(String ammaccature) {
		this.ammaccature = ammaccature;
	}
	public String getBolla_trasversale() {
		return bolla_trasversale;
	}
	public void setBolla_trasversale(String bolla_trasversale) {
		this.bolla_trasversale = bolla_trasversale;
	}
	public String getRegolazione() {
		return regolazione;
	}
	public void setRegolazione(String regolazione) {
		this.regolazione = regolazione;
	}
	public String getCentraggio() {
		return centraggio;
	}
	public void setCentraggio(String centraggio) {
		this.centraggio = centraggio;
	}
	public String getnCertificato() {
		return nCertificato;
	}
	public void setnCertificato(String nCertificato) {
		this.nCertificato = nCertificato;
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
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public String getRif_campione() {
		return rif_campione;
	}
	public void setRif_campione(String rif_campione) {
		this.rif_campione = rif_campione;
	}
	public String getRif_campione_lavoro() {
		return rif_campione_lavoro;
	}
	public void setRif_campione_lavoro(String rif_campione_lavoro) {
		this.rif_campione_lavoro = rif_campione_lavoro;
	}
	
	
}
