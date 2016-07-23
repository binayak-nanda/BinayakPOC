package P3_M6_MQMB_HDMPROXY_PROJECT;

import java.util.*;

import com.ibm.broker.javacompute.MbJavaComputeNode;
import com.ibm.broker.plugin.*;

public class Change_Password_Update_LDAP_JavaCompute extends MbJavaComputeNode {

	public void evaluate(MbMessageAssembly assembly) throws MbException {
		MbOutputTerminal out = getOutputTerminal("out");
		MbOutputTerminal alt = getOutputTerminal("alternate");

		MbMessage message = assembly.getMessage();
		String successResponse = "<HDMProxyRequest><RequestType>UpdateUserInfo</RequestType><CustomerData>";
		String failureResponse = "<LDAPUpdateStatus>FAILED</LDAPUpdateStatus>";
		String hdmRequest = null;
		try {
			// ----------------------------------------------------------
			// Add user code below
			byte[] msgAsBytes = null;
			String ERROR_LDAP = ">>> ERROR - ";
			String SUCCESS_LDAP = ">>> SUCCESS - ";
			msgAsBytes = message.getRootElement().getLastChild().toBitstream("", "", "", 0, 0, 0);
			String  ldapMSg  = new String(msgAsBytes);
			ldapMSg = ldapMSg.replace('<', ' ');
			ldapMSg = ldapMSg.replace('>',' ');
			System.out.println("Request from sss server is :::"+ldapMSg);
		
			StringTokenizer parseMsg = new StringTokenizer(ldapMSg);
			String pppoeUserName = parseMsg.nextToken().trim();
			System.out.println(">>>>> user name: "+ pppoeUserName);
//			System.out.println(">>>>>using split function: "+pppoeUserName.trim());
			String pppoePassword = parseMsg.nextToken().trim();
			System.out.println(">>>>>password is: "+pppoePassword);
			UpdateLdap ldapRunner = new UpdateLdap();
			hdmRequest = successResponse + "<OrderID>"+pppoeUserName +"</OrderID><PPPOE_UserName>"+pppoeUserName+"</PPPOE_UserName><PPPOE_Password>"+pppoePassword+"</PPPOE_Password></CustomerData></HDMProxyRequest>";
			System.out.println("hdmRequest is: "+hdmRequest);
			System.out.println("Updating LDAP at :"+System.currentTimeMillis());
			String filter = "username=";
			filter = filter.concat(pppoeUserName);
	        String retVal = new String(); 
			String attrName;
			String attrVal;
			boolean result = true;
			if (!result) {
				System.out.println(ERROR_LDAP+ "record not found for"+ filter);
			}
			else 
			{
				System.out.println(SUCCESS_LDAP+ "record found for" + filter);
				attrName = "password";
				attrVal = pppoePassword;
				result = ldapRunner.updateAttribute(filter, attrName, attrVal, false);
				if (!result) 
				{
	                retVal="FAIL"; 
					System.out.println(ERROR_LDAP+ "Did not update: "+ attrVal);
				} else 
				{
	                retVal="SUCCESS"; 
					System.out.println(SUCCESS_LDAP+ "Updated : "+ attrVal);

			
			}
				
			}
				
				 if ((!"FAIL".equals(retVal))) {
						System.out.println("Updated LDAP at :"+System.currentTimeMillis()); 
						System.out.println("The success response to be put in the queue is " +hdmRequest); 
						 MbMessage outMessage = new MbMessage();
					        String myMsg = hdmRequest;
					        /* Converting string to MbMessage type */
					        MbElement outRoot = outMessage.getRootElement();
					        MbElement properties = outRoot.getFirstChild(); 
					        MbElement mqmd = outRoot.createElementAsLastChild("MQHMD");
					        MbElement format= mqmd.createElementAsLastChild(MbElement.TYPE_NAME_VALUE,"Format", "MQSTR");
					        MbElement msgBody = outRoot.createElementAsLastChildFromBitstream(myMsg.getBytes(),"XMLNSC", "", "", "", 0,0, 0);
					        
					        System.out.println("Format of message sent: "+ outMessage.getRootElement().getLastChild().getName());
							System.out.println("done");
							MbMessageAssembly newAssembly = new MbMessageAssembly(assembly, outMessage);
					        out.propagate(newAssembly);
						
						// End of user code
			// ----------------------------------------------------------

			// The following should only be changed
			// if not propagating message to the 'out' terminal
				 }
			
		} catch (Exception e) {
			// Example Exception handling	
			
			try{ MbMessage outMessage = new MbMessage();
		        String myMsg = failureResponse;
		        /* Converting string to MbMessage type */
		        MbElement outRoot = outMessage.getRootElement();
		        MbElement properties = outRoot.getFirstChild(); 
		        MbElement mqmd = outRoot.createElementAsLastChild("MQHMD");
		        MbElement format= mqmd.createElementAsLastChild(MbElement.TYPE_NAME_VALUE,"Format", "MQSTR");
		        MbElement msgBody = outRoot.createElementAsLastChildFromBitstream(myMsg.getBytes(),"XMLNSC", "", "", "", 0,0, 0);
		        
		        System.out.println("Format of message sent: "+ outMessage.getRootElement().getLastChild().getName());
				System.out.println("done");
				MbMessageAssembly newAssembly = new MbMessageAssembly(assembly, outMessage);
		        out.propagate(newAssembly);
		        }catch (Throwable e1) {
					// TODO: handle exception
		        	MbUserException mbue = new MbUserException(this, "evaluate()", "",
							"", e1.toString(), null);
		        	throw mbue;
				}
			
			
		}
	}

}
