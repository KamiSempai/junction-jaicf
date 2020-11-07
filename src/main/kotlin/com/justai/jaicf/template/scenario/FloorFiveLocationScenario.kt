package com.justai.jaicf.template.scenario

import com.justai.jaicf.model.scenario.Scenario

object FloorFiveLocationScenario : Scenario() {

    const val state = "/location/floor5"

    init {
        state(state) {
            action {
                reactions.say("Закрыл дверь, что дальше с ней делать?")
            }

            state("next_good") {
                activators {
                    intent("CloseDoor")
                }

                action {
                    reactions.say("Хорошо")
                    reactions.go(EndGameScenario.state)
                }
            }
            
        }
    }
}