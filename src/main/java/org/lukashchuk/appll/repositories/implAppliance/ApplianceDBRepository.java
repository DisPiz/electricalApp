package org.lukashchuk.appll.repositories.implAppliance;

import org.lukashchuk.appll.models.electricalAppliances.Appliance;
import org.lukashchuk.appll.models.electricalAppliances.BatteryAppliance;
import org.lukashchuk.appll.models.electricalAppliances.DefaultAppliance;
import org.lukashchuk.appll.models.electricalAppliances.LitreageAppliance;
import org.lukashchuk.appll.repositories.ApplianceRepository;
import org.lukashchuk.appll.utils.DBConnector;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

public class ApplianceDBRepository implements ApplianceRepository {
    @Override
    public List<Appliance> getAll() {
        DBConnector.conectionDB();
        List<Appliance> appliances = new ArrayList<>();
        try {
            Statement statement = DBConnector.getConnection().createStatement();
            ResultSet allAppliances = statement.executeQuery("select * from appliance");

            while (allAppliances.next()){
                Long applianceID = allAppliances.getLong("idappliance");
                String apllianceType = allAppliances.getString("type");

                String name = allAppliances.getString("name");
                String manufacturer = allAppliances.getString("manufacturer");
                Integer power = allAppliances.getInt("power");
                Integer apartment = allAppliances.getInt("apartment");
                Boolean plug = allAppliances.getBoolean("plug");

                if(Objects.equals(allAppliances.getString("type"), "default")){
                  DefaultAppliance defaultAppliance = new DefaultAppliance(name, manufacturer, power, apartment, plug);
                  defaultAppliance.setId(applianceID);
                  defaultAppliance.setType(apllianceType);
                  appliances.add(defaultAppliance);
                }
                if(Objects.equals(allAppliances.getString("type"), "litreage")){
                    Statement litreageStatement = DBConnector.getConnection().createStatement();
                    ResultSet litreageById = litreageStatement.executeQuery("select * from litreage_app where litreage_app.idlitreage = '"+applianceID+"'");
                    if(litreageById.next()){
                        LitreageAppliance litreageAppliance = new LitreageAppliance(name, manufacturer, power, apartment, plug, litreageById.getInt("litreage"));
                        litreageAppliance.setId(applianceID);
                        litreageAppliance.setType(apllianceType);
                        appliances.add(litreageAppliance);
                    }
                }
                if(Objects.equals(allAppliances.getString("type"), "battery")){
                    Statement batteryStatement = DBConnector.getConnection().createStatement();
                    ResultSet batteryById = batteryStatement.executeQuery("select * from battery_app where battery_app.idbattery = '"+applianceID+"'");
                    if(batteryById.next()){
                        BatteryAppliance batteryAppliance = new BatteryAppliance(name, manufacturer, power, apartment, plug, batteryById.getBoolean("battery_status"));
                        batteryAppliance.setId(applianceID);
                        batteryAppliance.setType(apllianceType);
                        appliances.add(batteryAppliance);
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        DBConnector.conectionDB();
        return appliances;
    }

    @Override
    public void add(Appliance applianceAdd) {
        DBConnector.conectionDB();
        try {
            String type = "";
            Statement statementInsert = DBConnector.getConnection().createStatement();
            Statement statementMaxID = DBConnector.getConnection().createStatement();
            Statement statementType = DBConnector.getConnection().createStatement();
            if(applianceAdd instanceof DefaultAppliance){
                type = "default";
            } else if (applianceAdd instanceof LitreageAppliance) {
                type = "litreage";
            } else if (applianceAdd instanceof BatteryAppliance){
                type = "battery";
            }
            statementInsert.execute("insert into appliance(appliance.name, appliance.manufacturer, appliance.power, appliance.apartment, appliance.plug, appliance.type)" +
                    "values ('"+applianceAdd.getName()+"', '"+applianceAdd.getManufacturer()+"', '"+applianceAdd.getPowerConsumption()+"', '"+applianceAdd.getApartment()+"', 0, '"+type+"')");
            ResultSet maxIdDb  = statementMaxID.executeQuery("SELECT MAX(appliance.idappliance) FROM appliance");
            if(maxIdDb.next()){
                Integer maxID = maxIdDb.getInt(1);
                if(type.equals("litreage")){
                    statementType.execute("insert into litreage_app(litreage_app.idlitreage, litreage_app.litreage)" +
                            "values ('"+ maxID +"', '"+ ((LitreageAppliance) applianceAdd).getLitreage() +"')");
                } else if (type.equals("battery")){
                    Integer battery = 0;
                    if(((BatteryAppliance) applianceAdd).getBatteryOn()){
                        battery = 1;
                    }
                    statementType.execute("insert into battery_app(battery_app.idbattery, battery_app.battery_status)" +
                            "values ('"+ maxID +"', '"+ battery +"')");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            DBConnector.conectionDB();
        }

    }

    @Override
    public void delete(Long removableId) {
        DBConnector.conectionDB();
        try {
            Statement statement = DBConnector.getConnection().createStatement();
            statement.execute("delete from appliance where appliance.idappliance = '"+removableId+"'");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            DBConnector.conectionDB();
        }

    }

    @Override
    public void update(Long updatableId) {
        DBConnector.conectionDB();
        try {
            Statement statement = DBConnector.getConnection().createStatement();
            statement.execute("update appliance set `plug` = if(appliance.plug = 0, appliance.plug + 1, appliance.plug - 1)  " +
                    "where appliance.idappliance = '"+updatableId+"'");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            DBConnector.conectionDB();
        }

    }

    @Override
    public void updateAllFields(Long updatableId, Appliance applianceAdd) {
        DBConnector.conectionDB();
        try {
            Statement statementType = DBConnector.getConnection().createStatement();
            Statement statementUpdate = DBConnector.getConnection().createStatement();
            Statement statementOptional = DBConnector.getConnection().createStatement();

            ResultSet appTypeDB = statementType.executeQuery("SELECT appliance.type FROM appliancedb.appliance where appliance.idappliance = '"+updatableId+"'");
            if(appTypeDB.next()){
                String appType = appTypeDB.getString("type");
                statementUpdate.execute("update appliance set appliance.name = '"+applianceAdd.getName()+"', appliance.manufacturer = '"+applianceAdd.getManufacturer()+"', appliance.power = '"+applianceAdd.getPowerConsumption()+"', appliance.apartment = '"+applianceAdd.getApartment()+"' where appliance.idappliance = '"+ updatableId +"'");
                if (appType.equals("battery")) {
                    statementOptional.execute("UPDATE appliancedb.battery_app set appliancedb.battery_app.battery_status = '"+ ((BatteryAppliance) applianceAdd).getBatteryOn() +"' where appliancedb.battery_app.idbattery = '"+ updatableId +"'");
                } else if (appType.equals("litreage")) {
                    statementOptional.execute("update appliancedb.litreage_app set appliancedb.litreage_app.litreage = '"+ ((LitreageAppliance) applianceAdd).getLitreage() +"' where appliancedb.litreage_app.idlitreage = '"+ updatableId +"'");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            DBConnector.conectionDB();
        }

    }

    @Override
    public List<Appliance> sortByPower(Integer apartment) {
        List<Appliance> appliances = this.getAll();
        DBConnector.conectionDB();
        List <Appliance> sortedList = appliances.stream().filter(appliance -> appliance.getApartment().equals(apartment))
                .sorted(Comparator.comparingInt(Appliance::getPowerConsumption)).toList();
        DBConnector.conectionDB();
        return sortedList;
    }

    @Override
    public Integer sumPower() {
        List<Appliance> appliances = this.getAll();
        DBConnector.conectionDB();
        Integer sum = 0;
        List<Appliance> powerSum = appliances.stream().filter(appliance -> appliance.getPlugStatus().equals(true)).toList();
        for (Appliance element : powerSum) {
            sum += element.getPowerConsumption();
        }
        DBConnector.conectionDB();
        return sum;
    }

    @Override
    public List<Appliance> findInRange(Integer apartment, Integer lowestPower, Integer highestPower) {
        List<Appliance> appliances = this.getAll();
        DBConnector.conectionDB();
        List <Appliance> sortedList = appliances.stream().filter(appliance -> appliance.getApartment().equals(apartment))
                .filter(appliance -> appliance.getPowerConsumption() >= lowestPower && appliance.getPowerConsumption() <= highestPower)
                .sorted(Comparator.comparingInt(Appliance::getPowerConsumption)).toList();
        DBConnector.conectionDB();
        return sortedList;
    }
}
