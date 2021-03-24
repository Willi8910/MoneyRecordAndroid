package com.example.williamlie8910.money;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.williamlie8910.money.R;

/**
 * Created by Williamlie8910 on 8/28/2017.
 */

public class MonthlyView extends ArrayAdapter {
    private final Context context;
    private final int[] ArTotal;
    private final String[] ArTanggal;
    private final TextView[] ArTxt;
    private final HistoryFragment hst;



    public MonthlyView(Context context, HistoryFragment hasfrag, int[] isi,String[] tgl) {
        super(context, R.layout.history_layout,tgl);
        this.context = context;
        hst = hasfrag;
       ArTanggal = tgl;
        ArTotal = isi;
        ArTxt = new TextView[ArTanggal.length];
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.history_layout,parent,false);

        TextView total= (TextView)v.findViewById(R.id.txthstTotal);
        ArTxt[position] = total;
       // final TextView tanggal= (TextView)v.findViewById(R.id.txthstTanggal);
        final TextView tanggalhid= (TextView)v.findViewById(R.id.txthidhstTanggal);
        Button btn = (Button)v.findViewById(R.id.btnhstInfo);
        total.setText("Total : "+ArTotal[position]);
       // tanggal.setText("Tanggal: " +ArTanggal[position]);
        tanggalhid.setText(""+ArTanggal[position]);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String dateee= ""+tanggalhid.getText();
                String[] tgl = dateee.split("-");
                hst.OpenDay(tgl[1]+"-"+tgl[2],tgl[0]);
               // Toast.makeText(context,dateee,Toast.LENGTH_SHORT).show();
            }
        });
        total.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // ((TextView)v.findViewById(R.id.txthstHidup)).setVisibility(View.VISIBLE);
               // ((TextView)v.findViewById(R.id.txthstKonsumsi)).setVisibility(View.VISIBLE);
                //((TextView)v.findViewById(R.id.txthstLainnya)).setVisibility(View.VISIBLE);
                //((TextView)v.findViewById(R.id.txthstSpesial)).setVisibility(View.VISIBLE);
                tanggalhid.setVisibility(View.INVISIBLE);
                view.setVisibility(View.INVISIBLE);
                //Toast.makeText(context,"tester",Toast.LENGTH_SHORT).show();
                hst.Refreshlist();
            }
        });



        return v;
    }
}
