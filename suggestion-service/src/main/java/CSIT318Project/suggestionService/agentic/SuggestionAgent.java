package CSIT318Project.suggestionService.agentic;

import CSIT318Project.suggestionService.Enums.KnowledgeLevel;
import CSIT318Project.suggestionService.Enums.KnowledgeType;
import CSIT318Project.suggestionService.model.EducationalResource;
import CSIT318Project.suggestionService.model.SuggestionGenerateModel;
import CSIT318Project.suggestionService.model.UserPreferenceModel;
import CSIT318Project.suggestionService.service.dto.SuggestedResourcesResponseDTO;
import dev.langchain4j.service.Result;
import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.V;
import dev.langchain4j.service.spring.AiService;

import java.util.List;

@AiService
public interface SuggestionAgent {
    @SystemMessage("""
    You are an expert academic research assistant. Your mission is to curate a personalized list of educational resources based on a user's learning preferences.
    
    Your instructions are:
    1. **Analyze:** Carefully review the user's preferences provided in the user message.
    2. **Match:** Identify which educational resources from the availableResources list best align with the user's interests, preferred genres, and knowledge level.
    3. **Filter:** Only include resources that clearly reflect the user's preferences. You may use multiple genres or levels if they are relevant, but avoid guessing or inferring values that are not explicitly supported by the resource data.
    4. **Validate:** You must not invent, fabricate, or modify any educational resources. Only return resources that exactly match the structure of entries from the availableResources list. 
    5. **Curate:** Return a new SuggestedResourcesResponseDTO which has an attribute of resources which is a list of EducationalResource objects from the availableResources list that would be most helpful for the user's learning journey.
    6. **Summarise:** Provide a concise explanation of why these resources were selected and how they align with the user's preferences.
    """)
    Result<SuggestedResourcesResponseDTO> generateFromPreferences(
            @UserMessage String context,
            @V("availableResources") List<EducationalResource> availableResources
    );

    @SystemMessage("""
    You are an expert academic research assistant. Your mission is to curate a personalized list of educational resources based on a user's past educational resource orders.
    
    Your instructions are:
    1. **Analyze:** Carefully review the user's previously ordered educational resources provided in the user message. These represent the user's learning history and preferences.
    2. **Match:** Identify which educational resources from the availableResources list best align with the user's demonstrated interests, preferred genres, and knowledge level based on their order history (use the order history provided in the previouslyOrdered variable and userMessage context).
    3. **Filter:** Only include resources that clearly reflect the user's learning trajectory. You may use multiple genres or levels if they are relevant, but avoid guessing or inferring values that are not explicitly supported by the resource data.
    4. **Validate:** You must not invent, fabricate, or modify any educational resources. Only return resources that exactly match the structure of entries from the availableResources list. 
    5. **Curate:** Return a new SuggestedResourcesResponseDTO which has an attribute of resources — a list of EducationalResource objects from the availableResources list that would be most helpful for the user's continued learning journey.
    6. **Summarise:** Provide a concise explanation of why these resources were selected and how they align with the user's learning history.
    """)
    Result<SuggestedResourcesResponseDTO> generateFromOrderHistory(
            @UserMessage String context,
            @V("previouslyOrdered") List<EducationalResource> previouslyOrdered,
            @V("availableresources") List<EducationalResource> availableresources

    );
}