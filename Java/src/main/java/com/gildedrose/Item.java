package com.gildedrose;

public class Item {

    private String name;

    private int sellIn;

    private int quality;

    public Item(String name, int sellIn, int quality) {
        this.name = name;
        this.sellIn = sellIn;
        this.quality = quality;
        assertQuality();
    }

    public void decreaseSellInAndUpdateQuality() {
        sellIn--;

        if ("Sulfuras, Hand of Ragnaros".equals(name)) {
            return;
        }

        if ("Backstage passes to a TAFKAL80ETC concert".equals(name)) {
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
            return;
        }

        if ("Aged Brie".equals(name)) {
            incrementQualityBetweenZeroAndFifty(1);
            return;
        }

        if (quality > 0 && sellIn < 0) {
            incrementQualityBetweenZeroAndFifty(-2);
        } else if (quality > 0) {
            incrementQualityBetweenZeroAndFifty(-1);
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

        if ("Sulfuras, Hand of Ragnaros".equals(name)) {
            if (quality != 80) {
                throw new IllegalArgumentException("Quality of Sulfuras, Hand of Ragnaros must be 80");
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
        return this.name + ", " + this.sellIn + ", " + this.quality;
    }
}
