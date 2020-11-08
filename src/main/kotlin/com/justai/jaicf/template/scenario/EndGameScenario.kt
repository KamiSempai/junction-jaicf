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
                reactions.say("Спасибо! Я спасен!")
                checkpoints.getCompactNegative()?.let {
                    reactions.say("Но тебе всё-таки стоит повторить правила поведения при пожаре.")
                    reactions.say("Ошибки были в случаях с $it")
                } ?: {
                    reactions.say("Спасателей лучше тебя я ещё не встречал.")
                    reactions.say("Всё сделано правильно! Ни одной ошибки.")
                }()
            }

            state("restart") {
                activators {
                    regex("/restart")
                    intent("Restart")
                }

                action {
                    context.run {
                        cleanSessionData()
                        dialogContext.run {
                            currentState = "/"
                            currentContext = "/"
                        }
                    }
                    reactions.say("Начинаем сначала.")
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
                    context.checkpoints.getCompactNegative()?.let {
                        reactions.say("Ошибки были в случаях с $it")
                    } ?: {
                        reactions.say("Всё сделалано правильно! Ни одной ошибки.")
                    }()
                }
            }
        }
    }
}