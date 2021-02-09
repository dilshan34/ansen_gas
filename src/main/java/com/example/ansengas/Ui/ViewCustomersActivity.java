package com.example.ansengas.Ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;

import com.example.ansengas.Adapters.CustomerAdapter;
import com.example.ansengas.DbHelper;
import com.example.ansengas.R;
import com.example.ansengas.models.getSetCustomer;

import java.util.ArrayList;

public class ViewCustomersActivity extends AppCompatActivity {

    CustomerAdapter adapter;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;

    ArrayList<getSetCustomer> arrayList = new ArrayList<>();
    private Toolbar mtoolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_customers);

        recyclerView = (RecyclerView) findViewById(R.id.all_customershow);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        mtoolbar = (Toolbar) findViewById(R.id.toolBAr);
        setSupportActionBar(mtoolbar);
        getSupportActionBar().setTitle("All Customer Details");

        showallcustomerdetails();
    }


    private void showallcustomerdetails() {
        arrayList.clear();
        DbHelper db_helper = new DbHelper(this);
        SQLiteDatabase database = db_helper.getReadableDatabase();

        Cursor cursor = db_helper.getAllCustomers(database);
        while (cursor.moveToNext()) {
            String cusid = cursor.getString(cursor.getColumnIndex("cusid"));
            String shop_name = cursor.getString(cursor.getColumnIndex("shop_name"));
            String mobile = cursor.getString(cursor.getColumnIndex("mobile"));
            String outStanding = cursor.getString(cursor.getColumnIndex("outStanding"));
            String address = cursor.getString(cursor.getColumnIndex("address"));
            String creditlimit = cursor.getString(cursor.getColumnIndex("creditlimit"));

            getSetCustomer contact = new getSetCustomer(cusid,shop_name,mobile,outStanding,address,creditlimit);
            arrayList.add(contact);
        }
        adapter = new CustomerAdapter(arrayList, ViewCustomersActivity.this);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        cursor.close();
    }

    @Override
    public void onBackPressed() {
        Intent intent=new Intent(ViewCustomersActivity.this,HomeActivity.class);
        startActivity(intent);
    }
}