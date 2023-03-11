package com.example.wardrobeapp.view

import android.Manifest
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.isEmpty
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Database
import com.example.wardrobeapp.databinding.ActivityMainBinding
import com.example.wardrobeapp.db.WardRobeDatabase
import com.example.wardrobeapp.model.Pant
import com.example.wardrobeapp.model.Shirt
import com.example.wardrobeapp.view.adapter.PantAdapter
import com.example.wardrobeapp.view.adapter.ShirtAdapter
import com.example.wardrobeapp.viewmodel.ViewModel
import java.io.File


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var wardRobeDatabase: WardRobeDatabase
    lateinit var viewModel: ViewModel
    lateinit var shirtAdapter: ShirtAdapter
    lateinit var pantAdapter: PantAdapter
    var shirtList: ArrayList<Shirt> = arrayListOf()
    var pantList: ArrayList<Pant> = arrayListOf()

    private val READ_EXTERNAL_STORAGE_REQUEST_CODE = 100
    private val READ_EXTERNAL_STORAGE_REQUEST_CODE_SHIRT = 101
    private val READ_EXTERNAL_STORAGE_REQUEST_CODE_PANT = 102



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val callback = object : OnBackPressedCallback(true){
            override fun handleOnBackPressed() {

                this@MainActivity.finish()
                this@MainActivity.finishAffinity()
            }

        }

        this@MainActivity.onBackPressedDispatcher.addCallback(callback)
        setContentView(binding.root)
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.READ_EXTERNAL_STORAGE
            )
            != PackageManager.PERMISSION_GRANTED
        ) {
            //not granted
            ActivityCompat.requestPermissions(
                this,
                arrayOf(
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                ),
                READ_EXTERNAL_STORAGE_REQUEST_CODE
            )
        } else {
            //granted

            setUpApp()
        }


    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == READ_EXTERNAL_STORAGE_REQUEST_CODE) {
            if (grantResults.isNotEmpty() &&
                grantResults[0] == PackageManager.PERMISSION_GRANTED
            ) {
                setUpApp()
            } else {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                    ),
                    READ_EXTERNAL_STORAGE_REQUEST_CODE
                )
            }
        }
    }

    private fun setUpApp() {

        viewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        )[ViewModel::class.java]

        wardRobeDatabase = WardRobeDatabase.getDatabase(this)

        viewModel.allShirts.observe(this, Observer { listofshirt ->
            shirtAdapter.updateCustomerList(listofshirt as ArrayList<Shirt>)
            if(listofshirt.isEmpty())
            {
                binding.shirtText.visibility = View.VISIBLE
            }
            else
            {
                binding.shirtText.visibility = View.GONE
            }

        })
        viewModel.allPants.observe(this, Observer {
            pantAdapter.updatePantList(it as ArrayList<Pant>)
            if(it.isEmpty())
            {
                binding.pantText.visibility = View.VISIBLE
            }
            else
            {

                binding.pantText.visibility = View.GONE
            }
        })

        setUpRecyclerView()

        binding.swapBtn.setOnClickListener {
            viewModel.allShirts.observe(this, Observer {
                shirtList = it as ArrayList<Shirt>

            })
            viewModel.allPants.observe(this, Observer {
                pantList = it as ArrayList<Pant>

            })
            Log.i("randomValue1", "${shirtList.size}   ${pantList.size}")

            val randShrit = (0..shirtList.size).random()
            val randPant = (0..pantList.size).random()
            binding.firstViewPager.setCurrentItem(randShrit)
            binding.secondViewPager.setCurrentItem(randPant)
        }
        binding.addShirtBtn.setOnClickListener {


            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(intent, READ_EXTERNAL_STORAGE_REQUEST_CODE_SHIRT)
        }


        binding.addPantBtn.setOnClickListener {
            val intent =
                Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(intent, READ_EXTERNAL_STORAGE_REQUEST_CODE_PANT)
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == READ_EXTERNAL_STORAGE_REQUEST_CODE_SHIRT && resultCode == RESULT_OK && data != null && data.data != null) {
            val uri = data.data
            addShirtData(uri)
        }
        if (requestCode == READ_EXTERNAL_STORAGE_REQUEST_CODE_PANT && resultCode == RESULT_OK && data != null) {
            val imageUri: Uri? = data.data
            Log.i("checkImage", imageUri.toString())
            addPantData(imageUri)
        }
//        setUpApp()
    }


    private fun addPantData(pantimg: Uri?) {
        viewModel.insertPant(Pant(0, uriToImageFile(pantimg!!)!!.absolutePath))

    }

    private fun addShirtData(shirtimg: Uri?) {

        viewModel.insertShirt(Shirt(0, uriToImageFile(shirtimg!!)!!.absolutePath))
    }

    private fun setUpRecyclerView(
    ) {

        shirtAdapter = ShirtAdapter(this)
        pantAdapter = PantAdapter(this)
        binding.firstViewPager.adapter = shirtAdapter

        binding.secondViewPager.adapter = pantAdapter
    }

    private fun uriToImageFile(uri: Uri): File? {
        val filePathColumn = arrayOf(MediaStore.Images.Media.DATA)
        val cursor = contentResolver.query(uri, filePathColumn, null, null, null)
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                val columnIndex = cursor.getColumnIndex(filePathColumn[0])
                val filePath = cursor.getString(columnIndex)
                cursor.close()
                return File(filePath)
            }
            cursor.close()
        }
        return null
    }

}