package com.skillbox.multithreading.fragments

import android.util.Log
import androidx.fragment.app.Fragment

class DeadlockFragment : Fragment() {

    private var i = 0
    private val lock1 = Any()
    private val lock2 = Any()

    override fun onResume() {
        super.onResume()

        val thread1 = Thread {
            Log.d("Deadlock", "Start1")

            // decomment the block bellow to get rid of Deadlock
//            (0..1000000).forEach { _ ->
//                synchronized(lock1) {
//                    i++
//                }
//            }

            // decomment the block bellow to get Deadlock
            (0..1000000).forEach { _ ->
                synchronized(lock1) {
                    synchronized(lock2) {
                        i++
                    }
                }
            }


            Log.d("Deadlock", "End1 i = $i")
        }

        val thread2 = Thread {
            Log.d("Deadlock", "Start2")

            // decomment the block bellow to get rid of Deadlock
//            (0..1000000).forEach { _ ->
//                synchronized(lock1) {
//                    i++
//                }
//            }

            // decomment the block bellow to get Deadlock
            (0..1000000).forEach { _ ->
                synchronized(lock2) {
                    synchronized(lock1) {
                        i++
                    }
                }
            }

            Log.d("Deadlock", "End2 i = $i")
        }

        thread1.start()
        thread2.start()
    }


}