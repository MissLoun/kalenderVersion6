package de.projects.janap.a05_kalender;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.Calendar;

public class Kalender_Adapter extends BaseAdapter {

    ////////////////////////////////////////////////////////////////////////////////////////////////
    //Attribute
    private Context context;
    private int[] tage;
    private int datum = 1;
    private int ersterTag;

    private Kalender_Steuerung strg;
    private Boolean ersterKlick = true;
    private View altesView;

    private Calendar kalender;
    private int heuteTag = Kalender_Steuerung.heute.get(Calendar.DAY_OF_MONTH);
    private int heuteMonat = Kalender_Steuerung.heute.get(Calendar.MONTH);
    private int heuteJahr = Kalender_Steuerung.heute.get(Calendar.YEAR);

    ////////////////////////////////////////////////////////////////////////////////////////////////
    //Konstruktor
    public Kalender_Adapter(Context pContext, int[] pTage, Calendar pKalender, Kalender_Steuerung pStrg){
        this.context = pContext;
        this.tage = pTage;
        this.kalender = pKalender;
        this.strg = pStrg;
        kalender.set(Calendar.DAY_OF_MONTH, 1);
        ersterTag = kalender.get(Calendar.DAY_OF_WEEK)-2;   //da 1. Position 0 ist und - dem Tag
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    //Methoden


    @Override
    public int getCount() {
        return tage.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){

        //wenn covertView = 0 ist, wird eine neue Zelle festgelegt
        if (convertView == null) {
            final LayoutInflater layoutInflater = LayoutInflater.from(context);
            convertView = layoutInflater.inflate(R.layout.zelle_aktueller_monat, null);
        }

        TextView textViewTag = convertView.findViewById(R.id.textview_tag);

        /*--------------------Fehlerbehebung------------------------------------------------------*/
        if (position == 0){
            if (ersterTag == 0){
                datum = 1;  //Da Fehler mit dem ersten des Monats auftrat
            }
            if(ersterTag == -1){
                ersterTag = 6;  //Da -1 Sonntag ist tritt sonst ein Fehler auf
            }
        }

        /*--------------------erstellen des Kalenders---------------------------------------------*/
        if ( (position >= ersterTag) && !(position >= kalender.getActualMaximum(Calendar.DAY_OF_MONTH)+ersterTag) ){    //wenn die aktuelle Position groeßer ist als der erste Tag des Monats & wenn die position groeßer ist als das Ende des Monats

            if ((datum == heuteTag) && (kalender.get(Calendar.MONTH) == heuteMonat) && (kalender.get(Calendar.YEAR)== heuteJahr)){
                String aktuelleFarbedesMonats = strg.getAktuelleFarbe();
                textViewTag.setTextColor(Color.parseColor(aktuelleFarbedesMonats));  //beim heutigen Tag soll es eingefärbt werden
            }
            textViewTag.setText(""+datum); //setzte das Datum
            datum++;   //setzte Datum eins hoeher

            /*--------------------OnClick Methode-------------------------------------------------*/
            final int welcherTag = Integer.parseInt( (String) textViewTag.getText());
            textViewTag.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (ersterKlick == true) {  //wenn zum ersten mal ein Tag ausgewählt wird, wird der jetzige Tag zum "alten" Tag
                        altesView = v;
                        ersterKlick = false;
                    }
                    altesView.setBackgroundColor(Color.parseColor("#ffffff"));  //alter ausgewählter Tag wird wieder weiß
                    v.setBackgroundColor(Color.parseColor("#3ca2a2a2"));    //ausgewählter Tag wird eingefärbt
                    strg.setTxtMomentanesDatum(welcherTag); //ausgewähltes Datum wird dargestellt
                    altesView = v;  //alter ausgewählter Tag wird gespeichert um ihn später wieder weiß zu färben
                }
            });

        }

        return convertView;
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////
    //Ende der Klasse
}
