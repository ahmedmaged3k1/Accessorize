package com.example.araccessories.ui.features.botMessaging

import android.content.ContentValues.TAG
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.araccessories.R
import com.example.araccessories.data.dataSource.localDataSource.entities.BotResponse
import com.example.araccessories.ui.core.utilities.Constants
import com.example.araccessories.ui.core.utilities.Time
import com.example.araccessories.data.dataSource.localDataSource.Message
import com.example.araccessories.databinding.FragmentBotBinding
import com.example.araccessories.ui.features.homeFragment.HomeFragmentViewModel
import com.example.araccessories.ui.features.mainNavigation.MainNavigationDirections
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@AndroidEntryPoint
class BotFragment : Fragment() ,  BotResponse.MessageClickListener {
    private lateinit var binding: FragmentBotBinding
    private  var adapter: MessagingAdapter = MessagingAdapter()
    private val botList = listOf("Peter", "Francesca", "Luigi", "Igor")
    private var botResponse = BotResponse(this)
    private val viewModel: BotViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentBotBinding.inflate(inflater, container, false)

        recyclerView()
        onClickSendMessage()

        val random = (0..3).random()
        customBotMessage("Hello! Today you're speaking with ${botList[random]}, how may I help?")
        return binding.root
    }
    private fun onClickSendMessage() {
        binding.btnSend.setOnClickListener {
            sendMessage()
        }

        //Scroll back to correct position when user clicks on text view
        binding.etMessage.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                delay(100)
                withContext(Dispatchers.Main) {
                    binding.rvMessages.scrollToPosition(adapter.itemCount - 1)

                }
            }
        }
    }
    private fun recyclerView() {
        adapter = MessagingAdapter()
        binding.rvMessages.adapter = adapter
        binding.rvMessages.layoutManager = LinearLayoutManager(context)

    }

    override fun onStart() {
        super.onStart()

        CoroutineScope(Dispatchers.IO).launch {
            delay(100)
            withContext(Dispatchers.Main) {
                binding.rvMessages.scrollToPosition(adapter.itemCount - 1)
            }
        }
    }
    private fun sendMessage() {
        val message = binding.etMessage.text.toString()
        val timeStamp = Time.timeStamp()
        if (message.isNotEmpty()) {
            binding.etMessage.setText("")
            adapter.insertMessage(Message(message, Constants.SEND_ID, timeStamp))
            binding.rvMessages.scrollToPosition(adapter.itemCount - 1)
            botResponse(message)
        }
    }
    private fun botResponse(message: String) {
        val timeStamp = Time.timeStamp()

        CoroutineScope(Dispatchers.IO).launch {
            delay(1000)
            withContext(Dispatchers.Main) {

                val response = botResponse.basicResponses(message)
                adapter.insertMessage(Message(response, Constants.RECEIVE_ID, timeStamp))
                binding.rvMessages.scrollToPosition(adapter.itemCount - 1)
                when (response) {
                    Constants.OPEN_GOOGLE -> {
                        val site = Intent(Intent.ACTION_VIEW)
                        site.data = Uri.parse("https://www.google.com/")
                        startActivity(site)
                    }
                    Constants.OPEN_SEARCH -> {
                        val site = Intent(Intent.ACTION_VIEW)
                        val searchTerm: String = message.substringAfterLast("search")
                        site.data = Uri.parse("https://www.google.com/search?&q=$searchTerm")
                        startActivity(site)
                    }
                }
                if (response.contains("redirect")){
                    val handler = Handler(Looper.getMainLooper())
                    handler.postDelayed({

                         val action = MainNavigationDirections.actionMainNavigationToProductDetailsFragment(viewModel.productRemote,0)
                         view?.findNavController()?.navigate(action)
                    }, 2500)
                }

            }

        }
    }
    private fun customBotMessage(message: String) {

        CoroutineScope(Dispatchers.IO).launch {
            delay(1000)
            withContext(Dispatchers.Main) {
                val timeStamp = Time.timeStamp()
                adapter.insertMessage(Message(message, Constants.RECEIVE_ID, timeStamp))
                binding.rvMessages.scrollToPosition(adapter.itemCount - 1)
            }
        }
    }

    override fun onMessageClick(name: String) {

        viewModel.searchByName(name)



    }



}