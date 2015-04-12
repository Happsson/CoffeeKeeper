/************************************************************************
 * This file contains a storageclass named 'Dataholder',
 * also contains comparators, can compare on time and name.
 ************************************************************************/


package nu.geeks.coffeekeeper;

/***********************************************************************
 * Storage class, all fields are accesible by setters/getter functions
 * Does no work on the data, returns copies of data, to make a change
 * use appropriate set function
 ***********************************************************************/
import java.util.ArrayList;
import java.util.Comparator;

public class Dataholder {
    //all object fields
    int amountCoffe;
    int amountWater;
     int grind;
     int temp;
     ArrayList<Integer> brewTime;
    //private int totalTime;
     String kindCoffe;
     String comments;
     String name;

    //constructor without arguments
    public Dataholder(){
        amountCoffe=0;
        amountWater=0;
        grind=0;
        temp=0;
        brewTime=new ArrayList<Integer>();
      //  totalTime=0;
        kindCoffe=" ";
        comments=" ";
        name=" ";
    }

    //set name constructor
    public Dataholder(String name){
        amountCoffe=0;
        amountWater=0;
        grind=0;
        temp=0;
        brewTime=new ArrayList<Integer>();
       // totalTime=0;
        kindCoffe=" ";
        comments=" ";
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

    /**
     * @param brewTime
     */
    public void setBrewTime(int brewTime){
        //if index is larger than size, then just add element
        //if(index>this.brewTime.size()) {
            this.brewTime.add(brewTime);
        //}else{      //else remove previous entry then add current element
         //   this.brewTime.remove(index);
          //  this.brewTime.add(index,brewTime);
        //}
    }

    public void setKindCoffe(String kindCoffe){
        this.kindCoffe=new String(kindCoffe);
    }

    public void setComments(String comments){
        this.comments=comments;
    }

    public void setName(String name){
        this.name=name;
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

    @Override
    public String toString(){
    return new String(name);
    }

}

/*****************************************************************
 *Quick->compares totaltime, -1for less, 0 for equal, 1 for greater
 *takes both objects to be compared as arguments
 *****************************************************************/
class DataholderTimeComparator implements Comparator<Dataholder> {
    public int compare(Dataholder d1,Dataholder d2){
        int t1=d1.getTotalTime();
        int t2=d2.getTotalTime();
        if      (t1<t2) return-1;
        else if (t1>t2) return 1;
        else            return 0;
    }
}
/*****************************************************************
 *Quick->  compares names, -1for less, 0 for equal, 1 for greater
 *takes both objects to be compared as arguments
 *****************************************************************/
class DataholderNameComparator implements Comparator<Dataholder>{
    public int compare(Dataholder d1,Dataholder d2){
        String name1=d1.getName();
        String name2=d2.getName();
        int comp=name1.compareTo(name2);
        return comp;
    }

}




































