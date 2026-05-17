package com.cmp.template.core_feature.base

/**
 * Base interface for all screen events.
 *
 * Each sealed subtype must implement [dispatch], routing itself to the
 * correct function on [H] (the handler / screen model).
 *
 * This eliminates `when` blocks in models entirely — the model just
 * implements [H] and each function IS the handler for that event.
 *
 * Example event definition:
 * ```
 * sealed interface MyEvent : ScreenEvent<MyEvent.Handler> {
 *
 *     data object Load : MyEvent {
 *         override fun dispatch(handler: Handler) = handler.onLoad()
 *     }
 *
 *     data class Submit(val value: String) : MyEvent {
 *         override fun dispatch(handler: Handler) = handler.onSubmit(value)
 *     }
 *
 *     interface Handler {
 *         fun onLoad()
 *         fun onSubmit(value: String)
 *     }
 * }
 * ```
 *
 * Example model — only implements handler functions, no when block:
 * ```
 * class MyScreenModel : BaseScreenModel<MyState, MyEvent, MyEvent.Handler>(...),
 *                       MyEvent.Handler {
 *     override fun onLoad()               { ... }
 *     override fun onSubmit(value: String){ ... }
 * }
 * ```
 */
interface ScreenEvent<H> {
    fun dispatch(handler: H)
}

