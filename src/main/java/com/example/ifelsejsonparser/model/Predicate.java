package com.example.ifelsejsonparser.model;

import java.util.List;

public class Predicate {
    Operator operator;
    List<Operand> operands;

    public Operator getOperator() {
        return operator;
    }

    public void setOperator(Operator operator) {
        this.operator = operator;
    }

    public void setOperands(List<Operand> operands) {
        this.operands = operands;
    }

    public boolean evaluate(String a, Integer b ) {
        if(operator == Operator.AND) {
            boolean conditions = operands.stream().filter(op -> op.inputId == 1 ? !op.evaluate(a) : !op.evaluate(b)).findAny().isPresent();
            return conditions ? false : true;
        } else if (operator == Operator.OR){
            boolean conditions = operands.stream().filter(op -> op.inputId == 1 ? op.evaluate(a) : op.evaluate(b)).findAny().isPresent();
            return conditions ? true : false;
        } else {
            return false;
        }
    }
}
