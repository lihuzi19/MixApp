package com.lihuzi.takenotes.ui.activity

import android.content.Intent
import android.content.pm.PackageManager
import android.content.pm.Signature
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.tokenotes.R
import com.example.tokenotes.databinding.ActivityMainTakenotesBinding
import com.lihuzi.takenotes.utils.AppUtils
import com.lihuzi.takenotes.utils.FindVerify
import com.lihuzi.takenotes.viewmodel.NoteMainVM
import java.util.Arrays

class NotesMainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainTakenotesBinding
    lateinit var viewModel: NoteMainVM
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_takenotes)
        initMVVM()
        initView()
    }

    private fun initMVVM() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main_takenotes)
        viewModel = ViewModelProvider(this)[NoteMainVM::class.java]
        binding.notesVM = viewModel
        viewModel.initBinding(binding, this)
    }

    private fun initView() {
        window.statusBarColor = getColor(R.color.colorPrimary)
        binding.toolbar.title = "记账本"
        binding.toolbar.setTitleTextColor(getColor(android.R.color.white))

        binding.actCoroutine.setOnClickListener {
            startActivity(Intent(this, CoroutineTestActivity::class.java))
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        binding.unbind()
        binding.notesVM = null
    }

    override fun onResume() {
        super.onResume()
        getSignatureMd5()
        printVerify()
    }

    private fun getSignatureMd5() {
        var signature: Signature =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                packageManager.getPackageInfo(
                    packageName,
                    PackageManager.GET_SIGNING_CERTIFICATES
                ).signingInfo.apkContentsSigners[0]
            } else {
                packageManager.getPackageInfo(
                    packageName,
                    PackageManager.GET_SIGNATURES
                ).signatures[0]
            }
        var md5 = AppUtils.getSignatureString(signature, AppUtils.MD5)
        var sha1 = AppUtils.getSignatureString(signature, AppUtils.SHA1)
        var sha256 = AppUtils.getSignatureString(signature, AppUtils.SHA256)
        md5 = FindVerify.md5
        Log.e(NotesMainActivity::class.java.simpleName, "md5:$md5")
        Log.e(NotesMainActivity::class.java.simpleName, "sha1:$sha1")
        Log.e(NotesMainActivity::class.java.simpleName, "sha256:$sha256")
    }

    private fun printVerify() {
        var signatureArray: Array<Signature>? =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                packageManager.getPackageInfo(
                    packageName,
                    PackageManager.GET_SIGNING_CERTIFICATES
                ).signingInfo.apkContentsSigners
            } else {
                packageManager.getPackageInfo(
                    packageName,
                    PackageManager.GET_SIGNATURES
                ).signatures
            }
        signatureArray?.let {
            var byteArray =
                it[0].toByteArray()
            Log.e(
                NotesMainActivity::class.java.simpleName,
                "before: ${FindVerify.toHexString(byteArray)}"
            )
            Log.e(
                NotesMainActivity::class.java.simpleName,
                "before md5: ${FindVerify.findMd5(byteArray)}"
            )
            byteArray = FindVerify.find()
            Log.e(
                NotesMainActivity::class.java.simpleName,
                "after: ${FindVerify.toHexString(byteArray)}"
            )
            Log.e(
                NotesMainActivity::class.java.simpleName,
                "after md5: ${FindVerify.findMd5(byteArray)}"
            )
        }
        if (signatureArray.isNullOrEmpty()) {
            Log.e(NotesMainActivity::class.java.simpleName, "signature is null")
        }
    }
}