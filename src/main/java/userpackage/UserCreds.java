package userpackage;

public class UserCreds {
    private String email;
    private String password;

    public UserCreds() {
    }

    public UserCreds(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public static UserCreds from(User user) {
        return new UserCreds(user.getEmail(), user.getPassword());
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
