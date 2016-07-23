/* @copyright module */
/*                                                                            */
/* DISCLAIMER OF WARRANTIES: */
/* ------------------------- */
/* The following [enclosed] code is sample code created by IBM Corporation. */
/* This sample code is provided to you solely for the purpose of assisting */
/* you in the development of your applications. */
/* The code is provided "AS IS", without warranty of any kind. IBM shall */
/* not be liable for any damages arising out of your use of the sample code, */
/* even if they have been advised of the possibility of such damages. */

package P3_M6_MQMB_HDMPROXY_PROJECT;

public interface GeneralDefs {

	//	 -------------------------------------------------
	// LDAP tree search base
	//	 -------------------------------------------------
	public static String BASE_SEARCH = "ou=Dataone,ou=EliteProxy,ou=Dialup,o=application";

	//	 -------------------------------------------------
	//	Attributes to be fetched from EPerson object.
	//	 -------------------------------------------------
	public static final String EPERSON_ATTRIBUTE_LIST[] ={ "accessHint",
			"accountHint", "audio", "businessCategory", "c", "sn","carLicense",
			"cn", "configPtr", "departmentNumber", "description",
			"destinationIndicator", "displayName", "employeeNumber",
			"employeeType", "facsimileTelephoneNumber", "generationQualifier",
			"givenName", "homeFax", "homePhone", "homePostalAddress",
			"initials", "internationalISDNNumber", "jpegPhoto", "l",
			"labeledURI", "mail", "manager", "middleName", "mobile", "o",
			"organizationalStatus", "otherMailbox", "ou", "pager",
			"personalTitle", "photo", "physicalDeliveryOfficeName",
			"postalAddress", "postalCode", "postOfficeBox",
			"preferredDeliveryMethod", "preferredLanguage",
			"registeredAddress", "roomNumber", "secretary", "seeAlso", "st",
			"street", "telephoneNumber", "teletexTerminalIdentifier",
			"telexNumber", "thumbNailLogo", "thumbNailPhoto", "title", "uid",
			"uniqueIdentifier", "userCertificate", "userPassword",
			"userPKCS12", "userSMIMECertificate", "x121Address",
			"x500UniqueIdentifier","password","aaapolicy"
			};

	//	Using Sun JNDI..
	//
	// Initial Context Factory..
	public static String LDAP_PROVIDER = "com.sun.jndi.ldap.LdapCtxFactory";

	// Processing referrals..
	public static String REFERRALS_IGNORE = "ignore";

	//	 -------------------------------------------------
	// LDAP root pwd
	//	 -------------------------------------------------
	public static String ROOT_PASSWORD = "MBACCESS";

	//	 -------------------------------------------------
	// LDAP root: this is just cn=root
	//	 -------------------------------------------------
	public static String ROOT_USER = "uid=Mbwrite,cn=users,o=application";

	// Security level to be used for LDAP connections..
	public static String SEARCH_SECURITY_LEVEL = "none";

	// security level for updates.
	public static String UPDATE_SECURITY_LEVEL = "simple";

	// Package Prefix for loading URL context factories..
	public static String URL_CONTEXT_PREFIX = "com.sun.jndi.url";
	public static String LDAP_URL = "ldap://10.16.51.86:389";
}
