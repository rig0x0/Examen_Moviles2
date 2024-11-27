package com.example.remc_exa_ll
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.remc_exa_ll.data.ApiUser
import com.example.remc_exa_ll.data.CommentDAO
import com.example.remc_exa_ll.data.Db
import com.example.remc_exa_ll.data.PostDAO
import com.example.remc_exa_ll.data.UserDAO
import com.example.remc_exa_ll.entity.Address
import com.example.remc_exa_ll.entity.Company
import com.example.remc_exa_ll.entity.Geo
import com.example.remc_exa_ll.entity.UserEntity
import com.example.remc_exa_ll.helpers.isInternetAvailable
import com.example.remc_exa_ll.repositories.CommentRepository
import com.example.remc_exa_ll.repositories.PostRepository
import com.example.remc_exa_ll.repositories.UserRepository
import com.example.remc_exa_ll.utils.CustomAdaptetUser
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import toApiUsers

class MainActivity : AppCompatActivity() {

    private val userRepository = UserRepository()
    private val postRepository = PostRepository()
    private val commentRepository = CommentRepository()

    private lateinit var recyclerViewUsuarios: RecyclerView

    private lateinit var userDAO: UserDAO
    private lateinit var postDAO: PostDAO
    private lateinit var commentDAO: CommentDAO
    private lateinit var db: Db


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Inicializa el RecyclerView dentro del onCreate
        recyclerViewUsuarios = findViewById<RecyclerView>(R.id.rvUsuarios)
        db = Db.getDatabase(this)
        userDAO = db.userDAO()
        postDAO = db.postDAO()
        commentDAO = db.commentDAO()

        if(isInternetAvailable(this)) {
            obtenerUsuarios()
        } else {
            obtenerUsuariosOfline()
        }

    }

    private fun <T> fetchData(
        repositoryCall: suspend () -> T,
        onSuccess: (T) -> Unit,
        onError: (Exception) -> Unit
    ) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val result = repositoryCall()
                withContext(Dispatchers.Main) {
                    onSuccess(result)
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    onError(e)
                }
            }
        }
    }


    private fun obtenerUsuarios() {
        fetchData(
            repositoryCall = { userRepository.getAllUsers() },
            onSuccess = { usuarios ->
                Log.d("Usuarios", usuarios.toString())
                crearRecyclerView(usuarios)
            },
            onError = { e ->
                Toast.makeText(this, "Error al obtener usuarios: ${e.message}", Toast.LENGTH_LONG).show()
                Log.e("Usuarios", "Error: ${e}")
            }
        )
    }

    private fun obtenerUsuariosOfline() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                // Lista de mascotas
                val usuarios = db.userDAO().getAllUsers()
                crearRecyclerView(usuarios.toApiUsers())
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {

                }
            }
        }
    }

    private fun crearRecyclerView(usuarios: List<ApiUser>) {
        // Requerimos un adaptador para recycler
        val adapterRecycler = CustomAdaptetUser(usuarios)
        // Diseño para despegar los items
        recyclerViewUsuarios.layoutManager = LinearLayoutManager(this)
        // Le pasamos el adaptador al RecyclerView
        recyclerViewUsuarios.adapter = adapterRecycler

        adapterRecycler.onSetClickListener(object : CustomAdaptetUser.onItemClickListener {
            override fun onItemClick(position: Int) {
                val intent = Intent(this@MainActivity, PostsActivity::class.java)
                intent.putExtra("id", usuarios[position].id)
                startActivity(intent)
            }
            override fun onItemLongClick(position: Int) {
                // Guardar nuevo Usuario en la bd local
                CoroutineScope(Dispatchers.IO).launch {
                    try {

                        val newCompany = Company(
                            name = usuarios[position].company.name,
                            catchPhrase = usuarios[position].company.catchPhrase,
                            bs = usuarios[position].company.bs
                        )

                        val newGeo = Geo(
                            lat = usuarios[position].address.geo.lat,
                            lng = usuarios[position].address.geo.lng
                        )

                        val newAddress = Address(
                            city = usuarios[position].address.city,
                            street = usuarios[position].address.street,
                            suite = usuarios[position].address.suite,
                            zipcode = usuarios[position].address.zipcode,
                            geo = newGeo
                        )

                        val newUser = UserEntity(
                            id = usuarios[position].id,
                            name = usuarios[position].name,
                            username = usuarios[position].username,
                            email = usuarios[position].email,
                            address = newAddress,
                            company = newCompany
                        )

                        userDAO.insert(newUser)

                        // Mostrar notificacion exitosa
                        withContext(Dispatchers.Main) {
                            Toast.makeText(
                                this@MainActivity,
                                "Usuario $newUser.nombre agregado",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    } catch (e: Exception) {
                        Log.e("Usuarios", "Error: ${e}")
                        // Mostrar notificacion de que ocurrió un error al intentar guardar al usuario
                        withContext(Dispatchers.Main) {
                            Toast.makeText(
                                this@MainActivity,
                                "Ocurrió un error al intentar guardar al usuario",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }
                }
            }
        })
    }
}