package nu.geeks.coffeekeeper;

import android.util.Log;

import java.util.ArrayList;

/**
 * Created by hannespa on 15-04-09.
 */
public class DataSaveAndRead {


    /**
     * Converts a recipeList to a String, to be saved on phone.
     * String will look like this:
     *      "€(name)€€(comment)€€(Coffee type)€€(Coffee amount)
     *      €€(Water amount)€€(Grind setting)€€(Brew time)€€(Temperature)"
     *
     *      where the 0 is the index. The same pattern will repeat with the next index.
     *      No spaces or new lines are used.
     *
     *
     * @param recipeList the list to convert
     * @return the string generated
     */
    public static String saveList(ArrayList<Recipe> recipeList){
        String dataString = "";

        for(Recipe recipe : recipeList) {
            dataString += saveRecipe(recipe);
        }

        //TODO - save this string on phone

        return dataString;
    }


    /**
     * Converts a string on the format described for method saveList to a
     * ArrayList<Dataholder>.
     *
     * @param inputString the string to be converted
     * @return ArrayList<Dataholder> the list.
     */
    public static ArrayList<Recipe> readListString(String inputString){
        ArrayList<Recipe> recipeList = new ArrayList<Recipe>();

        Recipe recipe = new Recipe();
        int value = 0;

        String read = "";

        //Iterate through the string and save it to the array.
        for(int i = 0; i < inputString.length(); i++){

            if(inputString.charAt(i) != '€') read += inputString.charAt(i);

            if(inputString.charAt(i) == '€'){
                if(read.length() != 0){
                    addProperty(recipe, value, read);
                    value++;
                    read = "";
                }
            }

            if(inputString.charAt(i) == '%'){
                //next recipe. Save this one.
                recipeList.add(recipe);
                value = 0;
                read = "";
            }
        }
        return recipeList;
    }

    /**
     * Converts a Dataholder object to a String. Refer to the method saveList for how this String
     * will behave.
     *
     * @param recipe the recipe to be converted.
     * @return String the converted string.
     */
    public static String saveRecipe(Recipe recipe){
        String dataString = "";
        //Using symbol € and % as identifiers. Not likely to be used by user.
        dataString += "€" + recipe.getName() + "€";
        dataString += "€" + recipe.getComments() + "€";
        dataString += "€" + recipe.getKindCoffe() + "€";
        dataString += "€" + recipe.getAmountCoffe() + "€";
        dataString += "€" + recipe.getAmountWater() + "€";
        dataString += "€" + recipe.getGrind() + "€";
        dataString += "€" + recipe.getBrewTime().get(0) + "€"; //TODO - again, need to be able to handle more times.
        dataString += "€" + recipe.getTemp() + "€%"; //note end sign, '%'

        return dataString;

    }

    /**
     *
     *
     *
     * @param inputString
     * @return
     */
    public static Recipe readRecipeString(String inputString){
        Recipe recipe = new Recipe();

        String read = "";

        int value = 0;

        for(int i = 0; i < inputString.length(); i++){

            if(inputString.charAt(i) != '€') read += inputString.charAt(i);

            if(inputString.charAt(i) == '€'){
                if(read.length() != 0){
                    addProperty(recipe, value, read);
                    value++;
                    read = "";
                }
            }

            if(inputString.charAt(i) == '%'){
                return recipe;
            }
        }

        Log.d("ERROR", "Error, reading recipe from string not successfull");
        return null;

    }

    private static void addProperty(Recipe recipe, int value, String read) {
        switch(value){
            case 0:
                recipe.setName(read);
                break;
            case 1:
                recipe.setComments(read);
                break;
            case 2:
                recipe.setKindCoffe(read);
                break;
            case 3:
                recipe.setAmountCoffe((Integer.parseInt(read)));
                break;
            case 4:
                recipe.setAmmountWater(Integer.parseInt(read));
                break;
            case 5:
                recipe.setGrind(Integer.parseInt(read));
                break;
            case 6:
                recipe.setBrewTime(Integer.parseInt(read)); //TODO - more than one brew time
                break;
            case 7:
                recipe.setTemp(Integer.parseInt(read));
                break;
            default:
                Log.d("ERROR", "Error in parser, default case should never happen!");
                break;
        }
    }
}