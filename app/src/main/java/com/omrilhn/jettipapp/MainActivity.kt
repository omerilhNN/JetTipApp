package com.omrilhn.jettipapp

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.omrilhn.jettipapp.components.InputField
import com.omrilhn.jettipapp.ui.theme.JetTipAppTheme
import com.omrilhn.jettipapp.widgets.RoundIconButton

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApp {
                //topHeader()
                MainContent()
            }
        }
    }
}
@Composable
fun MyApp(content: @Composable () -> Unit){
    JetTipAppTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background){
               content()
        }
    }
}
//@Preview
@Composable
fun topHeader(totalPerPerson : Double = 134.0)
{//clip: For give a shape to the container
    Surface(modifier = Modifier
        .fillMaxWidth()
        .height(150.dp)
        .clip(shape = RoundedCornerShape(corner = CornerSize(12.dp)))
        ,color = Color(0xFFB160BF)
    ){// .clip(shape = CircleShape.copy(all = CornerSize(12.dp) -> second way of giving shape to the corners
        Column(modifier = Modifier.padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center){
            val total = "%.2f".format(totalPerPerson)
            Text(text="Total Per Person",
                style = MaterialTheme.typography.headlineMedium)
            Text(text = "$$total",
                style = MaterialTheme.typography.displaySmall,
                fontWeight = FontWeight.ExtraBold)
        }
    }
}
@OptIn(ExperimentalComposeUiApi::class)
@Preview
@Composable
fun MainContent()
{
    BillForm(){billAmount->
        Log.d("AMT","Main Content: $billAmount")//
    }

}



//@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    JetTipAppTheme {
        MyApp {
            Text("Hello again!")
        }
    }
}
@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun BillForm(modifier : Modifier = Modifier,
            onValChange : (String) -> Unit = {}
                 ){
    val totalBillState = remember{
        mutableStateOf("")
    }
    val validState = remember(totalBillState.value){
        totalBillState.value.trim().isNotEmpty()
    }
    val keyboardController = LocalSoftwareKeyboardController.current

    val sliderPositionState = remember{
        mutableStateOf(0f)
    }

    Surface(modifier = Modifier
        .padding(2.dp)
        .fillMaxWidth(),shape = RoundedCornerShape(corner = CornerSize(8.dp)),
        border = BorderStroke(width = 1.dp,color = Color.LightGray)){
        Column(modifier = Modifier.padding(6.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start)
        {
            InputField(valueState = totalBillState , labelId ="Enter Bill" , enabled = true , isSingleLine = true,
                onAction = KeyboardActions{
                    if(!validState) return@KeyboardActions
                        onValChange(totalBillState.value.trim())
                    keyboardController?.hide()
                })
            //if(validState){
                Row(modifier = Modifier.padding(3.dp),
                    horizontalArrangement = Arrangement.Start){
                    Text("Split",
                        modifier = Modifier.align(
                            alignment = Alignment.CenterVertically
                        ))
                    Spacer(modifier = Modifier.width(120.dp))
                        Row(modifier = Modifier.padding(horizontal=3.dp),
                            horizontalArrangement = Arrangement.End
                            ){  
                            RoundIconButton( imageVector = Icons.Default.Remove,
                                onClick = { /*TODO*/ })

                            Text("2",modifier = Modifier
                                .align(Alignment.CenterVertically)
                                .padding(start = 9.dp, end = 9.dp))

                            RoundIconButton(
                                imageVector = Icons.Default.Add
                                , onClick = { /*TODO*/ })

                        }
                }
            //Tip Row
            Row (modifier = Modifier.padding(horizontal = 3.dp,vertical=12.dp)){
                Text(text = "Amount",
                    modifier = Modifier.align(alignment = Alignment.CenterVertically))

                Spacer(modifier = Modifier.width(200.dp))

                Text(text = "$33.00",
                    modifier = Modifier.align(alignment = Alignment.CenterVertically))
            }
            Column(verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally){

                Text(text = "%33")
                Spacer(modifier = Modifier.height(14.dp))

                //Slider
                Slider(value = sliderPositionState.value,
                    onValueChange ={ newVal->
                        sliderPositionState.value = newVal
                        Log.d("Slider","BillForm:$newVal")

                } )


            }
    //        }else{
      //      }

        }

    }
}