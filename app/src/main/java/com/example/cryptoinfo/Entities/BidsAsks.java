package com.example.cryptoinfo.Entities;

import java.util.List;

public class BidsAsks {

    private long timestamp;
    private List<List<String>> bids;
    private List<List<String>> asks;

    public long getTimestamp() {
        return timestamp;
    }

    public List<List<String>> getBids() {
        return bids;
    }

    public List<List<String>> getAsks() {
        return asks;
    }
}
