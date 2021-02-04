package com.dev.abhinav.tennisstats

import android.Manifest
import android.app.Activity
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.telephony.SmsManager
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import com.karumi.dexter.Dexter
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.PermissionListener


class FirstScreenActivity : AppCompatActivity() {

    private lateinit var cardView1: CardView
    private lateinit var cardView2: CardView
    private lateinit var feedback: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_first_screen)

        cardView1 = findViewById(R.id.cardview1)
        cardView2 = findViewById(R.id.cardview2)
        feedback = findViewById(R.id.feedback)

        cardView1.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("click", "atp")
            startActivity(intent)
        }

        cardView2.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("click", "wta")
            startActivity(intent)
        }

        feedback.setOnClickListener {
            smsPermission()
            val builder = AlertDialog.Builder(this)
            builder.setMessage("Send Feedback")
            val dialogLayout: View = layoutInflater.inflate(R.layout.feedback_dialog, null)
            val textBox = dialogLayout.findViewById<EditText>(R.id.textbox)
            builder.setView(dialogLayout)
            builder.setPositiveButton("OK") { dialog, _ ->
                dialog.dismiss()
                val msg = textBox.text.toString()

//                val to = arrayOf("abhinavp403@gmail.com")
//                val emailIntent = Intent(Intent.ACTION_SEND)
//                emailIntent.data = Uri.parse("mailto:")
//                emailIntent.type = "text/plain"
//                emailIntent.putExtra(Intent.EXTRA_EMAIL, to)
//                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Tennis Stats App Feedback")
//                emailIntent.putExtra(Intent.EXTRA_TEXT, msg)
//                startActivity(Intent.createChooser(emailIntent, "Sent!"))
//                finish()

//                val smsManager: SmsManager = SmsManager.getDefault()
//                smsManager.sendTextMessage("+14138014305", null, msg, null, null)
//                Toast.makeText(applicationContext, "Message Sent", Toast.LENGTH_LONG).show()

                  sendSMS("+14138014305", msg)
            }
            builder.setNegativeButton("Cancel") { dialog, _ ->
                dialog.cancel()
            }
            builder.create()
            builder.show()
        }
    }

    private fun sendSMS(phoneNumber: String, message: String) {
        val SENT: String = "SMS_SENT"
        val DELIVERED: String = "SMS_DELIVERED"

        val sentPI = PendingIntent.getBroadcast(this, 0, Intent(SENT), 0)
        val deliveredPI = PendingIntent.getBroadcast(this, 0, Intent(DELIVERED), 0)

        // ---when the SMS has been sent---
        val broadCastReceiver: BroadcastReceiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                when (resultCode) {
                    Activity.RESULT_OK -> {
                        Toast.makeText(context, "SMS sent", Toast.LENGTH_SHORT).show()
                    }
                    SmsManager.RESULT_ERROR_GENERIC_FAILURE -> {
                        Toast.makeText(context, "Generic failure", Toast.LENGTH_SHORT).show()
                    }
                    SmsManager.RESULT_ERROR_NO_SERVICE -> {
                        Toast.makeText(context, "No service", Toast.LENGTH_SHORT).show()
                    }
                    SmsManager.RESULT_ERROR_NULL_PDU -> {
                        Toast.makeText(context, "Null PDU", Toast.LENGTH_SHORT).show()
                    }
                    SmsManager.RESULT_ERROR_RADIO_OFF -> {
                        Toast.makeText(context, "Radio off", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
        registerReceiver(broadCastReceiver, IntentFilter(SENT))

        // ---when the SMS has been delivered---
        val broadCastSender: BroadcastReceiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                when(resultCode) {
                    Activity.RESULT_OK -> {
                        Toast.makeText(context, "SMS delivered", Toast.LENGTH_SHORT).show()
                    }
                    Activity.RESULT_CANCELED -> {
                        Toast.makeText(context, "SMS not delivered", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
        registerReceiver(broadCastSender, IntentFilter(DELIVERED))

        val sms = SmsManager.getDefault()
        sms.sendTextMessage(phoneNumber, null, message, sentPI, deliveredPI)
    }

    private fun smsPermission() {
        Dexter.withContext(this)
                .withPermission(Manifest.permission.SEND_SMS)
                .withListener(object : PermissionListener {
                    override fun onPermissionGranted(response: PermissionGrantedResponse) {
                        return
                    }

                    override fun onPermissionDenied(response: PermissionDeniedResponse) {
                    }

                    override fun onPermissionRationaleShouldBeShown(permission: PermissionRequest?, token: PermissionToken?) {
                        token!!.continuePermissionRequest()
                    }
                }).check()
    }
}