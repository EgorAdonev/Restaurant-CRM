package com.adonev.waurma.crm.data.repository;

import com.adonev.waurma.crm.data.entity.Customer;
import com.adonev.waurma.crm.data.entity.FoodItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FoodItemRepository extends JpaRepository<FoodItem, Integer> {
    @Query(value = "select o from FoodItem o "+
            "where lower(o.foodItemId) like lower(concat('%',:searchTerm,'%')) "
            + "or lower(o.foodName) like lower(concat('%',:searchTerm,'%'))"
            + "or lower(o.foodPrice) like lower(concat('%',:searchTerm,'%'))",nativeQuery = true)
    List<FoodItem> search(@Param("searchTerm") String searchTerm);
}
