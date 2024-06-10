package com.example.upx_contabil;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
public interface apiService {
    @GET("query?function=TIME_SERIES_INTRADAY&symbol=IBM&interval=5min&apikey=F2YRTWXR5LWZ4T3N")
    Call<ApiResponse> getStockData();
}
