package com.ra.repository;

import com.ra.model.entity.EOrderStatus;
import com.ra.model.entity.Orders;
import com.ra.model.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Orders, Long> {
    @Query("select o from Orders o where o.users.id = :userId")
    List<Orders> findAllByUserId(Long userId);
    @Query("SELECT o from Orders o WHERE o.receiveName like %?1% ")
    List<Orders> searchOrdersByReceiveName (String keyword);
    @Query("select o from Orders o where o.users.id = :userId and o.orderNumber = :serial")
    Orders findByUserIdAndSerial(Long userId, String serial);
    @Query("select o from Orders o where o.users.id = :userId and o.status = :status")
    List<Orders> findByUserIdAndStatus(Long userId, EOrderStatus status);
    @Query("select o from Orders o where o.users.id = :userId and o.status = :status and o.id = :orderId")
    Orders findByIdAndStatus(Long userId, Long orderId, EOrderStatus status);
}
