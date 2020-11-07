package com.justai.jaicf.template.scenario

import com.justai.jaicf.model.scenario.Scenario

object MainScenario : Scenario(
    dependencies = listOf(
        StartLocationScenario,
        LadderLocationScenario,
        FloorFiveLocationScenario,
        EndGameScenario
    )
) {

    const val state = "/start"

    init {
        state(state) {
            activators {
                regex("/start")
            }
            action {
                reactions.run {
                    sayRandom(
                        "Я в панике!"
                    )
                }
            }
        }

        fallback {
            reactions.sayRandom(
                "Sorry, I didn't get that...",
                "Sorry, could you repeat please?"
            )
        }
    }
}