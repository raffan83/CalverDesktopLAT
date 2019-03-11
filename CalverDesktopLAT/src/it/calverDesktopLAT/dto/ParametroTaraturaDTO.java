package it.calverDesktopLAT.dto;

import java.math.BigDecimal;
import java.util.Date;

public class ParametroTaraturaDTO {
	
	String descrizioneParametro;
	Date dataScadenza;
	String um;
	String um_fond;
	BigDecimal valoreTaratura;
	BigDecimal valore_nominale;
	BigDecimal risoluzione;
	BigDecimal incertezzaAssoluta;
	BigDecimal incertezzaRelativa;
	BigDecimal scostamentoPrecedente;
	String tipoGrandezza;
	int interpolazione;
	
	public String getDescrizioneParametro() {
		return descrizioneParametro;
	}
	public void setDescrizioneParametro(String descrizioneParametro) {
		this.descrizioneParametro = descrizioneParametro;
	}
	public BigDecimal getScostamentoPrecedente() {
		return scostamentoPrecedente;
	}
	public void setScostamentoPrecedente(BigDecimal scostamentoPrecedente) {
		this.scostamentoPrecedente = scostamentoPrecedente;
	}
	public Date getDataScadenza() {
		return dataScadenza;
	}
	public void setDataScadenza(Date dataScadenza) {
		this.dataScadenza = dataScadenza;
	}
	public String getUm() {
		return um;
	}
	public void setUm(String um) {
		this.um = um;
	}
	public String getUm_fond() {
		return um_fond;
	}
	public void setUm_fond(String um_fond) {
		this.um_fond = um_fond;
	}
	public BigDecimal getValoreTaratura() {
		return valoreTaratura;
	}
	public void setValoreTaratura(BigDecimal valoreTaratura) {
		this.valoreTaratura = valoreTaratura;
	}
	public BigDecimal getValore_nominale() {
		return valore_nominale;
	}
	public void setValore_nominale(BigDecimal valore_nominale) {
		this.valore_nominale = valore_nominale;
	}

	public BigDecimal getRisoluzione() {
		return risoluzione;
	}
	public void setRisoluzione(BigDecimal risoluzione) {
		this.risoluzione = risoluzione;
	}
	public BigDecimal getIncertezzaAssoluta() {
		return incertezzaAssoluta;
	}
	public void setIncertezzaAssoluta(BigDecimal incertezzaAssoluta) {
		this.incertezzaAssoluta = incertezzaAssoluta;
	}
	public BigDecimal getIncertezzaRelativa() {
		return incertezzaRelativa;
	}
	public void setIncertezzaRelativa(BigDecimal incertezzaRelativa) {
		this.incertezzaRelativa = incertezzaRelativa;
	}
	public String getTipoGrandezza() {
		return tipoGrandezza;
	}
	public void setTipoGrandezza(String tipoGrandezza) {
		this.tipoGrandezza = tipoGrandezza;
	}
	public int getInterpolazione() {
		return interpolazione;
	}
	public void setInterpolazione(int interpolazione) {
		this.interpolazione = interpolazione;
	}
	
	
	

}
