package com.forums.app.util;

import java.util.*;

public class TagsExtractor {
    public static List<String> extract(String content) {
        List<String> matchedTags = new ArrayList<>();
        // Define the tags and their associated keywords
        Map<String, List<String>> tagKeywordsMap = new HashMap<>();
        tagKeywordsMap.put("Recycling", Arrays.asList("Recycle", "Environment", "Bin", "Paper", "Cardboard", "Sustainable", "Pollution", "Biodegradable", "Ecosystem", "Plastic", "Compost"));
        tagKeywordsMap.put("Food", Arrays.asList("Food", "Waste", "Litter", "Milk", "Dairy", "Water", "Bread", "Pasta", "Beans", "Expired", "Healthy", "Fruit", "Vegetables", "Chocolate", "Cake"));
        tagKeywordsMap.put("Quest", Arrays.asList("Quest", "Challenge", "Reward"));

        // Iterate through the tag and keyword map to find matches
        for (Map.Entry<String, List<String>> entry : tagKeywordsMap.entrySet()) {
            String tag = entry.getKey();
            List<String> keywords = entry.getValue();

            // Check if any of the keywords are present in the post content
            for (String keyword : keywords) {
                if (content.toLowerCase().contains(keyword.toLowerCase())) {
                    matchedTags.add(tag);
                    break;
                }
            }
        }
        // Print the matched tags for the current post content
        System.out.println("Post content: " + content);
        System.out.println("Matched tags: " + matchedTags);
        return matchedTags;
    }
}