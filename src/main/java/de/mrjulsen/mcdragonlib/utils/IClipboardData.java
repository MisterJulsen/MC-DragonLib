package de.mrjulsen.mcdragonlib.utils;

import net.minecraft.nbt.CompoundTag;

public interface IClipboardData {
    CompoundTag serializeNbt();
    void deserializeNbt(CompoundTag nbt);
}
