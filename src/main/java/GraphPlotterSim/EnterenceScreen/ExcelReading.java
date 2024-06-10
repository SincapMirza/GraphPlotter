package GraphPlotterSim.EnterenceScreen;

import GraphPlotterSim.SelectionScreen.SelectionScreenFrame;
import com.aspose.cells.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ExcelReading {
    private static ExcelReading excelReading;
    private int columnCount;
    private List<String> columnHeaders;
    private List<String> columnHeaders2; // Yeni liste
    private List<List<Double>> columnDataLists = new ArrayList<>();
    private boolean firstColumnIsString;

    private ExcelReading(){}

    public void readExcelData(String filePath) {
        columnHeaders = new ArrayList<>(); // Sütun başlıkları listesini başlatma
        columnHeaders2 = new ArrayList<>(); // Yeni listeyi başlatma

        try {
            // Excel dosyasını yükleme
            Workbook workbook = new Workbook(filePath);

            // İlk sayfayı seçme
            Worksheet worksheet = workbook.getWorksheets().get(0);
            Cells cells = worksheet.getCells();

            // Veri içeren en son sütunun indeksini alma
            int maxDataColumn = cells.getMaxColumn();

            // Sütun sayısını belirle ve değişkene atama
            columnCount = maxDataColumn + 1;

            // İlk satırdaki sütun başlıklarını listeye aktarma
            for (int i = 0; i < columnCount; i++) {
                Cell cell = cells.get(0, i); // İlk satırdaki her sütun hücresini alma
                String header = cell.getStringValue(); // Hücre değerini alma
                columnHeaders.add(header); // Listeye ekleme
            }

            // İlk sütunu son satıra kadar okuma
            int maxDataRow = cells.getMaxDataRow();
            firstColumnIsString = false;
            for (int i = 1; i <= maxDataRow; i++) {
                Cell cell2 = cells.get(i, 0); // İlk sütundaki hücreyi alma
                if (cell2.getType() == CellValueType.IS_STRING) {
                    firstColumnIsString = true;
                    columnHeaders2.add(cell2.getStringValue()); // Değeri columnHeaders2 listesine ekleme
                }
            }

            // Eğer ilk sütun string ise, ilk satırın değerini columnHeaders listesine eklememe
            if (firstColumnIsString && !columnHeaders.isEmpty()) {
                columnHeaders.remove(0); // İlk satırı columnHeaders listesinden kaldırma
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<List<Double>> readDataContent(String filePath, List<Integer> columnNumbers) {
        columnDataLists.clear();
        try {
            // Excel dosyasını yükleme
            Workbook workbook = new Workbook(filePath);

            // İlk sayfayı seçme
            Worksheet worksheet = workbook.getWorksheets().get(0);
            Cells cells = worksheet.getCells();

            // Sütun verilerini saklamak için listeler oluşturma
            for (int col : columnNumbers) {
                columnDataLists.add(new ArrayList<>());
            }

            // Veri içeren en son satırın indeksini alma
            int maxDataRow = cells.getMaxDataRow();

            // Sütun verilerini listelere aktarma
            for (int i = 1; i <= maxDataRow; i++) {
                for (int j = 0; j < columnNumbers.size(); j++) {
                    Cell cell = cells.get(i, columnNumbers.get(j));
                    if (cell.getType() == CellValueType.IS_NUMERIC) {
                        columnDataLists.get(j).add(cell.getDoubleValue());
                    } else {
                        columnDataLists.get(j).add(Double.NaN);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return columnDataLists;
    }

    public int getColumnCount() {
        return columnCount;
    }

    public List<String> getColumnHeaders() {
        return columnHeaders;
    }

    public List<String> getColumnHeaders2() { // Yeni getter metodu
        return columnHeaders2;
    }

    public static ExcelReading getInstance() {
        if (excelReading == null) {
            synchronized (EnterenceScreenFrame.class) {
                if (excelReading == null) {
                    excelReading = new ExcelReading();
                }
            }
        }
        return excelReading;
    }

    public List<List<Double>> getColumnDataLists() {
        return columnDataLists;
    }

    public boolean isFirstColumnIsString() {
        return firstColumnIsString;
    }
}
