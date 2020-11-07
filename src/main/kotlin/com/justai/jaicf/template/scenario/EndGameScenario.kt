package com.justai.jaicf.template.scenario

import com.justai.jaicf.model.scenario.Scenario

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
        }
    }
}