package com.example.nyarticles.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.nyarticles.databinding.ArticleFragmentBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ArticleFragment: Fragment() {

    val viewModel by activityViewModels<ArticleViewModel>()
    private lateinit var binding: ArticleFragmentBinding
    private lateinit var articleAdapter: ArticleAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = ArticleFragmentBinding.inflate(inflater).apply {
            viewmodel = viewModel
        }

        viewModel.articles.observe(viewLifecycleOwner) {
            viewModel.refresh(it)
        }

        viewModel.openArticleDetail.observe(viewLifecycleOwner) {
            if(it > 0)
                findNavController().navigate(ArticleFragmentDirections.actionArticleFragmentToArticleDetailFragment(it))
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.lifecycleOwner = viewLifecycleOwner
        articleAdapter = ArticleAdapter(viewModel)
        binding.articles.adapter = articleAdapter
    }
}