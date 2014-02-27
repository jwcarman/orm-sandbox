package com.carmanconsulting.orm.jooq;

import com.carmanconsulting.orm.model.Repository;
import com.carmanconsulting.orm.test.OrmTestCase;

import javax.sql.DataSource;

public class JooqTest extends OrmTestCase {

    @Override
    protected Repository createRepository(DataSource dataSource) {
        return new JooqRepository(dataSource);
    }
}
