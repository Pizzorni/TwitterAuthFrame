import twitter4j.*;
import twitter4j.auth.*;

public class Tweet {

	Twitter twitter;
	RequestToken requestToken;
	AccessToken accessToken;

	public Tweet() throws TwitterException{
		// Create the twitter instance
		// The factory instance is re-useable and thread safe.
		twitter = TwitterFactory.getSingleton();
		
		// App credentials should go in a file
		twitter.setOAuthConsumer("yExf2BzchIyysn62hzMuog", "hhFzCgJnDCGniK5CuzpxmJZgOMECmvcCjwhjZic");
		
		requestToken = twitter.getOAuthRequestToken();

	}

	public void tweet(String msg) throws TwitterException {
		twitter.updateStatus(msg);
	}
	
	public void auth(String pin) throws TwitterException {
				if(pin.length() > 0){
					accessToken = twitter.getOAuthAccessToken(requestToken, pin);
				}else{
					accessToken = twitter.getOAuthAccessToken();
				}
	}
	
	public String getAuthURL() {
		return requestToken.getAuthorizationURL();
	}
}
