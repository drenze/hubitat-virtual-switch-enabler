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
    name: "Virtual Switch Enabler (Deprecated)",
    namespace: "net.devizo.vse",
    author: "Douglas J. Renze",
    singleInstance: true,
    description: "Turn a switch on/off only when enabled",
    documentationLink: "https://github.com/drenze/hubitat-misc/blob/main/src/apps/virtual-switch-enabler/README.md",
    category: "Convenience",
    iconUrl: "",
    iconX2Url: "",
    iconX3Url: "",
    installOnOpen: true
)

preferences {
    page(name: "pageMain")
    page(name: "pageRemove")
}

def pageMain() {
    dynamicPage(name: "pageMain", title: "", install: true, uninstall: false) {
        section("<h1>Virtual Switch Enabler</h1>") {
            paragraph "Turn a switch on/off only when enabled. " +
                "Primarily used for enabling/disabling groups and scenes."
        }

        section("<h2>Add New Virtual Switch Enabler</h2>") {
            app(
                name: "childApps",
                appName: "Virtual Switch Enabler-1.x",
                namespace: "net.devizo.vse",
                title: "Add new Virtual Switch Enabler.",
                multiple: true
            )
        }

        section("<h2>Remove Virtual Switch Enabler</h2>") {
            href "pageRemove",
                title: "Remove Virtual Switch Enabler",
                description: "Remove Virtual Switch Enabler app and all " +
                    "switches."
        }
    }
}

def pageRemove() {
    dynamicPage(name: "pageRemove", title: "", install: false, uninstall: true) {
        section("<h2>Warning</h2>") {
            paragraph "You are about to remove Virtual Switch Enabler and " +
                "<span style='font-weight:bold'>all</span> Enabled Virtual " +
                "Switches you've created. You will need to remove any " +
                "virtual switch devices manually.", required: true, state: null
        }
    }
}

def initialize() {
    log.debug "Initializing: there are ${childApps?.size()} Virtual Switch Enableres installed."
    childApps?.each {
        child->log.debug "  Virtual Switch Enabler: ${child.label}"
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
