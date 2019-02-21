package com.defaultxyz.skyline.extensions

fun <A, B> Pair<A?, B?>.takeIfNotEmpty(): Pair<A, B>? = first?.let { a ->
    second?.let { b -> Pair(a, b) }
}

fun <A, B, C> Triple<A?, B?, C?>.takeIfNotEmpty(): Triple<A, B, C>? = first?.let { a ->
    second?.let { b ->
        third?.let { c ->
            Triple(a, b, c)
        }
    }
}

fun <T, R> T?.doOnNull(block: () -> R): R? = if (this == null) block() else null

fun <T> T?.or(value: T): T = this ?: value