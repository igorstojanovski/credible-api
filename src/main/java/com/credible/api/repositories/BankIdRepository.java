package com.credible.api.repositories;

import com.credible.configuration.model.BankId;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BankIdRepository extends CrudRepository<BankId, Long> {
    BankId findBankIdByBankId(String bankId);
}
