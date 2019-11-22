public class Exceptions {
    public static void main(String[] args) {
        int N = 4; // тут можно изменить количество ячеек для ловли MyArraySizeException
        int M = 4; // и тут тоже

        String[][] Arr = new String[N][M];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                Arr[i][j] = String.valueOf(Math.round(Math.random() * 10));
            }
        }
    }

    public static void printArrSum(String[][] arr) throws MyArraySizeException {
        int sum = 0;
        if (arr.length != 4 || arr[0].length != 4) throw new MyArraySizeException(arr.length, arr[0].length);
        else {
            for (int i = 0; i < arr.length; i++) {
                for (int j = 0; j < arr[0].length; j++) {
                    try {
                        sum += Integer.parseInt(arr[i][j]);
                    } catch (NumberFormatException e){
                        System.out.println("Неверные данные в ячейке "+ i +" " + j);
                        e.printStackTrace();
                    }
                }
            }
            System.out.println("Сумма элементов массива " + sum);
        }
    }

    public static void printArr(String[][] arr) {
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr[0].length; j++) {
                System.out.print(" "+ arr[i][j]);
            }
            System.out.println();
        }
    }

    public static class  MyArraySizeException extends Exception {
        public MyArraySizeException(int rows, int columns){
            int A = 4; // при превышении А строк выкинет исключение
            int B = 4; // при превышении В столбцов выкинет исключение
            System.out.print("Неверный размер массива. ");
            if(rows > A){
                System.out.println("Строк должно быть " + A + ", а не " + rows + ". ");
            }
            else if (columns > B){
                System.out.println( "Cтолбцов должно быть " + B + ", а не " + columns);
            }
        }
    }
}
