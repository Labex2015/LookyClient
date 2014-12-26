package labex.feevale.br.looky.service.utils;

import labex.feevale.br.looky.utils.MessageResponse;

/**
 * Created by 0126128 on 23/12/2014.
 */
public interface ProcessChat {

    public void executeActionSuccess();
    public void executeActionFail(MessageResponse messageResponse);
}
