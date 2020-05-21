package com.earlyobject.ws.io.repositories;

import com.earlyobject.ws.entity.Role;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Repository
public interface RoleRepository extends CrudRepository<Role, Long> {

    @Transactional(readOnly = true)
    Role findByName(String name);
}
