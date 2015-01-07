package labex.feevale.br.looky.service;

import android.content.Context;
import android.widget.Toast;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ConnectionPoolTimeoutException;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import labex.feevale.br.looky.model.ServiceError;
import labex.feevale.br.looky.service.utils.BaseServiceAction;
import labex.feevale.br.looky.service.utils.HttpGetWithEntity;
import labex.feevale.br.looky.utils.AppHelp;
import labex.feevale.br.looky.utils.AppVariables;
import labex.feevale.br.looky.utils.JsonUtils;
import labex.feevale.br.looky.utils.MessageResponse;

/**
 * Created by PabloGilvan on 06/01/2015.
 */
public abstract class BaseHandler<T> {

    public final static int GET = 1;
    public final static int POST = 2;
    public static final int SERVICE = 100;
    public static final int TASK = 200;


    private T entity;
    private String params;
    private String url;
    private String response;
    private Context context;
    private MessageResponse messageResponse;
    private JsonUtils jsonUtils;
    private Integer type;
    private Integer methodConnection;
    private BaseServiceAction serviceAction;

    public BaseHandler(T entity, String url, Context context, Integer type, Integer methodConnection) {
        this.entity = entity;
        this.url = url;
        this.context = context;
        this.type = type;
        this.methodConnection = methodConnection;
        this.jsonUtils = new JsonUtils<T>(entity);
        messageResponse = new MessageResponse("", false);
    }

    public BaseHandler(T entity, String url, Context context, Integer type,
                       Integer methodConnection, BaseServiceAction serviceAction) {
        this(entity, url, context, type, methodConnection);
        this.serviceAction = serviceAction;
    }

    protected BaseHandler(T entity, String params, String url, Context context,
                          Integer type, Integer methodConnection) {
        this(entity, url, context, type, methodConnection);
        this.params = params;
    }

    protected BaseHandler(T entity, String params, String url, Context context,
                          MessageResponse messageResponse, Integer type, Integer methodConnection,
                                          BaseServiceAction baseServiceAction) {
        this(entity, url, context, type, methodConnection, baseServiceAction);
        this.params = params;
        this.messageResponse = messageResponse;
        this.serviceAction = baseServiceAction;
    }


    private Boolean validateConnection(){
        this.messageResponse.setMsg("Sem conexão com a internet.");
        this.messageResponse.setStatus(false);
        return new AppHelp(context).validateConnection();
    }

   public void makeServiceCall(){
        try{
            if(validateConnection()) {
                HttpParams httpParams = new BasicHttpParams();
                HttpConnectionParams.setConnectionTimeout(httpParams, AppVariables.HTTP_TIME_OUT);
                HttpConnectionParams.setSoTimeout(httpParams, AppVariables.SOCKET_TIME_OUT);
                DefaultHttpClient httpClient = new DefaultHttpClient(httpParams);
                HttpEntity httpEntity = null;
                HttpResponse httpResponse = null;
                if (methodConnection == POST) {
                    HttpPost httpPost = new HttpPost(url);
                    if (params != null) {
                        StringEntity se = new StringEntity(params.toString(), HTTP.UTF_8);
                        se.setContentEncoding("UTF-8");
                        se.setContentType("application/json; charset=UTF-8");
                        httpPost.setEntity(se);
                    }
                    httpResponse = httpClient.execute(httpPost);
                } else if (methodConnection == GET) {
                    if (params != null) {
                        HttpGetWithEntity httpGet = new HttpGetWithEntity(url);

                        StringEntity se = new StringEntity(params.toString());
                        se.setContentEncoding("UTF-8");
                        se.setContentType("application/json");
                        httpGet.setEntity(se);
                        httpResponse = httpClient.execute(httpGet);
                    }else{
                        HttpGet httpGet = new HttpGet(url);
                        httpResponse = httpClient.execute(httpGet);
                    }
                }
                httpEntity = httpResponse.getEntity();
                response = EntityUtils.toString(httpEntity);
                messageResponse.setStatus(true);
                postExecute(response);
            }else{
                postExecute(null);
            }
        }catch(Exception e) {
            messageResponse.setStatus(false);
            ServiceError serviceError;
            try {
                serviceError = new JsonUtils().JsonToError(response);
                messageResponse.setMsg("Status: "+serviceError.getStatus()+ ", Message: "+serviceError.getMessage());
            }catch (Exception ex){
                messageResponse.setMsg("Problemas ao tentar conectar com o servidor.");
            }
            if(e instanceof ConnectionPoolTimeoutException)
                messageResponse.setMsg("Problemas ao tentar conectar com o servidor.");
            else
                messageResponse.setMsg("Problemas ao processar retorno.");
            onExceptionValidation();
            e.printStackTrace();
        }
    }

    private void postExecute(String response) {
        if(messageResponse.getStatus()) {
            try {
                entity = (T) jsonUtils.process(response);
                close(entity);
            } catch (Exception e) {
                messageResponse.setMsg("Problemas ao processar retorno.");
                onExceptionValidation();
            }
        }else{
            onExceptionValidation();
        }
    }

    private void onExceptionValidation(){
        if(serviceAction != null)
            serviceAction.finalize();

        if(type == TASK)
            Toast.makeText(context, messageResponse.getMsg(), Toast.LENGTH_LONG).show();
    }

    private void close(T entity){
        if(serviceAction != null)
            serviceAction.finalize();

        finalize(entity);
    }

    protected abstract void finalize(T entity);

}
