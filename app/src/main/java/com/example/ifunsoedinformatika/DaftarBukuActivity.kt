package com.example.ifunsoedinformatika

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ifunsoedinformatika.data.model.BookDoc
import com.example.ifunsoedinformatika.databinding.ActivityDaftarBukuBinding
import com.example.ifunsoedinformatika.ui.adapter.BookAdapter
import com.example.ifunsoedinformatika.ui.adapter.OnBookClickListener
import com.example.ifunsoedinformatika.ui.fragment.BookDetailFragment
import com.example.ifunsoedinformatika.viewmodel.MainViewModel

class DaftarBukuActivity : AppCompatActivity(), OnBookClickListener {
    private lateinit var binding: ActivityDaftarBukuBinding
    private val viewModel: MainViewModel by viewModels()
    private val adapter = BookAdapter(emptyList(), this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDaftarBukuBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter

        viewModel.books.observe(this) {
            adapter.setData(it)
        }

        viewModel.fetchBooks("kotlin programming")
    }

    override fun onBookClick(book: BookDoc) {
        book.let { b->
            BookDetailFragment(
                b.title?:"-",
                b.authorName?.joinToString(", ")?: "Unknown Author",
                "${b.firstPublishYear}",
                b.coverId?:0
            ).show(supportFragmentManager, BookDetailFragment::class.java.simpleName)
        }
    }
}