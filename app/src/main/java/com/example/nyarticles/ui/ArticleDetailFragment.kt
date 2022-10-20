package com.example.nyarticles.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs
import com.example.nyarticles.databinding.ArticleDetailFragmentBinding

class ArticleDetailFragment: Fragment() {

    private lateinit var binding: ArticleDetailFragmentBinding
    private val viewModel by activityViewModels<ArticleViewModel>()
    private val args: ArticleDetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = ArticleDetailFragmentBinding.inflate(inflater).apply {
            viewmodel = viewModel
        }
        binding.lifecycleOwner = viewLifecycleOwner

        //Already consumed the event, set it to -ve
        //so when user press back it should not open the screen again
        viewModel.openArticleDetail(-1)
        viewModel.loadArticle(args.articleId)
        return binding.root
    }
}