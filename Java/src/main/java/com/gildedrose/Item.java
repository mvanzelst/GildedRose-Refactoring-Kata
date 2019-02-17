package com.gildedrose;

import java.util.Objects;

import static com.gildedrose.Item.ItemType.LEGENDARY;
import static com.gildedrose.Item.ItemType.NORMAL;

public class Item {

    private final String name;

    private int sellIn;

    private int quality;

    private final ItemType itemType;

    public Item(String name, int sellIn, int quality, ItemType itemType) {
        this.name = name;
        this.sellIn = sellIn;
        this.quality = quality;
        this.itemType = Objects.requireNonNull(itemType);
        assertQuality();
    }

    public void decreaseSellInAndUpdateQuality() {
        sellIn--;

        switch (itemType){
            case LEGENDARY:
                break;
            case BACKSTAGE_PASSES:
                updateBackstagePassesQuality();
                break;
            case AGED_BRIE:
                incrementQualityBetweenZeroAndFifty(1);
                break;
            case NORMAL:
            case CONJURED:
                updateNormalAndConjuredQuality();
                break;
            default:
                throw new IllegalArgumentException("Unknown Item Type");
        }
    }

    private void updateBackstagePassesQuality() {
        if (sellIn < 0) {
            setQuality(0);
        } else {
            if (sellIn < 5) {
                incrementQualityBetweenZeroAndFifty(3);
            } else if (sellIn < 10) {
                incrementQualityBetweenZeroAndFifty(2);
            } else {
                incrementQualityBetweenZeroAndFifty(1);
            }
        }
    }

    private void updateNormalAndConjuredQuality() {
        if (quality > 0 && sellIn < 0) {
            incrementQualityBetweenZeroAndFifty(itemType == NORMAL ? -2 : -4);
        } else if (quality > 0) {
            incrementQualityBetweenZeroAndFifty(itemType == NORMAL ? -1 : -2);
        }
    }

    private void incrementQualityBetweenZeroAndFifty(int increment) {
        int newQuality = quality + increment;
        if (newQuality >= 0) {
            if(newQuality <= 50){
                setQuality(newQuality);
            } else {
                setQuality(50);
            }
        } else {
            setQuality(0);
        }
    }

    public String getName() {
        return name;
    }

    public int getSellIn() {
        return sellIn;
    }

    public int getQuality() {
        return quality;
    }

    private void setQuality(int quality) {
        this.quality = quality;
        assertQuality();
    }

    private void assertQuality() {
        if (quality < 0) {
            throw new IllegalArgumentException("Quality cannot be negative");
        }

        if (itemType == LEGENDARY) {
            if (quality != 80) {
                throw new IllegalArgumentException("Quality of Legendary items must be 80");
            } else {
                return;
            }
        }

        if (quality > 50) {
            throw new IllegalArgumentException("Quality cannot be more than 50");
        }
    }

    @Override
    public String toString() {
        return this.name + ", " + this.sellIn + ", " + this.quality + ", " + this.itemType;
    }

    public enum ItemType {
        NORMAL,
        AGED_BRIE,
        BACKSTAGE_PASSES,
        LEGENDARY,
        CONJURED
    }
}
