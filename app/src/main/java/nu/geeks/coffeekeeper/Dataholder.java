package nu.geeks.coffeekeeper;

/**
 * Created by dudez on 2015-04-07.
 */
import java.util.ArrayList;

public class Dataholder {
    private int amountCoffe;
    private int amountWater;
    private int grind;
    private int temp;
    private ArrayList<Integer> brewTime;
    private int totalTime;
    private String kindCoffe;
    private String comments;
    private String name;

    //constructor without arguments
    public Dataholder(){
        amountCoffe=0;
        amountWater=0;
        grind=0;
        temp=0;
        brewTime=new ArrayList<Integer>();
        totalTime=0;
        kindCoffe="";
        comments="";
        name="";
    }

    //set name constructor
    public Dataholder(String name){
        amountCoffe=0;
        amountWater=0;
        grind=0;
        temp=0;
        brewTime=new ArrayList<Integer>();
        totalTime=0;
        kindCoffe="";
        comments="";
        this.name=name;
    }

    //set functions

    public void setAmountCoffe(int amountCoffe) {
        this.amountCoffe=amountCoffe;
    }

    public void setAmmountWater(int amountWater){
        this.amountWater=amountWater;
    }

    public void setGrind(int grind){
        this.grind=grind;
    }

    public void  setTemp(int temp){
        this.temp=temp;
    }

    public void setBrewTime(int index, int brewTime){
        if(index>this.brewTime.size()){
            this.brewTime.add(brewTime);
        }else{
            this.brewTime.remove(index);
            this.brewTime.add(index,brewTime);
        }
    }

    public void setTotalTime(){
    
    }



}
