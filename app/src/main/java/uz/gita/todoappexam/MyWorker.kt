package uz.gita.todoappexam

import android.annotation.SuppressLint
import android.app.Activity
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Context.NOTIFICATION_SERVICE
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.work.Worker
import androidx.work.WorkerParameters
import uz.gita.todoappexam.utils.myLog

class MyWorker(context: Context,workerParameters: WorkerParameters):Worker(context,workerParameters) {
    private val CHANNEL_ID = "TODO"

    override fun doWork(): Result {
        myLog("doWork")
        val title = inputData.getString("title").toString()
        val description = inputData.getString("desc").toString()
        val id = inputData.getLong("id",0)
        myLog("$title : $description $id")
        createChannel()
        createNotification(title, description,id)

        return Result.success()
    }
    private fun createChannel() {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.O) {
            myLog("create channel")
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val mChannel = NotificationChannel(CHANNEL_ID, "Todo_Bekhzod", importance)

            val notificationManager = applicationContext.getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(mChannel)
        }
    }

    @SuppressLint("MissingPermission")
    private fun createNotification(title:String,text: String, id: Long) {
//        val intent = Intent(this@MainActivity, SecondActivity::class.java).apply {
//            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
//        }
//        val pendingIntent = PendingIntent.getActivity(this@MainActivity, 0, intent, PendingIntent.FLAG_IMMUTABLE)
        myLog("created notification")
        /*val intent = Intent(applicationContext, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent = PendingIntent.getActivity(applicationContext, 0, intent, PendingIntent.FLAG_IMMUTABLE)*/

        val notificationBuilder = NotificationCompat.Builder(applicationContext, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_todo)
            .setContentTitle(title)
            .setContentText(text)

        NotificationManagerCompat.from(applicationContext).notify(id.toInt(), notificationBuilder.build())
    }

}