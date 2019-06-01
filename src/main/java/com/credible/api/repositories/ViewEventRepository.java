package com.credible.api.repositories;

import com.credible.api.model.ViewEvent;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ViewEventRepository extends CrudRepository<ViewEvent, Long> {
}
