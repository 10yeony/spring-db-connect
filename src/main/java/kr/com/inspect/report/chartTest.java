package kr.com.inspect.report;

import org.apache.poi.util.Units;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.knowm.xchart.BitmapEncoder;
import org.knowm.xchart.CategoryChart;
import org.knowm.xchart.CategoryChartBuilder;
import org.knowm.xchart.Histogram;
import org.knowm.xchart.style.Styler;
import org.knowm.xchart.style.colors.ChartColor;

import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class chartTest {
    public static void main(String[] args) {
        List<String> list = new ArrayList<>();
        List<Double> list1 = new ArrayList<>();
        List<Double> list2 = new ArrayList<>();
        List<Double> list3 = new ArrayList<>();
        String title = "학년과 성별로 나누어 측정한 발화시간";
        list.add("학년");list.add("여 (단위 : 분)");list.add("남 (단위 : 분)");list.add("미기입 (단위 : 분)");
        list1.add(1d);list1.add(4313.54);list1.add(1678.85);list1.add(1629.71);
        list2.add(2d);list2.add(2427.36);list2.add(2141.98);list2.add(1453.73);
        list3.add(3d);list3.add(1557.38);list3.add(6862.85);list3.add(2519.35);

        try (XWPFDocument document = new XWPFDocument()) {
//            // Create Chart
//            CategoryChart chart = new CategoryChartBuilder().width(800).height(600).title("Score Histogram").xAxisTitle("Score").yAxisTitle("Number").build();
//
//            // Customize Chart
//            chart.getStyler().setLegendPosition(Styler.LegendPosition.InsideNW);
//            chart.getStyler().setHasAnnotations(true);
//
//            // Series
//            chart.addSeries("test 1", Arrays.asList(new Integer[] { 0, 1, 2, 3, 4 }), Arrays.asList(new Integer[] { 4, 5, 9, 6, 5 }));

            // Create Chart
            CategoryChart chart = new CategoryChartBuilder().width(800).height(600).theme(Styler.ChartTheme.GGPlot2).title(title).xAxisTitle(list.get(0)).yAxisTitle("").build();
            
            // Customize Chart
            // 속성 상자 위치
            chart.getStyler().setLegendPosition(Styler.LegendPosition.InsideNW);
            // 막대 간 간격
            chart.getStyler().setAvailableSpaceFill(.96);
            // 막대 겹치게
            chart.getStyler().setOverlapped(true);
            // 차트 배경색
            chart.getStyler().setPlotBackgroundColor(ChartColor.getAWTColor(ChartColor.WHITE));
            chart.getStyler().setHasAnnotations(true);
//            chart.getStyler().setYAxisMax(4500d);
//            chart.getStyler().setYAxisMin(0d);
            // 제목 배경색
//            chart.getStyler().setChartTitleBoxBackgroundColor(new Color(0, 222, 0));

            // Series
            chart.addSeries(list.get(1), Arrays.asList(new String[] {list1.get(0).toString(), list2.get(0).toString(), list3.get(0).toString()}), list1.subList(1,list1.size()));
            chart.addSeries(list.get(2), Arrays.asList(new String[] {list1.get(0).toString(), list2.get(0).toString(), list3.get(0).toString()}), list2.subList(1,list2.size()));
            chart.addSeries(list.get(3), Arrays.asList(new String[] {list1.get(0).toString(), list2.get(0).toString(), list3.get(0).toString()}), list3.subList(1,list3.size()));

//            chart.addSeries("test 1", Arrays.asList(new Integer[] { 0, 1, 2, 3, 4 }), Arrays.asList(new Integer[] { 4, 5, 9, 6, 5 }));


//            // New Chart Element
//            CategoryChart chart = new CategoryChartBuilder().width(500).height(400).theme(Styler.ChartTheme.GGPlot2).title("Title").build();
//            chart.setTitle("Issue Count");
//            // Customize Chart
//            Color[] sliceColors = new Color[]{new Color(27, 50, 119), new Color(58, 146, 56), new Color(0, 161, 222), new Color(154, 205, 102), new Color(246, 199, 182)};
//            chart.getStyler().setSeriesColors(sliceColors);
//            chart.getStyler().setAxisTitlesVisible(false);
//
//            // Series
//            chart.addSeries("Critical", new ArrayList<>(Arrays.asList(new String[]{"Count"})), new ArrayList<>(Arrays.asList(new Number[]{10})));
//            chart.addSeries("High", new ArrayList<>(Arrays.asList(new String[]{"High"})), new ArrayList<>(Arrays.asList(new Number[]{5})));
//            chart.addSeries("Medium", new ArrayList<>(Arrays.asList(new String[]{"Medium"})), new ArrayList<>(Arrays.asList(new Number[]{2})));
//            chart.addSeries("Low", new ArrayList<>(Arrays.asList(new String[]{"Low"})), new ArrayList<>(Arrays.asList(new Number[]{1})));
//


            // Create and store a jpg image of the chart, then append it to the document
            BitmapEncoder.saveBitmapWithDPI(chart, "/home/namu/Documents/tmp.jpg", BitmapEncoder.BitmapFormat.JPG, 300);
            document.createParagraph().createRun().addPicture(new FileInputStream("/home/namu/Documents/tmp.jpg"), XWPFDocument.PICTURE_TYPE_JPEG, "tmp.jpg", Units.toEMU(500), Units.toEMU(400));


            new File("/home/namu/Documents/tmp.jpg").delete();

            FileOutputStream fileOutputStream = new FileOutputStream("/home/namu/Documents/doc1.docx");
            document.write(fileOutputStream);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
