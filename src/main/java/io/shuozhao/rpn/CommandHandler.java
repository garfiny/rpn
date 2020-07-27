package io.shuozhao.rpn;

import java.util.Optional;

public interface CommandHandler {

    void handle(CalculatorStorage storage);

    class ClearHandler implements CommandHandler {

        @Override
        public void handle(CalculatorStorage storage) {
            storage.clearAll();
        }
    }

    class UndoHandler implements CommandHandler {

        @Override
        public void handle(CalculatorStorage storage) {
            Optional<OperationRecord> record = storage.removeHistory();
            if (record.isEmpty()) {
                return;
            }
            OperationRecord operationRecord = record.get();
            if (operationRecord.isOperandOnly()) {
                storage.removeItem();
            } else if (operationRecord.isUnaryOp()) {
                storage.removeItem();
                storage.addItem(record.get().getLeftOperand());
            } else if (operationRecord.isBinaryOp()) {
                storage.removeItem();
                storage.addItem(record.get().getLeftOperand());
                storage.addItem(record.get().getRightOperand());
            }
        }
    }
}
