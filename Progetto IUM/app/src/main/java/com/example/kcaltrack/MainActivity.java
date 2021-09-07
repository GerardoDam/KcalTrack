
package com.example.kcaltrack;

        import androidx.annotation.Nullable;
        import androidx.appcompat.app.AlertDialog;
        import androidx.appcompat.app.AppCompatActivity;

        import android.content.DialogInterface;
        import android.content.Intent;
        import android.content.SharedPreferences;
        import android.graphics.Color;
        import android.os.Bundle;
        import android.view.View;
        import android.widget.ImageView;
        import android.widget.ProgressBar;
        import android.widget.TextView;

        import java.text.SimpleDateFormat;
        import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    ImageView aggiungiCibo, aggiungiAcqua, aggiungiEse, reset;
    int fabbisogno, proteine, grassi, carbo, progressAcqua,progressKcal,progressProte, progressGrassi,progressCarbo, fabbisogno_assunto = 0, prote_a = 0, carbo_a = 0, grassi_a = 0, calorie_bruciate = 0;
    float  acqua_necessaria, acqua_bevuta = 0, getAcqua_bevutaTMP;
    ProgressBar acquaBar, kcalbar, protebar, carboBar, grassibar;
    String nome;
    TextView tv_fabbisogno_n, tv_proteine_n, tv_grassi_n, tv_carbo_n, tv_nome, tv_acqua_n, tv_acqua_b,tv_fabbisogno_a,tv_proteine_a, tv_carbo_a, tv_grassi_a, tv_calorie_b;
    boolean first_access = true;
    private Calendar calendar;
    private SimpleDateFormat dateFormat;
    private String date;
    int INFORMATION_ACTIVITY = 1;
    int CIBI_ACTIVITY = 2;
    int ACQUA_ACTIVITY = 3;
    int ESE_ACTIVITY = 4;


    @Override
    protected void onPause() {
        super.onPause();

        // Creating a shared pref object
        // with a file name "MySharedPref"
        // in private mode
        SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref", MODE_PRIVATE);
        SharedPreferences.Editor myEdit = sharedPreferences.edit();

        // write all the data entered by the user in SharedPreference and apply
        myEdit.putString("nome", tv_nome.getText().toString());
        myEdit.putInt("fabbisogno", Integer.parseInt(tv_fabbisogno_n.getText().toString()));
        myEdit.putInt("proteine", Integer.parseInt(tv_proteine_n.getText().toString()));
        myEdit.putInt("carboidrati", Integer.parseInt(tv_carbo_n.getText().toString()));
        myEdit.putInt("grassi", Integer.parseInt(tv_grassi_n.getText().toString()));
        myEdit.putFloat("acqua_necessaria", Float.parseFloat(tv_acqua_n.getText().toString()));
        myEdit.putFloat("acqua_bevuta", Float.parseFloat(tv_acqua_b.getText().toString()));
        myEdit.putString("date", date);
        myEdit.putInt("calorie_bruciate", calorie_bruciate);
        myEdit.putInt("fabbisogno_assunto", Integer.parseInt(tv_fabbisogno_a.getText().toString()));
        myEdit.putInt("proteine_assunte", Integer.parseInt(tv_proteine_a.getText().toString()));
        myEdit.putInt("carboidrati_assunti", Integer.parseInt(tv_carbo_a.getText().toString()));
        myEdit.putInt("grassi_assunti", Integer.parseInt(tv_grassi_a.getText().toString()));
        myEdit.putInt("calorie_bruciate", Integer.parseInt(tv_calorie_b.getText().toString()));
        myEdit.apply();
    }


    @Override
    protected void onResume() {
        super.onResume();
        // Fetching the stored data
        // from the SharedPreference
        SharedPreferences sh = getSharedPreferences("MySharedPref", MODE_PRIVATE);
        nome = sh.getString("nome", "");
        fabbisogno = sh.getInt("fabbisogno_n", 0);
        proteine = sh.getInt("proteine_n",0);
        grassi = sh.getInt("grassi_n",0);
        carbo = sh.getInt("carboidrati_n",0);
        acqua_necessaria = sh.getFloat("acqua_necessaria", acqua_necessaria);
        date = sh.getString("date", date);
        acqua_bevuta = sh.getFloat("acqua_bevuta", acqua_bevuta);
        fabbisogno_assunto = sh.getInt("fabbisogno_assunto",0);
        prote_a = sh.getInt("proteine_assunte",0);
        carbo_a = sh.getInt("carboidrati_assunti",0);
        grassi_a = sh.getInt("grassi_assunti",0);
        calorie_bruciate = sh.getInt("calorie_bruciate",0);

        progressAcqua =(int) ((acqua_bevuta*100)/acqua_necessaria);
        progressKcal =(int) ((fabbisogno_assunto*100)/fabbisogno);
        progressProte =(int) ((prote_a*100)/proteine);
        progressCarbo =(int) ((carbo_a*100)/carbo);
        progressGrassi =(int) ((grassi_a*100)/grassi);

        if(prote_a >= proteine){
            tv_proteine_a.setTextColor(Color.RED);
        }else{
            tv_proteine_a.setTextColor(Color.GREEN);
        }
        if(grassi_a >= grassi ){
            tv_grassi_a.setTextColor(Color.RED);
        }else{
            tv_grassi_a.setTextColor(Color.GREEN);
        }

        if(carbo_a >= carbo){
            tv_carbo_a.setTextColor(Color.RED);
        }else{
            tv_carbo_a.setTextColor(Color.GREEN);
        }
        if(fabbisogno_assunto >= fabbisogno ){
            tv_fabbisogno_a.setTextColor(Color.RED);
        }else{
            tv_fabbisogno_a.setTextColor(Color.GREEN);
        }

        first_access = false;
        // Setting the fetched data
        // in the EditTexts

        //RESETTA LE VARIABILI SE IL GIORNO è DIVERSO
        if(!date.equalsIgnoreCase(dateFormat.format(calendar.getTime()))){
            date = dateFormat.format(calendar.getTime());
            tv_acqua_n.setText(String.valueOf(acqua_necessaria));
            progressAcqua = 0;
            progressProte = 0;
            progressCarbo = 0;
            progressKcal = 0;
            progressGrassi = 0;

            tv_acqua_b.setText(String.valueOf(0));
            acquaBar.setProgress(progressAcqua);
            protebar.setProgress(progressProte);
            carboBar.setProgress(progressCarbo);
            kcalbar.setProgress(progressKcal);
            grassibar.setProgress(progressGrassi);

            tv_nome.setText(nome);
            tv_fabbisogno_n.setText(String.valueOf(fabbisogno));
            tv_carbo_n.setText(String.valueOf(carbo));
            tv_proteine_n.setText(String.valueOf(proteine));
            tv_grassi_n.setText(String.valueOf(grassi));
            tv_calorie_b.setText(String.valueOf(0));
            tv_fabbisogno_a.setText(String.valueOf(0));
            tv_proteine_a.setText(String.valueOf(0));
            tv_carbo_a.setText(String.valueOf(0));
            tv_grassi_a.setText(String.valueOf(0));
        }else{
            tv_acqua_n.setText(String.valueOf(acqua_necessaria));
            tv_acqua_b.setText(String.valueOf(acqua_bevuta));
            tv_nome.setText(nome);
            tv_fabbisogno_n.setText(String.valueOf(fabbisogno));
            tv_carbo_n.setText(String.valueOf(carbo));
            tv_proteine_n.setText(String.valueOf(proteine));
            tv_grassi_n.setText(String.valueOf(grassi));
            acquaBar.setProgress(progressAcqua);
            protebar.setProgress(progressProte);
            carboBar.setProgress(progressCarbo);
            kcalbar.setProgress(progressKcal);
            grassibar.setProgress(progressGrassi);
            tv_fabbisogno_a.setText(String.valueOf(fabbisogno_assunto));
            tv_proteine_a.setText(String.valueOf(prote_a));
            tv_carbo_a.setText(String.valueOf(carbo_a));
            tv_grassi_a.setText(String.valueOf(grassi_a));
            tv_calorie_b.setText(String.valueOf(calorie_bruciate));
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent dat) {
        super.onActivityResult(requestCode, resultCode, dat);

        if (requestCode == 1 && resultCode == RESULT_OK && dat != null) {//CALCOLO INIZIALE
            nome = dat.getExtras().getString("nome");
            fabbisogno = dat.getExtras().getInt("fabbisogno");
            proteine = dat.getExtras().getInt("proteine");
            carbo = dat.getExtras().getInt("carboidrati");
            grassi = dat.getExtras().getInt("grassi");
            acqua_necessaria = dat.getExtras().getFloat("acqua_necessaria");
            fabbisogno_assunto = dat.getExtras().getInt("fabbisogno_assunto",0);
            tv_calorie_b.setText("0");
            tv_carbo_a.setText("0");
            tv_grassi_a.setText("0");
            tv_proteine_a.setText("0");
            tv_acqua_n.setText(String.valueOf(acqua_necessaria));
            tv_nome.setText(nome);
            tv_fabbisogno_n.setText(String.valueOf(fabbisogno));
            tv_carbo_n.setText(String.valueOf(carbo));
            tv_proteine_n.setText(String.valueOf(proteine));
            tv_grassi_n.setText(String.valueOf(grassi));


            SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref", MODE_PRIVATE);
            SharedPreferences.Editor myEdit = sharedPreferences.edit();

            myEdit.putInt("fabbisogno_assunto", 0);
            myEdit.putInt("proteine_assunte", 0);
            myEdit.putInt("carboidrati_assunti", 0);
            myEdit.putInt("grassi_assunti", 0);
            myEdit.apply();

        }else if(requestCode == 2 && resultCode == RESULT_OK & dat!= null){
            fabbisogno_assunto = dat.getExtras().getInt("fabbisogno_assunto");
            prote_a = dat.getExtras().getInt("proteine_assunte");
            grassi_a = dat.getExtras().getInt("grassi_assunti");
            carbo_a = dat.getExtras().getInt("carboidrati_assunti");
            tv_fabbisogno_a.setText(String.valueOf(fabbisogno_assunto));
            tv_proteine_a.setText(String.valueOf(prote_a));
            tv_carbo_a.setText(String.valueOf(carbo_a));
            tv_grassi_a.setText(String.valueOf(grassi_a));

        } else if(requestCode == 3 && resultCode == RESULT_OK & dat!= null){//ACQUA
            getAcqua_bevutaTMP = dat.getExtras().getFloat("acqua_bevuta");
            acqua_bevuta = acqua_bevuta + getAcqua_bevutaTMP;
            tv_acqua_b.setText(String.valueOf(acqua_bevuta));
        } else if(requestCode == 4 && resultCode == RESULT_OK & dat!= null){
            calorie_bruciate = dat.getExtras().getInt("calorie_bruciate");
            tv_calorie_b.setText(String.valueOf(calorie_bruciate));
        }

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        SharedPreferences sh = getSharedPreferences("MySharedPref", MODE_PRIVATE);
        nome = sh.getString("nome", "");

        Intent acqua = new Intent(this,  popupAcqua.class);
        Intent cibi = new Intent(this,  popupCibi.class);
        Intent eseIntent = new Intent(this,  popupEse.class);

        calendar = Calendar.getInstance();
        dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        date = dateFormat.format(calendar.getTime());
        aggiungiCibo = findViewById(R.id.btn_aggiungiCibo);
        aggiungiAcqua = findViewById(R.id.btn_aggiungiAcqua);
        aggiungiEse = findViewById(R.id.btn_aggiungiEse);
        reset = findViewById(R.id.reset);
        tv_calorie_b = findViewById(R.id.calorie_bruciate);
        tv_nome = findViewById(R.id.txtnome);
        tv_acqua_b = findViewById(R.id.acqua_bevuta);
        tv_acqua_n = findViewById(R.id.acqua_necessaria);
        tv_fabbisogno_n = findViewById(R.id.calorie_necessarie);
        tv_proteine_n = findViewById(R.id.proteine_necessarie);
        tv_grassi_n = findViewById(R.id.grassi_necessari);
        tv_carbo_n = findViewById(R.id.carboidrati_necessari);
        tv_fabbisogno_a = findViewById(R.id.calorie_assunte);
        tv_carbo_a = findViewById(R.id.carboidrati_assunti);
        tv_proteine_a = findViewById(R.id.proteine_assunte);
        tv_grassi_a = findViewById(R.id.grassi_assunti);
        acquaBar=(ProgressBar)findViewById(R.id.progressBarAcqua);
        kcalbar = (ProgressBar)findViewById(R.id.progressBarkcal);
        carboBar=(ProgressBar)findViewById(R.id.progressBarCarboidrati);
        protebar=(ProgressBar)findViewById(R.id.progressBarProteine);
        grassibar=(ProgressBar)findViewById(R.id.progressBarGrassi);

        if(nome.equalsIgnoreCase("")){
            first_access();
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(this);




        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builder.setTitle("Ricalcolo fabbisogno");
                builder.setMessage("Sei sicuro di voler ricalcolare il tuo fabbisogno?");
                builder.setNegativeButton("SI", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        first_access();
                        // Do nothing
                        dialog.dismiss();
                    }
                });

                builder.setPositiveButton("NO", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                AlertDialog alert = builder.create();
                alert.show();
            }
        });

        aggiungiCibo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(cibi, CIBI_ACTIVITY);
            }
        });

        aggiungiAcqua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(acqua, ACQUA_ACTIVITY);
            }
        });

        aggiungiEse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(eseIntent, ESE_ACTIVITY);
            }
        });

    }

    public void first_access(){


        tv_fabbisogno_a.setText("0");
        tv_grassi_a.setText("0");
        tv_proteine_a.setText("0");
        tv_carbo_a.setText("0");
        tv_calorie_b.setText("0");


        tv_proteine_a.setTextColor(Color.GREEN);
        tv_grassi_a.setTextColor(Color.GREEN);
        tv_carbo_a.setTextColor(Color.GREEN);
        tv_fabbisogno_a.setTextColor(Color.GREEN);

        SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref", MODE_PRIVATE);
        SharedPreferences.Editor myEdit = sharedPreferences.edit();
        myEdit.putInt("calorie_bruciate", 0);
        myEdit.putInt("fabbisogno_assunto", 0);
        myEdit.putInt("proteine_assunte", 0);
        myEdit.putInt("carboidrati_assunti", 0);
        myEdit.putInt("grassi_assunti", 0);
        myEdit.apply();


        Intent i = new Intent(this, information_page.class);
        startActivityForResult(i, INFORMATION_ACTIVITY);

    }
    @Override
    public void onBackPressed() {
        return;
    }
}