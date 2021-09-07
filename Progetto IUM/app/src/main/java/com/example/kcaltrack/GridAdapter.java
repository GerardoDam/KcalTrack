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

public class GridAdapter extends BaseAdapter implements Filterable {

    private ArrayList<Cibo> c;
    private Context context;
    private LayoutInflater inflater;
    CustomFilter filter;
    ArrayList<Cibo> filterList;

    public GridAdapter(Context context, ArrayList<Cibo> c){
        this.context = context;
        this.c = c;
        this.filterList = c;
    }

    @Override
    public int getCount() {
        return c.size();
    }

    @Override
    public Object getItem(int position) {
        return c.get(position);
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

        Cibo cibo = c.get(position);
        icon.setImageResource(cibo.getIcon());
        letter.setText(cibo.getNome());
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
                ArrayList<Cibo> filters = new ArrayList<>();

                for(int i = 0; i<filterList.size();i++){
                    if(filterList.get(i).getNome().toUpperCase().contains(constraint)){
                        Cibo c = new Cibo(filterList.get(i).getNome(),filterList.get(i).getIcon(), filterList.get(i).getKcal(), filterList.get(i).getM_prote(), filterList.get(i).getM_carbo(),filterList.get(i).getM_grassi());
                        filters.add(c);
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
                c = (ArrayList<Cibo>) results.values;
                notifyDataSetChanged();
        }
    };


}
