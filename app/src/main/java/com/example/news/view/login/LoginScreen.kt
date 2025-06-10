package com.example.news.view.login

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.news.R
import androidx.compose.foundation.shape.CircleShape
import com.example.news.presenter.AuthView
import com.example.news.presenter.impl.AuthPresenterImpl

@Composable
fun LoginScreen(onLoginSuccess: () -> Unit, presenter: AuthPresenterImpl = AuthPresenterImpl()
) {
    val context = LocalContext.current
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }


    DisposableEffect(Unit) {
        val view = object : AuthView {
            override fun onLoginSuccess() {
                Toast.makeText(context, "Login Successful!", Toast.LENGTH_SHORT).show()
                onLoginSuccess()
            }

            override fun onLoginFailure(error: String) {
                Toast.makeText(context, error, Toast.LENGTH_SHORT).show()
            }

            override fun onRegisterSuccess() {}
            override fun onRegisterFailure(error: String) {}
        }
        presenter.attachView(view)
        onDispose { presenter.detachView() }
    }

    Box(modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.TopCenter) {
        Image(
            painter = painterResource(id = R.drawable.news1),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp),
            contentScale = ContentScale.Crop
        )

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 0.dp)
                .offset(y = 240.dp),
            shape = RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 24.dp, vertical = 24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("Login", fontSize = 28.sp, color = Color.Black)

                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    label = { Text("Email ID") },
                    leadingIcon = {
                        Icon(Icons.Default.Email, contentDescription = null, tint = Color.Gray)
                    },
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
                )

                Spacer(modifier = Modifier.height(12.dp))

                OutlinedTextField(
                    value = password,
                    onValueChange = { password = it },
                    label = { Text("Password") },
                    leadingIcon = {
                        Icon(Icons.Default.Lock, contentDescription = null, tint = Color.Gray)
                    },
                    modifier = Modifier.fillMaxWidth(),
                    visualTransformation = PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
                )

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = { presenter.login(email, password) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(40.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Black),
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Text("Login  ->", color = Color.White)
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text("Or, login with …", fontSize = 14.sp)

                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    listOf(
                        R.drawable.google_icon to "Google",
                        R.drawable.facebook to "Facebook",
                        R.drawable.twitter to "X"
                    ).forEach { (icon, desc) ->
                        Surface(
                            shape = CircleShape,
                            color = Color.Transparent, // or Color.White or transparent
                            modifier = Modifier.size(48.dp)
                        ) {
                            IconButton(onClick = { /* TODO: $desc login */ }) {
                                Icon(
                                    painter = painterResource(id = icon),
                                    contentDescription = desc,
                                    modifier = Modifier.size(30.dp),
                                    tint = Color.Unspecified
                                )
                            }
                        }
                    }
                }


                Spacer(modifier = Modifier.height(16.dp))

                TextButton(onClick = { /* TODO: Navigate to Register */ }) {
                    Text("Register", color = Color.Blue)
                }
            }
        }
    }
}

