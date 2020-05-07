package com.earlyobject.ws.io.repositories;

import com.earlyobject.ws.entity.Authority;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorityRepository extends CrudRepository<Authority, Long> {

    Authority findByName(String name);
}
