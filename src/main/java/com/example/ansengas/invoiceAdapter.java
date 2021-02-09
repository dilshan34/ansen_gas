package com.example.ansengas;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class invoiceAdapter extends RecyclerView.Adapter<invoiceAdapter.MyViewHolder> {

    ArrayList<getSetInvoice>arrayList=new ArrayList<>();
    Context context;

    public invoiceAdapter(ArrayList<getSetInvoice> arrayList, Context context)
    {
        this.arrayList=arrayList;
        this.context=context;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.invoice_layout, parent, false);
        final MyViewHolder myviewHoldernew = new MyViewHolder(view);
        return myviewHoldernew;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int i) {
        holder.inno.setText(arrayList.get(i).getInvoId().toString());
        holder.placeodDate.setText(arrayList.get(i).getDate().toString());
        holder.cusname.setText(arrayList.get(i).getShop_name().toString());
        holder.amunt.setText(arrayList.get(i).getNetPrice().toString());

        holder.billviewplace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context,InvoiceDetailActivity.class);
                intent.putExtra("invo",arrayList.get(i).getInvoId().toString());
                intent.putExtra("billDate",arrayList.get(i).getDate().toString());
                intent.putExtra("cusName",arrayList.get(i).getShop_name().toString());
                intent.putExtra("netPrice",arrayList.get(i).getNetPrice().toString());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView inno,placeodDate,cusname,amunt;
        ImageView billviewplace;
        int view_type;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            inno = (TextView) itemView.findViewById(R.id.placeodno);
            placeodDate = (TextView) itemView.findViewById(R.id.placeodDate);
            cusname = (TextView) itemView.findViewById(R.id.placeodcusname);
            amunt = (TextView) itemView.findViewById(R.id.placeodtotamunt);
            billviewplace = (ImageView) itemView.findViewById(R.id.billviewplace);
        }
    }
}
