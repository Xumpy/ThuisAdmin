package com.xumpy.timesheets.dao.implementations;

import com.xumpy.timesheets.dao.model.EventDaoPojo;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface EventDaoImpl extends CrudRepository<EventDaoPojo, Integer> {

    @Query("from EventDaoPojo where eventDate >= :startDate and eventDate <= :endDate")
    List<EventDaoPojo> selectPeriode(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);
}
