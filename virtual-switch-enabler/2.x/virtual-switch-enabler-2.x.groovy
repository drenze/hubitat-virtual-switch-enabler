/**********************************************************************
 * Copyright 2021 Douglas J. Renze
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

definition(
    name: "Virtual Switch Enabler-2.x",
    namespace: "net.devizo.vse-2.x",
    author: "Douglas J. Renze",
    parent: "net.devizo.vse-2.x:Virtual Switch Enabler",
    description: "Do not install directly; use Virtual Switch Enabler app instead.",
    category: "convenience",
    iconUrl: "",
    iconX2Url: "",
    iconX3Url: ""
)

preferences {
    section("<h2>Virtual Switch Enabler</h2>") {
        paragraph "Turn a switch on/off only when enabled"
    }

    section("<h2>Switches</h2>") {
        paragraph "When the first (control) switch is turned on/off, " +
            "turn the target switches on/off only if the third " +
            "(enabled) switch is already on. See " +
            "<a href='https://github.com/drenze/hubitat-misc/blob/main/src/apps/enabled-virtual-switch/2.x/README.md' target='_doc'>documentation</a> " +
            "for additional details."
    }

    section("<h3>Note</h3>") {
        paragraph "All devices must be created before creating Virtual Switch Enabler."
    }

    section("") {
        input "controlSwitch", "capability.switch",
            title: "When this switch is turned on or off",
            multiple: false,
            required: true
        input "targetSwitches", "capability.switch",
            title: "Then turn these switches on or off",
            multiple: true,
            required: true
        input "enabledSwitch", "capability.switch",
            title: "Only if this switch is already on",
            multiple: false,
            required: true
        input "debugLogging", "bool", title: "Enable debug logging"
    }
}

def logDebug(msg) {
    if (debugLogging) {
        log.debug(msg)
    }
}

def logWarn(msg) {}

def installed() {
    log.info "Installed..."
    initialize()
}

def updated() {
    log.info "Updated..."
    initialize()
}

def initialize() {
    log.info "Initializing: subscribing to events"
    unsubscribe()
    unschedule()
    subscribe(controlSwitch, "switch", controlSwitchHandler)
}

/**********************************************************************
 * When controlSwitch is turned on or off, turn targetSwitch on or
 * off only if enabledSwitch is already on, otherwise immediately
 * turn controlSwitch off again without updating targetSwitch.
 */
def controlSwitchHandler(evt) {
    logDebug "${enabledSwitch.getName()} is ${enabledSwitch.currentValue('switch')}"
    if(evt.value == "on") {
        // Only turn `targetSwitches` on if `enabledSwitch` is on.
        if (enabledSwitch.currentValue("switch") == "on") {
            targetSwitches.each {
                logDebug "turning on ${it.getName()}"
                it.on()
            }
        }

        // Otherwise turn `controlSwitch` back off.
        else if (enabledSwitch.currentValue("switch") == "off") {
            logDebug "turning off ${controlSwitch.getName()}"
            controlSwitch.off()
        }
    }

    // Always turn `targetSwitches` off, even if `enabledSwitch` is also off.
    else if (evt.value == "off") {
        targetSwitches.each {
            logDebug "turning off ${it.getName()}"
            it.off()
        }
    }
}
