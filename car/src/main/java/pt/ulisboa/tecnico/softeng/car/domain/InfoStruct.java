package pt.ulisboa.tecnico.softeng.car.domain;

public class InfoStruct {
  private String name;
  private String nif;
  private String iban;

  public InfoStruct() {

  }

  public String getName() {
    return this.name;
  }

  public String getNif() {
    return this.nif;
  }

  public String getIban() {
    return this.iban;
  }

  public void setName(String arg) {
    this.name = arg;
  }

  public void setNif(String arg) {
    this.nif = arg;
  }

  public void setIban(String arg) {
    this.iban = arg;
  } 
}
