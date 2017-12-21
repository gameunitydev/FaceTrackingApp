package com.course.vadim.voicer;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.speech.RecognizerIntent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private final String LocaleRU = "ru-RU";
    private final String LocaleUA = "uk-UA";
    private final String LocaleEN = "en-US";

    private String currentLocale = Locale.getDefault().toString();

    private TextView voiceInput;
    private ImageButton speakButton;
    private ImageButton btnEN;
    private ImageButton btnRU;
    private ImageButton btnUA;
    private Button btnDefault;
    private final int REQ_CODE_SPEECH_INPUT = 100;

    private CharSequence toastText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        voiceInput = (TextView) findViewById(R.id.voiceInput);
        speakButton = (ImageButton) findViewById(R.id.btnSpeak);
        btnEN = (ImageButton) findViewById(R.id.btnLangEN);
        btnRU = (ImageButton) findViewById(R.id.btnLangRU);
        btnUA = (ImageButton) findViewById(R.id.btnLangUA);
        btnDefault = (Button) findViewById(R.id.btnLangDefault);

        speakButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                askSpeechInput();
            }
        });

        voiceInput.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                ShareText(voiceInput.getText().toString());
            }
        });

        btnEN.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                ChangeLang(v.getId());
            }
        });

        btnRU.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                ChangeLang(v.getId());
            }
        });

        btnUA.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                ChangeLang(v.getId());
            }
        });

        btnDefault.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                ChangeLang(v.getId());
            }
        });


    }

    private void ShareText(String viewText)
    {
        /*ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText("copiedData", viewText);
        clipboard.setPrimaryClip(clip);
        toastText = "Copied to clipboard!";

        ToastCall(toastText);*/

        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, viewText);
        sendIntent.setType("text/plain");
        startActivity(sendIntent);
    }

    // Changing lang

    private void ChangeLang(int buttonID)
    {

        if (buttonID == btnEN.getId())
        {
            currentLocale = LocaleEN;
            toastText = "English";
        }

        if (buttonID == btnRU.getId())
        {
            currentLocale = LocaleRU;
            toastText = "Русский";
        }

        if (buttonID == btnUA.getId())
        {
            currentLocale = LocaleUA;
            toastText = "Українська";
        }

        if (buttonID == btnDefault.getId())
        {
            currentLocale = Locale.getDefault().toString();
            toastText = "Default system language";
        }

        ToastCall(toastText);
    }


    private void ToastCall(CharSequence toastText)
    {

        Context context = getApplicationContext();
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, toastText, duration);
        toast.setGravity(Gravity.CENTER| Gravity.CENTER, 0, 0);
        toast.show();
    }

    // Showing speech input dialog

    private void askSpeechInput() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, currentLocale);
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,
                "Hi!\nSpeak something");
        try {
            startActivityForResult(intent, REQ_CODE_SPEECH_INPUT);
        } catch (ActivityNotFoundException a) {

        }
    }

    // Receiving speech input

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case REQ_CODE_SPEECH_INPUT: {
                if (resultCode == RESULT_OK && null != data) {

                    ArrayList<String> result = data
                            .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    voiceInput.setText(result.get(0).substring(0,1).toUpperCase() + result.get(0).substring(1));
                }
                break;
            }

        }
    }
}
