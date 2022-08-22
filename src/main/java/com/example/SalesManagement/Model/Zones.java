package com.example.SalesManagement.Model;

import javax.persistence.*;

@Entity
@Table(name = "Zones")
public class Zones {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "zoneId")
    private String zoneId;
    @Column(name = "City")
    private String city;
    @Column(name = "tarMonQuota")
    private Long tarMonQuota;
    
    public Zones()
    {
        super();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getZoneId() {
        return zoneId;
    }

    public void setZoneId(String zoneId) {
        this.zoneId = zoneId;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Long getTarMonQuota() {
        return tarMonQuota;
    }

    public void setTarMonQuota(Long tarMonQuota) {
        this.tarMonQuota = tarMonQuota;
    }
}
