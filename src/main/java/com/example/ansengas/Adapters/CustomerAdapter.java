package com.example.ansengas.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ansengas.Constant;
import com.example.ansengas.R;
import com.example.ansengas.Ui.InvoiceActivity;
import com.example.ansengas.models.getSetCustomer;

import java.util.ArrayList;

public class CustomerAdapter extends RecyclerView.Adapter<CustomerAdapter.MyViewHolder> {

    ArrayList<getSetCustomer> arrayList=new ArrayList<getSetCustomer>();
    Context context;

    public CustomerAdapter(ArrayList<getSetCustomer> arrayList,Context context){
        this.arrayList=arrayList;
        this.context=context;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.customer_layout, parent, false);
        final MyViewHolder myviewHoldernew = new MyViewHolder(view);
        return myviewHoldernew;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        holder.txtshname.setText(arrayList.get(position).getShop_name());
        holder.txtmobile.setText(arrayList.get(position).getMobile());
        holder.txtOutStanding.setText(arrayList.get(position).getOutStanding());
        holder.txtCreditLimit.setText(arrayList.get(position).getCreditlimit());
        holder.searchbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(context,InvoiceActivity.class);
                intent.putExtra("cusId",arrayList.get(position).getCusid().toString());
                intent.putExtra("outstanding",arrayList.get(position).getOutStanding().toString());
                intent.putExtra("creditLimit",arrayList.get(position).getCreditlimit().toString());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView txtshname,txtmobile,txtOutStanding,txtCreditLimit;
        ImageButton searchbtn;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            txtshname=(TextView)itemView.findViewById(R.id.txtshname);
            txtmobile=(TextView)itemView.findViewById(R.id.txtmobile);
            txtOutStanding=(TextView)itemView.findViewById(R.id.txtOutStanding);
            txtCreditLimit=(TextView)itemView.findViewById(R.id.txtCreditLimit);
            searchbtn=(ImageButton) itemView.findViewById(R.id.searchbtn);

        }
    }
}
