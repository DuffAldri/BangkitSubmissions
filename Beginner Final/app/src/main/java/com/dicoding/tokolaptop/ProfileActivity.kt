package com.dicoding.tokolaptop

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.dicoding.tokolaptop.databinding.ActivityProfileBinding

class ProfileActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonEmail.setOnClickListener(this)
    }

    override fun onClick(p0: View?) {
        when(p0?.id) {
            R.id.button_email -> {
                val emailAddress = R.string.profile_email
                val mIntent = Intent(Intent.ACTION_SEND)
                mIntent.data = Uri.parse("mailto:")
                mIntent.type = "text/plain"
                mIntent.putExtra(Intent.EXTRA_EMAIL, emailAddress)

                try {
                    startActivity(Intent.createChooser(mIntent, "Choose Email Client..."))
                }
                catch (e: Exception){
                    Toast.makeText(this, e.message, Toast.LENGTH_LONG).show()
                }

            }
        }
    }
}