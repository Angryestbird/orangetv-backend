package com.orangetv.server.util

import java.net.URLEncoder
import java.nio.charset.StandardCharsets

internal fun encodePath(value: String): String {
    return value.split("/").joinToString(separator = "/") { encode(it) }
}

private fun encode(value: String): String {
    return URLEncoder.encode(value, StandardCharsets.UTF_8.toString())
}