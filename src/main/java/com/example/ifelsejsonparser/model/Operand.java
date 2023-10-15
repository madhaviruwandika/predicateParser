package com.example.ifelsejsonparser.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Operand<T> {

    T value;
    Operator operator;
    int inputId;

    public void setValue(T value) {
        this.value = value;
    }

    public void setOperator(Operator operator) {
        this.operator = operator;
    }

    public void setInputId(int inputId) {
        this.inputId = inputId;
    }

    public boolean evaluate(T input) {
        boolean result = false;
        switch (operator) {
            case EQUAL:
                result = (input.equals(value));
                break;
            case LESS_THAN:
                result = ((int)input < (int)value);
                break;
            case GREATER_THAN:
                result = ((int)input > (int)value);
                break;
        }
        return result;
    }
}
