package com.blogger.service;


import com.blogger.EmployeeRepository;
import com.blogger.data.dtos.EmployeeDTO;
import com.blogger.data.mappers.EmployeeMapper;
import com.blogger.jooq.tables.pojos.Employees;
import io.reactivex.rxjava3.core.Single;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final EmployeeMapper employeeMapper;

    public List<EmployeeDTO> getAllEmployees() {
        return employeeRepository.findAll()
                .stream()
                .map(employeeMapper::toDTO)
                .collect(Collectors.toList());
    }

//    public EmployeeDTO getEmployeeById(Integer id) {
//        Employees employee = employeeRepository.findById(id);
//        return employeeMapper.toDTO(employee);
//    }

    public Single<EmployeeDTO> getEmployeeById(Integer id) {
        return Single.create(emitter -> {
            try {
                Employees employees = employeeRepository.findById(id);
                if (employees != null) {
                    emitter.onSuccess(employeeMapper.toDTO(employees));
                } else {
                    emitter.onError(new Exception("Employee not found"));
                }
            } catch (Exception e) {
                emitter.onError(e);
            }
        });
    }

    public Single<EmployeeDTO> enrichEmployee(Integer id) {
        return getEmployeeById(id)
                .map(employee -> {
                    employee.setName(employee.getName().toUpperCase());
                    return employee;
                })
                .flatMap(Single::just);
    }

    public EmployeeDTO createEmployee(EmployeeDTO employeeDTO) {
        Employees employee = employeeMapper.toPOJO(employeeDTO);
        employee = employeeRepository.save(employee);
        return employeeMapper.toDTO(employee);
    }


    @Transactional
    public Optional<EmployeeDTO> updateEmployee(Integer id, EmployeeDTO employeeDTO) {
        Optional<Employees> existingEmployeeOptional = Optional.ofNullable(employeeRepository.findById(id));
        return existingEmployeeOptional.map(existingEmployee -> {
            Employees employee = employeeMapper.toPOJO(employeeDTO);
            employee.setId(id);
            return employeeRepository.update(employee)
                    .map(employeeMapper::toDTO);
        }).orElseThrow(() -> new RuntimeException("Employee with ID " + id + " not found"));
    }


    public boolean deleteEmployee(Integer id) {
        return employeeRepository.deleteById(id);
    }
}