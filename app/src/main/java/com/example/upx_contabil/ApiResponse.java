package com.example.upx_contabil;
import com.google.gson.annotations.SerializedName;

import java.util.Map;

public class ApiResponse {
    @SerializedName("Time Series (5min)")
    private Map<String, StockData> timeSeries;

    public Map<String, StockData> getTimeSeries() {
        return timeSeries;
    }
}
