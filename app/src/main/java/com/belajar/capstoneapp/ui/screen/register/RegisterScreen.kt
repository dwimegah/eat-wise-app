package com.belajar.capstoneapp.ui.screen.login

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
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
import com.belajar.capstoneapp.ui.screen.home.HomeScreen
import com.belajar.capstoneapp.ui.theme.Green200
import com.belajar.capstoneapp.ui.theme.Purple700
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreen(navController: NavHostController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        val name = remember { mutableStateOf(TextFieldValue()) }
        val email = remember { mutableStateOf(TextFieldValue()) }
        val password = remember { mutableStateOf(TextFieldValue()) }
        val weight = remember { mutableStateOf(TextFieldValue()) }
        val height = remember { mutableStateOf(TextFieldValue()) }
        val isRegister = remember { mutableStateOf(false) }
//        val isVegan = remember { mutableStateOf(TextFieldValue()) }

        Text(text = "Register", style = TextStyle(fontSize = 40.sp, fontFamily = FontFamily.Default))

        Spacer(modifier = Modifier.height(20.dp))
        TextField(
            label = { Text(text = "Name") },
            value = name.value,
            onValueChange = { name.value = it })

        Spacer(modifier = Modifier.height(20.dp))
        TextField(
            label = { Text(text = "Email") },
            value = email.value,
            onValueChange = { email.value = it })

        Spacer(modifier = Modifier.height(20.dp))
        TextField(
            label = { Text(text = "Password") },
            value = password.value,
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            onValueChange = { password.value = it })

        Spacer(modifier = Modifier.height(20.dp))
        TextField(
            label = { Text(text = "fill Your Height") },
            value = height.value,
            onValueChange = { height.value = it })

        Spacer(modifier = Modifier.height(20.dp))
        TextField(
            label = { Text(text = " fill Your Weight") },
            value = weight.value,
            onValueChange = { weight.value = it })

        Spacer(modifier = Modifier.height(20.dp))
        Button(
            colors = ButtonDefaults.buttonColors(Green200),
            onClick = {
                isRegister.value = true
                navController.navigate("login")
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .clip(RoundedCornerShape(50.dp))
        ) {
            Text(text = "Sign Up")
        }

        if (isRegister.value == true) {
            regist(
                name = name.value.text,
                email = email.value.text,
                password = password.value.text,
                weight = weight.value.text,
                height = height.value.text,
                navController = navController,
            )
        }

        Spacer(modifier = Modifier.height(20.dp))
        Row(
            modifier = Modifier.fillMaxWidth()
                .padding(horizontal = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            ClickableText(
                text = AnnotatedString("Sudah punya account?"),
                onClick = { },
                style = TextStyle(
                    fontSize = 14.sp,
                    fontFamily = FontFamily.Default
                )
            )

            ClickableText(
                text = AnnotatedString("Login"),
                onClick = {navController.navigate("login") },
                style = TextStyle(
                    fontSize = 14.sp,
                    fontFamily = FontFamily.Default,
                    textDecoration = TextDecoration.Underline,
                    color = Purple700
                ),
                modifier = Modifier.padding(start = 16.dp)
            )
        }
    }
}

@Composable
fun regist(
    name : String,
    email : String,
    password : String,
    weight: String,
    height: String,
    navController: NavHostController,
    viewModel: LoginViewModel = viewModel(
        factory = ViewModelFactory(Injection.provideRepository(LocalContext.current))
    ),
) {
    val timeLogin = remember { mutableStateOf(0) }
    if (timeLogin.value == 0) {
        timeLogin.value = 1
        Toast.makeText(LocalContext.current, "Berhasil register!", Toast.LENGTH_LONG).show()
    }
    navController.navigate("login")
//    val paramObject = JsonObject()
//    paramObject.addProperty("originalname", name)
//    paramObject.addProperty("name", name)
//    paramObject.addProperty("email", email)
//    paramObject.addProperty("password", password)
//    paramObject.addProperty("weight", weight)
//    paramObject.addProperty("height", height)
//    paramObject.addProperty("isVegan", false)
//
//    val client = ApiConfigUser.getApiServiceUser().signup(paramObject)
//    val successRegist = remember { mutableStateOf(true) }
//    val timeLogin = remember { mutableStateOf(false) }

//    client.enqueue(object : Callback<UserResponse> {
//        override fun onResponse(call: Call<UserResponse>, response: Response<UserResponse>) {
//            if (response.isSuccessful) {
//                val data = response.body()
//                successRegist.value = true
//                navController.navigate("login")
//                Log.d("Register", "onResponse: ${response.body()}")
//            } else {
//                successRegist.value = false
//                val errorBody = response.errorBody()?.string()
//                Log.e("Register", "onResponse: $errorBody")
//            }
//        }
//
//        override fun onFailure(call: Call<UserResponse>, t: Throwable) {
//            successRegist.value = false
//            Log.e("Register", "onFailure: ${t.message}")
//        }
//
//    })

//    if (successRegist.value != true && timeLogin.value != true) {
//        timeLogin.value = false
//    Toast.makeText(LocalContext.current, "Berhasil register!", Toast.LENGTH_LONG).show()
//    }
//    navController.navigate("login")
}