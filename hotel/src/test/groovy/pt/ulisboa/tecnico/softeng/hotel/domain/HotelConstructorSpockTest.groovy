package pt.ulisboa.tecnico.softeng.hotel.domain

import pt.ist.fenixframework.FenixFramework
import pt.ulisboa.tecnico.softeng.hotel.exception.HotelException
import pt.ulisboa.tecnico.softeng.hotel.services.remote.BankInterface
import pt.ulisboa.tecnico.softeng.hotel.services.remote.TaxInterface
import spock.lang.Shared
import spock.lang.Unroll

class HotelConstructorSpockTest extends SpockRollbackTestAbstractClass {
    @Shared
    def IBAN = 'IBAN'
    @Shared
    def NIF = 'NIF'
    @Shared
    def HOTEL_NAME = 'Londres'
    @Shared
    def HOTEL_CODE = 'XPTO123'
    @Shared
    def PRICE_SINGLE = 20
    @Shared
    def PRICE_DOUBLE = 30

    @Override
    def populate4Test() {
    }

    def 'success'() {
        when: 'an hotel is created'
        def info = new InfoStruct.Builder()
          .setCode(HOTEL_CODE)
          .setName(HOTEL_NAME)
          .setNif(NIF)
          .setIban(IBAN)
          .build()
        def hotel = new Hotel(info, PRICE_SINGLE, PRICE_DOUBLE, new Processor(new BankInterface(), new TaxInterface()))

        then: 'all information is correctly set'
        hotel.getName().equals(HOTEL_NAME)
        hotel.getCode().length() == Hotel.CODE_SIZE
        hotel.getRoomSet().size() == 0
        FenixFramework.getDomainRoot().getHotelSet().size() == 1
        hotel.getPrice(Room.Type.SINGLE) == PRICE_SINGLE
        hotel.getPrice(Room.Type.DOUBLE) == PRICE_DOUBLE
    }

    @Unroll('one of the following arguments is not allowed: #code | #name | #nif | #iban | #priceSingle| #priceDouble')
    def 'incorrect input parameters'() {
        when: 'an hotel is created with an incorrect input parameter'
        def info = new InfoStruct.Builder()
          .setCode(code)
          .setName(name)
          .setNif(nif)
          .setIban(iban)
          .build()
        new Hotel(info, priceSingle, priceDouble, new Processor(new BankInterface(), new TaxInterface()))

        then: 'an HotelException is thrown'
        thrown(HotelException)

        where:
        code       | name       | nif | iban | priceSingle  | priceDouble
        null       | HOTEL_NAME | NIF | IBAN | PRICE_SINGLE | PRICE_DOUBLE
        '      '   | HOTEL_NAME | NIF | IBAN | PRICE_SINGLE | PRICE_DOUBLE
        ''         | HOTEL_NAME | NIF | IBAN | PRICE_SINGLE | PRICE_DOUBLE
        HOTEL_CODE | null       | NIF | IBAN | PRICE_SINGLE | PRICE_DOUBLE
        HOTEL_CODE | '  '       | NIF | IBAN | PRICE_SINGLE | PRICE_DOUBLE
        HOTEL_CODE | ''         | NIF | IBAN | PRICE_SINGLE | PRICE_DOUBLE
        '123456'   | HOTEL_NAME | NIF | IBAN | PRICE_SINGLE | PRICE_DOUBLE
        '12345678' | HOTEL_NAME | NIF | IBAN | PRICE_SINGLE | PRICE_DOUBLE
        HOTEL_CODE | HOTEL_NAME | NIF | IBAN | -1           | PRICE_DOUBLE
        HOTEL_CODE | HOTEL_NAME | NIF | IBAN | PRICE_SINGLE | -1
    }

    def 'code not unique'() {
        given: 'an hotel'
        def info = new InfoStruct.Builder()
          .setCode(HOTEL_CODE)
          .setName(HOTEL_NAME)
          .setNif(NIF)
          .setIban(IBAN)
          .build()
        new Hotel(info, PRICE_SINGLE, PRICE_DOUBLE, new Processor(new BankInterface(), new TaxInterface()))

        when: 'another hotel is created with the same code'
        info = new InfoStruct.Builder()
          .setCode(HOTEL_CODE)
          .setName(HOTEL_NAME + " City")
          .setNif(NIF + "1")
          .setIban(IBAN)
          .build()
        new Hotel(info, PRICE_SINGLE, PRICE_DOUBLE, new Processor(new BankInterface(), new TaxInterface()))

        then: 'an HotelException is thrown'
        thrown(HotelException)
        FenixFramework.getDomainRoot().getHotelSet().size() == 1
    }

    def 'nif not unique'() {
        given: 'an hotel'
        def info = new InfoStruct.Builder()
          .setCode(HOTEL_CODE)
          .setName(HOTEL_NAME)
          .setNif(NIF)
          .setIban(IBAN)
          .build()
        new Hotel(info, PRICE_SINGLE, PRICE_DOUBLE, new Processor(new BankInterface(), new TaxInterface()))

        when: 'another hotel is created with the same nif'
        info = new InfoStruct.Builder()
          .setCode("XPTO124")
          .setName(HOTEL_NAME + "_New")
          .setNif(NIF)
          .setIban(IBAN)
          .build()
        new Hotel(info, PRICE_SINGLE, PRICE_DOUBLE, new Processor(new BankInterface(), new TaxInterface()))

        then: 'an HotelException is thrown'
        thrown(HotelException)
        FenixFramework.getDomainRoot().getHotelSet().size() == 1
    }
}
