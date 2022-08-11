package com.example.caculator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
                    Column() {
                        TopBarView("计算器", {})
                        caculater()
                    }
                }
            }
        }
    }
}

@Composable
fun CurrentScreen(value: String, operator: String, value2: String) {
    Text(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp, 0.dp, 10.dp, 0.dp)
            .background(Color.White),
        text = value + operator + value2,
        fontSize = 50.sp,
        textAlign = TextAlign.End,
        color = Color.Black
    )
}

@Composable
fun ResultScreen(value: String) {
    Text(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp, 0.dp, 10.dp, 0.dp)
            .background(Color.White),
        text = "=$value",
        fontSize = 30.sp,
        textAlign = TextAlign.End,
        color = Color.Black
    )
}

@Composable
fun Keybord2(keyCharts: Array<Array<String>>, onclick: (i: Int, j: Int) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(), horizontalArrangement = Arrangement.SpaceAround
    ) {
        for (i in 0..3) {
            Column(
                Modifier
                    .fillMaxHeight()
                    .padding(0.dp, 30.dp), verticalArrangement = Arrangement.SpaceAround
            ) {
                for (j in 0..4) {
                    Button(
                        modifier = Modifier
                            .weight(1f, true)
                            .padding(0.dp, 10.dp),
                        onClick = { onclick(j, i) },
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = Color.LightGray,
                        )
                    ) {
                        Text(text = keyCharts[j][i], fontSize = 18.sp, color = Color.Black)
                    }
                }
            }
        }
    }
}

@Composable
fun TopBarView(title: String, callback: () -> Unit) {
    Column() {
        //Spacer(modifier = Modifier.height(10.dp).fillMaxWidth())
        TopAppBar(title = {
            Text(title)
        }, navigationIcon = {
            IconButton(onClick = {
                callback()
            }) {
                Icon(Icons.Filled.Home, "")
            }
        })
    }

}

@Composable
fun caculater() {
    val keyChars = arrayOf(
        arrayOf("C", "DEL", "%", "÷"), arrayOf("7", "8", "9", "×"),
        arrayOf("4", "5", "6", "-"), arrayOf("1", "2", "3", "+"), arrayOf("", "0", ".", "=")
    )
    var number1 by remember { mutableStateOf("0") }
    var operator by remember { mutableStateOf("") }
    var number2 by remember { mutableStateOf("") }
    var result by remember { mutableStateOf("0") }
    fun del() {
        if (number2 != "") {

        } else if (operator != "") {
            operator = ""
        } else if (number1.length > 1) {
            number1 = number1.substring(0, number1.length - 1)
        } else {
            number1 = "0"
        }
    }

    fun caculate() {
        val num1 = number1.toFloat()
        val num2 = number2.toFloat()
        when (operator) {
            "÷" -> {
                if (num2 == 0f) {
                    result = "除数不能为0"
                } else {
                    result = (num1 / num2).toString()
                }
            }
            "×" -> {
                result = (num1 * num2).toString()
            }
            "+" -> {
                result = (num1 + num2).toString()
            }
            "-" -> {
                result = (num1 - num2).toString()
            }
            "%" -> {
                result = (num1 % num2).toString()
            }
        }
    }


    fun input(char: String) {
        if (operator == "") {
            //输入的数字1
            if (char != ".") {
                if (number1 == "0") {
                    number1 = char
                } else {
                    number1 += char
                }
            } else {
                number1 += char
            }
        } else {
            //数字2
            if (char != ".") {
                if (number2 == "0") {
                    number2 = char
                } else {
                    number2 += char
                }
            } else {
                number2 += char
            }
        }
    }

    fun keyBoardClick(i: Int, j: Int) {
        var char = keyChars[i][j]
        when (char) {
            "C" -> {
                number1 = "0"
                operator = ""
                number2 = ""
                result = "0"
            }
            "DEL" -> {
                del()
            }
            "+", "-", "×", "÷", "%" -> {
                if (operator == "") {
                    operator = char
                }

            }
            "=" -> {
                caculate()
            }
            else -> {
                //输入数字
                input(char)
            }
        }
    }


    Column(
        modifier = Modifier
            .fillMaxHeight()
            .background(Color.LightGray)
    ) {
        Box(
            modifier = Modifier
                .weight(1f, true)
                .background(Color.White), contentAlignment = Alignment.BottomEnd
        ) {
            Column() {
                CurrentScreen(value = number1, operator = operator, value2 = number2)
                ResultScreen(value = result)
            }
        }
        Divider(modifier = Modifier.height(1.dp))
        Box(
            modifier = Modifier
                .weight(2f, true)
                .fillMaxWidth()
                .background(Color.White)
        ) {
            Keybord2(keyCharts = keyChars, onclick = { i, j -> keyBoardClick(i, j) })
        }
    }


}

@Preview
@Composable
fun previewView() {
    CaculatorTheme {
        Column() {
            TopBarView("计算器", {})
            caculater()
        }

    }
}
