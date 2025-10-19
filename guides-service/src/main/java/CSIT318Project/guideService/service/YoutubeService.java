package CSIT318Project.guideService.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import CSIT318Project.guideService.model.ExternalVideo;

@Service
public class YoutubeService {

	@Value("${youtube.api.key}")
	private String apiKey;

	@Value("${youtube.api.url}")
	private String apiUrl;

	private final RestTemplate restTemplate;
	private final ObjectMapper objectMapper; // Jackson's ObjectMapper for JSON parsing

	public YoutubeService(RestTemplate restTemplate, ObjectMapper objectMapper) {
		this.restTemplate = restTemplate;
		this.objectMapper = new ObjectMapper();
	}

	public List<ExternalVideo> searchVideos(String query) {
		// Construct the URL to get video results only, with the 'snippet' part
		String url = String.format(
				"%s?part=snippet&q=%s&type=video&maxResults=20&key=%s",
				apiUrl, query, apiKey);
		// Make the API call
		String jsonResponse = restTemplate.getForObject(url, String.class);

		List<ExternalVideo> videos = new ArrayList<>();

		try {
			// Parse the JSON response into a tree structure
			JsonNode root = objectMapper.readTree(jsonResponse);
			JsonNode items = root.path("items");

			// Iterate through each item in the 'items' array
			for (JsonNode item : items) {
				// Navigate to the videoId
				String videoId = item.path("id").path("videoId").asText();
				if (videoId.isEmpty()) {
					continue; // Skip if it's not a video item
				}

				// Navigate to the snippet to get title and description
				JsonNode snippet = item.path("snippet");
				String title = snippet.path("title").asText();
				String description = snippet.path("description").asText();

				// Construct the full YouTube URL
				String videoUrl = "https://www.youtube.com/watch?v=" + videoId;

				// Create and add the ExternalVideo object to our list
				videos.add(new ExternalVideo(videoUrl, title, description));
			}
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}

		return videos;
	}
}
