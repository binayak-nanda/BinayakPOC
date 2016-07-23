package P3_M6_MQMB_HDMPROXY_PROJECT;

import java.io.IOException;
import java.util.Properties;

import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.BasicAttribute;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.naming.directory.ModificationItem;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;



public class UpdateLdap implements GeneralDefs {

	public boolean updateAttribute(String updateDN, String attrName,
			String attrVal, boolean bRemoveAttribute) {
		boolean bUpdatedIt = false;
		// must specify a relative DN, attribute name and value.
		if ((MiscFuncs.isStringEmpty(updateDN))|| (MiscFuncs.isStringEmpty(attrName))|| (MiscFuncs.isStringEmpty(attrVal)))
		{
			System.out.println("You must specify a DN, attribute name and attribute value.");
			return bUpdatedIt;
		}
		DirContext ctx = null;
		try {
			System.out.print("Update attribute for " + updateDN + " attr: "	+ attrName + ": " + attrVal);
			ctx = connectLDAP(true);
			System.out.println("*********context-----"+ctx);
			// Search the named object and all of its descendants.
			SearchControls constraints = new SearchControls();
			constraints.setSearchScope(SearchControls.SUBTREE_SCOPE);

			// Retrieve the specified attributes only..
			constraints.setReturningAttributes(EPERSON_ATTRIBUTE_LIST);

			// Search the context specified in the String object "base".
			System.out.println("BASE SEARCH IS %%%%%%"+BASE_SEARCH);
			NamingEnumeration results = ctx.search(BASE_SEARCH, updateDN,constraints);
			if (results.hasMoreElements())
			{
				SearchResult sr = (SearchResult) results.next();
				System.out.println("search result......................."+sr);
				// Get all available attribute types and their associated
				Attributes attributes = sr.getAttributes();

				// Specify the changes to make
				ModificationItem[] mods = new ModificationItem[1];
				// -------------------------------------------
				// remove the attribute
				// -------------------------------------------
				if (bRemoveAttribute) {
					System.out.println("*******inside remove attribute*************");
					// eliminate the attribute
					if (null != attributes.get(attrName)) {
						mods[0] = new ModificationItem(
								DirContext.REMOVE_ATTRIBUTE,
								new BasicAttribute(attrName, attrVal));
						System.out.println("************************************1");
					}
				}
				// -------------------------------------------
				// replace or add the attribute
				// -------------------------------------------
				else {
					System.out.println("************************************2");
					// do a replace of the attribute
					if (null != attributes.get(attrName)) {
						
						mods[0] = new ModificationItem(
								DirContext.REPLACE_ATTRIBUTE,
								new BasicAttribute(attrName, attrVal));
						System.out.println("************************************3");
					}
					// add the attribute
					else {
						mods[0] = new ModificationItem(
								DirContext.ADD_ATTRIBUTE, new BasicAttribute(
										attrName, attrVal));
						System.out.println("************************************4");
					}
				}
System.out.println("***********************************5.0");
				ctx.modifyAttributes(updateDN + "," + BASE_SEARCH, mods);
				System.out.println("************************************5");
				ctx.close();
				// -------------------------------------------
				// do a search just to see the updated value
				//bUpdatedIt = searchAttribute(attrName, attrVal);
				bUpdatedIt = true;
				if (bUpdatedIt && !bRemoveAttribute) {
System.out.println("Entry was updated.");
				}
				if (!bUpdatedIt && bRemoveAttribute) {
					bUpdatedIt = true;
					System.out.println("Entry was removed.");
				}
			} else {
				System.out.println(" has returned no results..");
			}
		} catch (Exception e) {
			System.out.println("************************************6");
			System.out.println("Exception : " + e.toString());
				bUpdatedIt = false;
		}
		// always close the directory context when you are done
		finally {
			try {
				if (null != ctx)
					ctx.close();
			} catch (Exception e2) {
			}
		}
	//	System.out.println("--------------------------------------------");
	System.out.println("Updated it " + bUpdatedIt);
		return bUpdatedIt;
	}

private DirContext connectLDAP(boolean secureConnection)
	throws NamingException {
	DirContext ctx = null;
	// -----------------------------------------------
	// Set up the environment for creating the initial
	// context.
	// -----------------------------------------------
	Properties props = new Properties();
	props.setProperty(Context.INITIAL_CONTEXT_FACTORY, LDAP_PROVIDER);
	props.setProperty(Context.PROVIDER_URL, LDAP_URL);
	props.setProperty(Context.URL_PKG_PREFIXES, URL_CONTEXT_PREFIX);
	props.setProperty(Context.REFERRAL, REFERRALS_IGNORE);
	if (secureConnection) {
		System.out.println("$$$$$It's a Secure Connection$$$$$$$$$");
		props.setProperty(Context.SECURITY_AUTHENTICATION,UPDATE_SECURITY_LEVEL);
		// --------------------------------------------------
		//specify the root username
		// --------------------------------------------------
		String rootuser=new String("uid=Mbwrite,cn=users,o=application");
		props.setProperty(Context.SECURITY_PRINCIPAL, rootuser);
		System.out.println("rootuser@@@@@@@@@@@"+rootuser);
		//--------------------------------------------------
		//specify the root password
		// --------------------------------------------------
		props.setProperty(Context.SECURITY_CREDENTIALS, ROOT_PASSWORD);
		//--------------------------------------------------
		System.out.println("GENERAL DEFS ROOT USER"+ROOT_USER);
	}
	// search does not need root user id and password.
	else {
		props.setProperty(Context.SECURITY_AUTHENTICATION,SEARCH_SECURITY_LEVEL);

	}
	// -----------------------------------------------
	// Get the environment properties (props) for
	// creating initial context and specifying LDAP
	// service provider parameters.
	// -----------------------------------------------
	System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@1"+props.getProperty(Context.INITIAL_CONTEXT_FACTORY));
	System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@1"+props.getProperty(Context.PROVIDER_URL));
	System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@1"+props.getProperty(Context.SECURITY_PRINCIPAL));
	System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@1"+props.getProperty(Context.SECURITY_CREDENTIALS));
	System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@1"+props.getProperty(Context.SECURITY_AUTHENTICATION));
	
	ctx = new InitialDirContext(props);
	return ctx;
}

}
