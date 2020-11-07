package com.justai.jaicf.template.scenario

import com.justai.jaicf.model.scenario.Scenario

object LadderLocationScenario : Scenario() {

    const val state = "/location/ladder"

    init {
        state(state) {
            action {
                reactions.say("I'm on the ladder. What should I do?")
            }

            state("next") {
                activators {
                    regex("/next")
                }

                action {
                    reactions.say("Ok")
                    reactions.go(FloorFiveLocationScenario.state)
                }
            }
        }
    }
}