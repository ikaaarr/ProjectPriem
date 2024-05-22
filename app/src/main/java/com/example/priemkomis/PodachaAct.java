package com.example.priemkomis;

import static android.app.PendingIntent.getActivity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class PodachaAct extends AppCompatActivity implements AdapterView.OnItemSelectedListener{
DatabaseReference databaseReferenceAbiturient;
    private Context mContext;
String Abit = "Abiturient";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        setContentView(R.layout.activity_podacha);
        Spinner spin = findViewById(R.id.spin);
        Spinner spin2 = findViewById(R.id.spin2);
        Spinner spin3 = findViewById(R.id.spin3);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.группы, android.R.layout.simple_spinner_item);
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this, R.array.Оригинал, android.R.layout.simple_spinner_item);
        ArrayAdapter<CharSequence> adapter3 = ArrayAdapter.createFromResource(this, R.array.Допгруппы, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin.setAdapter(adapter);
        spin2.setAdapter(adapter2);
        spin3.setAdapter(adapter3);
        spin.setOnItemSelectedListener(this);
        spin2.setOnItemSelectedListener(this);
        spin3.setOnItemSelectedListener(this);
        EditText FIO = findViewById(R.id.FIO);
        EditText NumberPhone = findViewById(R.id.NumberPhone);
        EditText Ball = findViewById(R.id.BallAttestata);
databaseReferenceAbiturient =FirebaseDatabase.getInstance().getReference(Abit);
        Button btnPodacha = findViewById(R.id.button);
        btnPodacha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Fio = FIO.getText().toString();
                String Numberphone = NumberPhone.getText().toString();
                String ball = Ball.getText().toString();

                if(FIO.length()<10){
                    Toast.makeText(mContext, "Минимальное кол-во символов - 10", Toast.LENGTH_SHORT).show();

                }
                if(NumberPhone.length()<11){
                    Toast.makeText(mContext, "Введите корректный номер", Toast.LENGTH_SHORT).show();

                }

                try {
                    // Преобразование строки в число
                    Float ballValue = Float.parseFloat(ball);

                    // Проверка на максимальный балл
                    int maxBall = 5; // Максимальный балл
                    if (ballValue > maxBall) {
                        Toast.makeText(mContext, "Балл не может превышать " + maxBall, Toast.LENGTH_SHORT).show();
                        return;
                    }
                } catch (NumberFormatException e) {
                    // Обработка исключения, если введенная строка не может быть преобразована в число
                    Toast.makeText(mContext, "Некорректный формат числа", Toast.LENGTH_SHORT).show();
                    return;
                }
                // Проверяем, что поля FIO, NumberPhone и Ball не пустые
                if (TextUtils.isEmpty(Fio) || TextUtils.isEmpty(Numberphone) || TextUtils.isEmpty(ball)) {
                    Toast.makeText(PodachaAct.this, "Заполните все поля", Toast.LENGTH_SHORT).show();
                    return; // Прекращаем выполнение метода onClick, так как есть пустые поля
                }

                // Получаем выбранные элементы из выпадающих списков
                String selectedItem = spin2.getSelectedItem().toString();
                String selectedItem2 = spin.getSelectedItem().toString();
                String selectedItem3 = spin3.getSelectedItem().toString();

                // Проверяем, что выбраны все обязательные элементы
                if (selectedItem2.equals("Группа") || selectedItem.equals("Оригинал/Копия аттестата") || selectedItem3.equals("Доп.Группа")) {
                    Toast.makeText(PodachaAct.this, "Выберите элементы", Toast.LENGTH_SHORT).show();
                    return; // Прекращаем выполнение метода onClick, так как не выбраны все обязательные элементы
                }

                // Создаем объект Abiturient и отправляем его в базу данных
                Abiturient abiturient = new Abiturient(Fio, Numberphone, ball, selectedItem, selectedItem2, selectedItem3);
                databaseReferenceAbiturient.push().setValue(abiturient);

                // Показываем сообщение о том, что заявление отправлено
                Toast.makeText(PodachaAct.this, "Заявление отправлено", Toast.LENGTH_SHORT).show();
            }
        });


        ImageView imageViewDopInfo = findViewById(R.id.imageViewDopInfo);
        imageViewDopInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(PodachaAct.this,Otchet.class);
                startActivity(intent);
            }
        });
    }
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        String select = adapterView.getItemAtPosition(i).toString();
        if (select.equals("ИСИП")) {
            MyDialogFragment dialog = new MyDialogFragment(this);
            dialog.show(getSupportFragmentManager(), "dialog");

        }
    }




    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}