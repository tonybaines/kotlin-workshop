package com.github.tonybaines.kotlin.basics

object Loops {
    val rainbow = listOf("red", "orange", "yellow", "green", "blue", "indigo", "violet")

    @JvmStatic
    fun main(args: Array<String>) {
        for (colour in rainbow) {
            println(colour)
        }
    }
}