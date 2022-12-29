package com.example.allgateway

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import java.util.regex.Pattern


class RegistrationActivity : AppCompatActivity() {

    //Initialize Texbox, Password, ImageButton
    lateinit var reg_email: EditText
    private lateinit var reg_password: EditText
    private lateinit var image_button: ImageButton
    private lateinit var checkbox: CheckBox


    // Password Validation Initializa
    private val PASSWORD_PATTERN: Pattern = Pattern.compile(
        "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{4,}$"
    )

    // Firebase Initializa
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)

        //Go to Login Activity
        val textview = findViewById<TextView>(R.id.textView5)
        textview.setOnClickListener{
            intent = Intent(this,LoginActivity::class.java);
            startActivity(intent);
        }

        FirebaseApp.initializeApp(this@RegistrationActivity)

        // Initialize the id of Textbox, Password, Button
        reg_email = findViewById(R.id.reg_email)
        reg_password = findViewById(R.id.reg_password)
        image_button = findViewById(R.id.imageButton)
        checkbox = findViewById(R.id.checkBox)

        // Initialize the firebase connect manually
        auth = Firebase.auth

        image_button.setOnClickListener {
            signUpUser() //Call the Instance method which click
        }

    }

    // Registration Method
    private fun signUpUser() {
        val email = reg_email.text.toString()
        val pass = reg_password.text.toString()
        val check = checkbox.text.toString()

        // Validation of Form
        if (email.isBlank() ) {
            Toast.makeText(this, "Email can't be blank", Toast.LENGTH_SHORT).show()
            return
        }
        if (pass.isBlank()){
            Toast.makeText(this, "Password can't be blank", Toast.LENGTH_SHORT).show()
            return
        }
        if (pass.length < 8) {
            Toast.makeText(this, "Password must be 8 characters", Toast.LENGTH_SHORT).show()
            return
        }
        if (!PASSWORD_PATTERN.matcher(pass).matches()) {
            Toast.makeText(this, "Password must contain 1 special characters,1 Uppercase,1 Lowercase,1 digits and  no whitespace.", Toast.LENGTH_SHORT).show()
            return
        }
        if (check.isBlank()){
            Toast.makeText(this, "Please accept the terms and condtion.", Toast.LENGTH_SHORT).show()
            return
        }

        //For ProgressDialog
        val progressDialog = ProgressDialog(this@RegistrationActivity)
        progressDialog.setTitle("Loading")
        progressDialog.setMessage("Creating User... , please wait")
        progressDialog.show()

        // Code of registration of firebase using manually
        auth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(this) {
            if (it.isSuccessful) {
                Toast.makeText(this, "Register Successfully .", Toast.LENGTH_SHORT).show()
                progressDialog.dismiss()

            } else {
                Toast.makeText(this, "Singed Up Failed!", Toast.LENGTH_SHORT).show()
                progressDialog.dismiss()
            }
        }
    }


}