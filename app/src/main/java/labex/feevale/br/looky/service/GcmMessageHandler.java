package labex.feevale.br.looky.service;

import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.NotificationCompat;

import com.google.android.gms.gcm.GoogleCloudMessaging;

import labex.feevale.br.looky.MainActivity;
import labex.feevale.br.looky.model.ChatResponse;
import labex.feevale.br.looky.service.utils.GCMVariables;
import labex.feevale.br.looky.utils.JsonUtils;

/**
 * Created by 0126128 on 18/12/2014.
 */
public class GcmMessageHandler extends IntentService{

    private String message;
    private String messageTypeToRedirect;
    private Integer type;
    private Handler handler;
    private NotificationCompat.Builder notification;
    private NotificationManager manager;
    private ChatResponse chatResponse;

    public GcmMessageHandler() {
        super("GcmMessageHandler");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        handler = new Handler();
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Bundle extras = intent.getExtras();

        GoogleCloudMessaging gcm = GoogleCloudMessaging.getInstance(this);
        String messageType = gcm.getMessageType(intent);
        message = extras.getString("body");
        messageTypeToRedirect = extras.getString("type");
        type = Integer.parseInt(messageTypeToRedirect);
        if(type == GCMVariables.CHAT){
            sendNotification(message);
        }
        GcmBroadcastReceiver.completeWakefulIntent(intent);
    }

    public void sendNotification(String response) {
        chatResponse = new JsonUtils().JsonToChatResponse(message);
        Bundle argsBundle = new Bundle();
        argsBundle.putSerializable("CHAT2", chatResponse);
        Intent chat = new Intent(this, MainActivity.class);
        chat.putExtra("TYPE_FRAG", argsBundle);
        notification = new NotificationCompat.Builder(this);
        notification.setContentTitle(chatResponse.getUserFrom());
        notification.setContentText(chatResponse.getText());
        notification.setTicker("New Message !");
        PendingIntent contentIntent = PendingIntent.getActivity(this, 1000,chat, PendingIntent.FLAG_CANCEL_CURRENT);
        notification.setContentIntent(contentIntent);
        notification.setAutoCancel(true);
        manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(0, notification.build());
    }
}
