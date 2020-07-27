package io.shuozhao.rpn

import spock.lang.Specification

class OperatorTest extends Specification {

    def "parse operator enum from operator string"() {
        expect:
        Operator.parse(op) == op_enum

        where:
        op      | op_enum
        "+"     | Optional.of(Operator.PLUS)
        "-"     | Optional.of(Operator.MINUS)
        "*"     | Optional.of(Operator.MULTIPLY)
        "/"     | Optional.of(Operator.DIV)
        "sqrt"  | Optional.of(Operator.SQRT)
        "err"   | Optional.empty()
    }
}
