package com.mitsuki.jlpt.base

import android.view.View
import androidx.recyclerview.widget.BindAwareViewHolder
import com.uber.autodispose.lifecycle.CorrespondingEventsFunction
import com.uber.autodispose.lifecycle.LifecycleEndedException
import com.uber.autodispose.lifecycle.LifecycleScopeProvider
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject

/**
 * Example implementation of a [ViewHolder] implementation that implements
 * [LifecycleScopeProvider]. This could be useful for cases where you have subscriptions that should be
 * disposed upon unbinding or otherwise aren't overwritten in future binds.
 */
abstract class AutoDisposeViewHolder(itemView: View) :
    BindAwareViewHolder(itemView), LifecycleScopeProvider<AutoDisposeViewHolder.ViewHolderEvent> {

    private val lifecycleEvents by lazy { BehaviorSubject.create<ViewHolderEvent>() }

    enum class ViewHolderEvent {
        BIND, UNBIND
    }

    override fun onBind() = lifecycleEvents.onNext(ViewHolderEvent.BIND)

    override fun onUnbind() = lifecycleEvents.onNext(ViewHolderEvent.UNBIND)

    override fun lifecycle(): Observable<ViewHolderEvent> = lifecycleEvents.hide()

    override fun correspondingEvents(): CorrespondingEventsFunction<ViewHolderEvent> = CORRESPONDING_EVENTS

    override fun peekLifecycle(): ViewHolderEvent? = lifecycleEvents.value

    companion object {

        private val CORRESPONDING_EVENTS = CorrespondingEventsFunction<ViewHolderEvent> { viewHolderEvent ->
            when (viewHolderEvent) {
                ViewHolderEvent.BIND -> ViewHolderEvent.UNBIND
                else -> throw LifecycleEndedException(
                    "Cannot use ViewHolder lifecycle after unbind.")
            }
        }
    }
}