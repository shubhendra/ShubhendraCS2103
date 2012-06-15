/** Class which authenticates the user with the javaMail.
 *
 * 
 * @author Nirav Gandhi
 */
package sendMail;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

public class SocialAuth extends Authenticator{
	private String FROM_ADDRESS;
	private String PASSWORD;
/**Constructor
 * 
 */
	
public SocialAuth(String username,String password){
	FROM_ADDRESS=username;
	PASSWORD=password;
}
/** Authenticates the user.
 * 
 * @param FROM_ADDRESS the email address from where the mail is to be sent
 * @param pass password of the username
 * @return the authentication
 */

	protected PasswordAuthentication getPasswordAuthentication(String FROM_ADDRESS,String pass){
		return new PasswordAuthentication(FROM_ADDRESS,pass);
	}
}
