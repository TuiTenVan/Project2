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
	    StringBuilder sql = new StringBuilder("SELECT DISTINCT b.id, b.* FROM building b ");
	    sql.append("JOIN rentarea ra ON b.id = ra.id ");	   
	    sql.append("JOIN district d ON b.districtid = d.id ");
	    sql.append("JOIN buildingrenttype bt ON b.id = bt.buildingid ");
	    sql.append("JOIN renttype rt ON bt.renttypeid = rt.id ");
	    sql.append("JOIN assignmentbuilding ass ON b.id = ass.buildingid ");
	    sql.append("JOIN user u ON ass.staffid = u.id ");
	    sql.append("WHERE 1 = 1 ");
	    appendConditions(sql, conditions);
	    return executeQuery(sql.toString());
	}

	private void appendConditions(StringBuilder sql, Map<String, Object> conditions) {
		Map<String, Object> rentPrices = new HashMap<>();
		Map<String, Object> rentAreas = new HashMap<>();
	    for (Map.Entry<String, Object> entry : conditions.entrySet()) {	    	
	        String field = entry.getKey();
	        Object value = entry.getValue(); 	    
	        if(field.equals("minRentPrice") || field.equals("maxRentPrice")) {
	        	rentPrices.put(field, value);
	        }	 
	        else if(field.equals("minRentArea") || field.equals("maxRentArea")) {
	        	rentAreas.put(field, value);
	        }      
	        else if(field.equals("typeCode")) {
            	sql.append("AND (rt.code = ");
            	List<String> typeCodes = (List<String>) conditions.get(field);
            	for(int i = 0; i < typeCodes.size() - 1; i++) {
            		sql.append("'" + typeCodes.get(i) + "' OR rt.code = ");
            	}
            	sql.append("'" + typeCodes.get(typeCodes.size() - 1) + "') ");
	        }
	        else if(field.equals("id")) {
	        	
	        	Integer tmp1 = conditions.containsKey(field) ? Integer.parseInt(conditions.get(field).toString()) : null;
            	sql.append("AND u." + field + " = " + tmp1 + " ");	
	        }
	        else if (value != null) {
	            if (value instanceof String && !((String) value).isEmpty()) {             
	                if(field.equals("floorArea") || field.equals("numberOfBasement") || field.equals("districtId")) {
	                	Integer tmp = conditions.containsKey(field) ? Integer.parseInt(conditions.get(field).toString()) : null;
	                	sql.append("AND b." + field + " = " + tmp + " ");	
	                }	            
	                else {
	                	sql.append("AND b." + field + " LIKE '%" + value + "%' ");
	                }
	            } 	     
	        }
	    }
	    Integer minRentPrice = rentPrices.containsKey("minRentPrice") && rentPrices.get("minRentPrice") instanceof String && !((String) rentPrices.get("minRentPrice")).isEmpty()
	    		? Integer.parseInt((String) rentPrices.get("minRentPrice"))
	            : null;
		Integer maxRentPrice = rentPrices.containsKey("maxRentPrice") && rentPrices.get("maxRentPrice") instanceof String&& !((String) rentPrices.get("maxRentPrice")).isEmpty()
	    		? Integer.parseInt((String) rentPrices.get("maxRentPrice"))
	            : null;
		if(minRentPrice != null && maxRentPrice != null ) {		
			sql.append("AND b.rentprice >= " + minRentPrice + " AND b.rentprice <= " + maxRentPrice);
	    }
		else {
			if(minRentPrice == null && maxRentPrice != null) {
				sql.append("AND b.rentprice <= " + maxRentPrice + " ");
			}
			else if(maxRentPrice == null && minRentPrice != null){
				sql.append("AND b.rentprice >= " + minRentPrice + " ");
			}
		}
		Integer minRentArea = rentAreas.containsKey("minRentArea") && rentAreas.get("minRentArea") instanceof String && !((String) rentAreas.get("minRentArea")).isEmpty()
	    		? Integer.parseInt((String) rentAreas.get("minRentArea"))
	            : null;
		Integer maxRentArea = rentAreas.containsKey("maxRentArea") && rentAreas.get("maxRentArea") instanceof String&& !((String) rentAreas.get("maxRentArea")).isEmpty()
	    		? Integer.parseInt((String) rentAreas.get("maxRentArea"))
	            : null;
		if(minRentArea != null && maxRentArea != null ) {		
			sql.append("AND ra.value >= " + minRentArea + " AND ra.value <= " + maxRentArea + " ");
	    }
		else {
			if(minRentArea == null && maxRentArea != null) {
				sql.append("AND ra.value <= " + maxRentArea + " ");
			}
			else if(maxRentArea == null && minRentArea != null){
				sql.append("AND ra.value >= " + minRentArea + " ");
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
				building.setStaffId(rs.getInt("id"));
				result.add(building);
			}
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return result;
	}
}
