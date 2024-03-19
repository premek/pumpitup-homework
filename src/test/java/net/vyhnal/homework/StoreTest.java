package net.vyhnal.homework;

import net.vyhnal.homework.model.Attribute;
import net.vyhnal.homework.model.Category;
import net.vyhnal.homework.model.ConfigurationValue;
import net.vyhnal.homework.model.Item;
import net.vyhnal.homework.model.ItemType;
import net.vyhnal.homework.model.Order;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class StoreTest {
    private static final Store store = new Store();

    private static final Category tshirts = new Category("T-shirts");
    private static final Category socks = new Category("Socks");

    private static final Attribute season = new Attribute("Season");
    private static final Attribute color = new Attribute("Color");
    private static final Attribute size = new Attribute("Size");

    private static final ItemType programmingSocks = new ItemType("Programming socks", socks, Set.of(season));
    private static final ItemType sandalSocks = new ItemType("Sandal socks", socks, Set.of());
    private static final ItemType poloShirt = new ItemType("Polo Shirt", tshirts, Set.of(size, color));
    private static final ItemType basicTshirt = new ItemType("Basic T-shirt Black", tshirts, Set.of(size));


    @BeforeAll
    static void beforeAll() {
        store.add(new Item(1, 900, sandalSocks, Set.of()));
        store.add(new Item(2, 250, programmingSocks, Set.of(new ConfigurationValue(season, "Winter"))));
        store.add(new Item(3, 0, poloShirt, Set.of(new ConfigurationValue(size, "L"), new ConfigurationValue(color, "Black"))));
        store.add(new Item(4, 70, poloShirt, Set.of(new ConfigurationValue(size, "L"), new ConfigurationValue(color, "Red"))));
        store.add(new Item(5, 70, poloShirt, Set.of(new ConfigurationValue(size, "L"), new ConfigurationValue(color, "White"))));
        store.add(new Item(6, 70, poloShirt, Set.of(new ConfigurationValue(size, "XXXL"), new ConfigurationValue(color, "White"))));
        store.add(new Item(7, 150, basicTshirt, Set.of(new ConfigurationValue(size, "S"))));
        store.add(new Item(8, 100, basicTshirt, Set.of(new ConfigurationValue(size, "M"))));
        store.add(new Item(9, 50, basicTshirt, Set.of(new ConfigurationValue(size, "L"))));

        System.out.println(store);
    }

    @Test
    void add() {
        // polos cannot have a season configuration value
        assertThrows(IllegalArgumentException.class, () ->
                store.add(new Item(-1, 666, poloShirt, Set.of(new ConfigurationValue(season, "Winter")))));
    }

    @Test
    void findStockItem() {
        // not found
        Item redXxxlPolo = store.findStockItem(new Order(poloShirt, Set.of(
                new ConfigurationValue(size, "XXXL"),
                new ConfigurationValue(color, "Red"))));
        assertNull(redXxxlPolo);

        // zero stock
        Item blackLPolo = store.findStockItem(new Order(poloShirt, Set.of(
                new ConfigurationValue(size, "L"),
                new ConfigurationValue(color, "Black"))));
        assertNull(blackLPolo);

        // search by both attributes
        Item whiteLPolo = store.findStockItem(new Order(poloShirt, Set.of(
                new ConfigurationValue(size, "L"),
                new ConfigurationValue(color, "White"))));
        assertEquals(5, whiteLPolo.id());

        // search by one attribute only
        Item anyLPolo = store.findStockItem(new Order(poloShirt, Set.of(
                new ConfigurationValue(size, "L"))));
        assertTrue(anyLPolo.id() == 4 || anyLPolo.id() == 5);

        // search by item type only
        Item anyPolo = store.findStockItem(new Order(poloShirt, Set.of()));
        assertTrue(anyPolo.id() == 4 || anyPolo.id() == 5 || anyPolo.id() == 6);

        // item without attributes
        Item whiteSocks = store.findStockItem(new Order(sandalSocks, Set.of(new ConfigurationValue(color, "white"))));
        assertNull(whiteSocks);
        Item socks = store.findStockItem(new Order(sandalSocks, Set.of()));
        assertEquals(1, socks.id());
    }
}