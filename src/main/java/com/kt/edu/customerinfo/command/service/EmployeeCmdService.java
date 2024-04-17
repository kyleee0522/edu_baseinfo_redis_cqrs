package com.kt.edu.customerinfo.command.service;

import com.kt.edu.common.exception.ResourceNotFoundException;
import com.kt.edu.customerinfo.command.repository.EmployeeCmdRepository;
import com.kt.edu.common.annotation.Ktedu;
import com.kt.edu.customerinfo.command.domain.EmployeeEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class EmployeeCmdService {

    @Autowired
    private EmployeeCmdRepository employeeRepository;

    // spring profile
    @Value("${spring.profiles.active}")
    private String activeProfile;

    @Transactional
    public EmployeeEntity create(EmployeeEntity employeeEntity) {
        log.info("Request to create Employee : " +  employeeEntity);
        
        log.info("Active Springboot Profile : " + activeProfile );

        if (activeProfile.equals("prd")){ // maria, mysql
            employeeEntity.setId(employeeRepository.retvNextVal());
        } else { // h2 db
            employeeEntity.setId(employeeRepository.retvNextVal_H2());;
        }
        employeeEntity.setNew(true);
        return this.employeeRepository.save(employeeEntity);
    }

    @Ktedu
    public EmployeeEntity update(Long id,EmployeeEntity employeeEntity) {
        log.info("Request to update Employee : " +  employeeEntity);
        EmployeeEntity employee = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not exist with id :" + id));

        employee.setId(id);
        employee.setEmpName(employeeEntity.getEmpName());
        employee.setEmpDeptName(employeeEntity.getEmpDeptName());
        employee.setEmpTelNo(employeeEntity.getEmpTelNo());
        employee.setEmpMail(employeeEntity.getEmpMail());
        employee.setNew(false);
        return this.employeeRepository.save(employee);
    }

    public EmployeeEntity delete(Long id) {
        log.info("Request to delete Employee id : " +  id);
        EmployeeEntity employeeEntity = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Request not exist with id" +id));
        employeeRepository.delete(employeeEntity);
        return  employeeEntity;
    }
}
