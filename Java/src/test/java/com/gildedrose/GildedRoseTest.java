package com.gildedrose;

import static org.junit.Assert.*;

import org.junit.Test;

public class GildedRoseTest {

    @Test
    public void qualityOfItemCannotBeNegative() {
        Item[] items = new Item[] { new Item("foo", 0, 1), new Item("foo", 1, 0)};
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        assertEquals(0, app.items[0].quality);
        assertEquals(0, app.items[1].quality);
    }

    @Test
    public void qualityDegradesByOneWhenSellByDateNotPassed() {
        Item[] items = new Item[] { new Item("foo", 1, 2) };
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        assertEquals(1, app.items[0].quality);
    }

    @Test
    public void qualityDegradesTwiceAsFastWhenPastSellByDate() {
        Item[] items = new Item[] { new Item("foo", 0, 3), new Item("foo", -1, 3) };
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        assertEquals(1, app.items[0].quality);
        assertEquals(1, app.items[1].quality);
    }

    @Test
    public void agedBrieIncreasesInQualityTheOlderItGets() {
        Item[] items = new Item[] { new Item("Aged Brie", 0, 1),  new Item("Aged Brie", 1, 1)};
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        // TODO: Should the value increase by two on sellDate? Bug or feature?
//        assertEquals(2, app.items[0].quality);
        assertEquals(2, app.items[1].quality);
    }

    @Test
    public void qualityOfItemCannotBeHigherThan50() {
        Item[] items = new Item[] { new Item("Aged Brie", 0, 50)};
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        assertEquals(50, app.items[0].quality);
    }

    @Test
    public void sulfurasNeverDegradesInQuality() {
        Item[] items = new Item[]{ new Item("Sulfuras, Hand of Ragnaros", 0, 40),
                new Item("Sulfuras, Hand of Ragnaros", 1, 40),
                new Item("Sulfuras, Hand of Ragnaros", -1, 40)};
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        assertEquals(40, app.items[0].quality);
        assertEquals(40, app.items[1].quality);
        assertEquals(40, app.items[2].quality);
    }

    @Test
    public void backStagePassesIncreaseInQualityBy2WhenBetween6And10DayLeft() {
        Item[] items = new Item[]{
                new Item("Backstage passes to a TAFKAL80ETC concert", 6, 2),
                new Item("Backstage passes to a TAFKAL80ETC concert", 10, 2)};
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        assertEquals(4, app.items[0].quality);
        assertEquals(4, app.items[1].quality);
    }

    @Test
    public void backStagePassesIncreaseInQualityBy3WhenLessThan5DaysLeft() {
        Item[] items = new Item[]{
                new Item("Backstage passes to a TAFKAL80ETC concert", 5, 2),
                new Item("Backstage passes to a TAFKAL80ETC concert", 1, 2)};
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        assertEquals(5, app.items[0].quality);
        assertEquals(5, app.items[1].quality);
    }

    @Test
    public void backStagePassesDropToZeroWhenConcertPassed() {
        Item[] items = new Item[]{
                new Item("Backstage passes to a TAFKAL80ETC concert", -1, 2),
                new Item("Backstage passes to a TAFKAL80ETC concert", 0, 2)};
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        assertEquals(0, app.items[0].quality);
        assertEquals(0, app.items[1].quality);
    }



}
