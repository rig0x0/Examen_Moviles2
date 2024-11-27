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
import com.example.remc_exa_ll.repositories.CommentRepository
import com.example.remc_exa_ll.utils.CustomAdaptetComment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CommentActivity : AppCompatActivity() {

    private val commentRepository = CommentRepository()

    private lateinit var recyclerViewComments: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_comment)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Inicializa el RecyclerView dentro del onCreate
        recyclerViewComments = findViewById<RecyclerView>(R.id.rvComments)

        // Recuperar los datos enviados desde MainActivity
        val receivedId = intent.getIntExtra("id", 1)
        obtenerCommentsByPostId(receivedId)
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

    private fun obtenerCommentsByPostId(id: Int) {
        fetchData(
            repositoryCall = { commentRepository.getCommentsByPostId(id) },
            onSuccess = { comments ->
                Log.d("comments", comments.toString())

                // Requerimos un adaptador para recycler
                val adapterRecycler = CustomAdaptetComment(comments)
                // Diseño para despegar los items
                recyclerViewComments.layoutManager = LinearLayoutManager(this)
                // Le pasamos el adaptador al RecyclerView
                recyclerViewComments.adapter = adapterRecycler

                adapterRecycler.onSetClickListener(object : CustomAdaptetComment.onItemClickListener {
                    override fun onItemClick(position: Int) {

                    }

                    override fun onItemLongClick(position: Int) {

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