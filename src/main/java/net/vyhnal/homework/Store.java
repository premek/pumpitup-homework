package net.vyhnal.homework;

import net.vyhnal.homework.model.Attribute;
import net.vyhnal.homework.model.ConfigurationValue;
import net.vyhnal.homework.model.Item;
import net.vyhnal.homework.model.Order;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * The store keeps track of available stock and enables item search.
 */
public class Store {

    private final List<Item> items = new ArrayList<>();

    /**
     * @param item an Item to be added to the store
     */
    public void add(Item item) {
        Objects.requireNonNull(item, "item must not be null");

        // check that item attributes are allowed for this item type
        Set<Attribute> allowedAttributes = item.type().attributes();
        boolean attributesAllowed = item.configurationValues().stream()
                .map(ConfigurationValue::attribute)
                .allMatch(allowedAttributes::contains);
        if (!attributesAllowed) {
            throw new IllegalArgumentException("The item contains an attribute that is not allowed for this item type: " + item);
        }

        // TODO check duplicated IDs, duplicated attributes etc

        items.add(item);
    }

    /**
     * Finds an item in stock based on the item type and configuration values specified in the order.
     *
     * @param order Defines the item type to look for and zero or more configuration values.
     * @return the item or null if no item is found or if the item is not in stock
     */
    public Item findStockItem(Order order) {
        Objects.requireNonNull(order, "order must not be null");

        return items.stream()
                .filter(item -> matches(item, order))
                .filter(item -> item.stock() > 0)
                .findAny()
                .orElse(null);
    }

    private boolean matches(Item item, Order order) {
        if (!item.type().equals(order.itemType())) {
            return false;
        }

        // for each configuration value from the order:
        for (ConfigurationValue configurationValue : order.requestedConfigurationValues()) {
            // if the item does not have a configuration value with the same attribute type and the same value,
            if (item.configurationValues().stream().noneMatch(v -> v.equals(configurationValue))) {
                // then this is not the right item for this order.
                return false;
            }
        }
        return true;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        items.stream()
                .collect(Collectors.groupingBy(Item::type))
                .forEach((itemType, itemsOfType) -> {

                    sb.append("\t- ").append(itemType.name()).append("\n");

                    itemsOfType.forEach(item -> {

                        sb.append("\t\t").append(item.stock()).append("x ");

                        item.configurationValues().forEach(value ->
                                sb.append("\t")
                                        .append(value.attribute().name())
                                        .append(": ")
                                        .append(value.value()));
                        sb.append("\n");
                    });
                });

        return sb.toString();
    }

}
