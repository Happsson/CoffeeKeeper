package nu.geeks.coffeekeeper;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.VideoView;


public class SplashScreen extends Activity {

    CountDownTimer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);


        ImageView bg = (ImageView) findViewById(R.id.splashImage);

        bg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timer.cancel();
                startApplication();
            }
        });

        //Simple timer to show splash screen for a few seconds, then start the main activity.
        timer = new CountDownTimer(3000, 10) {
            @Override
            public void onTick(long millisUntilFinished) {
                //do nothing
            }

            @Override
            public void onFinish() {
                startApplication();

            }
        }.start();


    }

    /**
     * Starts main activity.
     */
    private void startApplication(){
        //kinda ugly solution. Simply return the fact that this has been run.
        setResult(RESULT_OK, null);
        finish();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //No menu enabled at this point.
        return false;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return false;
    }
}
