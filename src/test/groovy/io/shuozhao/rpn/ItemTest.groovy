package io.shuozhao.rpn

import spock.lang.Specification

class ItemTest extends Specification {

    def "parse number input parameter into item without losing precision"() {
        expect:
        def parsed = Item.parseParameter(input)
        parsed.operator == result.operator
        new BigDecimal(input).compareTo((BigDecimal)parsed.getItem()) == 0

        where:
        input                 | result
        "123"                 | new Item<BigDecimal>(ItemType.OPERAND, new BigDecimal(input))
        "123.123456789012345" | new Item<BigDecimal>(ItemType.OPERAND, new BigDecimal(input))
        "123.123456789"       | new Item<BigDecimal>(ItemType.OPERAND, new BigDecimal(input))
        "0.123456789012345"   | new Item<BigDecimal>(ItemType.OPERAND, new BigDecimal(input))
    }

    def "throw unsupport input parameter when input can not be parsed"() {
        given:
        def input = "something invalid"
        when:
        Item.parseParameter(input)

        then:
        thrown(InvalidParameterException)
    }

    def "parse operator input parameter into operator item"() {
        expect:
        Item.parseParameter(input) == result

        where:
        input  | result
        "+"    | new Item<Operator>(ItemType.OPERATOR, Operator.PLUS)
        "-"    | new Item<Operator>(ItemType.OPERATOR, Operator.MINUS)
        "*"    | new Item<Operator>(ItemType.OPERATOR, Operator.MULTIPLY)
        "/"    | new Item<Operator>(ItemType.OPERATOR, Operator.DIV)
        "sqrt" | new Item<Operator>(ItemType.OPERATOR, Operator.SQRT)
    }

    def "parse command input parameter into operator item"() {
        expect:
        Item.parseParameter(input) == result

        where:
        input    | result
        "clear"  | new Item<Command>(ItemType.COMMAND, Command.CLEAR)
        "undo"   | new Item<Command>(ItemType.COMMAND, Command.UNDO)
    }

    def "create operator item and check corresponding item type"() {
        when:
        def item = Item.createOperatorItem(operator)

        then:
        item.isOperator() == true

        where:
        operator << Operator.values()
    }

    def "create command item and check corresponding item type"() {
        when:
        def item = Item.createCommandItem(command)

        then:
        item.isCommand() == true

        where:
        command << Command.values()
    }

    def "create operand item from BigDecimal and check corresponding item type"() {
        when:
        def item = Item.createOperandItem(number)

        then:
        item.isOperand() == true

        where:
        number << [new BigDecimal(123), new BigDecimal("0.123")]
    }

    def "create operand item from String and check corresponding item type"() {
        when:
        def item = Item.createOperandItem(str)

        then:
        item.isOperand() == true

        where:
        str << ["123", "12345.123456789012345"]
    }

    def "throw unsupport input parameter when input is not a plaintext number"() {
        when:
        Item.createOperandItem("something invalid")

        then:
        thrown(InvalidParameterException)
    }

    def "assert null and empty check for createOperandItem"() {
        when:
        Item.createOperandItem(str)

        then:
        thrown(AssertionError)

        where:
        str << [null, " "]
    }

    def "assert null and empty check for createCommandItem"() {
        when:
        Item.createCommandItem(null)

        then:
        thrown(AssertionError)
    }

    def "assert null and empty check for createOperatorItem"() {
        when:
        Item.createOperatorItem(null)

        then:
        thrown(AssertionError)
    }
}
