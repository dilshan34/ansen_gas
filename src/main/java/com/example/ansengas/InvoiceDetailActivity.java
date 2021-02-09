package com.example.ansengas;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.ansengas.Ui.InvoiceActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class InvoiceDetailActivity extends AppCompatActivity {
    TextView placeorderid,txtBillDate,txtCusName,txtNetTotal,txtCashPayment,txtChequePayment,txtBalance;
    billItemtempAdapter adapter;
    ArrayList<getsetbillitemtemp> arrayList = new ArrayList<>();
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManagerl;
    String formattedtimedate="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invoice_detail);
        placeorderid=(TextView)findViewById(R.id.placeorderid);
        txtBillDate=(TextView)findViewById(R.id.txtBillDate);
        txtCusName=(TextView)findViewById(R.id.txtCusName);
        txtNetTotal=(TextView)findViewById(R.id.txtNetTotal);
        txtCashPayment=(TextView)findViewById(R.id.txtCashPayment);
        txtChequePayment=(TextView)findViewById(R.id.txtChequePayment);
        txtBalance=(TextView)findViewById(R.id.txtBalance);

        recyclerView = (RecyclerView) findViewById(R.id.viewallplaceorderitem);
        layoutManagerl = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManagerl);
        recyclerView.setHasFixedSize(true);

        Calendar c = Calendar.getInstance();
        SimpleDateFormat dfnew = new SimpleDateFormat("yyyy-MM-dd");
        formattedtimedate = dfnew.format(c.getTime());

        Intent intent=getIntent();
        Constant.invo=false;

        placeorderid.setText(intent.getStringExtra("invo"));
        txtBillDate.setText(intent.getStringExtra("billDate"));
        txtCusName.setText(intent.getStringExtra("cusName"));
        txtNetTotal.setText(intent.getStringExtra("netPrice"));
        txtCashPayment.setText(intent.getStringExtra("cash"));
        txtChequePayment.setText(intent.getStringExtra("cheque"));
        txtBalance.setText(intent.getStringExtra("balance"));
        readfromlocaldatabasebillitemtemp();

    }


    private void readfromlocaldatabasebillitemtemp() {
        arrayList.clear();
        DbHelper db_helper = new DbHelper(this);
        SQLiteDatabase database = db_helper.getReadableDatabase();

        Cursor cursor = db_helper.readfromlocaldatabasebilliteminfo(placeorderid.getText().toString(),database);
        Log.e("row count", "readfromlocaldatabasebillitemtemp: " + cursor.getCount());
        while (cursor.moveToNext()) {
            String invoiceId = cursor.getString(cursor.getColumnIndex("invoiceId"));
            String prductId = cursor.getString(cursor.getColumnIndex("prductId"));
            String fullQty = cursor.getString(cursor.getColumnIndex("fullQty"));
            String emptyQty = cursor.getString(cursor.getColumnIndex("emptyQty"));
            String trustQty = cursor.getString(cursor.getColumnIndex("trustQty"));
            String trustRetuenQty = cursor.getString(cursor.getColumnIndex("trustRetuenQty"));
            String refillQty = cursor.getString(cursor.getColumnIndex("refillQty"));
            String returnQty = cursor.getString(cursor.getColumnIndex("returnQty"));
            String newQty = cursor.getString(cursor.getColumnIndex("newQty"));
            String refillPrice = cursor.getString(cursor.getColumnIndex("refillPrice"));
            String newPrice = cursor.getString(cursor.getColumnIndex("newPrice"));
            String totalAmount = cursor.getString(cursor.getColumnIndex("totalAmount"));
            String product_name = cursor.getString(cursor.getColumnIndex("product_name"));

            getsetbillitemtemp contact = new getsetbillitemtemp(invoiceId, prductId, fullQty, emptyQty,trustQty,trustRetuenQty, refillQty,returnQty, newQty,refillPrice, newPrice, totalAmount, product_name);
            arrayList.add(contact);
        }
        adapter = new billItemtempAdapter(arrayList, InvoiceDetailActivity.this);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        cursor.close();
    }
}