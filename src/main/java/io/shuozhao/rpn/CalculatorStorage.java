package io.shuozhao.rpn;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Stack;

public class CalculatorStorage {

    private Stack<Item<BigDecimal>> currentStack;
    private Stack<OperationRecord> historyStack;

    public CalculatorStorage() {
        this.currentStack = new Stack<>();
        this.historyStack = new Stack<>();
    }

    public void clearAll() {
        this.currentStack.clear();
        this.historyStack.clear();
    }

    public void addItem(Item<BigDecimal> item) {
        this.currentStack.push(item);
    }

    public void addToHistory(OperationRecord record) {
        this.historyStack.push(record);
    }

    public Optional<Item<BigDecimal>> removeItem() {
        if (currentStack.empty()) {
            return Optional.empty();
        } else {
            return Optional.of(this.currentStack.pop());
        }
    }

    public Optional<OperationRecord> removeHistory() {
        if (historyStack.empty()) {
            return Optional.empty();
        } else {
            return Optional.of(this.historyStack.pop());
        }
    }

    public List<Item> getAllItemsAsList() {
        return Arrays.asList(currentStack.toArray(new Item[] {}));
    }

    public List<OperationRecord> getHistoryAsList() {
        return Arrays.asList(historyStack.toArray(new OperationRecord[] {}));
    }
}
