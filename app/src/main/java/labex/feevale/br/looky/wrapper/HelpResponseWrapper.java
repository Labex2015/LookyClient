package labex.feevale.br.looky.wrapper;

import labex.feevale.br.looky.model.User;

/**
 * Created by 0126128 on 15/12/2014.
 */
public class HelpResponseWrapper {

    public User user;
    public String[] searchTerms;

    public HelpResponseWrapper() {}

    public HelpResponseWrapper(User user, String[] searchTerms) {
        this.user = user;
        this.searchTerms = searchTerms;
    }

}
