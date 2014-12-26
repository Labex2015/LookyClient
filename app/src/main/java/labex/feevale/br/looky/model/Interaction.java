package labex.feevale.br.looky.model;

/**
 * Created by 0146596 on 11/12/2014.
 */
public class Interaction {

    Long idRequestor;
    Long idHelper;
    Long id;
    Boolean status;

    public Long getIdRequestor() {
        return idRequestor;
    }

    public void setIdRequestor(Long idRequestor) {
        this.idRequestor = idRequestor;
    }

    public Long getIdHelper() {
        return idHelper;
    }

    public void setIdHelper(Long idHelper) {
        this.idHelper = idHelper;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }
}
