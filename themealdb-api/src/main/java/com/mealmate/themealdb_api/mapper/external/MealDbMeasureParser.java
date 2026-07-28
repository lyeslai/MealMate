package com.mealmate.themealdb_api.mapper.external;

import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.mealmate.themealdb_api.domain.enums.Unit;

public class MealDbMeasureParser {

    private static final Map<String, Unit> UNIT_KEYWORDS = Map.ofEntries(
            Map.entry("tsp", Unit.CUILLERE_A_CAFE),
            Map.entry("teaspoon", Unit.CUILLERE_A_CAFE),
            Map.entry("tbsp", Unit.CUILLERE_A_SOUPE),
            Map.entry("tablespoon", Unit.CUILLERE_A_SOUPE),
            Map.entry("kg", Unit.KILOGRAMME),
            Map.entry("kilogram", Unit.KILOGRAMME),
            Map.entry("g", Unit.GRAMME),
            Map.entry("gram", Unit.GRAMME),
            Map.entry("ml", Unit.MILLILITRE),
            Map.entry("milliliter", Unit.MILLILITRE),
            Map.entry("l", Unit.LITRE),
            Map.entry("liter", Unit.LITRE),
            Map.entry("litre", Unit.LITRE),
            Map.entry("pinch", Unit.PINCEE),
            Map.entry("dash", Unit.PINCEE) 
       );
    private static Set<String> COUNTABLE_KEYWORDS = Set.of("clove", "cloves", "slive", "slices", "piece", "pieces", "can", "cans", 
       "tin", "tins", "packet", "packets", "bunch", "sprig", "stick", "stalk", "bulb", "head", "leaf", "leaves", "fillet", "fillets",
       "whole", "handfull"
    );

    private static final Double CUP_TO_ML = 240.0;

    private static final Pattern QUANTITY_PATTERN =
            Pattern.compile("([0-9]+\\s*/\\s*[0-9]+|[0-9]+\\.?[0-9]*)\\s*([a-zA-Z]+)?");

    public record ParsedMeasure(Double quantity, Unit unit) {}

    public static ParsedMeasure parse(String rawMeasure) {
        if (rawMeasure == null || rawMeasure.isBlank()) {
            return new ParsedMeasure(1.0, Unit.PIECE);
        }

        String normalized = rawMeasure.trim().toLowerCase();
        if (normalized.contains("to taste") || normalized.contains("as needed")) {
            return new ParsedMeasure(1.0, Unit.PIECE);
        }

        Matcher matcher = QUANTITY_PATTERN.matcher(normalized);

        if(!matcher.find()) {
            return new ParsedMeasure(1.0, Unit.PIECE);
        }

        Double quantity = parseNumber(matcher.group(1));
        String unitToken = matcher.group(2);
        if (quantity == null) quantity = 1.0;

        if (unitToken == null) {
            return new ParsedMeasure(quantity, Unit.PIECE);
        }


        if (unitToken.startsWith("cup")) {
            return new ParsedMeasure(quantity * CUP_TO_ML, Unit.MILLILITRE);
        }

        // Unités avec équivalent précis dans l'enum
        for (Map.Entry<String, Unit> entry : UNIT_KEYWORDS.entrySet()) {
            if (unitToken.startsWith(entry.getKey())) {
                return new ParsedMeasure(quantity, entry.getValue());
            }
        }

        // Unités comptables reconnues (clove, slice, packet...) → PIECE avec la quantité conservée
        for (String keyword : COUNTABLE_KEYWORDS) {
            if (unitToken.startsWith(keyword)) {
                return new ParsedMeasure(quantity, Unit.PIECE);
            }
        }

        // Token non reconnu → on garde quand même la quantité, en PIECE par défaut
        return new ParsedMeasure(quantity, Unit.PIECE);
    }

    private static Double parseNumber(String token) {
        try {
            if (token.contains("/")) {
                String[] parts = token.split("/");
                return Double.parseDouble(parts[0].trim()) / Double.parseDouble(parts[1].trim());
            }
            return Double.parseDouble(token.trim());
        } catch (NumberFormatException e) {
            return null;
        }
    }

    private MealDbMeasureParser() {}


       
}
