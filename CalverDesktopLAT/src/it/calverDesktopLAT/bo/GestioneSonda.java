package it.calverDesktopLAT.bo;




import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import it.calverDesktopLAT.dto.SondaDTO;

public class GestioneSonda {

	public static HashMap<Long, String> msgs = null;
	public static HashMap<Long, String> msgsOrd = null;
	public static HashMap<String, String> containerSonda = null;

	public static ArrayList<SondaDTO> getListaSonde(TwoWaySerialComm com) {

		ArrayList<SondaDTO> listaSonde= new ArrayList<>();

		 msgs = new HashMap<Long, String>();
		 msgsOrd = new HashMap<Long, String>();
		 containerSonda = new HashMap<String, String>();

		
		if(com.isConnected()){
			msgs = com.read();
			msgsOrd=sortByValues(msgs);
			if(!msgs.isEmpty()){
				SondaDTO sonda =null;
				for(String str : msgsOrd.values()){

					System.out.println(str);
					String idSonda="";  

					if(str.indexOf("[")>0 && str.indexOf("]")>0)
					{

						try
						{	  
							if(str.length()>3)
							{
								idSonda=str.substring(0,str.indexOf("=")); 
								str=str.substring(str.indexOf("[")+1,str.indexOf("]"));  

								String[] param=str.split(";");

								if(!param[0].substring(param[0].indexOf("=")+1,param[0].length()).startsWith("NC"))
								{


									sonda = new SondaDTO();
									sonda.setId_sonda(idSonda);
									sonda.setMinScale(new BigDecimal(param[0]));
									sonda.setMaxScale(new BigDecimal(param[1]));
									sonda.setUm(param[2]);
									sonda.setPrecision(new BigDecimal(param[3]));
									sonda.setLabel(param[4]);

									if(param.length> 5 && param[5].equalsIgnoreCase("REL"))
									{
										sonda.setZero(true);
									}
									else
									{
										sonda.setZero(false);
									}
									
									if(!containerSonda.containsKey(idSonda))
									{
										listaSonde.add(sonda);
										containerSonda.put(idSonda, idSonda);
									}
								}
							}
						}catch (Exception e) {
							System.err.println("Errore Lettura");
						}
					}  

				}
			}
		}

		return listaSonde;
	}

	public static Double getValue(TwoWaySerialComm com, SondaDTO sonda) {

		Double toReturn=null;
		if(com.isConnected()){
			msgs = com.read();

			if(!msgs.isEmpty()){

				for(String str : msgs.values()){
			//		System.out.println("STRING --"+str);
					try
					{
						if(str.startsWith(sonda.getId_sonda()+"="))
						{
							toReturn=Double.parseDouble(str.substring(str.indexOf("=")+1, str.indexOf("[")));
							System.out.println(toReturn);
							return toReturn;
						}
					}catch (Exception e) 
					{
						System.err.println("RoAD: "+toReturn);
						e.printStackTrace();
						return null;
					}

				}
			}
		}
		return null;
	}


	private static HashMap sortByValues(HashMap map) { 
		List list = new LinkedList(map.entrySet());
		// Defined Custom Comparator here
		Collections.sort(list, new Comparator() {
			public int compare(Object o1, Object o2) {
				return ((Comparable) ((Map.Entry) (o1)).getValue())
						.compareTo(((Map.Entry) (o2)).getValue());
			}
		});

		// Here I am copying the sorted list in HashMap
		// using LinkedHashMap to preserve the insertion order
		HashMap sortedHashMap = new LinkedHashMap();
		for (Iterator it = list.iterator(); it.hasNext();) {
			Map.Entry entry = (Map.Entry) it.next();
			sortedHashMap.put(entry.getKey(), entry.getValue());
		} 
		return sortedHashMap;
	}


}
