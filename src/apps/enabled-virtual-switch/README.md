# Enabled Virtual Switch

## Summary

Turn a switch on/off only when enabled.

## Description

Enabled Virtual Switch (EVS) is a small application, which allows a device, group, or scene to be controlled, and enabled/disabled by a second switch. EVS was written to facilitate enabling/disabling holiday groups and scenes during the appropriate seasons, to avoid the need to seasonally delete and recreate rules.

This is an easy rule to implement via Rule Manager, as seen here:

![Rule Manager Implementation of EVS](img/evs-rm4.png)

However, it was rewritten in Groovy, following the philosophy of, "If it will be used frequently, turn it into an app."

## Usage

To create an EVS instance:

  1. Create three switches:
     * **Control Switch:** Switch to be used to turn the group, scene, or device on/off; this switch will usually be used on dashboards, etc.
     * **Enabled Switch:** Switch to be used to enable/disable the group, scene, or device; must be *on* before turning the Control Switch on/off.
     * **Target Switch:** Switch which directly turns the group, scene, or device on/off.

  2. Create EVS instance:
     * If EVS hasn't been installed, install through **Hubitat Package Manager** or directly from source.
     * If EVS hasn't already been added, click "Add App," then add, otherwise select the EVS parent app.
     * Select switches, as prompted.


## Credit

EVS is my first application written for Hubitat and wouldn't have been written without being able to use the applications in [Robert Morris'](https://github.com/RMoRobert/Hubitat) repository as an example, especially TimedSwitchHelper.