package it.calverDesktopLAT.utl;

import java.awt.Color;
import java.awt.Paint;

public class Costanti {
	
	public static final String FONT = "Courier";
	public static final String[] TIPO_PROVA ={"Linearità","Ripetibilità"};
	public static final String[] TIPO_SATORICEZIONE = {"In Tolleranza","Fuori Tolleranza","Non Funzionante"};
	public static final String[] INTERPOLAZIONE = {"1","2","3","4","5","10"};
	public static final String[] TOLLERANZA = {"Tolleranza dgt/div","Tolleranza %","Tolleranza % FS","Tolleranza dgt + %"};
	public static final int SCALA = 10;
	public static final String IDONEO = "IDONEO";
	public static final String NON_IDONEO = "NON IDONEO";
	public static final String CLASSE_PER_POTENZA_DEFAULT = "unita () | 1";
	public static final String SVT = "SVT";
	public static final String RDT = "RDT";
	public static final String RDP = "RDP";
	public static final String[] LISTA_FIRME = {"OP + RL","OP","OP + RL + CL","OP + CL"};
	public static String PATH_DB="";
	public static final Color backgroundGrey = new Color(179, 179, 179);
	public static final Color backgroundGreyLight = new Color(204, 204, 204);
	public static final String SYSROOT = System.getenv("ProgramData")+"\\Calver";
	public static final String DEPLOY_HOST = "http://www.calver.it";
	public static final String VERSION = "1.0.7";
	public static final String REGISTER_KEY = "Software\\Calver";
	public static final String COD_OPT = "CL_OPR";
	public static final String COD_CNT ="CL_CNT";
	public static final String COD_DASM_PORT ="PORT_DASM";
	public static final String COD_DASM_FR ="FRAME_RATE_DASM";
	public static final String COD_IMG_PATH = "IMG_PATH";
	public static final String COD_LAST_PATH="LAST_PATH";
	public static final String COD_PRINT = "PRINT_TYPE";
	public static final Color COLOR_RED = new Color(215,23,29);
	public static final Paint COLOR_BLUE = new Color(20,55,200);
	public static final int RISOLUZIONE_LIVELLA_BOLLA = 2;
	public static final int RISOLUZIONE_LIVELLA_ELETTRONICA = 2;
	public static final String INCERTEZZA_CDT001 = "3";
	public static final double RISOLUZIONE_RSG30_TEMP_RIS = 0.01;
	public static final double RISOLUZIONE_RSG30_TEMP_RIS_U = 0.05;
	public static final double RISOLUZIONE_RSG30_UR_RIS = 0.1;
	public static final double RISOLUZIONE_RSG30_UR_RIS_U = 1.8;
	public static final double RISOLUZIONE_RSG30_PRESS_RIS = 0.1;
	public static final double RISOLUZIONE_RSG30_PRESS_RIS_U = 0.51;
	
	
}
