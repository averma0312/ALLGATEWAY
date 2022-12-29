package com.example.allgateway

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth


class LoginActivity : AppCompatActivity() {

    // Initialize a button and Textbox of email password and loginbtn
    lateinit var etEmail: EditText
    private lateinit var etPass: EditText
    lateinit var btnLogin: ImageButton

    // Creating firebaseAuth object
    lateinit var auth: FirebaseAuth

    //Forgot Password


    override fun onCreate(savedInstanceState: Bundle?) {


        //Firebase Connect Manually Initialize
        FirebaseApp.initializeApp(this@LoginActivity)

        // Handle the splash screen transition.
        val splashScreen = installSplashScreen()

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // Go to Registration page with Intent....
        val btnclick = findViewById<TextView>(R.id.textView5)
        btnclick.setOnClickListener{
            val intent = Intent(this, RegistrationActivity::class.java);
            startActivity(intent);
        }

//        val btnclick2 = findViewById<ImageView>(R.id.phone)
//        btnclick2.setOnClickListener{
//            val intent2 = Intent(this,PhoneActivity::class.java)
//            startActivity(intent2)
//        }

//        val btnclick3 = findViewById<TextView>(R.id.forgot)
//        btnclick3.setOnClickListener{
//            val intent3 = Intent(this,ForgotpasswordActivity::class.java)
//            startActivity(intent3)
//        }

        //Initialize a Textbox and button by id
        btnLogin = findViewById(R.id.imageButton)
        etEmail = findViewById(R.id.email)
        etPass = findViewById(R.id.password)

        // initialising Firebase auth object
        auth = FirebaseAuth.getInstance()
        btnLogin.setOnClickListener {
            login()
        }

        //Forgot Password

    }





    private fun login() {
        val email = etEmail.text.toString()
        val pass = etPass.text.toString()

        //Validation for Login
        if (email.isBlank()) {
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

        var progressDialog = ProgressDialog(this@LoginActivity)
        progressDialog.setTitle("Loading")
        progressDialog.setMessage("Processing...")
        progressDialog.show()

        // calling signInWithEmailAndPassword(email, pass)
        // function using Firebase auth object
        // On successful response Display a Toast
        auth.signInWithEmailAndPassword(email, pass).addOnCompleteListener(this) {
            if (it.isSuccessful) {
                Toast.makeText(this, "Successfully Logged In", Toast.LENGTH_SHORT).show()
                progressDialog.dismiss()
                startActivity(Intent(applicationContext, MainActivity::class.java))
            } else
                Toast.makeText(this, "Log In failed ", Toast.LENGTH_SHORT).show()
            progressDialog.dismiss()
        }
    }
}