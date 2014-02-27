package com.carmanconsulting.orm.dalesbred;

import com.carmanconsulting.orm.model.Repository;
import com.carmanconsulting.orm.test.OrmTestCase;
import fi.evident.dalesbred.Database;

import javax.sql.DataSource;

public class DalesbredTest extends OrmTestCase {
//----------------------------------------------------------------------------------------------------------------------
// Other Methods
//----------------------------------------------------------------------------------------------------------------------

    @Override
    protected Repository createRepository(DataSource dataSource) {
        return new DalesbredRepository(Database.forDataSource(dataSource));
    }
}
