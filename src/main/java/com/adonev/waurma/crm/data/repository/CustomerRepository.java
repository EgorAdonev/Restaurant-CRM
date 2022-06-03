package com.adonev.waurma.crm.data.repository;

import com.adonev.waurma.crm.data.entity.Customer;

import com.adonev.waurma.crm.data.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    @Query(value = "select o from Customer o "+
            "where lower(o.customerId) like lower(concat('%',:searchTerm,'%')) "
            + "or lower(o.name) like lower(concat('%',:searchTerm,'%'))"
            + "or lower(o.email) like lower(concat('%',:searchTerm,'%'))",nativeQuery = true)
    List<Customer> search(@Param("searchTerm") String searchTerm);

    @Query(value = "SELECT h FROM Customer h WHERE h.customerId = :customer_id",nativeQuery = true)
    Customer findByID(@Param("customer_id") Integer login);

//    List<Object> findById(Integer customerId);
}
