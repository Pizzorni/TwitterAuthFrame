import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import java.io.File;
import java.io.IOException;

import twitter4j.*;
import twitter4j.auth.*;
import twitter4j.TwitterException;

/**
 * JFrame for the Tweet class
 * 
 * @author Marcos Mirabent
 */
public class TweetAuthFrame extends JFrame {

	private static final long serialVersionUID = 42L;

	private AsyncTwitter twitter;
//	private RequestToken requestToken;
	private AccessToken accessToken;
	private Persistence p;
	private File f;

	/**
	 * Constructor for the JFrame
	 * 
	 * @param title
	 * @throws TwitterException
	 */
	public TweetAuthFrame(String title) throws TwitterException {
		super(title);
		
		/*
		 * Act I
		 */
		
		// Containment
		Container c = this.getContentPane();
		c.setLayout(new BoxLayout(c, BoxLayout.Y_AXIS));


		// UI Elements
		JLabel authLabel = new JLabel("Please paste the following URL into a "
				+ "web browser to authorize the app to use your twitter account. "
				+ "Then paste the PIN provided below");
		final JTextField authURLTextField = new JTextField(25);
		JLabel pinLabel = new JLabel("PIN");
		final JTextField pinTextField = new JTextField(25);
		JButton authButton = new JButton("Authenticate");
		JLabel tweetLabel = new JLabel("Insert text of tweet below, if no text, a default message will be sent");
		final JTextField tweetTextField = new JTextField("40");
		final JButton tweetButton = new JButton("Tweet");
		tweetButton.setEnabled(false);
		
		/*
		 * Act II
		 */
		
		//Twitter setup
		AsyncTwitterFactory aTwitterFactory = new AsyncTwitterFactory();
		
		// Persistence object helps to persist the access token after the
		// program is closed
		f = new File("access");
		p = new Persistence(f);

		// Try to read the access token from the file
		try {
			// If you find the token, set up the twitter instance
			accessToken = p.readAccessToken();
			twitter = aTwitterFactory.getInstance(accessToken);
			
		} catch (IOException e) {
			System.err.println("Could not retrieve access token from file");
		} catch (ClassNotFoundException cnfe) {
			cnfe.printStackTrace();
		} finally {
			// if not, set up a blank twitter instance
			twitter = aTwitterFactory.getInstance();
		}
		
		
		// Add callback methods for the asynchronous twitter
		twitter.addListener(new TwitterAdapter() {
			public void updatedStatus(Status s) {
				System.out.println("Updated status - " + s.getText());
			}

			public void gotOAuthAccessToken(AccessToken token) {
				try {
					p.writeAccessToken(token);
					tweetButton.setEnabled(true);
					System.out.println("Got access token");
				} catch (IOException e) {
					System.err.println("Couldn't persist the access token");
					e.printStackTrace();
				}
			}
			
			public void gotOAuthRequestToken(RequestToken token) {
				System.out.println("Got request token");
				authURLTextField.setText(token.getAuthorizationURL());
//				requestToken = token;
			}
		});
		
		twitter.getOAuthRequestTokenAsync();
		
		
		/*
		 * Act III
		 */
		
		// Listeners
		authButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String pin = pinTextField.getText();
				if(pin.length() > 0){
					twitter.getOAuthAccessTokenAsync(pin);
				}else{
					twitter.getOAuthAccessTokenAsync();
				}
			}
		});

		tweetButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String msg;
				String textField = tweetTextField.getText();
				if(textField == "" || textField == null) {
					Random r = new Random();
					msg = "Testing - " + r.nextInt();
				} else {
					msg = textField;
				}
				twitter.updateStatus(msg);
			}
		});
		
		/*
		 * Act IV
		 */

		// Adding UI Elements
		c.add(authLabel);
		c.add(authURLTextField);
		c.add(pinLabel);
		c.add(pinTextField);
		c.add(authButton);
		c.add(tweetLabel);
		c.add(tweetTextField);
		c.add(tweetButton);
		
		/*
		 * Act V
		 */

		// Frame setup and visibility
		pack();
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
}

