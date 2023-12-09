package com.langley.exercisestattracker

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform