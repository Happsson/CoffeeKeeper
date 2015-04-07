package nu.geeks.coffeekeeper;

/**
 * Created by ramonicon
 */
import java.util.ArrayList;

public class Dataholder {
    private int amountCoffe;
    private int amountWater;
    private int grind;
    private int temp;
    private ArrayList<Integer> brewTime;
    //private int totalTime;
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
      //  totalTime=0;
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
       // totalTime=0;
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
        //if index is larger than size, then just add element
        if(index>this.brewTime.size()) {
            this.brewTime.add(brewTime);
        }else{      //else remove previous entry then add current element
            this.brewTime.remove(index);
            this.brewTime.add(index,brewTime);
        }
    }

    public void setKindCoffe(String kindCoffe){
        this.kindCoffe=new String(kindCoffe);
    }

    public void setComments(String comments){
        this.comments=new String(comments);
    }

    public void setName(String name){
        this.name=new String(name);
    }

    //get functions
    public int getAmountCoffe(){
        return amountCoffe;
    }

    public int getAmountWater(){
        return amountWater;
    }

    public int getGrind(){
        return grind;
    }

    public int getTemp(){
        return temp;
    }

    public ArrayList<Integer> getBrewTime(){
        return new ArrayList<Integer>(brewTime);
    }

    public int getTotalTime(){
        int totTime=0;
        for(Integer x: brewTime){
            totTime+=x;
        }
        return totTime;
    }

    public String getKindCoffe(){
        return new String(kindCoffe);
    }

    public String getComments(){
        return new String(comments);
    }

    public String getName(){
        return new String(name);
    }





}






































