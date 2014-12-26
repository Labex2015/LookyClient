package labex.feevale.br.looky.utils;

/**
 * Created by PabloGilvan on 26/08/2014.
 */
public class MessageResponse {

    private String message;
    private Boolean status;

    public MessageResponse(String msg, Boolean status) {
        this.message = msg;
        this.status = status;
    }

    public MessageResponse() {
    }

    public String getMsg() {
        return message;
    }

    public void setMsg(String msg) {
        this.message = msg;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }
}
