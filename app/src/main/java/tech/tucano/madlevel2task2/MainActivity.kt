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

    private fun initViews(){
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

    private fun loadQuestions(){
        for(i in resources.getStringArray(R.array.questions)){
            addQuestion(i)
        }
    }

    private fun addQuestion(question: String){
        if(question.isNotBlank()){
            questions.add(Question(question, false))
            questionAdapter.notifyDataSetChanged()
        }
    }

    private fun createItemTouchHelper() : ItemTouchHelper {
        var callback = object :ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT){
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

                if((!questions[position].answer && direction == 4) || (questions[position].answer && direction == 8)) {
                    questions.removeAt(position)
                    questionAdapter.notifyDataSetChanged()
                } else {

                    Snackbar.make(tvQuestion, "Wrong answer! Try again...", Snackbar.LENGTH_SHORT).show()
                    questionAdapter.notifyDataSetChanged()
                }
            }
        }
        return ItemTouchHelper(callback)
    }
}