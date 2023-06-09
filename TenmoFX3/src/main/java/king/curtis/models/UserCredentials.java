package king.curtis.models;

public class UserCredentials {

	private String username;
	private String password;

	public UserCredentials(String username, String password) {
		this.username = username;
		this.password = password;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@java.lang.Override
	public java.lang.String toString() {
		return "UserCredentials{" +
				"username='" + username + '\'' +
				", password='" + password + '\'' +
				'}';
	}
}


