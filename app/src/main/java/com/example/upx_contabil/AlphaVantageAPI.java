package com.example.upx_contabil;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface AlphaVantageAPI {
    @GET("query")
    Call<OverviewResponse> getOverview(@Query("function") String function,
                                       @Query("symbol") String symbol,
                                       @Query("apikey") String apiKey);
}
