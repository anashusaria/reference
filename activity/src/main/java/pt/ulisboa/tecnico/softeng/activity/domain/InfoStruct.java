package pt.ulisboa.tecnico.softeng.activity.domain;

public class InfoStruct {

  public static class Builder {

    private String code;
    private String name;
    private String nif;
    private String iban;

    public Builder() {

    }

    public Builder setCode(String code) {
      this.code = code;
      return this;
    }

    public Builder setName(String name) {
      this.name = name;
      return this;
    }

    public Builder setNif(String nif) {
      this.nif = nif;
      return this;
    }

    public Builder setIban(String iban) {
      this.iban = iban;
      return this;
    }

    public InfoStruct build() {
      InfoStruct info = new InfoStruct();
      info.setCode(this.code);
      info.setName(this.name);
      info.setNif(this.nif);
      info.setIban(this.iban);
      return info;
    }

  }

  private String code;
  private String name;
  private String nif;
  private String iban;

  private InfoStruct() {

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
