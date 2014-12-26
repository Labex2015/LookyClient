package labex.feevale.br.looky.service.utils;

import android.widget.BaseAdapter;
import labex.feevale.br.looky.utils.MessageResponse;

/**
 * Created by 0139612 on 17/12/2014.
 */
public interface ProcessMessage {
    public MessageResponse getResponse();
    public void notifyAdapterChanged(BaseAdapter adapter);
}
