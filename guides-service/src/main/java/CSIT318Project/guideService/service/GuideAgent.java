package CSIT318Project.guideService.service;

import CSIT318Project.guideService.model.Guide;
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
			    2.  **Extract:** Based on the user's research goal, identify the most relevant subchapter and subsections of the document. Make sure you identify the smallest section unit of that document, not a big chapter or section, just a small sub one. Omit all sections that do not directly contribute to the research goal. Omit all summary and introduction sections.
			    3.  **Populate:** Return the relevant chapters and a brief summary for each section.
				4.  **Search Videos:** Formulate multiple queries and use the `searchVideos` tool to find the most relevant external videos that can help the user understand the topic better.
			    5. 	**Suggest Videos:** Pick 3 most relevant external videos from the results, ideally tackling different aspects of the research goal or different levels of understanding and thoroughness of the content. Then recommend them to the user.
				6.  **Summarise**: Provide a concise summary of the entire guide.
			""")
	@UserMessage("""
			    Please create a learning guide object using the following information:

			    **Research Goal:** {{researchGoal}}

			    **Full Document Content:**
			    ---
			    {{bookContent}}
			    ---
			""")
	Result<Guide> generateGuide(@V("researchGoal") String researchGoal, @V("bookContent") String bookContent);
}
