package net.vyhnal.homework.model;

import java.util.Set;

/**
 * Order has a reference to one item type (e.g. a "polo shirt")
 * and zero or more configuration values (attribute and value pairs, e.g. color:red, size:L).
 */
public record Order(ItemType itemType, Set<ConfigurationValue> requestedConfigurationValues) {
}
