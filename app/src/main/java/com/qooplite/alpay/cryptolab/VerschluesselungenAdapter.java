package com.qooplite.alpay.cryptolab;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.QuickContactBadge;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;

import java.util.ArrayList;

public class VerschluesselungenAdapter extends RecyclerView.Adapter<VerschluesselungenAdapter.VerschluesselungenHolder> {


    ArrayList<Verschluesselung> data;
    Context activity;

    public VerschluesselungenAdapter(ArrayList<Verschluesselung> data, Context activity) {

        this.data = data;
        this.activity = activity;

    }

    @NonNull
    @Override
    public VerschluesselungenHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View view = inflater.inflate(R.layout.verschluesselunglayout, viewGroup, false);

        return new VerschluesselungenHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final VerschluesselungenHolder verschluesselungenHolder, final int i) {


        final Dialog infoDialog;
        infoDialog = new Dialog(activity);
        infoDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        infoDialog.setContentView(R.layout.verschluesselunginfolayout);
        infoDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        final TextView infoName = infoDialog.findViewById(R.id.nameinfo);
        final TextView infotext = infoDialog.findViewById(R.id.infotext);
        final TextView infoinputtext = infoDialog.findViewById(R.id.inputtext);
        final TextView infoputputtext = infoDialog.findViewById(R.id.outputtext);


        String name = data.get(i).getName().toString().trim();
        verschluesselungenHolder.verschName.setText(name);

        for(int h = 0; h<data.get(i).getSicherheitslevel(); h++){

            verschluesselungenHolder.sterneListe.get(h).setImageResource(R.mipmap.sternvoll);

        }

        //Listener:

        verschluesselungenHolder.verschluesselunglayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(activity, data.get(verschluesselungenHolder.getAdapterPosition()).getName() + " ausgewählt", Toast.LENGTH_SHORT).show();

            }
        });


        verschluesselungenHolder.infoiv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                infoName.setText(data.get(verschluesselungenHolder.getAdapterPosition()).getName().toString().trim());
                infotext.setText(data.get(verschluesselungenHolder.getAdapterPosition()).getBeschreibung().toString().trim());
                infoinputtext.setText(data.get(verschluesselungenHolder.getAdapterPosition()).getInput().toString().trim());
                infoputputtext.setText(data.get(verschluesselungenHolder.getAdapterPosition()).getOutput().toString().trim());
                infoDialog.show();


            }
        });



    }

    @Override
    public int getItemCount() {
        return data.size();
    }


    public class VerschluesselungenHolder extends RecyclerView.ViewHolder {

        ConstraintLayout verschluesselunglayout;
        ImageView stern1iv;
        ImageView stern2iv;
        ImageView stern3iv;
        ImageView stern4iv;
        ImageView stern5iv;
        TextView verschName;
        ArrayList<ImageView> sterneListe = new ArrayList<>();
        ImageView infoiv;

        public VerschluesselungenHolder(@NonNull View itemView) {
            super(itemView);
            verschluesselunglayout = itemView.findViewById(R.id.verschlayout);
            verschName = itemView.findViewById(R.id.verschnametxt);
            stern1iv = itemView.findViewById(R.id.stern1);
            stern2iv = itemView.findViewById(R.id.stern2);
            stern3iv = itemView.findViewById(R.id.stern3);
            stern4iv = itemView.findViewById(R.id.stern4);
            stern5iv = itemView.findViewById(R.id.stern5);
            infoiv = itemView.findViewById(R.id.info);

            sterneListe.add(stern1iv);
            sterneListe.add(stern2iv);
            sterneListe.add(stern3iv);
            sterneListe.add(stern4iv);
            sterneListe.add(stern5iv);
        }
    }


}
