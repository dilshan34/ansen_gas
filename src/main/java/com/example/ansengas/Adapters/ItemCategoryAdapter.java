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
import com.example.ansengas.models.getSetCategory;

import java.util.ArrayList;

public class ItemCategoryAdapter extends RecyclerView.Adapter<ItemCategoryAdapter.MyViewHolder> {
    private ArrayList<getSetCategory> arrayList=new ArrayList<>();
    Context context;

    public ItemCategoryAdapter(ArrayList<getSetCategory> arrayList,Context context){
        this.arrayList=arrayList;
        this.context=context;
    }
    @NonNull
    @Override
    public ItemCategoryAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.btn_layout, parent, false);
        MyViewHolder myviewHoldernew = new MyViewHolder(view);
        return myviewHoldernew;
    }

    @Override
    public void onBindViewHolder(@NonNull ItemCategoryAdapter.MyViewHolder myViewHolder, final int i) {

        myViewHolder.itenName.setText(arrayList.get(i).getName());
        myViewHolder.btnView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((InvoiceActivity)context).getAllItemForCategory(arrayList.get(i).getId(),i);
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
