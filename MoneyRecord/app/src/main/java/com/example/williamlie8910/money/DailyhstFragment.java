package com.example.williamlie8910.money;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;


/**
 * A simple {@link Fragment} subclass.
 */
public class DailyhstFragment extends ListFragment {


    DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();
    DatabaseReference mGetMany = mRootRef.child("Expend");
    Context c;
    String BulanTahun;
    String Tanggal;
    ArrayList<String> ArKey = new ArrayList<String>();

    public DailyhstFragment() {
        // Required empty public constructor

    }
    public DailyhstFragment newInstance(){
        return new DailyhstFragment();
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        c = context;
        BulanTahun = getArguments().getString("BULAN");
        Tanggal = getArguments().getString("TANGGAL");
        mGetMany.orderByChild("Bulan").equalTo(BulanTahun).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<String> ArBarang = new ArrayList<String>();
                for(DataSnapshot post : dataSnapshot.getChildren())
                {
                    if(post.child("Tanggal").getValue().toString().equals(Tanggal))
                    {
                        String Dta = post.child("Keterangan").getValue().toString() + "("+post.child("Kategori").getValue().toString() +") = "+
                                post.child("Harga").getValue().toString();
                       ArBarang.add(Dta);
                        ArKey.add(post.getKey());
                    }
                }

                ArrayAdapter aa = new ArrayAdapter(c,android.R.layout.simple_list_item_1,ArBarang);
                setListAdapter(aa);
                try{ registerForContextMenu(getListView());}
                catch (Exception ex){}

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });




    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        menu.add("Remove");
        super.onCreateContextMenu(menu, v, menuInfo);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo menuinfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        //Toast.makeText(getContext(),item.getTitle() + " " + menuinfo.position,Toast.LENGTH_SHORT).show();
        mGetMany.child(ArKey.get(menuinfo.position)).removeValue();
        Toast.makeText(getContext(),"Data telah terhapus",Toast.LENGTH_SHORT).show();
        return super.onContextItemSelected(item);
    }
}
