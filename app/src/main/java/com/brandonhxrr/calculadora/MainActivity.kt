package com.brandonhxrr.calculadora

import android.os.Bundle
import android.widget.Space
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.brandonhxrr.calculadora.ui.theme.CalculadoraTheme
import java.text.NumberFormat

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CalculadoraTheme {
                TipCalculator(
                    modifier = Modifier
                        .fillMaxSize()
                        .wrapContentSize(Alignment.Center)
                )
            }
        }
    }
}

@Composable
fun TipCalculator(modifier: Modifier) {

    var porcentaje by remember { mutableStateOf("0") }
    val porcentajeDouble = porcentaje.toDoubleOrNull() ?: 0.0

    var cantidad by remember { mutableStateOf("0") }
    val cantidadDouble = cantidad.toDoubleOrNull() ?: 0.0
    val propina = calculateTip(cantidadDouble, porcentajeDouble)

    val focusManager = LocalFocusManager.current


    Column(
        modifier = modifier.padding(32.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = stringResource(id = R.string.title),
            fontSize = 24.sp,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
        Spacer(modifier = Modifier.height(16.dp))
        EditTextNumber(
            stringResource(id = R.string.total),
            cantidad,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number, imeAction = ImeAction.Next),
            keyboardActions = KeyboardActions(
                onNext = {
                    focusManager.moveFocus(focusDirection = FocusDirection.Down)
                }
            )
        ) { cantidad = it }
        Spacer(modifier = Modifier.height(16.dp))
        EditTextNumber(
            stringResource(id = R.string.porcentaje), porcentaje,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number, imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions(
                onDone = {
                    focusManager.clearFocus()
                }
            )) {porcentaje = it }
        Text(
            text = stringResource(id = R.string.propina, propina ),
            fontSize = 20.sp,
            modifier = Modifier.align(Alignment.CenterHorizontally),
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun EditTextNumber(label: String, cantidad: String, keyboardOptions: KeyboardOptions, keyboardActions: KeyboardActions, onValueChange: (String) -> Unit,) {
    TextField(
        value = cantidad,
        onValueChange = onValueChange,
        label = { Text(label) },
        modifier = Modifier.fillMaxWidth(),
        singleLine = true,
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions
    )
}

fun calculateTip(cantidad: Double, porcentaje: Double = 15.0): String{
    val propina = porcentaje / 100 * cantidad
    return NumberFormat.getCurrencyInstance().format(propina)
}


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    CalculadoraTheme {
        TipCalculator(
            modifier = Modifier
                .fillMaxSize()
                .wrapContentSize(Alignment.Center)
        )
    }
}