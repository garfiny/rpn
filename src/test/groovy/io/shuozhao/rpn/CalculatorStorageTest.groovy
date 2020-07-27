package io.shuozhao.rpn

import spock.lang.Shared
import spock.lang.Specification
import spock.lang.Subject

class CalculatorStorageTest extends Specification {

    @Shared
    def item = Item.createOperandItem(1)
    @Shared
    def operationRecord = new OperationRecord(
            item, item, item, Item.createOperatorItem(Operator.PLUS))

    @Subject
    def storage

    def setup() {
        storage = new CalculatorStorage()
    }

    def "addItem"() {
        when:
        storage.addItem(item)

        then:
        def list = storage.getAllItemsAsList()
        list.size() == 1
        list.get(0) == item
    }

    def "addItem - multiple items"() {
        given:
        def numOfItems = 10

        when:
        1.upto(numOfItems) {
            storage.addItem(item)
        }

        then:
        storage.getAllItemsAsList().size() == numOfItems
    }

    def "removeItem"() {
        given:
        def numOfItems = 10
        1.upto(numOfItems) {
            storage.addItem(item)
        }

        when:
        def removed = storage.removeItem()

        then:
        storage.getAllItemsAsList().size() == numOfItems - 1
        removed.get() == item
    }

    def "removeItem when stack is empty"() {
        when:
        def removed = storage.removeItem()

        then:
        removed.isEmpty() == true
    }


    def "addToHistory"() {
        when:
        storage.addToHistory(operationRecord)

        then:
        def list = storage.getHistoryAsList()
        list.size() ==  1
        list.get(0) == operationRecord
    }

    def "addToHistory - multiple records"() {
        given:
        def numOfRecords = 10
        when:
        1.upto(numOfRecords) {
            storage.addToHistory(operationRecord)
        }

        then:
        storage.getHistoryAsList().size() == numOfRecords
    }

    def "removeHistory"() {
        given:
        def numOfRecords = 10
        1.upto(numOfRecords) {
            storage.addToHistory(operationRecord)
        }

        when:
        def removed = storage.removeHistory()

        then:
        storage.getHistoryAsList().size() == numOfRecords - 1
        removed.get() == operationRecord
    }

    def "removeHistory - empty history"() {
        when:
        def removed = storage.removeHistory()

        then:
        removed.isEmpty() == true
    }

    def "clearAll"() {
        given:
        def count = 10
        1.upto(count) {
            storage.addToHistory(operationRecord)
            storage.addItem(item)
        }

        when:
        storage.clearAll()

        then:
        storage.getHistoryAsList().size() == 0
        storage.getAllItemsAsList().size() == 0
    }
}
