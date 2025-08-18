package Player;

public class Credentials {
    private String email;
    private String password;

    public Credentials(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String toString() {
        return "    Email: " + email + "\n" + "    Password: " + password;
    }

    public String getPassword() {
        return password;
    }
}
