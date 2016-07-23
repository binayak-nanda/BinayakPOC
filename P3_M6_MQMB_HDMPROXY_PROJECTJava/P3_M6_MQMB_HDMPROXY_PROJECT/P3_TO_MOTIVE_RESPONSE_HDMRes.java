package P3_M6_MQMB_HDMPROXY_PROJECT;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Properties;

import org.apache.xerces.impl.dv.util.Base64;

import com.ibm.broker.javacompute.MbJavaComputeNode;
import com.ibm.broker.plugin.*;

public class P3_TO_MOTIVE_RESPONSE_HDMRes extends MbJavaComputeNode {

	public void evaluate(MbMessageAssembly assembly) throws MbException {
		System.out.println("Inisde NEW JAVA COMPUTE NODE 11111111111111111");
		MbOutputTerminal out = getOutputTerminal("out");
		MbOutputTerminal alt = getOutputTerminal("alternate");
		
		   

		//MbElement env = assembly.getGlobalEnvironment().getRootElement() ;    
		 
		
		MbElement env = assembly.getGlobalEnvironment().getRootElement(); 
		System.out.println("Got Env 1 ");
		MbElement RT = env.getFirstElementByPath("Variable/RequestType");
		System.out.println("Got RTTTTTTTTTTTTTTTTTTTTTTTTTTTTTT ");
		
		/*String sourceFileName= env.getFirstElementByPath("Variables/RequestType").getValueAsString();
		System.out.println("SourceFilename     " +sourceFileName);
		String RequestType = RT.getValue().toString(); 
		
		System.out.println("Checking Request Type herer          ::::::::::::::::    " + RequestType);*/


		MbMessage message = assembly.getMessage();
		
		String m_contentEncoding = "";
		String m_motiveReqParam = "";
		String m_MIMEType = "";
		String m_EncodingStyle = "";
		String motiveURL = "";
	  String m_strPropFileName = "C:/wmbt80/workspace2/P3_M6_MQMB_HDMPROXY_PROJECTJava/P3_M6_MQMB_HDMPROXY_PROJECT/Adapter.properties";
	//	String m_strPropFileName ="/var/mqsi/MotiveAdapter/Adapter.properties";
		try {
			// ----------------------------------------------------------
			// Add user code below

			/* Fetching the Input message from queue */
			System.out.println("Format of message recieved: "+message.getRootElement().getLastChild().getName());
			
			/* Converting the message stream to String */
			byte[] msgAsBytes = null;
			msgAsBytes = message.getRootElement().getLastChild().toBitstream("", "", "", 0, 0, 0);
			String  msgAsText  = new String(msgAsBytes);
			System.out.println("xml is :::"+msgAsText);
			try{
				msgAsText = msgAsText.replace("&amp;","&");
				msgAsText = msgAsText.replace("&quot;", ";");
				msgAsText = msgAsText.replace("&apos;","'");
				msgAsText = msgAsText.replace("&lt;", "<");
				msgAsText = msgAsText.replace("&gt;", ">");
		               
	            }catch(Exception e)
	            {
	         	   System.out.println(e);
	            }
	            System.out.println("value of HDMREQUEST after removing all special char: "+msgAsText);
			/* Read Properties File */
			
			Properties props = new Properties();
			try {
				props.load(new FileInputStream(m_strPropFileName));
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			motiveURL = props.getProperty("MOTIVE_URL");
			m_MIMEType = props.getProperty("MIME_TYPE");
			m_EncodingStyle = props.getProperty("ENCODING_STYLE");
			m_contentEncoding = props.getProperty("CONTENT_ENCODING");
			m_motiveReqParam = props.getProperty("REQUEST_PARAMETER");
			System.out.println("motiveURL::"+motiveURL);
			System.out.println("m_MIMEType::"+m_MIMEType);
			System.out.println("m_EncodingStyle::"+m_EncodingStyle);
			System.out.println("m_contentEncoding::"+m_contentEncoding);
			System.out.println("m_motiveReqParam::"+m_motiveReqParam);
			
			
			String xmlResponse1="";
			xmlResponse1="<HDMProxyResponse><Response><RequestType>InsertUserInfo</RequestType><OrderID>ord1</OrderID><PPPoEUserName>test</PPPoEUserName>"+
            "<Status>Pass</Status><Date>Tue Nov 20 14:13:53 IST 2012</Date><ErrorCode>000</ErrorCode><ErrorMessage>NA </ErrorMessage>"+
            "</Response></HDMProxyResponse>";
			
			// Establishing connection with Motive 
			URL url = new URL(motiveURL);
			HttpURLConnection connection = (HttpURLConnection)url.openConnection();
			
			// Set Authentication for Motive 
			String name = "root";
			String password = "root";
			String authString = name + ":" + password;
			System.out.println("auth string: " + authString);
			String authEncBytes = Base64.encode(authString.getBytes());
			connection.setRequestProperty("Authorization", authEncBytes);
			connection.setRequestMethod("GET");
			connection.setDoInput(true);
			connection.setRequestProperty("Content-Encoding", m_contentEncoding);
			System.out.println("Permission&&&&&&&&&&&"+connection.getPermission().toString());
			System.out.println("USERINFO&&&&&&&&&&&"+connection.getURL().getAuthority());
			
            
			/*
			// Send input to Motive   1111111111111 
			connection.setDoOutput(true);
			OutputStreamWriter out1 = new OutputStreamWriter(connection.getOutputStream());
			out1.write(m_motiveReqParam+msgAsText);	        
			out1.flush();	        
			out1.close();			
			connection.connect();
			
			System.out.println("Permission&&&&&&&&&&&1234"+connection.getPermission().toString());
			System.out.println("USERINFO&&&&&&&&&&&1234"+connection.getURL().getAuthority());
			
	        int code = connection.getResponseCode();
	        System.out.println("Response code of the object is "+code);
	        // Fetch response from Motive 
	        if (code==200)
	        {
	        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
	        String inputLine;
	        
	        while ((inputLine = in.readLine()) != null) {
	           // System.out.println("input line:: "+inputLine);
	            xmlResponse1 = inputLine;
		        System.out.println("Motive Response is::"+xmlResponse1);
	        }
	        in.close();
	        connection.disconnect();  
			*/
			//upto this remov comment
	      
	        MbMessage outMessage = new MbMessage();
	               String myMsg = xmlResponse1;
	               
	               
	        /* Converting string to MbMessage type */
	        MbElement outRoot = outMessage.getRootElement();
	        MbElement properties = outRoot.getFirstChild(); 
	        MbElement mqmd = outRoot.createElementAsLastChild("MQHMD");
	        MbElement format= mqmd.createElementAsLastChild(MbElement.TYPE_NAME_VALUE,"Format", "MQSTR");
	        MbElement msgBody = outRoot.createElementAsLastChildFromBitstream(myMsg.getBytes(),"XMLNSC", "", "", "", 0,0, 0);
	        
	        System.out.println("Format of message sent: "+ outMessage.getRootElement().getLastChild().getName());
			System.out.println("done  -- " +outMessage);
			try
			{
			MbMessageAssembly newAssembly = new MbMessageAssembly(assembly, outMessage);

			//newAssembly.getGlobalEnvironment().getRootElement();
	        out.propagate(newAssembly);    
			}
			catch (Exception e) { System.out.println(" Error Occured while propagate " + e.getMessage());
			
			System.out.println("Cause " +e.getCause());}
			
			
	        System.out.println("Message Sent correctly from JCN ::::::::::::::: 11111111111   "+ xmlResponse1);
	        
	   //Commnent start again
		  /*} 
		   *
		   * 
		    else
		   
	        {
	        	System.out.println("in alt");
	        	alt.propagate(assembly);
	        }
			*/  
			

		} catch (Throwable e) {
			//System.out.println("this is an error");
			// Example Exception handling	
			MbUserException mbue = new MbUserException(this, "evaluate()", "",
					"", e.toString(), null);
			throw mbue;
		}
	}

}
