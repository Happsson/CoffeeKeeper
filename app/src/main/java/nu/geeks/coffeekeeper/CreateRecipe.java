package nu.geeks.coffeekeeper;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.ScrollView;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.ViewFlipper;


public class CreateRecipe extends Activity {

    //TODO - Should this, and all screens, have the menu? In that case, what should be on it?

    private Recipe recipe;

    private int CREATE_RECIPE = 1234;
    private Button bS1, bS2,bS3,bS4,bS5,bS6,bS7,bS8;
    private EditText eName, eComment, eCoffeeType;
    private NumberPicker npTemp,  npWater, npCoffee, npGrind;
    private TimePicker tpBrew1, tpBrew2,tpBrew3;
    private ViewFlipper viewFlipper;
    private ScrollView sv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_recipe);

        initializeXMLConnections();

        recipe = new Recipe();

        tpBrew1.setCurrentMinute(0);
        tpBrew1.setCurrentHour(0);
        tpBrew2.setCurrentMinute(0);
        tpBrew2.setCurrentHour(0);
        tpBrew3.setCurrentMinute(0);
        tpBrew3.setCurrentHour(0);


        setButtonListeners();


    }

    private void setButtonListeners() {
        bS1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(eName.getText().length() > 0){
                    recipe.setName(eName.getText().toString());
                    viewFlipper.showNext();
                }else{
                    Toast.makeText(getApplicationContext(), "Ditt recept behöver ett namn!", Toast.LENGTH_LONG).show();
                }
            }
        });

        bS2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(eComment.getText().length() > 0){
                    recipe.setComments(eComment.getText().toString());
                }else{
                    recipe.setComments("(Inga kommentarer)"); //No comment set, which is fine.
                }
                viewFlipper.showNext();
    }
});

        bS3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(eCoffeeType.getText().length() > 0){
                    recipe.setKindCoffe(eCoffeeType.getText().toString());

                }else{
                   recipe.setKindCoffe(" ");
                }
                viewFlipper.showNext();
            }
        });

        bS4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recipe.setAmountCoffe(npCoffee.getValue());
                viewFlipper.showNext();
            }
        });

        bS5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recipe.setAmmountWater(npWater.getValue());
                viewFlipper.showNext();
            }
        });

        bS6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recipe.setGrind(npGrind.getValue());
                viewFlipper.showNext();
            }
        });

        bS7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recipe.setTemp(npTemp.getValue());
                viewFlipper.showNext();
            }
        });

        bS8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO - add nicer looking timers.
                int time1 = tpBrew1.getCurrentHour()*60 + tpBrew1.getCurrentMinute();
                int time2 = tpBrew2.getCurrentHour()*60 + tpBrew2.getCurrentMinute();
                int time3 = tpBrew3.getCurrentHour()*60 + tpBrew3.getCurrentMinute();
                recipe.setBrewTime(time1);
                recipe.setBrewTime(time2);
                recipe.setBrewTime(time3);

                setResultAndFinish();

            }
        });
    }

    private void setResultAndFinish() {

        ((InAppData) this.getApplication()).addRecipe(recipe);
        Intent resultIntent = new Intent();

        resultIntent.putExtra("recipeRequestCode", 1);
        setResult(RESULT_OK, resultIntent);
        finish();
        overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK){

            if(viewFlipper.getCurrentView().equals(findViewById(R.id.v1))){
                finish();
            }
            else{
                viewFlipper.showPrevious();
            }

            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void initializeXMLConnections() {
        bS1 = (Button) findViewById(R.id.bSave1);   //Name
        bS2 = (Button) findViewById(R.id.bSave2);   //Comment
        bS3 = (Button) findViewById(R.id.bSave3);   //Coffeetype
        bS4 = (Button) findViewById(R.id.bSave4);   //Coffeeamount
        bS5 = (Button) findViewById(R.id.bSave5);   //Wateramount
        bS6 = (Button) findViewById(R.id.bSave6);   //GrindSetting
        bS7 = (Button) findViewById(R.id.bSave7);   //Temperature
        bS8 = (Button) findViewById(R.id.bSave8);   //Brewtimes

        eName = (EditText) findViewById(R.id.eName);
        eComment = (EditText) findViewById(R.id.eComment);
        eCoffeeType = (EditText) findViewById(R.id.eCoffeetype);

        npTemp = (NumberPicker) findViewById(R.id.npTemp);
        npWater = (NumberPicker) findViewById(R.id.npWater);
        npCoffee = (NumberPicker) findViewById(R.id.npCoffee);
        npGrind = (NumberPicker) findViewById(R.id.npGrind);

        tpBrew1 = (TimePicker) findViewById(R.id.tpBrew1);
        tpBrew2 = (TimePicker) findViewById(R.id.tpBrew2);
        tpBrew3 = (TimePicker) findViewById(R.id.tpBrew3);

        viewFlipper = (ViewFlipper) findViewById(R.id.viewFlipper);

        sv = (ScrollView) findViewById(R.id.scrollView);

        npCoffee.setMinValue(0);
        npCoffee.setMaxValue(100);
        npCoffee.setValue(5);

        npWater.setMinValue(0);
        npWater.setMaxValue(100);
        npWater.setValue(5);

        npTemp.setMinValue(0);
        npTemp.setMaxValue(100);
        npTemp.setValue(80);

        npGrind.setMinValue(0);
        npGrind.setMaxValue(10);
        npGrind.setValue(5);


        tpBrew1.setIs24HourView(true);
        tpBrew2.setIs24HourView(true);
        tpBrew3.setIs24HourView(true);

        sv.setScrollbarFadingEnabled(false);
        sv.setScrollBarSize(5);

    }

    @Override
    protected void onResume() {
        super.onResume();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }
}
