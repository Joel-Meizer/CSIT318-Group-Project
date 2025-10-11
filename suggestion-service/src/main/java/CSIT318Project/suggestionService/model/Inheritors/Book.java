package CSIT318Project.suggestionService.model.Inheritors;

import CSIT318Project.suggestionService.model.EducationalResource;
import CSIT318Project.suggestionService.Enums.KnowledgeType;
import jakarta.persistence.Entity;

@Entity
public class Book extends EducationalResource {

    @Override
    public KnowledgeType getKnowledgeType() {
        return KnowledgeType.Book;
    }
}
