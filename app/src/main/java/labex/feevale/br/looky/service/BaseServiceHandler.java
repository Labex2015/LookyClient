package labex.feevale.br.looky.service;

import android.content.Context;

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
import labex.feevale.br.looky.service.utils.HttpGetWithEntity;
import labex.feevale.br.looky.utils.AppVariables;
import labex.feevale.br.looky.utils.JsonUtils;
import labex.feevale.br.looky.utils.MessageResponse;

/**
 * Created by PabloGilvan on 03/01/2015.
 */
public abstract class BaseServiceHandler {
    public static String response = null;
    public final static int GET = 1;
    public final static int POST = 2;
    public Context activity;

    public BaseServiceHandler(Context activity) {
        this.activity = activity;
    }

    public abstract boolean validation();
    public abstract void onFailValidation();

    public void onFailValidation(MessageResponse messageResponse){

    }


    public void makeServiceCall(String url, int method, String params){

        try {
            if(validation()) {
                HttpParams httpParams = new BasicHttpParams();
                HttpConnectionParams.setConnectionTimeout(httpParams, AppVariables.HTTP_TIME_OUT);
                HttpConnectionParams.setSoTimeout(httpParams, AppVariables.SOCKET_TIME_OUT);
                DefaultHttpClient httpClient = new DefaultHttpClient(httpParams);
                HttpEntity httpEntity = null;
                HttpResponse httpResponse = null;
                if (method == POST) {
                    HttpPost httpPost = new HttpPost(url);
                    if (params != null) {
                        StringEntity se = new StringEntity(params.toString(), HTTP.UTF_8);
                        se.setContentEncoding("UTF-8");
                        se.setContentType("application/json; charset=UTF-8");
                        httpPost.setEntity(se);
                    }
                    httpResponse = httpClient.execute(httpPost);
                } else if (method == GET) {
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
                postExecute(response);
            }else{
                onFailValidation();
            }
        } catch (Exception e) {
            MessageResponse messageResponse = new MessageResponse("", false);
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
            onFailValidation(messageResponse);
            e.printStackTrace();
        }
    }

    protected void executeParameters(String params){};

    protected abstract void postExecute(String response);
    public abstract void execute();

}
