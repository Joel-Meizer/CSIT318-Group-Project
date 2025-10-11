package CSIT318Project.suggestionService.agentic;

import CSIT318Project.suggestionService.Enums.KnowledgeLevel;
import CSIT318Project.suggestionService.Enums.KnowledgeType;
import CSIT318Project.suggestionService.model.EducationalResource;
import CSIT318Project.suggestionService.model.SuggestionGenerateModel;
import CSIT318Project.suggestionService.model.UserPreferenceModel;
import dev.langchain4j.service.Result;
import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.V;
import dev.langchain4j.service.spring.AiService;

import java.util.List;

@AiService
public interface SuggestionAgent {
    @SystemMessage("""
    You are an expert academic research assistant. Your mission is to generate a SuggestionGenerateModel based on a user's learning preferences.
    
    Your instructions are:
    1. **Analyze:** Carefully review the user's preferences provided in the user message.
    2. **Extract:** Identify the most relevant genre, knowledge level, and knowledge type that reflect the user's interests. Only include a value if you are confident it aligns with the user's preferences.
    3. **Validate:** You must only use values that exist in the provided validGenres, validLevels, and validTypes lists. Do not invent or infer values that are not present.
    4. **Populate:** Return a SuggestionGenerateModel with only genre, knowledgeLevel, and knowledgeType populated. Leave all other fields null.
    5. **Match Resources:** Ensure that the selected values will match at least one educational resource from the availableResources list.
    6. **Summarise:** Provide a concise summary explaining how the selected values reflect the user's preferences and align with available resources.
    """)
    Result<SuggestionGenerateModel> generateFromPreferences(
            @UserMessage String context,
            @V("userPreferences") UserPreferenceModel userPreferences,
            @V("availableresources") List<EducationalResource> availableresources,
            @V("validGenres") List<String> validGenres,
            @V("validLevels") List<KnowledgeLevel> validLevels,
            @V("validTypes") List<KnowledgeType> validTypes
    );

    @SystemMessage("""
    You are an expert academic research assistant. Your mission is to generate a SuggestionGenerateModel based on a user's past educational resource orders.
    
    Your instructions are:
    1. **Analyze:** Carefully review the user's previously ordered resources provided in the user message.
    2. **Extract:** Identify the most relevant genre, knowledge level, and knowledge type that reflect the user's learning history. Only include a value if you are confident it reflects their interests.
    3. **Validate:** You must only use values that exist in the provided validGenres, validLevels, and validTypes lists. Do not invent or infer values that are not present.
    4. **Populate:** Return a SuggestionGenerateModel with only genre, knowledgeLevel, and knowledgeType populated. Leave all other fields null.
    5. **Match Resources:** Ensure that the selected values will match at least one educational resource from the availableResources list.
    6. **Summarise:** Provide a concise summary explaining how the selected values reflect the user's learning history and align with available resources.
    """)
    Result<SuggestionGenerateModel> generateFromOrderHistory(
            @UserMessage String context,
            @V("previouslyOrdered") List<EducationalResource> previouslyOrdered,
            @V("availableresources") List<EducationalResource> availableresources,
            @V("validGenres") List<String> validGenres,
            @V("validLevels") List<KnowledgeLevel> validLevels,
            @V("validTypes") List<KnowledgeType> validTypes
    );
}