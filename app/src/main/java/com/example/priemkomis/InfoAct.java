package com.example.priemkomis;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;

public class InfoAct extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        ImageView imageViewPodacha = findViewById(R.id.imageViewPodacha);
        imageViewPodacha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(InfoAct.this, PodachaAct.class);
                startActivity(intent);
            }
        });
        ImageView imageViewDopInfo = findViewById(R.id.imageViewDopInfo);
        imageViewDopInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri webpage = Uri.parse("https://katt-kazan.ru/");
                Intent intent = new Intent(Intent.ACTION_VIEW, webpage);
                startActivity(intent);
            }
        });
        WebView webView = findViewById(R.id.webview);
        String htmlText = "<html><head><style type='text/css'>p {text-indent: 30px; margin:0; padding:0;} </style></head>" +
                "<body>"
                + "<p>ДОКУМЕНТЫ, НЕОБХОДИМЫЕ ДЛЯ ПРИЕМА:/КАБУЛ ИТҮ ӨЧЕН КИРӘКЛЕ ДОКУМЕНТЛАР:</p>"
                + "<p>1. Заявление (по установленной форме)/Гариза (билгеләнгән форма буенча) </p>"
                + "<p>2. Паспорт (ксерокопия)/Паспорт (күчермәсе)</p>"
                + "<p>3. Аттестат об образовании (ксерокопия или оригинал)/Белеме турында аттестат (күчермәсе яки оригиналы)</p>"
                + "<p>4. Фотография 3х4 см (4 шт.)/Фоторәсем 3х4 см (4 данә) </p>"
                + "<p>5. Медицинская справка (форма 086-У) + Cертификат прививок (ксерокопия) - только для очного отделения/Медицина белешмәсе (086-У формасы) + Прививкалар турында сертификат (күчермәсе) - бары тик көндезге бүлек өчен генә</p>"
                + "<p>6. Характеристика школьная (только для очного отделения)/Мәктәптән бирелгән сыйфатнамә (бары тик көндезге бүлек өчен генә)</p>"
                + "<p>7. Ксерокопия трудовой книжки - только для заочного отделения/Хезмәт кенәгәсенең күчермәсе- бары тик читтән торып уку бүлеге өчен генә</p>"
                + "<p>               </p>"
                + "<p>ДОКУМЕНТЫ, НЕОБХОДИМЫЕ ПО ИТОГАМ ПОСТУПЛЕНИЯ: / КЕРҮ НӘТИҖӘЛӘРЕ БУЕНЧА КИРӘКЛЕ ДОКУМЕНТЛАР: </p>"
                + "<p>1. Медицинская карта (из школы)/Медицина картасы (мәктәптән)</p>"
                + "<p>2. Сертификат прививок (ксерокопия)/Прививкалар турында сертификат (күчермәсе)</p>"
                + "<p>3. Страховой медицинский полис (ксерокопия)/Мәҗбүри медицина иминияте  полисы (күчермәсе)</p>"
                + "<p>4. Страховое свидетельство (ксерокопия)/Иминият таныклыгы (күчермәсе)</p>"
                + "<p>5. ИНН (ксерокопия в 2 экземплярах)/ИНН салым түләүченең идентификацион номеры (2 нөсхәдә күчермәсе)</p>"
                + "<p>6. Приписное свидетельство (ксерокопия)/Хәрби хезмәткә чакырылырга тиешле граждан өчен таныклык (күчермәсе)</p>"
                + "<p>Зачисление осуществляется по результатам среднего балла аттестата./Кабул итү аттестатагы уртача балл нәтиҗәләре буенча башкарыла.</p>"
                + "</body></html>";
        webView.loadData(htmlText, "text/html", "utf-8");


    }
}