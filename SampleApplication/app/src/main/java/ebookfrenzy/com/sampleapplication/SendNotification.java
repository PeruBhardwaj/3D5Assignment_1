package ebookfrenzy.com.sampleapplication;

/**
 * Created by Peru on 15-12-2015.
 */
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.sample.backend.messaging.Messaging;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SendNotification extends AsyncTask<Void, Void, String>{
    private static Messaging notifyService = null;
    private Context context;
    private String friendId;
    private String message;

    public SendNotification(Context context, String friendId, String message) {
        this.context = context;
        this.friendId = friendId;
        this.message = message;
    }

    @Override
    protected String doInBackground(Void... params) {
        if (notifyService == null) {
            Messaging.Builder builder = new Messaging.Builder(AndroidHttp.newCompatibleTransport(), new AndroidJsonFactory(), null)
                    .setRootUrl("https://sampleapp-1152.appspot.com/_ah/api/");
            // end of optional local run code

            notifyService = builder.build();
        }

        //String msg = "";
        try {
            //msg = "Notification from Sample Application";
            notifyService.sendFriendId(friendId).execute();
            notifyService.send(message).execute();

        } catch (IOException ex) {
            ex.printStackTrace();
            message = "Error: " + ex.getMessage();
        }
        return message;
    }

    @Override
    protected void onPostExecute(String msg) {
        Toast.makeText(context, "Notification sent", Toast.LENGTH_LONG).show();
        Logger.getLogger("REGISTRATION").log(Level.INFO, msg);
    }
}

