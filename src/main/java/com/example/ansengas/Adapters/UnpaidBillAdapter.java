package com.example.ansengas.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ansengas.R;
import com.example.ansengas.models.getsetUnpaidBill;

import java.util.ArrayList;
import java.util.List;

public class UnpaidBillAdapter extends RecyclerView.Adapter<UnpaidBillAdapter.ViewHolder> {

    ArrayList<getsetUnpaidBill> arrayList=new ArrayList<getsetUnpaidBill>();
    Context context;

    public UnpaidBillAdapter(ArrayList<getsetUnpaidBill> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.booking_order,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

       // holder.invoiceid.setText(arrayList.get(position).getInvoiceid());
       //// holder.invoicedate.setText(arrayList.get(position).getInvoicedate());
       // holder.invoicetotal.setText(arrayList.get(position).getInvoicetotal());
       // holder.invoiceamount.setText(arrayList.get(position).getInvoiceamount());



    }

    @Override
    public int getItemCount() {
        return 2;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView invoiceid,invoicedate,invoicetotal,invoiceamount;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            invoiceid = (TextView) itemView.findViewById(R.id.invoiceid);
            invoicedate = (TextView) itemView.findViewById(R.id.invoicedate);
            invoicetotal = (TextView) itemView.findViewById(R.id.invoicetotal);
            invoiceamount = (TextView) itemView.findViewById(R.id.invoiceamount);

        }
    }

}
