package labex.feevale.br.looky.model;

/**
 * Created by Jeferson on 10/12/2014.
 * ticket 3.1
 */
public class RequestHelp {

    Long id;
    Area area;
    String text;

    public RequestHelp(String text, Area area) {
        this.text = text;
        this.area = area;
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
}
