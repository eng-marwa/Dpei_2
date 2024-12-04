package com.example.dpei_2

import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.dpei_2.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private val TAG = "MainActivity"
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate: ")
        enableEdgeToEdge()
//        setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        initComponents()


    }

    private fun initComponents() {
        binding.btnGoToSecondActivity.setOnClickListener {
            val studentName = binding.etStudentName.text.toString()
            val studentDegree = binding.etStudentDegree.text.toString()
            val s = Student(studentName, studentDegree.toDouble())

            val intent = Intent(this, SecondActivity::class.java)
            intent.putExtra("data", s)
            startActivity(intent)
        }


        binding.btnGoToGoogleMap.setOnClickListener {
            //action , data, type
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("geo:0,0"))
            intent.setPackage("com.google.android.apps.maps")
            if (intent.resolveActivity(packageManager) != null) {
                startActivity(intent)
            }
        }


        binding.btnPickImage.setOnClickListener {
            when {
                ContextCompat.checkSelfPermission(
                    this,
                    android.Manifest.permission.CAMERA
                ) == PackageManager.PERMISSION_GRANTED -> {
                    openCamera()
                }

                else -> {
                    cameraPermissions.launch(android.Manifest.permission.CAMERA)
                }
            }
        }


    }

    override fun onRestart() {
        super.onRestart()
        Log.d(TAG, "onRestart: ")
    }

    //for permission
    val cameraPermissions: ActivityResultLauncher<String> =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                openCamera()

            } else {
                Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show()
            }
            //handle user result
        }

    //open camera
    val takePicture: ActivityResultLauncher<Intent> =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                val i: Intent? = result.data
                val imageUri = i?.extras?.get("data") as Bitmap
                binding.ivPickedImage.setImageBitmap(imageUri)
//            Toast.makeText(this, "image uri: $imageUri", Toast.LENGTH_SHORT).show()

            } else {
                Toast.makeText(this, "user cancel operation ", Toast.LENGTH_SHORT).show()
            }

        }

    private fun openCamera() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            takePicture.launch(takePictureIntent)
        }
    }


    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart: ")

    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume: ")
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause: ")
        //save data
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop: ")
        //unregister listener
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy: ")
    }


}
/*
Intent
-------
1- Explicit Intent

2- Implicit Intent


-------------------
Pass data between activities
life cycle of activity
-------------------
ViewBinding

-------------------
Scoped function
 */