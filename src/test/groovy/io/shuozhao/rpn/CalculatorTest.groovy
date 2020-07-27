package io.shuozhao.rpn

import spock.lang.Specification
import spock.lang.Subject
import spock.lang.Unroll

class CalculatorTest extends Specification {

    @Subject
    def calculator

    def setup() {
        calculator = new Calculator()
    }

    def "addOperand"() {
        given:
        def operand = Item.createOperandItem("1")

        when:
        calculator.addOperand(operand)

        then:
        calculator.currentResult() == "1"
    }

    def "addOperand - multiple operand"() {
        given:
        def operand = Item.createOperandItem("1")

        when:
        calculator.addOperand(operand)
        calculator.addOperand(operand)

        then:
        calculator.currentResult() == "1 1"
    }

    @Unroll
    def "doCalculate - binary operator"() {
        expect:
        calculator.addOperand(Item.createOperandItem(op1))
        calculator.addOperand(Item.createOperandItem(op2))
        calculator.doCalculate(Item.createOperatorItem(operator))
        calculator.currentResult() == result

        where:
        op1  | op2 | operator          | result
        "2"  | "2" | Operator.PLUS     | "4"
        "2"  | "2" | Operator.MINUS    | "0"
        "2"  | "2" | Operator.MULTIPLY | "4"
        "2"  | "2" | Operator.DIV      | "1"
    }

    @Unroll
    def "doCalculate - sqrt operator"() {
        given:
        def operand = Item.createOperandItem("4")

        when:
        calculator.addOperand(operand)
        calculator.doCalculate(Item.createOperatorItem(Operator.SQRT))

        then:
        calculator.currentResult() == "2"
    }

    def "executeCommand - clear"() {
        given:
        int count = 10
        def operand = Item.createOperandItem("1")

        when:
        1.upto(count) {
            calculator.addOperand(operand)
        }
        calculator.executeCommand(Item.createCommandItem(Command.CLEAR))

        then:
        calculator.currentResult() == ""
    }

    def "executeCommand - undo"() {
        given:
        def operand = Item.createOperandItem("1")

        when:
        calculator.addOperand(operand)
        calculator.addOperand(operand)
        calculator.executeCommand(Item.createCommandItem(Command.UNDO))

        then:
        calculator.currentResult() == "1"
    }

    def "executeCommand - undo 2 times"() {
        given:
        def operand = Item.createOperandItem("1")

        when:
        calculator.addOperand(operand)
        calculator.addOperand(operand)
        calculator.executeCommand(Item.createCommandItem(Command.UNDO))
        calculator.executeCommand(Item.createCommandItem(Command.UNDO))

        then:
        calculator.currentResult() == ""
    }

    def "Insufficient Parameter Exception - Binary operations"() {
        given:
        def operand = Item.createOperandItem("1")
        calculator.addOperand(operand)

        when:
        calculator.doCalculate(Item.createOperatorItem(Operator.PLUS))

        then:
        thrown(InsufficientParameterException)
    }

    def "Insufficient Parameter Exception - Unary operations"() {
        when:
        calculator.doCalculate(Item.createOperatorItem(Operator.SQRT))

        then:
        thrown(InsufficientParameterException)
    }
}
