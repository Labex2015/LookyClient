package labex.feevale.br.looky.model;

import com.google.gson.annotations.Expose;

/**
 * Created by Jeferson on 09/12/2014.
 * ticket 1.1
 */
public class Knowledge {
    @Expose
    private Long idUser;
    private Long idArea;
    @Expose
    private String nivel;

    @Expose
    private Area area;
    private User user;

    public static final String INITIATE = "Initiate";
    public static final String INTERMEDIARY = "Intermediary";
    public static final String ADVANCED = "Advanced";

    public Knowledge() {
    }

    public void setIdUser(Long idUser) {
        this.idUser = idUser;
    }

    public void setNivel(String nivel) {
        this.nivel = nivel;
    }

    public void setIdArea(Long idArea) {
        this.idArea = idArea;
    }

    public void setArea(Area area) {
        this.area = area;
        this.idArea = area.getId();
    }

    public void setUser(User user) {
        this.user = user;
        this.idUser = user.getId();
    }

    public Long getIdUser() {
        return idUser;
    }

    public Long getIdArea() {
        return idArea;
    }

    public String getNivel() {
        return nivel;
    }

    public Area getArea() {
        return area;
    }

    public User getUser() {
        return user;
    }
}
