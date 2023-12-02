package de.mrjulsen.mcdragonlib.common;

public interface ITranslatableEnum {
    String getEnumName();
    String getEnumValueName();

    default String getValueTranslationKey(String modid) {
        return String.format("enum.%s.%s.%s", modid, this.getEnumName(), this.getEnumValueName());
    }
    default String getEnumDescriptionTranslationKey(String modid) {
        return String.format("enum.%s.%s.description", modid, this.getEnumName());
    }
    default String getValueInfoTranslationKey(String modid) {
        return String.format("enum.%s.%s.info.%s", modid, this.getEnumName(), this.getEnumValueName());
    }
}
