package com.example.kcaltrack;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.EmptyStackException;

public class GridAdapterEse extends BaseAdapter implements Filterable {

    private ArrayList<Esercizio> e;
    private Context context;
    private LayoutInflater inflater;
    CustomFilter filter;
    ArrayList<Esercizio> filterList;

    public GridAdapterEse(Context context, ArrayList<Esercizio> e ){
        this.context = context;
        this.e = e;
        this.filterList = e;
    }

    @Override
    public int getCount() {
        return e.size();
    }

    @Override
    public Object getItem(int position) {
        return e.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View gridView = convertView;
        if(convertView == null){
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            gridView = inflater.inflate(R.layout.item_layout,null);
        }
        ImageView icon = (ImageView) gridView.findViewById(R.id.icons);
        TextView letter = (TextView) gridView.findViewById(R.id.letters);

        Esercizio ese = e.get(position);
        icon.setImageResource(ese.getIcon());
        letter.setText(ese.getNome());
        return gridView;
    }

    @Override
    public Filter getFilter() {
        if(filter == null){
            filter = new CustomFilter();
        }
        return filter;
    }

    class CustomFilter extends Filter{
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();
            if(constraint!= null && constraint.length()>0){
                constraint = constraint.toString().toUpperCase();
                ArrayList<Esercizio> filters = new ArrayList<>();

                for(int i = 0; i<filterList.size();i++){
                    if(filterList.get(i).getNome().toUpperCase().contains(constraint)){
                        Esercizio e = new Esercizio(filterList.get(i).getNome(),filterList.get(i).getIcon(), filterList.get(i).getKcal());
                        filters.add(e);
                    }
                }
                results.count = filters.size();
                results.values = filters;
            }else{
                results.count = filterList.size();
                results.values = filterList;
            }
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
                e = (ArrayList<Esercizio>) results.values;
                notifyDataSetChanged();
        }
    };


}
