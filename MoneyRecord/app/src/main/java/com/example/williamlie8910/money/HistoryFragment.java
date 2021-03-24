package com.example.williamlie8910.money;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;


/**
 * A simple {@link Fragment} subclass.
 */
public class HistoryFragment extends ListFragment {

    DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();
    DatabaseReference mGetMany = mRootRef.child("Expend");
    String BulanTahun;
    String Tanggal;
    Context c;
    String[] ArTanggal;
    HistoryFragment hst = this;
    MonthlyView mon;

    public HistoryFragment() {
        // Required empty public constructor
    }

    public HistoryFragment newInstance(){
        return new HistoryFragment();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        ArrayAdapter aa = ArrayAdapter.createFromResource(context, R.array.Kategori,android.R.layout.simple_expandable_list_item_1);

        String[] a = {"ini", "adalah", "contohn","aaaa"};
        String[] b = {"jangan", "dianggap", "Susah","aaaada"};
        //MonthlyView mv = new MonthlyView(context,a,b);
      //  setListAdapter(mv);

        c = context;
        BulanTahun = getArguments().getString("BULAN");
       // String[] Arbt = BulanTahun.split("-");

      //  Tanggal = getArguments().getString("TANGGAL");
        mGetMany.orderByChild("Bulan").equalTo(BulanTahun).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
               // ArrayList<String> ArBarang = new ArrayList<String>();
                int[] ArTotal = new int[31];
                Arrays.fill(ArTotal,0);
                String[] Arbt = BulanTahun.split("-");
                 ArTanggal = new String[numberOfDaysInMonth(Integer.parseInt(Arbt[0])-1,Integer.parseInt(Arbt[1]))];
                for (int i=0;i<ArTanggal.length;i++)
                {
                    ArTanggal[i] = ""+(i+1)+ "-"+BulanTahun;
                }


                for(DataSnapshot post : dataSnapshot.getChildren())
                {
                    int Datee = Integer.parseInt(post.child("Tanggal").getValue().toString());
                    ArTotal[Datee-1] += Integer.parseInt(post.child("Harga").getValue().toString());
                }

                mon = new MonthlyView(c,hst,ArTotal,ArTanggal);
                setListAdapter(mon);
            }
            public int numberOfDaysInMonth(int month, int year) {
                Calendar monthStart = new GregorianCalendar(year, month, 1);
                return monthStart.getActualMaximum(Calendar.DAY_OF_MONTH);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);



       /* Fragment frag = new DailyhstFragment();
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction ft = fragmentManager.beginTransaction();
        Bundle bundle = new Bundle();
        String[] tgl = ArTanggal[position].split("-");

        bundle.putString("BULAN",tgl[1]+"-"+tgl[2]);
        bundle.putString("TANGGAL",tgl[0]);
        frag.setArguments(bundle);
        ft.replace(R.id.fragment,frag);
        ft.addToBackStack(null);
        ft.commit();*/
    }

    public void OpenDay(String bulan,String tanggal)
    {
        Fragment frag = new DailyhstFragment();
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction ft = fragmentManager.beginTransaction();
        Bundle bundle = new Bundle();


        bundle.putString("BULAN",bulan);
        bundle.putString("TANGGAL",tanggal);
        frag.setArguments(bundle);
        ft.replace(R.id.fragment,frag);
        ft.addToBackStack(null);
        ft.commit();
    }

    public void Refreshlist()
    {
        setListAdapter(mon);
    }





}
