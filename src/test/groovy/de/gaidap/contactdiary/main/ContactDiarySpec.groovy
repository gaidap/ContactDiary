package de.gaidap.contactdiary.main

import spock.lang.Specification

class ContactDiarySpec extends Specification {

    void 'Starting the application hooks up an sql connection with the SQLite jdbc driver'() {
        given:
        String[] args = ['test.db']

        when:
        ContactDiary.main(args)

        then:
        noExceptionThrown()

        and:
        File expectedDBFile = new File(args[0])
        expectedDBFile.exists()

        cleanup:
        expectedDBFile.delete()
    }
}
