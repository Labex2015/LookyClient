package labex.feevale.br.looky.model;

import com.google.gson.annotations.Expose;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jeferson on 09/12/2014.
 * ticket 1.1
 */
public class User implements Serializable{
    @Expose
    private Long idUser;
    @Expose
    private String username;
    @Expose
    private String password;
    @Expose
    private String email;
    private List<Knowledge> knowledgeList;
    @Expose
    private double latitude;
    @Expose
    private double longitude;
    @Expose
    private String userKey;

    public String getUserKey() {
        return userKey;
    }

    public void setUserKey(String userKey) {
        this.userKey = userKey;
    }

    public User(String userName, String password, String email, List<Knowledge> knowledges){
        this.username = userName;
        this.password = password;
        this.email = email;
        this.knowledgeList = knowledges;

    }

    public User(Long id, String userName, String email, double latitude, double longitude){
        this.idUser         = id;
        this.username   = userName;
        this.email      = email;
        this.latitude   = latitude;
        this.longitude  = longitude;
    }

    public User(String userName, String email){
        this.username = userName;
        this.email = email;
    }

    public User() {}

    public void setId(Long id) {
        this.idUser = id;
    }

    public void setUserName(String userName) {
        this.username = userName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setKnowledgeList(List<Knowledge> knowledgeList) {
        this.knowledgeList = knowledgeList;
    }

    public void addKnowledges(Knowledge knowledge){
        if(this.knowledgeList == null)
            this.knowledgeList = new ArrayList<Knowledge>();

        this.knowledgeList.add(knowledge);
    }

    public Long getId() {
        return idUser;
    }

    public String getUserName() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public List<Knowledge> getKnowledgeList() {
        return knowledgeList;
    }

    public String getLevelById( Long id) {

        if (knowledgeList != null) {
            for (int count = 0; count < knowledgeList.size(); count++) {

                if (knowledgeList.get(count).getIdArea() == id)
                    return knowledgeList.get(count).getNivel();
            }
        }
        return null;
    }

    /**
     *
     * @param lati
     * @param longi
     * @param result M (Mlhas), K (quilometros), N (milhas nauticas)
     * @return distancia do ponto de entrada no método até o usuário
     */
    public double getDistance(double lati, double longi, char result){
        return distance(lati, longi, latitude, longitude, result);
    }

    /**
     * Created by Vitor/Thaiane on 09/12/2014.
     * ticket 1.4
     */
    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }


    //calcula distancia de um ponto do mapa para outro
    private double distance(double lat1, double lon1, double lat2, double lon2, char unit) {
        double theta = lon1 - lon2;
        double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2)) + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515;
        if (unit == 'K') {
            dist = dist * 1.609344;
        } else if (unit == 'N') {
            dist = dist * 0.8684;
        }
        return (dist);
    }
    private double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }
    private double rad2deg(double rad) {
        return (rad * 180 / Math.PI);
    }
}
