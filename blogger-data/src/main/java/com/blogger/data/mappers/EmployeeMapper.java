package com.blogger.data.mappers;


import com.blogger.data.dtos.EmployeeDTO;
import com.blogger.jooq.tables.pojos.Employees;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;



@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface EmployeeMapper {

    EmployeeMapper INSTANCE = Mappers.getMapper(EmployeeMapper.class);

    Employees toPOJO(EmployeeDTO employeeDTO);

    EmployeeDTO toDTO(Employees employees);
}