package com.example.williamlie8910.money;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;


/**
 * A simple {@link Fragment} subclass.
 */
public class input extends Fragment {

    DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();
    DatabaseReference mkondisi = mRootRef.child("Keluaran").child("Kategori");
    TextView txt;
    TextView Ket;
    TextView harga;
    DatePicker Tanggal;
    Spinner countryView;
    View v;

    public input() {
        // Required empty public constructor
    }
    public static input newInstance() {
        // Required empty public constructor
        return new input();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
         v = inflater.inflate(R.layout.fragment_input, container, false);


          countryView = (Spinner) v.findViewById(R.id.spinKategori);

        ArrayAdapter adapter = ArrayAdapter.createFromResource(v.getContext(), R.array.Kategori,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        countryView.setAdapter(adapter);

        Button btn = (Button)v.findViewById(R.id.btnInput);
         Ket = ((TextView)v.findViewById(R.id.txtKeterangan));
         harga = (TextView)v.findViewById(R.id.txtHarga);
         Tanggal = (DatePicker) v.findViewById(R.id.datePicker);
        Tanggal.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                Toast.makeText(v.getContext(),""+ Tanggal.getMonth(),Toast.LENGTH_SHORT).show();
                return false;
            }
        });
        int tgl =  getArguments().getInt("TANGGAL");
        int bln = getArguments().getInt("BULAN");
        int thn = getArguments().getInt("TAHUN");
        Tanggal.updateDate(thn,bln,tgl);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HashMap<String, Object> result = new HashMap<>();
                //result.put("uid", 16);
                result.put("Harga", Integer.parseInt(harga.getText().toString()));
                result.put("Kategori", countryView.getSelectedItem().toString().trim());
                result.put("Keterangan", Ket.getText().toString());
                result.put("Tanggal", ""+Tanggal.getDayOfMonth());
                result.put("Bulan", ""+(Tanggal.getMonth()+1) +"-" + Tanggal.getYear());
                //result.put("stars", "Bioocion");

                mRootRef.child("Expend").push().setValue(result);
                Ket.setText("");
                countryView.setSelection(0);
                harga.setText("");

                Toast.makeText(v.getContext(),"Data telah dimasukkan",Toast.LENGTH_SHORT).show();
            }
        });

      /*  mkondisi.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String kata = dataSnapshot.getValue(String.class);
               // txt.setText(kata);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });*/
        return v;
    }

}
