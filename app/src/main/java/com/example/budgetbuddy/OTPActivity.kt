package com.example.budgetbuddy

import android.app.Activity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class OTPActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        setContent {

            OTPBody()

        }
    }
}

@Composable
fun OTPBody() {

    var otp by remember { mutableStateOf("") }

    val context = LocalContext.current
    val activity = context as? Activity

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF8FAFF))
            .padding(horizontal = 25.dp),

        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Spacer(modifier = Modifier.height(120.dp))

        Text(
            text = "Verify OTP",

            style = TextStyle(
                color = Color(0xFF4A6CF7),
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )
        )

        Spacer(modifier = Modifier.height(10.dp))

        Text(
            text = "Enter the OTP sent to your email",

            style = TextStyle(
                color = Color(0xFF7A7A7A),
                fontSize = 15.sp,
                textAlign = TextAlign.Center
            )
        )

        Spacer(modifier = Modifier.height(45.dp))

        OutlinedTextField(
            value = otp,
            onValueChange = {
                otp = it
            },

            modifier = Modifier.fillMaxWidth(),

            placeholder = {
                Text("Enter OTP")
            },

            shape = RoundedCornerShape(15.dp),

            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color(0xFFEAF0FF),
                unfocusedContainerColor = Color(0xFFEAF0FF),

                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            )
        )

        Spacer(modifier = Modifier.height(35.dp))

        Button(
            onClick = {

                if (otp == "1234") {

                    Toast.makeText(
                        context,
                        "OTP Verified",
                        Toast.LENGTH_LONG
                    ).show()

                } else {

                    Toast.makeText(
                        context,
                        "Invalid OTP",
                        Toast.LENGTH_LONG
                    ).show()
                }
            },

            modifier = Modifier
                .fillMaxWidth()
                .height(55.dp),

            shape = RoundedCornerShape(15.dp),

            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF4A6CF7)
            )

        ) {

            Text(
                text = "Verify",
                color = Color.White,
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = "Resend OTP",

            modifier = Modifier.clickable {

                Toast.makeText(
                    context,
                    "OTP sent again",
                    Toast.LENGTH_LONG
                ).show()
            },

            color = Color(0xFF4A6CF7),
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = "Back to Login",

            modifier = Modifier.clickable {

                activity?.finish()

            },

            color = Color(0xFF4A6CF7),
            fontWeight = FontWeight.Bold
        )
    }
}

@Preview()
@Composable
fun OTPPreview() {

    OTPBody()

}