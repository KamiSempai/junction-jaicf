package com.justai.jaicf.template.state

import com.justai.jaicf.context.BotContext

class TimeRelatedValue(
    private val name: String,
    private val context: BotContext,
    velocity: Float,
    private var initialValue: Float,
    private val states: Array<Float> = emptyArray()
) {
    private val keyVelocity = "${name}_velocity"
    private val keyInitialValue = "${name}_initialValue"
    private val keyStartTime = "${name}_startTime"
    private val keyHandledState = "${name}_handledState"

    val value: Float
        get() = initialValue + (System.currentTimeMillis() - startTime) / 1000L * velocity

    var velocity: Float = context.session[keyVelocity] as? Float ?: velocity
        set(value) {
            initialValue = this.value
            startTime = System.currentTimeMillis()
            field = value

            context.session[keyVelocity] = value
            context.session[keyInitialValue] = initialValue
            context.session[keyStartTime] = startTime
        }

    private var startTime: Long = context.session[keyStartTime] as? Long ?: 0L

    private var handledState: Int
        get() = context.session[keyHandledState] as? Int ?: -1
        set(value) {
            context.session[keyHandledState] = value
        }

    init {
        initialValue = context.session[keyInitialValue] as? Float ?: initialValue
    }

    fun handle(stateHandler: (state: Int) -> Unit) {
        start()
        val value = this.value
        for (i in handledState + 1 until states.size) {
            if (value >= states[i]) {
                stateHandler(i)
                handledState = i
            }
        }
    }

    private fun start() {
        if (startTime == 0L) {
            startTime = System.currentTimeMillis()
            context.session[keyStartTime] = startTime
        }
    }
}