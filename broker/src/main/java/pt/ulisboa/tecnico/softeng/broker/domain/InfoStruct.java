package pt.ulisboa.tecnico.softeng.broker.domain;

public class InfoStruct {

  public static class Builder {

    private String code;
    private String name;
    private String nif;
    private String iban;

    public Builder() {

    }

    public Builder setCode(String arg) {
      this.code = arg;
      return this;
    }

    public Builder setName(String arg) {
      this.name = arg;
      return this;
    }

    public Builder setNif(String arg) {
      this.nif = arg;
      return this;
    }

    public Builder setIban(String arg) {
      this.iban = arg;
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

  public void setCode(String arg) {
    this.code = arg;
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
