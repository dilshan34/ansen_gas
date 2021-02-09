package com.example.ansengas;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ansengas.Ui.InvoiceActivity;
import com.example.ansengas.Ui.ViewCustomersActivity;

public class PaymentActivity extends AppCompatActivity {

    TextView txtNoOfItem, txtNetTotal, txtOutStanding, txtCreditLimit, txtBalance;
    EditText txtPayment, txtChequePayment;
    Button btnSave;
    double totSum = 0.0;
    String currentDate, inno, cusId = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        txtNoOfItem = (TextView) findViewById(R.id.txtNoOfItem);
        txtNetTotal = (TextView) findViewById(R.id.txtNetTotal);
        txtOutStanding = (TextView) findViewById(R.id.txtOutStanding);
        txtCreditLimit = (TextView) findViewById(R.id.txtCreditLimit);
        txtBalance = (TextView) findViewById(R.id.txtBalance);
        txtPayment = (EditText) findViewById(R.id.txtPayment);
        txtChequePayment = (EditText) findViewById(R.id.txtChequePayment);
        btnSave = (Button) findViewById(R.id.btnSave);

        Intent intent = getIntent();

        txtNoOfItem.setText(intent.getStringExtra("noOfItem"));
        txtNetTotal.setText(intent.getStringExtra("netTot"));
        txtOutStanding.setText(intent.getStringExtra("outStanding"));
        txtCreditLimit.setText(intent.getStringExtra("creditLimit"));
        currentDate = intent.getStringExtra("date");
        inno = intent.getStringExtra("inno");
        cusId = intent.getStringExtra("cusId");

        totSum = (Double.parseDouble(txtNetTotal.getText().toString()) + Double.parseDouble(txtOutStanding.getText().toString()));


        txtPayment.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String chequeVal = txtChequePayment.getText().toString();
                String paymentVal = txtPayment.getText().toString();
                if (!paymentVal.equals("")) {
                    if (!chequeVal.equals("")) {

                        double finalVal = Double.parseDouble(txtPayment.getText().toString()) + Double.parseDouble(chequeVal) - totSum;
                        txtBalance.setText(String.valueOf(finalVal));
                    } else {
                        double finalVal = Double.parseDouble(txtPayment.getText().toString()) - totSum;
                        txtBalance.setText(String.valueOf(finalVal));
                    }
                }
            }
        });

        txtChequePayment.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {


            }


            @Override
            public void afterTextChanged(Editable s) {
                String cashVal = txtPayment.getText().toString();
                if (!txtChequePayment.getText().toString().equals("")) {
                    if (!cashVal.equals("")) {

                        double finalVal = Double.parseDouble(txtPayment.getText().toString()) + Double.parseDouble(txtChequePayment.getText().toString()) - totSum;
                        txtBalance.setText(String.valueOf(finalVal));
                    } else {
                        double finalVal = Double.parseDouble(txtChequePayment.getText().toString()) - totSum;
                        txtBalance.setText(String.valueOf(finalVal));
                    }
                }
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveDataToDatabase();
            }
        });

    }

    private void saveDataToDatabase() {

        DbHelper db_helper = new DbHelper(this);
        SQLiteDatabase database = db_helper.getReadableDatabase();

        int rowId = db_helper.saveInvoice(cusId, txtNetTotal.getText().toString(),currentDate,"rejectReason", database);
        readfromlocaldatabasebillitemtempAndSave(String.valueOf(rowId));
    }

    private void readfromlocaldatabasebillitemtempAndSave(String invoId) {

        DbHelper db_helper = new DbHelper(this);
        SQLiteDatabase database = db_helper.getReadableDatabase();

        Cursor cursor = db_helper.readfromlocaldatabasebilliteminfotmp(database);
        Log.e("row count", "readfromlocaldatabasebillitemtemp: " + cursor.getCount());
        while (cursor.moveToNext()) {
            String invoiceId = cursor.getString(cursor.getColumnIndex("invoiceId"));
            String prductId = cursor.getString(cursor.getColumnIndex("prductId"));
            String fullQty = cursor.getString(cursor.getColumnIndex("fullQty"));
            String emptyQty = cursor.getString(cursor.getColumnIndex("emptyQty"));
            String refillQty = cursor.getString(cursor.getColumnIndex("refillQty"));
            String returnQty = cursor.getString(cursor.getColumnIndex("returnQty"));
            String newQty = cursor.getString(cursor.getColumnIndex("newQty"));
            String refillPrice = cursor.getString(cursor.getColumnIndex("refillPrice"));
            String newPrice = cursor.getString(cursor.getColumnIndex("newPrice"));
            String totalAmount = cursor.getString(cursor.getColumnIndex("totalAmount"));
            String product_name = cursor.getString(cursor.getColumnIndex("product_name"));
            String trustQty = cursor.getString(cursor.getColumnIndex("trustQty"));
            String trustRetuenQty = cursor.getString(cursor.getColumnIndex("trustRetuenQty"));

            db_helper.saveInvoiceItem(invoId, prductId, fullQty, emptyQty,trustQty,trustRetuenQty, refillQty, newQty, refillPrice,returnQty, newPrice, totalAmount, database);
        }
        Intent intent1 = new Intent(PaymentActivity.this, ViewCustomersActivity.class);
        Toast.makeText(PaymentActivity.this,"Payment Complete",Toast.LENGTH_LONG).show();
        startActivity(intent1);
    }
}