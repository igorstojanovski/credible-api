package com.credible.api.repositories;

import com.credible.api.model.Endorsement;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EndorsementRepository extends CrudRepository<Endorsement, Long> {
}
