package com.example.practicaguias_pedro.Activitys

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.practicaguias_pedro.R
import com.example.practicaguias_pedro.databinding.ActivityRestaurantesBinding
import com.example.practicaguias_pedro.recyclerViewRestaurantes.RestaurantesAdapter
import com.example.practicaguias_pedro.recyclerViewRestaurantes.RestaurantesR
import com.google.firebase.firestore.FirebaseFirestore

class RestaurantesA : AppCompatActivity() {
    lateinit var binding: ActivityRestaurantesBinding
    lateinit var ListaRestaurantes: MutableList<RestaurantesR>
    lateinit var adapter: RestaurantesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRestaurantesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.RVRestaurantes.layoutManager = LinearLayoutManager(this)
        ListaRestaurantes = ArrayList<RestaurantesR>()
        adapter = RestaurantesAdapter(ListaRestaurantes)
        binding.RVRestaurantes.adapter = adapter

        datos()

        binding.BAnaditrRestaurante.setOnClickListener {
            val intent = Intent(this, Insertar_Restaurante::class.java)
            startActivityForResult(intent, 1) // Inicia la actividad con un resultado esperado
        }
    }

    public fun datos() {
        val db = FirebaseFirestore.getInstance()

        db.collection("restaurantes").get().addOnSuccessListener { querySnapshot ->
            ListaRestaurantes.clear() // Limpia la lista antes de agregar nuevos elementos

            for (restaurantes in querySnapshot) {
                var Info = RestaurantesR(
                    nombre = restaurantes["Nombre"].toString(),
                    direccion = restaurantes["direccion"].toString(),
                    enlace = restaurantes["enlace"].toString(),
                    horario = restaurantes["horario"].toString()
                )
                ListaRestaurantes.add(Info)
            }
            adapter.notifyDataSetChanged() // Notifica al adaptador sobre el cambio en los datos
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_des,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.m_iglesia->{
                val intent = Intent(this, IglesiasA::class.java)
                startActivity(intent)
            }
            R.id.m_parques->{
                val intent = Intent(this, ParquesA::class.java)
                startActivity(intent)
            }
            R.id.m_monumentos->{
                val intent = Intent(this, MonumentosA::class.java)
                startActivity(intent)
            }
            R.id.m_restaurantes->{
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

    // Este método se ejecutará cuando se complete la actividad de inserción y se vuelva a esta actividad
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1 && resultCode == RESULT_OK) { // Si el código de solicitud y el resultado son correctos
            datos() // Actualiza la lista de restaurantes
        }
    }
}
