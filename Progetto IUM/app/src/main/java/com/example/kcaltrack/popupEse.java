package com.example.kcaltrack;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


    public class popupEse extends Activity {
        LinearLayout inserimento_ese, creazione_ese;
        Button aggiungi, crea_ese, inserisci;
        ImageView icona_ese;
        GridView gridEse;
        ArrayList<Esercizio> esercizi= new ArrayList<>();
        SearchView search_ese;
        GridAdapterEse adapterEse;
        TextView kcal_ese,nome_ese;
        EditText input_time, kcal_input, nome_input;
        int kcal;
        int icon;
        String nome;

        Esercizio e;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.popupese);
            DisplayMetrics dm = getResources().getDisplayMetrics();
            int width = dm.widthPixels;
            int height = dm.heightPixels;
            getWindow().setLayout((int)(width*.9),(int)(height*.78));
            //LAYOUT
            inserimento_ese = (LinearLayout) findViewById(R.id.ll_Inserimento_ese);
            creazione_ese = (LinearLayout) findViewById(R.id.ll_Creaese);
            //BOTTONI
            aggiungi = (Button) findViewById(R.id.btn_aggiungiInPopupEse);
            crea_ese = (Button)findViewById(R.id.btn_Creaese) ;
            inserisci=  (Button)findViewById(R.id.btn_InserisciinPopEse) ;

            //TEXTVIEW
            icona_ese = (ImageView) findViewById(R.id.icona_ese);
            kcal_ese = (TextView) findViewById(R.id.kcal_esercizio);
            input_time = (EditText) findViewById(R.id.input_time);
            nome_ese = (TextView) findViewById(R.id.nome_ese);

            inserisci.setVisibility(View.INVISIBLE);
            aggiungi.setVisibility(View.INVISIBLE);
            inserimento_ese.setVisibility(View.INVISIBLE);
            creazione_ese.setVisibility(View.INVISIBLE);
            nome_input = (EditText) findViewById(R.id.input_nome_ese);
            kcal_input = (EditText) findViewById(R.id.input_calorie_ese);

            gridEse = (GridView) findViewById(R.id.gridEse);
            adapterEse = new GridAdapterEse(popupEse.this, esercizi);
            fillArray();
            gridEse.setAdapter(adapterEse);

            search_ese = (SearchView) findViewById(R.id.SearchViewEse);

            crea_ese.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    creazione_ese.setVisibility(View.VISIBLE);
                    inserimento_ese.setVisibility(View.INVISIBLE);
                    inserisci.setVisibility(View.VISIBLE);
                }
            });
            inserisci.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String nome_e;
                    int kcal_e;
                    if(nome_input.getText().toString().equalsIgnoreCase("")|| nome_input.getText().toString().equalsIgnoreCase("Inserisci il nome...")){
                        Toast toast = Toast.makeText(popupEse.this, "Compilare tutti i campi!",
                                Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.TOP,0,600);
                        toast.show();
                        return;
                    }else{
                        nome_e = nome_input.getText().toString();
                    }

                    if(kcal_input.getText().toString().equalsIgnoreCase("")|| kcal_input.getText().toString().equalsIgnoreCase("Inserisci calorie...")){
                        Toast toast = Toast.makeText(popupEse.this, "Compilare tutti i campi!",
                                Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.TOP,0,600);
                        toast.show();
                        return;
                    }else{
                        kcal_e = Integer.parseInt(kcal_input.getText().toString());
                    }

                    if(kcal_e == 0 ||kcal_e >= 1000){
                        Toast toast = Toast.makeText(popupEse.this, "Quantità di kcal non accettata!",
                                Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.TOP,0,600);
                        toast.show();
                        return;
                    }

                    Esercizio e = new Esercizio(nome_e,R.drawable.star,kcal_e);
                    esercizi.add(e);
                    Toast toast = Toast.makeText(com.example.kcaltrack.popupEse.this, "Esercizio aggiunto correttamente alla lista!",
                            Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.TOP,0,600);
                    toast.show();
                    adapterEse.notifyDataSetChanged();
                    gridEse.setAdapter(adapterEse);
                    aggiungi.setVisibility(View.INVISIBLE);
                    inserisci.setVisibility(View.INVISIBLE);
                    creazione_ese.setVisibility(View.INVISIBLE);

                }
            });

            aggiungi.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int time = 0;
                    if(String.valueOf(input_time.getText()).equalsIgnoreCase("")||String.valueOf(input_time.getText()).equalsIgnoreCase("Inserisci minuti...")){
                        Toast toast = Toast.makeText(com.example.kcaltrack.popupEse.this, "Inserisci il tempo in minuti!",
                                Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.TOP,0,600);
                        toast.show();
                        return;
                    }else{
                        time = Integer.parseInt(String.valueOf(input_time.getText()));
                    }
                    if(time > 240){
                        Toast toast = Toast.makeText(com.example.kcaltrack.popupEse.this, "Puoi inserire massimo 240 minuti!",
                                Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.TOP,0,600);
                        toast.show();
                        return;
                    }else if(time < 1){
                        Toast toast = Toast.makeText(com.example.kcaltrack.popupEse.this, "Inserisci almeno 1 minuto!",
                                Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.TOP,0,600);
                        toast.show();
                        return;
                    }

                    SharedPreferences sh = getSharedPreferences("MySharedPref", MODE_PRIVATE);
                    SharedPreferences.Editor myEdit = sh.edit();

                    int calorie_tmp = kcal * (time/10);
                    int  calorie_bruciate = sh.getInt("calorie_bruciate",0);
                    myEdit.putInt("calorie_bruciate", calorie_bruciate + calorie_tmp);
                    myEdit.apply();
                    Intent data = new Intent();
                    data.putExtra("calorie_bruciate",calorie_bruciate + calorie_tmp);
                    Toast toast = Toast.makeText(popupEse.this, "Esercizio aggiunto correttamente!",
                            Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.TOP,0,600);
                    toast.show();
                    setResult(Activity.RESULT_OK, data);
                    finish();



                }
            });


            input_time.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if (hasFocus) {
                        input_time.setText("");
                    }
                }
            });

            nome_input.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if (hasFocus) {
                        nome_input.setText("");
                    }
                }
            });
            kcal_input.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if (hasFocus) {
                        kcal_input.setText("");
                    }
                }
            });

            search_ese.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

                @Override
                public boolean onQueryTextSubmit(String query) {
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    adapterEse.getFilter().filter(newText);
                    adapterEse.notifyDataSetChanged();
                    return false;
                }

            });

            gridEse.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapter, View view, int position, long id) {
                    creazione_ese.setVisibility(View.INVISIBLE);
                    inserisci.setVisibility(View.INVISIBLE);
                    crea_ese.setVisibility(View.VISIBLE);
                    aggiungi.setVisibility(View.VISIBLE);
                    inserimento_ese.setVisibility(View.VISIBLE);

                    e = (Esercizio) adapter.getItemAtPosition(position);

                    kcal = e.getKcal();
                    icon = e.getIcon();
                    nome = e.getNome();

                    icona_ese.setImageResource(icon);
                    kcal_ese.setText(String.valueOf(kcal));
                    nome_ese.setText(nome);

                    //setResult(Activity.RESULT_OK,null);
                    // finish();
                }
            });

        }
        public void fillArray(){
            Esercizio corsa = new Esercizio("Corsa",R.drawable.corsa, 70);
            Esercizio camminata = new Esercizio("Camminata",R.drawable.camminata, 35);
            Esercizio corda = new Esercizio("Corda",R.drawable.corda, 150);
            Esercizio calcio = new Esercizio("Calcio",R.drawable.calcio, 98);
            Esercizio pesi = new Esercizio("Pesi",R.drawable.pesi, 45);
            Esercizio squat = new Esercizio("Squat",R.drawable.squat, 98);
            Esercizio basket = new Esercizio("Basket",R.drawable.basket, 70);

            esercizi.add(basket);
            esercizi.add(calcio);
            esercizi.add(camminata);
            esercizi.add(corda);
            esercizi.add(corsa);
            esercizi.add(pesi);
            esercizi.add(squat);

        }


    }

