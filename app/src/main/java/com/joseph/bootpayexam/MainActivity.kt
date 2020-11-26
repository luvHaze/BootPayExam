package com.joseph.bootpayexam

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.FragmentActivity
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_main.*
import kr.co.bootpay.Bootpay
import kr.co.bootpay.BootpayAnalytics
import kr.co.bootpay.enums.Method
import kr.co.bootpay.enums.PG
import kr.co.bootpay.enums.UX
import kr.co.bootpay.listener.ConfirmListener
import kr.co.bootpay.model.BootExtra
import kr.co.bootpay.model.BootUser
import org.xml.sax.Parser

class MainActivity : AppCompatActivity() {

    // 수량
    private var stuck = 10

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        BootpayAnalytics.init(this, "API_KEY")

        play_payment_button.setOnClickListener {
            requestPayment()
        }
    }

    fun requestPayment() {
        var bootUser = BootUser().apply {
            this.phone = "010-1234-5678"
        }

        var bootExtra = BootExtra().apply {
            setQuotas(intArrayOf(0, 2, 3))
        }

        Bootpay.init(this)
            .setApplicationId("API_KEY")
            .setPG(PG.KCP)
            .setContext(this@MainActivity)
            .setBootUser(bootUser)
            .setBootExtra(bootExtra)
            .setUX(UX.PG_DIALOG)
            .setName("개쩌는 인삼")
            .setOrderId("1222")
            .setPrice(1000)
            .onConfirm { message ->
                if (0 < stuck) Bootpay.confirm(message)
                else Bootpay.removePaymentWindow()
                Log.d("Confirm", message)
            }
            .onDone {
                Log.d("Done", it)
            }
            .onReady {
                Log.d("Ready", it)
            }
            .onCancel {
                Log.d("Cancel", it)
            }
            .onError {
                Log.d("Error", it)
            }
            .onClose {
                Log.d("Close", it)
            }
            .request()
    }
}