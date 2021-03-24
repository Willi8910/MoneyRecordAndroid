package com.example.williamlie8910.money;


import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;


public class home extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener{
    TextView txt ;
    Button panas;
    Button dingin;

    DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();
    DatabaseReference mkondisi = mRootRef.child("Keluaran").child("Kategori");
    DatabaseReference mGetMany = mRootRef.child("Expend");

    ValueEventListener evts;
    Fragment floatfrag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        Date curDate = new Date();
        SimpleDateFormat format = new SimpleDateFormat("M-yyyy");
        beranda.DateToStr = format.format(curDate);
        beranda.Tanggal = (new SimpleDateFormat("d")).format(curDate);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                       // .setAction("Action", null).show();

                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction ft = fragmentManager.beginTransaction();
                floatfrag =input.newInstance();
                int tgl = Integer.parseInt(beranda.Tanggal);
                String[] blthn = beranda.DateToStr.split("-");
                int bln = Integer.parseInt(blthn[0])-1;
                int thn = Integer.parseInt(blthn[1]);
                Bundle bundle = new Bundle();
                bundle.putInt("BULAN",bln);
                bundle.putInt("TAHUN",thn);
                bundle.putInt("TANGGAL",tgl);
                floatfrag.setArguments(bundle);
                ft.replace(R.id.fragment,floatfrag);
                ft.addToBackStack(null);
                ft.commit();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        Fragment frag = beranda.newInstance();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction ft = fragmentManager.beginTransaction();
            ft.replace(R.id.fragment,frag);
            ft.addToBackStack(null);
            ft.commit();

      /*  txt = (TextView)findViewById(R.id.txt);
        panas = (Button)findViewById(R.id.btnPanas);
        panas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mkondisi.setValue("Panas");
            }
        });
        dingin = (Button)findViewById(R.id.btnDingin);
        dingin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mkondisi.setValue("Dingin");
            }
        });*/

       /* HashMap<String, Object> result = new HashMap<>();
        //result.put("uid", 16);
        result.put("author", "Bloastiosi");
        result.put("title", "Geogastor");
        result.put("body", "Flababe");
        result.put("starCount", 21);
        result.put("stars", "Bioocion");

        mRootRef.child("Kagegashi").child("uid").setValue(result);
        mRootRef.child("Kagegashi").child("title").removeValue();
        mRootRef.child("Kagegashi").child("starCount").removeValue();*/

        //mRootRef.child("Kagegashi").push().setValue(result);
       // Toast.makeText(getApplicationContext(),mRootRef.child("Kagegashi").getKey(),Toast.LENGTH_LONG).show();

       // Toast.makeText(getApplicationContext(),String.valueOf(numberOfDaysInMonth(1,2017)),Toast.LENGTH_SHORT).show();
        String lala = "2-5-2017";
        String[] lol = lala.split("-");
        //Toast.makeText(getApplicationContext(),lol[0] +"   " + lol[2],Toast.LENGTH_LONG).show();
    }

    public static int numberOfDaysInMonth(int month, int year) {
        Calendar monthStart = new GregorianCalendar(year, month, 1);
        return monthStart.getActualMaximum(Calendar.DAY_OF_MONTH);
    }

    @Override
    protected void onStart() {
        super.onStart();

       /* evts = mGetMany.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                int bla = 0;
                for(DataSnapshot post : dataSnapshot.getChildren())
                {
                    bla += Integer.parseInt(post.child("Harga").getValue().toString());
                }
                ((TextView)findViewById(R.id.txtTotal)).setText(String.valueOf(bla));
                //Toast.makeText(getApplicationContext(),bla,Toast.LENGTH_LONG).show();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        mGetMany.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String bla = "";
                for(DataSnapshot post : dataSnapshot.getChildren())
                {
                    bla += post.child("author").getValue() + ", ";
                }
                //Toast.makeText(getApplicationContext(),bla,Toast.LENGTH_LONG).show();
                mkondisi.setValue(bla);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        mkondisi.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String kata = dataSnapshot.getValue(String.class);
//                txt.setText(kata);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });*/
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Fragment frag;
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction ft = fragmentManager.beginTransaction();
        if (id == R.id.nav_home) {

            frag = beranda.newInstance();
            floatfrag =input.newInstance();
            ft.replace(R.id.fragment,frag);
            ft.addToBackStack(null);
            ft.commit();
            // Handle the camera action
           // Intent i = new Intent(getApplicationContext(), MainActivity.class);
            //startActivity(i);
        } else if (id == R.id.nav_Add) {
            //mGetMany.removeEventListener(evts);
            frag = input.newInstance();
            floatfrag =beranda.newInstance();
            ft.replace(R.id.fragment,frag);
            ft.addToBackStack(null);
            ft.commit();
        } else if (id == R.id.nav_history) {
            frag = new HistoryFragment();
            ft.replace(R.id.fragment,frag);
            ft.addToBackStack(null);
            ft.commit();
        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void OnListFragmentInteractionListener(String s)
    {

    }

}
