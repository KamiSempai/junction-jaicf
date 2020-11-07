package com.justai.jaicf.template.scenario

import com.justai.jaicf.channel.jaicp.channels.TelephonyEvents
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
                reactions.say(${audio("https://248305.selcdn.ru/demo_bot_static/Keep_talking_кашель3с.wav")})
                reactions.say("Привет, это Никита! Слушай, такое дело, у нас пожар в общаге! Срочно нужна твоя помощь! Что мне делать?")
                reactions.go(StartRoomScenario.state)
            }
        }

        state("HangUp") {
            activators {
                event(TelephonyEvents.HANGUP)
            }
            action {
                context.run {
                    cleanSessionData()
                    dialogContext.run {
                        currentState = "/"
                        currentContext = "/"
                    }
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