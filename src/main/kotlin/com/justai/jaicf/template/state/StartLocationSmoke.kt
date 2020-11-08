package com.justai.jaicf.template.state

import com.justai.jaicf.context.ActionContext
import com.justai.jaicf.helpers.ssml.audio

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
                0 -> reactions.say("Дыма становится все больше.")
                1 -> {
                    reactions.say("${audio("https://248305.selcdn.ru/demo_bot_static/Keep_talking_кашель3с.wav")}")
                    reactions.say("Тут всё в дыму. Нужно бежать отсюда!")
                }
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