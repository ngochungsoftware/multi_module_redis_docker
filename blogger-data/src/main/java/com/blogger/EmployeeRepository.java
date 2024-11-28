package com.blogger;



import com.blogger.jooq.Tables;
import com.blogger.jooq.tables.pojos.Employees;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jooq.DSLContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
@Slf4j
public class EmployeeRepository {

    private static final Logger logger = LoggerFactory.getLogger(EmployeeRepository.class);

    private final DSLContext dslContext;

    public List<Employees> findAll() {
        return dslContext.selectFrom(Tables.EMPLOYEES)
                .fetch()
                .into(Employees.class);
    }

    public Employees findById(Integer id) {
        return dslContext.selectFrom(Tables.EMPLOYEES)
                .where(Tables.EMPLOYEES.ID.eq(id))
                .fetchOne()
                .into(Employees.class);

    }

    public Employees save(Employees employee) {
        return dslContext.insertInto(Tables.EMPLOYEES)
                .set(dslContext.newRecord(Tables.EMPLOYEES, employee))
                .returning()
                .fetchOne()
                .into(Employees.class);
    }

    public Optional<Employees> update(Employees employee) {
        try {
            int affectedRows = dslContext.update(Tables.EMPLOYEES)
                    .set(dslContext.newRecord(Tables.EMPLOYEES, employee))
                    .where(Tables.EMPLOYEES.ID.eq(employee.getId()))
                    .execute();
            if (affectedRows == 0) {
                return Optional.empty();
            }
            return Optional.of(dslContext.selectFrom(Tables.EMPLOYEES)
                    .where(Tables.EMPLOYEES.ID.eq(employee.getId()))
                    .fetchOne()
                    .into(Employees.class));
        } catch (Exception e) {
            logger.error("Error updating employee with ID {}: {}", employee.getId(), e.getMessage(), e);
            throw new RuntimeException("Error updating employee", e);
        }
    }


    public boolean deleteById(Integer id) {
        return dslContext.deleteFrom(Tables.EMPLOYEES)
                .where(Tables.EMPLOYEES.ID.eq(id))
                .execute() > 0;
    }
}