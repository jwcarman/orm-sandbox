package com.carmanconsulting.orm.test;

import com.carmanconsulting.orm.model.Person;
import com.carmanconsulting.orm.model.Repository;
import org.apache.commons.dbcp.BasicDataSource;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import javax.sql.DataSource;
import java.util.Map;

import static org.junit.Assert.*;

@ContextConfiguration(locations = {"classpath:/OrmTestCase.xml"})
public abstract class OrmTestCase extends AbstractJUnit4SpringContextTests {
//----------------------------------------------------------------------------------------------------------------------
// Fields
//----------------------------------------------------------------------------------------------------------------------

    private Repository repository;

    @Autowired
    private BasicDataSource dataSource;

//----------------------------------------------------------------------------------------------------------------------
// Abstract Methods
//----------------------------------------------------------------------------------------------------------------------

    protected abstract Repository createRepository(DataSource dataSource);

//----------------------------------------------------------------------------------------------------------------------
// Other Methods
//----------------------------------------------------------------------------------------------------------------------

    protected void assertDatabaseValueEquals(Person p) {
        Map<String,Object> row = getRow(p.getId());
        assertEquals(p.getId(), row.get("id"));
        assertEquals(p.getFirstName(), row.get("firstName"));
        assertEquals(p.getLastName(), row.get("lastName"));
    }

    protected Map<String, Object> getRow(String id) {
        return new JdbcTemplate(dataSource).queryForMap(String.format("select * from person where id = '%s'", id));
    }

    protected boolean rowExists(String id) {
        return new JdbcTemplate(dataSource).queryForObject(String.format("select count(id) from person where id = '%s'", id), Integer.class) == 1;
    }

    @Before
    public void initializeRepository() {
        this.repository = createRepository(dataSource);
    }

    @Test
    public void testSave() {
        Person p = new Person();
        p.setFirstName("Joe");
        p.setLastName("Shmoe");
        repository.save(p);
        assertDatabaseValueEquals(p);
    }

    @Test
    public void testDelete() {
        assertTrue(rowExists("DELETEME"));

        Person p = new Person();
        p.setId("DELETEME");
        repository.delete(p);
        assertFalse(rowExists("DELETEME"));
    }

    @Test
    public void testFind() {
        Person p = repository.find("FINDME");
        assertNotNull(p);
        assertEquals("Find", p.getFirstName());
        assertEquals("Me", p.getLastName());
    }
}
