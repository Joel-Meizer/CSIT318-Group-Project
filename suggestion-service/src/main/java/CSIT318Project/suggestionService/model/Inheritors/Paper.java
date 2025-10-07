package CSIT318Project.suggestionService.model.Inheritors;

import CSIT318Project.suggestionService.model.EducationalResource;
import CSIT318Project.suggestionService.Enums.KnowledgeType;

public class Paper extends EducationalResource {

    @Override
    public KnowledgeType getKnowledgeType() {
        return KnowledgeType.Paper;
    }
}
