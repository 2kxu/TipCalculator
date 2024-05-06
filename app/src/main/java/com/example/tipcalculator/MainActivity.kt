package com.example.tipcalculator

import android.annotation.SuppressLint
import android.icu.number.NumberFormatter
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tipcalculator.ui.theme.TipCalculatorTheme
import java.text.NumberFormat

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TipCalculatorTheme {
                TipCalculator()
                }
            }
        }
    }

@Composable
fun TipCalculator(modifier: Modifier = Modifier) {
    var amount by remember  { mutableStateOf("") }
    val finalAmount = amount.toDoubleOrNull() ?: 0.0
//    Amount is getting converted to Double Value or NUll Vale,
//    if its null then it'll get o.o value that is written after elvis operator

    val tipCalc = calcAmount(finalAmount)
// App UI
    Column(
        modifier
            .fillMaxSize()
            .wrapContentSize(Alignment.Center)
            .statusBarsPadding()
            .padding(horizontal = 40.dp)
            .verticalScroll(rememberScrollState())
            .safeDrawingPadding(),
    ) {
//        1st element
        Text(
            text = stringResource(R.string.calculate_tip_Text),
            fontSize = 16.sp,
            fontWeight = FontWeight.ExtraBold,
            modifier = modifier.padding(bottom = 16.dp, top = 40.dp)
                    .align(alignment = Alignment.Start)
        )
        Spacer(modifier = modifier.height(16.dp))

//        2nd element
        EditNumberField(
            value = amount,
            onValueChange = {amount = it},
        )

//        3rd element
        Text(
            text = stringResource(R.string.bill_amount_Text, tipCalc),
            fontSize = 40.sp,
            fontWeight = FontWeight.Bold,
            fontStyle = FontStyle.Italic,
            style = MaterialTheme.typography.displaySmall
        )
    }
}

@Composable
fun EditNumberField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    TextField(
        value = value,
        onValueChange = onValueChange,
        singleLine = true,
        label = { Text(stringResource(R.string.amount_Text)) },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        modifier = modifier
    )
}

private fun calcAmount (amount1: Double, tipPer: Double = 15.0): String{
     val tip = tipPer / 100 * amount1
    return NumberFormat.getCurrencyInstance().format(tip)
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    TipCalculatorTheme {
        TipCalculator()
    }
}