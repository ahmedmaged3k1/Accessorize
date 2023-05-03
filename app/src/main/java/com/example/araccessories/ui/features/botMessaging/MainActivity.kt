package com.example.araccessories.ui.features.botMessaging

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.codepalace.chatbot.utils.BotResponse
import com.codepalace.chatbot.utils.Constants
import com.codepalace.chatbot.utils.Time
import com.example.araccessories.R
import com.example.araccessories.data.dataSource.localDataSource.Message
import com.example.araccessories.databinding.ActivityMain3Binding
import com.example.araccessories.databinding.ActivityMainBinding
import kotlinx.android.synthetic.main.activity_main3.btn_send
import kotlinx.android.synthetic.main.activity_main3.et_message
import kotlinx.android.synthetic.main.activity_main3.rv_messages
import kotlinx.android.synthetic.main.activity_main3.view.btn_send
import kotlinx.android.synthetic.main.activity_main3.view.et_message
import kotlinx.android.synthetic.main.activity_main3.view.rv_messages
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {
    private val TAG = "MainActivity"

    //You can ignore this messageList if you're coming from the tutorial,
    // it was used only for my personal debugging
    var messagesList = mutableListOf<Message>()
    private  lateinit var binding : ActivityMain3Binding
    private  var adapter: MessagingAdapter = MessagingAdapter()
    private val botList = listOf("Peter", "Francesca", "Luigi", "Igor")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMain3Binding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)


        recyclerView()

        clickEvents()


        val random = (0..3).random()
        customBotMessage("Hello! Today you're speaking with ${botList[random]}, how may I help?")
    }

    private fun clickEvents() {

        //Send a message

        binding.btnSend.setOnClickListener {
            sendMessage()
        }

        //Scroll back to correct position when user clicks on text view
        et_message.setOnClickListener {
            GlobalScope.launch {
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
        binding.rvMessages.layoutManager = LinearLayoutManager(applicationContext)

    }

    override fun onStart() {
        super.onStart()
        //In case there are messages, scroll to bottom when re-opening app
        GlobalScope.launch {
            delay(100)
            withContext(Dispatchers.Main) {
                binding.rvMessages.scrollToPosition(adapter.itemCount - 1)
            }
        }
    }

    private fun sendMessage() {
        val message = et_message.text.toString()
        val timeStamp = Time.timeStamp()

        if (message.isNotEmpty()) {
            //Adds it to our local list
            messagesList.add(Message(message, Constants.SEND_ID, timeStamp))
            binding.etMessage.setText("")

            adapter.insertMessage(Message(message, Constants.SEND_ID, timeStamp))
            binding.rvMessages.scrollToPosition(adapter.itemCount - 1)

            botResponse(message)
        }
    }

    private fun botResponse(message: String) {
        val timeStamp = Time.timeStamp()

        GlobalScope.launch {
            //Fake response delay
            delay(1000)

            withContext(Dispatchers.Main) {
                //Gets the response
                val response = BotResponse.basicResponses(message)

                //Adds it to our local list
                messagesList.add(Message(response, Constants.RECEIVE_ID, timeStamp))

                //Inserts our message into the adapter
                adapter.insertMessage(Message(response, Constants.RECEIVE_ID, timeStamp))

                //Scrolls us to the position of the latest message
                binding.rvMessages.scrollToPosition(adapter.itemCount - 1)

                //Starts Google
                when (response) {
                    Constants.OPEN_GOOGLE -> {
                        val site = Intent(Intent.ACTION_VIEW)
                        site.data = Uri.parse("https://www.google.com/")
                        startActivity(site)
                    }
                    Constants.OPEN_SEARCH -> {
                        val site = Intent(Intent.ACTION_VIEW)
                        val searchTerm: String? = message.substringAfterLast("search")
                        site.data = Uri.parse("https://www.google.com/search?&q=$searchTerm")
                        startActivity(site)
                    }

                }
            }
        }
    }

    private fun customBotMessage(message: String) {

        GlobalScope.launch {
            delay(1000)
            withContext(Dispatchers.Main) {
                val timeStamp = Time.timeStamp()
                messagesList.add(Message(message, Constants.RECEIVE_ID, timeStamp))
                adapter.insertMessage(Message(message, Constants.RECEIVE_ID, timeStamp))

                binding.rvMessages.scrollToPosition(adapter.itemCount - 1)
            }
        }
    }
}