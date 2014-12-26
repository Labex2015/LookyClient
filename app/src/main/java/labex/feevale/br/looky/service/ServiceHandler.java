package labex.feevale.br.looky.service;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import labex.feevale.br.looky.service.utils.HttpGetWithEntity;

public abstract class ServiceHandler {

    static String response = null;
    public final static int GET = 1;
    public final static int POST = 2;

    public ServiceHandler() {
    }

    public abstract boolean validation();
    public abstract void onFailValidation();

    public void makeServiceCall(String url, int method, String params) {

        try {
            if(validation()) {
                DefaultHttpClient httpClient = new DefaultHttpClient();
                HttpEntity httpEntity = null;
                HttpResponse httpResponse = null;
                if (method == POST) {
                    HttpPost httpPost = new HttpPost(url);
                    if (params != null) {
                        StringEntity se = new StringEntity(params.toString());
                        se.setContentEncoding("UTF-8");
                        se.setContentType("application/json");
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
            e.printStackTrace();
        }
    }

    protected void executeParameters(String params){

    };

    protected abstract void postExecute(String response);
    public abstract void execute();

}