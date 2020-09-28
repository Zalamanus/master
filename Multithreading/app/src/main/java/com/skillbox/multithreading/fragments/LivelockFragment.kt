package com.skillbox.multithreading.fragments

import android.util.Log
import androidx.fragment.app.Fragment
import java.util.concurrent.TimeUnit
import java.util.concurrent.locks.Lock
import java.util.concurrent.locks.ReentrantLock

class LivelockFragment : Fragment() {
    private val lock1: Lock = ReentrantLock(true)
    private val lock2: Lock = ReentrantLock(true)

    override fun onResume() {
        super.onResume()



        val thread1 = Thread {
            Log.d("Livelock1", "Start1")
            while (true) {
                lock1.tryLock(500,TimeUnit.MILLISECONDS)
                Log.d("Livelock1","lock1 acquired, trying to acquire lock2.")
                Thread.sleep(500)
                // decomment the line bellow to get rid of Livelock
                if (lock2.tryLock(500,TimeUnit.MILLISECONDS)) {
                // decomment the line bellow to get Livelock
//                if (lock2.tryLock()) {
                    Log.d("Livelock1","lock2 acquired.")
                } else {
                    Log.d("Livelock1","cannot acquire lock2, releasing lock1.")
                    lock1.unlock()
                    continue
                }

                Log.d("Livelock1","Running1")
                break
            }
            lock2.unlock()
            lock1.unlock()
            Log.d("Livelock1", "End1")
        }

        val thread2 = Thread {
            Log.d("Livelock2", "Start2")

            while (true) {
                lock2.tryLock(500,TimeUnit.MILLISECONDS)
                Log.d("Livelock2","lock2 acquired, trying to acquire lock1.")
                Thread.sleep(500)
                // decomment the line bellow to get rid of Livelock
                if (lock1.tryLock(500,TimeUnit.MILLISECONDS)) {
                // decomment the line bellow to get Livelock
//                if (lock1.tryLock()) {
                    Log.d("Livelock2","lock1 acquired.")
                } else {
                    Log.d("Livelock2","cannot acquire lock1, releasing lock2.")
                    lock2.unlock()
                    continue
                }

                Log.d("Livelock2","Running2")
                break
            }
            lock1.unlock()
            lock2.unlock()

            Log.d("Livelock2", "End2")
        }

        thread1.start()
        thread2.start()
    }


}