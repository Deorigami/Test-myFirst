package com.cmp.template.di
import dev.jordond.connectivity.Connectivity
actual fun createConnectivity(): Connectivity = Connectivity()
