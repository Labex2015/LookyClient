package labex.feevale.br.looky.model;

import java.io.Serializable;

/**
 * Created by PabloGilvan on 21/12/2014.
 */
public class ChatResponse implements Serializable{

    private Long idFrom;
    private String userFrom;
    private Double longitude;
    private Double latitude;
    private String date;
    private String text;

    public ChatResponse() {}

    public ChatResponse(Long idFrom, String userFrom, Double longitude, Double latitude, String date, String text) {
        this.idFrom = idFrom;
        this.userFrom = userFrom;
        this.longitude = longitude;
        this.latitude = latitude;
        this.date = date;
        this.text = text;
    }

    public Long getIdFrom() {
        return idFrom;
    }

    public void setIdFrom(Long idFrom) {
        this.idFrom = idFrom;
    }

    public String getUserFrom() {
        return userFrom;
    }

    public void setUserFrom(String userFrom) {
        this.userFrom = userFrom;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
