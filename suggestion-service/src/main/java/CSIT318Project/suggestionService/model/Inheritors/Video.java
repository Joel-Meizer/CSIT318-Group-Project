package CSIT318Project.suggestionService.model.Inheritors;

import CSIT318Project.suggestionService.model.EducationalResource;
import CSIT318Project.suggestionService.Enums.KnowledgeType;

public class Video extends EducationalResource {

    private double duration;

    @Override
    public KnowledgeType getKnowledgeType() {
        return KnowledgeType.Video;
    }
}
