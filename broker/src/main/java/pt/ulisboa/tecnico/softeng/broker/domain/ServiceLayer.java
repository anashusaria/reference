package pt.ulisboa.tecnico.softeng.broker.domain;

import pt.ulisboa.tecnico.softeng.broker.services.remote.*;

public class ServiceLayer {
  private ActivityInterface activityInterface;
  private HotelInterface hotelInterface;
  private CarInterface carInterface;
  private BankInterface bankInterface;
  private TaxInterface taxInterface;

  public ServiceLayer() {
  }

  public ActivityInterface getActivityInterface() {
    if (this.activityInterface == null) {
      this.activityInterface = new ActivityInterface();
    }
    return this.activityInterface;
  }

  public HotelInterface getHotelInterface() {
    if (this.hotelInterface == null) {
      this.hotelInterface = new HotelInterface();
    }
    return this.hotelInterface;
  }

  public CarInterface getCarInterface() {
    if (this.carInterface == null) {
      this.carInterface = new CarInterface();
    }
    return this.carInterface;
  }

  public BankInterface getBankInterface() {
    if (this.bankInterface == null) {
      this.bankInterface = new BankInterface();
    }
    return this.bankInterface;
  }

  public TaxInterface getTaxInterface() {
    if (this.taxInterface == null) {
      this.taxInterface = new TaxInterface();
    }
    return this.taxInterface;
  }

  public void setActivityInterface(ActivityInterface activityInterface) {
    this.activityInterface = activityInterface;
  }

  public void setHotelInterface(HotelInterface hotelInterface) {
    this.hotelInterface = hotelInterface;
  }

  public void setCarInterface(CarInterface carInterface) {
    this.carInterface = carInterface;
  }

  public void setBankInterface(BankInterface bankInterface) {
    this.bankInterface = bankInterface;
  }

  public void setTaxInterface(TaxInterface taxInterface) {
    this.taxInterface = taxInterface;
  }
}
