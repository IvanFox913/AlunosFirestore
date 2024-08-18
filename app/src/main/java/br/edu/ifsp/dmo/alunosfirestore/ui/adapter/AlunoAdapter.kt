package br.edu.ifsp.dmo.alunosfirestore.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.edu.ifsp.dmo.alunosfirestore.R
import br.edu.ifsp.dmo.alunosfirestore.data.model.Aluno
import br.edu.ifsp.dmo.alunosfirestore.databinding.ItemAlunoBinding

class AlunoAdapter: RecyclerView.Adapter<AlunoAdapter.ViewHolder>()  {

    private var dataset: List<Aluno> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_aluno, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.textNome.text = dataset[position].nome
    }

    override fun getItemCount(): Int {
        return dataset.size
    }

    fun submitDataset(dataset: List<Aluno>){
        this.dataset = dataset
        notifyDataSetChanged()
    }

    class ViewHolder(view: View): RecyclerView.ViewHolder(view){
        val binding: ItemAlunoBinding = ItemAlunoBinding.bind(view)
    }

}