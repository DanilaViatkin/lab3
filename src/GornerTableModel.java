import javax.swing.table.AbstractTableModel;

@SuppressWarnings("serial")
public class GornerTableModel extends AbstractTableModel {
    private final Double[] coefficients;
    private final Double from;
    private final Double to;
    private final Double step;

    private Double gornerCalc(Double[] coefficients, Double x) {
        Double result = coefficients[0];
        for (int i = 1; i < coefficients.length; i++) {
            result *= x;
            result += coefficients[i];
        }
        return result;
    }

    private Boolean flag(Object value) {
        String string = GornerTableCellRenderer.formatter.format(value);
        return string.charAt(0) == string.charAt(string.length() - 1);
    }

    public GornerTableModel(Double from, Double to, Double step, Double[] coefficients) {
        this.from = from;
        this.to = to;
        this.step = step;
        this.coefficients = coefficients;
    }

    public Double getFrom() {
        return from;
    }

    public Double getTo() {
        return to;
    }

    public Double getStep() {
        return step;
    }

    public int getColumnCount() {
        return 3;
    }

    public int getRowCount() {
        return ((Double) Math.ceil((to - from) / step)).intValue() + 1;
    }

    public Object getValueAt(int row, int col) {
        // Вычислить значение X как НАЧАЛО_ОТРЕЗКА + ШАГ*НОМЕР_СТРОКИ
        double x = from + step * row;
        switch (col) {
            case 0:
                return x;
            case 1:
                return gornerCalc(coefficients, x);
            case 2:
                return flag(gornerCalc(coefficients, x));
        }
        return null;
    }

    public String getColumnName(int col) {
        switch (col) {
            case 0:
                return "Значение X";
            case 1:
                return "Значение многочлена";
            case 2:
                return "Кравевая симметрия";
        }
        return null;
    }

    public Class<?> getColumnClass(int col) {
        // во всех столбцах находятся значения типа Double
        return Double.class;
    }
}
