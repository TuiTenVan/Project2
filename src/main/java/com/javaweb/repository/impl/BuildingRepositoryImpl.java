package com.javaweb.repository.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.javaweb.repository.BuildingRepository;
import com.javaweb.repository.entity.BuildingEntity;

@Repository
public class BuildingRepositoryImpl implements BuildingRepository {
	static String URL = "jdbc:mysql://localhost:3306/estatebasic";
	static String USER = "root";
	static String PASS = "Nvv@02022003";
	
	@Override
	public List<BuildingEntity> findAll(Map<String, Object> conditions) {
	    StringBuilder sql = new StringBuilder("SELECT DISTINCT b.*, d.*, " +
				"(SELECT GROUP_CONCAT(DISTINCT ra.value) " +
				"FROM rentarea ra WHERE b.id = ra.buildingid ) " +
				"AS rentAreas " +
				"FROM building b ");
		sql.append("JOIN district d ON b.districtid = d.id ");

		boolean hasTypeCodeCondition = conditions.containsKey("typeCode")
				&& conditions.get("typeCode") != null && !conditions.get("typeCode").toString().isEmpty();

		if(hasTypeCodeCondition) {
			sql.append("JOIN buildingrenttype bt ON b.id = bt.buildingid ");
			sql.append("JOIN renttype rt ON bt.renttypeid = rt.id ");
		}
		boolean hasStaffIdCondition = conditions.containsKey("staffId")
				&& conditions.get("staffId") != null && !conditions.get("staffId").toString().isEmpty();

		if(hasStaffIdCondition) {
			sql.append("JOIN assignmentbuilding ass ON b.id = ass.buildingid ");
			sql.append("JOIN user u ON ass.staffid = u.id ");
		}

	    sql.append("WHERE 1 = 1 ");
	    appendConditions(sql, conditions);
	    return executeQuery(sql.toString());
	}
	private void appendConditions(StringBuilder sql, Map<String, Object> conditions) {
	    for (Map.Entry<String, Object> entry : conditions.entrySet()) {
	        String field = entry.getKey();
	        Object value = entry.getValue();
			if(value instanceof String && !((String) value).isEmpty()) {
				if (field.equals("minRentPrice")) {
					Integer minRentPrice = Integer.parseInt(conditions.get(field).toString());
					sql.append("AND b.rentprice >= " + minRentPrice + " ");
				}
				else if (field.equals("maxRentPrice")) {
					Integer maxRentPrice = Integer.parseInt(conditions.get(field).toString()) ;
					sql.append("AND b.rentprice <= " + maxRentPrice + " ");
				}
				else if (field.equals("minRentArea")) {
					Integer minRentArea = Integer.parseInt(conditions.get(field).toString());
					sql.append("AND ra.value >= " + minRentArea + " ");
				}
				else if (field.equals("maxRentArea")) {
					Integer maxRentArea = Integer.parseInt(conditions.get(field).toString());
					sql.append("AND ra.value <= " + maxRentArea + " ");
				}
				else if (field.equals("typeCode")) {
					sql.append("AND (rt.code = ");
					String[] typeCodes = conditions.get(field).toString().split(",");
					for (int i = 0; i < typeCodes.length - 1; i++) {
						sql.append("'" + typeCodes[i].trim() + "' OR rt.code = ");
					}
					sql.append("'" + typeCodes[typeCodes.length - 1].trim() + "') ");
				}
				else if (field.equals("staffId")) {
					Integer staffId = Integer.parseInt(conditions.get(field).toString());
					sql.append("AND u.id" + " = " + staffId + " ");
				}
				else{
					if (field.equals("floorArea") || field.equals("numberOfBasement") || field.equals("districtId")) {
						Integer tmp = Integer.parseInt(conditions.get(field).toString());
						sql.append("AND b." + field + " = " + tmp + " ");
					} else {
						sql.append("AND b." + field + " LIKE '%" + value + "%' ");
					}
				}
			}
	    }
	}
	public List<BuildingEntity> executeQuery(String sql){
		List<BuildingEntity> result = new ArrayList<>();
		try(Connection connect = DriverManager.getConnection(URL, USER, PASS);
				Statement statement = connect.createStatement();
				ResultSet rs = statement.executeQuery(sql);){
			while(rs.next()) {
				BuildingEntity building = new BuildingEntity();
				building.setName(rs.getString("name"));
				building.setStreet(rs.getString("street"));
				building.setWard(rs.getString("ward"));
				building.setNumberOfBasement(rs.getInt("numberOfBasement"));
				building.setFloorArea(rs.getInt("floorArea"));
				building.setRentPrice(rs.getInt("rentPrice"));
				building.setManagerName(rs.getString("managerName"));
				building.setManagerPhoneNumber(rs.getString("managerPhoneNumber"));
				building.setRentArea(rs.getString("rentAreas"));
				building.setDistrictId(rs.getString("d.name"));
				result.add(building);
			}
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return result;
	}
}
