package com.defaultxyz.skyline.extensions

fun <A, B> Pair<A?, B?>.takeIfNotEmpty(): Pair<A, B>? = first?.let { a ->
    second?.let { b -> Pair(a, b) }
}

fun <T, R> T?.doOnNull(block: () -> R): R? = if (this == null) block() else null

fun <T> T?.or(value: T): T = this ?: value