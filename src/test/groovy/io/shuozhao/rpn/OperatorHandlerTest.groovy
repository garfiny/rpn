package io.shuozhao.rpn

import spock.lang.Specification
import spock.lang.Unroll

import static io.shuozhao.rpn.Constants.CALULATION_DECIMAL_PLACES
import static io.shuozhao.rpn.Constants.ROUNDING_MODE

class OperatorHandlerTest extends Specification {

    def bigDecimal(num) {
        return new BigDecimal(num).setScale(CALULATION_DECIMAL_PLACES, ROUNDING_MODE)
    }

    @Unroll
    def "Plus handler"() {
        expect:
        def plus = new OperatorHandler.PlusOperatorHandler()
        plus.handle(op1, op2).compareTo(result) == 0

        where:
        op1                                | op2                                | result
        bigDecimal(123)                | bigDecimal(234)                | bigDecimal(357)
        bigDecimal("10.123456789012345") | bigDecimal("1.000000000000001") | bigDecimal("11.123456789012346")
        bigDecimal("10.123456789012345") | bigDecimal("1.000000000000006") | bigDecimal("11.123456789012351")
    }

    @Unroll
    def "Minus handler"() {
        expect:
        def minus = new OperatorHandler.MinusOperatorHandler()
        minus.handle(op1, op2).compareTo(result) == 0

        where:
        op1                                | op2                                | result
        bigDecimal(123)                | bigDecimal(123)                 | bigDecimal(0)
        bigDecimal(123)                | bigDecimal(120)                 | bigDecimal(3)
        bigDecimal(123)                | bigDecimal(234)                 | bigDecimal(-111)
        bigDecimal("1.123456789012345") | bigDecimal("1.123456789012345")  | bigDecimal(0)
        bigDecimal("1.123456789012345") | bigDecimal("1.1234567890123451") | bigDecimal(0)
    }

    @Unroll
    def "Multiply handler"() {
        expect:
        def mul = new OperatorHandler.MultiplyOperatorHandler()
        mul.handle(op1, op2).compareTo(result) == 0

        where:
        op1                      | op2                      | result
        bigDecimal(3)            | bigDecimal(3)            | bigDecimal(9)
        bigDecimal(3.001)        | bigDecimal(3.001)        | bigDecimal("9.006000999999993")
        bigDecimal("1.00000001") | bigDecimal("1.00000001") | bigDecimal("1.00000002")
        bigDecimal("1.00000001") | bigDecimal("1.0000001")  | bigDecimal("1.000000110000001")
        bigDecimal(1) | bigDecimal("0.0000000000000001") | bigDecimal(0)
    }

    @Unroll
    def "Division handler"() {
        expect:
        def div = new OperatorHandler.DivisionOperatorHandler()
        div.handle(op1, op2).compareTo(result) == 0

        where:
        op1                      | op2                      | result
        bigDecimal(3)            | bigDecimal(3)            | bigDecimal(1)
        bigDecimal(3.001)        | bigDecimal(3.001)        | bigDecimal(1)
        bigDecimal(1) | bigDecimal("0.000000000000001") | bigDecimal("1000000000000000")
    }

    def "Division Handler - division by zero exception"() {
        given:
        def op1 = bigDecimal(1)
        def op2 = bigDecimal("0.00000000000000001")
        def div = new OperatorHandler.DivisionOperatorHandler()

        when:
        div.handle(op1, op2)

        then:
        thrown(ArithmeticException)
    }

    @Unroll
    def "Square Root Handler"() {
        expect:
        def sqrt = new OperatorHandler.SqrtOperatorHandler()
        sqrt.handle(op1, null).compareTo(result) == 0

        where:
        op1            | result
        bigDecimal(9)  | bigDecimal(3)
        bigDecimal(2)  | bigDecimal("1.414213562373095")
        bigDecimal(1234567890123456)  | bigDecimal("35136418.288201431303269")
    }

    def "Square Root Handler - Attempted square root of negative BigDecimal"() {
        given:
        def op = bigDecimal(-1)
        def sqrt = new OperatorHandler.SqrtOperatorHandler()

        when:
        sqrt.handle(op, null)

        then:
        thrown(ArithmeticException)
    }
}
