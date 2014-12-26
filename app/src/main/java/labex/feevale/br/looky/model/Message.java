package labex.feevale.br.looky.model;

import com.google.gson.annotations.Expose;

import java.util.Date;

/**
 * Created by 0126128 on 23/12/2014.
 */
public class Message {

    @Expose
    private Long idFrom;
    @Expose
    private Long idTo;
    @Expose
    private String text;
    @Expose
    private Date date;

    public Message() {}

    public Message(Long idFrom, Long idTo, String text, Date date) {
        this.idFrom = idFrom;
        this.idTo = idTo;
        this.text = text;
        this.date = date;
    }

    public Long getIdFrom() {
        return idFrom;
    }

    public void setIdFrom(Long idFrom) {
        this.idFrom = idFrom;
    }

    public Long getIdTo() {
        return idTo;
    }

    public void setIdTo(Long idTo) {
        this.idTo = idTo;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
