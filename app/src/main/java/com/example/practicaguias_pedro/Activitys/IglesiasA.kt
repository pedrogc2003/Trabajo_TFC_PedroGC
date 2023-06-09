package com.example.practicaguias_pedro.Activitys

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.practicaguias_pedro.R
import com.example.practicaguias_pedro.databinding.ActivityIglesiasBinding
import com.example.practicaguias_pedro.recyclerViewIglesias.IglesiaAdapter
import com.example.practicaguias_pedro.recyclerViewIglesias.IglesiasR
import com.google.firebase.firestore.FirebaseFirestore

class IglesiasA : AppCompatActivity() {
    private lateinit var binding: ActivityIglesiasBinding
    private val db = FirebaseFirestore.getInstance()
    private lateinit var listaIglesias: MutableList<IglesiasR>

    companion object {
        private const val INSERT_IGLESIA_REQUEST_CODE = 1
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityIglesiasBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.RVIglesias.layoutManager = LinearLayoutManager(this)

        datos()

        binding.BInsertarIglesia.setOnClickListener {
            val intent = Intent(this, Insertar_Iglesia::class.java)
            startActivityForResult(intent, INSERT_IGLESIA_REQUEST_CODE)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == INSERT_IGLESIA_REQUEST_CODE && resultCode == RESULT_OK) {
            datos()
        }
    }

    private fun datos() {
        listaIglesias = ArrayList<IglesiasR>()

        db.collection("iglesias").get().addOnSuccessListener { querySnapshot ->
            for (document in querySnapshot) {
                val nombre = document.getString("Nombre") ?: ""
                val direccion = document.getString("direccion") ?: ""
                val enlace = document.getString("enlace") ?: ""
                val horario = document.getString("horario") ?: ""
                val iglesia = IglesiasR(nombre, direccion, enlace, horario)
                listaIglesias.add(iglesia)
            }
            binding.RVIglesias.adapter = IglesiaAdapter(listaIglesias)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_des, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.m_iglesia -> {
                val intent = Intent(this, IglesiasA::class.java)
                startActivity(intent)
            }
            R.id.m_parques -> {
                val intent = Intent(this, ParquesA::class.java)
                startActivity(intent)
            }
            R.id.m_monumentos -> {
                val intent = Intent(this, MonumentosA::class.java)
                startActivity(intent)
            }
            R.id.m_restaurantes -> {
                val intent = Intent(this, RestaurantesA::class.java)
                startActivity(intent)
            }
            R.id.m_otros -> {
                val intent = Intent(this, Otros::class.java)
                startActivity(intent)
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
