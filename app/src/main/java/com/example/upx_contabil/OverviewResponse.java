package com.example.upx_contabil;

public class OverviewResponse {
    private String PERatio;
    private String PriceToBookRatio;
    private String DividendYield;

    public String getDividendDate() {
        return DividendDate;
    }

    public OverviewResponse(String dividendDate, String exDividendDate) {
        DividendDate = dividendDate;
        ExDividendDate = exDividendDate;
    }

    public void setDividendDate(String dividendDate) {
        DividendDate = dividendDate;
    }

    public String getExDividendDate() {
        return ExDividendDate;
    }

    public void setExDividendDate(String exDividendDate) {
        ExDividendDate = exDividendDate;
    }

    private String DividendDate;
    private String ExDividendDate;



    private String Name;

    public OverviewResponse(String name) {
        Name = name;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public OverviewResponse() {
    }

    public String getPERatio() {
        return PERatio;
    }

    public void setPERatio(String PERatio) {
        this.PERatio = PERatio;
    }

    public String getPriceToBookRatio() {
        return PriceToBookRatio;
    }

    public void setPriceToBookRatio(String PriceToBookRatio) {
        this.PriceToBookRatio = PriceToBookRatio;
    }

    public String getDividendYield() {
        return DividendYield;
    }

    public void setDividendYield(String DividendYield) {
        this.DividendYield = DividendYield;
    }

    @Override
    public String toString() {
        return "OverviewResponse{" +
                "PERatio='" + PERatio + '\'' +
                ", PriceToBookRatio='" + PriceToBookRatio + '\'' +
                ", DividendYield='" + DividendYield + '\'' +
                ", Symbol='" + Name + '\'' +
                ", DividendDate='" + DividendDate + '\'' +
                ", ExDividendDate='" + ExDividendDate + '\'' +
                '}';
    }
}
