package com.example.kcaltrack;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputType;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.WindowMetrics;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class popupCibi extends Activity {
    LinearLayout inserimento_cibi, creazione_cibi;
    Button aggiungi, crea_alimento, inserisci;
    ImageView icona_cibo;
    GridView gridCibi;
    ArrayList<Cibo> cibi = new ArrayList<>();
    SearchView search_cibi;
    GridAdapter adapterCibi;
    TextView kcal_cibo, prote_cibo, grassi_cibo, carbo_cibo, nome_alimento;
    EditText input_gr, input_nome, input_kcal, input_grassi, input_prote, input_carbo;
    String nome, nome_c;
    int grassi, prote, carbo, icon, kcal, grassi_c, prote_c, carbo_c, kcal_c;
    Cibo c;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.popupcibi);
        DisplayMetrics dm = getResources().getDisplayMetrics();
        int width = dm.widthPixels;
        int height = dm.heightPixels;
        getWindow().setLayout((int)(width*.9),(int)(height*.78));
        //LAYOUT
        inserimento_cibi = (LinearLayout) findViewById(R.id.ll_Inserimento_cibo);
        creazione_cibi = (LinearLayout) findViewById(R.id.ll_Creacibo);
        //BOTTONI
        aggiungi = (Button) findViewById(R.id.btn_aggiungiInPopupCibo);
        crea_alimento = (Button)findViewById(R.id.btn_CreaAlimento) ;
        inserisci= (Button)findViewById(R.id.btn_InserisciinPopCibo) ;
        //TEXTVIEW
        icona_cibo = (ImageView) findViewById(R.id.icona_cibo);
        kcal_cibo = (TextView) findViewById(R.id.kcal_alimento);
        prote_cibo = (TextView) findViewById(R.id.proteine_alimento);
        grassi_cibo = (TextView) findViewById(R.id.grassi_alimento);
        carbo_cibo = (TextView) findViewById(R.id.carbo_alimento);
        nome_alimento = (TextView) findViewById(R.id.nome_alimento);
        //EDITTEXT
        input_gr = (EditText) findViewById(R.id.input_grammi);
        input_gr.setInputType(InputType.TYPE_CLASS_NUMBER);
        input_nome = (EditText) findViewById(R.id.nome_creazione);
        input_kcal = (EditText) findViewById(R.id.calorie_creazione);
        input_kcal.setInputType(InputType.TYPE_CLASS_NUMBER);
        input_grassi = (EditText) findViewById(R.id.grassi_creazione);
        input_grassi.setInputType(InputType.TYPE_CLASS_NUMBER);
        input_prote = (EditText) findViewById(R.id.prote_creazione);
        input_prote.setInputType(InputType.TYPE_CLASS_NUMBER);
        input_carbo = (EditText) findViewById(R.id.carbo_creazione);
        input_carbo.setInputType(InputType.TYPE_CLASS_NUMBER);


        inserisci.setVisibility(View.INVISIBLE);
        aggiungi.setVisibility(View.INVISIBLE);
        inserimento_cibi.setVisibility(View.INVISIBLE);
        creazione_cibi.setVisibility(View.INVISIBLE);



        gridCibi = (GridView) findViewById(R.id.gridcibi);
        adapterCibi = new GridAdapter(popupCibi.this, cibi);
        fillArray();
        gridCibi.setAdapter(adapterCibi);

        search_cibi = (SearchView) findViewById(R.id.SearchViewCibi);


        crea_alimento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                aggiungi.setVisibility(View.INVISIBLE);
                inserisci.setVisibility(View.VISIBLE);
                inserimento_cibi.setVisibility(View.INVISIBLE);
                creazione_cibi.setVisibility(View.VISIBLE);
            }
        });

        aggiungi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int gr = 0;
            if(String.valueOf(input_gr.getText()).equalsIgnoreCase("")||String.valueOf(input_gr.getText()).equalsIgnoreCase("Inserisci gr...") ){
                Toast toast = Toast.makeText(popupCibi.this, "Inserisci la quantità di grammi!",
                        Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.TOP,0,600);
                toast.show();
                return;
            }else{
                gr = Integer.parseInt(String.valueOf(input_gr.getText()));
            } if(gr > 1000){
                Toast toast = Toast.makeText(popupCibi.this, "Puoi inserire massimo 1000gr!",
                        Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.TOP,0,600);
                toast.show();
                return;
            }else if(gr <= 1){
                Toast toast = Toast.makeText(popupCibi.this, "Inserisci almeno 1gr!",
                        Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.TOP,0,600);
                toast.show();
                return;
            }

                SharedPreferences sh = getSharedPreferences("MySharedPref", MODE_PRIVATE);
                SharedPreferences.Editor myEdit = sh.edit();

                int fabbisogno_assunto= sh.getInt("fabbisogno_assunto",0);
                int fabbisogno_a_tmp =(int)( (kcal*gr)/100);

                myEdit.putInt("fabbisogno_assunto", fabbisogno_assunto + fabbisogno_a_tmp);
                myEdit.apply();
                Intent data = new Intent();
                data.putExtra("fabbisogno_assunto",fabbisogno_assunto + fabbisogno_a_tmp);

                int carboidrati_assunti= sh.getInt("carboidrati_assunti",0);
                int carbo_tmp =(int)( (carbo*gr)/100);
                myEdit.putInt("carboidrati_assunti", carboidrati_assunti + carbo_tmp);
                myEdit.apply();
                data.putExtra("carboidrati_assunti", carboidrati_assunti + carbo_tmp);

                int grassi_assunti= sh.getInt("grassi_assunti",0);
                int grassi_tmp =(int)( (grassi*gr)/100);
                myEdit.putInt("grassi_assunti", grassi_assunti + grassi_tmp);
                myEdit.apply();
                data.putExtra("grassi_assunti", grassi_assunti + grassi_tmp);

                int proteine_assunte= sh.getInt("proteine_assunte",0);
                int prote_tmp =(int)( (prote*gr)/100);
                myEdit.putInt("proteine_assunte", proteine_assunte + prote_tmp);
                myEdit.apply();
                data.putExtra("proteine_assunte", proteine_assunte + prote_tmp);
                Toast toast = Toast.makeText(popupCibi.this, "Alimento aggiunto correttamente!",
                        Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.TOP,0,600);
                toast.show();

                setResult(Activity.RESULT_OK, data);
                finish();



            }
        });
        input_kcal.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    input_kcal.setText("");
                }
            }
        });
        input_nome.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    input_nome.setText("");
                }
            }
        });
        input_prote.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    input_prote.setText("");
                }
            }
        });
        input_grassi.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    input_grassi.setText("");
                }
            }
        });
        input_carbo.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    input_carbo.setText("");
                }
            }
        });
        input_gr.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    input_gr.setText("");
                }
            }
        });

        search_cibi.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapterCibi.getFilter().filter(newText);
                adapterCibi.notifyDataSetChanged();
                return false;
            }

        });

        gridCibi.setOnItemClickListener(new AdapterView.OnItemClickListener() {


            @Override
            public void onItemClick(AdapterView<?> adapter, View view, int position, long id) {
                crea_alimento.setVisibility(View.VISIBLE);
                creazione_cibi.setVisibility(View.INVISIBLE);
                aggiungi.setVisibility(View.VISIBLE);
                inserisci.setVisibility(View.INVISIBLE);
                inserimento_cibi.setVisibility(View.VISIBLE);
                c = (Cibo) adapter.getItemAtPosition(position);

                c.toString();
                kcal = c.getKcal();
                icon = c.getIcon();
                nome = c.getNome();
                grassi = c.getM_grassi();
                prote = c.getM_prote();
                carbo = c.getM_carbo();

                icona_cibo.setImageResource(icon);
                kcal_cibo.setText(String.valueOf(kcal));
                grassi_cibo.setText(String.valueOf(grassi));
                carbo_cibo.setText(String.valueOf(carbo));
                prote_cibo.setText(String.valueOf(prote));
                nome_alimento.setText(nome);

                //setResult(Activity.RESULT_OK,null);
                // finish();
            }
        });

        inserisci.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if(input_grassi.getText().toString().equalsIgnoreCase("")||input_grassi.getText().toString().equalsIgnoreCase("Inserisci grassi...")){
                    Toast toast = Toast.makeText(popupCibi.this, "Compilare tutti i campi!",
                            Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.TOP,0,600);
                    toast.show();
                    return;
                } else {
                    grassi_c = Integer.parseInt(input_grassi.getText().toString());
                }
                if(input_prote.getText().toString().equalsIgnoreCase("")||input_prote.getText().toString().equalsIgnoreCase("Inserisci proteine...")){
                    Toast toast = Toast.makeText(popupCibi.this, "Compilare tutti i campi!",
                            Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.TOP,0,600);
                    toast.show();
                    return;
                }else{
                    prote_c = Integer.parseInt(input_prote.getText().toString());
                }
                if(input_nome.getText().toString().equalsIgnoreCase("")||input_nome.getText().toString().equalsIgnoreCase("Inserisci il nome...")){
                    Toast toast = Toast.makeText(popupCibi.this, "Compilare tutti i campi!",
                            Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.TOP,0,600);
                    toast.show();
                    return;
                }else{
                    nome_c = input_nome.getText().toString();
                }
                if(input_carbo.getText().toString().equalsIgnoreCase("")|| input_carbo.getText().toString().equalsIgnoreCase("Inserisci carboidrati...")){
                    Toast toast = Toast.makeText(popupCibi.this, "Compilare tutti i campi!",
                            Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.TOP,0,600);
                    toast.show();
                    return;
                }else{
                    carbo_c = Integer.parseInt(input_carbo.getText().toString());
                }

                if(input_kcal.getText().toString().equalsIgnoreCase("")|| input_kcal.getText().toString().equalsIgnoreCase("Inserisci calorie...")){
                    Toast toast = Toast.makeText(popupCibi.this, "Compilare tutti i campi!",
                            Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.TOP,0,600);
                    toast.show();
                    return;
                }else{
                    kcal_c = Integer.parseInt(input_kcal.getText().toString());
                }


                if(kcal_c == 0 ||kcal_c >= 900){
                    Toast toast = Toast.makeText(popupCibi.this, "Quantità di kcal non accettata!",
                            Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.TOP,0,600);
                    toast.show();
                    return;
                }if(prote_c < 0 ||prote_c > 100){
                    Toast toast = Toast.makeText(popupCibi.this, "Quantità di proteine non accettata!",
                            Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.TOP,0,600);
                    toast.show();
                    return;
                }if(carbo_c < 0 ||carbo_c > 100){
                    Toast toast = Toast.makeText(popupCibi.this, "Quantità di carboidrati non accettata!",
                            Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.TOP,0,600);
                    toast.show();
                    return;
                }if(grassi_c < 0 ||grassi_c > 100){
                    Toast toast = Toast.makeText(popupCibi.this, "Quantità di grassi l non accettata!",
                            Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.TOP,0,600);
                    toast.show();
                    return;
                }
                Cibo ins = new Cibo(nome_c,R.drawable.fork,kcal_c,prote_c,carbo_c,grassi_c);
                cibi.add(ins);
                Toast toast = Toast.makeText(popupCibi.this, "Alimento inserito correttamente nella lista!",
                        Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.TOP,0,600);
                toast.show();
                adapterCibi.notifyDataSetChanged();
                gridCibi.setAdapter(adapterCibi);
                aggiungi.setVisibility(View.INVISIBLE);
                inserisci.setVisibility(View.INVISIBLE);
                creazione_cibi.setVisibility(View.INVISIBLE);

                return;

            }
        });
    }
    public void fillArray(){
        Cibo mela = new Cibo("Mela",R.drawable.mela,38, 0, 10,0);
        Cibo banana = new Cibo("Banana",R.drawable.banana,89,1 , 22,0);
        Cibo anguria = new Cibo("Anguria",R.drawable.anguria,30,1 , 7,0);
        Cibo vitello = new Cibo("Spezzatino di manzo",R.drawable.spezzatinodivitello,130,22 , 0,5);
        Cibo petto = new Cibo("Petto di pollo",R.drawable.pettodipollo,165,31 , 0,4);
        Cibo pasta = new Cibo("Pasta al pomodoro",R.drawable.pasta,360 ,10 , 80,2);
        Cibo Riso = new Cibo("Riso",R.drawable.riso,130,3 , 28,0);
        Cibo burro = new Cibo("Burro",R.drawable.burro,717,1 , 0,81);
        Cibo Olio = new Cibo("Olio di oliva",R.drawable.olio,884,0 , 0,100);
        Cibo Proteine = new Cibo("Proteine 100% whey",R.drawable.proteine,360 ,86 , 2,4);
        Cibo Pizza = new Cibo("Pizza",R.drawable.pizza,266 ,11 , 33,10);
        Cibo kiwi = new Cibo("Kiwi",R.drawable.kiwi,61 ,1 , 15,0);
        Cibo salmone = new Cibo("Salmone",R.drawable.salmone,208,20 , 0,13);
        Cibo cocco = new Cibo("Cocco",R.drawable.cocco,354 ,3 , 15,33);
        Cibo pomodori = new Cibo("Pomodori",R.drawable.pomodori,884,1 , 4,0);
        Cibo parmigiano = new Cibo("Parmigiano",R.drawable.parmigiano,431 ,38 , 4,29);

        cibi.add(anguria);
        cibi.add(banana);
        cibi.add(burro);
        cibi.add(cocco);
        cibi.add(mela);
        cibi.add(Olio);
        cibi.add(pasta);
        cibi.add(parmigiano);
        cibi.add(petto);
        cibi.add(Pizza);
        cibi.add(Proteine);
        cibi.add(pomodori);
        cibi.add(Riso);
        cibi.add(salmone);
        cibi.add(kiwi);
        cibi.add(vitello);
    }


}
