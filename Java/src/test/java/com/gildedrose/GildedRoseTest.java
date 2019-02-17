package com.gildedrose;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class GildedRoseTest {

    @Test(expected = IllegalArgumentException.class)
    public void qualityOfItemCannotBeNegative() {
        new Item("foo", 0, -1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void sulfurasMustBe80() {
        new Item("Sulfuras, Hand of Ragnaros", 0, 70);
    }

    @Test(expected = IllegalArgumentException.class)
    public void qualityCannotBeHigherThan50() {
        new Item("foo", 0, 51);
    }

    @Test
    public void qualityOfItemCannotBeNegativeOnUpdate() {
        Item[] items = new Item[] { new Item("foo", 0, 1), new Item("foo", 1, 0)};
        GildedRose app = new GildedRose(items);
        app.updateInventoryQuality();
        assertEquals(0, app.items[0].getQuality());
        assertEquals(0, app.items[1].getQuality());
    }

    @Test
    public void qualityDegradesByOneWhenSellByDateNotPassed() {
        Item[] items = new Item[] { new Item("foo", 1, 2) };
        GildedRose app = new GildedRose(items);
        app.updateInventoryQuality();
        assertEquals(1, app.items[0].getQuality());
    }

    @Test
    public void qualityDegradesTwiceAsFastWhenPastSellByDate() {
        Item[] items = new Item[] { new Item("foo", 0, 3), new Item("foo", -1, 3) };
        GildedRose app = new GildedRose(items);
        app.updateInventoryQuality();
        assertEquals(1, app.items[0].getQuality());
        assertEquals(1, app.items[1].getQuality());
    }

    @Test
    public void agedBrieIncreasesInQualityTheOlderItGets() {
        Item[] items = new Item[] { new Item("Aged Brie", 0, 1),  new Item("Aged Brie", 1, 1)};
        GildedRose app = new GildedRose(items);
        app.updateInventoryQuality();
        // TODO: Should the value increase by two on sellDate? Bug or feature?
        assertEquals(2, app.items[0].getQuality());
        assertEquals(2, app.items[1].getQuality());
    }

    @Test
    public void qualityOfItemCannotBeHigherThan50() {
        Item[] items = new Item[] { new Item("Aged Brie", 0, 50), new Item("Backstage passes to a TAFKAL80ETC concert", 1, 49)};
        GildedRose app = new GildedRose(items);
        app.updateInventoryQuality();
        assertEquals(50, app.items[0].getQuality());
        assertEquals(50, app.items[1].getQuality());
    }

    @Test
    public void sulfurasNeverDegradesInQuality() {
        Item[] items = new Item[]{ new Item("Sulfuras, Hand of Ragnaros", 0, 80),
                new Item("Sulfuras, Hand of Ragnaros", 1, 80),
                new Item("Sulfuras, Hand of Ragnaros", -1, 80)};
        GildedRose app = new GildedRose(items);
        app.updateInventoryQuality();
        assertEquals(80, app.items[0].getQuality());
        assertEquals(80, app.items[1].getQuality());
        assertEquals(80, app.items[2].getQuality());
    }

    @Test
    public void backStagePassesIncreaseInQualityBy2WhenBetween6And10DayLeft() {
        Item[] items = new Item[]{
                new Item("Backstage passes to a TAFKAL80ETC concert", 6, 2),
                new Item("Backstage passes to a TAFKAL80ETC concert", 10, 2)};
        GildedRose app = new GildedRose(items);
        app.updateInventoryQuality();
        assertEquals(4, app.items[0].getQuality());
        assertEquals(4, app.items[1].getQuality());
    }

    @Test
    public void backStagePassesIncreaseInQualityBy3WhenLessThan5DaysLeft() {
        Item[] items = new Item[]{
                new Item("Backstage passes to a TAFKAL80ETC concert", 5, 2),
                new Item("Backstage passes to a TAFKAL80ETC concert", 1, 2)};
        GildedRose app = new GildedRose(items);
        app.updateInventoryQuality();
        assertEquals(5, app.items[0].getQuality());
        assertEquals(5, app.items[1].getQuality());
    }

    @Test
    public void backStagePassesDropToZeroWhenConcertPassed() {
        Item[] items = new Item[]{
                new Item("Backstage passes to a TAFKAL80ETC concert", -1, 2),
                new Item("Backstage passes to a TAFKAL80ETC concert", 0, 2)};
        GildedRose app = new GildedRose(items);
        app.updateInventoryQuality();
        assertEquals(0, app.items[0].getQuality());
        assertEquals(0, app.items[1].getQuality());
    }

    @Test
    public void backStagePassesDecreaseBy1InQualityWhenMoreThan10DaysLeft() {
        Item[] items = new Item[]{
                new Item("Backstage passes to a TAFKAL80ETC concert", 11, 2),
                new Item("Backstage passes to a TAFKAL80ETC concert", 50, 2)};
        GildedRose app = new GildedRose(items);
        app.updateInventoryQuality();
        assertEquals(3, app.items[0].getQuality());
        assertEquals(3, app.items[1].getQuality());
    }



}
