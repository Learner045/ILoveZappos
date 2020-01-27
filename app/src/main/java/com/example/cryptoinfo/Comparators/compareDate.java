package com.example.cryptoinfo.Comparators;

import com.example.cryptoinfo.Entities.Currency;
import com.example.cryptoinfo.Additional.Util;

import java.util.Comparator;

public class compareDate implements Comparator<Currency> {
    @Override
    public int compare(Currency o1, Currency o2) {
        return Util.getInt(o1.getDate())-Util.getInt(o2.getDate());
    }
}
