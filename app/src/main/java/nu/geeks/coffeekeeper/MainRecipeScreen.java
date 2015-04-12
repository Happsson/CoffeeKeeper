package nu.geeks.coffeekeeper;

import android.app.Activity;
import android.content.Context;
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

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;


public class MainRecipeScreen extends Activity {

    private int CREATE_RECIPE = 1234;
    private Button bAddRecipe;
    private ListView listRecipes;
    private Spinner sSort;
    private ArrayList<String> sortOptions;
    private ArrayList<Dataholder> recipes;
    private ArrayAdapter<Dataholder> recipeAdapter;

    private boolean hasPlayedSplash = false;

    private final String savedRecipes = "saved_recipes";
    private final int splashRequestCode = 1;

    private final String[] SORTOPTIONS = {
            "Sortera efter namn",
            "Sortera efter bryggtid"
    };

    private static final String TAG = MainRecipeScreen.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_recipe_screen);


        //Check if there are any saved instances of the recipe-list. If not, create a new empty
        //list.
        if(savedInstanceState == null){
            recipes = new ArrayList<Dataholder>();

        }else{
            //If there are a list saved, retrieve it as a string and convert it to a recipelist.
            recipes = DataSaveAndRead.readListString(savedInstanceState.getString(savedRecipes));
        }

        initializeView();
        initializeButton();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {

        //Save the current recipelist when app closes. Convert it to a String and save it.
        outState.putString(savedRecipes, DataSaveAndRead.saveList(recipes));

        super.onSaveInstanceState(outState);
    }

    /**
     * This is the method that is called when the app comes back alive after when app i being
     * reopened.
     *
     * Stuff is initialized here instead of in onCreate for this reason.
     */
    @Override
    protected void onResume() {
        super.onResume();



        //initializeView();
        //initializeButton();

        //Start splash-screen
        if(!hasPlayedSplash) {
            Intent intent = new Intent(this, SplashScreen.class);
            startActivityForResult(intent, splashRequestCode);
        }

        String savedRecipes = readSavedData();
        if(savedRecipes != null){
            recipes = DataSaveAndRead.readListString(savedRecipes);
            recipeAdapter.notifyDataSetChanged();
        }
    }

    public String readSavedData ( ) {
        StringBuffer datax = new StringBuffer("");
        try {
            FileInputStream fIn = openFileInput(savedRecipes) ;
            InputStreamReader isr = new InputStreamReader ( fIn ) ;
            BufferedReader buffreader = new BufferedReader ( isr ) ;

            String readString = buffreader.readLine ( ) ;
            while ( readString != null ) {
                datax.append(readString);
                readString = buffreader.readLine ( ) ;
            }

            isr.close ( ) ;
        } catch ( IOException ioe ) {
            Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_LONG).show();
            ioe.printStackTrace ( ) ;
        }
        String returnString = datax.toString();
        if(returnString.length() != 0) return returnString;
        else return null;

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
        if(requestCode == splashRequestCode){
            hasPlayedSplash = true;
        }
    }

    private void initializeView(){
        bAddRecipe = (Button) findViewById(R.id.bNewRecipe);
        listRecipes = (ListView) findViewById(R.id.listRecipes);
        sSort = (Spinner) findViewById(R.id.sSort);

       // recipes = GlobalRecipeList.getInstance().recipeList;

        sortOptions = new ArrayList<String>();

        //Add sortoptions
        for(int i = 0; i < SORTOPTIONS.length; i++){
            sortOptions.add(SORTOPTIONS[i]);
        }

        //Create and populate sorting dropdown menu

        ArrayAdapter<String> sortAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, sortOptions);
        sortAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sSort.setAdapter(sortAdapter);

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

    @Override
    protected void onStop() {

        //Save recipe list
        String recipeString = DataSaveAndRead.saveList(recipes);

        try {
            FileOutputStream fos = openFileOutput(savedRecipes, Context.MODE_PRIVATE);
            fos.write(recipeString.getBytes());
            fos.close();
        } catch (FileNotFoundException e) {
            Toast.makeText(getApplicationContext(), "Can't find file", Toast.LENGTH_LONG).show();
        } catch (IOException e) {
            Toast.makeText(getApplicationContext(), "Can't write to file", Toast.LENGTH_LONG).show();
        }


        super.onStop();
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
