package org.lukashchuk.appll.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.lukashchuk.appll.models.electricalAppliances.Appliance;
import org.lukashchuk.appll.repositories.implAppliance.ApplianceDBRepository;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
public class ApplianceService {

    private ApplianceDBRepository applianceRepository = new ApplianceDBRepository();

    public List<Appliance> getAll() {
        List<Appliance> appliances = applianceRepository.getAll();
        log.info("IN getAll - {} appliance found", appliances.size());
        return appliances;
    }


    public void add(Appliance appliance) {
        applianceRepository.add(appliance);
        log.info("IN add - {} appliance added", appliance.getName());
    }

    public void delete(Long removableId) {
        applianceRepository.delete(removableId);
        log.info("IN delete - {} appliance deleted", removableId);
    }

    public void update(Long updatableId){
        applianceRepository.update(updatableId);
        log.info("IN add - {} appliance updated", updatableId);
    }

    public Integer sumPower(){
       Integer sum = applianceRepository.sumPower();
       log.info("IN sumPower - {} power sum", sum);
       return sum;
    }

    public void updateAllFields(Long updatableId, Appliance appliance){
        List<Appliance> appliances = applianceRepository.getAll();
        if(!appliances.isEmpty()){
            applianceRepository.updateAllFields(updatableId, appliance);
            log.info("IN add - {} appliance updated at id = {}", appliance.getName(), updatableId);
        } else {
            log.warn("You have nothing to update!");
        }
    }
    public List<Appliance> sortByPower(Integer apartment){
        List<Appliance> sortedList = applianceRepository.sortByPower(apartment);
        log.info("IN sortByPower - {} appliances sorted", sortedList.size());
        return sortedList;
    }

    public List<Appliance> findInRange(Integer apartment, Integer lowestPower, Integer highestPower){
        List<Appliance> sortedList = applianceRepository.findInRange(apartment, lowestPower, highestPower);
        log.info("IN findInRange - {} appliances found in range from {} to {}", sortedList.size(), lowestPower, highestPower);
        return sortedList;
    }

}
