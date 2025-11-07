package com.example.cmp_toast

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform