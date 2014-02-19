import twitter4j.*;
import twitter4j.auth.*;

public class Tweet {

	Twitter twitter;
	RequestToken requestToken;
	AccessToken accessToken;

	/**
	 * Wrapper class for the twitter4j library. Simplifies using the twitter4j
	 * library.
	 * @throws TwitterException
	 */
	public Tweet() throws TwitterException{
		// Create the twitter instance
		// The factory instance is re-useable and thread safe.
		twitter = TwitterFactory.getSingleton();
		
		// App credentials should go in a file, set up the API Key and Secret
		twitter.setOAuthConsumer("yExf2BzchIyysn62hzMuog", "hhFzCgJnDCGniK5CuzpxmJZgOMECmvcCjwhjZic");
		
		// Get the request token used to sign API requests
		requestToken = twitter.getOAuthRequestToken();

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
