package com.justai.jaicf.template.state

import com.justai.jaicf.context.ActionContext

object StartLocationSmoke {

    private const val smokeKey = "start_location_smoke"
    private const val smokeVelocityNormal = 0.1f
    private const val smokeVelocityStep = 0.1f
    private const val smokeInitial = 0.1f
    private val smokeStates = arrayOf(6.0f, 24.0f)

    private val ActionContext.smoke: TimeRelatedValue
        get() = timeRelatedValue(smokeKey, smokeVelocityNormal, smokeInitial, smokeStates)

    fun handleSmoke(context: ActionContext) = context.run {
        smoke.handle {
            when(it) {
                0 -> reactions.say("Smoke!") //TODO fix text
                1 -> reactions.say("SMOKE!!!!") //TODO fix text
            }
        }
    }

    fun increaseVelocity(context: ActionContext) {
        context.smoke.velocity += smokeVelocityStep
    }

    fun decreaseVelocity(context: ActionContext) {
        context.smoke.velocity -+ smokeVelocityStep
    }

}