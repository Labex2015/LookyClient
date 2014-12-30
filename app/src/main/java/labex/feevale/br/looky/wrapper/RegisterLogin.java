package labex.feevale.br.looky.wrapper;

import com.google.gson.annotations.Expose;

/**
 * Created by Jeferson on 30/12/2014.
 */
public class RegisterLogin
{
    @Expose
    String email;
    @Expose
    String password;
    @Expose
    String username;
    @Expose
    String userKey;

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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserKey() {
        return userKey;
    }

    public void setUserKey(String userKey) {
        this.userKey = userKey;
    }
}
