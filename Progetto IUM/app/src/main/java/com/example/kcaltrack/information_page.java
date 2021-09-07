package com.example.kcaltrack;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputType;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class information_page extends AppCompatActivity{
    Button btn_iniziamo;
    EditText nom, pes,alt,eta;
    String nome;
    float  peso, altezza, acqua;
    int fabbisogno, input_eta, proteine, grassi, carbo;
    RadioGroup radioGroup;


    @Override
    protected void onPause() {
        super.onPause();

        // Creating a shared pref object
        // with a file name "MySharedPref"
        // in private mode
        SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref", MODE_PRIVATE);
        SharedPreferences.Editor myEdit = sharedPreferences.edit();

        // write all the data entered by the user in SharedPreference and apply
        myEdit.putString("nome", nome);
        myEdit.putInt("fabbisogno_n", fabbisogno);
        myEdit.putInt("proteine_n", proteine);
        myEdit.putInt("carboidrati_n", carbo);
        myEdit.putInt("grassi_n", grassi);
        myEdit.putFloat("acqua_necessaria", acqua);
        myEdit.apply();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.information_main);
        btn_iniziamo =(Button) findViewById(R.id.btn_iniziamo);
        nom = (EditText) findViewById(R.id.input_nome);
        eta = (EditText) findViewById(R.id.input_eta);
        eta.setInputType(InputType.TYPE_CLASS_NUMBER);
        pes = (EditText) findViewById(R.id.input_peso);
        pes.setInputType(InputType.TYPE_CLASS_NUMBER);
        alt = (EditText) findViewById(R.id.input_altezza);
        alt.setInputType(InputType.TYPE_CLASS_NUMBER);
        radioGroup = (RadioGroup) findViewById(R.id.input_genere);



        btn_iniziamo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String radiovalue;
                if(!nom.getText().toString().equalsIgnoreCase("")){
                    nome = nom.getText().toString();
                }else{
                    Toast toast = Toast.makeText(information_page.this, "Compilare tutti i campi proseguire",
                            Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.TOP,0,600);
                    toast.show();
                    return;
                }
                if(!eta.getText().toString().equalsIgnoreCase("")){
                    input_eta = Integer.parseInt(eta.getText().toString());
                    if(input_eta > 125|| input_eta < 5){
                        Toast toast = Toast.makeText(information_page.this, "Inserisci correttamente l'età!",
                                Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.TOP,0,600);
                        toast.show();
                        return;
                    }
                }else{
                    Toast toast = Toast.makeText(information_page.this, "Compilare tutti i campi proseguire",
                            Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.TOP,0,600);
                    toast.show();
                    return;
                }
                if(!pes.getText().toString().equalsIgnoreCase("")){
                    peso = Float.parseFloat(pes.getText().toString());
                    if(peso > 650 || peso < 10){
                        Toast toast = Toast.makeText(information_page.this, "Inserisci correttamente il peso in KG!",
                                Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.TOP,0,600);
                        toast.show();
                        return;
                    }
                }else{
                    Toast toast = Toast.makeText(information_page.this, "Compilare tutti i campi proseguire",
                            Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.TOP,0,600);
                    toast.show();
                    return;
                }
                if((RadioButton)findViewById(radioGroup.getCheckedRadioButtonId())== null){
                    Toast toast = Toast.makeText(information_page.this, "Compilare tutti i campi proseguire",
                            Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.TOP,0,600);
                    toast.show();
                    return;
                }else{
                    radiovalue = ((RadioButton)findViewById(radioGroup.getCheckedRadioButtonId())).getText().toString();
                }
                if(!alt.getText().toString().equalsIgnoreCase("")){
                    altezza = Float.parseFloat(alt.getText().toString());
                    if(altezza > 260|| altezza < 20){
                        Toast toast = Toast.makeText(information_page.this, "Inserisci correttamente l'altezza in CM",
                                Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.TOP,0,600);
                        toast.show();
                        return;
                    }
                }else{
                    Toast toast = Toast.makeText(information_page.this, "Compilare tutti i campi proseguire",
                            Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.TOP,0,600);
                    toast.show();
                    return;
                }

                if (radiovalue.equalsIgnoreCase("Maschio")) {
                    fabbisogno = (int) (10 * peso + 6.5 * altezza - 5 * input_eta + 5);
                    fabbisogno = fabbisogno +((fabbisogno*15)/100);
                    acqua = (float) 2.5;
                } else if (radiovalue.equalsIgnoreCase("Femmina")) {//DONNA
                    fabbisogno = (int) (10 * peso + 6.25 * altezza - 5 * input_eta - 161);
                    fabbisogno = fabbisogno +((fabbisogno*10)/100);
                    acqua = 2;
                }

                //CALCOLO MACRO
                proteine = ((fabbisogno*35)/100)/4;  //proteine 4kcal 1 grammo
                grassi = ((fabbisogno*25)/100)/9;  //grassi 9kcal 1 grammo
                carbo = ((fabbisogno*40)/100)/4;  //carboidrati 4kcal 1 grammo

                SharedPreferences sh = getSharedPreferences("MySharedPref", MODE_PRIVATE);
                SharedPreferences.Editor myEdit = sh.edit();
                myEdit.putFloat("acqua_bevuta", 0);
                myEdit.putInt("carboidrati_assunti", 0);
                myEdit.putInt("grassi_assunti", 0);
                myEdit.putInt("proteine_assunte", 0);
                myEdit.putInt("fabbisogno_assunto", 0);

                myEdit.apply();

                Intent data = new Intent();
                data.putExtra("nome",nome);
                data.putExtra("proteine",proteine);
                data.putExtra("grassi",grassi);
                data.putExtra("carboidrati",carbo);
                data.putExtra("nome",nome);
                data.putExtra("fabbisogno",fabbisogno);
                data.putExtra("acqua_necessaria",acqua);
                data.putExtra("acqua_bevuta",0);
                setResult(Activity.RESULT_OK, data);
                finish();
            }
        });

        nom.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    nom.setText("");
                }
            }
        });
        eta.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    eta.setText("");
                }
            }
        });
        pes.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    pes.setText("");
                }
            }
        });
        alt.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    alt.setText("");
                }
            }
        });



    }
}
