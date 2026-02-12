package app.tktn.attendees_check.di

import dev.jordond.connectivity.Connectivity
import dev.jordond.connectivity.PollResult

actual fun createConnectivity(): Connectivity = Connectivity {
	pollingIntervalMs = 10.seconds
	timeoutMs = 2.seconds
	onPollResult { result ->
		when (result) {
			is PollResult.Error -> println("Poll error: ${result.throwable.message}")
			is PollResult.Response -> println("Poll http response: ${result.response}")
		}
	}
}
