package com.javaweb.repository.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.javaweb.repository.BuildingRepository;
import com.javaweb.repository.entity.BuildingEntity;

@Repository
public class BuildingRepositoryImpl implements BuildingRepository {
	static String URL = "jdbc:mysql://localhost:3306/estatebasic";
	static String USER = "root";
	static String PASS = "Nvv@02022003";
	@Override
	public List<BuildingEntity> findAll(String name, Long district) {
//		String sql = "SELECT * FROM building b WHERE name like '%" + name + "%'";
		StringBuilder sql = new StringBuilder("SELECT * FROM building WHERE 1 = 1 ");
		if(name != null && !name.equals("")) {
			sql.append("AND name like '%" + name + "%' ");
		}
		if(district != null) {
			sql.append("AND districtid = " + district + " ");
		}
		
		List<BuildingEntity> result = new ArrayList<>();
		try(Connection connect = DriverManager.getConnection(URL, USER, PASS); 
				Statement statement = connect.createStatement();
				ResultSet rs = statement.executeQuery(sql.toString());){
//			System.out.println("Connect successful!");
			while(rs.next()) {
				BuildingEntity building = new BuildingEntity();
				building.setName(rs.getString("name"));
				building.setStreet(rs.getString("street"));
				building.setWard(rs.getString("ward"));
				building.setNumberOfBasement(rs.getInt("numberOfBasement"));
				result.add(building);
			}
		} catch(SQLException e) {
			e.printStackTrace();
//			System.out.print("Connect database failed");
		}
		return result;
	}

}
