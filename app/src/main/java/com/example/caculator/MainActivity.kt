package com.example.caculator

import android.os.Bundle
import android.text.Layout
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.caculator.ui.theme.CaculatorTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CaculatorTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.primary) {
                    var number1 by remember { mutableStateOf("0") }
                    var operator by remember { mutableStateOf("") }
                    var number2 by remember { mutableStateOf("") }
                    var result by remember { mutableStateOf("0") }
                    Column {
                        CurrentScreen(number1,operator,number2)
                        ResultScreen(result)
                        Keyboard(callback={
                            if(operator != ""){
                                if(number2 == "0"){
                                    number2 = it
                                }else{
                                    number2 += it
                                }
                            }else{
                                if(number1 == "0"){
                                    number1 = it
                                }else{
                                    number1 += it
                                }
                            }

                        }, caculate = {
                            if(operator!=""){
                                if (operator=="+"){
                                    result = if(number2 != ""){
                                        (number1.toFloat() + number2.toFloat()).toString()
                                    }  else{
                                        number1
                                    }
                                    number1 = result
                                    number2 = ""
                                    operator = ""
                                }else if(operator == "-"){
                                    result = if(number2 != ""){
                                        (number1.toFloat() - number2.toFloat()).toString()
                                    } else{
                                        number1
                                    }
                                    number1 = result
                                    number2 = ""
                                    operator = ""
                                }else if(operator == "×"){
                                    result = if(number2 != ""){
                                        (number1.toFloat() * number2.toFloat()).toString()
                                    } else{
                                        number1
                                    }
                                    number1 = result
                                    number2 = ""
                                    operator = ""
                                }else if(operator == "÷"){
                                    if(number2 == "0"){
                                        result = "除数不能为0"
                                    }else{
                                        result = if(number2 != ""){
                                            (number1.toFloat() / number2.toFloat()).toString()
                                        } else{
                                            number1
                                        }
                                        number1 = result
                                        number2 = ""
                                        operator = ""
                                    }
                                }else if(operator == "%"){
                                    result = if(number2 != ""){
                                        (number1.toFloat() % number2.toFloat()).toString()
                                    } else{
                                        number1
                                    }
                                    number1 = result
                                    number2 = ""
                                    operator = ""
                                }else if(operator == "ac"){
                                    result = "0"
                                    number1 = "0"
                                    operator = ""
                                    number2 = ""
                                }
                            }
                        }, operator = {
                            if(it == "ac"){
                                operator = it
                            }
                            if(operator == ""){
                                operator = it
                            }
                        },del={
                            if(number2 != ""){
                                val len = number2.length
                                if(len == 1){
                                    number2 = ""
                                }else{
                                    number2 = number2.substring(0,len-1)
                                }
                            }else if(operator != ""){
                                operator = ""
                            }else{
                                val len = number1.length
                                if(len == 1){
                                    number1 = "0"
                                }else{
                                    number1 = number1.substring(0,len-1)
                                }

                            }
                        })
                    }

                }
            }
        }
    }
}

@Composable
fun HisScreen(){

}
@Composable
fun CurrentScreen(value:String,operator:String,value2:String){
    Text(modifier = Modifier
        .fillMaxWidth().padding(10.dp,0.dp,10.dp,0.dp) ,text = value + operator + value2, fontSize = 30.sp, textAlign = TextAlign.End)
}

@Composable
fun ResultScreen(value: String){
    Text(modifier = Modifier
        .fillMaxWidth().padding(10.dp,0.dp,10.dp,0.dp) ,text = "=$value", fontSize = 20.sp, textAlign = TextAlign.End
    )
}

@Composable
fun Keyboard(callback:(value:String) -> Unit,caculate:()->Unit,operator:(value:String) -> Unit,del:() -> Unit){
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceAround) {
        Column() {
            Button(onClick = { operator("ac");caculate() }, Modifier.background(Color.Gray)) {
                Text("AC")
            }
            Button(onClick = { callback("7") }) {
                Text("7")
            }
            Button(onClick = { callback("4") }) {
                Text("4")
            }
            Button(onClick = { callback("1") }) {
                Text("1")
            }
            Button(onClick = { /*TODO*/ }) {
                Text("")
            }
        }
        Column {
            Button(onClick = { del()}) {
                Text("DEL")
            }
            Button(onClick = { callback("8") }) {
                Text("8")
            }
            Button(onClick = { callback("5")}) {
                Text("5")
            }
            Button(onClick = { callback("2")}) {
                Text("2")
            }
            Button(onClick = { callback("0")}) {
                Text("0")
            }
        }
        Column {
            Button(onClick = { operator("%") }) {
                Text("%")
            }
            Button(onClick = { callback("9") }) {
                Text("9")
            }
            Button(onClick = { callback("6") }) {
                Text("6")
            }
            Button(onClick = {callback("3")}) {
                Text("3")
            }
            Button(onClick = { callback(".") }) {
                Text(".")
            }
        }
        Column {
            Button(onClick = { operator("÷") }) {
                Text("÷")
            }
            Button(onClick = { operator("×") }) {
                Text("×")
            }
            Button(onClick = { operator("-") }) {
                Text("-")
            }
            Button(onClick = { operator("+") }) {
                Text("+")
            }
            Button(onClick = { operator("=");caculate() }) {
                Text("=")
            }
        }
    }
}
@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    CaculatorTheme {
    }
}