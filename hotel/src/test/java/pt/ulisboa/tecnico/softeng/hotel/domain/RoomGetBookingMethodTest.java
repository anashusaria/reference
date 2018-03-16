package pt.ulisboa.tecnico.softeng.hotel.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import mockit.Mocked;
import mockit.integration.junit4.JMockit;
import org.joda.time.LocalDate;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import org.junit.runner.RunWith;
import pt.ulisboa.tecnico.softeng.hotel.domain.Room.Type;
import pt.ulisboa.tecnico.softeng.hotel.interfaces.BankInterface;
import pt.ulisboa.tecnico.softeng.hotel.interfaces.TaxInterface;

@RunWith(JMockit.class)
public class RoomGetBookingMethodTest {
	private final LocalDate arrival = new LocalDate(2016, 12, 19);
	private final LocalDate departure = new LocalDate(2016, 12, 24);
	private Hotel hotel;
	private Room room;
	private Booking booking;

    @Mocked
    private TaxInterface taxInterface;
    @Mocked private BankInterface bankInterface;

    @Before
	public void setUp() {
		this.hotel = new Hotel("XPTO123", "Lisboa", "NIF", "IBAN", 20.0, 30.0);
		this.room = new Room(this.hotel, "01", Type.SINGLE);
		this.booking = this.room.reserve(Type.SINGLE, this.arrival, this.departure, "NIF");
	}

	@Test
	public void success() {
		assertEquals(this.booking, this.room.getBooking(this.booking.getReference()));
	}

	@Test
	public void successCancelled() {
		this.booking.cancel();

		assertEquals(this.booking, this.room.getBooking(this.booking.getCancellation()));
	}

	@Test
	public void doesNotExist() {
		assertNull(this.room.getBooking("XPTO"));
	}

	@After
	public void tearDown() {
		Hotel.hotels.clear();
	}
}
