package io.shuozhao.rpn

import spock.lang.Specification

class CommandHandlerTest extends Specification {

    def clearHandler = new CommandHandler.ClearHandler();
    def undoHandler = new CommandHandler.UndoHandler();

    def storage
    def setup() {
        storage = new CalculatorStorage();
    }

    def "clear command - clear the whole stack"() {
        given:
        def op1 = Item.createOperandItem(new BigDecimal(1))
        def op2 = Item.createOperandItem(new BigDecimal(1))
        storage.addItem(op1)
        storage.addItem(op2)

        when:
        clearHandler.handle(storage)

        then:
        storage.getAllItemsAsList().size() == 0
    }

    def "undo command - undo inputing operand"() {
        given:
        def op1 = Item.createOperandItem(new BigDecimal(1))
        storage.addItem(op1)
        storage.addToHistory(new OperationRecord(op1))

        when:
        undoHandler.handle(storage)

        then:
        storage.getAllItemsAsList().size() == 0
    }

    def "undo command - undo binary operator"() {
        given:
        def op1 = Item.createOperandItem(new BigDecimal(1))
        def op2 = Item.createOperandItem(new BigDecimal(1))
        def result = Item.createOperandItem(new BigDecimal(2))
        def operator = Item.createOperatorItem(Operator.PLUS)
        storage.addItem(result)
        storage.addToHistory(new OperationRecord(op1, op2, result, operator))

        when:
        undoHandler.handle(storage)

        then:
        storage.getAllItemsAsList().size() == 2
        storage.getAllItemsAsList().get(0) == op1
        storage.getAllItemsAsList().get(1) == op2
    }

    def "undo command - undo unary operation"() {
        given:
        def op1 = Item.createOperandItem(new BigDecimal(4))
        def result = Item.createOperandItem(new BigDecimal(2))
        def operator = Item.createOperatorItem(Operator.SQRT)
        storage.addItem(result)
        storage.addToHistory(new OperationRecord(op1, result, operator))

        when:
        undoHandler.handle(storage)

        then:
        storage.getAllItemsAsList().size() == 1
        storage.getAllItemsAsList().get(0) == op1
    }

    def "undo command - undo last 2 operations"() {
        given:
        def op1 = Item.createOperandItem(new BigDecimal(2))
        def op2 = Item.createOperandItem(new BigDecimal(2))
        def result = Item.createOperandItem(new BigDecimal(4))
        def operator = Item.createOperatorItem(Operator.PLUS)
        storage.addToHistory(new OperationRecord(op1, op2, result, operator))

        def sqrtResult = Item.createOperandItem(new BigDecimal(2))
        def sqrtOp = Item.createOperatorItem(Operator.SQRT)
        storage.addItem(sqrtResult)
        storage.addToHistory(new OperationRecord(result, sqrtResult, sqrtOp))

        when:
        undoHandler.handle(storage)
        undoHandler.handle(storage)

        then:
        storage.getAllItemsAsList().size() == 2
        storage.getAllItemsAsList().get(0) == op1
        storage.getAllItemsAsList().get(1) == op2
    }
}
