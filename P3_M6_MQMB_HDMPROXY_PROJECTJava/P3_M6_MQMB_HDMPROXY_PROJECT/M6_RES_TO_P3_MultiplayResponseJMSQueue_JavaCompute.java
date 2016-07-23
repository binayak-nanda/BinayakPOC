package P3_M6_MQMB_HDMPROXY_PROJECT;
import java.util.Hashtable;
import javax.jms.JMSException;
//import javax.jms.Message;
import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.QueueSender;
import javax.jms.QueueSession;
import javax.jms.Session;
import javax.jms.TextMessage;



import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import com.ibm.broker.javacompute.MbJavaComputeNode;
import com.ibm.broker.plugin.*;
//import com.ibm.jvm.Trace;
public class M6_RES_TO_P3_MultiplayResponseJMSQueue_JavaCompute extends
		MbJavaComputeNode {

	public void evaluate(MbMessageAssembly assembly) throws MbException {
		MbOutputTerminal out = getOutputTerminal("out");
//		MbOutputTerminal alt = getOutputTerminal("alternate");

		MbMessage message = assembly.getMessage();
		
		System.out.println("assembly message  is: "+assembly);
		System.out.println("Modified for target Process Server");
		
	/*System.out.println("message recieved: "+message.getRootElement().getLastChild().toString());
	System.out.println("last child value: "+message.getRootElement().getLastChild());
	*/
		
		//code added for manipulating the data
		
		
		System.out.println("message recieved: "+message.getRootElement().getLastChild().getName());
		MbElement parentNode = message.getRootElement().getLastChild().getFirstChild();
		System.out.println("parent name: "+parentNode.getName());
		MbElement child1=parentNode.getFirstChild();
		System.out.println("First element name: "+child1.getName());
//		System.out.println("First element value: "+child1.getValue());
		MbElement lastChild=parentNode.getLastChild();
//		System.out.println("Next sibling of the node is : "+child1.getNextSibling().getName());
		
		System.out.println("Before while loop value of node is: "+parentNode);
		String  rootName=null;
		String rootValue=null;
		String output=null;
		String chileAppend=null;
		String noderoot=null;
		String child1Name=null;
		String child1Value=null;
		String child1StartTag=null;
		String child1EndTag=null;
		while(parentNode!=null)
		{
			rootName=parentNode.getName();
			rootValue=(String)parentNode.getValue();
			output="<"+rootName+">";
			noderoot="</"+rootName+">";
			System.out.println("in the while loop: "+parentNode.getName());
			while(child1!=null)
	      {
			if(child1.getFirstChild()!=null)
			{
				System.out.println("child1 has a child");
				MbElement child2=child1.getFirstChild();
				child1Name=child1.getName();
				child1Value=(String)child1.getValue();
				child1StartTag="<"+child1Name+">";
				output=output.concat(child1StartTag);
				child1EndTag="</"+child1Name+">";
//				System.out.println("in the while loop: "+parentNode.getName());
				while(child2!=null)
				{
					System.out.println("in child2 while loop: "+child2.getName());
//			        outRoot.addAsLastChild(header.copy());
					String chile2Append ="<"+child2.getName()+">"+child2.getValue()+"</"+child2.getName()+">";
					System.out.println("value of child append: "+chileAppend);
					output = output.concat(chile2Append);
					System.out.println("value of output append: "+output);
//					child = child.getNextSibling();
//					System.out.println("value of NEXT Sibling: "+child.getName());
//			        System.out.println("value of child: "+child);
					System.out.println("value of child: "+child2.getName());
			        System.out.println("value of lastchild: "+child1.getLastChild().getName());
			        if(!(child2.getName().equalsIgnoreCase(child1.getLastChild().getName())))
			        { 
			        	System.out.println("in if");
			        	child2=child2.getNextSibling();
			        	System.out.println("value of NEXT Sibling: "+child2.getName());
				        System.out.println("value of child: "+child2);
			        }else{
			        	System.out.println("in else");
			        	child2=null;}
				}
				
				System.out.println("value of output outside 2nd while : "+output);
				output = output.concat(child1EndTag);
				System.out.println("final value of output:"+output);
				child1 =child1.getNextSibling();
				
			}
			else
			{
				
					System.out.println("in the while loop: "+child1.getName());
//			        outRoot.addAsLastChild(header.copy());
					chileAppend ="<"+child1.getName()+">"+child1.getValue()+"</"+child1.getName()+">";
					System.out.println("value of child append: "+chileAppend);
					output = output.concat(chileAppend);
					System.out.println("value of output append: "+output);
//					child = child.getNextSibling();
//					System.out.println("value of NEXT Sibling: "+child.getName());
//			        System.out.println("value of child: "+child);
					System.out.println("value of child: "+child1.getName());
			        System.out.println("value of lastchild: "+parentNode.getLastChild().getName());
			        if(!(child1.getName().equalsIgnoreCase(parentNode.getLastChild().getName())))
			        { 
			        	System.out.println("in if");
			        	child1=child1.getNextSibling();
			        	System.out.println("value of NEXT Sibling: "+child1.getName());
				        System.out.println("value of child: "+child1);
			        }else{
			        	System.out.println("in else");
			        	child1=null;}
				
				
			}
			}
			
	        
			System.out.println("value of output outside 2nd while : "+output);
			output = output.concat(noderoot);
			System.out.println("final value of output:"+output);
			parentNode =parentNode.getNextSibling(); 
	      }	
		
		QueueConnectionFactory qcf = null;
		QueueConnection qc = null;
		Queue q = null;
		QueueSession qsess = null;
		QueueSender qsndr = null;
		
		
		/*String QCF_NAME = "MultiplayJMSConnectionFactory";
		String QUEUE_NAME = "MultiplayResponseJMSQueue";
		
		Hashtable env =  new Hashtable();
		InitialContext ctx = null;
		env.put(Context.INITIAL_CONTEXT_FACTORY, "weblogic.jndi.WLInitialContextFactory");
		env.put(Context.PROVIDER_URL, "t3://10.16.51.45:7001");*/
		
		String QCF_NAME = "jms/MPLYQCF";
		String QUEUE_NAME = "CDR_MPLY.CDRExport_RECEIVE_D";
		String SECU = "none"; 
		
		
		Hashtable env =  new Hashtable();
		InitialContext ctx = null;
		env.put(Context.INITIAL_CONTEXT_FACTORY, "com.ibm.websphere.naming.WsnInitialContextFactory");
		//WPS test environment connectivity
		/*env.put(Context.PROVIDER_URL, "iiop://bgl-svr-tod-03.bsnl.net.in:2809");*/
		
		//WPS production environment connectivity details
		env.put(Context.PROVIDER_URL, "iiop://bgl-svr-was-01.bsnl.net.in:9810");
//		env.put(Context.PROVIDER_URL, "iiop://10.16.54.48:9810");
		
		//env.put(Context.SECURITY_AUTHENTICATION,SECU);
		try {
			ctx = new InitialContext(env);
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace(System.err);
			System.exit(0);
		}
		System.out.println("Got InitialContext " + ctx.toString());
		//create queue connection factory
		try {
			System.out.println("Trying to connect QueueConnectionFactory");
			qcf = (QueueConnectionFactory)ctx.lookup(QCF_NAME);
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			System.out.println("exception in geting qcf "+ e);
			e.printStackTrace(System.err);
			System.exit(0);
		}
		System.out.println("Got QueueConnectionFactory " + qcf.toString());
		
		//create queue connection
		try {
			qc = qcf.createQueueConnection();
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace(System.err);
			System.exit(0);
		}
		System.out.println("Got QueueConnection " + qc.toString());
		
		//create queue session
		try {
			qsess = qc.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace(System.err);
			System.exit(0);
		}
		
		System.out.println("Got QueueSession " + qsess.toString());
		//lookup queue
		try {
			q = (Queue)ctx.lookup(QUEUE_NAME);
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace(System.err);
			System.exit(0);
		}
		System.out.println("Got Queue " + q.toString());
		
//		 create QueueSender
		try {
		qsndr = qsess.createSender(q);
		} catch (JMSException jmse) {
		jmse.printStackTrace(System.err);
		System.exit(0);
		}
		System.out.println("Got QueueSender " + qsndr.toString());
		
		//create message
		TextMessage txtMsg = null;
		try {
			txtMsg = qsess.createTextMessage();
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace(System.err);
			System.exit(0);
		}
		System.out.println("Got TextMessage " + txtMsg.toString());
		
		try {
			txtMsg.setText(output);
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace(System.err);
			System.exit(0);
		}
		
		System.out.println("Set text in TextMessage " + txtMsg.toString());
		
		
		try {
			qsndr.send(txtMsg);
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace(System.err);
			System.exit(0);
		}
		try {
			txtMsg = null;
			qsndr.close();
			qsndr = null;
			q = null;
			qsess.close();
			qsess = null;
			qc.close();
			qc = null;
			qcf = null;
			ctx = null;
			} catch (JMSException jmse) {
			jmse.printStackTrace(System.err);
			System.exit(0);
			}
			System.out.println("Cleaned up and done.");
			System.out.println("Message sent to Process Server Queue");
			}
		
		
		
	
		

		// ----------------------------------------------------------
		// Add user code below

		// End of user code
		// ----------------------------------------------------------

		// The following should only be changed
		// if not propagating message to the 'out' terminal

//		out.propagate(txt);
	
public static  void main(String arg[])
{
	M6_RES_TO_P3_MultiplayResponseJMSQueue_JavaCompute test=new M6_RES_TO_P3_MultiplayResponseJMSQueue_JavaCompute();
	System.out.println("This  is  the  main method");
}
}

	