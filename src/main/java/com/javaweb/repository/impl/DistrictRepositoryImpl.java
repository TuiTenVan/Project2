package com.javaweb.repository.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.javaweb.Utils.ConnectionJDBC;
import org.springframework.stereotype.Repository;

import com.javaweb.repository.DistrictRepository;
import com.javaweb.repository.entity.DistrictEntity;


@Repository
public class DistrictRepositoryImpl implements DistrictRepository {

    public DistrictEntity findOne(Object districtId) {

        StringBuilder sql = new StringBuilder("SELECT d.* FROM district d WHERE d.id = " + districtId);

        return executeQuery(sql.toString());
    }

    public DistrictEntity executeQuery(String sql){

        DistrictEntity result = new DistrictEntity();

        try(
                Connection connect = ConnectionJDBC.getConnection();
                Statement stm = connect.createStatement();
                ResultSet rs = stm.executeQuery(sql.toString());)
            {
                while(rs.next()) {
                    result.setId(rs.getInt("d.id"));
                    result.setCode(rs.getString("d.code"));
                    result.setName(rs.getString("d.name"));
                }
            }
            catch(SQLException e) {
                System.out.print("Failed");
                e.printStackTrace();
            }

            return result;
    }

}