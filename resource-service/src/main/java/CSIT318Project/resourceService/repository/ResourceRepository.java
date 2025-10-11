package CSIT318Project.resourceService.repository;

import CSIT318Project.resourceService.model.EducationalResource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface ResourceRepository extends JpaRepository<EducationalResource, UUID>, JpaSpecificationExecutor<EducationalResource> {
    @Query("SELECT DISTINCT e.genre FROM EducationalResource e")
    List<String> findDistinctGenres();
}