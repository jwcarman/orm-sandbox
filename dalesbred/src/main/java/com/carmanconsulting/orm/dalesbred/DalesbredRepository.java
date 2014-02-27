package com.carmanconsulting.orm.dalesbred;

import com.carmanconsulting.orm.model.Person;
import com.carmanconsulting.orm.model.Repository;
import fi.evident.dalesbred.Database;

public class DalesbredRepository implements Repository {
//----------------------------------------------------------------------------------------------------------------------
// Fields
//----------------------------------------------------------------------------------------------------------------------

    private final Database database;

//----------------------------------------------------------------------------------------------------------------------
// Constructors
//----------------------------------------------------------------------------------------------------------------------

    public DalesbredRepository(Database database) {
        this.database = database;
    }

//----------------------------------------------------------------------------------------------------------------------
// Repository Implementation
//----------------------------------------------------------------------------------------------------------------------


    @Override
    public void save(Person person) {
        database.update("insert into person (id, firstName, lastName) values (?,?,?)", person.getId(), person.getFirstName(), person.getLastName());
    }

    @Override
    public void delete(Person person) {
        database.update("delete from person where id = ?", person.getId());
    }

    @Override
    public Person find(String id) {
        return database.findUnique(Person.class, "select * from person where id = ?", id);
    }
}
