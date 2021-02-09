package com.example.ansengas.Ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.TextView;

import com.example.ansengas.DbHelper;
import com.example.ansengas.R;
import com.example.ansengas.getSetInvoice;
import com.example.ansengas.invoiceAdapter;

import java.util.ArrayList;

public class ViewOldInvoiceActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    TextView totamunt;
    private RecyclerView.LayoutManager layoutManager;
    invoiceAdapter adapter;
    ArrayList<getSetInvoice> arrayList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_old_invoice);

        totamunt = (TextView) findViewById(R.id.placeorderdaytotamunt);
        recyclerView = (RecyclerView) findViewById(R.id.showplaceorderdet);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        loadalldetails();

    }

    private void loadalldetails() {
//        arrayList.clear();

         DbHelper db_helper = new DbHelper(this);
        SQLiteDatabase database = db_helper.getReadableDatabase();

        Cursor cursor = db_helper.readfromlocaldatabasedailyplaceorderbilldetails(database);
        Double total = 0.0;
        while (cursor.moveToNext()) {
            String id = cursor.getString(cursor.getColumnIndex("ID"));
            String shop_name = cursor.getString(cursor.getColumnIndex("shop_name"));
            String netPrice = cursor.getString(cursor.getColumnIndex("netPrice"));
            String rejectReason = cursor.getString(cursor.getColumnIndex("rejectReason"));
            String billDaate = cursor.getString(cursor.getColumnIndex("date"));
//            getsetplaceorderitem contact = new getsetplaceorderitem(shop_name, mobile, add1, add2);
            getSetInvoice contact = new getSetInvoice(id,shop_name,netPrice,rejectReason,billDaate);
//            total = total + Double.parseDouble(billamount);
            arrayList.add(contact);
        }
        adapter = new invoiceAdapter(arrayList, ViewOldInvoiceActivity.this);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        cursor.close();
//        totamunt.setText(total.toString());
    }
}