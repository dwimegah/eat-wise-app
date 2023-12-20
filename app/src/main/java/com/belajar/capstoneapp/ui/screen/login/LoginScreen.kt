package com.belajar.capstoneapp.ui.screen.login

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.belajar.capstoneapp.ViewModelFactory
import com.belajar.capstoneapp.data.pref.UserModel
import com.belajar.capstoneapp.data.response.UserResponse
import com.belajar.capstoneapp.data.retrofit.ApiConfigUser
import com.belajar.capstoneapp.di.Injection
import com.belajar.capstoneapp.ui.theme.Green200
import com.belajar.capstoneapp.ui.theme.Green300
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.concurrent.CountDownLatch




@Composable
fun LoginScreen(navController: NavHostController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        val username = remember { mutableStateOf(TextFieldValue()) }
        val password = remember { mutableStateOf(TextFieldValue()) }
        val isLogin = remember { mutableStateOf(false) }

        Text(text = "Login", style = TextStyle(fontSize = 40.sp, fontFamily = FontFamily.SansSerif))

        Spacer(modifier = Modifier.height(20.dp))
        TextField(
            label = { Text(text = "Username") },
            value = username.value,
            onValueChange = { username.value = it })

        Spacer(modifier = Modifier.height(20.dp))
        TextField(
            label = { Text(text = "Password") },
            value = password.value,
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            onValueChange = { password.value = it })

        Spacer(modifier = Modifier.height(20.dp))
        Button(
            colors = ButtonDefaults.buttonColors(Green200),
            onClick = {
                isLogin.value = true
//                loginAuth(
//                    username = username.value.text,
//                    password = password.value.text,
//                    navController = navController
//                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .clip(RoundedCornerShape(50.dp))
        ) {
            Text(text = "Login")
        }

        Spacer(modifier = Modifier.height(20.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            ClickableText(
                text = AnnotatedString("Belum punya akun?"),
                onClick = { },
                style = TextStyle(
                    fontSize = 14.sp,
                    fontFamily = FontFamily.Default
                )
            )

            ClickableText(
                text = AnnotatedString("Sign Up"),
                onClick = {
//                    navController.navigate("register")
                          },
                style = TextStyle(
                    fontSize = 14.sp,
                    fontFamily = FontFamily.Default,
                    textDecoration = TextDecoration.Underline,
                    color = Green300
                ),
                modifier = Modifier.padding(start = 16.dp)
            )
        }

        if (isLogin.value == true) {
            loginAuth(
                username = username.value.text,
                password = password.value.text,
                navController = navController,
            )
        }
    }
}

@Composable
fun loginAuth(
    username : String,
    password : String,
    navController: NavHostController,
    viewModel: LoginViewModel = viewModel(
        factory = ViewModelFactory(Injection.provideRepository(LocalContext.current))
    ),
) {
//    navController.navigate("home")
//    viewModel.login(username, password)
    val paramObject = JsonObject()
    paramObject.addProperty("email", "admin1@gmail.com")
    paramObject.addProperty("password", "12345")

    val client = ApiConfigUser.getApiServiceUser().login(paramObject)
//    val countDownLatch = CountDownLatch(1)
    val successLogin = remember { mutableStateOf(true) }
    val timeLogin = remember { mutableStateOf(false) }
    client.enqueue(object : Callback<UserResponse> {
        override fun onResponse(call: Call<UserResponse>, response: Response<UserResponse>) {
            if (response.isSuccessful) {
                successLogin.value = true
//                countDownLatch.countDown();
                val data = response.body()
                data?.let {
                    viewModel.saveSession(
                        UserModel(
                            it.user.user,
                            "jwt=" + it.user.token,
                            true
                        )
//                        it.user
                    )
                }
                navController.navigate("home")
                Log.d("Login", "onResponse: ${response.body()}")
            } else {
                successLogin.value = false
                val errorBody = response.errorBody()?.string()
//                countDownLatch.countDown();
                Log.e("Login", "onResponse: $errorBody")
            }
        }

        override fun onFailure(call: Call<UserResponse>, t: Throwable) {
//            countDownLatch.countDown();
            successLogin.value = false
            Log.e("Login", "onFailure: ${t.message}")
        }

    })

//    countDownLatch.await();
    if (successLogin.value != true && timeLogin.value != true) {
        timeLogin.value = false
        Toast.makeText(LocalContext.current, "Gagal login!", Toast.LENGTH_LONG).show()
    }
}