package com.example.upx_contabil;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
public interface AlphaVantageService {
    @GET("query?function=OVERVIEW")
    Call<CompanyOverview> getCompanyOverview(@Query("symbol") String symbol, @Query("apikey") String apikey);
}
