package repository;

import entities.AccountPlan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountPlanRepository extends JpaRepository<AccountPlan, Long> {
}
