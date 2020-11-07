package com.justai.jaicf.template.state

import com.justai.jaicf.context.ActionContext
import com.justai.jaicf.context.BotContext

val BotContext.checkpoints: CheckPoints
    get() {
        val key = "checkpoints"
        if (this.session.containsKey(key))
            return this.session[key] as CheckPoints

        val checkPoints = CheckPoints(this)
        this.session[key] = checkPoints
        return checkPoints
    }


fun BotContext.timeRelatedValue(key: String, velocity: Float, initialValue: Float, states: Array<Float>): TimeRelatedValue {
    return session[key] as? TimeRelatedValue
        ?: TimeRelatedValue(key, this, velocity, initialValue, states)
            .apply {
                session[key] = this
            }
}

val ActionContext.checkpoints: CheckPoints
    get() = context.checkpoints


fun ActionContext.timeRelatedValue(key: String, velocity: Float, initialValue: Float, states: Array<Float>): TimeRelatedValue {
    return context.timeRelatedValue(key, velocity, initialValue, states)
}