package com.lihuzi.takenotes.viewmodel

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModel
import com.example.tokenotes.databinding.ActivityMainTakenotesBinding
import com.lihuzi.takenotes.db.CoreDB
import com.lihuzi.takenotes.ui.activity.RecordActivity
import com.lihuzi.takenotes.utils.GoodsTypeUtils
import com.lihuzi.takenotes.utils.notNull

class NoteMainVM : ViewModel() {

    private lateinit var binding: ActivityMainTakenotesBinding
    private lateinit var context: Context

    fun initBinding(binding: ActivityMainTakenotesBinding, context: Context) {
        this.binding = binding
        this.context = context
        initView()
    }


    private fun initView() {
        binding.actActType.setOnClickListener {
            showTypeDialog()
        }
        binding.actActQuery.setOnClickListener {
            context.startActivity(Intent(context, RecordActivity::class.java))
        }
        binding.actActDone.setOnClickListener {
            if (binding.actActName.text.isNotBlank() &&
                    binding.actActSum.text.isNotBlank() &&
                    binding.actActType.text.isNotBlank() &&
                    binding.actActDesc.text.isNotBlank()) {
                notNull(binding.actActSum.text.toString().toFloatOrNull(),
                        binding.actActType.text.toString().toIntOrNull(),
                        success = {
                            it as Array<*>
                            CoreDB.insertNotes(
                                    binding.actActName.text.toString(),
                                    it[0] as Float,
                                    it[1] as Int,
                                    binding.actActDesc.text.toString(), System.currentTimeMillis()
                            )
                        },
                        fail = { Toast.makeText(context, "请输入正确的内容", Toast.LENGTH_SHORT).show() }
                )
            } else {
                Toast.makeText(context, "请输入正确的内容", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun showTypeDialog() {
        var builder = AlertDialog.Builder(context)
        builder.setItems(arrayOf("支出", "收入")) { _: DialogInterface, which: Int ->
            showTypeArrayDialog(which == 1)
        }
        builder.create().show()
    }

    private fun showTypeArrayDialog(obtain: Boolean) {
        var builder = AlertDialog.Builder(context)
        builder.setItems(GoodsTypeUtils.getItem(obtain)) { _: DialogInterface, which: Int ->
            binding.actActType.setText(GoodsTypeUtils.getTypeWithPosition(obtain, which).toString())
        }
        builder.create().show()
    }
}