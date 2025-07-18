package com.example.news.view.login

import android.app.Activity
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.OAuthProvider
// For Google Sign-In
import com.google.android.gms.common.api.ApiException


@Composable
fun LoginScreen(onLoginSuccess: () -> Unit,onRegisterClick: () -> Unit, presenter: AuthPresenterImpl = AuthPresenterImpl()
) {
    val context = LocalContext.current
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    // Add this inside your LoginScreen composable

    val googleSignInLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
        try {
            val account = task.getResult(ApiException::class.java)
            val credential = GoogleAuthProvider.getCredential(account.idToken, null)
            FirebaseAuth.getInstance().signInWithCredential(credential)
                .addOnCompleteListener { authResult ->
                    if (authResult.isSuccessful) {
                        Toast.makeText(context, "Google Login Successful", Toast.LENGTH_SHORT).show()
                        onLoginSuccess()
                    } else {
                        Toast.makeText(context, "Google Login Failed", Toast.LENGTH_SHORT).show()
                    }
                }
        } catch (e: Exception) {
            Toast.makeText(context, "Google Sign-in Error: ${e.localizedMessage}", Toast.LENGTH_SHORT).show()
        }
    }

    fun loginWithGitHub(
        activity: Activity,
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit
    ) {
        val provider = OAuthProvider.newBuilder("github.com")
        val auth = FirebaseAuth.getInstance()
        val pendingResultTask = auth.pendingAuthResult
        if (pendingResultTask != null) {
            pendingResultTask
                .addOnSuccessListener { onSuccess() }
                .addOnFailureListener { e -> onFailure(e.localizedMessage ?: "GitHub Login Failed") }
        } else {
            auth.startActivityForSignInWithProvider(activity, provider.build())
                .addOnSuccessListener { onSuccess() }
                .addOnFailureListener { e -> onFailure(e.localizedMessage ?: "GitHub Login Failed") }
        }
    }

    fun loginWithTwitter(
        activity: Activity,
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit
    ) {
        val provider = OAuthProvider.newBuilder("twitter.com")
        val auth = FirebaseAuth.getInstance()
        val pendingResultTask = auth.pendingAuthResult
        if (pendingResultTask != null) {
            pendingResultTask
                .addOnSuccessListener { onSuccess() }
                .addOnFailureListener { e -> onFailure(e.localizedMessage ?: "Twitter Login Failed") }
        } else {
            auth.startActivityForSignInWithProvider(activity, provider.build())
                .addOnSuccessListener { onSuccess() }
                .addOnFailureListener { e -> onFailure(e.localizedMessage ?: "Twitter Login Failed") }
        }
    }


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
                    // Google Login Button
                    Surface(
                        shape = CircleShape,
                        color = Color.Transparent,
                        modifier = Modifier.size(48.dp)
                    ) {
                        IconButton(onClick = {
                            val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                                .requestIdToken("1007847092116-bpfms3phhmcnhnla6n6d1cbkb0klcsot.apps.googleusercontent.com") // Replace with your actual Web client ID!
                                .requestEmail()
                                .build()
                            val googleSignInClient = GoogleSignIn.getClient(context, gso)
                            googleSignInLauncher.launch(googleSignInClient.signInIntent)
                        }) {
                            Icon(
                                painter = painterResource(id = R.drawable.google_icon),
                                contentDescription = "Google",
                                modifier = Modifier.size(38.dp),
                                tint = Color.Unspecified
                            )
                        }
                    }
                    // GitHub Login Button
                    Surface(
                        shape = CircleShape,
                        color = Color.Transparent,
                        modifier = Modifier.size(48.dp)
                    ) {
                        IconButton(onClick = {
                            loginWithGitHub(
                                activity = context as Activity,
                                onSuccess = {
                                    Toast.makeText(context, "GitHub Login Successful", Toast.LENGTH_SHORT).show()
                                    onLoginSuccess()
                                },
                                onFailure = { error ->
                                    Toast.makeText(context, error, Toast.LENGTH_SHORT).show()
                                }
                            )
                        }) {
                            Icon(
                                painter = painterResource(id = R.drawable.github),
                                contentDescription = "GitHub",
                                modifier = Modifier.size(38.dp),
                                tint = Color.Unspecified
                            )
                        }
                    }
                    // Twitter Login Button
                    Surface(
                        shape = CircleShape,
                        color = Color.Transparent,
                        modifier = Modifier.size(48.dp)
                    ) {
                        IconButton(onClick = {
                            loginWithTwitter(
                                activity = context as Activity,
                                onSuccess = {
                                    Toast.makeText(context, "Twitter Login Successful", Toast.LENGTH_SHORT).show()
                                    onLoginSuccess()
                                },
                                onFailure = { error ->
                                    Toast.makeText(context, error, Toast.LENGTH_SHORT).show()
                                }
                            )
                        }) {
                            Icon(
                                painter = painterResource(id = R.drawable.twitter),
                                contentDescription = "Twitter",
                                modifier = Modifier.size(38.dp),
                                tint = Color.Unspecified
                            )
                        }
                    }
                }



                Spacer(modifier = Modifier.height(16.dp))

                TextButton(onClick = { onRegisterClick()}) {
                    Text("Register", color = Color.Blue)
                }
            }
        }
    }
}

