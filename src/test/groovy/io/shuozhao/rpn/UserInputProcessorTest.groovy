package io.shuozhao.rpn

import spock.lang.Specification
import spock.lang.Subject
import spock.lang.Unroll

class UserInputProcessorTest extends Specification {

    @Subject
    def processor = new UserInputProcessor(new Calculator())

    @Unroll
    def "process a line of input"() {
        expect:
        processor.process(input).getResult().equals(result)

        where:
        input                  | result
        "1 2"                  | "1 2"
        "1 2 +"                | "3"
        "1 2 3 4 5 * *"        | "1 2 60"
        "2 2 + sqrt"           | "2"
        "2 2 + sqrt clear"     | ""
        "2 2 + sqrt undo"      | "4"
        "2 2 + sqrt undo undo" | "2 2"
    }

    @Unroll
    def "Process a line of input with - insufficient parameter error"() {
        expect:
        def processResult = processor.process(input)
        processResult.getResult().equals(result)
        processResult.hasError() == true
        processResult.getError().equals(errMsg)

        where:
        input                  | result | errMsg
        "1 +"                  | "1"    | "operator + (position: 3): insufficient parameters"
        "1 2 + +"              | "3"    | "operator + (position: 7): insufficient parameters"
        "1 2 + clear sqrt 1"   | ""     | "operator sqrt (position: 13): insufficient parameters"
        "1 2 undo + 3"         | "1"    | "operator + (position: 10): insufficient parameters"
        "1 2 undo undo sqrt"   | ""     | "operator sqrt (position: 15): insufficient parameters"
        "1 2 3 * 5 + * * 6 5"  | "11"   | "operator * (position: 15): insufficient parameters"
        "1 2 3 * 5  + * * 6 5" | "11"   | "operator * (position: 16): insufficient parameters"
    }

    @Unroll
    def "Process a line of input with - Invalid Parameter"() {
        expect:
        def processResult = processor.process(input)
        processResult.getResult().equals(result)
        processResult.hasError() == true
        processResult.getError().equals(errMsg)

        where:
        input       | result | errMsg
        "1 2 + abc" | "3"    | "parameter abc (position: 7): invalid parameters"
        "abc 1 2 +" | ""     | "parameter abc (position: 1): invalid parameters"
    }

    def "Process a line of input - blank input"() {
        given:
        def input = "   "

        when:
        def processResult = processor.process(input)

        then:
        processResult.getResult().equals("")
        processResult.hasError() == false
    }
}
