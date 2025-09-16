package CSIT318Project.guideService.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import CSIT318Project.guideService.model.Guide;

public interface GuideRepository extends JpaRepository<Guide, UUID> {
}
