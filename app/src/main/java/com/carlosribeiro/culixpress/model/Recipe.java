package com.carlosribeiro.culixpress.model;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;
import java.util.List;

public class Recipe implements Serializable {

    @SerializedName("id")
    private int id;

    @SerializedName("title")
    private String title;

    @SerializedName("image")
    private String imageUrl;

    @SerializedName("servings")
    private int servings;

    @SerializedName("preparationMinutes")
    private int preparationTime;

    @SerializedName("instructions")
    private String instructions;

    @SerializedName("analyzedInstructions")
    private List<AnalyzedInstruction> analyzedInstructions;

    @SerializedName("extendedIngredients")
    private List<ExtendedIngredient> extendedIngredients;

    @SerializedName("dishTypes")
    private List<String> dishTypes;

    // ✅ Getters
    public int getId() { return id; }
    public String getTitle() { return title; }
    public String getImageUrl() { return imageUrl; }
    public int getPreparationTime() {
        return preparationTime > 0 ? preparationTime : 0;
    }
    public int getServings() {
        return servings > 0 ? servings : 0;
    }
    // 🔹 Evita NullPointerException ao acessar os tipos de pratos
    public List<String> getDishTypes() {
        return dishTypes != null && !dishTypes.isEmpty() ? dishTypes : List.of("Desconhecido");
    }

    // 🔹 Método Getter corrigido para `analyzedInstructions`
    public List<AnalyzedInstruction> getAnalyzedInstructions() {
        return analyzedInstructions != null ? analyzedInstructions : List.of();
    }

    // 🔹 Método que retorna as instruções principais
    public String getInstructions() {
        return instructions != null && !instructions.trim().isEmpty() ? instructions : "Instruções não disponíveis.";
    }

    // 🔹 Método que retorna as instruções formatadas corretamente
    public String getFormattedInstructions() {
        if (!getAnalyzedInstructions().isEmpty()) {
            StringBuilder steps = new StringBuilder();
            for (AnalyzedInstruction instruction : getAnalyzedInstructions()) {
                for (Step step : instruction.getSteps()) {
                    steps.append("• ").append(step.getStep()).append("\n");
                }
            }
            return steps.length() > 0 ? steps.toString() : "Instruções não disponíveis.";
        }
        return getInstructions();
    }

    // 🔹 Evita erro caso `extendedIngredients` seja null
    public List<ExtendedIngredient> getExtendedIngredients() {
        return extendedIngredients != null ? extendedIngredients : List.of();
    }

    // 🔹 Classe interna para os passos detalhados da receita
    public static class AnalyzedInstruction {
        @SerializedName("steps")
        private List<Step> steps;

        public List<Step> getSteps() {
            return steps != null ? steps : List.of();
        }
    }

    // 🔹 Classe interna para os passos individuais
    public static class Step {
        @SerializedName("number")
        private int number;

        @SerializedName("step")
        private String step;

        public int getNumber() { return number; }
        public String getStep() { return step; }

        @Override
        public String toString() {
            return number + ". " + step;
        }
    }

    // 🔹 Classe interna para os ingredientes
    public static class ExtendedIngredient {
        @SerializedName("id")
        private int id;

        @SerializedName("aisle")
        private String aisle;

        @SerializedName("amount")
        private double amount;

        @SerializedName("consistency")
        private String consistency;

        @SerializedName("image")
        private String image;

        @SerializedName("measures")
        private Measures measures;

        public int getId() { return id; }
        public String getAisle() { return aisle; }
        public double getAmount() { return amount; }
        public String getConsistency() { return consistency; }
        public String getImage() { return image; }
        public Measures getMeasures() { return measures; }

        // 🔹 Classe interna para medidas dos ingredientes
        public static class Measures {
            @SerializedName("metric")
            private MeasureUnit metric;

            @SerializedName("us")
            private MeasureUnit us;

            public MeasureUnit getMetric() { return metric; }
            public MeasureUnit getUs() { return us; }

            public static class MeasureUnit {
                @SerializedName("amount")
                private double amount;

                @SerializedName("unitLong")
                private String unitLong;

                @SerializedName("unitShort")
                private String unitShort;

                public double getAmount() { return amount; }
                public String getUnitLong() { return unitLong; }
                public String getUnitShort() { return unitShort; }
            }
        }
    }
}
