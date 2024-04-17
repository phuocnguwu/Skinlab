package com.example.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.models.Account;
import com.example.skinlab.R;

import java.util.List;

public class AccountRecyclerAdapter {
    private List<Account> AccountList;
    private Context context;

    public AccountRecyclerAdapter(List<Account> accountList, Context context) {
        AccountList = accountList;
        this.context = context;
    }


}
