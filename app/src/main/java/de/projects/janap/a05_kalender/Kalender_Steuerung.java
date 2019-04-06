package de.projects.janap.a05_kalender;

import android.graphics.Color;
import android.icu.util.LocaleData;
import android.nfc.Tag;
import android.support.annotation.ColorInt;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;

public class Kalender_Steuerung extends AppCompatActivity {

    ////////////////////////////////////////////////////////////////////////////////////////////////
    //Attribute
    /*-------------------------Darstellung--------------------------------------------------------*/
    private TextView txtMonatAnzeige, txtHeutigerTag, txtMomentanesDatum;
    private GridView tabelleAktuellerMonat;
    private Button btnZuvor, btnWeiter;
    private LinearLayout wochentage;
    private ConstraintLayout trennbalken;

    /*-------------------------Andere Variablen---------------------------------------------------*/
    private String[] bezeichnungen = new String[11];
    private String[] farben = {"#FBC765", "#F08563", "#E76062", "#E53C6E", "#DC276B", "#9D286C", "#742964", "#562363", "#292563", "#153D6B", "#2A6C7C", "#40BD9C"};
    private int[] tageMax = new int[42]; //42 ist die maximale Anzahl der Zellen die die Tabelle brauchen koennte

    /*-------------------------Kalender-----------------------------------------------------------*/
    private Calendar kalender = Calendar.getInstance(); //erstellt einen Kalender mit aktuellen Datum Angaben
    static Calendar heute = Calendar.getInstance(); //Kalender mit heutigem Datum


    ////////////////////////////////////////////////////////////////////////////////////////////////
    //Methoden
    /*-------------------------Set Methoden-------------------------------------------------------*/
    public void setTxtMonatAnzeige(String pNeuerMonat){
        txtMonatAnzeige.setText(pNeuerMonat);
    }
    public void setTxtMomentanesDatum(int pTag) {
        kalender.set(Calendar.DAY_OF_MONTH, pTag);
        txtMomentanesDatum.setText((kalender.get(Calendar.DAY_OF_MONTH)) + " . " + (kalender.get(Calendar.MONTH)+1) + " . " + (kalender.get(Calendar.YEAR)));
    }

    /*-------------------------Andere Methoden----------------------------------------------------*/
    public void aktualisiereKalender(){
        Kalender_Adapter adapterAktuellerMonat = new Kalender_Adapter(this,  tageMax, kalender, this); //KalenderAdapter um den Kalender in der Tabelle darzustellen

        setTxtMonatAnzeige(bezeichnungen[kalender.get(Calendar.MONTH)]);  //setzt die neue Monatsbezeichnung fest

        txtMomentanesDatum.setText("" + (kalender.get(Calendar.YEAR)));
        tabelleAktuellerMonat.setAdapter(adapterAktuellerMonat);    //Kalender wird dargestellt

        aendereFarbe(kalender.get(Calendar.MONTH));
    }
    public void aendereFarbe(int pMonat){
        int farbe = Color.parseColor(farben[pMonat]);

        txtMonatAnzeige.setBackgroundColor(farbe);
        txtMomentanesDatum.setBackgroundColor(farbe);
        btnZuvor.setBackgroundColor(farbe);
        btnWeiter.setBackgroundColor(farbe);
        wochentage.setBackgroundColor(farbe);
        trennbalken.setBackgroundColor(farbe);

    }
    public String getAktuelleFarbe(){
        String aktuelleFarbe = farben[kalender.get(Calendar.MONTH)];
        return aktuelleFarbe;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    //Erstellung
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kalender__gui);

        tabelleAktuellerMonat = findViewById(R.id.gridView_Kalender_Tabelle_AktuellerMonat);

        txtMonatAnzeige = findViewById(R.id.txt_Kalender_Monat);
        txtHeutigerTag = findViewById(R.id.txt_Kalender_HeutigerTag);
        txtMomentanesDatum = findViewById(R.id.txt_Kalender_Momentanes_Datum);
        btnZuvor = findViewById(R.id.btn_Kalender_Zuvor);
        btnWeiter = findViewById(R.id.btn_Kalender_Weiter);
        wochentage = findViewById(R.id.layout_Kalender_Wochentage);
        trennbalken = findViewById(R.id.trennbalken);

        bezeichnungen = getResources().getStringArray(R.array.monate);

        txtHeutigerTag.setText("" + kalender.get(Calendar.DAY_OF_MONTH));

        aktualisiereKalender();     //der Monat wird mit den momentanen Daten des Kalenders dargestellt
        txtMomentanesDatum.setText("" + (heute.get(Calendar.DAY_OF_MONTH)) + " . " + (heute.get(Calendar.MONTH)+1) + " . " + (heute.get(Calendar.YEAR)));

        btnZuvor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                kalender.add(Calendar.MONTH,-1);    //der Monat des Kalenders wird um eins reduziert
                aktualisiereKalender(); //der Monat wird mit den momentanen Daten des Kalenders dargestellt
            }
        });

        btnWeiter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                kalender.add(Calendar.MONTH,1); //der Monat des Kalenders wird um eins addiert
                aktualisiereKalender(); //der Monat wird mit den momentanen Daten des Kalenders dargestellt
            }
        });

        txtHeutigerTag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                kalender.set(Calendar.YEAR, heute.get(Calendar.YEAR));
                kalender.set(Calendar.MONTH, heute.get(Calendar.MONTH));
                aktualisiereKalender();
                txtMomentanesDatum.setText("" + (heute.get(Calendar.DAY_OF_MONTH)) + " . " + (heute.get(Calendar.MONTH)+1) + " . " + (heute.get(Calendar.YEAR)));

            }
        });

    }
    ////////////////////////////////////////////////////////////////////////////////////////////////
    //Ende der Klasse
}

