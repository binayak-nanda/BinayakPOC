package com.ibm.betaworks;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.JAXBElement;
import org.w3c.dom.Document;


import com.ibm.broker.javacompute.MbJavaComputeNode;
import com.ibm.broker.plugin.MbException;
import com.ibm.broker.plugin.MbGlobalMap;
import com.ibm.broker.plugin.MbMessage;
import com.ibm.broker.plugin.MbMessageAssembly;
import com.ibm.broker.plugin.MbOutputTerminal;
import com.ibm.broker.plugin.MbUserException;
import com.ibm.broker.plugin.MbXMLNSC;

public class GetEmployeeFromCache extends MbJavaComputeNode {

 protected static JAXBContext jaxbContext = null;
	
 public void onInitialize() throws MbException {
  try {
   // Initialise context path "com.ibm.betaworks" the package of 
   // Java object classes used in the EmployeeService lab 
   
   jaxbContext = JAXBContext.newInstance("com.ibm.betaworks");
  } catch (JAXBException e) {
  // This exception will cause the deploy of this Java compute node to fail
  //  Typical cause is the JAXB package above is not available
  
  MbUserException mbue = new MbUserException(this, "onInitialize()",
					"", "", e.toString(), null);
  throw mbue;
  }
 }

public void evaluate(MbMessageAssembly inAssembly) throws MbException {
 MbOutputTerminal out = getOutputTerminal("out");
 MbOutputTerminal alt = getOutputTerminal("alternate");

 MbMessage inMessage = inAssembly.getMessage();
 MbMessageAssembly outAssembly = null;
 try {

   // create new message as a copy of the input
   MbMessage outMessage = new MbMessage(inMessage);
   outAssembly = new MbMessageAssembly(inAssembly, outMessage);
   // ----------------------------------------------------------
   // Add user code below
		
   // unmarshal the JAXB classes - enables JAXB -> POJO so that you can get at the actual data in the message
   //
   GetEmployeeCache cacheRequest = (GetEmployeeCache) jaxbContext.createUnmarshaller().unmarshal(inMessage.getDOMDocument());
		
   // Create a copy of the input data  
   EMPLOYEEType EmployeeRequest = cacheRequest.getCacheRequest();
   


   // Obtain key to use to search for Employee - use JAXB getter for EMPNO 
   String employeeKey = EmployeeRequest.getEMPNO();
			
   // Go to cache to find the employee 
   MbGlobalMap globalMap;
			
   // For XC10 use: (need to set up userXXdatagrid in XC10 and CS_XC10DATAGRID as a configurable service  
   // globalMap = MbGlobalMap.getGlobalMap("userXXdatagrid","CS_XC10DATAGRID");
			
   // For Embedded Cache (requires Embedded cache to be turned on)
   globalMap=MbGlobalMap.getGlobalMap("aliveUntilRestart");
			
   EMPLOYEEType cachedEmployee = new EMPLOYEEType();
			
   // Read cache with key
   cachedEmployee = (EMPLOYEEType) globalMap.get(employeeKey );
			
   // Prepare response
   GetEmployeeCacheResponse response = new GetEmployeeCacheResponse();
   EmployeeResponseType detail = new EmployeeResponseType();		
   DBRespType cacheResponse = new DBRespType();
   
   if (cachedEmployee == null) {
     // set response if nothing found ... 
	
	 // pass back request
     detail.getEMPLOYEE().add(EmployeeRequest);
     //Set return code in dbResp
     cacheResponse.setUserReturnCode(0);
     cacheResponse.setSQLErrorMessage("Key for User not found");
     cacheResponse.setRowsRetrieved(0);
     response.setCacheResponse(detail);
     }				
    else {
      //Pass data from cache 
      detail.getEMPLOYEE().add(cachedEmployee);
      cacheResponse.setUserReturnCode(0);
      cacheResponse.setRowsRetrieved(1);
		}		
    
     detail.setDBResp(cacheResponse);
     response.setCacheResponse(detail);
  
     // Set the required Broker domain to form the output message, eg XMLNSC
     Document outDocument = outMessage.createDOMDocument(MbXMLNSC.PARSER_NAME);
			
     // marshal the new or updated output Java object class into the Broker tree
     jaxbContext.createMarshaller().marshal(response, outDocument);
			
     // The following should only be changed if not propagating message to 
     // the node's 'out' terminal
     out.propagate(outAssembly);
			
     // End of user code
     // ----------------------------------------------------------
  } catch (MbException e) {
   // Re-throw to allow Broker handling of MbException
   throw e;
  } catch (RuntimeException e) {
   // Re-throw to allow Broker handling of RuntimeException
   throw e;
  } catch (Exception e) {
  // Consider replacing Exception with type(s) thrown by user code
  // Example handling ensures all exceptions are re-thrown to be handled in the flow
  throw new MbUserException(this, "evaluate()", "", "", e.toString(),
					null);
 }
 // The following should only be changed
 // if not propagating message to the 'out' terminal
 out.propagate(outAssembly);

 }

}