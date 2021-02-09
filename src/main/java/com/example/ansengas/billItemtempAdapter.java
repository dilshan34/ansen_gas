package com.example.ansengas;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ansengas.Ui.InvoiceActivity;

import java.util.ArrayList;

public class billItemtempAdapter extends RecyclerView.Adapter<billItemtempAdapter.MyViewHolder> {
    ArrayList<getsetbillitemtemp> arrayList = new ArrayList<>();
    Context context;
    private static final int TYPE_HEAD = 0;
    private static final int TYPE_LIST = 1;

    public billItemtempAdapter(ArrayList<getsetbillitemtemp> arrayList, Context context) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == TYPE_LIST) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.bill_item_layout, parent, false);
            final MyViewHolder myviewHoldernew = new MyViewHolder(view, viewType);
            return myviewHoldernew;
        } else if (viewType == TYPE_HEAD) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.bil_item_header_layout, parent, false);
            MyViewHolder myviewHoldernew = new MyViewHolder(view, viewType);
            return myviewHoldernew;
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int position) {

        if (myViewHolder.view_type == TYPE_LIST) {
            myViewHolder.item_name.setText(arrayList.get(position - 1).getProduct_name());
            myViewHolder.fullQty.setText(arrayList.get(position - 1).getFullQty());
            myViewHolder.emptyQty.setText(arrayList.get(position - 1).getEmptyQty());
            myViewHolder.refillQty.setText(arrayList.get(position - 1).getRefillQty());
            myViewHolder.newQty.setText(arrayList.get(position - 1).getNewQty());
            myViewHolder.refillPrice.setText(arrayList.get(position - 1).getRefillPrice());
            myViewHolder.newPrice.setText(arrayList.get(position - 1).getNewPrice());
            myViewHolder.totalAmount.setText(arrayList.get(position - 1).getTotalAmount());


            final getsetbillitemtemp get1 = arrayList.get(position - 1);
            myViewHolder.btndelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    DbHelper db_helper = new DbHelper(context);
                    String itmprice = ((InvoiceActivity) context).txtNetTotal.getText().toString();
                    String itemQty = ((InvoiceActivity) context).txtItemCount.getText().toString();

                    Double newitemprice = Double.parseDouble(itmprice) - Double.parseDouble(arrayList.get(position - 1).getTotalAmount());
                    ((InvoiceActivity) context).txtNetTotal.setText(newitemprice.toString());

                    int newQty = Integer.parseInt(itemQty) - 1;
                    ((InvoiceActivity) context).txtItemCount.setText(String.valueOf(newQty));

                       /* Integer newtotqty = Integer.parseInt(((HomeActivity) context).totqty.getText().toString());
                        Integer newqy = newtotqty - 1;
                        ((HomeActivity) context).totqty.setText(newqy.toString());*/

                    db_helper.deletedatabase(arrayList.get(position - 1).getPrductId());

                    removedata(get1);

                }
            });
        } else if (myViewHolder.view_type == TYPE_HEAD) {
            myViewHolder.item_nameh.setText("Name");
            myViewHolder.fullQtyh.setText("Full Qty");
            myViewHolder.emptyQtyh.setText("Empty Qty");
            myViewHolder.refillQtyh.setText("Refill Qty");
            myViewHolder.newQtyh.setText("New Qty");
            myViewHolder.refillPriceh.setText("Refill Price");
            myViewHolder.newPriceh.setText("New Price");
            myViewHolder.totalAmounth.setText("Tot Amount");
            myViewHolder.acton.setText("Action");
        }
    }

    @Override
    public int getItemCount() {
        return arrayList.size() + 1;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        int view_type;
        TextView item_name, fullQty, emptyQty, refillQty, newQty, refillPrice, newPrice, totalAmount, item_nameh, fullQtyh, emptyQtyh, refillQtyh, newQtyh, refillPriceh, newPriceh, totalAmounth, acton;
        ImageView btndelete;

        public MyViewHolder(@NonNull View itemView, int viewtype) {
            super(itemView);

            if (viewtype == TYPE_LIST) {
                item_name = (TextView) itemView.findViewById(R.id.item_name);
                fullQty = (TextView) itemView.findViewById(R.id.fullQty);
                emptyQty = (TextView) itemView.findViewById(R.id.emptyQty);
                refillQty = (TextView) itemView.findViewById(R.id.refillQty);
                newQty = (TextView) itemView.findViewById(R.id.newQty);
                refillPrice = (TextView) itemView.findViewById(R.id.refillPrice);
                newPrice = (TextView) itemView.findViewById(R.id.newPrice);
                totalAmount = (TextView) itemView.findViewById(R.id.totalAmount);
                btndelete = (ImageView) itemView.findViewById(R.id.btndelete);
                if (Constant.invo) {
                    btndelete.setVisibility(View.VISIBLE);
                } else {
                    btndelete.setVisibility(View.GONE);
                }
                view_type = 1;
            } else if (viewtype == TYPE_HEAD) {
                item_nameh = (TextView) itemView.findViewById(R.id.item_nameh);
                fullQtyh = (TextView) itemView.findViewById(R.id.fullQtyh);
                emptyQtyh = (TextView) itemView.findViewById(R.id.emptyQtyh);
                refillQtyh = (TextView) itemView.findViewById(R.id.refillQtyh);
                newQtyh = (TextView) itemView.findViewById(R.id.newQtyh);
                refillPriceh = (TextView) itemView.findViewById(R.id.refillPriceh);
                newPriceh = (TextView) itemView.findViewById(R.id.newPriceh);
                totalAmounth = (TextView) itemView.findViewById(R.id.totalAmounth);
                acton = (TextView) itemView.findViewById(R.id.actonh);
                if (Constant.invo) {
                    acton.setVisibility(View.VISIBLE);
                } else {
                    acton.setVisibility(View.GONE);
                }
                view_type = 0;
            }
        }
    }

    private void removedata(getsetbillitemtemp get) {
        int position1 = arrayList.indexOf(get);
        arrayList.remove(position1);
        notifyItemRemoved(position1);
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0)
            return TYPE_HEAD;
        return TYPE_LIST;
    }
}
