package it.calverDesktopLAT.dto;

public class LatMassaClasseDTO {
	
	private int id;
	private String val_nominale;
	private int mg;
	private int dens_min;
	private int dens_max;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getVal_nominale() {
		return val_nominale;
	}
	public void setVal_nominale(String val_nominale) {
		this.val_nominale = val_nominale;
	}
	public int getMg() {
		return mg;
	}
	public void setMg(int mg) {
		this.mg = mg;
	}
	public int getDens_min() {
		return dens_min;
	}
	public void setDens_min(int dens_min) {
		this.dens_min = dens_min;
	}
	public int getDens_max() {
		return dens_max;
	}
	public void setDens_max(int dens_max) {
		this.dens_max = dens_max;
	}

	
	
}
