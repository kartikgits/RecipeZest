package com.northwindlabs.kartikeya.recipezest;

public class Weather {
    private String condition;
    private int temp;
    private int conditionCode;

    public Weather(String condition, int temp, int conditionCode) {
        this.condition = condition;
        this.temp = temp;
        this.conditionCode = conditionCode;
    }

    public String getCondition() {
        return condition;
    }

    public int getTemp() {
        return temp;
    }

    public int getConditionCode() {
        return conditionCode;
    }
}
