package com.justai.jaicf.template.scenario

import com.justai.jaicf.model.scenario.Scenario
import com.justai.jaicf.template.state.EmergencyPaths
import com.justai.jaicf.template.state.checkpoints

object StartRoomScenario : Scenario() {

    const val state = "/location/start_room"
    private const val window = "/location/window"
    private const val door = "/location/door"
    private const val things = "/location/things"

    init {
        state(state) {
            state("call firefighters") {
                activators {
                    intent("CallFirefighters")
                }

                action {
                    reactions.say("Да, пожарных я уже вызвал, но когда они ещё приедут, а мне нужно что-то делать…")
                    context.checkpoints.CallFirefighters = true
                    context.checkpoints.KeepCalm = true
                    reactions.go(window)
                }
            }

            state("keep calm") {
                activators {
                    intent("KeepCalm")
                }

                action {
                    reactions.say("Вдох-выдох, вдох-выдох")
                    context.checkpoints.CallFirefighters = false
                    context.checkpoints.KeepCalm = true
                    reactions.go(window)
                }

            }

            fallback {
                reactions.say("АААААААААа!!!!!!")
                context.checkpoints.CallFirefighters = false
                context.checkpoints.KeepCalm = false
                reactions.go(window)
            }
        }

        state(window) {
            action {
                reactions.say("Думаю открыть окно. Стоит?")
            }

            state("No") {
                activators {
                    intent("No")
                }
                action {
                    reactions.say("Отлично, понял.")
                    context.checkpoints.OpenWindow = false
                    reactions.go(door)
                }
            }

            state("Yes") {
                activators {
                    intent("Yes")
                }
                action {
                    reactions.say("Кажется, стало только хуже, дыма резко стало больше, кажется, я даже вижу огонёк.")
                    context.checkpoints.OpenWindow = true
                    reactions.say("Too much smoke")
                    reactions.go(door)
                }
            }

            fallback {
                reactions.say("Открыл. Кажется, стало только хуже, дыма резко стало больше, кажется, я даже вижу огонёк.")
                context.checkpoints.OpenWindow = true
                reactions.say("Too much smoke")
                reactions.go(door)
            }
        }

        state(door) {
            action {
                reactions.say("Надо выбираться отсюда, сейчас только дверь открою.")
            }

            state("Check doorknob") {
                activators {
                    intent("CheckDoorknob")
                }

                action {
                    reactions.say("Сейчас проверю")
                    context.checkpoints.CheckDoorknob = true
                    reactions.go(things)
                }
            }

            fallback {
                context.checkpoints.CheckDoorknob = false
                reactions.go(things)
            }
        }

        state(things) {
            action {
                reactions.say("Стоп, надо собрать с собой учебники библиотечные и конспекты, новые джинсы, а вдруг сгорят? Так, где тут чемодан…")
            }

            state("No") {
                activators {
                    intent("No")
                }
                action {
                    context.checkpoints.GetExtraClothes = false
                    reactions.go(StartFloorScenario.state)
                }
            }

            fallback {
                context.checkpoints.GetExtraClothes = true
                reactions.go(StartFloorScenario.state)
            }
        }
    }
}