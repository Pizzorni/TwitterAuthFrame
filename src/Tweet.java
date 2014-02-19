import java.io.File;
import java.io.IOException;

import twitter4j.*;
import twitter4j.auth.*;


public class Tweet {

	Twitter twitter;
	RequestToken requestToken;
	AccessToken accessToken;
	Persistence p;
	File f;

	/**
	 * Wrapper class for the twitter4j library. Simplifies using the twitter4j
	 * library.
	 * @throws TwitterException
	 */
	public Tweet() throws TwitterException{
		// Create the twitter instance
		// The factory instance is re-useable and thread safe.
		twitter = TwitterFactory.getSingleton();

		// Get the request token used to sign API requests
		requestToken = twitter.getOAuthRequestToken();

		f = new File("access");
		p = new Persistence(f);
		
		try {
			accessToken = p.readAccessToken();
			twitter.setOAuthAccessToken(accessToken);
		} catch (Exception e) {
			System.err.println("Could not retrieve access token from file");
			e.printStackTrace();
		}

	}

	/**
	 * Send a tweet.
	 * 
	 * Must have already authenticated or else many exceptions will be had
	 * @param msg
	 * @throws TwitterException
	 */
	public void tweet(String msg) throws TwitterException {
		twitter.updateStatus(msg);
	}

	/**
	 * Authenticate to the API
	 * 
	 * This method sends the authorization request to Twitter. It sets up the
	 * access token that's used to make API calls on the behalf of the user
	 * such as sending tweets 
	 * @param pin
	 * @throws TwitterException
	 */
	public void auth(String pin) throws TwitterException {
		if(pin.length() > 0){
			accessToken = twitter.getOAuthAccessToken(requestToken, pin);
		}else{
			accessToken = twitter.getOAuthAccessToken();
		}
		try {
			p.writeAccessToken(accessToken);
		} catch(IOException ioe) {
			System.err.println("Something shitty happened - could not persist the "
					+ "twitter access token");
			ioe.printStackTrace();
		}
	}
	/**
	 * Get Authorization URL
	 * 
	 * This returns the authorization URL that the user must navigate to in
	 * order to get the PIN to complete the authorization dance
	 * @return Authorization URL
	 */
	public String getAuthURL() {
		return requestToken.getAuthorizationURL();
	}
}
