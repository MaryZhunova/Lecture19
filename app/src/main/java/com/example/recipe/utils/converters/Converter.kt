package com.example.recipe.utils.converters

/**
 * Конверетер из типа данных [F] в тип данных [T]
 */
interface Converter<F, T> {

    /**
     * Конвертировать из типа данных [F] в тип данных [T]
     *
     * @param [from]
     * @return [T]
     */
    fun convert(from: F): T
}