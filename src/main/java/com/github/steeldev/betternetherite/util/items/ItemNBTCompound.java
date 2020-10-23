package com.github.steeldev.betternetherite.util.items;

public class ItemNBTCompound {
    public String compoundKey;
    public ItemNBTType compoundType;
    public Object compoundValue;

    public ItemNBTCompound(String compoundKey,
                           boolean compoundValue) {
        this.compoundKey = compoundKey;
        this.compoundValue = compoundValue;
        this.compoundType = ItemNBTType.BOOLEAN;
    }

    public ItemNBTCompound(String compoundKey,
                           int compoundValue) {
        this.compoundKey = compoundKey;
        this.compoundValue = compoundValue;
        this.compoundType = ItemNBTType.INTEGER;
    }

    public ItemNBTCompound(String compoundKey,
                           float compoundValue) {
        this.compoundKey = compoundKey;
        this.compoundValue = compoundValue;
        this.compoundType = ItemNBTType.FLOAT;
    }

    public ItemNBTCompound(String compoundKey,
                           String compoundValue) {
        this.compoundKey = compoundKey;
        this.compoundValue = compoundValue;
        this.compoundType = ItemNBTType.STRING;
    }

    public ItemNBTCompound(String compoundKey,
                           double compoundValue) {
        this.compoundKey = compoundKey;
        this.compoundValue = compoundValue;
        this.compoundType = ItemNBTType.DOUBLE;
    }
}
