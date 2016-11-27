package ejb;

import javax.ejb.Remove;
import javax.ejb.Stateful;

@Stateful
public class Connexion {

	private boolean connected = false;
	private String hashPwd = null;
	private String id = null;

	@Remove
	public void clientDisconnect() {
		disconnect();
	}

	public void connect(String id, String hashPwd) {
		this.connected = true;
		this.hashPwd = hashPwd;
		this.id = id;
	}

	public void disconnect() {
		this.connected = false;
		this.hashPwd = null;
		this.id = null;
	}

	public boolean getConnected() {
		return this.connected;
	}

	public String getHashPwd() {
		return this.hashPwd;
	}

	public String getId() {
		return this.id;
	}
}
