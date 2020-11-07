package com.justai.jaicf.template.state

import com.justai.jaicf.context.BotContext

class TimeRelatedValue(
    private val name: String,
    private val context: BotContext,
    private val velocity: Float,
    private val initialValue: Float
) {
    val value: Float
        get() = initialValue + (System.currentTimeMillis() - startTime) / 1000L * velocity

    private val startTimeKey = "${name}_startTime"
    private var startTime: Long = 0

    init {
        startTime = context.session[startTimeKey] as? Long ?: 0L
    }

    fun start() {
        if (startTime == 0L) {
            startTime = System.currentTimeMillis()
            context.session[startTimeKey] = startTime
        }
    }

}