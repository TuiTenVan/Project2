package com.javaweb.repository.impl;

import com.javaweb.repository.RentAreaRepository;
import com.javaweb.repository.entity.BuildingEntity;
import com.javaweb.repository.entity.RentAreaEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Repository
public class RentAreaRepositoryImpl implements RentAreaRepository {

    static String URL = "jdbc:mysql://localhost:3306/estatebasic";
    static String USER = "root";
    static String PASS = "Nvv@02022003";
    @Override
    public List<RentAreaEntity> findAll(Integer rentArea) {
        StringBuilder sql = new StringBuilder("SELECT DISTINCT b.id, b.* FROM building b ");
        sql.append("JOIN rentarea ra ON b.id = ra.buildingid ");
        sql.append("WHERE 1 = 1 ");
        List<RentAreaEntity> result = new ArrayList<>();
        try(Connection connect = DriverManager.getConnection(URL, USER, PASS);
            Statement statement = connect.createStatement();
            ResultSet rs = statement.executeQuery(sql.toString());){
            while(rs.next()) {
                RentAreaEntity rent = new RentAreaEntity();
                rent.setValue(rs.getInt("value"));
                result.add(rent);
            }
        } catch(SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
}
