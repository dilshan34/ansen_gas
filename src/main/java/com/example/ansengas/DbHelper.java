package com.example.ansengas;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class DbHelper extends SQLiteOpenHelper {

    private Context context;
    private static DbHelper db_helper;
    private static final int DATABASE_VERSION = 2;
    private static final String CREATE_CUSTOMER_TABLE = "CREATE TABLE CUSTOMER(cusid TEXT PRIMARY KEY,shop_name TEXT,mobile TEXT,outStanding TEXT,address TEXT,creditlimit TEXT)";
    private static final String CREATE_CATEGORY_TABLE = "CREATE TABLE PRODUCT_CAT(catId TEXT PRIMARY KEY,name TEXT)";
    private static final String CREATE_CUSTOMER_PRODUCT_TABLE = "CREATE TABLE CUSTOMER_PRODUCT(id TEXT PRIMARY KEY,newsaleprice TEXT,refillsaleprice TEXT,productId TEXT,customerId TEXT)";
    private static final String CREATE_PRODUCT_TABLE = "CREATE TABLE PRODUCT (id TEXT PRIMARY KEY,product_code TEXT,product_name TEXT,size TEXT,unitprice TEXT,refillprice TEXT,category TEXT,qty TEXT)";
    private static final String CREATE_INVOICE_ITEM_TABLE = "CREATE TABLE INVOICE_ITEM(ID INTEGER PRIMARY KEY AUTOINCREMENT,invoiceId,prductId TEXT,fullQty TEXT,emptyQty TEXT,trustQty TEXT,trustRetuenQty TEXT,refillQty TEXT,returnQty TEXT,newQty TEXT,refillPrice TEXT,newPrice TEXT,totalAmount TEXT,suncStatus TEXT)";
    private static final String CREATE_INVOICE_ITEM_TABLETEMP = "CREATE TABLE INVOICE_ITEM_TEMP(ID INTEGER PRIMARY KEY AUTOINCREMENT,invoiceId TEXT,prductId TEXT,fullQty TEXT,emptyQty TEXT,trustQty TEXT,trustRetuenQty TEXT,refillQty TEXT,returnQty TEXT,newQty TEXT,refillPrice TEXT,newPrice TEXT,totalAmount TEXT,suncStatus TEXT)";
    private static final String CREATE_INVOICE_TABLE = "CREATE TABLE INVOICE(ID INTEGER PRIMARY KEY AUTOINCREMENT,customerId TEXT,netPrice TEXT,date TEXT,rejectReason TEXT,syncStatus TEXT)";
    private static final String CREATE_REJECT_REASON_TABLE = "CREATE TABLE REJECT_REASON(Id TEXT PRIMARY KEY,reason TEXT)";
    private static final String CREATE_BANK = " CREATE TABLE BANK(bank_id TEXT PRIMARY KEY,bankname TEXT)";

    SQLiteDatabase db;

    public DbHelper(Context context) {

        super(context, "ansenGas", null, DATABASE_VERSION);
        //  db=getWritableDatabase();
        this.context = context;
    }


    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(CREATE_CUSTOMER_TABLE);
        db.execSQL(CREATE_CATEGORY_TABLE);
        db.execSQL(CREATE_CUSTOMER_PRODUCT_TABLE);
        db.execSQL(CREATE_PRODUCT_TABLE);
        db.execSQL(CREATE_INVOICE_ITEM_TABLE);
        db.execSQL(CREATE_INVOICE_ITEM_TABLETEMP);
        db.execSQL(CREATE_INVOICE_TABLE);
        db.execSQL(CREATE_REJECT_REASON_TABLE);
        db.execSQL(CREATE_BANK);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onCreate(db);
    }

    public void saveAllBanks(String bank_id , String bankname ,SQLiteDatabase database){

        ContentValues contentValues = new ContentValues();
        contentValues.put("bank_id",bank_id);
        contentValues.put("bankname",bankname);
        database.replace("BANK",null,contentValues);
    }

    public void saveCustomersToLocal(String cusid, String name, String mobile, String outStanding, String address, String creditlimit, SQLiteDatabase database) {

        ContentValues contentValues = new ContentValues();
        contentValues.put("cusid", cusid);
        contentValues.put("shop_name", name);
        contentValues.put("mobile", mobile);
        contentValues.put("outStanding", outStanding);
        contentValues.put("address", address);
        contentValues.put("creditlimit", creditlimit);
        database.replace("CUSTOMER", null, contentValues);
    }

    public void saveProductCategory(String catId, String category, SQLiteDatabase database) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("catId", catId);
        contentValues.put("name", category);
        database.replace("PRODUCT_CAT", null, contentValues);
    }

    public void saveCustomerProdust(String id, String newSalePrice, String refilPrice, String productId, String cusId, SQLiteDatabase database) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("id", id);
        contentValues.put("newsaleprice", newSalePrice);
        contentValues.put("refillsaleprice", refilPrice);
        contentValues.put("productId", productId);
        contentValues.put("customerId", cusId);
        database.replace("CUSTOMER_PRODUCT", null, contentValues);
    }

    public void saveProduct(String id, String proCode, String proName, String size,String unitprice,String refillprice, String cat, String qty, SQLiteDatabase database) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("id", id);
        contentValues.put("product_code", proCode);
        contentValues.put("product_name", proName);
        contentValues.put("size", size);
        contentValues.put("unitprice", unitprice);
        contentValues.put("refillprice", refillprice);
        contentValues.put("category", cat);
        contentValues.put("qty", qty);
        database.replace("PRODUCT", null, contentValues);
    }

    public void saveRejectReason(String id, String reason, SQLiteDatabase database) {

        ContentValues contentValues = new ContentValues();
        contentValues.put("Id", id);
        contentValues.put("reason", reason);
        database.replace("REJECT_REASON", null, contentValues);
    }

    public int saveInvoice(String customerId, String netPrice, String currentDate,String rejectReason, SQLiteDatabase database) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("customerId", customerId);
        contentValues.put("netPrice", netPrice);
        contentValues.put("date", currentDate);
        contentValues.put("rejectReason", rejectReason);
        contentValues.put("syncStatus", "0");

        long rowId = database.insert("INVOICE", null, contentValues);
        return (int) rowId;
    }

    public void saveInvoiceItem(String invoiceId, String prductId, String fullQty, String emptyQty, String trustQty, String trustReQty, String refillQty,String returnQty, String newQty, String refillPrice, String newPrice, String totalAmount, SQLiteDatabase database) {

        ContentValues contentValues = new ContentValues();
        contentValues.put("invoiceId", invoiceId);
        contentValues.put("prductId", prductId);
        contentValues.put("fullQty", fullQty);
        contentValues.put("emptyQty", emptyQty);
        contentValues.put("trustQty", trustQty);
        contentValues.put("trustRetuenQty", trustReQty);
        contentValues.put("refillQty", refillQty);
        contentValues.put("returnQty", returnQty);
        contentValues.put("newQty", newQty);
        contentValues.put("refillPrice", refillPrice);
        contentValues.put("newPrice", newPrice);
        contentValues.put("totalAmount", totalAmount);
        contentValues.put("suncStatus", "0");
        database.insert("INVOICE_ITEM", null, contentValues);

    }

    public void saveInvoiceItemTemp(String invoiceId, String prductId, String fullQty, String emptyQty, String trustQty, String trustReQty, String refillQty,String returnQty, String newQty,String refillPrice, String newPrice, String totalAmount, SQLiteDatabase database) {

        ContentValues contentValues = new ContentValues();
        contentValues.put("invoiceId", invoiceId);
        contentValues.put("prductId", prductId);
        contentValues.put("fullQty", fullQty);
        contentValues.put("emptyQty", emptyQty);
        contentValues.put("trustQty", trustQty);
        contentValues.put("trustRetuenQty", trustReQty);
        contentValues.put("refillQty", refillQty);
        contentValues.put("returnQty", returnQty);
        contentValues.put("newQty", newQty);
        contentValues.put("refillPrice", refillPrice);
        contentValues.put("newPrice", newPrice);
        contentValues.put("totalAmount", totalAmount);
        contentValues.put("suncStatus", "0");
        database.insert("INVOICE_ITEM_TEMP", null, contentValues);

    }

    public Cursor getAllCustomers(SQLiteDatabase database) {
        String[] projection = {"cusid", "shop_name", "mobile", "outStanding", "address", "creditlimit"};
        Cursor cursor = database.query("CUSTOMER", projection, null, null, null, null, null);
        return cursor;
    }

    public Cursor getAllCategory(SQLiteDatabase database) {
        String[] projection = {"catId", "name"};
        Cursor cursor = database.query("PRODUCT_CAT", projection, null, null, null, null, null);
        return cursor;
    }

    public Cursor getAllRejectReason(SQLiteDatabase database) {
        String[] projection = {"Id", "reason"};
        Cursor cursor = database.query("REJECT_REASON", projection, null, null, null, null, null);
        return cursor;
    }

    public Cursor getAllItemForCustomer(String cusId, String catId, SQLiteDatabase database) {

        String[] projection = {"CUSTOMER_PRODUCT.newsaleprice", "CUSTOMER_PRODUCT.refillsaleprice", "CUSTOMER_PRODUCT.productId", "PRODUCT.product_name","PRODUCT.qty"};
        String selection = "CUSTOMER_PRODUCT.customerId LIKE ? AND PRODUCT.category LIKE ?";
        String[] selection_args = {cusId, catId};
        Cursor cursor = database.query("CUSTOMER_PRODUCT INNER JOIN PRODUCT ON CUSTOMER_PRODUCT.productId=PRODUCT.id", projection, selection, selection_args, null, null, null);
        return cursor;
    }

    public Cursor readfromlocaldatabaseplaceorderinvoicenoavailable(SQLiteDatabase database) {
        String[] projection = {"ID"};
        Cursor cursor = database.query("INVOICE", projection, null, null, null, null, null);
        return cursor;
    }

    public Cursor readfromlocaldatabaseProduceExitOrNot(String ProductId, SQLiteDatabase database) {
        String[] projection = {"invoiceId"};
        String selection = "prductId LIKE ?";
        String[] selection_args = {ProductId};
        Cursor cursor = database.query("INVOICE_ITEM_TEMP", projection, selection, selection_args, null, null, null);
        return cursor;
    }

    public Cursor readfromlocaldatabasedailyplaceorderbilldetails(SQLiteDatabase database) {
        String[] projection = {"INVOICE.ID", "INVOICE.netPrice", "INVOICE.date","INVOICE.rejectReason","CUSTOMER.shop_name"};
        Cursor cursor = database.query("INVOICE INNER JOIN CUSTOMER ON INVOICE.customerId=CUSTOMER.cusid", projection, null, null, null, null, null);
        return cursor;

    }

    public Cursor readfromlocaldatabasemaxplaceorderinvoicenumber(SQLiteDatabase database) {
        String[] projection = {"MAX(ID)"};
        Cursor cursor = database.query("INVOICE", projection, null, null, null, null, null);
        return cursor;
    }

    public Cursor readfromlocaldatabasebilliteminfotmp(SQLiteDatabase database) {

        String[] projection = {"INVOICE_ITEM_TEMP.invoiceId", "INVOICE_ITEM_TEMP.prductId", "INVOICE_ITEM_TEMP.fullQty", "INVOICE_ITEM_TEMP.emptyQty", "INVOICE_ITEM_TEMP.trustQty", "INVOICE_ITEM_TEMP.trustRetuenQty", "INVOICE_ITEM_TEMP.refillQty", "INVOICE_ITEM_TEMP.returnQty","INVOICE_ITEM_TEMP.newQty","INVOICE_ITEM_TEMP.refillPrice", "INVOICE_ITEM_TEMP.newPrice", "INVOICE_ITEM_TEMP.totalAmount", "PRODUCT.product_name"};
        Cursor cursor = database.query("INVOICE_ITEM_TEMP INNER JOIN PRODUCT ON INVOICE_ITEM_TEMP.prductId=PRODUCT.id", projection, null, null, null, null, null);
        return cursor;
    }

    public Cursor readfromlocaldatabasebilliteminfo(String inno, SQLiteDatabase database) {

        String[] projection = {"INVOICE_ITEM.invoiceId", "INVOICE_ITEM.prductId", "INVOICE_ITEM.fullQty", "INVOICE_ITEM.emptyQty", "INVOICE_ITEM.trustQty", "INVOICE_ITEM.trustRetuenQty", "INVOICE_ITEM.refillQty","INVOICE_ITEM.returnQty", "INVOICE_ITEM.newQty", "INVOICE_ITEM.refillPrice", "INVOICE_ITEM.newPrice", "INVOICE_ITEM.totalAmount", "PRODUCT.product_name","PRODUCT.refillprice as newrefillprice","PRODUCT.unitprice as newunitprice"};
        String selection = "INVOICE_ITEM.invoiceId LIKE ?";
        String[] selection_args = {inno};
        Cursor cursor = database.query("INVOICE_ITEM INNER JOIN PRODUCT ON INVOICE_ITEM.prductId=PRODUCT.id", projection, selection, selection_args, null, null, null);
        return cursor;
    }
    public Cursor readAvailableQuantity(String proId,SQLiteDatabase database){

        String[] projection = {"qty"};
        String selection = "id LIKE ?";
        String[] selection_args = {proId};
        Cursor cursor = database.query("PRODUCT", projection, selection, selection_args, null, null, null);
        return cursor;

    }

    public void updateavailableQty(String proId,String newQty, SQLiteDatabase database) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("qty", newQty);
        String selection = "id = ?";
        String[] selection_args = {proId};
        database.update("PRODUCT", contentValues, selection, selection_args);
    }

    public void deletedatabasedailyitemtemp() {
        SQLiteDatabase dbnew = this.getWritableDatabase();
        String querydelete = "DELETE FROM INVOICE_ITEM_TEMP ";
        dbnew.execSQL(querydelete);
    }

    public void updateSyncStatus(SQLiteDatabase database) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("syncStatus", "1");
        database.update("INVOICE", contentValues, null, null);
    }

    public Cursor readMainInvoiceTableNotSync(SQLiteDatabase database) {

        String[] projection = {"ID", "customerId", "netPrice","rejectReason","date"};
        String selection = "syncStatus LIKE ?";
        String[] selection_args = {"0"};
        Cursor cursor = database.query("INVOICE", projection, selection, selection_args, null, null, null);
        return cursor;
    }

    public void deleteAllProduct() {
        SQLiteDatabase dbnew = this.getWritableDatabase();
        String querydelete = "DELETE FROM PRODUCT";
        dbnew.execSQL(querydelete);
    }
    public void deleteAllCustomerProduct() {
        SQLiteDatabase dbnew = this.getWritableDatabase();
        String querydelete = "DELETE FROM CUSTOMER_PRODUCT";
        dbnew.execSQL(querydelete);
    }

    public void deletedatabase(String itemId) {
        SQLiteDatabase dbnew = this.getWritableDatabase();
        String querydelete = "DELETE FROM INVOICE_ITEM_TEMP WHERE prductId ='" + itemId + "'";
        dbnew.execSQL(querydelete);
    }
}