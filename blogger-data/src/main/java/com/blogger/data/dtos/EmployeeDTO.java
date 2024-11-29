package com.blogger.data.dtos;

import lombok.*;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class EmployeeDTO implements Serializable {
    private Integer id;
    private String name;
    private String position;
    private Integer departmentId;
}
