package com.kt.edu.customerinfo.query.service;

import com.kt.edu.customerinfo.query.repository.EmployeeQryRepository;
import com.kt.edu.customerinfo.query.domain.EmployeeEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class EmployeeQryService {

    private final EmployeeQryRepository employeeQryRepository;

    //@Cacheable("employees")
    public List<EmployeeEntity> getEmployeeList()  {
        log.info("Request to get all Employees");

        List<EmployeeEntity> employeeList = new ArrayList<>();
        employeeQryRepository.findAll().forEach(employeeList::add);
        return employeeList;
    }

    // 플러시를 생략해서 dirty checking등을 하지 않으므로 약간의 성능 향상
    @Cacheable("employee")
    @Transactional(readOnly = true)
    public EmployeeEntity getEmployee(Long id) {
        log.info("Request to get Employee : {}", id);
        return employeeQryRepository.findById(id).get();
    }

}
