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



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

/*
        ImageView bg = (ImageView) findViewById(R.id.splashImage);

        bg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startApplication();
            }
        });
*/
        //Simple timer to show splash screen for a few seconds, then start the main activity.
        CountDownTimer timer = new CountDownTimer(5000, 10) {
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

      //  Intent intent = new Intent(this, MainRecipeScreen.class);
        Intent intent = new Intent(this, MainRecipeScreen.class);
        startActivity(intent);

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
