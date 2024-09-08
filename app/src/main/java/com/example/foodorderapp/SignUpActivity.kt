package com.example.foodorderapp

import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.foodorderapp.databinding.ActivitySignUpBinding
import com.google.firebase.auth.FirebaseAuth

class SignUpActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private val binding: ActivitySignUpBinding by lazy {
        ActivitySignUpBinding.inflate(layoutInflater)
    }

    private var passwordShowing = false
    private var passwordShowing2 = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        auth = FirebaseAuth.getInstance()

        binding.eyepassword.setOnClickListener{
            if(passwordShowing)
            {
                passwordShowing=false

                binding.editTextTextPassword2.setInputType(InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD)
                binding.eyepassword.setImageResource(R.drawable.eye_off)
            }
            else
            {
                passwordShowing=true
                binding.editTextTextPassword2.setInputType(InputType.TYPE_CLASS_TEXT)
                binding.eyepassword.setImageResource(R.drawable.eye)
            }
        }

        binding.eyerepassword.setOnClickListener{
            if(passwordShowing2)
            {
                passwordShowing2=false

                binding.repeatPassword2.setInputType(InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD)
                binding.eyerepassword.setImageResource(R.drawable.eye_off)
            }
            else
            {
                passwordShowing2=true
                binding.repeatPassword2.setInputType(InputType.TYPE_CLASS_TEXT)
                binding.eyerepassword.setImageResource(R.drawable.eye)
            }
        }

        binding.alreadybutton.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

        binding.createaccountbutton.setOnClickListener {
            val email = binding.EmailAddress2.text.toString()
            val password = binding.editTextTextPassword2.text.toString()
            val password2 = binding.repeatPassword2.text.toString()

            if (email.isBlank() || password.isBlank() || password2.isBlank()) {
                Toast.makeText(this, "Please Fill All The Fields", Toast.LENGTH_LONG).show()
            } else if (!email.contains('@')) {
                Toast.makeText(this, "Please Enter Valid Email", Toast.LENGTH_LONG).show()
            } else if (password2 != password) {
                Toast.makeText(this, "Password Mismatch", Toast.LENGTH_LONG).show()
            } else if (password.length < 6) {
                Toast.makeText(this, "Password Length Too Short", Toast.LENGTH_LONG).show()
            } else {
                binding.progressBar.visibility = View.VISIBLE
                binding.createaccountbutton.visibility = View.INVISIBLE
                binding.createaccountbutton.isEnabled = false

                auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this) { task ->
                        binding.progressBar.visibility = View.GONE
                        binding.createaccountbutton.visibility = View.VISIBLE
                        binding.createaccountbutton.isEnabled = true

                        if (task.isSuccessful) {
                            Toast.makeText(this, "Account Created Successfully!", Toast.LENGTH_LONG).show()
                            val intent = Intent(this, LoginActivity::class.java)
                            //intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
                            startActivity(intent)
                            finish()
                        } else {
                            Toast.makeText(this, "Registration Failed", Toast.LENGTH_LONG).show()
                        }
                    }
            }
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}
