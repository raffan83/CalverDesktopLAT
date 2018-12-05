package it.calverDesktopLAT.bo;

import it.calverDesktopLAT.dto.DatiDASM_DTO;
import it.calverDesktopLAT.utl.Costanti;

public class GestioneDASM {

	public static DatiDASM_DTO getDatiPorta() {
		
		DatiDASM_DTO	dati = new DatiDASM_DTO();

		dati.setPorta(SessionBO.globalKey.get(Costanti.COD_DASM_PORT).toString());
		dati.setFramerate(Integer.parseInt(SessionBO.globalKey.get(Costanti.COD_DASM_FR).toString()));
		return dati;
	}

	public static void saveDatiDasm(String port, int frameR) {
		
		GestioneRegistro.setStringValue(Costanti.COD_DASM_PORT, port);
		GestioneRegistro.setStringValue(Costanti.COD_DASM_FR, ""+frameR);

		SessionBO.globalKey.put(Costanti.COD_DASM_PORT, port);
		SessionBO.globalKey.put(Costanti.COD_DASM_FR, frameR);
		
	}

}
