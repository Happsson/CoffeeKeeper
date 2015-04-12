package nu.geeks.coffeekeeper;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.ViewFlipper;


public class CreateRecipe extends Activity {

    private Dataholder recipe;

    private int CREATE_RECIPE = 1234;
    private Button bS1, bS2,bS3,bS4,bS5,bS6,bS7,bS8;
    private EditText eName, eComment, eCoffeeType;
    private NumberPicker npTemp,  npWater, npCoffee, npGrind;
    private TimePicker tpBrew1, tpBrew2,tpBrew3;
    private ViewFlipper viewFlipper;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_recipe);

        initializeXMLConnections();

        recipe = new Dataholder();

        setButtonListeners();


    }

    private void setButtonListeners() {
        bS1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(eName.getText().length() > 0){
                    recipe.setName(eName.toString());
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
                    recipe.setComments(eComment.toString());
                }else{
                    recipe.setComments(" "); //No comment set, which is fine.
                }
                viewFlipper.showNext();
            }
        });

        bS3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(eCoffeeType.getText().length() > 0){
                    recipe.setKindCoffe(eCoffeeType.toString());

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
                int time = tpBrew1.getCurrentHour()*60 + tpBrew1.getCurrentMinute();
                recipe.setBrewTime(1, time);
                setResultAndFinis();

            }
        });
    }

    private void setResultAndFinis() {
        //TODO
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

    }

    @Override
    protected void onResume() {
        super.onResume();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        // getMenuInflater().inflate(R.menu.menu_create_recipe, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return super.onOptionsItemSelected(item);
    }
}
