package com.blogger.data.mappers;


import com.blogger.data.dtos.EmployeeDTO;
import com.blogger.jooq.tables.pojos.Employees;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface EmployeeMapper {

    EmployeeDTO toDTO(Employees employee);

    Employees toPOJO(EmployeeDTO employeeDTO);
}
