package io.shuozhao.rpn;

import java.math.BigDecimal;
import java.util.Objects;

import static io.shuozhao.rpn.Constants.*;
import static io.shuozhao.rpn.ItemType.*;

public class Item<T> {
    private ItemType itemType;
    private T item;

    private Item(ItemType itemType, T item) {
        this.itemType = itemType;
        this.item = item;
    }

    public static Item parseParameter(String param) {
        if (Operator.parse(param).isPresent()) {
            return createOperatorItem(Operator.parse(param).get());
        }
        if (Command.parse(param).isPresent()) {
            return createCommandItem(Command.parse(param).get());
        }
        return createOperandItem(param);
    }

    public static Item<BigDecimal> createOperandItem(BigDecimal num) {
        assert(num != null);
        return new Item<>(OPERAND, num.setScale(CALULATION_DECIMAL_PLACES));
    }

    public static Item<BigDecimal> createOperandItem(String param) {
        assert(param != null && !param.isBlank());
        try {
            return new Item<>(OPERAND, new BigDecimal(param).setScale(CALULATION_DECIMAL_PLACES));
        } catch (Exception ex) {
            throw new InvalidParameterException(
                    "The input parameter is not supported: [" + param + "]", ex);
        }
    }

    public static Item<Operator> createOperatorItem(Operator operator) {
        assert(operator != null);
        return new Item<>(OPERATOR, operator);
    }

    public static Item<Command> createCommandItem(Command command) {
        assert(command != null);
        return new Item<>(COMMAND, command);
    }

    public boolean isOperator() {
        return this.itemType == OPERATOR;
    }

    public boolean isCommand() {
        return this.itemType == COMMAND;
    }

    public boolean isOperand() {
        return this.itemType == OPERAND;
    }

    public T getItem() {
        return item;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Item<?> item1 = (Item<?>) o;
        return itemType == item1.itemType &&
                item.equals(item1.item);
    }

    @Override
    public int hashCode() {
        return Objects.hash(itemType, item);
    }

    @Override
    public String toString() {
        switch (itemType) {
            case OPERATOR:
            case COMMAND:
                return this.getItem().toString();
            default:
                return ((BigDecimal)this.getItem())
                            .setScale(DISPLAY_DECIMAL_PLACES, ROUNDING_MODE)
                            .stripTrailingZeros()
                            .toPlainString();
        }
    }
}
