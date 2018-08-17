package com.azimgaurav.smartstudentassistant.fragments;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.azimgaurav.smartstudentassistant.ExpenseAnalysis;
import com.azimgaurav.smartstudentassistant.FoodExpense;
import com.azimgaurav.smartstudentassistant.R;

public class ExpensesFragment extends Fragment {

    public ExpensesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_expenses, container, false);
        RelativeLayout budget_header=view.findViewById(R.id.budget_header_card_layout);
        Button btnEnterBudget=view.findViewById(R.id.btn_enter_budget);
        CardView cardFood=view.findViewById(R.id.card_food);
        CardView cardShopping=view.findViewById(R.id.card_shopping);
        CardView cardMoneyExchange=view.findViewById(R.id.card_money_exchange);
        CardView cardOthers=view.findViewById(R.id.card_others);

        cardFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), FoodExpense.class));
            }
        });

        btnEnterBudget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
                builder.setTitle("Enter amount");
                EditText et=new EditText(getActivity());
                et.setHint("Enter budget here..");
                builder.setView(et);
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                builder.show();
            }
        });
        budget_header.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(),ExpenseAnalysis.class));
            }
        });
        return view;
    }
}