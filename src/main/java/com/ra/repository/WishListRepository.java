package com.ra.repository;

import com.ra.model.entity.WishList;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Transactional
public interface WishListRepository extends JpaRepository<WishList,Long> {
    @Query("select w from WishList w where w.users.id = :userId")
    List<WishList> getAllByUserId(Long userId);
    @Modifying
    @Query("delete from WishList w where w.id = :wishListId and w.users.id = :userId")
    void deleteByIdAndUserId(Long wishListId, Long userId);
}
