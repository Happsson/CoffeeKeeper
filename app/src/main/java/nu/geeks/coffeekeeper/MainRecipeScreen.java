package nu.geeks.coffeekeeper;

import android.app.Activity;
import android.content.Intent;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


public class MainRecipeScreen extends Activity {

    private int CREATE_RECIPE = 1234;
    private Button bAddRecipe;
    private ListView listRecipes;
    private Spinner sSort;
    private ArrayList<String> sortOptions;
    private ArrayList<Dataholder> recipes;
    private ArrayAdapter<Dataholder> recipeAdapter;

    private final String[] SORTOPTIONS = {
            "Sortera efter namn",
            "Sortera efter bryggtid"
    };

    private static final String TAG = MainRecipeScreen.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_recipe_screen);
        initializeView();
        initializeButton();
    }

    private void initializeButton() {

        bAddRecipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                createRecipe();

            }
        });

        sSort.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(sortOptions.get(position).equals(SORTOPTIONS[0])){ //SORTOPTION[0] == "Sort by name"
                    recipeAdapter.sort(new DataholderNameComparator());
                    recipeAdapter.notifyDataSetChanged();
                }
                if(sortOptions.get(position).equals(SORTOPTIONS[1])){ // Sort by brewtime
                    recipeAdapter.sort(new DataholderTimeComparator());
                    recipeAdapter.notifyDataSetChanged();
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void createRecipe() {
        Intent intent = new Intent(this, CreateRecipe.class);
        startActivityForResult(intent, CREATE_RECIPE);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == CREATE_RECIPE){
            if(resultCode == RESULT_OK){


                //Data recieved from CreateRecipe. Save it to recipies.
                Dataholder newRecipe = new Dataholder();
                String dataReceived = data.getStringExtra("ReturnData");
                Dataholder recipe = DataSaveAndRead.readRecipeString(dataReceived);
                recipes.add(recipe);

                recipeAdapter.notifyDataSetChanged();


                Toast.makeText(getApplicationContext(), "Recept sparat!", Toast.LENGTH_LONG).show();
            }
        }
    }

    private void initializeView(){
        bAddRecipe = (Button) findViewById(R.id.bNewRecipe);
        listRecipes = (ListView) findViewById(R.id.listRecipes);
        sSort = (Spinner) findViewById(R.id.sSort);

       // recipes = GlobalRecipeList.getInstance().recipeList;
        recipes = new ArrayList<Dataholder>();

        fillWithExampleRecipes();

        sortOptions = new ArrayList<String>();

        //Add sortoptions
        for(int i = 0; i < SORTOPTIONS.length; i++){
            sortOptions.add(SORTOPTIONS[i]);
        }

        //Create and populate sorting dropdown menu

        ArrayAdapter<String> sortAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, sortOptions);
        sortAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sSort.setAdapter(sortAdapter);

        Log.d(TAG, recipes.get(0).getName());
        Log.d(TAG, recipes.get(0).getComments());

        recipeAdapter = new ArrayAdapter<Dataholder>(this, android.R.layout.simple_list_item_2,
                android.R.id.text1, recipes){

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);

                TextView text1 = (TextView) view.findViewById(android.R.id.text1);
                TextView text2 = (TextView) view.findViewById(android.R.id.text2);

                text1.setText(recipes.get(position).getName());
                text2.setText(recipes.get(position).getComments());


                return view;
            }
        };


        listRecipes.setAdapter(recipeAdapter);

    }

    /**
     */
    private void fillWithExampleRecipes() {
        Dataholder test = new Dataholder();
        test.setAmmountWater(2);
        test.setAmountCoffe(4);
        test.setBrewTime(1,1);
        test.setComments("Kommentar");
        test.setGrind(3);
        test.setKindCoffe("tjo");
        test.setName("Kaffe1");
        test.setTemp(4);

        recipes.add(test);
    }


    //TODO - add menu buttons
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main_recipe_screen, menu);
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
