package labex.feevale.br.looky.model;

import java.io.Serializable;

/**
 * Created by Jeferson on 10/12/2014.
 * ticket 3.1
 */
public class RequestHelp implements Serializable{

    Long id;
    Area area;
    String text;
    User user;

    public RequestHelp(Area area, String text) {
        this.area = area;
        this.text = text;
    }

    public RequestHelp(Long id, Area area, String text, User user) {
        this.id = id;
        this.area = area;
        this.text = text;
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Area getArea() {
        return area;
    }

    public void setArea(Area area) {
        this.area = area;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
