package nu.geeks.coffeekeeper;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class CreateRecipe extends Activity {

    private int CREATE_RECIPE = 1234;
    private Button bSave, bCancel;
    private EditText eName, eComment, eCoffeAmount, eWaterAmount, eGrindAmount, eBrewtime, eCoffeeType, eTemp;
    private String name, comment, coffeeamount, wateramount, grindamount, brewtime;

    //TODO - IMPORTANT! No field can contain the symbol € and %, it is used by the parser when saving data.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_recipe);

        eName = (EditText) findViewById(R.id.eName);
        eComment = (EditText) findViewById(R.id.eComment);
        eCoffeAmount = (EditText) findViewById(R.id.eCoffeeamount);
        eWaterAmount = (EditText) findViewById(R.id.eWateramount);
        eGrindAmount = (EditText) findViewById(R.id.eGrind);
        eBrewtime = (EditText) findViewById(R.id.eBrewTime);
        eCoffeeType = (EditText) findViewById(R.id.eCoffeeType);
        eTemp = (EditText) findViewById(R.id.eTemp);

        bSave = (Button) findViewById(R.id.bSave);
        bCancel = (Button) findViewById(R.id.bCancel);


        listeners();


    }

    private void listeners() {
        bSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = "\n ";
               //Check fields
                if(
                        eName.getText().length() == 0 ||
                        eCoffeAmount.getText().length() == 0 ||
                        eWaterAmount.getText().length() == 0 ||
                        eGrindAmount.getText().length() == 0 ||
                        eBrewtime.getText().length() == 0 ||
                        eComment.getText().length() == 0 ||
                        eTemp.getText().length() == 0 ||
                        eCoffeeType.getText().length() == 0
                        ) {
                    Toast.makeText(getApplicationContext(), "Fyll i alla fält!", Toast.LENGTH_LONG).show();
                }else{

                    /*

                    Dataholder recipe = new Dataholder();
                    recipe.setTemp(Integer.parseInt(eTemp.toString()));
                    recipe.setName(eName.toString());
                    recipe.setKindCoffe(eCoffeeType.toString());
                    recipe.setGrind(Integer.parseInt(eGrindAmount.toString()));
                    recipe.setAmmountWater(Integer.parseInt(eWaterAmount.toString()));
                    recipe.setAmountCoffe(Integer.parseInt(eCoffeAmount.toString()));
                    recipe.setBrewTime(1,Integer.parseInt(eBrewtime.toString()));

                    GlobalRecipeList.getInstance().recipeList.add(recipe);

                    */
                    //Dataholder data = new Dataholder();
                    String[] dataToSave = new String[8];
                    dataToSave[0] = eName.getText().toString();
                    dataToSave[1] = eComment.getText().toString();
                    dataToSave[2] = eCoffeeType.getText().toString();
                    dataToSave[3] = eCoffeAmount.getText().toString();
                    dataToSave[4] = eWaterAmount.getText().toString();
                    dataToSave[5] = eBrewtime.getText().toString();
                    dataToSave[6] = eGrindAmount.getText().toString();
                    dataToSave[7] = eTemp.getText().toString();

                    boolean save = true;


                    //Check i user used symbol '€' or '%'. These are illegal, used as identifiers in parser.
                    for(String s : dataToSave){
                        for(char c : s.toCharArray()){
                            if(c == '€' || c == '%') save = false;
                        }
                    }

                    if(save) {
                        //Save the data in an intent, to be catched by the main acticity when this exits.
                        Intent resultIntent = new Intent();
                        resultIntent.putExtra("ReturnData", dataToSave);
                        setResult(RESULT_OK, resultIntent);
                        finish();
                    }else {
                        Toast.makeText(getApplicationContext(), "Tyvärr får du inte använda € eller % i något fält.", Toast.LENGTH_LONG).show();
                    }

                }


            }
        });

        bCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_create_recipe, menu);
        return true;
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
