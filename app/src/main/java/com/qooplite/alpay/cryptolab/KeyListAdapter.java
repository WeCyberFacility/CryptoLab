package com.qooplite.alpay.cryptolab;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.google.gson.Gson;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class  KeyListAdapter extends RecyclerView.Adapter<KeyListAdapter.KeyListHolder> {


    ArrayList<Key> data;
    Context activity;

    public KeyListAdapter(ArrayList<Key> data, Context activity) {

        this.data = data;
        this.activity = activity;

    }

    @NonNull
    @Override
    public KeyListHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View view = inflater.inflate(R.layout.keylistlayout, viewGroup, false);

        return new KeyListHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final KeyListHolder keyListHolder, final int i) {

        keyListHolder.nameTv.setText(data.get(i).getName());

        keyListHolder.normalLayout.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {


                keyListHolder.normalLayout.setVisibility(View.INVISIBLE);
                keyListHolder.deleteLayout.setVisibility(View.VISIBLE);
                Animation animation = AnimationUtils.loadAnimation(activity, R.anim.bounce1);

                keyListHolder.deleteLayout.startAnimation(animation);
                return true;
            }
        });


        keyListHolder.deleteLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                data.remove(i);
                notifyItemRemoved(i);
                notifyDataSetChanged();

                saveSharedPreferencesLogList(activity, data);

                keyListHolder.deleteLayout.setVisibility(View.INVISIBLE);
                keyListHolder.normalLayout.setVisibility(View.VISIBLE);


            }
        });

        keyListHolder.deleteLayout.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {



                keyListHolder.deleteLayout.setVisibility(View.INVISIBLE);
                keyListHolder.normalLayout.setVisibility(View.VISIBLE);
                Animation animation = AnimationUtils.loadAnimation(activity, R.anim.bounce1);

                keyListHolder.normalLayout.startAnimation(animation);
                return true;
            }
        });



    }

    @Override
    public int getItemCount() {
        return data.size();
    }


    public static void saveSharedPreferencesLogList(Context context, ArrayList<Key> keylisteee) {
        SharedPreferences mPrefs = context.getSharedPreferences("KeyListe", context.MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = mPrefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(keylisteee);
        prefsEditor.putString("myJson", json);
        prefsEditor.commit();
    }

    public class KeyListHolder extends RecyclerView.ViewHolder {

        ConstraintLayout normalLayout;
        ConstraintLayout deleteLayout;
        TextView nameTv;



        public KeyListHolder(@NonNull View itemView) {
            super(itemView);

            normalLayout = itemView.findViewById(R.id.normallayout);
            deleteLayout = itemView.findViewById(R.id.deletelayout);
            nameTv = itemView.findViewById(R.id.nametxt);
        }
    }


}
