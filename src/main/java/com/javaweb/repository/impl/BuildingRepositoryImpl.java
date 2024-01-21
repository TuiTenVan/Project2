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
import java.util.stream.Collectors;

import com.javaweb.Utils.NumberUtil;
import com.javaweb.Utils.StringUtil;
import org.springframework.stereotype.Repository;

import com.javaweb.repository.BuildingRepository;
import com.javaweb.repository.entity.BuildingEntity;

@Repository
public class BuildingRepositoryImpl implements BuildingRepository {
	static String URL = "jdbc:mysql://localhost:3306/estatebasic";
	static String USER = "root";
	static String PASS = "Nvv@02022003";

	private void joinTable(Map<String, Object> conditions, List<String> typeCode, StringBuilder sql) {

		String staffId = (String) conditions.get("staffId");
		if(StringUtil.checkString(staffId)) {
			sql.append(" JOIN assignmentbuilding ass ON b.id = ass.buildingid ");
			sql.append(" JOIN user u ON ass.staffid = u.id ");
		}

		if(typeCode != null && typeCode.size() > 0){
			sql.append(" JOIN buildingrenttype bt ON b.id = bt.buildingid ");
			sql.append(" JOIN renttype rt ON bt.renttypeid = rt.id ");
		}
	}
	public static void queryNormal(StringBuilder sql, Map<String, Object> conditions){
		for(Map.Entry<String, Object> item : conditions.entrySet()){

			if(!item.getKey().equals("staffId") && !item.getKey().equals("typeCode")
					&& !item.getKey().startsWith("rentPrice") && !item.getKey().startsWith("area")){

				String value = item.getValue().toString();
				if(StringUtil.checkString(value)){

					if(NumberUtil.isNumber(value)){
						sql.append("AND b." + item.getKey() + " = " + value + " ");
					}

					else{
						sql.append("AND b." + item.getKey() + " LIKE '%" + item.getValue() + "%' ");
					}
				}
			}
		}
	}
	public static void querySpecial(StringBuilder sql, Map<String, Object> conditions, List<String> typeCode){
		String staffId = (String) conditions.get("staffId");
		if(StringUtil.checkString(staffId)){
			sql.append("AND u.id" + " = " + staffId + " ");
		}

		String areaTo = (String)(conditions.get("areaTo"));
		String areaFrom = (String)(conditions.get("areaFrom"));
		if(StringUtil.checkString(areaTo) || StringUtil.checkString(areaFrom)){
			sql.append(" AND EXISTS (SELECT * FROM rentarea r WHERE b.id = r.buildingid ");
			if(StringUtil.checkString(areaFrom)){
				sql.append("AND r.value >= " + areaFrom + " ");
			}
			if(StringUtil.checkString(areaTo)){
				sql.append("AND r.value <= " + areaTo + " ");
			}
			sql.append(") ");
		}

		String rentPriceTo = (String)(conditions.get("rentPriceTo"));
		String rentPriceFrom = (String)(conditions.get("rentPriceFrom"));
		if(StringUtil.checkString(rentPriceTo) || StringUtil.checkString(rentPriceFrom)){
			if(StringUtil.checkString(rentPriceFrom)){
				sql.append("AND b.rentprice >= " + rentPriceFrom + " ");
			}
			if(StringUtil.checkString(rentPriceTo)){
				sql.append("AND b.rentprice <= " + rentPriceTo + " ");
			}
		}

		if(typeCode != null && typeCode.size() > 0){
//			List<String> code = new ArrayList<>();
//			for(String s : typeCode) {
//				code.add("'" + s + "'");
//			}
//			sql.append("AND rt.code IN(" + String.join(", ", code) + ")");
			sql.append(" AND (");
			String tmp = typeCode.stream().map(it-> "rt.code = " + "'" + it + "'").collect(Collectors.joining(" OR "));
			sql.append(tmp + " ) ");
		}
	}
	@Override
	public List<BuildingEntity> findAll(Map<String, Object> conditions, List<String> typeCode) {
	    StringBuilder sql = new StringBuilder("SELECT b.* FROM building b ");
		joinTable(conditions, typeCode, sql);
	    sql.append("WHERE 1 = 1 ");
	    queryNormal(sql, conditions);
		querySpecial(sql, conditions, typeCode);
		sql.append(" GROUP BY b.id");
	    return executeQuery(sql.toString());
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
				building.setDistrictId(rs.getString("districtid"));
				building.setId(rs.getInt("id"));
				result.add(building);
			}
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		return result;
	}
}
