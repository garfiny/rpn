package io.shuozhao.rpn

import spock.lang.Specification

class CommandTest extends Specification {

    def "parse command operator clear and undo"() {
        expect:
        Command.parse(command) == result

        where:
        command | result
        "clear" | Optional.of(Command.CLEAR)
        "undo"  | Optional.of(Command.UNDO)
        "some"  | Optional.empty()
    }
}
