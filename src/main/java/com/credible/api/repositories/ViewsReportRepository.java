package com.credible.api.repositories;

import com.credible.api.model.ViewsReport;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ViewsReportRepository extends CrudRepository<ViewsReport, Long> {

    @Query("SELECT vr FROM ViewsReport vr WHERE vr.owner.id = 1 ")
    ViewsReport findByOwnerId(Long id);
}
