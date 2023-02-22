package org.lukashchuk.appll.models.electricalAppliances;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class DefaultAppliance extends Appliance {

    public DefaultAppliance(String name, String manufacturer, Integer powerConsumption, Integer apartment, Boolean plugStatus){
        super(name, manufacturer, powerConsumption, apartment, plugStatus);
    }

    @Override
    public String toString() {
        return "DefaultAppliance [Id=" + this.getId()
                + ", name =" + this.getName()
                + ", manufacturer =" + this.getManufacturer()
                + ", powerConsumption =" + this.getPowerConsumption()
                + ", apartment =" + this.getApartment()
                + ", plugStatus =" + this.getPlugStatus() + "]";
    }
}
