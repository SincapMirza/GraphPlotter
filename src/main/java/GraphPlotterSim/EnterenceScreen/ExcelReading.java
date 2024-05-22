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
        columnHeaders = new ArrayList<>(); // Sütun başlıkları listesini başlat
        columnHeaders2 = new ArrayList<>(); // Yeni listeyi başlat

        try {
            // Excel dosyasını yükle
            Workbook workbook = new Workbook(filePath);

            // İlk sayfayı seç
            Worksheet worksheet = workbook.getWorksheets().get(0);
            Cells cells = worksheet.getCells();

            // Veri içeren en son sütunun indeksini alın
            int maxDataColumn = cells.getMaxColumn();

            // Sütun sayısını belirle ve değişkene ata
            columnCount = maxDataColumn + 1;

            // İlk satırdaki sütun başlıklarını listeye aktar
            for (int i = 0; i < columnCount; i++) {
                Cell cell = cells.get(0, i); // İlk satırdaki her sütun hücresini al
                String header = cell.getStringValue(); // Hücre değerini al
                columnHeaders.add(header); // Listeye ekle
            }

            // İlk sütunu son satıra kadar okuyun
            int maxDataRow = cells.getMaxDataRow();
            firstColumnIsString = false;
            for (int i = 1; i <= maxDataRow; i++) {
                Cell cell = cells.get(i, 0); // İlk sütundaki hücreyi al
                if (cell.getType() == CellValueType.IS_STRING) {
                    firstColumnIsString = true;
                    columnHeaders2.add(cell.getStringValue()); // Değeri columnHeaders2 listesine ekle
                }
            }

            // Eğer ilk sütun string ise, ilk satırın değerini columnHeaders listesine eklemeyin
            if (firstColumnIsString) {
                columnHeaders.removeFirst(); // İlk satırı columnHeaders listesinden kaldır
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<List<Double>> readDataContent(String filePath, List<Integer> columnNumbers) {
        columnDataLists.clear();
        try {
            // Excel dosyasını yükle
            Workbook workbook = new Workbook(filePath);

            // İlk sayfayı seç
            Worksheet worksheet = workbook.getWorksheets().get(0);
            Cells cells = worksheet.getCells();

            // Sütun verilerini saklamak için listeler oluştur
            for (int col : columnNumbers) {
                columnDataLists.add(new ArrayList<>());
            }

            // Veri içeren en son satırın indeksini alın
            int maxDataRow = cells.getMaxDataRow();

           // int j = 0;
           /* if(firstColumnIsString){
                j++;
            }*/
            // Sütun verilerini listelere aktar
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
