package pe.edu.utp.coinsoftwebprofile.models;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ManagersEntity extends BaseEntity{

    public ManagersEntity(){
        super();
        setTableName("managers");
    }

    public ManagersEntity(Connection connection, String tableName){
        super(connection,tableName);
    }

    public List<Manager> findByCriteria(String criteria){
        try{
            ResultSet rs = getConnection()
                    .createStatement()
                    .executeQuery(
                            getBaseStatement()
                                    .concat(criteria));
            List<Manager> managers = new ArrayList<>();
            while(rs.next()){
                managers.add(Manager.from(rs));
                return managers;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;

    }

    public Manager findById(String idmanager){
        return findByCriteria(String.format("WHERE name= %s",idmanager)).get(0);
    }

    public Manager findByName(String Name){
        return findByCriteria(String.format("WHERE name= %s",Name)).get(0);
    }

    public Manager findByLastName(String LastName){
        return findByCriteria(String.format("WHERE last_name= %s",LastName)).get(0);
    }

    public List<Manager> findAll(){
        return findByCriteria("");
    }

    public Manager login(String dni, String password){
        return findByCriteria(String.format("WHERE dni= %s AND password= %s",dni,password)).get(0);
    }

    public boolean create(Manager manager){
        return executeUpdate(String.format("INSERT INTO %s (type,dni,password,name,last_name,email,time_work,idadmin,status) VALUES" +
                        "('%s','%s','%s','%s','%s','%s',%d,%d,'%s')",getTableName(),manager.getType(),manager.getDni(),manager.getName(),manager.getLast_name()
                ,manager.getEmail(),manager.getTime_work(),manager.getIdadmin(),manager.getStatus()));
    }

    public boolean create(String type,String dni,String password,String name,String last_name,String email,String status,int time_work,int idadmin){
        return create(new Manager(type,dni,password,name,last_name,email,status,time_work,idadmin));
    }

}
