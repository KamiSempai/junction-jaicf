package com.justai.jaicf.template.state

import java.io.Serializable

class CheckPoints: Serializable {
    var KeepCalm: Boolean? = null
    var CallFirefighters: Boolean? = null
    var OpenWindow: Boolean? = null
    var GetExtraClothes: Boolean? = null
    var CheckDoorknob: Boolean? = null
    var LeaveDoorClosed: Boolean? = null
    var ChooseEmergencyPath: EmergencyPaths? = null
    var ActivateFireAlarm: Boolean? = null
    var RunOnStair: Boolean? = null
    var GoToSmoke: Boolean? = null
    var FillGap: Boolean? = null
    var AlertFirefighters: Boolean? = null
}