import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
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
	private RequestToken requestToken;
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
		
		Container c = this.getContentPane();
		c.setLayout(new BoxLayout(c, BoxLayout.Y_AXIS));
		
		JLabel authLabel = new JLabel("Please paste the following URL into a "
			+ "web browser to authorize the app to use your twitter account. "
			+ "Then paste the PIN provided below");
		JTextField authURLTextField = new JTextField(25);
		JLabel pinLabel = new JLabel("PIN");
		final JTextField pinTextField = new JTextField(25);
		JButton authButton = new JButton("Authenticate");
		JLabel tweetLabel = new JLabel("Insert text of tweet below, if no text, a default message will be sent");
		final JTextField tweetTextField = new JTextField("40");
		JButton tweetButton = new JButton("Tweet");
		
		
		authURLTextField.setText(requestToken.getAuthorizationURL());
		
		authButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					auth(pinTextField.getText());
				} catch (TwitterException te) {
					JOptionPane.showMessageDialog(null, "An error occured while trying to authorize\n" + te, "Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		
		tweetButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String msg;
				String textField = tweetTextField.getText();
				try {
					if(textField == "" || tweetTextField.getText() == null) {
						Random r = new Random();
						msg = "Testing - " + r.nextInt();
					} else {
						msg = textField;
					}
					System.out.println(msg);
					twitter.updateStatus(msg);
				} catch (TwitterException te) {
					JOptionPane.showMessageDialog(null, "An error occured while trying to tweet\n" + te, "Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		});

		c.add(authLabel);
		c.add(authURLTextField);
		c.add(pinLabel);
		c.add(pinTextField);
		c.add(authButton);
		c.add(tweetLabel);
		c.add(tweetTextField);
		c.add(tweetButton);

		pack();
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	
	protected void setUpTwitter() throws TwitterException {
		// Create the twitter instance
		twitter = (new AsyncTwitterFactory()).getInstance();

		// Get the request token used to sign API requests
		requestToken = twitter.getOAuthRequestToken();

		// Persistence object helps to persist the access token after the
		// program is closed
		f = new File("access");
		p = new Persistence(f);

		// First, try to read the access token, this can fail any number of
		// ways. If there is no file, IOException, if there is no AccessToken
		// class for some reason, ClassNotFoundException, etc. If we can find
		// the old token set it to the Twitter object
		try {
			accessToken = p.readAccessToken();
			twitter.setOAuthAccessToken(accessToken);
		} catch (Exception e) {
			System.err.println("Could not retrieve access token from file");
			e.printStackTrace();
		}
	}
	
	protected void auth(String pin) throws TwitterException {
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
	
	public Tweet getTweetObject() {
		return tweet;
	}
}

