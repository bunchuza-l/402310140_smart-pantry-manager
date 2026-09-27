package com.richfield.smartpantrymanager.ui.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.richfield.smartpantrymanager.R;
import com.richfield.smartpantrymanager.ui.model.Ingredient;

import java.util.List;

/**
 * Adapter for rendering ingredient list cards with expiration status indicators.
 */
public class IngredientAdapter extends ArrayAdapter<Ingredient> {

    public interface OnIngredientActionListener {
        void onEdit(Ingredient ingredient);
        void onDelete(Ingredient ingredient);
    }

    private final OnIngredientActionListener listener;

    public IngredientAdapter(@NonNull Context context, @NonNull List<Ingredient> ingredients, OnIngredientActionListener listener) {
        super(context, 0, ingredients);
        this.listener = listener;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_ingredient, parent, false);
        }

        Ingredient ingredient = getItem(position);

        TextView textViewName = convertView.findViewById(R.id.textViewName);
        TextView textViewQuantity = convertView.findViewById(R.id.textViewQuantity);
        TextView textViewExpiry = convertView.findViewById(R.id.textViewExpiry);
        TextView textViewStatusBadge = convertView.findViewById(R.id.textViewStatusBadge);
        LinearLayout layoutCardContent = convertView.findViewById(R.id.layoutCardContent);
        ImageButton buttonEdit = convertView.findViewById(R.id.buttonEdit);
        ImageButton buttonDelete = convertView.findViewById(R.id.buttonDelete);

        if (ingredient != null) {
            textViewName.setText(ingredient.getName());
            textViewQuantity.setText("📦 Quantity: " + ingredient.getQuantity() + " " + ingredient.getUnit());

            GradientDrawable badgeBg = new GradientDrawable();
            badgeBg.setCornerRadius(16f);

            if (ingredient.isExpired()) {
                textViewName.setTextColor(Color.RED);
                textViewExpiry.setText("Expired!");
                textViewStatusBadge.setText(R.string.status_expired);
                textViewStatusBadge.setTextColor(ContextCompat.getColor(getContext(), R.color.status_expired_text));
                badgeBg.setColor(ContextCompat.getColor(getContext(), R.color.status_expired_bg));
                layoutCardContent.setBackgroundColor(Color.parseColor("#FFF5F5"));
            } else if (ingredient.isNearExpiry()) {
                textViewName.setTextColor(Color.parseColor("#FFA500")); // Orange
                textViewExpiry.setText("Expires soon!");
                textViewStatusBadge.setText(R.string.status_expiring_soon);
                textViewStatusBadge.setTextColor(ContextCompat.getColor(getContext(), R.color.status_warning_text));
                badgeBg.setColor(ContextCompat.getColor(getContext(), R.color.status_warning_bg));
                layoutCardContent.setBackgroundColor(Color.parseColor("#FFFBEB"));
            } else {
                textViewName.setTextColor(Color.BLACK);
                textViewExpiry.setText("Expires on: " + ingredient.getExpiryDate());
                textViewStatusBadge.setText(R.string.status_fresh);
                textViewStatusBadge.setTextColor(ContextCompat.getColor(getContext(), R.color.status_fresh_text));
                badgeBg.setColor(ContextCompat.getColor(getContext(), R.color.status_fresh_bg));
                layoutCardContent.setBackgroundColor(Color.parseColor("#FFFFFF"));
            }

            textViewStatusBadge.setBackground(badgeBg);

            buttonEdit.setOnClickListener(v -> {
                if (listener != null) listener.onEdit(ingredient);
            });

            buttonDelete.setOnClickListener(v -> {
                if (listener != null) listener.onDelete(ingredient);
            });
        }

        return convertView;
    }
}
