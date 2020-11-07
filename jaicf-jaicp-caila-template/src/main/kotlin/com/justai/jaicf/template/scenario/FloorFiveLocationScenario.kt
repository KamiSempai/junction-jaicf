package com.justai.jaicf.template.scenario

import com.justai.jaicf.model.scenario.Scenario

object FloorFiveLocationScenario : Scenario() {

    const val state = "/location/floor5"

    init {
        state(state) {
            action {
                reactions.say("I'm on the floor 5. What should I do?")
            }

            state("next") {
                activators {
                    regex("/next")
                }

                action {
                    reactions.say("Ok")
                    reactions.go(EndGameScenario.state)
                }
            }
        }
    }
}