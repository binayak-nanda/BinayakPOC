package Utilities;

import com.ibm.broker.plugin.MbElement;
import com.ibm.broker.plugin.MbException;
import com.ibm.broker.plugin.MbGlobalMap;

public class CacheUtil {
	/**
	 * Method to get a value from Global Cache using map name and key
	 */
	public static String getValue(String strMapName, String strKey) {
		
		String strValue = null;
		MbGlobalMap globalMap = null;
		
		try
		{
			globalMap = MbGlobalMap.getGlobalMap(strMapName);
			strValue = (String) globalMap.get(strKey);
		}
		catch(MbException mbe)
		{
			System.out.println(mbe.getMessage());
			mbe.printStackTrace();
		}
		
		return strValue;
	}
	
	/**
	 * Method to add all the key-value pairs for a map in Global Cache
	 */	
	
	public static Boolean addMap(MbElement elmMap) {
		
		String strValue = null;
		String strKey = null;
		String strMapName = null;
		MbGlobalMap globalMap = null;
		
		try
		{
			elmMap = elmMap.getFirstChild();
			strMapName = elmMap.getValueAsString();
			
			globalMap = MbGlobalMap.getGlobalMap(strMapName);
			
			MbElement elmEntry = elmMap.getNextSibling();
			
			while (elmEntry != null) {
				
				strKey = elmEntry.getFirstChild().getValueAsString();
				strValue = elmEntry.getValueAsString();
				
				if(globalMap.containsKey(strKey)) {
					
					globalMap.update(strKey,strValue);
				} else {
					globalMap.put(strKey, strValue);
				}
				
				elmEntry = elmEntry.getNextSibling();
			}
				
			
		}
		catch(MbException mbe) {
			System.out.println(mbe.getMessage());
			mbe.printStackTrace();
			return Boolean.FALSE;
		}		

		
		return Boolean.TRUE;
	}	
	
	/**
	 * Method to get a add a key-value pair to a map in Global Cache
	 */	
	
	public static Boolean addUpdateKey(String strMapName, String strKey, String strValue) {
		
		MbGlobalMap globalMap = null;
		
		try
		{
			// Get an existing Map, or create the dynamic map if it doesn't exist
			globalMap = MbGlobalMap.getGlobalMap(strMapName);
			
			// If key is not present, add the key-value pair to the map
			// If key exists, refresh the value of the key
			if(globalMap.containsKey(strKey)) {
				globalMap.update(strKey,strValue);
			} else {
				globalMap.put(strKey, strValue);
			}
		}
		catch(MbException mbe) {
			System.out.println(mbe.getMessage());
			mbe.printStackTrace();
			return Boolean.FALSE;
		}		
		
		return Boolean.TRUE;
	}	

}
