package br.edu.ifsp.dmo.alunosfirestore.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.edu.ifsp.dmo.alunosfirestore.data.model.Aluno
import br.edu.ifsp.dmo.alunosfirestore.data.repository.AlunoRepository

class MainViewModel: ViewModel() {

    private val repository = AlunoRepository()
    private val _students = MutableLiveData<List<Aluno>>()
    val students: LiveData<List<Aluno>> = _students
    private val _inserted = MutableLiveData<Boolean>()
    val inserted: LiveData<Boolean> = _inserted

    init {
        loadStudents()
    }

    fun insert(name: String, prontuario: Int, nascimento: Int) {
        val aluno = Aluno(nascimento, name, prontuario)
        repository.insert(aluno) { result ->
            _inserted.value = result
        }
    }

    private fun loadStudents() {
        repository.findAll { list -> _students.value = list }
    }
}