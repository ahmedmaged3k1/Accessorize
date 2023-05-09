package com.example.araccessories.data.dataSource.localDataSource.entities

import com.example.araccessories.ui.core.utilities.Constants.OPEN_GOOGLE
import com.example.araccessories.ui.core.utilities.Constants.OPEN_SEARCH
import com.example.araccessories.ui.core.utilities.RecommendProducts
import com.example.araccessories.ui.core.utilities.SolveMath
import java.sql.Date
import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.util.Locale

class BotResponse( private val listener: MessageClickListener) {
    interface MessageClickListener {
        fun onMessageClick(name: String)

    }


    fun basicResponses(_message: String): String {
        RecommendProducts.initializeRecommenders()
        val random = (0..2).random()
        val message = _message.lowercase(Locale.getDefault())
        if (message.contains("el nadara el gamda")) {
            listener.onMessageClick("Rouge")
        } else if (message.contains("rouge")) {
            listener.onMessageClick("El Nadara El gamda")
        } else if (message.contains("mask amirat")) {
            listener.onMessageClick("Hat Belki")
        } else if (message.contains("hat belki")) {
            listener.onMessageClick("Mask Amirat")
        }
        return when {

            //Flips a coin
            message.contains("flip") && message.contains("coin") -> {
                val r = (0..1).random()
                val result = if (r == 0) "heads" else "tails"

                "I flipped a coin and it landed on $result"
            }

            //Math calculations
            message.contains("solve") -> {
                val equation: String? = message.substringAfterLast("solve")
                return try {
                    val answer = SolveMath.solveMath(equation ?: "0")
                    "$answer"

                } catch (e: Exception) {
                    "Sorry, I can't solve that."
                }
            }

            //Hello
            message.contains("hello") -> {
                when (random) {
                    0 -> "Hello there!"
                    1 -> "Sup"
                    2 -> "Buongiorno!"
                    else -> "error"
                }
            }

            //How are you?
            message.contains("how are you") -> {
                when (random) {
                    0 -> "I'm doing fine, thanks!"
                    1 -> "I'm hungry..."
                    2 -> "Pretty good! How about you?"
                    else -> "error"
                }
            }

            //What time is it?
            message.contains("time") && message.contains("?") -> {
                val timeStamp = Timestamp(System.currentTimeMillis())
                val sdf = SimpleDateFormat("dd/MM/yyyy HH:mm")
                val date = sdf.format(Date(timeStamp.time))

                date.toString()
            }

            //Open Google
            message.contains("open") && message.contains("google") -> {
                OPEN_GOOGLE
            }

            message.contains("help") && message.contains("google") -> {
                "I can help you by recommending for you what to buy"
            }


            //Search on the internet
            message.contains("search") -> {
                OPEN_SEARCH
            }

            message.contains("rouge") -> {
                "People Often buy El Nadara El gamda   with the red rouge  \n" +
                        "I will redirect you to this product\n"

            }

            message.contains("hat belki") -> {
                "People Often buy  mask amirat with  hat belki     \n" +
                        "I will redirect you to this product\n"

            }

            message.contains("mask amirat") -> {
                "People Often buy hat belki  with mask amirat  \n" +
                        "I will redirect you to this product\n"

            }

            message.contains("el nadara el gamda") -> {
                "People Often buy red rouge with the  El Nadara El gamda  E\n" +
                        "I will redirect you to this product\n"

            }

            //When the programme doesn't understand...
            else -> {
                when (random) {
                    0 -> "I don't understand..."
                    1 -> "Try asking me something different"
                    2 -> "I don't know "
                    else -> "error"
                }
            }
        }
    }


}