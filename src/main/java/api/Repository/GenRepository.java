package api.Repository;

import api.Model.Gen;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GenRepository extends JpaRepository<Gen, String> {
}
