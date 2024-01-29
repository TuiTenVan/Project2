package com.javaweb.repository.impl;

import com.javaweb.builder.BuildingSearchBuilder;
import com.javaweb.repository.BuildingRepository;
import com.javaweb.repository.entity.BuildingEntity;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.lang.reflect.Field;
import java.util.List;
import java.util.stream.Collectors;

@Repository
@Primary
public class JDBCBuildingRepositoryImpl implements BuildingRepository {
	
	@PersistenceContext
	private EntityManager entityManager;

	private void joinTable(BuildingSearchBuilder buildingSearchBuilder, StringBuilder sql) {

		Integer staffId = buildingSearchBuilder.getStaffId();
		if(staffId != null) {
			sql.append(" JOIN assignmentbuilding ass ON b.id = ass.buildingid ");
			sql.append(" JOIN user u ON ass.staffid = u.id ");
		}
		List<String> typeCode = buildingSearchBuilder.getTypeCode();
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
		Query query = entityManager.createNativeQuery(sql.toString(), BuildingEntity.class);
	    return query.getResultList();
	}
}
