package com.example.cryptoinfo.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.cryptoinfo.Comparators.comparePrice;
import com.example.cryptoinfo.Entities.Currency;
import com.example.cryptoinfo.APIRetrofit.JsonPlaceholderApi;
import com.example.cryptoinfo.R;
import com.example.cryptoinfo.APIRetrofit.RetrofitClientInstance;
import com.example.cryptoinfo.Additional.Util;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


public class TransactionActivity extends AppCompatActivity  {

    private LineChart mChart;
    private float price = Float.MAX_VALUE;
    private EditText priceText;
    private SharedPreferences preferences = null;
    private ArrayList<Entry> yValues;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction);

        //implements OnChartGestureListener, OnChartValueSelectedListener
        mChart = (LineChart) findViewById(R.id.linechart);

        Button mButton = (Button) findViewById(R.id.click);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity( new Intent(TransactionActivity.this, OrderTableActivity.class));
            }
        });


        priceText = (EditText) findViewById(R.id.price);
        priceText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                String text = priceText.getText().toString();
                if(!text.equals("") && preferences==null){

                    text = text.replaceAll(" ","");
                    if(Util.isValid(text)){
                        price = Util.getFloat(text);
                        trackPrice(price);
                        Util.scheduleAlarm(TransactionActivity.this);
                    }
                }
            }
        });



       // mChart.setOnChartGestureListener(TransactionActivity.this);
       //  mChart.setOnChartValueSelectedListener(TransactionActivity.this);
        mChart.setDragEnabled(true);
        mChart.setScaleEnabled(false);

        if(savedInstanceState==null)
        makeAPICall();
        else{
            setChartConfig(savedInstanceState.<Entry>getParcelableArrayList("yV"));

        }


    } //end of onCreate

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        yValues = savedInstanceState.getParcelableArrayList("yV");
        setChartConfig(yValues);
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState, @NonNull PersistableBundle outPersistentState) {
        outState.putParcelableArrayList("yV",yValues);
        super.onSaveInstanceState(outState, outPersistentState);
    }

    private void makeAPICall(){
        Retrofit retrofit = RetrofitClientInstance.getRetrofitInstance();
        JsonPlaceholderApi jsonPlaceholderApi = retrofit.create(JsonPlaceholderApi.class);

        //async call
        Call<List<Currency>> call = jsonPlaceholderApi.getCurrencyData();
        call.enqueue(new Callback<List<Currency>>() {
            @Override
            public void onResponse(Call<List<Currency>> call, Response<List<Currency>> response) {

                if(response.isSuccessful()){

                    List<Currency> info = response.body();
                    TextView taptext = (TextView) findViewById(R.id.tap);
                    getData(info);
                    //taptext.setVisibility(View.GONE);

                }else{
                    //display error message
                }
            }

            @Override
            public void onFailure(Call<List<Currency>> call, Throwable t) {
                //display error message
            }
        });
    }

    private void trackPrice(float price) {

        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putFloat("price", price).apply();
    }




    private void getData(List<Currency> currencyList){
        //takes currency information -> converts results into appropriate floats
        //populates dataSet and sends resultant data


        yValues = new ArrayList<>();
        //y:price x:date

         //y - > price -> float SCALING min-max normalization
        Currency c_min  = Collections.min(currencyList, new comparePrice());
        float y_min = Util.getFloat(c_min.getPrice());
        Currency c_max  = Collections.max(currencyList, new comparePrice());
        float y_max = Util.getFloat(c_max.getPrice());



        float normalized_y;

        for(int i=0; i<currencyList.size(); i++){
            Currency current_currency = currencyList.get(i);

            int x = Util.getInt(current_currency.getDate());
            float y = Util.getFloat(current_currency.getPrice());

            normalized_y = (y-y_min)/(y_max - y_min);

            yValues.add(new Entry(x, normalized_y*100));

        }

        setChartConfig(yValues);



    }

    private void setChartConfig(ArrayList<Entry> yValues){
        LineDataSet set1 = new LineDataSet(yValues, getString(R.string.chart_desc));
        set1.setFillAlpha(110);

        ArrayList<ILineDataSet> dataSets = new ArrayList<>();
        dataSets.add(set1);

        LineData data = new LineData(dataSets);

        mChart.setData(data);
    }


}
