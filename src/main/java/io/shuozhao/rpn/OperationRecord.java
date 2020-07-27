package io.shuozhao.rpn;

import java.math.BigDecimal;

public class OperationRecord {

    private Item<BigDecimal> leftOperand;
    private Item<BigDecimal> rightOperand;
    private Item<BigDecimal> result;
    private Item<Operator> operator;

    public OperationRecord(Item<BigDecimal> leftOperand,
                           Item<BigDecimal> rightOperand,
                           Item<BigDecimal> result,
                           Item<Operator> operator) {
        assert(leftOperand != null);
        this.leftOperand = leftOperand;
        this.rightOperand = rightOperand;
        this.result = result;
        this.operator = operator;
    }

    public OperationRecord(Item<BigDecimal> leftOperand, Item<BigDecimal> result, Item<Operator> operator) {
        this(leftOperand, null, result, operator);
    }

    public OperationRecord(Item<BigDecimal> leftOperand) {
        this(leftOperand, null, null, null);
    }

    public boolean isBinaryOp() {
        return this.operator != null && this.operator.getItem().isBinaryOperator();
    }

    public boolean isUnaryOp() {
        return this.operator != null && this.operator.getItem().isUnaryOperator();
    }

    public boolean isOperandOnly() {
        return this.operator == null;
    }

    public Item<BigDecimal> getLeftOperand() {
        return leftOperand;
    }

    public Item<BigDecimal> getRightOperand() {
        return rightOperand;
    }

    public Item<BigDecimal> getResult() {
        return result;
    }

    public Item<Operator> getOperator() {
        return operator;
    }

    @Override
    public String toString() {
        return "(" + leftOperand +
                "," + rightOperand +
                "," + result +
                "," + operator +
                ")";
    }
}
