package proektna.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import proektna.demo.model.Bundle;

@Repository
public interface BundleRepository extends JpaRepository<Bundle,Long> {
}
