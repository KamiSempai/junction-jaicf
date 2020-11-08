package com.justai.jaicf.template

import com.justai.jaicf.channel.jaicp.reactions.TelephonyReactions
import com.justai.jaicf.context.ActionContext
import com.justai.jaicf.helpers.ssml.breakS
import com.justai.jaicf.reactions.TextReactions

fun ActionContext.delayS(seconds: Int): String {
    return when {
        this.reactions is TelephonyReactions -> "sil<[${seconds * 1000}]>"
        this.reactions is TextReactions -> ""
        else -> breakS(seconds)
    }
}