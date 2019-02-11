package com.yilong.bank.repository;

import com.yilong.bank.entity.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PageRepository extends JpaRepository<Page,Long> {

    @Query("select p from Page p where p.currentMonth=:currentMonth")
    Page findByCurrentMonth(@Param("currentMonth") String currentMonth);
}
