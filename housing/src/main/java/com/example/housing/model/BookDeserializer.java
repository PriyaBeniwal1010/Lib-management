package com.example.housing.model;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;
import java.util.HashSet;

public class BookDeserializer extends JsonDeserializer<Book> {

    @Override
    public Book deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
        JsonNode node = jp.getCodec().readTree(jp);

        // Extract common fields with ternary operators for null checks
        int bookID = (node.has("bookID") && node.get("bookID").isInt()) ? node.get("bookID").asInt() : 0;
        String bookName = node.has("bookName") ? node.get("bookName").asText() : "";
        String ISBN = node.has("ISBN") ? node.get("ISBN").asText() : "";
        int totalQuantity = (node.has("totalQuantity") && node.get("totalQuantity").isInt()) ? node.get("totalQuantity").asInt() : 0;
        int issuedQuantity = (node.has("issuedQuantity") && node.get("issuedQuantity").isInt()) ? node.get("issuedQuantity").asInt() : 0;

        // Check for PrintBook-specific fields (availableStores)
        if (node.has("availableStores")) {
            // Deserialize availableStores into a HashSet using ternary operator
            JsonNode availableStoresNode = node.get("availableStores");
            HashSet<String> availableStores = new HashSet<>();
            if (availableStoresNode.isArray()) {
                for (JsonNode storeNode : availableStoresNode) {
                    availableStores.add(storeNode.asText());
                }
            }

            // Return a PrintBook instance
            return new PrintBook(bookID, bookName, ISBN, totalQuantity, issuedQuantity, availableStores);
        } else {
            // Deserialize EBook-specific fields using ternary operator
            String downloadLink = node.has("downloadLink") ? node.get("downloadLink").asText() : "";
            boolean isDRMProtected = node.has("isDRMProtected") ? node.get("isDRMProtected").asBoolean() : false;

//            {
//                "bookID": 34,
//                    "bookName": "Updated",
//                    "ISBN": "1234567890",
//                    "totalQuantity": 10,
//                    "issuedQuantity": 5,
//                    "downloadLink": "https://example.com/updatedbook",
//                    "isDRMProtected":true
//            }
            // Return an EBook instance
            return new EBook(bookID, bookName, ISBN, downloadLink, isDRMProtected);
        }
    }
}
