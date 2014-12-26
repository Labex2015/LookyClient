package labex.feevale.br.looky.model;

import com.google.gson.annotations.Expose;

/**
 * Created by Jeferson on 09/12/2014.
 * ticket 1.1
 */
public class Area {
    @Expose
    private Long id;
    @Expose
    private String name;

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        if(id == null)
            this.id = 0L;
        return id;
    }

    public String getName() {
        return name;
    }
}
