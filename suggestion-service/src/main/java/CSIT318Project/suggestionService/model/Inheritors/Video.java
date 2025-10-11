package CSIT318Project.suggestionService.model.Inheritors;

import CSIT318Project.suggestionService.model.EducationalResource;
import CSIT318Project.suggestionService.Enums.KnowledgeType;
import jakarta.persistence.Entity;

@Entity
public class Video extends EducationalResource {

    private double duration;

    @Override
    public KnowledgeType getKnowledgeType() {
        return KnowledgeType.Video;
    }
}
