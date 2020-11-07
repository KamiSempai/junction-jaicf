package com.justai.jaicf.template.state

import com.justai.jaicf.context.ActionContext
import com.justai.jaicf.context.BotContext
import com.justai.jaicf.template.templateBot

val BotContext.checkpoints: CheckPoints
    get() {
        val key = "checkpoints"
        if (this.session.containsKey(key))
            return this.session[key] as CheckPoints

        val checkPoints = CheckPoints(this)
        this.session[key] = checkPoints
        return checkPoints
    }

val ActionContext.checkpoints: CheckPoints
    get() = context.checkpoints