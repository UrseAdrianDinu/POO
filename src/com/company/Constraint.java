package com.company;

class Constraint {
    Long limitainferioara;
    Long limitasuperioara;

    public Constraint(Long inf, Long sup) {
        limitainferioara = inf;
        limitasuperioara = sup;
    }

    @Override
    public String toString() {
        return "Constraint{" +
                "limitainferioara=" + limitainferioara +
                ", limitasuperioara=" + limitasuperioara +
                '}';
    }
}
