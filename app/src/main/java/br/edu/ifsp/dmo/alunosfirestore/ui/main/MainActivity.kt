package br.edu.ifsp.dmo.alunosfirestore.ui.main

import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import br.edu.ifsp.dmo.alunosfirestore.R
import br.edu.ifsp.dmo.alunosfirestore.databinding.ActivityMainBinding
import br.edu.ifsp.dmo.alunosfirestore.databinding.DialogAlunoBinding
import br.edu.ifsp.dmo.alunosfirestore.ui.adapter.AlunoAdapter

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel
    private val adapter = AlunoAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        setupRecyclerView()
        setupListeners()
        setupObservers()
    }

    private fun setupListeners() {
        binding.buttonAdicionar.setOnClickListener{
            openInputDialog()
        }
    }

    private fun setupObservers() {
        viewModel.students.observe(this, Observer { alunos ->
            for(aluno in alunos){
                Log.d("DMO", "Students: ${aluno.nome}")
            }
            adapter.submitDataset(alunos)
            //adapter.notifyDataSetChanged()
        })
        viewModel.inserted.observe(this, Observer {
            val str = if (it){
                "Aluno salvo com sucesso"
            } else {
                "Erro ao salvar aluno."
            }
            Toast.makeText(this, str, Toast.LENGTH_SHORT).show()
        })
    }

    private fun setupRecyclerView() {
        binding.recyclerAlunos.layoutManager = LinearLayoutManager(this)
        binding.recyclerAlunos.adapter = adapter
    }

    private fun openInputDialog() {
        val dialogView = layoutInflater.inflate(R.layout.dialog_aluno, null)
        val bindingDialog: DialogAlunoBinding = DialogAlunoBinding.bind(dialogView)
        val builder = AlertDialog.Builder(this)
            .setView(dialogView)
            .setTitle("Adicionar aluno")
            .setPositiveButton("Salvar", DialogInterface.OnClickListener{ dialog,
                                                                          which ->
                val strNome = bindingDialog.editNome.text.toString()
                val strNascimento = bindingDialog.editNascimento.text.toString()
                val strProntuario = bindingDialog.editProntuario.text.toString()
                viewModel.insert(strNome, strProntuario.toInt(),
                    strNascimento.toInt())
                dialog.dismiss()
            })
            .setNegativeButton("Cancelar", DialogInterface.OnClickListener {
                    dialog, i ->
                dialog.dismiss()
            })
        val dialog = builder.create()
        dialog.show()
    }
}
