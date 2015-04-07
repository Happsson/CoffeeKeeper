package nu.geeks.coffeekeeper;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class CreateRecipe extends Activity {

    private Button bSave;
    private EditText eName, eComment, eCoffeAmount, eWaterAmount, eGrindAmount, eBrewtime;
    private String name, comment, coffeeamount, wateramount, grindamount, brewtime;

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

        bSave = (Button) findViewById(R.id.bSave);

        listeners();


    }

    private void listeners() {
        bSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO - fixa klart här.
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
