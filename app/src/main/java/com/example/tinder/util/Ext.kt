package com.example.tinder.util

import java.net.URL

const val SECURE_HOST = "https"
fun URL.getSecuredUrl() = URL(SECURE_HOST, host, port, file).toString()

fun String.limited() =
    if (length >= 28) StringBuilder(substring(0, 28)).append("...").toString() else this