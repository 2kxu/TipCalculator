package com.example.tipcalculator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
//import androidx.compose.foundation.gestures.ModifierLocalScrollableContainerProvider.value
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
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
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tipcalculator.R.string
import com.example.tipcalculator.ui.theme.TipCalculatorTheme
import java.text.NumberFormat
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.Icon
import androidx.compose.ui.res.painterResource

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
    var tipPer by remember { mutableStateOf("") }
    var roundFigure by remember { mutableStateOf(false)    }

    val finalAmount = amount.toDoubleOrNull() ?: 0.0
    val tipAmount = tipPer.toDoubleOrNull() ?: 0.0
//    Amount is getting converted to Double Value or NUll Vale,
//    if its null then it'll get o.o value that is written after elvis operator

    val tipCalc = calcAmount(finalAmount, tipAmount, roundFigure)
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
            text = stringResource(string.calculate_tip_Text),
            fontSize = 16.sp,
            fontWeight = FontWeight.ExtraBold,
            modifier = modifier
                .padding(bottom = 16.dp, top = 40.dp)
                .align(alignment = Alignment.Start)
        )
        Spacer(modifier = modifier.height(16.dp))

//        2nd element
        EditNumberField(
            value = amount,
            onValueChange = {amount = it},
            label = string.amount_Text,
            leadingIcon = R.drawable.money,
            modifier = Modifier
                .padding(bottom = 32.dp)
                .fillMaxWidth(),
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Next
            )
        )

//        3rd element
        EditNumberField(
            value = tipPer,
            onValueChange = { tipPer = it },
            label = string.Tip_Text,
            leadingIcon = R.drawable.percent,
            modifier = Modifier
                .padding(bottom = 32.dp)
                .fillMaxWidth(),
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Done
            )
        )

//        4th element
        RoundFigureToggle(
            roundFigure = roundFigure,
            onToggle = { roundFigure = it},
            modifier = Modifier.padding(bottom = 32.dp)
        )


//        5th element
        Text(
            text = stringResource(string.bill_amount_Text, tipCalc),
            fontSize = 40.sp,
            fontWeight = FontWeight.Bold,
            fontStyle = FontStyle.Italic,
            style = MaterialTheme.typography.displaySmall,
        )
    }
}

// Input field
@Composable
fun EditNumberField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    @StringRes label: Int,
    @DrawableRes leadingIcon: Int,
    keyboardOptions: KeyboardOptions
) {
    TextField(
        value = value,
        onValueChange = onValueChange,
        singleLine = true,
        modifier = modifier,
        label = { Text(stringResource(label)) },
        keyboardOptions = keyboardOptions,
        leadingIcon = { Icon(painter = painterResource(id = leadingIcon), contentDescription = null)}
    )
}

@Composable
fun RoundFigureToggle(modifier: Modifier = Modifier,
                      roundFigure: Boolean,
                      onToggle: (Boolean) -> Unit,
                      ){
    Row (modifier = modifier
        .fillMaxWidth()
        .size(48.dp),
        verticalAlignment = Alignment.CenterVertically)
    {
      Text(text = stringResource(string.round_figure))
      Switch(modifier = modifier
          .fillMaxWidth()
          .wrapContentWidth(Alignment.End),
          checked = roundFigure,
          onCheckedChange = onToggle)

    }
}

// Calculation Logic
private fun calcAmount (amount1: Double, tipPer: Double = 15.0, roundFigure: Boolean): String{
     var tip = tipPer / 100 * amount1
    if(roundFigure){
        tip = kotlin.math.ceil(tip)
    }
    return NumberFormat.getCurrencyInstance().format(tip)
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    TipCalculatorTheme {
        TipCalculator()
    }
}