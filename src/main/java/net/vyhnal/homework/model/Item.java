package net.vyhnal.homework.model;

import java.util.Set;

/**
 * Links an item type (e.g. a polo shirt) with a specific configuration (e.g. red color, size L)
 * @param id unique identifier
 * @param stock number of pieces of this specific configuration in stock
 * @param type item type (e.g. a polo shirt)
 * @param configurationValues set of configuration values (e.g. red color, size L), could be empty.
 *                            The attributes must match the ones defined for the given ItemType.
 */
public record Item(long id, int stock, ItemType type, Set<ConfigurationValue> configurationValues) {

}
