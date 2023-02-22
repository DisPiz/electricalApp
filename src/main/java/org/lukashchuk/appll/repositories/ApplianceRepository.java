package org.lukashchuk.appll.repositories;


import org.lukashchuk.appll.models.electricalAppliances.Appliance;

import java.util.List;

public interface ApplianceRepository {
    List<Appliance> getAll();

    void add(Appliance appliance);

    void delete(Long removableId);

    void update(Long updatableId);

    void updateAllFields(Long updatableId, Appliance appliance);

    List<Appliance> sortByPower(Integer apartment);

    Integer sumPower();
    List<Appliance> findInRange(Integer apartment, Integer lowestPower, Integer highestPower);

}
