package com.example.cryptoinfo.Comparators;

import com.example.cryptoinfo.Entities.Currency;
import com.example.cryptoinfo.Additional.Util;

import java.util.Comparator;

public class comparePrice implements Comparator<Currency> {
    @Override
    public int compare(Currency o1, Currency o2) {
        float f1 = Util.getFloat(o1.getPrice());
        float f2 = Util.getFloat(o2.getPrice());
        if(f1<f2)return -1;
        return 1;

    }
}
