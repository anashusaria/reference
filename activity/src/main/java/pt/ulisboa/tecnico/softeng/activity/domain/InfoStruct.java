package pt.ulisboa.tecnico.softeng.activity.domain;

public class InfoStruct {
  private String code;
  private String name;
  private String nif;
  private String iban;

  public InfoStruct() {

  }

  public String getCode() {
    return this.code;
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

  public void setCode(String code) {
    this.code = code;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setNif(String nif) {
    this.nif = nif;
  }

  public void setIban(String iban) {
    this.iban = iban;
  }
}
