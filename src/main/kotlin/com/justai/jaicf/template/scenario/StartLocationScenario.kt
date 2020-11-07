package com.justai.jaicf.template.scenario

import com.justai.jaicf.model.scenario.Scenario
import com.justai.jaicf.template.state.EmergencyPaths
import com.justai.jaicf.template.state.checkpoints

object StartLocationScenario : Scenario() {

    const val state = "/location/start"
    private const val window = "/location/window"
    private const val door = "/location/door"
    private const val things = "/location/things"
    private const val doorOutside = "/location/doorOutside"
    private const val choosePath = "/location/choosePath"
    private const val elevator = "/location/elevator"
    private const val redButton = "/location/redButton"
    private const val stair = "/location/stair"
    private const val forthFlat = "/location/forthFlat"

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

            state("Yes")
            {
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
                    reactions.go(doorOutside)
                }
            }

            fallback {
                context.checkpoints.GetExtraClothes = true
                reactions.go(doorOutside)
            }
        }

        state(doorOutside) {
            action {
                reactions.say("Вышел, дверь закрыть или оставить?")
            }

            state("Yes") {
                activators {
                    intent("Yes")
                }

                action {
                    context.checkpoints.LeaveDoorClosed = true
                    reactions.go(choosePath)
                }
            }

            fallback {
                context.checkpoints.LeaveDoorClosed = true
                reactions.go(choosePath)
            }
        }

        state(choosePath) {
            action {
                reactions.say("Окей, я в коридоре, тут тоже дым. Непонятно, что и где горит. Надо эвакуироваться. Лестница справа, лифты слева, куда бежать?")
            }

            state("Stair") {
                activators {
                    regex("right")
                }

                action {
                    reactions.say("Да, кажется, что-то такое нам и говорили на инструктаже по безопасности, жаль, я всё прослушал. Бегу.")
                    context.checkpoints.ChooseEmergencyPath = EmergencyPaths.Stair
                    reactions.go(redButton)
                }
            }

            fallback {
                reactions.say("Побежал к лифтам")
                context.checkpoints.ChooseEmergencyPath = EmergencyPaths.Elevator
                reactions.go(elevator)
            }
        }

        state(elevator) {
            action {
                reactions.say("Окей, я добежал до лифтов, вызвал.")
                reactions.say("Похоже, лифты отключились, и я только зря потратил время… Побегу на лестницу.")
                context.checkpoints.KeepCalm = false
                reactions.go(redButton)
            }
        }

        state(redButton) {
            action {
                reactions.say("Тут на стене какая-то красная кнопка под стеклом, нажать или не тратить время?")
            }

            state("Push it!")
            {
                activators {
                    intent("PressAlarm")
                }
                action {
                    reactions.say("Врубилась тревога")
                    context.checkpoints.ActivateFireAlarm = true
                    reactions.go(stair)
                }
            }

            fallback {
                context.checkpoints.ActivateFireAlarm = false
                reactions.go(stair)
            }
        }

        state(stair) {
            action {
                reactions.say("Так, я на лестнице, бегу вниз. Тут, правда, очень темно, поэтому я лучше ускорюсь, чтобы поскорее отсюда выбраться.")
            }

            state("Don't run") {
                activators {
                    intent("No")
                }

                action {
                    reactions.say("Ступенька, ещё ступенька и… дошёл, отлично.")
                    context.checkpoints.RunOnStair = false
                    reactions.go(forthFlat)
                }
            }

            fallback {
                reactions.say("Чёрт, я споткнулся на лестнице и, кажется, подвернул ногу! Как же больно. Хорошо хоть идти могу. Но такими темпами я могу и не успеть выбраться!")
                context.checkpoints.RunOnStair = true
                context.checkpoints.KeepCalm = false
                reactions.go(forthFlat)
            }
        }

        state(forthFlat) {
            action {
                reactions.say("Я на площадке пятого этажа, но снизу валит дым! Что мне делать?")
            }

            state("Go to room") {
                activators {
                    regex("Stop")
                }

                action {
                    reactions.say("Отлично, кухня открыта, запрусь здесь.")
                    context.checkpoints.GoToSmoke = false
                    reactions.go(FloorFiveLocationScenario.state)
                }
            }

            fallback {
                reactions.say("Кх-кх-кх! Ффу! Ничего не видно, я только зря дыма наглотался. Пойду лучше закроюсь на кухне и буду ждать пожарных.")
                context.checkpoints.GoToSmoke = true
                reactions.go(FloorFiveLocationScenario.state)
            }
        }
    }
}