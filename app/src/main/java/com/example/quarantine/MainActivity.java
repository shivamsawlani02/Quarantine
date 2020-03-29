package com.example.quarantine;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.SearchView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayDeque;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    TextView nameOfMarket,phone,crowd;
    RecyclerView itemsRV;
    DatabaseReference databaseReference;
    String productName,quantity;
    ItemsRVAdapter adapter;
    SearchView searchView;
    ArrayList<Items> itemlist=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        attachID();

        //nameOfMarket intent se le ke setText kar dena

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                phone.setText(dataSnapshot.child("phone").getValue().toString());
                crowd.setText(dataSnapshot.child("crowd").getValue().toString());
                productName=dataSnapshot.child("item").child("name").getValue().toString();
                quantity=dataSnapshot.child("item").child("quantity").getValue().toString();
                itemlist.add(new Items(productName,quantity));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        initRecyclerView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                adapter.getFilter().filter(s);
                return false;
            }
        });
    }
    private void initRecyclerView()
    {
        itemsRV=findViewById(R.id.rv_items);
        itemsRV.setLayoutManager(new GridLayoutManager(this,3));
        itemsRV.hasFixedSize();
        adapter=new ItemsRVAdapter(itemlist,this);
        itemsRV.setAdapter(adapter);

    }

    private void attachID() {
        nameOfMarket=findViewById(R.id.tv_name_of_market);
        phone=findViewById(R.id.tv_phone);
        crowd=findViewById(R.id.tv_crowded);
        searchView=findViewById(R.id.search_view);
        databaseReference=FirebaseDatabase.getInstance().getReference();//yahan par child add kar dena jiska bhi reference chahiye ho
        //maine assume kiya hai ki ek market ke different child honge as phone,crowd,items.Items ke further do child honge product name and quantity.


    }
}
