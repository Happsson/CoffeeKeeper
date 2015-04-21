package nu.geeks.coffeekeeper;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.ViewFlipper;

import java.util.ArrayList;


public class CreateRecipe extends BaseActivity  {

    //TODO - Should this, and all screens, have the menu? In that case, what should be on it?

    private NavigationDrawerFragment mNavigationDrawerFragment;
    private CharSequence mTitle;


    private Recipe recipe;

    private Typeface futuraBookOblique;
    private Typeface futuraLightOblique;


    private int CREATE_RECIPE = 1234;
    private Button bS;
    private EditText eName, eComment, eCoffeeType, npTemp,  npWater, npCoffee, npGrind, tpBrew1, tpBrew2,tpBrew3, tpCBrew1, tpCBrew2,tpCBrew3;
    private ScrollView sv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_recipe);


        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));

        futuraBookOblique = Typeface.createFromAsset(getAssets(), "fonts/futurabookob.otf");
        futuraLightOblique = Typeface.createFromAsset(getAssets(), "fonts/futuralightob.otf");

        initializeXMLConnections();

        recipe = new Recipe();

        setButtonListeners();

        //Find all textviews and edittexts and set the font.
        RelativeLayout relView = (RelativeLayout) findViewById(R.id.createRecipeRelLayout);
        for(int i = 0; i < relView.getChildCount(); i++){
            if(relView.getChildAt( i ) instanceof TextView ){
                ((TextView) relView.getChildAt( i )).setTypeface(futuraBookOblique);
            }
            if(relView.getChildAt( i ) instanceof  EditText ) {
                ((EditText) relView.getChildAt( i )).setTypeface(futuraLightOblique);
            }
        }
    }

    private void setButtonListeners() {
        bS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean ret = true;
                if(eName.getText().length() == 0) {
                    Toast.makeText(getApplicationContext(), "Your recipe needs a name!", Toast.LENGTH_LONG).show();
                    ret = false;
                }
                if (timeChecker(tpBrew1) == -1 || timeChecker(tpBrew2) == -1 || timeChecker(tpBrew3) == -1){
                    Toast.makeText(getApplicationContext(), "Brew times must be in format 'mm:ss' or 'ss'", Toast.LENGTH_LONG).show();
                    ret = false;
                }
                if(ret) setResultAndFinish();

            }
        });

    }

    private void setResultAndFinish() {

        recipe.setName(eName.getText().toString());
        if(eComment.getText().toString().length()>0) recipe.setComments(eComment.getText().toString());
        if(eCoffeeType.getText().toString().length()>0) recipe.setKindCoffe(eCoffeeType.getText().toString());
        if(npTemp.getText().toString().length()>0) recipe.setTemp(Integer.parseInt(npTemp.getText().toString()));
        if(npWater.getText().toString().length()>0) recipe.setAmmountWater(Integer.parseInt(npWater.getText().toString()));
        if(npCoffee.getText().toString().length()>0) recipe.setAmountCoffe((Integer.parseInt(npCoffee.getText().toString())));
        if(npGrind.getText().toString().length()>0) recipe.setGrind(Integer.parseInt(npGrind.getText().toString()));

        recipe.setBrewTime(timeChecker(tpBrew1));
        recipe.setBrewTime(timeChecker(tpBrew2));
        recipe.setBrewTime(timeChecker(tpBrew3));

        if(tpCBrew1.getText().toString().length()>0){
            recipe.addBrewComments(tpCBrew1.getText().toString());
        }else{
            recipe.addBrewComments(" ");
        }
        if(tpCBrew2.getText().toString().length()>0){
                recipe.addBrewComments(tpCBrew2.getText().toString());
        }else{
            recipe.addBrewComments(" ");
        }

        if(tpCBrew3.getText().toString().length()>0){
                    recipe.addBrewComments(tpCBrew3.getText().toString());
        }else{
            recipe.addBrewComments(" ");
        }


        ((InAppData) this.getApplication()).addRecipe(recipe);
        Intent resultIntent = new Intent();

        resultIntent.putExtra("recipeRequestCode", 1);
        setResult(RESULT_OK, resultIntent);
        finish();
        overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
    }



    private void initializeXMLConnections() {
        bS = (Button) findViewById(R.id.bSave);   //Name

        eName = (EditText) findViewById(R.id.eName);
        eComment = (EditText) findViewById(R.id.eComment);
        eCoffeeType = (EditText) findViewById(R.id.eCoffeetype);

        npTemp = (EditText) findViewById(R.id.npTemp);
        npWater = (EditText) findViewById(R.id.npWater);
        npCoffee = (EditText) findViewById(R.id.npCoffee);
        npGrind = (EditText) findViewById(R.id.npGrind);

        tpBrew1 = (EditText) findViewById(R.id.tpBrew1);
        tpBrew2 = (EditText) findViewById(R.id.tpBrew2);
        tpBrew3 = (EditText) findViewById(R.id.tpBrew3);

        tpCBrew1 = (EditText) findViewById(R.id.tpBrew1Comment);
        tpCBrew2 = (EditText) findViewById(R.id.tpBrew2Comment);
        tpCBrew3 = (EditText) findViewById(R.id.tpBrew3Comment);



        sv = (ScrollView) findViewById(R.id.scrollView);

        sv.setScrollbarFadingEnabled(false);
        sv.setScrollBarSize(5);

    }

    @Override
    protected void onResume() {
        super.onResume();
    }


    private int timeChecker(EditText time){
        int returnInt = 0;
        char[] t = time.getText().toString().toCharArray();
        if(t.length == 5){
            if(t[2] == ':'){
                int sec = ((t[3]-48)*10) + t[4]-48;
                int min = ((t[0]-48)*10) + t[1]-48;
                if(sec < 60){
                    time.setTextColor(Color.WHITE);
                    return (min*60) + sec;
                }
            }
        }else if(t.length == 2){
            if(t[0] != ':' && t[1] != ':'){
                int sec = ((t[0]-48)*10) + t[1]-48;
                time.setTextColor(Color.WHITE);
                return sec;
            }
        }
        time.setTextColor(Color.RED);
        return -1;
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }
}
