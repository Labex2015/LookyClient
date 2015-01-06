package labex.feevale.br.looky.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.util.List;

import labex.feevale.br.looky.model.ChatResponse;
import labex.feevale.br.looky.model.Knowledge;
import labex.feevale.br.looky.model.Message;
import labex.feevale.br.looky.model.RequestHelp;
import labex.feevale.br.looky.model.ResponseHelp;
import labex.feevale.br.looky.model.ServiceError;
import labex.feevale.br.looky.model.User;
import labex.feevale.br.looky.wrapper.HelpResponseWrapper;
import labex.feevale.br.looky.wrapper.HelpWrapper;
import labex.feevale.br.looky.wrapper.KnowledgeWrapper;
import labex.feevale.br.looky.wrapper.RegisterLogin;

/**
 * Created by 0118230 on 12/12/2014.
 */
public class JsonUtils<T>{
    private Gson mGson;
    private T entity;


    public JsonUtils() {
    }

    public JsonUtils(T entity) {
        this.entity = entity;
    }

    private void postExecute(){
        mGson = new GsonBuilder().registerTypeAdapter(Boolean.class, new GsonBoolean()).serializeNulls().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
    }

    private void preExecute(){
        mGson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ").serializeNulls().create();
    }

    private Object process(String response){
        postExecute();
        try{
            return mGson.fromJson(response, entity.getClass());
        }catch (Exception e){
            return entity;
        }
    }

    private String process(T toProcess){
        preExecute();
        try{
            return mGson.toJson(toProcess);
        }catch (Exception e){
            return "";
        }
    }

    public HelpWrapper JsonToRequest(String response){
        postExecute();
        return mGson.fromJson(response, HelpWrapper.class);
    }

    public String ResponseHelptToJson( ResponseHelp responseHelp){
        preExecute();
        return mGson.toJson(responseHelp);
    }

    public String RequestToJson(HelpWrapper helpWrapper){
        preExecute();
        return mGson.toJson(helpWrapper);
    }

    public User JsonToUser(String response){
        postExecute();
        return mGson.fromJson(response, User.class);
    }

    public String UserToJson(User user){
        preExecute();
        return mGson.toJson(user);
    }

    public String RegisterLoginToJson(RegisterLogin registerLogin){
        preExecute();
        return mGson.toJson(registerLogin);
    }

    public MessageResponse JsonToMessageResponse(String response){
        postExecute();
        MessageResponse messageResponse = mGson.fromJson(response, MessageResponse.class);
        if(messageResponse != null)
            return messageResponse;

        return new MessageResponse("Problemas ao consultar servidor!", false);
    }




    public List<User> JsonToListUsers(String response){
        postExecute();
        return mGson.fromJson(response, new TypeToken<List<User>>(){}.getType());
    }

    public String RequestToJson(List<User> users){
        preExecute();
        return mGson.toJson(users);
    }

    public List<HelpResponseWrapper> JsonToHelpResponse(String response){
        postExecute();
        return mGson.fromJson(response, new TypeToken<List<HelpResponseWrapper>>() {
        }.getType());
    }

    /**
     * Created by Vitor on 16/12/2014.
     */
    public KnowledgeWrapper JsonToKnowledgeWrapper(String response){
        postExecute();
        return mGson.fromJson(response, KnowledgeWrapper.class);
    }

    public Knowledge JsonToKnowledge(String response){
        postExecute();
        return mGson.fromJson(response, Knowledge.class);
    }

    public String KnowledgeToJson(Knowledge knowledge){
        preExecute();
        return mGson.toJson(knowledge);
    }

    public ChatResponse JsonToChatResponse(String response){
        postExecute();
        return mGson.fromJson(response, ChatResponse.class);
    }

    public String MessageToJson(Message message){
        preExecute();
        return mGson.toJson(message);
    }

    public List<String> jsonStringList(String response){
        return (List<String>)process(response);
    }

    public RequestHelp JsonToRequestHelp(String response){
        postExecute();
        return mGson.fromJson(response, RequestHelp.class);
    }

    public String MessageToJson(MessageResponse response){
        preExecute();
        return mGson.toJson(response);
    }

    public ServiceError JsonToError(String response){
        postExecute();
        return mGson.fromJson(response, ServiceError.class);
    }
}
