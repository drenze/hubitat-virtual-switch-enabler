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
    iconX3Url: "",
    installOnOpen: true
)

preferences {
    page(name: "pageMain")
    page(name: "pageRemove")
}

def pageMain() {
    dynamicPage(name: "pageMain", title: "", install: true, uninstall: false) {
        section("<h1>Enabled Virtual Switch</h1>") {
            paragraph "Turn a switch on/off only when enabled. " +
                "Primarily used for enabling/disabling groups and scenes."
        }

        section("<h2>Add New Enabled Virtual Switch</h2>") {
            app(
                name: "childApps",
                appName: "Enabled Virtual Switch-1.x",
                namespace: "net.devizo.evs",
                title: "Add new enabled virtual switch.",
                multiple: true
            )
        }

        section("<h2>Remove Enabled Virtual Switch</h2>") {
            href "pageRemove",
                title: "Remove Enabled Virtual Switch",
                description: "Remove Enabled Virtual Switch app and all " +
                    "switches."
        }
    }
}

def pageRemove() {
	dynamicPage(name: "pageRemove", title: "", install: false, uninstall: true) {
		section("<h2>Warning</h2>") {			
			paragraph "You are about to remove Enabled Virtual Switch and " +
                "<span style='font-weight:bold'>all</span> Enabled Virtual " +
                "Switches you've created. You will need to remove any " +
                "virtual switch devices manually.", required: true, state: null
		}
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

