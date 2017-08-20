package com.megahertzlabs.dothrakitranslator;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class MainActivity extends AppCompatActivity {
    EditText messageEnglish;
    Button convertButton;
    TextView messageDothraki;
    String messageInEnglish;
    String messageInDothraki;
    String JSONResult;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        messageEnglish = (EditText)findViewById(R.id.english);
        convertButton = (Button) findViewById(R.id.button);
        messageDothraki = (TextView) findViewById(R.id.dothraki);


        convertButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                messageInEnglish = messageEnglish.getText().toString();
                InputMethodManager inputMethodManager=(InputMethodManager)getSystemService(Activity.INPUT_METHOD_SERVICE);
                inputMethodManager.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY,0);
                new NetworkTask().execute();


            }
        });
    }


    class NetworkTask extends AsyncTask<Void, Void, Void> {
        ProgressDialog dialog = new ProgressDialog(MainActivity.this);

        @Override
        protected void onPreExecute() {
            dialog.setMessage("Translating....");
            dialog.show();
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                String encodedUrl = URLEncoder.encode(messageInEnglish, "UTF-8");
                JSONResult = DothrakiConverter.run(encodedUrl);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

            return null;
        }


        @Override
        protected void onPostExecute(Void aVoid) {
            dialog.dismiss();
            try {
                JSONObject contents = new JSONObject(new JSONObject(JSONResult).getString("contents"));
                messageInDothraki = contents.getString("translated");
                messageDothraki.setText(messageInDothraki);

            } catch (JSONException e) {

                messageDothraki.setText(R.string.api_limit_error);

                e.printStackTrace();
            }
            super.onPostExecute(aVoid);
        }
    }
}








