package net.vyhnal.homework.model;

import java.util.Set;

/**
 * An Item type
 * @param name e.g. a "polo shirt"
 * @param category e.g. "t-shirts"
 * @param attributes a set of allowed attributes, e.g. size, color.
 *                   This does not contain attribute values (like "red", "L", "XXL", ...).
 */
public record ItemType(String name, Category category, Set<Attribute> attributes) {
}
