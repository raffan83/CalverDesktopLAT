package it.calverDesktopLAT.dto;

import java.math.BigDecimal;

public class CampioneCorrettoDTO {
	
	private BigDecimal valoreCampioneCorretto;
	private BigDecimal incertezzaCorretta;
	
	public BigDecimal getValoreCampioneCorretto() {
		return valoreCampioneCorretto;
	}
	public void setValoreCampioneCorretto(BigDecimal valoreCampioneCorretto) {
		this.valoreCampioneCorretto = valoreCampioneCorretto;
	}
	public BigDecimal getIncertezzaCorretta() {
		return incertezzaCorretta;
	}
	public void setIncertezzaCorretta(BigDecimal incertezzaCorretta) {
		this.incertezzaCorretta = incertezzaCorretta;
	}

}
