package com.gildedrose;

import org.junit.Test;

import static com.gildedrose.Item.ItemType.*;
import static org.junit.Assert.assertEquals;

public class GildedRoseTest {

    @Test(expected = IllegalArgumentException.class)
    public void qualityOfItemCannotBeNegative() {
        new Item("foo", 0, -1, NORMAL);
    }

    @Test(expected = IllegalArgumentException.class)
    public void legendaryQualityMustBeEighty() {
        new Item("Sulfuras, Hand of Ragnaros", 0, 70, LEGENDARY);
    }

    @Test(expected = IllegalArgumentException.class)
    public void qualityCannotBeHigherThanFifty() {
        new Item("foo", 0, 51, NORMAL);
    }

    @Test
    public void qualityOfItemCannotBeNegativeOnUpdate() {
        Item[] items = new Item[] {
                new Item("foo", 0, 1, NORMAL),
                new Item("foo", 1, 0, NORMAL)};
        GildedRose app = new GildedRose(items);
        app.updateInventoryQuality();
        assertEquals(0, app.items[0].getQuality());
        assertEquals(0, app.items[1].getQuality());
    }

    @Test
    public void qualityDegradesByOneWhenSellByDateNotPassed() {
        Item[] items = new Item[] { new Item("foo", 1, 2, NORMAL) };
        GildedRose app = new GildedRose(items);
        app.updateInventoryQuality();
        assertEquals(1, app.items[0].getQuality());
    }

    @Test
    public void qualityDegradesByTwoWhenSellByDateNotPassedAndItemIsConjured() {
        Item[] items = new Item[] {
                new Item("Conjured foo", 1, 1, CONJURED),
                new Item("Conjured foo", 1, 2, CONJURED),
                new Item("Conjured foo", 1, 3, CONJURED)
        };
        GildedRose app = new GildedRose(items);
        app.updateInventoryQuality();
        assertEquals(0, app.items[0].getQuality());
        assertEquals(0, app.items[1].getQuality());
        assertEquals(1, app.items[2].getQuality());
    }

    @Test
    public void qualityDegradesByTwoWhenPastSellByDate() {
        Item[] items = new Item[] {
                new Item("foo", 0, 3, NORMAL),
                new Item("foo", -1, 3, NORMAL) };
        GildedRose app = new GildedRose(items);
        app.updateInventoryQuality();
        assertEquals(1, app.items[0].getQuality());
        assertEquals(1, app.items[1].getQuality());
    }

    @Test
    public void qualityDegradesByFourWhenSellByPassedAndItemIsConjured() {
        Item[] items = new Item[] {
                new Item("Conjured foo", 0, 5, CONJURED),
                new Item("Conjured foo", 0, 3, CONJURED),
                new Item("Conjured foo", 0, 0, CONJURED)};
        GildedRose app = new GildedRose(items);
        app.updateInventoryQuality();
        assertEquals(1, app.items[0].getQuality());
        assertEquals(0, app.items[1].getQuality());
        assertEquals(0, app.items[2].getQuality());
    }

    @Test
    public void agedBrieIncreasesInQualityTheOlderItGets() {
        Item[] items = new Item[] {
                new Item("Aged Brie", 0, 1, AGED_BRIE),
                new Item("Aged Brie", 1, 1, AGED_BRIE)};
        GildedRose app = new GildedRose(items);
        app.updateInventoryQuality();
        assertEquals(2, app.items[0].getQuality());
        assertEquals(2, app.items[1].getQuality());
    }

    @Test
    public void qualityOfItemCannotBeHigherThan50() {
        Item[] items = new Item[] {
                new Item("Aged Brie", 0, 50, AGED_BRIE),
                new Item("Backstage passes to a TAFKAL80ETC concert", 1, 49, BACKSTAGE_PASSES)};
        GildedRose app = new GildedRose(items);
        app.updateInventoryQuality();
        assertEquals(50, app.items[0].getQuality());
        assertEquals(50, app.items[1].getQuality());
    }

    @Test
    public void legendaryItemsNeverDegradesInQuality() {
        Item[] items = new Item[]{
                new Item("Sulfuras, Hand of Ragnaros", 0, 80, LEGENDARY),
                new Item("Sulfuras, Hand of Ragnaros", 1, 80, LEGENDARY),
                new Item("Sulfuras, Hand of Ragnaros", -1, 80, LEGENDARY)};
        GildedRose app = new GildedRose(items);
        app.updateInventoryQuality();
        assertEquals(80, app.items[0].getQuality());
        assertEquals(80, app.items[1].getQuality());
        assertEquals(80, app.items[2].getQuality());
    }

    @Test
    public void backStagePassesIncreaseInQualityBy2WhenBetween6And10DayLeft() {
        Item[] items = new Item[]{
                new Item("Backstage passes to a TAFKAL80ETC concert", 6, 2, BACKSTAGE_PASSES),
                new Item("Backstage passes to a TAFKAL80ETC concert", 10, 2, BACKSTAGE_PASSES)};
        GildedRose app = new GildedRose(items);
        app.updateInventoryQuality();
        assertEquals(4, app.items[0].getQuality());
        assertEquals(4, app.items[1].getQuality());
    }

    @Test
    public void backStagePassesIncreaseInQualityBy3WhenLessThan5DaysLeft() {
        Item[] items = new Item[]{
                new Item("Backstage passes to a TAFKAL80ETC concert", 5, 2, BACKSTAGE_PASSES),
                new Item("Backstage passes to a TAFKAL80ETC concert", 1, 2, BACKSTAGE_PASSES)};
        GildedRose app = new GildedRose(items);
        app.updateInventoryQuality();
        assertEquals(5, app.items[0].getQuality());
        assertEquals(5, app.items[1].getQuality());
    }

    @Test
    public void backStagePassesDropToZeroWhenConcertPassed() {
        Item[] items = new Item[]{
                new Item("Backstage passes to a TAFKAL80ETC concert", -1, 2, BACKSTAGE_PASSES),
                new Item("Backstage passes to a TAFKAL80ETC concert", 0, 2, BACKSTAGE_PASSES)};
        GildedRose app = new GildedRose(items);
        app.updateInventoryQuality();
        assertEquals(0, app.items[0].getQuality());
        assertEquals(0, app.items[1].getQuality());
    }

    @Test
    public void backStagePassesDecreaseBy1InQualityWhenMoreThan10DaysLeft() {
        Item[] items = new Item[]{
                new Item("Backstage passes to a TAFKAL80ETC concert", 11, 2, BACKSTAGE_PASSES),
                new Item("Backstage passes to a TAFKAL80ETC concert", 50, 2, BACKSTAGE_PASSES)};
        GildedRose app = new GildedRose(items);
        app.updateInventoryQuality();
        assertEquals(3, app.items[0].getQuality());
        assertEquals(3, app.items[1].getQuality());
    }



}
