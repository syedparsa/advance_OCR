package hu.unideb.inf.advanceOcr;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import java.util.Locale;

public class EditText extends AppCompatActivity {

    private static final int REQUEST_CODE_STT =1000 ;
    private TextView tv;
    private TextToSpeech tts;
    private Locale locale;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_text);
        tv = findViewById(R.id.textView4);
        tv.setText(getIntent().getExtras().getString(getString(R.string.transltedText)));
        locale = new Locale(getIntent().getExtras().getString(getString(R.string.Langugae)));
       // Log.d("gg",(getString(R.string.Langugae));
        tv.setMovementMethod(new ScrollingMovementMethod());

    }

    @Override
    public boolean onSupportNavigateUp() {
        return super.onSupportNavigateUp();
    }

    public void whatsappSend(View view) {

        SendTextToWhsatpp(tv.getText().toString());
    }

    private void SendTextToWhsatpp(String message) {
        Intent share = new Intent(Intent.ACTION_SEND);
        share.setType("text/plain");

        share.putExtra(Intent.EXTRA_TEXT, message);
        startActivity(Intent.createChooser(share, "Share to"));
    }


    public void speak(View view) {
         tts=new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status != TextToSpeech.ERROR) {
                    tts.setLanguage(locale);
                    String toSpeak = tv.getText().toString();
                   // Toast.makeText(getApplicationContext(), toSpeak,Toast.LENGTH_SHORT).show();
                    tts.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null);
                }
            }
        });
    }

    public void onPause(){
        if(tts !=null){
            tts.stop();
            tts.shutdown();
        }
        super.onPause();
    }


}