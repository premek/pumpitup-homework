package net.vyhnal.homework;

import net.vyhnal.homework.model.Attribute;
import net.vyhnal.homework.model.Category;
import net.vyhnal.homework.model.ConfigurationValue;
import net.vyhnal.homework.model.Item;
import net.vyhnal.homework.model.ItemType;
import net.vyhnal.homework.model.Order;

import java.util.Set;

public class Main {

    public static void main(String[] args) {
        Category tshirts = new Category("T-shirts");
        Attribute color = new Attribute("Color");
        Attribute size = new Attribute("Size");
        ItemType poloShirt = new ItemType("Polo Shirt", tshirts, Set.of(size, color));

        Store store = new Store();
        store.add(new Item(5, 70, poloShirt, Set.of(new ConfigurationValue(size, "L"), new ConfigurationValue(color, "White"))));

        Order order = new Order(poloShirt, Set.of(
                new ConfigurationValue(size, "L"),
                new ConfigurationValue(color, "White")));

        Item item = store.findStockItem(order);
        if (item == null) {
            System.out.println("Ordering item from supplier: " + order);
        } else {
            System.out.println("Shipping item: " + item);
        }
    }
}
