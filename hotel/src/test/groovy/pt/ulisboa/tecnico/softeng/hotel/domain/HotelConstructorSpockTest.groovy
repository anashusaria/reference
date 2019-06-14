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
        def info = new InfoStruct()
        info.setCode(HOTEL_CODE)
        info.setName(HOTEL_NAME)
        info.setNif(NIF)
        info.setIban(IBAN)
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
        def info = new InfoStruct()
        info.setCode(code)
        info.setName(name)
        info.setNif(nif)
        info.setIban(iban)
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
        def info = new InfoStruct()
        info.setCode(HOTEL_CODE)
        info.setName(HOTEL_NAME)
        info.setNif(NIF)
        info.setIban(IBAN)
        new Hotel(info, PRICE_SINGLE, PRICE_DOUBLE, new Processor(new BankInterface(), new TaxInterface()))

        when: 'another hotel is created with the same code'
        info.setCode(HOTEL_CODE)
        info.setName(HOTEL_NAME + " City")
        info.setNif(NIF + "1")
        info.setIban(IBAN)
        new Hotel(info, PRICE_SINGLE, PRICE_DOUBLE, new Processor(new BankInterface(), new TaxInterface()))

        then: 'an HotelException is thrown'
        thrown(HotelException)
        FenixFramework.getDomainRoot().getHotelSet().size() == 1
    }

    def 'nif not unique'() {
        given: 'an hotel'
        def info = new InfoStruct()
        info.setCode(HOTEL_CODE)
        info.setName(HOTEL_NAME)
        info.setNif(NIF)
        info.setIban(IBAN)
        new Hotel(info, PRICE_SINGLE, PRICE_DOUBLE, new Processor(new BankInterface(), new TaxInterface()))

        when: 'another hotel is created with the same nif'
        info.setCode("XPTO124")
        info.setName(HOTEL_NAME + "_New")
        info.setNif(NIF)
        info.setIban(IBAN)
        new Hotel(info, PRICE_SINGLE, PRICE_DOUBLE, new Processor(new BankInterface(), new TaxInterface()))

        then: 'an HotelException is thrown'
        thrown(HotelException)
        FenixFramework.getDomainRoot().getHotelSet().size() == 1
    }
}
