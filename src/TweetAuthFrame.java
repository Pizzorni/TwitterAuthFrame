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


import twitter4j.TwitterException;

/**
 * JFrame for the Tweet class
 * 
 * @author Marcos Mirabent
 */
public class TweetAuthFrame extends JFrame {

	private static final long serialVersionUID = 42L;
	private Tweet tweet;
	
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
		
		tweet = new Tweet();
		
		authURLTextField.setText(tweet.getAuthURL());
		
		authButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					tweet.auth(pinTextField.getText());
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
					tweet.tweet(msg);
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
	
	public Tweet getTweetObject() {
		return tweet;
	}
}

