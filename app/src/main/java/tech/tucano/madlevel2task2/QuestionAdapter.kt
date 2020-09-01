package tech.tucano.madlevel2task2

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import tech.tucano.madlevel2task2.databinding.QuestionItemBinding

class QuestionAdapter (private val questions: List<Question>) :
    RecyclerView.Adapter<QuestionAdapter.ViewHolder>() {
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        private val binding = QuestionItemBinding.bind(itemView)

        fun dataBind(question: Question){
            binding.tvQuestion.text = question.question
        }
    }

    override fun getItemCount() : Int {
        return questions.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.question_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.dataBind(questions[position])
    }
}