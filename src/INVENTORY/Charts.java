package INVENTORY;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Rectangle;
import java.sql.SQLException;
import java.sql.ResultSet;
import javax.swing.JPanel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;

public class Charts {
    
    public DefaultCategoryDataset loadDataset(String xAxis, String yAxis, String data) {
        Connections connect = new Connections();
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        String query = "SELECT " + xAxis + ", " + yAxis + " FROM inventory ORDER BY " + yAxis + " " + data + " LIMIT 6";
        
        try {
            connect.connectToDatabase();  
            ResultSet rs = connect.getStatement().executeQuery(query);
            while (rs.next()) {
                String x = rs.getString(xAxis);
                double y = rs.getDouble(yAxis);
                dataset.addValue(y, "", x);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dataset;
    }
    
    public void showLineChart(JPanel panelLineChart,String xAxis,String yAxis, String data) {
        CategoryDataset dataset = loadDataset(xAxis, yAxis, data);

        JFreeChart lineChart = ChartFactory.createLineChart("", "Products", "Sales", dataset, PlotOrientation.VERTICAL, false, true, false);
        
        CategoryPlot lineCategoryPlot = lineChart.getCategoryPlot();
        LineAndShapeRenderer lineRenderer = (LineAndShapeRenderer) lineCategoryPlot.getRenderer();
        
        lineCategoryPlot.setBackgroundPaint(Color.WHITE);
        
        Color lineChartColor = new Color(123,164,255);
        lineRenderer.setSeriesPaint(10, lineChartColor);
        ChartPanel lineChartPanel = new ChartPanel(lineChart);
        panelLineChart.removeAll();
        
        lineChart.getTitle().setFont(new Font("Lucida Sans", Font.PLAIN, 12));
        
        // Set font for domain axis (x-axis)
        CategoryAxis domainAxis = lineCategoryPlot.getDomainAxis();
        domainAxis.setTickLabelFont(new Font("Lucida Sans", Font.PLAIN, 12));
        // Set font for range axis (y-axis)
        NumberAxis rangeAxis = (NumberAxis) lineCategoryPlot.getRangeAxis();
        rangeAxis.setTickLabelFont(new Font("Lucida Sans", Font.PLAIN, 12));
        
        lineCategoryPlot.setOutlineVisible(false); // Remove outline border
        lineCategoryPlot.setBackgroundPaint(Color.WHITE);
        lineCategoryPlot.getRenderer().setSeriesStroke(0, new BasicStroke(2.0f)); // Customize line thickness
        lineCategoryPlot.getRenderer().setSeriesPaint(0, Color.BLUE); // Customize line color
        lineCategoryPlot.getRenderer().setSeriesShape(0, new Rectangle(5, 5)); // Customize data point shape 
        panelLineChart.add(lineChartPanel, BorderLayout.CENTER);
        panelLineChart.validate();
    }
    
    public void showBarChart(JPanel panelShowBarChart,String xAxis,String yAxis, String data){
        CategoryDataset dataset = loadDataset(xAxis, yAxis, data);       
        
        JFreeChart chart = ChartFactory.createBarChart("","Stocks","Products", dataset, PlotOrientation.VERTICAL, false,true,false);
        ChartPanel barChartPanel = new ChartPanel(chart); 
        CategoryPlot categoryPlot = chart.getCategoryPlot();
        BarRenderer renderer = (BarRenderer) categoryPlot.getRenderer();
        
        categoryPlot.setBackgroundPaint(Color.WHITE);
        categoryPlot.setRangeGridlinesVisible(false);   
        chart.getTitle().setFont(new Font("Lucida Sans", Font.PLAIN, 12));        
        CategoryAxis domainAxis = categoryPlot.getDomainAxis();
        domainAxis.setTickLabelFont(new Font("Lucida Sans", Font.PLAIN, 12));
        //Set font for range axis (y-axis)
        NumberAxis rangeAxis = (NumberAxis) categoryPlot.getRangeAxis();
        rangeAxis.setTickLabelFont(new Font("Lucida Sans", Font.PLAIN, 12));
        
        Color color = new Color(123,164,255);
        renderer.setSeriesPaint(0, color);
        renderer.setDrawBarOutline(false);        

        panelShowBarChart.removeAll();
        panelShowBarChart.add(barChartPanel, BorderLayout.CENTER);
        panelShowBarChart.validate();
      
    }
    
    public void showPieChart(JPanel panelPieChart){
        Connections connect = new Connections();
        DefaultPieDataset dataset = new DefaultPieDataset();

        String query = "SELECT category, SUM(quantity) AS total FROM inventory GROUP BY category";
        
        try {
            connect.connectToDatabase();  
            ResultSet rs = connect.getStatement().executeQuery(query);

            while (rs.next()) {
                String category = rs.getString("category");
                double total = rs.getDouble("total");
                dataset.setValue(category, total); // Set category and total for the pie chart
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        JFreeChart pieChart = ChartFactory.createPieChart("", dataset, false, true, false);
        PiePlot piePlot = (PiePlot) pieChart.getPlot();

        // Set custom colors for the sections (optional)
        piePlot.setSectionPaint("", new Color(255,255,102));
        piePlot.setSectionPaint("", new Color(102,255,102)); 
        piePlot.setSectionPaint("", new Color(255,102,153)); 
        piePlot.setSectionPaint("", new Color(0,204,204)); 
        piePlot.setSectionPaint("", new Color(255, 0, 0)); 
        piePlot.setSectionPaint("", new Color(0, 255, 0)); 
        piePlot.setSectionPaint("", new Color(0, 0, 255)); 
        piePlot.setSectionPaint("", new Color(255, 255, 0));
        piePlot.setSectionPaint("", new Color(255, 0, 255));
        
        // Change the title font
        pieChart.getTitle().setFont(new Font("Lucida Sans", Font.PLAIN, 12));

        // Change the section label font
        piePlot.setLabelFont(new Font("Lucida Sans", Font.PLAIN, 12));

        // Set the section label paint (color)
        piePlot.setLabelPaint(Color.BLACK);

        
        piePlot.setBackgroundPaint(Color.white);
        
        // Create chartPanel to display chart(graph)
        ChartPanel pieChartPanel = new ChartPanel(pieChart);
        panelPieChart.removeAll();
        piePlot.setOutlineStroke(null);
        panelPieChart.add(pieChartPanel, BorderLayout.CENTER);
        panelPieChart.validate();    
    }
    
    
    
}
