package P3_M6_MQMB_HDMPROXY_PROJECT;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Properties;

import org.apache.xerces.impl.dv.util.Base64;

import com.ibm.broker.javacompute.MbJavaComputeNode;
import com.ibm.broker.plugin.*;

public class M6_TO_MOTIVE_BACKOUT_FLOW_JavaCompute extends MbJavaComputeNode {

	public void evaluate(MbMessageAssembly assembly) throws MbException {
		MbOutputTerminal out = getOutputTerminal("out");
		MbOutputTerminal alt = getOutputTerminal("alternate");

		MbMessage message = assembly.getMessage();

		
		String m_contentEncoding = "";
		String m_motiveReqParam = "";
		String m_MIMEType = "";
		String m_EncodingStyle = "";
		String motiveURL = "";
		//String m_strPropFileName = "C:/Users/IBM_ADMIN/Workspaces/wmbt80/workspace/BSNL_NEWJava/P3_M6_MQMB_HDMPROXY_PROJECT/Adapter.properties";
		String m_strPropFileName ="/var/mqsi/MotiveAdapter/Adapter.properties";
		
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
			
			/* Establishing connection with Motive */
			URL url = new URL(motiveURL);
			HttpURLConnection connection = (HttpURLConnection)url.openConnection();
			
			/* Set Authentication for Motive */
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
			
			/* Send input to Motive */
			connection.setDoOutput(true);
			OutputStreamWriter out1 = new OutputStreamWriter(connection.getOutputStream());
			out1.write(m_motiveReqParam+msgAsText);
	        out1.flush();
	        out1.close();
			
			connection.connect();
			/*System.out.println("Permission&&&&&&&&&&&1234"+connection.getPermission().toString());
			System.out.println("USERINFO&&&&&&&&&&&1234"+connection.getURL().getAuthority());*/
			String xmlResponse1="";
	        int code = connection.getResponseCode();
	        System.out.println("Response code of the object is "+code);
	        /* Fetch response from Motive */
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
	        /* Generating motive response when response from motive is null */
	        if(xmlResponse1.equals("") || xmlResponse1 == "" || xmlResponse1 == null){
	        	//System.out.println("message recieved: "+message.getRootElement().getLastChild().getName());
				MbElement parentNode = message.getRootElement().getLastChild().getFirstChild();
				//System.out.println("parent name: "+parentNode.getName());
				MbElement child1=parentNode.getFirstChild();
				//System.out.println("First element name: "+child1.getName());
				//System.out.println("First element value: "+child1.getValue());
				String RequestType = child1.getValue().toString();
				
				MbElement nextChild=child1.getNextSibling();
				MbElement child2=nextChild.getFirstChild();
				//System.out.println("OrderId element: "+child2.getName());
				System.out.println("RequestType : "+child1.getValue().toString());
				System.out.println("OrderId : "+child2.getValue().toString());
				String OrderId = child2.getValue().toString();
				MbElement child3=child2;
				//System.out.println("Next sibling of the node is : "+child1.getNextSibling().getName());
				for(int i=0;i<10;i++)
				{
					child3 = child3.getNextSibling();
					if(child3.getName().toString().equals("PPPOE_UserName"))
					{
						//System.out.println("loop "+i+" :"+child3.getName());
						break;
					}
					//System.out.println("loop "+i+" :"+child3.getName());
				}
				//System.out.println("User id:"+child3.getName());
				String UserId = child3.getValue().toString();
				System.out.println("UserId::"+UserId);
				
				DateFormat dateFormat = new SimpleDateFormat("E MMM dd HH:mm:ss 'IST' yyyy");
				Calendar cal = Calendar.getInstance();
				String date=dateFormat.format(cal.getTime());
				//System.out.println("Date: "+date);
	            
	        	xmlResponse1="<HDMProxyResponse><Response><RequestType>"+RequestType+"</RequestType><OrderID>"+OrderId+"</OrderID><PPPoEUserName>"+UserId+"</PPPoEUserName><Status>Fail</Status><Date>"+date+"</Date><ErrorCode>0999</ErrorCode><ErrorMessage>No Response received from HDM Proxy</ErrorMessage></Response></HDMProxyResponse>";
	        	System.out.println("Motive Response Generated is::"+xmlResponse1);
	        	//changes done for null responses 
				MbMessage inMessage= new MbMessage();
		        String inputmessage = msgAsText;
		        MbElement inRoot = inMessage.getRootElement();
		        MbElement inProperties = inRoot.getFirstChild(); 
		        MbElement inMqmd = inRoot.createElementAsLastChild("MQHMD");
		        MbElement inFormat= inMqmd.createElementAsLastChild(MbElement.TYPE_NAME_VALUE,"Format", "MQSTR");
		        MbElement inMsgBody = inRoot.createElementAsLastChildFromBitstream(inputmessage.getBytes(),"XMLNSC", "", "", "", 0,0, 0);
		        MbMessageAssembly newAssembly2 = new MbMessageAssembly(assembly, inMessage);
		        out.propagate(newAssembly2);
	        }
	        MbMessage outMessage = new MbMessage();
	        String myMsg = xmlResponse1;
	        
            
            
	        /* Converting string to MbMessage type */
	        MbElement outRoot = outMessage.getRootElement();
	        MbElement properties = outRoot.getFirstChild(); 
	        MbElement mqmd = outRoot.createElementAsLastChild("MQHMD");
	        MbElement format= mqmd.createElementAsLastChild(MbElement.TYPE_NAME_VALUE,"Format", "MQSTR");
	        MbElement msgBody = outRoot.createElementAsLastChildFromBitstream(myMsg.getBytes(),"XMLNSC", "", "", "", 0,0, 0);
	        
	        System.out.println("Format of message sent: "+ outMessage.getRootElement().getLastChild().getName());
			System.out.println("done");
			MbMessageAssembly newAssembly = new MbMessageAssembly(assembly, outMessage);
			
			MbMessage resMessage = newAssembly.getMessage();
			
		
	        out.propagate(newAssembly);
	      
	        
	        
	        System.out.println("Message Sent");
	        }
			
			// End of user code
			// ----------------------------------------------------------

			// The following should only be changed
			// if not propagating message to the 'out' terminal
			

		} catch (Throwable e) {
			// Example Exception handling	
			MbUserException mbue = new MbUserException(this, "evaluate()", "",
					"", e.toString(), null);
			throw mbue;
		}
	}

}
