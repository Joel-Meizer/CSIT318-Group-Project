package CSIT318Project.resourceService.model.Inheritors;

import CSIT318Project.resourceService.Enums.KnowledgeType;
import CSIT318Project.resourceService.model.EducationalResource;
import jakarta.persistence.Entity;

@Entity
public class Book extends EducationalResource {

    @Override
    public KnowledgeType getKnowledgeType() {
        return KnowledgeType.Book;
    }
}
