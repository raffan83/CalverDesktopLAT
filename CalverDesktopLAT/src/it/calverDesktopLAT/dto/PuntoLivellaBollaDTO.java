package it.calverDesktopLAT.dto;

import java.math.BigDecimal;

import it.calverDesktopLAT.utl.Costanti;

public class PuntoLivellaBollaDTO {

	
	public int id;
	public int id_misura; 
	public int rif_tacca;
	public String semisc;
	public BigDecimal valore_nominale_tratto;
	public BigDecimal valore_nominale_tratto_sec;
	public BigDecimal p1_andata ;
	public BigDecimal p1_ritorno;
	public BigDecimal p1_media;
	public BigDecimal p1_diff;
	public BigDecimal p2_andata;
	public BigDecimal p2_ritorno;
	public BigDecimal p2_media;
	public BigDecimal p2_diff;
	public BigDecimal media;
	public BigDecimal errore_cum;
	public BigDecimal media_corr_sec;
	public BigDecimal media_corr_mm;
	public BigDecimal div_dex;
	public String valore_nominale_tacca;
	public BigDecimal corr_boll_mm;
	public BigDecimal corr_boll_sec;
	
	/*public PuntoLivellaBollaDTO() 
	
	{
		 valore_nominale_tratto.setScale(Costanti.RISOLUZIONE_LIVELLA_BOLLA);
		 valore_nominale_tratto_sec.setScale(Costanti.RISOLUZIONE_LIVELLA_BOLLA);
		 p1_andata.setScale(Costanti.RISOLUZIONE_LIVELLA_BOLLA);
		 p1_ritorno.setScale(Costanti.RISOLUZIONE_LIVELLA_BOLLA);
		 p1_media.setScale(Costanti.RISOLUZIONE_LIVELLA_BOLLA);
		 p1_diff.setScale(Costanti.RISOLUZIONE_LIVELLA_BOLLA);
		 p2_andata.setScale(Costanti.RISOLUZIONE_LIVELLA_BOLLA);
		 p2_ritorno.setScale(Costanti.RISOLUZIONE_LIVELLA_BOLLA);
		 p2_media.setScale(Costanti.RISOLUZIONE_LIVELLA_BOLLA);
		 p2_diff.setScale(Costanti.RISOLUZIONE_LIVELLA_BOLLA);
		 media.setScale(Costanti.RISOLUZIONE_LIVELLA_BOLLA);
		 errore_cum.setScale(Costanti.RISOLUZIONE_LIVELLA_BOLLA);
		 media_corr_sec.setScale(Costanti.RISOLUZIONE_LIVELLA_BOLLA);
		 media_corr_mm.setScale(Costanti.RISOLUZIONE_LIVELLA_BOLLA+1);
		 div_dex.setScale(Costanti.RISOLUZIONE_LIVELLA_BOLLA+1);
	}*/

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

	public int getRif_tacca() {
		return rif_tacca;
	}

	public void setRif_tacca(int rif_tacca) {
		this.rif_tacca = rif_tacca;
	}

	public String getSemisc() {
		return semisc;
	}

	public void setSemisc(String semisc) {
		this.semisc = semisc;
	}

	public BigDecimal getValore_nominale_tratto() {
		return valore_nominale_tratto;
	}

	public void setValore_nominale_tratto(BigDecimal valore_nominale_tratto) {
		this.valore_nominale_tratto = valore_nominale_tratto;
	}

	public BigDecimal getValore_nominale_tratto_sec() {
		return valore_nominale_tratto_sec;
	}

	public void setValore_nominale_tratto_sec(BigDecimal valore_nominale_tratto_sec) {
		this.valore_nominale_tratto_sec = valore_nominale_tratto_sec;
	}

	public BigDecimal getP1_andata() {
		return p1_andata;
	}

	public void setP1_andata(BigDecimal p1_andata) {
		this.p1_andata = p1_andata;
	}

	public BigDecimal getP1_ritorno() {
		return p1_ritorno;
	}

	public void setP1_ritorno(BigDecimal p1_ritorno) {
		this.p1_ritorno = p1_ritorno;
	}

	public BigDecimal getP1_media() {
		return p1_media;
	}

	public void setP1_media(BigDecimal p1_media) {
		this.p1_media = p1_media;
	}

	public BigDecimal getP1_diff() {
		return p1_diff;
	}

	public void setP1_diff(BigDecimal p1_diff) {
		this.p1_diff = p1_diff;
	}

	public BigDecimal getP2_andata() {
		return p2_andata;
	}

	public void setP2_andata(BigDecimal p2_andata) {
		this.p2_andata = p2_andata;
	}

	public BigDecimal getP2_ritorno() {
		return p2_ritorno;
	}

	public void setP2_ritorno(BigDecimal p2_ritorno) {
		this.p2_ritorno = p2_ritorno;
	}

	public BigDecimal getP2_media() {
		return p2_media;
	}

	public void setP2_media(BigDecimal p2_media) {
		this.p2_media = p2_media;
	}

	public BigDecimal getP2_diff() {
		return p2_diff;
	}

	public void setP2_diff(BigDecimal p2_diff) {
		this.p2_diff = p2_diff;
	}

	public BigDecimal getMedia() {
		return media;
	}

	public void setMedia(BigDecimal media) {
		this.media = media;
	}

	public BigDecimal getErrore_cum() {
		return errore_cum;
	}

	public void setErrore_cum(BigDecimal errore_cum) {
		this.errore_cum = errore_cum;
	}

	public BigDecimal getMedia_corr_sec() {
		return media_corr_sec;
	}

	public void setMedia_corr_sec(BigDecimal media_corr_sec) {
		this.media_corr_sec = media_corr_sec;
	}

	public BigDecimal getMedia_corr_mm() {
		return media_corr_mm;
	}

	public void setMedia_corr_mm(BigDecimal media_corr_mm) {
		this.media_corr_mm = media_corr_mm;
	}

	public BigDecimal getDiv_dex() {
		return div_dex;
	}

	public void setDiv_dex(BigDecimal div_dex) {
		this.div_dex = div_dex;
	}

	public String getValore_nominale_tacca() {
		return valore_nominale_tacca;
	}

	public void setValore_nominale_tacca(String valore_nominale_tacca) {
		this.valore_nominale_tacca = valore_nominale_tacca;
	}

	public BigDecimal getCorr_boll_mm() {
		return corr_boll_mm;
	}

	public void setCorr_boll_mm(BigDecimal corr_boll_mm) {
		this.corr_boll_mm = corr_boll_mm;
	}

	public BigDecimal getCorr_boll_sec() {
		return corr_boll_sec;
	}

	public void setCorr_boll_sec(BigDecimal corr_boll_sec) {
		this.corr_boll_sec = corr_boll_sec;
	}
	
	
}
