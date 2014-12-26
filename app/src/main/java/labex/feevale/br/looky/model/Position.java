package labex.feevale.br.looky.model;

/**
 * Created by 0146596 on 10/12/2014.
 */
public class Position {

    private double latitude;
    private double longitude;
    private double acuracy;

    public double getAcuracy() {
        return acuracy;
    }

    public void setAcuracy(double acuracy) {
        this.acuracy = acuracy;
    }

    public double getLatitude() {
        return this.latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return this.longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}
