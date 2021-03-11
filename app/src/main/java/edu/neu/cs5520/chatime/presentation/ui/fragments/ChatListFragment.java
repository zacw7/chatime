package edu.neu.cs5520.chatime.presentation.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import edu.neu.cs5520.chatime.R;
import edu.neu.cs5520.chatime.presentation.ui.viewmodel.ChatListViewModel;

public class ChatListFragment extends Fragment {

  private ChatListViewModel chatListViewModel;

  public View onCreateView(@NonNull LayoutInflater inflater,
      ViewGroup container, Bundle savedInstanceState) {
    chatListViewModel =
        new ViewModelProvider(this).get(ChatListViewModel.class);
    View root = inflater.inflate(R.layout.fragment_chat_list, container, false);
    final TextView textView = root.findViewById(R.id.text_chat_list);
    chatListViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
      @Override
      public void onChanged(@Nullable String s) {
        textView.setText(s);
      }
    });
    return root;
  }
}