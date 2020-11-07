package com.justai.jaicf.template

fun delayS(seconds: Int): String {
    return "sil<[${seconds * 1000}]>"
}