package ebookfrenzy.com.sampleapplication;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.sampleapp.backend.quoteApi.QuoteApi;
import com.google.sampleapp.backend.quoteApi.model.Quote;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    public String phone_number, acname;
    public EditText editTextWho;
    public EditText editTextWhat;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent = getIntent();
        //Accounts ac = new Accounts();
        //phone_number = ac.findNumber(this);
        AccountManager am = AccountManager.get(this);
        Account[] accounts = am.getAccounts();
        for(Account ac:accounts){
            acname = ac.name;
            if(acname.matches("[0-9,+]+")&& acname.length()>2){
                phone_number = acname;
            }
            Log.i("Accounts:", "Account:"+acname);
        }
        //new EndpointsAsyncTask().execute(new Pair<Context, String>(this, phone_number));
        new GcmRegistrationAsyncTask(this,"userId").execute();


    }

    public void insertQuotes(View v) {
        editTextWho = (EditText)findViewById(R.id.etWho);
        editTextWhat = (EditText)findViewById(R.id.etWhat);
        String who = editTextWho.getText().toString();
        String what = editTextWhat.getText().toString();
        new EndpointsAsyncTask(this,who,what).execute();
    }

    public void getQuotes(View v) {
        new RetrieveData(this).execute();
    }

    public void notify(View v){
        new SendNotification(this,"456","Notification from sample app").execute();
    }
}

class EndpointsAsyncTask extends AsyncTask<Void, Void, Void> {
    private static QuoteApi myApiService = null;
    private Context context;
    private String who;
    private String what;

    EndpointsAsyncTask(Context context,String who,String what) {
        this.context = context;
        this.who = who;
        this.what = what;
    }

    @Override
    protected Void doInBackground(Void... params) {
        if(myApiService == null) {  // Only do this once
            QuoteApi.Builder builder = new QuoteApi.Builder(AndroidHttp.newCompatibleTransport(), new AndroidJsonFactory(), null)
                    .setRootUrl("https://sampleapp-1152.appspot.com/_ah/api/");
            // end options for devappserver

            myApiService = builder.build();
        }

        //context = params[0].first;
        //String name = params[0].second;

        try {
            Quote q = new Quote();
            q.setWho(who);
            q.setWhat(what);
            Quote temp = new Quote();
            temp = myApiService.insert(q).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;

    }


}

