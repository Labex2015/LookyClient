package labex.feevale.br.looky.wrapper;

import com.google.gson.annotations.Expose;

import labex.feevale.br.looky.model.User;

/**
 * Created by Vitor/Thaiane on 12/12/2014.
 */
public class HelpWrapper {

    @Expose
    public User user;
    @Expose
    public String[] searchTerms;
    @Expose
    public String text;

}
