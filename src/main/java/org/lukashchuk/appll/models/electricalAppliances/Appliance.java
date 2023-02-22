package org.lukashchuk.appll.models.electricalAppliances;

import lombok.*;

@EqualsAndHashCode
@Getter
@Setter
@NoArgsConstructor
@ToString
public abstract class Appliance {
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public Integer getPowerConsumption() {
        return powerConsumption;
    }

    public void setPowerConsumption(Integer powerConsumption) {
        this.powerConsumption = powerConsumption;
    }

    public Boolean getPlugStatus() {
        return plugStatus;
    }

    public void setPlugStatus(Boolean plugStatus) {
        this.plugStatus = plugStatus;
    }

    public Integer getApartment() {
        return apartment;
    }

    public void setApartment(Integer apartment) {
        this.apartment = apartment;
    }

    private Long id;
    private String type;
    private String name;
    private String manufacturer;
    private Integer powerConsumption;
    private Boolean plugStatus;
    private Integer apartment;

    public Appliance(String name, String manufacturer, Integer powerConsumption, Integer apartment, Boolean plugStatus){
        this.name = name;
        this.manufacturer = manufacturer;
        this.powerConsumption = powerConsumption;
        this.apartment = apartment;
        this.plugStatus = plugStatus;
    }


}
