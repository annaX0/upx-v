package com.example.upx_contabil;

public class OverviewResponse {
    private String PERatio;
    private String PriceToBookRatio;
    private String DividendYield;
    // Adicione outros campos conforme necessário

    // Construtor (se necessário)
    public OverviewResponse() {
        // Inicialização de campos, se necessário
    }

    // Getters e Setters
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

    // Adicione outros getters e setters conforme necessário

    @Override
    public String toString() {
        return "OverviewResponse{" +
                "PERatio='" + PERatio + '\'' +
                ", PriceToBookRatio='" + PriceToBookRatio + '\'' +
                ", DividendYield='" + DividendYield + '\'' +
                // Adicione outros campos aqui
                '}';
    }
}
