package com.example.exercise5

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {

    private lateinit var email: EditText
    private lateinit var password1: EditText
    private lateinit var password2: EditText
    private lateinit var submit: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()
        submitListener()
    }
    private fun init(){
        email = findViewById(R.id.email)
        password1 = findViewById(R.id.password1)
        password2 = findViewById(R.id.password2)
        submit = findViewById(R.id.submit)
    }

    fun submitListener(){
        submit.setOnClickListener(){
            if (validate()){
                val eMail = email.text.toString()
                val passWord = password1.text.toString()
                FirebaseAuth.getInstance()
                .createUserWithEmailAndPassword(eMail, passWord)
            }
        }
    }
    fun validate(): Boolean{
        if (email.text.toString().isEmpty()){
            email.error = "Enter your E-mail"
            return false
        }else if (password1.text.toString().isEmpty()){
            password1.error = "Enter your password"
            return false
        }else if (password2.text.toString().isEmpty()){
            password2.error = "Repeat your password"
            return false
        }else if (password1.text.toString().length < 9){
            password1.error = "Password must contain at least 9 characters"
            return false
        }else if (!validPassword(password1.text.toString())){
            password1.error = "Make sure your password contains both letters and numbers"
            return false
        } else if (!password1.text.toString().equals(password2.text.toString())){
            password2.error ="Make sure the second password matches the first one"
            return false
        }else if (!validEmail(email.text.toString())){
            email.error = "make sure your E-mail is valid"
            return false
        }else{
            Toast.makeText(this, "You have registered successfully", Toast.LENGTH_SHORT).show()
        }
        return true
    }
    fun validPassword(password: String?): Boolean {
        password?.let {
            val pattern = "(?=.*[a-z])(?=.*[0-9])"
            val passwordMatcher = Regex(pattern)
            return passwordMatcher.find(password) != null
        }
            ?:return false
    }
    fun validEmail(email: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }
}