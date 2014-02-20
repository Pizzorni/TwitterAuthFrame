import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.IOException;

import twitter4j.auth.AccessToken;


/**
 * Persist Access Tokens
 * @author Marcos
 *
 */
public class Persistence {

	File f;
	/**
	 * Creates a new Persistence object using File f as the storage location
	 * @param f
	 */
	public Persistence(File f) {
		this.f = f;
	}
	
	/**
	 * This opens the file f fir writing, writes the AccessToken to it and closes the file
	 * @param at
	 * @throws IOException
	 */
	public void writeAccessToken(AccessToken at) throws IOException {
		ObjectOutputStream oos = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(f)));
		oos.writeObject(at);
		oos.close();
	}
	
	/**
	 * Opens the file f for reading, reads the AccessToken object from the file and closes the file
	 * @return the users access token
	 * @throws Exception
	 */
	public AccessToken readAccessToken() throws Exception {
		ObjectInputStream ois = new ObjectInputStream(new BufferedInputStream(new FileInputStream(f)));
		AccessToken at = (AccessToken)ois.readObject();
		ois.close();
		return at;
	}

}
