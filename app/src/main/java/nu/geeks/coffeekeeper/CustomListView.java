package nu.geeks.coffeekeeper;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

/**
 * Created by hannespa on 15-04-20.
 */
public class CustomListView extends ArrayAdapter<String> {

    private final Activity context;
    private final String[] listText;
    private final int[] imageId;
    private Typeface bebas;



    public CustomListView(Activity context, String[] listText, int[] imageId, Typeface bebas) {
        super(context, R.layout.list_single, listText);
        this.context = context;
        this.listText = listText;
        this.imageId = imageId;
        this.bebas = bebas;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent){

        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.list_single, null, true);

        TextView listItem = (TextView) rowView.findViewById(R.id.customListText);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.customListImg);

        listItem.setTypeface(bebas);
        listItem.setTextColor(Color.WHITE);

        listItem.setText(listText[position]);
        imageView.setImageResource(imageId[position]);
        return rowView;
    }



}
