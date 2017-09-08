package com.example.arham.dltesting;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.LegendRenderer;
import com.jjoe64.graphview.series.BarGraphSeries;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.*;

public class GraphDisplay extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph_display);
        GraphView graph2 = (GraphView) findViewById(R.id.graph);
        LineGraphSeries<DataPoint> series = new LineGraphSeries<DataPoint>(new DataPoint[] {
                new DataPoint(0, 1),
                new DataPoint(1, 5),
                new DataPoint(2, 3),
                new DataPoint(3, 2),
                new DataPoint(4, 6)
        });
        graph2.addSeries(series);

        try {
            ZipFile f = new ZipFile("c://users//arham/downloads/20170828.lis.z");
            InputStream temp = f.getInputStream(f.getEntry("closing.lis"));
        } catch (IOException e) {
            e.printStackTrace();
            TextView view = (TextView) findViewById(R.id.textView2);
            view.setText("File not found");
        }
    }


}