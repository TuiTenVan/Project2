package com.javaweb.repository.impl;

import com.javaweb.Utils.ConnectionJDBC;
import com.javaweb.repository.RentAreaRepository;
import com.javaweb.repository.entity.BuildingEntity;
import com.javaweb.repository.entity.DistrictEntity;
import com.javaweb.repository.entity.RentAreaEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Repository
public class RentAreaRepositoryImpl implements RentAreaRepository {

	@Override
    public List<RentAreaEntity> findAll(Object rent) {

        StringBuilder sql = new StringBuilder("SELECT * FROM rentarea r WHERE r.buildingid = " + rent);
        
        return executeQuery(sql.toString());
    }

    public List<RentAreaEntity> executeQuery(String sql){

    	List<RentAreaEntity> result = new ArrayList<>();

        try(
                Connection connect = ConnectionJDBC.getConnection();
                Statement stm = connect.createStatement();
                ResultSet rs = stm.executeQuery(sql.toString());)
            {
                while(rs.next()) {
                	RentAreaEntity rentArea = new RentAreaEntity();
                    rentArea.setId(rs.getInt("r.id"));
                    rentArea.setValue(rs.getInt("r.value"));
                    rentArea.setBuildingId(rs.getInt("r.buildingid"));
                    result.add(rentArea);
                }
            }
            catch(SQLException e) {
                System.out.print("Failed");
                e.printStackTrace();
            }
            return result;
    }

}
