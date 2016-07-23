package com.capgemini.test;

import org.json.JSONObject;

import com.ibm.broker.javacompute.MbJavaComputeNode;
import com.ibm.broker.plugin.MbBLOB;
import com.ibm.broker.plugin.MbElement;
import com.ibm.broker.plugin.MbException;
import com.ibm.broker.plugin.MbMessage;
import com.ibm.broker.plugin.MbMessageAssembly;
import com.ibm.broker.plugin.MbOutputTerminal;

public class SampleTest_JavaCompute extends MbJavaComputeNode {

	public void evaluate(MbMessageAssembly inAssembly) throws MbException {
		MbOutputTerminal out = getOutputTerminal("out");
		// MbOutputTerminal alt = getOutputTerminal("alternate");

		MbMessage inMessage = inAssembly.getMessage();
		MbMessageAssembly outAssembly = null;
		try {
			// create new message as a copy of the input
			MbMessage outMessage = new MbMessage();
			copyMessageHeaders(inMessage, outMessage);
			outAssembly = new MbMessageAssembly(inAssembly, outMessage);
			// ----------------------------------------------------------
			// Add user code below
			byte[] msg = (byte[]) inMessage.getRootElement()
					.getFirstElementByPath("/BLOB/BLOB").getValue();
			String jsonmsg = new String(msg);

			/*MbMessage envMsg = inAssembly.getLocalEnvironment();
			MbMessage newEnvMsg = new MbMessage(envMsg);
			byte[] reqID = (byte[]) inAssembly.getGlobalEnvironment()
					.getRootElement()
					.getFirstElementByPath("/SaveState/requestIdentifier")
					.getValue();
			newEnvMsg.getRootElement().createElementAsLastChild("Destination")
					.createElementAsLastChild("HTTP")
					.createElementAsLastChild("RequestIdentifier")
					.setValue(reqID);*/
            outMessage.getRootElement().getFirstElementByPath("/Properties/ContentType").setValue("application/json");
			JSONObject jsonObject = new JSONObject(jsonmsg);
			String value = (String) jsonObject.get("Name");
			System.out.println("The Value is ::: " + value);
			JSONObject obJsonObject = new JSONObject();
			obJsonObject.put("Greetings", "Hello Mr. " + value);
			outMessage
					.getRootElement()
					.createElementAsLastChild(MbBLOB.PARSER_NAME)
					.createElementAsLastChild(MbElement.TYPE_NAME_VALUE,
							"BLOB", obJsonObject.toString().getBytes());
			outAssembly = new MbMessageAssembly(inAssembly, outMessage);

			// End of user code
			// ----------------------------------------------------------
		} /*
		 * catch (MbException e) { // Re-throw to allow Broker handling of
		 * MbException throw e; } catch (RuntimeException e) { // Re-throw to
		 * allow Broker handling of RuntimeException throw e; }
		 */catch (Exception e) {
			// Consider replacing Exception with type(s) thrown by user code
			// Example handling ensures all exceptions are re-thrown to be
			// handled in the flow
			// throw new MbUserException(this, "evaluate()", "", "",
			// e.toString(),
			// null);
			e.printStackTrace();
		}
		// The following should only be changed
		// if not propagating message to the 'out' terminal
		out.propagate(outAssembly);

	}

	public void copyMessageHeaders(MbMessage inMessage, MbMessage outMessage)
			throws MbException {
		MbElement outRoot = outMessage.getRootElement();
		MbElement header = inMessage.getRootElement().getFirstChild();

		while (header != null && header.getNextSibling() != null) {
			/*if(header.getName().equalsIgnoreCase("ContentType")){
				header.setValue("application/json");
			}*/
			outRoot.addAsLastChild(header.copy());
			header = header.getNextSibling();
		}
	}

}
