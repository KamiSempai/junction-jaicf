package com.justai.jaicf.template

import com.justai.jaicf.builder.ScenarioBuilder
import com.justai.jaicf.channel.jaicp.channels.TelephonyEvents
import com.justai.jaicf.context.ActionContext

fun ScenarioBuilder.fallbackOrSilence(fallBackAction: ActionContext.() -> Unit) {
    state("SPEECH_NOT_RECOGNISED") {
        activators {
            event(TelephonyEvents.SPEECH_NOT_RECOGNISED)
        }
        action(fallBackAction)
    }

    fallback(action = fallBackAction)
}