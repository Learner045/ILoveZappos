package com.example.cryptoinfo.APIRetrofit;

import com.example.cryptoinfo.Entities.BidsAsks;
import com.example.cryptoinfo.Entities.BitcoinStatus;
import com.example.cryptoinfo.Entities.Currency;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface JsonPlaceholderApi {

    @GET("api/v2/transactions/btcusd/")
    Call<List<Currency>> getCurrencyData();

    @GET("api/v2/order_book/btcusd/")
    Call<BidsAsks> getBidsAndAsksData();

    @GET("api/v2/ticker_hour/btcusd/")
    Call<BitcoinStatus> getBitcoinStatus();

}
