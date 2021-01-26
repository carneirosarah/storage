package com.example.firestorage

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_home.*


class Home : AppCompatActivity() {

    private lateinit var db: FirebaseFirestore
    private lateinit var cr: CollectionReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        config()

        btnSave.setOnClickListener {
            var prod = getData()
            sendProd(prod)
        }

        readProds()
    }

    fun config() {
        db = FirebaseFirestore.getInstance()
        cr = db.collection("produtos")
    }

    fun getData(): MutableMap<String, Any> {

        val prod : MutableMap<String, Any> = HashMap()

        prod["nome"] = edNomeProd.text.toString()
        prod["qtd"] = edQtdProd.text.toString()
        prod["preco"] = edPrecoProd.text.toString()

        return prod
    }

    fun sendProd(prod: MutableMap<String, Any>) {

        val nome = edNomeProd.text.toString()
        cr.document(nome).set(prod).addOnSuccessListener {
        }.addOnFailureListener {
            Log.i("HOME", it.toString())
        }
    }

    private fun updateProd(prod: MutableMap<String, Any>) {
        cr.document("key").update(prod)
    }

    private fun deleteProd(prod: MutableMap<String, Any>) {
        cr.document("key").update(prod)
    }

    fun readProds() {
        cr.get()
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        for (document in task.result!!) {
                            Log.d("HOME", document.id + " => " + document.data)
                        }
                    } else {
                        Log.w("HOME", "Error getting documents.", task.exception)
                    }
                }
    }
}