package com.example.ansengas.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ansengas.R;
import com.example.ansengas.Ui.InvoiceActivity;
import com.example.ansengas.models.getSetNewItem;

import java.util.ArrayList;

public class itemAdapter extends RecyclerView.Adapter<itemAdapter.MyViewHolder> {

    private ArrayList<getSetNewItem> arrayList = new ArrayList<>();
    Context context;

    public itemAdapter(ArrayList<getSetNewItem> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public itemAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.btn_layout, parent, false);
        MyViewHolder myviewHoldernew = new MyViewHolder(view);
        return myviewHoldernew;
    }

    @Override
    public void onBindViewHolder(@NonNull itemAdapter.MyViewHolder myViewHolder, final int i) {
        myViewHolder.itenName.setText(arrayList.get(i).getProduct_name());
        myViewHolder.btnView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((InvoiceActivity)context).txtSalePrice.setText(arrayList.get(i).getNewsaleprice().toString());
                ((InvoiceActivity)context).txtRefillPrice.setText(arrayList.get(i).getRefillsaleprice().toString());
                ((InvoiceActivity)context).ItemId=arrayList.get(i).getProductId().toString();
                ((InvoiceActivity)context).productAvailableQty=arrayList.get(i).getQty().toString();
                ((InvoiceActivity)context).txtAvaQty.setText(arrayList.get(i).getQty().toString());

            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        RelativeLayout btnView;
        TextView itenName;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            btnView=(RelativeLayout)itemView.findViewById(R.id.btnView);
            itenName=(TextView)itemView.findViewById(R.id.txtItemName);
        }
    }
}
