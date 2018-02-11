package com.maddog05.sampleutils.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.AppCompatButton
import android.support.v7.widget.AppCompatEditText
import android.support.v7.widget.AppCompatTextView
import com.maddog05.maddogutilities.string.Strings
import com.maddog05.sampleutils.R

/*
 * Created by andreetorres on 10/02/18.
 */
class BolderDemoActivity : AppCompatActivity() {

    private lateinit var inputEt: AppCompatEditText
    private lateinit var normalBtn: AppCompatButton
    private lateinit var boldBtn: AppCompatButton
    private lateinit var clearBtn: AppCompatButton
    private lateinit var resultTv: AppCompatTextView

    private val bolder = Strings.CharSequenceStyle()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bolder_demo)
        inputEt = findViewById(R.id.et_bolder_input)
        normalBtn = findViewById(R.id.btn_bolder_normal)
        boldBtn = findViewById(R.id.btn_bolder_bold)
        clearBtn = findViewById(R.id.btn_bolder_clear)
        resultTv = findViewById(R.id.tv_bolder_result)

        normalBtn.setOnClickListener {
            bolder.addNormal(inputEt.text.toString())
            resultTv.text = bolder.build()
            inputEt.text.clear()
        }

        boldBtn.setOnClickListener {
            bolder.addBold(inputEt.text.toString())
            resultTv.text = bolder.build()
            inputEt.text.clear()
        }

        clearBtn.setOnClickListener {
            bolder.clear()
            resultTv.text = bolder.build()
            inputEt.text.clear()
        }
    }
}