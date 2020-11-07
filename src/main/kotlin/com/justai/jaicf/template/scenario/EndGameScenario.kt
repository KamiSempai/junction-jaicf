package com.justai.jaicf.template.scenario

import com.justai.jaicf.model.scenario.Scenario
import com.justai.jaicf.template.state.checkpoints
import com.justai.jaicf.template.state.getCompactNegative
import com.justai.jaicf.template.state.getReadableResults

object EndGameScenario : Scenario() {

    const val state = "/endgame"

    init {
        state(state) {
            action {
                reactions.say("Thank you! I'm saved!")
            }

            state("restart") {
                activators {
                    regex("/restart")
                }

                action {
                    reactions.say("Restarting the game")
                    reactions.go(MainScenario.state)
                }
            }

            state("results") {
                activators {
                    regex("/results")
                }

                action {
                    val (positive, negative) = context.checkpoints.getReadableResults()
                    if (positive.isNotEmpty()) {
                        reactions.say("В этих ситуациях вы поступили правильно:")
                        positive.forEach { reactions.say(it) }
                    }
                    if (negative.isNotEmpty()) {
                        reactions.say("В этих случаях вы ошиблись:")
                        negative.forEach { reactions.say(it) }
                    }
                }
            }

            state("small results") {
                activators {
                    regex("/compact")
                }

                action {
                    val message = context.checkpoints.getCompactNegative()
                    if (message == null)
                        reactions.say("Ты всё сделал правильно!")
                    else
                        reactions.say("Ты ошибся в случаях с $message")
                }
            }
        }
    }
}