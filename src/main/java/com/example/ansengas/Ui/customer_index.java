package com.example.ansengas.Ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.ansengas.R;

public class customer_index extends AppCompatActivity {

    CardView bookingorder,payment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_index);

        bookingorder = (CardView) findViewById(R.id.bookingorder);
        payment = (CardView) findViewById(R.id.payment);

        bookingorder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(customer_index.this,InvoiceActivity.class);
                startActivity(intent);
            }
        });

        payment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(customer_index.this,invoice_payment.class);
                startActivity(intent);
            }
        });
    }
}