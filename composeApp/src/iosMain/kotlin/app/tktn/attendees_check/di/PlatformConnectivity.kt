package app.tktn.attendees_check.di

import dev.jordond.connectivity.Connectivity

actual fun createConnectivity(): Connectivity = Connectivity()
