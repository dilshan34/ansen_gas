package com.example.ansengas.Ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ansengas.Constant;
import com.example.ansengas.DbHelper;
import com.example.ansengas.Adapters.ItemCategoryAdapter;
import com.example.ansengas.PaymentActivity;
import com.example.ansengas.R;
import com.example.ansengas.billItemtempAdapter;
import com.example.ansengas.getsetbillitemtemp;
import com.example.ansengas.models.getSetCategory;
import com.example.ansengas.models.getSetNewItem;
import com.example.ansengas.Adapters.itemAdapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

public class InvoiceActivity extends AppCompatActivity {

    public TextView placeorderid, txtplaceorderdate, txtCredit, txtSalePrice, txtRefillPrice, txtItemCount, txtNetTotal, txtrejectReason,txtAvaQty;
    public RecyclerView categoryView, itemView, recyclerView;
    public EditText txtFull, txtEmpty, txtRefil, txtNew, txtQty, txtReturn;
    RecyclerView.LayoutManager layoutManagerl, layoutManagerCat, layoutManagerItemView;
    LinearLayout layoutItem, layoutCat, layoutAss, layoutAddBtn, layoutShopDet;
    ArrayList<getSetCategory> arrayListCategory = new ArrayList<>();
    ArrayList<getSetNewItem> arrayListItem = new ArrayList<>();
    ArrayList<getsetbillitemtemp> arrayList = new ArrayList<>();
    ItemCategoryAdapter itemCategoryAdapter;
    itemAdapter itemAdapter;
    String cusId = "", outstanding = "", formattedtimedate = "", creditLimit = "";
    private Button btnBackItem, btnplaceadd, btnaddAs, btnplacefinish, btnAddShop, btnTrustAdd, btnRejectReason;
    public String ItemId = "", totAmount = "", trustQty = "0", trustReturnQty = "0", shopEmptyQty = "0", shopFullQtu = "0",reasonId="",productAvailableQty="";
    billItemtempAdapter adapter;
    Dialog mydiallog;
    AlertDialog.Builder builder;
    private String[] namenew;
    HashMap<String, String> rejectReason = new HashMap<String, String>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invoice);

        placeorderid = (TextView) findViewById(R.id.placeorderid);
        txtplaceorderdate = (TextView) findViewById(R.id.txtplaceorderdate);
        txtCredit = (TextView) findViewById(R.id.txtCredit);
        txtSalePrice = (TextView) findViewById(R.id.txtSalePrice);
        txtRefillPrice = (TextView) findViewById(R.id.txtRefillPrice);
        txtNetTotal = (TextView) findViewById(R.id.txtNetTotal);
        txtItemCount = (TextView) findViewById(R.id.txtItemCount);
        txtrejectReason = (TextView) findViewById(R.id.txtrejectReason);
        txtAvaQty = (TextView) findViewById(R.id.txtAvaQty);

        txtFull = (EditText) findViewById(R.id.txtFull);
        txtEmpty = (EditText) findViewById(R.id.txtEmpty);
        txtRefil = (EditText) findViewById(R.id.txtRefil);
        txtNew = (EditText) findViewById(R.id.txtNew);
        txtQty = (EditText) findViewById(R.id.txtQty);
        txtReturn = (EditText) findViewById(R.id.txtReturn);
        layoutCat = (LinearLayout) findViewById(R.id.layoutCat);
        layoutItem = (LinearLayout) findViewById(R.id.layoutItem);
        layoutAss = (LinearLayout) findViewById(R.id.layoutAss);
        layoutAddBtn = (LinearLayout) findViewById(R.id.layoutAddBtn);
        layoutShopDet = (LinearLayout) findViewById(R.id.layoutShopDet);

        btnBackItem = (Button) findViewById(R.id.btnBackItem);
        btnplaceadd = (Button) findViewById(R.id.btnplaceadd);
        btnaddAs = (Button) findViewById(R.id.btnaddAs);
        btnplacefinish = (Button) findViewById(R.id.btnplacefinish);
        btnAddShop = (Button) findViewById(R.id.btnAddShop);
        btnTrustAdd = (Button) findViewById(R.id.btnTrustAdd);
        btnRejectReason = (Button) findViewById(R.id.btnRejectReason);

        layoutAss.setVisibility(View.GONE);
        layoutAddBtn.setVisibility(View.GONE);
        layoutShopDet.setVisibility(View.GONE);

        recyclerView = (RecyclerView) findViewById(R.id.viewallplaceorderitem);
        layoutManagerl = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManagerl);
        recyclerView.setHasFixedSize(true);

        categoryView = (RecyclerView) findViewById(R.id.categoryView);
        layoutManagerCat = new LinearLayoutManager(InvoiceActivity.this, LinearLayoutManager.HORIZONTAL, false);
        categoryView.setLayoutManager(layoutManagerCat);
        categoryView.setHasFixedSize(true);

        itemView = (RecyclerView) findViewById(R.id.itemView);
        layoutManagerItemView = new LinearLayoutManager(InvoiceActivity.this, LinearLayoutManager.HORIZONTAL, false);
        itemView.setLayoutManager(layoutManagerItemView);
        itemView.setHasFixedSize(true);

        getmaxinvoicenumber();
        readfromlocaldatabasebillitemtemp();

        Calendar c = Calendar.getInstance();
        SimpleDateFormat dfnew = new SimpleDateFormat("yyyy-MM-dd");
        formattedtimedate = dfnew.format(c.getTime());
        txtplaceorderdate.setText(formattedtimedate);
        Constant.invo = true;

        Intent intent = getIntent();
        cusId = intent.getStringExtra("cusId");
        outstanding = intent.getStringExtra("outstanding");
        creditLimit = intent.getStringExtra("creditLimit");

        txtCredit.setText(outstanding);
        getAllCategory();

        btnBackItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                layoutItem.setVisibility(View.GONE);
                layoutCat.setVisibility(View.VISIBLE);
            }
        });

        btnplaceadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveInvoiceItemTempGas();
            }
        });
        btnAddShop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogBox("shop");
            }
        });

        btnTrustAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogBox("trust");
            }
        });

        btnaddAs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveInvoiceItemTempAss();
            }
        });

        btnplacefinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent1 = new Intent(InvoiceActivity.this, PaymentActivity.class);
//                intent1.putExtra("noOfItem", txtItemCount.getText().toString());
//                intent1.putExtra("netTot", txtNetTotal.getText().toString());
//                intent1.putExtra("outStanding", outstanding);
//                intent1.putExtra("creditLimit", creditLimit);
//                intent1.putExtra("date", formattedtimedate);
//                intent1.putExtra("inno", placeorderid.getText().toString());
//                intent1.putExtra("cusId", cusId);
//                startActivity(intent1);
                saveDataToDatabase();
            }
        });

        btnRejectReason.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DbHelper db_helper = new DbHelper(InvoiceActivity.this);
                SQLiteDatabase database = db_helper.getReadableDatabase();

                Cursor cursorReason = db_helper.getAllRejectReason(database);
                int ii = cursorReason.getCount();
                namenew = new String[ii];

                //     Toast.makeText(HomeActivity.this,count,Toast.LENGTH_SHORT).show();
                while (cursorReason.moveToNext()) {
                    int i = 0;
                    String Id = cursorReason.getString(cursorReason.getColumnIndex("Id"));
                    String reason = cursorReason.getString(cursorReason.getColumnIndex("reason"));

                    rejectReason.put(reason, Id);
                    namenew[i] = reason;
                    i++;
                }


                final AlertDialog.Builder mybulder = new AlertDialog.Builder(InvoiceActivity.this);
                mybulder.setTitle("Select Booking Type");
                mybulder.setSingleChoiceItems(namenew, -1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        txtrejectReason.setText(namenew[i].toString());
                        dialogInterface.dismiss();
                        getRejectReason(namenew[i].toString());
                    }
                });
                mybulder.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                mybulder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        txtrejectReason.setText(namenew[i].toString());
                        getRejectReason(namenew[i].toString());
                        dialogInterface.dismiss();
                    }
                });

                AlertDialog mdialog = mybulder.create();
                mdialog.setCanceledOnTouchOutside(false);
                mdialog.show();

            }
        });

    }

    private void saveInvoiceItemTempGas() {
        try {

            DbHelper db_helper = new DbHelper(this);
            SQLiteDatabase database = db_helper.getReadableDatabase();
            int totQty = Integer.parseInt(trustQty) + Integer.parseInt(txtRefil.getText().toString()) + Integer.parseInt(txtNew.getText().toString());

            if (!txtRefil.getText().toString().equals("") && !txtNew.getText().toString().equals("") && !txtSalePrice.getText().toString().equals("") && !txtAvaQty.getText().toString().equals("")) {

                if (checkProdustExistrNot(ItemId)) {
                    Toast.makeText(InvoiceActivity.this, "Item Already Exist", Toast.LENGTH_LONG).show();
                } else if (totQty > Integer.parseInt(txtAvaQty.getText().toString())) {
                    Toast.makeText(InvoiceActivity.this, "Quantity not Available", Toast.LENGTH_LONG).show();
                } else {
//        if (txtFull.getText().toString() != "" && txtEmpty.getText().toString() != "" && txtRefil.getText().toString() != "" && txtNew.getText().toString() != "") {
                    double tot = (Double.parseDouble(txtRefil.getText().toString()) * Double.parseDouble(txtRefillPrice.getText().toString())) + (Double.parseDouble(txtNew.getText().toString()) * Double.parseDouble(txtSalePrice.getText().toString()));
//            db_helper.saveInvoiceItemTemp(placeorderid.getText().toString(), ItemId, shopFullQtu, shopFullQtu, trustQty, trustReturnQty, txtRefil.getText().toString(), txtReturn.getText().toString(), txtNew.getText().toString(), txtRefillPrice.getText().toString(), txtSalePrice.getText().toString(), String.valueOf(tot), database);
                    //}


                    db_helper.saveInvoiceItemTemp(placeorderid.getText().toString(), ItemId, shopFullQtu, shopEmptyQty, trustQty, trustReturnQty, txtRefil.getText().toString(), txtReturn.getText().toString(), txtNew.getText().toString(), txtRefillPrice.getText().toString(), txtSalePrice.getText().toString(), String.valueOf(tot), database);
                    int itemCount = Integer.parseInt(txtItemCount.getText().toString()) + 1;
                    txtItemCount.setText(String.valueOf(itemCount));

                    double netTot = Double.parseDouble(txtNetTotal.getText().toString()) + tot;
                    txtNetTotal.setText(String.valueOf(netTot));

                    readfromlocaldatabasebillitemtemp();
                    txtFull.setText("");
                    txtEmpty.setText("");
                    txtRefil.setText("");
                    txtNew.setText("");
                    shopEmptyQty = "0";
                    shopFullQtu = "0";
                    trustQty = "0";
                    trustReturnQty = "0";
                }
            } else {
                if (txtNew.getText().toString().equals("")) {
                    txtNew.setError("Enter New Quantity");
                } else if (txtRefil.getText().toString().equals("")) {
                    txtRefil.setError("Enter refill quantity");
                } else if (txtSalePrice.getText().toString().equals("") || !txtAvaQty.getText().toString().equals("")) {

                    txtSalePrice.setError("Please Select Item");
                }
            }
        }

        catch (Exception ex){
            Toast.makeText(InvoiceActivity.this,"Please Enter Value For text and Select Item",Toast.LENGTH_LONG).show();
        }


    }

    private void saveInvoiceItemTempAss() {

        DbHelper db_helper = new DbHelper(this);
        SQLiteDatabase database = db_helper.getReadableDatabase();


        if (checkProdustExistrNot(ItemId)) {
            Toast.makeText(InvoiceActivity.this, "ItemAlready Exist", Toast.LENGTH_LONG).show();
        }
        else if(Integer.parseInt(txtQty.getText().toString())>Integer.parseInt(txtAvaQty.getText().toString())){
            Toast.makeText(InvoiceActivity.this, "Quantity not Available", Toast.LENGTH_LONG).show();
        }

        else {

            if (txtQty.getText().toString() != "" && txtSalePrice.getText().toString() != "") {

                double tot = Double.parseDouble(txtQty.getText().toString()) * Double.parseDouble(txtSalePrice.getText().toString());

                db_helper.saveInvoiceItemTemp(placeorderid.getText().toString(), ItemId, "0", "0", "0", "0", "0", "0", txtQty.getText().toString(), "0", txtSalePrice.getText().toString(), String.valueOf(tot), database);
                double netTot = Double.parseDouble(txtNetTotal.getText().toString()) + tot;
                txtNetTotal.setText(String.valueOf(netTot));
            }
            int itemCount = Integer.parseInt(txtItemCount.getText().toString()) + 1;
            txtItemCount.setText(String.valueOf(itemCount));


            readfromlocaldatabasebillitemtemp();

        }

    }

    private void getmaxinvoicenumber() {
        DbHelper db_helper = new DbHelper(this);
        SQLiteDatabase database = db_helper.getReadableDatabase();
        db_helper.deletedatabasedailyitemtemp();
        Cursor cursorcheck = db_helper.readfromlocaldatabaseplaceorderinvoicenoavailable(database);
        Cursor cursormax = db_helper.readfromlocaldatabasemaxplaceorderinvoicenumber(database);

        int colomnu = cursorcheck.getCount();
        String stcount = Integer.toString(colomnu);
        if (stcount.equals("0")) {
            placeorderid.setText("1000");
        } else {
            if (cursormax.moveToFirst()) {
                String maxinno = cursormax.getString(0);
                ;
                Integer newmaxinno = Integer.parseInt(maxinno) + 1;
                placeorderid.setText(newmaxinno.toString());
            }
        }
    }

    private void getAllCategory() {

        layoutItem.setVisibility(View.GONE);
        layoutCat.setVisibility(View.VISIBLE);
        DbHelper db_helper = new DbHelper(this);
        SQLiteDatabase database = db_helper.getReadableDatabase();

        Cursor cursor = db_helper.getAllCategory(database);
        int ii = cursor.getCount();

        //     Toast.makeText(HomeActivity.this,count,Toast.LENGTH_SHORT).show();
        while (cursor.moveToNext()) {
            String catid = cursor.getString(cursor.getColumnIndex("catId"));
            String category = cursor.getString(cursor.getColumnIndex("name"));

            getSetCategory getSetCategory = new getSetCategory(catid, category);
            arrayListCategory.add(getSetCategory);
        }
        itemCategoryAdapter = new ItemCategoryAdapter(arrayListCategory, InvoiceActivity.this);
        categoryView.setAdapter(itemCategoryAdapter);
        itemCategoryAdapter.notifyDataSetChanged();
        cursor.close();

    }

    public void getAllItemForCategory(String id, int position) {

        if (position == 0) {
            layoutAss.setVisibility(View.GONE);
            layoutAddBtn.setVisibility(View.VISIBLE);
            layoutShopDet.setVisibility(View.VISIBLE);
        } else {
            layoutAss.setVisibility(View.VISIBLE);
            layoutAddBtn.setVisibility(View.GONE);
            layoutShopDet.setVisibility(View.GONE);
        }

        arrayListItem.clear();
        layoutItem.setVisibility(View.VISIBLE);
        layoutCat.setVisibility(View.GONE);
        DbHelper db_helper = new DbHelper(this);
        SQLiteDatabase database = db_helper.getReadableDatabase();

        Log.e("New Data", "getAllItemForCategory: "+cusId+","+id );
        Cursor cursor = db_helper.getAllItemForCustomer(cusId, id, database);
        int ii = cursor.getCount();
        Log.e("ii" ,"getAllItemForCategory: "+ii );

        //     Toast.makeText(HomeActivity.this,count,Toast.LENGTH_SHORT).show();
        while (cursor.moveToNext()) {
            String itemId = cursor.getString(cursor.getColumnIndex("productId"));
            String refillsaleprice = cursor.getString(cursor.getColumnIndex("refillsaleprice"));
            String salePrice = cursor.getString(cursor.getColumnIndex("newsaleprice"));
            String product_name = cursor.getString(cursor.getColumnIndex("product_name"));
            String qty = cursor.getString(cursor.getColumnIndex("qty"));

            getSetNewItem getSetNewItem = new getSetNewItem(itemId, salePrice, refillsaleprice, product_name,qty);
            arrayListItem.add(getSetNewItem);
        }
        itemAdapter = new itemAdapter(arrayListItem, InvoiceActivity.this);
        itemView.setAdapter(itemAdapter);
        itemAdapter.notifyDataSetChanged();
        cursor.close();
    }

    private void readfromlocaldatabasebillitemtemp() {
        arrayList.clear();
        DbHelper db_helper = new DbHelper(this);
        SQLiteDatabase database = db_helper.getReadableDatabase();

        Cursor cursor = db_helper.readfromlocaldatabasebilliteminfotmp(database);
        Log.e("row count", "readfromlocaldatabasebillitemtemp: " + cursor.getCount());
        while (cursor.moveToNext()) {
            String invoiceId = cursor.getString(cursor.getColumnIndex("invoiceId"));
            String prductId = cursor.getString(cursor.getColumnIndex("prductId"));
            String fullQty = cursor.getString(cursor.getColumnIndex("fullQty"));
            String emptyQty = cursor.getString(cursor.getColumnIndex("emptyQty"));
            String trustQtynew = cursor.getString(cursor.getColumnIndex("trustQty"));
            String trustRetuenQtynew = cursor.getString(cursor.getColumnIndex("trustRetuenQty"));
            String refillQty = cursor.getString(cursor.getColumnIndex("refillQty"));
            String returnQty = cursor.getString(cursor.getColumnIndex("returnQty"));
            String newQty = cursor.getString(cursor.getColumnIndex("newQty"));
            String refillPrice = cursor.getString(cursor.getColumnIndex("refillPrice"));
            String newPrice = cursor.getString(cursor.getColumnIndex("newPrice"));
            String totalAmount = cursor.getString(cursor.getColumnIndex("totalAmount"));
            String product_name = cursor.getString(cursor.getColumnIndex("product_name"));

            getsetbillitemtemp contact = new getsetbillitemtemp(invoiceId, prductId, fullQty, emptyQty, trustQtynew, trustRetuenQtynew, refillQty, returnQty, newQty,refillPrice, newPrice, totalAmount, product_name);
            arrayList.add(contact);
        }
        adapter = new billItemtempAdapter(arrayList, InvoiceActivity.this);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        cursor.close();
    }

    public boolean checkProdustExistrNot(String proId) {
        DbHelper db_helper = new DbHelper(this);
        SQLiteDatabase database = db_helper.getReadableDatabase();

        Cursor cursor = db_helper.readfromlocaldatabaseProduceExitOrNot(proId, database);
        if (cursor.getCount() > 0) {
            return true;
        } else {
            return false;
        }

    }

    private void showDialogBox(String type) {
        mydiallog = new Dialog(InvoiceActivity.this);
        mydiallog.setContentView(R.layout.add_qty_layout);
        mydiallog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        builder = new AlertDialog.Builder(InvoiceActivity.this);

        LinearLayout emptyQtyLayout, txtFullQtyLayout, trQtyLayout, trReQtyLayout;
        EditText txttrystQty, txttrustReqty, txtEmptyQty, txtFullQty;
        Button addTrust, addQty;

        emptyQtyLayout = (LinearLayout) mydiallog.findViewById(R.id.emptyQtyLayout);
        txtFullQtyLayout = (LinearLayout) mydiallog.findViewById(R.id.txtFullQtyLayout);
        trQtyLayout = (LinearLayout) mydiallog.findViewById(R.id.trQtyLayout);
        trReQtyLayout = (LinearLayout) mydiallog.findViewById(R.id.trReQtyLayout);

        txttrystQty = (EditText) mydiallog.findViewById(R.id.txttrystQty);
        txttrustReqty = (EditText) mydiallog.findViewById(R.id.txttrustReqty);
        txtEmptyQty = (EditText) mydiallog.findViewById(R.id.txtEmptyQty);
        txtFullQty = (EditText) mydiallog.findViewById(R.id.txtFullQty);

        addTrust = (Button) mydiallog.findViewById(R.id.addTrust);
        addQty = (Button) mydiallog.findViewById(R.id.addQty);


        if (type.equals("shop")) {

            emptyQtyLayout.setVisibility(View.VISIBLE);
            txtFullQtyLayout.setVisibility(View.VISIBLE);
            trQtyLayout.setVisibility(View.GONE);
            trReQtyLayout.setVisibility(View.GONE);
            addTrust.setVisibility(View.GONE);
            addQty.setVisibility(View.VISIBLE);

            txtEmptyQty.setText(shopEmptyQty);
            txtFullQty.setText(shopFullQtu);

        } else if (type.equals("trust")) {

            emptyQtyLayout.setVisibility(View.GONE);
            txtFullQtyLayout.setVisibility(View.GONE);
            trQtyLayout.setVisibility(View.VISIBLE);
            trReQtyLayout.setVisibility(View.VISIBLE);
            addTrust.setVisibility(View.VISIBLE);
            addQty.setVisibility(View.GONE);

            txttrystQty.setText(trustQty);
            txttrustReqty.setText(trustReturnQty);

        }
        addTrust.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                trustQty = txttrystQty.getText().toString();
                trustReturnQty = txttrustReqty.getText().toString();
//                shopEmptyQty = txtEmptyQty.getText().toString();
//                shopFullQtu = txtFullQty.getText().toString();
                mydiallog.dismiss();
            }
        });

        addQty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                trustQty = txttrystQty.getText().toString();
//                trustReturnQty = txttrustReqty.getText().toString();
                shopEmptyQty = txtEmptyQty.getText().toString();
                shopFullQtu = txtFullQty.getText().toString();
                mydiallog.dismiss();
            }
        });

        mydiallog.show();

    }

    private void saveDataToDatabase() {

        DbHelper db_helper = new DbHelper(this);
        SQLiteDatabase database = db_helper.getReadableDatabase();

        int rowId = db_helper.saveInvoice(cusId, txtNetTotal.getText().toString(),formattedtimedate,reasonId, database);
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

//            db_helper.saveInvoiceItem(invoId, prductId, fullQty, emptyQty,trustQty,trustRetuenQty, refillQty, newQty, refillPrice,returnQty, newPrice, totalAmount, database);
            db_helper.saveInvoiceItem(invoId,prductId,fullQty,emptyQty,trustQty,trustRetuenQty,refillQty,returnQty,newQty,refillPrice,newPrice,totalAmount,database);
            Cursor cursorQty = db_helper.readAvailableQuantity(prductId,database);
            cursorQty.moveToFirst();
            String availableQty=cursorQty.getString(cursorQty.getColumnIndex("qty"));
            int newAvailableQty=Integer.parseInt(availableQty)-(Integer.parseInt(refillQty)+Integer.parseInt(newQty)+Integer.parseInt(trustQty));
            db_helper.updateavailableQty(prductId,String.valueOf(newAvailableQty),database);

        }
        Intent intent1 = new Intent(InvoiceActivity.this, ViewCustomersActivity.class);
        Toast.makeText(InvoiceActivity.this,"Payment Complete",Toast.LENGTH_LONG).show();
        startActivity(intent1);
    }

    private void getRejectReason(String reason){
        reasonId=rejectReason.get(reason);
        Log.e("reasonId", "getRejectReason: "+reasonId );
    }
}