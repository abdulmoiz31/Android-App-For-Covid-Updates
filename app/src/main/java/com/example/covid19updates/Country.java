package com.example.covid19updates;


public class Country {
    //fields
    private String name;
    private String recovered;
    private String deaths;
    private String total;
    private String critical;
    private String deathrate;
    private String recoveryrate;
    private String recovervsdeath;
    private String tdeath;
    private String tconfirm;

    //Constructors


    public Country(String name, String recovered, String deaths, String total) {
        this.name = name;
        this.recovered = recovered;
        this.deaths = deaths;
        this.total = total;
    }


    public Country() {
    }
    //getters and setters

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRecovered() {
        return recovered;
    }

    public void setRecovered(String recovered) {
        this.recovered = recovered;
    }

    public String getDeaths() {
        return deaths;
    }

    public void setDeaths(String deaths) {
        this.deaths = deaths;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getCritical() {
        return critical;
    }

    public void setCritical(String critical) {
        this.critical = critical;
    }

    public String getDeathrate() {
        return deathrate;
    }

    public void setDeathrate(String deathrate) {
        this.deathrate = deathrate;
    }

    public String getRecoveryrate() {
        return recoveryrate;
    }

    public void setRecoveryrate(String recoveryrate) {
        this.recoveryrate = recoveryrate;
    }

    public String getRecovervsdeath() {
        return recovervsdeath;
    }

    public void setRecovervsdeath(String recovervsdeath) {
        this.recovervsdeath = recovervsdeath;
    }

    public String getTdeath() {
        return tdeath;
    }

    public void setTdeath(String tdeath) {
        this.tdeath = tdeath;
    }

    public String getTconfirm() {
        return tconfirm;
    }

    public void setTconfirm(String tconfirm) {
        this.tconfirm = tconfirm;
    }
}
