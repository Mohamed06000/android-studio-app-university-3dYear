package com.example.plpla.controleur;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;

import com.example.plpla.Client;
import com.example.plpla.CourseActivity;
import com.example.plpla.MainNavigation;
import com.example.plpla.R;
import com.example.plpla.ui.home.HomeFragment;
import com.example.plpla.vue.Vue;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import io.socket.client.Socket;

import static android.content.Context.MODE_PRIVATE;

public class ListenerButton implements View.OnClickListener{

    private final Socket socket;
    private HomeFragment activity;
    private int compteurTouche = 0;

    public ListenerButton(Socket socket, HomeFragment activity) {
        this.socket = socket;
        this.activity = activity;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.BoutonSemestre:
                compteurTouche++;
                Log.d("Bouton semestre", "Pression sur le bouton semestre");
                activity.getEnregistrer().setEnabled(false);
                if (compteurTouche%2 != 0) {
                    activity.getCheckBox1().setVisibility(View.VISIBLE);
                    activity.getCheckBox2().setVisibility(View.VISIBLE);
                    activity.getTextView1().setVisibility(View.VISIBLE);
                    activity.getTextView2().setVisibility(View.VISIBLE);
                    activity.getEnregistrer().setVisibility(View.VISIBLE);
                    activity.getAccordeon().setText(R.string.deroulementMoins);
                    /*Le serveur ne semble pas recevoir l'event*/
                    ((Client)activity.getActivity().getApplicationContext()).getUniqueConnexion().envoyerEvent("touche");
                    //socket.emit("touche");
                }
                else {
                    activity.getCheckBox1().setVisibility(View.INVISIBLE);
                    activity.getCheckBox2().setVisibility(View.INVISIBLE);
                    activity.getTextView1().setVisibility(View.INVISIBLE);
                    activity.getTextView2().setVisibility(View.INVISIBLE);
                    activity.getEnregistrer().setVisibility(View.INVISIBLE);
                    activity.getAccordeon().setText(R.string.deroulementPlus);
                }
                break;
            case R.id.Enregistrer:
                Log.d("Bouton enregistrer", "Parcours enregistre");
                String fileName = "mon_parcours";
                String final_selection = "";
                for (String selections : HomeFragment.getSelectionItem()){
                    Log.d("WRITEFILE", "ecriture de "+HomeFragment.getSelectionItem().toString());
                    final_selection += selections + "\n";
                    Log.d("WRITEFILE", "Valeur de final_selection "+final_selection);

                }
                try {
                    FileOutputStream ecriture = activity.getActivity().openFileOutput(fileName, MODE_PRIVATE);
                    ecriture.write(final_selection.getBytes());
                    ecriture.close();
                    Toast.makeText(activity.getActivity(), "Parcours enregistré", Toast.LENGTH_LONG).show();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }



        }
    }


}