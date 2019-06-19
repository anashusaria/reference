package pt.ulisboa.tecnico.softeng.broker.domain;

import org.joda.time.LocalDate;
import pt.ist.fenixframework.FenixFramework;
import pt.ulisboa.tecnico.softeng.broker.exception.BrokerException;
import pt.ulisboa.tecnico.softeng.broker.services.remote.*;
import pt.ulisboa.tecnico.softeng.broker.services.remote.dataobjects.RestRoomBookingData;

import java.util.Objects;

public class Broker extends Broker_Base {
    private ServiceLayer services;

    public Broker(String code, String name, String nif, String iban, ServiceLayer services) {
        checkArguments(code, name, nif, iban);

        setCode(code);
        setName(name);
        setNif(nif);
        setIban(iban);

        this.services = services;

        FenixFramework.getDomainRoot().addBroker(this);
    }

    public void delete() {
        setRoot(null);

        for (Adventure adventure : getAdventureSet()) {
            adventure.delete();
        }

        for (BulkRoomBooking bulkRoomBooking : getRoomBulkBookingSet()) {
            bulkRoomBooking.delete();
        }

        for (Client client : getClientSet()) {
            client.delete();
        }

        deleteDomainObject();
    }

    private void checkArguments(String code, String name, String nif, String iban) {
        if (code == null || code.trim().length() == 0 || name == null || name.trim().length() == 0
                || nif == null || nif.trim().length() == 0 || iban == null || iban.trim().length() == 0) {
            throw new BrokerException();
        }

        for (Broker broker : FenixFramework.getDomainRoot().getBrokerSet()) {
            if (broker.getCode().equals(code)) {
                throw new BrokerException();
            }
        }

        for (Broker broker : FenixFramework.getDomainRoot().getBrokerSet()) {
            if (broker.getNif().equals(nif)) {
                throw new BrokerException();
            }
        }

    }

    public Client getClientByNIF(String NIF) {
        for (Client client : getClientSet()) {
            if (client.getNif().equals(NIF)) {
                return client;
            }
        }
        return null;
    }

    boolean drivingLicenseIsRegistered(String drivingLicense) {
        return getClientSet().stream().anyMatch(client -> client.getDrivingLicense().equals(drivingLicense));
    }

    public void bulkBooking(int number, LocalDate arrival, LocalDate departure) {
        BulkRoomBooking bulkBooking = new BulkRoomBooking(this, number, arrival, departure);
        bulkBooking.processBooking();
    }

    @Override
    public int getCounter() {
        int counter = super.getCounter() + 1;
        setCounter(counter);
        return counter;
    }

    public RestRoomBookingData getRoomBookingFromBulkBookings(String type, LocalDate arrival, LocalDate departure) {
        return getRoomBulkBookingSet().stream().map(bulkRoomBooking -> bulkRoomBooking.getRoomBookingData4Type(type, arrival, departure)).
                filter(Objects::nonNull).findAny().orElse(null);
    }


    public ActivityInterface getActivityInterface() {
        if (this.services == null) {
            this.services = new ServiceLayer.Builder().build();
        }

        return this.services.getActivityInterface();
    }

    public HotelInterface getHotelInterface() {
        if (this.services == null) {
            this.services = new ServiceLayer.Builder().build();
        }
        return this.services.getHotelInterface();
    }

    CarInterface getCarInterface() {
        if (this.services == null) {
            this.services = new ServiceLayer.Builder().build();
        }
        return this.services.getCarInterface();
    }

    public BankInterface getBankInterface() {
        if (this.services == null) {
            this.services = new ServiceLayer.Builder().build();
        }
        return this.services.getBankInterface();
    }

    TaxInterface getTaxInterface() {
        if (this.services == null) {
            this.services = new ServiceLayer.Builder().build();
        }
        return this.services.getTaxInterface();
    }

}
