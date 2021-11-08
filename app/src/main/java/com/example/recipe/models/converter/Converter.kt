package com.example.recipe.models.converter

/**
 * Конверетер из типа данных [F] в тип данных [T]
 */
interface Converter<F, T> {

    /**
     * Конвертировать из типа данных [F] в тип данных [T]
     *
     * @return [T]
     */
    fun convert(from: F): T
}