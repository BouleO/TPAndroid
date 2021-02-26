package bao.tp2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


public class Authentication extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authentication);



        Button authenticateButton = (Button)findViewById(R.id.authenticateButton);
        authenticateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String login = ((EditText)findViewById(R.id.loginField)).getText().toString();
                String password = ((EditText)findViewById(R.id.passwordField)).getText().toString();

                TextView resultField = (TextView)findViewById(R.id.resultField);

                AuthThread authThread = new AuthThread(login, password,resultField);
                authThread.start();

            }
        });

    }

}