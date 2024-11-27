package com.example.remc_exa_ll

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.remc_exa_ll.repositories.PostRepository
import com.example.remc_exa_ll.utils.CustomAdaptetPost
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class PostsActivity : AppCompatActivity() {

    private val postRepository = PostRepository()

    private lateinit var recyclerViewPosts: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_posts)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Inicializa el RecyclerView dentro del onCreate
        recyclerViewPosts = findViewById<RecyclerView>(R.id.rvPosts)

        // Recuperar los datos enviados desde MainActivity
        val receivedId = intent.getIntExtra("id", 1)
        obtenerPostsByUserId(receivedId)
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

    private fun obtenerPostsByUserId(id: Int) {
        fetchData(
            repositoryCall = { postRepository.getPostsByUserId(id) },
            onSuccess = { posts ->
                Log.d("posts", posts.toString())

                // Requerimos un adaptador para recycler
                val adapterRecycler = CustomAdaptetPost(posts)
                // Diseño para despegar los items
                recyclerViewPosts.layoutManager = LinearLayoutManager(this)
                // Le pasamos el adaptador al RecyclerView
                recyclerViewPosts.adapter = adapterRecycler

                adapterRecycler.onSetClickListener(object : CustomAdaptetPost.onItemClickListener {
                    override fun onItemClick(position: Int) {
                        Toast.makeText(this@PostsActivity,"Touch:"+posts[position].title ,Toast.LENGTH_SHORT).show()
                    }

                    override fun onItemLongClick(position: Int) {
                        Toast.makeText(this@PostsActivity,"Largo Touch:"+posts[position].title ,Toast.LENGTH_SHORT).show()
                    }

                })
            },
            onError = { e ->
                Toast.makeText(this, "Error al obtener posts: ${e.message}", Toast.LENGTH_LONG).show()
                Log.e("Posts", "Error: ${e}")
            }
        )
    }

}