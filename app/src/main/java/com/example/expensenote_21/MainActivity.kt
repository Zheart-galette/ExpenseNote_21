package com.example.expensenote_21

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    //สร้างตัวแปรสำหรับหน้า UI Element
    private lateinit var etAmount: EditText
    private  lateinit var etNote: EditText
    private lateinit var btnAdd: Button
    private lateinit var btnClear: Button
    private lateinit var tvLastItem: TextView
    private lateinit var tvTotal: TextView

    private  var total: Double = 0.0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        etAmount = findViewById<EditText>(R.id.TfAmount)
        etNote = findViewById<EditText>(R.id.TfNote)
        btnAdd = findViewById<Button>(R.id.TfAdd)
        btnClear = findViewById<Button>(R.id.TfClear)
        tvLastItem = findViewById<TextView>(R.id.TfLastItem)
        tvTotal = findViewById<TextView>(R.id.TfTotal)

        btnAdd.setOnClickListener {
            val amountText = etAmount.text.toString().trim()
            val noteText = etNote.text.toString().trim()
            //ตรวจสอบเงื่อนไข
            if (amountText.isEmpty()){
                Toast.makeText(this, "กรุณากรอกจำนวนเงิน", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            //ตรวจสอบว่าผู้ใช้งานกรอกข้อมูลช่องจำนวนเงิน เป็นตัวเลขรุึป่าว
            val amount = amountText.toDoubleOrNull()
            if (amount == null){
                Toast.makeText(this, "กรุณากรอกจำนวนเงินเป็นตัวเลข", Toast.LENGTH_SHORT).show()
                return@setOnClickListener

            }
            //ทำการบวกจำนวนเงินเข้าไปในยอดสะสม
            total += amount

            //ตรวจสอบว่าช่องคำอธิบายมีการกรอกข้อมูลมารีป่าว ถ้าไม่มีให้ใช้เป็นคำว่า "ไม่ระบุ"
            val showNote = if (noteText.isNotEmpty()) noteText else "ไม่ระบุ"

            tvLastItem.text = "รายการล่าสุด: $showNote - %.2f บาท".format(amount)
            tvTotal.text = "ยอดรวมวันนี้: %.2f บาท".format(total)
            etAmount.text.clear()
            etNote.text.clear()
        }
        btnClear.setOnClickListener {
            total = 0.0
            etAmount.text.clear()
            etNote.text.clear()

            tvLastItem.text = "รายการ: -"
            tvTotal.text = "ยอดรวมววันนี้ 0.00 บาท"
        }
    }
}