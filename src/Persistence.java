import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.IOException;

import twitter4j.auth.AccessToken;

public class Persistence {

	File f;
	
	public Persistence(File f) {
		this.f = f;
	}
	
	public void writeAccessToken(AccessToken at) throws IOException {
		ObjectOutputStream oos = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(f)));
		oos.writeObject(at);
		oos.close();
	}
	
	public AccessToken readAccessToken() throws Exception {
		ObjectInputStream ois = new ObjectInputStream(new BufferedInputStream(new FileInputStream(f)));
		AccessToken at = (AccessToken)ois.readObject();
		ois.close();
		return at;
	}

}
