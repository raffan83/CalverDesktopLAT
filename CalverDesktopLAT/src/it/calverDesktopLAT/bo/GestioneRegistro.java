package it.calverDesktopLAT.bo;
import static com.sun.jna.platform.win32.WinReg.HKEY_CURRENT_USER;

import java.util.Map;

import com.sun.jna.platform.win32.Advapi32Util;

import it.calverDesktopLAT.utl.Costanti;

public class GestioneRegistro {

    // https://github.com/twall/jna#readme
    //  you need 2 jars : jna-3.5.1.jar and platform-3.5.1.jar

	
	public static void createKey() 
	{
		Advapi32Util.registryCreateKey(HKEY_CURRENT_USER, Costanti.REGISTER_KEY);
	}
	public static String  getStringValue(String value) 
	{
		return Advapi32Util.registryGetStringValue(HKEY_CURRENT_USER,Costanti.REGISTER_KEY,value);
	}
	
	public static void  setStringValue(String key,String value) 
	{
		 Advapi32Util.registrySetStringValue(HKEY_CURRENT_USER,Costanti.REGISTER_KEY,key,value);
	}
	
	public Map<String, Object> getRegysterValues() 
	{
		return Advapi32Util.registryGetValues(HKEY_CURRENT_USER,Costanti.REGISTER_KEY);
	}
	public static boolean isConfig() 
	{
		return Advapi32Util.registryKeyExists(HKEY_CURRENT_USER, Costanti.REGISTER_KEY);
	}
	
    public static void main(String[] args) {
    /*    System.out.println(Advapi32Util.registryGetStringValue
                (HKEY_CURRENT_USER,
                 "Software\\Calver", "User")
        );
        System.out.println(Advapi32Util.registryGetStringValue
                (HKEY_LOCAL_MACHINE,
                 "Software\\Microsoft\\Windows\\CurrentVersion\\App Paths\\AcroRd32.exe",
                 ""));
        System.out.println(Advapi32Util.registryGetStringValue
                (HKEY_LOCAL_MACHINE,
                 "Software\\Microsoft\\Windows\\CurrentVersion\\App Paths\\AcroRd32.exe",
                 "Path"));
        System.out.println(Advapi32Util.registryGetIntValue
                (HKEY_LOCAL_MACHINE,
                 "Software\\Wow6432Node\\Javasoft\\Java Update\\Policy",
                "Frequency"));
        String [] keys = Advapi32Util.registryGetKeys
                (HKEY_CURRENT_USER,
                 "Software\\Microsoft\\Windows\\CurrentVersion\\Internet Settings"
                 );
        for (String key : keys) {
            System.out.println(key);
        }
        Map <String, Object>values = Advapi32Util.registryGetValues
        (HKEY_CURRENT_USER,
         "Software\\Microsoft\\Windows\\CurrentVersion\\Internet Settings"
         );
        for (String value : values.keySet()) {
            System.out.println(value);
        }*/

       // System.out.println(Advapi32Util.registryKeyExists(HKEY_CURRENT_USER, "Software\\calver"));
     //   Advapi32Util.registryCreateKey(HKEY_CURRENT_USER, "Software\\Calver");
     //   System.out.println(Advapi32Util.registryKeyExists(HKEY_CURRENT_USER, "Software\\RealHowTo"));
        Advapi32Util.registrySetStringValue(HKEY_CURRENT_USER, "Software\\Calver", "CL_OPR", "RF");
        //System.out.println(Advapi32Util.registryValueExists(HKEY_CURRENT_USER, "Software\\RealHowTo", "url"));
        //System.out.println(Advapi32Util.registryValueExists(HKEY_CURRENT_USER, "Software\\RealHowTo", "foo"));
    }
	public static boolean esixt(String value) 
	{
	 return Advapi32Util.registryValueExists(HKEY_CURRENT_USER, Costanti.REGISTER_KEY, value);
	}

}