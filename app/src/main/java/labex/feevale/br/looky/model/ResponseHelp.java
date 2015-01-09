package labex.feevale.br.looky.model;

import com.google.gson.annotations.Expose;

/**
 * Created by 0118230 on 19/12/2014.
 */

public class ResponseHelp {

    @Expose
    public String message;
    @Expose
    public Boolean status;
    @Expose
    public User user;

    public ResponseHelp() {
    }

    public ResponseHelp(String message, Boolean status, User user) {
        this.message = message;
        this.status = status;
        this.user = user;
    }
}