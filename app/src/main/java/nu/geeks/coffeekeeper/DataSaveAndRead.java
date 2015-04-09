package nu.geeks.coffeekeeper;

import android.provider.ContactsContract;

import java.util.ArrayList;

/**
 * Created by hannespa on 15-04-09.
 */
public class DataSaveAndRead {


    /**
     * Converts a recipeList to a String, to be saved on phone.
     * String will look like this:
     *      "€€€0name€(name)€comment€(comment)€cType€(Coffee type)€cAmount€(Coffee amount)
     *      €wAmount€(Water amount)€grind€(Grind setting)€bTime€(Brew time)€temp€(Temperature)"
     *
     *      where the 0 is the index. The same pattern will repeat with the next index.
     *      No spaces or new lines are used.
     *
     *
     * @param recipeList the list to convert
     * @return the string generated
     */
    public static String saveList(ArrayList<Dataholder> recipeList){
        String dataString = "";

        int pos = 0;

        for(Dataholder recipe : recipeList) {
            //Using symbol € and % as identifiers. Not likely to be used by user.
            dataString += "%" + pos + "name";
            dataString += "€" + recipe.getName() + "€";
            dataString += "€" + recipe.getComments() + "€";
            dataString += "€" + recipe.getKindCoffe() + "€";
            dataString += "€" + recipe.getAmountCoffe() + "€";
            dataString += "€" + recipe.getAmountWater() + "€";
            dataString += "€" + recipe.getGrind() + "€";
            dataString += "€" + recipe.getBrewTime() + "€";
            dataString += "€" + recipe.getTemp();
        }

        //TODO - save this string on phone

        return dataString;
    }


    public static ArrayList<Dataholder> getList(String inputString){
        ArrayList<Dataholder> recipeList = new ArrayList<Dataholder>();

        //Iterate through the string. First, search for the symbol '€', and catch everything between.
        for(int i = 0; i < inputString.length(); i++){
            String read = "";
            if(inputString.charAt(i) != '€'){
                read += inputString.charAt(i);
            }else{

            }


        }






        return recipeList;
    }
}
