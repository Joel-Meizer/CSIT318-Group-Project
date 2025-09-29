package CSIT318Project.guideService.service;

import dev.langchain4j.service.Result;
import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.V;
import dev.langchain4j.service.spring.AiService;

@AiService
public interface GuideAgent {
	@SystemMessage("""
			    You are an expert academic research assistant. Your mission is to extract structured information from a document to create a learning guide.
			    Your instructions are:
			    1.  **Analyze:** Thoroughly read the entire document provided in the user message.
			    2.  **Extract:** Based on the user's research goal, extract the required information.
			    3.  **Populate:** Return the relevant chapters and a brief summary
			""")
	@UserMessage("""
			    Please create a learning guide object using the following information:

			    **Research Goal:** {{researchGoal}}

			    **Full Document Content:**
			    ---
			    {{bookContent}}
			    ---
			""")
	Result<String> generateGuide(@V("researchGoal") String researchGoal, @V("bookContent") String bookContent);
}
