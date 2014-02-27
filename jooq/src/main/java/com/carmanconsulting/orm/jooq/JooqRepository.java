package com.carmanconsulting.orm.jooq;

import com.carmanconsulting.orm.model.Person;
import com.carmanconsulting.orm.model.Repository;
import org.jooq.DSLContext;
import org.jooq.Field;
import org.jooq.Record;
import org.jooq.SQLDialect;
import org.jooq.Table;
import org.jooq.impl.DSL;

import javax.sql.DataSource;

import static org.jooq.impl.DSL.*;


public class JooqRepository implements Repository {
//----------------------------------------------------------------------------------------------------------------------
// Fields
//----------------------------------------------------------------------------------------------------------------------

    public static final Table<Record> PERSON = table("person");
    public static final Field<String> ID = field("ID", String.class);
    public static final Field<String> FIRST_NAME = field("FIRSTNAME", String.class);
    public static final Field<String> LAST_NAME = field("LASTNAME", String.class);
    private final DataSource dataSource;

//----------------------------------------------------------------------------------------------------------------------
// Constructors
//----------------------------------------------------------------------------------------------------------------------

    public JooqRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

//----------------------------------------------------------------------------------------------------------------------
// Repository Implementation
//----------------------------------------------------------------------------------------------------------------------

    @Override
    public void delete(Person person) {
        dsl().delete(PERSON).where("id=?", person.getId()).execute();
    }

    @Override
    public Person find(String id) {
        final Record record = dsl().select().from(PERSON).where("id=?", id).fetchOne();
        return map(record);
    }

    @Override
    public void save(Person person) {
        dsl().insertInto(PERSON)
                .set(ID, person.getId())
                .set(FIRST_NAME, person.getFirstName())
                .set(LAST_NAME, person.getLastName())
                .execute();
    }

//----------------------------------------------------------------------------------------------------------------------
// Other Methods
//----------------------------------------------------------------------------------------------------------------------

    protected DSLContext dsl() {
        return DSL.using(dataSource, SQLDialect.HSQLDB);
    }

    private Person map(Record record) {
        if(record == null) {
            return null;
        }
        Person person = new Person();
        person.setId(record.getValue(ID));
        person.setFirstName(record.getValue(FIRST_NAME));
        person.setLastName(record.getValue(LAST_NAME));
        return person;
    }
}
