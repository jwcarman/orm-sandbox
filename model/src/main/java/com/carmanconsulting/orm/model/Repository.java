package com.carmanconsulting.orm.model;

public interface Repository {
//----------------------------------------------------------------------------------------------------------------------
// Other Methods
//----------------------------------------------------------------------------------------------------------------------

    void save(Person person);
    void delete(Person person);
    Person find(String id);
}
