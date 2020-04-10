package com.kaushik.unacademyapplication

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.AppCompatButton
import kotlinx.android.synthetic.main.activity_main.*
import okhttp3.OkHttpClient
import okhttp3.Request

class MainActivity : AppCompatActivity() {

    val serviceWorker1: ServiceWorker<Bitmap> by lazy {
        ServiceWorker<Bitmap>("service_worker_1")
    }

    val serviceWorker2: ServiceWorker<Bitmap> by lazy {
        ServiceWorker<Bitmap>("service_worker_2")
    }

    val okHttpClient: OkHttpClient by lazy {
        OkHttpClient.Builder().build()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn1.setOnClickListener {
            fetchImage1()
        }

        btn2.setOnClickListener {
            fetchImage2()
        }
    }

    private fun fetchImage1() {
        serviceWorker1.addTask(object: Task<Bitmap> {
            override fun onExecuteTask(): Bitmap {
                val request = Request.Builder().url(IMAGE1).build()
                val response = okHttpClient.newCall(request).execute()
                val bitmap = BitmapFactory.decodeStream(response.body?.byteStream())
                return bitmap
            }

            override fun onTaskComplete(result: Bitmap) {
                image1.setImageBitmap(result)
            }
        })
    }

    private fun fetchImage2() {
        serviceWorker2.addTask(object: Task<Bitmap> {
            override fun onExecuteTask(): Bitmap {
                val request = Request.Builder().url(IMAGE2).build()
                val response = okHttpClient.newCall(request).execute()
                val bitmap = BitmapFactory.decodeStream(response.body?.byteStream())
                return bitmap
            }

            override fun onTaskComplete(result: Bitmap) {
                image2.setImageBitmap(result)
            }
        })
    }

    companion object {
        const val IMAGE1 = "https://live.staticflickr.com/5295/5538610252_3830635d38_w.jpg"
        const val IMAGE2 = "https://live.staticflickr.com/8400/8627782394_4beec6dbbd_m.jpg"
    }
}
