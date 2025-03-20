package net.vidgital.bunchofredstone.block;

import net.minecraft.util.StringRepresentable;

public enum IntersectionMode implements StringRepresentable
{
    FORWARD("forward"),
    LEFT("left"),
    RIGHT("right");

    private final String name;

    @Override
    public String toString()
    {
        return this.name;
    }

    @Override
    public String getSerializedName()
    {
        return this.name;
    }

    IntersectionMode(final String pName)
    {
        this.name = pName;
    }
}
