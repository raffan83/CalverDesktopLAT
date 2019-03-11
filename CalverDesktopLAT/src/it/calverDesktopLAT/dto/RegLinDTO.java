package it.calverDesktopLAT.dto;

import java.math.BigDecimal;

public class RegLinDTO {
	private BigDecimal valore_riferimento;
	private BigDecimal scostamento;
	private BigDecimal valore_misurato;
	private BigDecimal m;
	private BigDecimal q;
	public BigDecimal getValore_riferimento() {
		return valore_riferimento;
	}
	public void setValore_riferimento(BigDecimal valore_riferimento) {
		this.valore_riferimento = valore_riferimento;
	}
	public BigDecimal getScostamento() {
		return scostamento;
	}
	public void setScostamento(BigDecimal scostamento) {
		this.scostamento = scostamento;
	}
	public BigDecimal getValore_misurato() {
		return valore_misurato;
	}
	public void setValore_misurato(BigDecimal valore_misurato) {
		this.valore_misurato = valore_misurato;
	}
	public BigDecimal getM() {
		return m;
	}
	public void setM(BigDecimal m) {
		this.m = m;
	}
	public BigDecimal getQ() {
		return q;
	}
	public void setQ(BigDecimal q) {
		this.q = q;
	}
	
	@Override
	public String toString() {
		
		if(valore_misurato!=null) 
		{
			return valore_riferimento+" || "+scostamento+" || "+valore_misurato+" || "+m+" || "+q;
		}
		return super.toString();
	}
	

}
