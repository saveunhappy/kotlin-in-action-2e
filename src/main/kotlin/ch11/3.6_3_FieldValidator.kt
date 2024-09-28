package ch11.FieldValidator

import kotlin.reflect.KClass

interface FieldValidator<in T> {
    fun validate(input: T): Boolean
}

object DefaultStringValidator : FieldValidator<String> {
    override fun validate(input: String) = input.isNotEmpty()
}

object DefaultIntValidator : FieldValidator<Int> {
    override fun validate(input: Int) = input >= 0
}

fun main() {
    val validators = mutableMapOf<KClass<*>, FieldValidator<*>>()
    validators[String::class] = DefaultStringValidator
    validators[Int::class] = DefaultIntValidator
//    val isValidString = validators[String::class]?.validate("some string") ?: false
//    val isValidInt = validators[Int::class]?.validate(42) ?: false
    val stringValidator = validators[String::class] as? FieldValidator<String>
    val intValidator = validators[Int::class] as? FieldValidator<Int>

    val isValidString = stringValidator?.validate("some string") ?: false
    val isValidInt = intValidator?.validate(42) ?: false

    println("String is valid: $isValidString")
    println("Int is valid: $isValidInt")
}
