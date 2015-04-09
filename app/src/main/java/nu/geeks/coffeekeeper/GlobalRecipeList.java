package nu.geeks.coffeekeeper;

import android.app.Application;

import java.util.ArrayList;

/**
 * Created by Hannes on 2015-04-09.
 */
public class GlobalRecipeList {


    //TODO - MAKE THIS WOOOOORK

        private static GlobalRecipeList mInstance= null;

        public ArrayList<Dataholder> recipeList = new ArrayList<Dataholder>();

        protected GlobalRecipeList(){
        }

        public static synchronized GlobalRecipeList getInstance(){
            if(null == mInstance){
                mInstance = new GlobalRecipeList();
            }
            return mInstance;
        }
    }
