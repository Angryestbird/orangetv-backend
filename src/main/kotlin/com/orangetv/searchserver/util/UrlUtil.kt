package com.orangetv.searchserver.util

import java.net.URLDecoder
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

internal fun encode(value: String?): String? {
    return URLEncoder.encode(value, StandardCharsets.UTF_8.toString())
}

internal fun decode(value: String?): String? {
    return URLDecoder.decode(value, StandardCharsets.UTF_8.toString())
}