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
                        "Hello! I need your help!",
                        "Hi there! I need your help!"
                    )
                }
            }
        }

        state("Start Game") {
            activators {
                regex("/next")
            }

            action {
                reactions.go(StartLocationScenario.state)
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