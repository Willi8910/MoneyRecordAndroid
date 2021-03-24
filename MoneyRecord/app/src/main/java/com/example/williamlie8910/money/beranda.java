package com.example.williamlie8910.money;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static com.example.williamlie8910.money.R.layout.fragment_home;


/**
 * A simple {@link Fragment} subclass.
 */
public class beranda extends Fragment {

    DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();
    DatabaseReference mGetMany = mRootRef.child("Expend");
    View v;
    Date curDate = new Date();
    SimpleDateFormat format = new SimpleDateFormat("M-yyyy");
    public static String DateToStr;
    public static String Tanggal;
    DataSnapshot snap;

    public beranda() {
        // Required empty public constructor
    }
    public static beranda newInstance() {
        // Required empty public constructor
        return new beranda();
    }


    public void RefreshData(DataSnapshot dataSnapshot)
    {
        int bla = 0;
        int bli = 0;
        for(DataSnapshot post : dataSnapshot.getChildren())
        {

            bla += Integer.parseInt(post.child("Harga").getValue().toString());
            if(post.child("Tanggal").getValue().toString().equals(Tanggal))
            {
                // Toast.makeText(v.getContext(),post.child("Harga").getValue().toString(),Toast.LENGTH_LONG).show();
                bli+=Integer.parseInt(post.child("Harga").getValue().toString());
            }
        }
        snap = dataSnapshot;
        ((TextView)v.findViewById(R.id.txtTotal)).setText(String.valueOf(bla));
        ((TextView)v.findViewById(R.id.txtTotalDay)).setText(String.valueOf(bli));
        ((TextView)v.findViewById(R.id.txthomeTanggal)).setText("Tanggal : " + Tanggal + "-" + DateToStr);
        if(Tanggal.equals((new SimpleDateFormat("d")).format(curDate)))
        {
            ((Button)v.findViewById(R.id.btnhomeNext)).setVisibility(View.GONE);
        }
        else{
            ((Button)v.findViewById(R.id.btnhomeNext)).setVisibility(View.VISIBLE);
        }


    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
         v = inflater.inflate(fragment_home, container, false);
        ((Button)v.findViewById(R.id.btnhomeNext)).setVisibility(View.GONE);

            //((Button)v.findViewById(R.id.btnhomeNext)).setVisibility(View.GONE);


        ((TextView)v.findViewById(R.id.txthomeTanggal)).setText("Tanggal : " + Tanggal + "-" + DateToStr);
       // Toast11.makeText(v.getContext(),(new SimpleDateFormat("d")).format(curDate),Toast.LENGTH_SHORT).show();
        mGetMany.orderByChild("Bulan").equalTo(DateToStr).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                RefreshData(dataSnapshot);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        TextView txt = (TextView)v.findViewById(R.id.txtTotalDay);
        txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment frag = new DailyhstFragment();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction ft = fragmentManager.beginTransaction();
                Bundle bundle = new Bundle();
                bundle.putString("BULAN",DateToStr);
                bundle.putString("TANGGAL",Tanggal);
                frag.setArguments(bundle);
                ft.replace(R.id.fragment,frag);
                ft.addToBackStack(null);
                ft.commit();
            }
        });

        TextView ttl = (TextView)v.findViewById(R.id.txtTotal);
        ttl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment frag = new HistoryFragment();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction ft = fragmentManager.beginTransaction();
                Bundle bundle = new Bundle();
                bundle.putString("BULAN",DateToStr);
                //bundle.putString("TANGGAL",(new SimpleDateFormat("dd")).format(curDate));
                frag.setArguments(bundle);
                ft.replace(R.id.fragment,frag);
                ft.addToBackStack(null);
                ft.commit();
            }
        });

        Button Next = (Button)v.findViewById(R.id.btnhomeNext);
        Next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int dat = Integer.parseInt(Tanggal);
                dat++;
                Tanggal = String.valueOf(dat);
                RefreshData(snap);
                Toast.makeText(getActivity(),"Tanggal : " + Tanggal + "-" + DateToStr,Toast.LENGTH_SHORT).show();
                /*Fragment frag = new beranda();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction ft = fragmentManager.beginTransaction();
                Bundle bundle = new Bundle();
                bundle.putString("BULAN",DateToStr);
                bundle.putString("TANGGAL",String.valueOf(dat));
                frag.setArguments(bundle);
                ft.replace(R.id.fragment,frag);
                ft.addToBackStack(null);
                ft.commit();*/
            }
        });
        Button Prev = (Button)v.findViewById(R.id.btnhomePrev);
        Prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int dat = Integer.parseInt(Tanggal);
                dat--;
                if(dat ==0){Toast.makeText(v.getContext(),"Ini adalah Tanggal Minimun",Toast.LENGTH_SHORT).show();}
                else {
                    Tanggal = String.valueOf(dat);
                    RefreshData(snap);
                    Toast.makeText(getActivity(),"Tanggal : " + Tanggal + "-" + DateToStr,Toast.LENGTH_SHORT).show();
                   /* Fragment frag = new beranda();
                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    FragmentTransaction ft = fragmentManager.beginTransaction();
                    Bundle bundle = new Bundle();
                    bundle.putString("BULAN", DateToStr);
                    bundle.putString("TANGGAL", String.valueOf(dat));
                    frag.setArguments(bundle);
                    ft.replace(R.id.fragment, frag);
                    ft.addToBackStack(null);
                    ft.commit();*/
                }
            }
        });
        return v;
    }

}
