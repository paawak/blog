package com.swayam.demo.design;

public class AggregationOperation implements OperationElement {

    private final AggregateOperand aggregateOperand;
    private final String fieldName;

    public AggregationOperation(AggregateOperand aggregateOperand, String fieldName) {
        this.aggregateOperand = aggregateOperand;
        this.fieldName = fieldName;
    }

    @Override
    public void accept(OperationElementVisitor calculator) {
        calculator.visit(this);
    }

    public AggregateOperand getAggregateOperand() {
        return aggregateOperand;
    }

    public String getFieldName() {
        return fieldName;
    }

}
