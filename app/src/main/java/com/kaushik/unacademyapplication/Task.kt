package com.kaushik.unacademyapplication

import android.os.Handler
import android.os.Looper
import java.util.concurrent.Executor
import java.util.concurrent.Executors

interface Task<T> {
    fun onExecuteTask(): T
    fun onTaskComplete(result: T)
}

class ServiceWorker<T>(val name: String) {
    private val executor: Executor = Executors.newSingleThreadExecutor()
    private val uiHandler: Handler = Handler(Looper.getMainLooper())

    fun addTask(task: Task<T>) {
        executor.execute {
            val result = task.onExecuteTask()
            uiHandler.post {
                task.onTaskComplete(result)
            }
        }
    }
}