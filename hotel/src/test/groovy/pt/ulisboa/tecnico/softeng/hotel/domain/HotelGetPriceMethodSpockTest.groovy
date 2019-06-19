package pt.ulisboa.tecnico.softeng.hotel.domain

import pt.ulisboa.tecnico.softeng.hotel.exception.HotelException
import pt.ulisboa.tecnico.softeng.hotel.services.remote.BankInterface
import pt.ulisboa.tecnico.softeng.hotel.services.remote.TaxInterface

class HotelGetPriceMethodSpockTest extends SpockRollbackTestAbstractClass {
    def PRICE_SINGLE = 20
    def PRICE_DOUBLE = 30
    def hotel

    @Override
    def populate4Test() {
    }

    def 'price single'() {
        given: 'a hotel'
        def info = new InfoStruct.Builder()
          .setCode("XPTO123")
          .setName("Lisboa")
          .setNif("NIF")
          .setIban("IBAN")
          .build()
        hotel = new Hotel(info, PRICE_SINGLE, PRICE_DOUBLE, new Processor(new BankInterface(), new TaxInterface()))

        expect:
        hotel.getPrice(Room.Type.SINGLE) == PRICE_SINGLE
    }

    def 'price double'() {
        given: 'a hotel'
        def info = new InfoStruct.Builder()
          .setCode("XPTO123")
          .setName("Lisboa")
          .setNif("NIF")
          .setIban("IBAN")
          .build()
        hotel = new Hotel(info, PRICE_SINGLE, PRICE_DOUBLE, new Processor(new BankInterface(), new TaxInterface()))

        expect:
        hotel.getPrice(Room.Type.DOUBLE) == PRICE_DOUBLE
    }

    def 'incorrect input'() {
        given: 'a hotel'
        def info = new InfoStruct.Builder()
          .setCode("XPTO123")
          .setName("Lisboa")
          .setNif("NIF")
          .setIban("IBAN")
          .build()
        hotel = new Hotel(info, PRICE_SINGLE, PRICE_DOUBLE, new Processor(new BankInterface(), new TaxInterface()))

        when:
        hotel.getPrice(null)

        then: 'a HotelException'
        def error = thrown(HotelException)
    }
}
