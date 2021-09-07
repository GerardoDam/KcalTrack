package com.example.kcaltrack;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputType;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class popupAcqua extends Activity {
    EditText acqua_bevuta;
    Button inserisci_acqua;
    float acqua_b, acquaTMP;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.popupacqua);
        DisplayMetrics dm = getResources().getDisplayMetrics();
        int width = dm.widthPixels;
        int height = dm.heightPixels;
        getWindow().setLayout((int)(width*.9),(int)(height*.75));

        inserisci_acqua = (Button) findViewById(R.id.inserisci_acqua);
        acqua_bevuta = (EditText) findViewById(R.id.acqua_bevuta);
        acqua_bevuta.setInputType(InputType.TYPE_CLASS_NUMBER);

        inserisci_acqua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!acqua_bevuta.getText().toString().equalsIgnoreCase("") ) {
                    int a = Integer.parseInt(acqua_bevuta.getText().toString());
                    if (a <= 3000 && a >= 1 ){
                        acquaTMP = Float.parseFloat(acqua_bevuta.getText().toString());
                        acquaTMP = acquaTMP / 1000;
                    }else{
                        Toast toast = Toast.makeText(popupAcqua.this, "Inserimento massimo 3000 ml per volta!",
                                Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.TOP,0,600);
                        toast.show();
                        return;
                    }
                }else {
                    Toast toast = Toast.makeText(popupAcqua.this, "Inserisci la quantità di acqua!",
                            Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.TOP,0,600);
                    toast.show();
                    return;
                }
                SharedPreferences sh = getSharedPreferences("MySharedPref", MODE_PRIVATE);
                SharedPreferences.Editor myEdit = sh.edit();
                acqua_b = sh.getFloat("acqua_bevuta",0);
                myEdit.putFloat("acqua_bevuta", acqua_b + acquaTMP);
                myEdit.apply();
                Intent data = new Intent();
                data.putExtra("acqua_bevuta",acqua_b + acquaTMP);
                Toast toast = Toast.makeText(popupAcqua.this, "Acqua aggiunta correttamente!",
                        Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.TOP,0,600);
                toast.show();
                setResult(Activity.RESULT_OK, data);
                finish();
            }
        });

        acqua_bevuta.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    acqua_bevuta.setText("");
                }
            }
        });
    }


}