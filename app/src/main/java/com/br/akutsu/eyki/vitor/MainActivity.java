package com.br.akutsu.eyki.vitor;

import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.br.akutsu.eyki.vitor.adapter.ChequeAdapter;
import com.br.akutsu.eyki.vitor.entities.Cheque;
import com.google.android.material.textfield.TextInputEditText;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicReference;

public class MainActivity extends AppCompatActivity {

    private SeekBar sbJuros;
    private TextView tvJuros;
    private TextInputEditText tiValor;
    private Button btnIncluir;
    private DatePicker datePicker;
    private ListView lvItem;
    private ArrayList<Cheque> cheques;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        sbJuros = findViewById(R.id.sbJuros);
        tvJuros = findViewById(R.id.tvJuros);
        tiValor = findViewById(R.id.tiValor);
        btnIncluir = findViewById(R.id.btnIncluir);
        datePicker = findViewById(R.id.dpData);
        lvItem = findViewById(R.id.lvItem);

        cheques = new ArrayList<>();
        ChequeAdapter chequeAdapter = new ChequeAdapter(this, cheques);
        lvItem.setAdapter(chequeAdapter);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        sbJuros.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
                tvJuros.setText(String.format("%.2f", progress / 10.0));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        btnIncluir.setOnClickListener((view) -> {
            Cheque cheque = calcularDias();
            if (cheque.getJuros() != 0 && cheque.getDias() != 0 && cheque.getValor() != 0) {
                cheques.add(cheque);
                resetarValores();
                chequeAdapter.notifyDataSetChanged();
            }
        });

        lvItem.setOnItemLongClickListener((parent, view, position, id) -> {
            cheques.remove(position);
            chequeAdapter.notifyDataSetChanged();

            return true;
        });


        lvItem.setOnItemClickListener((adapterView, view, i, l) -> {
            AtomicReference<Double> valor = new AtomicReference<>((double) 0);
            AtomicReference<Double> juros = new AtomicReference<>((double) 0);
            cheques.forEach((cheque) -> {
                valor.updateAndGet(v -> new Double((double) (v + cheque.getValor())));
                juros.updateAndGet(v -> new Double((double) (v + cheque.getJuros())));
            });

            Toast.makeText(MainActivity.this, "Valor líquido: `" + String.format("%.2f", valor.get() - juros.get()), Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    private Cheque calcularDias() {
        double jurosMensais = Double.parseDouble((String) tvJuros.getText());
        double valorCheque = Double.parseDouble(String.valueOf(tiValor.getText()));

        LocalDate hoje = LocalDate.now();

        LocalDate datacheque = LocalDate.of(datePicker.getYear(), datePicker.getMonth() + 1, datePicker.getDayOfMonth());
        int diasCheque = (int) hoje.until(datacheque, ChronoUnit.DAYS);
        if (diasCheque > 0) {
            Toast.makeText(this, "Faltam " + diasCheque + " para depositar o cheque", Toast.LENGTH_SHORT).show();
            double juroscheque = valorCheque * (((jurosMensais / 30) * diasCheque) / 100);
            Toast.makeText(this, "Será descontado R$ " + juroscheque + " do valor do cheque", Toast.LENGTH_SHORT).show();

            return new Cheque(valorCheque, diasCheque, juroscheque);
        } else {
            Toast.makeText(this, "Data invalida", Toast.LENGTH_SHORT).show();
            return new Cheque(0, 0, 0);
        }
    }

    private void resetarValores() {
        datePicker.updateDate(LocalDate.now().getYear(), LocalDate.now().getMonthValue() - 1, LocalDate.now().getDayOfMonth());
        tiValor.setText("");
    }
}