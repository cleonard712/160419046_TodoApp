package com.ubaya.todoapp.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.ubaya.todoapp.R
import com.ubaya.todoapp.viewmodel.TodoListViewModel
import kotlinx.android.synthetic.main.fragment_todo_list.*


/**
 * A simple [Fragment] subclass.
 * Use the [TodoListFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class TodoListFragment : Fragment() {
    private lateinit var viewModel:TodoListViewModel
    private val todoListAdapter = TodoListAdapter(arrayListOf()) { todo ->
        viewModel.update(todo.uuid)
        Toast.makeText(view?.context,"done",Toast.LENGTH_SHORT).show()
        observeViewModel()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_todo_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this).get(TodoListViewModel::class.java)
        viewModel.refresh()
        recViewTodo.layoutManager = LinearLayoutManager(context)
        recViewTodo.adapter = todoListAdapter

        fabAddTodo.setOnClickListener {
            val action = TodoListFragmentDirections.actionCreateTodo()
            Navigation.findNavController(it).navigate(action)
        }
        observeViewModel()
    }
    fun observeViewModel(){
        viewModel.todoLD.observe(viewLifecycleOwner, Observer {
            todoListAdapter.updateTodoList(it)
            if (it.isEmpty()){
                textEmpty.visibility = View.VISIBLE
            }else{
                textEmpty.visibility = View.GONE
            }
        })
    }
}