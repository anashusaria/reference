package pt.ulisboa.tecnico.softeng.broker.domain

import pt.ist.fenixframework.FenixFramework
import pt.ulisboa.tecnico.softeng.broker.exception.BrokerException
import pt.ulisboa.tecnico.softeng.broker.services.remote.*
import spock.lang.Unroll

class BrokerConstructorMethodSpockTest extends SpockRollbackTestAbstractClass {

    @Override
    def populate4Test() {
    }

    def success() {
        when: 'a broker is created'
        def info = new InfoStruct.Builder()
          .setCode(BROKER_CODE)
          .setName(BROKER_NAME)
          .setNif(BROKER_NIF)
          .setIban(BROKER_IBAN)
          .build()
        def broker = new Broker(info, new ServiceLayer.Builder().build())

        then: 'the attributes are correctly set'
        broker.getCode().equals(BROKER_CODE)
        broker.getName().equals(BROKER_NAME)
        broker.getAdventureSet().size() == 0
        FenixFramework.getDomainRoot().getBrokerSet().contains(broker)
    }

    @Unroll('#label: #broker, #name, #nif_seller, #nif_buyer, #iban')
    def 'invalid arguments'() {
        when: 'a broker is created'
        def info = new InfoStruct.Builder()
          .setCode(broker)
          .setName(name)
          .setNif(nif)
          .setIban(iban)
          .build()

        new Broker(info, new ServiceLayer.Builder().build())

        then: 'an exception is thrown'
        thrown(BrokerException)
        FenixFramework.getDomainRoot().getBrokerSet().size() == 0

        where: 'the arguments are invalid'
        broker      | name        | nif        | iban        | label
        null        | BROKER_NAME | BROKER_NIF | BROKER_IBAN | 'null code'
        ""          | BROKER_NAME | BROKER_NIF | BROKER_IBAN | 'empty code'
        BROKER_CODE | null        | BROKER_NIF | BROKER_IBAN | 'null name'
        "   "       | BROKER_NAME | BROKER_NIF | BROKER_IBAN | 'blank code'
        BROKER_CODE | ""          | BROKER_NIF | BROKER_IBAN | 'empty name'
        BROKER_CODE | "    "      | BROKER_NIF | BROKER_IBAN | 'blank name'
        BROKER_CODE | BROKER_NAME | null       | BROKER_IBAN | 'null seller nif'
        BROKER_CODE | BROKER_NAME | ""         | BROKER_IBAN | 'empty seller nif'
        BROKER_CODE | BROKER_NAME | "    "     | BROKER_IBAN | 'blank seller nif'
        BROKER_CODE | BROKER_NAME | BROKER_NIF | null        | 'null sourceIban'
        BROKER_CODE | BROKER_NAME | BROKER_NIF | ""          | 'empty sourceIban'
        BROKER_CODE | BROKER_NAME | BROKER_NIF | "   "       | 'blank sourceIban'
    }

    @Unroll('duplicate #label')
    def 'unique verifications'() {
        given: 'a broker'
        def info = new InfoStruct.Builder()
          .setCode(code_one)
          .setName(BROKER_NAME)
          .setNif(nif_one)
          .setIban(BROKER_IBAN)
          .build()

        def broker = new Broker(info, new ServiceLayer.Builder().build())

        when: 'another broker is created'
        info.setCode(code_two)
        info.setNif(nif_two)
        new Broker(info, new ServiceLayer.Builder().build())

        then: 'an exception is thrown'
        thrown(BrokerException)
        FenixFramework.getDomainRoot().getBrokerSet().contains(broker)


        where: 'because it violates a unique constraint'
        label        | code_one    | code_two    | nif_one    | nif_two
        'code equal' | BROKER_CODE | BROKER_CODE | BROKER_NIF | "012345678"
        'nif equal'  | BROKER_CODE | "BR02"      | BROKER_NIF | BROKER_NIF
    }
}
