package ebookfrenzy.com.sampleapplication;

/**
 * Created by Peru on 14-12-2015.
 */
import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.sampleapp.backend.quoteApi.QuoteApi;
import com.google.sampleapp.backend.quoteApi.model.Quote;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

public class RetrieveData extends AsyncTask<Void, Void, List<Quote>> {
        private static QuoteApi myApiService = null;
        private Context context;

        RetrieveData(Context context) {
            this.context = context;
        }

        @Override
        protected List<com.google.sampleapp.backend.quoteApi.model.Quote> doInBackground(Void... params) {
            if(myApiService == null) {  // Only do this once
                QuoteApi.Builder builder = new QuoteApi.Builder(AndroidHttp.newCompatibleTransport(), new AndroidJsonFactory(), null)
                        .setRootUrl("https://sampleapp-1152.appspot.com/_ah/api/");
                // end options for devappserver

                myApiService = builder.build();
            }

            //context = params[0].first;
            //String name = params[0].second;

            try {
                return myApiService.list().execute().getItems();
            } catch (IOException e) {
                return Collections.EMPTY_LIST;
            }

        }

        @Override
        protected void onPostExecute(List<com.google.sampleapp.backend.quoteApi.model.Quote> result) {
            for (com.google.sampleapp.backend.quoteApi.model.Quote q : result) {
                Toast.makeText(context, q.getWho() + " : " + q.getWhat(), Toast.LENGTH_LONG).show();
            }
        }
    }

