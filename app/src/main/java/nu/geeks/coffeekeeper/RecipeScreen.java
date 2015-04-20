package nu.geeks.coffeekeeper;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Vibrator;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;


public class RecipeScreen extends Activity {

    //TODO - add notification when timer is done. Add sound? Make timer keep going, to tell how much off you are in the recipe?

    TextView tSetName, tSetComment, tSetCoffeetype, tSetCoffeeAmount, tSetWaterAmount, tSetGrind,
    tSettemperature, tSetBT1, tSetBT2, tSetBT3, tSetTimer;

    Recipe recipe;

    private Typeface bebas;
    private Typeface futuraBookOblique;
    private Typeface futuraLightOblique;

    int currentTimer = 0;
    boolean timerAccessible = false;

    CountDownTimer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_screen);

        Bundle b = getIntent().getExtras();
        int index = b.getInt("ReceivedRecipe");

        bebas = Typeface.createFromAsset(getAssets(), "fonts/bebas.ttf");
        futuraBookOblique = Typeface.createFromAsset(getAssets(), "fonts/futurabookob.otf");
        futuraLightOblique = Typeface.createFromAsset(getAssets(), "fonts/futuralightob.otf");

        recipe = ((InAppData) this.getApplication()).getRecipes().get(index);
        InitializeView();
        setTexts();

        timerSettings();

    }

    private void timerSettings() {


        tSetTimer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(timer != null && timerAccessible){
                    tSetTimer.setTextColor(Color.GREEN);
                    timer.start();
                    timerAccessible = false;
                }
            }
        });

        tSetBT1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timerAccessible = true;
                tSetTimer.setText(secondsToString(recipe.getBrewTime().get(0)));
                tSetTimer.setTextColor(Color.BLUE);
                if(timer != null) timer.cancel();
                timer = new CountDownTimer(recipe.getBrewTime().get(0) * 1000, 2) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                        tSetTimer.setText(secondsToString((int) millisUntilFinished/1000));
                    }

                    @Override
                    public void onFinish() {
                        tSetTimer.setTextColor(Color.RED);
                        vibrate();
                    }
                };
            }
        });

        tSetBT2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timerAccessible = true;
                tSetTimer.setText(secondsToString(recipe.getBrewTime().get(1)));
                tSetTimer.setTextColor(Color.BLUE);
                if(timer != null) timer.cancel();
                timer = new CountDownTimer(recipe.getBrewTime().get(1) * 1000, 2) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                        tSetTimer.setText(secondsToString((int) millisUntilFinished/1000));
                    }

                    @Override
                    public void onFinish() {
                        tSetTimer.setTextColor(Color.RED);
                        vibrate();
                    }
                };
            }
        });

        tSetBT3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timerAccessible = true;
                tSetTimer.setText(secondsToString(recipe.getBrewTime().get(2)));
                tSetTimer.setTextColor(Color.BLUE);
                if(timer != null) timer.cancel();

                timer = new CountDownTimer(recipe.getBrewTime().get(2) * 1000, 2) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                        tSetTimer.setText(secondsToString((int) millisUntilFinished/1000));
                    }

                    @Override
                    public void onFinish() {
                        tSetTimer.setTextColor(Color.RED);
                        vibrate();
                    }
                };
            }
        });

    }

    private void vibrate(){
        Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        v.vibrate(2000);
    }

    private void setTexts() {


        tSetName.setText(recipe.getName());
        tSetComment.setText(recipe.getComments());
        tSetCoffeetype.setText(recipe.getKindCoffe());
        tSetCoffeeAmount.setText(""+recipe.getAmountCoffe());
        tSetWaterAmount.setText(""+recipe.getAmountWater());
        tSetGrind.setText(""+recipe.getGrind());
        tSettemperature.setText(""+recipe.getTemp());

        tSetBT1.setText(secondsToString(recipe.getBrewTime().get(0)));
        tSetBT2.setText(secondsToString(recipe.getBrewTime().get(1)));
        tSetBT3.setText(secondsToString(recipe.getBrewTime().get(2)));
        tSetTimer.setText("00:00");

        tSetComment.setText(recipe.getComments());
        tSetCoffeetype.setText(recipe.getKindCoffe());
        tSetCoffeeAmount.setText(""+recipe.getAmountCoffe());
        tSetWaterAmount.setText(""+recipe.getAmountWater());
        tSetGrind.setText(""+recipe.getGrind());
        tSettemperature.setText(""+recipe.getTemp());

        tSetBT1.setText(secondsToString(recipe.getBrewTime().get(0)));
        tSetBT2.setText(secondsToString(recipe.getBrewTime().get(1)));
        tSetBT3.setText(secondsToString(recipe.getBrewTime().get(2)));
        tSetTimer.setText("00:00");
        tSetTimer.setTypeface(bebas);


    }

    private String secondsToString(int pTime) {
        return String.format("%02d:%02d", pTime / 60, pTime % 60);
    }

    private void InitializeView() {
        tSetName        =             (TextView) findViewById(R.id.tSetName);
        tSetComment     =               (TextView) findViewById(R.id.tSetComment);
        tSetCoffeetype  = (TextView) findViewById(R.id.tSetCoffe);
        tSetCoffeeAmount  = (TextView) findViewById(R.id.tSetCoffeAmount);
        tSetWaterAmount  = (TextView) findViewById(R.id.tSetWaterAmount);
        tSetGrind  = (TextView) findViewById(R.id.tSetGrind);
        tSettemperature  = (TextView) findViewById(R.id.tSetTemp);
        tSetBT1  = (TextView) findViewById(R.id.tSetTime1);
        tSetBT2  = (TextView) findViewById(R.id.tSetTime2);
        tSetBT3  = (TextView) findViewById(R.id.tSetTime3);
        tSetTimer = (TextView) findViewById(R.id.tCurrentTimer);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        return false;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
