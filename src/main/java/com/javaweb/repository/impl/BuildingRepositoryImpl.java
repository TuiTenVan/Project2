package com.javaweb.repository.impl;

import java.lang.reflect.Field;
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

import com.javaweb.Utils.ConnectionJDBC;
import com.javaweb.Utils.NumberUtil;
import com.javaweb.Utils.StringUtil;
import com.javaweb.builder.BuildingSearchBuilder;
import org.springframework.stereotype.Repository;

import com.javaweb.repository.BuildingRepository;
import com.javaweb.repository.entity.BuildingEntity;

@Repository
public class BuildingRepositoryImpl implements BuildingRepository {

	private void joinTable(BuildingSearchBuilder buildingSearchBuildere, StringBuilder sql) {

		Integer staffId = buildingSearchBuildere.getStaffId();
		if(staffId != null) {
			sql.append(" JOIN assignmentbuilding ass ON b.id = ass.buildingid ");
			sql.append(" JOIN user u ON ass.staffid = u.id ");
		}
		List<String> typeCode = buildingSearchBuildere.getTypeCode();
		if(typeCode != null && typeCode.size() > 0){
			sql.append(" JOIN buildingrenttype bt ON b.id = bt.buildingid ");
			sql.append(" JOIN renttype rt ON bt.renttypeid = rt.id ");
		}
	}
	public static void queryNormal(StringBuilder sql, BuildingSearchBuilder buildingSearchBuilder){
		try{
			Field[] fields = BuildingSearchBuilder.class.getDeclaredFields();
			for(Field item : fields){
				item.setAccessible(true);
				String fieldName = item.getName();
				if(!fieldName.equals("staffId") && !fieldName.equals("typeCode")
					&& !fieldName.startsWith("rentPrice") && !fieldName.startsWith("area")) {
					Object value = item.get(buildingSearchBuilder);
					if(value != null && !value.toString().isEmpty()) {
						if (item.getType().getName().equals("java.lang.Integer")) {
							sql.append("AND b." + fieldName + " = " + value + " ");
						} else {
							sql.append("AND b." + fieldName + " LIKE '%" + value + "%' ");
						}
					}
				}
			}
		}
		catch (Exception e){
			e.printStackTrace();
		}
	}
	public static void querySpecial(StringBuilder sql, BuildingSearchBuilder buildingSearchBuilder){
		Integer staffId = buildingSearchBuilder.getStaffId();
		if(staffId != null){
			sql.append("AND u.id" + " = " + staffId + " ");
		}

		Integer areaTo = buildingSearchBuilder.getAreaTo();
		Integer areaFrom = buildingSearchBuilder.getAreaFrom();
		if(areaFrom != null || areaTo != null){
			sql.append(" AND EXISTS (SELECT * FROM rentarea r WHERE b.id = r.buildingid ");
			if(areaFrom != null){
				sql.append("AND r.value >= " + areaFrom + " ");
			}
			if(areaTo != null){
				sql.append("AND r.value <= " + areaTo + " ");
			}
			sql.append(") ");
		}

		Integer rentPriceTo = buildingSearchBuilder.getRentPriceTo();
		Integer rentPriceFrom = buildingSearchBuilder.getRentPriceFrom();
		if(rentPriceFrom != null || rentPriceTo != null){
			if(rentPriceFrom != null){
				sql.append("AND b.rentprice >= " + rentPriceFrom + " ");
			}
			if(rentPriceTo != null){
				sql.append("AND b.rentprice <= " + rentPriceTo + " ");
			}
		}
		List<String> typeCode = buildingSearchBuilder.getTypeCode();
		if(typeCode != null && typeCode.size() > 0){
			sql.append(" AND (");
			String tmp = typeCode.stream().map(it-> "rt.code = " + "'" + it + "'").collect(Collectors.joining(" OR "));
			sql.append(tmp + " ) ");
		}
	}
	@Override
	public List<BuildingEntity> findAll( BuildingSearchBuilder buildingSearchBuilder) {
	    StringBuilder sql = new StringBuilder("SELECT b.* FROM building b ");
		joinTable(buildingSearchBuilder, sql);
	    sql.append("WHERE 1 = 1 ");
	    queryNormal(sql, buildingSearchBuilder);
		querySpecial(sql, buildingSearchBuilder);
		sql.append(" GROUP BY b.id");
	    return executeQuery(sql.toString());
	}

	public List<BuildingEntity> executeQuery(String sql){
		List<BuildingEntity> result = new ArrayList<>();

		try(Connection connect = ConnectionJDBC.getConnection();
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
