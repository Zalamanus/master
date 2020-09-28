package com.example.viewandlayout

import android.util.Log
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.OnLifecycleEvent

class MyLifecycleObserver : LifecycleObserver {
    companion object {
        val TAG = "LifecycleObserver"
    }
    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun logOnCreate(source: LifecycleOwner) {
        Log.v(TAG, "State=${source.lifecycle.currentState.toString()} Event=OnCreate")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun logOnStart(source: LifecycleOwner) {
        Log.d(TAG, "State=${source.lifecycle.currentState.toString()} Event=OnStart")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun logOnResume(source: LifecycleOwner) {
        Log.i(TAG, "State=${source.lifecycle.currentState.toString()} Event=OnResume")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun logOnPause(source: LifecycleOwner) {
        Log.w(TAG, "State=${source.lifecycle.currentState.toString()} Event=OnPause")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun logOnStop(source: LifecycleOwner) {
        Log.e(TAG, "State=${source.lifecycle.currentState.toString()} Event=OnStop")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun logOnDestroy(source: LifecycleOwner) {
        Log.v(TAG, "State=${source.lifecycle.currentState.toString()} Event=OnDestroy")
    }
}