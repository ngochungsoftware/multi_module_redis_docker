package com.blogger.data.dtos;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class EmployeeDTO {
    private Integer id;
    private String name;
    private String position;
    private Integer departmentId;
}
