package com.adonev.waurma.crm.data.repository;

import com.adonev.waurma.crm.data.entity.Order;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {
    @Query(value = "select o from Order o "+
            "where lower(o.date) like lower(concat('%',:searchTerm,'%')) "
            + "or lower(o.ready) like lower(concat('%',:searchTerm,'%'))"
            + "or lower(o.orderId) like lower(concat('%',:searchTerm,'%'))",nativeQuery = true)
    List<Order> search(@Param("searchTerm") String searchTerm);

    @Query(value = "SELECT h FROM Orders h WHERE h.orderId = :order_id",nativeQuery = true)
    Order findByID(@Param("order_id") String login);

}
