package com.example.ansengas.Ui;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ansengas.Adapters.CustomerAdapter;
import com.example.ansengas.Adapters.UnpaidBillAdapter;
import com.example.ansengas.R;
import com.example.ansengas.models.getSetCustomer;
import com.example.ansengas.models.getsetUnpaidBill;

import java.util.ArrayList;

public class invoice_payment extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;

    ArrayList<getsetUnpaidBill> arrayList = new ArrayList<>();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.invoice_payment);

        recyclerView = (RecyclerView) findViewById(R.id.invoicerecyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        adapter = new UnpaidBillAdapter(arrayList, invoice_payment.this);
        recyclerView.setAdapter(adapter);




    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        }
}
