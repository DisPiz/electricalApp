package org.lukashchuk.appll.models.electricalAppliances;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class LitreageAppliance extends Appliance {

    private Integer litreage;

    public LitreageAppliance(String name, String manufacturer, Integer powerConsumption, Integer apartment, Boolean plugStatus, Integer litreage){
        super(name, manufacturer, powerConsumption, apartment, plugStatus);
        this.litreage = litreage;
    }

    @Override
    public String toString() {
        return "LitreageAppliance [Id=" + this.getId()
                + ", name =" + this.getName()
                + ", manufacturer =" + this.getManufacturer()
                + ", powerConsumption =" + this.getPowerConsumption()
                + ", apartment =" + this.getApartment()
                + ", plugStatus =" + this.getPlugStatus()
                + ", litreage =" + this.getLitreage() + "]";
    }
}
