package com.kt.edu.customerinfo.command.repository;

import com.kt.edu.customerinfo.command.repository.sql.QueryEmployeeSqls;
import com.kt.edu.customerinfo.command.domain.EmployeeEntity;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeCmdRepository extends CrudRepository<EmployeeEntity, Long> {

    @Query(QueryEmployeeSqls.RETV_NEXT_VAL)
    Long retvNextVal();

    @Query(QueryEmployeeSqls.RETV_NEXT_VAL_H2)
    Long retvNextVal_H2();
    
}
