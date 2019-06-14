package pt.ulisboa.tecnico.softeng.car.services.local

import org.joda.time.LocalDate
import spock.lang.Unroll

import pt.ulisboa.tecnico.softeng.car.domain.*
import pt.ulisboa.tecnico.softeng.car.exception.CarException
import pt.ulisboa.tecnico.softeng.car.services.remote.BankInterface
import pt.ulisboa.tecnico.softeng.car.services.remote.TaxInterface

class RentACarInterfaceCancelRentingMethodSpockTest extends SpockRollbackTestAbstractClass {
	def ADVENTURE_ID = 'AdventureId'
	def PLATE_CAR='22-33-HZ'
	def RENT_A_CAR_NAME='Eartz'
	def DRIVING_LICENSE='lx1423'
	def BEGIN= LocalDate.parse('2018-01-06')
	def END= LocalDate.parse('2018-01-09')
	def NIF='NIF'
	def IBAN='IBAN'
	def IBAN_BUYER='IBAN'
	def rentACar
	def car
	def renting
	def rentACarInterface

	@Override
	def populate4Test() {
		rentACarInterface = new RentACarInterface()

		def bankInterface = new BankInterface()
		def taxInterface = new TaxInterface()
		def processor = new Processor(bankInterface, taxInterface)

    def info = new InfoStruct()
    info.setName(RENT_A_CAR_NAME)
    info.setNif(NIF)
    info.setIban(IBAN)
    rentACar = new RentACar(info, processor)

		car = new Car(PLATE_CAR,10,10,rentACar)

		renting = car.rent(DRIVING_LICENSE, BEGIN, END, NIF, IBAN_BUYER, ADVENTURE_ID)
	}

	def 'success'() {
		when: 'when cancelling a rentingOne'
		String cancel = rentACarInterface.cancelRenting(renting.getReference())

		then: 'the rentingOne becomes cancelled, and the cancellation reference stored'
		renting.isCancelled()
		renting.getCancellationReference() == cancel
	}

	@Unroll('#label')
	def 'exceptions'() {
		when: 'canceling a wrong ref'
		rentACarInterface.cancelRenting(ref)

		then: 'throws an exception'
		thrown(CarException)

		where:
		label         | ref
		'missing ref' | 'MISSING_REFERENCE'
		'null ref'    | null
		'empty ref'   | ''
	}
}
