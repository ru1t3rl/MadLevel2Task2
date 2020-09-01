package tech.tucano.madlevel2task2

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.question_item.*
import tech.tucano.madlevel2task2.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private val questions = arrayListOf<Question>()
    private val questionAdapter = QuestionAdapter(questions)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initViews()
    }

    private fun initViews() {
        binding.rvQuestions.layoutManager = LinearLayoutManager(
            this@MainActivity,
            RecyclerView.VERTICAL,
            false
        )
        binding.rvQuestions.addItemDecoration(
            DividerItemDecoration(
                this@MainActivity,
                RecyclerView.VERTICAL
            )
        )

        binding.rvQuestions.adapter = questionAdapter

        createItemTouchHelper().attachToRecyclerView(binding.rvQuestions)

        loadQuestions()
    }

    private fun loadQuestions() {
        for ((i, question) in resources.getStringArray(R.array.questions).withIndex()) {
            when(i) {
                0 -> addQuestion(question, resources.getBoolean(R.bool.q1))
                1 -> addQuestion(question, resources.getBoolean(R.bool.q2))
                2 -> addQuestion(question, resources.getBoolean(R.bool.q3))
                3 -> addQuestion(question, resources.getBoolean(R.bool.q4))
                else -> print("Error, could not load the answer and question")
            }
        }
    }

    private fun addQuestion(question: String, answer: Boolean) {
        if (question.isNotBlank()) {
            questions.add(Question(question, answer))
            questionAdapter.notifyDataSetChanged()
        }
    }

    private fun createItemTouchHelper(): ItemTouchHelper {
        var callback = object :
            ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            /**
             * Directions:
             * Right - 8 - true
             * Left - 4 - false
             */
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition

                if ((!questions[position].answer && direction == 4) || (questions[position].answer && direction == 8)) {
                    questions.removeAt(position)
                    questionAdapter.notifyDataSetChanged()
                } else {

                    Snackbar.make(tvQuestion, "Wrong answer! Try again...", Snackbar.LENGTH_SHORT)
                        .show()
                    questionAdapter.notifyDataSetChanged()
                }
            }
        }
        return ItemTouchHelper(callback)
    }
}