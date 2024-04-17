package com.kt.edu.customerinfo.query.repository;

import com.kt.edu.customerinfo.query.domain.EmployeeEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeQryRepository extends CrudRepository<EmployeeEntity, Long> {

}
