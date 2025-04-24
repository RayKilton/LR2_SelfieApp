package com.rk.lr2_selfieapp.presentation

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import com.rk.lr2_selfieapp.R
import java.io.File

class MainActivity : AppCompatActivity() {
    private lateinit var selfieView: ImageView
    private lateinit var btnTake: Button
    private lateinit var btnSend: Button
    private var photoUri: Uri? = null

    private val takePhotoLauncher = registerForActivityResult(  //безопасный вызвать камеру и получить результат
        ActivityResultContracts.TakePicture()                   // контракт, который говорит: "Открой камеру и сохрани фото в файл".
    ) { success ->
        if (success && photoUri != null) {
            selfieView.setImageURI(photoUri)
            btnSend.isEnabled = true
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        selfieView = findViewById(R.id.selfieView)
        btnTake = findViewById<Button>(R.id.buttonTakeSelfie)
        btnSend = findViewById(R.id.buttonSendSelfie)

        btnTake.setOnClickListener {
            takeSelfie()
        }

        btnSend.setOnClickListener {
            sendEmailWithPhoto()
        }
    }

    private fun takeSelfie() {
        val photoFile = File.createTempFile("selfie_", ".jpg", cacheDir)    //создает временный файл
        val uri = FileProvider.getUriForFile(this, "$packageName.provider", photoFile) //чтобы другие приложения (например, камера) могли безопасно сохранить туда фото.
        photoUri = uri // сохраняем в поле класса, чтобы потом использовать при возврате результата

        takePhotoLauncher.launch(uri) // передаём в лончер
    }

    private fun sendEmailWithPhoto() {
        val emailIntent = Intent(Intent.ACTION_SEND).apply {
            type = "image/*"
            putExtra(Intent.EXTRA_EMAIL, arrayOf("hodovychenko@op.edu.ua"))
            putExtra(Intent.EXTRA_SUBJECT, "ANDROID Плетос Анатолій")
            putExtra(Intent.EXTRA_TEXT, "Ось моє селфі. Репозиторій - ")
            putExtra(Intent.EXTRA_STREAM, photoUri)
            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        }

        startActivity(Intent.createChooser(emailIntent, "Відправити листа..."))
    }
}