package com.blogger.service.impl;

import com.blogger.EmployeeRepository;
import com.blogger.data.dtos.EmployeeDTO;
import com.blogger.data.mappers.EmployeeMapper;
import com.blogger.jooq.tables.pojos.Employees;
import com.blogger.service.BaseRedisService;
import io.reactivex.rxjava3.core.Single;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EmployeeServiceImpl extends BaseRedisServiceImpl implements BaseRedisService {

    private static final String EMPLOYEE_CACHE_KEY = "employees"; // Key chính cho cache Redis.

    private final EmployeeRepository employeeRepository;
    private final EmployeeMapper employeeMapper;

    public EmployeeServiceImpl(RedisTemplate<String, Object> redisTemplate,
                               HashOperations<String, String, Object> hashOperations,
                               EmployeeRepository employeeRepository, EmployeeMapper employeeMapper) {
        super(redisTemplate, hashOperations);
        this.employeeRepository = employeeRepository;
        this.employeeMapper = employeeMapper;
    }

    public List<EmployeeDTO> getAllEmployees() {
        List<Object> cachedEmployees = hashGetByFieldPrefix(EMPLOYEE_CACHE_KEY, "");
        if (!cachedEmployees.isEmpty()) {
            return cachedEmployees.stream()
                    .map(EmployeeDTO.class::cast)
                    .collect(Collectors.toList());
        }
        List<EmployeeDTO> employees = employeeRepository.findAll()
                .stream()
                .map(employeeMapper::toDTO)
                .collect(Collectors.toList());

        for (EmployeeDTO employee : employees) {
            hashSet(EMPLOYEE_CACHE_KEY, employee.getId().toString(), employee);
        }

        return employees;
    }
    public Single<EmployeeDTO> getEmployeeById(Integer id) {
        return Single.create(emitter -> {
            try {
                Object cachedEmployee = hashGet(EMPLOYEE_CACHE_KEY, id.toString());
                if (cachedEmployee != null) {
                    emitter.onSuccess((EmployeeDTO) cachedEmployee);
                    return;
                }
                Employees employee = employeeRepository.findById(id);
                if (employee != null) {
                    EmployeeDTO employeeDTO = employeeMapper.toDTO(employee);
                    hashSet(EMPLOYEE_CACHE_KEY, id.toString(), employeeDTO); // Lưu vào Redis
                    emitter.onSuccess(employeeDTO);
                } else {
                    emitter.onError(new Exception("Employee not found"));
                }
            } catch (Exception e) {
                emitter.onError(e);
            }
        });
    }
    public EmployeeDTO createEmployee(EmployeeDTO employeeDTO) {
        Employees employee = employeeMapper.toPOJO(employeeDTO);
        System.out.println(employee);
        employee = employeeRepository.save(employee);
        EmployeeDTO createdEmployee = employeeMapper.toDTO(employee);
        System.out.println("Attempting to save to Redis with key: " + createdEmployee.getId());
        hashSet(EMPLOYEE_CACHE_KEY, createdEmployee.getId().toString(), createdEmployee);
        System.out.println("Saved to Redis successfully");
        return createdEmployee;
    }

    @Transactional
    public Optional<EmployeeDTO> updateEmployee(Integer id, EmployeeDTO employeeDTO) {
        Optional<Employees> existingEmployeeOptional = Optional.ofNullable(employeeRepository.findById(id));
        if (existingEmployeeOptional.isEmpty()) {
            throw new RuntimeException("Employee with ID " + id + " not found");
        }
        Employees employeeToUpdate = employeeMapper.toPOJO(employeeDTO);
        employeeToUpdate.setId(id);
        Optional<Employees> updatedEmployeeOptional = employeeRepository.update(employeeToUpdate);
        if (updatedEmployeeOptional.isEmpty()) {
            throw new RuntimeException("Failed to update employee with ID " + id);
        }
        EmployeeDTO updatedDTO = employeeMapper.toDTO(updatedEmployeeOptional.get());
        hashSet(EMPLOYEE_CACHE_KEY, id.toString(), updatedDTO);
        return Optional.of(updatedDTO);
    }



    public boolean deleteEmployee(Integer id) {
        delete(EMPLOYEE_CACHE_KEY, id.toString());
        return employeeRepository.deleteById(id);
    }
}
