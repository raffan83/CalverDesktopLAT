package it.calverDesktopLAT.dto;

import java.math.BigDecimal;

public class MisuraDTO {
	
	int ordine;
	int id;
	int id_ripetizione;
	int idTabella;
	
	String tipoVerifica;
	String label;
	String tipoProva;
	String um;
	BigDecimal valoreCampione;
	BigDecimal valoreMedioCampione;
	BigDecimal valoreStrumento;
	BigDecimal valoreMedioStrumento;
	BigDecimal scostamento;
	BigDecimal accettabilita;
	BigDecimal incertezza;
	String esito;
	
	String descrizioneParametro;
	String descrizioneCampione;
	BigDecimal misura;
	String um_calc;
	String fm;
	BigDecimal risoluzione_misura;
	BigDecimal risoluzione_campione;
	int interpolazione;
	BigDecimal fondoScala;
	int selConversione;
	int selTolleranza;
	BigDecimal percentuale;
	BigDecimal letturaCampione;
	String misuraPrecedente;
	String misuraCampionePrecedente;
	String provaPrecedente;
	String esitoPrecedente;
	String calibrazione;
	String applicabile="S";
	BigDecimal dgt;
	byte[] file_att;
	byte[] file_att_prec;
	
	
	public byte[] getFile_att_prec() {
		return file_att_prec;
	}
	public void setFile_att_prec(byte[] file_att_prec) {
		this.file_att_prec = file_att_prec;
	}
	public String getProvaPrecedente() {
		return provaPrecedente;
	}
	public void setProvaPrecedente(String provaPrecedente) {
		this.provaPrecedente = provaPrecedente;
	}
	public String getEsitoPrecedente() {
		return esitoPrecedente;
	}
	public void setEsitoPrecedente(String esitoPrecedente) {
		this.esitoPrecedente = esitoPrecedente;
	}
	public byte[] getFile_att() {
		return file_att;
	}
	public void setFile_att(byte[] file_att) {
		this.file_att = file_att;
	}

	public String getTipoProva() {
		return tipoProva;
	}
	public void setTipoProva(String tipoProva) {
		this.tipoProva = tipoProva;
	}
	public int getIdTabella() {
		return idTabella;
	}
	public void setIdTabella(int idTabella) {
		this.idTabella = idTabella;
	}
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public String getCalibrazione() {
		return calibrazione;
	}
	public void setCalibrazione(String calibrazione) {
		this.calibrazione = calibrazione;
	}
	public int getOrdine() {
		return ordine;
	}
	public void setOrdine(int ordine) {
		this.ordine = ordine;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getId_ripetizione() {
		return id_ripetizione;
	}
	public void setId_ripetizione(int id_ripetizione) {
		this.id_ripetizione = id_ripetizione;
	}
	public String getTipoVerifica() {
		return tipoVerifica;
	}
	public void setTipoVerifica(String tipoVerifica) {
		this.tipoVerifica = tipoVerifica;
	}
	public String getUm() {
		return um;
	}
	public void setUm(String um) {
		this.um = um;
	}
	public BigDecimal getValoreCampione() {
		return valoreCampione;
	}
	public void setValoreCampione(BigDecimal valoreCampione) {
		this.valoreCampione = valoreCampione;
	}
	public BigDecimal getValoreMedioCampione() {
		return valoreMedioCampione;
	}
	public void setValoreMedioCampione(BigDecimal valoreMedioCampione) {
		this.valoreMedioCampione = valoreMedioCampione;
	}
	public BigDecimal getValoreStrumento() {
		return valoreStrumento;
	}
	public void setValoreStrumento(BigDecimal valoreStrumento) {
		this.valoreStrumento = valoreStrumento;
	}
	public BigDecimal getValoreMedioStrumento() {
		return valoreMedioStrumento;
	}
	public void setValoreMedioStrumento(BigDecimal valoreMedioStrumento) {
		this.valoreMedioStrumento = valoreMedioStrumento;
	}
	public BigDecimal getScostamento() {
		return scostamento;
	}
	public void setScostamento(BigDecimal scostamento) {
		this.scostamento = scostamento;
	}
	public BigDecimal getAccettabilita() {
		return accettabilita;
	}
	public void setAccettabilita(BigDecimal accettabilita) {
		this.accettabilita = accettabilita;
	}
	public BigDecimal getIncertezza() {
		return incertezza;
	}
	public void setIncertezza(BigDecimal incertezza) {
		this.incertezza = incertezza;
	}
	public String getEsito() {
		return esito;
	}
	public void setEsito(String esito) {
		this.esito = esito;
	}
	public String getDescrizioneParametro() {
		return descrizioneParametro;
	}
	public void setDescrizioneParametro(String descrizioneParametro) {
		this.descrizioneParametro = descrizioneParametro;
	}
	public String getDescrizioneCampione() {
		return descrizioneCampione;
	}
	public void setDescrizioneCampione(String descrizioneCampione) {
		this.descrizioneCampione = descrizioneCampione;
	}
	public BigDecimal getMisura() {
		return misura;
	}
	public void setMisura(BigDecimal misura) {
		this.misura = misura;
	}
	public String getUm_calc() {
		return um_calc;
	}
	public void setUm_calc(String um_calc) {
		this.um_calc = um_calc;
	}
	public String getFm() {
		return fm;
	}
	public void setFm(String fm) {
		this.fm = fm;
	}
	public BigDecimal getRisoluzione_misura() {
		return risoluzione_misura;
	}
	public void setRisoluzione_misura(BigDecimal risoluzione_misura) {
		this.risoluzione_misura = risoluzione_misura;
	}
	public BigDecimal getRisoluzione_campione() {
		return risoluzione_campione;
	}
	public void setRisoluzione_campione(BigDecimal risoluzione_campione) {
		this.risoluzione_campione = risoluzione_campione;
	}
	public int getInterpolazione() {
		return interpolazione;
	}
	public void setInterpolazione(int interpolazione) {
		this.interpolazione = interpolazione;
	}
	public BigDecimal getFondoScala() {
		return fondoScala;
	}
	public void setFondoScala(BigDecimal fondoScala) {
		this.fondoScala = fondoScala;
	}
	public int getSelConversione() {
		return selConversione;
	}
	public void setSelConversione(int selConversione) {
		this.selConversione = selConversione;
	}
	public int getSelTolleranza() {
		return selTolleranza;
	}
	public void setSelTolleranza(int selTolleranza) {
		this.selTolleranza = selTolleranza;
	}
	public BigDecimal getPercentuale() {
		return percentuale;
	}
	public void setPercentuale(BigDecimal percentuale) {
		this.percentuale = percentuale;
	}
	public BigDecimal getLetturaCampione() {
		return letturaCampione;
	}
	public void setLetturaCampione(BigDecimal letturaCampione) {
		this.letturaCampione = letturaCampione;
	}
	
	public String getMisuraPrecedente() {
		return misuraPrecedente;
	}
	public void setMisuraPrecedente(String misuraPrecedente) {
		this.misuraPrecedente = misuraPrecedente;
	}
	public String getMisuraCampionePrecedente() {
		return misuraCampionePrecedente;
	}
	public void setMisuraCampionePrecedente(String misuraCampionePrecedente) {
		this.misuraCampionePrecedente = misuraCampionePrecedente;
	}
	public String getApplicabile() {
		return applicabile;
	}
	public void setApplicabile(String applicabile) {
		this.applicabile = applicabile;
	}
	public BigDecimal getDgt() {
		return dgt;
	}
	public void setDgt(BigDecimal dgt) {
		this.dgt = dgt;
	}
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return ""+id;
	}

}
