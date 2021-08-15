package com.ckh.lottonumberchoose

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

import android.widget.Button
import android.widget.NumberPicker
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible


class MainActivity : AppCompatActivity() {
    private val resetBtn:Button by lazy {findViewById(R.id.resetBtn)}
    private val addBtn:Button by lazy {findViewById(R.id.addBtn)}
    private val randomBtn:Button by lazy {findViewById(R.id.randomBtn)}
    private val numberPicker:NumberPicker by lazy {findViewById(R.id.numberPicker)}
    private var didRun = false
    private val pickNumberSet = hashSetOf<Int>()
    private val numberTextView:List<TextView> by lazy {
        listOf(
            findViewById(R.id.firstNumber),
            findViewById(R.id.secondNumber),
            findViewById(R.id.thirdNumber),
            findViewById(R.id.fourthNumber),
            findViewById(R.id.fifthNumber),
            findViewById(R.id.sixthNumber)
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        numberPicker.minValue = 1
        numberPicker.maxValue = 45
        initRunButton()
        initAddButton()
        initResetButton()
    }
    private fun initRunButton() {
        randomBtn.setOnClickListener {
            val list = getRandomNumber()
            didRun = true
            list.forEachIndexed{ index,number ->
                val textView = numberTextView[index]
                textView.text = number.toString()
                textView.isVisible = true
                setBackground(number,textView)
            }
            Log.d("MainActivity",list.toString())
        }
    }
    private  fun initAddButton() {
        addBtn.setOnClickListener {
            if(didRun){
                Toast.makeText(this,"초기화 후 시도해주세요",Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if(pickNumberSet.size >=5) {
                Toast.makeText(this,"최대 5개까지만 선택 가능합니다",Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if(pickNumberSet.contains(numberPicker.value)){
                Toast.makeText(this,"중복값은 불가합니다",Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            val textView = numberTextView[pickNumberSet.size]
            textView.isVisible = true
            textView.text=numberPicker.value.toString()
            setBackground(numberPicker.value,textView)
            pickNumberSet.add(numberPicker.value)

        }
    }
    private fun initResetButton(){
        resetBtn.setOnClickListener {
            pickNumberSet.clear()
            numberTextView.forEach {
                it.isVisible=false
            }
            didRun = false
        }
    }
    private fun getRandomNumber():List<Int> {
        val numberList = mutableListOf<Int>().apply {
            for(i in 1 .. 45) {
                if (pickNumberSet.contains(i)){
                    continue
                }
                this.add(i)
            }
        }
        numberList.shuffle()
        val newList = pickNumberSet.toList() + numberList.subList(0,6 - pickNumberSet.size)
        return  newList.sorted()
    }
    private fun setBackground(number:Int , textView: TextView){
        when(number){
            in 1..9 ->  textView.background = ContextCompat.getDrawable(this,R.drawable.circle_gray)
            in 10..19 ->  textView.background = ContextCompat.getDrawable(this,R.drawable.circle_blue)
            in 20..29 ->  textView.background = ContextCompat.getDrawable(this,R.drawable.circle_green)
            in 30..39 ->  textView.background = ContextCompat.getDrawable(this,R.drawable.circle_yellow)
            else -> textView.background = ContextCompat.getDrawable(this,R.drawable.circle_red)
        }  //drewble꺼내오기
    }
}
