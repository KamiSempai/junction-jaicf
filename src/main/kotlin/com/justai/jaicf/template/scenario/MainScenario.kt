package com.justai.jaicf.template.scenario

import com.justai.jaicf.model.scenario.Scenario

object MainScenario : Scenario(
    dependencies = listOf(
        StartRoomScenario,
        StartFloorScenario,
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
                reactions.say("Привет, это Никита! Слушай, такое дело, у нас пожар в общаге! Срочно нужна твоя помощь! Что мне делать?")
                reactions.go(StartRoomScenario.state)
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