package com.qooplite.alpay.cryptolab;

import android.content.ClipData;
import android.content.ClipboardManager;
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
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.ArrayList;

public class PasswordListAdapter  extends RecyclerView.Adapter<PasswordListAdapter.PasswordListHolder>{



    ArrayList<Password> data;
    Context activity;

    public PasswordListAdapter(ArrayList<Password> data, Context activity) {

        this.data = data;
        this.activity = activity;

    }

    @NonNull
    @Override
    public PasswordListHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View view = inflater.inflate(R.layout.passwordlistlayout, viewGroup, false);

        return new PasswordListHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final PasswordListHolder passwordListHolder, final int i) {

        passwordListHolder.nameTvpm.setText(data.get(i).getName());

        passwordListHolder.normalLayoutpm.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {


                passwordListHolder.normalLayoutpm.setVisibility(View.INVISIBLE);
                passwordListHolder.deleteLayoutpm.setVisibility(View.VISIBLE);
                Animation animation = AnimationUtils.loadAnimation(activity, R.anim.bounce1);

                passwordListHolder.deleteLayoutpm.startAnimation(animation);
                return true;
            }
        });


        passwordListHolder.normalLayoutpm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Animation animation = AnimationUtils.loadAnimation(activity, R.anim.rotate);

                passwordListHolder.normalLayoutpm.startAnimation(animation);



                ClipboardManager clipboard = (ClipboardManager) activity.getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("label", data.get(i).getPassword());
                clipboard.setPrimaryClip(clip);

                Toast.makeText(activity, "copied", Toast.LENGTH_SHORT).show();


            }
        });

        passwordListHolder.deleteLayoutpm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                data.remove(i);
                notifyItemRemoved(i);
                notifyDataSetChanged();

                saveSharedPreferencesLogList(activity, data);

                passwordListHolder.deleteLayoutpm.setVisibility(View.INVISIBLE);
                passwordListHolder.normalLayoutpm.setVisibility(View.VISIBLE);


            }
        });

        passwordListHolder.deleteLayoutpm.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {



                passwordListHolder.deleteLayoutpm.setVisibility(View.INVISIBLE);
                passwordListHolder.normalLayoutpm.setVisibility(View.VISIBLE);
                Animation animation = AnimationUtils.loadAnimation(activity, R.anim.bounce1);

                passwordListHolder.normalLayoutpm.startAnimation(animation);
                return true;
            }
        });



    }

    @Override
    public int getItemCount() {
        return data.size();
    }


    public static void saveSharedPreferencesLogList(Context context, ArrayList<Password> passwordlistee) {
        SharedPreferences mPrefs = context.getSharedPreferences("PasswordListe", context.MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = mPrefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(passwordlistee);
        prefsEditor.putString("myJson2", json);
        prefsEditor.commit();
    }

    public class PasswordListHolder extends RecyclerView.ViewHolder {

        ConstraintLayout normalLayoutpm;
        ConstraintLayout deleteLayoutpm;
        TextView nameTvpm;



        public PasswordListHolder(@NonNull View itemView) {
            super(itemView);

            normalLayoutpm = itemView.findViewById(R.id.normallayoutPM);
            deleteLayoutpm = itemView.findViewById(R.id.deletelayoutPM);
            nameTvpm = itemView.findViewById(R.id.nametxtPM);
        }
    }



}
