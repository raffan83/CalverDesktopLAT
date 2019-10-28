package it.calverDesktopLAT.dto;

import java.math.BigDecimal;

public class LatMassaAMB {
	private int id;
	private int id_misura;
	private String data_ora;
	private BigDecimal  ch1_temperatura;
	private BigDecimal  ch2_temperatura;
	private BigDecimal  ch3_temperatura;
	private BigDecimal  ch1_temperatura_corr;
	private BigDecimal  ch2_temperatura_corr;
	private BigDecimal  ch3_temperatura_corr;
	private BigDecimal  umidita;
	private BigDecimal  umidita_corr;
	private BigDecimal  pressione;
	private BigDecimal  pressione_corretta;
	
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
	public String getData_ora() {
		return data_ora;
	}
	public void setData_ora(String data_ora) {
		this.data_ora = data_ora;
	}
	public BigDecimal getCh1_temperatura() {
		return ch1_temperatura;
	}
	public void setCh1_temperatura(BigDecimal ch1_temperatura) {
		this.ch1_temperatura = ch1_temperatura;
	}
	public BigDecimal getCh2_temperatura() {
		return ch2_temperatura;
	}
	public void setCh2_temperatura(BigDecimal ch2_temperatura) {
		this.ch2_temperatura = ch2_temperatura;
	}
	public BigDecimal getCh3_temperatura() {
		return ch3_temperatura;
	}
	public void setCh3_temperatura(BigDecimal ch3_temperatura) {
		this.ch3_temperatura = ch3_temperatura;
	}
	public BigDecimal getCh1_temperatura_corr() {
		return ch1_temperatura_corr;
	}
	public void setCh1_temperatura_corr(BigDecimal ch1_temperatura_corr) {
		this.ch1_temperatura_corr = ch1_temperatura_corr;
	}
	public BigDecimal getCh2_temperatura_corr() {
		return ch2_temperatura_corr;
	}
	public void setCh2_temperatura_corr(BigDecimal ch2_temperatura_corr) {
		this.ch2_temperatura_corr = ch2_temperatura_corr;
	}
	public BigDecimal getCh3_temperatura_corr() {
		return ch3_temperatura_corr;
	}
	public void setCh3_temperatura_corr(BigDecimal ch3_temperatura_corr) {
		this.ch3_temperatura_corr = ch3_temperatura_corr;
	}
	public BigDecimal getUmidita() {
		return umidita;
	}
	public void setUmidita(BigDecimal umidita) {
		this.umidita = umidita;
	}
	public BigDecimal getUmidita_corr() {
		return umidita_corr;
	}
	public void setUmidita_corr(BigDecimal umidita_corr) {
		this.umidita_corr = umidita_corr;
	}
	public BigDecimal getPressione() {
		return pressione;
	}
	public void setPressione(BigDecimal pressione) {
		this.pressione = pressione;
	}
	public BigDecimal getPressione_corretta() {
		return pressione_corretta;
	}
	public void setPressione_corretta(BigDecimal pressione_corretta) {
		this.pressione_corretta = pressione_corretta;
	}
	
	
		

}
