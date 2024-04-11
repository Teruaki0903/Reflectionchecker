package com.example.first
import android.app.Activity
import android.content.Intent
import android.graphics.ColorMatrix
import android.graphics.ColorMatrixColorFilter
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import android.widget.ImageView
import android.widget.SeekBar
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var imageView: ImageView
    private lateinit var selectButton: Button
    private lateinit var colorChangeButton: Button
    private lateinit var resetButton: Button
    private lateinit var redSeekBar: SeekBar
    private lateinit var greenSeekBar: SeekBar
    private lateinit var blueSeekBar: SeekBar

    companion object {
        private const val PICK_IMAGE_REQUEST = 1
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        imageView = findViewById(R.id.imageView)
        selectButton = findViewById(R.id.selectButton)
        colorChangeButton = findViewById(R.id.colorChangeButton)
        resetButton = findViewById(R.id.resetButton)
        redSeekBar = findViewById(R.id.redSeekBar)
        greenSeekBar = findViewById(R.id.greenSeekBar)
        blueSeekBar = findViewById(R.id.blueSeekBar)

        selectButton.setOnClickListener {
            openGallery()
        }

        colorChangeButton.setOnClickListener {
            applyColorFilter()
        }

        resetButton.setOnClickListener{
            resetColorValue()
        }
    }

    private fun openGallery() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(intent, PICK_IMAGE_REQUEST)
    }

    private fun applyColorFilter() {
        val redValue = redSeekBar.progress.toFloat()
        val greenValue = greenSeekBar.progress.toFloat()
        val blueValue = blueSeekBar.progress.toFloat()

        val colorMatrix = ColorMatrix().apply {
            setScale(redValue / 122f, greenValue / 122f, blueValue / 122f, 1f)

        }
        val colorFilter = ColorMatrixColorFilter(colorMatrix)
        imageView.colorFilter = colorFilter
    }

    private fun resetColorValue() {
        redSeekBar.setProgress(122)
        greenSeekBar.setProgress(122)
        blueSeekBar.setProgress(122)
        applyColorFilter()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null) {
            val imageUri = data.data
            imageView.setImageURI(imageUri)
        }
    }
}
