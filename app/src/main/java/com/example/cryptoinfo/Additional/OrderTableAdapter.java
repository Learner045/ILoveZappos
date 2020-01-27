package com.example.cryptoinfo.Additional;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cryptoinfo.R;

import java.util.ArrayList;
import java.util.List;

public class OrderTableAdapter extends RecyclerView.Adapter<OrderTableAdapter.ViewHolderForAdapter> {


    private LayoutInflater inflater;
    private List<String> bidList = new ArrayList<>();
    private List<String> askList = new ArrayList<>();
    public OrderTableAdapter(Context context)
    {
        inflater=LayoutInflater.from(context);

    }

    @NonNull
    @Override
    public ViewHolderForAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view=inflater.inflate(R.layout.order_table_row,parent,false);
        return new ViewHolderForAdapter(view);

    }

    public void setItemList( List<String> dataBid, List<String> dataAsk){


        this.bidList= dataBid;
        this.askList = dataAsk;
        notifyItemRangeChanged(0, bidList.size());
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderForAdapter holder, int position) {


        String currentItemBid=bidList.get(position);
        String currentItemAsk = askList.get(position);
        if(currentItemBid!=null && currentItemAsk!=null){
           holder.bidText.setText(currentItemBid);
           holder.askText.setText(currentItemAsk);
        }
    }

    @Override
    public int getItemCount() {
        return bidList.size();
    }

    class ViewHolderForAdapter extends RecyclerView.ViewHolder{

        TextView bidText;
        TextView askText;

         ViewHolderForAdapter(@NonNull View itemView) {
            super(itemView);

            bidText = (TextView) itemView.findViewById(R.id.bidsView);
            askText = (TextView) itemView.findViewById(R.id.asksView);

        }
    }
}
