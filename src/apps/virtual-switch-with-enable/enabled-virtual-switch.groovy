/**********************************************************************
 * Copyright 2020 Douglas J. Renze
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
    name: "Enabled Virtual Switch",
    namespace: "net.devizo.evs",
    author: "Douglas J. Renze",
    singleInstance: true,
    description: "Turn a switch on/off only when enabled",
    category: "Convenience",
    iconUrl: "",
    iconX2Url: "",
    iconX3Url: ""
)

preferences {
    section("") {
        paragraph title: "Enabled Virtual Switch",
            "Turn a switch on/off only when enabled"
    }

    section {
        app(
            name: "childApps",
            appName: "Enabled Virtual Switch-1.x",
            namespace: "net.devizo.evs",
            title: "Add new enabled virtual switch.",
            multiple: true
        )
    }
}

def initialize() {
    log.debug "Initializing: there are ${childApps?.size()} enabled virtual switches installed."
    childApps?.each {
        child->log.debug "  enabled virtual switch: ${child.label}"
    }
}

def installed() {
    log.debug "Installed with settings: ${settings}"
    intitialize()
}

def updated() {
    log.debug "Updated with settings: ${settings}"
    unsubscribe()
    initialize()
}

