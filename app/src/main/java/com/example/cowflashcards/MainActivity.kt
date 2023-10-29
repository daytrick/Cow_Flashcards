package com.example.cowflashcards

import android.R.attr.height
import android.R.attr.shape
import android.content.res.Resources
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.cowflashcards.ui.theme.CowFlashcardsTheme


// How to create maps from: https://www.baeldung.com/kotlin/maps
val cows = mapOf<String, Int>(
    "Amnesty"   to  R.drawable.amnesty,
    "Bakka"     to  R.drawable.bakka,
    "Benny"     to  R.drawable.benny,
    "Bessie"    to  R.drawable.bessie,
    "Buttercup" to  R.drawable.buttercup,
    "Callum"    to  R.drawable.callum,
    "Denny"     to  R.drawable.denny,
    "Goosifer"  to  R.drawable.goosifer,
    "Head Cow"  to  R.drawable.head_cow,
    "It"        to  R.drawable.it,
    "Jasmine"   to  R.drawable.jasmine,
    "Jeremy"    to  R.drawable.jeremy,
    "Jerry"     to  R.drawable.jerry,
    "Jessy"     to  R.drawable.jessy,
    "Johnbrown" to  R.drawable.johnbrown,
    "Lenny"     to  R.drawable.lenny,
    "Leo"       to  R.drawable.leo,
    "Marta"     to  R.drawable.marta,
    "Mildred"   to  R.drawable.mildred,
    "Moodle"    to  R.drawable.moodle,
    "Mooley"    to  R.drawable.mooley,
    "Morgan"    to  R.drawable.morgan,
    "Muriel"    to  R.drawable.muriel,
    "Mush"      to  R.drawable.mush,
    "Nath"      to  R.drawable.nath,
    "Ned"       to  R.drawable.ned,
    "Noisy Cow" to  R.drawable.noisy_cow,
    "Nuzzly"    to  R.drawable.nuzzly,
    "Percy"     to  R.drawable.percy,
    "Robert"    to  R.drawable.robert,
    "Spring"    to  R.drawable.spring,
    "Squidglet" to  R.drawable.squidglet,
    "Susie"     to  R.drawable.susie,
    "Tarri"     to  R.drawable.tarri,
    "Toby"      to  R.drawable.toby,
    "Tom"       to  R.drawable.tom,
    "Tomfurry"  to  R.drawable.tomfurry,
    "William"   to  R.drawable.william,
    "Wizard"    to  R.drawable.wizard
)



class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CowFlashcardsTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    CowDisplay()
                }
            }
        }
    }

}


@Composable
fun CowDisplay() {
    var index by rememberSaveable { mutableStateOf(getRandomIndex()) }
    ShowRandomCow(index, onIndexChange = {index = it})
}


@Composable
fun ShowRandomCow(index: Int, onIndexChange: (Int) -> Unit) {

    var showCowName by rememberSaveable { mutableStateOf(false) }
    val cow = cows.entries.elementAt(index)

    // How to stop elements from overlapping from: https://developer.android.com/jetpack/compose/layouts/basics
    // How to add inter-element spacing from: https://stackoverflow.com/a/68201569
    Column(
        modifier = Modifier.padding(10.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {

        Box(
            contentAlignment = Alignment.Center
        ) {

            // How to load image from disc from: https://developer.android.com/jetpack/compose/graphics/images/loading
            Image(
                painter = painterResource(cow.value),
                contentDescription = "A cow?"
            )

            if (showCowName) {
                Text(cow.key)
            }

        }

        // Text(text = cow.key)
        NameField(who = cow.key, onIndexChange = onIndexChange, onShowCowNameChange = {showCowName = it})

    }

}


/**
 * BasicTextField component from: https://developer.android.com/reference/kotlin/androidx/compose/foundation/text/package-summary#BasicTextField(kotlin.String,kotlin.Function1,androidx.compose.ui.Modifier,kotlin.Boolean,kotlin.Boolean,androidx.compose.ui.text.TextStyle,androidx.compose.foundation.text.KeyboardOptions,androidx.compose.foundation.text.KeyboardActions,kotlin.Boolean,kotlin.Int,kotlin.Int,androidx.compose.ui.text.input.VisualTransformation,kotlin.Function1,androidx.compose.foundation.interaction.MutableInteractionSource,androidx.compose.ui.graphics.Brush,kotlin.Function1)
 */
@Composable
fun NameField(who: String, onIndexChange: (Int) -> Unit, onShowCowNameChange: (Boolean) -> Unit) {

    var answer by rememberSaveable { mutableStateOf("") }
    var doCheck by rememberSaveable { mutableStateOf(false) }
    var showRevealButton by remember { mutableStateOf(false) }

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        BasicTextField(
            value = answer,
            onValueChange = {
                doCheck = false
                Log.d("resultMessage",("Hid popup: $doCheck"))
                answer = it
            },
            maxLines = 1,
            decorationBox = { innerTextField ->
                // Because the decorationBox is used, the whole Row gets the same behaviour as the
                // internal input field would have otherwise. For example, there is no need to add a
                // Modifier.clickable to the Row anymore to bring the text field into focus when user
                // taps on a larger text field area which includes paddings and the icon areas.
                Row(
                    Modifier
                        .background(Color.LightGray, RoundedCornerShape(percent = 30))
                        .padding(5.dp)
                ) {
                    innerTextField()

                }

            }
        )

        Button(
            onClick = {
                doCheck = true
                Log.d("resultMessage", "Clicked button: $doCheck")
            },
            contentPadding = ButtonDefaults.ContentPadding
        ) {
            Text("Check")
        }

        if (showRevealButton) {
            Button(
                onClick = {
                    onShowCowNameChange(true);
                },
                contentPadding = ButtonDefaults.ContentPadding
            ) {
                Text("Reveal")
            }
        }

    }

    if (doCheck) {
        val correct = checkAnswer(who, answer)
        ShowResult(correct);

        if (correct) {
            answer = ""
            doCheck = false
            showRevealButton = false
            onShowCowNameChange(false)
            onIndexChange(getRandomIndex())
        }
        else {
            showRevealButton = true;
        }

        Log.d("resultMessage", "Showing popup: $doCheck")

    }

}




fun checkAnswer(who: String, answer: String): Boolean {
    return (who.compareTo(answer.trim(), true) == 0)
}



@Composable
fun ShowResult(correct: Boolean) {

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(),
        contentAlignment = Alignment.Center
    ) {
        val message = if (correct) "Correct!" else "Wrong!"
        Text(message)
    }

}


@Composable
fun ShowRevealButton() {

}





@Composable
fun ShowCow(resources: Resources) {

    // How to load image from disc from: https://developer.android.com/jetpack/compose/graphics/images/loading
    Image(
        painter = painterResource(R.drawable.benny),
        contentDescription = "Benny!"
    )
    val name = resources.getResourceName(R.drawable.benny)
    Text(text = name)


}

fun getRandomIndex(): Int {
    // Get random number
    // How to get random number from: https://stackoverflow.com/a/45687695
    return (0..(cows.size - 1)).random()
}



@Preview(showBackground = true)
@Composable
fun CowPreview() {
    CowFlashcardsTheme {
        Column(
            Modifier.padding(10.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            Image(
                painter = painterResource(R.drawable.benny),
                contentDescription = "Benny!"
            )
            // Text(text = "Benny")
            NameField(who = "Benny", {}, {})

        }
    }
}