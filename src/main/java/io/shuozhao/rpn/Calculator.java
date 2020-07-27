package io.shuozhao.rpn;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class Calculator {

    private CalculatorStorage storage;

    public Calculator() {
        this.storage = new CalculatorStorage();
    }

    public void addOperand(Item<BigDecimal> operand) {
        this.storage.addItem(operand);
        this.storage.addToHistory(new OperationRecord(operand));
    }

    public String currentResult() {
        List<String> strings =
                storage.getAllItemsAsList()
                        .stream()
                        .map(item -> item.toString())
                        .collect(Collectors.toList());
        return String.join(" ", strings);
    }

    public void doCalculate(Item<Operator> operatorItem) {
        Operator operator = operatorItem.getItem();
        Optional<Item<BigDecimal>> rightOp =
                operator.isBinaryOperator() ? this.storage.removeItem() : Optional.empty();
        Optional<Item<BigDecimal>> leftOp = this.storage.removeItem();
        if(!checkRequiredOperands(operator, leftOp, rightOp)) {
            if (leftOp.isPresent()) {
                this.storage.addItem(leftOp.get());
            }
            if (rightOp.isPresent()) {
                this.storage.addItem(rightOp.get());
            }
            throw new InsufficientParameterException();
        }

        BigDecimal leftOperand = leftOp.get().getItem();
        BigDecimal rightOperand = rightOp.isPresent() ? rightOp.get().getItem() : null;
        BigDecimal result = operator.getHandler().handle(leftOperand, rightOperand);

        this.storage.addItem(Item.createOperandItem(result));
        this.storage.addToHistory(
                new OperationRecord(
                        leftOp.get(),
                        rightOp.orElse(null),
                        Item.createOperandItem(result),
                        operatorItem)
        );
    }

    private boolean checkRequiredOperands(Operator operator,
                                          Optional<Item<BigDecimal>> leftOp,
                                          Optional<Item<BigDecimal>> rightOp
                                          ) {
        boolean operandsSatisfied = leftOp.isPresent() && leftOp.get().getItem() != null;
        if (operator.isUnaryOperator()) {
            return operandsSatisfied;
        } else {
            return operandsSatisfied && rightOp.isPresent() && rightOp.get().getItem() != null;
        }
    }

    public void executeCommand(Item<Command> command) {
        command.getItem().getHandler().handle(storage);
    }
}
