package org.lukashchuk.appll.models.electricalAppliances;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
public class BatteryAppliance extends Appliance {

    private Boolean batteryOn;
    public BatteryAppliance(String name, String manufacturer, Integer powerConsumption, Integer apartment, Boolean plugStatus, Boolean batteryOn){
        super(name, manufacturer, powerConsumption, apartment, plugStatus);
        this.batteryOn = batteryOn;
    }

    @Override
    public String toString() {
        return "BatteryAppliance [Id=" + this.getId()
                + ", name =" + this.getName()
                + ", manufacturer =" + this.getManufacturer()
                + ", powerConsumption =" + this.getPowerConsumption()
                + ", apartment =" + this.getApartment()
                + ", plugStatus =" + this.getPlugStatus()
                + ", batteryStatus =" + this.getBatteryOn() + "]";
    }
}
