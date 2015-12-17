package ebookfrenzy.com.sampleapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

/**
 * Created by Peru on 17-12-2015.
 */
public class NotifyResultActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notify_result);
    }
    public void notify(View v){
        new SendNotification(this,"456","Request accepted").execute();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void notifyReject(View v){
        new SendNotification(this,"456"," Sorry Request rejected").execute();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
