package com.example.upx_contabil;
import com.google.gson.annotations.SerializedName;

public class CompanyOverview {
    @SerializedName("Symbol")
    private String symbol;

    @SerializedName("EBITDA")
    private String ebitda;


    public String getEbitda() {
        return ebitda;
    }

}
