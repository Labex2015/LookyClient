package labex.feevale.br.looky.model;

import java.util.List;

/**
 * Created by 0146596 on 11/12/2014.
 */
public class Talk {

    private int id;
    private List<String> conversa;
    private List<User> u;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<String> getConversa() {
        return conversa;
    }

    public void setConversa(List<String> conversa) {
        this.conversa = conversa;
    }

    public List<User> getU() {
        return u;
    }

    public void setU(List<User> u) {
        this.u = u;
    }
}
