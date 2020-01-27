package com.example.cryptoinfo.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.cryptoinfo.Entities.BidsAsks;
import com.example.cryptoinfo.APIRetrofit.JsonPlaceholderApi;
import com.example.cryptoinfo.Additional.OrderTableAdapter;
import com.example.cryptoinfo.R;
import com.example.cryptoinfo.APIRetrofit.RetrofitClientInstance;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class OrderTableActivity extends AppCompatActivity {

    private  List<String> bidList = new ArrayList<>();
    private List<String> askList = new ArrayList<>();
    private OrderTableAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_table);

        getBidsAsks();

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        adapter = new OrderTableAdapter(OrderTableActivity.this);
        recyclerView.setLayoutManager(new LinearLayoutManager(OrderTableActivity.this));
        recyclerView.setAdapter(adapter);


    }


    //get data from API dummy``
    private List<String> getData() {

        List<String> data = new ArrayList<>();
        for(int i=0; i<20; i++){
            data.add("A"+i);
        }
        return data;
    }

    private void populate(BidsAsks bidsAsks){
        List<List<String>> bids = bidsAsks.getBids();
        List<List<String>> asks = bidsAsks.getAsks();


        for(int i=0; i<bids.size() && i<asks.size() ;i++){
            //get ith list

            String bid_value = bids.get(i).get(0);
            String ask_value = asks.get(i).get(0);


            bidList.add(bid_value);
            askList.add(ask_value);

        }


    }

    private void getBidsAsks(){
        Retrofit retrofit = RetrofitClientInstance.getRetrofitInstance();

        JsonPlaceholderApi jsonPlaceholderApi = retrofit.create(JsonPlaceholderApi.class);


        Call<BidsAsks> call = jsonPlaceholderApi.getBidsAndAsksData();
        call.enqueue(new Callback<BidsAsks>() {
            @Override
            public void onResponse(Call<BidsAsks> call, Response<BidsAsks> response) {

                if(response.isSuccessful()){

                    BidsAsks bidsAsks = response.body();

                    if(bidsAsks!=null)
                    populate(bidsAsks);

                    adapter.setItemList(bidList, askList);

                }else{
                   // Log.i("err", "err while parsing");
                }
            }



            @Override
            public void onFailure(Call<BidsAsks> call, Throwable t) {

            }
        });


    }
}
