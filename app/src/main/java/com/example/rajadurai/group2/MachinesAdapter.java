package com.example.rajadurai.group2;

import android.app.Activity;
import android.content.Context;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.daimajia.swipe.adapters.BaseSwipeAdapter;


import java.util.ArrayList;
import java.util.List;

public class MachinesAdapter extends BaseSwipeAdapter {

    private Activity context;
    private ArrayList<String> items;
    private SparseBooleanArray selected = new SparseBooleanArray();
    private Runnable onSelectionChangedListener;
    Context act;

    public MachinesAdapter (Activity context, ArrayList<String> items, Context act) {
        this.context = context;
        this.items = items;
        this.act = act;
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
        return position;
    }

    @Override
    public int getSwipeLayoutResourceId(int position) {
        return R.id.swipe;
    }

    @Override
    public View generateView(int position, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View row;
        row = inflater.inflate(R.layout.machine_history_item, parent, false);

        return row;
    }

    @Override
    public void fillValues(final int position, View row) {


        TextView type, date, length, checkMark;
        type = row.findViewById(R.id.type_text);

        final String name = items.get(position);
        type.setText(name);


        Button remove = row.findViewById(R.id.remove);
        remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (MachinesAdapter.this.removeItem(name)) {
                    PrefSingleton.removeMachineFromHistory(PrefSingleton.MACHINE_LIST_KEY, name, act);
                    System.out.println("Are you here");
                }
            }
        });
        row.setActivated(selected.get(position, false));
//                row.setActivated(selectedItems.contains(position));
    }

    public boolean removeItem(String item){
        for (int i = 0; i < items.size(); i++) {

            if (items.get(i) == item) {
                closeItem(i);
                items.remove(i);
                selected.delete(i);
                for (int j = 0; j < selected.size(); j++) {

                    if (selected.keyAt(j) > i) {
                        int prevKey = selected.keyAt(j);
                        selected.delete(prevKey);
                        selected.put(prevKey-1, true);
                    }
                }


                return true;
            }
        }
        return true;

    }
    public void onItemClick(View view,int position){
        if (selected.get(position, false)){
            selected.delete(position);
            view.setActivated(false);
        }else {
            view.setActivated(true);
            selected.put(position, true);
        }
        notifySelectionChanged();
    }

    public ArrayList<String> getSelectedItems() {
        final ArrayList<String> selectedRecordings = new ArrayList<>();
        for (int i=0;i<selected.size(); i++){
            String item = items.get(selected.keyAt(i));
            selectedRecordings.add(item);
        }
        return selectedRecordings;
    }

    public void unSelectAll() {

        selected.clear();
        notifyDataSetChanged();
        notifySelectionChanged();
    }

    private void notifySelectionChanged(){
        if (onSelectionChangedListener!=null){
            onSelectionChangedListener.run();
        }
    }

    public void setOnSelectionChangedListener(Runnable onSelectionChangedListener) {
        this.onSelectionChangedListener = onSelectionChangedListener;
    }


}

