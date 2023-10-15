package com.example.ifelsejsonparser.model;

public class PredicateNode {

    Predicate predicate;
    PredicateNode elseStat;
    boolean result = false;

    public PredicateNode(boolean result) {
        this.result = result;
    }

    public PredicateNode() {
    }

    public void setPredicate(Predicate predicate) {
        this.predicate = predicate;
    }

    public void setElseStat(PredicateNode elseStat) {
        this.elseStat = elseStat;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    public boolean findResult(String a, Integer b) {
        if(predicate == null || predicate.evaluate(a, b)) {
            return result;
        } else if(elseStat != null){
            return elseStat.findResult(a,b);
        } else {
            return false;
        }
    }
}
