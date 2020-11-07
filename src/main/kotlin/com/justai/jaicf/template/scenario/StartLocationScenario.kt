package com.justai.jaicf.template.scenario

import com.justai.jaicf.model.scenario.Scenario

object StartLocationScenario : Scenario() {

    const val state = "/location/start"

    init {
        state(state) {
            action {
                reactions.say("I'm in the room. What should I do?")
            }

            state("next") {
                activators {
                    regex("/next")
                }

                action {
                    reactions.say("Ok")
                    reactions.go(LadderLocationScenario.state)
                }
            }
        }
    }
}