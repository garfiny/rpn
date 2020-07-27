package io.shuozhao.rpn;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static io.shuozhao.rpn.Operator.OperatorType.BINARY;
import static io.shuozhao.rpn.Operator.OperatorType.UNARY;
import static io.shuozhao.rpn.OperatorHandler.*;

public enum Operator {

    PLUS("+", BINARY, new PlusOperatorHandler()),
    MINUS("-", BINARY, new MinusOperatorHandler()),
    MULTIPLY("*", BINARY, new MultiplyOperatorHandler()),
    DIV("/", BINARY, new DivisionOperatorHandler()),
    SQRT("sqrt", UNARY, new SqrtOperatorHandler());

    private static Map<String, Operator> OPERATORS_ENUM;
    static {
        OPERATORS_ENUM = new HashMap<>();
        OPERATORS_ENUM.put(PLUS.operator, PLUS);
        OPERATORS_ENUM.put(MINUS.operator, MINUS);
        OPERATORS_ENUM.put(MULTIPLY.operator, MULTIPLY);
        OPERATORS_ENUM.put(DIV.operator, DIV);
        OPERATORS_ENUM.put(SQRT.operator, SQRT);
    }

    private String operator;
    private OperatorType operatorType;
    private OperatorHandler handler;

    Operator(String operator, OperatorType operatorType, OperatorHandler handler) {
        this.operator = operator;
        this.operatorType = operatorType;
        this.handler = handler;
    }

    public static Optional<Operator> parse(String op) {
        return Optional.ofNullable(OPERATORS_ENUM.get(op));
    }

    public OperatorHandler getHandler() {
        return handler;
    }

    public boolean isBinaryOperator() {
        return this.operatorType.equals(BINARY);
    }

    public boolean isUnaryOperator() {
        return this.operatorType.equals(UNARY);
    }

    public enum OperatorType {
        UNARY, BINARY;
    }
}
