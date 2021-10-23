package ro.csie.en.mda04;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class CityAdapter extends BaseAdapter {

    private static final String TAG = CityAdapter.class.getName();
    private Context context;
    private List<City> items;
    LayoutInflater layoutInflater;

    public CityAdapter(Context context, List<City> items) {
        this.context = context;
        this.items = items;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
       return items.size();
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return items.get(position).getCode();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Log.d(TAG, "getView");
        if(convertView == null)
            convertView = layoutInflater.inflate(R.layout.list_view_item, parent, false);
        ImageView logo = convertView.findViewById(R.id.cityLogo);
        TextView name = convertView.findViewById(R.id.cityName);
        TextView population = convertView.findViewById(R.id.cityPopulation);

        logo.setImageDrawable(context.getDrawable(R.drawable.ic_launcher_foreground));
        name.setText(items.get(position).getName());
        population.setText(items.get(position).getPopulation() + " people");

        return convertView;
    }
}
