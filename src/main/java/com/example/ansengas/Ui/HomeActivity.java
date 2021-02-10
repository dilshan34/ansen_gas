package com.example.ansengas.Ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.ansengas.Constant;
import com.example.ansengas.DbHelper;
import com.example.ansengas.MySingleton;
import com.example.ansengas.R;
import com.example.ansengas.Shaireprefmanager;
import com.example.ansengas.getsetInvoiceMain;
import com.example.ansengas.getsetbillitemtemp;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class HomeActivity extends AppCompatActivity {

    CardView btnCustomers, btnOldInvoice, btnSync, btnlogOut;
    ProgressDialog pd;
    String taskMessage = "";
    private Toolbar mtoolbar;
    String TAG = "s";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        btnCustomers = (CardView) findViewById(R.id.customers);
        btnOldInvoice = (CardView) findViewById(R.id.btnOldInvoice);
        btnSync = (CardView) findViewById(R.id.btnSync);
        btnlogOut = (CardView) findViewById(R.id.btnlogOut);

//        mtoolbar = (Toolbar) findViewById(R.id.toolBAr);
//        setSupportActionBar(mtoolbar);
//        getSupportActionBar().setTitle(Shaireprefmanager.getInstance(HomeActivity.this).getKeyName().toString());

        btnCustomers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, ViewCustomersActivity.class);
                startActivity(intent);
            }
        });

        btnOldInvoice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, ViewOldInvoiceActivity.class);
                startActivity(intent);
            }
        });

        btnSync.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new LoginSync().execute();
//                getProductCategory();
//                syncInvoice();
            }
        });
        btnlogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Shaireprefmanager.getInstance(HomeActivity.this).Loogout();
                finish();
//                final Db_Helper db_helper = new Db_Helper(View_All_CustomerActivity.this);
//                db_helper.deletecustomer();
//                db_helper.deleteAllItemDetails();

                startActivity(new Intent(HomeActivity.this, MainActivity.class));
            }
        });
    }

    final class LoginSync extends AsyncTask<Void, Integer, Void> {


        protected void onPreExecute() {
            super.onPreExecute();


            pd = new ProgressDialog(HomeActivity.this);
            pd.setMessage("Please wait, synchronizing data");
            //pd.show();
            //pd.setCanceledOnTouchOutside(false);
            //pd.setCancelable(false);

            pd.setIndeterminate(false);
            pd.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            pd.setCancelable(false);
            pd.setCanceledOnTouchOutside(false);
            pd.setMax(6);
            pd.show();

        }

        public void onProgressUpdate(Integer... progress) {
            // Update the progress bar on dialog
            pd.setProgress(progress[0]);
            pd.setMessage(taskMessage);
        }

        public Void doInBackground(Void... arg0) {
            int progressCount = 0;

            taskMessage = ("Synchronizing Invoice...");
            syncInvoice();
            publishProgress(progressCount++);

            taskMessage = ("Synchronizing All Customers...");
            getCustomers();
            publishProgress(progressCount++);
//
            taskMessage = ("Synchronizing Product Category...");
            getProductCategory();
            publishProgress(progressCount++);
//
            taskMessage = ("Synchronizing Product...");
            getProduct();
            publishProgress(progressCount++);
//
            taskMessage = ("Synchronizing Customer Product Price...");
            getCustomerProduct();
            publishProgress(progressCount++);
//
            taskMessage = ("Synchronizing Reject Reason...");
            getRejectReason();
            publishProgress(progressCount++);

            taskMessage =("Bank Data adding");
            getAllBanks();
            publishProgress(progressCount++);


            return null;
        }

        @Override
        public void onPostExecute(Void aVoid) {
            pd.dismiss();
        }
    }

    private void getCustomers() {
        final DbHelper db_helper = new DbHelper(HomeActivity.this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constant.GET_CUSTOMER_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            Log.e("response", "onResponse: " + response);
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                String id = jsonObject.getString("id");
                                String shopname = jsonObject.getString("shop_name");
                                String mobile = jsonObject.getString("mobile");
                                String address = jsonObject.getString("address");
                                String creditlimit = jsonObject.getString("creditlimit");
                                String outStanding = jsonObject.getString("outStanding");

                                SQLiteDatabase database = db_helper.getWritableDatabase();
                                db_helper.saveCustomersToLocal(id, shopname, mobile, outStanding, address, creditlimit, database);

                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                //  Hours.setText("Something Went Wrong Please Try again later....");
                error.printStackTrace();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parmas = new HashMap<>();
                parmas.put("rootId", Shaireprefmanager.getInstance(HomeActivity.this).getKeyRoot().toString());
                return parmas;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                60000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MySingleton.getInstance(getApplicationContext()).addToRequestque(stringRequest);

    }

    private void getProductCategory() {
        final DbHelper db_helper = new DbHelper(HomeActivity.this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constant.GET_PRODUCT_CATEGORY_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            Log.e("response", "onResponse: " + response);
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                String id = jsonObject.getString("id");
                                String category = jsonObject.getString("category");

                                SQLiteDatabase database = db_helper.getWritableDatabase();
                                db_helper.saveProductCategory(id, category, database);

                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                //  Hours.setText("Something Went Wrong Please Try again later....");
                error.printStackTrace();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parmas = new HashMap<>();
                parmas.put("rootId", Shaireprefmanager.getInstance(HomeActivity.this).getKeyRoot().toString());
                return parmas;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                60000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MySingleton.getInstance(getApplicationContext()).addToRequestque(stringRequest);

    }

    private void getAllBanks(){
        final DbHelper db_helper = new DbHelper(HomeActivity.this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constant.Get_All_Banks,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            Log.e("response", "onResponse: " + response);
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                String bank_id = jsonObject.getString("bank_id");
                                String bankname = jsonObject.getString("bankname");

                                Log.e(TAG, "onResponse: "+ bankname );

                                SQLiteDatabase database = db_helper.getWritableDatabase();
                                db_helper.saveAllBanks(bank_id, bankname, database);

                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                //  Hours.setText("Something Went Wrong Please Try again later....");
                error.printStackTrace();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parmas = new HashMap<>();
                parmas.put("rootId", Shaireprefmanager.getInstance(HomeActivity.this).getKeyRoot().toString());
                return parmas;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                60000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MySingleton.getInstance(getApplicationContext()).addToRequestque(stringRequest);

    }

    private void getProduct() {
        final DbHelper db_helper = new DbHelper(HomeActivity.this);
        db_helper.deleteAllProduct();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constant.GET_PRODUCT_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            Log.e("response", "onResponse: " + response);
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                String id = jsonObject.getString("id");
                                String product_code = jsonObject.getString("product_code");
                                String product_name = jsonObject.getString("product_name");
                                String size = jsonObject.getString("size");
                                String unitprice = jsonObject.getString("unitprice");
                                String refillprice = jsonObject.getString("refillprice");
                                String category = jsonObject.getString("category");
                                String qty = jsonObject.getString("qty");

                                SQLiteDatabase database = db_helper.getWritableDatabase();
                                db_helper.saveProduct(id, product_code, product_name, size,unitprice,refillprice, category,qty, database);

                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                //  Hours.setText("Something Went Wrong Please Try again later....");
                error.printStackTrace();
            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parmas = new HashMap<>();
                parmas.put("refId", Shaireprefmanager.getInstance(HomeActivity.this).getKeyusertid().toString());
                return parmas;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                60000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MySingleton.getInstance(getApplicationContext()).addToRequestque(stringRequest);

    }

    private void getCustomerProduct() {
        final DbHelper db_helper = new DbHelper(HomeActivity.this);
        db_helper.deleteAllCustomerProduct();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constant.GET_CUSTOMER_PRODUCT_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            Log.e("response", "onResponse: " + response);
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                String id = jsonObject.getString("id");
                                String newsaleprice = jsonObject.getString("newsaleprice");
                                String refillsaleprice = jsonObject.getString("refillsaleprice");
                                String proId = jsonObject.getString("tbl_product_idtbl_product");
                                String cusId = jsonObject.getString("tbl_customer_idtbl_customer");

                                SQLiteDatabase database = db_helper.getWritableDatabase();
                                db_helper.saveCustomerProdust(id, newsaleprice, refillsaleprice, proId, cusId, database);

                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                //  Hours.setText("Something Went Wrong Please Try again later....");
                error.printStackTrace();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parmas = new HashMap<>();
                parmas.put("rootId", Shaireprefmanager.getInstance(HomeActivity.this).getKeyRoot().toString());
                return parmas;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                60000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MySingleton.getInstance(getApplicationContext()).addToRequestque(stringRequest);

    }

    private void getRejectReason() {
        final DbHelper db_helper = new DbHelper(HomeActivity.this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constant.GET_REJECT_REASON_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            Log.e("response", "onResponse: " + response);
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                String id = jsonObject.getString("id");
                                String reason = jsonObject.getString("reason");

                                SQLiteDatabase database = db_helper.getWritableDatabase();
                                db_helper.saveRejectReason(id, reason, database);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                //  Hours.setText("Something Went Wrong Please Try again later....");
                error.printStackTrace();
            }
        });
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                60000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MySingleton.getInstance(getApplicationContext()).addToRequestque(stringRequest);

    }

    private void syncInvoice() {

        final DbHelper db_helper = new DbHelper(HomeActivity.this);
        final ArrayList<getsetInvoiceMain> arrayListInvo = new ArrayList<>();

        final SQLiteDatabase database = db_helper.getReadableDatabase();
        Cursor cursor = db_helper.readMainInvoiceTableNotSync(database);
        int rowCount=cursor.getCount();
        Log.e("row count", "readfromlocaldatabasebillitemtemp: " + cursor.getCount());
        while (cursor.moveToNext()) {

            final String id = cursor.getString(cursor.getColumnIndex("ID"));
            final String customerId = cursor.getString(cursor.getColumnIndex("customerId"));
            final String netPrice = cursor.getString(cursor.getColumnIndex("netPrice"));
            final String date = cursor.getString(cursor.getColumnIndex("date"));
            final String rejectReason = cursor.getString(cursor.getColumnIndex("rejectReason"));

            Cursor cursorItem = db_helper.readfromlocaldatabasebilliteminfo(id, database);
            ArrayList<getsetbillitemtemp> arrayListItem = new ArrayList<>();
            Log.e("Item row count", "readfromlocaldatabasebillitemtemp: " + cursorItem.getCount());


            while (cursorItem.moveToNext()) {
                String invoiceId = cursorItem.getString(cursorItem.getColumnIndex("invoiceId"));
                String prductId = cursorItem.getString(cursorItem.getColumnIndex("prductId"));
                String fullQty = cursorItem.getString(cursorItem.getColumnIndex("fullQty"));
                String emptyQty = cursorItem.getString(cursorItem.getColumnIndex("emptyQty"));
                String trustQty = cursorItem.getString(cursorItem.getColumnIndex("trustQty"));
                String trustRetuenQty = cursorItem.getString(cursorItem.getColumnIndex("trustRetuenQty"));
                String refillQty = cursorItem.getString(cursorItem.getColumnIndex("refillQty"));
                String newrefillprice = cursorItem.getString(cursorItem.getColumnIndex("newrefillprice"));
                String newunitprice = cursorItem.getString(cursorItem.getColumnIndex("newunitprice"));
                String returnQty = cursorItem.getString(cursorItem.getColumnIndex("returnQty"));
                String newQty = cursorItem.getString(cursorItem.getColumnIndex("newQty"));
                String refillPrice = cursorItem.getString(cursorItem.getColumnIndex("refillPrice"));
                String newPrice = cursorItem.getString(cursorItem.getColumnIndex("newPrice"));
                String totalAmount = cursorItem.getString(cursorItem.getColumnIndex("totalAmount"));
                String product_name = cursorItem.getString(cursorItem.getColumnIndex("product_name"));

                getsetbillitemtemp contact = new getsetbillitemtemp(invoiceId, prductId, fullQty, emptyQty, trustQty, trustRetuenQty, refillQty,returnQty, newQty, refillPrice,newrefillprice,newunitprice, newPrice, totalAmount, product_name);
                arrayListItem.add(contact);
            }

            getsetInvoiceMain cont = new getsetInvoiceMain(id, customerId, netPrice, date,rejectReason, arrayListItem);
            arrayListInvo.add(cont);
        }

        Gson gson = new GsonBuilder().create();
        final JsonArray myCustomArray = gson.toJsonTree(arrayListInvo).getAsJsonArray();

        Log.e("myCustomArray", "syncInvoice: "+myCustomArray );
        if(rowCount>0){

            StringRequest stringRequest = new StringRequest(Request.Method.POST, Constant.UPLOAD_INVOICE_URL,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                                Log.e("response", "onResponse: " + response);
                            JSONObject jsonObject= null;
                            try {
                                jsonObject = new JSONObject(response);
                                String code=jsonObject.getString("code");

                                if(code.equals("200")){
                                    db_helper.updateSyncStatus(database);
                                }
                                else {
                                    Toast.makeText(HomeActivity.this,"Try Again",Toast.LENGTH_LONG).show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }


                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                    //  Hours.setText("Something Went Wrong Please Try again later....");
                    error.printStackTrace();
                }
            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> parmas = new HashMap<>();
                    parmas.put("data", String.valueOf(myCustomArray));
                    parmas.put("refId", Shaireprefmanager.getInstance(HomeActivity.this).getKeyusertid().toString());
                    return parmas;
                }
            };
            stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                    60000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            MySingleton.getInstance(getApplicationContext()).addToRequestque(stringRequest);
//
        }

    }

}